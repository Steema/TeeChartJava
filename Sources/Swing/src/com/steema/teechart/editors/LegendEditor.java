package com.steema.teechart.editors;

import java.awt.*;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.steema.teechart.drawing.StringAlignment;
import com.steema.teechart.legend.Legend;
import com.steema.teechart.legend.LegendTitle;
import com.steema.teechart.Chart;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import com.steema.teechart.legend.LegendAlignment;
import com.steema.teechart.legend.LegendStyle;
import com.steema.teechart.legend.LegendTextStyle;

import javax.swing.border.BevelBorder;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import com.steema.teechart.languages.Language;
import com.steema.teechart.legend.LegendSymbolSize;
import com.steema.teechart.legend.LegendSymbolPosition;

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
public class LegendEditor extends JPanel {

    private Chart chart;
    private Legend legend;
    private LegendTitle legendTitle;

    BorderLayout borderLayout1 = new BorderLayout();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JCheckBox cbVisible = new JCheckBox();
    JCheckBox cbInverted = new JCheckBox();
    BorderLayout borderLayout2 = new BorderLayout();
    JCheckBox cbFontColor = new JCheckBox();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JComboBox cbLegStyle = new JComboBox();
    JComboBox cbTextStyle = new JComboBox();
    ButtonPen bDividing = new ButtonPen();
    JLabel jLabel3 = new JLabel();
    JSpinner spVertical = new JSpinner();
    JComboBox cbCheckBox = new JComboBox();
    JPanel jPanel5 = new JPanel();
    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("Position"));
    JRadioButton rbLeft = new JRadioButton();
    JRadioButton rbRight = new JRadioButton();
    JRadioButton rbTop = new JRadioButton();
    JRadioButton rbBottom = new JRadioButton();
    JCheckBox cbResize = new JCheckBox();
    JPanel jPanel6 = new JPanel();
    Border border3 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    JCheckBox cbCustomPos = new JCheckBox();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel5 = new JLabel();
    JSpinner spCustLeft = new JSpinner();
    JSpinner spCustTop = new JSpinner();
    JLabel jLabel6 = new JLabel();
    JSpinner spMargin = new JSpinner();
    JLabel jLabel7 = new JLabel();
    JSpinner spTopPos = new JSpinner();
    JCheckBox cbSymVisible = new JCheckBox();
    JCheckBox cbSymContinuous = new JCheckBox();
    JCheckBox cbSymSquared = new JCheckBox();
    private ShapeEditor shapeEditor;
    private ShapeEditor titleEditor;
    BorderLayout borderLayout3 = new BorderLayout();
    JTabbedPane jTabbedPane2 = new JTabbedPane();
    JPanel jPanel7 = new JPanel();
    JTextArea MemoText = new JTextArea();
    JLabel jLabel8 = new JLabel();
    Border border4 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(124, 124, 124),
            new Color(178, 178, 178));
    Border border5 = BorderFactory.createLineBorder(Color.white, 2);
    Border border6 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(124, 124, 124),
            new Color(178, 178, 178));
    Border border7 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(124, 124, 124),
            new Color(178, 178, 178));
    JLabel jLabel9 = new JLabel();
    JComboBox cbTextAlign = new JComboBox();
    JCheckBox cbTitleVisible = new JCheckBox();
    JLabel jLabel10 = new JLabel();
    JSpinner spColWi = new JSpinner();
    JLabel jLabel11 = new JLabel();
    JLabel jLabel12 = new JLabel();
    JComboBox cbColWUnits = new JComboBox();
    JComboBox cbSymbolPos = new JComboBox();
    JCheckBox cbSymPen = new JCheckBox();
    ButtonPen bSymPen = new ButtonPen();

    public LegendEditor() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public LegendEditor(Chart chart) {
        this(chart.getLegend());
    }

    public LegendEditor(Legend legend) {
        this();

        this.chart = ((Chart)legend.getChart());
        this.legend = legend;

        legendTitle = legend.getTitle();
        cbVisible.setSelected(legend.getVisible());
        cbInverted.setSelected(legend.getInverted());
        cbFontColor.setSelected(legend.getFontSeriesColor());
        bDividing.setPen(legend.getDividingLines());
        spVertical.setValue(new Integer(legend.getVertSpacing()));

        cbCheckBox.addItem(Language.getString("No_check_boxes"));
        cbCheckBox.addItem(Language.getString("Check_boxes"));
        // TODO cbCheckBox.addItem("Radio buttons");

        if (legend.getCheckBoxes()) {
            cbCheckBox.setSelectedIndex(1);
        } else {
            cbCheckBox.setSelectedIndex(0);
        }

        cbLegStyle.setSelectedIndex(legend.getLegendStyle().getValue());
        cbTextStyle.setSelectedIndex(legend.getTextStyle().getValue());

        cbResize.setSelected(legend.getResizeChart());

        if (legend.getVertical()) {
            spMargin.setValue(new Integer(legend.getHorizMargin()));
        } else {
            spMargin.setValue(new Integer(legend.getVertMargin()));
        }

        spTopPos.setValue(new Integer(legend.getTopLeftPos()));

        cbSymVisible.setSelected(legend.getSymbol().getVisible());
        cbSymContinuous.setSelected(legend.getSymbol().getContinuous());

        ButtonGroup group = new ButtonGroup();
        group.add(rbLeft);
        group.add(rbRight);
        group.add(rbTop);
        group.add(rbBottom);

        MemoText.setText(legendTitle.getText());

        if (legendTitle.getTextAlign()==StringAlignment.NEAR)
            cbTextAlign.setSelectedIndex(0);
        else
            if (legendTitle.getTextAlign()==StringAlignment.CENTER)
                cbTextAlign.setSelectedIndex(1);
            else
                cbTextAlign.setSelectedIndex(2);

        cbTitleVisible.setSelected(legendTitle.getVisible());
	bSymPen.setPen(legend.getSymbol().getPen());

        switch (legend.getAlignment().getValue()) {
        case 0:
            rbLeft.setSelected(true);
            break;
        case 1:
            rbRight.setSelected(true);
            break;
        case 2:
            rbTop.setSelected(true);
            break;
        default:
            rbBottom.setSelected(true);
            break;
        }

        if(legend.getCustomPosition()) {
            cbCustomPos.setSelected(true);
        }
        else {
            cbCustomPos.setSelected(false);
        }

        EnableMarginControls();
        enableCustomPosition();

        shapeEditor=new ShapeEditor(jTabbedPane1, legend);
        titleEditor=new ShapeEditor(jTabbedPane2,legendTitle);

        cbColWUnits.addItem(Language.getString("Percent"));
        cbColWUnits.addItem(Language.getString("Pixels"));

        cbSymbolPos.addItem(Language.getString("Left"));
        cbSymbolPos.addItem(Language.getString("Right"));

    }

    static public void editLegend(Container owner, Legend legend) {
        JDialog d = new JDialog();
        d.setSize(new Dimension(400, 300));
        d.setModal(true);
        LegendEditor editor = new LegendEditor(legend);

        d.getContentPane().add(editor);
        d.setLocationRelativeTo(owner);
        d.setVisible(true);
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout2);
        jPanel2.setLayout(null);
        jPanel1.setLayout(null);
        cbVisible.setMnemonic(Language.getString("V").charAt(0));
        cbVisible.setText(Language.getString("Visible"));
        cbVisible.setBounds(new Rectangle(11, 14, 123, 26));
        cbVisible.addActionListener(new LegendEditor_cbVisible_actionAdapter(this));
        cbInverted.setMnemonic(Language.getString("I").charAt(0));
        cbInverted.setText(Language.getString("Inverted"));
        cbInverted.setBounds(new Rectangle(11, 44, 133, 19));
        cbInverted.addActionListener(new LegendEditor_cbInverted_actionAdapter(this));
        jTabbedPane1.setPreferredSize(new Dimension(55, 280));
        cbFontColor.setText(Language.getString("Font_Series_Color"));
        cbFontColor.setBounds(new Rectangle(11, 68, 138, 23));
        cbFontColor.addActionListener(new LegendEditor_jCheckBox1_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(cbLegStyle);
        jLabel1.setText(Language.getString("Legend_Style"));
        jLabel1.setBounds(new Rectangle(122, 17, 84, 24));
        jLabel2.setDisplayedMnemonic(Language.getString("Y").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setLabelFor(cbTextStyle);
        jLabel2.setText(Language.getString("Text_Style"));
        jLabel2.setBounds(new Rectangle(135, 50, 71, 20));
        cbLegStyle.setBounds(new Rectangle(215, 18, 122, 22));
        cbLegStyle.addActionListener(new LegendEditor_cbLegStyle_actionAdapter(this));
        cbTextStyle.setBounds(new Rectangle(215, 49, 122, 22));
        cbTextStyle.addActionListener(new
                                      LegendEditor_cbTextStyle_actionAdapter(this));
        bDividing.setBounds(new Rectangle(212, 123, 121, 25));
        bDividing.setMnemonic(Language.getString("D").charAt(0));
        bDividing.setText(Language.getString("Dividing_Lines"));
        jLabel3.setDisplayedMnemonic(Language.getString("V").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setLabelFor(spVertical);
        jLabel3.setText(Language.getString("Vertical_Spacing"));
        jLabel3.setBounds(new Rectangle(172, 91, 92, 14));
        spVertical.setBounds(new Rectangle(269, 87, 65, 22));
        spVertical.addChangeListener(new LegendEditor_spVertical_changeAdapter(this));
        cbCheckBox.setBounds(new Rectangle(16, 101, 143, 22));
        cbCheckBox.addActionListener(new LegendEditor_cbCheckBox_actionAdapter(this));
        cbCheckBox.addPropertyChangeListener(new
                                             LegendEditor_cbCheckBox_propertyChangeAdapter(this));
        jPanel5.setBorder(border2);
        jPanel5.setBounds(new Rectangle(17, 11, 110, 115));
        jPanel5.setLayout(null);
        rbLeft.setMnemonic(Language.getString("L").charAt(0));
        rbLeft.setText(Language.getString("Left"));
        rbLeft.setBounds(new Rectangle(10, 18, 93, 23));
        rbLeft.addActionListener(new LegendEditor_rbLeft_actionAdapter(this));
        rbRight.setMnemonic(Language.getString("R").charAt(0));
        rbRight.setText(Language.getString("Right"));
        rbRight.setBounds(new Rectangle(10, 38, 93, 23));
        rbRight.addActionListener(new LegendEditor_rbRight_actionAdapter(this));
        rbTop.setMnemonic(Language.getString("T").charAt(0));
        rbTop.setText(Language.getString("Top"));
        rbTop.setBounds(new Rectangle(10, 59, 93, 23));
        rbTop.addActionListener(new LegendEditor_rbTop_actionAdapter(this));
        rbBottom.setMnemonic(Language.getString("B").charAt(0));
        rbBottom.setText(Language.getString("Bottom"));
        rbBottom.setBounds(new Rectangle(10, 81, 93, 23));
        rbBottom.addActionListener(new LegendEditor_rbBottom_actionAdapter(this));
        cbResize.setMnemonic(Language.getString("Z").charAt(0));
        cbResize.setText(Language.getString("Resize_Chart"));
        cbResize.setBounds(new Rectangle(250, 16, 124, 23));
        cbResize.addActionListener(new LegendEditor_cbResize_actionAdapter(this));
        jPanel6.setBorder(border3);
        jPanel6.setBounds(new Rectangle(17, 133, 163, 79));
        jPanel6.setLayout(null);
        cbCustomPos.setMnemonic(Language.getString("C").charAt(0));
        cbCustomPos.setText(Language.getString("Custom"));
        cbCustomPos.setBounds(new Rectangle(7, 5, 81, 23));
        cbCustomPos.addActionListener(new
                                      LegendEditor_cbCustomPos_actionAdapter(this));
        jLabel4.setDisplayedMnemonic(Language.getString("E").charAt(0));
        jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel4.setLabelFor(spCustLeft);
        jLabel4.setText(Language.getString("Left"));
        jLabel4.setBounds(new Rectangle(51, 30, 34, 14));
        jLabel5.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel5.setLabelFor(spCustTop);
        jLabel5.setText(Language.getString("Top"));
        jLabel5.setBounds(new Rectangle(51, 54, 34, 14));
        spCustLeft.setBounds(new Rectangle(92, 26, 61, 20));
        spCustLeft.addChangeListener(new LegendEditor_spCustLeft_changeAdapter(this));
        spCustTop.setBounds(new Rectangle(92, 52, 61, 20));
        spCustTop.setToolTipText("");
        spCustTop.addChangeListener(new LegendEditor_spCustTop_changeAdapter(this));
        jLabel6.setDisplayedMnemonic(Language.getString("M").charAt(0));
        jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel6.setLabelFor(spMargin);
        jLabel6.setText(Language.getString("Margin"));
        jLabel6.setBounds(new Rectangle(163, 47, 79, 14));
        spMargin.setBounds(new Rectangle(250, 45, 86, 20));
        spMargin.addChangeListener(new LegendEditor_spMargin_changeAdapter(this));

        jLabel7.setDisplayedMnemonic(Language.getString("P").charAt(0));
        jLabel7.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel7.setLabelFor(spTopPos);
        jLabel7.setText(Language.getString("Position_Offset"));
        jLabel7.setBounds(new Rectangle(146, 75, 99, 14));
        spTopPos.setBounds(new Rectangle(250, 71, 86, 20));
        spTopPos.addChangeListener(new LegendEditor_spTopPos_changeAdapter(this));
        cbSymVisible.setMnemonic(Language.getString("V").charAt(0));
        cbSymVisible.setText(Language.getString("Visible"));
        cbSymVisible.setBounds(new Rectangle(81, 12, 81, 23));
        cbSymVisible.addActionListener(new
                                       LegendEditor_cbSymVisible_actionAdapter(this));
        jPanel3.setLayout(null);
        cbSymContinuous.setMnemonic(Language.getString("C").charAt(0));
        cbSymContinuous.setText(Language.getString("Continuous"));
        cbSymContinuous.setBounds(new Rectangle(81, 147, 114, 23));
        cbSymContinuous.addActionListener(new
                                          LegendEditor_cbSymContinuous_actionAdapter(this));
        cbSymSquared.setMnemonic(Language.getString("S").charAt(0));
        cbSymSquared.setText(Language.getString("Squared"));
        cbSymSquared.setBounds(new Rectangle(81, 169, 81, 23));
        cbSymSquared.addActionListener(new
                                       LegendEditor_cbSymSquared_actionAdapter(this));
        jPanel4.setLayout(borderLayout3);
        MemoText.setBorder(border7);
        MemoText.setBounds(new Rectangle(19, 30, 225, 96));
        MemoText.addKeyListener(new LegendEditor_MemoText_keyAdapter(this));
        jPanel7.setLayout(null);
        jLabel8.setDisplayedMnemonic(Language.getString("T").charAt(0));
        jLabel8.setText(Language.getString("Text"));
        jLabel8.setBounds(new Rectangle(19, 9, 32, 17));
        jLabel9.setDisplayedMnemonic(Language.getString("A").charAt(0));
        jLabel9.setText(Language.getString("Text_alignment"));
        jLabel9.setBounds(new Rectangle(19, 131, 100, 19));
        cbTextAlign.setBounds(new Rectangle(19, 152, 89, 21));
        cbTextAlign.addActionListener(new
                                      LegendEditor_cbTextAlign_actionAdapter(this));
        cbTitleVisible.setMnemonic(Language.getString("V").charAt(0));
        cbTitleVisible.setText(Language.getString("Visible"));
        cbTitleVisible.setBounds(new Rectangle(118, 152, 77, 21));
        cbTitleVisible.addActionListener(new
                LegendEditor_cbTitleVisible_actionAdapter(this));
        jLabel10.setDisplayedMnemonic('W');
        jLabel10.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel10.setText(Language.getString("Width")+":");
        jLabel10.setBounds(new Rectangle(27, 54, 57, 18));
        spColWi.setBounds(new Rectangle(90, 50, 49, 22));
        spColWi.addChangeListener(new LegendEditor_spColWi_changeAdapter(this));
        jLabel11.setDisplayedMnemonic('U');
        jLabel11.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel11.setText(Language.getString("Width_Units")+":");
        jLabel11.setBounds(new Rectangle(10, 85, 74, 18));
        jLabel12.setDisplayedMnemonic('P');
        jLabel12.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel12.setText(Language.getString("Position")+":");
        jLabel12.setBounds(new Rectangle(26, 115, 58, 18));
        cbColWUnits.setBounds(new Rectangle(90, 82, 96, 22));
        cbColWUnits.addActionListener(new
                                      LegendEditor_cbColWUnits_actionAdapter(this));
        cbSymbolPos.setBounds(new Rectangle(90, 113, 96, 22));
        cbSymbolPos.addActionListener(new
                                      LegendEditor_cbSymbolPos_actionAdapter(this));
        cbSymPen.setMnemonic('D');
        cbSymPen.setText("Default border");
        cbSymPen.setBounds(new Rectangle(199, 54, 109, 18));
        cbSymPen.addActionListener(new LegendEditor_cbSymPen_actionAdapter(this));
        bSymPen.setBounds(new Rectangle(209, 80, 73, 25));
        bSymPen.setMnemonic('B');
        bSymPen.setText("Border..");
        jTabbedPane1.add(jPanel1, Language.getString("Style"));
        jPanel1.add(cbVisible);
        jPanel1.add(cbInverted);
        jPanel1.add(cbFontColor);
        jPanel1.add(jLabel1);
        jPanel1.add(cbLegStyle);
        jPanel1.add(cbTextStyle);
        jPanel1.add(jLabel2);
        jPanel1.add(spVertical);
        jPanel1.add(jLabel3);
        jPanel1.add(cbCheckBox);
        jPanel1.add(bDividing);
        jTabbedPane1.add(jPanel2, Language.getString("Position"));
        jPanel2.add(jPanel5);
        jPanel2.add(cbResize);
        jPanel2.add(jPanel6);
        jPanel2.add(spMargin);
        jPanel2.add(jLabel6);
        jPanel2.add(spTopPos);
        jPanel2.add(jLabel7);
        jPanel6.add(spCustLeft);
        jPanel6.add(spCustTop);
        jPanel6.add(jLabel4);
        jPanel6.add(jLabel5);
        jPanel6.add(cbCustomPos);
        jPanel5.add(rbTop);
        jPanel5.add(rbBottom);
        jPanel5.add(rbLeft);
        jPanel5.add(rbRight);
        jTabbedPane1.add(jPanel3, Language.getString("Symbols"));
        jPanel3.add(cbSymVisible);
        jPanel3.add(jLabel10);
        jPanel3.add(spColWi);
        jPanel3.add(jLabel11);
        jPanel3.add(cbSymPen);
        jPanel3.add(bSymPen);
        jPanel3.add(cbSymSquared);
        jPanel3.add(cbSymContinuous);
        jPanel3.add(jLabel12);
        jPanel3.add(cbColWUnits);
        jPanel3.add(cbSymbolPos);
        jTabbedPane1.add(jPanel4, Language.getString("Title"));
        jPanel4.add(jTabbedPane2, java.awt.BorderLayout.CENTER);
        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jPanel7.add(jLabel8);
        jPanel7.add(jLabel9);
        jPanel7.add(MemoText);
        jPanel7.add(cbTextAlign);
        jPanel7.add(cbTitleVisible);
        jTabbedPane2.add(jPanel7, Language.getString("Options"));
        cbTextStyle.addItem(Language.getString("Plain"));
        cbTextStyle.addItem(Language.getString("Left_Value"));
        cbTextStyle.addItem(Language.getString("Right_Value"));
        cbTextStyle.addItem(Language.getString("Left_Percent"));
        cbTextStyle.addItem(Language.getString("Right_Percent"));
        cbTextStyle.addItem(Language.getString("XValue"));
        cbTextStyle.addItem(Language.getString("Value"));
        cbTextStyle.addItem(Language.getString("Percent"));
        cbTextStyle.addItem(Language.getString("Xand_Value"));
        cbTextStyle.addItem(Language.getString("Xand_Percent"));

        cbLegStyle.addItem(Language.getString("Automatic"));
        cbLegStyle.addItem(Language.getString("Series_Names"));
        cbLegStyle.addItem(Language.getString("Series_Values"));
        cbLegStyle.addItem(Language.getString("Last_Values"));
        cbLegStyle.addItem(Language.getString("Palette"));

        cbTextAlign.addItem(Language.getString("Left"));
        cbTextAlign.addItem(Language.getString("Center"));
        cbTextAlign.addItem(Language.getString("Right"));
    }

    private void EnableMarginControls() {
        spMargin.setValue(new Integer(legend.getVertical() ? legend.getHorizMargin() : legend.getVertMargin()));
    }

    public void cbVisible_actionPerformed(ActionEvent e) {
        legend.setVisible(cbVisible.isSelected());
    }

    public void cbInverted_actionPerformed(ActionEvent e) {
        legend.setInverted(cbInverted.isSelected());
    }

    public void jCheckBox1_actionPerformed(ActionEvent e) {
        legend.setFontSeriesColor(cbFontColor.isSelected());
    }

    public void spVertical_stateChanged(ChangeEvent e) {
        legend.setVertSpacing(((Integer) spVertical.getValue()).intValue());
    }

    public void cbCheckBox_propertyChange(PropertyChangeEvent
                                          propertyChangeEvent) {

    }

    public void cbResize_actionPerformed(ActionEvent e) {
        legend.setResizeChart(cbResize.isSelected());
    }

    public void cbCustomPos_actionPerformed(ActionEvent e) {
        legend.setCustomPosition(cbCustomPos.isSelected());

        if (legend.getCustomPosition()) {

            legend.setResizeChart(false);
            cbResize.setSelected(false);
        }

        enableCustomPosition();

    }

    private void enableCustomPosition() {
        boolean tmp = legend.getCustomPosition();
        spCustLeft.setEnabled(tmp);
        spCustTop.setEnabled(tmp);

        if (tmp) {
            spCustLeft.setValue(new Integer(legend.getLeft()));
            spCustTop.setValue(new Integer(legend.getTop()));
        }

        cbResize.setEnabled(!tmp);
        spMargin.setEnabled(!tmp);
        spTopPos.setEnabled(!tmp);
        rbLeft.setEnabled(!tmp);
        rbTop.setEnabled(!tmp);
        rbRight.setEnabled(!tmp);
        rbBottom.setEnabled(!tmp);
    }

    public void rbLeft_actionPerformed(ActionEvent e) {
        legend.setAlignment(LegendAlignment.LEFT);
    }

    public void rbRight_actionPerformed(ActionEvent e) {
        legend.setAlignment(LegendAlignment.RIGHT);
    }

    public void rbTop_actionPerformed(ActionEvent e) {
        legend.setAlignment(LegendAlignment.TOP);
    }

    public void rbBottom_actionPerformed(ActionEvent e) {
        legend.setAlignment(LegendAlignment.BOTTOM);
    }

    public void spMargin_stateChanged(ChangeEvent e) {
        if (legend.getVertical()) {
            legend.setHorizMargin(((Integer) spMargin.getValue()).intValue());
        } else {
            legend.setVertMargin(((Integer) spMargin.getValue()).intValue());
        }
    }

    public void spTopPos_stateChanged(ChangeEvent e) {
        legend.setTopLeftPos(((Integer) spTopPos.getValue()).intValue());
    }

    public void cbSymVisible_actionPerformed(ActionEvent e) {
        legend.getSymbol().setVisible(cbSymVisible.isSelected());
    }

    public void cbSymContinuous_actionPerformed(ActionEvent e) {
        legend.getSymbol().setContinuous(cbSymContinuous.isSelected());
    }

    public void cbSymSquared_actionPerformed(ActionEvent e) {
        legend.getSymbol().setSquared(cbSymSquared.isSelected());
        enableWidthControls();
    }

    private void enableWidthControls() {
        //spColWidth.setEnabled(! legend.getSymbol().getSquared());

    }

    public void cbCheckBox_actionPerformed(ActionEvent e) {
        if (cbCheckBox.getSelectedIndex()==1)
            legend.setCheckBoxes(true);
        else
            legend.setCheckBoxes(false);
    }

    public void cbLegStyle_actionPerformed(ActionEvent e) {
        if (cbLegStyle.getModel().getSize()==5)
            switch (cbLegStyle.getSelectedIndex())  {
              case 0: legend.setLegendStyle(LegendStyle.AUTO); break;
              case 1: legend.setLegendStyle(LegendStyle.SERIES); break;
              case 2: legend.setLegendStyle(LegendStyle.VALUES); break;
              case 3: legend.setLegendStyle(LegendStyle.LASTVALUES); break;
              default: legend.setLegendStyle(LegendStyle.PALETTE); break;
            }
    }

    public void cbTextStyle_actionPerformed(ActionEvent e) {
        if (cbTextStyle.getModel().getSize()==10)
            switch (cbTextStyle.getSelectedIndex()) {
              case 0: legend.setTextStyle(LegendTextStyle.PLAIN); break;
              case 1: legend.setTextStyle(LegendTextStyle.LEFTVALUE); break;
              case 2: legend.setTextStyle(LegendTextStyle.RIGHTVALUE); break;
              case 3: legend.setTextStyle(LegendTextStyle.LEFTPERCENT); break;
              case 4: legend.setTextStyle(LegendTextStyle.RIGHTPERCENT); break;
              case 5: legend.setTextStyle(LegendTextStyle.XVALUE); break;
              case 6: legend.setTextStyle(LegendTextStyle.VALUE); break;
              case 7: legend.setTextStyle(LegendTextStyle.PERCENT); break;
              case 8: legend.setTextStyle(LegendTextStyle.XANDVALUE); break;
              case 9: legend.setTextStyle(LegendTextStyle.XANDPERCENT); break;
              default: legend.setTextStyle(LegendTextStyle.VALUE); break;
      }
    }

    public void spCustLeft_stateChanged(ChangeEvent e) {
      legend.setLeft(((Integer)spCustLeft.getValue()).intValue());
    }

    public void spCustTop_stateChanged(ChangeEvent e) {
        legend.setTop(((Integer)spCustTop.getValue()).intValue());
    }

    public void cbTitleVisible_actionPerformed(ActionEvent e) {
        legendTitle.setVisible(cbTitleVisible.isSelected());
    }

    public void cbTextAlign_actionPerformed(ActionEvent e) {
        if (cbTextAlign.getModel().getSize()==3)
            switch (cbTextAlign.getSelectedIndex()) {
            case 0:
                legendTitle.setTextAlign(StringAlignment.NEAR);
                break;
            case 1:
                legendTitle.setTextAlign(StringAlignment.CENTER);
                break;
            default:
                legendTitle.setTextAlign(StringAlignment.FAR);
                break;
            }
    }

    public void MemoText_keyReleased(KeyEvent e) {
        legendTitle.setText(MemoText.getText());
    }

    public void spColWi_stateChanged(ChangeEvent changeEvent) {
        legend.getSymbol().setWidth(((Integer)spColWi.getValue()).intValue());
    }

    public void cbColWUnits_actionPerformed(ActionEvent actionEvent) {
        if (cbColWUnits.getSelectedIndex()==0)
          legend.getSymbol().setWidthUnits(LegendSymbolSize.PERCENT);
        else
          legend.getSymbol().setWidthUnits(LegendSymbolSize.PIXELS);
    }

    public void cbSymbolPos_actionPerformed(ActionEvent actionEvent) {
        if (cbSymbolPos.getSelectedIndex()==0)
            legend.getSymbol().setPosition(LegendSymbolPosition.LEFT);
        else
            legend.getSymbol().setPosition(LegendSymbolPosition.RIGHT);
    }

    public void cbSymPen_actionPerformed(ActionEvent actionEvent) {
        legend.getSymbol().setDefaultPen(cbSymPen.isSelected());
        bSymPen.setEnabled(!cbSymPen.isSelected());
    }
}


class LegendEditor_cbSymPen_actionAdapter implements ActionListener {
    private LegendEditor adaptee;
    LegendEditor_cbSymPen_actionAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.cbSymPen_actionPerformed(actionEvent);
    }
}


