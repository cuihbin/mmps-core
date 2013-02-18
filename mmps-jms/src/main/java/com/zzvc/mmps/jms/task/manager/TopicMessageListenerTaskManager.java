package com.zzvc.mmps.jms.task.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zzvc.mmps.jms.task.TopicMessageListenerTask;

/**
 * Topic message listener task manager
 * @author CHB
 *
 */
public class TopicMessageListenerTaskManager extends BaseMessageListenerTaskManager {
	@Autowired(required=false)
	protected List<TopicMessageListenerTask> listenerTasks;

	@Override
	protected List getListenerTasks() {
		return listenerTasks;
	}
	
}
