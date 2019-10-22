package com.honeywell.payloads.itembasegenerator;

import com.honeywell.openadr.core.signal.ItemBaseType;
import com.honeywell.openadr.core.signal.EnergyReactiveType;

import com.honeywell.ven.api.report.EnergyReactive;
import com.honeywell.ven.api.report.ItemBase;

public class EnergyReactiveTypeGenerator implements ItemBaseFactory{

	private EnergyReactive energyReactive;
	
	@Override
	public ItemBase getData() {		
		return energyReactive;
	}

	@Override
	public void setData(ItemBaseType oadrItemBase) {
		EnergyReactiveType energyReactiveType = (EnergyReactiveType) oadrItemBase;
		if(null == energyReactive)
			energyReactive = new EnergyReactive();
		energyReactive.setItemDescription(energyReactiveType.getItemDescription().toString());
		energyReactive.setItemUnits(energyReactiveType.getItemUnits());
		energyReactive.setSiScaleCode(energyReactiveType.getSiScaleCode());
	}

}
