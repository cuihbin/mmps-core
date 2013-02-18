package com.zzvc.mmps.gui.console;

/**
 * Define a GUI button
 * @author CHB
 *
 */
abstract public class GuiConsoleButton {
	private String label;
	
	public GuiConsoleButton(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	abstract public void buttonAction();
}
