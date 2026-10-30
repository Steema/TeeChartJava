package com.steema.teechart.editors;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Insets;
import java.beans.Beans;

import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.HorizontalAxis;
import com.steema.teechart.styles.VerticalAxis;
import com.steema.teechart.Cursor;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.MarksStyle;
import com.steema.teechart.styles.SeriesMarks;
import com.steema.teechart.axis.Axes;
import com.steema.teechart.editors.series.SeriesPointerEditor;
import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.MarksTail;
import com.steema.teechart.styles.TailAlignment;

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
public class SeriesGeneral extends JPanel {


    private Series series;
    private SeriesMarks marks;

    private static final int DATA_MANUAL = 0;
    private static final int DATA_RANDOM = 1;
    private static final int DATA_FUNCTION = 2;
    private static final int FIXEDAXISOPTION = 3; //ie. Left, Right, Left & Right

    ShapeEditor marksEditor = new ShapeEditor();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    BorderLayout borderLayout3 = new BorderLayout();
    Border border1 = BorderFactory.createLineBorder(SystemColor.controlText, 2);
    JCheckBox cbShowInLegend = new JCheckBox();
    BorderLayout borderLayout4 = new BorderLayout();
    BorderLayout borderLayout7 = new BorderLayout();
    JTabbedPane jTabbedPane2 = new JTabbedPane();
    JPanel jPanel6 = new JPanel();
    JPanel jPanel7 = new JPanel();
    JPanel jPanelSymbol = new JPanel();
    JPanel jPanelMarkTail = new JPanel();
    
    JLabel lbMarksTailStyle = new JLabel();
    JComboBox cbMarksTailStyle = new JComboBox();
    JLabel lbMarksTailAlign = new JLabel();
    JComboBox cbMarksTailAlign = new JComboBox();
    JCheckBox chMarksTailCustom = new JCheckBox();
    
    
    JCheckBox cbMarksVisible = new JCheckBox();
    ButtonPen bArrowPen = new ButtonPen();
    JComboBox cbDatasourceStyle = new JComboBox();
    JLabel jLabel1 = new JLabel();
    JComboBox cbHorizAxis = new JComboBox();
    JLabel jLabel2 = new JLabel();
    JComboBox cbVertAxis = new JComboBox();
    ButtonColor buttonColor1 = new ButtonColor();
    JCheckBox cbColorEach = new JCheckBox();
    JPanel panelRandom = new JPanel();
    JLabel jLabel3 = new JLabel();
    JSpinner spNumSample = new JSpinner();
    JCheckBox cbDefaultSample = new JCheckBox();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JLabel labelIcon = new JLabel();
    JCheckBox cbHorizDate = new JCheckBox();
    JCheckBox cbVertDate = new JCheckBox();
    JPanel jPanel10 = new JPanel();
    Border border2 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border3 = new TitledBorder(border2, Language.getString("Formats"));
    JLabel jLabel4 = new JLabel();
    JLabel jLabel5 = new JLabel();
    JComboBox cbFormat = new JComboBox();
    JComboBox cbPercentFormat = new JComboBox();
    JLabel jLabel6 = new JLabel();
    JSpinner spDepth = new JSpinner();
    JCheckBox cbAutoDepth = new JCheckBox();
    JCheckBox cbMarksClip = new JCheckBox();
    JCheckBox cbMarksMultiline = new JCheckBox();
    JCheckBox cbAllMarks = new JCheckBox();
    JLabel jLabel7 = new JLabel();
    JComboBox cbMarksStyle = new JComboBox();
    JLabel jLabel8 = new JLabel();
    JSpinner spEvery = new JSpinner();
    JLabel jLabel9 = new JLabel();
    JSlider slAngle = new JSlider();
    ShapeEditor marksSymbol = new ShapeEditor();
    BorderLayout borderLayout5 = new BorderLayout();
    JButton jButton3 = new JButton();
    JLabel lArrowHead = new JLabel();
    JComboBox cbArrowHead = new JComboBox();
    JLabel jLabel10 = new JLabel();
    JSpinner spArrowSize = new JSpinner();
    JLabel jLabel11 = new JLabel();
    JSpinner spArrowLength = new JSpinner();
    JLabel jLabel12 = new JLabel();
    JSpinner spDistance = new JSpinner();
    JComboBox cbCursor = new JComboBox();
    JLabel jLabel13 = new JLabel();
    JPanel jPanel8 = new JPanel();
    JComboBox cbSeries = new JComboBox();
    private boolean setting;


    public SeriesGeneral(Series series, ChartListBox chartListBox) {
        this();
        setting = true;
        if(series.getChart().getSeriesCount()>0) {
            DefaultComboBoxModel model = new DefaultComboBoxModel();
            for(int i = 0; i < series.getChart().getSeriesCount(); ++i) {
               model.addElement(series.getChart().getSeries(i));
            }
            cbSeries.setModel(model);
            cbSeries.setSelectedItem(series);
        }

        AddSeriesEditor(series);
        setting = false;
    }
    
    private void FillAxes(JComboBox aItems, boolean horizontal) 
    {
      while (aItems.getItemCount()>3) aItems.removeItemAt(aItems.getItemCount()-1);
		
      com.steema.teechart.Chart c=(com.steema.teechart.Chart)(series.getChart());

      if (c!=null) 
        for (int t=Axes.STANDARD_AXES; t<c.getAxes().getCount(); t++) 
        {
            if ( (c.getAxes().getAxis(t).getHorizontal() && horizontal) ||
                    ((!c.getAxes().getAxis(t).getHorizontal()) && (!horizontal)) )
            {
              com.steema.teechart.editors.AxisObj aObj=
                 new com.steema.teechart.editors.AxisObj(Language.getString("CustomAxesEditor")+" "+ Integer.toString(t-Axes.STANDARD_AXES),
                                     t-Axes.STANDARD_AXES);
              aItems.addItem(aObj);
            }
        }
    }

