package com.qualitylogic.openadr.core.vtn;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.bean.VENServiceType;
import com.qualitylogic.openadr.core.channel.Sender;
import com.qualitylogic.openadr.core.channel.factory.ChannelFactory;
import com.qualitylogic.openadr.core.channel.util.StringUtil;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCancelReportType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCanceledPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.helper.CancelReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CanceledPartyRegistrationHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisterReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisteredReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.ResponseHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.XMLDBUtil;

public class VtnToVenClient {

	static ArrayList<OadrDistributeEventType> oadrDistributeEvntSent = new ArrayList<OadrDistributeEventType>();

	public synchronized static ArrayList<OadrDistributeEventType> getOadrDistributeEvntSent() {
		return oadrDistributeEvntSent;
	}

	public synchronized static void addOadrDistributeEvntSent(
			OadrDistributeEventType oadrDistributeEvnt) {
		oadrDistributeEvntSent.add(oadrDistributeEvnt);
	}
	
	public static String post(String data) throws Exception {
		return post(data,VENServiceType.EiEvent);
	}

	public static String post(String data,VENServiceType venservice) throws Exception {
		if (StringUtil.isBlank(data)) {
			System.out.println("Warning, passed empty data to post.");
			return null;
		}
		
		if(TestSession.isValidationErrorFound()){
			return "";
		}
	
		String reqObjectType = SchemaHelper.getEventTypeName(data);
		if (reqObjectType != null && reqObjectType.equals("OadrRegisterReportType")) {
			OadrRegisterReportType oadrRegisterReportType = RegisterReportEventHelper.createOadrRegisterReportFromString(data);
			new XMLDBUtil().addEachReportReceived(oadrRegisterReportType, ServiceType.VTN);
			TestSession.addOadrRegisterReportTypeSentList(oadrRegisterReportType);
		}else if(reqObjectType!=null && reqObjectType.equals("OadrCreateReportType")){
			OadrCreateReportType createReportSent=CreateReportEventHelper.createOadrCreateReportFromString((String)data);
			TestSession.addOadrCreateReportTypeSent(createReportSent);
		}else if(reqObjectType!=null && reqObjectType.equals("OadrUpdateReportType")){
			OadrUpdateReportType updateReportSent=UpdateReportEventHelper.createReportFromString((String)data);
			TestSession.addOadrUpdateReportTypeSentList(updateReportSent);
		}else if(reqObjectType!=null && reqObjectType.equals("OadrRegisteredReportType")){
			OadrRegisteredReportType oadrRegisteredReportType = RegisteredReportEventHelper.createOadrRegisteredReportFromString(data);
			TestSession.addOadrRegisteredReportTypeSentToList(oadrRegisteredReportType);
		}else if(reqObjectType!=null && reqObjectType.equals("OadrCancelReportType")){
			OadrCancelReportType oadrCancelReportType = CancelReportEventHelper.createOadrCancelReportTypeFromString(data);
			TestSession.addCancelReportTypeSentToList(oadrCancelReportType);	
		}
		
		// We can either create a global connection or connect on every post.
		// We are connecting on every post for this implementation to minimize code changes.
		Sender sender = ChannelFactory.getSender();
		String response = null;
		try {
			String to = venservice.toString();
			if (to.equals("EiRegistration")) {
				to = "EiRegisterParty";
			}
			response = sender.send(ServiceType.VTN, to, data);
			
			String resObjectType = SchemaHelper.getEventTypeName(response);
			if (resObjectType!=null && resObjectType.equals("OadrRegisterReportType")) {
				OadrRegisterReportType oadrRegisterReportType = RegisterReportEventHelper.createOadrRegisterReportFromString(response);
				XMLDBUtil xmlDBUtil = new XMLDBUtil();
				xmlDBUtil.addEachReportReceived(oadrRegisterReportType,ServiceType.VEN);
			}else if (resObjectType != null && resObjectType.equals("OadrCanceledPartyRegistrationType")) {
				OadrCanceledPartyRegistrationType canceledPartyRegistration = CanceledPartyRegistrationHelper.createOadrCanceledPartyRegistrationTypeFromString(response);
				if (canceledPartyRegistration.getEiResponse().getResponseCode().equals("200")) {
					new XMLDBUtil().resetDatabase();
				}
			} else if (resObjectType!=null && resObjectType.equals("OadrResponseType")) {
				OadrResponseType oadrResponseObj = ResponseHelper.createOadrResponseFromString(response);
				TestSession.addOadrResponseList(oadrResponseObj);
			}else if(resObjectType!=null && resObjectType.equals("OadrRegisteredReportType")){
				OadrRegisteredReportType oadrRegisteredReportType = RegisteredReportEventHelper.createOadrRegisteredReportFromString(response);
				TestSession.addOadrRegisteredReportTypeReceivedToList(oadrRegisteredReportType);
			}
			
			ConformanceRuleValidator.validateRequestID(data,response);

		} finally {
			sender.stop();
		}
		return response;
	}
}
