package com.qualitylogic.openadr.core.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.mysql.jdbc.Util;
import com.qualitylogic.openadr.core.base.Debug;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.ItemBaseType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrReportDescriptionType;
import com.qualitylogic.openadr.core.signal.OadrReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;

public class XMLDBUtil {
	
	String xmlDBFile;
	Document xmlDom = null;
	//Element log = null;

	public XMLDBUtil(){
	
		synchronized(this){
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
		
		
		if(TestSession.getServiceType().equals(ServiceType.VTN)){
			xmlDBFile = propertiesFileReader.getXMLDBFile_VTN();
		}
		
		if(TestSession.getServiceType().equals(ServiceType.VEN)){
			xmlDBFile = propertiesFileReader.getXMLDBFile_VEN();
		}
		
		xmlDom = parse(xmlDBFile);
		}
	}

	public String getAttributeValue(Node node,String attributeName){
		
		if(node!=null && node.getAttributes()!=null && node.getAttributes().getNamedItem(attributeName)!=null){
			return node.getAttributes().getNamedItem(attributeName).getNodeValue();
		}
		return "";
	}	/*	
	
	public ArrayList<Node> getDataPoint(){
		Element dbRoot = xmlDom.getDocumentElement();
		NodeList registererReportReceived = dbRoot.getElementsByTagName("EACH_DATA_POINT");
		Node node=registererReportReceived.item(0);
		//NodeList childNodes = node.getChildNodes();
		
		return getAllChildNodes(node);    
	}
	*/
	
	public String getReportNameFromReportsReceivedFromVEN(String reportSpecifierIDReceivd){
		ArrayList<Node> reportList = getReportsReceivedFromVEN();
		return getReportNameFromReportsReceived(reportSpecifierIDReceivd,reportList);
	}
		

	
	public String getReportNameFromReportsReceivedFromVTN(String reportSpecifierIDReceivd){
		ArrayList<Node> reportList = getReportsReceivedFromVTN();
		return getReportNameFromReportsReceived(reportSpecifierIDReceivd,reportList);
	}
	
	
	public String getReportNameFromReportsReceived(String reportSpecifierIDReceivd,ArrayList<Node> reportList){

		for(Node eachNode:reportList){
			
			String reportSpecifierIDInDB = getAttributeValue(eachNode,"reportSpecifierID");
			
			if(reportSpecifierIDReceivd.equals(reportSpecifierIDInDB)){
				return getAttributeValue(eachNode,"reportName");
			}
		}
		
		return "";
	}

	public Node getReportFromVenByName(String reportName) {
		Node node = null;
		for (Node report : getReportsReceivedFromVEN()) {
			String name = getAttributeValue(report, "reportName");
			if (reportName.equals(name)) {
				node = report;
				break;
			}
		}
		
		return node;
	}
	
	public Node getReportFromVenBySpecifierID(String reportSpecifierID) {
		Node node = null;
		for (Node report : getReportsReceivedFromVEN()) {
			String id = getAttributeValue(report, "reportSpecifierID");
			if (reportSpecifierID.equals(id)) {
				node = report;
				break;
			}
		}
		
		return node;
	}
	
	public boolean isRIDExists(String reportSpecifierIDReceived,String ridReceived,Direction direction){

		if(ridReceived==null || ridReceived.trim().equals("") || ridReceived.trim().equals("0")) return true;
		
		ServiceType serviceType = TestSession.getServiceType();
		
		ArrayList<String> reportsindb = null;

		if(serviceType.equals(ServiceType.VTN)){
			if(direction.equals(Direction.Receive)){
				//VEN Report
				reportsindb = getRIDListFromReportsReceivedFromVTN(reportSpecifierIDReceived);
				
			}else if(direction.equals(Direction.Send)){
				//VTN Report
				reportsindb = getRIDListFromReportsReceivedFromVEN(reportSpecifierIDReceived);

			}
		}else if(serviceType.equals(ServiceType.VEN)){
			
			if(direction.equals(Direction.Receive)){
				//VTN Report
				reportsindb = getRIDListFromReportsReceivedFromVEN(reportSpecifierIDReceived);

			}else if(direction.equals(Direction.Send)){
				//VEN Report				
				reportsindb = getRIDListFromReportsReceivedFromVTN(reportSpecifierIDReceived);


			}
			
		}else{
			System.out.println("******************Unable to determine ServiceType*****************");
			System.exit(-1);
		}
		
		
		if(!OadrUtil.isIDFoundInList(ridReceived,reportsindb)){
			return false;
		}
		
		return true;
	}

	

