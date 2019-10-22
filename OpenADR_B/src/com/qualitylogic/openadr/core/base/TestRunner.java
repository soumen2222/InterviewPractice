package com.qualitylogic.openadr.core.base;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

import com.qualitylogic.openadr.core.util.PropertiesFileReader;

public final class TestRunner {
	private TestRunner(){
	}
	
	public static void executeTestCase(final String className) {
		String stClassName = null;
		String thClassName = null;
		if (className.contains("selftest")) {
			stClassName = className;
			thClassName = getThClassName(className);
		} else {
			stClassName = getStClassName(className);
			thClassName = className;
		}
		
		String firstCommand = null;
		String secondCommand = null;
		if (stClassName.endsWith("_1")) {
			firstCommand = stClassName;
			secondCommand = thClassName;
		} else {
			firstCommand = thClassName;
			secondCommand = stClassName;
		}
		
		runTestCommand(firstCommand, "1");
		sleep(2000);
		
		while (isPromptOpen()) {
			sleep(1000);
		}
		
        runTestCommand(secondCommand, "2");
        sleep(1000);
	}
	
	private static boolean isPromptOpen() {
		boolean open = false;
		try (RandomAccessFile lockFile = new RandomAccessFile("./src/runner/prompt.lock", "rw")) {
			FileLock promptLock = lockFile.getChannel().tryLock();
			
			if (promptLock != null) {
				open = false;
				promptLock.release();
			} else {
				open = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return open;
	}
	
	private static void runTestCommand(final String command, final String suffix) {
		try {
			File directory = new File(WORKING_DIR);

			String batchFile = null;
			PropertiesFileReader properties = new PropertiesFileReader();
			if (properties.isTestRunnerShortcuts()) {
				batchFile = directory.getCanonicalPath() + "/" + RUNTEST_BAT.replace(".bat", suffix + ".bat.lnk");
			} else {
				batchFile = directory.getCanonicalPath() + "/" + RUNTEST_BAT;
			}
			
			ProcessBuilder pb = new ProcessBuilder("cmd", "/C", "start", batchFile, command);
			pb.directory(directory);
			pb.start();
		} catch (IOException e) {
			throw new IllegalStateException("Cannot run test=" + command);
		}
	}

	private static String getStClassName(final String thClassName) {
		String stClassName = null;
		if (thClassName.contains(".A_")) {
			stClassName = thClassName.
					replace("ven.", "ven.selftest_a_ported.").
					replace("vtn.", "vtn.selftest_a_ported.").
					replace("_TH_VEN_1", "_DUT_VTN").
					replace("_TH_VTN_1", "_DUT_VEN").
					replace("_TH_VEN", "_DUT_VTN_1").
					replace("_TH_VTN", "_DUT_VEN_1");
		} else {
			stClassName = getPartnerClassName(thClassName);
		}
		
		return stClassName;
	}

	private static String getThClassName(final String stClassName) {
		String thClassName = null;
		if (stClassName.contains(".A_")) {
			thClassName = stClassName.
					replace(".selftest_a_ported.", ".").
					replace(".selftest_a_ported.", ".").
					replace("_DUT_VEN_1", "_TH_VTN").
					replace("_DUT_VTN_1", "_TH_VEN").
					replace("_DUT_VEN", "_TH_VTN_1").
					replace("_DUT_VTN", "_TH_VEN_1");
		} else {
			thClassName = getPartnerClassName(stClassName);
		}
		
		return thClassName;
	}

	private static String getPartnerClassName(final String className) {
		boolean first = (className.endsWith("_1"));
		
		String result = (className.contains("ven.")) ? 
				className.replace("ven.", "vtn.") : 
				className.replace("vtn.", "ven.");
				
		if (first) {
			result = (result.contains("_TH_VEN_1")) ? 
					result.replace("_TH_VEN_1", "_TH_VTN") : 
					result.replace("_TH_VTN_1", "_TH_VEN");
		} else {
			result = (result.contains("_TH_VEN")) ?
					result.replace("_TH_VEN", "_TH_VTN_1") :
					result.replace("_TH_VTN", "_TH_VEN_1");
		}
		return result;
	}

	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static final String WORKING_DIR = ".";

    private static final String RUNTEST_BAT = "src/runner/runtest.bat";
}
