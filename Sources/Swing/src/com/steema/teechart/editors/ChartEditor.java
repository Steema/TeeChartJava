package com.steema.teechart.editors;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

import com.steema.teechart.Chart;

import com.steema.teechart.axis.Axes;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.styles.Series;
import com.steema.teechart.tools.Tool;
import java.awt.Component;
import com.steema.teechart.languages.Language;
import com.steema.teechart.misc.StringFormat;

/**
 *
 * <p>Title: ChartEditor class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class ChartEditor extends JDialog {


    private ChartEditorPanel editor=new ChartEditorPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JButton closeButton = new JButton();
    BorderLayout borderLayout2 = new BorderLayout();
    Border border1 = BorderFactory.createEmptyBorder();
    JPanel jPanel2 = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    JPanel contentPane;

    public ChartEditor() {
        this((JFrame)null);
    }

    public ChartEditor(JFrame owner) {
        this(owner, (Chart)null);
    }

    public ChartEditor(Chart chart) {
        this((JFrame)null, chart);
    }

    public ChartEditor(JFrame owner, Chart chart) {
        this(owner, chart, "");
    }

    public ChartEditor(Chart chart, String name) {
        this((JFrame)null, chart, name);
    }

    public ChartEditor(JFrame owner, Chart chart, String name) {
        super(owner, true);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        InputMap iMap = getRootPane().getInputMap(
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), Language.getString("escape"));
        getRootPane().getActionMap().put(Language.getString("escape"), new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                closeButton_actionPerformed(null);
            }
        });

        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        this.setTitle(StringFormat.format(Language.getString("Editing"),name));

        editor.setChart(chart);


    }

    static protected ChartEditor createChartEditor(Chart chart, String name) {
        ChartEditor editor = new ChartEditor(chart, name);
//        editor.validate();
        return editor;
    }

    static public void editChart(Chart chart) {
        editChart(chart, "");
    }

    static public void editChart(Chart chart, String name) {
        ChartEditor editor = createChartEditor(chart, name);
        editor.setVisible(true);
    }

    static public void editAxes(Axes axes) {
        editAxes(axes, ((Chart)axes.getChart()).toString());
    }

    static public void editAxes(Axes axes, String chartName) {
        ChartEditor editor = createChartEditor((Chart)axes.getChart(), chartName);
        editor.getEditor().selectAxes();
        editor.setVisible(true);
    }

    static public void editAxis(Axis axis) {
        editAxis(axis, ((Chart)axis.getChart()).toString());
    }

    static public void editAxis(Axis axis, String chartName) {
        ChartEditor editor = createChartEditor((Chart)axis.getChart(), chartName);
        editor.getEditor().selectAxis(axis);
        editor.setVisible(true);
    }

    static public void editLegend(Chart chart) {
        editLegend(chart, chart.toString());
    }

    static public void editLegend(Chart chart, String name) {
        ChartEditor editor = createChartEditor(chart, name);
        editor.getEditor().selectLegend();
        editor.setVisible(true);
    }
/*
    static public void editAxis(Axis axis) {
        editAxis(axis, ((Chart)axis.getChart()).toString());
    }

    static public void editAxis(Axis axis, String chartName) {
        editAxis(axis, chartName, "");
    }

    static public void editAxis(Axis axis, String chartName, String focusTab) {
        ChartEditor editor = createChartEditor((Chart)axis.getChart(), chartName);
        editor.getEditor().selectAxis(axis);
        Component tmpPanel = editor.getEditor().getActiveComponent();
        if (tmpPanel.getClass() == SeriesGeneral.class) {
            ((SeriesGeneral)tmpPanel).setActiveTab(focusTab);
        }
        editor.setVisible(true);
    }
*/
    static public void editSeries(Series series) {
        editSeries(series, ((Chart)series.getChart()).toString());
    }

    static public void editSeries(Series series, String chartName) {
        editSeries(series, chartName, "");
    }

    static public void editSeries(Series series, String chartName, String focusTab) {
        ChartEditor editor = createChartEditor((Chart)series.getChart(), chartName);
        editor.getEditor().selectSeries(series);
        Component tmpPanel = editor.getEditor().getActiveComponent();
        if (tmpPanel.getClass() == SeriesGeneral.class) {
            ((SeriesGeneral)tmpPanel).setActiveTab(focusTab);
        }
        editor.setVisible(true);
    }

    static public void editSeriesDatasource(Series series) {
        editSeriesDatasource(series, ((Chart)series.getChart()).toString());
    }

    static public void editSeriesDatasource(Series series, String chartName) {
        editSeries(series, chartName, Language.getString("Data_Source"));
    }

    static public void editSeriesMarks(Series series) {
        editSeriesMarks(series, ((Chart)series.getChart()).toString());
    }

    static public void editSeriesMarks(Series series, String chartName) {
        editSeries(series, chartName, Language.getString("Marks"));
    }

    static public void editTool(Tool tool) {
        editTool(tool, ((Chart)tool.getChart()).toString());
    }

    static public void editTool(Tool tool, String chartName) {
        ChartEditor editor = createChartEditor((Chart)tool.getChart(), chartName);
        editor.getEditor().selectTool(tool);
        editor.setVisible(true);
    }

    public ChartEditorPanel getEditor() {
        return editor;
    }

    private void jbInit() throws Exception {
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(borderLayout1);
        contentPane.add(editor,BorderLayout.CENTER);
        jPanel1.setPreferredSize(new Dimension(620, 40));
        jPanel1.setLayout(borderLayout3);
        closeButton.setBounds(new Rectangle(11, 9, 84, 23));
        closeButton.setSelected(true);
        closeButton.setText(Language.getString("Close"));
        closeButton.addActionListener(new ChartEditor_closeButton_actionAdapter(this));
        jPanel2.setLayout(null);
        jPanel2.setPreferredSize(new Dimension(110, 41));
        contentPane.add(jPanel1, java.awt.BorderLayout.SOUTH);
        jPanel1.add(jPanel2, java.awt.BorderLayout.EAST);
        jPanel2.add(closeButton);
        setSize(500, 350);
        pack();
        setLocationRelativeTo(getParent());
    }

    public void closeButton_actionPerformed(ActionEvent e) {
        dispose();
    }
}

final class ChartEditor_closeButton_actionAdapter implements ActionListener {
    private ChartEditor adaptee;
    ChartEditor_closeButton_actionAdapter(ChartEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.closeButton_actionPerformed(e);
    }
}

