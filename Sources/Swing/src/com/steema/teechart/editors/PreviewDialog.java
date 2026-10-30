package com.steema.teechart.editors;

import com.steema.teechart.Chart;
import com.steema.teechart.printer.PreviewPanel;
import com.steema.teechart.printer.Printer;

/**
 *
 * <p>Title: PreviewDialog class</p>
 *
 * <p>Description: Print Preview </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class PreviewDialog extends BaseEditorDialog {

    public PreviewDialog(Chart c) {
        this(c, c.getPrinter());
    }

    public PreviewDialog(Chart c, Printer p) {
        super();
        setEditorPane(new PreviewFrame(c,p));
    }

    static public void showModal(Chart c)
    {
       showModal(c,c.getPrinter());
    }

    static public void showModal(Chart c,Printer p)
    {
       BaseEditorDialog.showModal(new PreviewFrame(c,p));
    }

    public PreviewPanel getPreviewPanel() {
        if (editorPane != null) {
            return ((PreviewFrame)editorPane).getPreviewPanel();
        } else {
            return null;
        }
    }
}