	public String getCreateReport_ReportName(String reportSpecifierIDReceived,Direction direction){

		ServiceType serviceType = TestSession.getServiceType();
		
		if(serviceType.equals(ServiceType.VTN)){
			if(direction.equals(Direction.Receive)){
				//VEN Report
				return getReportNameFromReportsReceivedFromVTN(reportSpecifierIDReceived);
				
			}else if(direction.equals(Direction.Send)){
				//VTN Report
				return getReportNameFromReportsReceivedFromVEN(reportSpecifierIDReceived);

			}
		}else if(serviceType.equals(ServiceType.VEN)){
			
			if(direction.equals(Direction.Receive)){
				//VTN Report
				return getReportNameFromReportsReceivedFromVEN(reportSpecifierIDReceived);

			}else if(direction.equals(Direction.Send)){
				//VEN Report
				return getReportNameFromReportsReceivedFromVTN(reportSpecifierIDReceived);
				
			}
			
		}else{
			System.out.println("******************Unable to determine ServiceType*****************");
			System.exit(-1);
		}
		
		
		
		return null;
	}


	public String getUpdateReport_ReportName(String reportSpecifierIDReceived,Direction direction){

		ServiceType serviceType = TestSession.getServiceType();
		
		if(serviceType.equals(ServiceType.VTN)){
			if(direction.equals(Direction.Receive)){
				//VEN Report
				return getReportNameFromReportsReceivedFromVEN(reportSpecifierIDReceived);
				
			}else if(direction.equals(Direction.Send)){
				//VTN Report
				return getReportNameFromReportsReceivedFromVTN(reportSpecifierIDReceived);

			}
		}else if(serviceType.equals(ServiceType.VEN)){
			
			if(direction.equals(Direction.Receive)){
				//VTN Report
				return getReportNameFromReportsReceivedFromVTN(reportSpecifierIDReceived);

			}else if(direction.equals(Direction.Send)){
				//VEN Report
				return getReportNameFromReportsReceivedFromVEN(reportSpecifierIDReceived);
				
			}
			
		}else{
			System.out.println("******************Unable to determine ServiceType*****************");
			System.exit(-1);
		}
		
		
		
		return null;
	}

/*


	public String getCreateReport_ReportName(String reportSpecifierIDReceived,Direction direction){

		ServiceType serviceType = TestSession.getServiceType();
		
		String reportNameInDB = "";
		if(serviceType.equals(ServiceType.VTN)){
			if(direction.equals(Direction.Receive)){
				//VEN Report
				return getReportNameFromReportsReceivedFromVTN(reportSpecifierIDReceived);
				
			}else if(direction.equals(Direction.Send)){
				//VTN Report
				return reportNameInDB = getReportNameFromReportsReceivedFromVEN(reportSpecifierIDReceived);

			}
		}else if(serviceType.equals(ServiceType.VEN)){
			
			if(direction.equals(Direction.Receive)){
				//VTN Report
				return getReportNameFromReportsReceivedFromVTN(reportSpecifierIDReceived);

			}else if(direction.equals(Direction.Send)){
				//VEN Report
				return getReportNameFromReportsReceivedFromVEN(reportSpecifierIDReceived);
				
			}
			
		}else{
			System.out.println("******************Unable to determine ServiceType*****************");
			System.exit(-1);
		}
		
		
		
		return null;
	}

	
 */
	public boolean isReportNameValid(String reportNameReceived, String reportSpecifierIDReceived,Direction direction){

		ServiceType serviceType = TestSession.getServiceType();
		
		String reportNameInDB = "";
		if(serviceType.equals(ServiceType.VTN)){
			if(direction.equals(Direction.Receive)){
				//VEN Report
				reportNameInDB = getReportNameFromReportsReceivedFromVEN(reportSpecifierIDReceived);
				
			}else if(direction.equals(Direction.Send)){
				//VTN Report
				reportNameInDB = getReportNameFromReportsReceivedFromVTN(reportSpecifierIDReceived);

			}
		}else if(serviceType.equals(ServiceType.VEN)){
			
			if(direction.equals(Direction.Receive)){
				//VTN Report
				reportNameInDB = getReportNameFromReportsReceivedFromVTN(reportSpecifierIDReceived);

			}else if(direction.equals(Direction.Send)){
				//VEN Report
				reportNameInDB = getReportNameFromReportsReceivedFromVEN(reportSpecifierIDReceived);
				
			}
			
		}else{
			System.out.println("******************Unable to determine ServiceType*****************");
			System.exit(-1);
		}
		
		
		if(reportNameReceived.equals(reportNameInDB)){
			return true;
		}
		
		return false;
	}
	public ArrayList<String> getReportRIDFromReportsReceived(String reportSpecifierIDReceivd,ArrayList<Node> reportList){

		ArrayList<String> ridList = new ArrayList<String>();
		
		for(Node eachNode:reportList){
			
			String reportSpecifierIDInDB = getAttributeValue(eachNode,"reportSpecifierID");
			
			if(reportSpecifierIDReceivd.equals(reportSpecifierIDInDB)){
				NodeList dataPoints = eachNode.getChildNodes();
			
				for(int i=0;i<dataPoints.getLength();i++){
					Node eachDataPoint = dataPoints.item(i);

					ridList.add(getAttributeValue(eachDataPoint,"rID"));
				}
			}
		}
		
		return ridList;
	}	
	
