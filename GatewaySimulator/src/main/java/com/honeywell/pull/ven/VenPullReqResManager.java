package com.honeywell.pull.ven;


import javax.ejb.Local;
import javax.ejb.Remote;

import com.honeywell.ven.api.common.Response;
import com.honeywell.ven.api.event.CreatedEventRequest;
import com.honeywell.ven.api.fault.VenException;
import com.honeywell.ven.api.poll.PollRequest;
import com.honeywell.ven.api.registration.CreatePartyRegistrationRequest;
import com.honeywell.ven.api.registration.CreatedPartyRegistration;
import com.honeywell.ven.api.report.CreatedReportRequest;
import com.honeywell.ven.api.report.RegisterReportRequest;
import com.honeywell.ven.api.report.RegisteredReport;
import com.honeywell.ven.api.report.RegisteredReportRequest;
import com.honeywell.ven.api.report.UpdateReportRequest;
import com.honeywell.ven.api.report.UpdatedReport;


public interface VenPullReqResManager  {
	@Remote
    public interface R extends VenPullReqResManager {}
    @Local
    public interface L extends VenPullReqResManager {}
	
    public RegisteredReport sendregisterReport(RegisterReportRequest registerReportRequest)
			throws VenException ;
    
   
    public Response sendregisteredReport(RegisteredReportRequest registeredReportRequest)
			throws VenException ;
    
    public Object sendpoll(PollRequest pollRequest) throws VenException;
    public CreatedPartyRegistration sendCreatePartyRegistration (CreatePartyRegistrationRequest createPartyRegistrationRequest)
			throws VenException;
	public UpdatedReport updateReport(UpdateReportRequest updateReportRequest)
			throws VenException;
	

	public Response sendcreatedReport(CreatedReportRequest createdReportRequest)
			throws VenException;
	
	public Response sendcreatedEvent(CreatedEventRequest createdEventRequest) throws VenException;
	
	
	
}
