package com.qualitylogic.openadr.core.base;

import java.io.RandomAccessFile;
import java.util.Date;

import com.qualitylogic.openadr.core.bean.ModeType;
import com.qualitylogic.openadr.core.channel.util.StringUtil;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.exception.CancelledException;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.exception.NotApplicableException;
import com.qualitylogic.openadr.core.exception.ValidationException;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.util.XMLDBUtil;
import com.qualitylogic.openadr.core.util.XMLLogHelper;

public abstract class BaseTestCase {
	private String testCaseName = "";

	public void executeTestCase() {
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
		if ((propertiesFileReader.isTestRunner() || TestSession.isTestRunner()) && "selftest".equals(System.getProperty("th.test"))) {
			String className = this.getClass().getName();
			TestRunner.executeTestCase(className);
			return;
		}
		
		String className = this.getClass().getName();
		String lockFilename = "./src/runner/" + ((className.contains("_VTN")) ? "vtn" : "ven") + ".lock";
		
		try (RandomAccessFile lockFile = new RandomAccessFile(lockFilename, "rw")) {
			lockFile.getChannel().lock();

			init();
			try {
				test();
				if (OadrUtil.isValidationErrorPresent()) {
					throw new ValidationException();
				}
				
				if (newTest) {
					LogHelper.setResult(LogResult.PASS);
					LogHelper.addTrace("The expected flow is completed");
					LogHelper.addTrace("Test Case has PASSED");
				}
				
			} catch (NotApplicableException ex) {
				System.out.println(ex.getMessage());
				LogHelper.addTrace(ex.getMessage());
				LogHelper.addTrace("N/A");
				LogHelper.setResult(LogResult.NA);
				
			} catch (CancelledException ex) {
				System.out.println(ex.getMessage());
				LogHelper.addTrace(ex.getMessage());
				LogHelper.setResult(LogResult.NA);
				
			} catch (FailedException ex) {
				System.out.println(ex.getMessage());
				LogHelper.addTrace(ex.getMessage());
				LogHelper.addTrace("Test Case has Failed");
				LogHelper.setResult(LogResult.FAIL);
				
			} catch (ValidationException ex) {
				System.out.println(ex.getMessage());
				LogHelper.addTrace("Validation Error(s) are present");
				LogHelper.addTrace("Test Case has Failed");
				LogHelper.setResult(LogResult.FAIL);
				
			} catch (Exception ex) {
				if (Debug.printBaseTestCaseStackTrace) {
					System.out.println(ex.getMessage());
				}
				OadrUtil.exceptionHandler(ex, ex.getMessage());

				LogHelper.addTrace("Test Case has Failed");
				LogHelper.setResult(LogResult.FAIL);

			
			}
			cleanUp();
			done();

		} catch (Exception ex) {
			OadrUtil.exceptionHandler(ex, "");

			LogHelper.addTrace("Test Case has Failed");
			LogHelper.setResult(LogResult.FAIL);

			try {
				cleanUp();
			} catch (Exception e) {
				OadrUtil.exceptionHandler(e, "");
			}
		}
	}

	private boolean enableLogging;

	public boolean isEnableLogging() {
		return enableLogging;
	}

	public void setEnableLogging(boolean enableLogging) {
		this.enableLogging = enableLogging;
	}

	public abstract void test() throws Exception;

	public abstract void cleanUp() throws Exception;

	private void abortIfNull(String key, String value) {
		if (value == null || value.trim().length() < 1) {
			System.out.println(" ERROR : Cannot read property value for '"
					+ key + "' in the properties file.");
			System.out.println(" *****Aborting testcase execution*****");
			System.exit(0);
		}
	}

