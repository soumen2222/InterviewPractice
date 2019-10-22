package com.honeywell.payloads.itembasegenerator;

import com.honeywell.openadr.core.signal.ItemBaseType;
import com.honeywell.openadr.core.signal.PowerReactiveType;

import com.honeywell.ven.api.report.ItemBase;
import com.honeywell.ven.api.report.PowerReactive;

public class PowerReactiveTypeGenerator implements ItemBaseFactory{

	private PowerReactive powerReactive;
	
	@Override
	public ItemBase getData() {		
		return powerReactive;
	}

	@Override
	public void setData(ItemBaseType oadrItemBase) {
		PowerReactiveType powerReactiveType = (PowerReactiveType) oadrItemBase;
		if(null == powerReactive)
			powerReactive = new PowerReactive();
		powerReactive.setItemDescription(powerReactiveType.getItemDescription().toString());
		powerReactive.setItemUnits(powerReactiveType.getItemUnits());
		powerReactive.setSiScaleCode(powerReactiveType.getSiScaleCode());
	}

}
