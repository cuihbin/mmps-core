package com.zzvc.mmps.gui.console;

import javax.swing.JOptionPane;

public class GuiConsoleDialog {
	
	private final GuiConsole console;
	private String title;

	public GuiConsoleDialog(GuiConsole console) {
		this.console = console;
	}
	
	protected void setTitle(String title) {
		this.title = title;
	}

	public void showMessage(String key, Object... args) {
		JOptionPane.showMessageDialog(null, console.findText(key, args));
	}
	
	public boolean showConfirm(String key, Object... args) {
		return JOptionPane.showConfirmDialog(null, console.findText(key, args), title, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION;
	}

}
