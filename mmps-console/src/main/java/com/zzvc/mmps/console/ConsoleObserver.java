package com.zzvc.mmps.console;

/**
 * Observer interface of consoles. 
 * @author cuihbin
 *
 */
public interface ConsoleObserver extends ConsoleBehavior {
	void init();
	void destroy();
}
