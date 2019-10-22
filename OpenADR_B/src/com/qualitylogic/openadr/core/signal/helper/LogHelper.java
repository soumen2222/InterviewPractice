package com.qualitylogic.openadr.core.signal.helper;

import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.Trace;

public class LogHelper {

	public static void addTrace(String trace) {
		Trace sessionTrace = TestSession.getTraceObj();
		if (sessionTrace != null) {
			sessionTrace.getLogFileContentTrace().append("\n" + trace + "\n");
		}
	}

	
	public static void addTraceAndConsole(String trace) {
		System.out.println(trace);
		Trace sessionTrace = TestSession.getTraceObj();
		if (sessionTrace != null) {
			sessionTrace.getLogFileContentTrace().append("\n" + trace + "\n");
		}
	}

	public static void setResult(LogResult logResult) {
		Trace sessionTrace = TestSession.getTraceObj();
		if (sessionTrace != null) {
			sessionTrace.setResult(logResult.toString());
		}

	}

	public static void logCurrentTime() {
		LogHelper.addTrace("Current Time : " + OadrUtil.getCurrentTime());
	}
}
