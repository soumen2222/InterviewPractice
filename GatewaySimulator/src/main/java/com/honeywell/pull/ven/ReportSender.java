package com.honeywell.pull.ven;

import javax.ejb.Local;
import javax.ejb.Remote;

public interface ReportSender {
	
	@Remote
    public interface R extends ReportSender {}
    @Local
    public interface L extends ReportSender {}
    
    public void createReportPushTimeoutHandler();

}
