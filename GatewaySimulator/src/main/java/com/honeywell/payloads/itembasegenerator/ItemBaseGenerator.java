package com.honeywell.payloads.itembasegenerator;

import com.honeywell.openadr.core.signal.ItemBaseType;

import com.honeywell.ven.api.report.ItemBase;

public class ItemBaseGenerator {

	public ItemBase getItemBase(ItemBaseType oadrItemBaseType,String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		
		Class<?> name = Class.forName("com.honeywell.payloads.itembasegenerator." +className+ "Generator");
		Object newInstance = name.newInstance();
		
		((ItemBaseFactory)newInstance).setData(oadrItemBaseType);
		
		return ((ItemBaseFactory)newInstance).getData();
	}
	
}
