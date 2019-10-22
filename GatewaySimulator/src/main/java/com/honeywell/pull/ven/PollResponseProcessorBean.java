package com.honeywell.pull.ven;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
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
import com.honeywell.openadr.core.signal.OptTypeType;
import com.honeywell.payloads.PojoGenerator;
import com.honeywell.ven.api.OptType;
import com.honeywell.ven.api.common.PushProfile;
import com.honeywell.ven.api.common.Response;
import com.honeywell.ven.api.event.CreatedEvent;
import com.honeywell.ven.api.event.CreatedEventRequest;
import com.honeywell.ven.api.event.DistributeEvent;
import com.honeywell.ven.api.event.Event;
import com.honeywell.ven.api.event.EventResponse;
import com.honeywell.ven.api.fault.VenException;
import com.honeywell.ven.api.poll.PollRequest;
import com.honeywell.ven.api.registration.ProfileName;
import com.honeywell.ven.api.registration.TransportName;
import com.honeywell.ven.api.report.CreateReport;
import com.honeywell.ven.api.report.CreatedReport;
import com.honeywell.ven.api.report.CreatedReportRequest;
import com.honeywell.ven.api.report.PendingReports;
import com.honeywell.ven.api.report.ReportRequest;
import com.honeywell.ven.entities.event.VenEvent;
import com.honeywell.ven.entities.registration.Registration;
import com.honeywell.ven.entities.report.Reportdetail;


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
public class PollResponseProcessorBean implements PollResponseProcessor.L , PollResponseProcessor.R {
	
	@EJB
	private VenManager.L VenManager;
	
		
	@EJB
	private VenPullReqResManager.L venPullReqResManager;
		