    private void AddSeriesEditor(Series s) {

        series = s;
        marks = series.getMarks();
        
        cbMarksTailStyle.setSelectedIndex(marks.getMarkerTail().getIndex());
        cbMarksTailAlign.setSelectedIndex(marks.getTailParams().getAlign().getIndex());
        chMarksTailCustom.setSelected(marks.getTailParams().isCustomPoint());
        

        cbShowInLegend.setSelected(series.getShowInLegend());
        cbHorizDate.setSelected(series.getXValues().getDateTime());
        cbVertDate.setSelected(series.getYValues().getDateTime());

        cbMarksVisible.setSelected(marks.getVisible());
        cbAllMarks.setSelected(allMarksVisible());

        bArrowPen.setPen(marks.getArrow());
        cbArrowHead.setSelectedIndex(marks.getCallout().getArrowHead().getValue());
        spArrowSize.setValue(new Integer(marks.getCallout().getArrowHeadSize()));
        spArrowLength.setValue(new Integer(marks.getArrowLength()));
        spDistance.setValue(new Integer(marks.getCallout().getDistance()));

        cbMarksClip.setSelected(marks.getClip());
        cbMarksMultiline.setSelected(marks.getMultiLine());
        cbMarksStyle.setSelectedIndex(marks.getStyle().getValue());
        spEvery.setValue(new Integer(marks.getDrawEvery()));
        slAngle.setValue((int) marks.getAngle());

        cbColorEach.setSelected(series.getColorEach());
        buttonColor1.setColor(series.getColor());

        spNumSample.setValue(new Integer(series.getNumSampleValues()));
        cbDefaultSample.setSelected(true);
        cbDefaultSample.setEnabled(false);

        cbDatasourceStyle.setSelectedIndex(Beans.isDesignTime() ? 1 : 0);

        cbFormat.setSelectedItem(series.getValueFormat());
        cbPercentFormat.setSelectedItem(series.getPercentFormat());

        cbCursor.setSelectedIndex(series.getCursor().getType());

        FillAxes(cbHorizAxis,true);
        FillAxes(cbVertAxis,false);
        
        if (series.getHorizontalAxis()!=HorizontalAxis.CUSTOM)
          cbHorizAxis.setSelectedIndex(series.getHorizontalAxis().getValue());
        else
        {
            for (int i=0; i < cbHorizAxis.getItemCount();i++)
            {
                if (cbHorizAxis.getItemAt(i).getClass()==AxisObj.class)
                {
                    AxisObj aObj=(AxisObj)cbHorizAxis.getItemAt(i);
                    if (aObj.index==series.getChart().getAxes().indexOf(series.getCustomHorizAxis())- Axes.STANDARD_AXES)
                      cbHorizAxis.setSelectedIndex(i);    
                }
            }
        }
        
        if (series.getVerticalAxis()!=VerticalAxis.CUSTOM)
          cbVertAxis.setSelectedIndex(series.getVerticalAxis().getValue());
        else
        {
            for (int i=0; i < cbVertAxis.getItemCount();i++)
            {
                if (cbVertAxis.getItemAt(i).getClass()==AxisObj.class)
                {
                    AxisObj aObj=(AxisObj)cbVertAxis.getItemAt(i);
                    if (aObj.index==series.getChart().getAxes().indexOf(series.getCustomVertAxis())- Axes.STANDARD_AXES)
                      cbVertAxis.setSelectedIndex(i);    
                }
            }
        }
        
        

        ImageIcon i = new ImageIcon(series.getBitmapEditor());
        if (i != null) {
            labelIcon.setIcon(i);
        }

        marksEditor.refreshControls(marks);
        //marksSymbol.refreshControls(marks.getSymbol());

        int n = com.steema.teechart.misc.Utils.seriesTypesIndex(series.getClass());
        Class editorClass = Utils.seriesEditorsOf[n];

        jPanel2.removeAll();
        jTabbedPane1.removeAll();
        AddFormatTabs();
        if (editorClass == null) {
            jTabbedPane1.remove(jPanel2);
        } else {
            BaseSeriesEditor editor = null;
            try {
                editor = (BaseSeriesEditor) editorClass.newInstance();
            } catch (IllegalAccessException ex) {
            } catch (InstantiationException ex) {
            }

            if (editor == null) {
                jTabbedPane1.remove(jPanel2);
            } else {
                editor.setSeries(series, jTabbedPane1);
                jPanel2.add(editor, BorderLayout.CENTER);
            }
        }

    }

    private void AddFormatTabs() {
        jTabbedPane1.add(jPanel2, Language.getString("Format"));
        jTabbedPane1.add(jPanel4, Language.getString("Marks"));
        jTabbedPane1.add(jPanel3, Language.getString("General"));
        jTabbedPane1.add(jPanel5, Language.getString("Data_Source"));
    }

    public SeriesGeneral() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
      
        lbMarksTailStyle.setLabelFor(cbMarksTailStyle);
        lbMarksTailStyle.setText(Language.getString("Tail Style"));
        lbMarksTailAlign.setLabelFor(cbMarksTailAlign);
        lbMarksTailAlign.setText(Language.getString("Tail Align"));
        chMarksTailCustom.setText(Language.getString("Custom"));

