package com.zzvc.mmps.console;

/**
 * Define behaviors of a console 
 * @author cuihbin
 *
 */
public interface ConsoleBehavior {
	void trace(String text);
	void info(String text);
	void warn(String text);
	void error(String text);
	void status(String text);
	
	void setConsoleTitle(String consoleTitle);
}
