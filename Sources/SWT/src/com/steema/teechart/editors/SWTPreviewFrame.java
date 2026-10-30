package com.steema.teechart.editors;
import java.awt.print.*;
import java.awt.Cursor;
import javax.print.PrintService;

import com.steema.teechart.Chart;
import com.steema.teechart.printer.*;
import com.steema.teechart.languages.*;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;


public class SWTPreviewFrame extends org.eclipse.swt.widgets.Composite {
	private Composite buttonPane;
	private Label printerLabel;
	private Combo cbPrinter;
	private TabItem tabFormat;
	private TabItem tabPage;
	private TabFolder tabOptions;
	private Composite tabPane;
	private Button bSetup;
	private Button bPrint;
	private Composite previewPane;
	private Button checkGrayscale;
	private Button checkProportional;
	private Button checkPrintBackground;
	private Label lbMarginUnits;
	private Text txtMarginBottom;
	private Text txtMarginTop;
	private Text txtMarginRight;
	private Text txtMarginLeft;
	private Label lbLast;
	private Label lbNext;
	private Label lbPrevious;
	private Label lbFirst;
	private Combo cbMarginUnits;
	private Slider slMarginBottom;
	private Slider slMarginRight;
	private Slider slMarginLeft;
	private Slider slMarginTop;
	private Button rbLandscape;
	private Button rbPortrait;
	private Composite composite1;
	private Composite tabComposite2;
	private Button button3;
	private Group group3;
	private Group group2;
	private Group group1;
	private Composite grpMargins;
	private Composite grpOrientatiion;
	private ChartPreviewPanel chPreviewPanel1;
	
