package com.soumen.generic.inheritence.Example2;

import java.io.Serializable;


public class ReportPayloadType implements Serializable
{

private final static long serialVersionUID = 1L;
protected String rid;
protected Long confidence;
protected Float accuracy;
protected Element<? extends PayloadBaseType> payloadBase;


public String getRID() {
    return rid;
}


public void setRID(String value) {
    this.rid = value;
}


public Long getConfidence() {
    return confidence;
}


public void setConfidence(Long value) {
    this.confidence = value;
}


public Float getAccuracy() {
    return accuracy;
}


public void setAccuracy(Float value) {
    this.accuracy = value;
}


public Element<? extends PayloadBaseType> getPayloadBase() {
	return payloadBase;
}


public void setPayloadBase(Element<? extends PayloadBaseType> payloadBase) {
	this.payloadBase = payloadBase;
}




}

