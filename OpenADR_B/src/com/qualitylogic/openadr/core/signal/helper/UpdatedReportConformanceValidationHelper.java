package com.qualitylogic.openadr.core.signal.helper;
import java.util.ArrayList;
import java.util.List;

import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.EiResponseType;
import com.qualitylogic.openadr.core.signal.OadrCancelReportType;
import com.qualitylogic.openadr.core.signal.OadrCanceledReportType;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedOptType;
import com.qualitylogic.openadr.core.signal.OadrReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdatedReportType;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class UpdatedReportConformanceValidationHelper {

	public static boolean isResponseCodeSuccess(
			OadrUpdatedReportType oadrUpdatedReportType) {
		return OadrUtil.isSuccessResponse(oadrUpdatedReportType.getEiResponse());
	}
	
	public static boolean isCancelReportRequestIDMatch(
			OadrUpdatedReportType oadrUpdatedReportType, Direction direction){
		

		ArrayList<OadrUpdateReportType>  updateReportList = null;

		if(direction.equals(Direction.Receive)){
			updateReportList = TestSession.getOadrUpdateReportTypeSentList();
		}else{
			updateReportList = TestSession.getOadrUpdateReportTypeReceivedList();

		}

		if(updateReportList.size()<1){
			LogHelper.addTrace("Conformance check failed : Unable to find any OadrUpdateReport to match with OadrUpdatedReport");

			return false;
		}
		
		 OadrUpdateReportType oadrUpdateReport = updateReportList.get(updateReportList.size()-1);
		 List<OadrReportType> oadrReportList = oadrUpdateReport.getOadrReport();
		 
		 ArrayList<String> updateReportReqIDList=new ArrayList<String>();
		 
		 for(OadrReportType eachOadrReportType:oadrReportList){
			 updateReportReqIDList.add(eachOadrReportType.getReportRequestID());
		 }

		 
		OadrCancelReportType oadrCancelReportType  = oadrUpdatedReportType.getOadrCancelReport();
		if(oadrCancelReportType!=null && oadrCancelReportType.getReportRequestID()!=null && oadrCancelReportType.getReportRequestID().size()>0){
			List<String> reqIDList = oadrCancelReportType.getReportRequestID();
			
			for(String eachCancelReportReqID:reqIDList){
				
				if(OadrUtil.isIDFoundInList(eachCancelReportReqID, updateReportReqIDList)){
					return true;
				}
				
			}
						
			LogHelper.addTrace("Conformance check failed : Unable to map RequestID between OadrCancelReport and OadrUpdateReport");

			return false;
		}
		
		return true;
	}
	
	public static boolean isVenIDValid(OadrUpdatedReportType oadrUpdatedReportType, Direction direction) {
		
		if(!OadrUtil.isVENIDValid(oadrUpdatedReportType.getVenID(),direction)){
			return false;
		}

		return true;
	}
	
	
	public static boolean isRequestIDsMatch(OadrUpdatedReportType oadrUpdatedReportType,Direction direction){
		
		if (!ConformanceRuleValidator.getDisableRequestIDValid_Check()) {
			ArrayList<OadrUpdateReportType>  oadrUpdateReportTypeList= null;
			if(direction.equals(Direction.Receive)){
				oadrUpdateReportTypeList= TestSession.getOadrUpdateReportTypeSentList();
			}else{
				oadrUpdateReportTypeList= TestSession.getOadrUpdateReportTypeReceivedList();			
			}
			
			if(oadrUpdateReportTypeList==null||oadrUpdateReportTypeList.size()<1){
				LogHelper.addTrace("Conformance Validation Error : Unable to find any OadrUpdateReport to map with OadrUpdatedReport");
				return false;
			}
			
			OadrUpdateReportType oadrUpdateReportType = oadrUpdateReportTypeList.get(oadrUpdateReportTypeList.size()-1);
			if(!validateRequestIDsMatch(oadrUpdateReportType,oadrUpdatedReportType)){
				return false;
			}
			
		}else{
			ConformanceRuleValidator.skippingConformanceRut("disableRequestIDValid_Check","Check to see if RequestID in OadrRegisterReport and OadrRegisteredReport");
		}
		
		return true;
	}

	public static boolean validateRequestIDsMatch(OadrUpdateReportType oadrUpdateReportType,OadrUpdatedReportType oadrUpdatedReportType){
		
		if(TestSession.isValidationErrorFound()) return true;
	
			if (!ConformanceRuleValidator.getDisableRequestIDValid_Check()) {

				String requestIDReceived = oadrUpdateReportType.getRequestID();
				EiResponseType  eiResponseType  = oadrUpdatedReportType.getEiResponse();
				
				if (!OadrUtil.isRequestIDsMatch(requestIDReceived,eiResponseType)) {
					ConformanceRuleValidator.logFailure("disableRequestIDValid_Check","The RequestID in OadrUpdateReport and OadrUpdatedReport did not match");
					return false;
				}
			} else {
				ConformanceRuleValidator.skippingConformanceRut("disableRequestIDValid_Check","Check to see if RequestID in OadrUpdateReport and OadrUpdatedReport match");
			}
			
			return true;
	}

	
}