class LegendEditor_cbSymbolPos_actionAdapter implements ActionListener {
    private LegendEditor adaptee;
    LegendEditor_cbSymbolPos_actionAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.cbSymbolPos_actionPerformed(actionEvent);
    }
}


class LegendEditor_cbColWUnits_actionAdapter implements ActionListener {
    private LegendEditor adaptee;
    LegendEditor_cbColWUnits_actionAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.cbColWUnits_actionPerformed(actionEvent);
    }
}


class LegendEditor_spColWi_changeAdapter implements ChangeListener {
    private LegendEditor adaptee;
    LegendEditor_spColWi_changeAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent changeEvent) {
        adaptee.spColWi_stateChanged(changeEvent);
    }
}


class LegendEditor_MemoText_keyAdapter extends KeyAdapter {
    private LegendEditor adaptee;
    LegendEditor_MemoText_keyAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void keyReleased(KeyEvent e) {
        adaptee.MemoText_keyReleased(e);
    }
}


class LegendEditor_cbTextAlign_actionAdapter implements ActionListener {
    private LegendEditor adaptee;
    LegendEditor_cbTextAlign_actionAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbTextAlign_actionPerformed(e);
    }
}


class LegendEditor_cbTitleVisible_actionAdapter implements ActionListener {
    private LegendEditor adaptee;
    LegendEditor_cbTitleVisible_actionAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbTitleVisible_actionPerformed(e);
    }
}


