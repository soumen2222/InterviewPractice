package com.honeywell.pull.ven;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.persistence.EntityNotFoundException;

import com.honeywell.dras.vtn.api.CommsStatusType;
import com.honeywell.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.honeywell.openadr.core.signal.OadrCancelReportType;
import com.honeywell.openadr.core.signal.OadrCreateReportType;
import com.honeywell.openadr.core.signal.OadrDistributeEventType;
import com.honeywell.openadr.core.signal.OadrPayload;
import com.honeywell.openadr.core.signal.OadrRegisterReportType;
import com.honeywell.openadr.core.signal.OadrRequestReregistrationType;
import com.honeywell.openadr.core.signal.OadrResponseType;
import com.honeywell.openadr.core.signal.OadrSignedObject;
import com.honeywell.openadr.core.signal.OadrUpdateReportType;
import com.honeywell.payloads.PojoGenerator;
import com.honeywell.ven.api.common.PushProfile;
import com.honeywell.ven.api.common.Response;
import com.honeywell.ven.api.event.DistributeEvent;
import com.honeywell.ven.api.event.Event;
import com.honeywell.ven.api.fault.VenException;
import com.honeywell.ven.api.poll.Poll;
import com.honeywell.ven.api.poll.PollRequest;
import com.honeywell.ven.api.registration.ProfileName;
import com.honeywell.ven.api.registration.TransportName;
import com.honeywell.ven.api.report.CreateReport;
import com.honeywell.ven.api.report.CreatedReport;
import com.honeywell.ven.api.report.CreatedReportRequest;
import com.honeywell.ven.api.report.PendingReports;
import com.honeywell.ven.api.report.ReportRequest;
import com.honeywell.ven.entities.event.VenEvent;
import com.honeywell.ven.entities.event.VenEventEAO;
import com.honeywell.ven.entities.registration.Registration;
import com.honeywell.ven.entities.registration.RegistrationEAO;
import com.honeywell.ven.entities.report.Reportdetail;
import com.honeywell.ven.entities.report.ReportdetailEAO;


/*possible response of poll sequence
• oadrResponse
• oadrDistributeEvent
• oadrCreateReport
• oadrRegisterReport
• oadrCancelReport
• oadrUpdateReport
• oadrCancelPartyRegistration
• oadrRequestReregistration*/


@Stateless
public class PollingSeqBean implements PollingSeq.L , PollingSeq.R {
	
	@EJB
	private VenManager.L VenManager;
	
		
	@EJB
	private DevicePollQueue.L devicePollQueue;
	
	@javax.annotation.Resource
	TimerService ts;

	@Timeout
	public void periodicallysendpoll () throws VenException , EntityNotFoundException
	{
				
		Object pollresponse = null;
		
		    PollRequest pollRequest = new PollRequest();
			Poll poll = new Poll();
			PushProfile pushProfile = new PushProfile() ;
			ProfileName profileName = null ;
			pushProfile.setProfileName(profileName.PROFILE2B);
			TransportName transport = null;
			pushProfile.setTransport(transport.simpleHttp);	
			List<Registration> registrations = new ArrayList<Registration>();
			registrations= VenManager.getallRegistrationdetails();
			if (registrations!=null){
			
			for (Registration registration : registrations) {
				
				pushProfile.setPushUrl(registration.getVtnurl());
				pollRequest.setPushProfile(pushProfile);
				poll.setVenId(registration.getVenid());
				poll.setSchemaVersion("2.0b");
				pollRequest.setPoll(poll );
				devicePollQueue.sendMessage(pollRequest);				
			}
		}
		
}	
	
	@Override
	public void createpollTimeoutHandler() {
		TimerConfig tc = new TimerConfig();
		tc.setPersistent(false);
		ts.createIntervalTimer(10000, 60000, tc);
		
	}


}