        cbDatasourceStyle.addItem(Language.getString("Manual"));
        cbDatasourceStyle.addItem(Language.getString("Random"));
        cbDatasourceStyle.addItem(Language.getString("Function"));
        
        cbMarksTailStyle.addItem(Language.getString("None"));
        cbMarksTailStyle.addItem(Language.getString("WithPointer"));
        
        cbMarksTailAlign.addItem(Language.getString("None"));
        cbMarksTailAlign.addItem(Language.getString("Auto"));
        cbMarksTailAlign.addItem(Language.getString("BottomLeft"));
        cbMarksTailAlign.addItem(Language.getString("BottomRight"));
        cbMarksTailAlign.addItem(Language.getString("BottomCenter"));
        cbMarksTailAlign.addItem(Language.getString("TopLeft"));
        cbMarksTailAlign.addItem(Language.getString("TopRight"));
        cbMarksTailAlign.addItem(Language.getString("TopCenter"));
        cbMarksTailAlign.addItem(Language.getString("LeftTop"));
        cbMarksTailAlign.addItem(Language.getString("LeftCenter"));
        cbMarksTailAlign.addItem(Language.getString("LeftBottom"));
        cbMarksTailAlign.addItem(Language.getString("RightTop"));
        cbMarksTailAlign.addItem(Language.getString("RightCenter"));
        cbMarksTailAlign.addItem(Language.getString("RightBottom"));        

        cbHorizAxis.addItem(Language.getString("Top"));
        cbHorizAxis.addItem(Language.getString("Bottom"));
        cbHorizAxis.addItem(Language.getString("Top_and_Bottom"));

        cbVertAxis.addItem(Language.getString("Left"));
        cbVertAxis.addItem(Language.getString("Right"));
        cbVertAxis.addItem(Language.getString("Left_and_Right"));
        
        cbMarksStyle.addItem(Language.getString("Value"));
        cbMarksStyle.addItem(Language.getString("Percent4"));
        cbMarksStyle.addItem(Language.getString("Label"));
        cbMarksStyle.addItem(Language.getString("Label_and_Percent"));
        cbMarksStyle.addItem(Language.getString("Label_and_Value"));
        cbMarksStyle.addItem(Language.getString("Legend"));
        cbMarksStyle.addItem(Language.getString("Percent_Total"));
        cbMarksStyle.addItem(Language.getString("Label_and_Percent"));
        cbMarksStyle.addItem(Language.getString("Xvalue"));
        cbMarksStyle.addItem(Language.getString("Xand_Y_values"));

        cbArrowHead.addItem(Language.getString("None"));
        cbArrowHead.addItem(Language.getString("Line"));
        cbArrowHead.addItem(Language.getString("Solid"));

