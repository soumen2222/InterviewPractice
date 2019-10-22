package com.qualitylogic.openadr.core.signal.helper;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrReportRequestType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.signal.ReportNameEnumeratedType;
import com.qualitylogic.openadr.core.signal.ReportSpecifierType;
import com.qualitylogic.openadr.core.signal.SpecifierPayloadType;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.XMLDBUtil;

public class CreateReportEventHelper {

	private static final String RID_POWER_REAL = "PowerReal";
	private static final String RID_ENERGY_REAL = "EnergyReal";
	private static final String REPORT_TELEMETRY_STATUS = "TELEMETRY_STATUS";
	private static final String REPORT_TELEMETRY_USAGE = "TELEMETRY_USAGE";
	private static final String REPORT_HISTORY_USAGE = "HISTORY_USAGE";

	public static OadrCreateReportType createOadrCreateReportFromString(String data) {
		OadrCreateReportType oadrCreateReportType = null;
		if (data == null || data.length() < 1)
			return null;

		try {

			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();

			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			oadrCreateReportType = oadrSignedObject.getOadrCreateReport();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrCreateReportType;
	}

	public static OadrCreateReportType loadOadrCreateReport(String fileName)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		OadrCreateReportType oadrCreateReportType = ((OadrCreateReportType) new SchemaHelper()
				.loadTestDataXMLFile(fileName).getOadrCreateReport());

		return oadrCreateReportType;
	}

	public static OadrCreateReportType loadOadrCreateReport()
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {
		String fileName="oadrCreateReport.xml";
		OadrCreateReportType oadrCreateReportType = ((OadrCreateReportType) new SchemaHelper()
				.loadTestDataXMLFile(fileName).getOadrCreateReport());

		return oadrCreateReportType;
	}

	public static OadrCreateReportType loadOadrCreateReport_Request_004() throws JAXBException, FileNotFoundException, UnsupportedEncodingException {
		String fileName="Request_004.xml";
		OadrCreateReportType oadrCreateReportType = ((OadrCreateReportType) new SchemaHelper().loadTestDataXMLFile(fileName).getOadrCreateReport());

		PropertiesFileReader properties = new PropertiesFileReader();
		String venID = properties.getVenID();
		oadrCreateReportType.setVenID(venID);
		oadrCreateReportType.setRequestID(OadrUtil.createUniqueReportRequestID());
		
		List<OadrReportRequestType> oadrReportRequestList = oadrCreateReportType.getOadrReportRequest();
		
		oadrReportRequestList.get(0).setReportRequestID(OadrUtil.createUniqueReportRequestID());
		
		return oadrCreateReportType;
	}	
	
	public static OadrCreateReportType loadOadrCreateReport_Request_005() throws JAXBException, FileNotFoundException, UnsupportedEncodingException {
		String fileName="Request_005.xml";
		OadrCreateReportType oadrCreateReportType = ((OadrCreateReportType) new SchemaHelper().loadTestDataXMLFile(fileName).getOadrCreateReport());

		PropertiesFileReader properties = new PropertiesFileReader();
		String venID = properties.getVenID();
		oadrCreateReportType.setVenID(venID);
		oadrCreateReportType.setRequestID(OadrUtil.createUniqueReportRequestID());

		List<OadrReportRequestType> oadrReportRequestList = oadrCreateReportType.getOadrReportRequest();
		
		oadrReportRequestList.get(0).setReportRequestID(OadrUtil.createUniqueReportRequestID());
		
		// TODO: only difference with Request_004
		oadrReportRequestList.get(0).getReportSpecifier().getReportInterval().getProperties().getDtstart().setDateTime(OadrUtil.getCurrentTime());
		
		return oadrCreateReportType;
	}	
	
	public static OadrCreateReportType loadOadrCreateReport_Request_006() throws JAXBException, FileNotFoundException, UnsupportedEncodingException {
		String fileName="Request_006.xml";
		OadrCreateReportType oadrCreateReportType = ((OadrCreateReportType) new SchemaHelper().loadTestDataXMLFile(fileName).getOadrCreateReport());

		PropertiesFileReader properties = new PropertiesFileReader();
		String venID = properties.getVenID();
		oadrCreateReportType.setVenID(venID);
		oadrCreateReportType.setRequestID(OadrUtil.createUniqueReportRequestID());
		
		List<OadrReportRequestType> oadrReportRequestList = oadrCreateReportType.getOadrReportRequest();
		
		oadrReportRequestList.get(0).setReportRequestID(OadrUtil.createUniqueReportRequestID());
		
		// TODO: only difference with Request_005
		XMLGregorianCalendar currentTime = OadrUtil.getCurrentTime();
		Duration _15seconds = OadrUtil.createDuration(0, 15);
		currentTime.add(_15seconds);
		oadrReportRequestList.get(0).getReportSpecifier().getReportInterval().getProperties().getDtstart().setDateTime(currentTime);
		
		return oadrCreateReportType;
	}	