class LegendEditor_spCustLeft_changeAdapter implements ChangeListener {
    private LegendEditor adaptee;
    LegendEditor_spCustLeft_changeAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spCustLeft_stateChanged(e);
    }
}


class LegendEditor_spCustTop_changeAdapter implements ChangeListener {
    private LegendEditor adaptee;
    LegendEditor_spCustTop_changeAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spCustTop_stateChanged(e);
    }
}


class LegendEditor_cbTextStyle_actionAdapter implements ActionListener {
    private LegendEditor adaptee;
    LegendEditor_cbTextStyle_actionAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbTextStyle_actionPerformed(e);
    }
}


class LegendEditor_cbLegStyle_actionAdapter implements ActionListener {
    private LegendEditor adaptee;
    LegendEditor_cbLegStyle_actionAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbLegStyle_actionPerformed(e);
    }
}


class LegendEditor_cbCheckBox_actionAdapter implements ActionListener {
    private LegendEditor adaptee;
    LegendEditor_cbCheckBox_actionAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbCheckBox_actionPerformed(e);
    }
}


class LegendEditor_cbSymSquared_actionAdapter implements ActionListener {
    private LegendEditor adaptee;
    LegendEditor_cbSymSquared_actionAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbSymSquared_actionPerformed(e);
    }
}


