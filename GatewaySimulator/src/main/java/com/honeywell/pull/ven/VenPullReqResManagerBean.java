package com.honeywell.pull.ven;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.xml.datatype.DatatypeConfigurationException;

import org.jboss.logging.Logger;

import com.honeywell.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.honeywell.openadr.core.signal.OadrCancelReportType;
import com.honeywell.openadr.core.signal.OadrCreatePartyRegistrationType;
import com.honeywell.openadr.core.signal.OadrCreateReportType;
import com.honeywell.openadr.core.signal.OadrCreatedEventType;
import com.honeywell.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.honeywell.openadr.core.signal.OadrCreatedReportType;
import com.honeywell.openadr.core.signal.OadrDistributeEventType;
import com.honeywell.openadr.core.signal.OadrPayload;
import com.honeywell.openadr.core.signal.OadrPollType;
import com.honeywell.openadr.core.signal.OadrRegisterReportType;
import com.honeywell.openadr.core.signal.OadrRegisteredReportType;
import com.honeywell.openadr.core.signal.OadrRequestEventType;
import com.honeywell.openadr.core.signal.OadrRequestReregistrationType;
import com.honeywell.openadr.core.signal.OadrResponseType;
import com.honeywell.openadr.core.signal.OadrUpdateReportType;
import com.honeywell.payloads.PayloadGenerator;
import com.honeywell.payloads.PojoGenerator;
import com.honeywell.payloads.util.PayloadUtil;
import com.honeywell.push.client.HttpPushClient;
import com.honeywell.push.client.HttpPushException;
import com.honeywell.ven.api.common.PushProfile;
import com.honeywell.ven.api.common.Response;
import com.honeywell.ven.api.event.CreatedEventRequest;
import com.honeywell.ven.api.fault.VenException;
import com.honeywell.ven.api.poll.Poll;
import com.honeywell.ven.api.poll.PollRequest;
import com.honeywell.ven.api.registration.CancelPartyRegistration;
import com.honeywell.ven.api.registration.CanceledPartyRegistration;
import com.honeywell.ven.api.registration.CreatePartyRegistration;
import com.honeywell.ven.api.registration.CreatePartyRegistrationRequest;
import com.honeywell.ven.api.registration.CreatedPartyRegistration;
import com.honeywell.ven.api.registration.ProfileName;
import com.honeywell.ven.api.registration.TransportName;
import com.honeywell.ven.api.report.CreatedReportRequest;
import com.honeywell.ven.api.report.RegisterReportRequest;
import com.honeywell.ven.api.report.RegisteredReport;
import com.honeywell.ven.api.report.RegisteredReportRequest;
import com.honeywell.ven.api.report.UpdateReportRequest;
import com.honeywell.ven.api.report.UpdatedReport;




@Stateless
public class VenPullReqResManagerBean implements VenPullReqResManager.L , VenPullReqResManager.R{
	
	/*
	@javax.annotation.Resource
	TimerService ts;*/

	private Logger log = Logger.getLogger(VenPullReqResManagerBean.class);
	private HttpPushClient httpPushClient =null;
	private final static String OadrPoll ="/OpenADR2/Simple/2.0b/OadrPoll";
	private final static String EiEvent ="/OpenADR2/Simple/2.0b/EiEvent";
	private final static String EiReport ="/OpenADR2/Simple/2.0b/EiReport";
	private final static String EiOpt ="/OpenADR2/Simple/2.0b/EiOpt";
	private final static String EiRegisterParty ="/OpenADR2/Simple/2.0b/EiRegisterParty";
		