        Utils.addCursorsToCombo(cbCursor);
    }

    private void jbInit() throws Exception {
        border1 = BorderFactory.createLineBorder(Color.white, 1);
        this.setLayout(borderLayout1);
        jPanel1.setPreferredSize(new Dimension(10, 30));
        jPanel1.setLayout(borderLayout2);
        jPanel2.setLayout(borderLayout3);
        jPanel3.setLayout(null);
        cbShowInLegend.setMnemonic(Language.getString("S").charAt(0));
        cbShowInLegend.setText(Language.getString("Show_in_Legend"));
        cbShowInLegend.setBounds(new Rectangle(6, 5, 131, 23));
        cbShowInLegend.addActionListener(new
                                         SeriesGeneral_cbShowInLegend_actionAdapter(this));
        jPanel4.setLayout(borderLayout4);
        cbMarksVisible.setMnemonic(Language.getString("V").charAt(0));
        cbMarksVisible.setText(Language.getString("Visible"));
        cbMarksVisible.setBounds(new Rectangle(11, 8, 81, 23));
        cbMarksVisible.addActionListener(new
                                         SeriesGeneral_cbMarksVisible_actionAdapter(this));
        jPanel6.setLayout(null);
        jPanel7.setLayout(null);
        bArrowPen.setBounds(new Rectangle(8, 16, 91, 23));
        bArrowPen.setMnemonic(Language.getString("B").charAt(0));
        bArrowPen.setText(Language.getString("Border"));
        jPanel5.setLayout(null);
        cbDatasourceStyle.setBounds(new Rectangle(11, 5, 168, 22));
        cbDatasourceStyle.addActionListener(new
                                            SeriesGeneral_cbDatasourceStyle_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("H").charAt(0));
        jLabel1.setLabelFor(cbHorizAxis);
        jLabel1.setText(Language.getString("Horizontal_Axis"));
        jLabel1.setBounds(new Rectangle(231, 9, 137, 14));
        cbHorizAxis.setBounds(new Rectangle(231, 26, 103, 22));
        cbHorizAxis.addActionListener(new
                                      SeriesGeneral_cbHorizAxis_actionAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("V").charAt(0));
        jLabel2.setText(Language.getString("Vertical_Axis"));
        jLabel2.setBounds(new Rectangle(231, 89, 149, 14));
        cbVertAxis.setBounds(new Rectangle(231, 107, 103, 22));
        cbVertAxis.addActionListener(new SeriesGeneral_cbVertAxis_actionAdapter(this));
        buttonColor1.setPreferredSize(new Dimension(22, 9));
        buttonColor1.setMargin(new Insets(2, 1, 2, 1));
        buttonColor1.setText("");
        buttonColor1.addActionListener(new
                                       SeriesGeneral_buttonColor1_actionAdapter(this));
        cbColorEach.setMnemonic(Language.getString("C").charAt(0));
        cbColorEach.setText(Language.getString("Color_each_point"));
        cbColorEach.setBounds(new Rectangle(6, 28, 162, 23));
        cbColorEach.addActionListener(new
                                      SeriesGeneral_cbColorEach_actionAdapter(this));
        panelRandom.setBounds(new Rectangle(12, 36, 238, 138));
        panelRandom.setLayout(null);
        jLabel3.setDisplayedMnemonic(Language.getString("N").charAt(0));
        jLabel3.setLabelFor(spNumSample);
        jLabel3.setText(Language.getString("Number_of_Sample"));
        jLabel3.setBounds(new Rectangle(12, 8, 160, 14));
        spNumSample.setBounds(new Rectangle(11, 28, 111, 20));
        spNumSample.addChangeListener(new
                                      SeriesGeneral_spNumSample_changeAdapter(this));
        cbDefaultSample.setMnemonic(Language.getString("D").charAt(0));
        cbDefaultSample.setText(Language.getString("Default"));
        cbDefaultSample.setBounds(new Rectangle(138, 27, 81, 23));
        cbDefaultSample.addActionListener(new
                                          SeriesGeneral_jCheckBox1_actionAdapter(this));
        jButton1.setBounds(new Rectangle(11, 60, 75, 23));
        jButton1.setMnemonic(Language.getString("A").charAt(0));
        jButton1.setText(Language.getString("Apply"));
        jButton1.addActionListener(new SeriesGeneral_jButton1_actionAdapter(this));
        jButton2.setBounds(new Rectangle(110, 60, 75, 23));
        jButton2.setMnemonic(Language.getString("C").charAt(0));
        jButton2.setText(Language.getString("Clear"));
        jButton2.addActionListener(new SeriesGeneral_jButton2_actionAdapter(this));
        labelIcon.setPreferredSize(new Dimension(32, 0));
        cbHorizDate.setMnemonic(Language.getString("E").charAt(0));
        cbHorizDate.setText(Language.getString("DateTime"));
        cbHorizDate.setBounds(new Rectangle(231, 54, 81, 23));
        cbHorizDate.addActionListener(new
                                      SeriesGeneral_cbHorizDate_actionAdapter(this));
        cbVertDate.setMnemonic(Language.getString("A").charAt(0));
        cbVertDate.setText(Language.getString("DateTime"));
        cbVertDate.setBounds(new Rectangle(231, 135, 81, 23));
        cbVertDate.addActionListener(new SeriesGeneral_cbVertDate_actionAdapter(this));
        jPanel10.setBorder(border3);
        jPanel10.setBounds(new Rectangle(6, 118, 207, 71));
        jPanel10.setLayout(null);
        jLabel4.setDisplayedMnemonic(Language.getString("V").charAt(0));
        jLabel4.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel4.setLabelFor(cbFormat);
        jLabel4.setText(Language.getString("Values"));
        jLabel4.setBounds(new Rectangle(10, 19, 65, 14));
        jLabel5.setDisplayedMnemonic(Language.getString("P").charAt(0));
        jLabel5.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel5.setLabelFor(cbPercentFormat);
        jLabel5.setText(Language.getString("Percents"));
        jLabel5.setBounds(new Rectangle(10, 45, 65, 14));
        cbFormat.setEditable(true);
        cbFormat.setBounds(new Rectangle(79, 14, 88, 22));
        cbFormat.addActionListener(new SeriesGeneral_cbFormat_actionAdapter(this));
        cbPercentFormat.setEditable(true);
        cbPercentFormat.setBounds(new Rectangle(80, 40, 87, 22));
        cbPercentFormat.addActionListener(new
                                          SeriesGeneral_cbPercentFormat_actionAdapter(this));
        jLabel6.setDisplayedMnemonic(Language.getString("D").charAt(0));
        jLabel6.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel6.setLabelFor(spDepth);
        jLabel6.setText(Language.getString("Depth"));
        jLabel6.setBounds(new Rectangle(7, 61, 49, 14));
        spDepth.setBounds(new Rectangle(61, 58, 63, 20));
        spDepth.addChangeListener(new SeriesGeneral_spDepth_changeAdapter(this));
        cbAutoDepth.setText(Language.getString("Auto"));
        cbAutoDepth.setBounds(new Rectangle(129, 56, 57, 23));
        cbMarksClip.setMnemonic(Language.getString("D").charAt(0));
        cbMarksClip.setText(Language.getString("Clipped"));
        cbMarksClip.setBounds(new Rectangle(11, 37, 81, 23));
        cbMarksClip.addActionListener(new
                                      SeriesGeneral_cbMarksClip_actionAdapter(this));
        cbMarksMultiline.setMnemonic(Language.getString("M").charAt(0));
        cbMarksMultiline.setText(Language.getString("Multi_line"));
        cbMarksMultiline.setBounds(new Rectangle(11, 68, 81, 23));
        cbMarksMultiline.addActionListener(new
                                           SeriesGeneral_cbMarksMultiline_actionAdapter(this));
        cbAllMarks.setMnemonic(Language.getString("A").charAt(0));
        cbAllMarks.setText(Language.getString("All_Series_Visible"));
        cbAllMarks.setBounds(new Rectangle(132, 8, 142, 23));
        cbAllMarks.addActionListener(new SeriesGeneral_cbAllMarks_actionAdapter(this));
        jLabel7.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel7.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel7.setLabelFor(cbMarksStyle);
        jLabel7.setText(Language.getString("Style"));
        jLabel7.setBounds(new Rectangle(102, 40, 64, 14));
        cbMarksStyle.setBounds(new Rectangle(173, 35, 102, 22));
        cbMarksStyle.addActionListener(new
                                       SeriesGeneral_cbMarksStyle_actionAdapter(this));
        jLabel8.setDisplayedMnemonic(Language.getString("W").charAt(0));
        jLabel8.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel8.setLabelFor(spEvery);
        jLabel8.setText(Language.getString("Draw_every"));
        jLabel8.setBounds(new Rectangle(108, 69, 103, 14));
        spEvery.setBounds(new Rectangle(219, 66, 56, 20));
        spEvery.addChangeListener(new SeriesGeneral_spEvery_changeAdapter(this));
        jLabel9.setDisplayedMnemonic(Language.getString("N").charAt(0));
        jLabel9.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel9.setText(Language.getString("Angle"));
        jLabel9.setBounds(new Rectangle(101, 102, 75, 14));
        slAngle.setMaximum(360);
        slAngle.setBounds(new Rectangle(185, 99, 89, 23));
        slAngle.addChangeListener(new SeriesGeneral_slAngle_changeAdapter(this));
        jPanelSymbol.setLayout(borderLayout5);
        jButton3.setBounds(new Rectangle(119, 16, 88, 23));
        jButton3.setMnemonic(Language.getString("P").charAt(0));
        jButton3.setText(Language.getString("Pointer"));
        jButton3.addActionListener(new SeriesGeneral_jButton3_actionAdapter(this));
        lArrowHead.setDisplayedMnemonic(Language.getString("A").charAt(0));
        lArrowHead.setLabelFor(cbArrowHead);
        lArrowHead.setText(Language.getString("Arrow_Head"));
        lArrowHead.setBounds(new Rectangle(8, 49, 92, 14));
        cbArrowHead.setBounds(new Rectangle(8, 68, 91, 22));
        jLabel10.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel10.setLabelFor(spArrowSize);
        jLabel10.setText(Language.getString("Size"));
        jLabel10.setBounds(new Rectangle(8, 101, 78, 14));
        spArrowSize.setBounds(new Rectangle(8, 118, 56, 20));
        spArrowSize.addChangeListener(new
                                      SeriesGeneral_spArrowSize_changeAdapter(this));
        jLabel11.setDisplayedMnemonic(Language.getString("L").charAt(0));
        jLabel11.setLabelFor(spArrowLength);
        jLabel11.setText(Language.getString("Length"));
        jLabel11.setBounds(new Rectangle(119, 49, 88, 14));
        spArrowLength.setBounds(new Rectangle(119, 68, 52, 20));
        spArrowLength.addChangeListener(new
                                        SeriesGeneral_spArrowLength_changeAdapter(this));
        jLabel12.setDisplayedMnemonic(Language.getString("D").charAt(0));
        jLabel12.setLabelFor(spDistance);
        jLabel12.setText(Language.getString("Distance"));
        jLabel12.setBounds(new Rectangle(119, 101, 88, 14));
        spDistance.setBounds(new Rectangle(119, 118, 52, 20));
        spDistance.addChangeListener(new SeriesGeneral_spDistance_changeAdapter(this));
        cbCursor.setBounds(new Rectangle(61, 86, 105, 21));
        cbCursor.addActionListener(new SeriesGeneral_cbCursor_actionAdapter(this));
        jLabel13.setDisplayedMnemonic(Language.getString("U").charAt(0));
        jLabel13.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel13.setLabelFor(cbCursor);
        jLabel13.setText(Language.getString("Cursor"));
        jLabel13.setBounds(new Rectangle(6, 89, 48, 15));
        jPanel8.setBorder(null);
        jPanel8.setBounds(new Rectangle(1, 28, 343, 216));
        jPanel8.setLayout(borderLayout6);
        jTabbedPane1.addChangeListener(new
                                       SeriesGeneral_jTabbedPane1_changeAdapter(this));
        cbSeries.addActionListener(new SeriesGeneral_cbSeries_actionAdapter(this));
        this.add(jPanel1, java.awt.BorderLayout.NORTH);
        jPanel1.add(buttonColor1, java.awt.BorderLayout.EAST);
        jPanel1.add(labelIcon, java.awt.BorderLayout.WEST);
        jPanel1.add(cbSeries, java.awt.BorderLayout.CENTER);
        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jPanel10.add(jLabel4);
        jPanel10.add(jLabel5);
        jPanel10.add(cbPercentFormat);
        jPanel10.add(cbFormat);
        jPanel3.add(cbColorEach);
        jPanel3.add(cbShowInLegend);
        jPanel3.add(spDepth);
        jPanel3.add(jLabel6);
        jPanel3.add(cbAutoDepth);
        jPanel3.add(cbCursor);
        jPanel3.add(jLabel13);
        jPanel3.add(cbVertAxis);
        jPanel3.add(jLabel1);
        jPanel3.add(cbHorizAxis);
        jPanel3.add(cbHorizDate);
        jPanel3.add(jLabel2);
        jPanel3.add(cbVertDate);
        jPanel3.add(jPanel10);
        jPanel4.add(jTabbedPane2, java.awt.BorderLayout.CENTER);
        jPanel5.add(cbDatasourceStyle);
        jPanel5.add(panelRandom);
        jPanel5.add(jPanel8);
        panelRandom.add(jLabel3);
        panelRandom.add(cbDefaultSample);
        panelRandom.add(jButton1);
        panelRandom.add(jButton2);
        panelRandom.add(spNumSample);
        jPanel6.add(cbMarksVisible);
        jPanel6.add(cbMarksClip);
        jPanel6.add(cbMarksMultiline);
        jPanel6.add(jLabel7);
        jPanel6.add(cbMarksStyle);
        jPanel6.add(spEvery);
        jPanel6.add(jLabel8);
        jPanel6.add(jLabel9);
        jPanel6.add(slAngle);
        jPanel6.add(cbAllMarks);
        jPanel7.add(bArrowPen);
        jPanel7.add(jButton3);
        jPanel7.add(lArrowHead);
        jPanel7.add(cbArrowHead);
        jPanel7.add(jLabel10);
        jPanel7.add(spArrowSize);
        jPanel7.add(jLabel11);
        jPanel7.add(spArrowLength);
        jPanel7.add(jLabel12);
        jPanel7.add(spDistance);
        jTabbedPane2.add(jPanel6, Language.getString("Style"));
        jPanelSymbol.add(marksSymbol, BorderLayout.CENTER);
        jTabbedPane2.add(jPanelSymbol, Language.getString("Symbol"));
        jTabbedPane2.add(jPanelMarkTail, Language.getString("Tail"));
        jTabbedPane2.add(jPanel7, Language.getString("Arrow"));
        marksEditor.addTabsTo(jTabbedPane2);
        
        jPanelMarkTail.setLayout(null);
        
        lbMarksTailStyle.setBounds(new Rectangle(11, 5, 80, 22));
        cbMarksTailStyle.setBounds(new Rectangle(81, 5, 168, 22));
        cbMarksTailStyle.addActionListener(new
                                            SeriesGeneral_cbMarksTailStyle_actionAdapter(this));
        lbMarksTailAlign.setBounds(new Rectangle(11, 45, 80, 22));
        cbMarksTailAlign.setBounds(new Rectangle(81, 45, 168, 22));
        cbMarksTailAlign.addActionListener(new
                                                    SeriesGeneral_cbMarksTailAlign_actionAdapter(this));

        chMarksTailCustom.setBounds(new Rectangle(11, 85, 168, 22));
        chMarksTailCustom.addActionListener(new
                                                    SeriesGeneral_chMarksTailCustom_actionAdapter(this));
        
        jPanelMarkTail.add(lbMarksTailStyle);
        jPanelMarkTail.add(cbMarksTailStyle); 
        jPanelMarkTail.add(lbMarksTailAlign);
        jPanelMarkTail.add(cbMarksTailAlign);
        jPanelMarkTail.add(chMarksTailCustom);

    }

    public void cbShowInLegend_actionPerformed(ActionEvent e) {
        series.setShowInLegend(cbShowInLegend.isSelected());
    }

    public void setActiveTab(String tabName) {
        int tmpIndex = jTabbedPane1.indexOfTab(tabName);
        if (tmpIndex != -1) {
            jTabbedPane1.setSelectedIndex(tmpIndex);
        }
    }

    private boolean allMarksVisible() {
        IBaseChart tmp = series.getChart();

        for (int t = 0; t < tmp.getSeriesCount(); t++) {
            if (!tmp.getSeries(t).getMarks().getVisible()) {
                return false;
            }
        }

        return true;
    }

    public void cbMarksVisible_actionPerformed(ActionEvent e) {
        marks.setVisible(cbMarksVisible.isSelected());
        cbAllMarks.setSelected(allMarksVisible());
    }

    public void buttonColor1_actionPerformed(ActionEvent e) {
        series.setColor(buttonColor1.getColor());
    }

    public void cbColorEach_actionPerformed(ActionEvent e) {
        series.setColorEach(cbColorEach.isSelected());
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        series.clear();
        cbDatasourceStyle.setSelectedIndex(DATA_MANUAL);
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        int tmp = ((Integer) spNumSample.getValue()).intValue();
        if (tmp == 0) {
            series.fillSampleValues();
        } else {
            series.fillSampleValues(tmp);
        }

        cbDefaultSample.setSelected(false);
    }

    public void jCheckBox1_actionPerformed(ActionEvent e) {
        if (cbDefaultSample.isSelected()) {
            spNumSample.setValue(new Integer(series.getNumSampleValues()));
            cbDefaultSample.setEnabled(false);
        }
    }

    public void spNumSample_stateChanged(ChangeEvent e) {
        boolean defaultSamples = ((Integer) spNumSample.getValue()).intValue() ==
                                 series.getNumSampleValues();
        cbDefaultSample.setEnabled(!defaultSamples);
        cbDefaultSample.setSelected(defaultSamples);
    }

    public void cbHorizDate_actionPerformed(ActionEvent e) {
        series.getXValues().setDateTime(cbHorizDate.isSelected());
    }

    public void cbVertDate_actionPerformed(ActionEvent e) {
        series.getYValues().setDateTime(cbVertDate.isSelected());
    }

    FuncEdit functionEditor;
    BorderLayout borderLayout6 = new BorderLayout();

    public void cbDatasourceStyle_actionPerformed(ActionEvent actionEvent) {
        panelRandom.setVisible(cbDatasourceStyle.getSelectedIndex() ==
                               DATA_RANDOM);
        if (cbDatasourceStyle.getSelectedIndex() == DATA_FUNCTION)
        {
            if (functionEditor==null)
                functionEditor=new FuncEdit(series);

            jPanel8.removeAll();
            jPanel8.add(functionEditor);
        }
        jPanel8.setVisible(cbDatasourceStyle.getSelectedIndex() ==
                               DATA_FUNCTION);
    }

    public void cbFormat_actionPerformed(ActionEvent actionEvent) {
        series.setValueFormat(cbFormat.getSelectedItem().toString());
    }

    public void cbPercentFormat_actionPerformed(ActionEvent actionEvent) {
        series.setPercentFormat(cbPercentFormat.getSelectedItem().toString());
    }

    public void cbHorizAxis_actionPerformed(ActionEvent actionEvent) {
        if (series != null) {
            if (cbHorizAxis.getSelectedIndex()<FIXEDAXISOPTION)
              series.setHorizontalAxis(HorizontalAxis.fromInt(cbHorizAxis.
                    getSelectedIndex()));
            else
            {
                AxisObj aObj=(AxisObj)cbHorizAxis.getSelectedItem();
                com.steema.teechart.axis.Axis a=series.getChart().getAxes().getCustom().getAxis(aObj.index); 
                series.setCustomHorizAxis(a);
            }
        }
    }

    public void cbVertAxis_actionPerformed(ActionEvent actionEvent) {
        if (series != null) {
            if (cbVertAxis.getSelectedIndex()<FIXEDAXISOPTION)
              series.setVerticalAxis(VerticalAxis.fromInt(cbVertAxis.
                    getSelectedIndex()));
            else
            {
                AxisObj aObj=(AxisObj)cbVertAxis.getSelectedItem();
                com.steema.teechart.axis.Axis a=series.getChart().getAxes().getCustom().getAxis(aObj.index); 
                series.setCustomVertAxis(a);
            }
        }
    }

    public void spDepth_stateChanged(ChangeEvent e) {
        series.setDepth(((Integer) spDepth.getValue()).intValue());
    }

    public void cbMarksClip_actionPerformed(ActionEvent e) {
        marks.setClip(cbMarksClip.isSelected());
    }

    public void cbMarksMultiline_actionPerformed(ActionEvent e) {
        marks.setMultiLine(cbMarksMultiline.isSelected());
    }

    public void cbAllMarks_actionPerformed(ActionEvent e) {
        if (canModifySeries()) {
            for (int t = 0; t < series.getChart().getSeriesCount(); t++) {
                series.getChart().getSeries(t).getMarks().setVisible(cbAllMarks.
                        isSelected());
            }
            cbMarksVisible.setSelected(cbAllMarks.isSelected());
        }

    }

    private boolean canModifySeries() {
        //return (! creatingForm) && (! changingPage) &&
        return (series != null);
    }
    
    public void cbMarksTailStyle_actionPerformed(ActionEvent actionEvent) {
       if(series!=null) {
            series.getMarks().setMarkerTail(MarksTail.fromInt(cbMarksTailStyle.getSelectedIndex()));
       }
    }
    
    public void cbMarksTailAlign_actionPerformed(ActionEvent actionEvent) {
        if(series!=null) {
            series.getMarks().getTailParams().setAlign(TailAlignment.fromInt(cbMarksTailAlign.getSelectedIndex()));
        }
    }
    
    public void chMarksTailCustom_actionPerformed(ActionEvent actionEvent) {
        if(series!=null) {
            series.getMarks().getTailParams().setCustomPoint(chMarksTailCustom.isSelected());        
        }
    }


    public void cbMarksStyle_actionPerformed(ActionEvent actionEvent) {
        if (series != null) {
            marks.setStyle(MarksStyle.fromValue(cbMarksStyle.getSelectedIndex()));
        }
    }

    public void spEvery_stateChanged(ChangeEvent e) {
        marks.setDrawEvery(((Integer) spEvery.getValue()).intValue());
    }

    public void slAngle_stateChanged(ChangeEvent e) {
        marks.setAngle(slAngle.getValue());
    }

    public void jButton3_actionPerformed(ActionEvent e) {
        SeriesPointerEditor.edit(this, marks.getCallout());
    }

    public void spArrowSize_stateChanged(ChangeEvent e) {
        marks.getCallout().setArrowHeadSize(((Integer) spArrowSize.getValue()).
                                            intValue());
    }

    public void spArrowLength_stateChanged(ChangeEvent e) {
        marks.setArrowLength(((Integer) spArrowLength.getValue()).intValue());
    }

    public void spDistance_stateChanged(ChangeEvent e) {
        marks.getCallout().setDistance(((Integer) spDistance.getValue()).
                                       intValue());
    }

    public void cbCursor_actionPerformed(ActionEvent e) {
        if (series != null) {
            series.setCursor(new Cursor(cbCursor.getSelectedIndex()));
        }
    }

    public void jTabbedPane1_stateChanged(ChangeEvent e) {
        if (jTabbedPane1.getSelectedComponent()==jPanel5) SetTabDataSource();
    }

    private void SetTabDataSource() {
    int tmp;
    Series tmpSource;

    tmp=cbDatasourceStyle.getSelectedIndex();

    if (series.getFunction()!=null)
        cbDatasourceStyle.setSelectedIndex(2);
    else
      if (!(series.getDataSource()!=null)) {
        if ((series.getCount()>0) && (!series.manualData))
           cbDatasourceStyle.setSelectedIndex(1);
        else
           cbDatasourceStyle.setSelectedIndex(0);
      }
      else
        if (series.getDataSource() == series)
         cbDatasourceStyle.setSelectedIndex(2);
        else {
          tmpSource = null;

      }
          //if (cbDatasourceStyle.getSelectedIndex() != tmp) //CDI Qu?
          //    cbDatasourceStyle.setSelectedIndex(2);
    }

    public void cbSeries_actionPerformed(ActionEvent e) {
        if(!setting) {
            AddSeriesEditor((Series) cbSeries.getSelectedItem());
        }
    }

}


