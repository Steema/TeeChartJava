package com.steema.teechart.editors;

import java.awt.*;
import com.steema.teechart.languages.Language;
import java.awt.Rectangle;
import java.awt.event.*;
import java.awt.Insets;
import java.awt.Dimension;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.event.*;

import com.steema.teechart.*;
import com.steema.teechart.axis.Axes;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.axis.AxisLabelAlign;
import com.steema.teechart.axis.AxisLabels;
import com.steema.teechart.axis.AxisLabelStyle;
import java.text.DecimalFormat;



/**
 *
 * <p>Title:  AxesEditor class</p>
 *
 * <p>Description: Editor dialog for Chart axes.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class AxesEditor extends JPanel {

    private Chart chart;
    private Axes axes;
    private Axis axis;

    JPanel jPanel1 = new JPanel();
    JCheckBox cbBehind = new JCheckBox();
    JCheckBox cbViewAxes = new JCheckBox();
    BorderLayout borderLayout1 = new BorderLayout();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();
    JPanel jPanel6 = new JPanel();
    JPanel jPanel7 = new JPanel();
    JPanel jPanel8 = new JPanel();
    JLabel jLabel1 = new JLabel();
    JPanel jPanel9 = new JPanel();
    JList lAxes = new JList();
    JButton bIncrement = new JButton();
    JLabel jLabel2 = new JLabel();
    JCheckBox cbLog = new JCheckBox();
    JLabel jLabel3 = new JLabel();
    JTextPane eLogBase = new JTextPane();
    JCheckBox cbLogE = new JCheckBox();
    JTabbedPane jTabbedPane2 = new JTabbedPane();
    JPanel jPanel10 = new JPanel();
    JPanel jPanel11 = new JPanel();
    JCheckBox cbAutoMin = new JCheckBox();
    JLabel lAxisMin = new JLabel();
    JButton bAxisMin = new JButton();
    JLabel jLabel5 = new JLabel();
    JSpinner spMinOff = new JSpinner();
    JLabel lAxisMax = new JLabel();
    JSpinner spMaxOff = new JSpinner();
    JButton bAxisMax = new JButton();
    JLabel jLabel7 = new JLabel();
    JCheckBox cbAutoMax = new JCheckBox();
    BorderLayout borderLayout2 = new BorderLayout();
    JTabbedPane jTabbedPane3 = new JTabbedPane();
    JPanel jPanel12 = new JPanel();
    JPanel jPanel13 = new JPanel();
    JLabel jLabel8 = new JLabel();
    JTextField eTitle = new JTextField();
    JCheckBox cbTitleVisible = new JCheckBox();
    JButton bDelete = new JButton();
    JButton bAdd = new JButton();
    ButtonPen bAxisPen = new ButtonPen();
    ButtonPen bAxisGrid = new ButtonPen();
    ButtonPen bTickPen = new ButtonPen();
    ButtonPen bTickInner = new ButtonPen();
    ButtonPen bTickMinor = new ButtonPen();
    ButtonPen bMinorGrid = new ButtonPen();
    JTabbedPane jTabbedPane4 = new JTabbedPane();
    BorderLayout borderLayout3 = new BorderLayout();
    JPanel jPanel14 = new JPanel();
    JPanel jPanel15 = new JPanel();
    JPanel jPanel16 = new JPanel();
    JCheckBox cbLabels = new JCheckBox();
    JCheckBox cbMultiline = new JCheckBox();
    JCheckBox cbRoundFirst = new JCheckBox();
    JCheckBox cbLabelOnAxis = new JCheckBox();
    JCheckBox cbAlternate = new JCheckBox();
    JCheckBox cbExpo = new JCheckBox();
    JCheckBox cbLabelAlign = new JCheckBox();
    JLabel lAxisFormat = new JLabel();
    JComboBox cbFormat = new JComboBox();
    JCheckBox cbGridCentered = new JCheckBox();
    JCheckBox cbTickOnLabels = new JCheckBox();
    JPanel jPanel17 = new JPanel();
    JCheckBox cbVisible = new JCheckBox();
    JCheckBox cbAutomatic = new JCheckBox();
    JCheckBox cbInverted = new JCheckBox();
    BorderLayout borderLayout4 = new BorderLayout();
    BorderLayout borderLayout5 = new BorderLayout();
    BorderLayout borderLayout6 = new BorderLayout();
    private ChartFontEditor titleFont = new ChartFontEditor();
    private ChartFontEditor labelsFont = new ChartFontEditor();
    JLabel jLabel10 = new JLabel();
    JSpinner spPosition = new JSpinner();
    JLabel jLabel25 = new JLabel();
    JSpinner spGridDrawEvery = new JSpinner();
    JLabel jLabel11 = new JLabel();
    JComboBox cbUnits = new JComboBox();
    JLabel jLabel12 = new JLabel();
    JLabel jLabel13 = new JLabel();
    JSpinner spStart = new JSpinner();
    JSpinner spEnd = new JSpinner();
    JLabel jLabel14 = new JLabel();
    JCheckBox cbOtherSide = new JCheckBox();
    JCheckBox cbHorizontal = new JCheckBox();
    JSpinner spZPos = new JSpinner();
    JLabel jLabel15 = new JLabel();
    JSpinner spMinorLength = new JSpinner();
    JLabel jLabel16 = new JLabel();
    JSpinner spMinorCount = new JSpinner();
    JLabel jLabel17 = new JLabel();
    JSpinner spTitleAngle = new JSpinner();
    JLabel jLabel18 = new JLabel();
    JSpinner spTitleSize = new JSpinner();
    JLabel jLabel19 = new JLabel();
    JSpinner spLabelsSize = new JSpinner();
    JLabel jLabel20 = new JLabel();
    JSpinner spLabelsAngle = new JSpinner();
    JLabel jLabel21 = new JLabel();
    JSpinner spMinSep = new JSpinner();
    JLabel jLabel22 = new JLabel();
    JComboBox cbStyle = new JComboBox();
    JLabel jLabel9 = new JLabel();
    JLabel jLabel23 = new JLabel();
    JSpinner spTickLen = new JSpinner();
    JSpinner spInnerLen = new JSpinner();
    JSpinner spGridZ = new JSpinner();
    JLabel jLabel24 = new JLabel();
    JLabel lAxisIncre = new JLabel();

    public AxesEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        cbUnits.addItem(Language.getString("Percent"));
        cbUnits.addItem(Language.getString("Pixels"));

        cbStyle.addItem(Language.getString("Auto"));
        cbStyle.addItem(Language.getString("None"));
        cbStyle.addItem(Language.getString("Value"));
        cbStyle.addItem(Language.getString("Mark"));
        cbStyle.addItem(Language.getString("Text"));
    }

    public AxesEditor(Chart chart) {
        this();
        this.chart = chart;
        axes = chart.getAxes();
        cbViewAxes.setSelected(axes.getVisible());
        cbBehind.setSelected(axes.getDrawBehind());
        lAxes.setModel(new DefaultListModel());
        addAxes(true);

        axis = axes.getLeft();
        setAxisIndex();
    }

    public void setAxis(Axis axis) {
        if (this.axis != axis) {
            this.axis = axis;
            setAxisIndex();
        }
    }

  void spGridDrawEvery_stateChanged(ChangeEvent changeEvent) {
    if (((Integer)spGridDrawEvery.getValue()).intValue() != axis.getGrid().getDrawEvery()){
      axis.getGrid().setDrawEvery(((Integer)spGridDrawEvery.getValue()).intValue());
    }
  }

    /**
     * addAxes
     */
    private void addAxes(boolean addDepth) {
        DefaultListModel l = (DefaultListModel) lAxes.getModel();
        l.clear();
        l.addElement(Language.getString("LeftAxis"));
        l.addElement(Language.getString("TopAxis"));
        l.addElement(Language.getString("RightAxis"));
        l.addElement(Language.getString("BottomAxis"));

        if (addDepth) {
            l.addElement(Language.getString("DepthAxis"));
            l.addElement(Language.getString("DepthTopAxis"));
        }

        for (int t = Axes.STANDARD_AXES; t < chart.getAxes().getCount(); t++) {
            l.addElement(Language.getString("CustomAxesEditor") + " " +
                         Integer.toString(t - Axes.STANDARD_AXES));
        }
    }

    private void setAxisIndex() {
        if (axis == axes.getLeft()) {
            lAxes.setSelectedIndex(0);
        } else
        if (axis == axes.getRight()) {
            lAxes.setSelectedIndex(1);
        } else
        if (axis == axes.getTop()) {
            lAxes.setSelectedIndex(2);
        } else
        if (axis == axes.getBottom()) {
            lAxes.setSelectedIndex(3);
        } else
        if (axis == axes.getDepth()) {
            lAxes.setSelectedIndex(4);
        } else {
        if (axis==axes.getDepthTop())
            lAxes.setSelectedIndex(5);
        else
            lAxes.setSelectedIndex(axes.indexOf(axis));
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        cbBehind.setMnemonic(Language.getString("B").charAt(0));
        cbBehind.setText(Language.getString("Behind"));
        cbBehind.setBounds(new Rectangle(0, 23, 86, 23));
        cbBehind.addActionListener(new AxesEditor_cbBehind_actionAdapter(this));
        cbViewAxes.setMnemonic(Language.getString("V").charAt(0));
        cbViewAxes.setText(Language.getString("Visible"));
        cbViewAxes.setBounds(new Rectangle(1, 0, 87, 23));
        cbViewAxes.addActionListener(new AxesEditor_cbViewAxes_actionAdapter(this));
        jPanel1.setMinimumSize(new Dimension(100, 60));
        jPanel1.setPreferredSize(new Dimension(90, 90));
        jPanel1.setLayout(null);
        jPanel8.setBounds(new Rectangle(5, 51, 81, 206));
        jPanel8.setLayout(null);
        jLabel1.setDisplayedMnemonic(Language.getString("X").charAt(0));
        jLabel1.setLabelFor(lAxes);
        jLabel1.setText(Language.getString("Axes"));
        jLabel1.setBounds(new Rectangle(2, 4, 34, 14));
        jPanel9.setBounds(new Rectangle(1, 171, 78, 32));
        jPanel9.setLayout(null);
        lAxes.setBounds(new Rectangle(0, 21, 81, 147));
        lAxes.addListSelectionListener(new
                                       AxesEditor_lAxes_listSelectionAdapter(this));
        jPanel2.setLayout(borderLayout4);
        jPanel3.setLayout(borderLayout2);
        jPanel4.setLayout(borderLayout3);
        jPanel5.setLayout(null);
        jPanel6.setLayout(null);
        jPanel7.setLayout(null);
        bIncrement.setBounds(new Rectangle(9, 35, 75, 23));
        bIncrement.setMargin(new Insets(2, 14, 2, 2));
        bIncrement.setMnemonic(Language.getString("C").charAt(0));
        bIncrement.setText(Language.getString("Change"));
        bIncrement.addActionListener(new AxesEditor_bIncrement_actionAdapter(this));
        jLabel2.setText(Language.getString("Desired_increment"));
        jLabel2.setBounds(new Rectangle(91, 40, 103, 14));
        cbLog.setMnemonic(Language.getString("L").charAt(0));
        cbLog.setText(Language.getString("Logarithmic"));
        cbLog.setBounds(new Rectangle(5, 64, 90, 23));
        cbLog.addActionListener(new AxesEditor_cbLog_actionAdapter(this));
        jLabel3.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setLabelFor(eLogBase);
        jLabel3.setText(Language.getString("Log_Base"));
        jLabel3.setBounds(new Rectangle(106, 68, 56, 14));
        eLogBase.setText("10");
        eLogBase.setBounds(new Rectangle(165, 64, 49, 20));
        eLogBase.addKeyListener(new AxesEditor_eLogBase_keyAdapter(this));
        cbLogE.setText(Language.getString("e"));
        cbLogE.setBounds(new Rectangle(219, 64, 37, 23));
        cbLogE.addActionListener(new AxesEditor_cbLogE_actionAdapter(this));
        cbAutoMin.setText(Language.getString("Auto"));
        cbAutoMin.setBounds(new Rectangle(8, 6, 52, 23));
        cbAutoMin.addActionListener(new AxesEditor_cbAutoMin_actionAdapter(this));
        jPanel10.setLayout(null);
        jPanel11.setLayout(null);
        lAxisMin.setText(Language.getString("_min"));
        lAxisMin.setBounds(new Rectangle(78, 10, 155, 14));
        bAxisMin.setBounds(new Rectangle(13, 34, 75, 23));
        bAxisMin.setMargin(new Insets(2, 14, 2, 2));
        bAxisMin.setMnemonic(Language.getString("N").charAt(0));
        bAxisMin.setText(Language.getString("Change"));
        bAxisMin.addActionListener(new AxesEditor_bAxisMin_actionAdapter(this));
        jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel5.setLabelFor(spMinOff);
        jLabel5.setText(Language.getString("Offset"));
        jLabel5.setBounds(new Rectangle(92, 38, 56, 14));
        spMinOff.setBounds(new Rectangle(156, 34, 55, 20));
        spMinOff.addChangeListener(new AxesEditor_spMinOff_changeAdapter(this));
        lAxisMax.setText(Language.getString("_max"));
        lAxisMax.setBounds(new Rectangle(78, 10, 155, 14));
        spMaxOff.setBounds(new Rectangle(156, 36, 55, 20));
        spMaxOff.addChangeListener(new AxesEditor_spMaxOff_changeAdapter(this));
        bAxisMax.setBounds(new Rectangle(13, 34, 75, 23));
        bAxisMax.setMargin(new Insets(2, 14, 2, 2));
        bAxisMax.setMnemonic(Language.getString("N").charAt(0));
        bAxisMax.setText(Language.getString("Change"));
        bAxisMax.addActionListener(new AxesEditor_bAxisMax_actionAdapter(this));
        jLabel7.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel7.setLabelFor(spMaxOff);
        jLabel7.setText(Language.getString("Offset"));
        jLabel7.setBounds(new Rectangle(92, 38, 56, 14));
        cbAutoMax.setText(Language.getString("Auto"));
        cbAutoMax.setBounds(new Rectangle(8, 6, 52, 23));
        cbAutoMax.addActionListener(new AxesEditor_cbAutoMax_actionAdapter(this));
        jPanel12.setLayout(null);
        jLabel8.setDisplayedMnemonic(Language.getString("I").charAt(0));
        jLabel8.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel8.setLabelFor(eTitle);
        jLabel8.setText(Language.getString("Title"));
        jLabel8.setBounds(new Rectangle(5, 18, 54, 14));
        eTitle.setBounds(new Rectangle(63, 16, 181, 20));
        eTitle.addKeyListener(new AxesEditor_eTitle_keyAdapter(this));
        eTitle.addActionListener(new AxesEditor_eTitle_actionAdapter(this));
        cbTitleVisible.setMnemonic(Language.getString("V").charAt(0));
        cbTitleVisible.setText(Language.getString("Visible"));
        cbTitleVisible.setBounds(new Rectangle(58, 107, 81, 23));
        cbTitleVisible.addActionListener(new
                                         AxesEditor_cbTitleVisible_actionAdapter(this));
        bDelete.setBounds(new Rectangle(44, 5, 29, 23));
        bDelete.setMargin(new Insets(2, 1, 2, 1));
        bDelete.setText("-");
        bDelete.addActionListener(new AxesEditor_bDel_actionAdapter(this));
        bAdd.setBounds(new Rectangle(5, 5, 29, 23));
        bAdd.setMargin(new Insets(2, 1, 2, 1));
        bAdd.setText("+");
        bAdd.addActionListener(new AxesEditor_bAdd_actionAdapter(this));
        bAxisPen.setBounds(new Rectangle(9, 12, 75, 23));
        bAxisPen.setMnemonic(Language.getString("A").charAt(0));
        bAxisPen.setText(Language.getString("Axis"));
        bAxisGrid.setBounds(new Rectangle(9, 46, 75, 23));
        bAxisGrid.setMnemonic(Language.getString("G").charAt(0));
        bAxisGrid.setText(Language.getString("Grid"));
        bTickPen.setBounds(new Rectangle(9, 81, 75, 23));
        bTickPen.setMnemonic(Language.getString("I").charAt(0));
        bTickPen.setText(Language.getString("Ticks"));
        bTickInner.setBounds(new Rectangle(9, 115, 75, 23));
        bTickInner.setMnemonic(Language.getString("N").charAt(0));
        bTickInner.setText(Language.getString("Inner"));
        bTickMinor.setBounds(new Rectangle(21, 18, 91, 23));
        bTickMinor.setMnemonic(Language.getString("T").charAt(0));
        bTickMinor.setText(Language.getString("Ticks"));
        bMinorGrid.setBounds(new Rectangle(21, 51, 91, 23));
        bMinorGrid.setMnemonic(Language.getString("G").charAt(0));
        bMinorGrid.setText(Language.getString("Grid"));
        jPanel14.setLayout(null);
        jPanel15.setLayout(null);
        jPanel16.setLayout(borderLayout6);
        cbLabels.setMnemonic(Language.getString("I").charAt(0));
        cbLabels.setText(Language.getString("Visible"));
        cbLabels.setBounds(new Rectangle(8, 10, 81, 23));
        cbLabels.addActionListener(new AxesEditor_cbLabels_actionAdapter(this));
        cbMultiline.setMnemonic(Language.getString("E").charAt(0));
        cbMultiline.setText(Language.getString("Multi_line"));
        cbMultiline.setBounds(new Rectangle(8, 31, 81, 23));
        cbMultiline.addActionListener(new AxesEditor_cbMultiline_actionAdapter(this));
        cbRoundFirst.setText(Language.getString("Round_First"));
        cbRoundFirst.setBounds(new Rectangle(8, 54, 81, 23));
        cbRoundFirst.addActionListener(new
                                       AxesEditor_cbRoundFirst_actionAdapter(this));
        cbLabelOnAxis.setMnemonic(Language.getString("X").charAt(0));
        cbLabelOnAxis.setText(Language.getString("Label_on_Axis"));
        cbLabelOnAxis.setBounds(new Rectangle(8, 78, 112, 23));
        cbLabelOnAxis.addActionListener(new
                                        AxesEditor_cbLabelOnAxis_actionAdapter(this));
        cbAlternate.setMnemonic(Language.getString("A").charAt(0));
        cbAlternate.setText(Language.getString("Alternate"));
        cbAlternate.setBounds(new Rectangle(8, 103, 81, 23));
        cbAlternate.addActionListener(new AxesEditor_cbAlternate_actionAdapter(this));
        cbExpo.setMnemonic(Language.getString("E").charAt(0));
        cbExpo.setText(Language.getString("Exponential"));
        cbExpo.setBounds(new Rectangle(22, 26, 94, 23));
        cbExpo.addActionListener(new AxesEditor_cbExpo_actionAdapter(this));
        cbLabelAlign.setMnemonic(Language.getString("D").charAt(0));
        cbLabelAlign.setText(Language.getString("Default_Alignment"));
        cbLabelAlign.setBounds(new Rectangle(22, 108, 138, 23));
        lAxisFormat.setDisplayedMnemonic(Language.getString("M").charAt(0));
        lAxisFormat.setLabelFor(cbFormat);
        lAxisFormat.setText(Language.getString("Labels_Format"));
        lAxisFormat.setBounds(new Rectangle(28, 58, 104, 14));
        cbFormat.setEditable(true);
        cbFormat.setBounds(new Rectangle(28, 73, 138, 22));
        cbFormat.addActionListener(new AxesEditor_cbFormat_actionAdapter(this));
        cbGridCentered.setMnemonic(Language.getString("E").charAt(0));
        cbGridCentered.setText(Language.getString("Centered"));
        cbGridCentered.setBounds(new Rectangle(180, 46, 101, 23));
        cbGridCentered.addActionListener(new
                                         AxesEditor_cbGridCentered_actionAdapter(this));
        spGridDrawEvery.setBounds(new Rectangle(132, 152, 42, 20));
        spGridDrawEvery.addChangeListener(new AxesEditor_spGridDrawEvery_changeAdapter(this));
        spGridDrawEvery.setVisible(true);
        jLabel25.setBounds(new Rectangle(70,152,60,20));
        jLabel25.setText(Language.getString("Draw_every"));
        cbTickOnLabels.setMnemonic(Language.getString("Y").charAt(0));
        cbTickOnLabels.setText(Language.getString("At_Labels_Only"));
        cbTickOnLabels.setBounds(new Rectangle(180, 81, 101, 23));
        cbTickOnLabels.addActionListener(new
                                         AxesEditor_cbTickOnLabels_actionAdapter(this));
        jPanel17.setLayout(null);
        cbVisible.setMnemonic(Language.getString("V").charAt(0));
        cbVisible.setText(Language.getString("Visible"));
        cbVisible.setBounds(new Rectangle(6, 7, 72, 23));
        cbVisible.addActionListener(new AxesEditor_cbVisible_actionAdapter(this));
        cbAutomatic.setMnemonic(Language.getString("A").charAt(0));
        cbAutomatic.setText(Language.getString("Automatic"));
        cbAutomatic.setBounds(new Rectangle(76, 7, 91, 23));
        cbAutomatic.addActionListener(new AxesEditor_jCheckBox2_actionAdapter(this));
        cbInverted.setMnemonic(Language.getString("I").charAt(0));
        cbInverted.setText(Language.getString("Inverted"));
        cbInverted.setBounds(new Rectangle(163, 7, 100, 23));
        cbInverted.addActionListener(new AxesEditor_jCheckBox3_actionAdapter(this));
        jPanel17.setPreferredSize(new Dimension(1, 91));
        jPanel13.setLayout(borderLayout5);
        jLabel10.setDisplayedMnemonic(Language.getString("P").charAt(0));
        jLabel10.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel10.setLabelFor(spPosition);
        jLabel10.setText(Language.getString("Position"));
        jLabel10.setBounds(new Rectangle(11, 19, 77, 15));
        spPosition.setBounds(new Rectangle(92, 17, 45, 20));
        spPosition.addChangeListener(new AxesEditor_spPosition_changeAdapter(this));
        jLabel11.setDisplayedMnemonic(Language.getString("U").charAt(0));
        jLabel11.setLabelFor(cbUnits);
        jLabel11.setText(Language.getString("Units"));
        jLabel11.setBounds(new Rectangle(157, 5, 40, 15));
        cbUnits.setBounds(new Rectangle(156, 23, 89, 21));
        cbUnits.addActionListener(new AxesEditor_cbUnits_actionAdapter(this));
        jLabel12.setDisplayedMnemonic(Language.getString("A").charAt(0));
        jLabel12.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel12.setLabelFor(spStart);
        jLabel12.setText(Language.getString("Start"));
        jLabel12.setBounds(new Rectangle(11, 61, 83, 15));
        jLabel13.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel13.setLabelFor(spEnd);
        jLabel13.setText(Language.getString("End"));
        jLabel13.setBounds(new Rectangle(6, 89, 88, 15));
        spStart.setBounds(new Rectangle(96, 59, 53, 20));
        spStart.addChangeListener(new AxesEditor_spStart_changeAdapter(this));
        spEnd.setBounds(new Rectangle(96, 86, 53, 20));
        spEnd.addChangeListener(new AxesEditor_spEnd_changeAdapter(this));
        jLabel14.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel14.setLabelFor(spZPos);
        jLabel14.setText(Language.getString("Z"));
        jLabel14.setBounds(new Rectangle(167, 74, 40, 15));
        cbOtherSide.setMnemonic(Language.getString("O").charAt(0));
        cbOtherSide.setText(Language.getString("Other_Side"));
        cbOtherSide.setBounds(new Rectangle(53, 113, 94, 23));
        cbOtherSide.addActionListener(new AxesEditor_jCheckBox1_actionAdapter(this));
        cbHorizontal.setMnemonic(Language.getString("H").charAt(0));
        cbHorizontal.setText(Language.getString("Horizontal"));
        cbHorizontal.setBounds(new Rectangle(53, 138, 96, 23));
        cbHorizontal.addActionListener(new
                                       AxesEditor_cbHorizontal_actionAdapter(this));
        spZPos.setBounds(new Rectangle(210, 72, 47, 20));
        spZPos.addChangeListener(new AxesEditor_spZPos_changeAdapter(this));
        jLabel15.setDisplayedMnemonic(Language.getString("L").charAt(0));
        jLabel15.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel15.setLabelFor(spMinorLength);
        jLabel15.setText(Language.getString("Length"));
        jLabel15.setBounds(new Rectangle(123, 21, 65, 15));
        spMinorLength.setBounds(new Rectangle(194, 19, 48, 20));
        spMinorLength.addChangeListener(new
                                        AxesEditor_spMinorLength_changeAdapter(this));
        jLabel16.setDisplayedMnemonic(Language.getString("C").charAt(0));
        jLabel16.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel16.setLabelFor(spMinorCount);
        jLabel16.setText(Language.getString("Count"));
        jLabel16.setBounds(new Rectangle(124, 54, 64, 15));
        spMinorCount.setBounds(new Rectangle(195, 52, 46, 20));
        spMinorCount.addChangeListener(new
                                       AxesEditor_spMinorCount_changeAdapter(this));
        jLabel17.setDisplayedMnemonic(Language.getString("A").charAt(0));
        jLabel17.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel17.setLabelFor(spTitleAngle);
        jLabel17.setText(Language.getString("Angle"));
        jLabel17.setBounds(new Rectangle(7, 50, 51, 15));
        spTitleAngle.setBounds(new Rectangle(64, 49, 51, 20));
        spTitleAngle.addChangeListener(new
                                       AxesEditor_spTitleAngle_changeAdapter(this));
        jLabel18.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel18.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel18.setLabelFor(spTitleSize);
        jLabel18.setText(Language.getString("Size"));
        jLabel18.setBounds(new Rectangle(8, 81, 52, 15));
        spTitleSize.setBounds(new Rectangle(65, 79, 49, 20));
        spTitleSize.addChangeListener(new AxesEditor_spTitleSize_changeAdapter(this));
        jLabel19.setDisplayedMnemonic(Language.getString("Z").charAt(0));
        jLabel19.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel19.setLabelFor(spLabelsSize);
        jLabel19.setText(Language.getString("Size"));
        jLabel19.setBounds(new Rectangle(131, 19, 66, 15));
        spLabelsSize.setBounds(new Rectangle(200, 17, 54, 20));
        spLabelsSize.addChangeListener(new
                                       AxesEditor_spLabelsSize_changeAdapter(this));
        jLabel20.setDisplayedMnemonic(Language.getString("A").charAt(0));
        jLabel20.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel20.setLabelFor(spLabelsAngle);
        jLabel20.setText(Language.getString("Angle"));
        jLabel20.setBounds(new Rectangle(156, 49, 40, 15));
        spLabelsAngle.setBounds(new Rectangle(201, 47, 53, 20));
        spLabelsAngle.addChangeListener(new
                                        AxesEditor_spLabelsAngle_changeAdapter(this));
        jLabel21.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel21.setLabelFor(spMinSep);
        jLabel21.setText(Language.getString("Min_Separation"));
        jLabel21.setBounds(new Rectangle(102, 107, 99, 15));
        spMinSep.setBounds(new Rectangle(203, 105, 49, 20));
        spMinSep.addChangeListener(new AxesEditor_spMinSep_changeAdapter(this));
        jLabel22.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel22.setLabelFor(cbStyle);
        jLabel22.setText(Language.getString("Style"));
        jLabel22.setBounds(new Rectangle(13, 134, 104, 15));
        cbStyle.setBounds(new Rectangle(13, 154, 120, 21));
        cbStyle.addActionListener(new AxesEditor_cbStyle_actionAdapter(this));
        jLabel9.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel9.setLabelFor(spGridZ);
        jLabel9.setText(Language.getString("Z"));
        jLabel9.setBounds(new Rectangle(88, 50, 42, 15));
        jLabel23.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel23.setLabelFor(spInnerLen);
        jLabel23.setText(Language.getString("Len"));
        jLabel23.setBounds(new Rectangle(90, 119, 40, 15));
        spTickLen.setBounds(new Rectangle(132, 82, 42, 20));
        spTickLen.addChangeListener(new AxesEditor_spTickLen_changeAdapter(this));
        spInnerLen.setBounds(new Rectangle(132, 116, 42, 20));
        spInnerLen.addChangeListener(new AxesEditor_spInnerLen_changeAdapter(this));
        spGridZ.setBounds(new Rectangle(132, 47, 42, 20));
        spGridZ.addChangeListener(new AxesEditor_spGridZ_changeAdapter(this));
        jLabel24.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel24.setLabelFor(spTickLen);
        jLabel24.setText(Language.getString("Len"));
        jLabel24.setBounds(new Rectangle(88, 85, 42, 15));
        lAxisIncre.setText("(Increment)");
        lAxisIncre.setBounds(new Rectangle(204, 40, 60, 14));
        this.add(jPanel1, java.awt.BorderLayout.WEST);
        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.add(jPanel2, Language.getString("Scales"));
        jTabbedPane1.add(jPanel3, Language.getString("Title"));
        jTabbedPane1.add(jPanel4, Language.getString("Labels"));
        jTabbedPane1.add(jPanel5, Language.getString("Ticks"));
        jPanel5.add(bAxisPen);
        jPanel5.add(bTickPen);
        jPanel5.add(bAxisGrid);
        jPanel5.add(bTickInner);
        jPanel5.add(jLabel23);
        jPanel5.add(spInnerLen);
        jPanel5.add(cbTickOnLabels);
        jPanel5.add(cbGridCentered);
        jPanel5.add(jLabel25);
        jPanel5.add(spGridDrawEvery);
        jPanel5.add(jLabel24);
        jPanel5.add(spTickLen);
        jPanel5.add(jLabel9);
        jPanel5.add(spGridZ);
        jTabbedPane1.add(jPanel6, Language.getString("Minor"));
        jPanel6.add(bTickMinor);
        jPanel6.add(bMinorGrid);
        jPanel6.add(jLabel15);
        jPanel6.add(spMinorLength);
        jPanel6.add(jLabel16);
        jPanel6.add(spMinorCount);
        jTabbedPane1.add(jPanel7, Language.getString("Position"));
        jPanel7.add(spPosition);
        jPanel7.add(jLabel10);
        jPanel7.add(jLabel11);
        jPanel7.add(cbUnits);
        jPanel7.add(spStart);
        jPanel7.add(spEnd);
        jPanel7.add(cbOtherSide);
        jPanel7.add(jLabel12);
        jPanel7.add(jLabel13);
        jPanel7.add(spZPos);
        jPanel7.add(jLabel14);
        jPanel7.add(cbHorizontal);
        jPanel1.add(cbBehind);
        jPanel1.add(jPanel8);
        jPanel8.add(jLabel1);
        jPanel9.add(bAdd);
        jPanel9.add(bDelete);
        jPanel1.add(cbViewAxes, null);
        jPanel8.add(lAxes);
        jPanel8.add(jPanel9);
        jPanel10.add(cbAutoMin);
        jPanel10.add(lAxisMin);
        jPanel10.add(bAxisMin);
        jPanel10.add(jLabel5);
        jPanel10.add(spMinOff);
        jTabbedPane2.add(jPanel10, Language.getString("Minimum"));
        jTabbedPane2.add(jPanel11, Language.getString("Maximum"));
        jPanel11.add(lAxisMax);
        jPanel11.add(bAxisMax);
        jPanel11.add(jLabel7);
        jPanel11.add(cbAutoMax);
        jPanel11.add(spMaxOff);
        jPanel17.add(cbAutomatic);
        jPanel17.add(cbInverted);
        jPanel17.add(bIncrement);
        jPanel17.add(cbLog);
        jPanel17.add(jLabel3);
        jPanel17.add(eLogBase);
        jPanel17.add(cbLogE);
        jPanel17.add(cbVisible);
        jPanel17.add(jLabel2);
        jPanel17.add(lAxisIncre);
        jPanel3.add(jTabbedPane3, java.awt.BorderLayout.CENTER);
        jTabbedPane3.add(jPanel12, Language.getString("Style"));
        jPanel12.add(jLabel8);
        jPanel12.add(jLabel17);
        jPanel12.add(eTitle);
        jPanel12.add(spTitleAngle);
        jPanel12.add(cbTitleVisible);
        jPanel12.add(jLabel18);
        jPanel12.add(spTitleSize);
        jTabbedPane3.add(jPanel13, Language.getString("Text"));
        jPanel4.add(jTabbedPane4, java.awt.BorderLayout.CENTER);
        jTabbedPane4.add(jPanel14, Language.getString("Style"));
        jPanel14.add(spLabelsSize);
        jPanel14.add(jLabel19);
        jPanel14.add(spLabelsAngle);
        jPanel14.add(jLabel20);
        jPanel14.add(cbLabels);
        jPanel14.add(cbMultiline);
        jPanel14.add(cbRoundFirst);
        jPanel14.add(cbLabelOnAxis);
        jPanel14.add(cbAlternate);
        jPanel14.add(jLabel21);
        jPanel14.add(spMinSep);
        jPanel14.add(jLabel22);
        jPanel14.add(cbStyle);
        jTabbedPane4.add(jPanel15, Language.getString("Format"));
        jPanel15.add(cbExpo);
        jPanel15.add(cbLabelAlign);
        jPanel15.add(lAxisFormat);
        jPanel15.add(cbFormat);
        jTabbedPane4.add(jPanel16, Language.getString("Text"));
        jPanel2.add(jPanel17, java.awt.BorderLayout.NORTH);
        jPanel2.add(jTabbedPane2, java.awt.BorderLayout.CENTER);
        jPanel13.add(titleFont, BorderLayout.CENTER);
        jPanel16.add(labelsFont, BorderLayout.CENTER);
    }

    public void cbBehind_actionPerformed(ActionEvent e) {
        axes.setDrawBehind(cbBehind.isSelected());
    }

    public void cbViewAxes_actionPerformed(ActionEvent e) {
        axes.setVisible(cbViewAxes.isSelected());
    }

    public void cbLog_actionPerformed(ActionEvent e) {
        try {
            axis.setLogarithmic(cbLog.isSelected());
            if(cbLog.isSelected()) {
                eLogBase.setEnabled(true);
                cbLogE.setEnabled(true);
            }
            else {
                eLogBase.setEnabled(false);
                cbLogE.setEnabled(false);
            }
        } catch (ChartException ex) {
        }
    }

    public void cbLogE_actionPerformed(ActionEvent e) {
        if(cbLogE.isSelected())
            axis.setLogarithmicBase(Math.E);
        else
            setLogarithmicBase(eLogBase.getText());
    }

    public void cbAutoMin_actionPerformed(ActionEvent e) {
        axis.setAutomaticMinimum(cbAutoMin.isSelected());
    }

    public void spMinOff_stateChanged(ChangeEvent e) {
        Integer i = (Integer) spMinOff.getModel().getValue();
        axis.setMinimumOffset(i.intValue());
    }

    public void cbAutoMax_actionPerformed(ActionEvent e) {
        axis.setAutomaticMaximum(cbAutoMax.isSelected());
    }

    public void spMaxOff_stateChanged(ChangeEvent e) {
        Integer i = (Integer) spMaxOff.getModel().getValue();
        axis.setMaximumOffset(i.intValue());
    }

    public void eTitle_actionPerformed(ActionEvent e) {
        axis.getTitle().setText(eTitle.getText());
    }

    public void cbTitleVisible_actionPerformed(ActionEvent e) {
        axis.getTitle().setVisible(cbTitleVisible.isSelected());
    }

    public void lAxes_valueChanged(ListSelectionEvent e) {
        axis = chart.getAxes().getAxis(lAxes.getSelectedIndex());
        fillAxis();
    }

    private void fillAxis() {
        bAxisGrid.setPen(axis.getGrid());
        bTickMinor.setPen(axis.getMinorTicks());
        bTickInner.setPen(axis.getTicksInner());
        bTickPen.setPen(axis.getTicks());
        bAxisPen.setPen(axis.getAxisPen());
        bMinorGrid.setPen(axis.getMinorGrid());

        setAxisLabels();
        setAxisScales();
        setAxisTitle();
        setAxisTicks();
        setAxisMinorTicks();
        setAxisPositions();

        bDelete.setEnabled(lAxes.getSelectedIndex() >= Axes.STANDARD_AXES);
    }

    private void setAxisPositions() {
        spPosition.setValue(new Integer((int) axis.getRelativePosition()));
        cbUnits.setSelectedIndex(axis.getPositionUnits().getValue());
        spStart.setValue(new Integer((int) axis.getStartPosition()));
        spEnd.setValue(new Integer((int) axis.getEndPosition()));
        spZPos.setValue(new Integer((int) axis.getZPosition()));
        cbOtherSide.setSelected(axis.getOtherSide());
        cbHorizontal.setSelected(axis.getHorizontal());

        cbOtherSide.setEnabled(axis.isCustom());
        cbHorizontal.setEnabled(axis.isCustom());
    }

    private void setAxisMinorTicks() {
        spMinorLength.setValue(new Integer(axis.getMinorTicks().getLength()));
        spMinorCount.setValue(new Integer(axis.getMinorTickCount()));
    }

    private void setAxisTicks() {
        cbTickOnLabels.setSelected(axis.getTickOnLabelsOnly());
        cbGridCentered.setSelected(axis.getGrid().getCentered());
        spGridZ.setValue(new Integer((int)axis.getGrid().getZPosition()));
        spTickLen.setValue(new Integer(axis.getTicks().getLength()));
        spInnerLen.setValue(new Integer(axis.getTicksInner().getLength()));
    }

    private void setAxisTitle() {
        eTitle.setText(axis.getTitle().getCaption());
        spTitleAngle.setValue(new Integer(axis.getTitle().getAngle()));
        spTitleSize.setValue(new Integer(axis.getTitle().getCustomSize()));
        cbTitleVisible.setSelected(axis.getTitle().getVisible());
        titleFont.refreshControls(axis.getTitle().getFont());
    }

    private void setAxisScales() {
        lAxisIncre.setText(AxisIncrement.getIncrementText(axis.getIncrement(),axis.isDateTime(),
                axis.getLabels().getExactDateTime(),axis.getLabels().getValueFormat()));
        if ( axis.isDateTime() ) {
            /* TODO DateTime
           String f=System.Globalization.DateTimeFormatInfo.CurrentInfo.ShortDatePattern+
                    " "+System.Globalization.DateTimeFormatInfo.CurrentInfo.ShortTimePattern;

            if ( axis.getMinimum()>=1 )
                lAxisMin.setText(DateTime.FromOADate(axis.getMinimum()).ToString(f));
            else
                lAxisMin.setText(com.steema.teechart.misc.Utils.timeToStr(axis.getMinimum()));

            if ( axis.getMaximum()>=1 )
                lAxisMax.setText(DateTime.FromOADate(axis.getMaximum()).ToString(f));
            else
                lAxisMax.setText(com.steema.teechart.misc.Utils.timeToStr(axis.getMaximum()));
            */
        } else {
            lAxisMin.setText(new DecimalFormat(axis.getLabels().getValueFormat()).format(axis.getMinimum()));
            lAxisMax.setText(new DecimalFormat(axis.getLabels().getValueFormat()).format(axis.getMaximum()));
        }

        cbAutomatic.setSelected(axis.getAutomatic());
        cbAutoMax.setSelected(axis.getAutomaticMaximum());
        cbAutoMin.setSelected(axis.getAutomaticMinimum());
        spMaxOff.setValue(new Integer(axis.getMaximumOffset()));
        spMinOff.setValue(new Integer(axis.getMinimumOffset()));
        cbLog.setSelected(axis.getLogarithmic());
        if(!axis.getLogarithmic()) {
            eLogBase.setEnabled(false);
            cbLogE.setEnabled(false);
        }
        eLogBase.setText(Double.toString(axis.getLogarithmicBase()));
        cbInverted.setSelected(axis.getInverted());
        cbVisible.setSelected(axis.getVisible());
    }

    private void setAxisLabels() {
        AxisLabels l = axis.getLabels();

        cbLabels.setSelected(l.getVisible());
        cbMultiline.setSelected(l.getMultiLine());
        cbRoundFirst.setSelected(l.getRoundFirstLabel());
        cbLabelOnAxis.setSelected(l.getOnAxis());
        cbAlternate.setSelected(l.getAlternate());
        cbLabelAlign.setSelected(l.getAlign() == AxisLabelAlign.DEFAULT);
        cbLabelAlign.setEnabled((!axis.getHorizontal()) && (!axis.isDepthAxis));
        labelsFont.refreshControls(l.getFont());
        cbStyle.setSelectedIndex(l.getStyle().getValue());

        spLabelsSize.setValue(new Integer(l.getCustomSize()));
        spLabelsAngle.setValue(new Integer(l.getAngle()));
        spMinSep.setValue(new Integer(l.getSeparation()));

        cbFormat.removeAllItems();

        String tmp = "";

        if (axis.isDateTime()) {

            SimpleDateFormat dateFormat = new SimpleDateFormat();
            // @TODO How to check for datetime "time-only" axis ?
            if (axis.getMaximum() - axis.getMinimum() <= 1) {

                cbFormat.addItem(((SimpleDateFormat)dateFormat.getTimeInstance(DateFormat.SHORT)).toPattern());
                cbFormat.addItem(((SimpleDateFormat)dateFormat.getTimeInstance(DateFormat.LONG)).toPattern());
                cbFormat.addItem(Language.getString("hh_nn"));
                cbFormat.addItem(Language.getString("hh_nn_ss"));
                cbFormat.addItem(Language.getString("hh_nn_ss_zzz"));
            } else {

                cbFormat.addItem(((SimpleDateFormat)dateFormat.getDateInstance(DateFormat.SHORT)).toPattern());
                cbFormat.addItem(((SimpleDateFormat)dateFormat.getDateInstance(DateFormat.LONG)).toPattern());
                cbFormat.addItem(Language.getString("yyyy_mm_dd"));
                cbFormat.addItem(Language.getString("mm_dd_yyyy"));
                cbFormat.addItem(Language.getString("dd_mm_yyyy"));
                cbFormat.addItem(Language.getString("ddd"));
                cbFormat.addItem(Language.getString("dddd"));
                cbFormat.addItem(Language.getString("mmm"));
                cbFormat.addItem(Language.getString("mmmm"));
            }

            lAxisFormat.setText(Language.getString("DateTimeFormat"));
            tmp = axis.getLabels().getDateTimeFormat();

            if (tmp.length() == 0) {
                tmp = axis.dateTimeDefaultFormat(axis.getMaximum() -
                                                 axis.getMinimum()).toString();
            }

        } else {
            addDefaultValueFormats(cbFormat);

            lAxisFormat.setText(Language.getString("ValuesFormat"));
            tmp = axis.getLabels().getValueFormat();

            if (tmp.length() == 0) {
                tmp = Language.getString("DefValueFormat");
            }

            // @TODO: Necessary checking?
            //tmp = ToLocalFormat(tmp);
        }

        if (((DefaultComboBoxModel) (cbFormat.getModel())).getIndexOf(tmp) ==
            -1) {
            cbFormat.addItem(tmp);
        }

        cbFormat.setSelectedItem(tmp);
    }

    // @TODO Verify all formats
    static public void addDefaultValueFormats(JComboBox combo) {
        combo.addItem(Language.getString("DefValueFormat"));
        combo.addItem("0");
        combo.addItem("0.0");
        combo.addItem("0.#");
        combo.addItem("#.#");
        combo.addItem("#,##0.00;(#,##0.00)");
        combo.addItem(Language.getString("00e_0"));
        combo.addItem(Language.getString("_0_x10_E_0"));
        combo.addItem(Language.getString("_x10E"));
    }

    public void cbLabels_actionPerformed(ActionEvent e) {
        axis.getLabels().setVisible(cbLabels.isSelected());
    }

    public void cbMultiline_actionPerformed(ActionEvent e) {
        axis.getLabels().setMultiLine(cbMultiline.isSelected());
    }

    public void cbRoundFirst_actionPerformed(ActionEvent e) {
        axis.getLabels().setRoundFirstLabel(cbRoundFirst.isSelected());
    }

    public void cbLabelOnAxis_actionPerformed(ActionEvent e) {
        axis.getLabels().setOnAxis(cbLabelOnAxis.isSelected());
    }

    public void cbAlternate_actionPerformed(ActionEvent e) {
        axis.getLabels().setAlternate(cbAlternate.isSelected());
        axis.invalidate();
    }

    public void cbGridCentered_actionPerformed(ActionEvent e) {
        axis.getGrid().setCentered(cbGridCentered.isSelected());
    }

    public void cbTickOnLabels_actionPerformed(ActionEvent e) {
        axis.setTickOnLabelsOnly(cbTickOnLabels.isSelected());
    }

    public void cbVisible_actionPerformed(ActionEvent e) {
        axis.setVisible(cbVisible.isSelected());
    }

    public void jCheckBox2_actionPerformed(ActionEvent e) {
        axis.setAutomatic(cbAutomatic.isSelected());
    }

    public void jCheckBox3_actionPerformed(ActionEvent e) {
        axis.setInverted(cbInverted.isSelected());
    }

    public void spPosition_stateChanged(ChangeEvent e) {
        axis.setRelativePosition(((Integer) spPosition.getValue()).intValue());
    }

    public void cbUnits_actionPerformed(ActionEvent e) {
        if (axis != null) {
            boolean tmp = (cbUnits.getSelectedIndex() == 0);
            axis.setPositionUnits(tmp ? PositionUnits.PERCENT :
                                  PositionUnits.PIXELS);
        }
    }

    public void cbOtherSide_actionPerformed(ActionEvent e) {
        axis.setOtherSide(cbOtherSide.isSelected());
    }

    public void cbHorizontal_actionPerformed(ActionEvent e) {
        axis.setHorizontal(cbHorizontal.isSelected());
    }

    public void spStart_stateChanged(ChangeEvent e) {
        axis.setStartPosition(((Integer) spStart.getValue()).intValue());
    }

    public void spEnd_stateChanged(ChangeEvent e) {
        axis.setEndPosition(((Integer) spEnd.getValue()).intValue());
    }

    public void spZPos_stateChanged(ChangeEvent e) {
        axis.setZPosition(((Integer) spZPos.getValue()).intValue());
    }

    public void spMinorLength_stateChanged(ChangeEvent e) {
        axis.getMinorTicks().setLength(((Integer) spMinorLength.getValue()).
                                       intValue());
    }

    public void spMinorCount_stateChanged(ChangeEvent e) {
        axis.setMinorTickCount(((Integer) spMinorCount.getValue()).intValue());
    }

    public void spTitleAngle_stateChanged(ChangeEvent e) {
        axis.getTitle().setAngle(((Integer) spTitleAngle.getValue()).intValue());
    }

    public void spTitleSize_stateChanged(ChangeEvent e) {
        axis.getTitle().setCustomSize(((Integer) spTitleSize.getValue()).
                                      intValue());
    }

    public void cbStyle_actionPerformed(ActionEvent e) {
        if (axis != null) {
            axis.getLabels().setStyle(AxisLabelStyle.fromValue(cbStyle.
                    getSelectedIndex()));
        }
    }

    public void spLabelsSize_stateChanged(ChangeEvent e) {
        axis.getLabels().setCustomSize(((Integer) spLabelsSize.getValue()).
                                       intValue());
    }

    public void spLabelsAngle_stateChanged(ChangeEvent e) {
        axis.getLabels().setAngle(((Integer) spLabelsAngle.getValue()).intValue());
    }

    public void spMinSep_stateChanged(ChangeEvent e) {
        axis.getLabels().setSeparation(((Integer) spMinSep.getValue()).intValue());
    }

    public void cbExpo_actionPerformed(ActionEvent e) {
        axis.getLabels().setExponent(cbExpo.isSelected());
    }

    public void cbFormat_actionPerformed(ActionEvent e) {
        String s = (String) cbFormat.getSelectedItem();
        if (s != null) {
            if (axis.isDateTime()) {
                axis.getLabels().setDateTimeFormat(s);
            } else {
                axis.getLabels().setValueFormat(s);
            }
        }
    }

    public void spTickLen_stateChanged(ChangeEvent e) {
        axis.getTicks().setLength(((Integer) spTickLen.getValue()).intValue());
    }

    public void spInnerLen_stateChanged(ChangeEvent e) {
        axis.getTicksInner().setLength(((Integer) spInnerLen.getValue()).
                                       intValue());
    }

    public void spGridZ_stateChanged(ChangeEvent changeEvent) {
        axis.getGrid().setZPosition(((Integer)spGridZ.getValue()).intValue());
    }


    private void setLogarithmicBase(String s) {
       double tmp = 0.0;
       try {
           tmp = Double.parseDouble(s);
       } catch (NumberFormatException ex) {
           tmp=axis.getLogarithmicBase();
       }

       if ((tmp!=1) && (tmp!=0)) {
          axis.setLogarithmicBase(tmp);
          cbLogE.setSelected(axis.getLogarithmicBase()==Math.E);
          cbLogE.setEnabled(! cbLogE.isSelected());
       }
    }


    public void eLogBase_keyTyped(KeyEvent keyEvent) {
       setLogarithmicBase(eLogBase.getText());
    }

    public void eTitle_keyReleased(KeyEvent e) {
        axis.getTitle().setText(eTitle.getText());
    }

    public void bIncrement_actionPerformed(ActionEvent e) {
        AxisIncrement a=new AxisIncrement();

// TODO        a.setTitle(String.Format(Texts.DesiredIncrement,axis.titleOrName()));
        a.isDateTime=axis.isDateTime();
        a.isExact=axis.getLabels().getExactDateTime();
        a.dIncrement=axis.getIncrement();
        a.iStep=Axis.findDateTimeStep(a.dIncrement);

        a.setSize(400, 170);
        a.setModal(true);
        a.setLocationRelativeTo(this);

        if (a.showModal()==0)  {
                axis.setIncrement(a.dIncrement);
                axis.getLabels().setExactDateTime(a.isExact);
                setAxisScales();
                axis.invalidate();
        }

        a.dispose();
    }

    public void bAxisMin_actionPerformed(ActionEvent e) {
        editAxisMaxMin(Language.getString("Minimum"),cbAutoMin,false);
    }

    private void editAxisMaxMin(String aTitle, JCheckBox checkBox, boolean isMax) {

        AxisMaxMin a=new AxisMaxMin();

        a.setTitle(aTitle+" "+axis.titleOrName());
        a.isDateTime=axis.isDateTime();
        if (isMax)
            a.maxMin=axis.getMaximum();
        else
            a.maxMin=axis.getMinimum();

        a.setSize(300, 115);
        a.setModal(true);
        a.setLocationRelativeTo(this);

        if (a.showModal()==0 ) {
            if ( isMax )
                axis.setMaximum(a.maxMin);
            else
                axis.setMinimum(a.maxMin);

            checkBox.setSelected(false);
            setAxisScales();
        }
    }

    public void bAxisMax_actionPerformed(ActionEvent e) {
        editAxisMaxMin(Language.getString("Maximum"),cbAutoMax,true);
    }
    
    public void bAdd_actionPerformed(ActionEvent e) {
        chart.getAxes().getCustom().add(new Axis());
        DefaultListModel l = (DefaultListModel) lAxes.getModel();
        l.addElement(Language.getString("CustomAxesEditor") + " " +
                          Integer.toString(chart.getAxes().getCount()-1- Axes.STANDARD_AXES)); 
       
    }
    
    public void bDel_actionPerformed(ActionEvent e) {
        int idx=lAxes.getSelectedIndex();
        if (idx>=Axes.STANDARD_AXES)
        {
          Axis a=chart.getAxes().getCustom().getAxis(idx-Axes.STANDARD_AXES);
          chart.getAxes().getCustom().remove(a);
          lAxes.invalidate();
          chart.invalidate();
        }
    }
}
class AxesEditor_bAxisMax_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_bAxisMax_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bAxisMax_actionPerformed(e);
    }
}