	@Override
	public void pollprocessor(Object o , PollRequest pollRequest) {
		
		
		OadrSignedObject signedObject = ((OadrPayload) o).getOadrSignedObject();
		if (signedObject != null) {
			o = signedObject.getOadrCanceledOpt();
			if (o == null) {
				o = signedObject.getOadrCanceledPartyRegistration();
			}
			if (o == null) {
				o = signedObject.getOadrCanceledReport();
			}
			if (o == null) {
				o = signedObject.getOadrCancelOpt();
			}
			if (o == null) {
				o = signedObject.getOadrCancelPartyRegistration();
			}
			if (o == null) {
				o = signedObject.getOadrCancelReport();
			}
			if (o == null) {
				o = signedObject.getOadrCreatedEvent();
			}
			if (o == null) {
				o = signedObject.getOadrCreatedOpt();
			}
			if (o == null) {
				o = signedObject.getOadrCreatedPartyRegistration();
			}
			if (o == null) {
				o = signedObject.getOadrCreatedReport();
			}
			if (o == null) {
				o = signedObject.getOadrCreateOpt();
			}
			if (o == null) {
				o = signedObject.getOadrCreatePartyRegistration();
			}
			if (o == null) {
				o = signedObject.getOadrCreateReport();
			}
			if (o == null) {
				o = signedObject.getOadrDistributeEvent();
			}
			if (o == null) {
				o = signedObject.getOadrPoll();
			}
			if (o == null) {
				o = signedObject.getOadrQueryRegistration();
			}
			if (o == null) {
				o = signedObject.getOadrRegisteredReport();
			}
			if (o == null) {
				o = signedObject.getOadrRegisterReport();
			}
			if (o == null) {
				o = signedObject.getOadrRequestEvent();
			}
			if (o == null) {
				o = signedObject.getOadrRequestReregistration();
			}
			if (o == null) {
				o = signedObject.getOadrResponse();
			}
			if (o == null) {
				o = signedObject.getOadrUpdatedReport();
			}
			if (o == null) {
				o = signedObject.getOadrUpdateReport();
			}
			if (o == null) {
				o = signedObject.getOadrCanceledReport();
			}
		}
    
	if (o instanceof OadrResponseType) {
			
		handleRequest((OadrResponseType) o);
	} else 	if (o instanceof OadrDistributeEventType) {
				
		try {
			handleRequest((OadrDistributeEventType) o , pollRequest);
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (VenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} else if (o instanceof OadrCreateReportType) {
		handleRequest((OadrCreateReportType) o );
		
	}else if (o instanceof OadrRegisterReportType) {	
		handleRequest((OadrRegisterReportType) o);
		
	}else if (o instanceof OadrCancelReportType) {
		handleRequest((OadrCancelReportType) o);
		
	}else if (o instanceof OadrUpdateReportType) {
		handleRequest((OadrUpdateReportType) o);
			
	}else if (o instanceof OadrCancelPartyRegistrationType) {
		handleRequest((OadrCancelPartyRegistrationType) o);
		
		
	}else if (o instanceof OadrRequestReregistrationType)
	{	
		handleRequest((OadrRequestReregistrationType) o);
	}	
	
		
}

	private void Devicecomupdate(OadrResponseType o) {
		// TODO Auto-generated method stub
			
		if (o!=null)
		{
			
		Registration registration = VenManager.getRegistrationdetailssbyvenid(o.getVenID());
	  if (registration!=null){
		  registration.setVenComStatus((CommsStatusType.valueOf("ONLINE")).toString());
			VenManager.updateRegistration(registration);
	  }
		
	}
		
	}

	private void handleRequest(OadrRequestReregistrationType o) {
		// TODO Auto-generated method stub
		
	}

	private void handleRequest(OadrCancelPartyRegistrationType o) {
		// TODO Auto-generated method stub
		
	}

	private void handleRequest(OadrUpdateReportType o) {
		// TODO Auto-generated method stub
		
	}

	private void handleRequest(OadrCancelReportType o) {
		// TODO Auto-generated method stub
		
	}

	private void handleRequest(OadrCreateReportType oadrCreateReportType) {
		// TODO Auto-generated method stub
		PojoGenerator pojoGenerator = new PojoGenerator();
		CreateReport createReport = new CreateReport();
		List<Reportdetail> reportdetails = new ArrayList<Reportdetail>();
		createReport = pojoGenerator.getCreateReport(oadrCreateReportType);
		Registration registration = VenManager.getRegistrationdetailssbyvenid(createReport.getVenId());
		if (registration!=null){
		
		for (ReportRequest reportRequest : createReport.getReportRequestList()) {
			Reportdetail  reportdetail = new Reportdetail();
			reportdetail.setReportRequestId(reportRequest.getReportRequestId());
			reportdetail.setReportSpecifierId(reportRequest.getReportSpecifier().getReportSpecifierId());
			reportdetail.setVenid(createReport.getVenId());
			reportdetail.setReportCreated(true);
			reportdetail.setrID("MeterData:" + registration.getVenName() );
			reportdetail.setVtnurl(registration.getVtnurl());
			reportdetails.add(reportdetail);
		}
				
		VenManager.createReportdetail(reportdetails );
		
		}
		CreatedReportRequest createdReportRequest = new CreatedReportRequest() ;
		PushProfile pushProfile = new PushProfile() ;
		ProfileName profileName = null ;
		pushProfile.setProfileName(profileName.PROFILE2B);
		TransportName transport = null;
		pushProfile.setTransport(transport.simpleHttp);	
		pushProfile.setPushUrl(registration.getVtnurl());
		createdReportRequest.setPushProfile(pushProfile);
		CreatedReport createdReport = new CreatedReport();
		createdReport.setSchemaVersion("2.0b");
		createdReport.setVenId(createReport.getVenId());		
		Response eiresponse = new Response();
		eiresponse.setRequestId("Req_1234");
		eiresponse.setResponseCode("200");
		eiresponse.setResponseDescription("OK");
		createdReport.setResponse(eiresponse );
		PendingReports pendingReports =new PendingReports() ;
		List<String> reportRequestIdList = new ArrayList<String>();
		for (Reportdetail reportdetail : reportdetails) {			
			reportRequestIdList.add(reportdetail.getReportRequestId());
		}
		
		pendingReports.setReportRequestIdList(reportRequestIdList);
		createdReport.setPendingReports(pendingReports);		
		createdReportRequest.setCreatedReport(createdReport );
		
		
		try {
			venPullReqResManager.sendcreatedReport(createdReportRequest);
		} catch (VenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void handleRequest(OadrDistributeEventType oadrDistributeEventType, PollRequest pollRequest ) throws EntityNotFoundException, VenException {
		// TODO Auto-generated method stub
		//Do the Distrubute Event Logic and populate VenEvent Entity.
		
		PojoGenerator pojoGenerator = new PojoGenerator();
		DistributeEvent distributeEvent = pojoGenerator.getDistributeEvent(oadrDistributeEventType);
		VenEvent Eventdetail =new VenEvent();
		List<EventResponse> eventResponseList = new ArrayList<EventResponse>();
		
		List<Event> events = distributeEvent.getEventList();
		if (events.size()>0){
		for (Event event : events) {
			Eventdetail.setEventID(event.getEventID());
			Eventdetail.setEventStatus(event.getEventStatus().value().toString());
			Eventdetail.setEventSignal(event.getSignals().get(0).getCurrentValue());
			Eventdetail.setVenid(event.getTarget().getVenIdList().get(0));
			Eventdetail.setCreationTime(event.getCreatedTime());
			EventResponse eventResponse = new EventResponse();
			eventResponse.setResponseCode("200");
			eventResponse.setRequestID(distributeEvent.getRequestId());
			eventResponse.setOptType(OptType.OPT_IN);
			eventResponse.setEventID(event.getEventID());
			eventResponseList.add(eventResponse);
			
		}
		
		CreatedEventRequest createdEventRequest= new CreatedEventRequest();
		createdEventRequest.setPushProfile(pollRequest.getPushProfile());
		CreatedEvent createdEvent = new CreatedEvent();
		createdEvent.setVenId(pollRequest.getPoll().getVenId());
		Response response = new Response();
		response.setResponseCode("200");
		createdEvent.setResponse(response);		
		createdEvent.setEventResponseList(eventResponseList);
		createdEventRequest.setCreatedEvent(createdEvent);
		venPullReqResManager.sendcreatedEvent(createdEventRequest);
		
		  }
		else
		{
			Eventdetail.setVenid(pollRequest.getPoll().getVenId());
		}
		
	
		VenManager.updateEventdetail(Eventdetail );
		
}

	private void handleRequest(OadrResponseType o) {
		   Devicecomupdate(o);
		
	}

	private void handleRequest(OadrRegisterReportType o) {
		// TODO Auto-generated method stub
		
	}



}
