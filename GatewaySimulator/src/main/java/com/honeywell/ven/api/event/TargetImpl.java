package com.honeywell.ven.api.event;

import java.util.List;

import com.honeywell.ven.api.Target;

public class TargetImpl implements Target{
	
	private List<String> groupIdList;
	
	private List<String> partyIdList;
	
	private List<String> resourceIdList;
	
	private List<String> venIdList;

	public List<String> getGroupIdList() {
		return groupIdList;
	}

	public void setGroupIdList(List<String> groupIdList) {
		this.groupIdList = groupIdList;
	}

	public List<String> getPartyIdList() {
		return partyIdList;
	}

	public void setPartyIdList(List<String> partyIdList) {
		this.partyIdList = partyIdList;
	}

	public List<String> getResourceIdList() {
		return resourceIdList;
	}

	public void setResourceIdList(List<String> resourceIdList) {
		this.resourceIdList = resourceIdList;
	}

	public List<String> getVenIdList() {
		return venIdList;
	}

	public void setVenIdList(List<String> venIdList) {
		this.venIdList = venIdList;
	}
	
}
