package com.zzvc.mmps.task;

import com.zzvc.mmps.console.ConsoleMessageSupport;

/**
 * Task's super class for convenience 
 * @author CHB
 *
 */
abstract public class TaskSupport extends ConsoleMessageSupport implements Task {
	private String id;
	private String name;
	private String label;

	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getLabel() {
		if (label == null) {
			label = findText("task.label." + getId());
		}
		return label;
	}

	@Override
	public boolean isWaitingPrequisiteInit() {
		return name == null;
	}

	@Override
	public void preInit() {
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
	}

	@Override
	public void preDestroy() {
	}

	@Override
	public void destroy() {
	}

	@Override
	public String getConsolePrefix() {
		return getName();
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
