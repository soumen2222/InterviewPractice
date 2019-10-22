package com.qualitylogic.openadr.core.util;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.engine.header.Header;
import org.restlet.util.Series;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.qualitylogic.openadr.core.bean.CreatedEventBean;
import com.qualitylogic.openadr.core.bean.DeviceClass;
import com.qualitylogic.openadr.core.bean.ModeType;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.EiEventSignalType;
import com.qualitylogic.openadr.core.signal.EiResponseType;
import com.qualitylogic.openadr.core.signal.EiTargetType;
import com.qualitylogic.openadr.core.signal.EndDeviceAssetType;
import com.qualitylogic.openadr.core.signal.IntervalType;
import com.qualitylogic.openadr.core.signal.PayloadFloatType;
import com.qualitylogic.openadr.core.signal.SignalPayloadType;
import com.qualitylogic.openadr.core.signal.StreamPayloadBaseType;
import com.qualitylogic.openadr.core.signal.EventResponses.EventResponse;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.QualifiedEventIDType;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;

@SuppressWarnings("deprecation")
public class OadrUtil {
	static DatatypeFactory datatypeFactory;

	static {
		try {
			datatypeFactory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public static String getClassName(Class cls) {
		if (cls == null)
			return "";
		String className = "";
		String testCaseFullName = cls.getName();
		String testCaseFullNameSplit[] = testCaseFullName.split("\\.");
		className = testCaseFullNameSplit[testCaseFullNameSplit.length - 1];
		return className;
	}



	public static void setServiceType(String className){
		if(className!=null && className.toString().contains("_VTN")){
			TestSession.setServiceType(ServiceType.VTN);
		}else if(className!=null && className.toString().contains("_VEN")){
			TestSession.setServiceType(ServiceType.VEN);			
		}else{
			System.out.println("The TestCase naming convention needs to be _VEN or _VTN");
			System.exit(-1);
		}
	}
	
	public static boolean isCreatePartyRegistration_PullModel(){
		
		if(TestSession.getMode()!=null){
			if(TestSession.getMode().equals(ModeType.PULL)){
				return true;
			}else{
				return false;
			}
		}
		System.out.println("Please set PULL or PUSH mode type in the testcase");
		System.exit(-1);
		
		return false;
	}
	
	public static boolean isValidationErrorPresent(){
		
		boolean atleastOneValidationErrorPresent = TestSession
				.isAtleastOneValidationErrorPresent();

		if (atleastOneValidationErrorPresent) {
			LogHelper.setResult(LogResult.FAIL);
			//LogHelper.addTrace("Validation Error(s) are present");
			//LogHelper.addTrace("Test Case has Failed");
			return true;
		}else{
			return false;
		}
		
	}
	
	public static boolean isExpected(String data,Class expected){
		if(data==null || data.trim().equals("")){
			return false;
		}
		
		Class test;
		try {
			test = SchemaHelper.getObjectType(data);
		} catch (UnsupportedEncodingException | JAXBException e) {
			
			return false;
		}
		if(test.equals(expected)){
			return true;
			
		}
		return false;
	}
	public static long getSize(Document doc) {
		try {
			Source source = new DOMSource(doc);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Result result = new StreamResult(out);
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.transform(source, result);
			return out.toByteArray().length;
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static Duration createDuration(int totalYears, int totalMonths,
			int totalDays, int totalHours, int totalMinutes, int totalSeconds) {
		boolean isPositive = true;

		Duration duration = createDuration(isPositive, totalYears, totalMonths,
				totalDays, totalHours, totalMinutes, totalSeconds);

		return duration;
	}

	public static Duration createDuration(boolean isPositive, int totalYears,
			int totalMonths, int totalDays, int totalHours, int totalMinutes,
			int totalSeconds) {

		Duration duration = datatypeFactory.newDuration(isPositive, totalYears,
				totalMonths, totalDays, totalHours, totalMinutes, totalSeconds);

		return duration;
	}

	/*
	 * public static org.joda.time.Duration testcreateDuration(boolean
	 * isPositive,int totalYears,int totalMonths,int totalDays,int
	 * totalHours,int totalMinutes,int totalSeconds){
	 * 
	 * org.joda.time.Duration duration = new org.joda.time.Duration(500);
	 * org.joda.time.Duration duration2 = new org.joda.time.Duration(500);
	 * 
	 * ReadableDuration redable=new ReadableDuration();
	 * 
	 * duration.withDurationAdded(duration2);
	 * 
	 * return duration; }
	 */

	public static Duration createDuration(String durationAsString) {
		Duration durationObj = datatypeFactory.newDuration(durationAsString);

		return durationObj;
	}

	public static Duration createDuration(int totalMinutes, int totalSeconds) {
		boolean isPositive = true;
		if (totalMinutes < 0) { // for test case E1_0100
			isPositive = false;
			totalMinutes = totalMinutes * -1; // make absolute
		}

		int totalYears = 0;
		int totalMonths = 0;
		int totalDays = 0;
		int totalHours = 0;

		Duration duration = datatypeFactory.newDuration(isPositive, totalYears,
				totalMonths, totalDays, totalHours, totalMinutes, totalSeconds);

		return duration;
	}

	public static Duration createDuration(int totalHours, int totalMinutes,
			int totalSeconds) {
		boolean isPositive = true;
		int totalYears = 0;
		int totalMonths = 0;
		int totalDays = 0;

		Duration duration = datatypeFactory.newDuration(isPositive, totalYears,
				totalMonths, totalDays, totalHours, totalMinutes, totalSeconds);

		return duration;
	}

	// This function is very limited and really only works with durations in the 
	// format PTnnA if it's divisible by 2 with no remainder.
	public static String divideBy2(String durationText) {
		if (durationText.equals("PT1H")) {
			durationText = "PT60M";
		}
		
		String numberText = durationText.substring(2, 4);
		int number = Integer.parseInt(numberText);
		number /= 2;
		return durationText.replace(numberText, String.valueOf(number));
	}
	
	public static XMLGregorianCalendar getCurrentTime() {
		SimpleDateFormat SDF = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		TimeZone utc = TimeZone.getTimeZone("UTC");
		SDF.setTimeZone(utc);
		String milliformat = SDF.format(new Date());
		String zulu = milliformat.substring(0, 19) + 'Z';

		XMLGregorianCalendar currentTime = datatypeFactory
				.newXMLGregorianCalendar(zulu);

		return currentTime;
	}

	public static long getTimeMilliseconds(XMLGregorianCalendar time) {
		
		GregorianCalendar calendar = time.toGregorianCalendar();
		
		return calendar.getTimeInMillis();
	}
	
public static XMLGregorianCalendar stringToXMLGregorianCalendar(String strDate)  throws ParseException, DatatypeConfigurationException{

  XMLGregorianCalendar result = null;

  Date date;

  SimpleDateFormat simpleDateFormat;

  GregorianCalendar gregorianCalendar;

  simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                date = simpleDateFormat.parse(strDate);        

                gregorianCalendar = 

                    (GregorianCalendar)GregorianCalendar.getInstance();

                gregorianCalendar.setTime(date);

                result = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);

                return result;

  }

public static javax.xml.datatype.Duration getTotalIntervalDuration(
			XMLGregorianCalendar startTime, XMLGregorianCalendar endTime)
			throws DatatypeConfigurationException {
		DateTime startIntervalDateTime = new DateTime(startTime.getYear(),
				startTime.getMonth(), startTime.getDay(), startTime.getHour(),
				startTime.getMinute(), startTime.getSecond());
		DateTime endIntervalDateTime = new DateTime(endTime.getYear(),
				endTime.getMonth(), endTime.getDay(), endTime.getHour(),
				endTime.getMinute(), endTime.getSecond());

		Period period = new Period(startIntervalDateTime, endIntervalDateTime);

		javax.xml.datatype.Duration duration;
		DatatypeFactory dtf = DatatypeFactory.newInstance();
		boolean isPositive = true;
		duration = dtf.newDuration(isPositive, period.getYears(),
				period.getMonths(), period.getDays(), period.getHours(),
				period.getMinutes(), period.getSeconds());

		return duration;
	}

	public static String createUniqueID() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy_hhmmss_SSS");
		return dateFormat.format(new Date());
	}

	public static String createUniqueTraceFileName() {
		return "TraceLog_" + createUniqueID() + ".txt";
	}

	public static void exceptionHandler(Exception e, String message) {

		LogHelper.addTrace("Exception : " + message);
		LogHelper.addTrace(stackTraceToString(e));

		System.out.println(message);
		e.printStackTrace();
		return;

	}

	public static String stackTraceToString(Throwable e) {
		StringBuilder sb = new StringBuilder();
		for (StackTraceElement element : e.getStackTrace()) {
			sb.append(element.toString());
			sb.append("\n");
		}
		return sb.toString();
	}

	public static String formatXMLAsString(String unformattedXml) {
		if (unformattedXml == null || unformattedXml.length() < 1)
			return "";
		try {
			final Document document = parseXmlFile(unformattedXml);

			OutputFormat format = new OutputFormat(document);
			format.setLineWidth(65);
			format.setIndenting(true);
			format.setIndent(2);
			Writer out = new StringWriter();
			XMLSerializer serializer = new XMLSerializer(out, format);
			serializer.serialize(document);

			return out.toString();
		} catch (Exception e) {
			Trace trace = TestSession.getTraceObj();
			trace.getLogFileContentTrace().append(
					"\n----Unable to parse as XML-----\n");
			trace.getLogFileContentTrace().append("\n" + unformattedXml + "\n");
		}
		return "";
	}

	private static Document parseXmlFile(String in) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(in));
			return db.parse(is);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void logResponseStatus(Request request) {

		Trace trace = TestSession.getTraceObj();

		trace.getLogFileContentTrace().append("----HTTP Request---\n");

		Map<String, Object> map = request.getAttributes();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			trace.getLogFileContentTrace().append(
					entry.getKey() + " : " + entry.getValue() + " \n");
		}
		trace.getLogFileContentTrace().append("\n");
		trace.getLogFileContentTrace().append("\n");
	}

