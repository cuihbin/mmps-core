package com.zzvc.mmps.jms.task.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zzvc.mmps.jms.task.QueueMessageListenerTask;

/**
 * Queue message listener task manager
 * @author CHB
 *
 */
public class QueueMessageListenerTaskManager extends BaseMessageListenerTaskManager {
	@Autowired(required=false)
	protected List<QueueMessageListenerTask> listenerTasks;

	@Override
	protected List getListenerTasks() {
		return listenerTasks;
	}
	
}
