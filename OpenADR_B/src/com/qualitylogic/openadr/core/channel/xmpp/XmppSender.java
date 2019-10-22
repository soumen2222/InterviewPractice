package com.qualitylogic.openadr.core.channel.xmpp;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smackx.ServiceDiscoveryManager;
import org.jivesoftware.smackx.packet.DiscoverItems;
import org.jivesoftware.smackx.packet.DiscoverItems.Item;

import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.channel.Sender;
import com.qualitylogic.openadr.core.channel.util.SmackUtil;
import com.qualitylogic.openadr.core.channel.util.TraceUtil;
import com.qualitylogic.openadr.core.channel.util.XmlUtil;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

/**
 * XMPP implementation of the channel Sender. 
 */
public class XmppSender implements Sender {
	
	private XMPPConnection connection;

	private ServiceType serviceType;
	
	@Override
	public String send(final ServiceType serviceType, final String to, final String message) {
		this.serviceType = serviceType;
		
		// CHANNEL logs
		// logger.log(Level.INFO, "XmppSender serviceType=" + serviceType + " to=" + to);

		if (connection == null) {
			connection = connect(serviceType);
		}

		String xml = XmlUtil.removeXmlDeclaration(message);
		String jid = getJid(serviceType, to);

		XmlIQ xmlIQ = new XmlIQ(xml);
		xmlIQ.setType(IQ.Type.SET);
		xmlIQ.setTo(jid);

		TraceUtil.traceRequest(serviceType, jid, message);
		
		// use PacketCollector to convert this to a synchronous response
		PacketFilter filter = new PacketIDFilter(xmlIQ.getPacketID());

		// send packet
		connection.sendPacket(xmlIQ);

		// collect
		PacketCollector collector = connection.createPacketCollector(filter);
		IQ iq = (IQ) collector.nextResult(TIMEOUT_MS);
		
		String response = null;
		if (iq != null) {
			if (iq.getType() != IQ.Type.RESULT) {
				// throw new IllegalStateException("Expecting an IQ type='result' but got type='" + iq.getType() + "' instead.");
				
				// return null instead of throwing an Exception for negative test cases
				response = null;
			} else if (iq instanceof XmlIQ) {
				if (TestSession.isValidationErrorFound()) {
					return "";
				}
				
				XmlIQ xmlIQResponse = (XmlIQ) iq;
				response = xmlIQResponse.getChildElementXML();
				
				TraceUtil.traceResponse(serviceType, jid, message, response, null);
			} else {
				// possible for empty responses
				response = "";
			}
			
		} else {
			logger.log(Level.WARNING, "Got a null IQ response, which is probably a response timeout.");
		}

		return response;
	}

	private String getJid(final ServiceType serviceType, final String to) {
		PropertiesFileReader properties = new PropertiesFileReader();
		String host = (serviceType == ServiceType.VEN) ? properties.getDutXmppAddress() : properties.getThXmppAddress();
		
		String jid = null;
		if (properties.isXmppServiceDiscovery() && (serviceType == ServiceType.VEN)) {
			try {
				if (SmackUtil.supportsFeature(connection, host, FEATURE_OPENADR)) {
					jid = getJidByDiscovery(host, to);
				} else {
					throw new IllegalStateException("XMPP server does not support OpenADR service discovery");
				}
			} catch (XMPPException e) {
				throw new IllegalStateException("Failed to discover JID for service=" + to);
			}

			// CHANNEL logs
			// logger.log(Level.INFO, "Using jid=" + jid + " from Service Discovery");
		} else if (serviceType == ServiceType.VTN) {
			// jid = properties.getXmppVENUsername() + "@" + host + "/" + to;
			jid = properties.getClient_VEN_JID();
			
			if (!jid.matches(".+@.+/.+")) {
				throw new IllegalStateException("Expected '" + jid + "' to be a full JID");
			}
			
			// CHANNEL logs
			// logger.log(Level.INFO, "Using jid=" + jid + " from TransportAddress");
		} else if (serviceType == ServiceType.VEN) {
			jid = properties.getXmppVTNUsername() + "@" + host + "/" + to;

			// CHANNEL logs
			// logger.log(Level.INFO, "Using jid=" + jid + " from Configuration");
		} else {
			throw new IllegalStateException("Unsupported serviceType=" + serviceType);
		}
		
		return jid;
	}
	
	private String getJidByDiscovery(final String entityID, final String to) throws XMPPException {
		String jid = null;
		
		ServiceDiscoveryManager discoManager = ServiceDiscoveryManager.getInstanceFor(connection);

		DiscoverItems discoItems = discoManager.discoverItems(entityID, FEATURE_OPENADR + "#services");
		Iterator<Item> i = discoItems.getItems();
		while (i.hasNext()) {
			DiscoverItems.Item item = (DiscoverItems.Item) i.next();
			String node = item.getNode();
			
			if (node.equalsIgnoreCase(FEATURE_OPENADR + "/" + to)) {
				jid = item.getEntityID();
				break;
			}
		}
		
		if (jid == null) {
			throw new IllegalStateException("Cannot find JID for service=" + to);
		}
		
		return jid;
	}
	
	private XMPPConnection connect(final ServiceType serviceType) {
		PropertiesFileReader properties = new PropertiesFileReader();
		
		// VEN/VTN logic
		String username = null;
		String password = null;
		String resourceName = null;
		String host = null;
		int port = 0;

		if (serviceType == ServiceType.VEN) {
			username = properties.getXmppVENUsername();
			password = properties.getXmppVENPassword();
			resourceName = properties.getXmppVENResourceName();
			host = properties.getDutXmppAddress();
			port = properties.getDutXmppPort();
		} else {
			username = properties.getXmppVTNUsername();
			password = properties.getXmppVTNPassword();
			resourceName = properties.getXmppVTNResourceName();
			host = properties.getThXmppAddress();
			port = properties.getThXmppPort();
		}
		
		XMPPConnection connection = null;
		try {
			if (serviceType == ServiceType.VEN) {
				connection = XmppVenConnection.getInstance().getConnection();
			} else {
				connection = SmackUtil.login(serviceType, host, port, resourceName, username, password);
			}
		} catch (XMPPException e) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Connection failed");
			TestSession.setValidationErrorFound(true);
			LogHelper.addTrace(e.getMessage());
			e.printStackTrace();
			throw new IllegalStateException("Failed to login to XMPP host=" + host + " username=" + username, e);
		}
		
		return connection;
	}

	@Override
	public void stop() {
		if (serviceType == ServiceType.VEN) {
			XmppVenConnection.getInstance().getConnection();
		} else {
			SmackUtil.logout(connection);
		}
		connection = null;
	}

	private static final String FEATURE_OPENADR = "http://openadr.org/openadr2";

	private static final int TIMEOUT_MS = 10 * 1000;
	
	private static final Logger logger = Logger.getLogger(XmppListener.class.getName());
}
