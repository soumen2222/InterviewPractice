package com.honeywell.ven.api.common;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.honeywell.ven.api.registration.ProfileName;
import com.honeywell.ven.api.registration.TransportName;

@XmlRootElement
public class PushProfile implements Serializable{
	private static final long serialVersionUID = 1L;
	private ProfileName profileName;
	private TransportName transport;
	private String pushUrl;
	private String xmppJid;
	private String ServerUrl;
	
	
	public String getServerUrl() {
		return ServerUrl;
	}
	public void setServerUrl(String serverUrl) {
		ServerUrl = serverUrl;
	}
	public ProfileName getProfileName() {
		return profileName;
	}
	public void setProfileName(ProfileName profileName) {
		this.profileName = profileName;
	}
	public TransportName getTransport() {
		return transport;
	}
	public void setTransport(TransportName transport) {
		this.transport = transport;
	}
	public String getPushUrl() {
		return pushUrl;
	}
	public void setPushUrl(String pushUrl) {
		this.pushUrl = pushUrl;
	}
	public String getXmppJid() {
		return xmppJid;
	}
	public void setXmppJid(String xmppJid) {
		this.xmppJid = xmppJid;
	}

}
