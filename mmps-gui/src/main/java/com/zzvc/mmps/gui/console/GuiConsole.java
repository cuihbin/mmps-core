package com.zzvc.mmps.gui.console;

import java.util.ArrayList;
import java.util.List;

import sun.misc.Signal;

import com.zzvc.mmps.console.ConsoleObserver;
import com.zzvc.mmps.console.localize.LocalizeUtil;
import com.zzvc.mmps.gui.util.PatternUtil;

/**
 * A GUI console for log output
 * @author CHB
 *
 */
public class GuiConsole extends LocalizeUtil implements ConsoleObserver {
	
	protected GuiConsoleFrame frame;
	
	private List<GuiConsoleButton> buttons = new ArrayList<GuiConsoleButton>();
	
	private String logPriorityTrace;
	private String logPriorityInfo;
	private String logPriorityWarn;
	private String logPriorityError;
	
	private PatternUtil patternUtil;

	public GuiConsole() {
		pushBundle("GuiConsoleResources");
		
		frame = new GuiConsoleFrame(this);
	}

	@Override
	public void setConsoleTitle(String consoleTitle) {
		frame.setTitle(consoleTitle);
	}

	@Override
	public void trace(String text) {
		frame.addTrace(patternUtil.format(logPriorityTrace, text));
	}

	@Override
	public void info(String text) {
		frame.addInfo(patternUtil.format(logPriorityInfo, text));
	}

	@Override
	public void warn(String text) {
		frame.addWarn(patternUtil.format(logPriorityWarn, text));
	}

	@Override
	public void error(String text) {
		frame.addError(patternUtil.format(logPriorityError, text));
	}

	@Override
	public void status(String text) {
		frame.setStatus(text);
	}
	
	@Override
	public void init() {
		try {
			patternUtil = new PatternUtil(findText("console.gui.log.pattern"));
		} catch (Exception e) {
			patternUtil = new PatternUtil();
		}
		
		logPriorityTrace = findText("console.gui.log.trace");
		logPriorityInfo = findText("console.gui.log.info");
		logPriorityWarn = findText("console.gui.log.warn");
		logPriorityError = findText("console.gui.log.error");
		
		frame.init();
	}
	
	@Override
	public void destroy() {
		
	}
	
	protected void close() {
		Signal.raise(new Signal("TERM"));
	}
	
	protected void addButton(GuiConsoleButton button) {
		buttons.add(button);
	}

	protected List<GuiConsoleButton> getButtons() {
		return buttons;
	}
}

