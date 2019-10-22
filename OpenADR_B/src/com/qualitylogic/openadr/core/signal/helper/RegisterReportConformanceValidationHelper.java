package com.qualitylogic.openadr.core.signal.helper;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.action.impl.DefaultResponseRegisteredReportTypeAckAction;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.channel.util.TraceUtil;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.EiTargetType;
import com.qualitylogic.openadr.core.signal.EnergyRealType;
import com.qualitylogic.openadr.core.signal.Intervals;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.signal.OadrReportDescriptionType;
import com.qualitylogic.openadr.core.signal.OadrReportRequestType;
import com.qualitylogic.openadr.core.signal.OadrReportType;
import com.qualitylogic.openadr.core.signal.OadrSamplingRateType;
import com.qualitylogic.openadr.core.signal.PowerRealType;
import com.qualitylogic.openadr.core.signal.ReportNameEnumeratedType;
import com.qualitylogic.openadr.core.signal.ReportPayloadType;
import com.qualitylogic.openadr.core.signal.xcal.Dtstart;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class RegisterReportConformanceValidationHelper {

	public static boolean isResponseCodeSuccess(
			OadrDistributeEventType oadrDistributeEvent) {
		if (oadrDistributeEvent.getEiResponse() != null
				&& oadrDistributeEvent.getEiResponse() != null
				&& !oadrDistributeEvent.getEiResponse().getResponseCode()
						.startsWith("2")) {
			return false;
		}
		return true;
	}

	public static boolean isMarketContextValid(
			OadrRegisterReportType oadrRegisterReportType) {
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

		String pDR_MarketContext_1_Name = propertiesFileReader
				.get("DR_MarketContext_1_Name");
		String pDR_MarketContext_2_Name = propertiesFileReader
				.get("DR_MarketContext_2_Name");

		List<OadrReportType> oadrReportList = oadrRegisterReportType.getOadrReport();
		
		for(OadrReportType eachOadrReport:oadrReportList){
			List<OadrReportDescriptionType> reportDescriptionList = eachOadrReport.getOadrReportDescription();
			for(OadrReportDescriptionType eachReportDescription:reportDescriptionList){
				String marketContexInPayload = eachReportDescription.getMarketContext();

				if(!OadrUtil.isEmpty(marketContexInPayload)){
					
					if (!marketContexInPayload.equalsIgnoreCase(pDR_MarketContext_1_Name)
							&& !marketContexInPayload.equalsIgnoreCase(pDR_MarketContext_2_Name)) {
	
						return false;
					}
					
				}

				
			}
		}
				return true;
	}
	

	public static boolean isAtLeastOneEiTargetMatch(
			OadrRegisterReportType oadrRegisterReportType) {
				
		List<OadrReportType>  oadrReportList = oadrRegisterReportType.getOadrReport();
		for(OadrReportType eachOadrReportType:oadrReportList){
			List<OadrReportDescriptionType> reportDescriptionList = eachOadrReportType.getOadrReportDescription();
			for(OadrReportDescriptionType eachOadrReportDescriptionType:reportDescriptionList){
				EiTargetType eachTarget= eachOadrReportDescriptionType.getReportDataSource();
				if(!OadrUtil.isAtLeastOneEiTargetMatch(eachTarget)){
					return false;
				}
			}
		}
		
		return true;
	}



	public static boolean isDurationValid(
			OadrRegisterReportType oadrRegisterReportType) {
				
		List<OadrReportType>  oadrReportList = oadrRegisterReportType.getOadrReport();
		for(OadrReportType eachOadrReportType:oadrReportList){
			
			if(eachOadrReportType.getDuration()==null || eachOadrReportType.getDuration().getDuration()==null){
				LogHelper
				.addTrace("Conformance Validation Error : Duration is not available in OadrRegisterReport with ReportRequestID "+eachOadrReportType.getReportRequestID());
				return false;
			}
			
			String strReportDuration = eachOadrReportType.getDuration().getDuration();
			Duration reportDuration=OadrUtil.createDuration(strReportDuration);

			List<OadrReportDescriptionType> reportDescriptionList = eachOadrReportType.getOadrReportDescription();
			for(OadrReportDescriptionType eachOadrReportDescriptionType:reportDescriptionList){
				
				OadrSamplingRateType oadrSamplingRate = eachOadrReportDescriptionType.getOadrSamplingRate();

				if(eachOadrReportType.getReportName().equals(ReportNameEnumeratedType.METADATA_HISTORY_USAGE.toString()) && oadrSamplingRate==null){
					continue;
				}
				
				if(oadrSamplingRate==null){
					LogHelper
					.addTrace("Conformance Validation Error : OadrSamplingRateType is not present");
					return false;
				}
				
				String strDurationMaxPeriod = oadrSamplingRate.getOadrMaxPeriod();
				//oadrSamplingRate:oadrMaxPeriod is not larger than oadrReport:Duration
				Duration durationMaxPeriod=OadrUtil.createDuration(strDurationMaxPeriod);
				if(durationMaxPeriod.isLongerThan(reportDuration)){
					LogHelper
					.addTrace("Conformance Validation Error : OadrMaxPeriod "+strDurationMaxPeriod+" is longer than ReportDuration "+strReportDuration);
					return false;
				}
			}
		}
		
		return true;
	}

	public static boolean isReportSpecifierIDAndRIDUnique(
			OadrRegisterReportType oadrRegisterReportType) {
				
		List<OadrReportType>  oadrReportList = oadrRegisterReportType.getOadrReport();
		for(OadrReportType eachOadrReportType:oadrReportList){
			
			String reportSpecifierID = eachOadrReportType.getReportSpecifierID();
			int reportSpecifierIDCountFoundCount = reportSpecifierIDCountFoundCount(reportSpecifierID,oadrReportList);
		
			if(reportSpecifierIDCountFoundCount!=1){
				LogHelper.addTrace("Conformance check failed : ReportSpecifierID is not unique");
				return false;
			}
			
			List<OadrReportDescriptionType>  oadrReportDescriptionList =eachOadrReportType.getOadrReportDescription();
			ArrayList<String> ridList = new ArrayList<String>();
			for(OadrReportDescriptionType eachOadrReportDescriptionType:oadrReportDescriptionList){
			
				for(String eachRIDAddedSofar:ridList){
					if(eachRIDAddedSofar.equals(eachOadrReportDescriptionType.getRID())){
						LogHelper.addTrace("Conformance check failed : Duplicate RID "+eachRIDAddedSofar+" found within ReportSpecifierID "+reportSpecifierID);						
						return false;
					}
				}
				
				ridList.add(eachOadrReportDescriptionType.getRID());
			
			}
		}
		
		return true;
	}

	private static int reportSpecifierIDCountFoundCount(String reportSpecifierIDToFind, 
			List<OadrReportType>  oadrReportList) {
				int countReportSpecifierIDFoundCount = 0; 
		for(OadrReportType eachOadrReportType:oadrReportList){
			String reportSpecifierID = eachOadrReportType.getReportSpecifierID();
			if(reportSpecifierIDToFind.equals(reportSpecifierID)){
				countReportSpecifierIDFoundCount++;
			}
		}
		return countReportSpecifierIDFoundCount;
	}
	
	
	public static boolean isSamplingRateFoundInTelemertyMetadata(
			OadrRegisterReportType oadrRegisterReportType) {
				
		List<OadrReportType>  oadrReportList = oadrRegisterReportType.getOadrReport();
		for(OadrReportType eachOadrReportType:oadrReportList){
			
			if(eachOadrReportType.getReportName().equals(ReportNameEnumeratedType.METADATA_TELEMETRY_STATUS.toString()) || eachOadrReportType.getReportName().equals(ReportNameEnumeratedType.METADATA_TELEMETRY_USAGE.toString())){
				List<OadrReportDescriptionType> oadrReportDescriptionTypeList = eachOadrReportType.getOadrReportDescription();
				
				for(OadrReportDescriptionType eachOadrReportDescriptionType:oadrReportDescriptionTypeList){
					OadrSamplingRateType oadrSamplingRateType = eachOadrReportDescriptionType.getOadrSamplingRate();
					if(oadrSamplingRateType==null){
						LogHelper.addTrace("Conformance check failed : OadrSamplingRate is not specified");
						return false;
					}
				}
			}
			
			
		}
		
		return true;
	}
	
	
	public static boolean isReportNameReportTypeItemBaseReadingTypeCombinationValid(
			OadrRegisterReportType oadrRegisterReportType) {
				
		List<OadrReportType>  oadrReportList = oadrRegisterReportType.getOadrReport();
		for(OadrReportType eachOadrReportType:oadrReportList){

			if(eachOadrReportType.getReportName().equals(ReportNameEnumeratedType.METADATA_TELEMETRY_USAGE.toString()) || eachOadrReportType.getReportName().equals(ReportNameEnumeratedType.METADATA_HISTORY_USAGE.toString())){
				List<OadrReportDescriptionType> oadrReportDescriptionTypeList = eachOadrReportType.getOadrReportDescription();
				
				
				for(OadrReportDescriptionType eachOadrReportDescriptionType:oadrReportDescriptionTypeList){
					if(eachOadrReportDescriptionType.getReportType()!=null &&!eachOadrReportDescriptionType.getReportType().equalsIgnoreCase("usage")){
						LogHelper
						.addTrace("Conformance Validation Error : For Report "+eachOadrReportType.getReportName()+" the ReportType received is "+eachOadrReportDescriptionType.getReportType()+". Expected is usage.");
						return false;
						
					}
					
					if(eachOadrReportDescriptionType.getItemBase()!=null && eachOadrReportDescriptionType.getItemBase().getValue()!=null){
						if(!eachOadrReportDescriptionType.getItemBase().getValue().getClass().equals(PowerRealType.class) && !eachOadrReportDescriptionType.getItemBase().getValue().getClass().equals(EnergyRealType.class)){
							LogHelper
							.addTrace("Conformance Validation Error : For Report "+eachOadrReportType.getReportName()+" the ItemBase received is "+eachOadrReportDescriptionType.getItemBase().getValue().getClass()+". Expected is PowerReal or EnergyReal.");
							return false;
						}
					}
					
					if(eachOadrReportDescriptionType.getReadingType()!=null && !eachOadrReportDescriptionType.getReadingType().equalsIgnoreCase("Direct Read")){
						LogHelper
						.addTrace("Conformance Validation Error : For Report "+eachOadrReportType.getReportName()+" the ReadingType received is "+eachOadrReportDescriptionType.getReadingType()+". Expected is 'Direct Read'.");
						return false;
					}
					
				}
			}
				
			if(eachOadrReportType.getReportName().equals(ReportNameEnumeratedType.METADATA_TELEMETRY_STATUS.toString())){
				List<OadrReportDescriptionType> oadrReportDescriptionTypeList = eachOadrReportType.getOadrReportDescription();
				
				
				for(OadrReportDescriptionType eachOadrReportDescriptionType:oadrReportDescriptionTypeList){
					if(eachOadrReportDescriptionType.getReportType()!=null && !eachOadrReportDescriptionType.getReportType().equalsIgnoreCase("x-resourceStatus")){
						LogHelper
						.addTrace("Conformance Validation Error : For Report TELEMETRY_STATUS the ReportType received is "+eachOadrReportDescriptionType.getReportType()+". Expected is x-resourceStatus.");
						return false;
					}
					
					 
					if(eachOadrReportDescriptionType.getItemBase()!=null && eachOadrReportDescriptionType.getItemBase().getValue()!=null){
						LogHelper
						.addTrace("Conformance Validation Error : For Report TELEMETRY_STATUS the ItemBase received is "+eachOadrReportDescriptionType.getItemBase().getValue().getClass()+". Expected is None.");
						return false;
					}
					
					
					if(eachOadrReportDescriptionType.getReadingType()!=null && !eachOadrReportDescriptionType.getReadingType().equalsIgnoreCase("x-notApplicable")){
						LogHelper
						.addTrace("Conformance Validation Error : For Report TELEMETRY_STATUS the ReadingType received is "+eachOadrReportDescriptionType.getReadingType()+". Expected is 'x-notApplicable'.");
						return false;
					}
					
				}
			}
			
			
		}
		
		return true;
	}
	
	
	public static boolean isVenIDValid(OadrRegisterReportType oadrRegisterReportType, Direction direction) {
		
		
		if(!OadrUtil.isVENIDValid(oadrRegisterReportType.getVenID(),direction)){
			return false;
		}

		return true;
	}
	
	public static boolean isVenReportNameValid(OadrRegisterReportType oadrRegisterReportType, Direction direction) {
		ServiceType serviceType = TestSession.getServiceType();

		boolean atLeastOneTelemetryStatusReportFound = false;
		boolean atLeastOneTelemetryUsageReportFound = false;
		
		if((serviceType.equals(ServiceType.VEN) && direction.equals(Direction.Send))||(serviceType.equals(ServiceType.VTN) && direction.equals(Direction.Receive))){
			List<OadrReportType> oadrReportList= oadrRegisterReportType.getOadrReport();
			if(oadrReportList==null || oadrReportList.size()<1){
				return true;
			}
			
			for(OadrReportType eachOadrReport:oadrReportList){
				
				if(eachOadrReport.getReportName().equals(ReportNameEnumeratedType.METADATA_TELEMETRY_STATUS.toString())){
					atLeastOneTelemetryStatusReportFound = true;
				}
				
				if(eachOadrReport.getReportName().equals(ReportNameEnumeratedType.METADATA_TELEMETRY_USAGE.toString())){
					atLeastOneTelemetryUsageReportFound = true;
				}
				
				if(atLeastOneTelemetryStatusReportFound && atLeastOneTelemetryUsageReportFound){
					break;
				}
			}
			
					
			if(!atLeastOneTelemetryStatusReportFound || !atLeastOneTelemetryUsageReportFound){

				String report = "";
				
				if(!atLeastOneTelemetryStatusReportFound){
					report = "TELEMETERY_STATUS";
				}

				if(!atLeastOneTelemetryUsageReportFound){
					if(report.trim().length()>1){
						report = report + " and ";
					}
				
					report = report+"TELEMETERY_USAGE";
				}
				
				LogHelper
				.addTrace("Conformance Validation Error : Did not receive the expected "+report+" Report");
				return false;
			
			}

		}
			
		if(!OadrUtil.isVENIDValid(oadrRegisterReportType.getVenID(),direction)){
			return false;
		}

		return true;
	}
	
	public static boolean isReportRequestIDZeroValid(OadrRegisterReportType oadrRegisterReportType) {
		
		List<OadrReportType>  reportList = oadrRegisterReportType.getOadrReport();
		
		for(OadrReportType eachOadrReport:reportList){
			String reportReqID = eachOadrReport.getReportRequestID();
				if(reportReqID==null || !reportReqID.equals("0")){
					return false;
				}
		}

		return true;
	}
	
	public static boolean isDeviceClassValuesPresent(
			OadrRegisterReportType oadrRegisterReportType) {

		List<OadrReportType> oadrReportList = oadrRegisterReportType.getOadrReport();
		
		for(OadrReportType eachOadrReport:oadrReportList){
			List<OadrReportDescriptionType> reportDescriptionList = eachOadrReport.getOadrReportDescription();
			for(OadrReportDescriptionType eachReportDescription:reportDescriptionList){
				
				EiTargetType targetType =  eachReportDescription.getReportSubject();
				if(targetType==null) return true;
				
				if(targetType.getEndDeviceAsset()==null){
					LogHelper
					.addTrace("Conformance Validation Error : TargetType is not empty but the EndDeviceAsset is empty");
					return false;
				}
				
				if(!OadrUtil.isDeviceClassFound(targetType.getEndDeviceAsset())){
					return false;
				}
				
			}
		}
		
		return true;
	}

	public static boolean isIntervalsDtstartEiReportIDNotPresent(
			OadrRegisterReportType oadrRegisterReportType) {

		List<OadrReportType> oadrReportList = oadrRegisterReportType.getOadrReport();
		
		for(OadrReportType eachOadrReport:oadrReportList){
			Intervals intervals = eachOadrReport.getIntervals();
			Dtstart dtstart = eachOadrReport.getDtstart();
			String eiReportID = eachOadrReport.getEiReportID();
			
			if(intervals!=null){
				LogHelper
				.addTrace("Conformance Validation Error : Intervals cannot be specified for metadata report");
				return false;
			}
			
			if(dtstart!=null){
				LogHelper
				.addTrace("Conformance Validation Error : Dtstart cannot be specified for metadata report");
				return false;
			}
		
			if(eiReportID!=null){
				LogHelper
				.addTrace("Conformance Validation Error : EiReportID cannot be specified for metadata report");
				return false;
			}
		}
		
		return true;
	}
	
	public static String isCreatedDateTimeWithinOneMin(
			OadrRegisterReportType oadrRegisterReportType)
			throws DatatypeConfigurationException {
		XMLGregorianCalendar currentDateTimeStart = OadrUtil.getCurrentTime();
		String currentTime = currentDateTimeStart.toString();

		List<OadrReportType> oadrReportList = oadrRegisterReportType.getOadrReport();
		
		for(OadrReportType eachOadrReportType:oadrReportList){
			boolean isCurrentDateTimeWithinWithinFiveMin = OadrUtil.isCurrentDateTimeWithinOneMin(eachOadrReportType.getCreatedDateTime(),currentDateTimeStart);
			
			if (!isCurrentDateTimeWithinWithinFiveMin) {
				return currentTime;
			}
	
		}
		
		
		return "";
	}

	
	public static boolean isReportRequestIDMatchPreviousCreateReport(
			OadrRegisterReportType oadrRegisterReportType,Direction direction)
			throws DatatypeConfigurationException {
		
		ArrayList<OadrCreateReportType>  createEventList = null;
		ArrayList<OadrRegisteredReportType>  registeredReportList = null;

		if(direction.equals(Direction.Send)){
			createEventList = TestSession.getOadrCreateReportTypeReceivedList();
			registeredReportList = TestSession.getOadrRegisteredReportTypeReceivedList();
			 
		}else if(direction.equals(Direction.Receive)){
			createEventList = TestSession.getOadrCreateReportTypeSentList();	
			registeredReportList = TestSession.getOadrRegisteredReportTypeSentList();

		}else{
			LogHelper
			.addTraceAndConsole("Conformance Validation Error : Unable to determin the Direction");
			return false;
		}

		//oadrRegisterReportType.get
		ArrayList<String> createReportMetadataReportReqIDList=new ArrayList<String>();
		
		for(OadrCreateReportType eachOadrCreateReport:createEventList){
			List<OadrReportRequestType>  reportRequestList = eachOadrCreateReport.getOadrReportRequest();
			createReportMetadataReportReqIDList.addAll(addCreateReportMetadataReportReqIDList(reportRequestList));
		}
		
		for(OadrRegisteredReportType oadrRegisteredReport:registeredReportList){
			List<OadrReportRequestType> reportRequestList = oadrRegisteredReport.getOadrReportRequest();
			createReportMetadataReportReqIDList.addAll(addCreateReportMetadataReportReqIDList(reportRequestList));
		}
		
		
			String reportRequestID = oadrRegisterReportType.getReportRequestID();
			
			if(createReportMetadataReportReqIDList.size()<1){
				
				if (reportRequestID!=null ){
					LogHelper
					.addTraceAndConsole("Conformance Validation Error : Expected the OadrRegisterReport.ReportRequestID to be empty. Received ReportRequestID  "+reportRequestID);
					return false;
				}	
				
				List<OadrReportType> oadrReportList = oadrRegisterReportType.getOadrReport();
				
				for(OadrReportType eachOadrReportType:oadrReportList){
					if(eachOadrReportType.getReportRequestID()==null || !eachOadrReportType.getReportRequestID().equals("0")){
						LogHelper
						.addTraceAndConsole("Conformance Validation Error : Expected the OadrReport.ReportRequestID to be 0. Received ReportRequestID  "+eachOadrReportType.getReportRequestID());
						return false;		
					}
				}
				
			}else{
				
				if (!OadrUtil.isIDFoundInList(reportRequestID, createReportMetadataReportReqIDList)){	
					LogHelper
					.addTraceAndConsole("Conformance Validation Error : Unknown ReportRequestID "+reportRequestID+" has been received");
					return false;
				}	
				
			}
			

			return true;
	}

	private static ArrayList<String> addCreateReportMetadataReportReqIDList(List<OadrReportRequestType>  reportRequestList){
		ArrayList<String> createReportMetadataReportReqIDList = new ArrayList<String>();
		for(OadrReportRequestType eachOadrReportType:reportRequestList){

			String reportSpecifierID = eachOadrReportType.getReportSpecifier().getReportSpecifierID();

			if(reportSpecifierID!=null && reportSpecifierID.trim().equals("METADATA")){
				if(eachOadrReportType.getReportRequestID()!=null){
					createReportMetadataReportReqIDList.add(eachOadrReportType.getReportRequestID());	
				}
			}
		}
		
		return createReportMetadataReportReqIDList;
	}
	
	public static void main(String []args) throws FileNotFoundException, UnsupportedEncodingException, JAXBException{

		OadrRegisterReportType oadrRegisterReportType = RegisterReportEventHelper.loadMetadata_001();
		isIntervalsDtstartEiReportIDNotPresent(oadrRegisterReportType);
	
	}
	
	
}