package testcases.util;

import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.XMLDBUtil;

public class SelfTest_RegisterNoReports {
	public static void main(String[] args) {
		run();
	}

	public static void run() {
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt("This utility modifies the local database to show a registered state for the VEN and VTN to aide with registration self tests. This utility does NOT update report registration so report test cases will not work after runnig this routine.");
		
		if (!TestSession.isUserClickedContinuePlay()) {
			return;
		}
		
		PropertiesFileReader properties = new PropertiesFileReader();
		
		String venID = OadrUtil.createUniqueVENID();
		String registrationID = OadrUtil.createUniqueRegistrationID();
		String transportAddress = properties.getCreatePartyRegistrationTransportAddress();

		OadrUtil.setServiceType("_VTN");
		XMLDBUtil vtnDbXml = new XMLDBUtil();
		vtnDbXml.setRegistrationID(registrationID);
		if (!properties.isUseStaticVENID()) {
			vtnDbXml.setVENID(venID);
		}
		vtnDbXml.setTransportAddress(transportAddress);
		vtnDbXml.resetReports();

		OadrUtil.setServiceType("_VEN");
		XMLDBUtil venDbXml = new XMLDBUtil();
		venDbXml.setRegistrationID(registrationID);
		if (!properties.isUseStaticVENID()) {
			venDbXml.setVENID(venID);
		}
		venDbXml.setTransportAddress(transportAddress);
		venDbXml.resetReports();
		
		System.out.println("\nDatabase updated with device registration.");
	}
}
