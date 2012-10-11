package com.zzvc.mmps.console.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.zzvc.mmps.console.Console;
import com.zzvc.mmps.console.ConsoleException;
import com.zzvc.mmps.console.localize.LocalizeUtil;

/**
 * Modules want to output to consoles may extend this helper class for convenience.
 * @author CHB
 *
 */
abstract public class ConsoleHelper extends LocalizeUtil {
	private Logger logger = Logger.getLogger(ConsoleHelper.class);
	
	@Autowired
	private List<Console> allDefinedConsoles;
	
	private static List<Console> consoles;
	
	abstract public String getConsolePrefix();

	public void setConsoleTitle(String consoleTitle) {
		for (Console console : consoles) {
			console.setConsoleTitle(consoleTitle);
		}
	}

	public void trace(String text) {
		for (Console console : consoles) {
			console.trace(getConsolePrefixedLabel() + text);
		}
	}

	public void info(String text) {
		for (Console console : consoles) {
			console.info(getConsolePrefixedLabel() + text);
		}
	}

	public void warn(String text) {
		for (Console console : consoles) {
			console.warn(getConsolePrefixedLabel() + text);
		}
	}

	public void error(String text) {
		for (Console console : consoles) {
			console.error(getConsolePrefixedLabel() + text);
		}
	}

	public void status(String text) {
		for (Console console : consoles) {
			console.status(getConsolePrefixedLabel() + text);
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
	
	protected void removeParentConsoles() {
		List<Console> childConsoles = new ArrayList<Console>();
		
		Iterator<Console> it = allDefinedConsoles.iterator();
		outer:
		while (it.hasNext()) {
			Console console = it.next();
			for (int i = 0; i < childConsoles.size(); i++) {
				if (isParentOf(console, childConsoles.get(i))) {
					continue outer;
				} else {
					if (isParentOf(childConsoles.get(i), console)) {
						childConsoles.remove(i);
						childConsoles.add(console);
						continue outer;
					}
				}
			}
			childConsoles.add(console);
		}
		
		consoles = Collections.synchronizedList(childConsoles);
	}
	
	protected void initConsoles() {
		Iterator<Console> it = consoles.iterator();
		while (it.hasNext()) {
			Console console = it.next();
			try {
				console.init();
			} catch (ConsoleException e) {
				logger.error("Console disabled due to init failure", e);
				it.remove();
			}
		}
	}
	
	protected void destroyConsoles() {
		for (Console console : consoles) {
			console.destroy();
		}
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
