package com.honeywell.pull.ven;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import org.jboss.logging.Logger;
import com.honeywell.ven.api.poll.PollRequest;


/**
 * Main entry point to Account Creation system. Puts Message on JMS queue.
 * 
 * 
 */

@Stateless
public class DevicePollQueueBean implements DevicePollQueue.L,DevicePollQueue.R {

	@Resource(mappedName = "java:/JmsXA")
    private ConnectionFactory connectionFactory;
		
	private Long timeToLive;

	public static final long DEFAULT_TIME_TO_LIVE = 2 * 60 * 60 * 1000; //2 hours 
			
    @Resource(mappedName = "java:/queue/devicePollQueue")
    private Queue DevicePollQueue;

    private Logger log = Logger.getLogger(DevicePollQueueBean.class);
    
    
    @Override
	public void sendMessage(PollRequest message) {
        Connection connection = null;
        Session session = null;
         try {
            if (log.isDebugEnabled()) log.debug("putting message on queue: "+message); 
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            final MessageProducer publisher = session.createProducer(DevicePollQueue);
            final javax.jms.Message jmsMessage = createJmsMessage(session, message);
            publisher.send(jmsMessage);
            if (log.isDebugEnabled()) log.debug("message in queue: "+message);
        } catch (final JMSException exc) {
            log.error("error sending message", exc);
        } finally {
            try {
                if (null != session)
                    session.close();
            } catch (JMSException exc) {
                log.warn("error closing session", exc);
            }
            try {
                if (null != connection)
                    connection.close();
            } catch (JMSException exc) {
                log.warn("error closing session", exc);
            }
        }
	}
 

	private final javax.jms.Message createJmsMessage(Session session, PollRequest message) throws JMSException
	{
        final ObjectMessage jmsMsg = session.createObjectMessage(message);
        return jmsMsg;
	}
  
	protected long timeToLive(PollRequest message) {
		return timeToLive == null ? DEFAULT_TIME_TO_LIVE : timeToLive.longValue();
	}

}
