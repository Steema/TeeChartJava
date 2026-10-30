package com.steema.teechart.editors;

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.steema.teechart.*;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: Bevel editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

public class BevelImageEditor extends JPanel {

    private ImageBevel ibevel;
    JLabel jLabel3 = new JLabel();
    JSpinner spWidth = new JSpinner();
    JCheckBox cbVisible = new JCheckBox();
    JButton bPen = new JButton();
    JButton bBrush = new JButton();

    public BevelImageEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public BevelImageEditor(ImageBevel b) {
            ibevel=b;
            try {
                jbInit();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            spWidth.setValue(new Integer(ibevel.getWidth()));
            cbVisible.setSelected(ibevel.getVisible());
    }


    static public void ShowModal(ImageBevel b) {
            BevelImageEditor.ShowModal(b);
    }

    public void spWidth_stateChanged(ChangeEvent e) {
        ibevel.setWidth(((Integer)spWidth.getValue()).intValue());
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jLabel3.setDisplayedMnemonic(Language.getString("W").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setLabelFor(spWidth);
        jLabel3.setText(Language.getString("Width"));
        jLabel3.setBounds(new Rectangle(8, 72, 43, 14));
        spWidth.setBounds(new Rectangle(57, 68, 45, 22));
        spWidth.addChangeListener(new BevelImageEditor_spWidth_changeAdapter(this));
        cbVisible.setMnemonic(Language.getString("V").charAt(0));
        cbVisible.setText(Language.getString("Visible"));
        cbVisible.setBounds(new Rectangle(1, 6, 95, 21));
        cbVisible.addActionListener(new
                                    BevelImageEditor_cbVisible_actionAdapter(this));
        bPen.setBounds(new Rectangle(4, 35, 70, 23));
        bPen.setMnemonic(Language.getString("P").charAt(0));
        bPen.setText(Language.getString("Pen"));
        bPen.addActionListener(new BevelImageEditor_bPen_actionAdapter(this));
        bBrush.setBounds(new Rectangle(76, 35, 70, 23));
        bBrush.setMnemonic(Language.getString("B").charAt(0));
        bBrush.setText(Language.getString("Brush"));
        bBrush.addActionListener(new BevelImageEditor_bBrush_actionAdapter(this));
        this.add(cbVisible);
        this.add(spWidth);
        this.add(bPen);
        this.add(jLabel3);
        this.add(bBrush);
        spWidth.setValue(new Integer(1));
    }

    public void bPen_actionPerformed(ActionEvent e) {
        ChartPenEditor.showDialog(null,ibevel.getPen());
    }

    public void bBrush_actionPerformed(ActionEvent e) {
        BrushEditor.edit(ibevel.getBrush());
    }

    public void cbVisible_actionPerformed(ActionEvent e) {
        ibevel.setVisible(cbVisible.isSelected());
    }

    public void doEnableControls(boolean value) {
        cbVisible.setEnabled(value);
        bPen.setEnabled(value);
        bBrush.setEnabled(value);
        spWidth.setEnabled(value);
        jLabel3.setEnabled(value);
    }
}


class BevelImageEditor_cbVisible_actionAdapter implements ActionListener {
    private BevelImageEditor adaptee;
    BevelImageEditor_cbVisible_actionAdapter(BevelImageEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbVisible_actionPerformed(e);
    }
}


class BevelImageEditor_bBrush_actionAdapter implements ActionListener {
    private BevelImageEditor adaptee;
    BevelImageEditor_bBrush_actionAdapter(BevelImageEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bBrush_actionPerformed(e);
    }
}


class BevelImageEditor_bPen_actionAdapter implements ActionListener {
    private BevelImageEditor adaptee;
    BevelImageEditor_bPen_actionAdapter(BevelImageEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bPen_actionPerformed(e);
    }
}


class BevelImageEditor_spWidth_changeAdapter implements ChangeListener {
    private BevelImageEditor adaptee;
    BevelImageEditor_spWidth_changeAdapter(BevelImageEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spWidth_stateChanged(e);
    }
}