	public static void logResponseStatus(Response response) {

		Trace trace = TestSession.getTraceObj();
		trace.getLogFileContentTrace().append(
				"\nHTTP Status :" + response.getStatus() + " \n");
		trace.getLogFileContentTrace().append("\n");
	}

	public static synchronized ArrayList<CreatedEventBean> transformToCreatedEventBeanList(
			ArrayList<OadrCreatedEventType> oadrCreatedEventList) {
		ArrayList<CreatedEventBean> createdEventList = new ArrayList<CreatedEventBean>();
		for (OadrCreatedEventType OadrCreatedEventType : oadrCreatedEventList) {
			ArrayList<CreatedEventBean> eachCreatedEventList = addToCreatedEventBeanList(
					OadrCreatedEventType, createdEventList);
			if (eachCreatedEventList != null && eachCreatedEventList.size() > 0) {
				createdEventList.addAll(eachCreatedEventList);
			}
		}

		ArrayList<CreatedEventBean> filteredList = new ArrayList<CreatedEventBean>();

		for (CreatedEventBean received : createdEventList) {
			boolean existingInFiltered = false;
			for (CreatedEventBean filtered : filteredList) {
				boolean eventIDMatch = received.getEventID().equals(
						filtered.getEventID());
				boolean optTypeMatch = received.getOptType().equals(
						filtered.getOptType());
				boolean venIDMatch = received.getVenID().equals(
						filtered.getVenID());
				boolean requestIDMatch = received.getRequestID().equals(
						filtered.getRequestID());
				boolean receivedModificationNuberMatch = received
						.getModificationnumber() == filtered
						.getModificationnumber();
				if (requestIDMatch && eventIDMatch && optTypeMatch
						&& venIDMatch && receivedModificationNuberMatch) {
					existingInFiltered = true;
					break;
				}
			}

			if (!existingInFiltered) {
				filteredList.add(received);
			}
		}

		return filteredList;
	}

