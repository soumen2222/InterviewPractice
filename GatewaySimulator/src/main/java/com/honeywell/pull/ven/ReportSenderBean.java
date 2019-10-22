package com.honeywell.pull.ven;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import org.jboss.logging.Logger;

import com.honeywell.ven.api.common.PushProfile;
import com.honeywell.ven.api.fault.VenException;
import com.honeywell.ven.api.registration.ProfileName;
import com.honeywell.ven.api.registration.TransportName;
import com.honeywell.ven.api.report.Report;
import com.honeywell.ven.api.report.ReportDataPayload;
import com.honeywell.ven.api.report.ReportDescription;
import com.honeywell.ven.api.report.ReportInterval;
import com.honeywell.ven.api.report.ResourceStatus;
import com.honeywell.ven.api.report.ResourceStatusPayload;
import com.honeywell.ven.api.report.UpdateReport;
import com.honeywell.ven.api.report.UpdateReportRequest;
import com.honeywell.ven.entities.report.Reportdetail;
import com.honeywell.ven.entities.report.ReportdetailEAO;

@Stateless
public class ReportSenderBean implements ReportSender.L , ReportSender.R{
	private Logger log = Logger.getLogger(ReportSenderBean.class);
	
	
	@Resource(mappedName = "java:/JmsXA")
    private ConnectionFactory connectionFactory;
	
	 @Resource(mappedName = "java:/queue/deviceMeterDataQueue")
	 private Queue DeviceMeterDataQueue;
	
	@EJB
	private ReportdetailEAO.L reportdetailEAO;
	
	//@EJB
	//private ReportSenderQueue.L reportSenderQueue;
	
	@EJB
	private VenPullReqResManager.L venPullReqResManager;
	
	@javax.annotation.Resource
	TimerService ReportTS;
	
	private  static String minute =System.getProperty("com.honeywell.baseline.meterInterval"); 
	

	@Override
	public void createReportPushTimeoutHandler() {
		long intervalduration = 15;
		if (minute!=null){
		 intervalduration = (Integer.parseInt(minute))*60*1000;
		
		TimerConfig tc = new TimerConfig();
		tc.setPersistent(false);
		ReportTS.createIntervalTimer(10000, intervalduration, tc);	
		}
		
	}
	
	@Timeout
	private void periodicallysendReportData () throws VenException{
		
				
		// Telemetry Status
		List<Reportdetail> statusReportdetails = new ArrayList<Reportdetail>();
		statusReportdetails=reportdetailEAO.getReportDetailsbyspecifierid("RS_44445");
		
		if (statusReportdetails!=null){
		
		for (Reportdetail reportdetail : statusReportdetails) {
			//venPullReqResManager.updateReport(UpdateStatusreport(reportdetail));
			
			UpdateReportRequest updatestatusreport = UpdateStatusreport(reportdetail);
			sendMessageintoQueue(updatestatusreport);
						
		}
		}
		
		
		// Telemetry Usage
				List<Reportdetail> usageReportdetails = new ArrayList<Reportdetail>();
				usageReportdetails=reportdetailEAO.getReportDetailsbyspecifierid("RS_44444");
			if (usageReportdetails!=null){
				
				for (Reportdetail reportdetail : usageReportdetails) {
					//venPullReqResManager.updateReport(UpdateUsagereport(reportdetail));
					
					UpdateReportRequest updateusagereport = UpdateUsagereport(reportdetail);
					sendMessageintoQueue(updateusagereport);
					
					
				}
			}
		
	}
	
