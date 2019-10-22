package com.honeywell.ven.api.event;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.honeywell.ven.api.common.BaseClass;
import com.honeywell.ven.api.common.Response;

@XmlRootElement
public class DistributeEvent extends BaseClass{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4559849270388188716L;
	private Response response;
	private List<Event> eventList;
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
	 * @return the eventList
	 */
	public List<Event> getEventList() {
		if (eventList == null) {
			eventList = new ArrayList<Event>();
		}
		return eventList;
	}
	/**
	 * @param eventList the eventList to set
	 */
	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}

}