	private synchronized static ArrayList<CreatedEventBean> addToCreatedEventBeanList(
			OadrCreatedEventType OadrCreatedEventType,
			ArrayList<CreatedEventBean> existing) {
		ArrayList<CreatedEventBean> createdEventList = new ArrayList<CreatedEventBean>();

		String venID = OadrCreatedEventType.getEiCreatedEvent().getVenID();

		if (OadrCreatedEventType == null
				|| OadrCreatedEventType.getEiCreatedEvent() == null
				|| OadrCreatedEventType.getEiCreatedEvent().getEventResponses() == null
				|| OadrCreatedEventType.getEiCreatedEvent().getEventResponses()
						.getEventResponse() == null) {
			return createdEventList;
		}

		List<EventResponse> eventResponseList = OadrCreatedEventType
				.getEiCreatedEvent().getEventResponses().getEventResponse();

		for (EventResponse eachEventResponse : eventResponseList) {
			String requestId = eachEventResponse.getRequestID();

			CreatedEventBean eachCreatedEventBean = new CreatedEventBean();

			if (eachEventResponse.getQualifiedEventID() != null) {
				eachCreatedEventBean.setModificationnumber(eachEventResponse
						.getQualifiedEventID().getModificationNumber());
			}

			QualifiedEventIDType evetId = eachEventResponse
					.getQualifiedEventID();
			String eventID = evetId.getEventID();

			OptTypeType optType = eachEventResponse.getOptType();
			eachCreatedEventBean.setVenID(venID);
			eachCreatedEventBean.setRequestID(requestId);
			eachCreatedEventBean.setEventID(eventID);
			eachCreatedEventBean.setOptType(optType);

			createdEventList.add(eachCreatedEventBean);
		}

		return createdEventList;

	}

