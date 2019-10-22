package com.qualitylogic.openadr.core.ven.channel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;

import com.mysql.jdbc.Util;
import com.qualitylogic.openadr.core.action.IResponseCanceledReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseCreatedReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.IResponseRegisteredReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseUpdatedReportTypeAckAction;
import com.qualitylogic.openadr.core.action.ResponseCanceledReportTypeAckActionList;
import com.qualitylogic.openadr.core.action.ResponseCreatedReportTypeAckActionList;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.ResponseRegisteredReportTypeAckActionList;
import com.qualitylogic.openadr.core.action.ResponseUpdatedReportTypeAckActionList;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseCanceledReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseCreatedReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseRegisteredReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseRegisteredReportTypeNoClearAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseUpdatedReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.base.Debug;
import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.channel.Handler;
import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCancelReportType;
import com.qualitylogic.openadr.core.signal.OadrCanceledReportType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.signal.OadrReportRequestType;
import com.qualitylogic.openadr.core.signal.OadrReportType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdatedReportType;
import com.qualitylogic.openadr.core.signal.helper.CanceledReportConformanceValidationHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatedReportConformanceValidationHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisteredReportConformanceValidationHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.signal.helper.UpdatedReportConformanceValidationHelper;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.OadrValidationEventHandler;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.util.XMLDBUtil;

public class EiReportVENHandler implements Handler {

