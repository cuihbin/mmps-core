package com.zzvc.mmps.app;

import java.util.MissingResourceException;

import com.zzvc.mmps.task.TaskSupport;

/**
 * Support class to define an application's basic behavior. Define exactly one bean extends this
 * class in an application. Extra resource bundle should be added to provide application information
 * like name and version
 * @author CHB
 *
 */
abstract public class AppSupport extends TaskSupport {
	private String appTitle;
	
	public AppSupport() {
		super();
		pushBundle("AppResources");
	}
	
	@Override
	public String getId() {
		return "app";
	}

	@Override
	public void prepareInit() {
		setAppLanguage();
		removeParentConsoles();
		initConsoles();
		
		appTitle = findText("app.title", findText("app.name"), findText("app.version"));
		setConsoleTitle(appTitle);
		infoMessage("app.starting", appTitle);
	}
	
	@Override
	public void afterStartup() {
		infoMessage("app.started", appTitle);
		statusMessage("app.status.running", appTitle);
	}
	
	@Override
	public void beforeShutdown() {
		infoMessage("app.shuttingdown", appTitle);
	}

	@Override
	public void destroy() {
		destroyConsoles();
	}

	@Override
	public String getConsolePrefix() {
		return "";
	}

	private void setAppLanguage() {
		try {
			setLanguage(getString("app.language", ""));
		} catch (MissingResourceException e) {
		}
	}

}
