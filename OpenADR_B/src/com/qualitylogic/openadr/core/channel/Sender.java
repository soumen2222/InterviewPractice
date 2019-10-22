package com.qualitylogic.openadr.core.channel;

import com.qualitylogic.openadr.core.bean.ServiceType;

/**
 * Channel Sender interface. This allows you to send and receive a String without 
 * knowing the underlying transport channel. Supported channels include HTTP and XMPP.
 */
public interface Sender {
	
	String send(final ServiceType serviceType, final String to, final String message);
	
	void stop();
}
