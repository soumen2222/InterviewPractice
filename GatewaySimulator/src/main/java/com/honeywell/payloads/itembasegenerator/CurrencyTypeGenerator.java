package com.honeywell.payloads.itembasegenerator;

import com.honeywell.openadr.core.signal.ItemBaseType;

import com.honeywell.openadr.core.signal.CurrencyType;
import com.honeywell.ven.api.report.Currency;
import com.honeywell.ven.api.report.ItemBase;

public class CurrencyTypeGenerator implements ItemBaseFactory{

	private Currency currency;
	
	@Override
	public ItemBase getData() {		
		return currency;
	}

	@Override
	public void setData(ItemBaseType oadrItemBase) {
		CurrencyType localCurrencyType = (CurrencyType) oadrItemBase;
		if(null == currency)
			currency = new Currency();
		currency.setItemDescription(localCurrencyType.getItemDescription().toString());
		currency.setItemUnits(localCurrencyType.getItemUnits().value());
		currency.setSiScaleCode(localCurrencyType.getSiScaleCode());
	}

}
