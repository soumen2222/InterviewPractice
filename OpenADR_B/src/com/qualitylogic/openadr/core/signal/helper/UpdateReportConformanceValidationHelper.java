package com.qualitylogic.openadr.core.signal.helper;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.Duration;

import com.qualitylogic.openadr.core.action.IResponseUpdateReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseCreateReportRequest_001Action;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseUpdateReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseUpdateReport_002TypeAckAction;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.EiEventSignalType;
import com.qualitylogic.openadr.core.signal.EiTargetType;
import com.qualitylogic.openadr.core.signal.EndDeviceAssetType;
import com.qualitylogic.openadr.core.signal.IntervalType;
import com.qualitylogic.openadr.core.signal.Intervals;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrLoadControlStateType;
import com.qualitylogic.openadr.core.signal.OadrPayloadResourceStatusType;
import com.qualitylogic.openadr.core.signal.OadrReportDescriptionType;
import com.qualitylogic.openadr.core.signal.OadrReportPayloadType;
import com.qualitylogic.openadr.core.signal.OadrReportRequestType;
import com.qualitylogic.openadr.core.signal.OadrReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdatedReportType;
import com.qualitylogic.openadr.core.signal.PayloadBaseType;
import com.qualitylogic.openadr.core.signal.ReportEnumeratedType;
import com.qualitylogic.openadr.core.signal.ReportNameEnumeratedType;
import com.qualitylogic.openadr.core.signal.SpecifierPayloadType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.StreamPayloadBaseType;
import com.qualitylogic.openadr.core.signal.greenbutton.ObjectFactory;
import com.qualitylogic.openadr.core.signal.xcal.Dtstart;
import com.qualitylogic.openadr.core.signal.xcal.DurationPropType;
import com.qualitylogic.openadr.core.signal.xcal.Uid;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.XMLDBUtil;

public class UpdateReportConformanceValidationHelper {

	private static boolean isRIDFoundinReceivedCreateReport(String reportSpecifierIDInUpdateReport,String ridInUpdateReport){
		
		ArrayList<OadrCreateReportType> oadrCreateReportList=TestSession.getOadrCreateReportTypeReceivedList();
		for(OadrCreateReportType eachOadrCreateReportType:oadrCreateReportList){
			List<OadrReportRequestType> reportRequestList = eachOadrCreateReportType.getOadrReportRequest();
			for(OadrReportRequestType eachOadrReportRequestType:reportRequestList){
				String reportSpecifierIDInCreateReport = eachOadrReportRequestType.getReportSpecifier().getReportSpecifierID();

				if(!reportSpecifierIDInCreateReport.equals(reportSpecifierIDInUpdateReport)) continue;
				
				List<SpecifierPayloadType>  specifierPayloadList = eachOadrReportRequestType.getReportSpecifier().getSpecifierPayload();
				for(SpecifierPayloadType specifierPayloadType:specifierPayloadList){
					String ridInCreateReport = specifierPayloadType.getRID();
					if(ridInCreateReport.equals(ridInUpdateReport)){
						return true;
					}
				}
			}
		}
		
		return false;
	}
	public static boolean isRIDValid_Check(
			OadrUpdateReportType oadrUpdateReportType) {

		List<OadrReportType> oadrReportTypeList = oadrUpdateReportType.getOadrReport();
		
		for(OadrReportType eachOadrReportType:oadrReportTypeList){
			String reportSpecifierID = eachOadrReportType.getReportSpecifierID();
			List<OadrReportDescriptionType>  reportDescriptionList = eachOadrReportType.getOadrReportDescription();
			for(OadrReportDescriptionType eachOadrReportDescriptionType:reportDescriptionList){
				String rid = eachOadrReportDescriptionType.getRID();
				if(!isRIDFoundinReceivedCreateReport(reportSpecifierID,rid)){
					LogHelper
					.addTrace("Conformance Validation Error : Unable to map reportSpecifierID "+reportSpecifierID+" and rid "+rid+" to OadrCreateReport");
					return false;
				}
			}
		}
		
		return true;
	}
	
	
	public static boolean isRequiredElementsAndReportNameValid(
			OadrUpdateReportType oadrUpdateReportType, Direction direction) {

		XMLDBUtil xmlDBUtil = new XMLDBUtil();
		
		List<OadrReportType> oadrReportTypeList = oadrUpdateReportType.getOadrReport();
		
		for(OadrReportType eachOadrReportType:oadrReportTypeList){
			
			String reportSpecifierID = eachOadrReportType.getReportSpecifierID();
			if(reportSpecifierID.equals("METADATA")){
				LogHelper.addTrace("Conformance Validation Error : ReportSpecifierID in OadrUpdateReport cannot be METADATA");
				return false;
			}
			
			Dtstart startDate = eachOadrReportType.getDtstart();
			if(startDate==null){
				
				LogHelper.addTrace("Conformance Validation Error : Dtstart is null");
				return false;
			}
			Intervals intervals = eachOadrReportType.getIntervals();
			
			
			if(intervals==null || intervals.getInterval().size()<1){
				LogHelper.addTrace("Conformance Validation Error : Does not contain Interval");
				return false;
			}
			
			String reportName = eachOadrReportType.getReportName();
			if(reportName==null || reportName.trim().length()<1){
				
				LogHelper.addTrace("Conformance Validation Error : ReportName is empty");
				return false;
			}
			
			if(reportName.trim().startsWith("METADATA_")){
				LogHelper.addTrace("Conformance Validation Error : METADATA_ prefix was found in the ReportName");
				return false;
			}
		
			
			String reportNameInDB = xmlDBUtil.getUpdateReport_ReportName(reportSpecifierID, direction);
			if(reportNameInDB==null || reportNameInDB.trim().length()<1){
				
				LogHelper.addTrace("Conformance check failed : ReportName not found in Database for the ReportSpecifierID "+reportSpecifierID);		
				return false;
			}else if(!reportNameInDB.equals(reportName)){
				LogHelper.addTrace("Conformance check failed : Metadata ReportName in database "+reportNameInDB+" does not match report name "+reportName+" specified in UpdateReport for the ReportSpecifierID "+reportSpecifierID);				
				return false;
			}
			
			
		}
		
		return true;
	}
		
	
	
