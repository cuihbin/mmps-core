package com.zzvc.mmps.app;

import java.util.Map;

import com.zzvc.mmps.app.util.BeanFactory;

/**
 * Main class to start the application
 * 
 * @author cuihbin
 * 
 */
public class Main {

	public static void main(String[] args) {
		BeanFactory.start();

		Map<String, AppArgsListener> listeners = BeanFactory.getBeansOfType(AppArgsListener.class);
		for (AppArgsListener listener : listeners.values()) {
			listener.processArgs(args);
		}
	}

}
