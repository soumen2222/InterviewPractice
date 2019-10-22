package com.qualitylogic.openadr.core.channel.http;

import java.util.HashSet;
import java.util.Set;

import org.restlet.Context;
import org.restlet.data.Parameter;
import org.restlet.engine.header.Header;
import org.restlet.util.Series;

import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.channel.util.AlwaysTrueHostnameVerifier;
import com.qualitylogic.openadr.core.channel.util.SslUtil;
import com.qualitylogic.openadr.core.channel.util.StringUtil;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.exception.ValidationException;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public final class RestletUtil {
	private RestletUtil() {
	}

	public static void setupClientSslContext(final ServiceType serviceType, final Context context) {
		SslUtil.setupClientSslContext(serviceType);

		PropertiesFileReader properties = new PropertiesFileReader();
		if ("true".equals(properties.getSecurity_Debug())) {
			System.setProperty("javax.net.debug", "SSL");
		}

		String[] protocols = TestSession.getClientTLSVersion();
		if (protocols == null) {
			protocols = properties.getTLSVersion();
		}

		Series<Parameter> parameters = context.getParameters();
		if (protocols != null) {
			parameters.add("enabledProtocols", StringUtil.join(protocols, ' '));
			
			Set<String> allCipherSuites = new HashSet<>();
			for (String protocol : protocols) {

				String[] cipherSuites = null;
				if (protocol.equals("TLSv1")) {
					String[] tls10Cipher = TestSession.getClientTLS10Ciphers();
					if (tls10Cipher == null) {
						tls10Cipher = properties.getClient_TLS10_Ciphers();
					}
					cipherSuites = tls10Cipher;
					
				} else if (protocol.equals("TLSv1.2")) {
					String[] tls12Cipher = TestSession.getClientTLS12Ciphers();
					if (tls12Cipher == null) {
						tls12Cipher = properties.getClient_TLS12_Ciphers();
					}
					cipherSuites = tls12Cipher;
				}

				if (cipherSuites != null) {
					for (String cipherSuite : cipherSuites) {
						if (!cipherSuite.trim().equals("NONE")) {
							allCipherSuites.add(cipherSuite);
						}
					}
				}
			}

			if (!allCipherSuites.isEmpty()) {
				parameters.add("enabledCipherSuites", StringUtil.join(allCipherSuites.toArray(new String[0]), ' '));
			}
		}

		String trustStore = null;
		String trustStorePassword = null;
		String keyStore = null;
		String keyStorePassword = null;
		String keyStoreType = null;
		
		// VEN/VTN logic
		if (serviceType == ServiceType.VEN) {
			trustStore = properties.getVEN_trustStorePath();
			trustStorePassword = properties.getVEN_truststorePassword();
	
			keyStore = properties.getVEN_keystorePath();
			keyStorePassword = properties.getVEN_keystorePassword();
			keyStoreType = properties.getVEN_keystoreType();
	
		} else {
			trustStore = properties.getVTN_trustStorePath();
			trustStorePassword = properties.getVTN_truststorePassword();
			
			keyStore = properties.getVTN_keystorePath();
			keyStorePassword = properties.getVTN_keystorePassword();
			keyStoreType = properties.getVTN_keystoreType();
		}

		if (trustStore != null) {
			parameters.add("trustStorePath", trustStore);     
			parameters.add("trustStorePassword", trustStorePassword);
			// parameters.add("trustStoreType", trustStoreType); not in properties
		}

		if (keyStore != null) {
			parameters.add("keyStorePath", keyStore);     
			parameters.add("keyStorePassword", keyStorePassword);
			parameters.add("keyStoreType", keyStoreType);
			parameters.add("keyPassword", keyStorePassword);
		}

		if (serviceType == ServiceType.VEN) {
			parameters.add("needClientAuthentication", properties.getVEN_needClientAuth());
	
			if (properties.getVenClientVerifyHostName()) {
				context.getAttributes().put("hostnameVerifier", new AlwaysTrueHostnameVerifier());
			}
		} else {
			parameters.add("needClientAuthentication", properties.getVTN_needClientAuth());
		
			if (properties.getVtnClientVerifyHostName()) {
				context.getAttributes().put("hostnameVerifier", new AlwaysTrueHostnameVerifier());
			}
		}
	}
	
	public static void setupServerSslContext(final ServiceType serviceType, final Context context) {
		SslUtil.setupServerSslContext(serviceType);
		
		PropertiesFileReader properties = new PropertiesFileReader();
		if ("true".equals(properties.getSecurity_Debug())) {
			System.setProperty("javax.net.debug", "SSL");
		}

		String[] protocols = TestSession.getServerTLSVersion();
		if (protocols == null) {
			protocols = properties.getTLSVersion();
		}

		Series<Parameter> parameters = context.getParameters();
		if (protocols != null) {
			parameters.add("enabledProtocols", StringUtil.join(protocols, ' '));
			
			Set<String> allCipherSuites = new HashSet<>();
			for (String protocol : protocols) {

				String[] cipherSuites = null;
				if (protocol.equals("TLSv1")) {
					String[] tls10Cipher = TestSession.getServerTLS10Ciphers();
					if (tls10Cipher == null) {
						tls10Cipher = properties.getServer_TLS10_Ciphers();
					}
					cipherSuites = tls10Cipher;
					
				} else if (protocol.equals("TLSv1.2")) {
					String[] tls12Cipher = TestSession.getServerTLS12Ciphers();
					if (tls12Cipher == null) {
						tls12Cipher = properties.getServer_TLS12_Ciphers();
					}
					cipherSuites = tls12Cipher;
				}

				if (cipherSuites != null) {
					for (String cipherSuite : cipherSuites) {
						if (!cipherSuite.trim().equals("NONE")) {
							allCipherSuites.add(cipherSuite);
						}
					}
				}
			}
		
			if (!allCipherSuites.isEmpty()) {
				parameters.add("enabledCipherSuites", StringUtil.join(allCipherSuites.toArray(new String[0]), ' '));
			}
		}

		String trustStore = null;
		String trustStorePassword = null;
		String keyStore = null;
		String keyStorePassword = null;
		String keyStoreType = null;
		
		// VEN/VTN logic
		if (serviceType == ServiceType.VEN) {
			trustStore = properties.getVEN_trustStorePath();
			trustStorePassword = properties.getVEN_truststorePassword();
	
			keyStore = properties.getVEN_keystorePath();
			keyStorePassword = properties.getVEN_keystorePassword();
			keyStoreType = properties.getVEN_keystoreType();
	
		} else {
			trustStore = properties.getVTN_trustStorePath();
			trustStorePassword = properties.getVTN_truststorePassword();
			
			keyStore = properties.getVTN_keystorePath();
			keyStorePassword = properties.getVTN_keystorePassword();
			keyStoreType = properties.getVTN_keystoreType();
		}

		if (trustStore != null) {
			parameters.add("trustStorePath", trustStore);     
			parameters.add("trustStorePassword", trustStorePassword);
			// parameters.add("trustStoreType", trustStoreType); not in properties
		}

		if (keyStore != null) {
			parameters.add("keyStorePath", keyStore);     
			parameters.add("keyStorePassword", keyStorePassword);
			parameters.add("keyStoreType", keyStoreType);
			parameters.add("keyPassword", keyStorePassword);
		}

		//
		if (serviceType == ServiceType.VEN) {
			parameters.add("needClientAuthentication", properties.getVEN_needClientAuth());
		} else {
			parameters.add("needClientAuthentication", properties.getVTN_needClientAuth());
		}
	}

	
}