	private Chart chart;
    private Printer prn;
    private boolean setting = false;
    private PrinterJob printDocument1;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		showGUI();
	}
		
	/**
	* Auto-generated method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		SWTPreviewFrame inst = new SWTPreviewFrame(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if(size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	public SWTPreviewFrame(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}
	
	public SWTPreviewFrame(Shell parent, Chart c, com.steema.teechart.printer.Printer p) {
		super(parent,SWT.NONE);
		
        chart = c;
        prn = p;

        initGUI();

        loadPrinterSettings();
        
        Point size = this.getSize();
        parent.setLayout(new FillLayout());
        parent.layout();
    		if(size.x == 0 && size.y == 0) {
    			this.pack();
    			parent.pack();
    		} else {
    			Rectangle shellBounds = parent.computeTrim(0, 0, size.x, size.y);
    			parent.setSize(shellBounds.width, shellBounds.height);
    		}
    		parent.open();
    		while (!parent.isDisposed()) {
    			if (!this.getDisplay().readAndDispatch())
    				this.getDisplay().sleep();
    		}
    }

	private void initGUI() {
		try {
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			this.setSize(616, 453);
			{
				FormData previewPaneLData = new FormData();
				previewPaneLData.left =  new FormAttachment(0, 1000, 142);
				previewPaneLData.top =  new FormAttachment(0, 1000, 46);
				previewPaneLData.width = 374;
				previewPaneLData.height = 308;
				previewPaneLData.bottom =  new FormAttachment(1000, 1000, -5);
				previewPaneLData.right =  new FormAttachment(1000, 1000, -6);
				previewPane = new Composite(this, SWT.BORDER);
				FillLayout previewPaneLayout = new FillLayout(org.eclipse.swt.SWT.HORIZONTAL);
				previewPane.setLayout(previewPaneLayout);

				previewPane.setLayoutData(previewPaneLData);
				{
					chPreviewPanel1 = new ChartPreviewPanel(previewPane, SWT.NULL, chart);
				}
			}
			{
				buttonPane = new Composite(this, SWT.NONE);
				FormLayout buttonPaneLayout = new FormLayout();
				FormData buttonPaneLData = new FormData();
				buttonPaneLData.width = 526;
				buttonPaneLData.height = 30;
				buttonPaneLData.left =  new FormAttachment(0, 1000, 0);
				buttonPaneLData.right =  new FormAttachment(1000, 1000, 0);
				buttonPaneLData.top =  new FormAttachment(0, 1000, 0);
				buttonPane.setLayoutData(buttonPaneLData);
				buttonPane.setLayout(buttonPaneLayout);
				{
					printerLabel = new Label(buttonPane, SWT.NONE);
					FormData printerLabelLData = new FormData();
					printerLabelLData.width = 52;
					printerLabelLData.height = 21;
					printerLabelLData.left =  new FormAttachment(0, 1000, 7);
					printerLabelLData.top =  new FormAttachment(0, 1000, 4);
					printerLabel.setLayoutData(printerLabelLData);
					printerLabel.setText("Printer" + ":");
				}
				{
					cbPrinter = new Combo(buttonPane, SWT.NONE);
					FormData cbPrinterLData = new FormData();
					cbPrinterLData.width = 242;
					cbPrinterLData.height = 21;
					cbPrinterLData.left =  new FormAttachment(106, 1000, 0);
					cbPrinterLData.right =  new FormAttachment(544, 1000, 0);
					cbPrinterLData.top =  new FormAttachment(150, 1000, 0);
					cbPrinterLData.bottom =  new FormAttachment(850, 1000, 0);
					cbPrinter.setLayoutData(cbPrinterLData);
					cbPrinter.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
					      if (!setting)
					      {
					          PrintService ps = PrinterJob.lookupPrintServices()[cbPrinter.getSelectionIndex()];
					          try {
					              prn.getPrinterJob().setPrintService(ps);
					          } catch (Exception ex) {
					              ex.printStackTrace();
					          }
					      }
						}
					});
				}
				{
					bPrint = new Button(buttonPane, SWT.PUSH | SWT.CENTER);
					FormData bPrintLData = new FormData();
					bPrintLData.width = 93;
					bPrintLData.height = 19;
					bPrintLData.left =  new FormAttachment(557, 1000, 0);
					bPrintLData.right =  new FormAttachment(734, 1000, 0);
					bPrintLData.top =  new FormAttachment(150, 1000, 0);
					bPrintLData.bottom =  new FormAttachment(783, 1000, 0);
					bPrint.setLayoutData(bPrintLData);
					bPrint.setText("Print");
					bPrint.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							java.awt.Cursor cursor = java.awt.Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);

							prn.isPrinting = true;

							PrinterJob printJob = prn.getPrinterJob();
							printJob.setPrintable(chPreviewPanel1);
							try {
								printJob.print();
							} catch (PrinterException pe) {
								System.out.println(Language.getString("Error_printing") + pe);
							}

							prn.isPrinting = false;
							cursor = java.awt.Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
						}
					});
				}
				{
					bSetup = new Button(buttonPane, SWT.PUSH | SWT.CENTER);
					FormData bSetupLData = new FormData();
					bSetupLData.width = 92;
					bSetupLData.height = 19;
					bSetupLData.left =  new FormAttachment(757, 1000, 0);
					bSetupLData.right =  new FormAttachment(932, 1000, 0);
					bSetupLData.top =  new FormAttachment(150, 1000, 0);
					bSetupLData.bottom =  new FormAttachment(783, 1000, 0);
					bSetup.setLayoutData(bSetupLData);
					bSetup.setText("Setup");
					bSetup.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							printDocument1.printDialog();
			        if (prn.getLandscape()) {
			          rbLandscape.setSelection(true);
			          rbPortrait.setSelection(false);
			        } else {
			        	rbLandscape.setSelection(false);
			          rbPortrait.setSelection(true);
			        }

			        RefreshView();
						}

					});
				}
			}
			{
				FormData composite1LData = new FormData();
				composite1LData.width = 140;
				composite1LData.height = 336;
				composite1LData.left =  new FormAttachment(0, 1000, 0);
				composite1LData.bottom =  new FormAttachment(1000, 1000, 7);
				composite1LData.top =  new FormAttachment(0, 1000, 36);
				tabPane = new Composite(this, SWT.NONE);
				FillLayout composite1Layout = new FillLayout(org.eclipse.swt.SWT.HORIZONTAL);
				tabPane.setLayout(composite1Layout);
				tabPane.setLayoutData(composite1LData);
				{
					tabOptions = new TabFolder(tabPane, SWT.NONE);
					{
						tabPage = new TabItem(tabOptions, SWT.NONE);
						tabPage.setText("Page");
						{
							composite1 = new Composite(tabOptions, SWT.NONE);
							tabPage.setControl(composite1);
							composite1.setLayout(null);
							{
								group1 = new Group(composite1, SWT.TOP);
								group1.setLayout(null);
								group1.setText("Orientation");
								group1.setBounds(5, 5, 116, 136);
								{
									rbPortrait = new Button(group1, SWT.RADIO | SWT.LEFT);
									rbPortrait.setText("Portrait");
									rbPortrait.setBounds(20, 47, 60, 30);
									rbPortrait.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(SelectionEvent evt) {
											rbPortraitWidgetSelected(evt);
										}
									});
								}
								{
									rbLandscape = new Button(group1, SWT.RADIO | SWT.LEFT);
									rbLandscape.setText("Landscape");
									rbLandscape.setBounds(20, 70, 72, 30);
									rbLandscape.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(SelectionEvent evt) {
											rbLandscapeWidgetSelected(evt);
										}
									});
								}
							}
							{
								group2 = new Group(composite1, SWT.NONE);
								//tabPage.setControl(group2);
								group2.setText("Margins %");
								group2.setBounds(5, 146, 117, 173);
								{
									slMarginTop = new Slider(group2, SWT.NONE);
									slMarginTop.setBounds(54, 24, 24, 18);
									slMarginTop.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(SelectionEvent evt) {
											slMarginTopWidgetSelected(evt);
										}
									});
								}
								{
									slMarginLeft = new Slider(group2, SWT.NONE);
									slMarginLeft.setBounds(31, 54, 24, 18);
									slMarginLeft.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(SelectionEvent evt) {
											slMarginLeftWidgetSelected(evt);
										}
									});
								}
								{
									slMarginRight = new Slider(group2, SWT.NONE);
									slMarginRight.setBounds(85, 54, 24, 18);
									slMarginRight.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(SelectionEvent evt) {
											slMarginRightWidgetSelected(evt);
										}
									});
								}
								{
									slMarginBottom = new Slider(group2, SWT.NONE);
									slMarginBottom.setBounds(54, 84, 24, 18);
									slMarginBottom.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(SelectionEvent evt) {
											slMarginBottomWidgetSelected(evt);
										}
									});
								}
								{
									cbMarginUnits = new Combo(group2, SWT.NONE);
									cbMarginUnits.setText("Percent");
									cbMarginUnits.setBounds(18, 141, 81, 21);
									cbMarginUnits.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(SelectionEvent evt) {
											if (cbMarginUnits.getSelectionIndex()== 0)
											  prn.setMarginUnits(PrintMarginUnit.PERCENT);
											else
												prn.setMarginUnits(PrintMarginUnit.SEVENTYSECONDSINCH);
											
											prn.setProportional(false);
							        prn.getOptions();
							        RefreshView();
										}
									});
								}
								{
									txtMarginLeft = new Text(group2, SWT.NONE);
									txtMarginLeft.setText("0");
									txtMarginLeft.setBounds(7, 54, 24, 18);
									txtMarginLeft.addModifyListener(new ModifyListener() {
										public void modifyText(ModifyEvent evt) {
											txtMarginLeftModifyText(evt);
										}
									});
									txtMarginLeft.addVerifyListener(new VerifyListener() {
										public void verifyText(VerifyEvent evt) {
											txtMarginLeftVerifyText(evt);
										}
									});
								}
								{
									txtMarginRight = new Text(group2, SWT.NONE);
									txtMarginRight.setText("0");
									txtMarginRight.setBounds(61, 54, 24, 18);
									txtMarginRight.addModifyListener(new ModifyListener() {
										public void modifyText(ModifyEvent evt) {
											txtMarginRightModifyText(evt);
										}
									});
									txtMarginRight.addVerifyListener(new VerifyListener() {
										public void verifyText(VerifyEvent evt) {
											txtMarginRightVerifyText(evt);
										}
									});
								}
								{
									txtMarginTop = new Text(group2, SWT.NONE);
									txtMarginTop.setText("0");
									txtMarginTop.setBounds(30, 24, 24, 18);
									txtMarginTop.addModifyListener(new ModifyListener() {
										public void modifyText(ModifyEvent evt) {
											txtMarginTopModifyText(evt);
										}
									});
									txtMarginTop.addVerifyListener(new VerifyListener() {
										public void verifyText(VerifyEvent evt) {
											txtMarginTopVerifyText(evt);
										}
									});
								}
								{
									txtMarginBottom = new Text(group2, SWT.NONE);
									txtMarginBottom.setText("0");
									txtMarginBottom.setBounds(30, 84, 24, 18);
									txtMarginBottom.addModifyListener(new ModifyListener() {
										public void modifyText(ModifyEvent evt) {
											txtMarginBottomModifyText(evt);
										}
									});
									txtMarginBottom.addVerifyListener(new VerifyListener() {
										public void verifyText(VerifyEvent evt) {
											txtMarginBottomVerifyText(evt);
										}
									});
								}
								{
									lbMarginUnits = new Label(group2, SWT.NONE);
									lbMarginUnits.setText("Margin Units");
									lbMarginUnits.setBounds(18, 120, 60, 15);
								}
							}
							{
								lbPrevious = new Label(composite1, SWT.NONE);
								lbPrevious.setText("<");
								lbPrevious.setBounds(45, 325, 8, 13);
								lbPrevious.addMouseListener(new MouseAdapter() {
									public void mouseDown(MouseEvent evt) {
										lbPrevious.setForeground(color(java.awt.Color.LIGHT_GRAY));
									}
									public void mouseUp(MouseEvent evt) {
										if (chart.getPage().getCount()>0)
										{
											chart.getPage().previous();
											checkPageButtons();
										}
									}
								});
							}
							{
								lbFirst = new Label(composite1, SWT.NONE);
								lbFirst.setText("<<");
								lbFirst.setBounds(17, 325, 16, 13);
								lbFirst.addMouseListener(new MouseAdapter() {
									public void mouseDown(MouseEvent evt) {
										lbFirst.setForeground(color(java.awt.Color.LIGHT_GRAY));
									}
									public void mouseUp(MouseEvent evt) {
										if (chart.getPage().getCount()>0)
										{
											chart.getPage().setCurrent(1);
											checkPageButtons();
										}
									}
								});
							}
							{
								lbNext = new Label(composite1, SWT.NONE);
								lbNext.setText(">");
								lbNext.setBounds(69, 325, 13, 13);
								lbNext.addMouseListener(new MouseAdapter() {
									public void mouseDown(MouseEvent evt) {
										lbNext.setForeground(color(java.awt.Color.LIGHT_GRAY));
									}
									public void mouseUp(MouseEvent evt) {
										if (chart.getPage().getCount()>0)
										{
											chart.getPage().next();
											checkPageButtons();
										}
									}
								});
							}
							{
								lbLast = new Label(composite1, SWT.NONE);
								lbLast.setText(">>");
								lbLast.setBounds(94, 325, 17, 16);
								lbLast.addMouseListener(new MouseAdapter() {
									public void mouseDown(MouseEvent evt) {
										lbLast.setForeground(color(java.awt.Color.LIGHT_GRAY));
									}
									public void mouseUp(MouseEvent evt) {
										if (chart.getPage().getCount()>0)
										{
											chart.getPage().setCurrent(chart.getPage().getCount());
											checkPageButtons();
										}
									}
								});
							}
						}
					}
					{
						tabFormat = new TabItem(tabOptions, SWT.NONE);
						tabFormat.setText("Format");
						{
							tabComposite2 = new Composite(tabOptions, SWT.NONE);
							tabFormat.setControl(tabComposite2);
							tabComposite2.setLayout(null);
							{
								checkPrintBackground = new Button(tabComposite2, SWT.CHECK | SWT.LEFT);
								checkPrintBackground.setText("Print Background");
								checkPrintBackground.setBounds(16, 54, 104, 18);
								checkPrintBackground.addSelectionListener(new SelectionAdapter() {
									public void widgetSelected(SelectionEvent evt) {
										prn.setPrintPanelBackground(checkPrintBackground.getSelection());
									}
								});
							}
							{
								checkProportional = new Button(tabComposite2, SWT.CHECK | SWT.LEFT);
								checkProportional.setText("Proportional");
								checkProportional.setBounds(16, 94, 84, 18);
								checkProportional.addSelectionListener(new SelectionAdapter() {
									public void widgetSelected(SelectionEvent evt) {
										prn.setProportional(checkProportional.getSelection());
										chPreviewPanel1.redraw();
									}
								});
							}
							{
								checkGrayscale = new Button(tabComposite2, SWT.CHECK | SWT.LEFT);
								checkGrayscale.setText("Grayscale");
								checkGrayscale.setBounds(16, 74, 94, 18);
								checkGrayscale.addSelectionListener(new SelectionAdapter() {
									public void widgetSelected(SelectionEvent evt) {
										prn.setGrayscale(checkGrayscale.getSelection());
									}
								});
							}
						}
						tabOptions.setSelection(0);
					}
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void checkPageButtons()
	{
		if (chart.getPage().getCount()>0)
		{
			if (chart.getPage().getCurrent()==1)
			{
				lbPrevious.setEnabled(false);
			  lbNext.setEnabled(true);
			  lbFirst.setEnabled(false);
			  lbLast.setEnabled(true);
			}
			else if (chart.getPage().getCurrent()==(chart.getPage().getCount()))
			{
				lbPrevious.setEnabled(true);
			  lbNext.setEnabled(false);
			  lbFirst.setEnabled(true);
			  lbLast.setEnabled(false);
			}
			else
			{
				lbPrevious.setEnabled(true);
			  lbNext.setEnabled(true);
			  lbFirst.setEnabled(true);
			  lbLast.setEnabled(true);
			}
		}
		else
		{
		  lbPrevious.setEnabled(false);
		  lbNext.setEnabled(false);
		  lbFirst.setEnabled(false);
		  lbLast.setEnabled(false);
		}
		lbPrevious.setForeground(color(java.awt.Color.BLACK));
	  lbNext.setForeground(color(java.awt.Color.BLACK));
	  lbFirst.setForeground(color(java.awt.Color.BLACK));
	  lbLast.setForeground(color(java.awt.Color.BLACK));
		chPreviewPanel1.redraw();
	}
	
	private Color color(java.awt.Color value) {
		return new Color(getDisplay(), value.getRed(), value.getGreen(), value.getBlue());
	}
	
	private void loadPrinterSettings() {
		setting = true;

		if (prn != null) {
			printDocument1 = prn.getPrinterJob();

			if (prn.getLandscape()) {
				rbLandscape.setSelection(true);
				rbPortrait.setSelection(false);
			} else {
				rbPortrait.setSelection(true);
				rbLandscape.setSelection(false);
			}

			prn.getOptions();

			PrintService[] services = PrinterJob.lookupPrintServices();

			cbPrinter.removeAll();
			for (int t = 0; t < services.length; t++) {
				cbPrinter.add(services[t].getName());
			}

			if (printDocument1 != null) {
				for (int t = 0; t < services.length; t++) {
					if (printDocument1.getPrintService().getName() ==
						services[t].getName()) {
						cbPrinter.select(t);
					}
				}
			}

			if (cbPrinter.getSelectionIndex() != -1) {
			} else {
				bSetup.setEnabled(false);
				bPrint.setEnabled(false);
			}
			
			cbMarginUnits.removeAll();
      cbMarginUnits.add(Language.getString("Percent"));
      cbMarginUnits.add(Language.getString("172_Inch"));

			if (prn.getLandscape()) {
				rbLandscape.setSelection(true);
			} else {
				rbPortrait.setSelection(true);
			}
			checkProportional.setSelection(prn.getProportional());
			checkGrayscale.setSelection(prn.getGrayscale());

			if (prn.getProportional()) {
				com.steema.teechart.Rectangle tmpRect=prn.calcProportionalMargins(chart,prn.getPaperRect());
				prn.setMargins(new PrintMargins(chart,tmpRect.getLeft(),
						tmpRect.getTop(),tmpRect.getRight(),
						tmpRect.getBottom()));
			}
			
			checkPageButtons();

			com.steema.teechart.printer.PrintMargins m = prn.getMargins();

			setting = true;
			this.txtMarginTop.setText(String.valueOf((m.getTop())));
	    this.txtMarginLeft.setText(String.valueOf((m.getLeft())));
	    this.txtMarginRight.setText(String.valueOf((m.getRight())));
	    this.txtMarginBottom.setText(String.valueOf((m.getBottom())));

			checkPrintBackground.setSelection(prn.getPrintPanelBackground());
			cbMarginUnits.select(prn.getMarginUnits().getValue());
			setting = false;
		}

		setting = false;

	}

	private void RefreshView() {
		checkPageButtons();

		if (prn != null) {
			loadPrinterSettings();
		}

		if (previewPane != null) {
			chPreviewPanel1.redraw();
		}
	}
	
	private void rbPortraitWidgetSelected(SelectionEvent evt) {
		prn.getPrinterJob().defaultPage().setOrientation(PageFormat.PORTRAIT);
    prn.setLandscape(false);
    RefreshView();
	}
	
	private void rbLandscapeWidgetSelected(SelectionEvent evt) {
		prn.getPrinterJob().defaultPage().setOrientation(PageFormat.LANDSCAPE);
    prn.setLandscape(true);
    RefreshView();
	}
	
	private void slMarginLeftWidgetSelected(SelectionEvent evt) {
		upDateMarginSize(evt, txtMarginLeft);
    if (!setting) {
      prn.getMargins().setLeft(Integer.valueOf(txtMarginLeft.getText()));
      prn.setProportional(false);
      RefreshView();
    }
	}
	
	private void slMarginTopWidgetSelected(SelectionEvent evt) {
		upDateMarginSize(evt, txtMarginTop);
    if (!setting) {
      prn.getMargins().setTop(Integer.valueOf(txtMarginTop.getText()));
      prn.setProportional(false);
      RefreshView();
    }
	}
	
	private void slMarginRightWidgetSelected(SelectionEvent evt) {
		upDateMarginSize(evt, txtMarginRight);
    if (!setting) {
      prn.getMargins().setRight(Integer.valueOf(txtMarginRight.getText()));
      prn.setProportional(false);
      RefreshView();
    }
	}
	
	private void slMarginBottomWidgetSelected(SelectionEvent evt) {
		upDateMarginSize(evt, txtMarginBottom);
    if (!setting) {
      prn.getMargins().setBottom(Integer.valueOf(txtMarginBottom.getText()));
      prn.setProportional(false);
      RefreshView();
    }
  }
	
	private void upDateMarginSize(SelectionEvent evt, Text txtMargin)
	{
		int val = Integer.valueOf(txtMargin.getText());
    switch (evt.detail) {
    case SWT.ARROW_DOWN:
    	txtMargin.setText(String.valueOf(val+1));
      break;
    case SWT.ARROW_UP:
    	txtMargin.setText(String.valueOf(val-1));
      break;
    }
	}
	
	private void txtMarginLeftVerifyText(VerifyEvent evt) {
		verifyText(evt);
	}
	
	private void txtMarginTopVerifyText(VerifyEvent evt) {
		verifyText(evt);
	}
	
	private void txtMarginRightVerifyText(VerifyEvent evt) {
		verifyText(evt);
	}
	
	private void txtMarginBottomVerifyText(VerifyEvent evt) {
		verifyText(evt);
	}
	
	private void verifyText(VerifyEvent evt) {
    if ((evt.character == '\b') || (Character.isDigit(evt.character)) || (evt.character == SWT.NULL))
    	evt.doit = true;
    else
    	evt.doit = false;		
	}
	
	private void txtMarginBottomModifyText(ModifyEvent evt) {
		if (!setting) {
			prn.getMargins().setBottom(Integer.valueOf(txtMarginBottom.getText()));
			prn.setProportional(false);
			RefreshView();
		}
	}

	private void txtMarginLeftModifyText(ModifyEvent evt) {
		if (!setting) {
			prn.getMargins().setLeft(Integer.valueOf(txtMarginLeft.getText()));
			prn.setProportional(false);
			RefreshView();
		}	
	}

	private void txtMarginTopModifyText(ModifyEvent evt) {
		if (!setting) {
			prn.getMargins().setTop(Integer.valueOf(txtMarginTop.getText()));
			prn.setProportional(false);
			RefreshView();
		}	
	}

	private void txtMarginRightModifyText(ModifyEvent evt) {
		if (!setting) {
			prn.getMargins().setRight(Integer.valueOf(txtMarginRight.getText()));
			prn.setProportional(false);
			RefreshView();
		}	
	}

}
