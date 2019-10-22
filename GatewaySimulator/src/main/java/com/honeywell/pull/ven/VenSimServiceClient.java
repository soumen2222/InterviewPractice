package com.honeywell.pull.ven;

import java.net.URL;
import javax.ejb.Stateless;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import org.jboss.logging.Logger;

import com.honeywell.ven.services.VenSimService;
import com.honeywell.ven.services.VenSimServicehelper;


//@Stateless
public  class VenSimServiceClient implements VenSimServicehelper.L , VenSimServicehelper.R

{

	private static String vensimServiceUrl = "http://127.0.0.1:8080/GatewaySimulator/VenSimService?WSDL";
	private VenSimService venSimService=null;
	
	private Logger log = Logger.getLogger(VenSimServiceClient.class);
	
	
	@Override
	public VenSimService getVenSimService() {
    	//If the vtnDrasService already exists just return the member variable instance
    	if(venSimService==null){
	    	URL url = null;
			try {
				url = new URL(vensimServiceUrl);
			} catch (Exception e) {
				log.error("Exception in getting VenService url !!! "+e);
			}
			QName qname = new QName("http://services.ven.honeywell.com/","VenSimService");
			Service service = Service.create(url, qname);
			// Extract the endpoint interface, the service "port".
			venSimService = service.getPort(VenSimService.class);
    	}
		return venSimService;
    }
    

}