	public RegisteredReport sendregisterReport(RegisterReportRequest registerReportRequest)
			throws VenException {
		if(null != registerReportRequest){
			OadrRegisterReportType oadrRegisterReportType =null;
			PayloadGenerator pg = new PayloadGenerator();
			try {
				oadrRegisterReportType = pg.getOadrRegisterReportType(registerReportRequest.getRegisterReport());
			} catch (DatatypeConfigurationException e) {
				log.error("Exception in registerReport - Push !!! "+e);
				throw new VenException("Exception in registerReport - Push !!! ",e);
			}
			OadrPayload oadrPayload = pg.getOadrPayload(oadrRegisterReportType);
			try {
				if(null != getHttpPushClient()){
					Object pushPayload =null;
					PushProfile pushProfile = resolvePushUrl(registerReportRequest);
					pushPayload = resolvePush(pushProfile, oadrPayload);
					PojoGenerator pojoGenerator = new PojoGenerator();
					OadrPayload oadrResponsePayload = (OadrPayload)pushPayload;
					return pojoGenerator.getRegisteredReport((OadrRegisteredReportType)oadrResponsePayload.getOadrSignedObject().getOadrRegisteredReport());
				}else{
					log.error("httpPushClient is null !!! ");
				}
			} catch (HttpPushException e) {
				log.error("HttpPushException in pushing the OadrRegisterReportType payload !!! "+e);
				throw new VenException("HttpPushException in pushing the OadrRegisterReportType payload !!! ",e);
			}
		}
		return null;
	}
	
	
	public Response sendregisteredReport(RegisteredReportRequest registeredReportRequest)
			throws VenException {
		if(null != registeredReportRequest){
			OadrRegisteredReportType oadrRegisteredReportType =null;
			PayloadGenerator pg = new PayloadGenerator();
			try {
				oadrRegisteredReportType = pg.getOadrRegisteredReportType(registeredReportRequest.getRegisteredReport());
			} catch (DatatypeConfigurationException e) {
				log.error("Exception in registeredReport - Push !!! "+e);
				throw new VenException("Exception in registeredReport - Push !!! ",e);
			}
			OadrPayload oadrPayload = pg.getOadrPayload(oadrRegisteredReportType);
			try {
				if(null != getHttpPushClient()){
					Object pushPayload =null;
					PushProfile pushProfile = resolvePushUrl(registeredReportRequest);
					pushPayload = resolvePush(pushProfile, oadrPayload);
					PojoGenerator pojoGenerator = new PojoGenerator();
					OadrPayload oadrResponsePayload = (OadrPayload)pushPayload;
					return pojoGenerator.getResponse((OadrResponseType)oadrResponsePayload.getOadrSignedObject().getOadrResponse());
				}else{
					log.error("httpPushClient is null !!! ");
				}
			} catch (HttpPushException e) {
				log.error("HttpPushException in pushing the OadrRegisteredReportType payload !!! "+e);
				throw new VenException("HttpPushException in pushing the OadrRegisteredReportType payload !!! ",e);
			}
		}
		return null;
	}
	
	
	public Response sendcreatedReport(CreatedReportRequest createdReportRequest)
			throws VenException {
		if(null != createdReportRequest){
			OadrCreatedReportType oadrCreatedReportType =null;
			PayloadGenerator pg = new PayloadGenerator();
			try {
				oadrCreatedReportType = pg.getOadrCreatedReportType(createdReportRequest.getCreatedReport());
			} catch (DatatypeConfigurationException e) {
				log.error("Exception in registeredReport - Push !!! "+e);
				throw new VenException("Exception in registeredReport - Push !!! ",e);
			}
			OadrPayload oadrPayload = pg.getOadrPayload(oadrCreatedReportType);
			try {
				if(null != getHttpPushClient()){
					Object pushPayload =null;
					PushProfile pushProfile = resolvePushUrl(createdReportRequest);
					pushPayload = resolvePush(pushProfile, oadrPayload);
					PojoGenerator pojoGenerator = new PojoGenerator();
					OadrPayload oadrResponsePayload = (OadrPayload)pushPayload;
					return pojoGenerator.getResponse((OadrResponseType)oadrResponsePayload.getOadrSignedObject().getOadrResponse());
				}else{
					log.error("httpPushClient is null !!! ");
				}
			} catch (HttpPushException e) {
				log.error("HttpPushException in pushing the OadrCreatedReportType payload !!! "+e);
				throw new VenException("HttpPushException in pushing the OadrCreatedReportType payload !!! ",e);
			}
		}
		return null;
	}
	
	@Override
	public Response sendcreatedEvent(CreatedEventRequest createdEventRequest)
			throws VenException {
		if(null != createdEventRequest){
			OadrCreatedEventType oadrCreatedEventType =null;
			PayloadGenerator pg = new PayloadGenerator();
			try {
				oadrCreatedEventType = pg.getOadrCreatedEventType(createdEventRequest.getCreatedEvent());
			} catch (DatatypeConfigurationException e) {
				log.error("Exception in registerReport - Push !!! "+e);
				throw new VenException("Exception in registerReport - Push !!! ",e);
			}
			OadrPayload oadrPayload = pg.getOadrPayload(oadrCreatedEventType);
			try {
				if(null != getHttpPushClient()){
					Object pushPayload =null;
					PushProfile pushProfile = resolvePushUrl(createdEventRequest);
					pushPayload = resolvePush(pushProfile, oadrPayload);
					PojoGenerator pojoGenerator = new PojoGenerator();
					OadrPayload oadrResponsePayload = (OadrPayload)pushPayload;
					return pojoGenerator.getResponse((OadrResponseType)oadrResponsePayload.getOadrSignedObject().getOadrResponse());
				}else{
					log.error("httpPushClient is null !!! ");
				}
			} catch (HttpPushException e) {
				log.error("HttpPushException in pushing the OadrRegisterReportType payload !!! "+e);
				throw new VenException("HttpPushException in pushing the OadrRegisterReportType payload !!! ",e);
			}
		}
		return null;
	}
	
	
	
