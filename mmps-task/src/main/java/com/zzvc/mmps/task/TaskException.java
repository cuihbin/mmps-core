package com.zzvc.mmps.task;

/**
 * Notify that in task execution an error happens and is properly handled (logged, message displayed, etc.)
 * Task manager will do no more logging or messaging on this Exception. 
 * @author CHB
 *
 */
public class TaskException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public TaskException() {
		super();
	}

	public TaskException(String message) {
		super(message);
	}
	
	public TaskException(Throwable cause) {
		super(cause);
	}
    
    public TaskException(String message, Throwable cause) {
    	super(message, cause);
    }
	
}
