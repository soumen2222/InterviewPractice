package com.honeywell.payloads.util;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.jboss.logging.Logger;

import com.honeywell.pull.ven.PollingSeq;
import com.honeywell.pull.ven.ReportSender;

@Singleton
@Startup
public class Rigger {
	
	@EJB
	private PollingSeq.L pollingSeq;
	
	@EJB
	private ReportSender.L reportSender;
	
	private static Logger log = Logger.getLogger(Rigger.class);
	

	@PostConstruct
	public void atStartup() {
		log.info("Starting Emulator...");
				
		pollingSeq.createpollTimeoutHandler();
		reportSender.createReportPushTimeoutHandler();

			
		
	}
	
	
}
