package com.zzvc.mmps.console;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.zzvc.mmps.console.localize.LocalizeUtil;

/**
 * Extend this class to convenient console message output.
 * @author cuihbin
 *
 */
abstract public class ConsoleHelper extends LocalizeUtil implements ConsoleSubject {
	private Logger logger = Logger.getLogger(ConsoleHelper.class);
	
	@Autowired
	private List<ConsoleObserver> allDefinedConsoles;
	
	private static Collection<ConsoleObserver> consoles;
	
	abstract public String getConsolePrefix();

	@Override
	public void setConsoleTitle(String consoleTitle) {
		for (ConsoleObserver console : consoles) {
			console.setConsoleTitle(consoleTitle);
		}
	}

	@Override
	public void trace(String text) {
		for (ConsoleObserver console : consoles) {
			console.trace(getConsolePrefixedLabel() + text);
		}
	}

	@Override
	public void info(String text) {
		for (ConsoleObserver console : consoles) {
			console.info(getConsolePrefixedLabel() + text);
		}
	}

	@Override
	public void warn(String text) {
		for (ConsoleObserver console : consoles) {
			console.warn(getConsolePrefixedLabel() + text);
		}
	}

	@Override
	public void error(String text) {
		for (ConsoleObserver console : consoles) {
			console.error(getConsolePrefixedLabel() + text);
		}
	}

	@Override
	public void status(String text) {
		for (ConsoleObserver console : consoles) {
			console.status(getConsolePrefixedLabel() + text);
		}
	}
	
	@Override
	public void registerConsoles(Collection<ConsoleObserver> observingConsoles) {
		Iterator<ConsoleObserver> it = observingConsoles.iterator();
		while (it.hasNext()) {
			ConsoleObserver console = it.next();
			try {
				console.init();
			} catch (ConsoleException e) {
				logger.error("Console disabled due to init failure", e);
				it.remove();
			}
		}
		consoles = Collections.synchronizedCollection(observingConsoles);
	}

	protected void initConsoles() {
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
		
		registerConsoles(childConsoles);
	}
	
	protected void destroyConsoles() {
		for (ConsoleObserver console : consoles) {
			try {
				console.destroy();
			} catch (Exception e) {
				logger.error("Console destroy error", e);
			}
		}
	}
	
	protected void traceMessage(String messageKey, Object... args) {
		trace(findText(messageKey, args));
	}
	
	protected void infoMessage(String messageKey, Object... args) {
		info(findText(messageKey, args));
	}
	
	protected void warnMessage(String messageKey, Object... args) {
		warn(findText(messageKey, args));
	}
	
	protected void errorMessage(String messageKey, Object... args) {
		error(findText(messageKey, args));
	}
	
	protected void statusMessage(String messageKey, Object... args) {
		status(findText(messageKey, args));
	}
	
	private String getConsolePrefixedLabel() {
		String prefix = getConsolePrefix();
		if (StringUtils.hasText(prefix)) {
			return "[" + prefix + "] ";
		}
		return "";
	}
	
	private boolean isParentOf(Object parent, Object child) {
		return parent.getClass().isInstance(child) && !child.getClass().isInstance(parent);
	}
}
