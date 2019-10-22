package com.honeywell.ven.entities.report;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;

public interface ReportdetailEAO {
	
	
	@Remote
	public interface R extends ReportdetailEAO {}
	
	@Local
    public interface L extends ReportdetailEAO {}
	
	public void createReportdetail(List<Reportdetail> reportdetails);
	
	public void deleteReportdetail(Reportdetail reportdetail);
	
	public void updateReportdetail(Reportdetail reportdetail);
	
	public List<Reportdetail> getallReportDetails();
			
	public List<Reportdetail> getReportDetailsbyvenid(String venid);
	public List<Reportdetail> getReportDetailsbyspecifierid(String specifierid);
	
	
}
