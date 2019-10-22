package com.qualitylogic.openadr.core.ven.channel;

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
import com.qualitylogic.openadr.core.action.IResponseCanceledPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseCanceledPartyRegistrationAckActionList;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.DefaultCanceledPartyRegistration;
import com.qualitylogic.openadr.core.base.Debug;
import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.channel.Handler;
import com.qualitylogic.openadr.core.channel.util.StringUtil;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.EiResponseType;
import com.qualitylogic.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCanceledPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrRequestReregistrationType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.signal.helper.CancelPartyRegistrationConfValHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.OadrValidationEventHandler;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.util.XMLDBUtil;

public class EiRegisterPartyVENHandler implements Handler {

	@Override
	public String handle(String data) {
		// System.out.println("EiRegisterPartyVENHandler.handle()");

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

				
				if (test.getClass() == OadrCancelPartyRegistrationType.class) {
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
					} else {
						
						if (responseEventAction != null
								&& !isPreconditionsMet) {
							String preConditionValidationFailed = "\n Creating default OadrCanceledPartyRegistration - precondition failed ";
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
										ServiceType.VEN,Direction.Send);
					}
					
					boolean isActiveRegistration = !StringUtil.isBlank(new XMLDBUtil().getRegistrationID());
					boolean isRegistrationValid = CancelPartyRegistrationConfValHelper.isRegistrationValid(oadrCancelPartyRegistrationType,Direction.Send);
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
				} else if (test.getClass() == OadrRequestReregistrationType.class){ 

					System.out
							.print("\n-> OadrRequestReregistration Request Received.\n");
					OadrRequestReregistrationType oadrRequestReregistration = (OadrRequestReregistrationType) test;
					
					ArrayList<OadrRequestReregistrationType>  oadrRequestReregistrationReceivedList= TestSession.getOadrRequestReregistrationReceivedList();
					
					oadrRequestReregistrationReceivedList
							.add(oadrRequestReregistration);

					IResponseEventAction eachResponseEventAction = ResponseEventActionList
							.getNextResponseEventAction();

					boolean isPreconditionsMet = false;
					if (eachResponseEventAction != null) {
						isPreconditionsMet = eachResponseEventAction
								.isPreConditionsMet();
					}

					String oadrResponseEventStr = null;

					if (eachResponseEventAction != null && isPreconditionsMet) {
						eachResponseEventAction.setEventCompleted(true);
						oadrResponseEventStr = SchemaHelper.getOadrResponseAsString(eachResponseEventAction.getResponse());
					
						Document document = builder.parse(new ByteArrayInputStream(
								oadrResponseEventStr.getBytes()));
						representation.setDocument(document);
						long size = OadrUtil.getSize(document);
						representation.setSize(size);
						
						System.out.print("\n<- OadrResponse Response Returned.\n");
						return oadrResponseEventStr;

					} else {
						if (eachResponseEventAction == null) {
							String noMoreOadrResponseEvents = "\n No more OadrResponse is available\n";
							if (trace != null) {
								trace.getLogFileContentTrace().append(
										noMoreOadrResponseEvents);
							}
							System.out.print(noMoreOadrResponseEvents);
						} else if (!isPreconditionsMet) {
							eachResponseEventAction.setEventCompleted(false);
							String preconditionValidation = "\n Not creating OadrResponse as the precondition validation failed";
							if (trace != null) {
								trace.getLogFileContentTrace().append(
										preconditionValidation);
							}
						}

					}
					
				} else {
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