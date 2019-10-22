//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.08 at 01:07:11 PM IST 
//


package com.honeywell.openadr.core.signal;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * This is the payload for reports that require a status.
 * 
 * <p>Java class for oadrPayloadResourceStatusType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oadrPayloadResourceStatusType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/ns/energyinterop/201110}PayloadBaseType">
 *       &lt;sequence>
 *         &lt;element name="oadrOnline" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="oadrManualOverride" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element ref="{http://openadr.org/oadr-2.0b/2012/07}oadrLoadControlState" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oadrPayloadResourceStatusType", propOrder = {
    "oadrOnline",
    "oadrManualOverride",
    "oadrLoadControlState"
})
public class OadrPayloadResourceStatusType
    extends PayloadBaseType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected boolean oadrOnline;
    protected boolean oadrManualOverride;
    protected OadrLoadControlStateType oadrLoadControlState;

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
    public OadrLoadControlStateType getOadrLoadControlState() {
        return oadrLoadControlState;
    }

    /**
     * Sets the value of the oadrLoadControlState property.
     * 
     * @param value
     *     allowed object is
     *     {@link OadrLoadControlStateType }
     *     
     */
    public void setOadrLoadControlState(OadrLoadControlStateType value) {
        this.oadrLoadControlState = value;
    }

}
