package com.qualitylogic.openadr.core.channel.factory;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.qualitylogic.openadr.core.channel.Listener;
import com.qualitylogic.openadr.core.channel.Sender;
import com.qualitylogic.openadr.core.channel.http.HttpListener;
import com.qualitylogic.openadr.core.channel.http.HttpSender;
import com.qualitylogic.openadr.core.channel.xmpp.XmppListener;
import com.qualitylogic.openadr.core.channel.xmpp.XmppSender;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

/**
 * Factory for creating channel Senders and Listeners. Supported channel 
 * types are 'HTTP' and 'XMPP'.  
 */
public final class ChannelFactory {
	public static Sender getSender() {
		PropertiesFileReader properties = new PropertiesFileReader();
		String channelType = properties.getTransportType();
		
		Sender sender = null;
		
		switch (channelType) {
			case TYPE_XMPP : {
				sender = new XmppSender(); 
				break;
			}
			case TYPE_HTTP : {
				sender = new HttpSender(); 
				break;
			}
			default : {
				logger.log(Level.WARNING, "Unknown channelType={0}. Using HttpSender.", channelType);
				sender = new HttpSender();
			}
		}
		
		return sender;
	}

	public static Listener getListener() {
		PropertiesFileReader properties = new PropertiesFileReader();
		String channelType = properties.getTransportType();
		
		Listener listener = null;
		
		switch (channelType) {
			case TYPE_XMPP : {
				listener = new XmppListener();
				break;
			}
			case TYPE_HTTP : {
				listener = new HttpListener(); 
				break;
			}
			default : {
				logger.log(Level.WARNING, "Unknown channelType={0}. Using HttpListener.", channelType);
				listener = new HttpListener();
			}
		}
		
		return listener;
	}

	private ChannelFactory() {
	}
	
	private static final String TYPE_HTTP = "HTTP";
	
	private static final String TYPE_XMPP = "XMPP";

	private static final Logger logger = Logger.getLogger(ChannelFactory.class.getName());
}
