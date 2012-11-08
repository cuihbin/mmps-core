package com.zzvc.mmps.console;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Subject interface that serves the console observers
 * @author cuihbin
 *
 */
abstract public class ConsoleSubject implements ConsoleBehavior {
	private static Collection<ConsoleObserver> consoles = Collections.synchronizedCollection(new HashSet<ConsoleObserver>());
	
	public static void registerConsole(ConsoleObserver console) {
		consoles.add(console);
	}
	
	public static void unregisterConsole(ConsoleObserver console) {
		consoles.remove(console);
	}

	@Override
	public void setConsoleTitle(String consoleTitle) {
		for (ConsoleObserver console : consoles) {
			console.setConsoleTitle(consoleTitle);
		}
	}

	@Override
	public void trace(String text) {
		for (ConsoleObserver console : consoles) {
			console.trace(text);
		}
	}

	@Override
	public void info(String text) {
		for (ConsoleObserver console : consoles) {
			console.info(text);
		}
	}

	@Override
	public void warn(String text) {
		for (ConsoleObserver console : consoles) {
			console.warn(text);
		}
	}

	@Override
	public void error(String text) {
		for (ConsoleObserver console : consoles) {
			console.error(text);
		}
	}

	@Override
	public void status(String text) {
		for (ConsoleObserver console : consoles) {
			console.status(text);
		}
	}
}