package com.honeywell.pull.ven;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;

import org.jboss.logging.Logger;

import com.honeywell.ven.api.fault.VenEventNoResultException;
import com.honeywell.ven.api.fault.VenException;
import com.honeywell.ven.entities.event.VenEvent;
import com.honeywell.ven.entities.event.VenEventEAO;
import com.honeywell.ven.entities.registration.Registration;
import com.honeywell.ven.entities.registration.RegistrationEAO;
import com.honeywell.ven.entities.report.Reportdetail;
import com.honeywell.ven.entities.report.ReportdetailEAO;
import com.honeywell.ven.services.messages.TotalVenDataResponse;

@Stateless
public class VenManagerBean implements VenManager.L , VenManager.R {
	
	private Logger log = Logger.getLogger(VenManagerBean.class);
	
	@EJB
	private RegistrationEAO.L registrationEAO;
	
	@EJB
	private VenEventEAO.L eventEAO;
	
	@EJB
	private ReportdetailEAO.L reportdetailEAO;

	@Override
	public void updateEventdetail(VenEvent Eventdetail)  {
		// TODO Auto-generated method stub
		
		VenEvent Vendetails = null;
		Vendetails = getEventDetailsbyvenid(Eventdetail.getVenid());
        if ((Eventdetail.getEventID()==null) && (Vendetails!=null)){
			
			eventEAO.deleteEventdetail(Vendetails);
		}
		else if((Eventdetail.getEventID()!=null) && (Vendetails!=null))
		{
			if (Eventdetail.getEventID()!=Vendetails.getEventID()){
				Vendetails.setEventID(Eventdetail.getEventID());
				Vendetails.setEventSignal(Eventdetail.getEventSignal());
				Vendetails.setEventStatus(Eventdetail.getEventStatus());
				Vendetails.setCreationTime(Eventdetail.getCreationTime());
			}
			
			eventEAO.updateEventdetail(Vendetails);
		}
				
		else if((Eventdetail.getEventID()!=null))
		{
			eventEAO.createEventdetail(Eventdetail);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public VenEvent getEventDetailsbyvenid(String Venid) {
		// TODO Auto-generated method stub
		
		try {
			return eventEAO.getEventDetailsbyvenid(Venid);
		} catch (VenEventNoResultException e) {
			log.error("No Ven found for the given venid");
		}catch(Exception e){
			log.error("Exception in !!!!"+e);
		}
		return null;
		
	}

	@Override
	public List<VenEvent> getallEventDetails() {
		// TODO Auto-generated method stub
		return eventEAO.getallEventDetails();
	}

	@Override
	public void createRegistration(Registration Registrationdetail) {
		// TODO Auto-generated method stub
		registrationEAO.createRegistration(Registrationdetail);
	}

	@Override
	public List<Registration> getallRegistrationdetails() {
		// TODO Auto-generated method stub
		return registrationEAO.getallRegistrationdetails();
	}

	@Override
	public Registration getRegistrationdetailssbyvenid(String venid) {
		// TODO Auto-generated method stub
		return registrationEAO.getRegistrationdetailssbyvenid(venid);
	}

	@Override
	public void createReportdetail(List<Reportdetail> reportdetails) {
		// TODO Auto-generated method stub
		reportdetailEAO.createReportdetail(reportdetails);
		
	}

	@Override
	public TotalVenDataResponse getallVendetails() {
		// TODO Auto-generated method stub
		return registrationEAO.getallVendetails();
	}

	@Override
	public void updateRegistration(Registration Registrationdetail) {
		// TODO Auto-generated method stub
		registrationEAO.updateRegistration(Registrationdetail);
		
	}

}
