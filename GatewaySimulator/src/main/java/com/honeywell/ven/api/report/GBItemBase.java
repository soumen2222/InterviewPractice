package com.honeywell.ven.api.report;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GBItemBase extends ItemBase{
	
	private FeedType feed;

	/**
	 * @return the feed
	 */
	public FeedType getFeed() {
		return feed;
	}

	/**
	 * @param feed the feed to set
	 */
	public void setFeed(FeedType feed) {
		this.feed = feed;
	}

}
