package com.honeywell.payloads.itembasegenerator;

import com.honeywell.openadr.core.signal.ItemBaseType;

import com.honeywell.openadr.core.signal.TemperatureType;
import com.honeywell.ven.api.report.ItemBase;
import com.honeywell.ven.api.report.Temperature;

public class TemperatureTypeGenerator implements ItemBaseFactory{

	private Temperature temperature;
	
	@Override
	public ItemBase getData() {		
		return temperature;
	}

	@Override
	public void setData(ItemBaseType oadrItemBase) {
		TemperatureType temperatureType = (TemperatureType) oadrItemBase;
		if(null == temperature)
			temperature = new Temperature();
		temperature.setItemDescription(temperatureType.getItemDescription().toString());
		temperature.setItemUnits(temperatureType.getItemUnits().value());
		temperature.setSiScaleCode(temperatureType.getSiScaleCode());	
	}

}
