package com.zzvc.mmps.console;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Helper class to wire, initialize and destroy defined consoles.
 * @author cuihbin
 *
 */
public class ConsoleHelper {
	private Logger logger = Logger.getLogger(ConsoleHelper.class);
	
	@Autowired
	private Collection<ConsoleObserver> definedConsoles;
	
	private Collection<ConsoleObserver> effectiveConsoles;

	public void initConsoles() {
		effectiveConsoles = findNotInheritedConsoles(definedConsoles);
		
		for (ConsoleObserver console : effectiveConsoles) {
			try {
				console.init();
				ConsoleSubject.registerConsole(console);
			} catch (ConsoleException e) {
				logger.error("Console disabled due to init failure: " + console.getClass().getName(), e);
			}
		}
	}
	
	public void destroyConsoles() {
		for (ConsoleObserver console : effectiveConsoles) {
			try {
				ConsoleSubject.unregisterConsole(console);
				console.destroy();
			} catch (Exception e) {
				logger.error("Console destroy error: " + console.getClass().getName(), e);
			}
		}
	}
	
	private Collection<ConsoleObserver> findNotInheritedConsoles(Collection<ConsoleObserver> consoles) {
		List<ConsoleObserver> childConsoles = new ArrayList<ConsoleObserver>();
		
		outer:
		for (ConsoleObserver console : consoles) {
			for (int i = 0; i < childConsoles.size(); i++) {
				if (isParentOf(console, childConsoles.get(i))) {
					continue outer;
				} else if (isParentOf(childConsoles.get(i), console)) {
					childConsoles.remove(i);
					break;
				}
			}
			childConsoles.add(console);
		}
		
		return childConsoles;
	}
	
	private boolean isParentOf(Object parent, Object child) {
		return parent.getClass().isInstance(child) && !child.getClass().isInstance(parent);
	}
}
