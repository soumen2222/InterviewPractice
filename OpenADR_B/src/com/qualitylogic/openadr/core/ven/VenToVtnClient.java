package com.qualitylogic.openadr.core.ven;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.bean.VTNServiceType;
import com.qualitylogic.openadr.core.channel.Sender;
import com.qualitylogic.openadr.core.channel.factory.ChannelFactory;
import com.qualitylogic.openadr.core.channel.util.StringUtil;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.exception.ValidationException;
import com.qualitylogic.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCancelReportType;
import com.qualitylogic.openadr.core.signal.OadrCanceledPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCanceledReportType;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatePartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrPollType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdatedReportType;
import com.qualitylogic.openadr.core.signal.helper.CancelPartyRegistrationConfValHelper;
import com.qualitylogic.openadr.core.signal.helper.CancelPartyRegistrationHelper;
import com.qualitylogic.openadr.core.signal.helper.CancelReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CanceledPartyRegistrationHelper;
import com.qualitylogic.openadr.core.signal.helper.CanceledReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CreateOptEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatePartyRegistrationReqSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatedOptEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatedPartyRegistrationSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatedReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.PollEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisterReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisteredReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.ResponseHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.UpdatedReportEventHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.XMLDBUtil;

public class VenToVtnClient {
	static ArrayList<OadrDistributeEventType> OadrDistributeEvntReceived = new ArrayList<OadrDistributeEventType>();
	public synchronized static ArrayList<OadrDistributeEventType> getOadrDistributeEvntReceived() {
		return OadrDistributeEvntReceived;
	}

	public synchronized static void addOadrDistributeEvntReceived(
			OadrDistributeEventType oadrDistributeEvnt) {
		OadrDistributeEvntReceived.add(oadrDistributeEvnt);
	}

	public static String post(String data) throws Exception {
		return post(data,VTNServiceType.EiEvent);
	}
	
