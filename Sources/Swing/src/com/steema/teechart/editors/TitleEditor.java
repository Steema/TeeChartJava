package com.steema.teechart.editors;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.steema.teechart.Title;
import com.steema.teechart.Chart;
import com.steema.teechart.drawing.StringAlignment;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class TitleEditor extends JPanel {

    private Chart chart;
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JComboBox cbTitle = new JComboBox();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel jPanel4 = new JPanel();
    JCheckBox cbVisible = new JCheckBox();
    JLabel jLabel1 = new JLabel();
    JCheckBox cbAdjust = new JCheckBox();
    JLabel jLabel2 = new JLabel();
    JComboBox cbAlign = new JComboBox();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTextArea text = new JTextArea();
    JCheckBox cbCustom = new JCheckBox();

    private boolean initDone;
    JLabel jLabel3 = new JLabel();
    JSpinner spLeft = new JSpinner();
    JLabel jLabel4 = new JLabel();
    JSpinner spTop = new JSpinner();
    private ShapeEditor shapeEditor;

    public TitleEditor(Chart chart) {
        this();
        this.chart = chart;

        cbTitle.addItem(Language.getString("Title"));
        cbTitle.addItem(Language.getString("SubTitle"));
        cbTitle.addItem(Language.getString("SubFooter"));
        cbTitle.addItem(Language.getString("Footer"));

        cbAlign.addItem(Language.getString("Left"));
        cbAlign.addItem(Language.getString("Center"));
        cbAlign.addItem(Language.getString("Right"));

        cbTitle.setSelectedIndex(0);

        initDone=true;

        shapeEditor=new ShapeEditor(jTabbedPane1, getTitle());
        setTitle();
    }

    public TitleEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jPanel1.setPreferredSize(new Dimension(10, 30));
        jPanel1.setLayout(null);
        cbTitle.setBounds(new Rectangle(10, 4, 132, 22));
        cbTitle.addActionListener(new TitleEditor_cbTitle_actionAdapter(this));
        jPanel2.setLayout(borderLayout2);
        jPanel4.setPreferredSize(new Dimension(10, 50));
        jPanel4.setLayout(null);
        cbVisible.setMnemonic(Language.getString("V").charAt(0));
        cbVisible.setText(Language.getString("Visible"));
        cbVisible.setBounds(new Rectangle(8, 6, 81, 23));
        cbVisible.addActionListener(new TitleEditor_cbVisible_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("T").charAt(0));
        jLabel1.setLabelFor(text);
        jLabel1.setText(Language.getString("Text"));
        jLabel1.setBounds(new Rectangle(6, 32, 34, 14));
        cbAdjust.setMnemonic(Language.getString("A").charAt(0));
        cbAdjust.setText(Language.getString("Adjust_frame"));
        cbAdjust.setBounds(new Rectangle(98, 6, 94, 23));
        cbAdjust.addActionListener(new TitleEditor_cbAdjust_actionAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("N").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setLabelFor(cbAlign);
        jLabel2.setText(Language.getString("Alignment"));
        jLabel2.setBounds(new Rectangle(195, 10, 72, 14));
        cbAlign.setBounds(new Rectangle(275, 6, 87, 22));
        cbAlign.addActionListener(new TitleEditor_cbAlign_actionAdapter(this));
        jPanel3.setLayout(null);
        cbCustom.setMnemonic(Language.getString("C").charAt(0));
        cbCustom.setText(Language.getString("Custom"));
        cbCustom.setBounds(new Rectangle(22, 16, 81, 23));
        cbCustom.addActionListener(new TitleEditor_cbCustom_actionAdapter(this));
        jLabel3.setDisplayedMnemonic(Language.getString("L").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setLabelFor(spLeft);
        jLabel3.setText(Language.getString("Left"));
        jLabel3.setBounds(new Rectangle(35, 57, 57, 14));
        spLeft.setBounds(new Rectangle(100, 54, 61, 20));
        spLeft.setEnabled(false);
        spLeft.addChangeListener(new TitleEditor_spLeft_changeAdapter(this));
        jLabel4.setDisplayedMnemonic(Language.getString("T").charAt(0));
        jLabel4.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel4.setText(Language.getString("Top"));
        jLabel4.setBounds(new Rectangle(32, 83, 60, 14));
        spTop.setBounds(new Rectangle(100, 80, 61, 20));
        spTop.setEnabled(false);
        spTop.addChangeListener(new TitleEditor_spTop_changeAdapter(this));
        this.add(jPanel1, java.awt.BorderLayout.NORTH);
        jPanel1.add(cbTitle);
        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.add(jPanel2, Language.getString("Style"));
        jPanel2.add(jPanel4, java.awt.BorderLayout.NORTH);
        jPanel4.add(cbVisible);
        jPanel4.add(jLabel1);
        jPanel4.add(cbAdjust);
        jPanel4.add(jLabel2);
        jPanel4.add(cbAlign);
        jTabbedPane1.add(jPanel3, Language.getString("Position"));
        jPanel3.add(cbCustom);
        jPanel3.add(jLabel3);
        jPanel3.add(spLeft);
        jPanel3.add(jLabel4);
        jPanel3.add(spTop);
        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        jScrollPane1.getViewport().add(text);
    }

    public void cbVisible_actionPerformed(ActionEvent e) {
        getTitle().setVisible(cbVisible.isSelected());
    }

    Title getTitle() {
        switch (cbTitle.getSelectedIndex()) {
        case 0:
            return chart.getHeader();
        case 1:
            return chart.getSubHeader();
        case 2:
            return chart.getSubFooter();
        default:
            return chart.getFooter();
        }
    }

    private void setTitle() {
        if (initDone) {
            Title tmp = getTitle();
            cbVisible.setSelected(tmp.getVisible());
            cbAdjust.setSelected(tmp.getAdjustFrame());
            cbAlign.setSelectedIndex(tmp.getAlignment().getValue());
            cbCustom.setSelected(tmp.getCustomPosition());
            text.setText(tmp.getText());
            text.getDocument().addDocumentListener(new TextAreaListener(this));
            spLeft.setValue(new Integer(tmp.getLeft()));
            spTop.setValue(new Integer(tmp.getTop()));

            shapeEditor.refreshControls(tmp);
        }
    }

    public void cbTitle_actionPerformed(ActionEvent e) {
        setTitle();
    }

    public void cbAdjust_actionPerformed(ActionEvent e) {
        getTitle().setAdjustFrame(cbAdjust.isSelected());
    }

    public void cbAlign_actionPerformed(ActionEvent e) {
        if (initDone) {
            getTitle().setAlignment(StringAlignment.fromValue(cbAlign.
                    getSelectedIndex()));
        }
    }

    public void cbCustom_actionPerformed(ActionEvent e) {
        getTitle().setCustomPosition(cbCustom.isSelected());
        spLeft.setEnabled(cbCustom.isSelected());
        spTop.setEnabled(cbCustom.isSelected());
    }

    public void spLeft_stateChanged(ChangeEvent e) {
        getTitle().setLeft(((Integer)spLeft.getValue()).intValue());
    }

    public void spTop_stateChanged(ChangeEvent e) {
        getTitle().setTop(((Integer)spTop.getValue()).intValue());
    }
}


class TitleEditor_spTop_changeAdapter implements ChangeListener {
    private TitleEditor adaptee;
    TitleEditor_spTop_changeAdapter(TitleEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spTop_stateChanged(e);
    }
}


class TitleEditor_spLeft_changeAdapter implements ChangeListener {
    private TitleEditor adaptee;
    TitleEditor_spLeft_changeAdapter(TitleEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spLeft_stateChanged(e);
    }
}


final class TitleEditor_cbCustom_actionAdapter implements ActionListener {
    private TitleEditor adaptee;
    TitleEditor_cbCustom_actionAdapter(TitleEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbCustom_actionPerformed(e);
    }
}


final class TitleEditor_cbAlign_actionAdapter implements ActionListener {
    private TitleEditor adaptee;
    TitleEditor_cbAlign_actionAdapter(TitleEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAlign_actionPerformed(e);
    }
}


final class TitleEditor_cbAdjust_actionAdapter implements ActionListener {
    private TitleEditor adaptee;
    TitleEditor_cbAdjust_actionAdapter(TitleEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAdjust_actionPerformed(e);
    }
}


final class TitleEditor_cbTitle_actionAdapter implements ActionListener {
    private TitleEditor adaptee;
    TitleEditor_cbTitle_actionAdapter(TitleEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbTitle_actionPerformed(e);
    }
}


final class TitleEditor_cbVisible_actionAdapter implements ActionListener {
    private TitleEditor adaptee;
    TitleEditor_cbVisible_actionAdapter(TitleEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbVisible_actionPerformed(e);
    }
}


final class TextAreaListener implements DocumentListener {

    private TitleEditor title;

    public TextAreaListener(TitleEditor title) {
        super();
        this.title = title;
    }

    public void insertUpdate(DocumentEvent e) {
        title.getTitle().setText(title.text.getText());
    }

    public void removeUpdate(DocumentEvent e) {
        title.getTitle().setText(title.text.getText());
    }

    public void changedUpdate(DocumentEvent e) {
    }
}

