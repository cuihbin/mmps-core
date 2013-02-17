package com.zzvc.mmps.jms.container;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.MessageListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.zzvc.mmps.jms.task.TopicMessageListenerTask;

/**
 * Topic message listener thread. Define a bean of this class to enable topic message listening
 * @author CHB
 *
 */
public class TopicMessageListenerContainer extends BaseMessageListenerContainer {
	
	@Resource
	private Destination topicDestination;
	
	@Resource
	private MessageListener topicMessageListener;

	@Autowired(required=false)
	protected List<TopicMessageListenerTask> listenerTasks;

	@Override
	public void preInit() {
		if (listenerTasks == null) {
			listenerTasks = new ArrayList<TopicMessageListenerTask>();
		}
	}

	@Override
	protected Destination getDestination() {
		return topicDestination;
	}

	@Override
	protected String getDestinationType() {
		return "topic";
	}

	@Override
	protected MessageListener getMessageListener() {
		return topicMessageListener;
	}
	
	protected List getListenerTasks() {
		return listenerTasks;
	}

}
