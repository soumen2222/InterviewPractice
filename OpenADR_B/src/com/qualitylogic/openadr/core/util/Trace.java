package com.qualitylogic.openadr.core.util;

import java.util.Date;

public class Trace {

	private String testCaseName;
	private String testStartTime;
	private String testEndTime;
	private String result = "NA";
	private String logFileName;
	private StringBuffer logFileContentTrace = new StringBuffer();

	public Trace(String testCaseName) {

		setTestCaseName(testCaseName);
		setLogFileName(OadrUtil.createUniqueTraceFileName());
		setTestStartTime(new Date().toString());

	}

	public StringBuffer getLogFileContentTrace() {
		return logFileContentTrace;
	}

	public String getLogFileName() {
		return logFileName;
	}

	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}

	public String getTestCaseName() {
		return testCaseName;
	}

	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}

	public String getTestStartTime() {
		return testStartTime;
	}

	public void setTestStartTime(String testStartTime) {
		this.testStartTime = testStartTime;
	}

	public String getTestEndTime() {
		return testEndTime;
	}

	public void setTestEndTime(String testEndTime) {
		this.testEndTime = testEndTime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
