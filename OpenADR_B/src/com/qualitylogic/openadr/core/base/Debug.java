package com.qualitylogic.openadr.core.base;

public final class Debug {
	public final static boolean allOn = false;
	
	public final static boolean setValidationErrorFoundStackTrace = false || allOn;
	
	public final static boolean printLogWhenConformanceRuleErrorFound = false || allOn;
	
	public final static boolean printHttpSenderStrackTrace = false || allOn;

	public final static boolean prettyPrintXmlDb = false || allOn;

	public final static boolean printBaseTestCaseStackTrace = false || allOn;

	public final static boolean printSetRegistrationIDStackTrace = false || allOn;

	public final static boolean printResetDbXmlStackTrace = false || allOn;

	public final static boolean printHandlerStackTrace = false || allOn;

	public final static boolean printSetVenIDStackTrace = false || allOn;

	private Debug() {
	}
}
