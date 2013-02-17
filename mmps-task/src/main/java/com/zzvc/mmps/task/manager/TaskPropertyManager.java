package com.zzvc.mmps.task.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.zzvc.mmps.task.Task;
import com.zzvc.mmps.task.TaskException;
import com.zzvc.mmps.task.TaskSupport;
import com.zzvc.mmps.task.utils.TaskUtils;

/**
 * Manager to check validity of task id, name and label
 * @author CHB
 *
 */
public class TaskPropertyManager extends TaskSupport {
	private static Logger logger = Logger.getLogger(TaskPropertyManager.class);

	@Autowired(required = false)
	protected List<Task> tasks;

	private Set<String> taskIds = new HashSet<String>();

	private Set<String> taskNames = new HashSet<String>();

	public TaskPropertyManager() {
		super();
		pushBundle("TaskResources");
	}

	@Override
	public String getConsolePrefix() {
		return "Task Manager";
	}

	@Override
	public void preInit() {
		if (tasks == null) {
			tasks = new ArrayList<Task>();
		}
	}

	@Override
	public boolean isWaitingPrequisiteInit() {
		return false;
	}

	@Override
	public void init() {
		for (Task task : tasks) {
			try {
				checkTaskId(task);
				checkTaskName(task);
				checkTaskLabel(task);
			} catch (TaskException e) {
				TaskUtils.markTaskFailed(task);
			} catch (Exception e) {
				logger.error("Error prepare initializing task [" + task.getName() + "]", e);
				TaskUtils.markTaskFailed(task);
				errorMessage("task.manager.error.prepare.failed", task.getName());
			}
		}
	}

	private void checkTaskId(Task task) {
		String taskId = task.getId();
		if (!StringUtils.hasText(taskId) || !taskId.matches("[a-z0-9\\.]*")) {
			errorMessage("task.manager.error.id.invalid", task.getClass().getName(), taskId);
			throw new TaskException("Task id invalid");
		}

		if (taskIds.contains(taskId)) {
			errorMessage("task.manager.error.id.duplicated", task.getClass().getName(), taskId);
			throw new TaskException("Task id duplicated");
		}
		taskIds.add(taskId);
	}

	private void checkTaskName(Task task) {
		String taskName = task.getName();
		if (!StringUtils.hasText(taskName)) {
			if (task instanceof TaskSupport) {
				taskName = capitalize(task.getId().replace('.', ' '));
				((TaskSupport) task).setName(taskName);
			} else {
				errorMessage("task.manager.error.name.invalid", task.getClass().getName(), taskName);
				throw new TaskException("Task name invalid");
			}
		}

		if (taskNames.contains(taskName)) {
			errorMessage("task.manager.error.name.duplicated", task.getClass().getName(), taskName);
			throw new TaskException("Task name duplicated");
		}

		taskNames.add(taskName);
	}

	private String capitalize(String s) {
		char[] ca = s.toCharArray();
		boolean firstCharOfWord = true;
		for (int i = 0; i < ca.length; i++) {
			if (ca[i] == ' ') {
				firstCharOfWord = true;
			} else if (Character.isLetter(ca[i])) {
				if (firstCharOfWord) {
					ca[i] = Character.toUpperCase(ca[i]);
				} else {
					ca[i] = Character.toLowerCase(ca[i]);
				}
				firstCharOfWord = false;
			}
		}
		return new String(ca);
	}

	private void checkTaskLabel(Task task) {
		try {
			task.getLabel();
		} catch (MissingResourceException e) {
			errorMessage("task.manager.error.label.missing", task.getClass().getName());
			throw new TaskException("Task label not defined");
		}
	}
}
