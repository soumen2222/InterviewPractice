package com.qualitylogic.openadr.core.base;

import com.qualitylogic.openadr.core.bean.ModeType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.XMLLogHelper;

public abstract class PULLBaseTestCase extends BaseTestCase{
	protected PULLBaseTestCase(){
		TestSession.setMode(ModeType.PULL);
		OadrUtil.setServiceType(this.getClass().getName());
		
		String transportType = new PropertiesFileReader().getTransportType();

		if(transportType.equalsIgnoreCase("XMPP")){

				TestSession.setValidationErrorFound(true);
				String errorMessage="********************PULL test cases are not applicable for XMPP********************";
				
				System.out.println(errorMessage);
				
				LogHelper.setResult(LogResult.NA);
				System.exit(-1);

		}
	}
	
}