package com.qualitylogic.openadr.core.signal.helper;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.Duration;

import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrReportRequestType;
import com.qualitylogic.openadr.core.signal.OadrReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdatedReportType;
import com.qualitylogic.openadr.core.signal.ReadingTypeEnumeratedType;
import com.qualitylogic.openadr.core.signal.ReportEnumeratedType;
import com.qualitylogic.openadr.core.signal.ReportNameEnumeratedType;
import com.qualitylogic.openadr.core.signal.SpecifierPayloadType;
import com.qualitylogic.openadr.core.signal.xcal.DurationPropType;
import com.qualitylogic.openadr.core.signal.xcal.Properties;
import com.qualitylogic.openadr.core.signal.xcal.Properties.Tolerance;
import com.qualitylogic.openadr.core.signal.xcal.WsCalendarIntervalType;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.XMLDBUtil;

public class CreateReportConformanceValidationHelper {

	public static boolean isRIDValid(
			OadrCreateReportType oadrCreateReportType, Direction direction) {
		
		List<OadrReportRequestType>  reportRequestList = oadrCreateReportType.getOadrReportRequest();
		XMLDBUtil xmlDBUtil = new XMLDBUtil();
		
		for(OadrReportRequestType eachOadrReportRequestType:reportRequestList){
			String reportSpecifierID = eachOadrReportRequestType.getReportSpecifier().getReportSpecifierID();
						
			List<SpecifierPayloadType>  specifierPayloadList = eachOadrReportRequestType.getReportSpecifier().getSpecifierPayload();
			for(SpecifierPayloadType eachSpecifierPayloadType:specifierPayloadList){

				String rid=eachSpecifierPayloadType.getRID();

				if(reportSpecifierID!=null && reportSpecifierID.trim().equals("METADATA")){
					if(rid==null || !rid.equals("0")){
						LogHelper.addTrace("Conformance check failed : For METADATA Report the rid was expected to be 0. RID is payload is "+rid);
						return false;
					}
				}else{
				
					if(!xmlDBUtil.isRIDExists(reportSpecifierID, rid,direction)){
						return false;
					}
					
				}
				
			}
		}
		
		return true;
	}
	
	
	public static boolean isReportRequestIDUnique_Check(
			OadrCreateReportType oadrCreateReportType, Direction direction) {

		ArrayList<OadrCreateReportType>  createReportList = null;
		if(direction.equals(Direction.Receive)){
			createReportList = TestSession.getOadrCreateReportTypeReceivedList();
		}else{
			createReportList = TestSession.getOadrCreateReportTypeSentList();
		}
		
		ArrayList<String> currentRequestID=new ArrayList<String>();
		
		List<OadrReportRequestType> oadrReportRequestList = oadrCreateReportType.getOadrReportRequest();
		for(OadrReportRequestType eachOadrReportRequestType:oadrReportRequestList){
			currentRequestID.add(eachOadrReportRequestType.getReportRequestID());
		}
		
		
		for(OadrCreateReportType eachOadrCreateReportType:createReportList){
			List<OadrReportRequestType> reportRequestList = eachOadrCreateReportType.getOadrReportRequest();
			for(OadrReportRequestType eachOadrReportRequestType:reportRequestList){
				String previousReportReqID = eachOadrReportRequestType.getReportRequestID();
				if(!OadrUtil.isIDUniqueInList(previousReportReqID, currentRequestID)){

					LogHelper.addTrace("Conformance check failed : Duplicate ReportRequestID "+previousReportReqID+" found");

					return false;
				}
				
			}
		}
		
		return true;
	}
	
	
	public static boolean isCreateReportReportSpecifierNotPresentValid(OadrCreateReportType oadrCreateReportType, Direction direction){
		 XMLDBUtil xmlDBUtil=new XMLDBUtil();

		 List<OadrReportRequestType>  reportRequestList = oadrCreateReportType.getOadrReportRequest();

			for(OadrReportRequestType eachOadrReportRequestType:reportRequestList){
				String reportSpecifierID = eachOadrReportRequestType.getReportSpecifier().getReportSpecifierID();
				if(reportSpecifierID.equals("METADATA")) continue;
				
				String reportName = xmlDBUtil.getCreateReport_ReportName(reportSpecifierID, direction);
				if(reportName==null || reportName.trim().length()<1){
					
					LogHelper.addTrace("Conformance check failed : ReportName not found for the ReportSpecifierID "+reportSpecifierID);
					return false;
				}

				if(!reportName.equals(ReportNameEnumeratedType.TELEMETRY_USAGE.toString()) && !reportName.equals(ReportNameEnumeratedType.TELEMETRY_STATUS.toString()) && !reportName.equals(ReportNameEnumeratedType.HISTORY_USAGE.toString())) continue;
				
					if(eachOadrReportRequestType.getReportSpecifier()!=null && eachOadrReportRequestType.getReportSpecifier().getSpecifierPayload()!=null){
						List<SpecifierPayloadType>  specifierPayloadList = eachOadrReportRequestType.getReportSpecifier().getSpecifierPayload();
								for(SpecifierPayloadType eachSpecifierPayloadType:specifierPayloadList){
									if(eachSpecifierPayloadType.getItemBase()!=null){
										LogHelper.addTrace("Conformance check failed : ItemBase was not expected to be present for TELEMETRY_USAGE, TELEMETRY_STATUS and HISTORY_USAGE Reports. ReportSpecifierID is "+reportSpecifierID);
										return false;
									}
								}
					}
				
				}
			return true;
		
	}
	
