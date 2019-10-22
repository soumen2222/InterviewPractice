package com.honeywell.payloads.itembasegenerator;

import com.honeywell.openadr.core.signal.ItemBaseType;

import com.honeywell.ven.api.report.ItemBase;

public interface ItemBaseFactory {
	
	public abstract ItemBase getData();
	
	public abstract void setData(ItemBaseType oadrItemBase);
}