class AxesEditor_bAxisMin_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_bAxisMin_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bAxisMin_actionPerformed(e);
    }
}

class AxesEditor_bAdd_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_bAdd_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bAdd_actionPerformed(e);
    }
}

class AxesEditor_bDel_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_bDel_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bDel_actionPerformed(e);
    }
}

class AxesEditor_bIncrement_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_bIncrement_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bIncrement_actionPerformed(e);
    }
}


final class AxesEditor_eLogBase_keyAdapter extends KeyAdapter {
    private AxesEditor adaptee;
    AxesEditor_eLogBase_keyAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void keyTyped(KeyEvent keyEvent) {
        adaptee.eLogBase_keyTyped(keyEvent);
    }
}


class AxesEditor_spGridZ_changeAdapter implements ChangeListener {
    private AxesEditor adaptee;
    AxesEditor_spGridZ_changeAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent changeEvent) {
        adaptee.spGridZ_stateChanged(changeEvent);
    }
}

class AxesEditor_spGridDrawEvery_changeAdapter implements ChangeListener {
    private AxesEditor adaptee;
    AxesEditor_spGridDrawEvery_changeAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent changeEvent) {
        adaptee.spGridDrawEvery_stateChanged(changeEvent);
    }
}


class AxesEditor_spInnerLen_changeAdapter implements ChangeListener {
    private AxesEditor adaptee;
    AxesEditor_spInnerLen_changeAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spInnerLen_stateChanged(e);
    }
}


