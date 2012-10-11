package com.zzvc.mmps.task.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.zzvc.mmps.task.Task;

/**
 * Utility to save/get status of tasks
 * @author CHB
 *
 */
public class TaskUtils {

	private static final Set<Task> failedTasks = Collections.synchronizedSet(new HashSet<Task>());

	private static final Set<Task> initedTasks = Collections.synchronizedSet(new HashSet<Task>());

	public static boolean isTaskFailed(Task task) {
		return failedTasks.contains(task);
	}

	public static boolean isTaskInited(Task task) {
		return initedTasks.contains(task);
	}

	public static void markTaskFailed(Task task) {
		initedTasks.remove(task);
		failedTasks.add(task);
	}

	public static void markTaskInited(Task task) {
		initedTasks.add(task);
	}

	public static boolean isFailedTaskExists() {
		return !failedTasks.isEmpty();
	}

}
