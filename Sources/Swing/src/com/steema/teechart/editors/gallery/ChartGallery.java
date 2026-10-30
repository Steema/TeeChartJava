package com.steema.teechart.editors.gallery;

import java.awt.Rectangle;
import java.awt.event.*;

import java.lang.reflect.InvocationTargetException;
import javax.swing.*;
import javax.swing.event.*;

import com.steema.teechart.*;
import com.steema.teechart.functions.*;
import com.steema.teechart.styles.*;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import com.steema.teechart.misc.*;
import com.steema.teechart.languages.Language;


/**
 *
 * <p>Title: ChartGallery class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class ChartGallery extends JDialog {


    int subIndex = 0;

    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JCheckBox cb3D = new JCheckBox();
    JCheckBox cbSmooth = new JCheckBox();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JPanel tabSeries = new JPanel();
    JPanel tabFunctions = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    BorderLayout borderLayout3 = new BorderLayout();
    JList jList1 = new JList();
    JTabbedPane tabSeriesPage = new JTabbedPane();
    JTabbedPane tabFunctionsPage = new JTabbedPane();

    boolean result;

    public ChartGallery() throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        setSize(400, 350);

        addFunctionTabs();
        tabFunctionsPage.setSelectedIndex(0);

        addSeriesTabs();
        tabSeriesPage.setSelectedIndex(0);

        InputMap iMap = getRootPane().getInputMap(
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), Language.getString("escape"));
        getRootPane().getActionMap().put(Language.getString("escape"), new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                jButton2_actionPerformed(null);
            }
        });
    }

    abstract class GalleryActionAdapter implements ActionListener {}

    private void tryAddPage(JTabbedPane tabs, String name, boolean functions) throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        if (tabs.indexOfTab(name) == -1) {
            GalleryPanel gallery = new GalleryPanel();
            tabs.add(gallery, name);
            gallery.createGalleryPage(name, functions);
            gallery.onSelectedChart = new GalleryActionAdapter() {
                public void actionPerformed(ActionEvent e) {
                    jButton1_actionPerformed(null);
                }
            };
        }
    }

    private void addFunctionTabs() throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        tabFunctionsPage.removeAll();

        for (int t = 0; t < Utils.FUNCTIONTYPESCOUNT; t++) {
            String tmpPage = GalleryPanel.galleryPages(Utils.
                    functionGalleryPage[t]);

            tryAddPage(tabFunctionsPage, tmpPage, true);
        }
    }

    private void addSeriesTabs() throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        tabSeriesPage.removeAll();

        for (int t = 0; t < Utils.SERIESTYPESCOUNT; t++) {
            String tmpPage = GalleryPanel.galleryPages(Utils.
                    seriesGalleryPage[
                    t]);

            tryAddPage(tabSeriesPage, tmpPage, false);
        }
    }

    public ChartGallery(Container owner) throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        this(); // <-- do not call "super(owner)"
        setLocationRelativeTo(owner);
    }

    public boolean showModal() {
        setModal(true);
        setVisible(true);
        return result;
    }

    static public Series createNew(Chart chart) throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        return createNew(null, chart);
    }

    static private class SeriesSubIndex {
        Series series;
        int subIndex;
    }


    static public Series changeSeriesType(Series series, Class newClass) throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        return changeSeriesType(series, newClass, 0, null);
    }

    static public Series changeSeriesType(Series series, Class newClass,
                                          int subType, Object tag) throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        Class type = newClass;
        Function tmpFunction = series.getFunction();
        Class function = (tmpFunction == null) ? null :
                         tmpFunction.getClass();
        try {
            return Series.createNewSeries(series.getChart(), type, function,
                                          subType, tag);
        } catch (IllegalAccessException e) {
            return null;
        } catch (InstantiationException e) {
            return null;
        }
    }

    static public Series changeSeriesDialog(Container owner, Series series) throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        SeriesSubIndex tmp = doCreateNew(owner, series, series.getChart(), false, true);
        if (tmp.series != null) {
            //assignSeries(series, tmp.series);
            series = com.steema.teechart.styles.Series.assignDispose(series, tmp.series);
          //  series.fillSampleValues();
           // series = tmp.series;
            if (tmp.subIndex != -1) {
                series.setSubGallery(tmp.subIndex);
            }
        }

        return series;
    }


   /* private static void assignSeries(Series oldSeries, Series newSeries) {
        if (newSeries != oldSeries) {
            newSeries.assign(oldSeries);
        }
    }*/

    /**
     * Shows the Chart Gallery dialog to let the user choose a Series or
     * Function class type.
     * If user clicks OK button, returns a new instance of choosen Series
     * and optional Function.
     *
     * @param owner Dialog
     * @param chart Chart
     * @return Series
     */
    static public Series createNew(Dialog owner, IBaseChart chart) throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        SeriesSubIndex tmp = doCreateNew(owner, null, chart, false, true);
        return (tmp == null) ? null : tmp.series;
    }

    static private SeriesSubIndex doCreateNew(Container owner,
                                              Series series,
                                              IBaseChart chart,
                                              boolean allowSameType,
                                              boolean checkSeries) throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        ChartGallery gallery = new ChartGallery(owner);
        SeriesSubIndex result = new SeriesSubIndex();

        if (gallery.showModal()) {
            Object tag = gallery.getGallery().getInitArgs();
            Series tmp = gallery.getSelectedChart().getSeries(0);
            result.series = changeSeriesType(tmp, tmp.getClass(),
                                             gallery.subIndex, tag);
            result.series.setChart(chart);
            result.subIndex = gallery.subIndex;
        }
        return result;
    }

    public Chart getSelectedChart() {
        return getGallery().selectedChart.getChart();
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        jPanel1.setMinimumSize(new Dimension(10, 10));
        jPanel1.setPreferredSize(new Dimension(10, 32));
        jPanel1.setLayout(null);
        cb3D.setMnemonic(Language.getString("V").charAt(0));
        cb3D.setSelected(true);
        cb3D.setText(Language.getString("View_3D"));
        cb3D.setBounds(new Rectangle(8, 2, 83, 23));
        cb3D.addActionListener(new ChartGallery_jCheckBox1_actionAdapter(this));
        cbSmooth.setMnemonic(Language.getString("S").charAt(0));
        cbSmooth.setText(Language.getString("Smooth"));
        cbSmooth.setBounds(new Rectangle(93, 4, 105, 18));
        cbSmooth.addActionListener(new ChartGallery_cbSmooth_actionAdapter(this));
        jButton1.setBounds(new Rectangle(223, 4, 70, 23));
        jButton1.setPreferredSize(new Dimension(67, 25));
        jButton1.setText(Language.getString("OK"));
        jButton1.addActionListener(new ChartGallery_jButton1_actionAdapter(this));
        jButton2.setBounds(new Rectangle(303, 4, 80, 23));
        jButton2.setText(Language.getString("Cancel"));
        jButton2.addActionListener(new ChartGallery_jButton2_actionAdapter(this));
        this.setDefaultCloseOperation(javax.swing.WindowConstants.
                                      DISPOSE_ON_CLOSE);
        this.setModal(true);
        this.setTitle(Language.getString("TeeChart_Gallery"));
        tabSeries.setLayout(borderLayout2);
        tabFunctions.setLayout(borderLayout3);
        tabSeriesPage.addComponentListener(new
                                           ChartGallery_tabSeriesPage_componentAdapter(this));
        tabSeriesPage.addChangeListener(new
                                        ChartGallery_tabSeriesPage_changeAdapter(this));
        tabFunctionsPage.addComponentListener(new
                                              ChartGallery_tabFunctionsPage_componentAdapter(this));
        tabFunctionsPage.addChangeListener(new
                                           ChartGallery_tabFunctionsPage_changeAdapter(this));
        jTabbedPane1.addChangeListener(new
                                       ChartGallery_jTabbedPane1_changeAdapter(this));
        this.getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);
        jPanel1.add(cb3D);
        jPanel1.add(cbSmooth);
        jPanel1.add(jButton1);
        jPanel1.add(jButton2);
        this.getContentPane().add(jTabbedPane1,
                                  java.awt.BorderLayout.CENTER);
        jTabbedPane1.add(tabSeries, Language.getString("Series"));
        jTabbedPane1.add(tabFunctions, Language.getString("Functions"));
        tabSeries.add(tabSeriesPage, java.awt.BorderLayout.CENTER);
        tabFunctions.add(tabFunctionsPage, java.awt.BorderLayout.CENTER);
        setLocationRelativeTo(getParent());
    }

    private GalleryPanel getGallery() {
        JTabbedPane tab = (jTabbedPane1.getSelectedIndex() == 0) ?
                          tabSeriesPage : tabFunctionsPage;
        return (GalleryPanel) tab.getSelectedComponent();
    }

    public void jCheckBox1_actionPerformed(ActionEvent e) {
        getGallery().setView3D(cb3D.isSelected());
    }

    public void cbSmooth_actionPerformed(ActionEvent e) {
        getGallery().setSmooth(cbSmooth.isSelected());
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        result = false;
        dispose();
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        result = true;
        dispose();
    }

    public void tabSeriesPage_componentResized(ComponentEvent e) {
        getGallery().resizeCharts();
    }

    public void tabSeriesPage_stateChanged(ChangeEvent e) {
        GalleryPanel g = getGallery();
        if (g != null) {
            g.resizeCharts();
            g.setView3D(cb3D.isSelected());
            g.setSmooth(cbSmooth.isSelected());
        }
    }

    public void tabFunctionsPage_componentResized(ComponentEvent e) {
        getGallery().resizeCharts();
    }

    public void tabFunctionsPage_stateChanged(ChangeEvent e) {
        GalleryPanel g = getGallery();
        if (g != null) {
            g.resizeCharts();
            g.setView3D(cb3D.isSelected());
            g.setSmooth(cbSmooth.isSelected());
        }
    }

    public void jTabbedPane1_stateChanged(ChangeEvent e) {
        if (getGallery() != null) {
            getGallery().resizeCharts();
        }
    }
}