class AxesEditor_spTickLen_changeAdapter implements ChangeListener {
    private AxesEditor adaptee;
    AxesEditor_spTickLen_changeAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spTickLen_stateChanged(e);
    }
}


class AxesEditor_cbFormat_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_cbFormat_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbFormat_actionPerformed(e);
    }
}


class AxesEditor_cbExpo_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_cbExpo_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbExpo_actionPerformed(e);
    }
}


class AxesEditor_spMinSep_changeAdapter implements ChangeListener {
    private AxesEditor adaptee;
    AxesEditor_spMinSep_changeAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spMinSep_stateChanged(e);
    }
}


class AxesEditor_spLabelsAngle_changeAdapter implements ChangeListener {
    private AxesEditor adaptee;
    AxesEditor_spLabelsAngle_changeAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spLabelsAngle_stateChanged(e);
    }
}


class AxesEditor_spLabelsSize_changeAdapter implements ChangeListener {
    private AxesEditor adaptee;
    AxesEditor_spLabelsSize_changeAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spLabelsSize_stateChanged(e);
    }
}


final class AxesEditor_cbStyle_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_cbStyle_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbStyle_actionPerformed(e);
    }
}


final class AxesEditor_spTitleSize_changeAdapter implements ChangeListener {
    private AxesEditor adaptee;
    AxesEditor_spTitleSize_changeAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spTitleSize_stateChanged(e);
    }
}


