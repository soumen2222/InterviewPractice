package com.honeywell.ven.api.opt;

import java.io.Serializable;

public class TransportInterface  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pointOfDelivery;
	private String pointOfReceipt;
	
	public String getPointOfReceipt() {
		return pointOfReceipt;
	}
	public void setPointOfReceipt(String pointOfReceipt) {
		this.pointOfReceipt = pointOfReceipt;
	}
	public String getPointOfDelivery() {
		return pointOfDelivery;
	}
	public void setPointOfDelivery(String pointOfDelivery) {
		this.pointOfDelivery = pointOfDelivery;
	}

}
