package com.steema.teechart.editors.series;

import javax.swing.*;
import com.steema.teechart.languages.Language;
import javax.swing.border.EtchedBorder;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.styles.Calendar;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.styles.Series;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * <p>Title: CalendarEditor class</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

public class CalendarEditor extends BaseSeriesEditor {

    private Calendar calendar;
    JCheckBox cbWeekDays = new JCheckBox();
    JCheckBox cbWeekUpper = new JCheckBox();
    JCheckBox cbToday = new JCheckBox();
    JCheckBox cbTrailing = new JCheckBox();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel2 = new JPanel();
    ButtonPen bPen = new ButtonPen();
    Border border1 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    JCheckBox cbMonths = new JCheckBox();
    JCheckBox cbMonthUpper = new JCheckBox();
    JCheckBox cbPrevious = new JCheckBox();
    JCheckBox cbNext = new JCheckBox();
    JPanel jPanel3 = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    BorderLayout borderLayout3 = new BorderLayout();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();
    JPanel jPanel6 = new JPanel();
    JPanel jPanel7 = new JPanel();
    BorderLayout borderLayout4 = new BorderLayout();
    BorderLayout borderLayout5 = new BorderLayout();
    BorderLayout borderLayout6 = new BorderLayout();
    BorderLayout borderLayout7 = new BorderLayout();

