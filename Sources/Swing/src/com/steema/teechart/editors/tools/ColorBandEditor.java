package com.steema.teechart.editors.tools;

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.steema.teechart.editors.tools.AxisToolEdit;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.ButtonColor;
import com.steema.teechart.tools.Tool;
import com.steema.teechart.tools.ColorBand;
import com.steema.teechart.editors.BrushEditor;
import com.steema.teechart.editors.GradientEditor;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: ColorBandEditor class</p>
 *
 * <p>Description:  ColorBand Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ColorBandEditor extends AxisToolEdit {


    public ColorBandEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTool(Tool tool) {
        setting=true;
        super.setTool(tool);
        colorBand=(ColorBand)tool;

        spTransp.setValue(new Integer(colorBand.getTransparency()));
        tbStart.setText(String.valueOf(colorBand.getStart()));
        tbEnd.setText(String.valueOf(colorBand.getEnd()));
        cbBehind.setSelected(colorBand.getDrawBehind());
        bColor.setColor(colorBand.getColor());
        bStart.setPen(colorBand.getStartLinePen());
        bEnd.setPen(colorBand.getEndLinePen());
        cbStart.setSelected(colorBand.getResizeStart());
        cbEnd.setSelected(colorBand.getResizeEnd());
        setting=false;
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jTabbedPane1.setBounds(new Rectangle(2, 81, 400, 220));
        jLabel1.setDisplayedMnemonic(Language.getString("E").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(tbEnd);
        jLabel1.setText(Language.getString("End_Value_"));
        jLabel1.setBounds(new Rectangle(8, 62, 57, 15));
        jPanel1.setLayout(null);
        jLabel2.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setLabelFor(tbStart);
        jLabel2.setText(Language.getString("Start_Value_"));
        jLabel2.setBounds(new Rectangle(8, 17, 57, 15));
        tbStart.setBounds(new Rectangle(82, 14, 59, 20));
        tbStart.addKeyListener(new ColorBandEditor_tbStart_keyAdapter(this));
        tbEnd.setBounds(new Rectangle(82, 59, 59, 20));
        tbEnd.addKeyListener(new ColorBandEditor_tbEnd_keyAdapter(this));
        bStart.setBounds(new Rectangle(147, 12, 74, 23));
        bStart.setMnemonic(Language.getString("B").charAt(0));
        bStart.setText(Language.getString("Border"));
        bEnd.setBounds(new Rectangle(147, 57, 74, 23));
        bEnd.setMnemonic(Language.getString("O").charAt(0));
        bEnd.setText(Language.getString("Border"));
        cbStart.setMnemonic(Language.getString("A").charAt(0));
        cbStart.setText(Language.getString("Allow_Drag1"));
        cbStart.setBounds(new Rectangle(79, 34, 83, 23));
        cbStart.addActionListener(new ColorBandEditor_cbStart_actionAdapter(this));
        cbEnd.setMnemonic(Language.getString("D").charAt(0));
        cbEnd.setText(Language.getString("Allow_Drag2"));
        cbEnd.setBounds(new Rectangle(79, 79, 83, 23));
        cbEnd.addActionListener(new ColorBandEditor_cbEnd_actionAdapter(this));
        bGradient.setBounds(new Rectangle(15, 19, 87, 23));
        bGradient.setMnemonic(Language.getString("G").charAt(0));
        bGradient.setText(Language.getString("Gradient"));
        bGradient.addActionListener(new ColorBandEditor_bGradient_actionAdapter(this));
        jPanel2.setLayout(null);
        cbBehind.setMnemonic(Language.getString("D").charAt(0));
        cbBehind.setText(Language.getString("Draw_Behind"));
        cbBehind.setBounds(new Rectangle(113, 19, 114, 23));
        cbBehind.addActionListener(new ColorBandEditor_cbBehind_actionAdapter(this));
        bColor.setBounds(new Rectangle(15, 50, 87, 23));
        bColor.setMnemonic(Language.getString("C").charAt(0));
        bColor.setText(Language.getString("Color"));
        bColor.addActionListener(new ColorBandEditor_bColor_actionAdapter(this));
        jLabel3.setDisplayedMnemonic(Language.getString("T").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setLabelFor(spTransp);
        jLabel3.setText(Language.getString("Transparency"));
        jLabel3.setBounds(new Rectangle(13, 85, 87, 15));
        spTransp.setBounds(new Rectangle(107, 81, 53, 23));
        spTransp.addChangeListener(new ColorBandEditor_spTransp_changeAdapter(this));
        bPattern.setBounds(new Rectangle(134, 43, 75, 24));
        bPattern.setMnemonic(Language.getString("P").charAt(0));
        bPattern.setText(Language.getString("Pattern"));
        bPattern.addActionListener(new ColorBandEditor_bPattern_actionAdapter(this));
        this.add(jTabbedPane1);
        jPanel1.add(jLabel2);
        jPanel1.add(tbStart);
        jPanel1.add(jLabel1);
        jPanel1.add(tbEnd);
        jPanel1.add(cbEnd);
        jPanel1.add(cbStart);
        jPanel1.add(bEnd);
        jPanel1.add(bStart);
        jTabbedPane1.add(jPanel1, Language.getString("Values"));
        jTabbedPane1.add(jPanel2, Language.getString("Options"));
        jPanel2.add(bGradient);
        jPanel2.add(cbBehind);
        jPanel2.add(bColor);
        jPanel2.add(spTransp);
        jPanel2.add(jLabel3);
        this.add(bPattern);
    }

    boolean setting;
    ColorBand colorBand;
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JTextField tbStart = new JTextField();
    JTextField tbEnd = new JTextField();
    ButtonPen bStart = new ButtonPen();
    ButtonPen bEnd = new ButtonPen();
    JCheckBox cbStart = new JCheckBox();
    JCheckBox cbEnd = new JCheckBox();
    JButton bGradient = new JButton();
    JCheckBox cbBehind = new JCheckBox();
    ButtonColor bColor = new ButtonColor();
    JLabel jLabel3 = new JLabel();
    JSpinner spTransp = new JSpinner();
    JButton bPattern = new JButton();

    public void spTransp_stateChanged(ChangeEvent e) {
        if (!setting)
          colorBand.setTransparency(((Integer)spTransp.getValue()).intValue());
    }



    public void cbBehind_actionPerformed(ActionEvent e) {
        if (!setting)
        colorBand.setDrawBehind(cbBehind.isSelected());
    }

    public void bPattern_actionPerformed(ActionEvent e) {
        BrushEditor.edit(colorBand.getBrush());
    }

    public void bGradient_actionPerformed(ActionEvent e) {
        GradientEditor.edit(this,colorBand.getGradient());
    }

    public void bColor_actionPerformed(ActionEvent e) {
        colorBand.setColor(bColor.getColor());
    }

    public void cbStart_actionPerformed(ActionEvent e) {
        colorBand.setResizeStart(cbStart.isSelected());
    }

    public void cbEnd_actionPerformed(ActionEvent e) {
        colorBand.setResizeEnd(cbEnd.isSelected());
    }

    public void tbStart_keyReleased(KeyEvent e) {
        double tmpval=0;
        tmpval=com.steema.teechart.misc.Utils.stringToDouble(tbStart.getText(),tmpval);
        colorBand.setStart(tmpval);
    }

    public void tbEnd_keyReleased(KeyEvent e) {
         double tmpval=0;
         tmpval=com.steema.teechart.misc.Utils.stringToDouble(tbEnd.getText(),tmpval);
         colorBand.setEnd(tmpval);
    }
}


class ColorBandEditor_cbEnd_actionAdapter implements ActionListener {
    private ColorBandEditor adaptee;
    ColorBandEditor_cbEnd_actionAdapter(ColorBandEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbEnd_actionPerformed(e);
    }
}


class ColorBandEditor_cbStart_actionAdapter implements ActionListener {
    private ColorBandEditor adaptee;
    ColorBandEditor_cbStart_actionAdapter(ColorBandEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbStart_actionPerformed(e);
    }
}


class ColorBandEditor_bColor_actionAdapter implements ActionListener {
    private ColorBandEditor adaptee;
    ColorBandEditor_bColor_actionAdapter(ColorBandEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bColor_actionPerformed(e);
    }
}


class ColorBandEditor_bGradient_actionAdapter implements ActionListener {
    private ColorBandEditor adaptee;
    ColorBandEditor_bGradient_actionAdapter(ColorBandEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bGradient_actionPerformed(e);
    }
}


class ColorBandEditor_bPattern_actionAdapter implements ActionListener {
    private ColorBandEditor adaptee;
    ColorBandEditor_bPattern_actionAdapter(ColorBandEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bPattern_actionPerformed(e);
    }
}


class ColorBandEditor_cbBehind_actionAdapter implements ActionListener {
    private ColorBandEditor adaptee;
    ColorBandEditor_cbBehind_actionAdapter(ColorBandEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbBehind_actionPerformed(e);
    }
}



class ColorBandEditor_tbEnd_keyAdapter extends KeyAdapter {
    private ColorBandEditor adaptee;
    ColorBandEditor_tbEnd_keyAdapter(ColorBandEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void keyReleased(KeyEvent e) {
        adaptee.tbEnd_keyReleased(e);
    }
}


class ColorBandEditor_tbStart_keyAdapter extends KeyAdapter {
    private ColorBandEditor adaptee;
    ColorBandEditor_tbStart_keyAdapter(ColorBandEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void keyReleased(KeyEvent e) {
        adaptee.tbStart_keyReleased(e);
    }
}


class ColorBandEditor_spTransp_changeAdapter implements ChangeListener {
    private ColorBandEditor adaptee;
    ColorBandEditor_spTransp_changeAdapter(ColorBandEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spTransp_stateChanged(e);
    }
}


