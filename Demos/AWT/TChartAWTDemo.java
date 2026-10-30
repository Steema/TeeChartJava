package com.steema.teechart.awt;

import java.awt.Frame;
import java.awt.AWTEvent;
import java.awt.event.WindowEvent;

import com.steema.teechart.TChart;
import com.steema.teechart.styles.Bar;

/**
 * <p>Title: TeeChart demo for Java AWT</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) Steema Software 2005-2006</p>
 *
 * <p>Company: Steema Software</p>
 *
 * @author db
 * @version 1.0
 */
public class TChartAWTDemo extends Frame {

    private TChart chart = new TChart();

    public TChartAWTDemo() {

        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        setTitle("TeeChart for Java AWT Frame example");

        setSize(600, 400);

        chart.addSeries(new Bar());

        chart.getSeries(0).fillSampleValues();

        add(chart);
    }

    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            System.exit(0);
        }
    }

}


class MainApp {

    public static void main(String[] args) {
        TChartAWTDemo tchartawtdemo = new TChartAWTDemo();
        tchartawtdemo.setVisible(true);
    }
}
