package com.honeywell.account.creater;

import java.util.logging.Level;
import java.util.logging.Logger;


import com.honeywell.dras.configure.party.Party;
import com.honeywell.dras.configure.party.PartyRole;
import com.honeywell.dras.scapi.messages.party.AddProgramEnrollmentRequest;
import com.honeywell.dras.scapi.messages.party.ChangePasswordRequest;
import com.honeywell.dras.scapi.messages.party.CreatePartyRequest;
import com.honeywell.dras.scapi.messages.party.DeletePartyRequest;
import com.honeywell.dras.scapi.messages.party.GetPartyRequest;
import com.honeywell.dras.scapi.messages.party.GetPartyResponse;
import com.honeywell.dras.scapi.messages.party.PartyServiceException;
import com.honeywell.dras.scapi.messages.party.ScEnrollment;
import com.honeywell.dras.scapi.messages.party.ScParty;
import com.honeywell.dras.scapi.messages.program.GetAllProgramsResponse;
import com.honeywell.dras.scapi.messages.program.ProgramServiceException;
import com.honeywell.dras.scapi.messages.program.ScProgram;
import com.honeywell.dras.scapi.party.scPartyService;
import com.honeywell.dras.scapi.program.scProgramService;

public class ScPartyServiceClient {
	
	private static final String SUCCESS = "success";
	private scPartyService scPartyService = null;
	
	private scProgramService scProgramService = null;
	
	
		
	private scPartyService getScPartyService() {
		if (scPartyService == null) {
			scPartyService = new DRASServiceFactory().getScPartyService();
		}
		return scPartyService;

	}

	
	private scProgramService getScProgramService() {
		if (scProgramService == null) {
			scProgramService = new DRASServiceFactory().getScProgramService();
		}
		return scProgramService;
	}
	
	public ScParty createParty(ProgramPartyDTO party) throws ProgramServiceException{
		CreatePartyRequest partyrequest= new CreatePartyRequest();
		ScParty scParty = party.getParty();
		partyrequest.setParty(party.getParty());
		
		// as filed is mandatory, updated display name as lastname		
		scParty.setFirstName(scParty.getName());
		scParty.setLastName(scParty.getName());
		
		String entityId = party.getAccountNumber();
		scParty.setEntityId(entityId);
		
		scParty.setPartyRole(PartyRole.CUSTOMER);
		
		try {
			getScPartyService().create(partyrequest);
		} catch (PartyServiceException e) {
			Logger.getLogger(ScPartyServiceClient.class.getName()).log(Level.SEVERE,
					"Error create  party in  SC Party Service URL's", e);
			return null;
		}
		
		//set password - default password is Best_1234
		ChangePasswordRequest cpReq = new ChangePasswordRequest();
		cpReq.setAuthName(scParty.getAuthUsername());
		cpReq.setCurrentPassword(null);
		String authPassword= "Best_1234";
		scParty.setAuthPassword(authPassword);
		cpReq.setNewPassword(scParty.getAuthPassword());			
		try {
			getScPartyService().changePassword(cpReq);
		} catch (PartyServiceException e2) {
			Logger.getLogger(ScPartyServiceClient.class.getName()).log(Level.SEVERE,
					"Error set Party password in  SC Party Service URL's", e2);
			return null;
		}
		
		try {
			// Get all programs and enroll the party in all program.
			GetAllProgramsResponse alPrograms = getScProgramService().findAll();
			
			if(alPrograms.getPrograms()!=null){
				//firstly get the load the party again 
				GetPartyRequest load = new GetPartyRequest();
				load.setEntityId(entityId);
					GetPartyResponse response;
					try {
						response = getScPartyService().findByEntityId(load);
						scParty=response.getParty();
					} catch (PartyServiceException e1) {
						Logger.getLogger(ScPartyServiceClient.class.getName()).log(Level.SEVERE,
								"Error get  party in  SC Party Service URL's", e1);
						return null;
					}
				
				for(ScProgram prog:alPrograms.getPrograms()){
					AddProgramEnrollmentRequest enroll = new AddProgramEnrollmentRequest();
					ScEnrollment e = new ScEnrollment();
					e.setParty(scParty);
					e.setProgram(prog);
					e.setEntityId(scParty.getEntityId() + "|" + prog.getEntityId());
					e.setActive(true);
					e.setEnrollmentComments("Enrolled from Simulator");
					enroll.setEnrollment(e);
					
					try {
						getScPartyService().addProgramEnrollment(enroll);
					} catch (PartyServiceException ex) {
						Logger.getLogger(ScPartyServiceClient.class.getName()).log(Level.SEVERE,
								"Error add  party to program in  SC Party Service URL's", ex);
						return null;
					}
				}
			}
			
		} catch (ProgramServiceException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		return scParty;
	}

	

	
	

	public String deleteParty(PartyDTO input) {		
		try {
			DeletePartyRequest deletePartyRequest = getDeletePartyRequest(input);
			if(deletePartyRequest != null)
				getScPartyService().delete(deletePartyRequest);
			else
				return "Operator ID is invalid";
		} catch (PartyServiceException ex) {
			Logger.getLogger(ScPartyServiceClient.class.getName()).log(Level.SEVERE,
					"Error in delete party SC Party Service URL's", ex);
			return ex.toString();
		}
		return SUCCESS;
	}


	private DeletePartyRequest getDeletePartyRequest(PartyDTO input) throws PartyServiceException {
		DeletePartyRequest partyRequest = new DeletePartyRequest();		
		GetPartyRequest pReq = new GetPartyRequest();
		pReq.setEntityId(input.getEntityID());
		GetPartyResponse pResp = getScPartyService().findByEntityId(pReq);
		Party p = pResp.getParty();
		if(input.getOperatorID().equalsIgnoreCase(p.getAuthUsername())){
			p.setName(input.getDisplayName());
			p.setAuthUsername(input.getOperatorID());
			p.setAuthPassword(input.getPassword());
			p.setFirstName(input.getDisplayName());
			partyRequest.setParty(new ScParty(p));	
			return partyRequest;	
		}
		
		return null;
	}
	


	public void updateCustomerPassword(String authUserName,String oldPassword,String newPassword)
				throws PartyServiceException{
		ChangePasswordRequest cpReq = new ChangePasswordRequest();
		cpReq.setAuthName(authUserName);
		cpReq.setCurrentPassword(oldPassword);
		cpReq.setNewPassword(newPassword);		
		getScPartyService().changePassword(cpReq);
	}
	

	
	


}
