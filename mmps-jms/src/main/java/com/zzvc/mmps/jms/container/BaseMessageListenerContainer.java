package com.zzvc.mmps.jms.container;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageListener;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.zzvc.mmps.task.TaskSupport;

/**
 * Base message listener thread
 * @author CHB
 *
 */
public abstract class BaseMessageListenerContainer extends TaskSupport implements Runnable {
	@Resource
	private ActiveMQConnectionFactory amqConnectionFactory;
	
	@Resource
	private ConnectionFactory connectionFactory;
	
	private DefaultMessageListenerContainer container;
	
	volatile boolean brokerStartedSuccess = false;
	
	abstract protected Destination getDestination();
	
	abstract protected String getDestinationType();
	
	abstract protected MessageListener getMessageListener();

	public BaseMessageListenerContainer() {
		super();
		pushBundle("JmsResources");
	}

	@Override
	public String getId() {
		return "jms.task.listener." + getDestinationType();
	}
	
	@Override
	public String getLabel() {
		return findText("task.label.jms.task.listener", getDestinationName());
	}
	
	@Override
	public void postInit() {
		new Thread(this).start();
	}

	@Override
	public void preDestroy() {
		if (brokerStartedSuccess) {
			container.stop();
			container.destroy();
		}
	}

	@Override
	public void run() {
		infoMessage("jms.listener.starting", getDestinationName());
		
		// Try to connection to the broker first to avoid starting DefaultMessageListenerContainer w/o
		// a running broker cause the application not able to exit gracefully
		testStartupConnection();
		
		container = new DefaultMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setDestination(getDestination());
		container.setMessageListener(getMessageListener());
		container.setSessionTransacted(true);
		
		container.afterPropertiesSet();
		container.start();
		
		brokerStartedSuccess = true;
		infoMessage("jms.listener.starting.done", getDestinationName());
	}
	
	private String getDestinationName() {
		return findText("jms.destination." + getDestinationType());
	}
	
	private void testStartupConnection() {
		Connection testConnection = null;
		try {
			testConnection = amqConnectionFactory.createConnection();
			testConnection.start();
			testConnection.stop();
			testConnection.close();
		} catch (JMSException e) {
		}
	}

}