final class ChartGallery_jTabbedPane1_changeAdapter implements
        ChangeListener {
    private ChartGallery adaptee;
    ChartGallery_jTabbedPane1_changeAdapter(ChartGallery adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jTabbedPane1_stateChanged(e);
    }
}


final class ChartGallery_tabSeriesPage_componentAdapter extends
        ComponentAdapter {
    private ChartGallery adaptee;
    ChartGallery_tabSeriesPage_componentAdapter(ChartGallery adaptee) {
        this.adaptee = adaptee;
    }

    public void componentResized(ComponentEvent e) {
        adaptee.tabSeriesPage_componentResized(e);
    }
}


final class ChartGallery_tabSeriesPage_changeAdapter implements
        ChangeListener {
    private ChartGallery adaptee;
    ChartGallery_tabSeriesPage_changeAdapter(ChartGallery adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.tabSeriesPage_stateChanged(e);
    }
}


final class ChartGallery_tabFunctionsPage_componentAdapter extends
        ComponentAdapter {
    private ChartGallery adaptee;
    ChartGallery_tabFunctionsPage_componentAdapter(ChartGallery adaptee) {
        this.adaptee = adaptee;
    }

    public void componentResized(ComponentEvent e) {
        adaptee.tabFunctionsPage_componentResized(e);
    }
}


final class ChartGallery_tabFunctionsPage_changeAdapter implements
        ChangeListener {
    private ChartGallery adaptee;
    ChartGallery_tabFunctionsPage_changeAdapter(ChartGallery adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.tabFunctionsPage_stateChanged(e);
    }
}


final class ChartGallery_jButton1_actionAdapter implements ActionListener {
    private ChartGallery adaptee;
    ChartGallery_jButton1_actionAdapter(ChartGallery adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}


final class ChartGallery_jButton2_actionAdapter implements ActionListener {
    private ChartGallery adaptee;
    ChartGallery_jButton2_actionAdapter(ChartGallery adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


final class ChartGallery_cbSmooth_actionAdapter implements ActionListener {
    private ChartGallery adaptee;
    ChartGallery_cbSmooth_actionAdapter(ChartGallery adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbSmooth_actionPerformed(e);
    }
}


final class ChartGallery_jCheckBox1_actionAdapter implements ActionListener {
    private ChartGallery adaptee;
    ChartGallery_jCheckBox1_actionAdapter(ChartGallery adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jCheckBox1_actionPerformed(e);
    }
}