	public ArrayList<Node> getReportsReceivedFromVEN(){
		Element dbRoot = xmlDom.getDocumentElement();
		NodeList registererReportReceived = dbRoot.getElementsByTagName("VEN_REGISTER_REPORT_RECEIVED");
		Node node=registererReportReceived.item(0);
		//NodeList childNodes = node.getChildNodes();
		
		return getAllChildNodes(node);
	}
	
	public ArrayList<String> getRIDListFromReportsReceivedFromVEN(String reportSpecifierIDReceivd){
		ArrayList<Node> reportList = getReportsReceivedFromVEN();
		ArrayList<String> rIDList=getReportRIDFromReportsReceived(reportSpecifierIDReceivd,reportList);
		return rIDList;
		
	}
	
		public ArrayList<String> getRIDListFromReportsReceivedFromVTN(String reportSpecifierIDReceivd){
		ArrayList<Node> reportList = getReportsReceivedFromVTN();
		ArrayList<String> rIDList=getReportRIDFromReportsReceived(reportSpecifierIDReceivd,reportList);
		return rIDList;
	}
		
	public ArrayList<String> getRIDListFromVTN(String reportSpecifierIDReceivd){

		ArrayList<Node> reportList = getReportsReceivedFromVTN();
		
		return getRIDList(reportSpecifierIDReceivd,reportList);
	}

	public ArrayList<String> getRIDListFromVEN(String reportSpecifierIDReceivd){

		ArrayList<Node> reportList = getReportsReceivedFromVEN();
		
		return getRIDList(reportSpecifierIDReceivd,reportList);
	}
	
	public ArrayList<String> getRIDList(String reportSpecifierIDReceivd,ArrayList<Node> reportList){

		for(Node eachNode:reportList){
			
			String reportSpecifierIDInDB = getAttributeValue(eachNode,"reportSpecifierID");
			
			if(reportSpecifierIDReceivd.equals(reportSpecifierIDInDB)){
				getRIDList(eachNode);
				
				return getRIDList(eachNode);
			}
		}
		
		return new ArrayList<String>();
	}

	
	public ArrayList<String> getRIDList(Node reportNode){

		 NodeList dataPointList = reportNode.getChildNodes();
		 ArrayList<String> ridList = new ArrayList<String>();

			for (int i = 0; i < dataPointList.getLength(); i++) {
               Node test = dataPointList.item(i);
               if(test.getNodeType() == Node.ELEMENT_NODE){
               	Element eachDataPoint = (Element)test;
               	ridList.add(getAttributeValue(eachDataPoint,"rID"));
               }
				
			}
		return ridList;
	}

	public List<DataPoint> getDataPoints(Node reportNode) {

		NodeList childNodes = reportNode.getChildNodes();
		List<DataPoint> dataPoints = new ArrayList<DataPoint>();

		for (int i = 0; i < childNodes.getLength(); i++) {
			Node node = childNodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				NamedNodeMap attributes = element.getAttributes();
				
				String maxPeriod = getNodeValue(attributes, "oadrMaxPeriod");
				String minPeriod = getNodeValue(attributes, "oadrMinPeriod");
				String rID = getNodeValue(attributes, "rID");
				String type = getNodeValue(attributes, "type");
				
				DataPoint dataPoint = new DataPoint();
				dataPoint.setMaxPeriod(maxPeriod);
				dataPoint.setMinPeriod(minPeriod);
				dataPoint.setRID(rID);
				dataPoint.setType(type);
				dataPoints.add(dataPoint);
			}
		}
		
