package com.zzvc.mmps.console;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.zzvc.mmps.console.localize.LocalizeUtil;

/**
 * Extend this class to convenient console message output.
 * @author cuihbin
 *
 */
abstract public class ConsoleHelper extends ConsoleSubject {
	private Logger logger = Logger.getLogger(ConsoleHelper.class);
	
	@Autowired
	private List<ConsoleObserver> allDefinedConsoles;
	
	private Collection<ConsoleObserver> consoles;
	
	private LocalizeUtil localizeUtil = new LocalizeUtil();
	
	public void pushBundle(String baseName) {
		localizeUtil.pushBundle(baseName);
	}
	
	public String findText(String key, Object... args) {
		return localizeUtil.findText(key, args);
	}
	
	public String getString(String key, Locale locale) {
		return localizeUtil.getString(key, locale);
	}

	protected void initConsoles() {
		consoles = findNotInheritedConsoles();
		
		for (ConsoleObserver console : consoles) {
			try {
				console.init();
				registerConsole(console);
			} catch (ConsoleException e) {
				logger.error("Console disabled due to init failure: " + console.getClass().getName(), e);
			}
		}
	}
	
	protected void destroyConsoles() {
		for (ConsoleObserver console : consoles) {
			try {
				console.destroy();
			} catch (Exception e) {
				logger.error("Console destroy error: " + console.getClass().getName(), e);
			}
		}
	}
	
	public void traceMessage(String messageKey, Object... args) {
		trace(findText(messageKey, args));
	}
	
	public void infoMessage(String messageKey, Object... args) {
		info(findText(messageKey, args));
	}
	
	public void warnMessage(String messageKey, Object... args) {
		warn(findText(messageKey, args));
	}
	
	public void errorMessage(String messageKey, Object... args) {
		error(findText(messageKey, args));
	}
	
	public void statusMessage(String messageKey, Object... args) {
		status(findText(messageKey, args));
	}
	
	private List<ConsoleObserver> findNotInheritedConsoles() {
		List<ConsoleObserver> childConsoles = new ArrayList<ConsoleObserver>();
		
		Iterator<ConsoleObserver> it = allDefinedConsoles.iterator();
		outer:
		while (it.hasNext()) {
			ConsoleObserver console = it.next();
			for (int i = 0; i < childConsoles.size(); i++) {
				if (isParentOf(console, childConsoles.get(i))) {
					continue outer;
				} else if (isParentOf(childConsoles.get(i), console)) {
					childConsoles.remove(i);
					childConsoles.add(console);
					continue outer;
				}
			}
		}
		
		return childConsoles;
	}
	
	private boolean isParentOf(Object parent, Object child) {
		return parent.getClass().isInstance(child) && !child.getClass().isInstance(parent);
	}
}
