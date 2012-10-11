package com.zzvc.mmps.scheduler.task.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;

import com.zzvc.mmps.scheduler.task.SchedulerTask;
import com.zzvc.mmps.task.Task;
import com.zzvc.mmps.task.TaskSupport;
import com.zzvc.mmps.task.utils.TaskUtils;

/**
 * Scheduler task manager
 * @author CHB
 *
 */
public class SchedulerTaskManager extends TaskSupport {
	private Logger logger = Logger.getLogger(SchedulerTaskManager.class);
	
	@Autowired(required=false)
	private List<SchedulerTask> schedulerTasks;

	private ResourceBundle bundle;
	
	private Map<Task, CronExpression> cronExpressions = new HashMap<Task, CronExpression>();

	public SchedulerTaskManager() {
		super();
		pushBundle("SchedulerTaskResources");
	}

	@Override
	public void prepareInit() {
		if (schedulerTasks == null) {
			schedulerTasks = new ArrayList<SchedulerTask>();
		}
	}

	@Override
	public void init() {
		try {
			bundle = ResourceBundle.getBundle("scheduler");
		} catch (MissingResourceException e) {
			return;
		}
		
		for (SchedulerTask task : schedulerTasks) {
			try {
				cronExpressions.put(task, new CronExpression(bundle.getString("scheduler.cron.expr." + task.getId())));
			} catch (Exception e) {
			}
		}
	}

	public void minutely() {
		boolean allTaskExecuteSuccess = true;
		for (SchedulerTask task : schedulerTasks) {
			if (TaskUtils.isTaskInited(task) && isOnSchedule(task)) {
				try {
					task.onSchedule();
				} catch (Exception e) {
					errorMessage("scheduler.task.manager.error.onschedule", task.getName());
					logger.error("Error executing scheduler task [" + task.getName() + "]", e);
					allTaskExecuteSuccess = false;
				}
			}
		}
		
		if (!allTaskExecuteSuccess) {
			statusMessage("scheduler.task.manager.status.errorexists", formatDateTimeFull(new Date()));
		}
	}
	
	private boolean isOnSchedule(Task task) {
		return !cronExpressions.containsKey(task) || cronExpressions.get(task).isSatisfiedBy(new Date());
	}

}
