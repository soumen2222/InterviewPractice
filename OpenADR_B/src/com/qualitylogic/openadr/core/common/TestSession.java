package com.qualitylogic.openadr.core.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IRequestReregistrationAction;
import com.qualitylogic.openadr.core.action.IResponseCancelPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.action.IResponseCancelReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseCanceledOptAckAction;
import com.qualitylogic.openadr.core.action.IResponseCanceledPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.action.IResponseCanceledReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseCreateReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseCreatedOptAckAction;
import com.qualitylogic.openadr.core.action.IResponseCreatedPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.action.IResponseCreatedReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.IResponseRegisterReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseRegisteredReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseUpdateReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseUpdatedReportTypeAckAction;
import com.qualitylogic.openadr.core.base.Debug;
import com.qualitylogic.openadr.core.bean.ModeType;
import com.qualitylogic.openadr.core.bean.PingDistributeEventMap;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.signal.OadrCancelOptType;
import com.qualitylogic.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCancelReportType;
import com.qualitylogic.openadr.core.signal.OadrCanceledPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCanceledReportType;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatePartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrCreatedOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.OadrQueryRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.signal.OadrRequestReregistrationType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdatedReportType;
import com.qualitylogic.openadr.core.util.Trace;

public class TestSession {

	private static ArrayList<IDistributeEventAction> distributeEventActionList = new ArrayList<IDistributeEventAction>();
	
	private static ArrayList<IRequestReregistrationAction> requestReregistrationActionList = new ArrayList<IRequestReregistrationAction>();
	
	private static ArrayList<ICreatedEventAction> createEventActionList = new ArrayList<ICreatedEventAction>();
	
	private static ArrayList<IResponseEventAction> responseEventActionList = new ArrayList<IResponseEventAction>();
	
	private static ArrayList<IResponseCreatedOptAckAction> responseCreatedOptAckActionList = new ArrayList<IResponseCreatedOptAckAction>();
	
	
	private static ArrayList<IResponseCanceledOptAckAction> responseCanceledOptAckActionList = new ArrayList<IResponseCanceledOptAckAction>();
	
	
	private static ArrayList<OadrCreateOptType> createOptEventReceivedList = new ArrayList<OadrCreateOptType>();
	
	private static ArrayList<OadrCreateOptType> createOptEventSentList = new ArrayList<OadrCreateOptType>();
	
	
	private static ArrayList<OadrCreatedOptType> createdOptReceivedList = new ArrayList<OadrCreatedOptType>();
	
	private static ArrayList<OadrCancelOptType> createOadrCancelOptTypeList = new ArrayList<OadrCancelOptType>();
	
	
	private static ArrayList<OadrCreatePartyRegistrationType> oadrCreatePartyRegistrationTypeList = new ArrayList<OadrCreatePartyRegistrationType>();
	
	
	private static ArrayList<OadrQueryRegistrationType> oadrQueryRegistrationTypeReceivedList = new ArrayList<OadrQueryRegistrationType>();
	
	private static ArrayList<OadrRegisterReportType> oadrRegisterReportTypeReceivedList = new ArrayList<OadrRegisterReportType>();
	
	private static ArrayList<OadrRegisterReportType> oadrRegisterReportTypeSentList = new ArrayList<OadrRegisterReportType>();
	
	
	private static ArrayList<OadrCreateReportType> oadrCreateReportTypeReceivedList = new ArrayList<OadrCreateReportType>();
	private static ArrayList<OadrUpdateReportType> oadrUpdateReportTypeReceivedList = new ArrayList<OadrUpdateReportType>();
	private static ArrayList<OadrCancelReportType> oadrCancelReportTypeReceivedList = new ArrayList<OadrCancelReportType>();
	private static ArrayList<OadrCancelReportType> oadrCancelReportTypeSentList = new ArrayList<OadrCancelReportType>();
	
	private static ArrayList<OadrCancelPartyRegistrationType> oadrCancelPartyRegistrationReceivedTypeList = new ArrayList<OadrCancelPartyRegistrationType>();
	private static ArrayList<OadrCancelPartyRegistrationType> oadrCancelPartyRegistrationSentTypeList = new ArrayList<OadrCancelPartyRegistrationType>();
	
	private static ArrayList<OadrResponseType> registerParty_OadrResponseList = new ArrayList<OadrResponseType>();
	
	private static ArrayList<OadrCanceledPartyRegistrationType> oadrCanceledPartyRegistrationReceivedList = new ArrayList<OadrCanceledPartyRegistrationType>();
	
