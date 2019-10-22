package com.honeywell.pull.ven;

import javax.ejb.Local;
import javax.ejb.Remote;

import com.honeywell.ven.api.poll.PollRequest;
import com.honeywell.ven.entities.registration.Registration;

public interface PollResponseProcessor {
	
	@Remote
    public interface R extends PollResponseProcessor {}
    @Local
    public interface L extends PollResponseProcessor {}
    
    
    public void pollprocessor(Object o , PollRequest pollRequest);   

}
