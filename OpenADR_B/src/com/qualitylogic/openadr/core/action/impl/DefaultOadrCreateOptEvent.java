package com.qualitylogic.openadr.core.action.impl;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.signal.EiTargetType;
import com.qualitylogic.openadr.core.signal.EndDeviceAssetType;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.QualifiedEventIDType;
import com.qualitylogic.openadr.core.signal.helper.CreateOptEventHelper;
import com.qualitylogic.openadr.core.signal.xcal.ArrayOfVavailabilityContainedComponents;
import com.qualitylogic.openadr.core.signal.xcal.AvailableType;
import com.qualitylogic.openadr.core.signal.xcal.VavailabilityType;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class DefaultOadrCreateOptEvent {


	private boolean isEventCompleted;
	private String responseCode = "200";

	// Use this Constructor if you want to set specific response code other than
	// 200.
	public DefaultOadrCreateOptEvent(String responseCode) {
		this.responseCode = responseCode;
	}

	// Default Constructor sets response code to 200.
	public DefaultOadrCreateOptEvent(){
	}

	public boolean isPreConditionsMet() {
		return true;
	}

	public boolean isEventCompleted() {
		return isEventCompleted;
	}

	public void setEventCompleted(boolean isEventCompleted) {
		this.isEventCompleted = isEventCompleted;
	}

	public OadrCreateOptType getOadrCreateOptEvent(OptTypeType optInOut,String reason, String marketContext,List<String> resourceIDs,List<String> endDeviceAsset,List<AvailableType> pAvailabilityList) {
		String eventID = null;	
		int modificationNumber = -1;
		
		return getOadrCreateOptEvent(optInOut,reason,marketContext,eventID,modificationNumber,resourceIDs,endDeviceAsset,pAvailabilityList);
	}

	public OadrCreateOptType getOadrCreateOptEventOptSchedule_001(OptTypeType optInOut,String reason, String marketContext,String eventID,long modificationNumber,List<String> resourceIDs,List<String> endDeviceAsset,List<AvailableType> pAvailabilityList,ServiceType serviceType) {

		String optID = OadrUtil.createUniqueOadrCreateOptID();
		String reqID = OadrUtil.createUniqueOadrCreateOptReqID();
		OadrCreateOptType oadrCreateOptType = null;
		try {
			oadrCreateOptType = CreateOptEventHelper.loadOadrCreateEvent("OptSchedule_001.xml");

			XMLGregorianCalendar createdDateTime =OadrUtil.getCurrentTime();
			
			oadrCreateOptType.setOptID(optID);
			oadrCreateOptType.setOptType(optInOut);
			oadrCreateOptType.setOptReason(reason);
			if(eventID==null || eventID.trim().length()<1){
							oadrCreateOptType.setMarketContext(marketContext);
			
				VavailabilityType vavAilability = oadrCreateOptType.getVavailability();
				if(vavAilability!=null){
					ArrayOfVavailabilityContainedComponents  availability=vavAilability.getComponents();	
					List<AvailableType> availabilityList = availability.getAvailable();
					availabilityList.clear();
					
					if(pAvailabilityList!=null && pAvailabilityList.size()>0){
						availabilityList.addAll(pAvailabilityList);					
					}
													
				}
				
			
			}else{
				
				oadrCreateOptType.setMarketContext(null);
				oadrCreateOptType.setVavailability(null);
				
				oadrCreateOptType.setQualifiedEventID(new QualifiedEventIDType());
				oadrCreateOptType.getQualifiedEventID().setEventID(eventID);
				oadrCreateOptType.getQualifiedEventID().setModificationNumber(modificationNumber);
					
			}
			
			oadrCreateOptType.setVenID(new PropertiesFileReader().getVenID());
			
			oadrCreateOptType.setCreatedDateTime(createdDateTime);
			oadrCreateOptType.setRequestID(reqID);
			
			
			EiTargetType resourceTarget = oadrCreateOptType.getEiTarget();
				List<String> resourceIDList = resourceTarget.getResourceID();
				resourceIDList.clear();
				resourceIDList.addAll(resourceIDs);
		/*	
			EiTargetType deviceTarget = oadrCreateOptType.getOadrDeviceClass();
				 List<EndDeviceAssetType> endDeviceAssetList = deviceTarget.getEndDeviceAsset();
				 endDeviceAssetList.clear();
				 
			 	
			 	if(endDeviceAsset!=null){
			 		for(String eachEndDeviceAssetStr:endDeviceAsset){
			 			 EndDeviceAssetType eachEndDeviceAssetType = new EndDeviceAssetType();
					 	eachEndDeviceAssetType.setMrid(eachEndDeviceAssetStr);
			 			endDeviceAssetList.add(eachEndDeviceAssetType);			 		

			 		}
			 	}
			 	*/
				oadrCreateOptType.getEiTarget().getVenID().clear();
			 	oadrCreateOptType.getEiTarget().getVenID().add(new PropertiesFileReader().getVenID());
			 	
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrCreateOptType;
	}

	public OadrCreateOptType getOadrCreateOptEvent(OptTypeType optInOut,String reason, String marketContext,String eventID,long modificationNumber,List<String> resourceIDs,List<String> endDeviceAsset,List<AvailableType> pAvailabilityList) {

		String optID = OadrUtil.createUniqueOadrCreateOptID();
		String reqID = OadrUtil.createUniqueOadrCreateOptReqID();
		OadrCreateOptType oadrCreateOptType = null;
		try {
			oadrCreateOptType = CreateOptEventHelper.loadOadrCreateEvent("oadrCreateOpt_Schedule.xml");

			XMLGregorianCalendar createdDateTime =OadrUtil.getCurrentTime();
			
			oadrCreateOptType.setOptID(optID);
			oadrCreateOptType.setOptType(optInOut);
			oadrCreateOptType.setOptReason(reason);
			if(eventID==null || eventID.trim().length()<1){
							oadrCreateOptType.setMarketContext(marketContext);
			
				VavailabilityType vavAilability = oadrCreateOptType.getVavailability();
				if(vavAilability!=null){
					ArrayOfVavailabilityContainedComponents  availability=vavAilability.getComponents();	
					List<AvailableType> availabilityList = availability.getAvailable();
					availabilityList.clear();
					
					if(pAvailabilityList!=null && pAvailabilityList.size()>0){
						availabilityList.addAll(pAvailabilityList);					
					}
													
				}
				
			
			}else{
				
				oadrCreateOptType.setMarketContext(null);
				oadrCreateOptType.setVavailability(null);
				
				oadrCreateOptType.setQualifiedEventID(new QualifiedEventIDType());
				oadrCreateOptType.getQualifiedEventID().setEventID(eventID);
				oadrCreateOptType.getQualifiedEventID().setModificationNumber(modificationNumber);
					
			}
			
			oadrCreateOptType.setVenID(new PropertiesFileReader().getVenID());
			
			oadrCreateOptType.setCreatedDateTime(createdDateTime);
			oadrCreateOptType.setRequestID(reqID);
			
			
			EiTargetType resourceTarget = oadrCreateOptType.getEiTarget();
				List<String> resourceIDList = resourceTarget.getResourceID();
				resourceIDList.clear();
				resourceIDList.addAll(resourceIDs);
			
			EiTargetType deviceTarget = oadrCreateOptType.getOadrDeviceClass();
			if (deviceTarget != null) {
				 List<EndDeviceAssetType> endDeviceAssetList = deviceTarget.getEndDeviceAsset();
				 endDeviceAssetList.clear();
				 
			 	
			 	if(endDeviceAsset!=null){
			 		for(String eachEndDeviceAssetStr:endDeviceAsset){
			 			 EndDeviceAssetType eachEndDeviceAssetType = new EndDeviceAssetType();
					 	eachEndDeviceAssetType.setMrid(eachEndDeviceAssetStr);
			 			endDeviceAssetList.add(eachEndDeviceAssetType);			 		

			 		}
			 	}
			}
			 	
			oadrCreateOptType.getEiTarget().getVenID().add(new PropertiesFileReader().getVenID());
			 	
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrCreateOptType;
	}
}