	public static boolean isUIDValid(OadrUpdateReportType updateReportType){
		
		List<OadrReportType> reportList = updateReportType.getOadrReport();
		
		for(OadrReportType eachOadrReport:reportList){
			
			if(eachOadrReport.getReportName().equals(ReportNameEnumeratedType.HISTORY_GREENBUTTON)){
				continue;
			}
			
			Intervals intervals = eachOadrReport.getIntervals();
			List<IntervalType> intervalList = intervals.getInterval();
			
			int expectedUID = 0;
			for(IntervalType eachInterval:intervalList){
				Dtstart  dtstart  = eachInterval.getDtstart();
				if(dtstart==null || dtstart.getDateTime()==null){
					
					com.qualitylogic.openadr.core.signal.xcal.Uid uid = eachInterval.getUid();
					if(uid!=null){
						
						if(!OadrUtil.isUIDIntValue(uid)){
							return false;
						}

						int currentUID = Integer.parseInt(uid.getText());
						if (currentUID != expectedUID) {
							LogHelper
									.addTrace("\nConformance Validation Error : UID received is "
											+ currentUID
											+ " but expected is "
											+ expectedUID
											+ " for the reportRequestID : "
											+ eachOadrReport.getReportRequestID() + "\n");

							return false;

						}
						
						expectedUID++;
					}else{
						LogHelper.addTrace("Conformance check failed : UID is not present");
						return false;
					}
					
					//continue;
				}else{
					com.qualitylogic.openadr.core.signal.xcal.Uid uid = eachInterval.getUid();
					if(uid==null){

						return true;
					}else{
						
						if(!OadrUtil.isUIDIntValue(uid)){
							return false;
						}
						
						
						int currentUID = Integer.parseInt(uid.getText());
						if (currentUID != expectedUID) {
							LogHelper
									.addTrace("\nConformance Validation Error : UID received is "
											+ currentUID
											+ " but expected is "
											+ expectedUID
											+ " for the reportRequestID : "
											+ eachOadrReport.getReportRequestID() + "\n");

							return false;

						}
						
						expectedUID++;
					}
				}
			}
			
		}
		
	
		return true;
	}


	
	private static boolean isAtleastOneDtstartPresentInInterval(List<IntervalType> intervalList){
		if(intervalList==null) return true;

		for(IntervalType eachInterval:intervalList){
			Dtstart  dtstart  = eachInterval.getDtstart();
			if(dtstart!=null && dtstart.getDateTime()!=null){
				return true;
			}
		}
		
		return false;
	}

