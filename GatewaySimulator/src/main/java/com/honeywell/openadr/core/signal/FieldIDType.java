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
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>Java class for FieldIDType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FieldIDType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{http://www.w3.org/2009/xmldsig11#}Prime"/>
 *         &lt;element ref="{http://www.w3.org/2009/xmldsig11#}TnB"/>
 *         &lt;element ref="{http://www.w3.org/2009/xmldsig11#}PnB"/>
 *         &lt;element ref="{http://www.w3.org/2009/xmldsig11#}GnB"/>
 *         &lt;any processContents='lax' namespace='##other'/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FieldIDType", namespace = "http://www.w3.org/2009/xmldsig11#", propOrder = {
    "prime",
    "tnB",
    "pnB",
    "gnB",
    "any"
})
public class FieldIDType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "Prime")
    protected PrimeFieldParamsType prime;
    @XmlElement(name = "TnB")
    protected TnBFieldParamsType tnB;
    @XmlElement(name = "PnB")
    protected PnBFieldParamsType pnB;
    @XmlElement(name = "GnB")
    protected CharTwoFieldParamsType gnB;
    @XmlAnyElement(lax = true)
    protected Object any;

    /**
     * Gets the value of the prime property.
     * 
     * @return
     *     possible object is
     *     {@link PrimeFieldParamsType }
     *     
     */
    public PrimeFieldParamsType getPrime() {
        return prime;
    }

    /**
     * Sets the value of the prime property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrimeFieldParamsType }
     *     
     */
    public void setPrime(PrimeFieldParamsType value) {
        this.prime = value;
    }

    /**
     * Gets the value of the tnB property.
     * 
     * @return
     *     possible object is
     *     {@link TnBFieldParamsType }
     *     
     */
    public TnBFieldParamsType getTnB() {
        return tnB;
    }

    /**
     * Sets the value of the tnB property.
     * 
     * @param value
     *     allowed object is
     *     {@link TnBFieldParamsType }
     *     
     */
    public void setTnB(TnBFieldParamsType value) {
        this.tnB = value;
    }

    /**
     * Gets the value of the pnB property.
     * 
     * @return
     *     possible object is
     *     {@link PnBFieldParamsType }
     *     
     */
    public PnBFieldParamsType getPnB() {
        return pnB;
    }

    /**
     * Sets the value of the pnB property.
     * 
     * @param value
     *     allowed object is
     *     {@link PnBFieldParamsType }
     *     
     */
    public void setPnB(PnBFieldParamsType value) {
        this.pnB = value;
    }

    /**
     * Gets the value of the gnB property.
     * 
     * @return
     *     possible object is
     *     {@link CharTwoFieldParamsType }
     *     
     */
    public CharTwoFieldParamsType getGnB() {
        return gnB;
    }

    /**
     * Sets the value of the gnB property.
     * 
     * @param value
     *     allowed object is
     *     {@link CharTwoFieldParamsType }
     *     
     */
    public void setGnB(CharTwoFieldParamsType value) {
        this.gnB = value;
    }

    /**
     * Gets the value of the any property.
     * 
     * @return
     *     possible object is
     *     {@link Element }
     *     {@link Object }
     *     
     */
    public Object getAny() {
        return any;
    }

    /**
     * Sets the value of the any property.
     * 
     * @param value
     *     allowed object is
     *     {@link Element }
     *     {@link Object }
     *     
     */
    public void setAny(Object value) {
        this.any = value;
    }

}
