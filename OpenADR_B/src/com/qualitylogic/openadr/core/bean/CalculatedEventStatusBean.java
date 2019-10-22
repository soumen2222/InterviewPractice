package com.qualitylogic.openadr.core.bean;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;

public class CalculatedEventStatusBean {

	ArrayList<String> eventStatusLogTrace = new ArrayList<String>();

	public EventStatusEnumeratedType getEventStatus() {
		return EventStatus;
	}

	public void setEventStatus(EventStatusEnumeratedType eventStatus) {
		EventStatus = eventStatus;
	}

	public ArrayList<String> getEventStatusLogTrace() {
		return eventStatusLogTrace;
	}

	public void addLogTrace(String eventStatusLog) {
		eventStatusLogTrace.add(eventStatusLog);
	}

	EventStatusEnumeratedType EventStatus;
}
