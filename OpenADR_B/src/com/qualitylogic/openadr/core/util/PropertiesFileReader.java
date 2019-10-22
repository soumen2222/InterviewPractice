package com.qualitylogic.openadr.core.util;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.exception.NotApplicableException;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;

public class PropertiesFileReader {

	static Properties props = new Properties();
	InputStream inputStream = null;
	static PropertiesFileReader propFilePreader=new PropertiesFileReader();
    
	public static void main(String []args){
		String httpVENBaseURL = new PropertiesFileReader().get("HTTP_VEN_BaseURL");
		
		if(httpVENBaseURL==null || httpVENBaseURL.trim().length()<1){
			TestSession.setValidationErrorFound(true);
			String errorMessage="********************HTTP_VEN_BaseURL is not specified in properties file********************";
			
			LogHelper.addTrace(errorMessage);
			System.out.println(errorMessage);
			
			LogHelper.setResult(LogResult.NA);
			XMLLogHelper.writeTestLog(null);
			System.exit(-1);
			
		}
		
		if( httpVENBaseURL.charAt(httpVENBaseURL.length() -1) == '/')
			httpVENBaseURL = httpVENBaseURL.substring(0, httpVENBaseURL.length()-1);

		System.out.println(httpVENBaseURL);
	}
	
	//VEN_EiEvent=http://127.0.0.1:8081/OpenADR2/Simple/2.0b/EiEvent
	//VEN_EiRegistrationURL=http://127.0.0.1:8081/OpenADR2/Simple/2.0b/EiRegisterParty
	//VEN_EiReportURL=http://127.0.0.1:8081/OpenADR2/Simple/2.0b/EiReport

	//VTN_OadrPollURL=http://127.0.0.1:8080/OpenADR2/Simple/2.0b/OadrPoll
	//VTN_EiOptURL=http://127.0.0.1:8080/OpenADR2/Simple/2.0b/EiOpt
	//VTN_EiRegistrationURL=http://127.0.0.1:8080/OpenADR2/Simple/2.0b/EiRegisterParty
	//VTN_EiReportURL=http://127.0.0.1:8080/OpenADR2/Simple/2.0b/EiReport
	//VTN_EiEventURL=http://127.0.0.1:8080/OpenADR2/Simple/2.0b/EiEvent

	//Client_VEN_BaseURL=http://127.0.0.1:8081
	//Client_VTN_BaseURL=http://127.0.0.1:8080

	private String getHttpVEN_BaseURL(){	
		XMLDBUtil xmlDBUtil = new XMLDBUtil();
		if(!OadrUtil.isCreatePartyRegistration_PullModel()){
/*			if(xmlDBUtil.getTransportAddress()==null || xmlDBUtil.getTransportAddress().trim().length()<1){
				TestSession.setValidationErrorFound(true);
				String errorMessage="********************Conformance check failed : Registration is not active for PUSH********************";
				
				LogHelper.addTrace(errorMessage);
				System.out.println(errorMessage);
				
				LogHelper.setResult(LogResult.FAIL);
				new XMLLogHelper().writeTestLog(null);
				System.exit(-1);
			}
			
*/			String transportAddress = new XMLDBUtil().getTransportAddress();
			if (com.qualitylogic.openadr.core.channel.util.StringUtil.isBlank(transportAddress)) {
				// possible during negative test cases
				transportAddress = get("HTTP_VEN_BaseURL");
			}
			return transportAddress;
		}
		
		String httpVENBaseURL = get("HTTP_VEN_BaseURL");
		
		if(httpVENBaseURL==null || httpVENBaseURL.trim().length()<1){
			TestSession.setValidationErrorFound(true);
			String errorMessage="********************HTTP_VEN_BaseURL is not specified in properties file********************";
			
			LogHelper.addTrace(errorMessage);
			System.out.println(errorMessage);
			
			LogHelper.setResult(LogResult.NA);
			XMLLogHelper.writeTestLog(null);
			System.exit(-1);
			
		}
		
		if( httpVENBaseURL.charAt(httpVENBaseURL.length() -1) == '/')
			httpVENBaseURL = httpVENBaseURL.substring(0, httpVENBaseURL.length()-1);
		
		return httpVENBaseURL;
	}
	
	
	
