package com.soumen.generic.inheritence.Example2;

import java.util.List;

public class PojoGenerator {
	
public  Element<? extends PayloadBaseType> getPayloadBase (PayloadBase payloads){
		
		if(payloads instanceof PayloadFloat){
			
			PayloadFloatType payloadFloatType = new PayloadFloatType();
			payloadFloatType.setValue(payloads.getValue());
			Element<PayloadFloatType> t = new Element<PayloadFloatType>();
			t.setValue(payloadFloatType);
			return t;
			
		}else if(payloads instanceof PayloadResourceStatus){
			
			PayloadResourceStatusType payloadResourceStatusType = new PayloadResourceStatusType();
			payloadResourceStatusType.setOadrOnline(payloads.getResourceStatus().getOnline());
			Element<PayloadResourceStatusType> t = new Element<PayloadResourceStatusType>();
			t.setValue(payloadResourceStatusType);
			return t;
			
		}
		return null;
	}
	
	
	public  PayloadBase getPayLoadBase(Element<? extends PayloadBaseType> oadrPayloadBase){
		PayloadBase payload = new PayloadBase();
		if(null != oadrPayloadBase){
			PayloadBaseType oadrPayloadBaseType = oadrPayloadBase.getValue();
			if(oadrPayloadBaseType instanceof PayloadFloatType){
				float value = ((PayloadFloatType) oadrPayloadBaseType).getValue();
				payload.setValue(value);
			}else if(oadrPayloadBaseType instanceof PayloadResourceStatusType){
				payload.setResourceStatus(getResourceStatus((PayloadResourceStatusType) oadrPayloadBaseType));	
			}
		}
		return payload;
	}
	
	
	public  <T> void copy(List<? super T> dst, List<? extends T> src) {
		for (int i = 0; i < src.size(); i++) {
		dst.add(i, src.get(i));
		}
		}
	
	private static ResourceStatus getResourceStatus(PayloadResourceStatusType oadrPayloadResourceStatusType){
		ResourceStatus resourceStatus = new  ResourceStatus();
		if(null != oadrPayloadResourceStatusType){
			resourceStatus.setManualOverride(oadrPayloadResourceStatusType.isOadrManualOverride());
			resourceStatus.setOnline(oadrPayloadResourceStatusType.isOadrOnline());			
		}
		return resourceStatus;
	}


}
