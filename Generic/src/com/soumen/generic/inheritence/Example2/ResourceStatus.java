package com.soumen.generic.inheritence.Example2;



import java.io.Serializable;

public class ResourceStatus implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Boolean online;
	private Boolean manualOverride;
	/**
	 * @return the online
	 */
	public Boolean getOnline() {
		return online;
	}
	/**
	 * @param online the online to set
	 */
	public void setOnline(Boolean online) {
		this.online = online;
	}
	/**
	 * @return the manualOverride
	 */
	public Boolean getManualOverride() {
		return manualOverride;
	}
	/**
	 * @param manualOverride the manualOverride to set
	 */
	public void setManualOverride(Boolean manualOverride) {
		this.manualOverride = manualOverride;
	}
	/**
	 * @return the capacity
	 */
	

}
