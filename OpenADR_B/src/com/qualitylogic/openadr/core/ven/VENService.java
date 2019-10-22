package com.qualitylogic.openadr.core.ven;

import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.channel.Listener;
import com.qualitylogic.openadr.core.channel.factory.ChannelFactory;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.ven.channel.EiRegisterPartyVENHandler;
import com.qualitylogic.openadr.core.ven.channel.EiReportVENHandler;
import com.qualitylogic.openadr.core.ven.channel.ResourceHandler;
import com.qualitylogic.openadr.core.ven.channel.VENHandler;

public class VENService {

	/*
	static JettyServerHelper jettyServerHelper = null;
	static Component component = null;
	*/

	private static Listener listener;
	
	public static void startVENService() throws Exception {
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

    	listener = ChannelFactory.getListener();
    	
    	if (propertiesFileReader.isXmppTransportType()) {
	    	listener.addHandler(propertiesFileReader.getXmppVENResourceName(), new ResourceHandler());
    	} else {
    		listener.addHandler("EiReport", new EiReportVENHandler());
    		listener.addHandler("EiEvent", new VENHandler());
    		listener.addHandler("EiRegisterParty", new EiRegisterPartyVENHandler());
    	}
    	
    	listener.start(ServiceType.VEN);

		/*
		System.setProperty("org.mortbay.log.class",
				"com.qualitylogic.openadr.util.NoLog");
		NoLog noLogger = new NoLog();
		org.eclipse.jetty.util.log.Log.setLog(noLogger);

		String venPort = propertiesFileReader.get("VenURL_Port");
		String venURLResource = PropertiesFileReader.getOpenADRProfileBRoot();

		if(propertiesFileReader.isSecurity_Enabled()){
			 HttpsServerHelper jettyServerHelper = new HttpsServerHelper(null);
		        Engine.getInstance().getRegisteredServers().add(0, jettyServerHelper);

		        component = new Component();
		        Server server = component.getServers().add(Protocol.HTTPS, Integer.parseInt(venPort));
		        component.getDefaultHost().attach(venURLResource, new VENApplication());
		    	setUpSSL(server.getContext());	
			
		}else{
			org.restlet.Server embedingJettyServer = new org.restlet.Server(
					Protocol.HTTP);
	        component = new Component();
			component.getDefaultHost().attach(venURLResource, new VENApplication());
			HttpServerHelper jettyServerHelper = new HttpServerHelper(
					embedingJettyServer);
			Engine.getInstance().getRegisteredServers().add(0, jettyServerHelper);
			component.getServers().add(Protocol.HTTP, Integer.parseInt(venPort));
		}
		
		component.start();
		*/
	}

	public static void stopVENService() throws Exception {
		if (listener != null) {
			listener.stop();
		}

    	/*
		if (component != null) {
			component.stop();
		}
		*/

	}

	/*
	public static void main(String[] args) throws Exception {

		startVENService();
		System.out.println("Done");
	}


	   static void setUpSSL(Context workingCtx) {
	    	PropertiesFileReader properties=new PropertiesFileReader();
	    	
	    	if(properties.getSecurity_Debug()!=null && properties.getSecurity_Debug().equals("true") ){
	    		System.setProperty("javax.net.debug", "SSL");
	    	}
	    	       	   
	    	String sessionTLS[] = TestSession.getServerTLSVersion();
			
			if(sessionTLS==null){
				sessionTLS=properties.getTLSVersion();
			}
			
			
	    	if(sessionTLS!=null){

	 
	    		for(String eachTLSVersion:sessionTLS){
	    			
	    			workingCtx.getParameters().add("enabledProtocols", eachTLSVersion);
	    			
	    			String []ciphers=null;
	    			if(eachTLSVersion.equals("TLSv1")){
	    				String tls10Cipher[]=TestSession.getServerTLS10Ciphers();
	    				if(tls10Cipher==null){
	    					tls10Cipher=properties.getServer_TLS10_Ciphers();
	    				}
	    				ciphers=tls10Cipher;
	    			}else if(eachTLSVersion.equals("TLSv1.2")){
	    				String tls12Cipher[]=TestSession.getServerTLS12Ciphers();
	    				if(tls12Cipher==null){
	    					tls12Cipher=properties.getServer_TLS12_Ciphers();;
	    				}
	    				ciphers=tls12Cipher;
	    			}
	    			
	    			for(String eachCipher:ciphers){
	    		 		if(!eachCipher.trim().equals("NONE")){
	    		 			workingCtx.getParameters().add("enabledCipherSuites", eachCipher);	   
	    		 		}
	    			}
	    			
	    		}
	    	}
	    	
	    	if(properties.getTestHarnessVTN_trustStorePath()!=null){	
	    		System.setProperty("javax.net.ssl.trustStore",properties.getTestHarnessVEN_trustStorePath());
	    	}

   	 	   workingCtx.getParameters().add("keystorePath",properties.getTestHarnessVEN_keystorePath());             
           workingCtx.getParameters().add("keystorePassword", properties.getTestHarnessVEN_keystorePassword());
           workingCtx.getParameters().add("keystoreType", properties.getTestHarnessVEN_keystoreType());
           workingCtx.getParameters().add("keyPassword", properties.getTestHarnessVEN_keystorePassword());
	    	
	    	workingCtx.getParameters().add("needClientAuthentication", properties.getTestHarnessVEN_needClientAuth());
	       
	        
	    }
    */
}
