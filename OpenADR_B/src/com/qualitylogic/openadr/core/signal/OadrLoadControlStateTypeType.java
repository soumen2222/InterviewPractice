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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oadrLoadControlStateTypeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oadrLoadControlStateTypeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oadrMin" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="oadrMax" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="oadrCurrent" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="oadrNormal" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oadrLoadControlStateTypeType", propOrder = {
    "oadrMin",
    "oadrMax",
    "oadrCurrent",
    "oadrNormal"
})
public class OadrLoadControlStateTypeType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected Float oadrMin;
    protected Float oadrMax;
    protected float oadrCurrent;
    protected Float oadrNormal;

    /**
     * Gets the value of the oadrMin property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getOadrMin() {
        return oadrMin;
    }

    /**
     * Sets the value of the oadrMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setOadrMin(Float value) {
        this.oadrMin = value;
    }

    /**
     * Gets the value of the oadrMax property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getOadrMax() {
        return oadrMax;
    }

    /**
     * Sets the value of the oadrMax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setOadrMax(Float value) {
        this.oadrMax = value;
    }

    /**
     * Gets the value of the oadrCurrent property.
     * 
     */
    public float getOadrCurrent() {
        return oadrCurrent;
    }

    /**
     * Sets the value of the oadrCurrent property.
     * 
     */
    public void setOadrCurrent(float value) {
        this.oadrCurrent = value;
    }

    /**
     * Gets the value of the oadrNormal property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getOadrNormal() {
        return oadrNormal;
    }

    /**
     * Sets the value of the oadrNormal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setOadrNormal(Float value) {
        this.oadrNormal = value;
    }

}
