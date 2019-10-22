package com.qualitylogic.openadr.core.base;

import java.util.List;

import com.qualitylogic.openadr.core.action.CreatedEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.bean.ModeType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.signal.EiTargetType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.ven.VENServerResource;

public abstract class VenPushTestCase extends VenTestCase {
	
	protected void newInit() {
		super.newInit();
		TestSession.setMode(ModeType.PUSH);
	}

	protected ICreatedEventAction addCreatedEventResponse(ICreatedEventAction createdEvent) {
		CreatedEventActionList.addCreatedEventAction(createdEvent);
		return createdEvent;
	}

	protected void checkRegisteredReportRequest(int size) {
		List<OadrRegisteredReportType> registeredReports = TestSession.getOadrRegisteredReportTypeReceivedList();
		if (registeredReports.size() != size) {
			throw new FailedException("Expected " + size + " OadrRegisteredReport(s), received " + registeredReports.size());
		}
	}
	
	protected void checkCreatedEventCompleted(ICreatedEventAction createdEvent) {
		if (!createdEvent.isEventCompleted()) {
			throw new FailedException("OadrCreatedEvent is not complete.");
		}
	}

	protected OadrDistributeEventType checkDistributeEventRequest(int size) {
		List<OadrDistributeEventType> distributeEvents = VENServerResource.getOadrDistributeEventReceivedsList(); 
		if (distributeEvents.size() != size){
			throw new FailedException("Expected " + size + " OadrDistributeEvent(s), received " + distributeEvents.size());
		}
		
		return VENServerResource.getOadrDistributeEventReceivedsList().get(size - 1);
	}

	protected void checkDistributeEventRequest(int size, int eventSize, int resourceSize) {
		List<OadrDistributeEventType> distributeEvents = VENServerResource.getOadrDistributeEventReceivedsList();
		if (distributeEvents.size() != size) {
			throw new FailedException("Expected " + size + " OadrDistributeEvent(s), received " + distributeEvents.size());
		}

		List<OadrEvent> events = distributeEvents.get(0).getOadrEvent();
		if (events.size() != eventSize) {
			throw new FailedException("Expected " + eventSize + " OadrEvent(s), received " + events.size());
		}

		EiTargetType target = events.get(0).getEiEvent().getEiTarget();
		if (target == null) {
			throw new FailedException("Expected non-null EiTarget");
		}
		
		List<String> resourceIDs = target.getResourceID();
		if (resourceIDs.size() != resourceSize) {
			throw new FailedException("Expected " + resourceSize + " OadrEvent(s), received " + resourceIDs.size());
		}
	}

	protected void waitForCreatedEvent(ICreatedEventAction createEvent) {
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(1);
			
			if (createEvent.isEventCompleted()) {
				break;
			} else if (TestSession.isAtleastOneValidationErrorPresent()) {
				break;
			}
		}

		if (!createEvent.isEventCompleted()) {
			throw new FailedException("CreateEvent has not been received.");
		}
		
		checkForValidationErrors();
	}
}
