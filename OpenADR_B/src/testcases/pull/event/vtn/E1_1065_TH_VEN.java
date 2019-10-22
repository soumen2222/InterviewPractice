package testcases.pull.event.vtn;

import java.util.List;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public class E1_1065_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new E1_1065_TH_VEN());
	}

	@Override
	public void test() throws Exception {
				
		prompt(resources.prompt_010());

		OadrDistributeEventType distributeEvent = pollDistributeEvent();
		checkSignalNames(distributeEvent, "ELECTRICITY_PRICE");
		
		OadrEvent oadrEvent = distributeEvent.getOadrEvent().get(0);
		
		String resourceID = oadrEvent.getEiEvent().getEiTarget().getResourceID().get(1);
		OadrCreateOptType createOpt = getCreateOptOptIn(oadrEvent, resourceID);
		sendCreateOpt(createOpt);
		
		sendCreatedEvent(SchemaHelper.getDistributeEventAsString(distributeEvent), OptTypeType.OPT_OUT);

		boolean yesClicked = promptYes(resources.prompt_040());
		if (!yesClicked) {
			throw new FailedException("The VTN should show the VEN as still opt'd in.");
		}
	}


	private OadrDistributeEventType pollDistributeEvent() throws Exception {
		String distributeEvent = VenToVtnClient.poll(OadrDistributeEventType.class);
		if (!OadrUtil.isExpected(distributeEvent, OadrDistributeEventType.class)) {
			throw new FailedException("Expected OadrDistributeEvent has not been received");
		}
		
		OadrDistributeEventType oadrDistributeEventType = DistributeEventSignalHelper.createOadrDistributeEventFromString(distributeEvent);
		List<String> resourceIDs = oadrDistributeEventType.getOadrEvent().get(0).getEiEvent().getEiTarget().getResourceID();
		if (resourceIDs == null || resourceIDs.size() != 2) {
			throw new FailedException("Expected 2 ResourceIDs in OadrDistributeEvent has not been received");
		}
		
		sendCreatedEvent(distributeEvent, OptTypeType.OPT_IN);
		
		return oadrDistributeEventType;
	}
}
