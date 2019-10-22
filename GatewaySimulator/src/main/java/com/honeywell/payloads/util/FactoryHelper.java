package com.honeywell.payloads.util;

import com.honeywell.openadr.core.signal.ObjectFactory;


public class FactoryHelper {
	
	public static com.honeywell.openadr.core.signal.ObjectFactory getOadrObjectFactory(){
		ObjectFactory of = new ObjectFactory();
		return of;
	}
	

	public static com.honeywell.openadr.core.signal.xcal.ObjectFactory getCalObjectFactory(){
		com.honeywell.openadr.core.signal.xcal.ObjectFactory of = new com.honeywell.openadr.core.signal.xcal.ObjectFactory();
		return of;
	}
	

	public static com.honeywell.openadr.core.signal.greenbutton.ObjectFactory getgbuttonObjectFactory(){
		com.honeywell.openadr.core.signal.greenbutton.ObjectFactory of = new com.honeywell.openadr.core.signal.greenbutton.ObjectFactory();
		return of;
	}
	
	
	
}



