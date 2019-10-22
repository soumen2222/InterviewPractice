package com.qualitylogic.openadr.core.signal.helper;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;

import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.signal.EiTargetType;
import com.qualitylogic.openadr.core.signal.IntervalType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrPayload;
import com.qualitylogic.openadr.core.signal.OadrReportDescriptionType;
import com.qualitylogic.openadr.core.signal.OadrReportPayloadType;
import com.qualitylogic.openadr.core.signal.OadrReportRequestType;
import com.qualitylogic.openadr.core.signal.OadrReportType;
import com.qualitylogic.openadr.core.signal.OadrSignedObject;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.SpecifierPayloadType;
import com.qualitylogic.openadr.core.signal.StreamPayloadBaseType;
import com.qualitylogic.openadr.core.signal.xcal.Dtstart;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.XMLDBUtil;

public class UpdateReportEventHelper {

	@SuppressWarnings("unchecked")
	public static OadrUpdateReportType createReportFromString(String data) {
		OadrUpdateReportType oadrUpdateReportType = null;
		if (data == null || data.length() < 1)
			return null;

		try {

			JAXBContext testcontext = JAXBContext
					.newInstance("com.qualitylogic.openadr.core.signal");
			InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
			Unmarshaller unmarshall = testcontext.createUnmarshaller();

			//oadrUpdateReportType = (OadrUpdateReportType)((JAXBElement<Object>)unmarshall.unmarshal(is)).getValue();
			OadrSignedObject oadrSignedObject = ((OadrPayload)unmarshall.unmarshal(is)).getOadrSignedObject();
			oadrUpdateReportType = oadrSignedObject.getOadrUpdateReport();
			//oadrUpdateReportType.getOadrReport().get(0).getCreatedDateTime()
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return oadrUpdateReportType;
	}

	
	public static int numberOfReportsReceivedForReportRequestID(String reportRequestID) {
		int i=0;
		ArrayList<OadrUpdateReportType>  updateReportList = TestSession.getOadrUpdateReportTypeReceivedList();
		
		for(OadrUpdateReportType oadrUpdateReportType:updateReportList){
			List<OadrReportType> oadrReportList = oadrUpdateReportType.getOadrReport();
			for(OadrReportType eachOadrReportType:oadrReportList){
				if(eachOadrReportType.getReportRequestID().equals(reportRequestID)){
					i++;
				}
			}
				
		}
			
		return i;
	}
	
	public static OadrUpdateReportType loadOadrUpdateReport(String fileName)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {
		
		OadrUpdateReportType oadrUpdateReportType = ((OadrUpdateReportType) new SchemaHelper()
				.loadTestDataXMLFile(fileName).getOadrUpdateReport());
		
		return oadrUpdateReportType;
	}

	// looks unused
	public static OadrUpdateReportType loadOadrUpdateReport(ServiceType serivceType,boolean isCreaterOfRegisterReport,OadrCreateReportType oadrCreateReportType)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		PropertiesFileReader properties = new PropertiesFileReader();

		OadrUpdateReportType oadrUpdateReportType = null;
		oadrUpdateReportType = ((OadrUpdateReportType) new SchemaHelper().loadTestDataXMLFile("oadrUpdateReport_History_Usage.xml").getOadrUpdateReport());
		
		// Last Created event received if oadrCreateReportType is empty.
		if(oadrCreateReportType==null){
		
			ArrayList<OadrCreateReportType>  createReportTypeReceivedList= TestSession.getOadrCreateReportTypeReceivedList();
			oadrCreateReportType=createReportTypeReceivedList.get(createReportTypeReceivedList.size()-1);
		}
		//OadrCreateReportType lastReceivedOadrCreateReportType =createReportTypeReceivedList.get(createReportTypeReceivedList.size()-1);
		//String requestID = oadrCreateReportType.getRequestID();
		
		//reportSpecifierID
		oadrUpdateReportType.setRequestID(OadrUtil.createUniqueUpdReportRequestID());
		
		oadrUpdateReportType.setVenID(properties.getVenID());
		
		OadrReportType reportTemplate=Clone.clone(oadrUpdateReportType.getOadrReport().get(0));		
		oadrUpdateReportType.getOadrReport().clear();
		
		//Instead of DB it should come from CreateReport
		List<OadrReportRequestType> oadrReportRequestTypeList =oadrCreateReportType.getOadrReportRequest();
		XMLDBUtil xmlDBUtil=new XMLDBUtil();
		for(OadrReportRequestType eachReportRequest:oadrReportRequestTypeList){
			String reportReqID = eachReportRequest.getReportRequestID();
			String reportSpecifierID = eachReportRequest.getReportSpecifier().getReportSpecifierID();
			String durationReceived = eachReportRequest.getReportSpecifier().getReportInterval().getProperties().getDuration().getDuration();
			Dtstart dtStartReceived = eachReportRequest.getReportSpecifier().getReportInterval().getProperties().getDtstart();
			
			String reportName = "";
			//ArrayList<String> ridList = new ArrayList<String>();
			//oadrCreateReportType.getOadrReportRequest().get

			
			if(serivceType.equals(ServiceType.VTN) && !isCreaterOfRegisterReport ){
				reportName = xmlDBUtil.getReportNameFromReportsReceivedFromVEN(reportSpecifierID);
			}else if(serivceType.equals(ServiceType.VEN) && !isCreaterOfRegisterReport ){
				reportName =  xmlDBUtil.getReportNameFromReportsReceivedFromVTN(reportSpecifierID);	
			}else if(serivceType.equals(ServiceType.VTN) && isCreaterOfRegisterReport ){
				reportName = xmlDBUtil.getReportNameFromReportsReceivedFromVTN(reportSpecifierID);
			}else if(serivceType.equals(ServiceType.VEN) && isCreaterOfRegisterReport ){
				reportName =  xmlDBUtil.getReportNameFromReportsReceivedFromVEN(reportSpecifierID);	
			}
			if (StringUtils.isBlank(reportName)) {
				throw new FailedException("Unrecognized ReportSpecifierID=" + reportSpecifierID);
			}
			
			reportTemplate.setReportRequestID(reportReqID);
			reportTemplate.setReportSpecifierID(reportSpecifierID);
			
			reportTemplate.setReportName(reportName);
			reportTemplate.setCreatedDateTime(OadrUtil.getCurrentTime());
			reportTemplate.getDuration().setDuration(durationReceived);
			reportTemplate.setDtstart(dtStartReceived);
			
			IntervalType interval = Clone.clone(reportTemplate.getIntervals().getInterval().get(0));
			reportTemplate.getIntervals().getInterval().clear();
			interval.setDtstart(dtStartReceived);
			//interval.getDtstart().setDateTime(dtStartReceived);
			interval.getDuration().setDuration(durationReceived);
			
			JAXBElement<OadrReportPayloadType> oadrReportPayloadType = (JAXBElement<OadrReportPayloadType>)interval.getStreamPayloadBase().get(0);
			
			//JAXBElement<OadrReportPayloadType> oadrReportPayloadTypeTemplate = Clone.clone((JAXBElement<OadrReportPayloadType>)interval.getStreamPayloadBase().get(0));
			if(interval.getStreamPayloadBase().size()==2){
				interval.getStreamPayloadBase().remove(1);	
			}
			
			
			//OadrReportPayloadType oadrReportPayload=oadrReportPayloadType.getValue();
			
			reportTemplate.getIntervals().getInterval().add(interval);
			

			
			//reportTemplate.setDtstart(eachReportRequest.)
			//reportTemplate.setDuration();

			List<SpecifierPayloadType>  reportSpecifierList = eachReportRequest.getReportSpecifier().getSpecifierPayload();
			for(SpecifierPayloadType eachSpecifierPayloadType:reportSpecifierList){
				OadrReportType  oadrReportType = Clone.clone(reportTemplate);
				OadrReportPayloadType oadrRptPayloadType=(OadrReportPayloadType)oadrReportType.getIntervals().getInterval().get(0).getStreamPayloadBase().get(0).getValue();
				
				String ridInCreateEvent=eachSpecifierPayloadType.getRID();
				//oadrRptPayloadType.setRID(ridInCreateEvent);
				oadrRptPayloadType.setRID(ridInCreateEvent);
				oadrUpdateReportType.getOadrReport().add(oadrReportType);
				
			}
			
			/*oadrUpdateReportType.getOadrReport().add(Clone.clone(reportTemplate));

			for(String eachRid:ridList){
				// Need to change it to multiple rid after test event
				OadrReportPayloadType oadrRptPayloadType=(OadrReportPayloadType)oadrUpdateReportType.getOadrReport();
				
				//oadrRptPayloadType.setRID(value);
				//OadrReportPayloadType oadrRptPayloadType=(OadrReportPayloadType)oadrUpdateReportType.getOadrReport().get(0).getIntervals().getInterval().get(0).getStreamPayloadBase().get(0).getValue();
				oadrRptPayloadType.setRID(eachRid);
				oadrUpdateReportType.getOadrReport()
			}
*/			
		}
				
		return oadrUpdateReportType;
	}
	
