package com.steema.teechart;

import com.steema.teechart.events.ITeeEventListener;
import com.steema.teechart.languages.Language;
import com.steema.teechart.events.TeeEvent;
import com.steema.teechart.events.TeeMouseEvent;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.steema.teechart.editors.*;
import com.steema.teechart.editors.export.ExportPanel;
import com.steema.teechart.tools.Rotate;
import com.steema.teechart.misc.StringFormat;
import com.steema.teechart.misc.Utils;

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
public class Commander extends JToolBar implements ITeeEventListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String iconPath = "editors/images/commander/";

    private int iMouseButton;
    private boolean iDragging;
    private int fOldX;
    private int fOldY;
    private double rotationFloat;
    private double elevationFloat;
    private boolean labelValues = false;
    private boolean showHints = true;

    JToggleButton bNormal = new JToggleButton();
    JToggleButton bRotate = new JToggleButton();
    JToggleButton bMove = new JToggleButton();
    JToggleButton bZoom = new JToggleButton();
    JToggleButton bDepth = new JToggleButton();
    JToggleButton bView3D = new JToggleButton();
    JButton bEdit = new JButton();
    JButton bPrint = new JButton();
    JButton bCopy = new JButton();
    JButton bExport = new JButton();
    private Chart chart;
    JLabel labelCaption = new JLabel();

    public Commander(Chart chart) {
        this();
        setChart(chart);

        bView3D.setSelected(chart.getAspect().getView3D());
    }
    /**
     * Associates the TeeCommander Component with a TCustomPanel
     * (TChart or TDraw3D). 
     * 
     * @return
     */
    public Chart getChart() {
        return chart;
    }
    /**
     * Associates the TeeCommander Component with a TCustomPanel
     * (TChart or TDraw3D). 
     * 
     * @param chart
     */
    public void setChart(Chart chart) {
        if (this.chart != null) {
            this.chart.removeListener(this);
        }

        this.chart = chart;

        if (this.chart != null) {
            if (chart.getListeners() != null) {
                chart.getListeners().add(this);
            }
        }
    }

    public Commander() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        setHints(true);

        setIcon(bNormal, "Normal");
        setIcon(bRotate, "Rotate");
        setIcon(bMove, "Move");
        setIcon(bZoom, "Zoom");
        setIcon(bDepth, "Depth");
        setIcon(bView3D, "View3D");
        setIcon(bEdit, "Edit");
        setIcon(bPrint, "Print");
        setIcon(bCopy, "Copy");
        setIcon(bExport, "Export");

        bEdit.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                ChartEditor.editChart(chart);
            }
        });

        bView3D.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (chart != null) {
                    chart.getAspect().setView3D(bView3D.isSelected());
                }
            }
        });

        ButtonGroup group = new ButtonGroup();
        group.add(bNormal);
        group.add(bRotate);
        group.add(bMove);
        group.add(bZoom);
        group.add(bDepth);

        setSize(512, 28);

    }

    private void setIcon(AbstractButton button, String iconName) {
        button.setIcon(new ImageIcon(Commander.class.getResource(iconPath +
                iconName + ".gif")));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setMargin(new Insets(2, 2, 2, 2));
    }

    private void jbInit() throws Exception {

        int tmpPos = 0;
        int buttonWidth = 35;
        int separatorWidth = 5;

        bCopy.addActionListener(new Commander_bCopy_actionAdapter(this));
        bPrint.addActionListener(new Commander_bPrint_actionAdapter(this));
        bExport.addActionListener(new Commander_bExport_actionAdapter(this));
        this.add(bNormal);
        this.addSeparator();
        this.add(bRotate);
        this.add(bMove);
        this.add(bZoom);
        this.add(bDepth);
        this.add(bView3D);
        this.addSeparator();
        this.add(bEdit);
        this.add(bPrint);
        this.add(bCopy);
        this.add(bExport);
        for (int i = 0; i < this.getComponents().length; i++) {
            if ((getComponents()[i].getClass() == JToggleButton.class) ||
                (getComponents()[i].getClass() == JButton.class)) {
                tmpPos = tmpPos + buttonWidth;
            } else if (getComponents()[i].getClass() == JToolBar.Separator.class) {
                tmpPos = tmpPos + separatorWidth;
            }
        }
        labelCaption.setLocation(tmpPos, 5);
        this.add(labelCaption);
    }

    public void bCopy_actionPerformed(ActionEvent e) {
        chart.getExport().getImage().copyToClipboard();
    }

    public void bPrint_actionPerformed(ActionEvent e) {
        PreviewDialog d = new PreviewDialog(chart);
        d.setModal(true);
        d.setVisible(true);
    }

    public void bExport_actionPerformed(ActionEvent e) {
        ExportPanel.showModal(chart);
    }
    /**
     *  
     * Enables/disables the display of TeeCommander's advisory status messages,
     * eg. Rotation and Elevation angles. <br><br>
     * Default value: true
     * 
     * @return
     */
    public boolean getLabelValues() {
        return labelValues;
    }

    /**
     * When true shows status text on ToolBar<br><br>
     * Enables/disables the display of TeeCommander's advisory status messages,
     * eg. Rotation and Elevation angles. <br><br>
     * Default value: true
     * 
     * @param value boolean
     */
    public void setLabelValues(boolean value) {
        labelValues = value;
        if (labelValues == false) {
            labelCaption.setText(null);
        }
        this.repaint();
    }

    /**
     * When true shows Hints over buttons on ToolBar
     * 
     * @return
     */
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
            bEdit.setToolTipText(Language.getString("CommanMsgEdit"));
            bPrint.setToolTipText(Language.getString("CommanMsgPrint"));
            bCopy.setToolTipText(Language.getString("CommanMsgCopy"));
            bExport.setToolTipText(Language.getString("CommanMsgSave"));
            showHints = true;
        } else {
            bNormal.setToolTipText(null);
            bRotate.setToolTipText(null);
            bMove.setToolTipText(null);
            bZoom.setToolTipText(null);
            bDepth.setToolTipText(null);
            bView3D.setToolTipText(null);
            bEdit.setToolTipText(null);
            bPrint.setToolTipText(null);
            bCopy.setToolTipText(null);
            bExport.setToolTipText(null);
            showHints = false;
        }
    }
    /**
     * Returns whether the ToolBar is activated or not.
     * @return
     */
    public boolean getEnabled() {
        return this.isEnabled();
    }

    /**
     * When true activates ToolBar
     *
     * @param value boolean
     */
    public void setEnabled(boolean value) {
        super.setEnabled(value);
        for (int i = 0; i < this.getComponents().length; i++) {
            getComponents()[i].setEnabled(value);
        }
    }

    private boolean IsButtonUp(JToggleButton aButton) {

        return ((!(aButton.isDisplayable())) || (aButton.isSelected()));
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

            if (e.event.getID() == MouseEvent.MOUSE_DRAGGED) {
                doMouseMove(e.event.getX(), e.event.getY(), mButton);
            } else if (e.event.getID() == MouseEvent.MOUSE_PRESSED) {
                doMouseDown(e.event.getX(), e.event.getY(), mButton);
            } else if (e.event.getID() == MouseEvent.MOUSE_RELEASED) {
                doMouseUp(e.event.getX(), e.event.getY(), mButton);
                mButton = 0;
            }
        }
        chart.setCancelMouse(doPanelMouse());
    }

    private boolean isButtonDown(JToggleButton button) {
        return button.isSelected();
    }

    private void doNormal() {
        int tmp;
        //MM todo
        /*
                   if (iDraggingIndex!=-1)
                   {
          if Assigned(IPieSeries) then
                     With IPieSeries do
                     begin
          tmp:=Math.Min(100,CalcDistPercent(100,CircleWidth,CircleHeight));
          ExplodedSlice[FDraggingIndex]:=tmp;
                   }*/
        return;
    }

    private void set3D() {
        chart.getAspect().setView3D(true);
        bView3D.setSelected(true);
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
        return Math.sqrt(Utils.pow(x, 2) + Utils.pow(y, 2));
    }


    private int calcDistPercent(int aPercent, int aWidth, int aHeight, int x,
                                int y) {
        return Utils.round(1.0 * aPercent *
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
                                             Utils.round(Math.max(1, tmp2))));
        } else
        if (tmp < 0) {
            chart.getAspect().setZoom((int) (Math.max(1,
                    chart.getAspect().getZoom() +
                    Utils.round(Math.min( -1, tmp2)))));
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
                chart.getAspect().setRotation(Utils.round(chart.getAspect().
                        getRotation() + tmpX));

                chart.getAspect().setElevation(Utils.round(correctAngle(
                        chart.getAspect().getElevation() + tmpY)));
            }
        } else {
            chart.getAspect().setRotation(Utils.round(Rotate.
                    rotationChange(allowFullRotation(),
                                   chart.getAspect().getRotation(), tmpX)));

            chart.getAspect().setElevation(Utils.round(Rotate.
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
            if (isButtonDown(bNormal)) {
                doNormal();
            }
            if ((isButtonDown(bRotate))) {
                doRotate(x, y, mButton);
            }
            if (isButtonDown(bMove)) {
                doMove(x, y, mButton);
            }
            if (isButtonDown(bZoom)) {
                doZoom(x, y, mButton);
            }
            if (isButtonDown(bDepth)) {
                doDepth(x, y, mButton);
            }

            if ((labelValues) &&
                (this.getOrientation() != SwingConstants.VERTICAL)) {
                showValues();
            }

            iDragging = true;
        }
    }

    public void setOrientation( int o )
    {
        super.setOrientation(o);
        if (getOrientation() == SwingConstants.VERTICAL)
            setLabelCaption(labelCaption.getText()); //provoke hide
    }

    private void setLabelCaption(String aCaption) {
        String tmp;
        String spacer = "    ";

        tmp = aCaption;
        //MM todo if ( Assigned(FOnSetLabel) ) FOnSetLabel(this,tmp); /* 5.02 */

        if ((labelCaption.isValid()) && (labelValues) && (this.getOrientation() != SwingConstants.VERTICAL)) {
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

        //MM todo
        /*  else
          if ( IsButtonDown(bDepth) )
          {
            if ( FPanel is TCustomAxisPanel )
            With TCustomAxisPanel(FPanel) do
              tmp=Format(TeeCommanMsg_Depthing,[Chart3DPercent])*/
        //}
        /*  else
           if ( IsButtonDown(bNormal) && (iDraggingIndex!=-1) )
           {
             if ( Assigned(IPieSeries) )
           tmp=Format( TeeCommanMsg_PieExploding,
         [FDraggingIndex,IPieSeries.ExplodedSlice[FDraggingIndex]])
             else
           tmp="";
          }
           else
           if ( Assigned(FLabel) ) tmp="";*/

        setLabelCaption(tmp);
        //  if ( Assigned(FLabel) && FLabel.Visible ) FLabel.Update;*/
    }


    public void doMouseDown(int x, int y, int mButton) {
        iDragging = true;
        fOldX = x;
        fOldY = y;
    }

    public void doMouseUp(int x, int y, int mButton) {
        iDragging = false;
    }

}


final class Commander_bCopy_actionAdapter implements ActionListener {
    private Commander adaptee;
    Commander_bCopy_actionAdapter(Commander adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bCopy_actionPerformed(e);
    }
}


class Commander_bPrint_actionAdapter implements ActionListener {
    private Commander adaptee;
    Commander_bPrint_actionAdapter(Commander adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bPrint_actionPerformed(e);
    }
}


class Commander_bExport_actionAdapter implements ActionListener {
    private Commander adaptee;
    Commander_bExport_actionAdapter(Commander adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bExport_actionPerformed(e);
    }
}

