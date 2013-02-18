package com.zzvc.mmps.gui.file;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * File filter for FileChooser in @GuiConsoleFrame
 * @author CHB
 *
 */
public class GuiFileFilter extends FileFilter {
	
	private String fileExtension;
	private String description;

    public GuiFileFilter(String fileExtension, String description) {
		this.fileExtension = fileExtension;
		this.description = description;
	}

	public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = getExtension(f);
        if (extension != null) {
        	return extension.equals(fileExtension);
        }

        return false;
    }

    //The description of this filter
    public String getDescription() {
        return description;
    }
    
    private String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}
