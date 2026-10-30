package com.steema.teechart.editors;

import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;

import com.steema.teechart.Chart;


public class SWTBaseEditorDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	
    /** The button pane. */
    protected Composite buttonPane;
    /** The editor pane. */
    protected Composite editorPane;
    /** The content pane. */
    private Composite contentPane;
	private Button closeButton;

	public static void showModal(Chart c, com.steema.teechart.printer.Printer p)
	{
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			SWTBaseEditorDialog inst = new SWTBaseEditorDialog(shell, SWT.NULL);
			inst.open(c,p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Dialog inside a new Shell.
	*/
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			SWTBaseEditorDialog inst = new SWTBaseEditorDialog(shell, SWT.NULL);
			inst.open(null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SWTBaseEditorDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void open(Chart c, com.steema.teechart.printer.Printer p) {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE | SWT.MIN );
			if ((c!=null) && (p!=null))
				  setEditorPane(c, p);
			dialogShell.setLayout(new FormLayout());
			{
//				if ((c!=null) && (p!=null))
//					  setEditorPane(c, p);
				
//				FormData contentPaneLData = new FormData();
//				contentPaneLData.width = 149;
//				contentPaneLData.height = 60;
//				contentPaneLData.left =  new FormAttachment(0, 1000, 7);
//				contentPaneLData.top =  new FormAttachment(0, 1000, 12);
//				contentPane = new Composite(dialogShell, SWT.NONE);
//				FormLayout contentPaneLayout = new FormLayout();
//				contentPane.setLayout(contentPaneLayout);
//				contentPane.setLayoutData(contentPaneLData);
//				{
//					closeButton = new Button(contentPane, SWT.PUSH | SWT.CENTER);
//					FormData closeButtonLData = new FormData();
//					closeButtonLData.width = 66;
//					closeButtonLData.height = 21;
//					closeButtonLData.left =  new FormAttachment(130, 1000, 0);
//					closeButtonLData.right =  new FormAttachment(573, 1000, 0);
//					closeButtonLData.top =  new FormAttachment(325, 1000, 0);
//					closeButtonLData.bottom =  new FormAttachment(675, 1000, 0);
//					closeButton.setLayoutData(closeButtonLData);
//					closeButton.setText(Language.getString("Close"));
//				}
			}
			dialogShell.layout();
			dialogShell.pack();
			dialogShell.setSize(504, 298);
			dialogShell.setLocation(getParent().toDisplay(100, 100));
			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setEditorPane(Chart c, com.steema.teechart.printer.Printer p) {
		
		Shell shell = new Shell(Display.getDefault());
		FormData editorPaneLData = new FormData();
		editorPaneLData.width = 470;
		editorPaneLData.height = 253;
		editorPaneLData.left =  new FormAttachment(0, 1000, 7);
		editorPaneLData.top =  new FormAttachment(0, 1000, 6);
		editorPane = new SWTPreviewFrame(dialogShell,c,p);
		//editorPane = new Composite(dialogShell, SWT.NONE);
		FormLayout editorPaneLayout = new FormLayout();
		editorPane.setLayout(editorPaneLayout);
		editorPane.setLayoutData(editorPaneLData);
		
//		editorPane.setLayoutData(editorPaneLData);
//		{
//			closeButton = new Button(editorPane, SWT.PUSH | SWT.CENTER);
//			FormData closeButtonLData = new FormData();
//			closeButtonLData.width = 66;
//			closeButtonLData.height = 21;
//			closeButtonLData.left =  new FormAttachment(130, 1000, 0);
//			closeButtonLData.right =  new FormAttachment(573, 1000, 0);
//			closeButtonLData.top =  new FormAttachment(325, 1000, 0);
//			closeButtonLData.bottom =  new FormAttachment(675, 1000, 0);
//			closeButton.setLayoutData(closeButtonLData);
//			closeButton.setText(Language.getString("Close"));
//		}
		
		//org.eclipse.swt.graphics.Color color= new org.eclipse.swt.graphics.Color();
		
		
		

		
        //if (this.editorPane != null) { contentPane.remove(this.editorPane); }
        //if (editorPane == null) { editorPane = new JPanel(); }
//        BorderLayout editorPaneLayout = new BorderLayout();
//        editorPane.setLayout(editorPaneLayout);
//        this.editorPane = editorPane;
//        contentPane.add(editorPane, BorderLayout.CENTER);
//        if (editorPane instanceof CloseButtonPane) {
//            ((CloseButtonPane)editorPane).addCloseButton(closeButton);
//            buttonPane.setVisible(false);
//        } else {
//            buttonPane.setVisible(true);
//        }
//        pack();
        //dialogShell.setSize(286, 190);
    }

}
