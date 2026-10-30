package com.steema.teechart;

import com.steema.teechart.events.ITeeEventListener;
import com.steema.teechart.languages.Language;
import com.steema.teechart.events.TeeEvent;
import com.steema.teechart.events.TeeMouseEvent;
import java.awt.*;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.steema.teechart.events.FrameworkMouseEvent;

//TODO MM import com.steema.teechart.editors.export.ExportPanel;
import com.steema.teechart.tools.Rotate;
import com.steema.teechart.misc.StringFormat;
import com.steema.teechart.misc.MathUtils;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Commander extends Composite implements ITeeEventListener {

	private static final String iconPath = "editors/images/commander/";

	private ToolBar commander;
	
	private int iMouseButton;
	private boolean iDragging;
	private int fOldX;
	private int fOldY;
	private double rotationFloat;
	private double elevationFloat;
	private boolean labelValues = false;
	private boolean showHints = true;

	Button bNormal;
	Button bRotate;
	Button bMove;
	Button bZoom;
	Button bDepth;
	Button bView3D;
	Button bEdit;
	Button bPrint;
	Button bCopy;
	Button bExport;
	
	private Chart chart;
	Label labelCaption = new Label();
	
	private int orientation;

	public Commander(Chart chart,Composite parent, int style) {
		this(parent, style);
		setChart(chart);

		bView3D.setSelection(chart.getAspect().getView3D());
	}

	public Chart getChart() {
		return chart;
	}

	public void setChart(Chart chart) {
		if (this.chart != null) {
			this.chart.removeListener(this);
		}

		this.chart = chart;

		if (this.chart != null) {
			if (chart.getListeners() != null) {
				chart.getListeners().add(this);
			}
			
			 bView3D.setSelection(chart.getAspect().getView3D());
			 bNormal.setSelection(true);
		}
	}

	/**
	 * @param parent
	 * @param style
	 */
	 public Commander(Composite parent, int style) {
		 
		super(parent, style);

		//this.setBackground(org.eclipse.draw2d.ColorConstants.cyan); 
		
		commander = new ToolBar(parent, SWT.HORIZONTAL | SWT.FLAT );
		bNormal = new Button(commander,SWT.TOGGLE | SWT.FLAT);
		bRotate = new Button(commander,SWT.TOGGLE | SWT.FLAT);
		bMove = new Button(commander,SWT.TOGGLE | SWT.FLAT );
		bZoom = new Button(commander,SWT.TOGGLE | SWT.FLAT );
		bDepth = new Button(commander,SWT.TOGGLE | SWT.FLAT);
		bView3D = new Button(commander,SWT.TOGGLE | SWT.FLAT);

		bPrint = new Button(commander,SWT.PUSH | SWT.FLAT);

		int buttonWidth = 35;
		int separatorWidth = 5;
		int tmpPos = 0;
		
		for (int i=0;i<commander.getChildren().length;i++)
		{
			if (commander.getChildren()[i].getClass() == Button.class)
			{
				commander.getChildren()[i].setBounds(tmpPos, 0, buttonWidth, buttonWidth);
				tmpPos = tmpPos + buttonWidth;
			}
		}
		
		orientation = style;

		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		setHints(true);

    	setButtonIcon(bNormal, "Normal");
    	setButtonIcon(bRotate, "Rotate");
    	setButtonIcon(bMove, "Move");
    	setButtonIcon(bZoom, "Zoom");
    	setButtonIcon(bDepth, "Depth");
    	setButtonIcon(bView3D, "View3D");
    	setButtonIcon(bPrint, "Print");

		Listener selectionListener = new Listener() {
			public void handleEvent(Event event) {
				Button item = (Button)event.widget;
				if (chart != null) {
					if (item==bView3D)
					  chart.getAspect().setView3D(bView3D.getSelection());
				}
				buttonMouseUp(event);
			}
		};

		Listener pushButtonListener = new Listener() {
			public void handleEvent(Event event) {
				Button item = (Button)event.widget;
				commander.getDisplay().syncExec(
						  new Runnable() {
							    public void run(){
							    	chart.getPrinter().preview();
							    }
							  });
			}
		};

		bPrint.addListener(SWT.Selection, pushButtonListener);
		bView3D.addListener(SWT.Selection, selectionListener);
		bDepth.addListener(SWT.Selection, selectionListener);
		bRotate.addListener(SWT.Selection, selectionListener);
		bMove.addListener(SWT.Selection, selectionListener);
		bZoom.addListener(SWT.Selection, selectionListener);
		bNormal.addListener(SWT.Selection, selectionListener);

	 }
	 
	 public void setBounds(int left, int top, int width, int height)
	 {
		 commander.setBounds(left,  top,  width,  height);
	 }
	 
	 public void setBounds(Rectangle r)
	 {
		 commander.setBounds(r.x,r.y,r.width,r.height);
	 }
	 
	 private void setButtonIcon(Button button, String iconName) {
		 
		 java.io.InputStream stream=Commander.class.getResourceAsStream(iconPath 
				                                             + iconName + ".gif");
		 
		 Image icon = new Image(this.getDisplay(), stream);
		 button.setImage(icon);
	 }

	 private void jbInit() throws Exception {

		 Listener CopyListener = new Listener() {
				public void handleEvent(Event event) {
					ToolItem item = (ToolItem)event.widget;
					chart.getExport().getImage().copyToClipboard();
				}
			};
			
			Listener PrintListener = new Listener() {
				public void handleEvent(Event event) {
				}
			};
			
			Listener ExportListener = new Listener() {
				public void handleEvent(Event event) {
					ToolItem item = (ToolItem)event.widget;
				}
			};
			
		 bPrint.addListener(SWT.Selection,PrintListener);
	 }

	 public boolean getLabelValues() {
		 return labelValues;
	 }

	 /**
	  * When true shows status text on ToolBar
	  *
	  * @param value boolean
	  */
	 public void setLabelValues(boolean value) {
		 labelValues = value;
		 if (labelValues == false) {
			 labelCaption.setText(null);
		 }
		 this.redraw();
	 }


	 public boolean getHints() {
		 return showHints;
	 }

	 /**
	  * When true shows Hints over buttons on ToolBar
	  *
	  * @param value boolean
	  */
	 public void setHints(boolean value) {
		 if (value) {
			 bNormal.setToolTipText(Language.getString("CommanMsgNormal"));
			 bRotate.setToolTipText(Language.getString("CommanMsgRotate"));
			 bMove.setToolTipText(Language.getString("CommanMsgMove"));
			 bZoom.setToolTipText(Language.getString("CommanMsgZoom"));
			 bDepth.setToolTipText(Language.getString("CommanMsgDepth"));
			 bView3D.setToolTipText(Language.getString("CommanMsg3D"));
			 bPrint.setToolTipText(Language.getString("CommanMsgPrint"));
			 showHints = true;
		 } else {
			 bNormal.setToolTipText(null);
			 bRotate.setToolTipText(null);
			 bMove.setToolTipText(null);
			 bZoom.setToolTipText(null);
			 bDepth.setToolTipText(null);
			 bView3D.setToolTipText(null);
			 bPrint.setToolTipText(null);
			 showHints = false;
		 }
	 }

	 /**
	  * When true activates ToolBar
	  *
	  * @param value boolean
	  */
	 public void setEnabled(boolean value) {
		 super.setEnabled(value);
		 for (int i = 0; i < commander.getItems().length; i++) {
			 commander.getItems()[i].setEnabled(value);
		 }
	 }

	 private boolean isButtonUp(Button aButton) {

		 return !(aButton.getSelection());
	 }
	 
	 private boolean isButtonDown(Button aButton) {

		 return (aButton.isEnabled()) && (aButton.getSelection());
	 }

	 private boolean doPanelMouse() {
		 return isButtonDown(bRotate) ||
		 isButtonDown(bDepth) ||
		 isButtonDown(bMove) ||
		 isButtonDown(bZoom);
	 }

	 private int mButton = 0;

	 public void invokeEvent(TeeEvent event) {
		 if (event instanceof TeeMouseEvent) {
			 TeeMouseEvent e = ((TeeMouseEvent) event);

			 if (e.event.getButton() != 0) {
				 mButton = e.event.getButton();
			 }

			 if (e.event.getID() == FrameworkMouseEvent.MOUSE_MOVED) {
				 doMouseMove(e.event.getX(), e.event.getY(), mButton);
			 } else if (e.event.getID() == FrameworkMouseEvent.MOUSE_PRESSED) {
				 doMouseDown(e.event.getX(), e.event.getY(), mButton);
			 } else if (e.event.getID() == FrameworkMouseEvent.MOUSE_RELEASED) {
				 doMouseUp(e.event.getX(), e.event.getY(), mButton);
				 mButton = 0;
			 }
		 }
		 chart.setCancelMouse(doPanelMouse());
	 }

	 private void doNormal() {
		 int tmp;
		 return;
	 }

	 private void set3D() {
		 chart.getAspect().setView3D(true);
		 bView3D.setSelection(true);
	 }

	 private boolean allowFullRotation() {
		 return chart.getGraphics3D().getSupportsFullRotation();
	 }

	 private double correctAngle(double aAngle) {
		 double tmpResult = aAngle;

		 if (tmpResult > 360) {
			 tmpResult = tmpResult - 360;
		 } else
			 if (tmpResult < 0) {
				 tmpResult = 360 + tmpResult;
			 }

		 return tmpResult;
	 }

	 private void doMove(int x, int y, int mButton) {
		 set3D();

		 chart.getAspect().setHorizOffset(chart.getAspect().getHorizOffset() +
				 (x - fOldX));
		 chart.getAspect().setVertOffset(chart.getAspect().getVertOffset() +
				 (y - fOldY));

		 fOldX = x;
		 fOldY = y;
	 }

	 private double distance(double x, double y) {
		 return Math.sqrt(MathUtils.pow(x, 2) + MathUtils.pow(y, 2));
	 }


	 private int calcDistPercent(int aPercent, int aWidth, int aHeight, int x,
			 int y) {
		 return MathUtils.round(1.0 * aPercent *
				 distance(fOldX - x, fOldY - y) /
				 distance(aWidth, aHeight));
	 }

	 private void doDepth(int x, int y, int mButton) {

		 set3D();

		 int tmp = calcDistPercent(200, chart.getWidth(), chart.getHeight(), x,
				 y);

		 if ((tmp >= 1)) {
			 chart.getAspect().setChart3DPercent(Math.min(100, tmp));
		 }
	 }

	 private void doZoom(int x, int y, int mButton) {

		 set3D();

		 double tmp = (10.0 * (fOldY - y) /
				 distance(chart.getWidth(), chart.getHeight()));

		 double tmp2 = (10.0 * tmp * chart.getAspect().getZoom() / 100.0);

		 if (tmp > 0) {
			 chart.getAspect().setZoom((int) (chart.getAspect().getZoom() +
					 MathUtils.round(Math.max(1, tmp2))));
		 } else
			 if (tmp < 0) {
				 chart.getAspect().setZoom((int) (Math.max(1,
						 chart.getAspect().getZoom() +
						 MathUtils.round(Math.min( -1, tmp2)))));
			 }

		 fOldY = y;
	 }

	 private void doRotate(int x, int y, int mButton) {
		 set3D();
		 chart.getAspect().setOrthogonal(false);

		 double tmpX = 90.0 * (x - fOldX) / (chart.getWidth() * 1.0);
		 double tmpY = 90.0 * (fOldY - y) / (chart.getHeight() * 1.0);

		 if (allowFullRotation()) {
			 if (mButton == 2) /*BUTTON2*/ {
				 chart.getAspect().setTilt((int) correctAngle(chart.getAspect().
						 getTilt() + tmpY));
			 } else {
				 chart.getAspect().setRotation(MathUtils.round(chart.getAspect().
						 getRotation() + tmpX));

				 chart.getAspect().setElevation(MathUtils.round(correctAngle(
						 chart.getAspect().getElevation() + tmpY)));
			 }
		 } else {
			 chart.getAspect().setRotation(MathUtils.round(Rotate.
					 rotationChange(allowFullRotation(),
							 chart.getAspect().getRotation(), tmpX)));

			 chart.getAspect().setElevation(MathUtils.round(Rotate.
					 elevationChange(allowFullRotation(),
							 chart.getAspect().getElevation(), tmpY)));
		 }

		 fOldX = x;
		 fOldY = y;
	 }

	 public void doMouseMove(int x, int y, int mButton) {
		 if (iDragging) {
			 iDragging = false;

			 if ((doPanelMouse()) && (mButton == 3)) { //BUTTON3
				 doMove(x, y, mButton);
			 }
			 if (bNormal.getSelection()) {
				 doNormal();
			 }
			 if (bRotate.getSelection()) {
				 doRotate(x, y, mButton);
			 }
			 if (bMove.getSelection()) {
				 doMove(x, y, mButton);
			 }
			 if (bZoom.getSelection()) {
				 doZoom(x, y, mButton);
			 }
			 if (bDepth.getSelection()) {
				 doDepth(x, y, mButton);
			 }

			 if ((labelValues) &&
					 (orientation != SWT.VERTICAL)) {
				 showValues();
			 }

			 iDragging = true;
		 }
	 }
	 
	 private void setLabelCaption(String aCaption) {
		 String tmp;
		 String spacer = "    ";

		 tmp = aCaption;

		 if ((labelCaption.isValid()) && (labelValues) && (orientation != SWT.VERTICAL)) {
			 labelCaption.setVisible(true);
			 labelCaption.setText(spacer + tmp);
		 } else {
			 labelCaption.setVisible(false);
		 }
		 labelCaption.repaint();
	 }

	 private void showValues() {
		 String tmp = "";

		 if (isButtonDown(bRotate)) {
			 tmp = StringFormat.format(Language.getString("CommanMsgRotating"),
					 new Object[] {String.valueOf(chart.
							 getAspect().getRotation()),
							 String.valueOf(chart.getAspect().
									 getElevation())});
		 } else
			 if (isButtonDown(bMove)) {
				 tmp = StringFormat.format(Language.getString("CommanMsgMoving"),
						 new Object[] {String.valueOf(chart.
								 getAspect().getHorizOffset()),
								 String.valueOf(chart.getAspect().
										 getVertOffset())});
			 } else
				 if (isButtonDown(bZoom)) {
					 tmp = StringFormat.format(Language.getString("CommanMsgZooming"),
							 new Object[] {String.valueOf(chart.
									 getAspect().getZoom())});
				 }

		 setLabelCaption(tmp);
	 }


	 public void doMouseDown(int x, int y, int mButton) {
		 iDragging = true;
		 fOldX = x;
		 fOldY = y;
	 }

	 public void doMouseUp(int x, int y, int mButton) {
		 iDragging = false;
	 }
	 
	 private void buttonMouseUp(Event evt) {
		 
		 //TODO add your code for bDepth.mouseUp
		 //if (evt.widget == bDepth)
		 {
			 if (evt.widget == bDepth)
			 {
				 if (bDepth.getSelection())
				 {
					 bView3D.setSelection(true);
					 chart.getAspect().setView3D(true);
					 bRotate.setSelection(false);
					 bZoom.setSelection(false);
					 bNormal.setSelection(false);
					 bMove.setSelection(false);
				 }
				 else
					 bDepth.setSelection(false);
			 }

			 else if (evt.widget == bRotate)
			 {
				 if (bRotate.getSelection())
				 {
					 bView3D.setSelection(true);
					 chart.getAspect().setView3D(true);
					 bDepth.setSelection(false);
					 bZoom.setSelection(false);
					 bNormal.setSelection(false);
					 bMove.setSelection(false);
				 } 
				 else
					 bRotate.setSelection(false);
			 }
			 else if (evt.widget == bMove)
			 {
				 if (bMove.getSelection())
				 {
					 bView3D.setSelection(true);
					 chart.getAspect().setView3D(true);
					 bDepth.setSelection(false);
					 bZoom.setSelection(false);
					 bNormal.setSelection(false);
					 bRotate.setSelection(false);
				 }
				 else
					 bMove.setSelection(false);
			 }
			 else if (evt.widget == bZoom)
			 {
				 if (bZoom.getSelection())
				 {
					 bView3D.setSelection(true);
					 chart.getAspect().setView3D(true);
					 bDepth.setSelection(false);
					 bRotate.setSelection(false);
					 bNormal.setSelection(false);
					 bMove.setSelection(false);
				 }
				 else
					 bZoom.setSelection(false);
			 }
			 else if (evt.widget == bNormal)
			 {
				 if (bNormal.getSelection())
				 {
					 bView3D.setSelection(chart.getAspect().getView3D());
					 bDepth.setSelection(false);
					 bZoom.setSelection(false);
					 bRotate.setSelection(false);
					 bMove.setSelection(false);
				 }
				 else
					 bNormal.setSelection(false);
			 }
		 }
		 if (evt.widget == bView3D)
		 {
			 if (chart != null) {
				 chart.getAspect().setView3D(bView3D.getSelection());
				 if (bView3D.getSelection())
				 {
					 bNormal.setSelection(false);
					 bDepth.setSelection(false);
					 bZoom.setSelection(false);
					 bRotate.setSelection(false);
					 bMove.setSelection(false);
				 }
				 else
					 bView3D.setSelection(false);
			 }
		 }
	 }
}

