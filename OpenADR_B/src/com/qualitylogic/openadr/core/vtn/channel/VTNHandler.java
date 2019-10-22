package com.qualitylogic.openadr.core.vtn.channel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;

import com.mysql.jdbc.Util;
import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.base.Debug;
import com.qualitylogic.openadr.core.bean.PingDistributeEventMap;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.channel.Handler;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.OadrValidationEventHandler;
import com.qualitylogic.openadr.core.util.Trace;

public class VTNHandler implements Handler {


	@Override
	public String handle(String data) {
		// Request request = getRequest();
		// Response response = getResponse();
		OadrDistributeEventType defaultDistributeEvent = null;
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

				//SchemaHelper().get;
				if (test.getClass() == OadrRequestEventType.class) {
					System.out.print("\n-> OadrRequestEvent Request Received.\n");
					OadrRequestEventType OadrRequestEventType = (OadrRequestEventType) test;
					IDistributeEventAction distributeEventAction = DistributeEventActionList
							.getNextDistributeEventAction();
					
					boolean isPreconditionsMet = false;
					
					
					if (distributeEventAction != null) {
						isPreconditionsMet = distributeEventAction
								.isPreConditionsMet(OadrRequestEventType);
						
					}

					OadrDistributeEventType oadrDistributeEventType = null;

					if (distributeEventAction != null && isPreconditionsMet) {
						// Preconditions met. Return DistributeEvent.
						oadrDistributeEventType = distributeEventAction
								.getDistributeEvent();
					} else {

						if (distributeEventAction != null
								&& !isPreconditionsMet) {
							String preConditionValidationFailed = "\n Creating default DistributeEvent without Events as the precondition validation failed";
							distributeEventAction.resetToInitialState();
							System.out.print(preConditionValidationFailed);
							if (trace != null) {
								trace.getLogFileContentTrace().append(
										preConditionValidationFailed);
							}
							distributeEventAction = null;
						} else {
							// Get previous completed event
							distributeEventAction = DistributeEventActionList
									.getPreviousCompletedDistributeEventAction(OadrRequestEventType);
						}

						// No previous sent events(may be due to the
						// preconditions not met) Return DistributeEvent without
						// events.
						if (distributeEventAction == null) {
							defaultDistributeEvent = DistributeEventSignalHelper
									.createDistributeEventPayloadWithoutEvent();

						} else {
							String parentDistributeReqID = "";
							oadrDistributeEventType = distributeEventAction
									.getDistributeEvent();
							parentDistributeReqID = oadrDistributeEventType
									.getRequestID();
							defaultDistributeEvent = DistributeEventSignalHelper
									.resetEventStatusAndFilterOutCancelled(oadrDistributeEventType);
							ArrayList<PingDistributeEventMap> pingDistributeEventMap = TestSession
									.getPingDistributeEventMap();
							boolean addToExistingEvent = false;

							for (PingDistributeEventMap eachPingDistributeEventMap : pingDistributeEventMap) {
								if (eachPingDistributeEventMap
										.getDistributeEventRequestID().equals(
												parentDistributeReqID)) {
									eachPingDistributeEventMap
											.addDistributeEvent(defaultDistributeEvent);
									addToExistingEvent = true;
									break;
								}

							}
							if (!addToExistingEvent) {
								PingDistributeEventMap pingMap = new PingDistributeEventMap();
								pingMap.setDistributeEventRequestID(parentDistributeReqID);
								pingMap.addDistributeEvent(defaultDistributeEvent);
								pingDistributeEventMap.add(pingMap);
							}

						}
					}
					String strOadrDistributeEvent = "";
					
					if (defaultDistributeEvent == null) {
						defaultDistributeEvent = oadrDistributeEventType;
					}
					
					OadrPayload oadrPayload = SchemaHelper.createOadrPayload();
					oadrPayload.getOadrSignedObject().setOadrDistributeEvent(defaultDistributeEvent);
					strOadrDistributeEvent = SchemaHelper.getoadrPayloadObjectAsString(oadrPayload);
					
					Document document = builder.parse(new ByteArrayInputStream(
							strOadrDistributeEvent.getBytes()));
					representation.setDocument(document);

					long size = OadrUtil.getSize(document);
					representation.setSize(size);
/*					new ByteArrayInputStream(
							strOadrDistributeEvent.getBytes("UTF-8"));
*/
					if (trace != null) {
						trace.getLogFileContentTrace()
								.append("\n----------------RESPONSE---------------------");
						LogHelper.logCurrentTime();
						// OadrUtil.logResponseStatus(response);
						trace.getLogFileContentTrace()
								.append(com.qualitylogic.openadr.core.util.OadrUtil
										.formatXMLAsString(strOadrDistributeEvent));

					}

					if (trace != null) {
						OadrValidationEventHandler
								.checkForSchemaValidationError(
										strOadrDistributeEvent,
										ServiceType.VTN_PULL_MODE,Direction.Send);
					}
					System.out
							.print("\n<- OadrDistributeEvent Response Returned.\n");

					if (defaultDistributeEvent == null
							&& distributeEventAction != null
							&& distributeEventAction.isEventCompleted()
							&& distributeEventAction
									.getCreatedEventTimeoutAction() != null) {
						distributeEventAction
								.startCreatedEventTimeoutActionThread();
					}
					return strOadrDistributeEvent;

				} else if (test.getClass() == OadrCreatedEventType.class) {
					System.out.print("\n-> OadrCreatedEvent Request Received.\n");
					OadrCreatedEventType createdEventReceived = (OadrCreatedEventType) test;
					TestSession
							.addCreatedEventReceivedToList(createdEventReceived);

					IResponseEventAction responseEventAction = ResponseEventActionList
							.getNextResponseEventAction();
					boolean isPreconditionsMet = false;
					if (responseEventAction != null) {
						isPreconditionsMet = responseEventAction
								.isPreConditionsMet();
					}
					OadrResponseType oadrResponseType = null;
					if (responseEventAction != null && isPreconditionsMet) {
						// Preconditions met. Return Response.
						oadrResponseType = responseEventAction.getResponse();
					} else {
						// Preconditions did not meet or no OadrResponseType
						// available

						if (responseEventAction == null) {
							String unexpectedCreatedEvent = "\n Creating default OadrResponseType";
							System.out.print(unexpectedCreatedEvent);
							if (trace != null) {
								trace.getLogFileContentTrace().append(
										unexpectedCreatedEvent);
							}
						} else if (!isPreconditionsMet) {
							String preConditionValidationFailed = "\n Creating default OadrResponseType as precondition failed ";
							System.out.print(preConditionValidationFailed);
							if (trace != null) {
								trace.getLogFileContentTrace().append(
										preConditionValidationFailed);
							}
						}
						oadrResponseType = new Default_ResponseEventAction()
								.getResponse();
						oadrResponseType.getEiResponse().setResponseCode("200");

					}
					
					OadrPayload oadrPayload = SchemaHelper.createOadrPayload();
					oadrPayload.getOadrSignedObject().setOadrResponse(oadrResponseType);
					String strOadrResponse = SchemaHelper.getoadrPayloadObjectAsString(oadrPayload);

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

					
					if(responseEventAction!=null && responseEventAction.islastEvent()){
						System.out.print("\n<- Last OadrResponse Response Returned.\n");
						TestSession.setTestCaseDone(true);
					} else {
						System.out.print("\n<- OadrResponse Response Returned.\n");
					}
					return strOadrResponse;
				} else {
					TestSession.setValidationErrorFound(true);
					String unexpectedRequest = "\nConformance check failed : Unexpected Request of type "
							+ test.getClass() + "received.\n";
					System.out.print(unexpectedRequest);
					if (trace != null) {
						trace.getLogFileContentTrace()
								.append(unexpectedRequest);
					}

				}

			} else {
				String unexpectedRequest = "\n*********Payload received will not be Processed due to previous errors.*********\n";
				System.out.println(unexpectedRequest);
				
				if (false) {
					// debugging only
					String s = trace.getLogFileContentTrace().toString();
					System.out.println(s.substring(s.lastIndexOf(">")));
				}
				
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