	public static String poll(Class expectedType){

		String data = "";
		
		try{
			OadrPollType oadrPollType = PollEventSignalHelper
					.loadOadrPollType();
			Class typeReceived=null;
			String stroadrPollType = SchemaHelper.getOadrPollTypeAsString(oadrPollType);
			
			
			long plusOneMinuteTime = System.currentTimeMillis()+60000;
			
			while(System.currentTimeMillis()<plusOneMinuteTime){
				data = VenToVtnClient
						.post(stroadrPollType,VTNServiceType.OadrPoll);
		
				typeReceived = SchemaHelper.getObjectType(data);
				if(typeReceived==expectedType){
					break;
				}
				
				Thread.sleep(3000);
			}
			
			if(typeReceived!=expectedType){
				data = VenToVtnClient
						.post(stroadrPollType,VTNServiceType.OadrPoll);
				typeReceived = SchemaHelper.getObjectType(data);			
			}
			
			
			if(expectedType==OadrRegisteredReportType.class){
				//ConformanceRuleValidator.validateRequestID(
				
				ArrayList<OadrRegisterReportType> oadrRegisterReportSentList= TestSession.getOadrRegisterReportTypeSentList();
				if(oadrRegisterReportSentList.size()<1){
					TestSession.setValidationErrorFound(true);
					LogHelper.addTraceAndConsole("No OadrRegisterReport was sent but OadrRegisteredReport has been received");
				}else{
					OadrRegisterReportType oadrRegisterReportSent = oadrRegisterReportSentList.get(oadrRegisterReportSentList.size()-1);
					String strOadrRegisterReportSent = SchemaHelper.getRegisterReportTypeAsString(oadrRegisterReportSent);
					ConformanceRuleValidator.validateRequestID(strOadrRegisterReportSent,data);
				}
				
				if(typeReceived==OadrRegisteredReportType.class){
					TestSession.addOadrRegisteredReportTypeReceivedToList(RegisteredReportEventHelper.createOadrRegisteredReportFromString(data));
				}
			}
			
		}catch(Exception ex){
			
		}

		return data;
	}
	public static String post(String data,VTNServiceType vtnservice) throws Exception {
		if (StringUtil.isBlank(data)) {
			System.out.println("Warning, passed empty data to post.");
			return null;
		}
		
		if (TestSession.isValidationErrorFound()) {
			return "";
		}
		
		// We can either create a global connection or connect on every post.
		// We are connecting on every post for this implementation to minimize code changes.
		Sender sender = ChannelFactory.getSender();
		String response = null;
		
		String reqObjectType = SchemaHelper.getEventTypeName(data);

		if (reqObjectType!=null && reqObjectType.equals("OadrRegisterReportType")) {
			OadrRegisterReportType oadrRegisterReportType = RegisterReportEventHelper.createOadrRegisterReportFromString(data);
			TestSession.addOadrRegisterReportTypeSentList(oadrRegisterReportType);
		}else if(reqObjectType!=null && reqObjectType.equals("OadrCreateOptType")){
			OadrCreateOptType oadrCreateOptType = CreateOptEventHelper.createCreateOptTypeEventFromString(data);
			TestSession.addCreateOptEventSentList(oadrCreateOptType);
		}else if(reqObjectType!=null && reqObjectType.equals("OadrRegisteredReportType")){
			OadrRegisteredReportType oadrRegisteredReportType = RegisteredReportEventHelper.createOadrRegisteredReportFromString(data);
			TestSession.addOadrRegisteredReportTypeSentToList(oadrRegisteredReportType);
		}else if(reqObjectType!=null && reqObjectType.equals("OadrCancelReportType")){
			OadrCancelReportType oadrCancelReportType = CancelReportEventHelper.createOadrCancelReportTypeFromString(data);
			TestSession.addCancelReportTypeSentToList(oadrCancelReportType);	
		}else if(reqObjectType!=null && reqObjectType.equals("OadrCancelPartyRegistrationType")){
			OadrCancelPartyRegistrationType oadrCancelPartyRegistrationType = CancelPartyRegistrationHelper.createOadrCancelPartyRegistrationTypeFromString(data);
			TestSession.addCancelPartyRegistrationSentToList(oadrCancelPartyRegistrationType);	
		}
		
		try {
			String to = vtnservice.toString();
			if (to.equals("EiRegistration")) {
				to = "EiRegisterParty";
			}
			response = sender.send(ServiceType.VEN, to, data);
			
		
			if (reqObjectType!=null && reqObjectType.equals("OadrRegisterReportType")) {
				OadrRegisterReportType oadrRegisterReportType = RegisterReportEventHelper.createOadrRegisterReportFromString(data);
				new XMLDBUtil().addEachReportReceived(oadrRegisterReportType,ServiceType.VEN);
			}else if(reqObjectType!=null && reqObjectType.equals("OadrCreatePartyRegistrationType")){
				OadrCreatePartyRegistrationType oadrCreatePartyRegistrationType = CreatePartyRegistrationReqSignalHelper.createCreatePartyRegistrationRequestFromString(data);
				String transportAddress = oadrCreatePartyRegistrationType.getOadrTransportAddress();
				new XMLDBUtil().setTransportAddress(transportAddress);
			}
			
			
						
			
			
			
			
			
			// response
			Class respObjectType = null;
			if (!StringUtil.isBlank(response)) {
				respObjectType = SchemaHelper.getObjectType(response);	
			}
			
			if (respObjectType != null && respObjectType.equals(OadrCreatedPartyRegistrationType.class)) {
				OadrCreatedPartyRegistrationType oadrCreatedPartyRegistrationType = CreatedPartyRegistrationSignalHelper.createCreatedPartyRegistrationTypeFromString(response);
				String venID = oadrCreatedPartyRegistrationType.getVenID();
				String registrationID = oadrCreatedPartyRegistrationType.getRegistrationID();
				
				if(registrationID!=null && registrationID.trim().length()>0){
						if (!new PropertiesFileReader().isUseStaticVENID()) {
							new XMLDBUtil().setVENID(venID);
						}
						new XMLDBUtil().setRegistrationID(registrationID);
				}
				
			}else if (respObjectType != null && respObjectType.equals(OadrCancelPartyRegistrationType.class)) {
				OadrCancelPartyRegistrationType cancelPartyRegistration = CancelPartyRegistrationHelper.createOadrCancelPartyRegistrationTypeFromString(response);
				boolean isRegistrationValid = CancelPartyRegistrationConfValHelper.isRegistrationValid(cancelPartyRegistration,Direction.Send);
				if (isRegistrationValid) {
					new XMLDBUtil().resetDatabase();
				}
			}else if (respObjectType != null && respObjectType.equals(OadrCanceledPartyRegistrationType.class)) {
				OadrCanceledPartyRegistrationType canceledPartyRegistration = CanceledPartyRegistrationHelper.createOadrCanceledPartyRegistrationTypeFromString(response);
				if (canceledPartyRegistration.getEiResponse().getResponseCode().equals("200")) {
					new XMLDBUtil().resetDatabase();
				}
				
				TestSession.addCanceledPartyRegistrationReceivedToList(canceledPartyRegistration);
				
			}else if(respObjectType != null && respObjectType.equals(OadrRegisterReportType.class)) {
				OadrRegisterReportType oadrRegisterReportType = RegisterReportEventHelper.createOadrRegisterReportFromString(response);
				new XMLDBUtil().addEachReportReceived(oadrRegisterReportType, ServiceType.VTN);
				
				TestSession.addOadrRegisterReportTypeReceivedToList(oadrRegisterReportType);
			}else if (respObjectType!=null && respObjectType.equals(OadrDistributeEventType.class)) {
				OadrDistributeEventType oadrDistributeEventReceived = DistributeEventSignalHelper
					.createOadrDistributeEventFromString(response);
				addOadrDistributeEvntReceived(oadrDistributeEventReceived);
			}else if(respObjectType!=null && respObjectType.equals(OadrUpdateReportType.class)){
				OadrUpdateReportType updateReportReceived=UpdateReportEventHelper.createReportFromString((String)response);
				TestSession.addUpdateReportTypeReceivedList(updateReportReceived);
			} else if (respObjectType != null && respObjectType.equals(OadrCreatedOptType.class)) {
				OadrCreatedOptType createdOpt = CreatedOptEventHelper.createCreatedOptTypeEventFromString(response);
				TestSession.getCreatedOptReceivedList().add(createdOpt);
			} else if (respObjectType != null && respObjectType.equals(OadrCreateReportType.class)) {
				OadrCreateReportType oadrCreateReportType  = CreateReportEventHelper.createOadrCreateReportFromString(response);
				TestSession.getOadrCreateReportTypeReceivedList().add(oadrCreateReportType);
			} else if (respObjectType != null && respObjectType.equals(OadrResponseType.class)) {
				OadrResponseType oadrResponseType = ResponseHelper.createOadrResponseFromString(response);
				//TestSession.addOadrResponseList(oadrResponseType);
			} else if (respObjectType != null && respObjectType.equals(OadrCanceledReportType.class)) {
				OadrCanceledReportType oadrCanceledReportType  = CanceledReportEventHelper.loadCanceledReportTypeFromString(response);
				TestSession.getOadrCanceledReportTypeReceivedList().add(oadrCanceledReportType);
			}else if(respObjectType!=null && respObjectType.equals(OadrUpdatedReportType.class)){
				OadrUpdatedReportType updatedReportReceived=UpdatedReportEventHelper.loadReportFromString((String)response);
				TestSession.addOadrUpdatedReportTypeReceivedToList(updatedReportReceived);
			}else if(respObjectType!=null && respObjectType.equals(OadrCreatedReportType.class)){
				OadrCreatedReportType oadrCreatedReportType=CreatedReportEventHelper.createOadrCreatedReportFromString((String)response);
				TestSession.addOadrCreatedReportTypeReceivedToList(oadrCreatedReportType);
			}else if(respObjectType!=null && respObjectType.equals(OadrRegisteredReportType.class)){
				OadrRegisteredReportType oadrRegisteredReportType=RegisteredReportEventHelper.createOadrRegisteredReportFromString((String)response);
				TestSession
				.addOadrRegisteredReportTypeReceivedToList(oadrRegisteredReportType);
			} 
			
			
			

			
			
			ConformanceRuleValidator.validateRequestID(data,response);
						
			
						if (TestSession.isAtleastOneValidationErrorPresent()) {
				throw new ValidationException();
			}
		} finally {
			sender.stop();
		}
		
		return response;
	}
}