package com.qualitylogic.openadr.core.oadrpoll.channel;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.restlet.ext.xml.DomRepresentation;

import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IRequestReregistrationAction;
import com.qualitylogic.openadr.core.action.IResponseCancelPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.action.IResponseCancelReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseCreateReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.IResponseRegisterReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseUpdateReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.channel.Handler;
import com.qualitylogic.openadr.core.common.IPrompt;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.exception.NotApplicableException;
import com.qualitylogic.openadr.core.oadrpoll.OadrPollQueue;
import com.qualitylogic.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCancelReportType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrPollType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrRequestReregistrationType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.helper.CancelPartyRegistrationConfValHelper;
import com.qualitylogic.openadr.core.signal.helper.CancelPartyRegistrationHelper;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrValidationEventHandler;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.util.XMLDBUtil;


public class OadrPollHandler implements Handler {

	public String handle(String oadrPoll) {
		// Request request = getRequest();
		
		DomRepresentation representation = null;
		// Response response = getResponse();
		
		Trace trace = TestSession.getTraceObj();

		if (trace != null) {
			trace.getLogFileContentTrace().append("\nVEN -> VTN");

			trace.getLogFileContentTrace().append(
					"\n----------------REQUEST---------------------\n");

			if (oadrPoll == null || oadrPoll.trim().length() < 1) {
				TestSession.setValidationErrorFound(true);
				String unexpectedRequest = "\nConformance check failed : Unexpected Request Received.\n";
				System.out.println(unexpectedRequest);
				if (trace != null) {
					trace.getLogFileContentTrace().append(unexpectedRequest);
				}

				// response.setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "No content received");

				return null;
			}

			trace.getLogFileContentTrace().append(
					com.qualitylogic.openadr.core.util.OadrUtil
							.formatXMLAsString(oadrPoll));
		}
		
		Object data = null;
		try {
		JAXBContext testcontext = JAXBContext
				.newInstance("com.qualitylogic.openadr.core.signal");
		InputStream is = new ByteArrayInputStream(oadrPoll.getBytes("UTF-8"));
		Unmarshaller unmarshall = testcontext.createUnmarshaller();

		OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
		Object test = new SchemaHelper().getPayloadFromOadrSignedObject(oadrSignedObject);

		if (test.getClass() != OadrPollType.class) {
			// response.setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "Expected oadrPoll");
			
			String unexpectedRequest = "\nConformance check failed : Unexpected Request or type "+test.getClass()+" Received.\n";
			System.out.println(unexpectedRequest);
			if (trace != null) {
				trace.getLogFileContentTrace().append(unexpectedRequest);
			}
			
			return null;
		}
		System.out.println("-> OadrPoll Request Received.");
		
		
		if (trace != null) {
			OadrValidationEventHandler.checkForSchemaValidationError(oadrPoll,
					ServiceType.VTN_PULL_MODE,Direction.Receive);
		}
		
		data = OadrPollQueue.getNext();

		OadrResponseType oadrResponseType = new Default_ResponseEventAction().getResponse();
		oadrResponseType.getEiResponse().setResponseCode("200");
		String defautResponse = SchemaHelper.getOadrResponseAsString(oadrResponseType);
		
		if (data==null) {
			System.out.println("<- Default OadrResponse Response Returned.");
			data=defautResponse;
		}else{
			
			if(data instanceof IDistributeEventAction){
				IDistributeEventAction	distributeEventAction = (IDistributeEventAction)data;
				
					boolean isPreconditionsMet = distributeEventAction
							.isPreConditionsMet(null);
					OadrDistributeEventType oadrDistributeEvent = null;
					if(isPreconditionsMet){
						distributeEventAction.setEventCompleted(true);
						oadrDistributeEvent = distributeEventAction.getDistributeEvent();
						data = SchemaHelper.getDistributeEventAsString(oadrDistributeEvent);
						
						if(distributeEventAction.getCreatedEventTimeoutAction() != null){
							distributeEventAction
							.startCreatedEventTimeoutActionThread();
						}
						System.out.println("<- OadrDistributeEvent Response Returned.");
						
						IPrompt prompt = distributeEventAction.getPrompt();
						
						if (prompt != null) {
							prompt.Prompt(prompt.getPromptMessage());
						}
						
					}else{
						data = defautResponse;
						String logMsg = "Returning default OadrResponse - precondition validation failed for DistributeEventAction";
						System.out.println(logMsg);
						LogHelper.addTrace(logMsg);
					}
				
			}else if(data instanceof IRequestReregistrationAction){

				IRequestReregistrationAction	requestReregistrationAction = (IRequestReregistrationAction)data;
				
				if(requestReregistrationAction.isPreConditionsMet()){
					requestReregistrationAction.setEventCompleted(true);
					OadrRequestReregistrationType oadrRequestReregistrationType = requestReregistrationAction.getOadrRequestReregistration();
					data = SchemaHelper.getOadrRequestReregistrationTypeAsString(oadrRequestReregistrationType);	
					System.out.println("<- OadrRequestReregistration Response Returned.");
					
					IPrompt prompt = requestReregistrationAction.getPrompt();
					
					if (prompt != null) {
						prompt.Prompt(prompt.getPromptMessage());
					}
					
				}else{
					data=defautResponse;
					String logMsg = "Returning default OadrResponse as precondition validation failed for RequestReregistrationAction";
					System.out.println(logMsg);
					LogHelper.addTrace(logMsg);
				}
			}else if(data instanceof IResponseCancelPartyRegistrationAckAction){
				IResponseCancelPartyRegistrationAckAction	requestCancelPartyRegistrationAckAction = (IResponseCancelPartyRegistrationAckAction)data;
				
				if(requestCancelPartyRegistrationAckAction.isPreConditionsMet()){
					requestCancelPartyRegistrationAckAction.setEventCompleted(true);
					OadrCancelPartyRegistrationType oadrCancelPartyRegistrationType = requestCancelPartyRegistrationAckAction.getOadrCancelPartyRegistration();
					data = SchemaHelper.getCancelPartyRegistrationAsString(oadrCancelPartyRegistrationType);					
					System.out.println("<- OadrCancelPartyRegistration Response Returned.");
					
					IPrompt prompt = requestCancelPartyRegistrationAckAction.getPrompt();
					
					if (prompt != null) {
						prompt.Prompt(prompt.getPromptMessage());
					}
					
				}else{
					data=defautResponse;
					String logMsg = "Returning default OadrResponse as precondition validation failed for ResponseCancelPartyRegistrationAckAction";
					System.out.println(logMsg);
					LogHelper.addTrace(logMsg);
				}
			}else if(data instanceof IRequestReregistrationAction){	
				IRequestReregistrationAction requestReregistrationAction = (IRequestReregistrationAction)data;
				
				if(requestReregistrationAction.isPreConditionsMet()){
					requestReregistrationAction.setEventCompleted(true);
					OadrRequestReregistrationType oadrRequestReregistration = requestReregistrationAction.getOadrRequestReregistration();
					data = SchemaHelper.getOadrRequestReregistrationTypeAsString(oadrRequestReregistration);
					System.out.println("<- OadrRequestReregistration Response Returned.");
		
					IPrompt prompt = requestReregistrationAction.getPrompt();
					
					if (prompt != null) {
						prompt.Prompt(prompt.getPromptMessage());
					}
					
				}else{
					data=defautResponse;
					String logMsg = "Returning default OadrResponse as precondition validation failed for RequestReregistrationAction";
					System.out.println(logMsg);
					LogHelper.addTrace(logMsg);
				}
			}else if(data instanceof IResponseRegisterReportTypeAckAction){	
				IResponseRegisterReportTypeAckAction responseRegisterReportTypeAckAction = (IResponseRegisterReportTypeAckAction)data;
				
				if(responseRegisterReportTypeAckAction.isPreConditionsMet()){
					responseRegisterReportTypeAckAction.setEventCompleted(true);
					OadrRegisterReportType oadrRegisterReportType = responseRegisterReportTypeAckAction.getOadrRegisterReportResponse();
					TestSession.addOadrRegisterReportTypeSentList(oadrRegisterReportType);
					data = SchemaHelper.getRegisterReportTypeAsString(oadrRegisterReportType);
					new XMLDBUtil().addEachReportReceived(oadrRegisterReportType,ServiceType.VTN);
					System.out.println("<- OadrRegisterReport Response Returned.");
										
					IPrompt prompt = responseRegisterReportTypeAckAction.getPrompt();
					
					if (prompt != null) {
						prompt.Prompt(prompt.getPromptMessage());
					}
			
					
				}else{
					data=defautResponse;
					String logMsg = "Returning default OadrResponse as precondition validation failed for ResponseRegisterReportTypeAckAction";
					System.out.println(logMsg);
					LogHelper.addTrace(logMsg);
				}
							
			}else if(data instanceof IResponseCreateReportTypeAckAction){
				IResponseCreateReportTypeAckAction responseCreateReportTypeAckAction = (IResponseCreateReportTypeAckAction)data;
				if(responseCreateReportTypeAckAction.isPreConditionsMet()){
					responseCreateReportTypeAckAction.setEventCompleted(true);
					OadrCreateReportType oadrCreateReportType = responseCreateReportTypeAckAction.getOadrCreateReportResponse();
					data = SchemaHelper.getCreateReportTypeAsString(oadrCreateReportType);
					System.out.println("<- OadrCreateReport Response Returned.");
					
					TestSession.addOadrCreateReportTypeSent(oadrCreateReportType);

					IPrompt prompt = responseCreateReportTypeAckAction.getPrompt();
					
					if (prompt != null) {
						prompt.Prompt(prompt.getPromptMessage());
					}
					
				}else{
					data=defautResponse;
					String logMsg = "Returning default OadrResponse as precondition validation failed for ResponseCreateReportTypeAckAction";
					System.out.println(logMsg);
					LogHelper.addTrace(logMsg);
				}
				
			}else if(data instanceof IResponseUpdateReportTypeAckAction){	
				IResponseUpdateReportTypeAckAction responseUpdateReportTypeAckAction = (IResponseUpdateReportTypeAckAction)data;
				if(responseUpdateReportTypeAckAction.isPreConditionsMet()){
					responseUpdateReportTypeAckAction.setEventCompleted(true);
					OadrUpdateReportType  oadrUpdateReportType  = responseUpdateReportTypeAckAction.getOadrUpdateReportType();
					data = SchemaHelper.getUpdateReportTypeAsString(oadrUpdateReportType);
					System.out.println("<- OadrUpdateReport Response Returned.");
					TestSession.addOadrUpdateReportTypeSentList(oadrUpdateReportType);
					IPrompt prompt = responseUpdateReportTypeAckAction.getPrompt();
					
					if (prompt != null) {
						prompt.Prompt(prompt.getPromptMessage());
					}
					
				}else{
					data=defautResponse;
					String logMsg = "Returning default OadrResponse as precondition validation failed for ResponseUpdateReportTypeAckAction";
					System.out.println(logMsg);
					LogHelper.addTrace(logMsg);
				}
				
			}else if(data instanceof IResponseCancelReportTypeAckAction){	
				IResponseCancelReportTypeAckAction responseCancelReportTypeAckAction = (IResponseCancelReportTypeAckAction)data;
				
				if(responseCancelReportTypeAckAction.isPreConditionsMet()){
					responseCancelReportTypeAckAction.setEventCompleted(true);
					OadrCancelReportType  oadrCancelReportType  = responseCancelReportTypeAckAction.getOadrCancelReportResponse();
					data = SchemaHelper.getCancelReportTypeAsString(oadrCancelReportType);
					System.out.println("<- OadrCancelReport Response Returned.");	
					TestSession.addCancelReportTypeSentToList(oadrCancelReportType);
					
					IPrompt prompt = responseCancelReportTypeAckAction.getPrompt();
					
					if (prompt != null) {
						prompt.Prompt(prompt.getPromptMessage());
					}
					
				}else{
					data=defautResponse;
					String logMsg = "Precondition validation failed for ResponseCancelReportTypeAckAction";
					System.out.println(logMsg);
					LogHelper.addTrace(logMsg);
				}
				
			}else if(data instanceof IResponseEventAction){
				
				//ResponseEventActionList.addResponseEventAction((IResponseEventAction)item);
				IResponseEventAction responseEventAction = (IResponseEventAction)data;

				if(responseEventAction.isPreConditionsMet()){
					responseEventAction.setEventCompleted(true);
					OadrResponseType  oadrResponse  = responseEventAction.getResponse();
					data = SchemaHelper.getOadrResponseAsString(oadrResponse);
					if(responseEventAction.islastEvent()){
						System.out.println("<- Last OadrResponse Returned.");
						TestSession.setTestCaseDone(true);
					}else{
						System.out.println("<- OadrResponse Returned.");
						
					}
				}else{
					data=defautResponse;
					String logMsg = "Precondition validation failed for OadrResponse";
					System.out.println(logMsg);
					LogHelper.addTrace(logMsg);
				}
				
			}else{
				System.out.println("Unexpected type added to queue");
				System.exit(-1);
			}
			
		}
		
		
		if (trace != null) {
			trace.getLogFileContentTrace()
					.append("\n----------------RESPONSE---------------------");
			LogHelper.logCurrentTime();
			// OadrUtil.logResponseStatus(response);
			trace.getLogFileContentTrace()
					.append(com.qualitylogic.openadr.core.util.OadrUtil
							.formatXMLAsString((String)data));

			OadrValidationEventHandler.checkForSchemaValidationError((String)data,
					ServiceType.VTN_PULL_MODE,Direction.Send);
			boolean atleastOneValidationErrorPresent = TestSession
					.isAtleastOneValidationErrorPresent();

			if(data!=null && !atleastOneValidationErrorPresent){
				Class type = SchemaHelper.getObjectType((String)data);
				if(type==OadrCancelPartyRegistrationType.class){
					OadrCancelPartyRegistrationType cancelPartyRegistration = CancelPartyRegistrationHelper.createOadrCancelPartyRegistrationTypeFromString((String)data);
					boolean isRegistrationValid = CancelPartyRegistrationConfValHelper.isRegistrationValid(cancelPartyRegistration,Direction.Send);
					if (isRegistrationValid) {
						new XMLDBUtil().resetDatabase();
					}
				}
				
				if(type==OadrCreateReportType.class){
					OadrCreateReportType createReportSent=CreateReportEventHelper.createOadrCreateReportFromString((String)data);
					TestSession.addOadrCreateReportTypeSent(createReportSent);
				}
			
				if(type==OadrUpdateReportType.class){
					OadrUpdateReportType updateReportSent=UpdateReportEventHelper.createReportFromString((String)data);
					TestSession.addOadrUpdateReportTypeSentList(updateReportSent);
				}

			}
		
		}

		/*
		representation = new DomRepresentation(MediaType.APPLICATION_XML);
		
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(false);

		
		DocumentBuilder builder = null;
			builder = factory.newDocumentBuilder();
		

			Document document = builder.parse(new ByteArrayInputStream(((String)data).getBytes()));
			representation.setDocument(document);
			long size = OadrUtil.getSize(document);
			representation.setSize(size);
		*/				

		} catch (NotApplicableException e) {
			System.out.println(e.getMessage());
			System.out.println("N/A");
			LogHelper.addTrace(e.getMessage());
			LogHelper.setResult(LogResult.NA);
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return (String) data;
	}

}
