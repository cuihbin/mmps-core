package com.zzvc.mmps.console;

import java.util.Locale;

import org.springframework.util.StringUtils;

import com.zzvc.mmps.console.localize.LocalizeUtil;

/**
 * Extend this to convenience console message output
 * @author cuihbin
 *
 */
abstract public class ConsoleMessageSupport extends ConsoleSubject {
	private LocalizeUtil localizeUtil = new LocalizeUtil();
	
	abstract protected String getConsolePrefix();
	
	@Override
	public void trace(String text) {
		super.trace(getConsolePrefixedLabel() + text);
	}

	@Override
	public void info(String text) {
		super.info(getConsolePrefixedLabel() + text);
	}

	@Override
	public void warn(String text) {
		super.warn(getConsolePrefixedLabel() + text);
	}

	@Override
	public void error(String text) {
		super.error(getConsolePrefixedLabel() + text);
	}

	@Override
	public void status(String text) {
		super.status(getConsolePrefixedLabel() + text);
	}

	public void pushBundle(String baseName) {
		localizeUtil.pushBundle(baseName);
	}
	
	public String findText(String key, Object... args) {
		return localizeUtil.findText(key, args);
	}
	
	public String getString(String key, Locale locale) {
		return localizeUtil.getString(key, locale);
	}
	
	public void traceMessage(String key, Object... args) {
		trace(findText(key, args));
	}
	
	public void infoMessage(String key, Object... args) {
		info(findText(key, args));
	}
	
	public void warnMessage(String key, Object... args) {
		warn(findText(key, args));
	}
	
	public void errorMessage(String key, Object... args) {
		error(findText(key, args));
	}
	
	public void statusMessage(String key, Object... args) {
		status(findText(key, args));
	}
	
	private String getConsolePrefixedLabel() {
		String prefix = getConsolePrefix();
		if (StringUtils.hasText(prefix)) {
			return "[" + prefix + "] ";
		}
		return "";
	}

}