	/**
	 * This is the XMPP version of getVEN_BaseURL()
	 */
	public String getClient_VEN_JID(){	
		if(!OadrUtil.isCreatePartyRegistration_PullModel()){
			String transportAddress = new XMLDBUtil().getTransportAddress();
			if (!transportAddress.contains(getDutXmppAddress()) && !transportAddress.contains(getThXmppAddress())) {
				System.out.println("WARN: transportAddress=" + transportAddress + " does not match " + getDutXmppAddress() + ", " + getThXmppAddress() + ". Check XML database.");
			}
			
			if (StringUtils.isBlank(transportAddress)) {
				// possible during negative test cases
				transportAddress = getXmppVENUsername() + "@" + getDutXmppAddress() + "/" + getXmppVENResourceName();
			}
			
			return transportAddress;
		}
		
		return getXmppVENUsername() + "@" + getDutXmppAddress() + "/" + getXmppVENResourceName();
	}

	private String getHttpVTN_BaseURL(){
		String httpVTNBaseURL = get("HTTP_VTN_BaseURL");
		
		if(httpVTNBaseURL==null || httpVTNBaseURL.trim().length()<1){
			TestSession.setValidationErrorFound(true);
			String errorMessage="********************HTTP_VTN_BaseURL is not specified in properties file********************";
			
			LogHelper.addTrace(errorMessage);
			System.out.println(errorMessage);
			
			LogHelper.setResult(LogResult.NA);
			XMLLogHelper.writeTestLog(null);
			System.exit(-1);
			
		}
		
		if( httpVTNBaseURL.charAt(httpVTNBaseURL.length() -1) == '/')
			httpVTNBaseURL = httpVTNBaseURL.substring(0, httpVTNBaseURL.length()-1);
		
		return httpVTNBaseURL;
	}
	
	/////////////////////////////////////////////////////////////////////
	//VEN_EiEvent
	public String getClient_VEN_EventURL(){
		return getHttpVEN_BaseURL()+getOpenADRService_Event();
	}
	
	//VEN_EiRegistrationURL
	public String getClient_VEN_RegisterPartyURL(){	
		return getHttpVEN_BaseURL()+getOpenADRService_RegisterParty();
	}
		
	//VEN_EiReportURL
	public String getClient_VEN_ReportURL(){	
		return getHttpVEN_BaseURL()+getOpenADRService_Report();
	}
	
	//VTN_OadrPollURL
	public String getClient_VTN_PollURL(){	
		return getHttpVTN_BaseURL()+getOpenADRService_Poll();
	}
	
	//VTN_EiOptURL
	public String getClient_VTN_OptURL(){	
		return getHttpVTN_BaseURL()+getOpenADRService_Opt();
	}	
	
	//VTN_EiRegistrationURL
	public String getClient_VTN_RegisterPartyURL(){	
		return getHttpVTN_BaseURL()+getOpenADRService_RegisterParty();
	}	
	
	//VTN_EiReportURL
	public String getClient_VTN_ReportURL(){	
		return getHttpVTN_BaseURL()+getOpenADRService_Report();
	}
	
	//VTN_EiEventURL
	public String getClient_VTN_EventURL(){	
		return getHttpVTN_BaseURL()+getOpenADRService_Event();
	}
		
	////////////////////////////////////////////////////////////////////////////
	
	public static String getOpenADRProfileBRoot(){	
		return "/OpenADR2/Simple/2.0b";
	}

	public static String getOpenADRService_Event(){
		return getOpenADRProfileBRoot()+"/EiEvent";
	}
	
	public static String getOpenADRService_RegisterParty(){
		return getOpenADRProfileBRoot()+"/EiRegisterParty";
	}
	
