/*
 * SliderPageNavigator.java
 *
 * Created on 19 april 2007, 0:24
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */

package com.steema.teechart.editors;

import com.steema.teechart.Chart;
import javax.swing.Box;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 */
public class SliderPageNavigator extends AbstractPageNavigator
    implements ChangeListener {

    /**
     * Creates a new instance of SliderPageNavigator
     */
    public SliderPageNavigator() {
        super();
    }

    /**
     * Creates a new instance of SliderPageNavigator
     * @param chart
     */
    public SliderPageNavigator(Chart chart) {
        super.setChart(chart);
    }

    public void stateChanged(ChangeEvent ce) {
        Object source = ce.getSource();
        if (source == pageSlider) {
            if (!pageSlider.getValueIsAdjusting()) {
                page.setCurrent((int)pageSlider.getValue());
            }
        }
    }

    protected void initSlider() {
        pageSlider = new JSlider(JSlider.HORIZONTAL, 1, 1 , 1);
        pageSlider.addChangeListener(this);
    }

    protected void init() {
        initSlider();
        this.add(pageSlider);
        this.add(Box.createHorizontalGlue());
    }

    protected void syncNavigator() {
        if (page != null) {
            if ((int)pageSlider.getValue() != page.getCount()) {
                pageSlider.setMaximum(page.getCount());
            }
            pageSlider.setValue(page.getCurrent());
        }
        pageSlider.setEnabled(
           (page != null) && (page.getMaxPointsPerPage() > 0)
        );
    }

    private JSlider pageSlider;
}

