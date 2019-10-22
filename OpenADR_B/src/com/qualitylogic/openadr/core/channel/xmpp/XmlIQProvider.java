package com.qualitylogic.openadr.core.channel.xmpp;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.provider.IQProvider;
import org.xmlpull.v1.XmlPullParser;

import com.qualitylogic.openadr.core.channel.util.XmlUtil;

/**
 * Smack IQProvider implementation interface for parsing XML IQ packets.
 */
public class XmlIQProvider implements IQProvider {

	@Override
	public IQ parseIQ(final XmlPullParser parser) throws Exception {
		String xml = XmlUtil.xmlPullParserToString(parser);
		
		// XMPP adds this XML namespace automatically, so we remove it here
		xml = xml.replace(JABBER_STREAM, "");
		
		return new XmlIQ(xml);
	}

	private static final String JABBER_STREAM = " xmlns:stream=\"http://etherx.jabber.org/streams\"";
}