class LegendEditor_cbSymContinuous_actionAdapter implements ActionListener {
    private LegendEditor adaptee;
    LegendEditor_cbSymContinuous_actionAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbSymContinuous_actionPerformed(e);
    }
}


class LegendEditor_cbSymVisible_actionAdapter implements ActionListener {
    private LegendEditor adaptee;
    LegendEditor_cbSymVisible_actionAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbSymVisible_actionPerformed(e);
    }
}


class LegendEditor_rbBottom_actionAdapter implements ActionListener {
    private LegendEditor adaptee;
    LegendEditor_rbBottom_actionAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbBottom_actionPerformed(e);
    }
}


class LegendEditor_rbTop_actionAdapter implements ActionListener {
    private LegendEditor adaptee;
    LegendEditor_rbTop_actionAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbTop_actionPerformed(e);
    }
}


class LegendEditor_rbRight_actionAdapter implements ActionListener {
    private LegendEditor adaptee;
    LegendEditor_rbRight_actionAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbRight_actionPerformed(e);
    }
}


class LegendEditor_rbLeft_actionAdapter implements ActionListener {
    private LegendEditor adaptee;
    LegendEditor_rbLeft_actionAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbLeft_actionPerformed(e);
    }
}


class LegendEditor_cbCustomPos_actionAdapter implements ActionListener {
    private LegendEditor adaptee;
    LegendEditor_cbCustomPos_actionAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbCustomPos_actionPerformed(e);
    }
}


