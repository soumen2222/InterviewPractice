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
 * <p>Java class for oadrLoadControlStateType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oadrLoadControlStateType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oadrCapacity" type="{http://openadr.org/oadr-2.0b/2012/07}oadrLoadControlStateTypeType" minOccurs="0"/>
 *         &lt;element name="oadrLevelOffset" type="{http://openadr.org/oadr-2.0b/2012/07}oadrLoadControlStateTypeType" minOccurs="0"/>
 *         &lt;element name="oadrPercentOffset" type="{http://openadr.org/oadr-2.0b/2012/07}oadrLoadControlStateTypeType" minOccurs="0"/>
 *         &lt;element name="oadrSetPoint" type="{http://openadr.org/oadr-2.0b/2012/07}oadrLoadControlStateTypeType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oadrLoadControlStateType", propOrder = {
    "oadrCapacity",
    "oadrLevelOffset",
    "oadrPercentOffset",
    "oadrSetPoint"
})
public class OadrLoadControlStateType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected OadrLoadControlStateTypeType oadrCapacity;
    protected OadrLoadControlStateTypeType oadrLevelOffset;
    protected OadrLoadControlStateTypeType oadrPercentOffset;
    protected OadrLoadControlStateTypeType oadrSetPoint;

    /**
     * Gets the value of the oadrCapacity property.
     * 
     * @return
     *     possible object is
     *     {@link OadrLoadControlStateTypeType }
     *     
     */
    public OadrLoadControlStateTypeType getOadrCapacity() {
        return oadrCapacity;
    }

    /**
     * Sets the value of the oadrCapacity property.
     * 
     * @param value
     *     allowed object is
     *     {@link OadrLoadControlStateTypeType }
     *     
     */
    public void setOadrCapacity(OadrLoadControlStateTypeType value) {
        this.oadrCapacity = value;
    }

    /**
     * Gets the value of the oadrLevelOffset property.
     * 
     * @return
     *     possible object is
     *     {@link OadrLoadControlStateTypeType }
     *     
     */
    public OadrLoadControlStateTypeType getOadrLevelOffset() {
        return oadrLevelOffset;
    }

    /**
     * Sets the value of the oadrLevelOffset property.
     * 
     * @param value
     *     allowed object is
     *     {@link OadrLoadControlStateTypeType }
     *     
     */
    public void setOadrLevelOffset(OadrLoadControlStateTypeType value) {
        this.oadrLevelOffset = value;
    }

    /**
     * Gets the value of the oadrPercentOffset property.
     * 
     * @return
     *     possible object is
     *     {@link OadrLoadControlStateTypeType }
     *     
     */
    public OadrLoadControlStateTypeType getOadrPercentOffset() {
        return oadrPercentOffset;
    }

    /**
     * Sets the value of the oadrPercentOffset property.
     * 
     * @param value
     *     allowed object is
     *     {@link OadrLoadControlStateTypeType }
     *     
     */
    public void setOadrPercentOffset(OadrLoadControlStateTypeType value) {
        this.oadrPercentOffset = value;
    }

    /**
     * Gets the value of the oadrSetPoint property.
     * 
     * @return
     *     possible object is
     *     {@link OadrLoadControlStateTypeType }
     *     
     */
    public OadrLoadControlStateTypeType getOadrSetPoint() {
        return oadrSetPoint;
    }

    /**
     * Sets the value of the oadrSetPoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link OadrLoadControlStateTypeType }
     *     
     */
    public void setOadrSetPoint(OadrLoadControlStateTypeType value) {
        this.oadrSetPoint = value;
    }

}
