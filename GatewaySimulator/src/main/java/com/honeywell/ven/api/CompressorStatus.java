package com.honeywell.ven.api;

public enum CompressorStatus {

	OFF("Off"),
	HEATING("Heating"),
	COOLING("Cooling");
	
	private final String value;
	
	CompressorStatus(String v) {
	    value = v;
	}
	
	public String value() {
	    return value;
	}
	
	public static CompressorStatus fromValue(String v) {
	    for (CompressorStatus c: CompressorStatus.values()) {
	        if (c.value.equalsIgnoreCase(v)) {
	            return c;
	        }
	    }
	    throw new IllegalArgumentException(v);
	}
}
