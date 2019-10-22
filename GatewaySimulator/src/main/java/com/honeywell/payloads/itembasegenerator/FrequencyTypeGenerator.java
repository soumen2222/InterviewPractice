package com.honeywell.payloads.itembasegenerator;

import com.honeywell.openadr.core.signal.ItemBaseType;

import com.honeywell.openadr.core.signal.FrequencyType;
import com.honeywell.ven.api.report.Currency;
import com.honeywell.ven.api.report.ItemBase;

public class FrequencyTypeGenerator implements ItemBaseFactory{

	private Currency frequency;
	
	@Override
	public ItemBase getData() {		
		return frequency;
	}

	@Override
	public void setData(ItemBaseType oadrItemBase) {
		FrequencyType frequencyType = (FrequencyType) oadrItemBase;
		if(null == frequency)
			frequency = new Currency();
		frequency.setItemDescription(frequencyType.getItemDescription().toString());
		frequency.setItemUnits(frequencyType.getItemUnits());
		frequency.setSiScaleCode(frequencyType.getSiScaleCode());
	}

}