final class AxesEditor_spTitleAngle_changeAdapter implements ChangeListener {
    private AxesEditor adaptee;
    AxesEditor_spTitleAngle_changeAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spTitleAngle_stateChanged(e);
    }
}


final class AxesEditor_spMinorCount_changeAdapter implements ChangeListener {
    private AxesEditor adaptee;
    AxesEditor_spMinorCount_changeAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spMinorCount_stateChanged(e);
    }
}


final class AxesEditor_spMinorLength_changeAdapter implements ChangeListener {
    private AxesEditor adaptee;
    AxesEditor_spMinorLength_changeAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spMinorLength_stateChanged(e);
    }
}


final class AxesEditor_spZPos_changeAdapter implements ChangeListener {
    private AxesEditor adaptee;
    AxesEditor_spZPos_changeAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spZPos_stateChanged(e);
    }
}


final class AxesEditor_spEnd_changeAdapter implements ChangeListener {
    private AxesEditor adaptee;
    AxesEditor_spEnd_changeAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spEnd_stateChanged(e);
    }
}


final class AxesEditor_spStart_changeAdapter implements ChangeListener {
    private AxesEditor adaptee;
    AxesEditor_spStart_changeAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spStart_stateChanged(e);
    }
}


final class AxesEditor_cbHorizontal_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_cbHorizontal_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbHorizontal_actionPerformed(e);
    }
}


