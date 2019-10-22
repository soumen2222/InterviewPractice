package com.honeywell.ven.entities.event;


import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;

import com.honeywell.dras.common.ejb.DuplicateKeyException;
import com.honeywell.dras.common.ejb.EntityNotFoundException;
import com.honeywell.ven.api.fault.VenEventNoResultException;





@Stateless
public class VenEventEAOBean implements VenEventEAO.L, VenEventEAO.R {
//public class PartyEAOBean extends BaseEAOBean<Party> implements PartyEAO.R, PartyEAO.L {
	
	private Logger log = Logger.getLogger(VenEventEAOBean.class);
	/** The Entity Manager. */
	@PersistenceContext(unitName="venSimulator")
	protected EntityManager em;
	
	public VenEventEAOBean() {
		super();
		}
	
	public EntityManager getEm() {
		return em;
	}
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void createEventdetail(VenEvent Eventdetail) throws ConstraintViolationException {
		// TODO Auto-generated method stub
		
		try{
			if(getEm()!=null){			
				getEm().persist(Eventdetail);
			}else{
				log.error("Entity manager is null");
			}
		}catch(ConstraintViolationException e){
			log.error("Constraint violation exception in storing Eventdetail!! "+e);
		}catch(Exception ex){
			log.error("Exception in persisting the Eventdetail entity"+ ex);
		}
		
	}

	@Override
	public void deleteEventdetail(VenEvent Eventdetail) {
		// TODO Auto-generated method stub
		getEm().remove(Eventdetail);
	}

	@Override
	public void updateEventdetail(VenEvent Eventdetail) {
		// TODO Auto-generated method stub
		getEm().merge(Eventdetail);
	}

	@Override
	public List<VenEvent> getallEventDetails() {
		// TODO Auto-generated method stub

		 Query q = getEm().createNamedQuery("VenEvent.findAll");
			
			@SuppressWarnings("unchecked")
			final List<VenEvent> results = q.getResultList();
			return results;
	}

	@Override
	public VenEvent getEventDetailsbyvenid(String VenID) throws VenEventNoResultException {
		// TODO Auto-generated method stub
		VenEvent venEvent = null;
		try{
		Query q = getEm().createNamedQuery("VenEvent.findByvenid").setParameter("Venid", VenID);
		venEvent =(VenEvent) q.getSingleResult();
		}catch (NoResultException e){
			//rethrowing the exception as a custom exception because if we throw an ejb exception it will roll back the transaction
			//in ejb container
			throw new VenEventNoResultException("No Ven Report found for the given venid");
		}
		return venEvent;
			
	}

	
}
	
	
	
