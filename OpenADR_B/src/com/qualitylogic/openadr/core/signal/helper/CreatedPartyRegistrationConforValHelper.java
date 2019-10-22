package com.qualitylogic.openadr.core.signal.helper;

import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class CreatedPartyRegistrationConforValHelper {
	
	public static boolean isResponseCodeSuccess(
			OadrCreatedPartyRegistrationType oadrCreatedPartyRegistrationType) {
		return OadrUtil.isSuccessResponse(oadrCreatedPartyRegistrationType.getEiResponse());
	}

	public static boolean isVtnIDValid(OadrCreatedPartyRegistrationType oadrCreatedPartyRegistrationType) {

		if (oadrCreatedPartyRegistrationType == null)
			return false;
		String vtnIDInPayload = "";

		vtnIDInPayload = oadrCreatedPartyRegistrationType.getVtnID();

		PropertiesFileReader ctnPropertiesFileReader = new PropertiesFileReader();
		String vtnIDInConfig = ctnPropertiesFileReader.get("VTN_ID");
		String vtnIDInConfig2 = ctnPropertiesFileReader.get("VTN_ID_2");

		if (vtnIDInPayload.equals(vtnIDInConfig)
				|| vtnIDInPayload.equals(vtnIDInConfig2)) {
			return true;
		} else {
			LogHelper
					.addTrace("\nConformance Validation Error : Unknown VTN ID "
							+ vtnIDInPayload
							+ " has been received expected is "
							+ vtnIDInConfig
							+ "\n");

			return false;
		}

	}
	

	public static boolean isPollFreqSpecifiedForPULL(String data) {
		OadrCreatedPartyRegistrationType  oadrCreatePartyRegistration = CreatedPartyRegistrationSignalHelper.createCreatedPartyRegistrationTypeFromString(data);

		if(OadrUtil.isCreatePartyRegistration_PullModel()){
			
			if(oadrCreatePartyRegistration.getOadrRequestedOadrPollFreq()==null || oadrCreatePartyRegistration.getOadrRequestedOadrPollFreq().getDuration()==null){

				LogHelper.addTrace("Conformance check failed : OadrRequestedOadrPollFreq is not specified in the payload for PULL Model.");
	
				return false;
			}
			
		}
		return true;
	}

	
	public static boolean isVenIDValid(OadrCreatedPartyRegistrationType oadrCreatedPartyRegistrationType) {

		String venIDInPropertiesFile = new PropertiesFileReader().getVenID();
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

		if(!propertiesFileReader.isUseStaticVENID()){
			return true;
		}
		
		if (oadrCreatedPartyRegistrationType == null || oadrCreatedPartyRegistrationType.getVenID()==null)
			return false;
		String venIDInPayload = "";

		venIDInPayload = oadrCreatedPartyRegistrationType.getVenID();

		if (propertiesFileReader.getVenID().equals(venIDInPayload)) {
			return true;
		} else {
			LogHelper
					.addTrace("\nConformance Validation Error : Unknown VEN ID "
							+ venIDInPayload
							+ " has been received. Expected is "
							+ venIDInPropertiesFile
							+ "\n");

			return false;
		}

	}
}
