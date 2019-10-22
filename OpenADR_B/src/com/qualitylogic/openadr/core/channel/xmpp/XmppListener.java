package com.qualitylogic.openadr.core.channel.xmpp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.IQTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;

import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.channel.Handler;
import com.qualitylogic.openadr.core.channel.Listener;
import com.qualitylogic.openadr.core.channel.util.SmackUtil;
import com.qualitylogic.openadr.core.channel.util.TraceUtil;
import com.qualitylogic.openadr.core.channel.util.XmlUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

/**
 * XMPP implementation of the channel Listener. 
 */
public class XmppListener implements Listener {
	
	private Collection<XMPPConnection> connections = new ArrayList<>();
	
	private final Map<String, Handler> handlers = new HashMap<>();
	
	private ServiceType serviceType;
	
	@Override
	public void start(final ServiceType serviceType) {
		this.serviceType = serviceType;
		
		// CHANNEL logs
		// logger.log(Level.INFO, "XmppListener start serviceType=" + serviceType);
		
		PropertiesFileReader properties = new PropertiesFileReader();
		
		// VEN/VTN logic
		String username = null;
		String password = null;
		String host = null;
		int port = 0;
		if (serviceType == ServiceType.VEN) {
			username = properties.getXmppVENUsername();
			password = properties.getXmppVENPassword();
			host = properties.getDutXmppAddress();
			port = properties.getDutXmppPort();
		} else {
			username = properties.getXmppVTNUsername();
			password = properties.getXmppVTNPassword();
			host = properties.getThXmppAddress();
			port = properties.getThXmppPort();
		}

		if ((serviceType == ServiceType.VEN) && (handlers.size() > 1)) {
			throw new IllegalStateException("Expecting only one VEN XMPP endpoint.");
		}
		
		for (Map.Entry<String, Handler> handlerEntry : handlers.entrySet()) {
			String resource = handlerEntry.getKey();
			Handler handler = handlerEntry.getValue();
		
			XMPPConnection connection = null;
			try {
				if (serviceType == ServiceType.VEN) {
					connection = XmppVenConnection.getInstance().getConnection();
				} else {
					connection = SmackUtil.login(serviceType, host, port, resource, username, password);
					connections.add(connection);
				}
				
				// logger.log(Level.INFO, "XmppListener login user=" + connection.getUser());
			} catch (XMPPException e) {
				throw new IllegalStateException("Failed to login to XMPP host=" + host + " username=" + username, e);
			}

			PacketListener packetListener = new PacketListenerImpl(connection, handler);
			PacketFilter packetFilter = new IQTypeFilter(IQ.Type.SET);
			connection.addPacketListener(packetListener, packetFilter);
		}
	}

	@Override
	public void stop() {
		// CHANNEL logs
		// logger.log(Level.INFO, "XmppListener stop");
		
		if (serviceType == ServiceType.VEN) {
			XmppVenConnection.getInstance().logout();
		} else {
			for (XMPPConnection connection : connections) {
				// logger.log(Level.INFO, "XmppListener logout user=" + connection.getUser());
				SmackUtil.logout(connection);
			}
		}
	}
	
	/**
	 * Adds the handler implementation for the specified XMPP resource.
	 */
	@Override
	public void addHandler(String resource, Handler handler) {
		handlers.put(resource, handler);
	}
	
	private static class PacketListenerImpl implements PacketListener {
		
		private final XMPPConnection connection;
		
		private final Handler handler;

		public PacketListenerImpl(final XMPPConnection connection, final Handler handler) {
			this.connection = connection;
			this.handler = handler;
		}

		@Override
		public void processPacket(final Packet packet) {
			// CHANNEL logs
			// logger.log(Level.INFO, "PacketListenerImpl.processPacket()");
			
			TraceUtil.logRequest(packet);
			
			XmlIQ xmlIQ = (XmlIQ) packet;
			if (xmlIQ.getType() != IQ.Type.SET && xmlIQ.getType() != IQ.Type.ERROR) {
				throw new IllegalArgumentException("Unsupported IQ type=" + xmlIQ.getType());
			}
			
			String response = handler.handle(xmlIQ.getChildElementXML());
			
			String xml = XmlUtil.removeXmlDeclaration(response);
			
			XmlIQ xmlIQResponse = new XmlIQ(xml);
			xmlIQResponse.setType(IQ.Type.RESULT);
			xmlIQResponse.setTo(packet.getFrom());
			xmlIQResponse.setPacketID(packet.getPacketID());

			connection.sendPacket(xmlIQResponse);
			
			TraceUtil.logResponse(xmlIQResponse);
		}
	};
	
	private static final Logger logger = Logger.getLogger(XmppListener.class.getName());
}
