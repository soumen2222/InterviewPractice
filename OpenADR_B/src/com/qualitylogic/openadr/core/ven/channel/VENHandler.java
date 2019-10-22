package com.qualitylogic.openadr.core.ven.channel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;

import com.qualitylogic.openadr.core.action.CreatedEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.channel.Handler;
import com.qualitylogic.openadr.core.channel.util.StringUtil;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.exception.NotApplicableException;
import com.qualitylogic.openadr.core.signal.EiResponseType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.OadrValidationEventHandler;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.ven.CreatedEventHandlerThread;
import com.qualitylogic.openadr.core.ven.VENServerResource;

public class VENHandler implements Handler {

	// static ArrayList<OadrDistributeEventType> oadrDistributeEventReceivedsList = new ArrayList<OadrDistributeEventType>();

	
	
	@Override
	public String handle(String data) {
		// System.out.println("VENHandler.handle()");
		
		/*
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(false);
		DocumentBuilder builder = null;
		
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		*/
		
		// Request request = getRequest();

		// getResponse();

		Trace trace = TestSession.getTraceObj();

		if (trace != null) {
			trace.getLogFileContentTrace().append("\nVTN -> VEN");
			trace.getLogFileContentTrace().append(
					"\n----------------REQUEST---------------------\n");

			trace.getLogFileContentTrace().append(
					com.qualitylogic.openadr.core.util.OadrUtil
							.formatXMLAsString(data));
		}

		DomRepresentation representation = null;

		try {
			representation = new DomRepresentation(MediaType.APPLICATION_XML);

			representation.setSize(0);
		
			} catch (IOException e) {
			e.printStackTrace();
		}

		String result = "";
		
		try {
			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();
			//unmarshall.setSchema(SchemaHelper.getSchema());
			if (trace != null) {
				unmarshall.setEventHandler(new OadrValidationEventHandler());
			}

			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			Object test = new SchemaHelper().getPayloadFromOadrSignedObject(oadrSignedObject);
				
			if (trace != null) {
				OadrValidationEventHandler.checkForSchemaValidationError(data,
						ServiceType.VEN,Direction.Receive);
			}

			if (!TestSession.isValidationErrorFound()) {
				if (test.getClass() == OadrDistributeEventType.class) {
					System.out.print("\n-> OadrDistributeEvent Request Received.\n");
					OadrDistributeEventType distributeEventReceived = (OadrDistributeEventType) test;
					VENServerResource.getOadrDistributeEventReceivedsList().add(distributeEventReceived);

					boolean isPreconditionsMet = false;
					ICreatedEventAction eachCreatedEventAction = CreatedEventActionList.getNextCreatedEventAction();
					if (eachCreatedEventAction != null) {
						isPreconditionsMet = eachCreatedEventAction.isPreConditionsMet();
					}

					String oadrCreatedEventStr = null;

					if (eachCreatedEventAction != null && isPreconditionsMet) {
						eachCreatedEventAction.setEventCompleted(true);
						oadrCreatedEventStr = eachCreatedEventAction
								.getCreateEvent();
						if (oadrCreatedEventStr != null
								&& oadrCreatedEventStr.length() > 1) {
							CreatedEventHandlerThread createdEventHandlerThread = new CreatedEventHandlerThread(
									eachCreatedEventAction);
							Thread createEventThread = new Thread(
									createdEventHandlerThread);
							createEventThread.start();
						}

					} else {
						if (eachCreatedEventAction == null) {
							String noMoreCreatedEvents = "\n No more CreatedEvents are available\n";
							if (trace != null) {
								trace.getLogFileContentTrace().append(
										noMoreCreatedEvents);
							}
							System.out.print(noMoreCreatedEvents);
						} else if (!isPreconditionsMet) {
							eachCreatedEventAction.setEventCompleted(false);
							String preconditionValidation = "\n Not creating CreatedEvents as the precondition validation failed";
							if (trace != null) {
								trace.getLogFileContentTrace().append(
										preconditionValidation);
							}
						}
					}
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

			}
		} catch (NotApplicableException e) {
			System.out.println(e.getMessage());
			System.out.println("N/A");
			LogHelper.addTrace(e.getMessage());
			LogHelper.setResult(LogResult.NA);
			System.exit(1);
		} catch (JAXBException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		TestSession.setValidationErrorFound(false);
		
		String responseMessage = null;
		if (StringUtil.isBlank(result)) {
			responseMessage = "<- Empty Response Returned.";
		} else {
			responseMessage = "<- OadrResponse Returned.";
		}

		if (trace != null) {

			trace.getLogFileContentTrace().append(
					"\n----------------RESPONSE---------------------");
			LogHelper.logCurrentTime();
			trace.getLogFileContentTrace().append(responseMessage);
			trace.getLogFileContentTrace().append(
					"\n------------------------------------------\n");

		}

		System.out.print("\n" + responseMessage + "\n");
		return result;
	}
}