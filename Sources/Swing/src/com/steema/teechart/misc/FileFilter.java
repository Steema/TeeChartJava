/*
 * FileFilter.java
 *
 * <p>Copyright (c) 2007 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */


package com.steema.teechart.misc;

import java.io.File;

/**
 *
 */
public class FileFilter extends javax.swing.filechooser.FileFilter {

    private String extension;
    private String description;

    /**
     * Creates a new instance of FileFilter
     *
     * @param description String
     * @param extension String
     */
    public FileFilter(String description, String extension) {
        if (extension != null) {
            this.extension = extension.toLowerCase();
        }
        this.description = description;
    }

    /**
     * Returns true when "f" is a directory or a file that has an accepted
     * extension.
     *
     * @param f File
     * @return boolean
     */
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String tmpExt = getExtension(f);
        if (tmpExt != null) {
            return tmpExt.equals(this.extension);
        } else {
            return false;
        }
    }

    //The description of this filter
    public String getDescription() {
        return description + " (*." + extension + ")";
    }

    //The extention of this filter
    public String getExtension() {
        return extension;
    }

    /**
     * Get the extension of a file.
     *
     * @param f File
     * @return String
     */
    public static String getExtension(File f) {
        String s = f.getName();
        int i = s.lastIndexOf('.');

        String ext = null;
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }
}

