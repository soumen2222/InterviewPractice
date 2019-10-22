package com.honeywell.ven.api.registration;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.honeywell.ven.api.common.PushProfile;

@XmlRootElement
public class CreatePartyRegistrationRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private CreatePartyRegistration createPartyRegistration;
	private PushProfile pushProfile;
	

	public CreatePartyRegistration getCreatePartyRegistration() {
		return createPartyRegistration;
	}
	public void setCreatePartyRegistration(
			CreatePartyRegistration createPartyRegistration) {
		this.createPartyRegistration = createPartyRegistration;
	}
	public PushProfile getPushProfile() {
		return pushProfile;
	}
	public void setPushProfile(PushProfile pushProfile) {
		this.pushProfile = pushProfile;
	}
	
	
}