	/*public boolean sendpoll(PollRequest pollRequest , Class<?> expected) throws VenException{
		
		
		if(null != pollRequest){
			OadrPollType oadrPollType = null;
			PayloadGenerator pg = new PayloadGenerator();			
			try {
				oadrPollType = pg.getoadrPollType(pollRequest.getPoll());
			} catch (DatatypeConfigurationException e) {
				log.error("Exception in Sending Poll !!! "+e);
				throw new VenException("Exception in Sending Poll !!! ",e);
			}
			OadrPayload oadrPayload = pg.getOadrPayload(oadrPollType);
			try {
				if(null != getHttpPushClient()){
					Object response =null;
					PushProfile pushProfile = resolvePushUrl(pollRequest);
					response = resolvePush(pushProfile, oadrPayload);
		    	if (!PayloadUtil.isExpected(response, expected))
		    	{
				throw new VenException("Expected " + expected + " has not been received");
			    }
		    	else{
		    		return true;
		    	}
				}
		    	else{
					log.error("httpPushClient is null !!! ");
				}
			} catch (HttpPushException e) {
				log.error("HttpPushException in pushing the OadrPollType payload !!! "+e);
				throw new VenException("HttpPushException in pushing the OadrPollType payload !!! ",e);
		}
	}
		return false;
}
	*/

	public Object sendpoll(PollRequest pollRequest) throws VenException{
				
		if(null != pollRequest){
			OadrPollType oadrPollType = null;
			PayloadGenerator pg = new PayloadGenerator();			
			try {
				oadrPollType = pg.getoadrPollType(pollRequest.getPoll());
			} catch (DatatypeConfigurationException e) {
				log.error("Exception in Sending Poll !!! "+e);
				throw new VenException("Exception in Sending Poll !!! ",e);
			}
			OadrPayload oadrPayload = pg.getOadrPayload(oadrPollType);
			try {
				if(null != getHttpPushClient()){
					Object pushPayloadresponse =null;
					PushProfile pushProfile = resolvePushUrl(pollRequest);
					pushPayloadresponse = resolvePush(pushProfile, oadrPayload);
					OadrPayload oadrResponsePayload = (OadrPayload)pushPayloadresponse;
										
					return oadrResponsePayload;
					
				}else{
					log.error("httpPushClient is null !!! ");
				}
			} catch (HttpPushException e) {
				log.error("HttpPushException in pushing the OadrRegisterReportType payload !!! "+e);
				throw new VenException("HttpPushException in pushing the OadrRegisterReportType payload !!! ",e);
			}
		}
		return null;
			
	}
	
	
	public CreatedPartyRegistration sendCreatePartyRegistration (CreatePartyRegistrationRequest createPartyRegistrationRequest)
			throws VenException {
		if(null != createPartyRegistrationRequest){
			OadrCreatePartyRegistrationType oadrCreatePartyRegistrationType =null;
			PayloadGenerator pg = new PayloadGenerator();
			try {
				oadrCreatePartyRegistrationType = pg.getOadrCreatePartyRegistrationType(createPartyRegistrationRequest.getCreatePartyRegistration());				
			} catch (DatatypeConfigurationException e) {
				log.error("Exception in Create Party - Push !!! "+e);
				throw new VenException("Exception in Create Party - Push !!! ",e);
			}
			OadrPayload oadrPayload = pg.getOadrPayload(oadrCreatePartyRegistrationType);
			try {
				if(null != getHttpPushClient()){
					Object pushPayload =null;
					PushProfile pushProfile = resolvePushUrl(createPartyRegistrationRequest);
					pushPayload = resolvePush(pushProfile, oadrPayload);
					PojoGenerator pojoGenerator = new PojoGenerator();
					OadrPayload oadrResponsePayload = (OadrPayload)pushPayload;
					return pojoGenerator.getCreatedPartyRegistration((OadrCreatedPartyRegistrationType)oadrResponsePayload.getOadrSignedObject().getOadrCreatedPartyRegistration());
					
				}else{
					log.error("httpPushClient is null !!! ");
				}
			} catch (HttpPushException e) {
				log.error("HttpPushException in pushing the OadrRegisterReportType payload !!! "+e);
				throw new VenException("HttpPushException in pushing the OadrRegisterReportType payload !!! ",e);
			}
		}
		return null;
	}
	
	
	public UpdatedReport updateReport(UpdateReportRequest updateReportRequest)
			throws VenException {
		if(null != updateReportRequest){
			OadrUpdateReportType oadrUpdateReportType = null;
			PayloadGenerator pg = new PayloadGenerator();
			try {
				oadrUpdateReportType = pg.getOadrUpdateReportType(updateReportRequest.getUpdateReport());
			} catch (DatatypeConfigurationException e) {
				log.error("Exception in updateReport - Push !!! "+e);
				throw new VenException("Exception in updateReport - Push !!! ",e);
			}
			OadrPayload oadrPayload = pg.getOadrPayload(oadrUpdateReportType);
			try {
				if(null != getHttpPushClient()){
					Object pushPayload =null;
					PushProfile pushProfile = resolvePushUrl(updateReportRequest);
					pushPayload = resolvePush(pushProfile, oadrPayload);
					PojoGenerator pojoGenerator = new PojoGenerator();
					OadrPayload oadrResponsePayload = (OadrPayload)pushPayload;
					return pojoGenerator.getUpdatedReport(oadrResponsePayload.getOadrSignedObject().getOadrUpdatedReport());
				}else{
					log.error("httpPushClient is null !!! ");
				}
			} catch (HttpPushException e) {
				log.error("HttpPushException in pushing the OadrUpdateReportType payload !!! "+e);
				throw new VenException("HttpPushException in pushing the OadrUpdateReportType payload !!! ",e);
			}
		}
                return null;
	}
	
	
	private Object resolvePush(PushProfile pushProfile, OadrPayload oadrPayload) throws HttpPushException{
		if(null != pushProfile){

			if(pushProfile.getProfileName().equals(ProfileName.PROFILE2B) 
					&& pushProfile.getTransport().equals(TransportName.simpleHttp)){
				log.info("Pushing to ..... "+ pushProfile.getPushUrl());
				return httpPushClient.pushPayload(pushProfile.getPushUrl(), oadrPayload);
			
		}
		
	}
		return null;
	
}
	
