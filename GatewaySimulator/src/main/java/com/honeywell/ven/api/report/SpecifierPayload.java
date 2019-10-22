package com.honeywell.ven.api.report;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SpecifierPayload  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ItemBase itemBase;
	private String readingType;
	private String rId;
	
	
	/**
	 * @return the itemBase
	 */
	public ItemBase getItemBase() {
		return itemBase;
	}
	/**
	 * @param itemBase the itemBase to set
	 */
	public void setItemBase(ItemBase itemBase) {
		this.itemBase = itemBase;
	}
	/**
	 * @return the readingType
	 */
	public String getReadingType() {
		return readingType;
	}
	/**
	 * @param readingType the readingType to set
	 */
	public void setReadingType(String readingType) {
		this.readingType = readingType;
	}
	/**
	 * @return the rId
	 */
	public String getrId() {
		return rId;
	}
	/**
	 * @param rId the rId to set
	 */
	public void setrId(String rId) {
		this.rId = rId;
	}

}
