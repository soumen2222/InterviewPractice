package com.honeywell.account.creater;


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

/**
 *
 * 
 *
 */
@MessageDriven(name = "AccountCreation", activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/queue/accountCreationQueue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "AUTO_ACKNOWLEDGE")
})

public class AccountCreaterMDB implements MessageListener {

	private final Logger log = Logger.getLogger(AccountCreaterMDB.class);

	@EJB
	private AccountManager.L  accountManager;
	
	/**
	 * Prevent re-send at the JMS level. Transaction started in dispatchManager.
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public void onMessage(javax.jms.Message jmsMsg) {
		ProgramPartyDTO message = extractMessage(jmsMsg);
		if (message != null) {
			
			try {
				accountManager.createCustomerInformation(message);
			} catch (GroupServiceException | ProgramServiceException e) {
				// TODO Auto-generated catch block
				log.error("JMS notification failed, message not delivered: "+message, e);
			}
			
		}
	}

	/**
	 * Extract DRAS Message from JMS Message
	 * @return null if unsuccessful 
	 */
	private final ProgramPartyDTO extractMessage(javax.jms.Message jmsMsg) {
		if (jmsMsg instanceof ObjectMessage) {
			final ObjectMessage objMsg = (ObjectMessage)jmsMsg;
			try {
				Serializable serializable = objMsg.getObject();
				if (serializable instanceof ProgramPartyDTO) {
					return (ProgramPartyDTO)serializable;
				} else {
					log.error("Expected JMS "+ProgramPartyDTO.class.getName()+" instance, but was "+serializable.getClass().getName());
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