	public void init() {
		
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

		if (propertiesFileReader.isXmppTransportType() && TestSession.getMode().equals(ModeType.PULL)) {
			TestSession.setValidationErrorFound(true);
			String errorMessage = "********************PULL test cases are not applicable for XMPP********************";
			System.out.println(errorMessage);
			LogHelper.setResult(LogResult.NA);
			System.exit(-1);
		}
		
		//String vtnURL = propertiesFileReader.get("VTN_EiEventURL");
		//abortIfNull("VTN_EiEventURL", vtnURL);
		//String venURL = propertiesFileReader.get("VEN_EiEvent");
		//abortIfNull("VEN_EiEvent", venURL);
		/*String venID = propertiesFileReader.getVenID();
		
		if(propertiesFileReader.isUseStaticVENID()){
			abortIfNull("VEN_ID", venID);
		}
		*/
		
		
		String vtnID = propertiesFileReader.get("VTN_ID");
		abortIfNull("VTN_ID", vtnID);
		String asyncResponseTimeout = propertiesFileReader
				.get("asyncResponseTimeout");
		abortIfNull("asyncResponseTimeout", asyncResponseTimeout);
		String createdEventAsynchTimeout = propertiesFileReader
				.get("createdEventAsynchTimeout");
		abortIfNull("createdEventAsynchTimeout", createdEventAsynchTimeout);
		String testCaseBufferTimeout = propertiesFileReader
				.get("testCaseBufferTimeout");
		abortIfNull("testCaseBufferTimeout", testCaseBufferTimeout);
		String SchemaFile = propertiesFileReader.get("SchemaFile");
		abortIfNull("SchemaFile", SchemaFile);
		// String logPath = propertiesFileReader.get("logPath");
		// abortIfNull("logPath", logPath);
		// String logFileName = propertiesFileReader.get("logFileName");
		// abortIfNull("logFileName", logFileName);

		@SuppressWarnings("rawtypes")
		Class cls = this.getClass();
		testCaseName = OadrUtil.getClassName(cls);
		Trace trace = null;
		if (isEnableLogging()) {
			trace = new Trace(testCaseName);
		}
		TestSession.setTraceObj(trace);
		System.out.print("\nStarted " + testCaseName + " at " + new Date()
				+ "\n");
	
		
	}

	public void done() {

		XMLLogHelper.writeTestLog(testCaseName);

	}

	protected void pause(int seconds) {
		
		long pauseTimeout = System.currentTimeMillis() + (seconds * 1000);

		if(seconds>30){
			LogHelper.addTraceAndConsole("The test case is pausing for about "+seconds+" seconds");
		}
		
		//boolean isFirstLoop = true;
		while (System.currentTimeMillis() < pauseTimeout) {

			try {
				
/*				if(isFirstLoop==true && seconds>30){
					isFirstLoop=false;
					PropertiesFileReader properties = new PropertiesFileReader(); 

					if(properties.isTestPrompt()){
						UIUserPrompt uiUserPromptAction = new UIUserPrompt();
						uiUserPromptAction.Prompt("The test case is pausing for about "+seconds+" seconds");	
						if (!TestSession.isUserClickedContinuePlay()) {
							throw new CancelledException();
						}	
					}
				}
				*/
				
				Thread.sleep(500);

				
			} catch (InterruptedException e) {
			}
		}
	}

	protected void pauseForTestCaseTimeout() {

		
		TestSession.setTestCaseDone(false);
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
		long totalDurationInput = Long.valueOf(propertiesFileReader
				.get("asyncResponseTimeout"));
		long testCaseTimeout = System.currentTimeMillis() + totalDurationInput;
		boolean atleastOneValidationErrorPresent = false;
		long testCaseBufferTimeout = Long.valueOf(propertiesFileReader
				.get("testCaseBufferTimeout"));

		// System.out.println("pauseForTestCaseTimeout Start"+ new Date());

		while (System.currentTimeMillis() < testCaseTimeout) {

			// -------------Validation Error Check ------------- Begin
			atleastOneValidationErrorPresent = TestSession
					.isAtleastOneValidationErrorPresent();
			// break out of the loop if any validation errors are found
			if (atleastOneValidationErrorPresent) {
				System.out.println("validation error(s) is present "
						+ new Date());
				break;
			}
			// -------------Validation Error Check ------------- Begin

			// -------------Created Event Asynch Timeout------------- Begin
			// break out of the loop if the createdEventAsynchTimeout expired
			// Applicable for VTN as TestHarness(VEN Test Case)
			if (TestSession.isCreatedEventNotReceivedTillTimeout()) {
				System.out
						.println("Created Event not received breaking out of loop "
								+ new Date());
				// System.out.println("Create event not received "+ new Date());
				break;
			}

			// -------------Expected End met------------- End

			// -------------Created Event Asynch Timeout------------- Begin
			// break out of the loop if the createdEventAsynchTimeout expired
			if (TestSession.isTestCaseDone()) {

				long testCaseBufferTime = System.currentTimeMillis()
						+ testCaseBufferTimeout;

				// System.out.println("Buffer timeout loop start "+new Date());
				while (System.currentTimeMillis() < testCaseBufferTime) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
					}
				}

				// System.out.println("Buffer timeout loop End "+new Date());
				break;
			}

			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}

			// -------------Expected End met------------- End

		}
		 System.out.println("Timeout done"+ new Date());
	}

	protected boolean newTest;

	protected void newInit() {
	}

	protected void checkActiveRegistration() {
		String registrationID = new XMLDBUtil().getRegistrationID();
		if (StringUtil.isBlank(registrationID)){
			throw new FailedException("Registration is not active.");
		}
	}
}
