package com.qualitylogic.openadr.core.channel.http;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.engine.Engine;
import org.restlet.ext.jetty.HttpServerHelper;
import org.restlet.ext.jetty.HttpsServerHelper;

import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.channel.Handler;
import com.qualitylogic.openadr.core.channel.Listener;
import com.qualitylogic.openadr.core.channel.util.TraceUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

/**
 * HTTP implementation of the channel Listener. 
 */
public class HttpListener implements Listener {
	
	private Component component;
	
	private final Map<String, Handler> handlers = new HashMap<>();
	
	@Override
	public void start(final ServiceType serviceType) {
		// CHANNEL logs
		// logger.log(Level.INFO, "HttpListener start serviceType=" + serviceType);

		// VEN/VTN logic
		String prefix = (serviceType == ServiceType.VEN) ? "Ven" : "Vtn";
		
		TraceUtil.turnOffJettyLogging();

		PropertiesFileReader properties = new PropertiesFileReader();
		int port = Integer.parseInt(properties.get("HTTP_" + prefix + "URL_Port"));
		String resource = PropertiesFileReader.getOpenADRProfileBRoot();
		
		if (properties.isSecurity_Enabled()) {
			Engine.getInstance().getRegisteredServers().add(0, new HttpsServerHelper(null));

			component = new Component();
			Server server = component.getServers().add(Protocol.HTTPS, port);
			component.getDefaultHost().attach(resource, new RestletApplication(handlers));
		        
			RestletUtil.setupServerSslContext(serviceType, server.getContext());
		} else {
			Server server = new Server(Protocol.HTTP);
			Engine.getInstance().getRegisteredServers().add(0, new HttpServerHelper(server));

			component = new Component();
			component.getServers().add(Protocol.HTTP, port);
			component.getDefaultHost().attach(resource, new RestletApplication(handlers));
		}

		try {
			component.start();
		} catch (Exception e) {
			throw new IllegalStateException("Failed to start Restlet component.", e);
		}
	}

	@Override
	public void stop() {
		// logger.log(Level.INFO, "HttpListener stop");
		
		if (component != null) {
			try {
				component.stop();
			} catch (Exception e) {
				throw new IllegalStateException("Failed to stop Restlet component.");
			}
			
			component = null;
		} else {
			logger.log(Level.WARNING, "Called stop without calling start first.");
		}
	}

	/**
	 * Adds the handler implementation for the specified path resource.
	 */
	@Override
	public void addHandler(String resource, Handler handler) {
		handlers.put(resource, handler);
	}
	
	private static final Logger logger = Logger.getLogger(HttpListener.class.getName());
}
