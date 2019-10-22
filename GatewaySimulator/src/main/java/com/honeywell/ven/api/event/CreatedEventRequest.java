package com.honeywell.ven.api.event;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.honeywell.ven.api.common.PushProfile;
import com.honeywell.ven.api.event.CreatedEvent;

@XmlRootElement
public class CreatedEventRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private CreatedEvent createdEvent;
	private PushProfile pushProfile;
	

	public CreatedEvent getCreatedEvent() {
		return createdEvent;
	}
	public void setCreatedEvent(CreatedEvent createdEvent) {
		this.createdEvent = createdEvent;
	}
	public PushProfile getPushProfile() {
		return pushProfile;
	}
	public void setPushProfile(PushProfile pushProfile) {
		this.pushProfile = pushProfile;
	}
	
	
}
