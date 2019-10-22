package com.honeywell.ven.api.registration;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum TransportName {
	
	
	simpleHttp("simpleHttp"),

	xmpp("xmpp");
	
    private final String value;

    TransportName(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TransportName fromValue(String v) {
        for (TransportName c: TransportName.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
