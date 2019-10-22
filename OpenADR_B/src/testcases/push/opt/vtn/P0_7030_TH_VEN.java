package testcases.push.opt.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.signal.EiTargetType;
import com.qualitylogic.openadr.core.signal.EndDeviceAssetType;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class P0_7030_TH_VEN extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new P0_7030_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		
		OadrCreateOptType schedule001 = getCreateOptSchedule_001();
		
		EiTargetType venID = new EiTargetType();
		venID.getVenID().add(properties.getVenID());
		schedule001.setEiTarget(venID);
		
		EiTargetType device = new EiTargetType();
		String[] deviceClasses = properties.getDeviceClass();
		for (String deviceClass : deviceClasses) {
			EndDeviceAssetType endDeviceAsset = new EndDeviceAssetType();
			endDeviceAsset.setMrid(deviceClass);
			device.getEndDeviceAsset().add(endDeviceAsset);
		}
		schedule001.setOadrDeviceClass(device);
		
		sendCreateOpt(schedule001);
		
		OadrCreateOptType schedule002 = getCreateOptSchedule_002();
		schedule002.setOadrDeviceClass(device);
		sendCreateOpt(schedule002);
	}
}
