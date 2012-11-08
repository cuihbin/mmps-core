package com.zzvc.mmps.jms.task.manager;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.zzvc.mmps.console.ConsoleHelper;
import com.zzvc.mmps.console.localize.LocalizeUtil;
import com.zzvc.mmps.jms.task.BaseMessageListenerTask;
import com.zzvc.mmps.task.utils.TaskUtils;

/**
 * Base message listener task manager
 * @author cuihbin
 *
 */
abstract public class BaseMessageListenerTaskManager extends ConsoleHelper {
	private Logger logger = Logger.getLogger(BaseMessageListenerTaskManager.class);
	
	abstract protected List<BaseMessageListenerTask> getListenerTasks();

	public BaseMessageListenerTaskManager() {
		super();
		pushBundle("JmsResources");
	}

	@Override
	public String getConsolePrefix() {
		return "Message Listener Task Manager";
	}

	public void handleMessage(Object message) {
		boolean allTaskExecuteSuccess = true;
		if (getListenerTasks() != null) {
			for (BaseMessageListenerTask task : getListenerTasks()) {
				if (TaskUtils.isTaskInited(task)) {
					try {
						task.handleMessage(message);
					} catch (Exception e) {
						errorMessage("jms.task.manager.error.onmessage", task.getName());
						logger.error("Error executing JMS task [" + task.getName() + "]", e);
						allTaskExecuteSuccess = false;
					}
				}
			}
		}
		
		if (!allTaskExecuteSuccess) {
			statusMessage("jms.task.manager.status.errorexists", LocalizeUtil.formatDateTimeFull(new Date()));
		}
	}

}
