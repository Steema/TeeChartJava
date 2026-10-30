/*
 * AbstractPageNavigator.java
 *
 * <p>Copyright  (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */

package com.steema.teechart.editors;

import com.steema.teechart.Chart;
import com.steema.teechart.Page;
import com.steema.teechart.events.ChangeEvent;
import com.steema.teechart.events.ChangeListener;

import java.awt.Graphics;
import javax.swing.BoxLayout;
import javax.swing.JPanel;


/**
 *
 */
public abstract class AbstractPageNavigator extends JPanel {

    protected Chart chart;
    protected Page page;
    private ChangeListener pageListener = null;

    public AbstractPageNavigator() {
        super();
        this.setLayout( new BoxLayout(this, BoxLayout.X_AXIS) );
        pageListener = new ChangeListener() {
            public void stateChanged(ChangeEvent ce) {
                syncNavigator();
            }
        };
        init();
    }

    public AbstractPageNavigator(Chart chart) {
        this();
        setChart(chart);
    }

    public void setChart(Chart chart) {
        if (page != null) {
            page.removeChangeListener( pageListener );
        }
        if (chart != null) {
            this.chart = chart;
            page=chart.getPage();
            page.addChangeListener(pageListener);
        } else {
            chart = null;
            page = null;
        }
        repaint();
    }

    public void paint(Graphics g) {
        syncNavigator();
        super.paint(g);
    }

    abstract protected void init();

    abstract protected void syncNavigator();
}

