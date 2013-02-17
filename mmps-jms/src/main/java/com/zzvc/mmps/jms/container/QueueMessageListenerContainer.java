package com.zzvc.mmps.jms.container;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.MessageListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.zzvc.mmps.jms.task.QueueMessageListenerTask;

/**
 * Queue message listener thread. Define a bean of this class to enable queue message listening
 * @author CHB
 *
 */
public class QueueMessageListenerContainer extends BaseMessageListenerContainer {
	
	@Resource
	private Destination queueDestination;
	
	@Resource
	private MessageListener queueMessageListener;
	
	@Autowired(required=false)
	protected List<QueueMessageListenerTask> listenerTasks;

	@Override
	public void preInit() {
		if (listenerTasks == null) {
			listenerTasks = new ArrayList<QueueMessageListenerTask>();
		}
	}

	@Override
	protected Destination getDestination() {
		return queueDestination;
	}

	@Override
	protected String getDestinationType() {
		return "queue";
	}

	@Override
	protected MessageListener getMessageListener() {
		return queueMessageListener;
	}
	
	protected List getListenerTasks() {
		return listenerTasks;
	}

}