	public static String getOpenADRService_Report(){
		return getOpenADRProfileBRoot()+"/EiReport";
	}
	
	
	public static String getOpenADRService_Poll(){
		
		return getOpenADRProfileBRoot()+"/OadrPoll";
	}

	public static String getOpenADRService_Opt(){
		
		return getOpenADRProfileBRoot()+"/EiOpt";
	}

	/*
	public static String getVENURL(VENServiceType venServiceType){
		
		if(venServiceType.equals(VENServiceType.EiEvent)){
			return ven_BaseURL+venURL_Resource+"/EiEvent";
		}else if(venServiceType.equals(VENServiceType.EiRegistration)){
			return ven_BaseURL+venURL_Resource+"/EiRegisterParty";
		}else if(venServiceType.equals(VENServiceType.EiReport)){
			return ven_BaseURL+venURL_Resource+"/EiReport";			
		}
		
		
		return "";
	}
	

	public static String getVTNURL(VTNServiceType vtnServiceType){
		
		if(vtnServiceType.equals(VTNServiceType.EiEvent)){
			return ven_BaseURL+venURL_Resource+"/EiEvent";
		}else if(vtnServiceType.equals(VTNServiceType.EiRegistration)){
			return ven_BaseURL+venURL_Resource+"/EiRegisterParty";
		}else if(vtnServiceType.equals(VTNServiceType.EiReport)){
			return ven_BaseURL+venURL_Resource+"/EiReport";			
		}else if(vtnServiceType.equals(VTNServiceType.OadrPoll)){
			return ven_BaseURL+venURL_Resource+"/OadrPoll";			
		}else if(vtnServiceType.equals(VTNServiceType.EiOpt)){
			return ven_BaseURL+venURL_Resource+"/EiOpt";			
		}
			
		return "";
	}*/
	public static String getRoot(){
		URL resource = Thread.currentThread().getContextClassLoader().getResource(".");
		String path = resource.getPath();
		return path.replace("%20", " ");
	}
	
	public PropertiesFileReader() {
		String fs = System.getProperty("file.separator");
		inputStream = this
				.getClass()
				.getClassLoader()
				.getResourceAsStream(
						"properties" + fs + "openadrconfig.properties");
		try {
			props.load(inputStream);
			if (getProperties_File() != null
					&& getProperties_File().trim().length() > 0) {
				String resourceName = "properties" + fs + getProperties_File().trim();
				// System.out.println("Loading " + resourceName);
				inputStream = this
						.getClass()
						.getClassLoader()
						.getResourceAsStream(resourceName);
				props.load(inputStream);
				
				String securityMode = getSecurityMode();
				if (!StringUtils.isBlank(securityMode)) {
					resourceName = "properties" + fs + "security" + fs + securityMode.trim() + ".properties";

					// System.out.println("Loading " + resourceName);
					inputStream = this.getClass().getClassLoader().getResourceAsStream(resourceName);
					props.load(inputStream);
				} else {
					System.out.println("Security configuration is not defined.");
					System.exit(0);
				}
				
			} else {
				System.out
						.println("Properties_File is not defined in openadrconfig.properties");
				System.exit(0);
			}
		} catch (Exception e) {
			System.out
					.println("Unable to load Properties_File defined in openadrconfig.properties");
			System.exit(0);
		}
	}

	public String getProperties_File() {
		return get("Properties_File");
	}

	public String getLogPath() {
		return FileUtil.getCurrentPath() + "/log/";
	}
	
	public String getlogFileName() {
		return "logfile.xml";
		
		// return get("logFileName");
	}

	public String getQuery_VTNLogCount() {
		return get("Query_VTNLogCount");
	}

	public String getVtnURL() {
		return get("VtnURL");
	}

	
	public boolean isSecurity_Enabled(){

		if(TestSession.getSecurityEnabled()!=null){
			if(TestSession.getSecurityEnabled().equalsIgnoreCase("true")){
				return true;
			}else{
				return false;
			}
		}
		String securityEnbldInProp = get("Security_Enabled");
		if(securityEnbldInProp!=null && securityEnbldInProp.equalsIgnoreCase("true")){
			return true;
		}
		
		return false;
	}
	
	
	public String[] getTLSVersion(){

		
		String tls=get("TLS_Version");	
		if(tls!=null && tls.trim().length()>0){
			String tlsarr[] = tls.split(",");
			return tlsarr;
		}
		
		return null;	
	}
	