	public static String createoadrDistributeRequestID() {
		return "OadrDisReq" + createUniqueID();
	}

	public static String createDescriptorEventID() {
		return "Event" + createUniqueID();
	}

	public static String createUniqueSignalName() {
		return "Signal" + createUniqueID();
	}
	
	public static String createUniqueOadrCreateOptID() {
		return "CreateOpt" + createUniqueID();
	}
	
	public static String createUniqueOadrCreateOptReqID() {
		return "CreateOptReq" + createUniqueID();
	}
	
	public static String createUniqueOadrCancelOptReqID() {
		return "CancelOptReq" + createUniqueID();
	}
	public static String createUniqueVENID() {
		return "VEN" + createUniqueID();
	}
	
	public static String createUniqueRegistrationID() {
		return "REG" + createUniqueID();
	}
	
	public static String createUniqueCreatePartyRegistrationReqID() {
		return "CrtPartRegReq" + createUniqueID();
	}
	
	public static String createUniqueRegisterReportReqID() {
		return "RegReq" + createUniqueID();
	}
	
	public static String createUniqueReportSpecifierID() {
		return "ReportSpecifierID" + createUniqueID();
	}
	
	public static String createUniqueRID() {
		return "rID" + createUniqueID();
	}
	public static String createUniqueReportRequestID() {
		return "ReportReqID" + createUniqueID();
	}
	
	public static String createUniqueUpdReportRequestID() {
		return "ReportUpdReqID" + createUniqueID();
	}
	
	public static String createUniqueCancelReportRequestID() {
		return "ReportCancelReqID" + createUniqueID();
	}
	
	public static String createUniqueQueryRegistrationRequestID() {
		return "QueryRegistrationReqID" + createUniqueID();
	}
	
	public static String createUniqueReportID() {
		return "ReportID" + createUniqueID();
	}
	
	public static String createUniqueCancelRequestID() {
		return "CancelRequest" + createUniqueID();
	}
	
	public static synchronized boolean isIDFoundInList(String idToFind,
			ArrayList<String> idList) {
		for (String eachIDInList : idList) {
			if (eachIDInList.equals(idToFind)) {
				return true;
			}
		}

		return false;
	}

	public static synchronized boolean isIDUniqueInList(String idToFind,
			ArrayList<String> idList) {
		int idCount=0;
		for (String eachIDInList : idList) {
			if (idToFind.equals(eachIDInList)) {
				idCount++;
			}
		}

		if(idCount>1) return false;
		
		return true;
	}
	
