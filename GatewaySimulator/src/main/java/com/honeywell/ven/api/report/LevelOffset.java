package com.honeywell.ven.api.report;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LevelOffset implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Float current;
	private Float max;
	private Float min;
	private Float normal;
	/**
	 * @return the current
	 */
	public Float getCurrent() {
		return current;
	}
	/**
	 * @param current the current to set
	 */
	public void setCurrent(Float current) {
		this.current = current;
	}
	/**
	 * @return the max
	 */
	public Float getMax() {
		return max;
	}
	/**
	 * @param max the max to set
	 */
	public void setMax(Float max) {
		this.max = max;
	}
	/**
	 * @return the min
	 */
	public Float getMin() {
		return min;
	}
	/**
	 * @param min the min to set
	 */
	public void setMin(Float min) {
		this.min = min;
	}
	/**
	 * @return the normal
	 */
	public Float getNormal() {
		return normal;
	}
	/**
	 * @param normal the normal to set
	 */
	public void setNormal(Float normal) {
		this.normal = normal;
	}


}
