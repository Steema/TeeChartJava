package com.steema.teechart.editors.gallery;

import java.lang.reflect.InvocationTargetException;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.KeyEvent;

import com.steema.teechart.misc.Utils;
import com.steema.teechart.editors.ChartListBox;
import com.steema.teechart.styles.Series;
import com.steema.teechart.Chart;
import java.awt.Container;
import javax.swing.border.Border;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: Chart Gallery by Series type.</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class GalleryByType extends JDialog {


    private GalleryPanel galleryPanel = new GalleryPanel();
    private Chart chart = new Chart();

    JSplitPane jSplitPane1 = new JSplitPane();
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    BorderLayout borderLayout3 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JComboBox jComboBox1 = new JComboBox();
    private ChartListBox chartListBox1 = new ChartListBox();
    JCheckBox cbView3D = new JCheckBox();
    JCheckBox cbSmooth = new JCheckBox();
    JButton bOK = new JButton();
    JButton bCancel = new JButton();
    private boolean result;
    private boolean initDialog;
    private JScrollPane jScrollPane1 = new JScrollPane();
    Border border1 = BorderFactory.createEmptyBorder();

    public GalleryByType() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        load();

        InputMap iMap = getRootPane().getInputMap(
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), Language.getString("escape"));
        getRootPane().getActionMap().put(Language.getString("escape"), new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                bCancel_actionPerformed(null);
            }
        });

    }

    /**
     * Returns the currently selected Series style at Gallery.
     *
     * @return Class
     */
    public Class getSeriesType() {
        return galleryPanel.getSeriesType();
    }

    public int getSubIndex() {
        return galleryPanel.getSubIndex();
    }

    public static Series createNew(Chart chart) {
        return createNew(null, chart);
    }

    public static Series createNew(Container owner, Chart chart) {
        GalleryByType gallery = new GalleryByType();
        gallery.setSize(528, 355);
        gallery.setLocationRelativeTo(owner);
        gallery.setVisible(true);

        // TODO: Finish returning new created Series...
        //return (gallery.result) ? gallery.: null;
        return null;
    }

    private void load() {

        initDialog = true;

        chart.getParent().checkGraphics();

        DefaultComboBoxModel items = (DefaultComboBoxModel) jComboBox1.getModel();

        for (int t = 0; t < Utils.SERIESTYPESCOUNT; t++) {
            if (Utils.seriesGalleryCount[t] > 0) {
                String tmpPage = GalleryPanel.galleryPages(Utils.
                        seriesGalleryPage[t]);
                if (items.getIndexOf(tmpPage) == -1) {
                    items.addElement(tmpPage);
                }
            }
        }

        cbView3D.setSelected(chart.getAspect().getView3D());

        chartListBox1.setChart(chart);

        initDialog = false;

        jComboBox1.setSelectedIndex(0);
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        jPanel1.setMinimumSize(new Dimension(100, 10));
        jPanel1.setPreferredSize(new Dimension(100, 10));
        jPanel1.setLayout(borderLayout2);
        jPanel2.setMinimumSize(new Dimension(10, 36));
        jPanel2.setPreferredSize(new Dimension(10, 36));
        jPanel2.setLayout(null);
        jComboBox1.setMinimumSize(new Dimension(125, 20));
        jComboBox1.setPreferredSize(new Dimension(129, 22));
        jComboBox1.setBounds(new Rectangle(5, 5, 107, 22));
        jComboBox1.addActionListener(new GalleryByType_jComboBox1_actionAdapter(this));
        jPanel4.setMinimumSize(new Dimension(10, 40));
        jPanel4.setPreferredSize(new Dimension(10, 40));
        jPanel4.setLayout(null);
        cbView3D.setMnemonic('3');
        cbView3D.setText(Language.getString("View_3D"));
        cbView3D.setBounds(new Rectangle(6, 9, 81, 23));
        cbView3D.addActionListener(new GalleryByType_cbView3D_actionAdapter(this));
        cbSmooth.setMnemonic(Language.getString("S").charAt(0));
        cbSmooth.setText(Language.getString("Smooth"));
        cbSmooth.setBounds(new Rectangle(89, 9, 81, 23));
        cbSmooth.addActionListener(new GalleryByType_cbSmooth_actionAdapter(this));
        bOK.setBounds(new Rectangle(217, 9, 75, 23));
        bOK.setEnabled(false);
        bOK.setText(Language.getString("OK"));
        bOK.addActionListener(new GalleryByType_bOK_actionAdapter(this));
        bCancel.setBounds(new Rectangle(307, 9, 75, 23));
        bCancel.setText(Language.getString("Cancel"));
        bCancel.addActionListener(new GalleryByType_bCancel_actionAdapter(this));
        chartListBox1.addListSelectionListener(new
                                               GalleryByType_chartListBox1_listSelectionAdapter(this));
        this.getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);
        jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);
        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        jScrollPane1.getViewport().add(chartListBox1);
        chartListBox1.setBorder(border1);

        jPanel2.add(jComboBox1);
        jSplitPane1.add(jPanel1, JSplitPane.LEFT);
        jPanel3.setLayout(borderLayout3);
        jPanel3.add(jPanel4, java.awt.BorderLayout.SOUTH);
        jPanel4.add(cbView3D);
        jPanel4.add(cbSmooth);
        jPanel4.add(bOK);
        jPanel4.add(bCancel);
        jPanel3.add(galleryPanel, java.awt.BorderLayout.CENTER);
        jSplitPane1.add(jPanel3, JSplitPane.RIGHT);
        jSplitPane1.setDividerLocation(120);
    }

    public void jComboBox1_actionPerformed(ActionEvent e) {
        if (!initDialog) {
            chart.getSeries().clear();

            for (int t = 0; t < Utils.SERIESTYPESCOUNT; t++) {
                if (Utils.seriesGalleryCount[t] > 0) {
                    String tmpPage = GalleryPanel.galleryPages(Utils.
                            seriesGalleryPage[t]);
                    if (jComboBox1.getSelectedItem().toString().equals(tmpPage)) {
                        Series s = null;
                        try {
                            s = chart.getSeries().add(Utils.seriesTypesOf[t]);
                        } catch (IllegalAccessException ex) {
                        } catch (InstantiationException ex) {
                        }

                        s.setTitle(s.getDescription());
                    }
                }
            }

            chartListBox1.setSelectedIndex(0);
            chartListBox1.repaint();

            chartListBox1_valueChanged(null);
        }
    }

    public void cbView3D_actionPerformed(ActionEvent e) {
        galleryPanel.setView3D(cbView3D.isSelected());
    }

    public void cbSmooth_actionPerformed(ActionEvent e) {
        galleryPanel.setSmooth(cbSmooth.isSelected());
    }

    public void chartListBox1_valueChanged(ListSelectionEvent e) {
        Series s = chartListBox1.getSeries();
        galleryPanel.addSubCharts(s);
        bOK.setEnabled(false);
    }

    private void GalleryPanel_OnChangeChart() {
        bOK.setEnabled(true);
    }

    private void GalleryPanel_OnSelectedChart() throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        try {
            galleryPanel.subSelected(null);
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        }

        bOK_actionPerformed(null);
    }

    public void bCancel_actionPerformed(ActionEvent e) {
        result = false;
        dispose();
    }

    public void bOK_actionPerformed(ActionEvent e) {
        result = true;
        dispose();
    }
}


