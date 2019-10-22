package com.honeywell.payloads.itembasegenerator;

import com.honeywell.openadr.core.signal.ItemBaseType;

import com.honeywell.openadr.core.signal.CurrentType;
import com.honeywell.ven.api.report.ItemBase;

public class CurrentTypeGenerator implements ItemBaseFactory {
	
	private com.honeywell.ven.api.report.Current current;
	
	@Override
	public ItemBase getData() {
		return current;
	}

	@Override
	public void setData(ItemBaseType oadrItemBase) {
		CurrentType currentType = (CurrentType) oadrItemBase;
		if(null == current)
			current = new com.honeywell.ven.api.report.Current();
		current.setItemDescription(currentType.getItemDescription().toString());
		current.setItemUnits(currentType.getItemUnits());
		current.setSiScaleCode(currentType.getSiScaleCode());
	}

}
