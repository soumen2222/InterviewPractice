package com.honeywell.ven.api;

import java.util.List;

/**
 * CreatedEvent
 * @author sunil
 *
 */
public interface CreatedEvent {

	public String getVenId();
	
	public Response getResponse();
	
	public List<EventResponse> getEventResponseList();
}
