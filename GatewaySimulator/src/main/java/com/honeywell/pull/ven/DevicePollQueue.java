
package com.honeywell.pull.ven;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.ejb.Local;
import javax.ejb.Remote;

import com.honeywell.ven.api.poll.PollRequest;

public interface DevicePollQueue {
    
    @Remote
    public interface R extends DevicePollQueue {}
    @Local
    public interface L extends DevicePollQueue {}
    
    public void sendMessage(PollRequest pollRequestmessage);
    
}