class SeriesGeneral_cbSeries_actionAdapter implements ActionListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_cbSeries_actionAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbSeries_actionPerformed(e);
    }
}


class SeriesGeneral_jTabbedPane1_changeAdapter implements ChangeListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_jTabbedPane1_changeAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jTabbedPane1_stateChanged(e);
    }
}


final class SeriesGeneral_cbCursor_actionAdapter implements ActionListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_cbCursor_actionAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbCursor_actionPerformed(e);
    }
}


final class SeriesGeneral_spDistance_changeAdapter implements ChangeListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_spDistance_changeAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spDistance_stateChanged(e);
    }
}


final class SeriesGeneral_spArrowLength_changeAdapter implements ChangeListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_spArrowLength_changeAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spArrowLength_stateChanged(e);
    }
}


class SeriesGeneral_spArrowSize_changeAdapter implements ChangeListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_spArrowSize_changeAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spArrowSize_stateChanged(e);
    }
}


final class SeriesGeneral_jButton3_actionAdapter implements ActionListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_jButton3_actionAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton3_actionPerformed(e);
    }
}


final class SeriesGeneral_slAngle_changeAdapter implements ChangeListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_slAngle_changeAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.slAngle_stateChanged(e);
    }
}


final class SeriesGeneral_spEvery_changeAdapter implements ChangeListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_spEvery_changeAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spEvery_stateChanged(e);
    }
}


