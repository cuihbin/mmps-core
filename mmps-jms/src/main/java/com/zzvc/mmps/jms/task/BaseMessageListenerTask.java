package com.zzvc.mmps.jms.task;

import com.zzvc.mmps.task.Task;

/**
 * Base Message Listener Task
 * @author CHB
 *
 */
public interface BaseMessageListenerTask extends Task {
	void handleMessage(Object message);
}
