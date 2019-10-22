package com.honeywell.push.client;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.jboss.logging.Logger;

import com.honeywell.payloads.util.PayloadHelper;
import com.honeywell.openadr.core.signal.OadrPayload;
import com.honeywell.payloads.util.PayloadUtil;

public class HttpPushClient {

	private final Logger log = Logger.getLogger(HttpPushClient.class);
	public static final String OPEN_ADR_2B_SSL_REQUIRED ="com.honeywell.dras.vtn.openadr.2b.ssl.required";
	private static final boolean debug = true;

	private String keyStoreFileName = null;
	private String keyStorePassword = null;
	private String trustStoreFileName = null;
	private String trustStorePassword = null;
	private String alias = null;

	public HttpPushClient() throws HttpPushException {

		keyStoreFileName = System
				.getProperty("com.honeywell.dras.vtn.push.client.keyStoreFileName");
		keyStorePassword = System
				.getProperty("com.honeywell.dras.vtn.push.client.keyStorePassword");
		trustStoreFileName = System
				.getProperty("com.honeywell.dras.vtn.push.client.trustStoreFileName");
		trustStorePassword = System
				.getProperty("com.honeywell.dras.vtn.push.client.trustStorePassword");
		alias = System.getProperty("com.honeywell.dras.vtn.push.client.alias");
		validateValues();

	}
	
	private void validateValues() throws HttpPushException {
		if (keyStoreFileName == null || keyStoreFileName.isEmpty()
				|| keyStorePassword == null || keyStorePassword.isEmpty()
				|| trustStoreFileName == null || trustStoreFileName.isEmpty()
				|| trustStorePassword == null || trustStorePassword.isEmpty()
				|| alias == null || alias.isEmpty()) {
			throw new HttpPushException(
					"System properties for OpenADR 2 Push SSL certificates is not set properly. Please check standalone.xml");
		}

	}
	
	public HttpPushClient(String keyStoreFileName, String keyStorePassword, String trustStoreFileName, String trustStorePassword, String alias) throws HttpPushException {

		this.keyStoreFileName = keyStoreFileName;
		this.keyStorePassword = keyStorePassword;
		this.trustStoreFileName = trustStoreFileName;
		this.trustStorePassword = trustStorePassword;
		this.alias = alias;
		validateValues();
		
	}
	
	public Object pushPayload(String endpoint, OadrPayload oadrPayload)
			throws HttpPushException {
		
		

		if(oadrPayload == null){
			throw new HttpPushException(
					"Payload was null. Cannot push the payload.");
		}
		
		if(endpoint == null){
			throw new HttpPushException(
					"Endpoint was null. Cannot push the payload.");
		}
		
		try {
			PayloadUtil.printRequest(oadrPayload);
		} catch (JAXBException e1) {
			throw new HttpPushException(e1.getLocalizedMessage(), e1 );
		}

		InputStream is = null;
		Object o = null;

		try {
			
			// need to revert the changes. isSslRequired should be true and sslrequired should be system variable.
			Boolean isSslRequired = true;
			String sslRequired = System.getProperty(OPEN_ADR_2B_SSL_REQUIRED);  //isSslRequired.toString();
			if (sslRequired == null || sslRequired.isEmpty()) {
				log.error("DRAS SSL Required flag is not set for Openadr 2b !!!!");
			}else{
				if("TRUE".equalsIgnoreCase(sslRequired))
					isSslRequired = true;
				else if("FALSE".equalsIgnoreCase(sslRequired))
					isSslRequired = false;
			}	

			SSLSocketFactory factory =null;
			if(isSslRequired){
				
				try{
				// create key and trust managers
				KeyManager[] keyManagers = createKeyManagers(keyStoreFileName,
						keyStorePassword, alias);
				TrustManager[] trustManagers = createTrustManagers(
						trustStoreFileName, trustStorePassword);
				// init context with managers data
				factory = initItAll(keyManagers, trustManagers);
				
				} catch (UnrecoverableKeyException e) {
					throw new HttpPushException("SSL Key Error: " + e.getMessage());
				} catch (CertificateException e) {
					throw new HttpPushException("SSL Certificate Error: " + e.getMessage());
				} catch (KeyStoreException e) {
					throw new HttpPushException("SSL Keystore Error: " + e.getMessage());
				} catch (NoSuchAlgorithmException e) {
					throw new HttpPushException("SSL Algo Error: " + e.getMessage());
				} catch (IOException e) {
					throw new HttpPushException("SSL File Error: " + e.getMessage());
				} catch (KeyManagementException e) {
					throw new HttpPushException("SSL Key Manager Error: " + e.getMessage());
				}

			}
			
			log.debug("The endpoint url is :"+endpoint);
			
			URL url = new URL(endpoint);
			URLConnection connection = url.openConnection();
			if(isSslRequired){
				if (connection instanceof HttpsURLConnection) {
					((HttpsURLConnection) connection).setSSLSocketFactory(factory);
				}
			}else{
				// Create a trust manager that does not validate certificate chains
				TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
						public java.security.cert.X509Certificate[] getAcceptedIssuers() {
							return null;
						}
						public void checkClientTrusted(X509Certificate[] certs, String authType) {
						}
						public void checkServerTrusted(X509Certificate[] certs, String authType) {
						}
					}
				};
			
				// Install the all-trusting trust manager
				SSLContext sc = SSLContext.getInstance("SSL");
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			
				// Create all-trusting host name verifier
				HostnameVerifier allHostsValid = new HostnameVerifier() {
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				};
			
				// Install the all-trusting host verifier
				HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
			}