	public String[] getClient_TLS10_Ciphers(){
		
		String ciphers=get("Client_TLS10_Ciphers");	
		if(ciphers!=null && ciphers.trim().length()>0){
			String tlsarr[] = ciphers.split(",");
			return tlsarr;
		}
		
		return null;
	}

	public String[] getServer_TLS10_Ciphers(){
		String ciphers=get("Server_TLS10_Ciphers");
		if(ciphers!=null && ciphers.trim().length()>0){
			String tlsarr[] = ciphers.split(",");
			return tlsarr;
		}
		
		return null;
	}
	
	
	public String[] getClient_TLS12_Ciphers(){
		String ciphers=get("Client_TLS12_Ciphers");	
		if(ciphers!=null && ciphers.trim().length()>0){
			String tlsarr[] = ciphers.split(",");
			return tlsarr;
		}
		
		return null;
	}
	
	public String[] getServer_TLS12_Ciphers(){
		String ciphers=get("Server_TLS12_Ciphers");	
		if(ciphers!=null && ciphers.trim().length()>0){
			String tlsarr[] = ciphers.split(",");
			return tlsarr;
		}
		
		return null;
	}
	
	
	public String getSecurity_Debug(){
		if(TestSession.getSecurityDebug()!=null){
			return TestSession.getSecurityDebug();	
		}
		return get("Security_Debug");
	}
	/*
	public String getUse_Valid_Cert(){
		if(TestSession.getUseValidCert()!=null){
			return TestSession.getUseValidCert();	
		}
		return get("Use_Valid_Cert");
	}
	*/
	
	public String getVTN_trustStorePath() {
		return getRoot() + get("VTN_trustStorePath");
	}

	public String getVEN_trustStorePath() {
		return getRoot() + get("VEN_trustStorePath");
	}

	public String getVTN_truststorePassword() {
		return get("VTN_truststorePassword");
	}

	public String getVTN_needClientAuth() {
		return get("VTN_needClientAuth");
	}
				
	public String getVTN_keystorePath() {
		if (propFilePreader.isXmppTransportType()) {
			return getRoot() + get("VTN_keystorePath_XMPP");
		} else {
			return getRoot() + get("VTN_keystorePath_HTTP");
		}
	}		
	
	public String getVTN_keystorePassword() {
		return get("VTN_keystorePassword");
	}
			
	public String getVTN_keystoreType() {
		return get("VTN_keystoreType");
	}
	
	public String getVEN_keystorePath() {
		if (propFilePreader.isXmppTransportType()) {
			return getRoot() + get("VEN_keystorePath_XMPP");
		} else {
			return getRoot() + get("VEN_keystorePath_HTTP");
		}
	}		

	public String getVEN_keystorePassword() {
		return get("VEN_keystorePassword");
	}		

	public String getVEN_keystoreType() {
		return get("VEN_keystoreType");
	}		

	public String getVEN_needClientAuth() {
		return get("VEN_needClientAuth");
	}
	
	/*public String getVEN_ReportAvailableDateTime() {
		return get("VEN_ReportAvailableDateTime");
	}
	public String getVTN_ReportAvailableDateTime() {
		return get("VTN_ReportAvailableDateTime");
	}*/
	
	public String getVEN_truststorePassword() {
		return get("VEN_truststorePassword");
	}
	
