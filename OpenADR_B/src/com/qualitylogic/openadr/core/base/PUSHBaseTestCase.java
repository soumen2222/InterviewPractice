package com.qualitylogic.openadr.core.base;

import com.qualitylogic.openadr.core.bean.ModeType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.util.OadrUtil;

public abstract class PUSHBaseTestCase extends BaseTestCase{
	protected PUSHBaseTestCase(){
		TestSession.setMode(ModeType.PUSH);
		OadrUtil.setServiceType(this.getClass().getName());

	}
}