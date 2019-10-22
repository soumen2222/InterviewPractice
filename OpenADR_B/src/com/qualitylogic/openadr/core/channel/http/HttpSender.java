package com.qualitylogic.openadr.core.channel.http;

import java.util.logging.Logger;

import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.engine.header.Header;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.util.Series;

import com.qualitylogic.openadr.core.base.Debug;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.channel.Sender;
import com.qualitylogic.openadr.core.channel.util.StringUtil;
import com.qualitylogic.openadr.core.channel.util.TraceUtil;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

/**
 * HTTP implementation of the channel Sender. 
 */
public class HttpSender implements Sender {
	
	@Override
	public String send(final ServiceType serviceType, final String to, final String message) {
		// CHANNEL logs
		// logger.log(Level.INFO, "Using HttpSender serviceType=" + serviceType + " to=" + to);
		
		if (StringUtil.isBlank(to) || StringUtil.isBlank(message)) {
			throw new IllegalArgumentException("The 'to' and 'message' parameters are required.");
		}
		
		PropertiesFileReader properties = new PropertiesFileReader();
		
		// TODO: rename VTN_EiEvent property to VTN_EiEventURL to remove this if condition
		// VEN/VTN logic
		/*
		String targetPrefix = (serviceType == ServiceType.VEN) ? "VTN" : "VEN";
		if ((serviceType == ServiceType.VTN) && (to.equals("EiEvent"))) {
			url = properties.get(targetPrefix + "_EiEvent");
		} else {
			if (to.equals("EiRegisterParty")) {
				url = properties.get(targetPrefix + "_EiRegistrationURL");
			} else {
				url = properties.get(targetPrefix + "_" + to + "URL");
			}
		}
		if (url == null) {
			throw new IllegalStateException("Cannot find url for " + to);
		}
		*/
		String url = null;
		if (serviceType == ServiceType.VEN) {
			switch (to) {
			case "OadrPoll" : url = properties.getClient_VTN_PollURL(); break;
			case "EiOpt" : url = properties.getClient_VTN_OptURL(); break;
			case "EiEvent" : url = properties.getClient_VTN_EventURL(); break;
			case "EiRegisterParty" : url = properties.getClient_VTN_RegisterPartyURL(); break;
			case "EiReport" : url = properties.getClient_VTN_ReportURL(); break;
			default: throw new IllegalStateException("Unknown to=" + to);
			}
		} else {
			switch (to) {
			case "EiEvent" : url = properties.getClient_VEN_EventURL(); break;
			case "EiRegisterParty" : url = properties.getClient_VEN_RegisterPartyURL(); break;
			case "EiReport" : url = properties.getClient_VEN_ReportURL(); break;
			default: throw new IllegalStateException("Unknown to=" + to);
			}
		}
		
		// SSL
		Client client = null;
		if (properties.isSecurity_Enabled()) {
			Context context = new Context();
			RestletUtil.setupClientSslContext(serviceType, context);
			client = new Client(context, Protocol.HTTPS);
			
			url = url.replace("http:", "https:");
		} else {
			client = new Client(Protocol.HTTP);
		}
		client.setConnectTimeout(CONNECT_TIMEOUT);

		ClientResource resource = new ClientResource(url);
		resource.setNext(client);
		
		// Authentication
		String loginName = properties.get("Login_Name");
		String loginPassword = properties.get("Login_Password");
		if (!StringUtil.isBlank(loginName) && !StringUtil.isBlank(loginPassword)) {
			resource.setChallengeResponse(ChallengeScheme.HTTP_BASIC, loginName, loginPassword);
		}
		
		String response = null;
		try {
			// Schema testing
			// message = message.replace("<ns3:requestID>", "<bug /><ns3:requestID>");

			TraceUtil.traceRequest(serviceType, url, message);
			
			
			if (TestSession.isValidationErrorFound()) {
				return "";
			}
			
			StringRepresentation input = new StringRepresentation(message, MediaType.APPLICATION_XML);
			Representation output = resource.post(input, MediaType.ALL);
			response = output.getText();
			
			TraceUtil.traceResponse(serviceType, url, message, response, resource.getResponse());
			
			if(response!=null && response.trim().length()>0){
			    Series<Header> headers = (Series<Header>)
			resource.getResponse().getAttributes().get("org.restlet.http.headers");
			    ConformanceRuleValidator.validateResponseHeaders(headers);
			}

			
		} catch (Exception e) {
			if (TestSession.isResponseExpected()) {
				if (Debug.printHttpSenderStrackTrace) {
					e.printStackTrace();
				}
				
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Connection failed");
				TestSession.setValidationErrorFound(true);
				LogHelper.addTrace(e.getMessage());
				e.printStackTrace();
			}
		}
		
		return response;
	}

	@Override
	public void stop() {
		// do nothing
	}

	private static final int CONNECT_TIMEOUT = 50_000;

	private static final Logger logger = Logger.getLogger(HttpSender.class.getName());
}