final class SeriesGeneral_cbMarksStyle_actionAdapter implements ActionListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_cbMarksStyle_actionAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.cbMarksStyle_actionPerformed(actionEvent);
    }
}


final class SeriesGeneral_cbAllMarks_actionAdapter implements ActionListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_cbAllMarks_actionAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAllMarks_actionPerformed(e);
    }
}


final class SeriesGeneral_cbMarksMultiline_actionAdapter implements
        ActionListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_cbMarksMultiline_actionAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbMarksMultiline_actionPerformed(e);
    }
}


final class SeriesGeneral_cbMarksClip_actionAdapter implements ActionListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_cbMarksClip_actionAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbMarksClip_actionPerformed(e);
    }
}


final class SeriesGeneral_spDepth_changeAdapter implements ChangeListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_spDepth_changeAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spDepth_stateChanged(e);
    }
}


final class SeriesGeneral_cbVertAxis_actionAdapter implements ActionListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_cbVertAxis_actionAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.cbVertAxis_actionPerformed(actionEvent);
    }
}


final class SeriesGeneral_cbHorizAxis_actionAdapter implements ActionListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_cbHorizAxis_actionAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.cbHorizAxis_actionPerformed(actionEvent);
    }
}


final class SeriesGeneral_cbPercentFormat_actionAdapter implements
        ActionListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_cbPercentFormat_actionAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.cbPercentFormat_actionPerformed(actionEvent);
    }
}


