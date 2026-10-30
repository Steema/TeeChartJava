package com.steema.teechart.editors;

import javax.swing.JColorChooser;
import java.awt.Component;
import java.awt.Color;
import com.steema.teechart.languages.Language;

/**
 *
 * <p>Title: ColorEditor class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ColorEditor extends JColorChooser {


    static public com.steema.teechart.drawing.Color choose(Component owner, Color value) {
        Color tmp=showDialog(owner, Language.getString("Choose_a_color"), value);
        if (tmp==null) {
            tmp=value;
        }
        return new com.steema.teechart.drawing.Color(tmp);
    }
}

