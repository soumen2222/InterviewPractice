package com.honeywell.payloads.itembasegenerator;

import com.honeywell.openadr.core.signal.ItemBaseType;
import com.honeywell.openadr.core.signal.EnergyApparentType;

import com.honeywell.ven.api.report.EnergyApparent;
import com.honeywell.ven.api.report.ItemBase;

public class EnergyApparentTypeGenerator implements ItemBaseFactory{

	private EnergyApparent energyApparent;
	
	@Override
	public ItemBase getData() {		
		return energyApparent;
	}

	@Override
	public void setData(ItemBaseType oadrItemBase) {
		EnergyApparentType energyApparentType = (EnergyApparentType) oadrItemBase;
		if(null == energyApparent)
			energyApparent = new EnergyApparent();
		energyApparent.setItemDescription(energyApparentType.getItemDescription().toString());
		energyApparent.setItemUnits(energyApparentType.getItemUnits());
		energyApparent.setSiScaleCode(energyApparentType.getSiScaleCode());
	}

}
