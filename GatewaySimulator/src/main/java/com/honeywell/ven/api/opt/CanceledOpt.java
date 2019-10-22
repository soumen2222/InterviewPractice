package com.honeywell.ven.api.opt;

import javax.xml.bind.annotation.XmlRootElement;

import com.honeywell.ven.api.common.BaseClass;
import com.honeywell.ven.api.common.Response;

@XmlRootElement
public class CanceledOpt extends BaseClass{

	private Response response;
	private String optId;
	
	
	public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}
	public String getOptId() {
		return optId;
	}
	public void setOptId(String optId) {
		this.optId = optId;
	}
	
}
