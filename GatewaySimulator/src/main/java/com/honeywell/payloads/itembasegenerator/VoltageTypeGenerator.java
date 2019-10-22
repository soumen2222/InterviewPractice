package com.honeywell.payloads.itembasegenerator;

import com.honeywell.openadr.core.signal.ItemBaseType;
import com.honeywell.openadr.core.signal.VoltageType;

import com.honeywell.ven.api.report.ItemBase;
import com.honeywell.ven.api.report.Voltage;

public class VoltageTypeGenerator implements ItemBaseFactory{

	private Voltage voltage;
	
	@Override
	public ItemBase getData() {		
		return voltage;
	}

	@Override
	public void setData(ItemBaseType oadrItemBase) {
		VoltageType voltageType = (VoltageType) oadrItemBase;
		if(null == voltage)
			voltage = new Voltage();
		voltage.setItemDescription(voltageType.getItemDescription().toString());
		voltage.setItemUnits(voltageType.getItemUnits());
		voltage.setSiScaleCode(voltageType.getSiScaleCode());
	}

}
