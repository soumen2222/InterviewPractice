package com.honeywell.account.creater;



import com.honeywell.dras.scapi.messages.group.GroupServiceException;
import com.honeywell.dras.scapi.messages.party.ScParty;
import com.honeywell.dras.scapi.messages.program.ProgramServiceException;



public class AccounTest {

	 
			/**
	 * @param args
			 * @throws ProgramServiceException 
			 * @throws GroupServiceException 
	 */
	public static void main(String[] args) throws ProgramServiceException, GroupServiceException {
		// TODO Auto-generated method stub
		ProgramPartyDTO createPartyRequest = new ProgramPartyDTO();
		DrasEndPoints.setDnsname("http://127.0.0.1:8080");
		ScParty partydata = new ScParty();
		/* ScResource createResourceRequest = new ScResource();
		 ScEndpoint endpointLinkRequestType = new ScEndpoint();*/
		String accountNumber ="11100018957";
		
		createPartyRequest .setAccountNumber(accountNumber);
		createPartyRequest.setName("Test2_Soumen_Ghosh");
		String authUsername = "soumen22@gmail.com";
		createPartyRequest.setAuthUsername(authUsername);
		String utilityName = "TATA";
		createPartyRequest.setUtilityName(utilityName );
		AccountManagerBean acc = new AccountManagerBean();
		 acc.createCustomerInformation(createPartyRequest);
	    System.out.println("Customer Created ");
	    System.out.println("Party UUID is " + partydata.getUUID());
	   

	
	}
	
	
}


