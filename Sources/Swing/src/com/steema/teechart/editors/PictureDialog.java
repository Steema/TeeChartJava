package com.steema.teechart.editors;

import java.io.File;
import javax.swing.*;

import com.steema.teechart.editors.export.ImageFilter;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: PictureDialog class</p>
 *
 * <p>Description: Picture Dialog</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

public class PictureDialog {


    public PictureDialog() {}

    static private JFileChooser fc;

    static public File getFileName(JComponent c) {
        fc = new JFileChooser();

        fc.setDialogTitle(Language.getString("Select_picture_file"));
        fc.setCurrentDirectory(new File("."));
        fc.setFileFilter(new ImageFilter());

        fc.showOpenDialog(c);
        return fc.getSelectedFile();
    }
}

