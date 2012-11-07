package com.zzvc.mmps.console;

import java.util.Collection;

/**
 * Subject interface that serves the console observers
 * @author cuihbin
 *
 */
public interface ConsoleSubject extends ConsoleBehavior {
	void registerConsoles(Collection<ConsoleObserver> consoles);
}