	@Override
	public String handle(String data) {
		// System.out.println("EiReportVENHandler.handle()");
		
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
			trace.getLogFileContentTrace().append("\nVTN -> VEN");
			trace.getLogFileContentTrace().append(
					"\n----------------REQUEST---------------------\n");

			trace.getLogFileContentTrace().append("\n");

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
			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			Object test = new SchemaHelper().getPayloadFromOadrSignedObject(oadrSignedObject);
			if (test.getClass() == OadrRegisterReportType.class) {
				// work around the issue of CreatedPartyRegistration too slow to update the DB XML
				Thread.sleep(3 * 1000);
			}
			
			//unmarshall.setSchema(SchemaHelper.getSchema());

			if (trace != null) {
				OadrValidationEventHandler.checkForSchemaValidationError(data,
						ServiceType.VTN_PULL_MODE,Direction.Receive);
			}

			if (!TestSession.isValidationErrorFound()) {

				if (test.getClass() == OadrRegisterReportType.class) {
					System.out.print("\n-> OadrRegisterReport Request Received.\n");
					OadrRegisterReportType oadrRegisterReportType = (OadrRegisterReportType) test;

					new XMLDBUtil().addEachReportReceived(oadrRegisterReportType, ServiceType.VTN);
					
					TestSession
							.addOadrRegisterReportTypeReceivedToList(oadrRegisterReportType);
		
					IResponseRegisteredReportTypeAckAction responseEventAction = ResponseRegisteredReportTypeAckActionList.getNextResponseRegisteredReportAckAction();
					boolean isPreconditionsMet = false;
					if (responseEventAction != null) {
						isPreconditionsMet = responseEventAction
								.isPreConditionsMet();
					}
					OadrRegisteredReportType oadrRegisteredReportType = null;
					if (responseEventAction != null && isPreconditionsMet) {
						// Preconditions met. Return Response.
						oadrRegisteredReportType = responseEventAction.getOadrRegisteredReportResponse();

						IPrompt prompt = responseEventAction.getPrompt();
						
						if (prompt != null) {
							prompt.Prompt(prompt.getPromptMessage());
						}
				
					} else {

						if (responseEventAction != null
								&& !isPreconditionsMet) {
							String preConditionValidationFailed = "\n Creating default OadrRegisteredReport - precondition failed ";
							System.out.print(preConditionValidationFailed);
							LogHelper.addTrace(preConditionValidationFailed);
							responseEventAction.resetToInitialState();	
						}
						
						if (responseEventAction == null) {
							String unexpectedCreatedEvent = "\n Creating default OadrRegisteredReport";
							
							System.out.print(unexpectedCreatedEvent);
							LogHelper.addTrace(unexpectedCreatedEvent);
							
						} 
						
						oadrRegisteredReportType = new DefaultResponseRegisteredReportTypeNoClearAckAction().getOadrRegisteredReportResponse();
						
						if(oadrRegisteredReportType.getOadrReportRequest()!=null){
							oadrRegisteredReportType.getOadrReportRequest().clear();							
						}
						
					}
					
					oadrRegisteredReportType.getEiResponse().setRequestID(oadrRegisterReportType.getRequestID());
					oadrRegisteredReportType.getEiResponse().setResponseCode("200");
					oadrRegisteredReportType.getEiResponse().setResponseDescription("OK");

					TestSession.addOadrRegisteredReportTypeSentToList(oadrRegisteredReportType);

					String strRegisteredReportType = SchemaHelper.getRegisteredReportTypeAsString(oadrRegisteredReportType);

					Document document = builder.parse(new ByteArrayInputStream(
							strRegisteredReportType.getBytes()));
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
										.formatXMLAsString(strRegisteredReportType));

					}

					if (trace != null) {
						OadrValidationEventHandler
								.checkForSchemaValidationError(strRegisteredReportType,
										ServiceType.VTN,Direction.Send);
						RegisteredReportConformanceValidationHelper.validateRequestIDsMatch(oadrRegisterReportType, oadrRegisteredReportType);
					}

					if(responseEventAction!=null && responseEventAction.islastEvent()){
						System.out.print("\n<- Last OadrRegisteredReport Response Returned.\n");
						TestSession.setTestCaseDone(true);
					} else {
						System.out.print("\n<- OadrRegisteredReport Response Returned.\n");
					}					

					return strRegisteredReportType;
				}else if (test.getClass() == OadrCreateReportType.class) {
					System.out.print("\n-> OadrCreateReport Request Received.\n");
					OadrCreateReportType oadrCreateReportType = (OadrCreateReportType) test;
					TestSession
							.addCreateReportTypeReceivedToList(oadrCreateReportType);
					String requestIDReceived = oadrCreateReportType.getRequestID();
					
					IResponseCreatedReportTypeAckAction responseEventAction = ResponseCreatedReportTypeAckActionList.getNextResponseCreatedReportAckAction();
					boolean isPreconditionsMet = false;
					if (responseEventAction != null) {
						isPreconditionsMet = responseEventAction
								.isPreConditionsMet();
					}
					OadrCreatedReportType oadrCreatedReportType = null;
					if (responseEventAction != null && isPreconditionsMet) {
						// Preconditions met. Return Response.
						oadrCreatedReportType = responseEventAction.getOadrCreatedReportResponse();
						
						IPrompt prompt = responseEventAction.getPrompt();
						
						if (prompt != null) {
							prompt.Prompt(prompt.getPromptMessage());
						}
					} else {

						if (responseEventAction != null
								&& !isPreconditionsMet) {
							String preConditionValidationFailed = "\n Creating default OadrCreatedReport - precondition failed ";
							System.out.print(preConditionValidationFailed);
							LogHelper.addTrace(preConditionValidationFailed);
							responseEventAction.resetToInitialState();	
						}
						
						if (responseEventAction == null) {
							String unexpectedCreatedEvent = "\n Creating default OadrCreatedReport";
							
							System.out.print(unexpectedCreatedEvent);
							LogHelper.addTrace(unexpectedCreatedEvent);
							
						} 

						oadrCreatedReportType = new DefaultResponseCreatedReportTypeAckAction().getOadrCreatedReportResponse();
						
						oadrCreatedReportType.getEiResponse().setResponseCode("200");
						oadrCreatedReportType.getEiResponse().setResponseDescription("OK");
						oadrCreatedReportType.getEiResponse().setRequestID(requestIDReceived);
						oadrCreatedReportType.getOadrPendingReports().getReportRequestID().clear();

					}
					

					String strCreatedReportType = SchemaHelper.getCreatedReportTypeAsString(oadrCreatedReportType);

					Document document = builder.parse(new ByteArrayInputStream(
							strCreatedReportType.getBytes()));
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
										.formatXMLAsString(strCreatedReportType));

					}

					if (trace != null) {
						OadrValidationEventHandler
								.checkForSchemaValidationError(strCreatedReportType,
										ServiceType.VTN,Direction.Send);
						CreatedReportConformanceValidationHelper.validateRequestIDsMatch(oadrCreateReportType, oadrCreatedReportType);
					}


					if(responseEventAction!=null && responseEventAction.islastEvent()){
						System.out.print("\nLast OadrCreatedReport Response Returned.\n");
						TestSession.setTestCaseDone(true);
					} else {
						System.out.print("\n<- OadrCreatedReport Response Returned.\n");
					}					

					return strCreatedReportType;
				}else if (test.getClass() == OadrUpdateReportType.class) {
					System.out.print("\n-> OadrUpdateReport Request Received.\n");
					OadrUpdateReportType oadrUpdateReportType = (OadrUpdateReportType) test;
					
					String requestIDReceived = oadrUpdateReportType.getRequestID();
					
					TestSession
							.addUpdateReportTypeReceivedList(oadrUpdateReportType);

					IResponseUpdatedReportTypeAckAction responseEventAction = ResponseUpdatedReportTypeAckActionList.getNextResponseUpdatedReportAckAction();
					boolean isPreconditionsMet = false;
					if (responseEventAction != null) {
						isPreconditionsMet = responseEventAction
								.isPreConditionsMet();
					}
					OadrUpdatedReportType oadrUpdatedReportType = null;
					if (responseEventAction != null && isPreconditionsMet) {
						// Preconditions met. Return Response.
						oadrUpdatedReportType = responseEventAction.getOadrUpdatedReportResponse();
						
						IPrompt prompt = responseEventAction.getPrompt();
						
						if (prompt != null) {
							prompt.Prompt(prompt.getPromptMessage());
						}
				

					} else {

						if (responseEventAction != null
								&& !isPreconditionsMet) {
							String preConditionValidationFailed = "\n Creating default OadrUpdatedReport - precondition failed ";
							System.out.print(preConditionValidationFailed);
							LogHelper.addTrace(preConditionValidationFailed);
							responseEventAction.resetToInitialState();	
						}
						
						if (responseEventAction == null) {
							String unexpectedCreatedEvent = "\n Creating default OadrUpdatedReport";
							
							System.out.print(unexpectedCreatedEvent);
							LogHelper.addTrace(unexpectedCreatedEvent);
							
						} 
						
						oadrUpdatedReportType = new DefaultResponseUpdatedReportTypeAckAction().getOadrUpdatedReportResponse();
						
						oadrUpdatedReportType.setOadrCancelReport(null);
					}

					oadrUpdatedReportType.getEiResponse().setResponseCode("200");
					oadrUpdatedReportType.getEiResponse().setResponseDescription("OK");
					oadrUpdatedReportType.getEiResponse().setRequestID(requestIDReceived);
					
					String strUpdatedReportType = SchemaHelper.getUpdatedReportTypeAsString(oadrUpdatedReportType);

					Document document = builder.parse(new ByteArrayInputStream(
							strUpdatedReportType.getBytes()));
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
										.formatXMLAsString(strUpdatedReportType));

					}

					if (trace != null) {
						OadrValidationEventHandler
								.checkForSchemaValidationError(strUpdatedReportType,
										ServiceType.VTN,Direction.Send);
						UpdatedReportConformanceValidationHelper.validateRequestIDsMatch(oadrUpdateReportType, oadrUpdatedReportType);
					}
					System.out.print("\n<- OadrUpdatedReport Response Returned.\n");

					if(responseEventAction!=null && responseEventAction.islastEvent()){
						System.out.print("\nLast OadrUpdatedReport Response Returned.\n");
						TestSession.setTestCaseDone(true);
					}
					
					return strUpdatedReportType;
				}else if (test.getClass() == OadrCancelReportType.class) {
					System.out.print("\n-> OadrCancelReport Request Received.\n");
					OadrCancelReportType oadrCancelReportType = (OadrCancelReportType) test;
					
					String requestID = oadrCancelReportType.getRequestID();
					TestSession
							.addCancelReportTypeReceivedToList(oadrCancelReportType);

					IResponseCanceledReportTypeAckAction responseEventAction = ResponseCanceledReportTypeAckActionList.getNextResponseCanceledReportAckAction();
					boolean isPreconditionsMet = false;
					if (responseEventAction != null) {
						isPreconditionsMet = responseEventAction
								.isPreConditionsMet();
					}
					OadrCanceledReportType oadrCanceledReportType = null;
					if (responseEventAction != null && isPreconditionsMet) {
						// Preconditions met. Return Response.
						oadrCanceledReportType = responseEventAction.getOadrCanceledReportResponse();
						
						IPrompt prompt = responseEventAction.getPrompt();
						
						if (prompt != null) {
							prompt.Prompt(prompt.getPromptMessage());
						}
					
					} else {
						if (responseEventAction != null
								&& !isPreconditionsMet) {
							String preConditionValidationFailed = "\n Creating default OadrCanceledReport - precondition failed ";
							System.out.print(preConditionValidationFailed);
							LogHelper.addTrace(preConditionValidationFailed);
							responseEventAction.resetToInitialState();	
						}
						
						if (responseEventAction == null) {
							String unexpectedCreatedEvent = "\n Creating default OadrCanceledReport";
							
							System.out.print(unexpectedCreatedEvent);
							LogHelper.addTrace(unexpectedCreatedEvent);
							
						} 
						oadrCanceledReportType = new DefaultResponseCanceledReportTypeAckAction().getOadrCanceledReportResponse();
						
						oadrCanceledReportType.getOadrPendingReports().getReportRequestID().clear();
					}
					
					if (oadrCanceledReportType.getEiResponse().getResponseCode().equals(ErrorConst.INVALID_ID_452)) {
						// need more study if we can safely remove this if-else condition
						// and just take the response code from oadrCanceledReportType itself
						oadrCanceledReportType.getEiResponse().setRequestID(requestID);
					} else {
						oadrCanceledReportType.getEiResponse().setResponseCode("200");
						oadrCanceledReportType.getEiResponse().setResponseDescription("OK");
						oadrCanceledReportType.getEiResponse().setRequestID(requestID);
					}

					String strCanceledReportType = SchemaHelper.getCanceledReportTypeAsString(oadrCanceledReportType);

					Document document = builder.parse(new ByteArrayInputStream(
							strCanceledReportType.getBytes()));
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
										.formatXMLAsString(strCanceledReportType));

					}

					if (trace != null) {
						OadrValidationEventHandler
								.checkForSchemaValidationError(strCanceledReportType,
										ServiceType.VTN,Direction.Send);
						CanceledReportConformanceValidationHelper.validateRequestIDsMatch(oadrCancelReportType, oadrCanceledReportType);
					}
					System.out.print("\n<- OadrCanceledReport Response Returned.\n");

					if(responseEventAction!=null && responseEventAction.islastEvent()){
						System.out.print("\nLast OadrCanceledReport Response Returned.\n");
						TestSession.setTestCaseDone(true);
					}
					
					return strCanceledReportType;
				}else if (test.getClass() == OadrCreatedReportType.class) {
					System.out.print("\n-> CreatedReport Request Received.\n");
					OadrCreatedReportType OadrCreatedReport = (OadrCreatedReportType) test;
					TestSession
							.addOadrCreatedReportTypeReceivedToList(OadrCreatedReport);
					String requestIDReceived = OadrCreatedReport.getEiResponse().getRequestID();
					
					IResponseEventAction responseEventAction = ResponseEventActionList.getNextResponseEventAction();
					boolean isPreconditionsMet = false;
					if (responseEventAction != null) {
						isPreconditionsMet = responseEventAction
								.isPreConditionsMet();
					}
					OadrResponseType oadrResponseType = null;
					if (responseEventAction != null && isPreconditionsMet) {
						// Preconditions met. Return Response.
						oadrResponseType = responseEventAction.getResponse();
	
						IPrompt prompt = responseEventAction.getPrompt();
						
						if (prompt != null) {
							prompt.Prompt(prompt.getPromptMessage());
						}
						
					} else {
						// Preconditions did not meet or no OadrCreatedOptType
						// available
						
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
						oadrResponseType = new Default_ResponseEventAction().getResponse();
						
						oadrResponseType.getEiResponse().setResponseCode("200");
						oadrResponseType.getEiResponse().setResponseDescription("OK");
						oadrResponseType.getEiResponse().setRequestID(requestIDReceived);
						
					}
					
					
					String strResponseType = SchemaHelper.getOadrResponseAsString(oadrResponseType);

					Document document = builder.parse(new ByteArrayInputStream(
							strResponseType.getBytes()));
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
										.formatXMLAsString(strResponseType));

					}

					if (trace != null) {
						OadrValidationEventHandler
								.checkForSchemaValidationError(strResponseType,
										ServiceType.VTN,Direction.Send);
					}
					
					if(responseEventAction!=null && responseEventAction.islastEvent()){
						System.out.print("\n<- Last OadrResponse Response Returned.\n");
						TestSession.setTestCaseDone(true);
					} else {
						System.out.print("\n<- OadrResponse Response Returned.\n");
					}
					
					return strResponseType;
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
