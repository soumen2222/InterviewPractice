package com.honeywell.ven.entities.report;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;





@Stateless
public class ReportdetailEAOBean implements ReportdetailEAO.L, ReportdetailEAO.R {
//public class PartyEAOBean extends BaseEAOBean<Party> implements PartyEAO.R, PartyEAO.L {
	
	/** The Entity Manager. */
	@PersistenceContext(unitName="venSimulator")
	protected EntityManager em;
	
	public ReportdetailEAOBean() {
		super();
		}
	
	public EntityManager getEm() {
		return em;
	}
	

	@Override
	public void deleteReportdetail(Reportdetail reportdetail) {
		// TODO Auto-generated method stub
		getEm().remove(reportdetail);
	}

	@Override
	public void updateReportdetail(Reportdetail reportdetail) {
		// TODO Auto-generated method stub
		getEm().merge(reportdetail);
	}

	@Override
	public List<Reportdetail> getallReportDetails() {
		// TODO Auto-generated method stub

		 Query q = getEm().createNamedQuery("Reportdetail.findAll");
			
			@SuppressWarnings("unchecked")
			final List<Reportdetail> results = q.getResultList();
			return results;
	}

	@Override
	public List<Reportdetail>  getReportDetailsbyvenid(String venid) {
		// TODO Auto-generated method stub
		Query q = getEm().createNamedQuery("Reportdetail.findByvenid").setParameter("Venid", venid);
		
		@SuppressWarnings("unchecked")
		final List<Reportdetail> results = q.getResultList();
		return results;
	}

	@Override
	public void createReportdetail(List<Reportdetail> reportdetails) {
	
		for (Reportdetail reportdetail : reportdetails) {
			
			getEm().persist(reportdetail);	
		}	
			
		
	}

	@Override
	public List<Reportdetail> getReportDetailsbyspecifierid(String specifierid) {
		// TODO Auto-generated method stub
      Query q = getEm().createNamedQuery("Reportdetail.findByReportSpecifierId").setParameter("ReportSpecifierId", specifierid);
		
		@SuppressWarnings("unchecked")
		final List<Reportdetail> results = q.getResultList();
		return results;
	}

	
}
	
	
	
