package com.zzvc.mmps.console.localize;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 * Utility to find localized & formated text from message key. 
 * Each child of LocalizeUtil can push a ResourceBundle on top of the resource-bundle stack. A message keys is  
 * retrieved from top of the stack and return on first matching. A MissingResourceException will be thrown if 
 * no matches is found.
 * @author cuihbin
 *
 */
public class LocalizeUtil {
	private Map<String, MessageFormat> messageFormats = new HashMap<String, MessageFormat>();
	
	private Vector<String> resourceNames = new Vector<String>();
	
	private static Locale locale = Locale.getDefault();
	
	private static DateFormat dateTimeFormatterFull = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locale);
	
	private static DateFormat dateTimeFormatterLong = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale); 
	
	private static DateFormat dateTimeFormatterMedium = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, locale);
	
	private static DateFormat dateTimeFormatterShort = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale); 
	
	public LocalizeUtil() {
	}
	
	public LocalizeUtil(String resourceName) {
		pushBundle(resourceName);
	}
	
	public static void setLanguage(String language) {
		locale = new Locale(language);
	}
	
	public static String formatDateTimeFull(Date date) {
		return formatDateTime(dateTimeFormatterFull, date);
	}
	
	public static String formatDateTimeLong(Date date) {
		return formatDateTime(dateTimeFormatterLong, date);
	}
	
	public static String formatDateTimeMedium(Date date) {
		return formatDateTime(dateTimeFormatterMedium, date);
	}
	
	public static String formatDateTimeShort(Date date) {
		return formatDateTime(dateTimeFormatterShort, date);
	}
	
	public void pushBundle(String baseName) {
		resourceNames.add(0, baseName);
	}
	
	public String findText(String key, Object... args) {
		String message = getString(key, locale);
		
		for (int i = 0; i < args.length; i++) {
			if (args[i] instanceof Date) {
				args[i] = formatDateTimeMedium((Date) args[i]);
			}
		}
		
		if (!messageFormats.containsKey(message)) {
			messageFormats.put(message, new MessageFormat(message));
		}
		
		return messageFormats.get(message).format(args);
	}
	
	public String getString(String key, Locale locale) {
		for (int i = 0; i < resourceNames.size(); i++) {
			try {
				return getResourceBundle(resourceNames.get(i), locale).getString(key);
			} catch (MissingResourceException e) {
			}
		}
        throw new MissingResourceException("Can't find resource for bundle "
                +this.getClass().getName()
                +", key "+key,
                this.getClass().getName(),
                key);
	}
	
	private static String formatDateTime(DateFormat df, Date date) {
		if (date == null) {
			return null;
		}
		
		return df.format(date);
	}
	
	private ResourceBundle getResourceBundle(String baseName, Locale locale) {
		if (locale == null) {
			locale = new Locale("");
		}
		return ResourceBundle.getBundle(baseName, locale);
	}
}
