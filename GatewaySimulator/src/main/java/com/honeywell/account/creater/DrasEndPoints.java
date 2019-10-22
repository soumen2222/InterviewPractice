package com.honeywell.account.creater;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DrasEndPoints {
	
	
	private static final String GroupService = "/soap/GroupService?WSDL";
	private static final String scProgramService = "/soap/scProgramService?WSDL";
	private static final String scResourceService = "/soap/scResourceService?WSDL";
	private static final String scPartyService = "/soap/scPartyService?WSDL";
	private static final String EndpointService="/soap/EndPointService?WSDL";
	private static String dnsname;
	
	
	public DrasEndPoints() {
		// TODO Auto-generated constructor stub
	}


	public static URL getGroupService() throws MalformedURLException, IOException{
		return getURL(GroupService);
	}	
	

	public static URL getScProgramService() throws MalformedURLException, IOException{
		return getURL(scProgramService);
	}	

	public static URL getScResourceService() throws MalformedURLException, IOException{
		return getURL(scResourceService);
	}	

	public static URL getScPartyService() throws MalformedURLException, IOException{
		return getURL(scPartyService);
	}	
	
	
	public static URL getEndpointService() throws MalformedURLException, IOException{
		return getURL(EndpointService);
	}	
	
	private static URL getURL(String key) throws MalformedURLException, IOException{
		return new URL(dnsname +key);
	}


	public static String getDnsname() {
		return dnsname;
	}


	public static void setDnsname(String dnsname) {
		DrasEndPoints.dnsname = dnsname;
	}
	
	

	

}