	private UpdateReportRequest UpdateStatusreport (Reportdetail reportdetail ){
		
		UpdateReportRequest updateReportRequest = new UpdateReportRequest();
		PushProfile pushProfile = new PushProfile() ;
		ProfileName profileName = null ;
		pushProfile.setProfileName(profileName.PROFILE2B);
		TransportName transport = null;
		pushProfile.setTransport(transport.simpleHttp);	

		UpdateReport updateReport = new UpdateReport();
		updateReport.setSchemaVersion("2.0b");	
		
		pushProfile.setPushUrl(reportdetail.getVtnurl());
		updateReportRequest.setPushProfile(pushProfile);	
						
		List<Report> reportList = new ArrayList<Report>();
		Report data1 = new Report();
		data1.setDuration(300000L);
		Date start = new Date();
		data1.setCreatedDate(start);		
		data1.setReportId(reportdetail.getrID());
		data1.setReportName("TELEMETRY_STATUS");		
		data1.setReportRequestId(reportdetail.getReportRequestId() );
		data1.setReportSpecifierId(reportdetail.getReportSpecifierId());
		data1.setStart(start );
		List<ReportDescription> reportDescriptionList1 = new ArrayList<ReportDescription>();
		data1.setReportDescriptionList(reportDescriptionList1 );
		List<ReportInterval> intervalList1 = new ArrayList<ReportInterval>();
		ReportInterval interval1 = new ReportInterval();
		interval1.setStart(start);
		interval1.setDuration(300000L);
		interval1.setuId("0");
		List<ResourceStatusPayload> payloadList1 = new ArrayList<ResourceStatusPayload>();		
		ResourceStatusPayload pay1 = new ResourceStatusPayload();
		pay1.setDataQuality("Good");
		pay1.setrId(reportdetail.getrID());
		ResourceStatus resourceStatus = new ResourceStatus();
		resourceStatus.setManualOverride(false);
		resourceStatus.setOnline(true);
		pay1.setResourceStatus(resourceStatus );
		payloadList1.add(pay1);		
		interval1.setPayloadList(payloadList1 );		
		intervalList1.add(interval1);
		data1.setIntervalList(intervalList1 );		
		reportList.add(data1);
		
		updateReport.setVenId(reportdetail.getVenid() );
		
		updateReport.setReportList(reportList );
		updateReportRequest.setUpdateReport(updateReport );
		
		return updateReportRequest;
	}
		
		// Telemetry Usage
	
	private UpdateReportRequest UpdateUsagereport (Reportdetail reportdetail ){
		UpdateReportRequest updateReportRequest = new UpdateReportRequest();
		PushProfile pushProfile = new PushProfile() ;
		ProfileName profileName = null ;
		pushProfile.setProfileName(profileName.PROFILE2B);
		TransportName transport = null;
		pushProfile.setTransport(transport.simpleHttp);	

		UpdateReport updateReport = new UpdateReport();
		updateReport.setSchemaVersion("2.0b");	
		
		pushProfile.setPushUrl(reportdetail.getVtnurl());
		updateReportRequest.setPushProfile(pushProfile);
		
		List<Report> reportList = new ArrayList<Report>();
		Report data2 = new Report();
		Date start = new Date();
		data2.setDuration(300000L);
		data2.setCreatedDate(start);		
		data2.setReportId(reportdetail.getrID());
		data2.setReportName("TELEMETRY_USAGE");		
		data2.setReportRequestId(reportdetail.getReportRequestId() );
		data2.setReportSpecifierId(reportdetail.getReportSpecifierId());
		data2.setStart(start );
		List<ReportDescription> reportDescriptionList2 = new ArrayList<ReportDescription>();
		data2.setReportDescriptionList(reportDescriptionList2 );
		List<ReportInterval> intervalList2 = new ArrayList<ReportInterval>();
		ReportInterval interval2 = new ReportInterval();
		interval2.setStart(start);
		interval2.setDuration(300000L);
		interval2.setuId("0");
		List<ReportDataPayload> payloadList2 = new ArrayList<ReportDataPayload>();		
		ReportDataPayload pay2 = new ReportDataPayload();
		pay2.setDataQuality("Good");
		pay2.setrId(reportdetail.getrID());
		float metervalue = (float) (50 * Math.random());
		float realmetervalue = metervalue;
		if (metervalue<15){
			realmetervalue = (float) 15.0;
		}
		else
			if (metervalue>30){
				realmetervalue = (float) 30.0;
			}
		
		pay2.setValue(realmetervalue);
		payloadList2.add(pay2);		
		interval2.setPayloadList(payloadList2 );		
		intervalList2.add(interval2);
		data2.setIntervalList(intervalList2 );		
		reportList.add(data2);
		
        updateReport.setVenId(reportdetail.getVenid() );
		
		updateReport.setReportList(reportList );
		updateReportRequest.setUpdateReport(updateReport );
		
		return updateReportRequest;	
		
	}
	
	private void sendMessageintoQueue(UpdateReportRequest mesg) {
        Connection connection = null;
        Session session = null;
         try {
             connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(DeviceMeterDataQueue);
            ObjectMessage message = session.createObjectMessage(mesg);
			messageProducer.send(message);
			messageProducer.close();
		} catch (JMSException ex) {
			log.error("JMS Exception in sendMessageintoQueue.  MESSAGE LOST!", ex);
		} catch (Exception ex) {
			log.warn("Unexpected Exception in sendMessageintoQueue.  MESSAGE LOST!", ex);
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (JMSException e) {
					log.error("JMS Exception", e);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					log.error("JMS Exception", e);
				}
			}
		}
	}

}
