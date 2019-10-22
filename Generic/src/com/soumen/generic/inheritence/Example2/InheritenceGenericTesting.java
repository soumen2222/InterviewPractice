package com.soumen.generic.inheritence.Example2;

import java.util.ArrayList;


public class InheritenceGenericTesting {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PojoGenerator p = new PojoGenerator();
		
		//FLoat Type
		PayloadFloatType payloadFloatType = new PayloadFloatType();
		payloadFloatType.setValue(55);
		
		PayloadFloatType payloadFloatType1 = new PayloadFloatType();
		payloadFloatType1.setValue(56);
		
		
		//PayloadResourceStatusType
		
		
		
		PayloadResourceStatusType payloadResourceType = new PayloadResourceStatusType();
		payloadResourceType.setValue(55);
		
		PayloadResourceStatusType payloadResourceType1 = new PayloadResourceStatusType();
		payloadResourceType1.setValue(56);
		
		
		//Base Type
		
		PayloadBaseType payloadBaseType = new PayloadBaseType();
		payloadBaseType.setValue(40);
		
		PayloadBaseType payloadBaseType1 = new PayloadBaseType();
		payloadBaseType1.setValue(41);
		
		PayloadBaseType payloadBaseType2 = new PayloadBaseType();
		payloadBaseType2.setValue(42);
		
		
		
		Element<PayloadFloatType> t = new Element<PayloadFloatType>();
		t.setValue(payloadFloatType);
		PayloadBase val = p.getPayLoadBase(t);
		System.out.println(val.getrId());
		System.out.println(val.getValue());
		
		ReportPayloadType reportPayloadType = new ReportPayloadType();
		PayloadFloat payloadFloat = new PayloadFloat();
		payloadFloat.setValue(65f);
		reportPayloadType.setPayloadBase(p.getPayloadBase(payloadFloat));
		//Element<? extends PayloadBaseType> vale1 = reportPayloadType.getPayloadBase();
		
		
		ArrayList<PayloadFloatType> src = new ArrayList<PayloadFloatType>();
		src.add(payloadFloatType);
		src.add(payloadFloatType1);
		
		ArrayList<PayloadResourceStatusType> src1 = new ArrayList<PayloadResourceStatusType>();
		src1.add(payloadResourceType);
		src1.add(payloadResourceType1);
		
		ArrayList<PayloadBaseType> dst = new ArrayList<PayloadBaseType>();
		dst.add(payloadBaseType);
		dst.add(payloadBaseType1);
		dst.add(payloadBaseType2);
		p.copy(dst, src);
		
		for (PayloadBaseType value : dst) {
			System.out.println("Values are :" + value.getValue());
			
		}

	}

}
