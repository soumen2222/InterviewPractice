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
 * currency
 * 
 * <p>Java class for currencyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="currencyType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/ns/emix/2011/06}ItemBaseType">
 *       &lt;sequence>
 *         &lt;element name="itemDescription" type="{http://openadr.org/oadr-2.0b/2012/07}currencyItemDescriptionType"/>
 *         &lt;element name="itemUnits" type="{urn:un:unece:uncefact:codelist:standard:5:ISO42173A:2010-04-07}ISO3AlphaCurrencyCodeContentType"/>
 *         &lt;element ref="{http://docs.oasis-open.org/ns/emix/2011/06/siscale}siScaleCode"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "currencyType", propOrder = {
    "itemDescription",
    "itemUnits",
    "siScaleCode"
})
public class CurrencyType
    extends ItemBaseType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected CurrencyItemDescriptionType itemDescription;
    @XmlElement(required = true)
    protected ISO3AlphaCurrencyCodeContentType itemUnits;
    @XmlElement(namespace = "http://docs.oasis-open.org/ns/emix/2011/06/siscale", required = true)
    protected String siScaleCode;

    /**
     * Gets the value of the itemDescription property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyItemDescriptionType }
     *     
     */
    public CurrencyItemDescriptionType getItemDescription() {
        return itemDescription;
    }

    /**
     * Sets the value of the itemDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyItemDescriptionType }
     *     
     */
    public void setItemDescription(CurrencyItemDescriptionType value) {
        this.itemDescription = value;
    }

    /**
     * Gets the value of the itemUnits property.
     * 
     * @return
     *     possible object is
     *     {@link ISO3AlphaCurrencyCodeContentType }
     *     
     */
    public ISO3AlphaCurrencyCodeContentType getItemUnits() {
        return itemUnits;
    }

    /**
     * Sets the value of the itemUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link ISO3AlphaCurrencyCodeContentType }
     *     
     */
    public void setItemUnits(ISO3AlphaCurrencyCodeContentType value) {
        this.itemUnits = value;
    }

    /**
     * Gets the value of the siScaleCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiScaleCode() {
        return siScaleCode;
    }

    /**
     * Sets the value of the siScaleCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiScaleCode(String value) {
        this.siScaleCode = value;
    }

}
