package com.honeywell.ven.api.poll;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.honeywell.ven.api.common.PushProfile;

@XmlRootElement
public class PollRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private Poll poll;
	private PushProfile pushProfile;
	

	public Poll getPoll() {
		return poll;
	}
	public void setPoll(Poll poll) {
		this.poll = poll;
	}
	public PushProfile getPushProfile() {
		return pushProfile;
	}
	public void setPushProfile(PushProfile pushProfile) {
		this.pushProfile = pushProfile;
	}
	
	
}
