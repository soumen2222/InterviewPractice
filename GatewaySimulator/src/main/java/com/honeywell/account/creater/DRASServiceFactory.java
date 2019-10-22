package com.honeywell.account.creater;

import java.net.URL;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import org.jboss.logging.Logger;

import com.honeywell.dras.scapi.group.GroupService;
import com.honeywell.dras.scapi.party.scPartyService;
import com.honeywell.dras.scapi.program.scProgramService;
import com.honeywell.dras.scapi.resource.scResourceService;
import com.honeywell.dras.services.api.ports.endpoint.EndPointService;
import com.honeywell.dras.services.soap.client.endpoint.EndPointService_Service;
import com.honeywell.dras.services.soap.client.group.GroupService_Service;
import com.honeywell.dras.services.soap.client.scparty.ScPartyService_Service;
import com.honeywell.dras.services.soap.client.scprogram.ScProgramService_Service;
import com.honeywell.dras.services.soap.client.scresource.ScResourceService_Service;

public class DRASServiceFactory {
    
	
	Logger log = Logger.getLogger(DRASServiceFactory.class);
	private scProgramService programService = null;
	private scPartyService partyService = null;
	private scResourceService resourceService =null;
	private GroupService groupService =null;
	private EndPointService endPointService =null;

	public scProgramService getScProgramService() {
		scProgramService res = getProgramService();
			
		return res;
	}
	
	private scProgramService getProgramService() {
		URL sc_program_service_wsdl = null;
		try {
			sc_program_service_wsdl = DrasEndPoints.getScProgramService();
		} catch (Exception ex) {
			log.error("Error getting DRAS Service SC Program Service URL's", ex);
		}
		
		
		if (programService==null){

		ScProgramService_Service s = new ScProgramService_Service(sc_program_service_wsdl);
		programService = s.getScProgramSoapServicePort();
		setAuth((BindingProvider)programService);
		}
		return programService;
	}
	
	public scPartyService getScPartyService() {
		scPartyService res = getPartyService();
				
		return res;
	}
	
	private scPartyService getPartyService() {
		URL sc_party_service_wsdl = null;
		try {
			sc_party_service_wsdl = DrasEndPoints.getScPartyService();
		} catch (Exception ex) {
			log.error("Error getting DRAS Service SC Party Service URL's", ex);
		}
        
		if (partyService==null){
			ScPartyService_Service s = new ScPartyService_Service(sc_party_service_wsdl);
			partyService= s.getScPartySoapServicePort();
			setAuth((BindingProvider)partyService);
		}
		
		return partyService;
	}
	
	public scResourceService getScResourceService() {
		scResourceService res = getResourceService();
			
		return res;
	}
	
	private scResourceService getResourceService() {
		URL sc_resource_service_wsdl = null;
		try {
			sc_resource_service_wsdl = DrasEndPoints.getScResourceService();
		} catch (Exception ex) {
			log.error("Error getting DRAS Service SC Resource Service URL's", ex);
		}
  
		if (resourceService == null){
			
			ScResourceService_Service s = new ScResourceService_Service(sc_resource_service_wsdl);
			resourceService = s.getScResourceSoapServicePort();
			setAuth((BindingProvider)resourceService);
		}
		
		return resourceService;
	}
		
	public GroupService getGroupService() {
		GroupService res = getGrpService();
			
		return res;
	}
	
	private GroupService getGrpService() {
		URL group_service_wsdl = null;
		try {
			group_service_wsdl = DrasEndPoints.getGroupService();
		} catch (Exception ex) {
			log.error("Error getting DRAS Service Group Service URL's", ex);
		}
		if( groupService==null)
		{
			GroupService_Service s = new GroupService_Service(group_service_wsdl);
			groupService= s.getGroupSoapServicePort();
			setAuth((BindingProvider)groupService);
		}

		return groupService ; 
	}
	
 private EndPointService getEPService() {
	 URL endpoint_service_wsdl = null;
		try {
			endpoint_service_wsdl = DrasEndPoints.getEndpointService();
		} catch (Exception ex) {
			log.error("Error getting DRAS Service End Point Service URL's", ex);
		}
		if (endPointService==null)
		{
			
			EndPointService_Service s = new EndPointService_Service(endpoint_service_wsdl);
			endPointService= s.getEndPointServiceSOAP();
			setAuth((BindingProvider)endPointService);
		}

		return endPointService; 

	    }
 
	public EndPointService getEndPointService() {
		EndPointService res = getEPService();
			
		return res;
	}
	
	
	static String soapuser = System
			.getProperty("com.honeywell.dras.soap.username");
	static String soappassword = System
			.getProperty("com.honeywell.dras.soap.password");

	private static void setAuth(BindingProvider bp) {
		Map<String, Object> requestContext = bp.getRequestContext();
		requestContext.put(BindingProvider.USERNAME_PROPERTY, soapuser);
		requestContext.put(BindingProvider.PASSWORD_PROPERTY, soappassword);
	}
	
	static {

	    java.net.Authenticator.setDefault(new java.net.Authenticator() {

	        @Override
	        protected java.net.PasswordAuthentication getPasswordAuthentication() {
	            return new java.net.PasswordAuthentication(soapuser, soappassword.toCharArray());
	        }
	    });
	}
	
}
