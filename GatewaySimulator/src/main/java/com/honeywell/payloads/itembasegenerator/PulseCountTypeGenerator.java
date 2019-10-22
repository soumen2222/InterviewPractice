package com.honeywell.payloads.itembasegenerator;

import com.honeywell.openadr.core.signal.ItemBaseType;

import com.honeywell.openadr.core.signal.PulseCountType;
import com.honeywell.ven.api.report.ItemBase;
import com.honeywell.ven.api.report.PulseCount;

public class PulseCountTypeGenerator implements ItemBaseFactory{

	private PulseCount pulseCount;
	
	@Override
	public ItemBase getData() {		
		return pulseCount;
	}

	@Override
	public void setData(ItemBaseType oadrItemBase) {
		PulseCountType pulseCountType = (PulseCountType) oadrItemBase;
		if(null == pulseCount)
			pulseCount = new PulseCount();
		pulseCount.setItemDescription(pulseCountType.getItemDescription().toString());
		pulseCount.setItemUnits(pulseCountType.getItemUnits());
		pulseCount.setSiScaleCode(String.valueOf(pulseCountType.getPulseFactor()));
	}
}
