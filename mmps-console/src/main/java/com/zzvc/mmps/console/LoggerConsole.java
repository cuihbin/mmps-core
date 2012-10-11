package com.zzvc.mmps.console;

import org.apache.log4j.Logger;


public class LoggerConsole implements Console {
	private static Logger logger = Logger.getLogger(LoggerConsole.class);
	
	@Override
	public void setConsoleTitle(String consoleTitle) {

	}

	@Override
	public void trace(String text) {
		logger.debug("[TRACE] " + text);
	}

	@Override
	public void info(String text) {
		logger.info("[INFO] " + text);
	}

	@Override
	public void warn(String text) {
		logger.warn("[WARN] " + text);
	}

	@Override
	public void error(String text) {
		logger.error("[ERROR] " + text);
	}

	@Override
	public void status(String text) {
		logger.debug("[STATUS] " + text);
	}

	@Override
	public void init() {
		
	}

	@Override
	public void destroy() {
		
	}

}