final class SeriesGeneral_cbFormat_actionAdapter implements ActionListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_cbFormat_actionAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.cbFormat_actionPerformed(actionEvent);
    }
}


final class SeriesGeneral_cbDatasourceStyle_actionAdapter implements
        ActionListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_cbDatasourceStyle_actionAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.cbDatasourceStyle_actionPerformed(actionEvent);
    }
}


final class SeriesGeneral_cbVertDate_actionAdapter implements ActionListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_cbVertDate_actionAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbVertDate_actionPerformed(e);
    }
}


final class SeriesGeneral_cbHorizDate_actionAdapter implements ActionListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_cbHorizDate_actionAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbHorizDate_actionPerformed(e);
    }
}


final class SeriesGeneral_spNumSample_changeAdapter implements ChangeListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_spNumSample_changeAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spNumSample_stateChanged(e);
    }
}


final class SeriesGeneral_jCheckBox1_actionAdapter implements ActionListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_jCheckBox1_actionAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jCheckBox1_actionPerformed(actionEvent);
    }
}


final class SeriesGeneral_jButton1_actionAdapter implements ActionListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_jButton1_actionAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jButton1_actionPerformed(actionEvent);
    }
}


final class SeriesGeneral_jButton2_actionAdapter implements ActionListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_jButton2_actionAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jButton2_actionPerformed(actionEvent);
    }
}


