/*
 * ButtonPageNavigator.java
 *
 * <p>Copyright  (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */

package com.steema.teechart.editors;

import com.steema.teechart.Chart;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;

/**
 *
 */
public class ButtonPageNavigator extends AbstractPageNavigator
    implements ActionListener {


    /**
     * Creates a new instance of ButtonPageNavigator
     */
    public ButtonPageNavigator() {
        super();
    }

    /**
     * Creates a new instance of ButtonPageNavigator
     * @param chart
     */
    public ButtonPageNavigator(Chart chart) {
        super.setChart(chart);
    }

    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if (source == firstButton) {
            page.setCurrent(1);
        } else if (source == prevButton) {
            page.previous();
        } else if (source == nextButton) {
            page.next();
        } else if (source == lastButton) {
            page.setCurrent(page.getCount());
        }
    }

    protected void initButtons() {
        firstButton = new JButton("<<");
        firstButton.addActionListener(this);
        prevButton = new JButton("<");
        prevButton.addActionListener(this);
        nextButton = new JButton(">");
        nextButton.addActionListener(this);
        lastButton = new JButton(">>");
        lastButton.addActionListener(this);
    }

    protected void init() {
        initButtons();
        this.add(firstButton);
        this.add(Box.createHorizontalStrut(HORIZONTAL_SPACE));
        this.add(prevButton);
        this.add(Box.createHorizontalStrut(HORIZONTAL_SPACE));
        this.add(nextButton);
        this.add(Box.createHorizontalStrut(HORIZONTAL_SPACE));
        this.add(lastButton);
        this.add(Box.createHorizontalGlue());
    }

    protected void syncNavigator() {
        firstButton.setEnabled(
                (page != null) && (page.getMaxPointsPerPage() > 0) && (page.getCurrent() != 1)
                );
        prevButton.setEnabled(
                (page != null) && (page.getMaxPointsPerPage() > 0) && (page.getCurrent() > 1)
                );
        nextButton.setEnabled(
                (page != null) && (page.getMaxPointsPerPage() > 0) && (page.getCurrent() < page.getCount())
                );
        lastButton.setEnabled(
                (page != null) && (page.getMaxPointsPerPage() > 0) && (page.getCurrent() != page.getCount())
                );
    }

    private JButton firstButton, prevButton, nextButton, lastButton;
    protected static final int HORIZONTAL_SPACE = 2;
}

