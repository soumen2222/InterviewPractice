package com.qualitylogic.openadr.core.channel.util;

import java.security.AlgorithmConstraints;
import java.security.Provider;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLSocket;

import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public class SslUtil {
	public static void setupClientSslContext(final ServiceType serviceType) {
		PropertiesFileReader properties = new PropertiesFileReader();
		if ("true".equals(properties.getSecurity_Debug())) {
			System.setProperty("javax.net.debug", "SSL");
		}

		String[] protocols = TestSession.getClientTLSVersion();
		if (protocols == null) {
			protocols = properties.getTLSVersion();
		}

		if (protocols != null) {
			System.setProperty("https.protocols", StringUtil.join(protocols, ','));
			
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
				System.setProperty("https.cipherSuites", StringUtil.join(allCipherSuites.toArray(new String[0]), ','));
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
			System.setProperty("javax.net.ssl.trustStore", trustStore);
			System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
			// System.setProperty("javax.net.ssl.trustStoreType", trustStoreType); no property
		}

		if (keyStore != null) {
			System.setProperty("javax.net.ssl.keyStore", keyStore); 
			System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
			System.setProperty("javax.net.ssl.keyStoreType", keyStoreType);
		}
	}
	
	public static void setupServerSslContext(final ServiceType serviceType) {
		PropertiesFileReader properties = new PropertiesFileReader();
		if ("true".equals(properties.getSecurity_Debug())) {
			System.setProperty("javax.net.debug", "SSL");
		}

		String[] protocols = TestSession.getServerTLSVersion();
		if (protocols == null) {
			protocols = properties.getTLSVersion();
		}

		if (protocols != null) {
			System.setProperty("https.protocols", StringUtil.join(protocols, ','));
			
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
				System.setProperty("https.cipherSuites", StringUtil.join(allCipherSuites.toArray(new String[0]), ','));
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
			System.setProperty("javax.net.ssl.trustStore", trustStore);
			System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
			// System.setProperty("javax.net.ssl.trustStoreType", trustStoreType); no property
		}

		if (keyStore != null) {
			System.setProperty("javax.net.ssl.keyStore", keyStore); 
			System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
			System.setProperty("javax.net.ssl.keyStoreType", keyStoreType);
		}
	}
	
	public static void printSslContext(String protocol) {
		try {
			SSLContext context = null; 
					
			if (protocol == null) {
				context = SSLContext.getDefault();
			} else {
				context = SSLContext.getInstance(protocol);
			}
			
			System.out.println("Protocol=" + context.getProtocol() + "\n");

			SocketFactory socketFactory = context.getSocketFactory();
			System.out.println("SocketFactory=" + socketFactory + "\n");
			
			SSLSocket socket = (SSLSocket) socketFactory.createSocket();
			printSocket(socket);
			
			Provider provider = context.getProvider();
			printProvider(provider);
			
			SSLSessionContext clientSslSessionContext = context.getClientSessionContext();
			System.out.println("Client");
			printSSLSessionContext(clientSslSessionContext);
			
			SSLSessionContext serverSslSessionContext = context.getServerSessionContext();
			System.out.println("Server");
			printSSLSessionContext(serverSslSessionContext);
			
			SSLParameters defaultSslParameters = context.getDefaultSSLParameters();
			System.out.println("Default");
			printSslParameters(defaultSslParameters);
			
			SSLParameters supportedSslParameters = context.getSupportedSSLParameters();
			System.out.println("Supported");
			printSslParameters(supportedSslParameters);
			
			SSLServerSocketFactory serverSocketFactory = context.getServerSocketFactory();
			printServerSocketFactory(serverSocketFactory);

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private static void printSocket(SSLSocket socket) {
		System.out.println("Socket");
		System.out.println("------");
		
		String[] cipherSuites = socket.getEnabledCipherSuites();
		for (String cipherSuite : cipherSuites) {
			System.out.println("CipherSuite=" + cipherSuite);
		}
		System.out.println();
	}

	public static void printProvider(Provider provider) {
		System.out.println("Provider");
		System.out.println("--------");
		
		System.out.println("Name=" + provider.getName());
		System.out.println("Version=" + provider.getVersion());
		System.out.println("Info=" + provider.getInfo());
		System.out.println();
	}

	public static void printSslParameters(SSLParameters sslParameters) {
		System.out.println("SSLParameters");
		System.out.println("-------------");
		
		String[] cipherSuites = sslParameters.getCipherSuites();
		for (String cipherSuite : cipherSuites) {
			System.out.println("CipherSuite=" + cipherSuite);
		}
		System.out.println();

		String[] protocols = sslParameters.getProtocols();
		for (String protocol : protocols) {
			System.out.println("Protocol=" + protocol);
		}
		System.out.println();
		
		String endpointIdentificationAlgorithm = sslParameters.getEndpointIdentificationAlgorithm();
		System.out.println("EndpointIdentificationAlgorithm=" + endpointIdentificationAlgorithm);
		boolean needClientAuth = sslParameters.getNeedClientAuth();
		System.out.println("NeedClientAuth=" + needClientAuth);
		boolean wantClientAuth = sslParameters.getWantClientAuth();
		System.out.println("WantClientAuth=" + wantClientAuth);
		AlgorithmConstraints algorithmConstraints = sslParameters.getAlgorithmConstraints();
		System.out.println("AlgorithmConstraints=" + algorithmConstraints);
		System.out.println();
	}

	public static void printSSLSessionContext(SSLSessionContext sslSessionContext) {
		System.out.println("SSLSessionContext");
		System.out.println("-----------------");
		
		Enumeration<byte[]> ids = sslSessionContext.getIds();
		while (ids.hasMoreElements()) {
			System.out.println("Ids=" + ids.nextElement());			
		}
		System.out.println("SessionCacheSize=" + sslSessionContext.getSessionCacheSize());
		System.out.println("SessionTimeout=" + sslSessionContext.getSessionTimeout());
		System.out.println();
	}

	private static void printServerSocketFactory(SSLServerSocketFactory serverSocketFactory) {
		System.out.println("SSLServerSocketFactory");
		System.out.println("----------------------");
		
		String[] defaultCipherSuites = serverSocketFactory.getDefaultCipherSuites();
		for (String cipherSuite : defaultCipherSuites) {
			System.out.println("Default CipherSuite=" + cipherSuite);
		}
		System.out.println();
		
		String[] supportedCipherSuites = serverSocketFactory.getSupportedCipherSuites();
		for (String cipherSuite : supportedCipherSuites) {
			System.out.println("Supported CipherSuite=" + cipherSuite);
		}
		System.out.println();
	}

	private static final Logger logger = Logger.getLogger(SslUtil.class.getName());
}
