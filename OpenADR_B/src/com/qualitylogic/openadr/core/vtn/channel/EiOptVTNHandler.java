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
import com.qualitylogic.openadr.core.action.IResponseCanceledOptAckAction;
import com.qualitylogic.openadr.core.action.IResponseCreatedOptAckAction;
import com.qualitylogic.openadr.core.action.ResponseCanceledOptAckActionList;
import com.qualitylogic.openadr.core.action.ResponseCreatedOptAckActionList;
import com.qualitylogic.openadr.core.action.impl.DefaultOadrCanceledOptEvent;
import com.qualitylogic.openadr.core.action.impl.DefaultOadrCreatedOptEvent;
import com.qualitylogic.openadr.core.base.Debug;
import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.channel.Handler;
import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.EiResponseType;
import com.qualitylogic.openadr.core.signal.OadrCancelOptType;
import com.qualitylogic.openadr.core.signal.OadrCanceledOptType;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedOptType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.signal.helper.CreatedOptConformanceValidationHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatedOptEventHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.OadrValidationEventHandler;
import com.qualitylogic.openadr.core.util.Trace;

public class EiOptVTNHandler implements Handler {

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

			//new SchemaHelper();
			//unmarshall.setSchema(SchemaHelper.getSchema());

			if (trace != null) {
				OadrValidationEventHandler.checkForSchemaValidationError(data,
						ServiceType.VTN_PULL_MODE,Direction.Receive);
			}

			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			
			Object test = new SchemaHelper().getPayloadFromOadrSignedObject(oadrSignedObject);
			
			
			if (!TestSession.isValidationErrorFound()) {

				new SchemaHelper();

				if (test.getClass() == OadrCreateOptType.class) {
					
					OadrPayload oadrPayload = SchemaHelper.createOadrPayload();
					
					System.out.print("\n-> OadrCreateOpt Request Received.\n");
					OadrCreateOptType oadrCreateOptType = (OadrCreateOptType) test;
					TestSession.addCreateOptEventReceivedToList(oadrCreateOptType);

					IResponseCreatedOptAckAction responseEventAction = ResponseCreatedOptAckActionList.getNextResponseEventAction();
					boolean isPreconditionsMet = false;
					if (responseEventAction != null) {
						isPreconditionsMet = responseEventAction
								.isPreConditionsMet();
					}
					OadrCreatedOptType oadrCreatedOptType = null;
					if (responseEventAction != null && isPreconditionsMet) {
						// Preconditions met. Return Response.
						oadrCreatedOptType = responseEventAction.getCreatedOptTypResponse();
						
						IPrompt prompt = responseEventAction.getPrompt();
						
						if (prompt != null) {
							prompt.Prompt(prompt.getPromptMessage());
						}
				
						
					} else {
						// Preconditions did not meet or no OadrCreatedOptType
						// available

						if (responseEventAction != null
								&& !isPreconditionsMet) {
							String preConditionValidationFailed = "\n Creating default OadrCreateOptType - precondition failed ";
							System.out.print(preConditionValidationFailed);
							LogHelper.addTrace(preConditionValidationFailed);
							responseEventAction.resetToInitialState();	
						}
						
						if (responseEventAction == null) {
							String unexpectedCreatedEvent = "\n Creating default OadrCreateOptType";
							
							System.out.print(unexpectedCreatedEvent);
							LogHelper.addTrace(unexpectedCreatedEvent);
							
						} 
						
						
						oadrCreatedOptType = new DefaultOadrCreatedOptEvent()
								.getCreatedOptTypResponse();
						oadrCreatedOptType.getEiResponse().setResponseCode("200");
						oadrCreatedOptType.setOptID(oadrCreateOptType.getOptID());
						oadrCreatedOptType.getEiResponse().setRequestID(oadrCreateOptType.getRequestID());



					}
					
					oadrPayload.getOadrSignedObject().setOadrCreatedOpt(oadrCreatedOptType);

					String stroadrCreatedOptType = SchemaHelper.getoadrPayloadObjectAsString(oadrPayload);

//					JAXBContext context = JAXBContext
//							.newInstance(OadrCreatedOptType.class);
//					String stroadrCreatedOptType = SchemaHelper.asString(context,
//							oadrCreatedOptType);

					Document document = builder.parse(new ByteArrayInputStream(
							stroadrCreatedOptType.getBytes()));
					representation.setDocument(document);
					long size = OadrUtil.getSize(document);
					representation.setSize(size);

					new ByteArrayInputStream(
							stroadrCreatedOptType.getBytes("UTF-8"));

					if (trace != null) {
						trace.getLogFileContentTrace()
								.append("\n----------------RESPONSE---------------------");
						LogHelper.logCurrentTime();
						// OadrUtil.logResponseStatus(response);
						trace.getLogFileContentTrace().append(
								com.qualitylogic.openadr.core.util.OadrUtil
										.formatXMLAsString(stroadrCreatedOptType));

					}

					if (trace != null) {
						OadrValidationEventHandler
								.checkForSchemaValidationError(stroadrCreatedOptType,
										ServiceType.VTN,Direction.Send);
						OadrCreatedOptType oadrCreatedOpt = CreatedOptEventHelper.createCreatedOptTypeEventFromString(stroadrCreatedOptType);
						
						CreatedOptConformanceValidationHelper.validateRequestIDsMatch(oadrCreateOptType, oadrCreatedOpt);
						
					}

					if(responseEventAction!=null && responseEventAction.islastEvent()){
						System.out.print("\n<- Last OadrCreatedOpt Response Returned.\n");
						TestSession.setTestCaseDone(true);
					} else {
						System.out.print("\n<- OadrCreatedOpt Response Returned.\n");
					}
					
					return stroadrCreatedOptType;
				} if (test.getClass() == OadrCancelOptType.class) {
					System.out.print("\n-> OadrCancelOpt Request Received.\n");
					OadrCancelOptType oadrCancelOptType = (OadrCancelOptType) test;
					TestSession.addCancelOptTypeReceivedToList(oadrCancelOptType);

					boolean isValidOptID = getOptID(oadrCancelOptType);
					
					IResponseCanceledOptAckAction responseEventAction = null;
					boolean isPreconditionsMet = false;
					if (isValidOptID) {
						responseEventAction = ResponseCanceledOptAckActionList.getNextResponseEventAction();
						if (responseEventAction != null) {
							isPreconditionsMet = responseEventAction.isPreConditionsMet();
						}
					}
					OadrCanceledOptType oadrCanceledOptType = null;
					if (responseEventAction != null && isPreconditionsMet) {
						// Preconditions met. Return Response.
						oadrCanceledOptType = responseEventAction.getOadrCanceledOptType();
					} else {
						// Preconditions did not meet or no OadrCreatedOptType
						// available

						if (responseEventAction == null) {
							String unexpectedCreatedEvent = "\n Creating default OadrCanceledOpt";
							System.out.print(unexpectedCreatedEvent);
							if (trace != null) {
								trace.getLogFileContentTrace().append(unexpectedCreatedEvent);
							}
						} else if (isValidOptID && !isPreconditionsMet) {
							String preConditionValidationFailed = "\n Creating default OadrCanceledOpt - precondition failed ";
							System.out.print(preConditionValidationFailed);
							if (trace != null) {
								trace.getLogFileContentTrace().append(preConditionValidationFailed);
							}
						}
						oadrCanceledOptType = new DefaultOadrCanceledOptEvent().getOadrCanceledOptType();
						oadrCanceledOptType.setOptID(oadrCancelOptType.getOptID());
						oadrCanceledOptType.getEiResponse().setRequestID(oadrCancelOptType.getRequestID());
						
						if (!isValidOptID) {
							EiResponseType eiResponse = oadrCanceledOptType.getEiResponse();
							eiResponse.setResponseCode(ErrorConst.INVALID_ID_452);
							eiResponse.setResponseDescription("ERROR");							
						}
					}
					
//					JAXBContext context = JAXBContext
//							.newInstance(OadrCanceledOptType.class);
//					String strOadrCanceledOptType = SchemaHelper.asString(context,
//							oadrCanceledOptType);

					OadrPayload oadrPayload = SchemaHelper.createOadrPayload();
					oadrPayload.getOadrSignedObject().setOadrCanceledOpt(oadrCanceledOptType);
					
					String strOadrCanceledOptType = SchemaHelper.getoadrPayloadObjectAsString(oadrPayload);
					
					Document document = builder.parse(new ByteArrayInputStream(
							strOadrCanceledOptType.getBytes()));
					representation.setDocument(document);
					long size = OadrUtil.getSize(document);
					representation.setSize(size);

					new ByteArrayInputStream(
							strOadrCanceledOptType.getBytes("UTF-8"));

					if (trace != null) {
						trace.getLogFileContentTrace()
								.append("\n----------------RESPONSE---------------------");
						LogHelper.logCurrentTime();
						// OadrUtil.logResponseStatus(response);
						trace.getLogFileContentTrace().append(
								com.qualitylogic.openadr.core.util.OadrUtil
										.formatXMLAsString(strOadrCanceledOptType));

					}

					if (trace != null) {
						OadrValidationEventHandler
								.checkForSchemaValidationError(strOadrCanceledOptType,
										ServiceType.VTN,Direction.Send);
					}

					if(responseEventAction!=null && responseEventAction.islastEvent()){
						System.out.print("\n<- Last OadrCanceledOpt Response Returned.\n");
						TestSession.setTestCaseDone(true);
					} else {
						System.out.print("\n<- OadrCanceledOpt Response Returned.\n");
					}
					return strOadrCanceledOptType;
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

	private boolean getOptID(OadrCancelOptType oadrCancelOptType) {
		boolean isValidOptID = false;
		try {
			OadrCreateOptType createOpt = TestSession.getCreateOptEventReceivedList().get(TestSession.getCreateOptEventReceivedList().size() - 1);
			String optID = createOpt.getOptID();
			isValidOptID = oadrCancelOptType.getOptID().equals(optID);
		} catch (Exception e) {
			System.out.println("Warning, problem getting optID.");
			// set this to true for other test cases
			isValidOptID = true;
		}
		return isValidOptID;
	}

}
