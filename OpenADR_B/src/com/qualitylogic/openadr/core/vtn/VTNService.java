package com.qualitylogic.openadr.core.vtn;

import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.channel.BusinessLogic;
import com.qualitylogic.openadr.core.channel.Listener;
import com.qualitylogic.openadr.core.channel.factory.ChannelFactory;
import com.qualitylogic.openadr.core.oadrpoll.channel.OadrPollHandler;
import com.qualitylogic.openadr.core.vtn.channel.EiOptVTNHandler;
import com.qualitylogic.openadr.core.vtn.channel.EiRegisterPartyVTNHandler;
import com.qualitylogic.openadr.core.vtn.channel.EiReportVTNHandler;
import com.qualitylogic.openadr.core.vtn.channel.VTNHandler;

public class VTNService {

	/*
	static JettyServerHelper jettyServerHelper = null;
	static Component component = null;
	*/
	//static VTNToPollServiceThread vtnToPollServiceThread= new VTNToPollServiceThread();

	private static Listener listener;
	
	public static void startVTNService() throws Exception {
    	listener = ChannelFactory.getListener();
    	listener.addHandler("EiReport", new EiReportVTNHandler());
		listener.addHandler("EiEvent", new VTNHandler());
		listener.addHandler("EiOpt", new EiOptVTNHandler());
		listener.addHandler("EiRegisterParty", new EiRegisterPartyVTNHandler());
		if (BusinessLogic.isOadrPollAllowed()) {
			listener.addHandler("OadrPoll", new OadrPollHandler());
		}
    	
    	listener.start(ServiceType.VTN);

		/*
		System.setProperty("org.mortbay.log.class",
			"com.qualitylogic.openadr.util.NoLog");
		NoLog noLogger = new NoLog();
		org.eclipse.jetty.util.log.Log.setLog(noLogger);


		
	

		String vtnPort = propertiesFileReader.get("VtnURL_Port");
		component = new Component();

		String vtnURLResource = PropertiesFileReader.getOpenADRProfileBRoot();

		if(propertiesFileReader.isSecurity_Enabled()){

			 HttpsServerHelper jettyServerHelper = new HttpsServerHelper(null);
		        Engine.getInstance().getRegisteredServers().add(0, jettyServerHelper);

		        component = new Component();
		        Server server = component.getServers().add(Protocol.HTTPS, Integer.parseInt(vtnPort));
		        component.getDefaultHost().attach(vtnURLResource, new VTNApplication());
		        
			setUpSSL(server.getContext());	
		}else{
			org.restlet.Server embedingJettyServer = new org.restlet.Server(
					Protocol.HTTP);
			component = new Component();
			component.getDefaultHost().attach(vtnURLResource, new VTNApplication());
			HttpServerHelper jettyServerHelper = new HttpServerHelper(
					embedingJettyServer);
			Engine.getInstance().getRegisteredServers().add(0, jettyServerHelper);
			component.getServers().add(Protocol.HTTP, Integer.parseInt(vtnPort));
		}

		component.start();
		*/
	}

	public static void stopVTNService() throws Exception {
    	if (listener != null) {
    		listener.stop();
    	}

		/*
		Thread.sleep(1000);
		
		if (component != null) {
			component.stop();
			
		}
		*/
	}

	/*
	public static void main(String[] args) throws Exception {  
        // Create the HTTP server and listen on port 8182  
        new Server(Protocol.HTTP, 8182, VTNServerResource.class).start();  
    }  
  
	
    static void setUpSSL(Context workingCtx) {
    	PropertiesFileReader properties=new PropertiesFileReader();
    	

    	if(properties.getSecurity_Debug()!=null && properties.getSecurity_Debug().equals("true") ){
    		System.setProperty("javax.net.debug", "SSL");
    		//System.setProperty("javax.net.debug", "ALL");
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
    		System.setProperty("javax.net.ssl.trustStore",properties.getTestHarnessVTN_trustStorePath());
    	}

    	workingCtx.getParameters().add("keystorePath",properties.getTestHarnessVTN_keystorePath());
        workingCtx.getParameters().add("keystorePassword", properties.getTestHarnessVTN_keystorePassword());
        workingCtx.getParameters().add("keystoreType", properties.getTestHarnessVTN_keystoreType());
        workingCtx.getParameters().add("keyPassword", properties.getTestHarnessVTN_keystorePassword());
        
        workingCtx.getParameters().add("needClientAuthentication", properties.getTestHarnessVTN_needClientAuth());
    }
    */
}
