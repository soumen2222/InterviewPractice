package com.qualitylogic.openadr.core.vtn.channel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;

import com.mysql.jdbc.Util;
import com.qualitylogic.openadr.core.action.IResponseCanceledPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.action.IResponseCreatedPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseCanceledPartyRegistrationAckActionList;
import com.qualitylogic.openadr.core.action.ResponseCreatedPartyRegistrationAckActionList;
import com.qualitylogic.openadr.core.action.ResponseCreatedPartyRegistrationToQueryAckActionList;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.DefaultCanceledPartyRegistration;
import com.qualitylogic.openadr.core.action.impl.DefaultCreatedPartyRegistration;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.base.Debug;
import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.channel.Handler;
import com.qualitylogic.openadr.core.channel.util.StringUtil;
import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.exception.ValidationException;
import com.qualitylogic.openadr.core.signal.EiResponseType;
import com.qualitylogic.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCanceledPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCreatePartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrQueryRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrRequestReregistrationType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.signal.helper.CancelPartyRegistrationConfValHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.OadrValidationEventHandler;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.util.XMLDBUtil;

public class EiRegisterPartyVTNHandler implements Handler {

	@Override
	public String handle(String data) {
		// Request request = getRequest();
		// Response response = getResponse();
		DomRepresentation representation = null;
		String emptyResponseToVTN = "\nReturning empty response to VEN\n";

		try {
			representation = new DomRepresentation(MediaType.APPLICATION_XML);
		} catch (IOException e) {
			e.printStackTrace();
		}

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(false);
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		Trace trace = TestSession.getTraceObj();

		if (trace != null) {
			trace.getLogFileContentTrace().append("\nVEN -> VTN");
			trace.getLogFileContentTrace().append(
					"\n----------------REQUEST---------------------\n");

			if (data == null || data.trim().length() < 1) {
				TestSession.setValidationErrorFound(true);
				String unexpectedRequest = "\nConformance check failed : Unexpected Request Received.\n";
				System.out.print(unexpectedRequest);
				if (trace != null) {
					trace.getLogFileContentTrace().append(unexpectedRequest);
				}

				System.out.print(emptyResponseToVTN);
				return null;
			}

			trace.getLogFileContentTrace().append(
					com.qualitylogic.openadr.core.util.OadrUtil
							.formatXMLAsString(data));
		}
		try {
			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();
			
			//unmarshall.setSchema(SchemaHelper.getSchema());
			
			if (trace != null) {
				OadrValidationEventHandler.checkForSchemaValidationError(data,
						ServiceType.VTN_PULL_MODE,Direction.Receive);
			}


			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();	
			Object test = new SchemaHelper().getPayloadFromOadrSignedObject(oadrSignedObject);
			
			if (!TestSession.isValidationErrorFound()) {

				

				if (test.getClass() == OadrCreatePartyRegistrationType.class) {
					System.out.print("\n-> OadrCreatePartyRegistration Request Received.\n");
					
					OadrCreatePartyRegistrationType oadrCreatePartyRegistrationType = (OadrCreatePartyRegistrationType) test;
					TestSession
							.addOadrCreatePartyRegistrationReceivedToList(oadrCreatePartyRegistrationType);

					String createParty_registrationID=oadrCreatePartyRegistrationType.getRegistrationID();
					String createParty_venID=oadrCreatePartyRegistrationType.getVenID();
					String venIDToSetInCreated = "";
					String registrationIDToSetInCreated = "";
					
					
					//Case 1
					// No registration and VEN ID
					if((createParty_registrationID==null || createParty_registrationID.trim().length()<1) && (createParty_venID==null || createParty_venID.trim().length()<1)){
						venIDToSetInCreated = OadrUtil.createUniqueVENID();
						registrationIDToSetInCreated=OadrUtil.createUniqueRegistrationID();
						//Write to database.
						new XMLDBUtil().setRegistrationID(registrationIDToSetInCreated);
						if (!new PropertiesFileReader().isUseStaticVENID()) {
							new XMLDBUtil().setVENID(venIDToSetInCreated);
						}
						}else
					//Case 2
					//VEN ID present but no registration ID
					if((createParty_registrationID==null || createParty_registrationID.trim().length()<1) && (createParty_venID!=null && createParty_venID.trim().length()>0)){
						venIDToSetInCreated = createParty_venID;
						registrationIDToSetInCreated=OadrUtil.createUniqueRegistrationID();
						//Write to database.
						new XMLDBUtil().setRegistrationID(registrationIDToSetInCreated);
						if (!new PropertiesFileReader().isUseStaticVENID()) {
							new XMLDBUtil().setVENID(venIDToSetInCreated);
						}						
					} 
					if(!OadrUtil.isCreatePartyRegistration_PullModel()){
						String transportAddress = oadrCreatePartyRegistrationType.getOadrTransportAddress();
						new XMLDBUtil().setTransportAddress(transportAddress);
					}
					
					IResponseCreatedPartyRegistrationAckAction responseEventAction = ResponseCreatedPartyRegistrationAckActionList.getNextResponseCreatedPartyRegistrationActionAction();
					boolean isPreconditionsMet = false;
					if (responseEventAction != null) {
						isPreconditionsMet = responseEventAction
								.isPreConditionsMet();
					}
					OadrCreatedPartyRegistrationType createdPartyRegistrationResponse = null;
					if (responseEventAction != null && isPreconditionsMet) {
						// Preconditions met. Return Response.
						createdPartyRegistrationResponse = responseEventAction.getOadrCreatedPartyRegistration();
					
						IPrompt prompt = responseEventAction.getPrompt();
						
						if (prompt != null) {
							prompt.Prompt(prompt.getPromptMessage());
						}
						
					} else {
						// Preconditions did not meet or no OadrCreatedOptType
						// available
						
						if (responseEventAction != null
								&& !isPreconditionsMet) {
							String preConditionValidationFailed = "\n Creating default OadrCreatedPartyRegistration as precondition validation failed ";
							System.out.print(preConditionValidationFailed);
							LogHelper.addTrace(preConditionValidationFailed);
							responseEventAction.resetToInitialState();	
						}
						
						if (responseEventAction == null) {
							String unexpectedCreatedEvent = "\n Creating default OadrCreatedPartyRegistration";
							
							System.out.print(unexpectedCreatedEvent);
							LogHelper.addTrace(unexpectedCreatedEvent);
							
						} 
						createdPartyRegistrationResponse = new DefaultCreatedPartyRegistration(OadrCreatedPartyRegistrationType.class)
								.getOadrCreatedPartyRegistration();
						
					}
					
					OadrPayload oadrPayload = SchemaHelper.createOadrPayload();
					
					oadrPayload.getOadrSignedObject().setOadrCreatedPartyRegistration(createdPartyRegistrationResponse);
					
					String strOadrCreatedPartyRegistration = SchemaHelper.getoadrPayloadObjectAsString(oadrPayload);
					
					
					Document document = builder.parse(new ByteArrayInputStream(
							strOadrCreatedPartyRegistration.getBytes()));
					representation.setDocument(document);
					long size = OadrUtil.getSize(document);
					representation.setSize(size);

					if (trace != null) {
						trace.getLogFileContentTrace()
								.append("\n----------------RESPONSE---------------------");
						LogHelper.logCurrentTime();
						// OadrUtil.logResponseStatus(response);
						trace.getLogFileContentTrace().append(
								com.qualitylogic.openadr.core.util.OadrUtil
										.formatXMLAsString(strOadrCreatedPartyRegistration));

					}

					if (trace != null) {
						OadrValidationEventHandler
								.checkForSchemaValidationError(strOadrCreatedPartyRegistration,
										ServiceType.VTN,Direction.Send);
					}

					if(responseEventAction!=null && responseEventAction.isLastEvent()){
						System.out.print("\n<- Last OadrCreatedPartyRegistration Response Returned.\n");
						TestSession.setTestCaseDone(true);
					} else {
						System.out.print("\n<- OadrCreatedPartyRegistration Response Returned.\n");
					}
					
					return strOadrCreatedPartyRegistration;
				}else if (test.getClass() == OadrQueryRegistrationType.class) {
					System.out.print("\n-> OadrQueryRegistration Request Received.\n");
					OadrQueryRegistrationType oadrQueryRegistrationType = (OadrQueryRegistrationType) test;
					TestSession
							.addOadrQueryRegistrationTypeReceivedToList(oadrQueryRegistrationType);

					IResponseCreatedPartyRegistrationAckAction responseEventAction = ResponseCreatedPartyRegistrationToQueryAckActionList.getNextResponseCreatedPartyRegistrationActionAction();
					
					
					boolean isPreconditionsMet = false;
					if (responseEventAction != null) {
						isPreconditionsMet = responseEventAction
								.isPreConditionsMet();
					}
					OadrCreatedPartyRegistrationType createdPartyRegistrationResponse = null;
					if (responseEventAction != null && isPreconditionsMet) {
						// Preconditions met. Return Response.
						createdPartyRegistrationResponse = responseEventAction.getOadrCreatedPartyRegistration();
					
						
						IPrompt prompt = responseEventAction.getPrompt();
						
						if (prompt != null) {
							prompt.Prompt(prompt.getPromptMessage());
						}
						
					} else {
						// Preconditions did not meet or no OadrCreatedOptType
						// available

						
						if (responseEventAction != null
								&& !isPreconditionsMet) {
							String preConditionValidationFailed = "\n Creating default OadrCreatedPartyRegistration - precondition failed ";
							System.out.print(preConditionValidationFailed);
							LogHelper.addTrace(preConditionValidationFailed);
							responseEventAction.resetToInitialState();	
						}
						
						if (responseEventAction == null) {
							String unexpectedCreatedEvent = "\n Creating default OadrCreatedPartyRegistration";
							
							System.out.print(unexpectedCreatedEvent);
							LogHelper.addTrace(unexpectedCreatedEvent);
							
						} 
						
						createdPartyRegistrationResponse = new DefaultCreatedPartyRegistration(OadrQueryRegistrationType.class)
								.getOadrCreatedPartyRegistration();
						
						createdPartyRegistrationResponse.getEiResponse().setRequestID(oadrQueryRegistrationType.getRequestID());
						createdPartyRegistrationResponse.getEiResponse().setResponseCode("200");
						createdPartyRegistrationResponse.getEiResponse().setResponseDescription("OK");
//						String regID = new XMLDBUtil().getRegistrationID();
//						if(regID==null || regID.trim().equals("")){
//							regID=OadrUtil.createUniqueCreatePartyRegistrationReqID();
//						}
//						createdPartyRegistrationResponse.setRegistrationID(new XMLDBUtil().getRegistrationID());
//						createdPartyRegistrationResponse.setVenID(new PropertiesFileReader().getVenID());
//						createdPartyRegistrationResponse.setRegistrationID(regID);
						
						
					}
					
					OadrPayload oadrPayload = SchemaHelper.createOadrPayload();
					
					oadrPayload.getOadrSignedObject().setOadrCreatedPartyRegistration(createdPartyRegistrationResponse);

					String strOadrCreatedPartyRegistration = SchemaHelper.getoadrPayloadObjectAsString(oadrPayload);
					

					Document document = builder.parse(new ByteArrayInputStream(
							strOadrCreatedPartyRegistration.getBytes()));
					representation.setDocument(document);
					long size = OadrUtil.getSize(document);
					representation.setSize(size);

					if (trace != null) {
						trace.getLogFileContentTrace()
								.append("\n----------------RESPONSE---------------------");
						LogHelper.logCurrentTime();
						// OadrUtil.logResponseStatus(response);
						trace.getLogFileContentTrace().append(
								com.qualitylogic.openadr.core.util.OadrUtil
										.formatXMLAsString(strOadrCreatedPartyRegistration));

					}

					if (trace != null) {
						OadrValidationEventHandler
								.checkForSchemaValidationError(strOadrCreatedPartyRegistration,
										ServiceType.VTN,Direction.Send);
					}

					if(responseEventAction!=null && responseEventAction.isLastEvent()){
						System.out.print("\n<- Last OadrCreatedPartyRegistration Response Returned.\n");
						TestSession.setTestCaseDone(true);
					} else {
						System.out.print("\n<- OadrCreatedPartyRegistration Response Returned.\n");
					}
					
					return strOadrCreatedPartyRegistration;
				}else if (test.getClass() == OadrCancelPartyRegistrationType.class) {
					System.out.print("\n-> OadrCancelPartyRegistration Request Received.\n");
					OadrCancelPartyRegistrationType oadrCancelPartyRegistrationType = (OadrCancelPartyRegistrationType) test;
					TestSession
							.addCancelPartyRegistrationReceivedToList(oadrCancelPartyRegistrationType);
					
					IResponseCanceledPartyRegistrationAckAction responseEventAction = null;
					boolean isPreconditionsMet = false;
					responseEventAction = ResponseCanceledPartyRegistrationAckActionList.getNextResponseCanceledPartyRegistrationActionAction();
					
					if (responseEventAction != null) {
						isPreconditionsMet = responseEventAction.isPreConditionsMet();
					}

					OadrCanceledPartyRegistrationType oadrCanceledPartyRegistration = null;
					if (responseEventAction != null && isPreconditionsMet) {
						oadrCanceledPartyRegistration = responseEventAction.getOadrCanceledPartyRegistration();
						
						IPrompt prompt = responseEventAction.getPrompt();
						
						if (prompt != null) {
							prompt.Prompt(prompt.getPromptMessage());
						}
						
					} else {
						if (responseEventAction != null && !isPreconditionsMet) {
							String preConditionValidationFailed = "\n Creating default OadrCanceledPartyRegistration - precondition validation failed ";
							System.out.print(preConditionValidationFailed);
							LogHelper.addTrace(preConditionValidationFailed);
							responseEventAction.resetToInitialState();	
						}
						
						if (responseEventAction == null) {
							String unexpectedCreatedEvent = "\n Creating default OadrCanceledPartyRegistration";
							
							System.out.print(unexpectedCreatedEvent);
							LogHelper.addTrace(unexpectedCreatedEvent);
							
						} 
						
						oadrCanceledPartyRegistration = new DefaultCanceledPartyRegistration()
								.getOadrCanceledPartyRegistration();
					}

					oadrCanceledPartyRegistration.setRegistrationID(oadrCancelPartyRegistrationType.getRegistrationID());
					oadrCanceledPartyRegistration.getEiResponse().setRequestID(oadrCancelPartyRegistrationType.getRequestID());
					oadrCanceledPartyRegistration.setVenID(oadrCancelPartyRegistrationType.getVenID());
					
					
					String strOadrCanceledPartyRegistration = SchemaHelper.getCanceledPartyRegistrationAsString(oadrCanceledPartyRegistration);

					Document document = builder.parse(new ByteArrayInputStream(
							strOadrCanceledPartyRegistration.getBytes()));
					representation.setDocument(document);
					long size = OadrUtil.getSize(document);
					representation.setSize(size);

					new ByteArrayInputStream(
							strOadrCanceledPartyRegistration.getBytes("UTF-8"));

					if (trace != null) {
						trace.getLogFileContentTrace()
								.append("\n----------------RESPONSE---------------------");
						LogHelper.logCurrentTime();
						// OadrUtil.logResponseStatus(response);
						trace.getLogFileContentTrace().append(
								com.qualitylogic.openadr.core.util.OadrUtil
										.formatXMLAsString(strOadrCanceledPartyRegistration));

					}

					if (trace != null) {
						OadrValidationEventHandler
								.checkForSchemaValidationError(strOadrCanceledPartyRegistration,
										ServiceType.VTN,Direction.Send);
					}

					boolean isActiveRegistration = !StringUtil.isBlank(new XMLDBUtil().getRegistrationID());					
					boolean isRegistrationValid = CancelPartyRegistrationConfValHelper.isRegistrationValid(oadrCancelPartyRegistrationType,Direction.Receive);
					if (isActiveRegistration && isRegistrationValid) {
						new XMLDBUtil().resetDatabase();
					}
					
					if(responseEventAction!=null && responseEventAction.islastEvent()){
						System.out.print("\n<- Last OadrCanceledPartyRegistration Response Returned.\n");
						TestSession.setTestCaseDone(true);
					} else {
						System.out.print("\n<- OadrCanceledPartyRegistration Response Returned.\n");
					}
					
					return strOadrCanceledPartyRegistration;
				}else if (test.getClass() == OadrCanceledPartyRegistrationType.class) {
					System.out.print("\n-> OadrCanceledPartyRegistration Request Received.\n");
					OadrCanceledPartyRegistrationType oadrCanceledPartyRegistrationType = (OadrCanceledPartyRegistrationType) test;
					TestSession
							.addCanceledPartyRegistrationReceivedToList(oadrCanceledPartyRegistrationType);

					IResponseEventAction responseEventAction = ResponseEventActionList.getNextResponseEventAction();
					
					boolean isPreconditionsMet = false;
					if (responseEventAction != null) {
						isPreconditionsMet = responseEventAction
								.isPreConditionsMet();
					}
					OadrResponseType oadrResponseType = null;
					if (responseEventAction != null && isPreconditionsMet) {
						oadrResponseType = responseEventAction.getResponse();
						IPrompt prompt = responseEventAction.getPrompt();
						
						if (prompt != null) {
							prompt.Prompt(prompt.getPromptMessage());
						}
					} else {
						
						if (responseEventAction != null
								&& !isPreconditionsMet) {
							String preConditionValidationFailed = "\n Creating default OadrResponse - precondition failed ";
							System.out.print(preConditionValidationFailed);
							LogHelper.addTrace(preConditionValidationFailed);
							responseEventAction.resetToInitialState();	
						}
						
						if (responseEventAction == null) {
							String unexpectedCreatedEvent = "\n Creating default OadrResponse";
							
							System.out.print(unexpectedCreatedEvent);
							LogHelper.addTrace(unexpectedCreatedEvent);
							
						} 
						
						oadrResponseType = new Default_ResponseEventAction()
								.getResponse();
						 
						String RequestID = oadrCanceledPartyRegistrationType.getEiResponse().getRequestID();
						oadrResponseType.getEiResponse().setRequestID(RequestID);

					}
					
					
					
					
					String strResponse = SchemaHelper.getOadrResponseAsString(oadrResponseType);

					Document document = builder.parse(new ByteArrayInputStream(
							strResponse.getBytes()));
					representation.setDocument(document);
					long size = OadrUtil.getSize(document);
					representation.setSize(size);

					new ByteArrayInputStream(
							strResponse.getBytes("UTF-8"));

					if (trace != null) {
						trace.getLogFileContentTrace()
								.append("\n----------------RESPONSE---------------------");
						LogHelper.logCurrentTime();
						// OadrUtil.logResponseStatus(response);
						trace.getLogFileContentTrace().append(
								com.qualitylogic.openadr.core.util.OadrUtil
										.formatXMLAsString(strResponse));

					}

					if (trace != null) {
						OadrValidationEventHandler
								.checkForSchemaValidationError(strResponse,
										ServiceType.VTN,Direction.Send);
					}

					if(responseEventAction!=null && responseEventAction.islastEvent()){
						System.out.print("\n<- Last OadrResponse Returned.\n");
						TestSession.setTestCaseDone(true);
					} else {
						System.out.print("\n<- OadrResponse Returned.\n");
					}
					
					return strResponse;
				}else if (test.getClass() == OadrRequestReregistrationType.class) {
					System.out.print("\n-> OadrRequestReregistration Request Received.\n");
					OadrRequestReregistrationType oadrRequestReregistrationType = (OadrRequestReregistrationType) test;
					TestSession
							.addOadrRequestReregistrationReceivedToList(oadrRequestReregistrationType);

					Default_ResponseEventAction default_ResponseEventAction=new Default_ResponseEventAction();
					
					String strOadrResponse = SchemaHelper.getOadrResponseAsString(default_ResponseEventAction.getResponse());

					Document document = builder.parse(new ByteArrayInputStream(
							strOadrResponse.getBytes()));
					representation.setDocument(document);
					long size = OadrUtil.getSize(document);
					representation.setSize(size);

					if (trace != null) {
						trace.getLogFileContentTrace()
								.append("\n----------------RESPONSE---------------------");
						LogHelper.logCurrentTime();
						// OadrUtil.logResponseStatus(response);
						trace.getLogFileContentTrace().append(
								com.qualitylogic.openadr.core.util.OadrUtil
										.formatXMLAsString(strOadrResponse));

					}

					if (trace != null) {
						OadrValidationEventHandler
								.checkForSchemaValidationError(strOadrResponse,
										ServiceType.VTN,Direction.Send);
					}
					System.out.print("\n<- OadrResponse Response Returned.\n");

					return strOadrResponse;
				}else if (test.getClass() == OadrResponseType.class) {
					System.out.print("\n-> OadrResponse Request Received.\n");
					OadrResponseType oadrResponseType = (OadrResponseType) test;
					TestSession
							.addRegisterParty_OadrResponseListReceivedList(oadrResponseType);

					// response.setStatus(Status.SUCCESS_OK);

					if (trace != null) {
						trace.getLogFileContentTrace()
								.append("\n----------------RESPONSE---------------------");
						LogHelper.logCurrentTime();
						// OadrUtil.logResponseStatus(response);
						
					}
					
					System.out.print("\n<- null Response Returned.\n");
					return null;
				}else {
					TestSession.setValidationErrorFound(true);
					String unexpectedRequest = "\nConformance check failed : Unexpected Request of type "
							+ test.getClass() + "Received.\n";
					System.out.print(unexpectedRequest);
					if (trace != null) {
						trace.getLogFileContentTrace()
								.append(unexpectedRequest);
					}

				}

			} else {
				String unexpectedRequest = "\n*********Payload received will not be Processed due to previous errors.*********\n";

				if (trace != null) {

					trace.getLogFileContentTrace().append(unexpectedRequest);
					trace.getLogFileContentTrace().append(
							"\n----------------RESPONSE---------------------");
					LogHelper.logCurrentTime();
					trace.getLogFileContentTrace().append(emptyResponseToVTN);
					trace.getLogFileContentTrace().append(
							"\n------------------------------------------\n");

				}

				return null;
			}
		} catch (ValidationException ex) {
			TestSession.setValidationErrorFound(true);
			LogHelper.addTraceAndConsole("\n" + ex.getMessage());
		} catch (Exception ex) {
			if (Debug.printHandlerStackTrace) {
				ex.printStackTrace();
			}
			
			if (trace != null) {
				String unexpectedException = "\nUnexpected Exception has occurred\n";
				trace.getLogFileContentTrace().append(unexpectedException);
				String exceptionString = Util.stackTraceToString(ex);
				trace.getLogFileContentTrace().append(exceptionString);
				trace.getLogFileContentTrace().append(
						"\n-------------------------------\n");
				System.out.print(unexpectedException);
			}
		}

		if (trace != null) {

			trace.getLogFileContentTrace().append(
					"\n----------------RESPONSE---------------------");
			LogHelper.logCurrentTime();
			trace.getLogFileContentTrace().append(emptyResponseToVTN);
			trace.getLogFileContentTrace().append(
					"\n------------------------------------------\n");

		}

		System.out.print(emptyResponseToVTN);
		return null;
	}


}
