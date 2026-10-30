package com.steema.teechart.editors;

import com.steema.teechart.Chart;
import com.steema.teechart.printer.Printer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

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
public class PreviewDialog extends SWTBaseEditorDialog {
	
    public PreviewDialog(Shell parent,Chart c) {
        this(parent,c, c.getPrinter());
    }

    public PreviewDialog(Shell parent,Chart c, Printer p) {
    	super(parent,0);
    }

    static public void showModal(Chart c)
    {
       showModal(c,c.getPrinter());
    }

    static public void showModal(Chart c,Printer p)
    {
    	Display display = Display.getDefault();
		  Shell shell = new Shell(display, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE | SWT.MIN | SWT.MAX);
    	SWTPreviewFrame pPreview = new SWTPreviewFrame(shell,c,p);
    }
}

