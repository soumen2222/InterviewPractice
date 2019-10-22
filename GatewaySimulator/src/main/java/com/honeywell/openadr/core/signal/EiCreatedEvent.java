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
 *         &lt;element ref="{http://docs.oasis-open.org/ns/energyinterop/201110}eiResponse"/>
 *         &lt;element ref="{http://docs.oasis-open.org/ns/energyinterop/201110}eventResponses" minOccurs="0"/>
 *         &lt;element ref="{http://docs.oasis-open.org/ns/energyinterop/201110}venID"/>
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
    "eiResponse",
    "eventResponses",
    "venID"
})
@XmlRootElement(name = "eiCreatedEvent", namespace = "http://docs.oasis-open.org/ns/energyinterop/201110/payloads")
public class EiCreatedEvent
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "http://docs.oasis-open.org/ns/energyinterop/201110", required = true)
    protected EiResponseType eiResponse;
    @XmlElement(namespace = "http://docs.oasis-open.org/ns/energyinterop/201110")
    protected EventResponses eventResponses;
    @XmlElement(namespace = "http://docs.oasis-open.org/ns/energyinterop/201110", required = true)
    protected String venID;

    /**
     * Gets the value of the eiResponse property.
     * 
     * @return
     *     possible object is
     *     {@link EiResponseType }
     *     
     */
    public EiResponseType getEiResponse() {
        return eiResponse;
    }

    /**
     * Sets the value of the eiResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link EiResponseType }
     *     
     */
    public void setEiResponse(EiResponseType value) {
        this.eiResponse = value;
    }

    /**
     * Gets the value of the eventResponses property.
     * 
     * @return
     *     possible object is
     *     {@link EventResponses }
     *     
     */
    public EventResponses getEventResponses() {
        return eventResponses;
    }

    /**
     * Sets the value of the eventResponses property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventResponses }
     *     
     */
    public void setEventResponses(EventResponses value) {
        this.eventResponses = value;
    }

    /**
     * Gets the value of the venID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVenID() {
        return venID;
    }

    /**
     * Sets the value of the venID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVenID(String value) {
        this.venID = value;
    }

}