	public String[] getEnabledClientCipherSuites(){
		ArrayList<String> enabledCipher=new ArrayList<String>();
		String []tlsVersions = getTLSVersion();
		for(String tlsVersion:tlsVersions){
			if(tlsVersion.equals("TLSv1")){
				String ciphers = get("Client_TLS10_Ciphers");
				String []ciphersarr =ciphers.split(",");
				for(String eachCipher:ciphersarr){
					enabledCipher.add(eachCipher);
				}

			}else if(tlsVersion.equals("TLSv1.2")){
				String ciphers = get("Client_TLS12_Ciphers");
				String []ciphersarr =ciphers.split(",");
				for(String eachCipher:ciphersarr){
					enabledCipher.add(eachCipher);
				}
				
			}
			
		}
		
		if(enabledCipher.size()<1) return null;
		String []ciphersEnabled=new String[enabledCipher.size()];
		int i=0;
		for(String eachCipher:enabledCipher){
			ciphersEnabled[i]=eachCipher;
			i++;
		}
		return ciphersEnabled;
	}
	
	public String[] getEnabledServerCipherSuites(){
		
		
		ArrayList<String> enabledCipher=new ArrayList<String>();
		
		String []tlsVersions = getTLSVersion();
		for(String tlsVersion:tlsVersions){
			if(tlsVersion.equals("TLSv1")){
				String ciphers = get("Server_TLS10_Ciphers");
				String []ciphersarr =ciphers.split(",");
				for(String eachCipher:ciphersarr){
					enabledCipher.add(eachCipher);
				}

			}else if(tlsVersion.equals("TLSv1.2")){
				String ciphers = get("Server_TLS12_Ciphers");
				String []ciphersarr =ciphers.split(",");
				for(String eachCipher:ciphersarr){
					enabledCipher.add(eachCipher);
				}
				
			}
			
		}
		
		if(enabledCipher.size()<1) return null;
		String []ciphersEnabled=new String[enabledCipher.size()];
		int i=0;
		for(String eachCipher:enabledCipher){
			ciphersEnabled[i]=eachCipher;
			i++;
		}
		return ciphersEnabled;
	}	
	
	public boolean getVtnClientVerifyHostName(){

		if(TestSession.getVtnClientVerifyHostName()!=null){
			if(TestSession.getVtnClientVerifyHostName().equalsIgnoreCase("false")){
				return true;
			}else{
				return false;
			}
		}
		String vtnClientVerifyHostName = get("vtnClientVerifyHostName");
		if(vtnClientVerifyHostName!=null && vtnClientVerifyHostName.equalsIgnoreCase("false")){
			return true;
		}
		
		return false;
	}
	
	
	public boolean getVenClientVerifyHostName(){

		if(TestSession.getVenClientVerifyHostName()!=null){
			if(TestSession.getVenClientVerifyHostName().equalsIgnoreCase("false")){
				return true;
			}else{
				return false;
			}
		}
		String venClientVerifyHostName = get("venClientVerifyHostName");
		if(venClientVerifyHostName!=null && venClientVerifyHostName.equalsIgnoreCase("false")){
			return true;
		}
		
		return false;
	}
		
	
	public String get(String key) {
		Object value = props.get(key);
		String valueToReturn = "";
		if (value != null) {
			valueToReturn = (String) value;
			valueToReturn = valueToReturn.trim();

		}

		return valueToReturn;
	}

	public String getProfile_A(){
		return get("Profile_A");
	}
	
	
	public String getProfile_A_Transport(){
		return get("Profile_A_Transport");
	}
	
	public String getProfile_B(){
		return get("Profile_B");
	}	

	public String getProfile_B_Transport(){
		return get("Profile_B_Transport");
	}	

	public String getXMLDBFile_VTN(){
		return FileUtil.getCurrentPath() + "/db/vtndb.xml";
	}	

	public String getXMLDBFile_VEN(){
		return FileUtil.getCurrentPath() + "/db/vendb.xml";
	}	
	
	public String getVenID(){
		if(!isUseStaticVENID()){
			String venID = new XMLDBUtil().getVENID();
			
			if (TestSession.isSwapCaseVenID()) {
				String swapCaseVenID = StringUtils.swapCase(venID);
				if (venID.equals(swapCaseVenID)) {
					throw new NotApplicableException("Slipping test case as venID has no alpha characters.");
				}
				venID = swapCaseVenID;
			}
			
			return venID;
		}
		
		return get("VEN_ID");
	}
			