	private PushProfile resolvePushUrl(Object o){
		if(o instanceof RegisterReportRequest){
			PushProfile pushProfile = ((RegisterReportRequest) o).getPushProfile();
			String baseUrl = pushProfile.getPushUrl();
			String pushUrl = baseUrl + EiReport;
			pushProfile.setPushUrl(pushUrl);
			return pushProfile;
		}else
		if(o instanceof RegisteredReportRequest){
			PushProfile pushProfile = ((RegisteredReportRequest) o).getPushProfile();
			String baseUrl = pushProfile.getPushUrl();
			String pushUrl = baseUrl + EiReport;
			pushProfile.setPushUrl(pushUrl);
			return pushProfile;
		}else		
		if(o instanceof CreatePartyRegistrationRequest){
			PushProfile pushProfile = ((CreatePartyRegistrationRequest) o).getPushProfile();
			String baseUrl = pushProfile.getPushUrl();
			String pushUrl = baseUrl + EiRegisterParty;
			pushProfile.setPushUrl(pushUrl);
			return pushProfile;
		}else			
		if(o instanceof PollRequest){
			PushProfile pushProfile = ((PollRequest) o).getPushProfile();
			String baseUrl = pushProfile.getPushUrl();
			String pushUrl = baseUrl + OadrPoll;
			pushProfile.setPushUrl(pushUrl);
			return pushProfile;
		}else
			if(o instanceof UpdateReportRequest){
				PushProfile pushProfile = ((UpdateReportRequest) o).getPushProfile();
				String baseUrl = pushProfile.getPushUrl();
				String pushUrl = baseUrl + EiReport;
				pushProfile.setPushUrl(pushUrl);
				return pushProfile;
		}else
			if(o instanceof CreatedReportRequest){
				PushProfile pushProfile = ((CreatedReportRequest) o).getPushProfile();
				String baseUrl = pushProfile.getPushUrl();
				String pushUrl = baseUrl + EiReport;
				pushProfile.setPushUrl(pushUrl);
				return pushProfile;
		}else
			if(o instanceof CreatedEventRequest){
				PushProfile pushProfile = ((CreatedEventRequest) o).getPushProfile();
				String baseUrl = pushProfile.getPushUrl();						
				pushProfile.setPushUrl(baseUrl);
				return pushProfile;}
		
			
		return null;
		
		
	}
	
	private HttpPushClient getHttpPushClient(){
		if(null == httpPushClient){
			try {
				httpPushClient = new HttpPushClient();
			} catch (HttpPushException e) {
				log.error("HttpPushException in creating HttpPushClient !!! "+e);
			}
		}
		return httpPushClient;
	}
}
