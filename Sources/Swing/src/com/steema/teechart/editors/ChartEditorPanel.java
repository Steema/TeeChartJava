package com.steema.teechart.editors;

import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.border.Border;

import com.steema.teechart.Chart;

import com.steema.teechart.axis.Axis;
import com.steema.teechart.styles.Series;
import com.steema.teechart.tools.Tool;

import com.steema.teechart.editors.export.ExportPanel;
import com.steema.teechart.editors.ToolsEditor.ToolListener;
import com.steema.teechart.editors.SeriesEditor.SeriesListener;
import com.steema.teechart.languages.Language;

/**
 *
 * <p>Title: ChartEditorPanel class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ChartEditorPanel extends JPanel implements
        ToolListener, SeriesListener {


    private Chart chart;

    private JSplitPane jSplitPane1 = new JSplitPane();
    private JTree jTree1 = new JTree(treeModel());
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JPanel jPanel = new JPanel();

    final private class SeriesTreeNode extends DefaultMutableTreeNode {
        private Series series;

        public SeriesTreeNode(Series series) {
            super(series.titleOrName());
            this.series = series;
        }
    }


    final private class ToolTreeNode extends DefaultMutableTreeNode {
        Tool tool;

        public ToolTreeNode(Tool tool) {
            super(tool.getDescription());
            this.tool = tool;
        }
    }


    public ChartEditorPanel(Chart chart) {
        this();
        setChart(chart);
        resetTexts();
    }

    private void addSeries() {
        seriesNode.removeAllChildren();
        if (chart != null) {
            for (int t = 0; t < chart.getSeriesCount(); t++) {
                seriesNode.add(new SeriesTreeNode(chart.getSeries(t)));
            }
        }
    }

    private void addTools() {
        toolsNode.removeAllChildren();
        if (chart != null) {
            for (int t = 0; t < chart.getTools().size(); t++) {
                toolsNode.add(new ToolTreeNode(chart.getTool(t)));
            }
        }
    }

    private void prepareTree() {
        jTree1.setRootVisible(false);
        jTree1.expandPath(new TreePath(chartNode.getPath()));
        jTree1.setSelectionPath(new TreePath(seriesNode.getPath()));

        DefaultTreeSelectionModel model = new DefaultTreeSelectionModel();
        model.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTree1.setSelectionModel(model);

        jTree1.setCellRenderer(new TreeRenderer());

        jSplitPane1.setLastDividerLocation(120);
    }

    public void setChart(Chart chart) {
        this.chart = chart;

        addSeries();
        addTools();

        jTree1.setSelectionRow(0);
    }

    public ChartEditorPanel() {
        this((JFrame)null);
    }

    public ChartEditorPanel(JFrame parent) {
        super(true);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        prepareTree();
    }

    private DefaultMutableTreeNode chartNode;
    private DefaultMutableTreeNode seriesNode;
    private DefaultMutableTreeNode generalNode;
    private DefaultMutableTreeNode aspectNode;
    private DefaultMutableTreeNode axesNode;
    private DefaultMutableTreeNode wallsNode;
    private DefaultMutableTreeNode printNode;
    private DefaultMutableTreeNode pagingNode;
    private DefaultMutableTreeNode titlesNode;
    private DefaultMutableTreeNode legendNode;
    private DefaultMutableTreeNode dataNode;
    private DefaultMutableTreeNode panelNode;
    private DefaultMutableTreeNode toolsNode;
    private DefaultMutableTreeNode themesNode;
    private DefaultMutableTreeNode exportNode;

    private BorderLayout borderLayout2 = new BorderLayout();
    private Border border1 = BorderFactory.createEmptyBorder();
    private BorderLayout borderLayout4 = new BorderLayout();

    private TreeModel treeModel() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        seriesNode = new DefaultMutableTreeNode(Language.getString("Series"));
        root.add(seriesNode);

        chartNode = new DefaultMutableTreeNode(Language.getString("Chart"));
        generalNode = new DefaultMutableTreeNode(Language.getString("General"));
        chartNode.add(generalNode);
        axesNode = new DefaultMutableTreeNode(Language.getString("Axis"));
        chartNode.add(axesNode);
        titlesNode = new DefaultMutableTreeNode(Language.getString("Titles"));
        chartNode.add(titlesNode);
        legendNode = new DefaultMutableTreeNode(Language.getString("Legend"));
        chartNode.add(legendNode);
        panelNode = new DefaultMutableTreeNode(Language.getString("Panel"));
        chartNode.add(panelNode);
        pagingNode = new DefaultMutableTreeNode(Language.getString("Paging"));
        chartNode.add(pagingNode);
        wallsNode = new DefaultMutableTreeNode(Language.getString("Walls"));
        chartNode.add(wallsNode);
        aspectNode = new DefaultMutableTreeNode(Language.getString("3D"));
        chartNode.add(aspectNode);

        root.add(chartNode);

        dataNode = new DefaultMutableTreeNode(Language.getString("Data"));
        root.add(dataNode);

        toolsNode = new DefaultMutableTreeNode(Language.getString("Tools"));
        root.add(toolsNode);

        exportNode = new DefaultMutableTreeNode(Language.getString("Export"));
        root.add(exportNode);

        printNode = new DefaultMutableTreeNode(Language.getString("Print"));
        root.add(printNode);

        themesNode = new DefaultMutableTreeNode(Language.getString("Themes"));
        root.add(themesNode);

        return new DefaultTreeModel(root);
    }

    private void jbInit() throws Exception {
        setSize(500, 350);
        jTree1.addTreeSelectionListener(new
                                        ChartEditor_jTree1_treeSelectionAdapter(this));
        jPanel.setLayout(borderLayout2);
        jScrollPane1.setBorder(border1);
        this.setLayout(borderLayout4);
        jSplitPane1.add(jScrollPane1, JSplitPane.LEFT);
        jScrollPane1.getViewport().add(jTree1);
        jSplitPane1.add(jPanel, JSplitPane.RIGHT);
        this.add(jSplitPane1, java.awt.BorderLayout.CENTER);
        jSplitPane1.setDividerLocation(96);
    }

    private void addPanel(JPanel panel) {
        if ((jPanel.getComponentCount() == 0) ||
            (jPanel.getComponent(0) != panel)) {
            jPanel.removeAll();
            jPanel.add(panel);


            resetTexts();

            panel.setVisible(true);
            jPanel.setVisible(false);
            jPanel.setVisible(true);
        }
    }

    private void resetTexts() {
        chartNode.setUserObject(Language.getString("Chart"));
        seriesNode.setUserObject(Language.getString("Series"));
        generalNode.setUserObject(Language.getString("General"));
        aspectNode.setUserObject(Language.getString("Aspect"));
        axesNode.setUserObject(Language.getString("Axis"));
        wallsNode.setUserObject(Language.getString("Walls"));
        printNode.setUserObject(Language.getString("Print"));
        pagingNode.setUserObject(Language.getString("Paging"));
        titlesNode.setUserObject(Language.getString("Titles"));
        legendNode.setUserObject(Language.getString("Legend"));
        dataNode.setUserObject(Language.getString("Data"));
        panelNode.setUserObject(Language.getString("Panel"));
        toolsNode.setUserObject(Language.getString("Tools"));
        themesNode.setUserObject(Language.getString("Themes"));
        exportNode.setUserObject(Language.getString("Export"));
    }

    public void toolAdded(Tool value) {
        toolsNode.add(new ToolTreeNode(value));
        jTree1.updateUI();
    }

    public void toolRemoved(Tool value) {
        for (int index = 0; index < toolsNode.getChildCount(); index++) {
            if (((ToolTreeNode) toolsNode.getChildAt(index)).tool == value) {
                toolsNode.remove(index);
                jTree1.updateUI();
                break;
            }
        }
    }

    public void seriesAdded(Series value) {
        seriesNode.add(new SeriesTreeNode(value));
        jTree1.updateUI();
    }

    public void seriesRemoved(Series value) {
        for (int index = 0; index < seriesNode.getChildCount(); index++) {
            if (((SeriesTreeNode) seriesNode.getChildAt(index)).series == value) {
                seriesNode.remove(index);
                jTree1.updateUI();
                break;
            }
        }
    }

    public void selectAxes() {
        addPanel(new AxesEditor(chart));
    }

    public void selectAxis(Axis axis) {
        AxesEditor editor = new AxesEditor(chart);
        editor.setAxis(axis);
        addPanel(editor);
    }

    public void selectLegend() {
        addPanel(new LegendEditor(chart));
    }

    public void selectSeries(Series value) {
        for (int index = 0; index < seriesNode.getChildCount(); index++) {
            if (((SeriesTreeNode) seriesNode.getChildAt(index)).series == value) {
                jTree1.expandPath(new TreePath(seriesNode));
                jTree1.addSelectionPath(new TreePath(seriesNode.getChildAt(
                        index)));
                break;
            }
        }
    }

    public void selectTool(Tool value) {
        for (int index = 0; index < toolsNode.getChildCount(); index++) {
            if (((ToolTreeNode) toolsNode.getChildAt(index)).tool == value) {
                jTree1.expandPath(new TreePath(toolsNode));
                jTree1.addSelectionPath(new TreePath(toolsNode.getChildAt(index)));
                break;
            }
        }
    }

    public Component getActiveComponent() {
        return jPanel.getComponent(0);
    }

    private ChartListBox tmpChartListBox;
    public void jTree1_valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                                      jTree1.getLastSelectedPathComponent();

        if (node instanceof SeriesTreeNode) {
            addPanel(new SeriesGeneral(((SeriesTreeNode) node).series,
                                       tmpChartListBox));
        } else
        if (node instanceof ToolTreeNode) {
            addPanel(new ToolsEditor(chart, (((ToolTreeNode) node).tool)));
        } else
        if (node == seriesNode) {
            SeriesEditor s = new SeriesEditor(chart);
            s.addSeriesListener(this);

            s.lbSeries.addMouseListener(new MouseAdapter() {

                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        tmpChartListBox = (ChartListBox) e.getSource();
                        if (tmpChartListBox.getSelectedIndex() != -1) {

                            jTree1.expandPath(new TreePath(seriesNode));
                            jTree1.addSelectionPath(new TreePath(seriesNode.
                                    getChildAt(tmpChartListBox.getSelectedIndex())));
                        }
                    }
                }
            });

            addPanel(s);
        } else
        if (node == generalNode) {
            addPanel(new GeneralEditor(chart));
        } else
        if (node == aspectNode) {
            addPanel(new AspectEditor(chart));
        } else
        if (node == wallsNode) {
            addPanel(new WallsEditor(chart));
        } else
        if (node == axesNode) {
            addPanel(new AxesEditor(chart));
        } else
        if (node == pagingNode) {
            addPanel(new PagingEditor(chart));
        } else
        if (node == printNode) {
            addPanel(new PreviewFrame(chart, this));
        } else
        if (node == legendNode) {
            addPanel(new LegendEditor(chart));
        } else
        if (node == dataNode) {
            addPanel(new DataEditor(chart));
        } else
        if (node == panelNode) {
            addPanel(new PanelEditor(chart));
        } else
        if (node == toolsNode) {
            ToolsEditor editor = new ToolsEditor(chart);
            editor.addToolListener(this);
            addPanel(editor);
        } else
        if (node == themesNode) {
            addPanel(new ThemesEditor(chart));
        } else
        if (node == titlesNode) {
            addPanel(new TitleEditor(chart));
        } else
        if (node == exportNode) {
            addPanel(new ExportPanel(chart));
        }
    }
}


final class ChartEditor_jTree1_treeSelectionAdapter implements
        TreeSelectionListener {
    private ChartEditorPanel adaptee;
    ChartEditor_jTree1_treeSelectionAdapter(ChartEditorPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void valueChanged(TreeSelectionEvent e) {
        adaptee.jTree1_valueChanged(e);
    }
}

final class TreeRenderer extends DefaultTreeCellRenderer {


    public TreeRenderer() {
        super();
        ImageIcon tmp = new ImageIcon(TreeRenderer.class.getResource(
                Language.getString("images_treeleaf_png")));
        ImageIcon tmp2 = new ImageIcon(Utils.makeTransparent(tmp.getImage(),
                new Color(234, 238, 255)));
        setLeafIcon(tmp2);
    }
}

