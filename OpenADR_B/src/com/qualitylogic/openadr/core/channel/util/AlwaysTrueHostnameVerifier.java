package com.qualitylogic.openadr.core.channel.util;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class AlwaysTrueHostnameVerifier implements HostnameVerifier {

	@Override
	public boolean verify(String hostname, SSLSession session) {
		return true;
	}
}
