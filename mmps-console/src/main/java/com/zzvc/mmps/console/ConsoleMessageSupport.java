package com.zzvc.mmps.console;

import java.util.Locale;

import com.zzvc.mmps.console.localize.LocalizeUtil;

/**
 * Extend this to convenience console message output
 * @author cuihbin
 *
 */
abstract public class ConsoleMessageSupport extends ConsoleSubject {
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

}
