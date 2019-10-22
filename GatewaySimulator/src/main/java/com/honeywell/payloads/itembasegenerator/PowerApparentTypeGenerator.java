package com.honeywell.payloads.itembasegenerator;

import com.honeywell.openadr.core.signal.ItemBaseType;
import com.honeywell.openadr.core.signal.PowerApparentType;

import com.honeywell.ven.api.report.ItemBase;
import com.honeywell.ven.api.report.PowerApparent;

public class PowerApparentTypeGenerator implements ItemBaseFactory{

	private PowerApparent powerApparent;
	
	@Override
	public ItemBase getData() {		
		return powerApparent;
	}

	@Override
	public void setData(ItemBaseType oadrItemBase) {
		PowerApparentType powerApparentType = (PowerApparentType) oadrItemBase;
		if(null == powerApparent)
			powerApparent = new PowerApparent();
		powerApparent.setItemDescription(powerApparentType.getItemDescription().toString());
		powerApparent.setItemUnits(powerApparentType.getItemUnits());
		powerApparent.setSiScaleCode(powerApparentType.getSiScaleCode());
	}

}
