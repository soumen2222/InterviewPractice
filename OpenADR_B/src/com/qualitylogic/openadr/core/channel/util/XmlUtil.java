package com.qualitylogic.openadr.core.channel.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.dom2_builder.DOM2XmlPullBuilder;

public final class XmlUtil {
	private XmlUtil() {
	}
	
	public static String removeXmlDeclaration(final String xml) {
		String result = xml;
		if (result.startsWith(XML_DECLARATION)) {
			result = result.replace(XML_DECLARATION, "");
		}
		return result;
	}
	
	public static String xmlPullParserToString(final XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
		DOM2XmlPullBuilder domBuilder = new DOM2XmlPullBuilder();
		Element element = domBuilder.parseSubTree(xmlPullParser);
		return nodeToString(element);
	}
	
	public static String nodeToString(final Node node) {
		final boolean indent = true;
		
		String xml = null;

		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			
			if (indent) {
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			}

			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(node);
			transformer.transform(source, result);
			Writer writer = result.getWriter();
			xml = writer.toString().trim();
		} catch (TransformerException e) {
			throw new IllegalStateException("Failed to transform node.");
		}

		return xml;
	}

	public final static String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
}
