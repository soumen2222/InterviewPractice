package com.soumen.generic.inheritence.Example2;

import java.io.Serializable;



public class PayloadResourceStatusType extends PayloadBaseType implements Serializable
{

private final static long serialVersionUID = 1L;
protected boolean oadrOnline;
protected boolean oadrManualOverride;

/**
 * Gets the value of the oadrOnline property.
 * 
 */
public boolean isOadrOnline() {
    return oadrOnline;
}

/**
 * Sets the value of the oadrOnline property.
 * 
 */
public void setOadrOnline(boolean value) {
    this.oadrOnline = value;
}

/**
 * Gets the value of the oadrManualOverride property.
 * 
 */
public boolean isOadrManualOverride() {
    return oadrManualOverride;
}

/**
 * Sets the value of the oadrManualOverride property.
 * 
 */
public void setOadrManualOverride(boolean value) {
    this.oadrManualOverride = value;
}

/**
 * Gets the value of the oadrLoadControlState property.
 * 
 * @return
 *     possible object is
 *     {@link OadrLoadControlStateType }
 *     
 */

}

