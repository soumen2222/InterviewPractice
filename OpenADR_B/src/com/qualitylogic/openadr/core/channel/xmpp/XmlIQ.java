package com.qualitylogic.openadr.core.channel.xmpp;

import org.jivesoftware.smack.packet.IQ;

/**
 * IQ (Info/Query) packet for holding an XML string. 
 */
public class XmlIQ extends IQ {
	private final String xml;

	public XmlIQ(final String xml) {
		this.xml = xml;
	}
	
	public String getChildElementXML() {
		return xml;
	}
}