	public static boolean isHistoryUsagHistoryUsageGreenbuttonGranularityReportBackDurationZero(
			OadrCreateReportType oadrCreateReportType, Direction direction) {
		
		List<OadrReportRequestType>  reportRequestList = oadrCreateReportType.getOadrReportRequest();
		XMLDBUtil xmlDBUtil = new XMLDBUtil();

		for(OadrReportRequestType eachOadrReportRequestType:reportRequestList){
			
			
			String reportSpecifierID = eachOadrReportRequestType.getReportSpecifier().getReportSpecifierID();
			
			if(reportSpecifierID.equals("METADATA")) continue;
			
			String reportName = xmlDBUtil.getCreateReport_ReportName(reportSpecifierID, direction);
			if(reportName==null || reportName.trim().length()<1){
				
				LogHelper.addTrace("Conformance check failed : ReportName not found in database the ReportSpecifierID "+reportSpecifierID);
		
				return false;
			}
			
			if(reportName.equals(ReportNameEnumeratedType.HISTORY_USAGE) || reportName.equals(ReportNameEnumeratedType.HISTORY_GREENBUTTON)){
				String strGranularity = eachOadrReportRequestType.getReportSpecifier().getGranularity().getDuration();
				String strReportBackDuration = eachOadrReportRequestType.getReportSpecifier().getReportBackDuration().getDuration();
				
				Duration granularity = OadrUtil.createDuration(strGranularity);
				Duration reportBackDuration = OadrUtil.createDuration(strReportBackDuration);
				
				Duration zeroDuration = OadrUtil.createDuration(0, 0);
				if(granularity.compare(zeroDuration)!=DatatypeConstants.EQUAL){
					
					LogHelper.addTrace("Conformance check failed : Expected granularity was zero. Received "+strGranularity);
					
					return false;
				}
				
				if(reportBackDuration.compare(zeroDuration)!=DatatypeConstants.EQUAL){
					
					LogHelper.addTrace("Conformance check failed : Expected ReportBackDuration was zero. Received "+strReportBackDuration);
					
					return false;
				}
			}
			
		}
		
		return true;
	}
	
	
	public static boolean isPeriodicTelemetryReportContainRequiredElements(OadrCreateReportType oadrCreateReportType, Direction direction){
	 XMLDBUtil xmlDBUtil=new XMLDBUtil();

	 List<OadrReportRequestType>  reportRequestList = oadrCreateReportType.getOadrReportRequest();

		for(OadrReportRequestType eachOadrReportRequestType:reportRequestList){
			String reportSpecifierID = eachOadrReportRequestType.getReportSpecifier().getReportSpecifierID();
			if(reportSpecifierID.equals("METADATA")) continue;
			
			String reportName = xmlDBUtil.getCreateReport_ReportName(reportSpecifierID, direction);
			if(reportName==null || reportName.trim().length()<1){
				
				LogHelper.addTrace("Conformance check failed : ReportName not found for the ReportSpecifierID "+reportSpecifierID);
		
				return false;
			}

			if(!reportName.equals(ReportNameEnumeratedType.TELEMETRY_USAGE.toString()) && !reportName.equals(ReportNameEnumeratedType.TELEMETRY_STATUS.toString()) ) continue;
			
			if(eachOadrReportRequestType.getReportSpecifier()==null || eachOadrReportRequestType.getReportSpecifier().getReportBackDuration()==null) continue;
			
			DurationPropType durationPropType = eachOadrReportRequestType.getReportSpecifier().getReportBackDuration();
			String strReportBackDuration = durationPropType.getDuration();
			Duration reportBackDuration = OadrUtil.createDuration(strReportBackDuration);
			
			Duration zeroDuration = OadrUtil.createDuration("PT0S");
			
			if(!reportBackDuration.isLongerThan(zeroDuration)) continue;
			
			
			WsCalendarIntervalType  interval = eachOadrReportRequestType.getReportSpecifier().getReportInterval();
			if(interval==null || interval.getProperties()==null ){
				LogHelper.addTrace("Conformance check failed : reportSpecifier:Interval not found.");

				return false;
			}else if(interval.getProperties().getDtstart() == null ){
				LogHelper.addTrace("Conformance check failed : reportSpecifier:Interval:Dtstart not found.");

				return false;
			}else if(interval.getProperties().getDuration() == null ){
				LogHelper.addTrace("Conformance check failed : reportSpecifier:Interval:Duration not found.");
				return false;
			}
			
		}
		return true;
	}
	
