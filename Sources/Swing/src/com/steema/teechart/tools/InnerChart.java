/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steema.teechart.tools;

import com.steema.teechart.BevelStyle;
import com.steema.teechart.TChart;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.events.FrameworkMouseEvent;
import com.steema.teechart.Rectangle;

/**
 *
 * @author Shunyata Kharg
 */
public class InnerChart extends TChart {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SubChart iParent;

    public InnerChart(SubChart parent) {
        iParent = parent;
        getLegend().setVisible(false);
        getHeader().getFont().setSize(7);
        getFooter().setVisible(false);
        getSubHeader().setVisible(false);
        getSubFooter().setVisible(false);

        getZoom().getPen().setVisible(true);
        getZoom().getPen().setStyle(DashStyle.DASH);
        getPanel().setMarginBottom(0);
        getPanel().setMarginLeft(0);
        getPanel().setMarginRight(0);
        getPanel().getBevel().setOuter(BevelStyle.NONE);
        getPanel().getBevel().setInner(BevelStyle.NONE);
        setBounds(Rectangle.fromLTRB(50, 50, 200, 150));
    }

    public InnerChart() {
        this(null);
    }

    @Override
    public void doInvalidate() {
        if (iParent != null && iParent.getITool() != null && iParent.getITool().getChart() != null && iParent.getITool().getChart().getParent() != null) {
            iParent.getITool().getChart().getParent().doInvalidate();
        }
    }

    public void fireMouseEvent(FrameworkMouseEvent e) {
        this.processMouseEvent(e);
        this.processMouseMotionEvent(e);
    }

    public SubChart getIParent() {
        return iParent;
    }
}
