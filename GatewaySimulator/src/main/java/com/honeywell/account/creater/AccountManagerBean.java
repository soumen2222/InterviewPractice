package com.honeywell.account.creater;


import java.util.List;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.honeywell.dras.configure.resource.ProtocolType;
import com.honeywell.dras.scapi.group.GroupService;
import com.honeywell.dras.scapi.messages.group.AddResourceToGroupRequest;
import com.honeywell.dras.scapi.messages.group.GetGroupsNameAndDescResponse;
import com.honeywell.dras.scapi.messages.group.GroupNameAndDesc;
import com.honeywell.dras.scapi.messages.group.GroupServiceException;
import com.honeywell.dras.scapi.messages.party.ScParty;
import com.honeywell.dras.scapi.messages.program.ProgramServiceException;
import com.honeywell.dras.scapi.messages.resource.ScResource;

@Stateless
public class AccountManagerBean implements AccountManager.L , AccountManager.R {
	
	Logger log = Logger.getLogger(AccountManagerBean.class);
	private GroupService groupService = null;
		
@SuppressWarnings("static-access")
public String createCustomerInformation(ProgramPartyDTO createPartyRequest) throws GroupServiceException, ProgramServiceException {
	    ScParty partydata = new ScParty();
     	partydata = createAccount(createPartyRequest);
     	
     	log.info("Customer is created");
     	
     	 if (partydata!=null){
     		 
     		ScResource createResourceRequest = new ScResource();
     		createResourceRequest.setEntityId(createPartyRequest.getName() );
     		createResourceRequest.setAuthUsername(createPartyRequest.getName() +"Sim@akuacom.com");
     		createResourceRequest.setAuthPassword("R3d_Lant3rn");
     	    createResourceRequest.setName(createPartyRequest.getName());	
     	    createResourceRequest.setProvidesMeterUsage(true);     	 
		    ProtocolType protocolType =null;
			createResourceRequest.setProtocolType(protocolType.OPENADR2B);
     		addResource(partydata, createResourceRequest);
     	 }
		  
        return "Sucess";
    }


    private ScParty createAccount(ProgramPartyDTO createPartyRequest) throws ProgramServiceException {
    	ScPartyServiceClient  objParty = new ScPartyServiceClient();
    	ScParty partyresponse = new ScParty();
    	partyresponse= objParty.createParty(createPartyRequest);
        return partyresponse;
    }

    
   
	private String addResource(ScParty createPartyRequest, ScResource createResourceRequest) throws GroupServiceException {
      
      ScResourceServiceClient objResource = new ScResourceServiceClient();
      ScResource resourceresponse = new ScResource();
      resourceresponse = objResource.createResource(createResourceRequest, createPartyRequest);
      System.out.println("Resource created with entityid " +  resourceresponse.getEntityId() );
		GetGroupsNameAndDescResponse  getGroupsNameAndDescResponse  = getGroupService().getGroupsNameAndDesc(null);
		String groupName = "TestGroup";
		boolean groupexists = false;
		if(getGroupsNameAndDescResponse != null){
			List<GroupNameAndDesc>  listGND = getGroupsNameAndDescResponse.getGroups();
			//Find if Test Group is available if not create Test group and map the resource with the test group.	
			for(GroupNameAndDesc groupNameAndDesc:listGND){
				//groupNameList.add(groupNameAndDesc.getName());
				if (groupNameAndDesc.getName().contains(groupName)){
					System.out.println("Group Already exists " +  groupNameAndDesc.getName() );
					groupexists= true;
				}
				
			}
		}
		if (!groupexists){
			   	System.out.println("Creating new group " + groupName);
        		getGroupService().createGroup(groupName);
		}
		
        	// Add the resource to Test Group
        	System.out.println("Adding Resource to group " + groupName);
           	AddResourceToGroupRequest addResourceToGroupRequest = new AddResourceToGroupRequest() ;
           	addResourceToGroupRequest.setGroupName(groupName);
           	String resourceId =resourceresponse.getEntityId();
			addResourceToGroupRequest.setResourceId(resourceId );
			getGroupService().addResourceToGroup(addResourceToGroupRequest )	;
       	
                
        return "Sucess";
    }

 
		
	   private GroupService getGroupService() {
	        if (groupService == null) {
	        	
	            groupService = new DRASServiceFactory().getGroupService();
	        }
	        return groupService;

	    }
	   
	}