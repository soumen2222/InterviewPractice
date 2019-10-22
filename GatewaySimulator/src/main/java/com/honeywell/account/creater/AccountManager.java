package com.honeywell.account.creater;


import javax.ejb.Local;
import javax.ejb.Remote;

import com.honeywell.dras.scapi.messages.group.GroupServiceException;
import com.honeywell.dras.scapi.messages.program.ProgramServiceException;


public interface AccountManager {
	@Remote
    public interface R extends AccountManager {}
    @Local
    public interface L extends AccountManager {}
    
    public String createCustomerInformation(ProgramPartyDTO createPartyRequest) throws GroupServiceException, ProgramServiceException;
    
}
