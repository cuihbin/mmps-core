package com.zzvc.mmps.gui.console;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.zzvc.mmps.gui.file.FileException;
import com.zzvc.mmps.gui.file.GuiFileFilter;

public class GuiConsoleFileChooser {
	private final GuiConsole console;
	
	private String title;
	private JFileChooser fDialog = new JFileChooser();
	
	public GuiConsoleFileChooser(GuiConsole console) {
		this.console = console;
	}

	protected void setTitle(String title) {
		this.title = title;
	}

	public File selectFileForRead(String fileExtension, String fileFilterDescription) {
		File file = selectFile(fileExtension, fileFilterDescription);
		if (!file.exists()) {
			JOptionPane.showMessageDialog(null, console.findText("console.gui.file.notexists", file.getName()));
			throw new FileException("File not exists");
		}
		return file;
	}
	
	public File selectFileForWrite(String fileExtension, String fileFilterDescription) {
		File file = selectFile(fileExtension, fileFilterDescription);
		if (file.exists()) {
			if (JOptionPane.showConfirmDialog(null, console.findText("console.gui.file.confirmoverwrite", file.getName()), title, JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
				throw new FileException("File already exists");
			}
		}
		return file;
	}

	private File selectFile(String fileExtension, String fileFilterDescription) {
		fDialog.resetChoosableFileFilters();
		fDialog.addChoosableFileFilter(new GuiFileFilter(fileExtension, fileFilterDescription));
		int result = fDialog.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			return fDialog.getSelectedFile();
		} else {
			throw new FileException("User canceled file selection");
		}
	}

}
