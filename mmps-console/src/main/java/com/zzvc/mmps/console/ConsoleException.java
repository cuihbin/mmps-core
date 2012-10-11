package com.zzvc.mmps.console;

/**
 * Inform that a console not initialized successfully and should disabled
 * @author CHB
 *
 */
public class ConsoleException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ConsoleException() {
		super();
	}

	public ConsoleException(String message) {
		super(message);
	}
	
	public ConsoleException(Throwable cause) {
		super(cause);
	}
    
    public ConsoleException(String message, Throwable cause) {
    	super(message, cause);
    }
	
}