	public static boolean isVenIDValid(OadrCreateReportType oadrCreateReportType, Direction direction) {
		
		if(!OadrUtil.isVENIDValid(oadrCreateReportType.getVenID(),direction)){
			return false;
		}

		return true;
	}
	
	public static boolean isReportRequestIDZeroValid(OadrCreateReportType oadrCreateReportType){
		
		List<OadrReportRequestType>  reportRequestList = oadrCreateReportType.getOadrReportRequest();
		
		for(OadrReportRequestType eachOadrReportRequestType:reportRequestList){
			String reportReqID = eachOadrReportRequestType.getReportRequestID();
				if(reportReqID==null || reportReqID.trim().length()<1 || reportReqID.equals("0")){
					return false;
				}
		}
		
		return true;
	}
	
	public static void main(String []args) throws FileNotFoundException, UnsupportedEncodingException, JAXBException{
		
		Duration durationGranularity = OadrUtil.createDuration("PT0M");
		Duration zeroDuration = OadrUtil.createDuration("PT0M");
		
		System.out.println(durationGranularity.isLongerThan(zeroDuration));
		
/*		TestSession.setServiceType(ServiceType.VTN);
		OadrCreateReportType createReport = CreateReportEventHelper.loadOadrCreateReport();
		createReport.getOadrReportRequest().get(0).getReportSpecifier().setReportSpecifierID("ReportSpecifierID170613_023613_541_0");
		createReport.getOadrReportRequest().get(0).getReportSpecifier().getSpecifierPayload().get(0).setRID("rID170613_023614_041_0");

		isReadingTypeValid(createReport);*/

		
/*		Duration reportDuration = OadrUtil.createDuration("PT2M");

		Duration minimumSamplingDuration = OadrUtil.createDuration("PT1M");
		
		if(reportDuration.isShorterThan(minimumSamplingDuration)){
			System.out.println("Conformance check failed : reportDuration "+reportDuration+" is less than minimum sampling period "+minimumSamplingDuration);
		}
*/		
		
	}
/*	public static boolean isPeriodicReportReportDurationNotLessThanMinimumSamplingPeriod(OadrCreateReportType oadrCreateReportType, Direction direction){

		List<OadrReportRequestType>  reportRequestList = oadrCreateReportType.getOadrReportRequest();

			for(OadrReportRequestType eachOadrReportRequestType:reportRequestList){
				XMLDBUtil xmlDBUtil = new XMLDBUtil();

				String reportSpecifierID = eachOadrReportRequestType.getReportSpecifier().getReportSpecifierID();
				if(reportSpecifierID.equals("METADATA")) continue;
				
				String reportName = xmlDBUtil.getCreateReport_ReportName(reportSpecifierID, direction);
				if(reportName==null || reportName.trim().length()<1){
					LogHelper.addTrace("Conformance check failed : ReportName not found for the ReportSpecifierID "+reportSpecifierID);
					return false;
				}

				if(!reportName.equals(ReportNameEnumeratedType.TELEMETRY_USAGE.toString()) && !reportName.equals(ReportNameEnumeratedType.TELEMETRY_STATUS.toString()) ) continue;
				
				String duration = eachOadrReportRequestType.getReportSpecifier().getReportBackDuration().getDuration();
				
				Duration reportDuration = OadrUtil.createDuration(duration);
				
				List<SpecifierPayloadType>  specifierPayloadList = eachOadrReportRequestType.getReportSpecifier().getSpecifierPayload();
				for(SpecifierPayloadType eachSpecifierPayloadType:specifierPayloadList){

					String rid=eachSpecifierPayloadType.getRID();
					
					String minPeriod = new XMLDBUtil().getMinPeriod(reportSpecifierID,rid,direction);
					if(minPeriod==null || minPeriod.trim().length()<1){
						continue;
					}
					
					Duration minimumSamplingDuration = OadrUtil.createDuration(minPeriod);
					
					if(reportDuration.isShorterThan(minimumSamplingDuration)){
						LogHelper.addTrace("Conformance check failed : reportDuration "+reportDuration+" is less than minimum sampling period "+minimumSamplingDuration);
						return false;
					}
					
				}
			}
			
			return true;
		
	}
*/	
	
