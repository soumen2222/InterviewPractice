package com.honeywell.ven.api.opt;

import java.io.Serializable;
import java.util.List;

public class ServiceArea implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<FeatureCollection> featureCollectionList;

	public List<FeatureCollection> getFeatureCollectionList() {
		return featureCollectionList;
	}

	public void setFeatureCollectionList(List<FeatureCollection> featureCollectionList) {
		this.featureCollectionList = featureCollectionList;
	}

}
