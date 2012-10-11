package com.zzvc.mmps.console;

/**
 * Console is a destination that application messages go. Delegated by ConsoleHelper application modules may output 
 * messages to multiple console instances without knowledge of each of them. 
 * @author CHB
 *
 */
public interface Console {
	void trace(String text);
	void info(String text);
	void warn(String text);
	void error(String text);
	void status(String text);
	
	void setConsoleTitle(String consoleTitle);
	
	void init();
	void destroy();
}
