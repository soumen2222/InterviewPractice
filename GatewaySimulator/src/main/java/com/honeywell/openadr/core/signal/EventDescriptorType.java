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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for eventDescriptorType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eventDescriptorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://docs.oasis-open.org/ns/energyinterop/201110}eventID"/>
 *         &lt;element ref="{http://docs.oasis-open.org/ns/energyinterop/201110}modificationNumber"/>
 *         &lt;element name="modificationDateTime" type="{urn:ietf:params:xml:ns:icalendar-2.0}DateTimeType" minOccurs="0"/>
 *         &lt;element name="modificationReason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="priority" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *         &lt;element name="eiMarketContext">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://docs.oasis-open.org/ns/emix/2011/06}marketContext"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{http://docs.oasis-open.org/ns/energyinterop/201110}createdDateTime"/>
 *         &lt;element ref="{http://docs.oasis-open.org/ns/energyinterop/201110}eventStatus"/>
 *         &lt;element name="testEvent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vtnComment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eventDescriptorType", namespace = "http://docs.oasis-open.org/ns/energyinterop/201110", propOrder = {
    "eventID",
    "modificationNumber",
    "modificationDateTime",
    "modificationReason",
    "priority",
    "eiMarketContext",
    "createdDateTime",
    "eventStatus",
    "testEvent",
    "vtnComment"
})
public class EventDescriptorType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String eventID;
    @XmlSchemaType(name = "unsignedInt")
    protected long modificationNumber;
    protected XMLGregorianCalendar modificationDateTime;
    protected String modificationReason;
    @XmlSchemaType(name = "unsignedInt")
    protected Long priority;
    @XmlElement(required = true)
    protected EventDescriptorType.EiMarketContext eiMarketContext;
    @XmlElement(required = true)
    protected XMLGregorianCalendar createdDateTime;
    @XmlElement(required = true)
    protected EventStatusEnumeratedType eventStatus;
    protected String testEvent;
    protected String vtnComment;

    /**
     * Gets the value of the eventID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventID() {
        return eventID;
    }

    /**
     * Sets the value of the eventID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventID(String value) {
        this.eventID = value;
    }

    /**
     * Gets the value of the modificationNumber property.
     * 
     */
    public long getModificationNumber() {
        return modificationNumber;
    }

    /**
     * Sets the value of the modificationNumber property.
     * 
     */
    public void setModificationNumber(long value) {
        this.modificationNumber = value;
    }

    /**
     * Gets the value of the modificationDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModificationDateTime() {
        return modificationDateTime;
    }

    /**
     * Sets the value of the modificationDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModificationDateTime(XMLGregorianCalendar value) {
        this.modificationDateTime = value;
    }

    /**
     * Gets the value of the modificationReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModificationReason() {
        return modificationReason;
    }

    /**
     * Sets the value of the modificationReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModificationReason(String value) {
        this.modificationReason = value;
    }

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPriority(Long value) {
        this.priority = value;
    }

    /**
     * Gets the value of the eiMarketContext property.
     * 
     * @return
     *     possible object is
     *     {@link EventDescriptorType.EiMarketContext }
     *     
     */
    public EventDescriptorType.EiMarketContext getEiMarketContext() {
        return eiMarketContext;
    }

    /**
     * Sets the value of the eiMarketContext property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventDescriptorType.EiMarketContext }
     *     
     */
    public void setEiMarketContext(EventDescriptorType.EiMarketContext value) {
        this.eiMarketContext = value;
    }

    /**
     * Gets the value of the createdDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreatedDateTime() {
        return createdDateTime;
    }

    /**
     * Sets the value of the createdDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreatedDateTime(XMLGregorianCalendar value) {
        this.createdDateTime = value;
    }

    /**
     * An indication of the event state: far, near, active, canceled, completed
     * 
     * @return
     *     possible object is
     *     {@link EventStatusEnumeratedType }
     *     
     */
    public EventStatusEnumeratedType getEventStatus() {
        return eventStatus;
    }

    /**
     * Sets the value of the eventStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventStatusEnumeratedType }
     *     
     */
    public void setEventStatus(EventStatusEnumeratedType value) {
        this.eventStatus = value;
    }

    /**
     * Gets the value of the testEvent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestEvent() {
        return testEvent;
    }

    /**
     * Sets the value of the testEvent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestEvent(String value) {
        this.testEvent = value;
    }

    /**
     * Gets the value of the vtnComment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVtnComment() {
        return vtnComment;
    }

    /**
     * Sets the value of the vtnComment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVtnComment(String value) {
        this.vtnComment = value;
    }


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
     *         &lt;element ref="{http://docs.oasis-open.org/ns/emix/2011/06}marketContext"/>
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
        "marketContext"
    })
    public static class EiMarketContext
        implements Serializable
    {

        private final static long serialVersionUID = 1L;
        @XmlElement(namespace = "http://docs.oasis-open.org/ns/emix/2011/06", required = true)
        protected String marketContext;

        /**
         * Gets the value of the marketContext property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMarketContext() {
            return marketContext;
        }

        /**
         * Sets the value of the marketContext property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMarketContext(String value) {
            this.marketContext = value;
        }

    }

}