	private static ArrayList<OadrRequestReregistrationType> oadrRequestReregistrationTypeList = new ArrayList<OadrRequestReregistrationType>();
	
	private static ArrayList<IResponseCreatedPartyRegistrationAckAction> oadrIResponseCreatedPartyRegistrationAckActionList = new ArrayList<IResponseCreatedPartyRegistrationAckAction>();
	private static ArrayList<IResponseCreatedPartyRegistrationAckAction> oadrIResponseCreatedPartyRegistrationToQueryAckActionList = new ArrayList<IResponseCreatedPartyRegistrationAckAction>();

	private static ArrayList<IResponseRegisteredReportTypeAckAction> oadrIResponseRegisteredReportTypeAckActionList = new ArrayList<IResponseRegisteredReportTypeAckAction>();

	private static ArrayList<IResponseCreatedReportTypeAckAction> oadrIResponseCreatedReportTypeAckActionList = new ArrayList<IResponseCreatedReportTypeAckAction>();
	private static ArrayList<IResponseUpdatedReportTypeAckAction> oadrIResponseUpdatedReportTypeAckActionList = new ArrayList<IResponseUpdatedReportTypeAckAction>();
	
	
	private static ArrayList<IResponseCanceledReportTypeAckAction> oadrIResponseCanceledReportTypeAckActionList = new ArrayList<IResponseCanceledReportTypeAckAction>();

	private static ArrayList<IResponseCanceledPartyRegistrationAckAction> oadrIResponseCanceledPartyRegistrationAckActionList = new ArrayList<IResponseCanceledPartyRegistrationAckAction>();

	private static ArrayList<IResponseCancelPartyRegistrationAckAction> oadrIResponseCancelPartyRegistrationAckActionList = new ArrayList<IResponseCancelPartyRegistrationAckAction>();
	
	
	
	
	private static ArrayList<PingDistributeEventMap> pingDistributeEventMap = new ArrayList<PingDistributeEventMap>();

	private static ArrayList<OadrCreatedEventType> createdEventReceivedList = new ArrayList<OadrCreatedEventType>();
	
	
	private static ArrayList<OadrRegisteredReportType> oadrRegisteredReportTypeList = new ArrayList<OadrRegisteredReportType>();
	
	
	private static ArrayList<OadrRegisteredReportType> oadrRegisteredReportSentTypeList = new ArrayList<OadrRegisteredReportType>();
	
	private static ArrayList<OadrCreatedReportType> oadrCreatedReportTypeList = new ArrayList<OadrCreatedReportType>();

	private static ArrayList<OadrUpdatedReportType> oadrUpdatedReportList = new ArrayList<OadrUpdatedReportType>();

	private static ArrayList<OadrCanceledReportType> oadrCanceledReportList = new ArrayList<OadrCanceledReportType>();

	private static ArrayList <OadrCreateReportType> oadrCreateReportTypeSentList = new ArrayList<OadrCreateReportType>();
	
	private static ArrayList <OadrUpdateReportType> oadrUpdateReportTypeSentList = new ArrayList<OadrUpdateReportType>();

	private static ModeType mode = null;
	private static ServiceType serviceType = null;

	private static boolean testRunner;
	private static boolean responseExpected = true;
	private static boolean swapCaseVenID;
	private static boolean swapCaseVtnID;
	
	public static synchronized ModeType getMode() {
		return mode;
	}

	public static synchronized void setMode(ModeType mode) {
		TestSession.mode = mode;
	}

	
	public static synchronized ServiceType getServiceType() {
		return serviceType;
	}

	public static synchronized void setServiceType(ServiceType serviceType) {
		TestSession.serviceType = serviceType;
	}

	private static ArrayList<IResponseRegisterReportTypeAckAction> oadrIResponseRegisterReportTypeAckActionList = new ArrayList<IResponseRegisterReportTypeAckAction>();
	public static synchronized ArrayList<IResponseRegisterReportTypeAckAction> getResponseRegisterReportTypeAckActionList() {
		return oadrIResponseRegisterReportTypeAckActionList;
	}

	public static synchronized void addResponseCanceledReportTypeAckActionToList(
			IResponseRegisterReportTypeAckAction responseRegisterReportTypeAckAction) {
		oadrIResponseRegisterReportTypeAckActionList.add(responseRegisterReportTypeAckAction);
	}
	
	

	private static ArrayList<IResponseCreateReportTypeAckAction> oadrIResponseCreateReportTypeAckActionList = new ArrayList<IResponseCreateReportTypeAckAction>();
	public static synchronized ArrayList<IResponseCreateReportTypeAckAction> getResponseCreateReportTypeAckActionList() {
		return oadrIResponseCreateReportTypeAckActionList;
	}

