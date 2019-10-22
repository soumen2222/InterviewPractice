package com.qualitylogic.openadr.core.signal.helper;

import java.util.ArrayList;
import java.util.List;

import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.EiResponseType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.signal.OadrReportRequestType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdatedReportType;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class CreatedReportConformanceValidationHelper {

	public static boolean isResponseCodeSuccess(
			OadrCreatedReportType oadrCreatedReportType) {
		return OadrUtil.isSuccessResponse(oadrCreatedReportType.getEiResponse());
	}

	public static boolean isVenIDValid(OadrCreatedReportType oadrCreatedReportType, Direction direction) {
		
		if(!OadrUtil.isVENIDValid(oadrCreatedReportType.getVenID(),direction)){
			return false;
		}

		return true;
	}
	
	public static boolean isRequestIDsMatch(OadrCreatedReportType oadrCreatedReport,Direction direction){
		
		if (!ConformanceRuleValidator.getDisableRequestIDValid_Check()) {
			ArrayList<OadrCreateReportType>  oadrCreateReportTypeList= null;
			ArrayList<OadrRegisteredReportType>  registeredReportList = null;

			
			if(direction.equals(Direction.Receive)){
				oadrCreateReportTypeList= TestSession.getOadrCreateReportTypeSentList();
				registeredReportList = TestSession.getOadrRegisteredReportTypeSentList();

			}else{
				oadrCreateReportTypeList= TestSession.getOadrCreateReportTypeReceivedList();			
				registeredReportList = TestSession.getOadrRegisteredReportTypeReceivedList();

			}
			
			if(oadrCreateReportTypeList==null && oadrCreateReportTypeList.size()<1 && registeredReportList==null && registeredReportList.size()<1){
				LogHelper.addTrace("Conformance Validation Error : Unable to find any OadrCreateReport or OadrRegisteredReport to map with OadrCreatedReport");
				return false;
			}
			
			if(oadrCreateReportTypeList!=null && oadrCreateReportTypeList.size()>0){
				OadrCreateReportType oadrCreateReportType = oadrCreateReportTypeList.get(oadrCreateReportTypeList.size()-1);
				if(isRequestIDsMatch(oadrCreateReportType,oadrCreatedReport)){
					return true;
				}	
			}
			
			if(registeredReportList!=null && registeredReportList.size()>0){
				OadrRegisteredReportType oadrRegisteredReportType = registeredReportList.get(registeredReportList.size()-1);
				if(isRequestIDsMatch(oadrRegisteredReportType,oadrCreatedReport)){
					return true;
				}	
			}
			
			
		}else{
			ConformanceRuleValidator.skippingConformanceRut("disableRequestIDValid_Check","Check to see if RequestID in OadrCreateReport and OadrCreatedReport match");
		}
		
		return true;
	}

/*	public static boolean isRequestIDsMatch(OadrCreatedReportType oadrCreatedReport,Direction direction){
		if (!ConformanceRuleValidator.getDisableRequestIDValid_Check()) {
			ArrayList<OadrCreateReportType>  oadrCreateReportTypeList= null;
			ArrayList<OadrRegisteredReportType>  registeredReportList = null;

						if(direction.equals(Direction.Receive)){
				oadrCreateReportTypeList= TestSession.getOadrCreateReportTypeSentList();
				
			}else{
				oadrCreateReportTypeList= TestSession.getOadrCreateReportTypeReceivedList();			
			}
			//////////////////////////////////////
			
			
			if(direction.equals(Direction.Send)){
				oadrCreateReportTypeList = TestSession.getOadrCreateReportTypeReceivedList();
				registeredReportList = TestSession.getOadrRegisteredReportTypeReceivedList();
				 
			}else if(direction.equals(Direction.Receive)){
				oadrCreateReportTypeList = TestSession.getOadrCreateReportTypeSentList();	
				registeredReportList = TestSession.getOadrRegisteredReportTypeSentList();

			}else{
				LogHelper
				.addTraceAndConsole("Conformance Validation Error : Unable to determin the Direction");
				return false;
			}
			
			OadrCreateReportType  oadrCreateReportType  = null;
			if(oadrCreateReportTypeList!=null && oadrCreateReportTypeList.size()>0){
				oadrCreateReportType  = oadrCreateReportTypeList.get(oadrCreateReportTypeList.size()-1);
				
			}
			
			OadrCreateReportType  oadrCreateReportType  = null;
			if(oadrCreateReportTypeList!=null && oadrCreateReportTypeList.size()>0){
				oadrCreateReportType  = oadrCreateReportTypeList.get(oadrCreateReportTypeList.size()-1);
				
			}
			
			
			ArrayList<String> createReportReportReqIDList=new ArrayList<String>();
			
			for(OadrCreateReportType eachOadrCreateReport:oadrCreateReportTypeList){
				List<OadrReportRequestType>  reportRequestList = eachOadrCreateReport.getOadrReportRequest();
				createReportReportReqIDList.addAll(addCreateReportReqIDList(reportRequestList));
			}
			
			for(OadrRegisteredReportType oadrRegisteredReport:registeredReportList){
				List<OadrReportRequestType> reportRequestList = oadrRegisteredReport.getOadrReportRequest();
				createReportReportReqIDList.addAll(addCreateReportReqIDList(reportRequestList));
			}
			
			
			//////////////////////////////////////
			
			if(createReportReportReqIDList==null||createReportReportReqIDList.size()<1){
				LogHelper.addTrace("Conformance Validation Error : Unable to find any OadrCreateReport to map with OadrCreatedReport");
				return false;
			}
			
			if(oadrCreatedReport.getEiResponse().getRequestID()==null) return true;
			
			if (!OadrUtil.isIDFoundInList(oadrCreatedReport.getEiResponse().getRequestID(), createReportReportReqIDList)){	
				LogHelper
				.addTraceAndConsole("Conformance Validation Error : Unknown ReportRequestID "+oadrCreatedReport.getEiResponse().getRequestID()+" has been received");
				return false;
			}	
			
			
			if(TestSession.isValidationErrorFound()) return true;
		
				if (!ConformanceRuleValidator.getDisableRequestIDValid_Check()) {

					String requestIDReceived = oadrCreateReport.getRequestID();
					EiResponseType  eiResponseType  = oadrCreatedReport.getEiResponse();
					
					if (!OadrUtil.isRequestIDsMatch(requestIDReceived,eiResponseType)) {
						ConformanceRuleValidator.logFailure("disableRequestIDValid_Check","The RequestID in OadrCreateReport and OadrCreatedReport did not match");
						return false;
					}
				} else {
					ConformanceRuleValidator.skippingConformanceRut("disableRequestIDValid_Check","Check to see if RequestID in OadrCreateReport and OadrCreatedReport match");
				}
				
				return true;
		
				
			
		}else{
			ConformanceRuleValidator.skippingConformanceRut("disableRequestIDValid_Check","Check to see if RequestID in OadrCreateReport and OadrCreatedReport match");
		}
		
		return true;
	}
*/

	private static ArrayList<String> addCreateReportReqIDList(List<OadrReportRequestType>  reportRequestList){
		ArrayList<String> createReportMetadataReportReqIDList = new ArrayList<String>();
		for(OadrReportRequestType eachOadrReportType:reportRequestList){

			String reportSpecifierID = eachOadrReportType.getReportSpecifier().getReportSpecifierID();

				if(eachOadrReportType.getReportRequestID()!=null){
					createReportMetadataReportReqIDList.add(eachOadrReportType.getReportRequestID());	
				}
			
		}
		
		return createReportMetadataReportReqIDList;
	}
	
	
	public static boolean validateRequestIDsMatch(OadrCreateReportType oadrCreateReport,OadrCreatedReportType oadrCreatedReport){
		
		if(TestSession.isValidationErrorFound()) return true;
	
			if (!ConformanceRuleValidator.getDisableRequestIDValid_Check()) {

				String requestIDReceived = oadrCreateReport.getRequestID();
				EiResponseType  eiResponseType  = oadrCreatedReport.getEiResponse();
				
				if (!OadrUtil.isRequestIDsMatch(requestIDReceived,eiResponseType)) {
					ConformanceRuleValidator.logFailure("disableRequestIDValid_Check","The RequestID in OadrCreateReport and OadrCreatedReport did not match");
					return false;
				}
			} else {
				ConformanceRuleValidator.skippingConformanceRut("disableRequestIDValid_Check","Check to see if RequestID in OadrCreateReport and OadrCreatedReport match");
			}
			
			return true;
	}

	public static boolean isRequestIDsMatch(OadrCreateReportType oadrCreateReport,OadrCreatedReportType oadrCreatedReport){
		
		if(TestSession.isValidationErrorFound()) return true;
	
			if (!ConformanceRuleValidator.getDisableRequestIDValid_Check()) {

				String requestIDReceived = oadrCreateReport.getRequestID();
				EiResponseType  eiResponseType  = oadrCreatedReport.getEiResponse();
				
				if (!OadrUtil.isRequestIDsMatch(requestIDReceived,eiResponseType)) {
					return false;
				}
			} 
			return true;
	}

	public static boolean isRequestIDsMatch(OadrRegisteredReportType oadrRegisteredReport,OadrCreatedReportType oadrCreatedReport){
		
		if(TestSession.isValidationErrorFound()) return true;
	
			if (!ConformanceRuleValidator.getDisableRequestIDValid_Check()) {

				if(oadrRegisteredReport.getEiResponse()==null || oadrRegisteredReport.getEiResponse().getRequestID()==null|| oadrRegisteredReport.getEiResponse().getRequestID().trim().length()<1) return true;

				EiResponseType  eiResponseType  = oadrCreatedReport.getEiResponse();
				
				if (!OadrUtil.isRequestIDsMatch(oadrRegisteredReport.getEiResponse().getRequestID(),eiResponseType)) {
					return false;
				}
			} 
			return true;
	}
}
