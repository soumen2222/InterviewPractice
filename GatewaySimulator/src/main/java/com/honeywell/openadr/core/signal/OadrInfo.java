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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oadrKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="oadrValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "oadrKey",
    "oadrValue"
})
@XmlRootElement(name = "oadrInfo")
public class OadrInfo
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String oadrKey;
    @XmlElement(required = true)
    protected String oadrValue;

    /**
     * Gets the value of the oadrKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOadrKey() {
        return oadrKey;
    }

    /**
     * Sets the value of the oadrKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOadrKey(String value) {
        this.oadrKey = value;
    }

    /**
     * Gets the value of the oadrValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOadrValue() {
        return oadrValue;
    }

    /**
     * Sets the value of the oadrValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOadrValue(String value) {
        this.oadrValue = value;
    }

}
