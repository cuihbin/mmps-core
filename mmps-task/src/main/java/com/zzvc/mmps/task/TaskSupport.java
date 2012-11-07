package com.zzvc.mmps.task;

import com.zzvc.mmps.console.ConsoleHelper;

/**
 * Task's super class for convenience 
 * @author CHB
 *
 */
abstract public class TaskSupport extends ConsoleHelper implements Task {
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
	public void prepareInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterStartup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeShutdown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
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