final class SeriesGeneral_cbColorEach_actionAdapter implements ActionListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_cbColorEach_actionAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.cbColorEach_actionPerformed(actionEvent);
    }
}


final class SeriesGeneral_buttonColor1_actionAdapter implements ActionListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_buttonColor1_actionAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.buttonColor1_actionPerformed(actionEvent);
    }
}


final class SeriesGeneral_cbMarksVisible_actionAdapter implements
        ActionListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_cbMarksVisible_actionAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbMarksVisible_actionPerformed(e);
    }
}

final class SeriesGeneral_cbShowInLegend_actionAdapter implements
        ActionListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_cbShowInLegend_actionAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbShowInLegend_actionPerformed(e);
    }
}

final class SeriesGeneral_cbMarksTailStyle_actionAdapter implements
        ActionListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_cbMarksTailStyle_actionAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.cbMarksTailStyle_actionPerformed(actionEvent);
    }
}

final class SeriesGeneral_cbMarksTailAlign_actionAdapter implements
        ActionListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_cbMarksTailAlign_actionAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.cbMarksTailAlign_actionPerformed(actionEvent);
    }
}

final class SeriesGeneral_chMarksTailCustom_actionAdapter implements
        ActionListener {
    private SeriesGeneral adaptee;
    SeriesGeneral_chMarksTailCustom_actionAdapter(SeriesGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.chMarksTailCustom_actionPerformed(actionEvent);
    }
}

final class AxisObj
{
    public String name;
    public int index;
    
    public AxisObj(String title, int idx)
    {
        name=title;
        index= idx;
    }
    
    public String toString()
    {
        return name;
    }
}