    public CalendarEditor() {
        try {
            jbInit();
            Load();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        this.calendar = (Calendar)series;

/* TODO        boolean canUpper= ((Language.getString("Translator")==null) || (Language.getString("Translator").HasUpperCase()));
        CBWeekUpper.Visible=canUpper;
      CBMonthUpper.Visible=canUpper;
*/
    }

    private void Load() {
        if (calendar != null) {
            bPen.setPen(calendar.getPen());
            cbWeekDays.setSelected(calendar.getWeekDays().getVisible());
            cbWeekUpper.setSelected(calendar.getWeekDays().getUpperCase());
            cbTrailing.setSelected(calendar.getTrailing().getVisible());
            cbToday.setSelected(calendar.getToday().getVisible());
            cbMonths.setSelected(calendar.getMonths().getVisible());
            cbMonthUpper.setSelected(calendar.getMonths().getUpperCase());
            cbPrevious.setSelected(calendar.getPreviousMonthButton().isVisible());
            cbNext.setSelected(calendar.getNextMonthButton().isVisible());
        }
   }


    private void jbInit() throws Exception {
        cbWeekDays.setActionCommand(Language.getString("Show_Weekdays"));
        cbWeekDays.setMnemonic(Language.getString("W").charAt(0));
        cbWeekDays.setSelected(true);
        cbWeekDays.setText(Language.getString("Show_Weekdays"));
        cbWeekDays.setBounds(new Rectangle(15, 43, 125, 23));
        cbWeekDays.addActionListener(new
                                     CalendarEditor_cbWeekDays_actionAdapter(this));
        this.setLayout(borderLayout1);
        cbWeekUpper.setActionCommand(Language.getString("Uppercase"));
        cbWeekUpper.setMnemonic(Language.getString("U").charAt(0));
        cbWeekUpper.setSelected(true);
        cbWeekUpper.setText(Language.getString("Uppercase"));
        cbWeekUpper.setBounds(new Rectangle(15, 69, 115, 23));
        cbWeekUpper.addActionListener(new
                                      CalendarEditor_cbWeekUpper_actionAdapter(this));
        cbToday.setActionCommand(Language.getString("Show_Today"));
        cbToday.setSelected(true);
        cbToday.setText(Language.getString("Show_Today"));
        cbToday.setBounds(new Rectangle(15, 120, 124, 23));
        cbToday.addActionListener(new CalendarEditor_cbToday_actionAdapter(this));
        cbTrailing.setMnemonic(Language.getString("T").charAt(0));
        cbTrailing.setSelected(true);
        cbTrailing.setText(Language.getString("Trailing_days"));
        cbTrailing.setBounds(new Rectangle(15, 95, 124, 23));
        cbTrailing.addActionListener(new
                                     CalendarEditor_cbTrailing_actionAdapter(this));
        jPanel1.setLayout(null);
        jPanel2.setLayout(borderLayout2);
        bPen.setBounds(new Rectangle(15, 11, 80, 23));
        bPen.setActionCommand(Language.getString("Lines"));
        bPen.setMnemonic(Language.getString("L").charAt(0));
        bPen.setText(Language.getString("Lines"));
        cbMonths.setActionCommand(Language.getString("Show_Months"));
        cbMonths.setMnemonic(Language.getString("M").charAt(0));
        cbMonths.setSelected(true);
        cbMonths.setText(Language.getString("Show_Months"));
        cbMonths.setBounds(new Rectangle(164, 44, 119, 23));
        cbMonths.addActionListener(new CalendarEditor_cbMonths_actionAdapter(this));
        cbMonthUpper.setMnemonic(Language.getString("P").charAt(0));
        cbMonthUpper.setSelected(true);
        cbMonthUpper.setText(Language.getString("Uppercase"));
        cbMonthUpper.setBounds(new Rectangle(164, 69, 109, 23));
        cbMonthUpper.addActionListener(new
                CalendarEditor_cbMonthUpper_actionAdapter(this));
        cbPrevious.setActionCommand(Language.getString("Show_Previous_Button"));
        cbPrevious.setMnemonic(Language.getString("E").charAt(0));
        cbPrevious.setText(Language.getString("Show_Previous_Button"));
        cbPrevious.setBounds(new Rectangle(163, 95, 135, 23));
        cbPrevious.addActionListener(new
                                     CalendarEditor_cbPrevious_actionAdapter(this));
        cbNext.setMnemonic(Language.getString("X").charAt(0));
        cbNext.setText(Language.getString("Show_Next_Button"));
        cbNext.setBounds(new Rectangle(163, 120, 114, 23));
        cbNext.addActionListener(new CalendarEditor_cbNext_actionAdapter(this));
        jPanel3.setLayout(borderLayout3);
        jPanel4.setLayout(borderLayout4);
        jPanel5.setLayout(borderLayout5);
        jPanel6.setLayout(borderLayout6);
        jPanel7.setLayout(borderLayout7);
        jTabbedPane1.addChangeListener(new
                CalendarEditor_jTabbedPane1_changeAdapter(this));
        jPanel1.add(bPen);
        jPanel1.add(cbWeekUpper);
        jPanel1.add(cbTrailing);
        jPanel1.add(cbToday);
        jPanel1.add(cbWeekDays);
        jPanel1.add(cbMonths);
        jPanel1.add(cbMonthUpper);
        jPanel1.add(cbPrevious);
        jPanel1.add(cbNext);
        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.add(jPanel1, Language.getString("Options"));
        jTabbedPane1.add(jPanel2, Language.getString("Days"));
        jTabbedPane1.add(jPanel3, Language.getString("WeekDays"));
        jTabbedPane1.add(jPanel4, Language.getString("Today"));
        jTabbedPane1.add(jPanel5, Language.getString("Sunday"));
        jTabbedPane1.add(jPanel6, Language.getString("Trailing"));
        jTabbedPane1.add(jPanel7, Language.getString("Months"));
        jTabbedPane1.setSelectedComponent(jPanel1);
    }

    public void cbWeekDays_actionPerformed(ActionEvent e) {
        calendar.getWeekDays().setVisible(cbWeekDays.isSelected());
    }

    public void cbWeekUpper_actionPerformed(ActionEvent e) {
        calendar.getWeekDays().setUpperCase(cbWeekUpper.isSelected());
    }

    public void cbTrailing_actionPerformed(ActionEvent e) {
        calendar.getTrailing().setVisible(cbTrailing.isSelected());
    }

    public void cbToday_actionPerformed(ActionEvent e) {
        calendar.getToday().setVisible(cbToday.isSelected());
    }

    public void cbMonths_actionPerformed(ActionEvent e) {
        calendar.getMonths().setVisible(cbMonths.isSelected());
    }

    public void cbMonthUpper_actionPerformed(ActionEvent e) {
        calendar.getMonths().setUpperCase(cbMonthUpper.isSelected());
    }

     public void cbPrevious_actionPerformed(ActionEvent e) {
        calendar.getPreviousMonthButton().setVisible(cbPrevious.isSelected());
    }
    public void cbNext_actionPerformed(ActionEvent e) {
        calendar.getNextMonthButton().setVisible(cbNext.isSelected());
    }

    private void AddShapeEditor(JTabbedPane page,JPanel tabControl,com.steema.teechart.TextShape c) {
       com.steema.teechart.editors.ShapeEditor s = new com.steema.teechart.editors.ShapeEditor(c);
       tabControl.add(s);
    }

    public void jTabbedPane1_stateChanged(ChangeEvent e) {

        switch (jTabbedPane1.getSelectedIndex()){
          case 1: AddShapeEditor(jTabbedPane1,jPanel2,calendar.getDays()); break;
          case 2: AddShapeEditor(jTabbedPane1,jPanel3,calendar.getWeekDays()); break;
          case 3: AddShapeEditor(jTabbedPane1,jPanel4,calendar.getToday()); break;
          case 4: AddShapeEditor(jTabbedPane1,jPanel5,calendar.getSunday()); break;
          case 5: AddShapeEditor(jTabbedPane1,jPanel6,calendar.getTrailing()); break;
          case 6: AddShapeEditor(jTabbedPane1,jPanel7,calendar.getMonths()); break;
      }
    }

}


class CalendarEditor_jTabbedPane1_changeAdapter implements ChangeListener {
    private CalendarEditor adaptee;
    CalendarEditor_jTabbedPane1_changeAdapter(CalendarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jTabbedPane1_stateChanged(e);
    }
}


final class CalendarEditor_cbNext_actionAdapter implements ActionListener {
    private CalendarEditor adaptee;
    CalendarEditor_cbNext_actionAdapter(CalendarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbNext_actionPerformed(e);
    }
}


final class CalendarEditor_cbPrevious_actionAdapter implements ActionListener {
    private CalendarEditor adaptee;
    CalendarEditor_cbPrevious_actionAdapter(CalendarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbPrevious_actionPerformed(e);
    }
}


final class CalendarEditor_cbMonthUpper_actionAdapter implements ActionListener {
    private CalendarEditor adaptee;
    CalendarEditor_cbMonthUpper_actionAdapter(CalendarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbMonthUpper_actionPerformed(e);
    }
}


final class CalendarEditor_cbMonths_actionAdapter implements ActionListener {
    private CalendarEditor adaptee;
    CalendarEditor_cbMonths_actionAdapter(CalendarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbMonths_actionPerformed(e);
    }
}


final class CalendarEditor_cbToday_actionAdapter implements ActionListener {
    private CalendarEditor adaptee;
    CalendarEditor_cbToday_actionAdapter(CalendarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbToday_actionPerformed(e);
    }
}


final class CalendarEditor_cbTrailing_actionAdapter implements ActionListener {
    private CalendarEditor adaptee;
    CalendarEditor_cbTrailing_actionAdapter(CalendarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbTrailing_actionPerformed(e);
    }
}


final class CalendarEditor_cbWeekUpper_actionAdapter implements ActionListener {
    private CalendarEditor adaptee;
    CalendarEditor_cbWeekUpper_actionAdapter(CalendarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbWeekUpper_actionPerformed(e);
    }
}


final class CalendarEditor_cbWeekDays_actionAdapter implements ActionListener {
    private CalendarEditor adaptee;
    CalendarEditor_cbWeekDays_actionAdapter(CalendarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbWeekDays_actionPerformed(e);
    }
}

