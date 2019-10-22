package testcases.push.event.vtn;

import java.util.ArrayList;
import java.util.List;

import com.qualitylogic.openadr.core.action.CreatedEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.ICreateOptEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_CreateOptEventActionOnLastDistTwoResEvntRecd;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventActionOnLastDistributeEventReceived;
import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class E0_6065_TH_VEN_1 extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new E0_6065_TH_VEN_1());
	}

	@Override
	public void test() throws Exception {
		ICreatedEventAction createdEvent = new Default_CreatedEventActionOnLastDistributeEventReceived(true, true, "Response Description");
		addCreatedEventResponseImpl(createdEvent);
		
		listenForRequests();
		
		prompt(resources.prompt_010());

		OadrDistributeEventType distributeEvent = waitForDistributeEvent(1);
		checkSignalNames(distributeEvent, "ELECTRICITY_PRICE");
		
		waitForCreatedOpt(1);

		ICreatedEventAction createdEventOptOut = new Default_CreatedEventActionOnLastDistributeEventReceived(true, false);
		sendCreatedEvent(createdEventOptOut);
		
		waitForCompletion();

		checkDistributeEventRequest(1, 1, 2);
		checkResponse(1);
		
		boolean yesClicked = promptYes(resources.prompt_040());
		if (!yesClicked) {
			throw new FailedException("The VTN should show the VEN as still opt'd in.");
		}
	}

	private void addCreatedEventResponseImpl(ICreatedEventAction createdEvent) {
		OptTypeType optType = OptTypeType.OPT_IN;
		int eventToSelect = 1;
		List<Integer> selectResourcePositionInPayload = new ArrayList<Integer>();
		selectResourcePositionInPayload.add(2);
		
		ICreateOptEventAction createOptEvent = new Default_CreateOptEventActionOnLastDistTwoResEvntRecd(optType, eventToSelect, selectResourcePositionInPayload);
		createOptEvent.setlastEvent(true);
		createdEvent.addCreateOptEventAction(createOptEvent);

		CreatedEventActionList.addCreatedEventAction(createdEvent);
	}
}
