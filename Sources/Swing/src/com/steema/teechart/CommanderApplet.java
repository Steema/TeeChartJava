package com.steema.teechart;

import java.applet.*;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class CommanderApplet extends Applet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Commander commander=new Commander();

    public CommanderApplet() {
        super();
        setLayout(new BorderLayout());
        add(commander, BorderLayout.CENTER);

        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);
        enableEvents(AWTEvent.MOUSE_WHEEL_EVENT_MASK);
        enableEvents(AWTEvent.COMPONENT_EVENT_MASK);
    }

    public Dimension getPreferredSize() {
        return commander.getSize();
    }

    public boolean isDoubleBuffered() {
        return false;
    }

    public void setChart(Chart value) {
        commander.setChart(value);
    }

    public void setChart(TChartApplet value) {
        commander.setChart(value.getChart());
    }

    public Chart getChart() {
        return commander.getChart();
    }
}

