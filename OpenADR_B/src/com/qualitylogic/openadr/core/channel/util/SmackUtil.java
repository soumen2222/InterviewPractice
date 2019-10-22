package com.qualitylogic.openadr.core.channel.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.ServiceDiscoveryManager;
import org.jivesoftware.smackx.packet.DiscoverInfo;
import org.jivesoftware.smackx.packet.DiscoverInfo.Feature;

import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public final class SmackUtil {
	private SmackUtil() {
	}
	
	public static XMPPConnection login(final ServiceType serviceType, final String host, final int port, final String resource, final String username, final String password) throws XMPPException {
		PropertiesFileReader properties = new PropertiesFileReader();

		if (properties.isXmppSaslExternal()) {
			SslUtil.setupClientSslContext(serviceType);
			SASLAuthentication.supportSASLMechanism("EXTERNAL");
		} else if (properties.isSecurity_Enabled()) {
			SslUtil.setupClientSslContext(serviceType);
		}
		
		ConnectionConfiguration config = new ConnectionConfiguration(host, port);
		config.setRosterLoadedAtLogin(false);
		// config.setCompressionEnabled();

		// require security
		if (properties.isSecurity_Enabled() || properties.isXmppSaslExternal()) {
			config.setSecurityMode(SecurityMode.required);
			config.setExpiredCertificatesCheckEnabled(true);
			config.setVerifyChainEnabled(true);
			config.setVerifyRootCAEnabled(true);
			config.setSelfSignedCertificateEnabled(false);
			
			// use ConnectionConfiguration as setting the system property doesn't work for some reason
			String trustStore = System.getProperty("javax.net.ssl.trustStore");
			String trustStorePassword = System.getProperty("javax.net.ssl.trustStorePassword");
			config.setTruststorePath(trustStore);
			config.setTruststorePassword(trustStorePassword);
			// config.setTruststoreType(trustStoreType); // no property
			
			String keyStore = System.getProperty("javax.net.ssl.keyStore"); 
			String keyStoreType = System.getProperty("javax.net.ssl.keyStoreType");
			config.setKeystorePath(keyStore);
			// config.setKeystorePassword(keyStorePassword); // no method
			config.setKeystoreType(keyStoreType);

			// domain check
			if (serviceType == ServiceType.VEN) {
				config.setNotMatchingDomainCheckEnabled(!properties.getVenClientVerifyHostName());
			} else {
				config.setNotMatchingDomainCheckEnabled(!properties.getVtnClientVerifyHostName());
			}
		} else {
			config.setSecurityMode(SecurityMode.disabled);
		}
		
		if (properties.isXmppDebug()) {
			// note that the debugger will keep the application from terminating
			config.setDebuggerEnabled(true);
		}

		XMPPConnection connection = new XMPPConnection(config, new CallbackHandlerImpl());
		connection.connect();

		connection.login(username, password, resource);
		return connection;
	}

	public static void logout(final XMPPConnection connection) {
		if ((connection != null) && (connection.isConnected())) {
			try {
				connection.disconnect();
			} catch (Exception e) {
				logger.log(Level.WARNING, "Failed to logout XMPP.", e);
			}
		}
	}

	public static boolean supportsFeature(final XMPPConnection connection, final String entityID, final String feature) throws XMPPException {
		boolean supported = false;
		
		ServiceDiscoveryManager discoManager = ServiceDiscoveryManager.getInstanceFor(connection);
		
		DiscoverInfo discoverInfo = discoManager.discoverInfo(entityID);
		Iterator<Feature> i = discoverInfo.getFeatures();
		while (i.hasNext()) {
			Feature item = (Feature) i.next();
			if (item.getVar().equals(feature)) {
				supported = true;
				break;
			}
		}
		
		return supported;
	}

	private static class CallbackHandlerImpl implements CallbackHandler {
		@Override
		public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
			for (Callback callback : callbacks) {
				if (callback instanceof PasswordCallback) {
					String password = System.getProperty("javax.net.ssl.keyStorePassword");

					PasswordCallback passwordCallback = (PasswordCallback) callback;
					passwordCallback.setPassword(password.toCharArray());
				}
			}
		}
	}	
	private static final Logger logger = Logger.getLogger(SmackUtil.class.getName());
}
