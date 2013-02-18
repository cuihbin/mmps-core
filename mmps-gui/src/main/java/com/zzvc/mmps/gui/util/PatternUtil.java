package com.zzvc.mmps.gui.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Utility to format message in a predefined pattern
 * @author CHB
 *
 */
public class PatternUtil {
	private static String DEFAULT_CONVERSION_PATTERN = "%d{yyyy-MM-dd HH:mm:ss} - %p%m%n";

	private PatternLayout layout;
	
	public PatternUtil() {
		this(DEFAULT_CONVERSION_PATTERN);
	}

	public PatternUtil(String pattern) {
		layout = new PatternLayout(pattern);
	}

	public void setPattern(String pattern) {
		layout.setConversionPattern(pattern);
	}

	public String format(String logPriority, String message) {
		synchronized (PatternUtil.this) {
			return layout.format(new LoggingEvent(PatternUtil.class.getName(), Logger.getRootLogger(), Level.DEBUG, message, null)).replace("DEBUG", logPriority);
		}
	}
	
	public String format(long timestamp, String logPriority, String message) {
		synchronized (PatternUtil.this) {
			return layout.format(new LoggingEvent(PatternUtil.class.getName(), Logger.getRootLogger(), timestamp, Level.DEBUG, message, null)).replace("DEBUG", logPriority);
		}
	}
}
