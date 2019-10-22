package com.qualitylogic.openadr.core.channel;

import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public final class BusinessLogic {
	private BusinessLogic() {
	}
	
	public static boolean isOadrPollAllowed() {
		boolean isOadrPollAllowed = true;
		
		PropertiesFileReader properties = new PropertiesFileReader();
		if (properties.isXmppTransportType()) {
			isOadrPollAllowed = false;
		}
		
		return isOadrPollAllowed;
	}
}
