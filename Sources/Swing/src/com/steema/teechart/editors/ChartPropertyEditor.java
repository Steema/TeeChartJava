package com.steema.teechart.editors;

import java.beans.PropertyEditorSupport;
import java.awt.Component;
import com.steema.teechart.Chart;
import com.steema.teechart.languages.Language;

/**
 *
 * <p>Title: ChartPropertyEditor class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ChartPropertyEditor extends PropertyEditorSupport {

    ChartEditor editor;

    public ChartPropertyEditor() {
    }

    public boolean supportsCustomEditor() {
        return true;
    }

    public Component getCustomEditor() {
        if (editor == null) {
            editor = new ChartEditor((Chart)getValue());
        }
        return editor;
    }

    public String getJavaInitializationString() {
        return Language.getString("Chart");
    }
}

