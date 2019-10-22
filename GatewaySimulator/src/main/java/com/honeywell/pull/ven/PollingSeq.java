package com.honeywell.pull.ven;

import javax.ejb.Local;
import javax.ejb.Remote;

import com.honeywell.ven.entities.registration.Registration;

public interface PollingSeq {
	
	@Remote
    public interface R extends PollingSeq {}
    @Local
    public interface L extends PollingSeq {}
    
   
     public void createpollTimeoutHandler();
    
    
    

}
