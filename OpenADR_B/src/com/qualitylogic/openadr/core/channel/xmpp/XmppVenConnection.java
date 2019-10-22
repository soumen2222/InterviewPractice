package com.qualitylogic.openadr.core.channel.xmpp;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.channel.util.SmackUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class XmppVenConnection {
	private static XmppVenConnection instance;
	
	static {
		try {
			instance = new XmppVenConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private XMPPConnection connection;
	
	private XmppVenConnection() {
		PropertiesFileReader properties = new PropertiesFileReader();		
		String username = properties.getXmppVENUsername();
		String password = properties.getXmppVENPassword();
		String resource = properties.getXmppVENResourceName();
		String host = properties.getDutXmppAddress();
		int port = properties.getDutXmppPort();
		
		try {
			connection = SmackUtil.login(ServiceType.VEN, host, port, resource, username, password);
		} catch (XMPPException e) {
			throw new IllegalStateException("Failed to login to XMPP host=" + host + " username=" + username, e);
		}
	}
	
	public static XmppVenConnection getInstance() {
		return instance;
	}
	
	public XMPPConnection getConnection() {
		return connection;
	}

	public void logout() {
		SmackUtil.logout(connection);
		connection = null;
	}
}