	public static OadrCreateReportType loadOadrCreateReport_Request_007() throws JAXBException, FileNotFoundException, UnsupportedEncodingException {
		OadrCreateReportType createReport = ((OadrCreateReportType) 
				new SchemaHelper().loadTestDataXMLFile("Request_007.xml").getOadrCreateReport());

		validateReportData(ServiceType.VTN, REPORT_HISTORY_USAGE, RID_ENERGY_REAL, RID_POWER_REAL);
		
		PropertiesFileReader properties = new PropertiesFileReader();
		createReport.setVenID(properties.getVenID());
		createReport.setRequestID(OadrUtil.createUniqueReportRequestID());
		
		OadrReportRequestType reportRequest = createReport.getOadrReportRequest().get(0);
		reportRequest.setReportRequestID(OadrUtil.createUniqueReportRequestID());

		ReportSpecifierType reportSpecifier = reportRequest.getReportSpecifier();
		
		XMLGregorianCalendar currentTime = OadrUtil.getCurrentTime();
		reportSpecifier.getReportInterval().getProperties().getDtstart().setDateTime(currentTime);
		
		XMLDBUtil xmlDBUtil = new XMLDBUtil();
		Node reportNode = xmlDBUtil.getReportFromVenByName(REPORT_HISTORY_USAGE);

		String reportSpecifierID = xmlDBUtil.getAttributeValue(reportNode, "reportSpecifierID");
		reportSpecifier.setReportSpecifierID(reportSpecifierID);

		List<SpecifierPayloadType> specifierPayloads = reportSpecifier.getSpecifierPayload();
		specifierPayloads.clear();
		
		NodeList nodes = reportNode.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eachDataPoint = (Element) node;
				String rID = xmlDBUtil.getAttributeValue(eachDataPoint, "rID");
				
				SpecifierPayloadType specifierPayload = new SpecifierPayloadType();
				specifierPayload.setRID(rID);
				specifierPayload.setReadingType("x-notApplicable");
				specifierPayloads.add(specifierPayload);
			}
		}
		
		return createReport;
	}	

	public static OadrCreateReportType loadOadrCreateReport_Request_008()
			throws JAXBException, FileNotFoundException, UnsupportedEncodingException {
		
		validateReportData(ServiceType.VTN, REPORT_HISTORY_USAGE, RID_ENERGY_REAL);
		
		OadrCreateReportType createReport = ((OadrCreateReportType) 
				new SchemaHelper().loadTestDataXMLFile("Request_008.xml").getOadrCreateReport());

		PropertiesFileReader properties = new PropertiesFileReader();
		createReport.setVenID(properties.getVenID());
		createReport.setRequestID(OadrUtil.createUniqueReportRequestID());
		
		OadrReportRequestType reportRequest = createReport.getOadrReportRequest().get(0);
		reportRequest.setReportRequestID(OadrUtil.createUniqueReportRequestID());

		ReportSpecifierType reportSpecifier = reportRequest.getReportSpecifier();

		XMLDBUtil xmlDBUtil = new XMLDBUtil();
		Node reportNode = xmlDBUtil.getReportFromVenByName(REPORT_HISTORY_USAGE);

		String durationText = "-PT30M"; // "-" + OadrUtil.divideBy2(xmlDBUtil.getAttributeValue(reportNode, "duration"));
		XMLGregorianCalendar currentTime = OadrUtil.getCurrentTime();
		
		Duration offset = OadrUtil.createDuration(durationText);
		currentTime.add(offset);
		reportSpecifier.getReportInterval().getProperties().getDtstart().setDateTime(currentTime);
		
		String reportSpecifierID = xmlDBUtil.getAttributeValue(reportNode, "reportSpecifierID");
		reportSpecifier.setReportSpecifierID(reportSpecifierID);

		List<SpecifierPayloadType> specifierPayloads = reportSpecifier.getSpecifierPayload();
		specifierPayloads.clear();
		
		NodeList nodes = reportNode.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eachDataPoint = (Element) node;
				String rID = xmlDBUtil.getAttributeValue(eachDataPoint, "rID");
				String type = xmlDBUtil.getAttributeValue(eachDataPoint, "type");
				
				if (RID_ENERGY_REAL.equals(type)) {
					SpecifierPayloadType specifierPayload = new SpecifierPayloadType();
					specifierPayload.setRID(rID);
					specifierPayload.setReadingType("x-notApplicable");
					specifierPayloads.add(specifierPayload);
				}
			}
		}
		
		return createReport;
	}

	public static OadrCreateReportType loadOadrCreateReport_Request_009()
			throws JAXBException, FileNotFoundException, UnsupportedEncodingException {
		
		validateReportData(ServiceType.VTN, REPORT_TELEMETRY_USAGE, RID_ENERGY_REAL, RID_POWER_REAL);
		
		OadrCreateReportType createReport = ((OadrCreateReportType) 
				new SchemaHelper().loadTestDataXMLFile("Request_009.xml").getOadrCreateReport());

		PropertiesFileReader properties = new PropertiesFileReader();
		createReport.setVenID(properties.getVenID());
		createReport.setRequestID(OadrUtil.createUniqueReportRequestID());
		
		OadrReportRequestType reportRequest = createReport.getOadrReportRequest().get(0);
		reportRequest.setReportRequestID(OadrUtil.createUniqueReportRequestID());

		ReportSpecifierType reportSpecifier = reportRequest.getReportSpecifier();
		
		XMLDBUtil xmlDBUtil = new XMLDBUtil();
		Node reportNode = xmlDBUtil.getReportFromVenByName(REPORT_TELEMETRY_USAGE);

		String reportSpecifierID = xmlDBUtil.getAttributeValue(reportNode, "reportSpecifierID");
		reportSpecifier.setReportSpecifierID(reportSpecifierID);

		String minPeriod = xmlDBUtil.getAttributeValue(reportNode.getChildNodes().item(1), "oadrMinPeriod");
		if (StringUtils.isBlank(minPeriod)) {
			throw new FailedException("Expected oadrMinPeriod in " + REPORT_TELEMETRY_USAGE + " not found.");
		}
		
		reportSpecifier.getGranularity().setDuration(minPeriod);

		List<SpecifierPayloadType> specifierPayloads = reportSpecifier.getSpecifierPayload();
		specifierPayloads.clear();
		
		NodeList nodes = reportNode.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eachDataPoint = (Element) node;
				String rID = xmlDBUtil.getAttributeValue(eachDataPoint, "rID");
				
				SpecifierPayloadType specifierPayload = new SpecifierPayloadType();
				specifierPayload.setRID(rID);
				specifierPayload.setReadingType("x-notApplicable");
				specifierPayloads.add(specifierPayload);
			}
		}
		
		return createReport;
	}	
	
	public static OadrCreateReportType loadOadrCreateReport_Request_010()
			throws JAXBException, FileNotFoundException, UnsupportedEncodingException {
		
		validateReportData(ServiceType.VTN, REPORT_TELEMETRY_USAGE, RID_POWER_REAL);

		OadrCreateReportType createReport = ((OadrCreateReportType) 
				new SchemaHelper().loadTestDataXMLFile("Request_010.xml").getOadrCreateReport());

		PropertiesFileReader properties = new PropertiesFileReader();
		createReport.setVenID(properties.getVenID());
		createReport.setRequestID(OadrUtil.createUniqueReportRequestID());
		
		OadrReportRequestType reportRequest = createReport.getOadrReportRequest().get(0);
		reportRequest.setReportRequestID(OadrUtil.createUniqueReportRequestID());

		ReportSpecifierType reportSpecifier = reportRequest.getReportSpecifier();
		
		XMLDBUtil xmlDBUtil = new XMLDBUtil();
		Node reportNode = xmlDBUtil.getReportFromVenByName(REPORT_TELEMETRY_USAGE);

		String reportSpecifierID = xmlDBUtil.getAttributeValue(reportNode, "reportSpecifierID");
		reportSpecifier.setReportSpecifierID(reportSpecifierID);

		// String minPeriod = xmlDBUtil.getAttributeValue(reportNode.getChildNodes().item(1), "oadrMinPeriod");
		// reportSpecifier.getGranularity().setDuration(minPeriod);

		XMLGregorianCalendar currentTime = OadrUtil.getCurrentTime();
		currentTime.add(OadrUtil.createDuration("PT15S"));
		reportSpecifier.getReportInterval().getProperties().getDtstart().setDateTime(currentTime);
		
		List<SpecifierPayloadType> specifierPayloads = reportSpecifier.getSpecifierPayload();
		specifierPayloads.clear();
		
		NodeList nodes = reportNode.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eachDataPoint = (Element) node;
				String rID = xmlDBUtil.getAttributeValue(eachDataPoint, "rID");
				String type = xmlDBUtil.getAttributeValue(eachDataPoint, "type");
				
				if (RID_POWER_REAL.equals(type)) {
					SpecifierPayloadType specifierPayload = new SpecifierPayloadType();
					specifierPayload.setRID(rID);
					specifierPayload.setReadingType("x-notApplicable");
					specifierPayloads.add(specifierPayload);
				}
			}
		}
		
		return createReport;
	}	

	public static OadrCreateReportType loadOadrCreateReport_Request_011()
			throws JAXBException, FileNotFoundException, UnsupportedEncodingException {

		validateReportData(ServiceType.VTN, REPORT_TELEMETRY_STATUS);
		
		OadrCreateReportType createReport = ((OadrCreateReportType) 
				new SchemaHelper().loadTestDataXMLFile("Request_011.xml").getOadrCreateReport());

		PropertiesFileReader properties = new PropertiesFileReader();
		createReport.setVenID(properties.getVenID());
		createReport.setRequestID(OadrUtil.createUniqueReportRequestID());
		
		OadrReportRequestType reportRequest = createReport.getOadrReportRequest().get(0);
		reportRequest.setReportRequestID(OadrUtil.createUniqueReportRequestID());

		ReportSpecifierType reportSpecifier = reportRequest.getReportSpecifier();
		
		XMLDBUtil xmlDBUtil = new XMLDBUtil();
		Node reportNode = xmlDBUtil.getReportFromVenByName(REPORT_TELEMETRY_STATUS);

		String reportSpecifierID = xmlDBUtil.getAttributeValue(reportNode, "reportSpecifierID");
		reportSpecifier.setReportSpecifierID(reportSpecifierID);

		XMLGregorianCalendar currentTime = OadrUtil.getCurrentTime();
		currentTime.add(OadrUtil.createDuration("PT2M"));
		reportSpecifier.getReportInterval().getProperties().getDtstart().setDateTime(currentTime);

		List<SpecifierPayloadType> specifierPayloads = reportSpecifier.getSpecifierPayload();
		specifierPayloads.clear();
		
		NodeList nodes = reportNode.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eachDataPoint = (Element) node;
				String rID = xmlDBUtil.getAttributeValue(eachDataPoint, "rID");
				
				SpecifierPayloadType specifierPayload = new SpecifierPayloadType();
				specifierPayload.setRID(rID);
				specifierPayload.setReadingType("x-notApplicable");
				specifierPayloads.add(specifierPayload);
			}
		}
		
		return createReport;
	}
	
	public static OadrCreateReportType loadOadrCreateReport_Request_012()
			throws JAXBException, FileNotFoundException, UnsupportedEncodingException {

		validateReportData(ServiceType.VTN, REPORT_TELEMETRY_STATUS);
		
		OadrCreateReportType createReport = ((OadrCreateReportType) 
				new SchemaHelper().loadTestDataXMLFile("Request_012.xml").getOadrCreateReport());

		PropertiesFileReader properties = new PropertiesFileReader();
		createReport.setVenID(properties.getVenID());
		createReport.setRequestID(OadrUtil.createUniqueReportRequestID());
		
		OadrReportRequestType reportRequest = createReport.getOadrReportRequest().get(0);
		reportRequest.setReportRequestID(OadrUtil.createUniqueReportRequestID());

		ReportSpecifierType reportSpecifier = reportRequest.getReportSpecifier();
		
		XMLDBUtil xmlDBUtil = new XMLDBUtil();
		Node reportNode = xmlDBUtil.getReportFromVenByName(REPORT_TELEMETRY_STATUS);

		String reportSpecifierID = xmlDBUtil.getAttributeValue(reportNode, "reportSpecifierID");
		reportSpecifier.setReportSpecifierID(reportSpecifierID);

		XMLGregorianCalendar currentTime = OadrUtil.getCurrentTime();
		reportSpecifier.getReportInterval().getProperties().getDtstart().setDateTime(currentTime);
		
		List<SpecifierPayloadType> specifierPayloads = reportSpecifier.getSpecifierPayload();
		specifierPayloads.clear();
		
		NodeList nodes = reportNode.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eachDataPoint = (Element) node;
				String rID = xmlDBUtil.getAttributeValue(eachDataPoint, "rID");
				
				SpecifierPayloadType specifierPayload = new SpecifierPayloadType();
				specifierPayload.setRID(rID);
				specifierPayload.setReadingType("x-notApplicable");
				specifierPayloads.add(specifierPayload);
			}
		}
		
		return createReport;
	}
	
	public static OadrCreateReportType loadOadrCreateReport_Request_013()
			throws JAXBException, FileNotFoundException, UnsupportedEncodingException {

		validateReportData(ServiceType.VTN, REPORT_TELEMETRY_STATUS);
		validateReportData(ServiceType.VTN, REPORT_TELEMETRY_USAGE, RID_ENERGY_REAL, RID_POWER_REAL);
		
		OadrCreateReportType createReport = ((OadrCreateReportType) 
				new SchemaHelper().loadTestDataXMLFile("Request_013.xml").getOadrCreateReport());

		PropertiesFileReader properties = new PropertiesFileReader();
		createReport.setVenID(properties.getVenID());
		createReport.setRequestID(OadrUtil.createUniqueReportRequestID());
		
		OadrReportRequestType reportRequest1 = createReport.getOadrReportRequest().get(0);
		reportRequest1.setReportRequestID(OadrUtil.createUniqueReportRequestID());

		ReportSpecifierType reportSpecifier1 = reportRequest1.getReportSpecifier();
		
		XMLDBUtil xmlDBUtil = new XMLDBUtil();
		Node reportNode1 = xmlDBUtil.getReportFromVenByName(REPORT_TELEMETRY_STATUS);

		String reportSpecifierID1 = xmlDBUtil.getAttributeValue(reportNode1, "reportSpecifierID");
		reportSpecifier1.setReportSpecifierID(reportSpecifierID1);

		List<SpecifierPayloadType> specifierPayloads1 = reportSpecifier1.getSpecifierPayload();
		specifierPayloads1.clear();
		
		NodeList nodes1 = reportNode1.getChildNodes();
		for (int i = 0; i < nodes1.getLength(); i++) {
			Node node = nodes1.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eachDataPoint = (Element) node;
				String rID = xmlDBUtil.getAttributeValue(eachDataPoint, "rID");
				
				SpecifierPayloadType specifierPayload = new SpecifierPayloadType();
				specifierPayload.setRID(rID);
				specifierPayload.setReadingType("x-notApplicable");
				specifierPayloads1.add(specifierPayload);
			}
		}
		
		// second report
		OadrReportRequestType reportRequest2 = createReport.getOadrReportRequest().get(1);
		reportRequest2.setReportRequestID(OadrUtil.createUniqueReportRequestID());

		ReportSpecifierType reportSpecifier2 = reportRequest2.getReportSpecifier();
		
		Node reportNode2 = xmlDBUtil.getReportFromVenByName(REPORT_TELEMETRY_USAGE);

		String reportSpecifierID2 = xmlDBUtil.getAttributeValue(reportNode2, "reportSpecifierID");
		reportSpecifier2.setReportSpecifierID(reportSpecifierID2);
	
		List<SpecifierPayloadType> specifierPayloads2 = reportSpecifier2.getSpecifierPayload();
		specifierPayloads2.clear();
		
		NodeList nodes2 = reportNode2.getChildNodes();
		for (int i = 0; i < nodes2.getLength(); i++) {
			Node node = nodes2.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eachDataPoint = (Element) node;
				String rID = xmlDBUtil.getAttributeValue(eachDataPoint, "rID");
				
				SpecifierPayloadType specifierPayload = new SpecifierPayloadType();
				specifierPayload.setRID(rID);
				specifierPayload.setReadingType("x-notApplicable");
				specifierPayloads2.add(specifierPayload);
			}
		}
		
		return createReport;
	}

	public static OadrCreateReportType loadOadrCreateReport_Request_014()
			throws JAXBException, FileNotFoundException, UnsupportedEncodingException {
		
		validateReportData(ServiceType.VTN, REPORT_TELEMETRY_STATUS);
		
		// TODO: only difference with Request_012
		OadrCreateReportType createReport = ((OadrCreateReportType) 
				new SchemaHelper().loadTestDataXMLFile("Request_014.xml").getOadrCreateReport());

		PropertiesFileReader properties = new PropertiesFileReader();
		createReport.setVenID(properties.getVenID());
		createReport.setRequestID(OadrUtil.createUniqueReportRequestID());
		
		OadrReportRequestType reportRequest = createReport.getOadrReportRequest().get(0);
		reportRequest.setReportRequestID(OadrUtil.createUniqueReportRequestID());

		ReportSpecifierType reportSpecifier = reportRequest.getReportSpecifier();
		
		XMLDBUtil xmlDBUtil = new XMLDBUtil();
		Node reportNode = xmlDBUtil.getReportFromVenByName(REPORT_TELEMETRY_STATUS);

		String reportSpecifierID = xmlDBUtil.getAttributeValue(reportNode, "reportSpecifierID");
		reportSpecifier.setReportSpecifierID(reportSpecifierID);

		XMLGregorianCalendar currentTime = OadrUtil.getCurrentTime();
		reportSpecifier.getReportInterval().getProperties().getDtstart().setDateTime(currentTime);
		
		List<SpecifierPayloadType> specifierPayloads = reportSpecifier.getSpecifierPayload();
		specifierPayloads.clear();
		
		NodeList nodes = reportNode.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eachDataPoint = (Element) node;
				String rID = xmlDBUtil.getAttributeValue(eachDataPoint, "rID");
				
				SpecifierPayloadType specifierPayload = new SpecifierPayloadType();
				specifierPayload.setRID(rID);
				specifierPayload.setReadingType("x-notApplicable");
				specifierPayloads.add(specifierPayload);
			}
		}
		
		return createReport;
	}
	
	public static OadrCreateReportType loadOadrCreateReport_Request_002(ServiceType venorvtn) throws JAXBException,
			FileNotFoundException, UnsupportedEncodingException {
		validateReportData(venorvtn, REPORT_TELEMETRY_STATUS);
		
		String fileName = "Request_002.xml";
		OadrCreateReportType oadrCreateReportType = ((OadrCreateReportType) new SchemaHelper().loadTestDataXMLFile(
				fileName).getOadrCreateReport());

		PropertiesFileReader properties = new PropertiesFileReader();
		String venID = properties.getVenID();
		oadrCreateReportType.setVenID(venID);
		oadrCreateReportType.setRequestID(OadrUtil.createUniqueReportRequestID());

		List<OadrReportRequestType> oadrReportRequestList = oadrCreateReportType.getOadrReportRequest();

		OadrReportRequestType reportTemplate = Clone.clone(oadrReportRequestList.get(0));

		oadrCreateReportType.getOadrReportRequest().clear();

		ArrayList<Node> reportList = null;

		XMLDBUtil xmlDBUtil = new XMLDBUtil();

		if (venorvtn != null && venorvtn.equals(ServiceType.VTN)) {
			reportList = xmlDBUtil.getReportsReceivedFromVEN();
		} else if (venorvtn != null && venorvtn.equals(ServiceType.VEN)) {
			reportList = xmlDBUtil.getReportsReceivedFromVTN();
		}

		for (Node eachReportRegistered : reportList) {
			String reportName = xmlDBUtil.getAttributeValue(eachReportRegistered, "reportName");
			if (!REPORT_TELEMETRY_STATUS.equals(reportName)) {
				continue;
			}

			OadrReportRequestType eachReportRequestToAdd = Clone.clone(reportTemplate);
			eachReportRequestToAdd.setReportRequestID(OadrUtil.createUniqueReportRequestID());
			ReportSpecifierType reportSpecifierType = eachReportRequestToAdd.getReportSpecifier();

			String reportSpecifierID = xmlDBUtil.getAttributeValue(eachReportRegistered, "reportSpecifierID");
			oadrCreateReportType.getOadrReportRequest().add(eachReportRequestToAdd);

			// TODO: only difference with Request_001
			eachReportRequestToAdd.getReportSpecifier().getReportInterval().getProperties().getDtstart()
					.setDateTime(OadrUtil.getCurrentTime());
			reportSpecifierType.setReportSpecifierID(reportSpecifierID);

			NodeList dataPointList = eachReportRegistered.getChildNodes();
			for (int i = 0; i < dataPointList.getLength(); i++) {
				Node test = dataPointList.item(i);
				if (test.getNodeType() == Node.ELEMENT_NODE) {
					Element eachDataPoint = (Element) test;
					String rID = xmlDBUtil.getAttributeValue(eachDataPoint, "rID");
					eachReportRequestToAdd.getReportSpecifier().getSpecifierPayload().get(0).setRID(rID);
					break;
				}
			}
		}

		return oadrCreateReportType;
	}	
	
	public static OadrCreateReportType loadOadrCreateReport_Request_001(ServiceType venorvtn) throws JAXBException,
			FileNotFoundException, UnsupportedEncodingException {
		validateReportData(venorvtn, REPORT_TELEMETRY_STATUS);
		
		String fileName = "Request_001.xml";
		OadrCreateReportType oadrCreateReportType = ((OadrCreateReportType) new SchemaHelper().loadTestDataXMLFile(
				fileName).getOadrCreateReport());

		PropertiesFileReader properties = new PropertiesFileReader();
		String venID = properties.getVenID();
		oadrCreateReportType.setVenID(venID);
		oadrCreateReportType.setRequestID(OadrUtil.createUniqueReportRequestID());

		List<OadrReportRequestType> oadrReportRequestList = oadrCreateReportType.getOadrReportRequest();

		OadrReportRequestType reportTemplate = Clone.clone(oadrReportRequestList.get(0));

		oadrCreateReportType.getOadrReportRequest().clear();

		ArrayList<Node> reportList = null;

		XMLDBUtil xmlDBUtil = new XMLDBUtil();

		if (venorvtn != null && venorvtn.equals(ServiceType.VTN)) {
			reportList = xmlDBUtil.getReportsReceivedFromVEN();
		} else if (venorvtn != null && venorvtn.equals(ServiceType.VEN)) {
			reportList = xmlDBUtil.getReportsReceivedFromVTN();
		}

		for (Node eachReportRegistered : reportList) {
			String reportName = xmlDBUtil.getAttributeValue(eachReportRegistered, "reportName");
			if (!REPORT_TELEMETRY_STATUS.equals(reportName)) {
				continue;
			}

			OadrReportRequestType eachReportRequestToAdd = Clone.clone(reportTemplate);
			eachReportRequestToAdd.setReportRequestID(OadrUtil.createUniqueReportRequestID());
			ReportSpecifierType reportSpecifierType = eachReportRequestToAdd.getReportSpecifier();

			String reportSpecifierID = xmlDBUtil.getAttributeValue(eachReportRegistered, "reportSpecifierID");
			oadrCreateReportType.getOadrReportRequest().add(eachReportRequestToAdd);

			reportSpecifierType.setReportSpecifierID(reportSpecifierID);

			NodeList dataPointList = eachReportRegistered.getChildNodes();
			for (int i = 0; i < dataPointList.getLength(); i++) {
				Node test = dataPointList.item(i);
				if (test.getNodeType() == Node.ELEMENT_NODE) {
					Element eachDataPoint = (Element) test;
					String rID = xmlDBUtil.getAttributeValue(eachDataPoint, "rID");
					eachReportRequestToAdd.getReportSpecifier().getSpecifierPayload().get(0).setRID(rID);
					break;
				}
			}
		}
		
		return oadrCreateReportType;
	}

	public static OadrCreateReportType loadOadrCreateReport_Request_003(ServiceType venorvtn) throws JAXBException,
			FileNotFoundException, UnsupportedEncodingException {
		validateReportData(venorvtn, REPORT_TELEMETRY_STATUS);
		
		String fileName = "Request_003.xml";
		OadrCreateReportType oadrCreateReportType = ((OadrCreateReportType) new SchemaHelper().loadTestDataXMLFile(
				fileName).getOadrCreateReport());

		PropertiesFileReader properties = new PropertiesFileReader();
		String venID = properties.getVenID();
		oadrCreateReportType.setVenID(venID);
		oadrCreateReportType.setRequestID(OadrUtil.createUniqueReportRequestID());

		List<OadrReportRequestType> oadrReportRequestList = oadrCreateReportType.getOadrReportRequest();

		OadrReportRequestType reportTemplate = Clone.clone(oadrReportRequestList.get(0));

		oadrCreateReportType.getOadrReportRequest().clear();

		ArrayList<Node> reportList = null;

		XMLDBUtil xmlDBUtil = new XMLDBUtil();

		if (venorvtn != null && venorvtn.equals(ServiceType.VTN)) {
			reportList = xmlDBUtil.getReportsReceivedFromVEN();
		} else if (venorvtn != null && venorvtn.equals(ServiceType.VEN)) {
			reportList = xmlDBUtil.getReportsReceivedFromVTN();
		}

		for (Node eachReportRegistered : reportList) {
			String reportName = xmlDBUtil.getAttributeValue(eachReportRegistered, "reportName");
			if (!REPORT_TELEMETRY_STATUS.equals(reportName)) {
				continue;
			}

			OadrReportRequestType eachReportRequestToAdd = Clone.clone(reportTemplate);
			eachReportRequestToAdd.setReportRequestID(OadrUtil.createUniqueReportRequestID());
			ReportSpecifierType reportSpecifierType = eachReportRequestToAdd.getReportSpecifier();
			
			// TODO: only difference with Request_001
			reportSpecifierType.getReportInterval().getProperties().getDtstart().setDateTime(OadrUtil.getCurrentTime());

			String reportSpecifierID = xmlDBUtil.getAttributeValue(eachReportRegistered, "reportSpecifierID");
			oadrCreateReportType.getOadrReportRequest().add(eachReportRequestToAdd);

			reportSpecifierType.setReportSpecifierID(reportSpecifierID);

			NodeList dataPointList = eachReportRegistered.getChildNodes();
			for (int i = 0; i < dataPointList.getLength(); i++) {
				Node test = dataPointList.item(i);
				if (test.getNodeType() == Node.ELEMENT_NODE) {
					Element eachDataPoint = (Element) test;
					String rID = xmlDBUtil.getAttributeValue(eachDataPoint, "rID");
					eachReportRequestToAdd.getReportSpecifier().getSpecifierPayload().get(0).setRID(rID);
					break;
				}
			}
		}

		return oadrCreateReportType;
	}

	// TODO: looks unused
	public static OadrCreateReportType loadOadrCreateReport(ServiceType venorvtn) throws JAXBException,
			FileNotFoundException, UnsupportedEncodingException {

		PropertiesFileReader properties = new PropertiesFileReader();

		OadrCreateReportType oadrCreateReportType = loadOadrCreateReport();

		oadrCreateReportType.setRequestID(OadrUtil.createUniqueReportRequestID());
		oadrCreateReportType.setVenID(properties.getVenID());

		OadrReportRequestType reportTemplate = Clone.clone(oadrCreateReportType.getOadrReportRequest().get(0));

		oadrCreateReportType.getOadrReportRequest().clear();

		XMLDBUtil xmlDBUtil = new XMLDBUtil();

		String reportVENID = properties.getVenID();

		ArrayList<Node> reportList = null;

		XMLGregorianCalendar reportAvailableTime = OadrUtil.getCurrentTime();

		if (venorvtn != null && venorvtn.equals(ServiceType.VTN)) {
			// reportAvailableTime = OadrUtil.stringToXMLGregorianCalendar(properties.getVTN_ReportAvailableDateTime());
			reportList = xmlDBUtil.getReportsReceivedFromVEN();
		} else if (venorvtn != null && venorvtn.equals(ServiceType.VEN)) {
			// reportAvailableTime = OadrUtil.stringToXMLGregorianCalendar(properties.getVEN_ReportAvailableDateTime());
			reportAvailableTime = OadrUtil.getCurrentTime();
			reportList = xmlDBUtil.getReportsReceivedFromVTN();
		}

		for (Node eachReportRegistered : reportList) {

			OadrReportRequestType eachReportRequestToAdd = Clone.clone(reportTemplate);

			oadrCreateReportType.getOadrReportRequest().add(eachReportRequestToAdd);

			String eachReportRequestID = OadrUtil.createUniqueReportRequestID();

			String eachDuration = xmlDBUtil.getAttributeValue(eachReportRegistered, "duration");
			// String oadrMaxPeriod = xmlDBUtil.getAttributeValue(eachReportRegistered,"oadrMaxPeriod");
			// String oadrMinPeriod = xmlDBUtil.getAttributeValue(eachReportRegistered,"oadrMinPeriod");
			// String rID = xmlDBUtil.getAttributeValue(eachReportRegistered,"rID");
			String reportName = xmlDBUtil.getAttributeValue(eachReportRegistered, "reportName");
			String reportSpecifierID = xmlDBUtil.getAttributeValue(eachReportRegistered, "reportSpecifierID");

			// Duration minPeriod = OadrUtil.createDuration(oadrMinPeriod);
			// Duration maxPeriod = OadrUtil.createDuration(oadrMaxPeriod);

			// Duration durationToRemove = OadrUtil.createDuration("-"+minPeriod.toString());

			// Duration reportDuration = Clone.clone(maxPeriod);
			// reportDuration.add(durationToRemove);

			// ReportNameEnumeratedType.METADATA_TELEMETRY_USAGE
			if (reportName != null && reportName.equals(ReportNameEnumeratedType.HISTORY_USAGE.toString())) {
				eachReportRequestToAdd.getReportSpecifier().getGranularity().setDuration("PT0M");
				eachReportRequestToAdd.getReportSpecifier().getReportInterval().getProperties().getDuration()
						.setDuration(eachDuration);
				// eachReportRequestToAdd.getReportSpecifier().getReportInterval().getProperties().getDtstart().setDateTime(reportAvailableTime);

			} else if (reportName != null && reportName.equals(ReportNameEnumeratedType.TELEMETRY_USAGE.toString())) {

				// When the data should be delivered. Current time is set for immediate delivery.
				// eachReportRequestToAdd.getReportSpecifier().getReportInterval().getProperties().getDtstart().setDateTime(reportAvailableTime);

				// How long the report should be delivered.
				eachReportRequestToAdd.getReportSpecifier().getReportInterval().getProperties().getDuration()
						.setDuration("PT0M");

				// How often the report should be delivered. If it is 0 then one time delivery.
				eachReportRequestToAdd.getReportSpecifier().getReportBackDuration().setDuration("PT0M");
			}

			eachReportRequestToAdd.setReportRequestID(eachReportRequestID);
			eachReportRequestToAdd.getReportSpecifier().setReportSpecifierID(reportSpecifierID);
			eachReportRequestToAdd.getReportSpecifier().getReportInterval().getProperties().getDtstart()
					.setDateTime(reportAvailableTime);

			// Add all RIDs from the database(Received during registration).
			NodeList dataPointList = eachReportRegistered.getChildNodes();
			for (int i = 0; i < dataPointList.getLength(); i++) {
				Node test = dataPointList.item(i);
				if (test.getNodeType() == Node.ELEMENT_NODE) {
					Element eachDataPoint = (Element) test;
					String rID = xmlDBUtil.getAttributeValue(eachDataPoint, "rID");
					eachReportRequestToAdd.getReportSpecifier().getSpecifierPayload().get(0).setRID(rID);
					break;
				}
			}

			// Properties reportIntervalProperties =
			// eachReportRequestToAdd.getReportSpecifier().getReportInterval().getProperties();

			// reportTemplate.setReportRequestID(eachReportRequestID);
			// ReportSpecifierType reportSpecifierType = reportTemplate.getReportSpecifier();
			// reportSpecifierType.setReportSpecifierID(eachReportSpecifierID);

		}

		// }
		return oadrCreateReportType;
	}

	private static void validateReportData(ServiceType serviceType, String reportName, String... types) {
		assert serviceType != null;
		
		XMLDBUtil xmlDBUtil = new XMLDBUtil();
		
		ArrayList<Node> reports = null;
		if (serviceType.equals(ServiceType.VTN)) {
			reports = xmlDBUtil.getReportsReceivedFromVEN();
		} else if (serviceType.equals(ServiceType.VEN)) {
			reports = xmlDBUtil.getReportsReceivedFromVTN();
		}
		
		boolean foundReportName = false; 
		for (Node report : reports) {
			String currentReportName = xmlDBUtil.getAttributeValue(report, "reportName");
			if (reportName.equals(currentReportName)) {
				foundReportName = true;
				
				String reportSpecifierID = xmlDBUtil.getAttributeValue(report, "reportSpecifierID");
				if (StringUtils.isBlank(reportSpecifierID)) {
					throw new FailedException("Expected reportSpecifierID for " + reportName + " not found.");
				}
				
				if (types.length == 0) {
					if (!checkRIdExists(xmlDBUtil, report)) {
						throw new FailedException("Expected RID not found.");
					}
				} else {
					for (String type : types) {
						if (!checkRIdExists(xmlDBUtil, report, type)) {
							throw new FailedException("Expected RID of type " + type + " not found.");
						}
					}
				}
				
				break;
			}
		}
		
		if (!foundReportName) {
			throw new FailedException("Expected report=" + reportName + " not found.");
		}
	}

	private static boolean checkRIdExists(XMLDBUtil xmlDBUtil, Node report) {
		boolean foundRID = false; 
		NodeList dataPoints = report.getChildNodes();
		for (int i = 0; i < dataPoints.getLength(); i++) {
			Node dataPoint = dataPoints.item(i);
			if (dataPoint.getNodeType() == Node.ELEMENT_NODE) {
				String rID = xmlDBUtil.getAttributeValue(dataPoint, "rID");
									
				if (!StringUtils.isBlank(rID)) {
					foundRID = true;
					break;
				}
			}
		}

		return foundRID;
	}

	private static boolean checkRIdExists(XMLDBUtil xmlDBUtil, Node report, String type) {
		boolean foundRID = false; 
		NodeList dataPoints = report.getChildNodes();
		for (int i = 0; i < dataPoints.getLength(); i++) {
			Node dataPoint = dataPoints.item(i);
			if (dataPoint.getNodeType() == Node.ELEMENT_NODE) {
				String rID = xmlDBUtil.getAttributeValue(dataPoint, "rID");
				String currentType = xmlDBUtil.getAttributeValue(dataPoint, "type");				
									
				if (!StringUtils.isBlank(rID) && type.equals(currentType)) {
					foundRID = true;
				}
			}
		}
		
		return foundRID;
	}
}