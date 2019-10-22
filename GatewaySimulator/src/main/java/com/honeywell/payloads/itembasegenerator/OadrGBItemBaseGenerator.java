package com.honeywell.payloads.itembasegenerator;

import com.honeywell.openadr.core.signal.ItemBaseType;
import com.honeywell.openadr.core.signal.FeedType;

import com.honeywell.openadr.core.signal.OadrGBItemBase;
import com.honeywell.ven.api.report.GBItemBase;
import com.honeywell.ven.api.report.ItemBase;

public class OadrGBItemBaseGenerator implements ItemBaseFactory{

	private GBItemBase gbItemBase;
	
	@Override
	public ItemBase getData() {		
		return gbItemBase;
	}

	@Override
	public void setData(ItemBaseType oadrItemBase) {
		OadrGBItemBase oadrGBItemBase = (OadrGBItemBase) oadrItemBase;
		if(null == gbItemBase)
			gbItemBase = new GBItemBase();
		
		FeedType oadrFeedType = oadrGBItemBase.getFeed();
		com.honeywell.ven.api.report.FeedType feedType = new com.honeywell.ven.api.report.FeedType();
		
		feedType.setBase(oadrFeedType.getBase());
		feedType.setLang(oadrFeedType.getLang());
		gbItemBase.setFeed(feedType);
	}

}