		return dataPoints;
	}

	private String getNodeValue(NamedNodeMap attributes, String name) {
		String value = null;
		
		Node node = attributes.getNamedItem(name);
		if (node != null) {
			value = node.getNodeValue();
		}
		
		return value;
	}
	
	public static class DataPoint {
		private String maxPeriod;
		private String minPeriod;
		private String rID;
		private String type;
		
		public String getMaxPeriod() {
			return maxPeriod;
		}
		public void setMaxPeriod(String maxPeriod) {
			this.maxPeriod = maxPeriod;
		}
		public String getMinPeriod() {
			return minPeriod;
		}
		public void setMinPeriod(String minPeriod) {
			this.minPeriod = minPeriod;
		}
		public String getRID() {
			return rID;
		}
		public void setRID(String rID) {
			this.rID = rID;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	}
	
	public static void main(String []args) throws URISyntaxException, MalformedURLException, IOException{
		TestSession.setServiceType(ServiceType.VTN);

		XMLDBUtil xmlDB=new XMLDBUtil();
		
		xmlDB.getTransportAddress();
	
		
		
	/*
		XMLDBUtil xmlDB = new XMLDBUtil();
		ArrayList<Node> reportList = xmlDB.getReportsReceivedFromVTN();
		
		for(Node eachReportNode:reportList){
			System.out.println(xmlDB.getAttributeValue(eachReportNode,"reportName"));
			System.out.println(xmlDB.getAttributeValue(eachReportNode,"reportSpecifierID"));
			System.out.println(xmlDB.getAttributeValue(eachReportNode,"duration"));
			
			ArrayList<String> ridList = xmlDB.getRIDList(eachReportNode);
		
			System.out.println("----------------------------------------------");
			for(String eachRID:ridList){
				System.out.println(eachRID);
			}
			System.out.println("----------------------------------------------");			
		 for(int i=0;i<dataPointList.getLength();i++){
			 		Node eachDataPoint = dataPointList.item(0);
			 		Document doc = eachDataPoint.getOwnerDocument();
			 		doc.getElementsByTagName("EACH_DATA_POINT");
			 		//eachDataPoint.get
			 		//NamedNodeMap attributes = eachDataPoint.getAttributes();
			 		
			 		System.out.println(eachDataPoint.getNodeName());
			 		System.out.println(eachDataPoint.getNodeValue());
			 		
			 		//System.out.println(new XMLDBUtil().getAttributeValue(eachDataPoint,"rID"));
			 	}
		
		}*/
	}

	public void addEachReportReceivedFromVEN(String reportSpecifierID, String duration, String reportName,List<OadrReportDescriptionType> reportDesciptionList){
		addEachReport(reportSpecifierID, duration, reportName,"VEN_REGISTER_REPORT_RECEIVED",reportDesciptionList);
		writeXmlToFile(xmlDBFile, xmlDom);
	}
	
	/*public void clearAllChildNodes(Node node){
		
		NodeList nodeList= node.getChildNodes();
		
		for(int i=0;i<nodeList.getLength();i++){
			Node oldChild = nodeList.item(i);
			node.removeChild(oldChild);
		}
	}*/
	
	//public void removeAll(Node node, short nodeType, String name) {
	public void removeAll(Node node, short nodeType) {
	    if (node.getNodeType() == nodeType) {
	      node.getParentNode().removeChild(node);
	    } else {
	      NodeList list = node.getChildNodes();
	      for (int i = 0; i < list.getLength(); i++) {
	        removeAll(list.item(i), nodeType);
	      }
	    }
	}
	    
	private void addEachReport(String reportSpecifierID, String duration,String reportName,String elementName,List<OadrReportDescriptionType> reportDesciptionList){
		Element eachReportElement = xmlDom.createElement("EACH_REPORT");
		
		eachReportElement.setAttribute("reportSpecifierID", reportSpecifierID);
	  	eachReportElement.setAttribute("duration", duration);
	  	eachReportElement.setAttribute("reportName", reportName.replace("METADATA_", ""));
	  	
		Element dbRoot = xmlDom.getDocumentElement();
		NodeList registererReportReceived = dbRoot.getElementsByTagName(elementName);
		Node node=registererReportReceived.item(0);
		
		//removeAll(node,Node.ELEMENT_NODE,"EACH_REPORT");
		node.appendChild(eachReportElement);
		
		
		
		for(OadrReportDescriptionType eachReportDescription:reportDesciptionList){

			Element eachDataPoint = xmlDom.createElement("EACH_DATA_POINT");

			eachDataPoint.setAttribute("rID", eachReportDescription.getRID());
			
			JAXBElement<? extends ItemBaseType> itemBase = eachReportDescription.getItemBase();
			if (itemBase != null) {
				String declaredType = itemBase.getDeclaredType().getName();
				String type = declaredType.substring(declaredType.lastIndexOf(".") + 1, declaredType.indexOf("Type"));
				eachDataPoint.setAttribute("type", type);
			}
		  	
		  	if(eachReportDescription.getOadrSamplingRate()!=null){
		  		if(eachReportDescription.getOadrSamplingRate().getOadrMinPeriod()!=null){
		  			eachDataPoint.setAttribute("oadrMinPeriod", eachReportDescription.getOadrSamplingRate().getOadrMinPeriod());	
		  		}

		  		if(eachReportDescription.getOadrSamplingRate().getOadrMaxPeriod()!=null){
		  			eachDataPoint.setAttribute("oadrMaxPeriod", eachReportDescription.getOadrSamplingRate().getOadrMaxPeriod());	
		  		}
		  			
		  	}
		  	
			eachReportElement.appendChild(eachDataPoint);
				
		}
		
		writeXmlToFile(xmlDBFile, xmlDom);
	}

	public synchronized void resetDatabase(){
		if (Debug.printResetDbXmlStackTrace) {
			new Throwable().printStackTrace();
		}
		
		setVENID("");
		setRegistrationID("");
		setTransportAddress("");
		resetReports();
	}

	public synchronized void resetReports() {
		String []reports = new String[]{"VTN_REGISTER_REPORT_RECEIVED","VEN_REGISTER_REPORT_RECEIVED"};

		Element dbRoot = xmlDom.getDocumentElement();
		
		for(String eachReport:reports){
			NodeList registererReportReceived = dbRoot.getElementsByTagName(eachReport);
			
			int numberOfExistingReports =  registererReportReceived.getLength();
			
			for(int i=0;i<numberOfExistingReports;i++){
				Node node=registererReportReceived.item(i);
				removeAll(node,Node.ELEMENT_NODE);
			}
			Element reportElement = xmlDom.createElement(eachReport);
			dbRoot.appendChild(reportElement);
		}
		
		writeXmlToFile(xmlDBFile, xmlDom);
		
	}
	
	public synchronized void addEachReportReceived(OadrRegisterReportType oadrRegisterReportType,ServiceType receivedFromVENorVTN){
		addEachReportReceived(oadrRegisterReportType.getOadrReport(), receivedFromVENorVTN);
	}

	public synchronized void addEachReportReceived(OadrUpdateReportType oadrUpdateReportType,ServiceType receivedFromVENorVTN){
		addEachReportReceived(oadrUpdateReportType.getOadrReport(), receivedFromVENorVTN);
	}

	private void addEachReportReceived(List<OadrReportType> reportList, ServiceType receivedFromVENorVTN) {
		///////////////////////
		String elementName = "VTN_REGISTER_REPORT_RECEIVED";
		
		if(receivedFromVENorVTN.equals(ServiceType.VEN)){
			elementName = "VEN_REGISTER_REPORT_RECEIVED";
		}else if(receivedFromVENorVTN.equals(ServiceType.VTN)){
			elementName = "VTN_REGISTER_REPORT_RECEIVED";
		}
		
		Element dbRoot = xmlDom.getDocumentElement();
		NodeList registererReportReceived = dbRoot.getElementsByTagName(elementName);
		
		int numberOfExistingReports =  registererReportReceived.getLength();
		
		for(int i=0;i<numberOfExistingReports;i++){
			Node node=registererReportReceived.item(i);
			//removeAll(node,Node.ELEMENT_NODE,"EACH_REPORT");	
			removeAll(node,Node.ELEMENT_NODE);
		}
		
		Element reportElement = xmlDom.createElement(elementName);
		dbRoot.appendChild(reportElement);
		writeXmlToFile(xmlDBFile, xmlDom);
		
		//////////////////////
		

		for(OadrReportType eachReportToRegister:reportList){

			String reportSpecifierID = eachReportToRegister.getReportSpecifierID();
			String duration = eachReportToRegister.getDuration().getDuration();
			String reportName = eachReportToRegister.getReportName();

			if(receivedFromVENorVTN.equals(ServiceType.VEN)){
				addEachReportReceivedFromVEN(reportSpecifierID, duration,reportName, eachReportToRegister.getOadrReportDescription());	
			}else if(receivedFromVENorVTN.equals(ServiceType.VTN)){
				addEachReportReceivedFromVTN(reportSpecifierID, duration, reportName, eachReportToRegister.getOadrReportDescription());	
			}
			
		}
	}

	public ArrayList<Node> getReportsReceivedFromVTN(){
		Element dbRoot = xmlDom.getDocumentElement();
		NodeList registererReportReceived = dbRoot.getElementsByTagName("VTN_REGISTER_REPORT_RECEIVED");
		Node node=registererReportReceived.item(0);
		//NodeList childNodes = node.getChildNodes();
		
		return getAllChildNodes(node);    
	}
	
	private void addEachReportReceivedFromVTN(String reportSpecifierID, String duration, String reportName,List<OadrReportDescriptionType> reportDesciptionList){

		addEachReport(reportSpecifierID, duration, reportName,"VTN_REGISTER_REPORT_RECEIVED",reportDesciptionList);
		
		writeXmlToFile(xmlDBFile, xmlDom);

	}
	

	private ArrayList<Node> getAllChildNodes(Node node) {
	    NodeList childNodes = node.getChildNodes();
	    int length = childNodes.getLength();
	 
	    ArrayList<Node> nodeList = new ArrayList<Node>();
	    
	    for (int i = 0; i < length; i++) {
	        Node childNode = childNodes.item(i);
	        if(childNode instanceof Element) {
	        	nodeList.add(childNode);
	        }
	    }
	    
	    return nodeList;
	}
	
	public Document parse(String fileName) {
		Document document = null;
		// Initiate DocumentBuilderFactory
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// To get a validating parser
		factory.setValidating(false);
		// To get one that understands namespaces
		factory.setNamespaceAware(true);

		try {
			// Get DocumentBuilder
			DocumentBuilder builder = factory.newDocumentBuilder();
			// Parse and load into memory the Document
			document = builder.parse(new File(fileName));

			return document;

		} catch (SAXParseException spe) {
			// Error generated by the parser
			System.out.println("\n** Parsing error , line "
					+ spe.getLineNumber() + ", uri " + spe.getSystemId());
			System.out.println(" " + spe.getMessage());
			// Use the contained exception, if any
			Exception x = spe;
			if (spe.getException() != null)
				x = spe.getException();

			String exceptionMessage = "An SAXParseException exception occurred.";
			OadrUtil.exceptionHandler(x, exceptionMessage);

		} catch (SAXException sxe) {
			// Error generated during parsing
			Exception x = sxe;
			if (sxe.getException() != null)
				x = sxe.getException();
			String exceptionMessage = "An SAXParseException exception occurred.";
			OadrUtil.exceptionHandler(x, exceptionMessage);
		} catch (ParserConfigurationException pce) {
			// Parser with specified options can't be built
			String exceptionMessage = "An ParserConfigurationException exception occurred.";
			OadrUtil.exceptionHandler(pce, exceptionMessage);

		} catch (IOException ioe) {
			// I/O error
			String exceptionMessage = "An IOException exception occurred.";
			OadrUtil.exceptionHandler(ioe, exceptionMessage);
		}

		return null;
	}

	public String getVENID(){
		Element docEle = xmlDom.getDocumentElement();
		if(docEle==null) return "";
		String venID = getTextValue(docEle, "ID");
		return venID;
	}
	
	public void setVENID(String venID){
		if (Debug.printSetVenIDStackTrace) {
			System.out.println("venID=" + venID);
			new Throwable().printStackTrace();
		}
	
		Element docEle = xmlDom.getDocumentElement();
		setTextValue(docEle, "ID",venID);
		writeXmlToFile(xmlDBFile, xmlDom);
	}
	
	public void setRegistrationID(String registrationID){
		if (Debug.printSetRegistrationIDStackTrace) {
			System.out.println("registrationID=" + registrationID);
			new Throwable().printStackTrace();
		}
		
		Element docEle = xmlDom.getDocumentElement();
		setTextValue(docEle, "REGISTRATION_ID",registrationID);
		writeXmlToFile(xmlDBFile, xmlDom);
	}
	
	public String getRegistrationID(){
		Element docEle = xmlDom.getDocumentElement();
		String venID = getTextValue(docEle, "REGISTRATION_ID");
		//writeXmlToFile(xmlDBFile, xmlDom);
		return venID;
	}


	public void setTransportAddress(String registrationID){
		
		Element docEle = xmlDom.getDocumentElement();
		setTextValue(docEle, "TRANSPORT_ADDRESS",registrationID);
		writeXmlToFile(xmlDBFile, xmlDom);
	}
	
	public String getTransportAddress(){
		Element docEle = xmlDom.getDocumentElement();
		String transportAddress = getTextValue(docEle, "TRANSPORT_ADDRESS");
		if(transportAddress==null || transportAddress.length()<1) return "";
		
		String transportType = new PropertiesFileReader().getTransportType();
		if(transportType==null){
			TestSession.setValidationErrorFound(true);
			String errorMessage="********************Transport_Type is not specified in properties file********************";
			
			LogHelper.addTrace(errorMessage);
			System.out.println(errorMessage);
			
			LogHelper.setResult(LogResult.NA);
			XMLLogHelper.writeTestLog(null);
			System.exit(-1);
			
		}
		String transportToCheck = transportAddress;
		transportToCheck = transportToCheck.toLowerCase();
		
		if(transportType.equalsIgnoreCase("HTTP")){
			if(!transportToCheck.startsWith("http")){
				TestSession.setValidationErrorFound(true);
				String errorMessage="********************HTTP TRANSPORT_ADDRESS in the XML DB file was expected to have a http address. File : "+xmlDBFile+"********************";
				
				LogHelper.addTrace(errorMessage);
				System.out.println(errorMessage);
				
				LogHelper.setResult(LogResult.NA);
				XMLLogHelper.writeTestLog(null);
				System.exit(-1);
			}

		}else if(transportType.equalsIgnoreCase("XMPP")){

			if(transportToCheck.startsWith("http")){
				TestSession.setValidationErrorFound(true);
				String errorMessage="********************XMPP TRANSPORT_ADDRESS in the XML DB file was not expected to have a http address. File : "+xmlDBFile+"********************";
				
				LogHelper.addTrace(errorMessage);
				System.out.println(errorMessage);
				
				LogHelper.setResult(LogResult.NA);
				XMLLogHelper.writeTestLog(null);
				System.exit(-1);
			}

		}

		if( transportAddress.charAt(transportAddress.length() -1) == '/')
			 transportAddress = transportAddress.substring(0, transportAddress.length()-1);
		return transportAddress;
	}

	
/*	public ArrayList<String> getReportRIDFromReportsReceived_(String reportSpecifierIDReceivd,ArrayList<Node> reportList){

		ArrayList<String> ridList = new ArrayList<String>();
		
		for(Node eachNode:reportList){
			
			String reportSpecifierIDInDB = getAttributeValue(eachNode,"reportSpecifierID");
			
			if(reportSpecifierIDReceivd.equals(reportSpecifierIDInDB)){
				NodeList dataPoints = eachNode.getChildNodes();
			
				for(int i=0;i<dataPoints.getLength();i++){
					Node eachDataPoint = dataPoints.item(i);

					ridList.add(getAttributeValue(eachDataPoint,"rID"));
				}
			}
		}
		
		return ridList;
	}	
*/	

	public String getMinPeriodFromVTNReport(String reportSpecifierIDReceivd,String ridReceived){

		ArrayList<Node> reportList = getReportsReceivedFromVTN();

		return getMinPeriod(reportSpecifierIDReceivd,ridReceived,reportList);
	}

	public String getMinPeriodFromVENReport(String reportSpecifierIDReceivd,String ridReceived){

		ArrayList<Node> reportList = getReportsReceivedFromVEN();

		return getMinPeriod(reportSpecifierIDReceivd,ridReceived,reportList);
	}
	
	public String getMaxPeriodFromVTNReport(String reportSpecifierIDReceivd,String ridReceived){

		ArrayList<Node> reportList = getReportsReceivedFromVTN();

		return getMaxPeriod(reportSpecifierIDReceivd,ridReceived,reportList);
	}

	public String getMaxPeriodFromVENReport(String reportSpecifierIDReceivd,String ridReceived){

		ArrayList<Node> reportList = getReportsReceivedFromVEN();

		return getMaxPeriod(reportSpecifierIDReceivd,ridReceived,reportList);
	}
	

	public String getMinPeriod(String reportSpecifierIDReceivd, String ridReceived, ArrayList<Node> reportList){
		
		for(Node eachNode:reportList){
			
			String reportSpecifierIDInDB = getAttributeValue(eachNode,"reportSpecifierID");
			
			if(reportSpecifierIDReceivd.equals(reportSpecifierIDInDB)){
				NodeList dataPoints = eachNode.getChildNodes();
				
				for(int i=0;i<dataPoints.getLength();i++){
					Node eachDataPoint = dataPoints.item(i);

					String ridInDB = getAttributeValue(eachDataPoint,"rID");
					if(ridReceived.equals(ridInDB)){
						return getAttributeValue(eachDataPoint,"oadrMinPeriod");
					}		
				}
			}
		}
		
		return "";
	}
	

	public String getMinPeriod(String reportSpecifierIDReceived,String ridReceived,Direction direction){

		if(ridReceived==null || ridReceived.trim().equals("") || ridReceived.trim().equals("0")) return "";
		
		ServiceType serviceType = TestSession.getServiceType();
		
		if(serviceType.equals(ServiceType.VTN)){
			if(direction.equals(Direction.Receive)){
				//VEN Report
				return getMinPeriodFromVTNReport(reportSpecifierIDReceived,ridReceived);
				
			}else if(direction.equals(Direction.Send)){
				//VTN Report
				return getMinPeriodFromVENReport(reportSpecifierIDReceived,ridReceived);
			}
		}else if(serviceType.equals(ServiceType.VEN)){
			
			if(direction.equals(Direction.Receive)){
				//VTN Report
				return getMinPeriodFromVENReport(reportSpecifierIDReceived,ridReceived);

			}else if(direction.equals(Direction.Send)){
				//VEN Report				
				return getMinPeriodFromVTNReport(reportSpecifierIDReceived,ridReceived);
			}
			
		}else{
			System.out.println("******************Unable to determine ServiceType*****************");
			System.exit(-1);
		}
		
		return "";
	}

public String getMaxPeriod(String reportSpecifierIDReceivd, String ridReceived, ArrayList<Node> reportList){
		
		for(Node eachNode:reportList){
			
			String reportSpecifierIDInDB = getAttributeValue(eachNode,"reportSpecifierID");
			
			if(reportSpecifierIDReceivd.equals(reportSpecifierIDInDB)){
				NodeList dataPoints = eachNode.getChildNodes();
				
				for(int i=0;i<dataPoints.getLength();i++){
					Node eachDataPoint = dataPoints.item(i);

					String ridInDB = getAttributeValue(eachDataPoint,"rID");
					if(ridReceived.equals(ridInDB)){
						return getAttributeValue(eachDataPoint,"oadrMaxPeriod");
					}		
				}
			}
		}
		
		return "";
	}
	

	public String getMaxPeriod(String reportSpecifierIDReceived,String ridReceived,Direction direction){

		if(ridReceived==null || ridReceived.trim().equals("") || ridReceived.trim().equals("0")) return "";
		
		ServiceType serviceType = TestSession.getServiceType();
		
		if(serviceType.equals(ServiceType.VTN)){
			if(direction.equals(Direction.Receive)){
				//VEN Report
				return getMaxPeriodFromVTNReport(reportSpecifierIDReceived,ridReceived);
				
			}else if(direction.equals(Direction.Send)){
				//VTN Report
				return getMaxPeriodFromVENReport(reportSpecifierIDReceived,ridReceived);
			}
		}else if(serviceType.equals(ServiceType.VEN)){
			
			if(direction.equals(Direction.Receive)){
				//VTN Report
				return getMaxPeriodFromVENReport(reportSpecifierIDReceived,ridReceived);

			}else if(direction.equals(Direction.Send)){
				//VEN Report				
				return getMaxPeriodFromVTNReport(reportSpecifierIDReceived,ridReceived);
			}
			
		}else{
			System.out.println("******************Unable to determine ServiceType*****************");
			System.exit(-1);
		}
		
		return "";
	}

	

/*	public boolean getMinPeriod(String reportSpecifierIDReceived,String ridReceived,Direction direction){

		if(ridReceived==null || ridReceived.trim().equals("") || ridReceived.trim().equals("0")) return true;
		
		ServiceType serviceType = TestSession.getServiceType();
		
		ArrayList<String> reportsindb = null;

		if(serviceType.equals(ServiceType.VTN)){
			if(direction.equals(Direction.Receive)){
				//VEN Report
				reportsindb = getRIDListFromReportsReceivedFromVTN(reportSpecifierIDReceived);
				
			}else if(direction.equals(Direction.Send)){
				//VTN Report
				reportsindb = getRIDListFromReportsReceivedFromVEN(reportSpecifierIDReceived);

			}
		}else if(serviceType.equals(ServiceType.VEN)){
			
			if(direction.equals(Direction.Receive)){
				//VTN Report
				reportsindb = getRIDListFromReportsReceivedFromVEN(reportSpecifierIDReceived);

			}else if(direction.equals(Direction.Send)){
				//VEN Report				
				reportsindb = getRIDListFromReportsReceivedFromVTN(reportSpecifierIDReceived);


			}
			
		}else{
			System.out.println("******************Unable to determine ServiceType*****************");
			System.exit(-1);
		}
		
		
	
		return true;
	}*/
	/*
	private void saveXMLDBFile() throws URISyntaxException, MalformedURLException, IOException{
		String fileName = "db.xml";
		URL resourceUrl = this.getClass().getResource("com/qualitylogic/openadr/core/db/"
				+ fileName);
		URLConnection resourceUrl1 = new URL("classpath:org/my/package/resource.extension").openConnection();
		File file = new File(resourceUrl.toURI());
		OutputStream output = new FileOutputStream(file);
		
		
		
		URL url = this.getClass().getResource("com/qualitylogic/openadr/core/db/db.xml");   
	    String path = url.getPath();   
	   // Writer writer = new FileWriter(path);  
		
	}
	*/
	
	/*
	private Document parseXmlDBFile(){
		//get the factory
		DocumentBuilderFactory doc = DocumentBuilderFactory.newInstance();
		
		try {

			//Using factory get an instance of document builder
			DocumentBuilder db = doc.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file
			String fileName = "db.xml";
			InputStream inputStream = this
					.getClass()
					.getClassLoader()
					.getResourceAsStream(
							"com/qualitylogic/openadr/core/db/"
									+ fileName);

	
			
			
			Document dom = db.parse(inputStream);
			
			
			return dom;
			//System.out.println("dom");

		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}
	*/
	@SuppressWarnings("unchecked")
	public Object loadTestDataXMLFile(String fileName) {

		JAXBContext context;
		Object testDataFile = null;
		try {
			context = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");

			Unmarshaller unmarshall = context.createUnmarshaller();
			InputStream inputStream = this
					.getClass()
					.getClassLoader()
					.getResourceAsStream(
							"com/qualitylogic/openadr/core/db/"
									+ fileName);

			testDataFile = ((JAXBElement<Object>)unmarshall.unmarshal(inputStream)).getValue();


		} catch (Exception e) {
			e.printStackTrace();

			String exceptionString = Util.stackTraceToString(e);
			LogHelper.addTrace(exceptionString);
		}
		return testDataFile;
	}

	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			if(el==null || el.getFirstChild()==null) return "";
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}

	private void setTextValue(Element ele, String tagName,String textVaue) {
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			el.setTextContent(textVaue);
			//el.getFirstChild().setNodeValue(textVaue);
		}


	}
	public void writeXmlToFile(String filename, Document document) {
		try {
			// Prepare the DOM document for writing
			Source source = new DOMSource(document);

			// Prepare the output file
			File file = new File(filename);
			Result result = new StreamResult(file);

			// Write the DOM document to the file
			// Get Transformer
			Transformer xformer = TransformerFactory.newInstance()
					.newTransformer();
			
			if (Debug.prettyPrintXmlDb) {
				// pretty print
				xformer.setOutputProperty(OutputKeys.INDENT, "yes");
				xformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			}
			
			// Write to a file
			xformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			System.out.println("TransformerConfigurationException: " + e);
		} catch (TransformerException e) {
			System.out.println("TransformerException: " + e);
		}
	}


}
