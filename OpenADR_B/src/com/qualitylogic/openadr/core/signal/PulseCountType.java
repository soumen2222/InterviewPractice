//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.06.19 at 04:24:15 AM PDT 
//


package com.qualitylogic.openadr.core.signal;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Pulse Count
 * 
 * <p>Java class for pulseCountType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pulseCountType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/ns/emix/2011/06}ItemBaseType">
 *       &lt;sequence>
 *         &lt;element name="itemDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="itemUnits" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{http://openadr.org/oadr-2.0b/2012/07}pulseFactor"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pulseCountType", propOrder = {
    "itemDescription",
    "itemUnits",
    "pulseFactor"
})
public class PulseCountType
    extends ItemBaseType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String itemDescription;
    @XmlElement(required = true)
    protected String itemUnits;
    protected float pulseFactor;

    /**
     * Gets the value of the itemDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     * Sets the value of the itemDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemDescription(String value) {
        this.itemDescription = value;
    }

    /**
     * Gets the value of the itemUnits property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemUnits() {
        return itemUnits;
    }

    /**
     * Sets the value of the itemUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemUnits(String value) {
        this.itemUnits = value;
    }

    /**
     * Gets the value of the pulseFactor property.
     * 
     */
    public float getPulseFactor() {
        return pulseFactor;
    }

    /**
     * Sets the value of the pulseFactor property.
     * 
     */
    public void setPulseFactor(float value) {
        this.pulseFactor = value;
    }

}