	public static synchronized void addResponseCreateReportTypeAckActionToList(
			IResponseCreateReportTypeAckAction responseCreateReportTypeAckAction) {
		oadrIResponseCreateReportTypeAckActionList.add(responseCreateReportTypeAckAction);
	}

	
	private static ArrayList<IResponseUpdateReportTypeAckAction> oadrIResponseUpdateReportTypeAckActionList = new ArrayList<IResponseUpdateReportTypeAckAction>();
	public static synchronized ArrayList<IResponseUpdateReportTypeAckAction> getResponseUpdateReportTypeAckActionList() {
		return oadrIResponseUpdateReportTypeAckActionList;
	}

	public static synchronized void addResponseUpdateReportTypeAckActionToList(
			IResponseUpdateReportTypeAckAction responseUpdateReportTypeAckAction) {
		oadrIResponseUpdateReportTypeAckActionList.add(responseUpdateReportTypeAckAction);
	}
	
	
	private static ArrayList<IResponseCancelReportTypeAckAction> oadrIResponseCancelReportTypeAckActionList = new ArrayList<IResponseCancelReportTypeAckAction>();
	public static synchronized ArrayList<IResponseCancelReportTypeAckAction> getResponseCancelReportTypeAckActionList() {
		return oadrIResponseCancelReportTypeAckActionList;
	}

	public static synchronized void addResponseCancelReportTypeAckActionToList(
			IResponseCancelReportTypeAckAction responseUpdateReportTypeAckAction) {
		oadrIResponseCancelReportTypeAckActionList.add(responseUpdateReportTypeAckAction);
	}
	
		
	
	public static synchronized ArrayList<OadrRegisteredReportType> getOadrRegisteredReportTypeReceivedList() {
		return oadrRegisteredReportTypeList;
	}

	public static synchronized void addOadrRegisteredReportTypeReceivedToList(
			OadrRegisteredReportType oadrRegisteredReportType) {
		oadrRegisteredReportTypeList.add(oadrRegisteredReportType);
	}

	public static synchronized ArrayList<OadrRegisteredReportType> getOadrRegisteredReportTypeSentList() {
		return oadrRegisteredReportSentTypeList;
	}

	public static synchronized void addOadrRegisteredReportTypeSentToList(
			OadrRegisteredReportType oadrRegisteredReportType) {
		oadrRegisteredReportSentTypeList.add(oadrRegisteredReportType);
	}
	
	
	
		public static synchronized ArrayList<OadrCreatedReportType> getOadrCreatedReportTypeReceivedList() {
		return oadrCreatedReportTypeList;
	}

	public static synchronized void addOadrCreatedReportTypeReceivedToList(
			OadrCreatedReportType oadrCreatedReportType) {
		oadrCreatedReportTypeList.add(oadrCreatedReportType);
	}
	
	
	public static synchronized ArrayList<OadrUpdatedReportType> getOadrUpdatedReportTypeReceivedList() {
		return oadrUpdatedReportList;
	}

	public static synchronized void addOadrUpdatedReportTypeReceivedToList(
			OadrUpdatedReportType oadrUpdatedReportType) {
		oadrUpdatedReportList.add(oadrUpdatedReportType);
	}
	
	public static synchronized ArrayList<OadrCanceledReportType> getOadrCanceledReportTypeReceivedList() {
		return oadrCanceledReportList;
	}

	public static synchronized void addOadrCanceledReportTypeReceivedToList(
			OadrCanceledReportType oadrCanceledReportType) {
		oadrCanceledReportList.add(oadrCanceledReportType);
	}
	
	public static synchronized ArrayList<OadrCreateReportType> getOadrCreateReportTypeSentList() {
		return oadrCreateReportTypeSentList;
	}
	
	
	public static synchronized ArrayList<OadrUpdateReportType> getOadrUpdateReportTypeSentList() {
		return oadrUpdateReportTypeSentList;
	}
	
	
	public static synchronized void addOadrCreateReportTypeSent(
			OadrCreateReportType oadrCreateReportType) {
		oadrCreateReportTypeSentList.add(oadrCreateReportType);
	}
	
	
	public static synchronized void addOadrUpdateReportTypeSentList(
			OadrUpdateReportType oadrUpdateReportType) {
		oadrUpdateReportTypeSentList.add(oadrUpdateReportType);
	}
	
	private static String vtnClientVerifyHostName=null;

	private static String venClientVerifyHostName=null;
	
