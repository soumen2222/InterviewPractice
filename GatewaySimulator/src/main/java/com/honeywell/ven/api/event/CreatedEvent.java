package com.honeywell.ven.api.event;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.honeywell.ven.api.common.BaseClass;
import com.honeywell.ven.api.common.Response;

@XmlRootElement
public class CreatedEvent extends BaseClass{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1993819788618964738L;
	private Response response;
	private List<EventResponse> eventResponseList;
	/**
	 * @return the response
	 */
	public Response getResponse() {
		return response;
	}
	/**
	 * @param response the response to set
	 */
	public void setResponse(Response response) {
		this.response = response;
	}
	/**
	 * @return the eventResponseList
	 */
	public List<EventResponse> getEventResponseList() {
		if (eventResponseList == null) {
			eventResponseList = new ArrayList<EventResponse>();
		}
		return eventResponseList;
	}
	/**
	 * @param eventResponseList the eventResponseList to set
	 */
	public void setEventResponseList(List<EventResponse> eventResponseList) {
		this.eventResponseList = eventResponseList;
	}
}
