package com.honeywell.ven.api;

import java.util.List;

/**
 * Target
 * @author sunil
 *
 */
public interface Target {

	public List<String> getGroupIdList();
	
	public List<String> getPartyIdList();
	
	public List<String> getResourceIdList();
	
	public List<String> getVenIdList();
}
