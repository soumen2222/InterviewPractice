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
import com.honeywell.dras.scapi.messages.group.GroupServiceException;
import com.honeywell.dras.scapi.messages.program.ProgramServiceException;
import com.honeywell.ven.api.fault.VenException;
import com.honeywell.ven.api.poll.PollRequest;

/**
 *
 * 
 *
 */
@MessageDriven(name = "DevicePoll", activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/queue/devicePollQueue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "AUTO_ACKNOWLEDGE")
})

public class DevicePollMDB implements MessageListener {

	private final Logger log = Logger.getLogger(DevicePollMDB.class);
	
	@EJB
	private PollResponseProcessor.L pollResponseProcessor;
	@EJB
	private VenPullReqResManager.L venPullReqResManager;
	/**
	 * Prevent re-send at the JMS level. Transaction started in PollingSeqBean.
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public void onMessage(javax.jms.Message jmsMsg) {
		PollRequest message = extractMessage(jmsMsg);
		if (message != null) {
			try {
				Object response = venPullReqResManager.sendpoll(message);
				pollResponseProcessor.pollprocessor(response , message);
			} catch (VenException e) {
				// TODO Auto-generated catch block
				log.error("Exception in processing poll message");
			}
		}
	}

	/**
	 * Extract DRAS Message from JMS Message
	 * @return null if unsuccessful 
	 */
	private final PollRequest extractMessage(javax.jms.Message jmsMsg) {
		if (jmsMsg instanceof ObjectMessage) {
			final ObjectMessage objMsg = (ObjectMessage)jmsMsg;
			try {
				Serializable serializable = objMsg.getObject();
				if (serializable instanceof PollRequest) {
					return (PollRequest)serializable;
				} else {
					log.error("Expected JMS "+PollRequest.class.getName()+" instance, but was "+serializable.getClass().getName());
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