	public String getVtnID() {
		String vtnID = get("VTN_ID");
		
		if (TestSession.isSwapCaseVtnID()) {
			String swapCaseVtnID = StringUtils.swapCase(vtnID);
			if (vtnID.equals(swapCaseVtnID)) {
				throw new NotApplicableException("Slipping test case as vtnID has no alpha characters.");
			}
			vtnID = swapCaseVtnID;
		}
		
		return vtnID;
	}

	public String getCreatePartyRegistration_ProfileName(){
		
		return get("CreatePartyRegistration_ProfileName");
	}
	
	public String getCreatePartyRegistrationTransportAddress(){
		if (isXmppTransportType()) {
			return getXmppVENUsername() + "@" + getDutXmppAddress() + "/" + getXmppVENResourceName();
		} else {
			String transportAddress = get("HTTP_VEN_BaseURL");
			if (isSecurity_Enabled()) {
				transportAddress = transportAddress.replace("http:", "https:");
			}
			
			return transportAddress;
		}
	}
	
/*	private String getXMPP_CreatePartyRegistration_TransportAddress(){
		
		return get("XMPP_CreatePartyRegistration_TransportAddress");
	}*/
	
/*	public boolean isCreatePartyRegistration_PullModel(){
	
		if(TestSession.getMode()!=null){
			if(TestSession.getMode().equals(ModeType.PULL)){
				return true;
			}else{
				return false;
			}
		}
		
		if(get("CreatePartyRegistration_PullModel")!=null && get("CreatePartyRegistration_PullModel").equalsIgnoreCase("true")){
			return true;
		}else if(get("CreatePartyRegistration_PullModel")!=null && get("CreatePartyRegistration_PullModel").equalsIgnoreCase("false")){
			return false;
		}
		
		System.out.println("Please set true or false in property  CreatePartyRegistration_PullModel");
		System.exit(0);

		return false;
	}*/
	
	public boolean getCreatePartyRegistration_ReportOnly(){
		
		String reportOnly = get("CreatePartyRegistration_ReportOnly");
		if(reportOnly!=null && reportOnly.length()>0 && reportOnly.equalsIgnoreCase("true")){
			return true;
		}
		
		return false;
	}

	public boolean getCreatePartyRegistration_XmlSignature(){
		
		String reportOnly = get("CreatePartyRegistration_XmlSignature");
		if(reportOnly!=null && reportOnly.length()>0 && reportOnly.equalsIgnoreCase("true")){
			return true;
		}
		
		return false;
	}
	
/*	public boolean getCreatePartyRegistration_HttpPullModel(){
		
		String reportOnly = get("CreatePartyRegistration_HttpPullModel");
		if(reportOnly!=null && reportOnly.length()>0 && reportOnly.equalsIgnoreCase("true")){
			return true;
		}
		
		return false;
	}
	*/
	
	public String getCreatePartyRegistration_oadrVenName(){
		
		return get("CreatePartyRegistration_oadrVenName");
	}
	
	public boolean isUseStaticVENID(){
		String useStaticVENID = get("UseStaticVENID");
		if(useStaticVENID!=null && useStaticVENID.length()>0 && useStaticVENID.equalsIgnoreCase("false")){
			return false;
		}
		return true;
	}

	public String getThXmppAddress() {
		return get("TH_XMPP_Address");
	}	

	public String getDutXmppAddress() {
		return get("DUT_XMPP_Address");
	}	

	public int getThXmppPort() {
		return Integer.parseInt(get("TH_XMPP_Port"));
	}	

	public int getDutXmppPort() {
		return Integer.parseInt(get("DUT_XMPP_Port"));
	}	

	public boolean isXmppServiceDiscovery() {
		return "true".equals(get("XMPP_Service_Discovery"));
	}	

	public String getXmppVENUsername() {
		return get("XMPP_VEN_Username");
	}	
	
	public String getXmppVENPassword() {
		return get("XMPP_VEN_Password");
	}	

