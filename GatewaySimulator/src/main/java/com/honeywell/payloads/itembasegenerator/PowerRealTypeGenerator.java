package com.honeywell.payloads.itembasegenerator;

import com.honeywell.openadr.core.signal.ItemBaseType;
import com.honeywell.openadr.core.signal.PowerRealType;

import com.honeywell.ven.api.report.ItemBase;
import com.honeywell.ven.api.report.PowerReal;

public class PowerRealTypeGenerator implements ItemBaseFactory{

	private PowerReal powerReal;
	
	@Override
	public ItemBase getData() {		
		return powerReal;
	}

	@Override
	public void setData(ItemBaseType oadrItemBase) {
		PowerRealType powerRealType = (PowerRealType) oadrItemBase;
		if(null == powerReal)
			powerReal = new PowerReal();
		powerReal.setItemDescription(powerRealType.getItemDescription().toString());
		powerReal.setItemUnits(powerRealType.getItemUnits());
		powerReal.setSiScaleCode(powerRealType.getSiScaleCode());
	}

}