class LegendEditor_cbResize_actionAdapter implements ActionListener {
    private LegendEditor adaptee;
    LegendEditor_cbResize_actionAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbResize_actionPerformed(e);
    }
}


class LegendEditor_cbCheckBox_propertyChangeAdapter implements
        PropertyChangeListener {
    private LegendEditor adaptee;
    LegendEditor_cbCheckBox_propertyChangeAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        adaptee.cbCheckBox_propertyChange(propertyChangeEvent);
    }
}


class LegendEditor_spVertical_changeAdapter implements ChangeListener {
    private LegendEditor adaptee;
    LegendEditor_spVertical_changeAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent changeEvent) {
        adaptee.spVertical_stateChanged(changeEvent);
    }
}


class LegendEditor_jCheckBox1_actionAdapter implements ActionListener {
    private LegendEditor adaptee;
    LegendEditor_jCheckBox1_actionAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jCheckBox1_actionPerformed(e);
    }
}


class LegendEditor_cbInverted_actionAdapter implements ActionListener {
    private LegendEditor adaptee;
    LegendEditor_cbInverted_actionAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbInverted_actionPerformed(e);
    }
}


class LegendEditor_cbVisible_actionAdapter implements ActionListener {
    private LegendEditor adaptee;
    LegendEditor_cbVisible_actionAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbVisible_actionPerformed(e);
    }
}

class LegendEditor_spMargin_changeAdapter implements ChangeListener {
    private LegendEditor adaptee;
    LegendEditor_spMargin_changeAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent changeEvent) {
        adaptee.spMargin_stateChanged(changeEvent);
    }
}

class LegendEditor_spTopPos_changeAdapter implements ChangeListener {
    private LegendEditor adaptee;
    LegendEditor_spTopPos_changeAdapter(LegendEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent changeEvent) {
        adaptee.spTopPos_stateChanged(changeEvent);
    }
}

