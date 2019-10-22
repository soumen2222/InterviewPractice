package com.honeywell.ven.api.common;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class BaseClass implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String schemaVersion;
	private String fingerprint;
	private String venId;
	private String vtnId;
	private String requestId;
	private String certCommonName;
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	

	public String getSchemaVersion() {
		return schemaVersion;
	}
	public void setSchemaVersion(String schemaVersion) {
		this.schemaVersion = schemaVersion;
	}
	public String getFingerprint() {
		return fingerprint;
	}
	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}
	public String getVenId() {
		return venId;
	}
	public void setVenId(String venId) {
		this.venId = venId;
	}
	
	
	public String getVtnId() {
		return vtnId;
	}
	public void setVtnId(String vtnId) {
		this.vtnId = vtnId;
	}
	public String getCertCommonName() {
		return certCommonName;
	}
	public void setCertCommonName(String certCommonName) {
		this.certCommonName = certCommonName;
	}


}
