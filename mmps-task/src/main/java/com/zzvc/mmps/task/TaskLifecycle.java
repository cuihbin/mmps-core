package com.zzvc.mmps.task;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

import com.zzvc.mmps.console.ConsoleMessageSupport;
import com.zzvc.mmps.console.localize.LocalizeUtil;
import com.zzvc.mmps.task.utils.TaskUtils;

/**
 * Managing task's lifecycle methods
 * 
 * @author cuihbin
 * 
 */
public class TaskLifecycle extends ConsoleMessageSupport implements InitializingBean, ApplicationListener<ApplicationContextEvent> {
	private static Logger logger = Logger.getLogger(TaskLifecycle.class);

	@Autowired(required = false)
	protected List<Task> tasks;

	public TaskLifecycle() {
		super();
		pushBundle("TaskResources");
	}

	@Override
	public String getConsolePrefix() {
		return "Task Lifecycle";
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		for (Task task : tasks) {
			try {
				task.prepareInit();
			} catch (TaskException e) {
				TaskUtils.markTaskFailed(task);
			} catch (Exception e) {
				TaskUtils.markTaskFailed(task);
				logger.error("Error prepare initializing task [" + task.getName() + "]", e);
				errorMessage("task.lifecycle.error.initializing.failed", task.getClass().getName());
			}
		}
	}

	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		if (event instanceof ContextRefreshedEvent) {
			onContextRefresh();
		} else if (event instanceof ContextClosedEvent) {
			onContextClose();
		}
	}

	public void init() {
		infoMessage("task.lifecycle.initializing");

		boolean inited;
		do {
			inited = false;
			for (Task task : tasks) {
				if (!(TaskUtils.isTaskFailed(task) || TaskUtils.isTaskInited(task) || task.isWaitingPrequisiteInit())) {
					try {
						task.init();
						TaskUtils.markTaskInited(task);
						infoMessage("task.lifecycle.initializing.success", task.getLabel());
						inited = true;
					} catch (TaskException e) {
						TaskUtils.markTaskFailed(task);
					} catch (Exception e) {
						TaskUtils.markTaskFailed(task);
						logger.error("Error initializing task [" + task.getName() + "]", e);
						errorMessage("task.lifecycle.error.initializing.failed", task.getLabel());
					}
				}
			}
		} while (inited);

		// In case some task's prerequisite condition never met
		for (Task task : tasks) {
			if (!TaskUtils.isTaskInited(task) && !TaskUtils.isTaskFailed(task)) {
				TaskUtils.markTaskFailed(task);
			}
		}

		updateInitStatus();
		infoMessage("task.lifecycle.initializing.done");
	}

	public void destroy() {
		for (Task task : tasks) {
			if (TaskUtils.isTaskInited(task)) {
				try {
					task.destroy();
				} catch (Exception e) {
					logger.error("Error destroying task [" + task.getName() + "]", e);
				}
			}
		}
	}

	private void updateInitStatus() {
		if (TaskUtils.isFailedTaskExists()) {
			statusMessage("task.lifecycle.status.initializing.success", LocalizeUtil.formatDateTimeFull(new Date()));
		} else {
			statusMessage("task.lifecycle.status.initializing.errorexists", LocalizeUtil.formatDateTimeFull(new Date()));
		}
	}

	private void onContextRefresh() {
		for (Task task : tasks) {
			if (TaskUtils.isTaskInited(task)) {
				try {
					task.afterStartup();
				} catch (Exception e) {
					warnMessage("task.lifecycle.warn.startup.exception", task.getName());
					logger.error("Error startup task [" + task.getName() + "]", e);
				}
			}
		}
	}

	private void onContextClose() {
		for (Task task : tasks) {
			if (TaskUtils.isTaskInited(task)) {
				try {
					task.beforeShutdown();
				} catch (Exception e) {
					warnMessage("task.lifecycle.warn.shutdown.exception", task.getName());
					logger.error("Error shutdown task [" + task.getName() + "]", e);
				}
			}
		}
	}

}
