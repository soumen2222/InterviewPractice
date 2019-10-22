package com.honeywell.ven.api.opt;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.honeywell.ven.api.OptType;
import com.honeywell.ven.api.common.BaseClass;
import com.honeywell.ven.api.common.Target;

@XmlRootElement
public class CreateOpt extends BaseClass{
	
	private String optId;
	private Target target;
	private String marketContext;
	private String optReason;
	private OptType optType;
	private String eventId;
	private List<Availibility> availibilityList;
	private DeviceClass deviceClass;
	
	
	public String getOptId() {
		return optId;
	}
	public void setOptId(String optId) {
		this.optId = optId;
	}
	public Target getTarget() {
		return target;
	}
	public void setTarget(Target target) {
		this.target = target;
	}
	public String getMarketContext() {
		return marketContext;
	}
	public void setMarketContext(String marketContext) {
		this.marketContext = marketContext;
	}
	public String getOptReason() {
		return optReason;
	}
	public void setOptReason(String optReason) {
		this.optReason = optReason;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public List<Availibility> getAvailibilityList() {
		return availibilityList;
	}
	public void setAvailibilityList(List<Availibility> availibilityList) {
		this.availibilityList = availibilityList;
	}
	public DeviceClass getDeviceClass() {
		return deviceClass;
	}
	public void setDeviceClass(DeviceClass deviceClass) {
		this.deviceClass = deviceClass;
	}
	public OptType getOptType() {
		return optType;
	}
	public void setOptType(OptType optType) {
		this.optType = optType;
	}

}
