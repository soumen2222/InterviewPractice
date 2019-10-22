package com.qualitylogic.openadr.core.channel.util;

import java.util.Collection;

import org.jivesoftware.smack.packet.Packet;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.engine.header.Header;
import org.restlet.util.Series;

import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;
import com.qualitylogic.openadr.core.util.Direction;
import com.qualitylogic.openadr.core.util.NoLog;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.OadrValidationEventHandler;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public final class TraceUtil {
	private TraceUtil() {
	}

	public static void traceRequest(ServiceType serviceType, final String target, final String data) {
		// VEN/VTN logic
		String targetLabel = (serviceType == ServiceType.VEN) ? "VTN" : "VEN";
		try {
			Trace trace = TestSession.getTraceObj();
			if (trace != null) {
				StringBuffer buff = trace.getLogFileContentTrace();
				buff.append("\n" + serviceType + " -> " + targetLabel);
				buff.append("\n----------------REQUEST---------------------");
				LogHelper.logCurrentTime();
				buff.append("\n" + targetLabel + ": " + target + "\n").append("\n");
				buff.append(com.qualitylogic.openadr.core.util.OadrUtil.formatXMLAsString(data)).append("\n");
				
				OadrValidationEventHandler.checkForSchemaValidationError(data, serviceType,Direction.Send);

				if (serviceType == ServiceType.VTN) { 
					@SuppressWarnings("rawtypes")
					Class objectType = SchemaHelper.getObjectType(data);
					if (objectType.equals(OadrDistributeEventType.class)) {
						OadrDistributeEventType oadrDistributeEventReceived = DistributeEventSignalHelper.createOadrDistributeEventFromString(data);
						VtnToVenClient.addOadrDistributeEvntSent(oadrDistributeEventReceived);
					}
				}
				
				Class reqObjectType = SchemaHelper.getObjectType(data);				
				if(reqObjectType!=null && reqObjectType==OadrCreateReportType.class){
					OadrCreateReportType createReportSent=CreateReportEventHelper.createOadrCreateReportFromString((String)data);
					TestSession.addOadrCreateReportTypeSent(createReportSent);
				}else if(reqObjectType!=null && reqObjectType==OadrUpdateReportType.class){
					OadrUpdateReportType updateReportSent=UpdateReportEventHelper.createReportFromString((String)data);
					TestSession.addOadrUpdateReportTypeSentList(updateReportSent);
				}
			}
			
			System.out.print("\n" + targetLabel + ": " + target + "\n");

			String reqObjectType = SchemaHelper.getEventTypeName(data);
			System.out.print("\n<- " + reqObjectType + " Request Posted.\n");

		} catch (Exception e) {
			throw new IllegalStateException("Failed to trace request.", e);
		}
	}

	public static void traceResponse(ServiceType serviceType, final String target, final String data, final String representationRep, final Response responseObj) {
		// VEN/VTN logic
		String targetLabel = (serviceType == ServiceType.VEN) ? "VTN" : "VEN";
		
		try {
			Trace trace = TestSession.getTraceObj();
			
			if (trace != null) {
				StringBuffer buff = trace.getLogFileContentTrace();
				buff.append("\n" + serviceType + " <- " + targetLabel);
				buff.append("\n----------------RESPONSE---------------------");
				LogHelper.logCurrentTime();
				buff.append("\n----Header---\n");

				if (responseObj != null) {
					@SuppressWarnings("unchecked")
					Series<Header> headers = (Series<Header>) responseObj.getAttributes().get("org.restlet.http.headers");
					for (Header eachParam : headers) {
						buff.append(eachParam + "\n");
					}
					buff.append("\n");
				}
				
				OadrUtil.writeHTTPHeaderDisabledMessage();
			
				if (!StringUtil.isBlank(representationRep)) {
					buff.append(com.qualitylogic.openadr.core.util.OadrUtil.formatXMLAsString(representationRep));
					OadrValidationEventHandler.checkForSchemaValidationError(representationRep, serviceType,Direction.Receive);

					if (serviceType == ServiceType.VEN) { 
						@SuppressWarnings("rawtypes")
						Class objectType = SchemaHelper.getObjectType(data);
						if (objectType != null && objectType.equals(OadrDistributeEventType.class)) {
							OadrDistributeEventType oadrDistributeEventReceived = DistributeEventSignalHelper.createOadrDistributeEventFromString(data);
							VenToVtnClient.addOadrDistributeEvntReceived(oadrDistributeEventReceived);
						}
					}
				}

				buff.append("\n");
				trace.setResult("NA");
			}

			String resObjectType = SchemaHelper.getEventTypeName(representationRep);
			if (resObjectType.isEmpty()) {
				resObjectType = "Empty";
			}
			System.out.print("\n-> " + resObjectType + " Response Received.\n");
		} catch (Exception e) {
			throw new IllegalStateException("Failed to trace request.", e);
		}
	}

	public static void turnOffJettyLogging() {
		System.setProperty("org.mortbay.log.class", "com.qualitylogic.openadr.util.NoLog");
		NoLog noLogger = new NoLog();
		org.eclipse.jetty.util.log.Log.setLog(noLogger);
	}

	public static void logRequest(final Request request) {
		Trace trace = TestSession.getTraceObj();
		if (trace != null) {
			StringBuffer buff = trace.getLogFileContentTrace();
			buff.append("\n-------------HTTP REQUEST-------------------");
			LogHelper.logCurrentTime();
			buff.append("\n----Client Address---\n");
			buff.append(request.getClientInfo().getAddress() + "\n\n");
	
			@SuppressWarnings("unchecked")
			Series<Header> headers = (Series<Header>) request.getAttributes().get("org.restlet.http.headers");
			
			buff.append("----Header---\n");
	
			for (Header eachParam : headers) {
				buff.append(eachParam + "\n");
			}
			
			OadrUtil.writeHTTPHeaderDisabledMessage();
		}
	}
	
	public static void logResponse(final Response response) {
		Trace trace = TestSession.getTraceObj();
		if (trace != null) {
			StringBuffer buff = trace.getLogFileContentTrace();
			buff.append("\n-------------HTTP RESPONSE------------------");
			buff.append("\nHTTP Status :" + response.getStatus() + " \n");
			buff.append("\n");
		}
	}

	public static void logRequest(final Packet packet) {
		Trace trace = TestSession.getTraceObj();
		if (trace != null) {
			StringBuffer buff = trace.getLogFileContentTrace();
			buff.append("\n-------------XMPP REQUEST-------------------");
			LogHelper.logCurrentTime();
			buff.append("\n----Client JID---\n");
			buff.append(packet.getFrom() + "\n\n");
	
			buff.append("----Properties---\n");
			buff.append("[to: " + packet.getTo() + "]\n");
			buff.append("[packetID: " + packet.getPacketID() + "]\n");
			
			Collection<String> propertyNames = packet.getPropertyNames();
			for (String propertyName : propertyNames) {
				buff.append("[" + propertyName + ": " + packet.getProperty(propertyName) + "]\n");
			}
		}
	}
	
	public static void logResponse(final Packet packet) {
		Trace trace = TestSession.getTraceObj();
		if (trace != null) {
			StringBuffer buff = trace.getLogFileContentTrace();
			buff.append("\n-------------XMPP RESPONSE------------------");
			buff.append("\nXMPP Status :OK\n");
			buff.append("\n");
		}
	}
}
