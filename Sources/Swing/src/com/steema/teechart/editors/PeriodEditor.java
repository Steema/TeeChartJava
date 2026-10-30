package com.steema.teechart.editors;

import javax.swing.JPanel;
import com.steema.teechart.languages.Language;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.SystemColor;
import javax.swing.border.Border;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.JComponent;
import javax.swing.ButtonGroup;

/**
 *
 * <p>Title: PeriodEditor class</p>
 *
 * <p>Description: Period Editor</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

public class PeriodEditor extends JPanel {


    private com.steema.teechart.functions.Function function;
    JComponent controlToEnable;
    JLabel jLabel1 = new JLabel();
    JSpinner spNum = new JSpinner();
    JCheckBox cbAll = new JCheckBox();

    public PeriodEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ButtonGroup group1=new ButtonGroup();
        group1.add(rbNum);
        group1.add(rbRange);

        ButtonGroup group2=new ButtonGroup();
        group2.add(rbFirst);
        group2.add(rbCenter);
        group2.add(rbLast);
    }

    public PeriodEditor(com.steema.teechart.functions.Function f) {
        this();
        function=f;

        if (function.getPeriodStyle()==com.steema.teechart.functions.PeriodStyle.NUMPOINTS) {
            rbNum.setSelected(true);

            cbAll.setSelected(f.getPeriod()==0);
        } else
            rbRange.setSelected(true);

      if (function.getPeriodAlign()==com.steema.teechart.functions.PeriodAlign.FIRST)
        rbFirst.setSelected(true);
      else
        if (function.getPeriodAlign()==com.steema.teechart.functions.PeriodAlign.CENTER)
        rbCenter.setSelected(true);
      else
        rbLast.setSelected(true);

       Load();
    }

    JRadioButton rbNum = new JRadioButton();
    JRadioButton rbRange = new JRadioButton();
    JButton bChange = new JButton();
    JLabel jLabel2 = new JLabel();
    JPanel jPanel1 = new JPanel();
    Border border1 = BorderFactory.createLineBorder(SystemColor.controlText, 2);
    Border border2 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border3 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border4 = BorderFactory.createEtchedBorder(Color.white,
            new Color(165, 163, 151));
    Border border5 = BorderFactory.createEtchedBorder(Color.white,
            new Color(165, 163, 151));
    Border border6 = new TitledBorder(border5, Language.getString("Alignment"));
    JRadioButton rbFirst = new JRadioButton();
    JRadioButton rbCenter = new JRadioButton();
    JRadioButton rbLast = new JRadioButton();

    private void jbInit() throws Exception {
        this.setLayout(null);
        jLabel1.setDisplayedMnemonic(Language.getString("P").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(null);
        jLabel1.setText(Language.getString("Calculate_every"));
        jLabel1.setBounds(new Rectangle(4, 4, 96, 23));
        spNum.setBounds(new Rectangle(142, 31, 56, 23));
        spNum.addChangeListener(new PeriodEditor_spNum_changeAdapter(this));
        cbAll.setMnemonic(Language.getString("A").charAt(0));
        cbAll.setSelected(true);
        cbAll.setText(Language.getString("All_points"));
        cbAll.setBounds(new Rectangle(206, 31, 87, 22));
        cbAll.addChangeListener(new PeriodEditor_cbAll_changeAdapter(this));
        rbNum.setMnemonic(Language.getString("N").charAt(0));
        rbNum.setText(Language.getString("Number_of_points"));
        rbNum.setBounds(new Rectangle(22, 31, 117, 22));
        rbNum.addActionListener(new PeriodEditor_rbNum_actionAdapter(this));
        rbRange.setMnemonic(Language.getString("R").charAt(0));
        rbRange.setText(Language.getString("Range_of_Values"));
        rbRange.setBounds(new Rectangle(22, 60, 115, 22));
        rbRange.addActionListener(new PeriodEditor_rbRange_actionAdapter(this));
        bChange.setBounds(new Rectangle(205, 58, 77, 26));
        bChange.setMnemonic(Language.getString("C").charAt(0));
        bChange.setText(Language.getString("Change"));
        bChange.addActionListener(new PeriodEditor_bChange_actionAdapter(this));
        jLabel2.setText("0");
        jLabel2.setBounds(new Rectangle(142, 61, 17, 21));
        jPanel1.setBorder(border6);
        jPanel1.setBounds(new Rectangle(24, 90, 259, 54));
        jPanel1.setLayout(null);
        rbFirst.setMnemonic(Language.getString("F").charAt(0));
        rbFirst.setText(Language.getString("First"));
        rbFirst.setBounds(new Rectangle(14, 19, 52, 23));
        rbFirst.addActionListener(new PeriodEditor_rbFirst_actionAdapter(this));
        rbCenter.setMnemonic(Language.getString("E").charAt(0));
        rbCenter.setText(Language.getString("Center"));
        rbCenter.setBounds(new Rectangle(95, 19, 59, 23));
        rbCenter.addActionListener(new PeriodEditor_rbCenter_actionAdapter(this));
        rbLast.setMnemonic(Language.getString("L").charAt(0));
        rbLast.setText(Language.getString("Last"));
        rbLast.setBounds(new Rectangle(178, 20, 55, 21));
        rbLast.addActionListener(new PeriodEditor_rbLast_actionAdapter(this));
        this.add(jLabel1);
        this.add(rbNum);
        this.add(spNum);
        this.add(cbAll);
        this.add(rbRange);
        this.add(jLabel2);
        this.add(jPanel1);
        jPanel1.add(rbFirst);
        jPanel1.add(rbLast);
        jPanel1.add(rbCenter);
        this.add(bChange);
    }

    public void rbFirst_actionPerformed(ActionEvent e) {
        function.setPeriodAlign(com.steema.teechart.functions.PeriodAlign.FIRST);
    }

    public void rbCenter_actionPerformed(ActionEvent e) {
        function.setPeriodAlign(com.steema.teechart.functions.PeriodAlign.CENTER);
    }

    public void rbLast_actionPerformed(ActionEvent e) {
        function.setPeriodAlign(com.steema.teechart.functions.PeriodAlign.LAST);
    }

    public void spNum_stateChanged(ChangeEvent e) {
        Object obj  = spNum.getValue();

        if(obj instanceof Double) {
            function.setPeriod(((Double) obj).doubleValue());
        }
        else if(obj instanceof Integer) {
           function.setPeriod(((Integer) obj).intValue());
        }

        cbAll.setSelected(function.getPeriod()==0);
        jLabel2.setText(spNum.getValue().toString());
    }

    public void rbNum_actionPerformed(ActionEvent e) {
        spNum.requestFocus();
    }

    public void rbRange_actionPerformed(ActionEvent e) {
        rbRange.requestFocus();
    }

    public void bChange_actionPerformed(ActionEvent e) {
        rbRange.setSelected(true);

        com.steema.teechart.editors.AxisIncrement tmp=new com.steema.teechart.editors.AxisIncrement();
        tmp.isExact=true;
        tmp.dIncrement=function.getPeriod();
//        tmp.iStep=com.steema.teechart.axis.Axis.findDateTimeStep(function.getPeriod());
        tmp.setTitle(Language.getString("PeriodRange"));
        tmp.setModal(true);
        tmp.setSize(10,10);
        tmp.setLocationRelativeTo(this);
        tmp.setVisible(true);
        if (tmp.result) {
          spNum.setValue(new Double(tmp.getIncrement()));
          jLabel2.setText(spNum.getValue().toString());
        }
//        this.repaint();
    }

    private void Load() {
        if (function!=null) {
            spNum.setValue(new Double(function.getPeriod()));
            cbAll.setSelected(function.getPeriod()==0);
        }
    }

    public void cbAll_stateChanged(ChangeEvent e) {
        if (cbAll.isSelected()) {
          function.setPeriod(0);
          spNum.setValue(new Integer(0));
      }
    }

    private void Changed()
    {
      if (controlToEnable!=null)
          controlToEnable.setEnabled(true);
    }

    public void Apply()
    {
    }
}


class PeriodEditor_cbAll_changeAdapter implements ChangeListener {
    private PeriodEditor adaptee;
    PeriodEditor_cbAll_changeAdapter(PeriodEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.cbAll_stateChanged(e);
    }
}


class PeriodEditor_bChange_actionAdapter implements ActionListener {
    private PeriodEditor adaptee;
    PeriodEditor_bChange_actionAdapter(PeriodEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bChange_actionPerformed(e);
    }
}


class PeriodEditor_rbRange_actionAdapter implements ActionListener {
    private PeriodEditor adaptee;
    PeriodEditor_rbRange_actionAdapter(PeriodEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbRange_actionPerformed(e);
    }
}


class PeriodEditor_rbNum_actionAdapter implements ActionListener {
    private PeriodEditor adaptee;
    PeriodEditor_rbNum_actionAdapter(PeriodEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbNum_actionPerformed(e);
    }
}


class PeriodEditor_spNum_changeAdapter implements ChangeListener {
    private PeriodEditor adaptee;
    PeriodEditor_spNum_changeAdapter(PeriodEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spNum_stateChanged(e);
    }
}


class PeriodEditor_rbLast_actionAdapter implements ActionListener {
    private PeriodEditor adaptee;
    PeriodEditor_rbLast_actionAdapter(PeriodEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbLast_actionPerformed(e);
    }
}


class PeriodEditor_rbCenter_actionAdapter implements ActionListener {
    private PeriodEditor adaptee;
    PeriodEditor_rbCenter_actionAdapter(PeriodEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbCenter_actionPerformed(e);
    }
}


class PeriodEditor_rbFirst_actionAdapter implements ActionListener {
    private PeriodEditor adaptee;
    PeriodEditor_rbFirst_actionAdapter(PeriodEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbFirst_actionPerformed(e);
    }
}