	public static boolean isGranularityValid(OadrCreateReportType oadrCreateReportType, Direction direction){

		List<OadrReportRequestType>  reportRequestList = oadrCreateReportType.getOadrReportRequest();
		Duration zeroDuration=OadrUtil.createDuration("PT0M");


		for(OadrReportRequestType eachOadrReportRequestType:reportRequestList){
				
				String reportSpecifierID = eachOadrReportRequestType.getReportSpecifier().getReportSpecifierID();
				//boolean isMetadataReport = false;
				if(reportSpecifierID.equals("METADATA")){
					continue;
					//isMetadataReport = true;
				}
								
				List<SpecifierPayloadType>  specifierPayloadList = eachOadrReportRequestType.getReportSpecifier().getSpecifierPayload();
				for(SpecifierPayloadType eachSpecifierPayloadType:specifierPayloadList){


					DurationPropType durationGranularityProp =eachOadrReportRequestType.getReportSpecifier().getGranularity();
					String strDurationGranularityProp = durationGranularityProp.getDuration();
					
					if(strDurationGranularityProp!=null && strDurationGranularityProp.trim().length()>0){
						Duration durationGranularity = OadrUtil.createDuration(strDurationGranularityProp);
						
						if(durationGranularity.equals(zeroDuration)){
							continue;
						}

						String rid=eachSpecifierPayloadType.getRID();
						String minPeriod = new XMLDBUtil().getMinPeriod(reportSpecifierID,rid,direction);
						System.out.println("");
						if(minPeriod==null || minPeriod.trim().length()<1){
							LogHelper.addTrace("Conformance check failed : Unable to find MinPeriod for the ReportSpecifierID :"+reportSpecifierID +" and RID :"+rid+" combination");
							return false;								
						}
						
						Duration minPeriodDuration =  OadrUtil.createDuration(minPeriod);
						if(durationGranularity.isShorterThan(minPeriodDuration)){
							LogHelper.addTrace("Conformance check failed : Granularity "+durationGranularity+" is less than oadrSamplingRate:oadrMinPeriod "+minPeriodDuration+" for the ReportSpecifierID :"+reportSpecifierID +" and RID :"+rid+" combination");
							return false;
						}
					
						
	/*					if(isMetadataReport){
							if(!durationGranularity.equals(zeroDuration)){
								LogHelper.addTrace("Conformance check failed : Granularity was expected to be 0. Received :"+durationGranularity +" for the ReportSpecifierID :"+reportSpecifierID);
								return false;																
							}
						}else{
							if(durationGranularity.isLongerThan(zeroDuration)){
								String rid=eachSpecifierPayloadType.getRID();
								String minPeriod = new XMLDBUtil().getMinPeriod(reportSpecifierID,rid,direction);
								System.out.println("");
								if(minPeriod==null || minPeriod.trim().length()<1){
									LogHelper.addTrace("Conformance check failed : Unable to find MinPeriod for the ReportSpecifierID :"+reportSpecifierID +" and RID :"+rid+" combination");
									return false;								
								}
								
								Duration minPeriodDuration =  OadrUtil.createDuration(minPeriod);
								if(durationGranularity.isShorterThan(minPeriodDuration)){
									LogHelper.addTrace("Conformance check failed : Granularity "+durationGranularity+" is less than oadrSamplingRate:oadrMinPeriod "+minPeriodDuration+" for the ReportSpecifierID :"+reportSpecifierID +" and RID :"+rid+" combination");
									return false;
								}
							}
						}*/

					}
										
				}
			}
			
			return true;
		
	}
	
	
	public static boolean isCreateReportDoesNotContainExcludedReportIntervalProperties(OadrCreateReportType oadrCreateReportType, Direction direction){
		 XMLDBUtil xmlDBUtil=new XMLDBUtil();

		 List<OadrReportRequestType>  reportRequestList = oadrCreateReportType.getOadrReportRequest();

			for(OadrReportRequestType eachOadrReportRequestType:reportRequestList){
				String reportSpecifierID = eachOadrReportRequestType.getReportSpecifier().getReportSpecifierID();
				if(reportSpecifierID.equals("METADATA")) continue;
				
				String reportName = xmlDBUtil.getCreateReport_ReportName(reportSpecifierID, direction);
				if(reportName==null || reportName.trim().length()<1){
					LogHelper.addTrace("Conformance check failed : ReportName not found for the ReportSpecifierID "+reportSpecifierID);
					return false;
				}
				
					if(eachOadrReportRequestType.getReportSpecifier()==null || eachOadrReportRequestType.getReportSpecifier().getReportInterval()==null || eachOadrReportRequestType.getReportSpecifier().getReportInterval().getProperties()==null) continue;
								
							
						Properties  properties  =eachOadrReportRequestType.getReportSpecifier().getReportInterval().getProperties();
					

							if((properties.getTolerance()!=null)){
								LogHelper.addTrace("Conformance check failed : Found Tolerance in report request payload");
								return false;
							}

							if(properties.getXEiNotification()!=null){
								LogHelper.addTrace("Conformance check failed : Found XEiNotification in report request payload");
								return false;
							}
							
							if(properties.getXEiRampUp()!=null){
								LogHelper.addTrace("Conformance check failed : Found XEiRampUp in report request payload");
								return false;
							}
							
							if(properties.getXEiRecovery()!=null){
								LogHelper.addTrace("Conformance check failed : Found XEiRecovery in report request payload");
								return false;
							}

				}
			return true;
	}



	public static boolean isReadingTypeValid(OadrCreateReportType oadrCreateReportType){
		 List<OadrReportRequestType>  reportRequestList = oadrCreateReportType.getOadrReportRequest();

			for(OadrReportRequestType eachOadrReportRequestType:reportRequestList){

						if(eachOadrReportRequestType.getReportSpecifier()==null || eachOadrReportRequestType.getReportSpecifier().getSpecifierPayload()==null) continue;
						
						List<SpecifierPayloadType>  specifierPayloadList = eachOadrReportRequestType.getReportSpecifier().getSpecifierPayload();
								for(SpecifierPayloadType eachSpecifierPayloadType:specifierPayloadList){
									String readingType = eachSpecifierPayloadType.getReadingType();
									
									if(readingType==null){
										LogHelper.addTrace("Conformance check failed : ReadingType was expected to be x-notApplicable. Received no ReadingType");
										return false;
									}
									
									if(!readingType.equals(ReadingTypeEnumeratedType.X_NOT_APPLICABLE.value())){
											LogHelper.addTrace("Conformance check failed : ReadingType is not set to x-notApplicable");
											return false;
									}
										
									
								}
				
				}
			return true;
		
	}

}