	public static String getVtnClientVerifyHostName() {
		return vtnClientVerifyHostName;
	}

	public static void setVtnClientVerifyHostName(String vtnClientVerifyHostName) {
		TestSession.vtnClientVerifyHostName = vtnClientVerifyHostName;
	}

	public static String getVenClientVerifyHostName() {
		return venClientVerifyHostName;
	}

	public static void setVenClientVerifyHostName(String venClientVerifyHostName) {
		TestSession.venClientVerifyHostName = venClientVerifyHostName;
	}
	
	private static String []clientTLS10Ciphers=null;
	
	public static String[] getClientTLS10Ciphers() {
		return clientTLS10Ciphers;
	}

	public static void setClientTLS10Ciphers(String[] clientTLS10_Ciphers) {
		TestSession.clientTLS10Ciphers = clientTLS10_Ciphers;
	}

	public static String[] getServerTLS10Ciphers() {
		return serverTLS10Ciphers;
	}

	public static void setServerTLS10Ciphers(String[] serverTLS10Ciphers) {
		TestSession.serverTLS10Ciphers = serverTLS10Ciphers;
	}

	public static String[] getClientTLS12Ciphers() {
		return clientTLS12Ciphers;
	}

	public static void setClientTLS12Ciphers(String[] clientTLS12Ciphers) {
		TestSession.clientTLS12Ciphers = clientTLS12Ciphers;
	}

	public static String[] getServerTLS12Ciphers() {
		return serverTLS12Ciphers;
	}

	public static void setServerTLS12Ciphers(String[] serverTLS12Ciphers) {
		TestSession.serverTLS12Ciphers = serverTLS12Ciphers;
	}

	private static String []clientTLSVersion=null;
	
	public static String[] getClientTLSVersion() {
		return clientTLSVersion;
	}

	public static void setClientTLSVersion(String[] clientTLSVersion) {
		TestSession.clientTLSVersion = clientTLSVersion;
	}

	public static String[] getServerTLSVersion() {
		return serverTLSVersion;
	}

	public static void setServerTLSVersion(String[] serverTLSVersion) {
		TestSession.serverTLSVersion = serverTLSVersion;
	}

	private static String []serverTLSVersion=null;
	
	
	private static String []serverTLS10Ciphers=null;
	private static String []clientTLS12Ciphers=null;
	private static String []serverTLS12Ciphers=null;

			
	public static String getTestHarnessVENServerTLS() {
		return testHarnessVENServerTLS;
	}

	public static void setTestHarnessVENServerTLS(String testHarnessVENServerTLS) {
		TestSession.testHarnessVENServerTLS = testHarnessVENServerTLS;
	}

	public static String getTestHarnessVENClientTLS() {
		return testHarnessVENClientTLS;
	}

	public static void setTestHarnessVENClientTLS(String testHarnessVENClientTLS) {
		TestSession.testHarnessVENClientTLS = testHarnessVENClientTLS;
	}

	public static String getTestHarnessVTNServerTLS() {
		return testHarnessVTNServerTLS;
	}

	public static void setTestHarnessVTNServerTLS(String testHarnessVTNServerTLS) {
		TestSession.testHarnessVTNServerTLS = testHarnessVTNServerTLS;
	}

	public static String getTestHarnessVTNClientTLS() {
		return testHarnessVTNClientTLS;
	}

	public static void setTestHarnessVTNClientTLS(String testHarnessVTNClientTLS) {
		TestSession.testHarnessVTNClientTLS = testHarnessVTNClientTLS;
	}

	private static String testHarnessVENServerTLS=null;
	private static String testHarnessVENClientTLS=null;
	private static String testHarnessVTNServerTLS=null;
	private static String testHarnessVTNClientTLS=null;
	
	
	private static String securityEnabled=null;
	private static String securityDebug=null;
	
	//private static String testHarnessVENCryptography=null;
	/*
	public static String getTestHarnessVENCryptography() {
		return testHarnessVENCryptography;
	}

	public static void setTestHarnessVENCryptography(
			String testHarnessVENCryptography) {
		TestSession.testHarnessVENCryptography = testHarnessVENCryptography;
	}
	 */

	public static String getSecurityDebug() {
		return securityDebug;
	}

	public static void setSecurityDebug(String securityDebug) {
		TestSession.securityDebug = securityDebug;
	}

	public static String getSecurityEnabled() {
		return securityEnabled;
	}

	public static void setSecurityEnabled(String securityEnabled) {
		TestSession.securityEnabled = securityEnabled;
	}