	public static OadrUpdateReportType loadOadrUpdateReport_Update002()
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		PropertiesFileReader properties = new PropertiesFileReader();

		OadrUpdateReportType oadrUpdateReportType = null;
		oadrUpdateReportType = ((OadrUpdateReportType) new SchemaHelper().loadTestDataXMLFile("Update_002.xml").getOadrUpdateReport());
		oadrUpdateReportType.setRequestID(OadrUtil.createUniqueUpdReportRequestID());
		oadrUpdateReportType.setVenID(properties.getVenID());	
		
		return oadrUpdateReportType;
	}
	public static OadrUpdateReportType loadOadrUpdateReport_Update001(ServiceType serivceType,boolean isCreaterOfRegisterReport,OadrCreateReportType oadrCreateReportType)
			throws JAXBException, FileNotFoundException,
			UnsupportedEncodingException {

		PropertiesFileReader properties = new PropertiesFileReader();

		OadrUpdateReportType oadrUpdateReportType = null;
		oadrUpdateReportType = ((OadrUpdateReportType) new SchemaHelper().loadTestDataXMLFile("Update_001.xml").getOadrUpdateReport());
		
		// Last Created event received if oadrCreateReportType is empty.
		if(oadrCreateReportType==null){
		
			ArrayList<OadrCreateReportType>  createReportTypeReceivedList= TestSession.getOadrCreateReportTypeReceivedList();
			oadrCreateReportType=createReportTypeReceivedList.get(createReportTypeReceivedList.size()-1);
		}
		//OadrCreateReportType lastReceivedOadrCreateReportType =createReportTypeReceivedList.get(createReportTypeReceivedList.size()-1);
		//String requestID = oadrCreateReportType.getRequestID();
		
		//reportSpecifierID
		oadrUpdateReportType.setRequestID(OadrUtil.createUniqueUpdReportRequestID());
		
		oadrUpdateReportType.setVenID(properties.getVenID());
		
		OadrReportType reportTemplate=Clone.clone(oadrUpdateReportType.getOadrReport().get(0));		
		oadrUpdateReportType.getOadrReport().clear();
		
		
		//Instead of DB it should come from CreateReport
		List<OadrReportRequestType> oadrReportRequestTypeList =oadrCreateReportType.getOadrReportRequest();
		XMLDBUtil xmlDBUtil=new XMLDBUtil();
		for(OadrReportRequestType eachReportRequest:oadrReportRequestTypeList){
			String reportReqID = eachReportRequest.getReportRequestID();
			String reportSpecifierID = eachReportRequest.getReportSpecifier().getReportSpecifierID();
			String reportName = "";

			if(serivceType.equals(ServiceType.VTN) && !isCreaterOfRegisterReport ){
				reportName = xmlDBUtil.getReportNameFromReportsReceivedFromVEN(reportSpecifierID);
			}else if(serivceType.equals(ServiceType.VEN) && !isCreaterOfRegisterReport ){
				reportName =  xmlDBUtil.getReportNameFromReportsReceivedFromVTN(reportSpecifierID);	
			}else if(serivceType.equals(ServiceType.VTN) && isCreaterOfRegisterReport ){
				reportName = xmlDBUtil.getReportNameFromReportsReceivedFromVTN(reportSpecifierID);
			}else if(serivceType.equals(ServiceType.VEN) && isCreaterOfRegisterReport ){
				reportName =  xmlDBUtil.getReportNameFromReportsReceivedFromVEN(reportSpecifierID);	
			}
			if (StringUtils.isBlank(reportName)) {
				throw new FailedException("Unrecognized ReportSpecifierID=" + reportSpecifierID);
			}
			
			reportTemplate.setReportRequestID(reportReqID);
			reportTemplate.setReportSpecifierID(reportSpecifierID);
			XMLGregorianCalendar  currentTime = OadrUtil.getCurrentTime();
			//reportTemplate.getIntervals().getInterval().get(0).getDtstart().setDateTime(currentTime);

			reportTemplate.getDtstart().setDateTime(currentTime);
			
			reportTemplate.setReportName(reportName);
			reportTemplate.setCreatedDateTime(OadrUtil.getCurrentTime());
			//reportTemplate.getDuration().setDuration(durationReceived);
			//reportTemplate.setDtstart(dtStartReceived);
			
			IntervalType interval = Clone.clone(reportTemplate.getIntervals().getInterval().get(0));
			reportTemplate.getIntervals().getInterval().clear();
			//interval.setDtstart(dtStartReceived);
			//interval.getDtstart().setDateTime(dtStartReceived);
			//interval.getDuration().setDuration(durationReceived);
			
			JAXBElement<OadrReportPayloadType> oadrReportPayloadType = (JAXBElement<OadrReportPayloadType>)interval.getStreamPayloadBase().get(0);
			
			if(interval.getStreamPayloadBase().size()==2){
				interval.getStreamPayloadBase().remove(1);	
			}
			
			reportTemplate.getIntervals().getInterval().add(interval);
			
			List<SpecifierPayloadType>  reportSpecifierList = eachReportRequest.getReportSpecifier().getSpecifierPayload();
			for(SpecifierPayloadType eachSpecifierPayloadType:reportSpecifierList){
				OadrReportType  oadrReportType = Clone.clone(reportTemplate);
				OadrReportPayloadType oadrRptPayloadType=(OadrReportPayloadType)oadrReportType.getIntervals().getInterval().get(0).getStreamPayloadBase().get(0).getValue();
				
				String ridInCreateEvent=eachSpecifierPayloadType.getRID();
				oadrRptPayloadType.setRID(ridInCreateEvent);
				oadrUpdateReportType.getOadrReport().add(oadrReportType);
				
			}
			
			
		}
				

		return oadrUpdateReportType;
	}

	public static OadrUpdateReportType loadOadrUpdateReport_Update003(OadrCreateReportType createReport) 
			throws JAXBException, FileNotFoundException, UnsupportedEncodingException {

		String filename = "Update_003.xml";
		OadrReportRequestType reportRequest = createReport.getOadrReportRequest().get(0);
		XMLGregorianCalendar currentTime = OadrUtil.getCurrentTime();
		
		OadrUpdateReportType updateReport = ((OadrUpdateReportType) new SchemaHelper().loadTestDataXMLFile(filename).getOadrUpdateReport());
		updateReport.setRequestID(OadrUtil.createUniqueUpdReportRequestID());
		updateReport.setVenID(new PropertiesFileReader().getVenID());

		OadrReportType report = updateReport.getOadrReport().get(0);
		String reportName = getReportName(reportRequest);
		
		report.setReportRequestID(reportRequest.getReportRequestID());
		report.setReportSpecifierID(reportRequest.getReportSpecifier().getReportSpecifierID());
		report.setReportName(reportName);
		report.setCreatedDateTime(currentTime);
		report.getDtstart().setDateTime(currentTime);
		
		setInterval2RIDs(0, report, reportRequest, currentTime);
		setInterval2RIDs(1, report, reportRequest, currentTime);
		setInterval2RIDs(2, report, reportRequest, currentTime);
		setInterval2RIDs(3, report, reportRequest, currentTime);
		setInterval2RIDs(4, report, reportRequest, currentTime);
		setInterval2RIDs(5, report, reportRequest, currentTime);
		
		return updateReport;
	}

	public static OadrUpdateReportType loadOadrUpdateReport_Update004(OadrCreateReportType createReport)
			throws JAXBException, FileNotFoundException, UnsupportedEncodingException {

		String filename = "Update_004.xml";
		OadrReportRequestType reportRequest = createReport.getOadrReportRequest().get(0);
		XMLGregorianCalendar currentTime = OadrUtil.getCurrentTime();
		
		OadrUpdateReportType updateReport = ((OadrUpdateReportType) new SchemaHelper().loadTestDataXMLFile(filename).getOadrUpdateReport());
		updateReport.setRequestID(OadrUtil.createUniqueUpdReportRequestID());
		updateReport.setVenID(new PropertiesFileReader().getVenID());
		
		OadrReportType report = updateReport.getOadrReport().get(0);
		report.setReportRequestID(reportRequest.getReportRequestID());
		report.setReportSpecifierID(reportRequest.getReportSpecifier().getReportSpecifierID());

		String reportName = getReportName(reportRequest);
		report.setReportName(reportName);
		report.setCreatedDateTime(currentTime);
		
		XMLGregorianCalendar timeMinusHalfDuration = (XMLGregorianCalendar) currentTime.clone();
		String durationText = "-PT30M"; // "-" + OadrUtil.divideBy2(xmlDBUtil.getAttributeValue(reportNode, "duration"));
		Duration offset = OadrUtil.createDuration(durationText);
		timeMinusHalfDuration.add(offset);
		
		report.getDtstart().setDateTime(timeMinusHalfDuration);
		
		setInterval1RID(0, 10, report, reportRequest, timeMinusHalfDuration, false);
		setInterval1RID(1, 10, report, reportRequest, timeMinusHalfDuration, false);
		setInterval1RID(2, 10, report, reportRequest, timeMinusHalfDuration, false);
		
		return updateReport;
	}

	public static OadrUpdateReportType loadOadrUpdateReport_Update005(OadrCreateReportType createReport)
			throws JAXBException, FileNotFoundException, UnsupportedEncodingException {

		String filename = "Update_005.xml";
		OadrReportRequestType reportRequest = createReport.getOadrReportRequest().get(0);
		XMLGregorianCalendar currentTime = OadrUtil.getCurrentTime();
		
		OadrUpdateReportType updateReport = ((OadrUpdateReportType) new SchemaHelper().loadTestDataXMLFile(filename).getOadrUpdateReport());
		updateReport.setRequestID(OadrUtil.createUniqueUpdReportRequestID());
		updateReport.setVenID(new PropertiesFileReader().getVenID());
		
		OadrReportType report = updateReport.getOadrReport().get(0);
		String reportName = getReportName(reportRequest);
		
		report.setReportRequestID(reportRequest.getReportRequestID());
		report.setReportSpecifierID(reportRequest.getReportSpecifier().getReportSpecifierID());
		report.setReportName(reportName);
		report.setCreatedDateTime(currentTime);
		report.getDtstart().setDateTime(currentTime);
		
		setInterval2RIDs(0, report, reportRequest, currentTime);
		
		return updateReport;
	}

	public static OadrUpdateReportType loadOadrUpdateReport_Update006(OadrCreateReportType createReport)
			throws JAXBException, FileNotFoundException, UnsupportedEncodingException {

		String filename = "Update_006.xml";
		OadrReportRequestType reportRequest = createReport.getOadrReportRequest().get(0);
		XMLGregorianCalendar currentTime = OadrUtil.getCurrentTime();
		
		OadrUpdateReportType updateReport = ((OadrUpdateReportType) new SchemaHelper().loadTestDataXMLFile(filename).getOadrUpdateReport());
		updateReport.setRequestID(OadrUtil.createUniqueUpdReportRequestID());
		updateReport.setVenID(new PropertiesFileReader().getVenID());
		
		OadrReportType report = updateReport.getOadrReport().get(0);
		String reportName = getReportName(reportRequest);
		
		report.setReportRequestID(reportRequest.getReportRequestID());
		report.setReportSpecifierID(reportRequest.getReportSpecifier().getReportSpecifierID());
		report.setReportName(reportName);
		report.setCreatedDateTime(currentTime);
		report.getDtstart().setDateTime(currentTime);
		
		setInterval1RID(0, 1, report, reportRequest, currentTime, true);
		setInterval1RID(1, 1, report, reportRequest, currentTime, true);
		
		return updateReport;
	}

	public static OadrUpdateReportType loadOadrUpdateReport_Update007(OadrCreateReportType createReport)
			throws JAXBException, FileNotFoundException, UnsupportedEncodingException {

		String filename = "Update_007.xml";
		OadrReportRequestType reportRequest = createReport.getOadrReportRequest().get(0);
		XMLGregorianCalendar currentTime = OadrUtil.getCurrentTime();
		PropertiesFileReader properties = new PropertiesFileReader();
		
		OadrUpdateReportType updateReport = ((OadrUpdateReportType) new SchemaHelper().loadTestDataXMLFile(filename).getOadrUpdateReport());
		updateReport.setRequestID(OadrUtil.createUniqueUpdReportRequestID());
		updateReport.setVenID(properties.getVenID());

		String resourceID1 = properties.getOneResourceID();
 		String marketContext = properties.get("DR_MarketContext_1_Name");
		
 		int i = 0;
		for (OadrReportType report : updateReport.getOadrReport()) {
			report.setReportRequestID(reportRequest.getReportRequestID());
			report.setReportSpecifierID(OadrUtil.createUniqueReportSpecifierID() + "_" + i++);
			report.setCreatedDateTime(currentTime);
			
			int j = 0;
		 	List<OadrReportDescriptionType> reportDescriptions = report.getOadrReportDescription();
		 	for (OadrReportDescriptionType reportDescription : reportDescriptions) {
		 		EiTargetType targetType =  reportDescription.getReportDataSource();
		 		targetType.getResourceID().clear();
		 		targetType.getResourceID().add(resourceID1);
		 		
		 		String rID = OadrUtil.createUniqueRID( )+ "_" + j++;			 		
		 		reportDescription.setRID(rID);					
				reportDescription.setMarketContext(marketContext);			 		
		 	}
		}
		
		return updateReport;
	}

	public static OadrUpdateReportType loadOadrUpdateReport_Update008(OadrCreateReportType createReport)
			throws JAXBException, FileNotFoundException, UnsupportedEncodingException {

		String filename = "Update_008.xml";
		OadrReportRequestType reportRequest = createReport.getOadrReportRequest().get(0);
		XMLGregorianCalendar currentTime = OadrUtil.getCurrentTime();
		PropertiesFileReader properties = new PropertiesFileReader();
		
		OadrUpdateReportType updateReport = ((OadrUpdateReportType) new SchemaHelper().loadTestDataXMLFile(filename).getOadrUpdateReport());
		updateReport.setRequestID(OadrUtil.createUniqueUpdReportRequestID());
		updateReport.setVenID(properties.getVenID());

		OadrReportType report = updateReport.getOadrReport().get(0);
		String reportName = getReportName(reportRequest);
		
		report.setReportRequestID(reportRequest.getReportRequestID());
		report.setReportSpecifierID(reportRequest.getReportSpecifier().getReportSpecifierID());
		report.setReportName(reportName);
		report.setCreatedDateTime(currentTime);
		report.getDtstart().setDateTime(currentTime);
		
		setInterval1RID(0, 0, report, reportRequest, currentTime, true);
		
		return updateReport;
	}
	
	public static OadrUpdateReportType loadOadrUpdateReport_Update009(OadrReportRequestType reportRequest1, OadrReportRequestType reportRequest2)
			throws JAXBException, FileNotFoundException, UnsupportedEncodingException {

		String filename = "Update_009.xml";
		XMLGregorianCalendar currentTime = OadrUtil.getCurrentTime();
		
		OadrUpdateReportType updateReport = ((OadrUpdateReportType) new SchemaHelper().loadTestDataXMLFile(filename).getOadrUpdateReport());
		updateReport.setRequestID(OadrUtil.createUniqueUpdReportRequestID());
		updateReport.setVenID(new PropertiesFileReader().getVenID());

		OadrReportType report1 = updateReport.getOadrReport().get(0);
		String reportName1 = getReportName(reportRequest1);
		
		report1.setReportRequestID(reportRequest1.getReportRequestID());
		report1.setReportSpecifierID(reportRequest1.getReportSpecifier().getReportSpecifierID());
		report1.setReportName(reportName1);
		report1.setCreatedDateTime(currentTime);
		report1.getDtstart().setDateTime(currentTime);
		
		OadrReportType report2 = updateReport.getOadrReport().get(1);
		String reportName2 = getReportName(reportRequest2);
		
		report2.setReportRequestID(reportRequest2.getReportRequestID());
		report2.setReportSpecifierID(reportRequest2.getReportSpecifier().getReportSpecifierID());
		report2.setReportName(reportName2);
		report2.setCreatedDateTime(currentTime);
		report2.getDtstart().setDateTime(currentTime);
		
		setInterval2RIDs(0, report2, reportRequest2, currentTime);
		
		return updateReport;
	}
	
	private static void setInterval2RIDs(int index, OadrReportType report, OadrReportRequestType reportRequest, XMLGregorianCalendar currentTime) {
		Duration offset = OadrUtil.createDuration(false, 0, 0, 0, 0, 10 * index, 0);
		XMLGregorianCalendar dtstart = (XMLGregorianCalendar) currentTime.clone();
		dtstart.add(offset);
		
		IntervalType interval = report.getIntervals().getInterval().get(index);
		interval.getDtstart().setDateTime(dtstart);

		List<SpecifierPayloadType> specifierPayload = reportRequest.getReportSpecifier().getSpecifierPayload();
		String rID1 = specifierPayload.get(0).getRID();
		String rID2 = specifierPayload.get(1).getRID();
		
		List<JAXBElement<? extends StreamPayloadBaseType>> streamPayloadBase = interval.getStreamPayloadBase();
		OadrReportPayloadType reportPayload1 = (OadrReportPayloadType) streamPayloadBase.get(0).getValue();
		OadrReportPayloadType reportPayload2 = (OadrReportPayloadType) streamPayloadBase.get(1).getValue();
		
		reportPayload1.setRID(rID1);
		reportPayload2.setRID(rID2);
	}

	private static void setInterval1RID(int index, int minutesOffset, OadrReportType report, OadrReportRequestType reportRequest, XMLGregorianCalendar currentTime, boolean hasDtstart) {
		Duration offset = OadrUtil.createDuration(false, 0, 0, 0, 0, (minutesOffset * index), 0);
		XMLGregorianCalendar dtstart = (XMLGregorianCalendar) currentTime.clone();
		dtstart.add(offset);

		List<SpecifierPayloadType> specifierPayload = reportRequest.getReportSpecifier().getSpecifierPayload();
		String rID = specifierPayload.get(0).getRID();
		
		IntervalType interval = report.getIntervals().getInterval().get(index);
		if (hasDtstart) {
			interval.getDtstart().setDateTime(dtstart);
		}
		
		List<JAXBElement<? extends StreamPayloadBaseType>> streamPayloadBase = interval.getStreamPayloadBase();
		OadrReportPayloadType reportPayload = (OadrReportPayloadType) streamPayloadBase.get(0).getValue();
		
		reportPayload.setRID(rID);
	}

	private static String getReportName(OadrReportRequestType reportRequest) {
		String reportName = new XMLDBUtil().getReportNameFromReportsReceivedFromVEN(reportRequest.getReportSpecifier().getReportSpecifierID());
		if (StringUtils.isBlank(reportName)) {
			throw new FailedException("Unrecognized ReportSpecifierID=" + reportRequest.getReportSpecifier().getReportSpecifierID());
		}
		return reportName;
	}
}
