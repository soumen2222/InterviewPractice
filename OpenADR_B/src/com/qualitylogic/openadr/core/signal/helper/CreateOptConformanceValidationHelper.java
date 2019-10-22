package com.qualitylogic.openadr.core.signal.helper;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import org.restlet.Component;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.DefaultOadrCreateOptEvent;
import com.qualitylogic.openadr.core.action.impl.DefaultOadrCreatedOptEvent;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventResultOptINOptOut;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.EiTargetType;
import com.qualitylogic.openadr.core.signal.EndDeviceAssetType;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrCreatedOptType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.xcal.ArrayOfVavailabilityContainedComponents;
import com.qualitylogic.openadr.core.signal.xcal.AvailableType;
import com.qualitylogic.openadr.core.signal.xcal.Dtstart;
import com.qualitylogic.openadr.core.signal.xcal.DurationPropType;
import com.qualitylogic.openadr.core.signal.xcal.Properties;
import com.qualitylogic.openadr.core.signal.xcal.Properties.Tolerance;
import com.qualitylogic.openadr.core.signal.xcal.Properties.Tolerance.Tolerate;
import com.qualitylogic.openadr.core.signal.xcal.VavailabilityType;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class CreateOptConformanceValidationHelper {

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

	public static boolean isAtLeastOneEiTargetMatch(
			OadrCreateOptType oadrCreateOptType) {
			EiTargetType eiTarget = oadrCreateOptType.getEiTarget();
			return OadrUtil.isAtLeastOneEiTargetMatch(eiTarget);
	}

	
	public static boolean isMarketContextValid(
			OadrCreateOptType oadrCreateOptType) {
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

		String pDR_MarketContext_1_Name = propertiesFileReader
				.get("DR_MarketContext_1_Name");
		String pDR_MarketContext_2_Name = propertiesFileReader
				.get("DR_MarketContext_2_Name");

		String marketContexInPayload = oadrCreateOptType.getMarketContext();
		if(OadrUtil.isEmpty(marketContexInPayload)){
			return true;
		}
		
		if (!marketContexInPayload.equalsIgnoreCase(pDR_MarketContext_1_Name)
				&& !marketContexInPayload.equalsIgnoreCase(pDR_MarketContext_2_Name)) {

			return false;
		}
		
		return true;
	}
	
	

	public static boolean isOptIDPresent(OadrCreateOptType oadrCreateOptType){
		
		if(OadrUtil.isEmpty(oadrCreateOptType.getOptID())){
			return false;
		}
		
		return true;
	}
	
	public static boolean isEventIDValid(OadrCreateOptType oadrCreateOptType) {
		
		if(TestSession.getServiceType()!=ServiceType.VTN) return true;
		VavailabilityType  vavailabilityType  = oadrCreateOptType.getVavailability();
		
		if(vavailabilityType!= null) return true;

		if(oadrCreateOptType.getQualifiedEventID()==null || oadrCreateOptType.getQualifiedEventID().getEventID()==null){
			
			LogHelper.addTrace("EventID is not present");
			return false;
		}
		
		String eventID = oadrCreateOptType.getQualifiedEventID().getEventID();
		
		
		ArrayList<IDistributeEventAction> distributeEventActionSentList = DistributeEventActionList
				.getDistributeEventActionCompletedList();
		ArrayList<OadrDistributeEventType> oadrDistributeEventList = new ArrayList<OadrDistributeEventType>();
		for (IDistributeEventAction eachDistributeEventAction : distributeEventActionSentList) {
			oadrDistributeEventList.add(eachDistributeEventAction
					.getDistributeEvent());
		}

		ArrayList<String> expectedEventIDList = DistributeEventSignalHelper
				.getEventIDs(oadrDistributeEventList);

			if (!OadrUtil.isIDFoundInList(eventID,
					expectedEventIDList)) {
				LogHelper
						.addTrace("\nConformance Validation Error : Unable to map the received EventID "
								+ eventID
								+ " to any Distribute Event\n");

				return false;
			}
		
		return true;

	}

	public static boolean isVenIDValid(OadrCreateOptType oadrCreateOptType, Direction direction) {
		
		if(!OadrUtil.isVENIDValid(oadrCreateOptType.getVenID(),direction)){
			return false;
		}

		return true;
	}
	
	public static boolean isDeviceClassValuesPresent(OadrCreateOptType oadrCreateOptType){
		EiTargetType targetType =  oadrCreateOptType.getOadrDeviceClass();
		
		if(targetType==null || targetType.getEndDeviceAsset()==null) return true;
		
		if(!OadrUtil.isDeviceClassFound(targetType.getEndDeviceAsset())){
			return false;
		}

		return true;
	}
	
	public static boolean isVavailabilityExcludedPropertiesNotPresent(OadrCreateOptType oadrCreateOptType){
		 VavailabilityType  vAvailabilityType = oadrCreateOptType.getVavailability();
		if(vAvailabilityType==null || vAvailabilityType.getComponents()==null || vAvailabilityType.getComponents().getAvailable()==null) return true;
			
		List<AvailableType> availableList = vAvailabilityType.getComponents().getAvailable();
		for(AvailableType eachAvailableType:availableList){
			 Properties properties = eachAvailableType.getProperties();
			 if(properties==null) continue;
			 Tolerance tolerance = properties.getTolerance();
			 DurationPropType  durationXEiRampUp  = properties.getXEiRampUp();
			 DurationPropType  durationXEiRecovery  = properties.getXEiRecovery();
			 DurationPropType  durationXEiNotification  = properties.getXEiNotification();
			 
			 if(tolerance!=null || durationXEiRampUp!=null || durationXEiRecovery!=null || durationXEiNotification!=null){
					String errString = "";
					if(tolerance!=null){
						errString = "Tolerance";
					}
					
					if(durationXEiRampUp!=null){
						if(errString.length()>0){
							errString = errString+", ";
						}
						errString = "XEiRampUp";
					}
					
					if(durationXEiRecovery!=null){
						if(errString.length()>0){
							errString = errString+", ";
						}
						errString = "XEiRecovery";
					}
					
					if(durationXEiNotification!=null){
						if(errString.length()>0){
							errString = errString+", ";
						}
						errString = "XEiNotification";
					}
					
				 LogHelper
					.addTrace("Conformance Validation Error : "+errString+" was not expected");
					
				 return false;
			 }

		}
		return true;
	}
	
	public static boolean isVavailabilityMarketContextNotPresentIfEventIDPresent(OadrCreateOptType oadrCreateOptType){
		boolean isEventIDPresent = false;
		if(oadrCreateOptType.getQualifiedEventID()!=null && oadrCreateOptType.getQualifiedEventID().getEventID().trim().length()>0){
			isEventIDPresent=true;
		}
		
		 VavailabilityType  vAvailabilityType = oadrCreateOptType.getVavailability();
		 String marketContext = oadrCreateOptType.getMarketContext();
		 
		if(isEventIDPresent){
			if(vAvailabilityType!=null){
				 LogHelper
					.addTrace("Conformance Validation Error : VavailabilityType was not expected as QualifiedEventID has been specified");
					
				 return false;
				
			}
			
			if(marketContext!=null){
				LogHelper
				.addTrace("Conformance Validation Error : MarketContext was not expected as QualifiedEventID has been specified");
				
			 return false;
			}
			
		}else{
			if(vAvailabilityType==null){
				 LogHelper
					.addTrace("Conformance Validation Error : VavailabilityType was expected as QualifiedEventID has not been specified");
					
				 return false;
				
			}
		}
		return true;
	}
	
	public static void main_(String []args) throws FileNotFoundException, UnsupportedEncodingException, JAXBException{
	
		//OadrCreateOptType oadrCreateOptType = CreateOptEventHelper.loadOadrCreateEvent("oadrCreateOpt_Event.xml");
		
		DefaultOadrCreateOptEvent oadrCreateOptTypeDef = new DefaultOadrCreateOptEvent();
		//isDeviceClassValuesPresent(oadrCreateOptType);
		
		//oadrCreateOptType = oadrCreateOptTypeDef.getOadrCreateOptEventOptSchedule_001(optInOut, reason, marketContext, eventID, modificationNumber, resourceIDs, endDeviceAsset, pAvailabilityList, serviceType)
		
	}
	public static void main(String []args){
		
		OptTypeType optType=OptTypeType.OPT_IN;
		String VENID=new PropertiesFileReader().getVenID();
		ArrayList<String> resourceList = new ArrayList<String>();
		resourceList.add("Res1");
		resourceList.add("Res2");
		////////////////////////////////
		
		String optID="Opt_1234";
		OptTypeType optInOut=OptTypeType.OPT_IN;
		String reason = "participating";
		String marketContext="Test marketcontext";
		//String venID = "VEN_3214";
		String reqID = "REQ_12343";
		String eventID = "Event_12345";
		long modificationNumber = 1;
		List<String> resourceIDs =new ArrayList<String>();
			resourceIDs.add("RES_123534");
			
		List<String> endDeviceAsset =new ArrayList<String>();
			endDeviceAsset.add("Exterior_Lighting");


			List<AvailableType> pAvailabilityList =new ArrayList<AvailableType>();
			
			
				AvailableType eachAvailable = new AvailableType();
					Dtstart durationStartTime=new Dtstart();
						durationStartTime.setDateTime(OadrUtil.getCurrentTime());
				
					pAvailabilityList.add(eachAvailable);
					eachAvailable.setProperties(new Properties());
					Properties eachAvailableProperties =  eachAvailable.getProperties();
								eachAvailableProperties.setDtstart(durationStartTime);
								//OadrUtil.createDuration("PT5M");
								DurationPropType durationProp=new DurationPropType();
								durationProp.setDuration("PT5M");
								eachAvailableProperties.setDuration(durationProp);
								
								Tolerance tolerance = new Tolerance();

								Tolerate tolerate = new Tolerate();
								tolerate.setStartafter("PT3M");
								tolerance.setTolerate(tolerate);
								
								eachAvailableProperties.setTolerance(tolerance);
									
									DurationPropType durationPropType = new DurationPropType();
										durationPropType.setDuration("PT3M");
										eachAvailableProperties.setXEiNotification(durationPropType);
										
										OadrCreateOptType oadrCreateOpt= new DefaultOadrCreateOptEvent().getOadrCreateOptEvent(optInOut,reason,marketContext,eventID,modificationNumber,resourceIDs,endDeviceAsset,pAvailabilityList);
										 //VavailabilityType  vAvailabilityType = oadrCreateOpt.getVavailability();
										VavailabilityType  vAvailabilityType = new VavailabilityType();
										
										oadrCreateOpt.setVavailability(vAvailabilityType);
										ArrayOfVavailabilityContainedComponents component=new ArrayOfVavailabilityContainedComponents();
										vAvailabilityType.setComponents(component);
										List<AvailableType>  availaabiliyList =  component.getAvailable();
										
										
										//List<AvailableType>  availaabiliyList = new ArrayList<AvailableType>();
										
										AvailableType availableType1=new AvailableType();
										availaabiliyList.add(availableType1);

										Properties properties=new Properties();
										
										DurationPropType notification = new DurationPropType();
										notification.setDuration("PT1M");
										
										properties.setXEiNotification(notification);
										availableType1.setProperties(properties);
										
										 VavailabilityType  vAvailabilityTypeCheck = oadrCreateOpt.getVavailability();
										
										
										
										
										
		String mrkt = new PropertiesFileReader().get("DR_MarketContext_1_Name");
		oadrCreateOpt.setMarketContext(mrkt);
		//boolean isMarketContextValid = isMarketContextValid(oadrCreateOpt);
		//isVavailabilityExcludedPropertiesNotPresent(oadrCreateOpt);
		isVavailabilityMarketContextNotPresentIfEventIDPresent(oadrCreateOpt);
		
		//System.out.println(isMarketContextValid);
	}


	public static String isCreatedDateTimeWithinOneMin(
			OadrCreateOptType oadrCreateOptType)
			throws DatatypeConfigurationException {
		XMLGregorianCalendar currentDateTimeStart = OadrUtil.getCurrentTime();
		String currentTime = currentDateTimeStart.toString();

		boolean isCurrentDateTimeWithinWithinOneMin = OadrUtil.isCurrentDateTimeWithinOneMin(oadrCreateOptType.getCreatedDateTime(),currentDateTimeStart);
		
		if (!isCurrentDateTimeWithinWithinOneMin) {
			return currentTime;
		}

		return "";
	}
 
}