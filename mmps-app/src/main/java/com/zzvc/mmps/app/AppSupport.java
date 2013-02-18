package com.zzvc.mmps.app;

import java.util.MissingResourceException;

import org.springframework.beans.factory.annotation.Autowired;

import com.zzvc.mmps.console.ConsoleHelper;
import com.zzvc.mmps.console.localize.LocalizeUtil;
import com.zzvc.mmps.task.TaskSupport;

/**
 * Support class to define an application's basic behavior. Define exactly one bean extends this
 * class in an application. Extra resource bundle should be added to provide application information
 * like name and version
 * @author cuihbin
 *
 */
abstract public class AppSupport extends TaskSupport {
	@Autowired
	private ConsoleHelper consoleHelper;
	
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
	public void preInit() {
		setAppLanguage();
		consoleHelper.initConsoles();
		
		appTitle = findText("app.title", findText("app.name"), findText("app.version"));
		setConsoleTitle(appTitle);
		appMessage("app.starting", appTitle);
	}
	
	@Override
	public void postInit() {
		appMessage("app.started", appTitle);
	}
	
	@Override
	public void preDestroy() {
		appMessage("app.stopping", appTitle);
	}

	@Override
	public void destroy() {
		consoleHelper.destroyConsoles();
	}

	@Override
	public String getConsolePrefix() {
		return "";
	}

	private void setAppLanguage() {
		try {
			LocalizeUtil.setLanguage(getString("app.language", null));
		} catch (MissingResourceException e) {
		}
	}

	private void appMessage(String key, Object... args) {
		infoMessage(key, args);
		statusMessage(key, args);
	}
}
