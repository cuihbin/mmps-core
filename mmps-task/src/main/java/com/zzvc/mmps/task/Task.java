package com.zzvc.mmps.task;

/**
 * Task is the basic working unit of an application. It provides task property management and life cycle event call backs.
 * @author CHB
 *
 */
public interface Task {
	/**
	 * Task's unique identifier for locating specific resources
	 * @return Task's Id
	 */
	String getId();
	
	/**
	 * Task's concise descriptive name. For identifying source of the message in logs.
	 * @return Task's Name
	 */
	String getName();
	
	/**
	 * Task's full-length label with i18n support. Used in UI message for better readability
	 * @return Task's Label
	 */
	String getLabel();
	
	/**
	 * If task is ready for initializing. Return true if task's prerequisite condition not met yet.
	 * This property is useful in adjusting tasks' calling order of init() call back
	 * @return If task is ready for initializing
	 */
	boolean isWaitingPrequisiteInit();
	
	/**
	 * Task's preparation process before initializing. The calling order among tasks is random. 
	 * Avoid message output to consoles in this call back as they are not ready yet. 
	 * Task marked as failed if exception thrown.
	 */
	void prepareInit();

	/**
	 * Initializing task. Task's isWaitingPrequisiteInit() must be false or the initializing will be postponed.
	 * Task marked as failed if exception thrown or never get initialized
	 */
	void init();
	
	/**
	 * Called as soon as all task finished initializing.
	 */
	void afterStartup();
	
	/**
	 * Called before shutdown but all underlying services like console are still available
	 */
	void beforeShutdown();
	
	/**
	 * Callback on destroy
	 */
	void destroy();
}
