package com.honeywell.pull.ven;


import java.io.Serializable;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.jboss.logging.Logger;

import com.honeywell.ven.api.fault.VenException;
import com.honeywell.ven.api.report.UpdateReportRequest;

/**
 *
 * 
 *
 */
@MessageDriven(name = "DeviceMeterDataQueue", activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/queue/deviceMeterDataQueue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "AUTO_ACKNOWLEDGE")
})

public class ReportSenderMDB implements MessageListener {

	private final Logger log = Logger.getLogger(ReportSenderMDB.class);
	
	
	@EJB
	private VenPullReqResManager.L venPullReqResManager;
	/**
	 * Prevent re-send at the JMS level. Transaction started in ReportSenderBean.
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public void onMessage(javax.jms.Message jmsMsg) {
		UpdateReportRequest message = extractMessage(jmsMsg);
		if (message != null) {
			try {
				venPullReqResManager.updateReport(message);
			} catch (VenException e) {
				// TODO Auto-generated catch block
				log.error("Exception in Sending Report Data");
			}
		}
	}

	/**
	 * Extract DRAS Message from JMS Message
	 * @return null if unsuccessful 
	 */
	private final UpdateReportRequest extractMessage(javax.jms.Message jmsMsg) {
		if (jmsMsg instanceof ObjectMessage) {
			final ObjectMessage objMsg = (ObjectMessage)jmsMsg;
			try {
				
				//Object theObj = objMsg.getObject();
				Serializable serializable = objMsg.getObject();
				if (serializable instanceof UpdateReportRequest) {
					return (UpdateReportRequest)serializable;
				} else {
					log.error("Expected JMS "+UpdateReportRequest.class.getName());
					return null;
				}
			} catch (JMSException e) {
				log.error("Error extracting JMS message", e);
				return null;
			}
		}
		log.error("Expected ObjectMessage JMS instance, but was "+jmsMsg.getClass().getName());
		return null;
	}

}


