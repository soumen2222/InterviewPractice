package com.honeywell.account.creater;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.honeywell.dras.common.util.MemorySequence;
import com.honeywell.dras.scapi.messages.endpoint.GetAllEndpointsResponse;
import com.honeywell.dras.scapi.messages.endpoint.GetEndpointListRequest;
import com.honeywell.dras.scapi.messages.endpoint.ScEndpoint;
import com.honeywell.dras.scapi.messages.party.ScParty;
import com.honeywell.dras.scapi.messages.resource.CreateResourceRequest;
import com.honeywell.dras.scapi.messages.resource.DeleteResourceRequest;
import com.honeywell.dras.scapi.messages.resource.GetResourceByAuthNameRequest;
import com.honeywell.dras.scapi.messages.resource.GetResourceResponse;
import com.honeywell.dras.scapi.messages.resource.LinkResourceToEndpointRequest;
import com.honeywell.dras.scapi.messages.resource.ResourceServiceException;
import com.honeywell.dras.scapi.messages.resource.ScResource;
import com.honeywell.dras.scapi.resource.scResourceService;

public class ScResourceServiceClient {

	
	private scResourceService scResourceService = null;

		
	public ScResourceServiceClient() {
		
	}
	
		
	private scResourceService  getScResourceService() {
		if (scResourceService == null) {
			scResourceService = new DRASServiceFactory().getScResourceService();
		}
		return scResourceService;

	}
	
	
	
	public String removeResource(ScResource resource){
		try{
			DeleteResourceRequest request = new DeleteResourceRequest();
				request.setResource(resource);
				this.getScResourceService().delete(request);
				return "success";
		} catch (ResourceServiceException e) {
			Logger.getLogger(ScResourceServiceClient.class.getName()).log(Level.SEVERE,
					"Error delete resource in SC Resource Service URL's", e);
			return e.getMessage();
		}
	}
	
		
	public ScResource createResource(ScResource resource,ScParty party){
		CreateResourceRequest request = new CreateResourceRequest();
		request.setResource(resource);
		request.setParty(party);
		ScResource scresource =resource;
		try {
			this.getScResourceService().create(request);
			//reload it
			GetResourceByAuthNameRequest authReq= new GetResourceByAuthNameRequest();
			authReq.setAuthName(resource.getAuthUsername());
			ScEndpoint endPoint = new ScEndpoint();			
			GetResourceResponse res=getScResourceService().findByAuthorizationName(authReq);
			scresource =res.getResource();
			ScResourceServiceClient newobj = new ScResourceServiceClient();
			List<ScEndpoint> endpoints = new ArrayList<ScEndpoint>();
			GetEndpointListRequest endpointrequest = new GetEndpointListRequest(); 
			endpointrequest.setVenName(resource.getName());
			endpoints = newobj.getEndPoints(endpointrequest);
				
				for (ScEndpoint endPt : endpoints) {
					System.out.println(">>>>>>>>>> endpoint: " + endPt.getUUID() +" name is "  + endPt.getVenName() + ", uri=" + endPt.getTransportAddress());
					if (endPt.getVenName().equals(resource.getName()));
					{
		                endPoint = endPt;
		               	System.out.println("Got the Endpoint");
					}
				}
								
				LinkResourceToEndpointRequest linkRequest = new LinkResourceToEndpointRequest();
								
				String venId = endPoint.getVenId();
				if(venId==null){
					venId = scresource.getEntityId();
				}
				
				linkRequest.setVendId(venId);
				linkRequest.setEndpoint(endPoint);
				linkRequest.setResource(scresource);
				getScResourceService().LinkResourceToEndpoint(linkRequest);
			
			
		} catch (ResourceServiceException e) {
			e.getMessage();
		}
		return scresource;
	}
		
		
	public List<ScEndpoint>  getEndPoints(GetEndpointListRequest request) {
		GetAllEndpointsResponse response;
		try {
			response = this.getScResourceService().findEndpointsByCriteria(request);
			return response.getEndpoints();
			
		} catch (ResourceServiceException e) {
			Logger.getLogger(ScResourceServiceClient.class.getName()).log(Level.SEVERE,
					"Failed to get end points  in SC Resource Service URL's", e);
			return Collections.emptyList();
		}
		
	}
	
	
	
	
	
}