	public static long getTestCaseTimeout() {

		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
		long totalDurationInput = Long.valueOf(propertiesFileReader
				.get("asyncResponseTimeout"));
		long testCaseTimeout = System.currentTimeMillis() + totalDurationInput;
		
		return testCaseTimeout;
	}
	
	
	public static long getCreatedEventAsynchTimeout() {

		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
		long totalDurationInput = Long.valueOf(propertiesFileReader
				.get("createdEventAsynchTimeout"));
		long testCaseTimeout = System.currentTimeMillis() + totalDurationInput;
		
		return testCaseTimeout;
	}
		
	public static void Push_VTN_Shutdown_Prompt() {
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		LogHelper.addTrace("*** Purging residual traffic ***");
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Push_VTN_EndOFTestFlush(), 1);
		int timeout = Integer.parseInt(propertiesFileReader
				.get("testCaseBufferTimeout"));
		if (TestSession.isUserClickedContinuePlay()) {
			System.out.println("\nWaiting " + Integer.toString(timeout / 1000)
					+ " seconds for any residual traffic....");
			long pauseTimeout = System.currentTimeMillis() + (timeout);
			while (System.currentTimeMillis() < pauseTimeout) {

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
			}
		}
	}
	
	public static boolean isEmpty(String value){
		if(value==null ||value.trim().length()<1){
			return true;
		}else{
			return false;
		}
	}
	
	
	public static boolean isSuccessResponse(EiResponseType eiResponse){
		
		if (eiResponse==null) {
			return true;
		}
		
		if(eiResponse.getResponseCode()==null) return false;
		
		if (!eiResponse.getResponseCode()
				.startsWith("2")) {
			return false;
		}
		
		return true;
	}

	public static boolean isResourceIDFoundInPropertiesFile(String eachResourceIDReceived){

		if(eachResourceIDReceived==null || eachResourceIDReceived.trim().length()<1) return false;
			
		PropertiesFileReader properties = new PropertiesFileReader();
		String resourceIDInPropertiesFile[] = properties.getAllResourceID();
		if(resourceIDInPropertiesFile!=null && resourceIDInPropertiesFile.length>0){
			for(String eachResourceIDInProperties:resourceIDInPropertiesFile){
				if(eachResourceIDInProperties.trim().equals(eachResourceIDReceived.trim())){
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static boolean isAtLeastOneEiTargetMatch(EiTargetType eiTarget){
		
		if (eiTarget != null) {

			List<String> resourceId = eiTarget.getResourceID();
			List<String> venId = eiTarget.getVenID();

			boolean isResourceIdEmpty = (resourceId == null || resourceId
					.size() < 1);
			boolean isVenIdEmpty = (venId == null || venId.size() < 1);
			
			if (isResourceIdEmpty && isVenIdEmpty) {
				return true;
			}

			PropertiesFileReader properties = new PropertiesFileReader();
			
			String venIDInPropertiesFile = properties.getVenID();
			if (!isVenIdEmpty) {
				for (String eachVenId : venId) {
					if (eachVenId != null
							&& eachVenId.trim().equals(
									venIDInPropertiesFile))
						return true;
				}
			}

			if (!isResourceIdEmpty) {
				for (String eachResourceId : resourceId) {
					if (eachResourceId != null){
						
						boolean isResourceIDFoundInProperties =OadrUtil.isResourceIDFoundInPropertiesFile(eachResourceId);
						if(isResourceIDFoundInProperties){
							return true;	
						}
						
					}
				}
			}

			return false;
		}
		return true;
	}
	
	public static String getSignalName(OadrDistributeEventType distributeEventReceived) {
		String signalName = null;
		try {
			List<OadrEvent> oadrEvent = distributeEventReceived.getOadrEvent();
			if (!oadrEvent.isEmpty()) { 
				List<EiEventSignalType> eiEventSignal = oadrEvent.get(0).getEiEvent().getEiEventSignals().getEiEventSignal();
				if (!eiEventSignal.isEmpty()) {
					signalName = eiEventSignal.get(0).getSignalName();
				}
			}
		} catch (Exception e) {
			System.out.println("Warning, " + e.getMessage());
		}
		
		return signalName;
	}

	public static boolean isVENIDValid(String venID, Direction direction){
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
		String venIDInConfig = propertiesFileReader.getVenID();

		ServiceType serviceType = TestSession.getServiceType();

		if(((serviceType.equals(ServiceType.VTN) && direction.equals(Direction.Send)) || (serviceType.equals(ServiceType.VEN) && direction.equals(Direction.Receive)))&& (venID==null || venID.trim().length()<0)){
			return true;
		}

		if(venID==null || venID.trim().length()<0){
			LogHelper
			.addTrace("\nConformance Validation Error : VEN ID has not been specified.\n");
			return false;
		}
		
		if (venID.equals(venIDInConfig)) {
			return true;
		} else {
			LogHelper
					.addTrace("\nConformance Validation Error : Unknown VEN ID "
							+ venID + " has been received\n");

			return false;
		}
	}
	
	public static void writeHTTPHeaderDisabledMessage(){
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
		if(propertiesFileReader.getTransportType().equalsIgnoreCase("HTTP") && ConformanceRuleValidator.isDisableHTTPHeader_Valid_Check()){
			if(ConformanceRuleValidator.isDisableHTTPHeader_Valid_Check()){
				
				LogHelper.addTrace("Skipping Conformance Rule 'HTTPHeader:HTTPHeader_Valid'. This Conformance Rule Check has been disabled\n");
				
				
			}
			
		}
			
	}
	
	public static boolean isCurrentDateTimeWithinFiveMin(XMLGregorianCalendar createdDateTimeReceived,XMLGregorianCalendar currentDateTime){
		
		if(createdDateTimeReceived==null){
			LogHelper.addTrace("Conformance Validation Error : CreatedDateTime has not been specified.\n");
			return false;
		}
		
		XMLGregorianCalendar currentDateTimeStart  = (XMLGregorianCalendar) currentDateTime
				.clone();
		XMLGregorianCalendar currentDateTimeEnd = (XMLGregorianCalendar) currentDateTime
				.clone();

		Duration plusFiveMin = OadrUtil.createDuration("PT5M1S");
		Duration minusFiveMin = OadrUtil.createDuration("-PT5M1S");
		currentDateTimeStart.add(minusFiveMin);
		currentDateTimeEnd.add(plusFiveMin);
		
		
		if (!(createdDateTimeReceived
				.toGregorianCalendar()
				.getTime()
				.after(currentDateTimeStart.toGregorianCalendar().getTime()) && createdDateTimeReceived
				.toGregorianCalendar().getTime()
				.before(currentDateTimeEnd.toGregorianCalendar().getTime()))) {
			return false;
		}
		
		return true;
	}
	
	public static boolean isCurrentDateTimeWithinOneMin(XMLGregorianCalendar createdDateTimeReceived,XMLGregorianCalendar currentDateTime){
		
		if(createdDateTimeReceived==null){
			LogHelper.addTrace("Conformance Validation Error : CreatedDateTime has not been specified.\n");
			return false;
		}
		XMLGregorianCalendar currentDateTimeStart  = (XMLGregorianCalendar) currentDateTime
				.clone();
		XMLGregorianCalendar currentDateTimeEnd = (XMLGregorianCalendar) currentDateTime
				.clone();

		Duration plusOneMin = OadrUtil.createDuration("PT1M1S");
		Duration minusOneMin = OadrUtil.createDuration("-PT1M1S");
		currentDateTimeStart.add(minusOneMin);
		currentDateTimeEnd.add(plusOneMin);
		
		
		if (!(createdDateTimeReceived
				.toGregorianCalendar()
				.getTime()
				.after(currentDateTimeStart.toGregorianCalendar().getTime()) && createdDateTimeReceived
				.toGregorianCalendar().getTime()
				.before(currentDateTimeEnd.toGregorianCalendar().getTime()))) {
			return false;
		}
		
		return true;
	}
	
	public static boolean isDeviceClassFound(String deviceClassToCheck){

			for (DeviceClass device : DeviceClass.values()) {
				if (device.toString().equals(deviceClassToCheck)){
					return true;
				}
			}
			LogHelper.addTrace(deviceClassToCheck+" is not a valid Device Class");
			System.out.println("");
		return false;
	}
	
	


	public static boolean isDeviceClassFound(List<EndDeviceAssetType> endDeviceAssetList){

		for(EndDeviceAssetType eachEndDeviceAssetType:endDeviceAssetList){
			if(eachEndDeviceAssetType.getMrid()!=null){
				boolean matchingFound = OadrUtil.isDeviceClassFound(eachEndDeviceAssetType.getMrid());
				if(!matchingFound){
					return false;
				}	
			}
		}
	return true;
}

	public static boolean isSchemaVersionValid(String schemaVersion){
		
		if(schemaVersion==null || schemaVersion.trim().length()<1){
			LogHelper
			.addTrace("Conformance Validation Error : SchemaVersion has not been specified");
			return false;
		}

		String profileBVersion = new PropertiesFileReader().getProfile_B();
		
		if(!schemaVersion.equals(profileBVersion)){
			LogHelper
			.addTrace("Conformance Validation Error : Expected SchemaVersion is "+profileBVersion+" Received SchemaVersion is "+schemaVersion);			
			return false;
		}
		
		return true;
		
	}
	

	public static boolean isRequestIDsMatch(String requestIDReceived,EiResponseType  eiResponseType){
		if(requestIDReceived==null || requestIDReceived.trim().length()<1) return true;
		
		String requestIDReturned = eiResponseType.getRequestID();
		
		if(requestIDReturned==null || requestIDReturned.trim().length()<1){
			LogHelper
			.addTrace("Conformance Validation Error : Response RequestID was empty but Request RequestID "+requestIDReceived+" is found");	
			TestSession.setValidationErrorFound(true);
			return false;
		}
		if(!requestIDReceived.equals(requestIDReturned)){
			//Write error message that  requestID in request and response did not match
			LogHelper
			.addTrace("Conformance Validation Error : Response's RequestID "+requestIDReturned+" did not match Request's RequestID "+requestIDReceived);	
			TestSession.setValidationErrorFound(true);
			return false;
		}
		
		return true;
	}
	
	public static boolean isOnePayloadFloatElementPresent(List<IntervalType> intervalList){
	
		if(intervalList==null) return true;
		
		for (IntervalType eachInterval : intervalList) {
			int numberOfSignalPayloadFloatFound = 0;

			List<JAXBElement<? extends StreamPayloadBaseType>>  streamPayloadBase = eachInterval.getStreamPayloadBase();

			for (JAXBElement<? extends StreamPayloadBaseType> eachStreamPayloadBase: streamPayloadBase) {
				
				StreamPayloadBaseType signalPayloadBase = eachStreamPayloadBase.getValue();
				
				if(signalPayloadBase.getClass()==SignalPayloadType.class){
					SignalPayloadType signalPayload = (SignalPayloadType)eachStreamPayloadBase.getValue();

					if(signalPayload.getPayloadBase().getValue().getClass()==PayloadFloatType.class){
						numberOfSignalPayloadFloatFound++;
					}
				}

			}
			
			if(numberOfSignalPayloadFloatFound!=1){
				LogHelper.addTrace("Conformance Validation Error : Received "+numberOfSignalPayloadFloatFound+" PayloadFloat. Expected only one");
				return false;
			}
		
		}
		return true;
	}

	
	
	public static boolean isUIDIntValue(com.qualitylogic.openadr.core.signal.xcal.Uid uid){
		try{
			Integer.parseInt(uid.getText());	
			return true;
		}catch(Exception ex){
			LogHelper.addTrace("Conformance Validation Error : Invalid UID "+uid.getText()+" found");
			return false;			
		}
	}
	
	private static String getHeaderValue(Series<Header> headers,String key){
		
		for(Header eachHeader:headers){
			String headerName = eachHeader.getName();
			String headerValue = eachHeader.getValue();
			
			if(key.equalsIgnoreCase(headerName)){
				return headerValue;
			}
			
		}
		
		return null;
	}
	
	public static String getHeaderContentLength(Series<Header> headers){		
		return getHeaderValue(headers,"Content-Length");
	}

	public static String getHeaderContentType(Series<Header> headers){		
		return getHeaderValue(headers,"Content-Type");
	}

	public static String getHeaderHost(Series<Header> headers){		
		return getHeaderValue(headers,"Host");
	}
	
}