package com.zzvc.mmps.scheduler.task;

import com.zzvc.mmps.task.Task;

/**
 * Handle scheduled task
 * @author CHB
 *
 */
public interface SchedulerTask extends Task {
	void onSchedule();
}
