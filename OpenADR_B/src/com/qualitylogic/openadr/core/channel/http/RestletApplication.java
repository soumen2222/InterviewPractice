package com.qualitylogic.openadr.core.channel.http;

import java.util.Map;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import com.qualitylogic.openadr.core.channel.Handler;

/**
 * Restlet Application that maps channel handlers to Reslet routes.
 */
public class RestletApplication extends Application {
	
	private final Map<String, Handler> handlers;
	
	public RestletApplication(Map<String, Handler> handlers) {
		this.handlers = handlers;
	}
	
	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());

		for (Map.Entry<String, Handler> handlerEntry : handlers.entrySet()) {
			router.attach("/" + handlerEntry.getKey(), RestletServerResource.class);
		}
		
		return router;
	}
	
	public Handler getHandler(String resource) {
		return handlers.get(resource);
	}
}
