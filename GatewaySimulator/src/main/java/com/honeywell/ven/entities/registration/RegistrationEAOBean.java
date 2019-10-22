package com.honeywell.ven.entities.registration;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.honeywell.dras.common.ejb.EntityNotFoundException;
import com.honeywell.dras.vtn.api.CommsStatusType;
import com.honeywell.ven.services.messages.TotalVenDataResponse;
import com.honeywell.ven.services.messages.VenData;


@Stateless
public class RegistrationEAOBean implements RegistrationEAO.L, RegistrationEAO.R {
//public class PartyEAOBean extends BaseEAOBean<Party> implements PartyEAO.R, PartyEAO.L {
	
	/** The Entity Manager. */
	@PersistenceContext(unitName="venSimulator")
	protected EntityManager em;
	
	public RegistrationEAOBean() {
		super();
		}
	
	public EntityManager getEm() {
		return em;
	}
	@Override
	public void createRegistration(Registration Registrationdetail) {
		// TODO Auto-generated method stub
		getEm().persist(Registrationdetail);
	}

	@Override
	public void deleteRegistration(Registration Registrationdetail) {
		// TODO Auto-generated method stub
		getEm().remove(Registrationdetail);
	}

	@Override
	public void updateRegistration(Registration Registrationdetail) {
		// TODO Auto-generated method stub
		getEm().merge(Registrationdetail);
	}

	@Override
	public List<Registration> getallRegistrationdetails() {
		// TODO Auto-generated method stub

		 Query q = getEm().createNamedQuery("Registration.findAll");
			
			@SuppressWarnings("unchecked")
			final List<Registration> results = q.getResultList();
			return results;
	}

	@Override
	public Registration getRegistrationdetailssbyvenid(String venid) {
		// TODO Auto-generated method stub
		Query q = getEm().createNamedQuery("Registration.findByvenid").setParameter("Venid", venid);
		
		Registration AccDt;
        try {
        	AccDt = (Registration) q.getSingleResult();
        } catch (NoResultException nre) {
        	AccDt = null;
        }
        
		return AccDt;
	}
   
	public TotalVenDataResponse getallVendetails() {
		
		Query q = getGroupQuery();
		
		@SuppressWarnings("unchecked")
		List<Object[]> obj = q.getResultList();
		
		List<VenData> veninEvents = new ArrayList<VenData>();
		List<VenData> vensData = new ArrayList<VenData>();
		TotalVenDataResponse totalVenDataResponse = new TotalVenDataResponse();
		
		//List of devices which are running event
		for (Object[] o : obj) {
			veninEvents.add(readValues(o));
		}
		
		//List all the Vens
		List<Registration> regs = new ArrayList<Registration> ();
		regs= getallRegistrationdetails();
		int vensexecutingevent =0; //Get the total number of vens executing event
		int totalvens =0; 
		for (Registration registration : regs) {
			
			VenData venData = new VenData();
			venData.setDRASVTNUrl(registration.getVtnurl());
			venData.setVenName(registration.getVenName());
			venData.setVenID(registration.getVenid());
			venData.setStatus(registration.getVenComStatus());
			
			for (VenData Eventvens : veninEvents) {
			
				if ((Eventvens.getVenID()).equals(registration.getVenid())){
					
					venData.setEventStatus(Eventvens.getEventStatus());
					venData.setEventID(Eventvens.getEventID());
					venData.setEventSignal(Eventvens.getEventSignal());
					vensexecutingevent++;
				}
				
			}
			totalvens++;
			
			vensData.add(venData);
		}
		
		int executingvenperc;
		if (totalvens>0){
		  executingvenperc = vensexecutingevent*100/totalvens;
		}
		else
			executingvenperc =0;
		totalVenDataResponse.setVenData(vensData);
		totalVenDataResponse.setNoOfResources(regs.size());
		totalVenDataResponse.setExecutingPercentage(executingvenperc);
		totalVenDataResponse.setOnlinePercentage(onlinevensperc(getallRegistrationdetails()));
		return totalVenDataResponse ;
		
	}

	private Query getGroupQuery() {
		StringBuilder sb = new StringBuilder();
		
		//select r.venname as VenName, r.venid as VenID, ve.eventstatus as EventStatus,ve.eventid as EventId from registration r JOIN venevent ve ON 
		//ve.venid = r.venid
				
			sb.append("select r.venname as VenName, r.venid as VenID, ");
			sb.append("ve.eventstatus as EventStatus, ve.eventid as EventId, ");
			sb.append("ve.eventsignal as EventSignal,  ");
			sb.append("r.vtnurl as VTNUrl  ");
			sb.append("from registration r ");
			sb.append("JOIN ");
		    sb.append("venevent ve ON ");
		    sb.append("ve.venid = r.venid");
				
		return getEm().createNativeQuery(sb.toString());
	}
	
	
	private VenData readValues(Object[] o) {
		VenData venData = new VenData();
		if (o == null || o[0] == null) {
			venData.setVenName("");
		} else {
			venData.setVenName(((String)o[0]));
		}
		
		if (o == null || o[1] == null) {
			venData.setVenID("");
		} else {
			venData.setVenID(((String)o[1]));
		}
		
		if (o == null || o[2] == null) {
			venData.setEventStatus("");
		} else {
			venData.setEventStatus(((String)o[2]));
		}
		
		if (o == null || o[3] == null) {
			venData.setEventID("");
		} else {
			venData.setEventID(((String)o[3]));
		}
		
		if (o == null || o[4] == null) {
			//venData.setEventSignal(0);
		} else {
			venData.setEventSignal(((Number)o[4]).floatValue());
		}
		
		if (o == null || o[5] == null) {
			venData.setDRASVTNUrl("");
		} else {
			venData.setDRASVTNUrl(((String)o[5]));
		}
			
		return venData;
	}
	
	
	private int onlinevensperc(List<Registration> registrations){
		
		int onlinevencount = 0;
		int totalven =0;
		
		
		for (Registration registration : registrations) {
			
			if (CommsStatusType.valueOf(registration.getVenComStatus()).equals(CommsStatusType.valueOf("ONLINE"))){
			  onlinevencount++;
			}
			totalven++;
		}
		
		if (totalven>0){
			return (onlinevencount*100/totalven); 
		}
		else
			return 0;
		
	}
}
	
	
	
