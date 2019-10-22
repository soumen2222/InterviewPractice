package com.honeywell.payloads.itembasegenerator;

import com.honeywell.openadr.core.signal.ItemBaseType;
import com.honeywell.openadr.core.signal.EnergyRealType;

import com.honeywell.ven.api.report.Currency;
import com.honeywell.ven.api.report.ItemBase;

public class EnergyRealTypeGenerator implements ItemBaseFactory{

	private Currency energyReal;
	
	@Override
	public ItemBase getData() {		
		return energyReal;
	}

	@Override
	public void setData(ItemBaseType oadrItemBase) {
		EnergyRealType energyRealType = (EnergyRealType) oadrItemBase;
		if(null == energyReal)
			energyReal = new Currency();
		energyReal.setItemDescription(energyRealType.getItemDescription().toString());
		energyReal.setItemUnits(energyRealType.getItemUnits());
		energyReal.setSiScaleCode(energyRealType.getSiScaleCode());
	}

}