			connection.setDoOutput(true);

			Marshaller marshaller = PayloadHelper.getMarshaller();

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			ByteArrayOutputStream buff = new ByteArrayOutputStream();
			PrintWriter writer = new PrintWriter(buff);
			marshaller.marshal(oadrPayload, writer);
			connection.setRequestProperty("Content-Type",
					"application/xml; charset=utf-8");
			connection.setRequestProperty("Content-Encoding", "UTF-8");
			connection.setRequestProperty("Content-Length", "" + buff.size());
			buff.writeTo(connection.getOutputStream());

			// marshaller.marshal(o, connection.getOutputStream());
			is = connection.getInputStream();
//			log.debug("<<<<<<<<< VTN 2b Push Response");
			Unmarshaller u = PayloadHelper.getUnmarshaller();
			o = u.unmarshal(is);
			PayloadUtil.printResponse(is);
			

		} catch (Exception e) {
			log.error("Exception in HttpPushClient - unable to send message to the endpoint : "+endpoint +" rethrowing exception to vtnPushService ");
			throw new HttpPushException(e.getLocalizedMessage(), e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}

			} catch (Exception e) {
			}
		}

		return o;

	}

	

	private static void doitAll(String urlString, SSLSocketFactory sslSocketFactory) throws IOException {
		URL url = new URL(urlString);
		URLConnection connection = url.openConnection();
		if (connection instanceof HttpsURLConnection) {
			((HttpsURLConnection) connection)
					.setSSLSocketFactory(sslSocketFactory);
		}
		int x;
		while ((x = ((InputStream) connection.getContent()).read()) != -1) {
			System.out.print(new String(new byte[] { (byte) x }));
		}
	}

	private static SSLSocketFactory initItAll(KeyManager[] keyManagers,
			TrustManager[] trustManagers) throws NoSuchAlgorithmException,
			KeyManagementException {
		SSLContext context = SSLContext.getInstance("SSLv3");
		// TODO investigate: could also be
		// "SSLContext context = SSLContext.getInstance("TLS");" Why?
		context.init(keyManagers, trustManagers, null);
		SSLSocketFactory socketFactory = context.getSocketFactory();
		return socketFactory;
	}

	private static KeyManager[] createKeyManagers(String keyStoreFileName,
			String keyStorePassword, String alias) throws CertificateException,
			IOException, KeyStoreException, NoSuchAlgorithmException,
			UnrecoverableKeyException, java.security.cert.CertificateException,
			HttpPushException {
		// create Inputstream to keystore file
		java.io.InputStream inputStream = new java.io.FileInputStream(
				keyStoreFileName);
		// create keystore object, load it with keystorefile data
		KeyStore keyStore = KeyStore.getInstance("JKS");
		keyStore.load(inputStream, keyStorePassword == null ? null
				: keyStorePassword.toCharArray());
		// DEBUG information should be removed
		if (debug) {
			printKeystoreInfo(keyStore);
		}

		KeyManager[] managers;
		if (alias != null) {
			managers = new KeyManager[] { new HttpPushClient().new AliasKeyManager(
					keyStore, alias, keyStorePassword) };
		} else {
			// create keymanager factory and load the keystore object in it
			KeyManagerFactory keyManagerFactory = KeyManagerFactory
					.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			keyManagerFactory.init(keyStore, keyStorePassword == null ? null
					: keyStorePassword.toCharArray());
			managers = keyManagerFactory.getKeyManagers();
		}
		// return
		inputStream.close();
		return managers;
		
	}

	private static TrustManager[] createTrustManagers(
			String trustStoreFileName, String trustStorePassword)
			throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, IOException,
			java.security.cert.CertificateException {
		// create Inputstream to truststore file
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };

		return trustAllCerts;
	}

	private static void printKeystoreInfo(KeyStore keystore)
			throws KeyStoreException {
		System.out.println("Provider : " + keystore.getProvider().getName());
		System.out.println("Type : " + keystore.getType());
		System.out.println("Size : " + keystore.size());

		Enumeration en = keystore.aliases();
		while (en.hasMoreElements()) {
			System.out.println("Alias: " + en.nextElement());
		}
	}

	private class AliasKeyManager implements X509KeyManager {

		private KeyStore _ks;
		private String _alias;
		private String _password;

		public AliasKeyManager(KeyStore ks, String alias, String password) {
			_ks = ks;
			_alias = alias;
			_password = password;
		}

		public String chooseClientAlias(String[] str, Principal[] principal,
				Socket socket) {
			return _alias;
		}

		public String chooseServerAlias(String str, Principal[] principal,
				Socket socket) {
			return _alias;
		}

		public java.security.cert.X509Certificate[] getCertificateChain(
				String alias) {
			try {
				java.security.cert.Certificate[] certificates = this._ks
						.getCertificateChain(alias);
				if (certificates == null) {
					throw new FileNotFoundException(
							"no certificate found for alias:" + alias);
				}
				X509Certificate[] x509Certificates = new X509Certificate[certificates.length];
				System.arraycopy(certificates, 0, x509Certificates, 0,
						certificates.length);
				return x509Certificates;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		public String[] getClientAliases(String str, Principal[] principal) {
			return new String[] { _alias };
		}

		public PrivateKey getPrivateKey(String alias) {
			try {
				return (PrivateKey) _ks.getKey(alias, _password == null ? null
						: _password.toCharArray());
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		public String[] getServerAliases(String str, Principal[] principal) {
			return new String[] { _alias };
		}

	}


}
