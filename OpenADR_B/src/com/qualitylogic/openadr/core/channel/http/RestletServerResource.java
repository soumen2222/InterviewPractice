package com.qualitylogic.openadr.core.channel.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.restlet.data.MediaType;
import org.restlet.engine.header.Header;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;
import org.w3c.dom.Document;

import com.qualitylogic.openadr.core.channel.Handler;
import com.qualitylogic.openadr.core.channel.util.TraceUtil;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.OadrUtil;

/**
 * Restlet ServerResource that delegates implementation to channel handlers.
 */
public class RestletServerResource extends ServerResource {
	
/*	@Post("application/xml")
    public String postHandler(final String data) {
		RestletApplication application = (RestletApplication) this.getApplication();
		String resource = getReference().getLastSegment();
		Handler handler = application.getHandler(resource);
		
		if (handler == null) {
			throw new IllegalStateException("Using unknown resource=" + resource);
		}
		
		TraceUtil.logRequest(getRequest());
		String result = handler.handle(data);
		TraceUtil.logResponse(getResponse());
		
		
		return result;
    }*/

	@Post("application/xml")
    public Representation postHandler(final String data) {
		RestletApplication application = (RestletApplication) this.getApplication();
		String resource = getReference().getLastSegment();
		Handler handler = application.getHandler(resource);
		
		DomRepresentation representation = null;
		
		
		if (handler == null) {
			throw new IllegalStateException("Using unknown resource=" + resource);
		}
		
		TraceUtil.logRequest(getRequest());
		
		Series<Header> headers = (Series<Header>) getRequest().getAttributes().get("org.restlet.http.headers");

		ConformanceRuleValidator.validateRequestHeaders(headers);
		
		String result = handler.handle(data);
		try {
			if (StringUtils.isBlank(result)) {
				representation = new DomRepresentation(MediaType.APPLICATION_XML);
				representation.setSize(0);
				return representation;
			}
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(false);
			representation = new DomRepresentation(MediaType.APPLICATION_XML);
			DocumentBuilder builder = null;
			builder = factory.newDocumentBuilder();

			Document document = builder.parse(new ByteArrayInputStream(
					result.getBytes()));
			representation.setDocument(document);
			long size = OadrUtil.getSize(document);
			representation.setSize(size);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TraceUtil.logResponse(getResponse());
		
		
		return representation;
    }
}