	public static ArrayList<PingDistributeEventMap> getPingDistributeEventMap() {
		return pingDistributeEventMap;
	}

	public static void addPingDistributeEventMap(
			PingDistributeEventMap pingDistributeEvent) {
		pingDistributeEventMap.add(pingDistributeEvent);
	}

	static ArrayList<OadrResponseType> oadrResponseList = new ArrayList<OadrResponseType>();
	static Trace traceObj;
	static boolean isValidationErrorFound = false;
	static boolean userClickedToCompleteUIAction = false;

	static boolean userClickedToCompleteUIDualAction = false;

	static boolean userClickedToCancelUIDualAction = false;

	public static boolean isUserClickedToCancelUIDualAction() {
		return userClickedToCancelUIDualAction;
	}

	public static void setUserClickedToCancelUIDualAction(
			boolean userClickedToCancelUIDualAction) {
		TestSession.userClickedToCancelUIDualAction = userClickedToCancelUIDualAction;
	}

	static boolean userCancelledCompleteUIDualAction = false;
	static boolean atleastOneValidationErrorPresent = false;
	static boolean createdEventNotReceivedTillTimeout = false;
	static boolean testCaseDone = false;
	static boolean userClickedContinuePlay = false;

	public static boolean isTestCaseDone() {
		return testCaseDone;
	}

	public static void setTestCaseDone(boolean testCaseDone) {
		TestSession.testCaseDone = testCaseDone;
	}

	public static boolean isCreatedEventNotReceivedTillTimeout() {
		return createdEventNotReceivedTillTimeout;
	}

	public static void setCreatedEventNotReceivedTillTimeout(
			boolean createdEventNotReceived) {
		TestSession.createdEventNotReceivedTillTimeout = createdEventNotReceived;
	}

	public static boolean isAtleastOneValidationErrorPresent() {
		return atleastOneValidationErrorPresent;
	}

	public static boolean isUserCancelledCompleteUIDualAction() {
		return userCancelledCompleteUIDualAction;
	}

	public static void setUserCancelledCompleteUIDualAction(
			boolean userCancelledCompleteUIDualAction) {
		TestSession.userCancelledCompleteUIDualAction = userCancelledCompleteUIDualAction;
	}

	public static boolean isUserClickedToCompleteUIDualAction() {
		return userClickedToCompleteUIDualAction;
	}

	public static void setUserClickedToCompleteUIDualAction(
			boolean userClickedToCompleteUIDualAction) {
		TestSession.userClickedToCompleteUIDualAction = userClickedToCompleteUIDualAction;

		handlePromptLocking(userClickedToCompleteUIDualAction);
	}

	public static boolean isUiDualActionYesOptionClicked() {
		return uiDualActionYesOptionClicked;
	}

	public static void setUiDualActionYesOptionClicked(
			boolean uiDualActionYesOptionClicked) {
		TestSession.uiDualActionYesOptionClicked = uiDualActionYesOptionClicked;
	}

	static boolean uiDualActionYesOptionClicked = false;

	public static boolean isUserClickedToCompleteUIAction() {
		return userClickedToCompleteUIAction;
	}

	public static void setUserClickedToCompleteUIAction(
			boolean userClickedToCompleteUIAction) {
		TestSession.userClickedToCompleteUIAction = userClickedToCompleteUIAction;
		
		handlePromptLocking(userClickedToCompleteUIAction);
	}

	private static FileLock promptLock; 

	private static RandomAccessFile promptLockFile; 
	
