package com.qualitylogic.openadr.core.channel;

import com.qualitylogic.openadr.core.bean.ServiceType;

/**
 * Channel Listener interface. This allows you to listen for requests without knowing 
 * the underlying transport channel. Request handlers are implemented by implementing 
 * the channel Handler interface. Supported channels include HTTP and XMPP.
 */
public interface Listener {
	
	void start(final ServiceType serviceType);

	void stop();
	
	void addHandler(String resource, Handler handler);
}
