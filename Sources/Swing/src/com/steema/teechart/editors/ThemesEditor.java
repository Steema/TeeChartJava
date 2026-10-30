package com.steema.teechart.editors;

import java.awt.BorderLayout;
import com.steema.teechart.languages.Language;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.steema.teechart.Chart;

import com.steema.teechart.themes.ThemesList;
import com.steema.teechart.themes.ColorPalettes;

/**
 * <p>Title: TeeChart graphic Themes</p>
 *
 * <p>Description: TeeChart Look and Feels</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ThemesEditor extends JPanel {


    public ThemesEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //private TChart editorTChart=new TChart();  //TChart as parent
    private Chart sourceChart=new Chart();
    private Chart editorChart;

    public ThemesEditor(Chart c) {
        this();

        editorChart=c;

        //editorChart=(Chart)c.clone();  //Separate Chart with apply
        //editorTChart.setChart(editorChart);
        //sourceChart=c;

        DefaultListModel m = new DefaultListModel();
        themes.setModel(m);

        m.addElement(Language.getString("Current"));

        for (int t = 0; t < ThemesList.size(); t++) {
            m.addElement(ThemesList.getThemeDescription(t));
        }

        themes.setSelectedIndex(0);

        for (int t = 0; t < ColorPalettes.PaletteNames.length; t++) {
            cbPalettes.addItem(ColorPalettes.PaletteNames[t]);
        }

        cbPalettes.setSelectedIndex(0);

        initPreviewChart();
    }

    /**
     * showModal
     *
     *
     * @param chart Chart
     */
    public static void showModal(Chart chart) {
        showModal((Frame)null, chart);
    }

    /**
     * showModal
     *
     *
     * @param owner Frame
     * @param chart Chart
     */
    public static void showModal(Frame owner, Chart chart) {
        JDialog d = new JDialog(owner);
        d.setTitle(Language.getString("Chart_Theme_Selector"));
        d.setModal(true);
        d.setSize(new Dimension(400, 300));
        ThemesEditor g = new ThemesEditor(chart);
        d.getContentPane().add(g);
        d.setLocationRelativeTo(owner);
        d.setVisible(true);
    }

    private void initPreviewChart() {
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jPanel1.setPreferredSize(new Dimension(130, 130));
        jPanel1.setLayout(null);
        jLabel1.setDisplayedMnemonic(Language.getString("T").charAt(0));
        jLabel1.setLabelFor(themes);
        jLabel1.setText(Language.getString("Themes"));
        jLabel1.setBounds(new Rectangle(5, 5, 50, 14));
        themes.setPreferredSize(new Dimension(0, 160));
        themes.setBounds(new Rectangle(5, 24, 93, 160));
        themes.addListSelectionListener(new
                                        ThemesEditor_themes_listSelectionAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("C").charAt(0));
        jLabel2.setLabelFor(cbPalettes);
        jLabel2.setText(Language.getString("Color_Palette"));
        jLabel2.setBounds(new Rectangle(5, 189, 92, 14));
        cbPalettes.setBounds(new Rectangle(5, 207, 93, 22));
        cbPalettes.addActionListener(new ThemesEditor_cbPalettes_actionAdapter(this));
        jSplitPane1.setOpaque(false);
        jSplitPane1.setPreferredSize(new Dimension(112, 132));
        jButtonApply.setBounds(new Rectangle(5, 242, 92, 23));
        jButtonApply.setToolTipText("");
        jButtonApply.setAction(null);
        jButtonApply.setText(Language.getString("OK"));
        jButtonApply.addActionListener(new
                                       ThemesEditor_jButtonApply_actionAdapter(this));
        jButtonApply.setVisible(false);
        jPanel1.add(jLabel1);
        jPanel1.add(themes);
        jPanel1.add(jLabel2);
        jPanel1.add(cbPalettes);
        jPanel1.add(jButtonApply);
        jSplitPane1.add(jTabbedPane1, JSplitPane.RIGHT);
        jSplitPane1.add(jPanel1, JSplitPane.LEFT);

        this.add(jSplitPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.add(preview, Language.getString("Preview"));
        jSplitPane1.setDividerLocation(121);
        this.setPreferredSize(new Dimension(400, 300));
    }

    private class PreviewPanel extends JPanel {

        public void paint(Graphics g) {
            if (g != null) {
                editorChart.getGraphics3D().setGraphics(g);
                editorChart.paint(editorChart.getGraphics3D(),preview.getWidth(),preview.getHeight());
            }
        }
    }


    JSplitPane jSplitPane1 = new JSplitPane();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JLabel jLabel1 = new JLabel();
    JList themes = new JList();
    JLabel jLabel2 = new JLabel();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JComboBox cbPalettes = new JComboBox();
    PreviewPanel preview = new PreviewPanel();
    JButton jButtonApply = new JButton();

    public void themes_valueChanged(ListSelectionEvent e) {
        if (themes.getSelectedIndex() > 0) {
            ThemesList.applyTheme(editorChart, themes.getSelectedIndex() - 1);
            preview.repaint();
        }
    }

    public void cbPalettes_actionPerformed(ActionEvent e) {
        if (cbPalettes.getSelectedIndex() == 0) {
            themes_valueChanged(null);
        } else {
            ColorPalettes.applyPalette(editorChart, cbPalettes.getSelectedIndex());
            preview.invalidate();
            preview.repaint();
        }
    }

    public void jButtonApply_actionPerformed(ActionEvent e) {
       sourceChart=(Chart)editorChart.clone();
       sourceChart.invalidate();
    }
}


final class ThemesEditor_jButtonApply_actionAdapter implements ActionListener {
    private ThemesEditor adaptee;
    ThemesEditor_jButtonApply_actionAdapter(ThemesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButtonApply_actionPerformed(e);
    }
}


final class ThemesEditor_cbPalettes_actionAdapter implements ActionListener {
    private ThemesEditor adaptee;
    ThemesEditor_cbPalettes_actionAdapter(ThemesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbPalettes_actionPerformed(e);
    }
}


final class ThemesEditor_themes_listSelectionAdapter implements ListSelectionListener {
    private ThemesEditor adaptee;
    ThemesEditor_themes_listSelectionAdapter(ThemesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void valueChanged(ListSelectionEvent e) {
        adaptee.themes_valueChanged(e);
    }
}

