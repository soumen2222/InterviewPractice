package com.honeywell.payloads.itembasegenerator;

import com.honeywell.openadr.core.signal.ItemBaseType;

import com.honeywell.openadr.core.signal.BaseUnitType;
import com.honeywell.ven.api.report.BaseUnit;
import com.honeywell.ven.api.report.ItemBase;

public class BaseUnitTypeGenerator implements ItemBaseFactory{

	private BaseUnit baseUnit;
	
	@Override
	public ItemBase getData() {
		return baseUnit;
	}

	@Override
	public void setData(ItemBaseType oadrItemBase) {
		BaseUnitType oadrBaseUnitType = (BaseUnitType) oadrItemBase;
		if(null == baseUnit)
			baseUnit = new BaseUnit();
		baseUnit.setItemDescription(oadrBaseUnitType.getItemDescription().toString());
		baseUnit.setItemUnits(oadrBaseUnitType.getItemUnits());
		baseUnit.setSiScaleCode(oadrBaseUnitType.getSiScaleCode());
	}	
}
