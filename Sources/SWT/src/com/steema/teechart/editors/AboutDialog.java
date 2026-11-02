package com.steema.teechart.editors;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;

import com.steema.teechart.languages.Language;

public class AboutDialog extends Dialog {

	private Object result;
	private Display display;
	private Shell shell;
	
	private static String steemaCom = Language.getString("www_steema_com");
	
	public AboutDialog(Shell parent, int style) {
		super(parent, style);
	}
	
	public AboutDialog(Shell parent) {
		super(parent, 0);
	}
	
	private Composite createTopUI(Composite parent) {
		
  	Composite pane = new Composite(parent, SWT.NONE);
 
  	final Image logoImage = new Image(display, 
        getClass().getResourceAsStream("/com/steema/teechart/images/teeChartPro.png"));
  	
  	GridLayout gridLayout = new GridLayout();
  	pane.setLayout(gridLayout);
  	parent.setBackgroundMode(SWT.INHERIT_DEFAULT);
  	pane.setBackgroundMode(SWT.INHERIT_DEFAULT);

  	Label lblHeader = new Label(pane, SWT.CENTER);
  	if (logoImage != null) {
  		lblHeader.setImage(logoImage);
  		lblHeader.setBounds(logoImage.getBounds());
  	}
  	lblHeader.setLayoutData(new GridData(SWT.TRANSPARENCY_ALPHA, SWT.NONE, true, false));

  	pane.addDisposeListener(new DisposeListener() {
  		public void widgetDisposed(DisposeEvent e) {
  			logoImage.dispose();
  		}
  	});
  	
  	return pane;
  }
	
	private Composite createMiddleUI(Composite parent) {
		
  	Composite pane = new Composite(parent, SWT.NONE);
  	
  	GridLayout gridLayout = new GridLayout();
  	gridLayout.numColumns = 1;
  	pane.setLayout(gridLayout);
  	parent.setBackgroundMode(SWT.INHERIT_DEFAULT);
  	pane.setBackgroundMode(SWT.INHERIT_DEFAULT);

  	Label labelSpacer = new Label(pane, SWT.CENTER);
  	labelSpacer.setLayoutData(new GridData(SWT.TRANSPARENCY_ALPHA, SWT.TOP, true, false));
  	
  	Label labelBuild = new Label(pane, SWT.CENTER);
  	labelBuild.setText(Language.getString("TeeChart_for_Java")
         +" "+com.steema.teechart.misc.Utils.BuildNumber);
  	labelBuild.setLayoutData(new GridData(SWT.TRANSPARENCY_ALPHA, SWT.TOP, true, false));
  	
  	Label labelCopy = new Label(pane, SWT.CENTER);
  	labelCopy.setText(Language.getString("Copyright")+"."+'\n'+Language.getString("All_Rights_Reserved"));
  	labelCopy.setLayoutData(new GridData(SWT.TRANSPARENCY_ALPHA, SWT.TOP, true, false));
  	
  	Label labelSteemaWeb = new Label(pane, SWT.CENTER);
  	labelSteemaWeb.setText("Steema Website: " + steemaCom);
  	labelSteemaWeb.setLayoutData(new GridData(SWT.TRANSPARENCY_ALPHA, SWT.TOP, true, false));
  	
  	Label labelJavaVer = new Label(pane, SWT.CENTER);
  	labelJavaVer.setText(Language.getString("Java_Version") + " " +
            System.getProperty(Language.getString("java_version")) + " " +
            Language.getString("from") + " " + System.getProperty(Language.getString("java_vendor")));
  	labelJavaVer.setLayoutData(new GridData(SWT.TRANSPARENCY_ALPHA, SWT.TOP, true, false));

  	return pane;
  }
	
	private Composite createBottomUI(Composite parent) {
		
  	Composite pane = new Composite(parent, SWT.NONE);
  	
		final Button buttonClose = new Button(pane, SWT.PUSH);
		buttonClose.setBounds(0, 30, 95, 25);
		buttonClose.setText(Language.getString("Close"));
		buttonClose.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				shell.close();
			}
		});
  	
//    GridData gridData = new GridData();
//    gridData.horizontalSpan = 2;
//    gridData.horizontalAlignment = GridData.END;
//    buttonClose.setLayoutData(gridData);

  	return pane;
  }
	
	public Object open() {		
		final Shell parent = getParent();
		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER);
		display = parent.getDisplay();

		shell.setSize(370, 300);
		shell.setText(Language.getString("About_TeeChart_for"));
		shell.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		
		GridLayout gl = new GridLayout();
  	gl.numColumns = 1;    	
  	shell.setLayout(gl);
  	Composite pane = createTopUI(shell);
  	pane.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
  	
  	Composite paneMiddle = createMiddleUI(shell);
  	paneMiddle.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
  	
  	Composite paneBottom = createBottomUI(shell);
  	paneBottom.setLayoutData(new GridData(SWT.CENTER, SWT.BOTTOM, true, false));
		
  	shell.open();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}
}
