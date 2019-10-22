package com.honeywell.views;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.honeywell.pull.ven.VenSimServiceClient;
import com.honeywell.ven.api.fault.VenException;
import com.honeywell.ven.services.VenSimService;
import com.honeywell.ven.services.messages.TotalVenDataResponse;



@ViewScoped
@ManagedBean(name = "gatewayViewBean")
public class GatewayViewBean {


	private  TotalVenDataResponse gatewayData;
	private VenSimService venSimService=null;
 
	
 public GatewayViewBean()
 {
	 try {
		populatedata();
	} catch (VenException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	   
 }
	
 private void populatedata() throws VenException
 {
	 try {
		gatewayData = getVenSimService().getAllVendata();
	} catch (VenException e) {
		throw new VenException("Error in Finding Ven Details ",e);
	}
 }
	



	public TotalVenDataResponse getGatewayData() {
		return gatewayData;
	}


	public void setGatewayData(TotalVenDataResponse gatewayData) {
		this.gatewayData = gatewayData;
	}


	private VenSimService  getVenSimService() {
		if (venSimService == null) {
			venSimService = new VenSimServiceClient().getVenSimService();
		}
		return venSimService;

	}
	
	
	
}