final class AxesEditor_jCheckBox1_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_jCheckBox1_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbOtherSide_actionPerformed(e);
    }
}


final class AxesEditor_cbUnits_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_cbUnits_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbUnits_actionPerformed(e);
    }
}


final class AxesEditor_spPosition_changeAdapter implements ChangeListener {
    private AxesEditor adaptee;
    AxesEditor_spPosition_changeAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spPosition_stateChanged(e);
    }
}


final class AxesEditor_jCheckBox3_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_jCheckBox3_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jCheckBox3_actionPerformed(e);
    }
}


final class AxesEditor_jCheckBox2_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_jCheckBox2_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jCheckBox2_actionPerformed(e);
    }
}


final class AxesEditor_cbVisible_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_cbVisible_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {

        adaptee.cbVisible_actionPerformed(e);
    }
}


final class AxesEditor_cbTickOnLabels_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_cbTickOnLabels_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbTickOnLabels_actionPerformed(e);
    }
}


final class AxesEditor_cbGridCentered_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_cbGridCentered_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbGridCentered_actionPerformed(e);
    }
}


final class AxesEditor_cbAlternate_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_cbAlternate_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAlternate_actionPerformed(e);
    }
}


final class AxesEditor_cbLabelOnAxis_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_cbLabelOnAxis_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbLabelOnAxis_actionPerformed(e);
    }
}