	private static void handlePromptLocking(boolean userClickedToCompleteUIAction) {
		if (promptLockFile == null) {
			try {
				promptLockFile = new RandomAccessFile("./src/runner/prompt.lock", "rw");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		try {
			if (userClickedToCompleteUIAction) {
				if (promptLock != null) {
					promptLock.release();
					promptLock = null;
				}
			} else if (promptLock == null) {
				promptLock = promptLockFile.getChannel().tryLock();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static boolean isUserClickedContinuePlay() {

		return userClickedContinuePlay;
	}

	public static void setUserClickedContinuePlay(
			boolean userClickedContinuePlay) {

		TestSession.userClickedContinuePlay = userClickedContinuePlay;
	}

	public synchronized static void addOadrResponseList(
			OadrResponseType OadrResponseType) {
		oadrResponseList.add(OadrResponseType);
	}

	public synchronized static ArrayList<OadrResponseType> getOadrResponse() {
		return oadrResponseList;
	}

	public static boolean isValidationErrorFound() {
		return isValidationErrorFound;
	}

	public static void setValidationErrorFound(boolean isValidationErrorFnd) {
		if (!atleastOneValidationErrorPresent && isValidationErrorFnd) {
			atleastOneValidationErrorPresent = true;
		}
		isValidationErrorFound = isValidationErrorFnd;
		
		if (isValidationErrorFnd && Debug.setValidationErrorFoundStackTrace) {
			System.out.println("Validation error found");
			new Throwable().printStackTrace();
		}
	}

	public static synchronized Trace getTraceObj() {
		return traceObj;
	}

	public static void setTraceObj(Trace traceObject) {
		traceObj = traceObject;
	}

	public static synchronized ArrayList<IDistributeEventAction> getDistributeEventActionList() {
		return distributeEventActionList;
	}
///////////////
	public static synchronized ArrayList<ICreatedEventAction> getCreatedEventActionList() {
		return createEventActionList;
	}
	
	public static synchronized ArrayList<IRequestReregistrationAction> getRequestReregistrationActionList() {
		return requestReregistrationActionList;
	}
	
	public static synchronized ArrayList<IResponseCreatedOptAckAction> getResponseCreatedOptAckActionList() {
		return responseCreatedOptAckActionList;
	}
	
	
	public static synchronized ArrayList<IResponseCanceledOptAckAction> getResponseCanceledOptAckActionList() {
		return responseCanceledOptAckActionList;
	}
	
	public static synchronized ArrayList<IResponseEventAction> getResponseEventActionList() {
		return responseEventActionList;
	}

	
	public static synchronized ArrayList<OadrCreateOptType> getCreateOptEventReceivedList() {
		return createOptEventReceivedList;
	}

	
	public static synchronized ArrayList<OadrCreateOptType> getCreateOptEventSentList() {
		return createOptEventSentList;
	}

	
	public static synchronized void addCreateOptEventReceivedToList(
			OadrCreateOptType oadrCreateEventType) {
		createOptEventReceivedList.add(oadrCreateEventType);
	}
	
	public static synchronized void addCreateOptEventSentList(
			OadrCreateOptType oadrCreateEventType) {
		createOptEventSentList.add(oadrCreateEventType);
	}
	
	
	public static synchronized ArrayList<OadrCreatedOptType> getCreatedOptReceivedList() {
		return createdOptReceivedList;
	}

	public static synchronized void addCreatedOptReceivedToList(OadrCreatedOptType oadrCreatedType) {
		createdOptReceivedList.add(oadrCreatedType);
	}
	
	public static synchronized ArrayList<OadrCancelOptType> getCancelOptTypeReceivedList() {
		return createOadrCancelOptTypeList;
	}

	public static synchronized void addCancelOptTypeReceivedToList(
			OadrCancelOptType oadrCancelOptType) {
		createOadrCancelOptTypeList.add(oadrCancelOptType);
	}

	public static synchronized ArrayList<OadrQueryRegistrationType> getOadrQueryRegistrationTypeReceivedList() {
		return oadrQueryRegistrationTypeReceivedList;
	}

	public static synchronized void addOadrQueryRegistrationTypeReceivedToList(
			OadrQueryRegistrationType oadrQueryRegistrationType) {
		oadrQueryRegistrationTypeReceivedList.add(oadrQueryRegistrationType);
	}
	
	
	public static synchronized ArrayList<OadrCreatePartyRegistrationType> getCreatePartyRegistrationTypeReceivedList() {
		return oadrCreatePartyRegistrationTypeList;
	}
	
	public static synchronized void addOadrCreatePartyRegistrationReceivedToList(
			OadrCreatePartyRegistrationType oadrCreatePartyRegistrationType) {
			oadrCreatePartyRegistrationTypeList.add(oadrCreatePartyRegistrationType);
	}
		
	
	public static synchronized ArrayList<OadrRegisterReportType> getOadrRegisterReportTypeSentList() {
		return oadrRegisterReportTypeSentList;
	}

	public static synchronized void addOadrRegisterReportTypeSentList(
			OadrRegisterReportType oadrRegisterReportType) {
		oadrRegisterReportTypeSentList.add(oadrRegisterReportType);
	}
	
	public static synchronized ArrayList<OadrRegisterReportType> getOadrRegisterReportTypeReceivedList() {
		return oadrRegisterReportTypeReceivedList;
	}

	public static synchronized void addCreateReportTypeReceivedToList(
			OadrCreateReportType oadrCreateReportType) {
		oadrCreateReportTypeReceivedList.add(oadrCreateReportType);
	}
	
	public static synchronized ArrayList<OadrCreateReportType> getOadrCreateReportTypeReceivedList() {
		return oadrCreateReportTypeReceivedList;
	}

	public static synchronized void addUpdateReportTypeReceivedList(
			OadrUpdateReportType oadrUpdateReportType) {
		oadrUpdateReportTypeReceivedList.add(oadrUpdateReportType);
	}
	
	public static synchronized ArrayList<OadrUpdateReportType> getOadrUpdateReportTypeReceivedList() {
		return oadrUpdateReportTypeReceivedList;
	}

	
	public static synchronized void addCancelReportTypeReceivedToList(
			OadrCancelReportType oadrCancelReportType) {
		oadrCancelReportTypeReceivedList.add(oadrCancelReportType);
	}
	
	
	public static synchronized void addCancelReportTypeSentToList(
			OadrCancelReportType oadrCancelReportType) {
		oadrCancelReportTypeSentList.add(oadrCancelReportType);
	}
	
	public static synchronized ArrayList<OadrCancelReportType> getOadrCancelReportTypeReceivedList() {
		return oadrCancelReportTypeReceivedList;
	}
	
	public static synchronized ArrayList<OadrCancelReportType> getOadrCancelReportTypeSentList() {
		return oadrCancelReportTypeSentList;
	}
	
	public static synchronized void addOadrRegisterReportTypeReceivedToList(
			OadrRegisterReportType oadrRegisterReport) {
		oadrRegisterReportTypeReceivedList.add(oadrRegisterReport);
	}
	
	
	public static synchronized ArrayList<OadrCancelPartyRegistrationType> getCancelPartyRegistrationTypeListReceived() {
		return oadrCancelPartyRegistrationReceivedTypeList;
	}

	public static synchronized void addCancelPartyRegistrationReceivedToList(
			OadrCancelPartyRegistrationType oadrCancelPartyRegistrationType) {
		oadrCancelPartyRegistrationReceivedTypeList.add(oadrCancelPartyRegistrationType);
	}
	
	public static synchronized ArrayList<OadrCancelPartyRegistrationType> getCancelPartyRegistrationTypeSentList() {
		return oadrCancelPartyRegistrationSentTypeList;
	}

	public static synchronized void addCancelPartyRegistrationSentToList(
			OadrCancelPartyRegistrationType oadrCancelPartyRegistrationType) {
		oadrCancelPartyRegistrationSentTypeList.add(oadrCancelPartyRegistrationType);
	}
	
	public static synchronized ArrayList<OadrResponseType> getRegisterParty_OadrResponseListReceivedList() {
		return registerParty_OadrResponseList;
	}

	public static synchronized void addRegisterParty_OadrResponseListReceivedList(
			OadrResponseType oadrResponseType) {
		registerParty_OadrResponseList.add(oadrResponseType);
	}
	
	public static synchronized ArrayList<OadrCanceledPartyRegistrationType> getCanceledPartyRegistrationReceivedToList() {
		return oadrCanceledPartyRegistrationReceivedList;
	}

	public static synchronized void addCanceledPartyRegistrationReceivedToList(
			OadrCanceledPartyRegistrationType oadrCanceledPartyRegistrationType) {
		oadrCanceledPartyRegistrationReceivedList.add(oadrCanceledPartyRegistrationType);
	}	
	
	
	
	
	
	///////////////////////////////////////////////
	public static synchronized ArrayList<OadrRequestReregistrationType> getOadrRequestReregistrationReceivedList() {
		return oadrRequestReregistrationTypeList;
	}

	public static synchronized void addOadrRequestReregistrationReceivedToList(
			OadrRequestReregistrationType oadrRequestReregistrationType) {
		oadrRequestReregistrationTypeList.add(oadrRequestReregistrationType);
	}
		
	///////////////////////////////////////////////
	public static synchronized ArrayList<IResponseCreatedPartyRegistrationAckAction> getCreatedPartyRegistrationAckActionList() {
		return oadrIResponseCreatedPartyRegistrationAckActionList;
	}

	public static synchronized void addResponseCreatedPartyRegistrationAckActionToList(
			IResponseCreatedPartyRegistrationAckAction responseCreatedPartyRegistrationAckAction) {
			oadrIResponseCreatedPartyRegistrationAckActionList.add(responseCreatedPartyRegistrationAckAction);
	}

	public static synchronized ArrayList<IResponseCreatedPartyRegistrationAckAction> getCreatedPartyRegistrationToQueryAckActionList() {
		return oadrIResponseCreatedPartyRegistrationToQueryAckActionList;
	}

	public static synchronized void addResponseCreatedPartyRegistrationToQueryAckActionToList(
			IResponseCreatedPartyRegistrationAckAction responseCreatedPartyRegistrationAckAction) {
			oadrIResponseCreatedPartyRegistrationToQueryAckActionList.add(responseCreatedPartyRegistrationAckAction);
	}
	
	public static synchronized ArrayList<IResponseRegisteredReportTypeAckAction> getResponseRegisteredReportTypeAckActionList() {
		return oadrIResponseRegisteredReportTypeAckActionList;
	}

	public static synchronized void addResponseRegisteredReportTypeAckActionToList(
			IResponseRegisteredReportTypeAckAction responseRegisteredReportTypeAckAction) {
		oadrIResponseRegisteredReportTypeAckActionList.add(responseRegisteredReportTypeAckAction);
	}
		
	public static synchronized ArrayList<IResponseCreatedReportTypeAckAction> getResponseCreatedReportTypeAckActionList() {
		return oadrIResponseCreatedReportTypeAckActionList;
	}

	public static synchronized void addResponseCreatedReportTypeAckActionToList(
			IResponseCreatedReportTypeAckAction responseCreatedReportTypeAckAction) {
		oadrIResponseCreatedReportTypeAckActionList.add(responseCreatedReportTypeAckAction);
	}

	public static synchronized ArrayList<IResponseUpdatedReportTypeAckAction> getResponseUpdatedReportTypeAckActionList() {
		return oadrIResponseUpdatedReportTypeAckActionList;
	}

	public static synchronized void addResponseUpdatedReportTypeAckActionToList(
			IResponseUpdatedReportTypeAckAction responseUpdatedReportTypeAckAction) {
		oadrIResponseUpdatedReportTypeAckActionList.add(responseUpdatedReportTypeAckAction);
	}

	public static synchronized ArrayList<IResponseCanceledReportTypeAckAction> getResponseCanceledReportTypeAckActionList() {
		return oadrIResponseCanceledReportTypeAckActionList;
	}

	public static synchronized void addResponseCanceledReportTypeAckActionToList(
			IResponseCanceledReportTypeAckAction responseCanceledReportTypeAckAction) {
		oadrIResponseCanceledReportTypeAckActionList.add(responseCanceledReportTypeAckAction);
	}
////////////////////////////////////
	
	public static synchronized ArrayList<IResponseCanceledPartyRegistrationAckAction> getResponseCanceledPartyRegistrationAckActionList() {
		return oadrIResponseCanceledPartyRegistrationAckActionList;
	}

	public static synchronized void addResponseCanceledPartyRegistrationAckActionToList(
			IResponseCanceledPartyRegistrationAckAction oadrIResponseCanceledPartyRegistrationAckAction) {
		oadrIResponseCanceledPartyRegistrationAckActionList.add(oadrIResponseCanceledPartyRegistrationAckAction);
	}	
	
	/////////////////////////

////////////////////////////////////
	
public static synchronized ArrayList<IResponseCancelPartyRegistrationAckAction> getResponseCancelPartyRegistrationAckActionList() {
return oadrIResponseCancelPartyRegistrationAckActionList;
}

public static synchronized void addResponseCancelPartyRegistrationAckActionToList(
IResponseCancelPartyRegistrationAckAction oadrCancelPartyRegistrationAckAction) {
oadrIResponseCancelPartyRegistrationAckActionList.add(oadrCancelPartyRegistrationAckAction);
}	

/////////////////////////
	public static synchronized void addCreatedEventReceivedToList(
			OadrCreatedEventType oadrCreatedEventType) {
		createdEventReceivedList.add(oadrCreatedEventType);
	}
	
	public static synchronized ArrayList<OadrCreatedEventType> getCreatedEventReceivedList(){
		return createdEventReceivedList;
	}

	public static boolean isTestRunner() {
		return testRunner;
	}

	public static void setTestRunner(boolean testRunner) {
		TestSession.testRunner = testRunner;
	}

	public static boolean isResponseExpected() {
		return responseExpected;
	}

	public static void setResponseExpected(boolean responseExpected) {
		TestSession.responseExpected = responseExpected;
	}

	public static boolean isSwapCaseVenID() {
		return swapCaseVenID;
	}

	public static void setSwapCaseVenID(boolean swapCaseVenID) {
		TestSession.swapCaseVenID = swapCaseVenID;
	}

	public static boolean isSwapCaseVtnID() {
		return swapCaseVtnID;
	}

	public static void setSwapCaseVtnID(boolean swapCaseVtnID) {
		TestSession.swapCaseVtnID = swapCaseVtnID;
	}
}