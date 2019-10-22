package com.honeywell.ven.services.messages;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.honeywell.ven.entities.registration.Registration;
@XmlRootElement
public class TotalVenDataResponse {

	
	private int noOfResources =10;
	private int onlinePercentage = 42;
	private int executingPercentage =10;
	private  List<VenData> venData;
	public int getNoOfResources() {
		return noOfResources;
	}
	public void setNoOfResources(int noOfResources) {
		this.noOfResources = noOfResources;
	}
	public int getOnlinePercentage() {
		return onlinePercentage;
	}
	public void setOnlinePercentage(int onlinePercentage) {
		this.onlinePercentage = onlinePercentage;
	}
	public int getExecutingPercentage() {
		return executingPercentage;
	}
	public void setExecutingPercentage(int executingPercentage) {
		this.executingPercentage = executingPercentage;
	}

	public List<VenData> getVenData() {
		return venData;
	}
	public void setVenData(List<VenData> venData) {
		this.venData = venData;
	}



	
}