final class AxesEditor_cbRoundFirst_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_cbRoundFirst_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbRoundFirst_actionPerformed(e);
    }
}


final class AxesEditor_cbMultiline_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_cbMultiline_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbMultiline_actionPerformed(e);
    }
}


final class AxesEditor_cbLabels_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_cbLabels_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbLabels_actionPerformed(e);
    }
}


final class AxesEditor_lAxes_listSelectionAdapter implements
        ListSelectionListener {
    private AxesEditor adaptee;
    AxesEditor_lAxes_listSelectionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void valueChanged(ListSelectionEvent e) {
        adaptee.lAxes_valueChanged(e);
    }
}


final class AxesEditor_cbTitleVisible_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_cbTitleVisible_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbTitleVisible_actionPerformed(e);
    }
}


final class AxesEditor_eTitle_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_eTitle_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.eTitle_actionPerformed(e);
    }
}


class AxesEditor_eTitle_keyAdapter extends KeyAdapter {
    private AxesEditor adaptee;
    AxesEditor_eTitle_keyAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void keyReleased(KeyEvent e) {
        adaptee.eTitle_keyReleased(e);
    }
}


final class AxesEditor_spMaxOff_changeAdapter implements ChangeListener {
    private AxesEditor adaptee;
    AxesEditor_spMaxOff_changeAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spMaxOff_stateChanged(e);
    }
}


final class AxesEditor_cbAutoMax_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_cbAutoMax_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAutoMax_actionPerformed(e);
    }
}


final class AxesEditor_spMinOff_changeAdapter implements ChangeListener {
    private AxesEditor adaptee;
    AxesEditor_spMinOff_changeAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spMinOff_stateChanged(e);
    }
}


final class AxesEditor_cbAutoMin_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_cbAutoMin_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAutoMin_actionPerformed(e);
    }
}


final class AxesEditor_cbLogE_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_cbLogE_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbLogE_actionPerformed(e);
    }
}


final class AxesEditor_cbLog_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_cbLog_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbLog_actionPerformed(e);
    }
}


final class AxesEditor_cbBehind_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_cbBehind_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbBehind_actionPerformed(e);
    }
}


final class AxesEditor_cbViewAxes_actionAdapter implements ActionListener {
    private AxesEditor adaptee;
    AxesEditor_cbViewAxes_actionAdapter(AxesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbViewAxes_actionPerformed(e);
    }
}