	public String getXmppVENResourceName() {
		return get("XMPP_VEN_ResourceName");
	}	

	public String getXmppVTNUsername() {
		return get("XMPP_VTN_Username");
	}	

	public String getXmppVTNPassword() {
		return get("XMPP_VTN_Password");
	}
	
	public String getXmppVTNResourceName() {
		return get("XMPP_VTN_ResourceName");
	}
	
	public boolean isXmppDebug() {
		return "true".equals(get("XMPP_Debug"));
	}
	
	public boolean isXmppSaslExternal() {
		return "true".equals(get("XMPP_SASL_External"));
	}

	public String getTransportType() {
		return get("Transport_Type");
	}

	public boolean isXmppTransportType() {
		// properties.isChannelEnabled() &&
		return "XMPP".equals(getTransportType());
	}

	public boolean isHttpTransportType() {
		return "HTTP".equals(getTransportType());
	}

	public String getGroupID(){
		return get("GroupID");
	}
	
	public  String getParty_ID(){
		return get("Party_ID");
	}

	public boolean isTestPrompt() {
		String prompt = get("Test_Prompt");
		
		return (prompt == null) ? true : "true".equals(prompt);
	}

	public boolean isTestOpenLog() {
		String openLog = get("Test_OpenLog");
		
		return (openLog == null) ? false : "true".equals(openLog);
	}
	
	public boolean isTestRunner() {
		String runner = get("Test_Runner");
		
		return (runner == null) ? false : "true".equals(runner);
	}
	
	public boolean isTestRunnerShortcuts() {
		String runner = get("Test_RunnerShortcuts");
		
		return (runner == null) ? false : "true".equals(runner);
	}
	
	String resourceList=get("ResourceIDs");
	
	public String[] getAllResourceID(){
		String resources[]=resourceList.split(",");
		return resources;	
	}
	
	public String getOneResourceID(){
		String resources[]=resourceList.split(",");

		String propFileResourceID[]= new PropertiesFileReader().getAllResourceID();
		if(propFileResourceID==null || propFileResourceID.length<1){
			System.out.println("***************For this test case please set at least one resources in the properties file. E.g ResourceID=resource1****************");
			System.exit(-1);
		}
		return resources[0];	
	}
	
	
	public String[] getTwoResourceID(){
		String resources[]=resourceList.split(",");

		String propFileResourceID[]= new PropertiesFileReader().getAllResourceID();
		if(propFileResourceID==null || propFileResourceID.length<2){
			System.out.println("***************For this test case please set at least two resources in the properties file. E.g ResourceID=resource1,resource2****************");
			System.exit(-1);
		}
		return new String[]{resources[0],resources[1]};	
	}

	public String[] getThreeResourceID(){
		String resources[]=resourceList.split(",");

		String propFileResourceID[]= new PropertiesFileReader().getAllResourceID();
		if(propFileResourceID==null || propFileResourceID.length<3){
			System.out.println("***************For this test case please set at least three resources in the properties file. E.g ResourceID=resource1,resource2,resource3****************");
			System.exit(-1);
		}
		return new String[]{resources[0],resources[1],resources[2]};	
	}

	public String[] getFourResourceID(){
		String resources[]=resourceList.split(",");

		String propFileResourceID[]= new PropertiesFileReader().getAllResourceID();
		if(propFileResourceID==null || propFileResourceID.length<4){
			System.out.println("***************For this test case please set at least four resources in the properties file. E.g ResourceID=resource1,resource2,resource3,resource4****************");
			System.exit(-1);
		}
		return new String[]{resources[0],resources[1],resources[2],resources[3]};	
	}

	public String[] getDeviceClass(){
		return get("Device_Class").split(",");
	}

	public String getCreatedPartyRegistration_RequestedOadrPollFreq() {
		return get("CreatedPartyRegistration_RequestedOadrPollFreq");
	}
	
	public String getSecurityMode() {
		return isXmppTransportType() ? get("XMPP_Security") : get("HTTP_Security");
	}
}
