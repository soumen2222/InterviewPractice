package com.honeywell.ven.api.opt;

import java.io.Serializable;
import java.util.List;

public class ServiceDeliveryPoint implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> nodeList;

	public List<String> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<String> nodeList) {
		this.nodeList = nodeList;
	}

}
