package com.honeywell.payloads.itembasegenerator;

import com.honeywell.openadr.core.signal.ItemBaseType;

import com.honeywell.openadr.core.signal.ThermType;
import com.honeywell.ven.api.report.ItemBase;
import com.honeywell.ven.api.report.Therm;

public class ThermTypeGenerator implements ItemBaseFactory{

	private Therm therm;
	
	@Override
	public ItemBase getData() {		
		return therm;
	}

	@Override
	public void setData(ItemBaseType oadrItemBase) {
		ThermType thermType = (ThermType) oadrItemBase;
		if(null == therm)
			therm = new Therm();
		therm.setItemDescription(thermType.getItemDescription().toString());
		therm.setItemUnits(thermType.getItemUnits());	
		therm.setSiScaleCode(thermType.getSiScaleCode());
	}

}
