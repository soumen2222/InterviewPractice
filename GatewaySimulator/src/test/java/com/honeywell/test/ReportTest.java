package com.honeywell.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.honeywell.ven.api.common.PushProfile;
import com.honeywell.ven.api.fault.VenException;
import com.honeywell.ven.api.registration.CreatePartyRegistration;
import com.honeywell.ven.api.registration.CreatePartyRegistrationRequest;
import com.honeywell.ven.api.registration.ProfileName;
import com.honeywell.ven.api.registration.TransportName;
import com.honeywell.ven.api.report.PayloadBase;
import com.honeywell.ven.api.report.Report;
import com.honeywell.ven.api.report.ReportDataPayload;
import com.honeywell.ven.api.report.ReportDescription;
import com.honeywell.ven.api.report.ReportInterval;
import com.honeywell.ven.api.report.ResourceStatus;
import com.honeywell.ven.api.report.ResourceStatusPayload;
import com.honeywell.ven.api.report.UpdateReport;
import com.honeywell.ven.api.report.UpdateReportRequest;
import com.honeywell.ven.services.VenSimSoapService;

public class ReportTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws VenException {
		
		
		// TODO Auto-generated method stub

		
		VenSimSoapService party = new VenSimSoapService();
		UpdateReportRequest updateReportRequest = new UpdateReportRequest();
	
			
		PushProfile pushProfile = new PushProfile() ;
		ProfileName profileName = null ;
		pushProfile.setProfileName(profileName.PROFILE2B);
		TransportName transport = null;
		pushProfile.setTransport(transport.simpleHttp);		
		String baseurl = "http://127.0.0.1:8080/bvtn";			
		updateReportRequest.setPushProfile(pushProfile);
				
		UpdateReport updateReport = new UpdateReport();
		String venId = "AKUACOM.8.3.VEN.ID:1419404685335";
		String reportRequestId = "AKUACOM.8.3.REQ:RReq:1419404956695";
		String rId ="MeterData:ADR_Gateway_DRAS7:00:40:84:03:40:31";
		updateReport.setVenId(venId );
		List<Report> reportList = new ArrayList<Report>();
		Report data1 = new Report();
		data1.setDuration(300000L);
		Date start = new Date();
		data1.setCreatedDate(start);		
		data1.setReportId(rId);
		data1.setReportName("TELEMETRY_USAGE");		
		data1.setReportRequestId(reportRequestId );
		data1.setReportSpecifierId("RS_44444");
		data1.setStart(start );
		List<ReportDescription> reportDescriptionList = new ArrayList<ReportDescription>();
		data1.setReportDescriptionList(reportDescriptionList );
		List<ReportInterval> intervalList = new ArrayList<ReportInterval>();
		ReportInterval interval1 = new ReportInterval();
		interval1.setStart(start);
		interval1.setDuration(300000L);
		interval1.setuId("0");
		List<ResourceStatusPayload> payloadList = new ArrayList<ResourceStatusPayload>();		
		ResourceStatusPayload pay1 = new ResourceStatusPayload();
		pay1.setDataQuality("Good");
		pay1.setrId(rId);
		ResourceStatus resourceStatus = new ResourceStatus();
		resourceStatus.setManualOverride(false);
		resourceStatus.setOnline(true);
		pay1.setResourceStatus(resourceStatus );
		payloadList.add(pay1);		
		interval1.setPayloadList(payloadList );		
		intervalList.add(interval1);
		data1.setIntervalList(intervalList );
		
		reportList.add(data1);
		
		updateReport.setReportList(reportList );
		updateReport.setSchemaVersion("2.0b");
		updateReportRequest.setUpdateReport(updateReport );
		
		
		party.sendReportData(updateReportRequest, baseurl);
		

	}

}