	private static boolean isAllIntervalHasDtstart(List<IntervalType> intervalList){
		if(intervalList==null) return true;

		for(IntervalType eachInterval:intervalList){
			Dtstart  dtstart  = eachInterval.getDtstart();
			if(dtstart==null || dtstart.getDateTime()==null){
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean isReportDtstartValid(OadrUpdateReportType updateReportType){
		
		List<OadrReportType> reportList = updateReportType.getOadrReport();
		
		for(OadrReportType eachOadrReport:reportList){
			Dtstart  reportDtstart  = eachOadrReport.getDtstart();
			
			if(eachOadrReport.getReportName().equals(ReportNameEnumeratedType.HISTORY_GREENBUTTON)){
				continue;
			}
			
			Intervals intervals = eachOadrReport.getIntervals();
			List<IntervalType> intervalList = intervals.getInterval();
			
			boolean isAtleastOneDtstartPresentInInterval = isAtleastOneDtstartPresentInInterval(intervalList);
			 if(isAtleastOneDtstartPresentInInterval){
				 if(!isAllIntervalHasDtstart(intervalList)){
						LogHelper.addTrace("Conformance check failed : If one interval contains dtstart, all intervals must contain dtstart.");
						return false;
				 }
			 }else{
				 return true;
			 }
			 
			 if(reportDtstart!=null && reportDtstart.getDateTime()!=null && intervalList!=null && intervalList.size()>0){
				 Dtstart firstIntervalDtstart = intervalList.get(0).getDtstart();
				 
				 if(!reportDtstart.getDateTime().equals(firstIntervalDtstart.getDateTime())){
					 
					 LogHelper.addTrace("Conformance check failed : Report Dtstart "+reportDtstart.getDateTime()+" did not match the first interval's Dtstart "+firstIntervalDtstart.getDateTime());
					 return false;
				 }
			 }
		}
		
	
		return true;
	}
///////////////////////////////////////////
	
	
	
	private static boolean isAtleastOneDurationPresentInInterval(List<IntervalType> intervalList){
		if(intervalList==null) return true;
		for(IntervalType eachInterval:intervalList){
			DurationPropType durationPropType = eachInterval.getDuration();
			if(durationPropType!=null && durationPropType.getDuration()!=null){
				return true;				
			}
		}
		return false;
	}

	private static boolean isAllIntervalHasDuration(List<IntervalType> intervalList){
		if(intervalList==null) return true;

		for(IntervalType eachInterval:intervalList){
			DurationPropType  durationPropType  = eachInterval.getDuration();
			if(durationPropType==null || durationPropType.getDuration()==null){
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean isReportDurationValid(OadrUpdateReportType updateReportType){
		
		List<OadrReportType> reportList = updateReportType.getOadrReport();
		
		for(OadrReportType eachOadrReport:reportList){
			DurationPropType  reportDurationPropType  = eachOadrReport.getDuration();
			
			
			Intervals intervals = eachOadrReport.getIntervals();
			List<IntervalType> intervalList = null;
			if(intervals!=null){
				intervalList = intervals.getInterval();				
			}
			
			boolean isAtleastOneDurationPresentInInterval = isAtleastOneDurationPresentInInterval(intervalList);
			 if(isAtleastOneDurationPresentInInterval){
				 if(!isAllIntervalHasDuration(intervalList)){
						LogHelper.addTrace("Conformance check failed : If one interval contains Duration, all intervals must contain Duration.");
						return false;
				 }
			 }
			 
			 if(reportDurationPropType!=null && reportDurationPropType.getDuration()!=null && intervalList!=null && intervalList.size()>0){
				 Duration totalIntervalDuration = OadrUtil.createDuration(0, 0);
				 
				 for(IntervalType eachIntervalType:intervalList){
					 
					 if(eachIntervalType.getDuration()==null || eachIntervalType.getDuration().getDuration()==null) continue;

					 Duration eachIntervalDuration  = OadrUtil.createDuration(eachIntervalType.getDuration().getDuration());
					 totalIntervalDuration = totalIntervalDuration.add(eachIntervalDuration);
					 
					 }
				 
				 if(!OadrUtil.createDuration(reportDurationPropType.getDuration()).equals(totalIntervalDuration)){
					 
					 LogHelper.addTrace("Conformance check failed : Report Duration "+reportDurationPropType.getDuration()+" did not match the total interval Duration "+totalIntervalDuration);
					 return false;
				 }	 
					 
			 }
			 
			 
			 boolean isAtleastOneDtstartPresentInInterval = isAtleastOneDtstartPresentInInterval(intervalList);
			 if(!isAtleastOneDtstartPresentInInterval && intervalList!=null && intervalList.size()>1){
				 if(!isAllIntervalHasDuration(intervalList)){
						LogHelper.addTrace("Conformance check failed : dtstart is not specified and there is more than one interval, hence duration must be specified. ");
						return false;					 
				 }
			 }
			 
			 
		}
		
	
		return true;
	}

	public static void main_(String []args) throws FileNotFoundException, UnsupportedEncodingException, JAXBException{
		TestSession.setServiceType(ServiceType.VEN);
			
		
		UpdateReportConformanceValidationHelper updateReortConf = new UpdateReportConformanceValidationHelper();
		
		OadrCreateReportType oadrCreateReportType = new DefaultResponseCreateReportRequest_001Action(ServiceType.VTN).getOadrCreateReportResponse();
		
		DefaultResponseUpdateReport_002TypeAckAction responseUpdateReportAckAction = new DefaultResponseUpdateReport_002TypeAckAction(ServiceType.VEN,true,oadrCreateReportType);
		
		//loadOadrUpdateReport_Update001(ServiceType serivceType,boolean isCreaterOfRegisterReport,OadrCreateReportType oadrCreateReportType)
		//OadrUpdateReportType updateReportType =
		//responseUpdateReportAckAction.loadOadrUpdateReport_Update001(ServiceType.VEN,true,oadrCreateReportType);
		OadrUpdateReportType updateReport= UpdateReportEventHelper.loadOadrUpdateReport_Update001(ServiceType.VEN,true,oadrCreateReportType);
		
		// Populating UID
		List<OadrReportType> oadrReportList = updateReport.getOadrReport();
		for(OadrReportType eachOadrReportType : oadrReportList){
			Intervals intervals = eachOadrReportType.getIntervals();
			List<IntervalType> intervalTypeList = intervals.getInterval();
			for(IntervalType eachIntervalType:intervalTypeList){
				//DatatypeFactory dataTypeFactory;
				Uid eachUid = new Uid();
				eachUid.setText("1");
				
				eachIntervalType.setUid(eachUid);
				
				//Uid uid=
				//Uid eachUid = eachIntervalType.getUid();
				//ObjectFactory objectFactory=new ObjectFactory()
				//objectFactory.createRefID("0");
			}
		}
		
		
		boolean isUIDValid = updateReortConf.isUIDValid(updateReport);
		
		System.out.println(isUIDValid);
	}
	
	
	public static boolean isVenIDValid(OadrUpdateReportType updateReportType,Direction direction) {
		
		if(!OadrUtil.isVENIDValid(updateReportType.getVenID(),direction)){
			return false;
		}

		return true;
	}
	
	
/*	public static boolean isOnePayloadFloatPresentInEachInterval(OadrUpdateReportType updateReportType){
		
		List<OadrReportType> reportList = updateReportType.getOadrReport();
		
		for(OadrReportType eachOadrReport:reportList){

			Intervals intervals = eachOadrReport.getIntervals();
			List<IntervalType> intervalList = null;
			if(intervals!=null){
				intervalList = intervals.getInterval();				
			}

			boolean isOnePayloadFloatElementPresent = OadrUtil.isOnePayloadFloatElementPresent(intervalList);
				if(!isOnePayloadFloatElementPresent){
					return false;
				}
		}
		
		return true;

	}*/
	
/*	public static boolean isDeviceClassValuesPresent(OadrUpdateReportType updateReportType){
		
		List<OadrReportType> reportList = updateReportType.getOadrReport();
		
		for(OadrReportType eachOadrReport:reportList){
			List<OadrReportDescriptionType>  oadrReportDescriptionList = eachOadrReport.getOadrReportDescription();
			for(OadrReportDescriptionType eachOadrReportDescriptionType:oadrReportDescriptionList){
				EiTargetType targetType =  eachOadrReportDescriptionType.getReportSubject();
				if(targetType==null || targetType.getEndDeviceAsset()==null) return true;
				
				if(!OadrUtil.isDeviceClassFound(targetType.getEndDeviceAsset())){
					return false;
				}
			
			}
		}
		
		return true;
	}
*/
	
	public static boolean isLoadControlStateConsistent(OadrUpdateReportType updateReportType){
		//Enoch
		List<OadrReportType> reportList = updateReportType.getOadrReport();
		
		for(OadrReportType eachOadrReport:reportList){
			
			//eachOadrReport.getIntervals().getInterval().get(0).g
			
			List<OadrReportDescriptionType>  oadrReportDescriptionList = eachOadrReport.getOadrReportDescription();
			for(OadrReportDescriptionType eachOadrReportDescriptionType:oadrReportDescriptionList){
				
				
				
				EiTargetType targetType =  eachOadrReportDescriptionType.getReportSubject();
				if(targetType==null || targetType.getEndDeviceAsset()==null) return true;
				
				if(!OadrUtil.isDeviceClassFound(targetType.getEndDeviceAsset())){
					return false;
				}
			
			}
		}
		
		return true;
	}

	public static boolean isLoadControlStateConsistentForAllIntervals(OadrUpdateReportType oadrUpdateReportType, Direction direction){

		
		List<OadrReportType> reportList = oadrUpdateReportType.getOadrReport();
		XMLDBUtil xmlDBUtil=new XMLDBUtil();
	
		for(OadrReportType eachOadrReport:reportList){
			
			
			String reportSpecifierID = eachOadrReport.getReportSpecifierID();
			System.out.println("");
			String reportName = xmlDBUtil.getUpdateReport_ReportName(reportSpecifierID, direction);
			if(reportName==null || reportName.trim().length()<1){
				
				LogHelper.addTrace("Conformance check failed : ReportName not found for the ReportSpecifierID "+reportSpecifierID);
		
				return false;
			}

			if(!reportName.equals(ReportNameEnumeratedType.TELEMETRY_STATUS.toString())) continue;
			
			
			boolean isAtleastOneCapacityFound = false;
			boolean isAtleastOneLevelOffsetFound = false;
			boolean isAtleastOnePercentOffsetFound = false;
			boolean isAtleastOneSetPointFound = false;
			
			
			List<IntervalType> intervalTypeList = eachOadrReport.getIntervals().getInterval();
			for(IntervalType intervalType:intervalTypeList){
				List<JAXBElement<? extends StreamPayloadBaseType>>   streamsList = intervalType.getStreamPayloadBase();
				
				for(JAXBElement<? extends StreamPayloadBaseType> eachStreamPayloadBaseType:streamsList){
					if(eachStreamPayloadBaseType.getValue().getClass()==OadrReportPayloadType.class){
						OadrReportPayloadType oadrReportPayloadType = (OadrReportPayloadType)eachStreamPayloadBaseType.getValue();
						JAXBElement<? extends PayloadBaseType>  payloadBase = oadrReportPayloadType.getPayloadBase();
						if(payloadBase!=null && payloadBase.getValue().getClass()==OadrPayloadResourceStatusType.class){
							OadrPayloadResourceStatusType oadrPayloadResourceStatus = (OadrPayloadResourceStatusType)payloadBase.getValue();
							OadrLoadControlStateType oadrLoadControlStateType  = oadrPayloadResourceStatus.getOadrLoadControlState();
							if(oadrLoadControlStateType!=null){
								if(oadrLoadControlStateType.getOadrCapacity()!=null){
									isAtleastOneCapacityFound=true;
								}
								
								if(oadrLoadControlStateType.getOadrLevelOffset()!=null){
									isAtleastOneLevelOffsetFound=true;
								}
								
								if(oadrLoadControlStateType.getOadrPercentOffset()!=null){
									isAtleastOnePercentOffsetFound=true;
								}
								
								if(oadrLoadControlStateType.getOadrSetPoint()!=null){
									isAtleastOneSetPointFound=true;
								}
								
							}
							
						}
					}
				}
				
			}
			
			if(!isLoadControlStateConsistentForAllIntervals(eachOadrReport, isAtleastOneCapacityFound, isAtleastOneLevelOffsetFound, isAtleastOnePercentOffsetFound, isAtleastOneSetPointFound)){
				return false;
			}
			
		}
	
		return true;
	}
	
	private static boolean isLoadControlStateConsistentForAllIntervals(OadrReportType eachOadrReport, boolean isAtleastOneCapacityFound, boolean isAtleastOneLevelOffsetFound, boolean isAtleastOnePercentOffsetFound, boolean isAtleastOneSetPointFound){
		
		List<IntervalType> intervalTypeList = eachOadrReport.getIntervals().getInterval();

		
		for(IntervalType intervalType:intervalTypeList){
			List<JAXBElement<? extends StreamPayloadBaseType>>   streamsList = intervalType.getStreamPayloadBase();
			
			for(JAXBElement<? extends StreamPayloadBaseType> eachStreamPayloadBaseType:streamsList){
				if(eachStreamPayloadBaseType.getValue().getClass()==OadrReportPayloadType.class){
					OadrReportPayloadType oadrReportPayloadType = (OadrReportPayloadType)eachStreamPayloadBaseType.getValue();
					JAXBElement<? extends PayloadBaseType>  payloadBase = oadrReportPayloadType.getPayloadBase();
					if(payloadBase!=null && payloadBase.getValue().getClass()==OadrPayloadResourceStatusType.class){
						OadrPayloadResourceStatusType oadrPayloadResourceStatus = (OadrPayloadResourceStatusType)payloadBase.getValue();
						OadrLoadControlStateType oadrLoadControlStateType  = oadrPayloadResourceStatus.getOadrLoadControlState();
						
						if((isAtleastOneCapacityFound || isAtleastOneLevelOffsetFound || isAtleastOnePercentOffsetFound || isAtleastOneSetPointFound) && oadrLoadControlStateType==null){
							LogHelper.addTrace("Conformance Validation Error : OadrLoadControlState is not consistently found in all Interval for the Report with ReportRequestID : "+eachOadrReport.getReportRequestID());
							return false;
						}
						
						if(isAtleastOneCapacityFound){
							if(oadrLoadControlStateType.getOadrCapacity()==null){
								LogHelper.addTrace("Conformance Validation Error : OadrCapacity > OadrCapacity is not consistently found in all Interval for the Report with ReportRequestID : "+eachOadrReport.getReportRequestID());
								return false;								
							}
						}
						
						if(isAtleastOneLevelOffsetFound){
							if(oadrLoadControlStateType.getOadrLevelOffset()==null){
									LogHelper.addTrace("Conformance Validation Error : OadrLoadControlState > OadrLevelOffset is not consistently found in all Interval for the Report with ReportRequestID : "+eachOadrReport.getReportRequestID());
									return false;								
							}
							
						}
						
						if(isAtleastOnePercentOffsetFound){
							if(oadrLoadControlStateType.getOadrPercentOffset()==null){
								LogHelper.addTrace("Conformance Validation Error : OadrLoadControlState > OadrPercentOffset is not consistently found in all Interval for the Report with ReportRequestID : "+eachOadrReport.getReportRequestID());
								return false;								
							}
						}
						
						if(isAtleastOneSetPointFound){
							if(oadrLoadControlStateType.getOadrSetPoint()==null){
								LogHelper.addTrace("Conformance Validation Error : OadrLoadControlState > OadrSetPoint is not consistently found in all Interval for the Report with ReportRequestID : "+eachOadrReport.getReportRequestID());
								return false;								
							}
						}
						
						
					}
				}
			}
			
		}		
		
		return true;
	}

	private static HashMap<String, String> getReportSpecifierIDRIDFromCreateReportIfReportBackDurationIsZero(String reportRequestIDInUpdateReport,Direction direction){

		HashMap<String, String> rptSpecifierIDAndRID = null;
		Duration zeroDuration = OadrUtil.createDuration("PT0M");
		ArrayList<OadrCreateReportType>  createEventList = null;
		if(direction.equals(Direction.Send)){
			createEventList = TestSession.getOadrCreateReportTypeReceivedList();
		}else if(direction.equals(Direction.Receive)){
			createEventList = TestSession.getOadrCreateReportTypeSentList();	
		}else{
			LogHelper
			.addTraceAndConsole("Conformance Validation Error : Unable to determin the Direction");
			System.exit(-1);
		}
		
		OadrCreateReportType oadrCreateReportType = createEventList.get(createEventList.size()-1);
		
		List<OadrReportRequestType>  reportRequestList = oadrCreateReportType.getOadrReportRequest();
		for(OadrReportRequestType eachOadrReportRequestType:reportRequestList){

			String createReportReportReqID = eachOadrReportRequestType.getReportRequestID();
			if(createReportReportReqID==null || reportRequestIDInUpdateReport==null) continue;
			
			if(!createReportReportReqID.equals(reportRequestIDInUpdateReport)) continue;
			
			List<SpecifierPayloadType>  specifierPayloadList = eachOadrReportRequestType.getReportSpecifier().getSpecifierPayload();
			for(SpecifierPayloadType specifierPayloadType:specifierPayloadList){

				
				String ridInCreateReport = specifierPayloadType.getRID();
				//if(ridInCreateReport.equals(ridInUpdateReport)){
					
					if(eachOadrReportRequestType.getReportSpecifier()!=null && eachOadrReportRequestType.getReportSpecifier().getReportBackDuration()!=null && eachOadrReportRequestType.getReportSpecifier().getReportBackDuration().getDuration()!=null){
						String durationAsString = eachOadrReportRequestType.getReportSpecifier().getReportBackDuration().getDuration();	
						Duration duration = OadrUtil.createDuration(durationAsString);
						if(duration.equals(zeroDuration)){
							rptSpecifierIDAndRID = new HashMap<String, String>();
							rptSpecifierIDAndRID.put("ReportSpecifierID", eachOadrReportRequestType.getReportSpecifier().getReportSpecifierID());
							rptSpecifierIDAndRID.put("RID", ridInCreateReport);
							
							return rptSpecifierIDAndRID;
						}
					}
				//}
			}
		}
		
		return rptSpecifierIDAndRID;
	}
	
	public static boolean isTelemetryUsageReportIntervalDurationPresent(
			OadrUpdateReportType oadrUpdateReportType, Direction direction) {

		ArrayList<OadrCreateReportType>  createEventList = null;
		if(direction.equals(Direction.Send)){
			createEventList = TestSession.getOadrCreateReportTypeReceivedList();
		}else if(direction.equals(Direction.Receive)){
			createEventList = TestSession.getOadrCreateReportTypeSentList();	
		}else{
			LogHelper
			.addTraceAndConsole("Conformance Validation Error : Unable to determin the Direction");
			return false;
		}
		
		if(createEventList==null){
			LogHelper
			.addTraceAndConsole("Conformance Validation Error : No OadrCreateReport found to match with OadrUpdateReport");
			return false;
		}

		XMLDBUtil xmlDBUtil = new XMLDBUtil();

		List<OadrReportType> reportList = oadrUpdateReportType.getOadrReport();
		
		
		for(OadrReportType eachOadrReport:reportList){
			if(!eachOadrReport.getReportName().equals(ReportNameEnumeratedType.TELEMETRY_USAGE.toString())) continue;
			
			String reportRequestIDInUpdateReport = eachOadrReport.getReportRequestID();
			
				HashMap<String,String>  reportSpecifierIDAndRID = getReportSpecifierIDRIDFromCreateReportIfReportBackDurationIsZero(reportRequestIDInUpdateReport,direction);
				
				if(reportSpecifierIDAndRID!=null){
					
					String reportSpecifierIDInCreateReport=reportSpecifierIDAndRID.get("ReportSpecifierID");
					String ridInCreateReport=reportSpecifierIDAndRID.get("RID");
					Direction directionForDB=Direction.Receive;
					if(direction.equals(Direction.Receive)){
						directionForDB=Direction.Send;
					}
					
					String strMinPeriod = xmlDBUtil.getMinPeriod(reportSpecifierIDInCreateReport, ridInCreateReport, directionForDB);
					String strMaxPeriod = xmlDBUtil.getMaxPeriod(reportSpecifierIDInCreateReport, ridInCreateReport, directionForDB);

					if(strMinPeriod==null || strMinPeriod.trim().length()<1 ||strMaxPeriod==null || strMaxPeriod.trim().length()<1)continue;
					
					Duration minPeriod = OadrUtil.createDuration(strMinPeriod);
					Duration maxPeriod = OadrUtil.createDuration(strMaxPeriod);
					if(!minPeriod.equals(maxPeriod)){
						boolean isDurationPresentInInterval = isDurationPresentInInterval(eachOadrReport.getIntervals());
						if(!isDurationPresentInInterval)
							return false;
						}
				}
				
		}
		

		return true;
	}
		
	private static boolean isDurationPresentInInterval(Intervals intervals){

		List<IntervalType> intervalList = intervals.getInterval();
		for(IntervalType intervalType:intervalList){
			if(intervalType.getDuration()==null || intervalType.getDuration().getDuration()==null){
				LogHelper
				.addTraceAndConsole("Conformance Validation Error : intervals.interval.duration not present");
				return false;
			}
		}
		return true;
	}
	public static void main(String []args) throws FileNotFoundException, UnsupportedEncodingException, JAXBException{
		
		OadrUpdateReportType oadrUpdateReportType  = UpdateReportEventHelper.loadOadrUpdateReport("oadrUpdateReport_Telemetry_Status.xml");
		List<OadrReportType> reportList = oadrUpdateReportType.getOadrReport();
		
	
		for(OadrReportType eachOadrReport:reportList){
			
			boolean isAtleastOneCapacityFound = false;
			boolean isAtleastOneLevelOffsetFound = false;
			boolean isAtleastOnePercentOffsetFound = false;
			boolean isAtleastOneSetPointFound = false;
			
			
			List<IntervalType> intervalTypeList = eachOadrReport.getIntervals().getInterval();
			for(IntervalType intervalType:intervalTypeList){
				//intervalType.getStreamPayloadBase().get(0).getValue();
				List<JAXBElement<? extends StreamPayloadBaseType>>   streamsList = intervalType.getStreamPayloadBase();
				
				for(JAXBElement<? extends StreamPayloadBaseType> eachStreamPayloadBaseType:streamsList){
					if(eachStreamPayloadBaseType.getValue().getClass()==OadrReportPayloadType.class){
						OadrReportPayloadType oadrReportPayloadType = (OadrReportPayloadType)eachStreamPayloadBaseType.getValue();
						JAXBElement<? extends PayloadBaseType>  payloadBase = oadrReportPayloadType.getPayloadBase();
						if(payloadBase!=null && payloadBase.getValue().getClass()==OadrPayloadResourceStatusType.class){
							OadrPayloadResourceStatusType oadrPayloadResourceStatus = (OadrPayloadResourceStatusType)payloadBase.getValue();
							OadrLoadControlStateType oadrLoadControlStateType  = oadrPayloadResourceStatus.getOadrLoadControlState();
							if(oadrLoadControlStateType!=null){
								if(oadrLoadControlStateType.getOadrCapacity()!=null){
									isAtleastOneCapacityFound=true;
								}
								
								if(oadrLoadControlStateType.getOadrLevelOffset()!=null){
									isAtleastOneLevelOffsetFound=true;
								}
								
								if(oadrLoadControlStateType.getOadrPercentOffset()!=null){
									isAtleastOnePercentOffsetFound=true;
								}
								
								if(oadrLoadControlStateType.getOadrSetPoint()!=null){
									isAtleastOneSetPointFound=true;
								}
								
							}
							
						}
					}
				}
				
			}
			
		}
	}

}