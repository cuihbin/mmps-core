package com.zzvc.mmps.gui.file;

/**
 * A file chooser error like opening an unexists file
 * @author CHB
 *
 */
public class FileException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public FileException() {
		super();
	}

	public FileException(String message) {
		super(message);
	}
	
	public FileException(Throwable cause) {
		super(cause);
	}
    
    public FileException(String message, Throwable cause) {
    	super(message, cause);
    }
	
}
