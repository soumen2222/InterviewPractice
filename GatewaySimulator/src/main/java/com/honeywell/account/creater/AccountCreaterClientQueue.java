
package com.honeywell.account.creater;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.ejb.Local;
import javax.ejb.Remote;

public interface AccountCreaterClientQueue {
    
    @Remote
    public interface R extends AccountCreaterClientQueue {}
    @Local
    public interface L extends AccountCreaterClientQueue {}
    
    public void sendMessage(ProgramPartyDTO message);
    
}
