package com.steema.teechart.editors.tools;

// TODO

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.tools.Tool;
import com.steema.teechart.editors.BaseToolsEditor;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: ScrollBarEditor class</p>
 *
 * <p>Description:  ScrollBar Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

public class ScrollBarEditor extends BaseToolsEditor {


    public ScrollBarEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        cbBevel.addItem(Language.getString("None"));
        cbBevel.addItem(Language.getString("Lowered"));
        cbBevel.addItem(Language.getString("Raised"));
    }

/*
     static public boolean edit(com.steema.teechart.tools.LegendScrollBar lsb) {
            ScrollBarEditor dialog = new ScrollBarEditor();
            dialog.setTool(lsb);

           return dialog.showDialog()==DialogResult.OK;
    }
*/

    public void setTool(Tool tool) {
/*
        legendScrollBar=(LegendScrollBar)tool;

        if(legendScrollBar!=null) {
                bPen.setPen(legendScrollBar.getPen());
                switch(legendScrollBar.getBevel().getOuter()) {
                        case BevelStyles.NONE:
                                cbBevel.setSelectedIndex(0);
                                break;
                        case BevelStyles.LOWERED:
                                cbBevel.setSelectedIndex(1);
                                break;
                        case BevelStyles.RAISED:
                                cbBevel.setSelectedIndex(2);
                                break;
                }
                cbAutoRepeat.setSelected(legendScrollBar.getAutoRepeat());
                spSize.setValue((decimal)legendScrollBar.getSize());
                spDelay.setValue((decimal)legendScrollBar.getInitialDelay());
        }
*/
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        bPen.setBounds(new Rectangle(14, 11, 78, 23));
        bPen.setMnemonic(Language.getString("B").charAt(0));
        bPen.setText(Language.getString("Border1"));
        bPen.addActionListener(new ScrollBarEditor_bPen_actionAdapter(this));
        bBack.setBounds(new Rectangle(15, 39, 78, 23));
        bBack.setMnemonic(Language.getString("A").charAt(0));
        bBack.setText(Language.getString("Back_"));
        bBack.addActionListener(new ScrollBarEditor_bBack_actionAdapter(this));
        bThumb.setBounds(new Rectangle(126, 11, 78, 23));
        bThumb.setMnemonic(Language.getString("T").charAt(0));
        bThumb.setText(Language.getString("Thumb_"));
        bThumb.addActionListener(new ScrollBarEditor_bThumb_actionAdapter(this));
        bArrows.setBounds(new Rectangle(126, 39, 78, 23));
        bArrows.setMnemonic(Language.getString("R").charAt(0));
        bArrows.setText(Language.getString("Arrows_"));
        bArrows.addActionListener(new ScrollBarEditor_bArrows_actionAdapter(this));
        bGradient.setBounds(new Rectangle(126, 68, 78, 23));
        bGradient.setMnemonic(Language.getString("G").charAt(0));
        bGradient.setText(Language.getString("Gradient"));
        bGradient.addActionListener(new ScrollBarEditor_bGradient_actionAdapter(this));
        jButton5.setText(Language.getString("jButton5"));
        cbAutoRepeat.setMnemonic(Language.getString("U").charAt(0));
        cbAutoRepeat.setText(Language.getString("Auto_Repeat"));
        cbAutoRepeat.setBounds(new Rectangle(15, 186, 121, 23));
        cbAutoRepeat.addActionListener(new
                ScrollBarEditor_cbAutoRepeat_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("E").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(cbBevel);
        jLabel1.setText(Language.getString("Bevel_"));
        jLabel1.setBounds(new Rectangle(57, 105, 42, 15));
        cbBevel.setBounds(new Rectangle(112, 102, 92, 21));
        cbBevel.addActionListener(new ScrollBarEditor_cbBevel_actionAdapter(this));
        spSize.setBounds(new Rectangle(161, 129, 43, 21));
        spSize.addChangeListener(new ScrollBarEditor_spSize_changeAdapter(this));
        spDelay.setBounds(new Rectangle(145, 156, 59, 21));
        spDelay.addChangeListener(new ScrollBarEditor_spDelay_changeAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("Z").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setLabelFor(spSize);
        jLabel2.setText(Language.getString("Size"));
        jLabel2.setBounds(new Rectangle(121, 132, 34, 15));
        jLabel3.setDisplayedMnemonic(Language.getString("D").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setLabelFor(spDelay);
        jLabel3.setText(Language.getString("Initial_Delay_"));
        jLabel3.setBounds(new Rectangle(67, 159, 70, 15));
        this.add(bPen);
        this.add(bBack);
        this.add(jLabel1);
        this.add(spSize);
        this.add(spDelay);
        this.add(jLabel2);
        this.add(jLabel3);
        this.add(bArrows);
        this.add(bGradient);
        this.add(bThumb);
        this.add(cbBevel);
        this.add(cbAutoRepeat);
    }

//  LegendScrollBar legendScrollBar;
    ButtonPen bPen = new ButtonPen();
    JButton bBack = new JButton();
    JButton bThumb = new JButton();
    JButton bArrows = new JButton();
    JButton bGradient = new JButton();
    JButton jButton5 = new JButton();
    JCheckBox cbAutoRepeat = new JCheckBox();
    JLabel jLabel1 = new JLabel();
    JComboBox cbBevel = new JComboBox();
    JSpinner spSize = new JSpinner();
    JSpinner spDelay = new JSpinner();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();

    public void bThumb_actionPerformed(ActionEvent e) {
//       com.steema.teechart.editors.BrushEditor.edit(legendScrollBar.getThumbBrush());
//       legendScrollBar.Invalidate();
    }

    public void bBack_actionPerformed(ActionEvent e) {
//         com.steema.teechart.editors.BrushEditor.edit(legendScrollBar.getBrush());
//         legendScrollBar.Invalidate();
    }

    public void bArrows_actionPerformed(ActionEvent e) {
//         com.steema.teechart.editors.BrushEditor.edit(legendScrollBar.getArrowBrush());
//        legendScrollBar.Invalidate();
    }

    public void bGradient_actionPerformed(ActionEvent e) {
//         com.steema.teechart.editors.GradientEditor.edit(legendScrollBar.Gradient);
//         legendScrollBar.Invalidate();
    }

    public void cbBevel_actionPerformed(ActionEvent e) {
/*         switch(cbBevel.getSelectedIndex()) {
                case 0:
                        legendScrollBar.getBevel().setOuter() = BevelStyles.NONE;
                        break;
                case 1:
                        legendScrollBar.getBevel().setOuter() = BevelStyles.LOWERED;
                        break;
                case 2:
                        legendScrollBar.getBevel().setOuter() = BevelStyles.RAISED;
                        break;
        }
        legendScrollBar.Invalidate();
*/
    }

    public void spSize_stateChanged(ChangeEvent e) {
//         legendScrollBar.setSize(((Integer)spSize.getValue()).intValue());
    }

    public void spDelay_stateChanged(ChangeEvent e) {
//         legendScrollBar.setInitialDelay(((Integer)spDelay.getValue()).intValue());
    }

    public void cbAutoRepeat_actionPerformed(ActionEvent e) {
//         legendScrollBar.setAutoRepeat(cbAutoRepeat.isSelected());
    }

    public void bPen_actionPerformed(ActionEvent e) {
//        legendScrollBar.Invalidate();
    }
}


final class ScrollBarEditor_bPen_actionAdapter implements ActionListener {
    private ScrollBarEditor adaptee;
    ScrollBarEditor_bPen_actionAdapter(ScrollBarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bPen_actionPerformed(e);
    }
}


final class ScrollBarEditor_cbAutoRepeat_actionAdapter implements ActionListener {
    private ScrollBarEditor adaptee;
    ScrollBarEditor_cbAutoRepeat_actionAdapter(ScrollBarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAutoRepeat_actionPerformed(e);
    }
}


final class ScrollBarEditor_spDelay_changeAdapter implements ChangeListener {
    private ScrollBarEditor adaptee;
    ScrollBarEditor_spDelay_changeAdapter(ScrollBarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spDelay_stateChanged(e);
    }
}


final class ScrollBarEditor_spSize_changeAdapter implements ChangeListener {
    private ScrollBarEditor adaptee;
    ScrollBarEditor_spSize_changeAdapter(ScrollBarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spSize_stateChanged(e);
    }
}


final class ScrollBarEditor_cbBevel_actionAdapter implements ActionListener {
    private ScrollBarEditor adaptee;
    ScrollBarEditor_cbBevel_actionAdapter(ScrollBarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbBevel_actionPerformed(e);
    }
}


final class ScrollBarEditor_bGradient_actionAdapter implements ActionListener {
    private ScrollBarEditor adaptee;
    ScrollBarEditor_bGradient_actionAdapter(ScrollBarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bGradient_actionPerformed(e);
    }
}


final class ScrollBarEditor_bArrows_actionAdapter implements ActionListener {
    private ScrollBarEditor adaptee;
    ScrollBarEditor_bArrows_actionAdapter(ScrollBarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bArrows_actionPerformed(e);
    }
}


final class ScrollBarEditor_bBack_actionAdapter implements ActionListener {
    private ScrollBarEditor adaptee;
    ScrollBarEditor_bBack_actionAdapter(ScrollBarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bBack_actionPerformed(e);
    }
}


final class ScrollBarEditor_bThumb_actionAdapter implements ActionListener {
    private ScrollBarEditor adaptee;
    ScrollBarEditor_bThumb_actionAdapter(ScrollBarEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bThumb_actionPerformed(e);
    }
}

