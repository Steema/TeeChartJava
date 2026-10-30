package com.steema.teechart.editors;

import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JSlider;

import com.steema.teechart.Chart;
import com.steema.teechart.Page;
import com.steema.teechart.languages.Language;



/**
 *
 * <p>Title: PagingEditor class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class PagingEditor extends JPanel {


    private Chart chart;
    private Page page;
    JLabel jLabel1 = new JLabel();
    JSpinner jSpinner1 = new JSpinner();
    JCheckBox jCheckBox1 = new JCheckBox();
    JCheckBox jCheckBox2 = new JCheckBox();
    JCheckBox cbScaleLast = new JCheckBox();
    JCheckBox cbAutoScale = new JCheckBox();
    JLabel labelPages = new JLabel();

    public PagingEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    final private ChangeListener sMaxPoints = new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
            page.setMaxPointsPerPage(((Integer) jSpinner1.getModel().getValue()).
                                     intValue());
            setLabelPages();
            sPages.setMaximum(page.getCount());
        }
    };
    JSlider sPages = new JSlider();

    public PagingEditor(Chart chart) {
        this();
        this.chart = chart;
        page = chart.getPage();

        jSpinner1.setValue(new Integer(page.getMaxPointsPerPage()));

        jSpinner1.addChangeListener(sMaxPoints);

        jCheckBox1.setSelected(chart.getLegend().getCurrentPage());
        cbScaleLast.setSelected(chart.getPage().getScaleLastPage());

        sPages.setMinimum(1);
        sPages.setMaximum(page.getCount());
        sPages.setValue(1);

        setLabelPages();
    }

    private void setLabelPages() {
        labelPages.setText(Language.getString("Page") + Integer.toString(page.getCurrent()) +
                           Language.getString("of") + Integer.toString(page.getCount()));
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jLabel1.setDisplayedMnemonic(Language.getString("P").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(jSpinner1);
        jLabel1.setText(Language.getString("Points_per_page"));
        jLabel1.setBounds(new Rectangle(20, 11, 148, 29));
        jSpinner1.setBounds(new Rectangle(177, 15, 74, 25));
        jCheckBox1.setMnemonic(Language.getString("C").charAt(0));
        jCheckBox1.setText(Language.getString("Current_page_Legend"));
        jCheckBox1.setBounds(new Rectangle(94, 67, 186, 22));
        jCheckBox1.addActionListener(new PagingEditor_jCheckBox1_actionAdapter(this));
        cbScaleLast.setMnemonic(Language.getString("S").charAt(0));
        cbScaleLast.setText(Language.getString("Scale_Last_page"));
        cbScaleLast.setBounds(new Rectangle(94, 92, 175, 23));
        cbScaleLast.addActionListener(new
                                      PagingEditor_cbScaleLast_actionAdapter(this));
        cbAutoScale.setMnemonic(Language.getString("A").charAt(0));
        cbAutoScale.setText(Language.getString("AutoScale"));
        cbAutoScale.setBounds(new Rectangle(94, 117, 175, 23));
        cbAutoScale.addActionListener(new
                                      PagingEditor_cbAutoScale_actionAdapter(this));
        labelPages.setBounds(new Rectangle(121, 142, 176, 14));
        sPages.setPaintTicks(true);
        sPages.setBounds(new Rectangle(96, 165, 234, 40));
        sPages.addChangeListener(new PagingEditor_sPages_changeAdapter(this));
        this.add(jSpinner1);
        this.add(jCheckBox1);
        this.add(cbAutoScale);
        this.add(jLabel1);
        this.add(cbScaleLast);
        this.add(labelPages);
        this.add(sPages);
    }

    public void jCheckBox1_actionPerformed(ActionEvent e) {
        chart.getLegend().setCurrentPage(jCheckBox1.isSelected());
    }

    public void jCheckBox2_actionPerformed(ActionEvent e) {
        chart.getLegend().setCurrentPage(jCheckBox1.isSelected());
    }
    public void cbScaleLast_actionPerformed(ActionEvent e) {
        chart.getPage().setScaleLastPage(cbScaleLast.isSelected());
    }
    
    public void cbAutoScale_actionPerformed(ActionEvent e) {
        chart.getPage().setAutoScale(cbAutoScale.isSelected());
    }

    public void sPages_stateChanged(ChangeEvent e) {
        chart.getPage().setCurrent(sPages.getValue());
        setLabelPages();
    }
}
class PagingEditor_sPages_changeAdapter implements ChangeListener {
    private PagingEditor adaptee;
    PagingEditor_sPages_changeAdapter(PagingEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.sPages_stateChanged(e);
    }
}


final class PagingEditor_jCheckBox1_actionAdapter implements ActionListener {
    private PagingEditor adaptee;
    PagingEditor_jCheckBox1_actionAdapter(PagingEditor adaptee) {
        this.adaptee = adaptee;
    }

   
    public void actionPerformed(ActionEvent e) {
        adaptee.jCheckBox1_actionPerformed(e);
    }
}


final class PagingEditor_cbScaleLast_actionAdapter implements ActionListener {
    private PagingEditor adaptee;
    PagingEditor_cbScaleLast_actionAdapter(PagingEditor adaptee) {
        this.adaptee = adaptee;
    }
    public void actionPerformed(ActionEvent e) {
        adaptee.cbScaleLast_actionPerformed(e);
    }
}
    
final class PagingEditor_cbAutoScale_actionAdapter implements ActionListener {
    private PagingEditor adaptee;
    PagingEditor_cbAutoScale_actionAdapter(PagingEditor adaptee) {
        this.adaptee = adaptee;
    }    
    public void actionPerformed(ActionEvent e) {
        adaptee.cbAutoScale_actionPerformed(e);
    }
}