final class GalleryByType_bCancel_actionAdapter implements ActionListener {
    private GalleryByType adaptee;
    GalleryByType_bCancel_actionAdapter(GalleryByType adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bCancel_actionPerformed(e);
    }
}


class GalleryByType_bOK_actionAdapter implements ActionListener {
    private GalleryByType adaptee;
    GalleryByType_bOK_actionAdapter(GalleryByType adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bOK_actionPerformed(e);
    }
}


class GalleryByType_chartListBox1_listSelectionAdapter implements
        ListSelectionListener {
    private GalleryByType adaptee;
    GalleryByType_chartListBox1_listSelectionAdapter(GalleryByType adaptee) {
        this.adaptee = adaptee;
    }

    public void valueChanged(ListSelectionEvent e) {
        adaptee.chartListBox1_valueChanged(e);
    }
}


class GalleryByType_cbSmooth_actionAdapter implements ActionListener {
    private GalleryByType adaptee;
    GalleryByType_cbSmooth_actionAdapter(GalleryByType adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbSmooth_actionPerformed(e);
    }
}


class GalleryByType_cbView3D_actionAdapter implements ActionListener {
    private GalleryByType adaptee;
    GalleryByType_cbView3D_actionAdapter(GalleryByType adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbView3D_actionPerformed(e);
    }
}


class GalleryByType_jComboBox1_actionAdapter implements ActionListener {
    private GalleryByType adaptee;
    GalleryByType_jComboBox1_actionAdapter(GalleryByType adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jComboBox1_actionPerformed(e);
    }
}

