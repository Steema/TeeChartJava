package com.steema.teechart.editors;

import javax.swing.*;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.steema.teechart.Wall;
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
public class WallEditor extends JPanel {


    public WallEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public WallEditor(Wall wall) {
        this();
        this.wall=wall;
        bColor.setColor(wall.getColor());
        bBorder.setPen(wall.getPen());
        cbVisible.setSelected(wall.getVisible());
        cbTransp.setSelected(wall.getTransparent());
        cbDark.setSelected(wall.getApplyDark());
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        bColor.setBounds(new Rectangle(9, 14, 91, 23));
        bColor.setMnemonic(Language.getString("C").charAt(0));
        bColor.setText(Language.getString("Color"));
        bColor.addActionListener(new WallEditor_bColor_actionAdapter(this));
        cbVisible.setMnemonic(Language.getString("V").charAt(0));
        cbVisible.setText(Language.getString("Visible"));
        cbVisible.setBounds(new Rectangle(126, 13, 81, 23));
        cbVisible.addActionListener(new WallEditor_cbVisible_actionAdapter(this));
        bBorder.setBounds(new Rectangle(9, 46, 91, 23));
        bBorder.setMnemonic(Language.getString("B").charAt(0));
        bBorder.setText(Language.getString("Border"));
        jButton1.setBounds(new Rectangle(9, 115, 91, 23));
        jButton1.setMnemonic(Language.getString("G").charAt(0));
        jButton1.setText(Language.getString("Gradient"));
        jButton1.addActionListener(new WallEditor_jButton1_actionAdapter(this));
        cbTransp.setMnemonic(Language.getString("T").charAt(0));
        cbTransp.setText(Language.getString("Transparent"));
        cbTransp.setBounds(new Rectangle(126, 113, 100, 23));
        cbTransp.addActionListener(new WallEditor_cbTransp_actionAdapter(this));
        cbDark.setMnemonic(Language.getString("D").charAt(0));
        cbDark.setText(Language.getString("Dark_3D"));
        cbDark.setBounds(new Rectangle(126, 44, 81, 23));
        cbDark.addActionListener(new WallEditor_cbDark_actionAdapter(this));
        this.add(bColor);
        this.add(cbVisible);
        this.add(bBorder);
        this.add(jButton1);
        this.add(cbTransp);
        this.add(cbDark);
    }

    ButtonColor bColor = new ButtonColor();
    JCheckBox cbVisible = new JCheckBox();
    ButtonPen bBorder = new ButtonPen();
    private Wall wall;
    JButton jButton1 = new JButton();
    JCheckBox cbTransp = new JCheckBox();
    JCheckBox cbDark = new JCheckBox();

    public void cbVisible_actionPerformed(ActionEvent e) {
        wall.setVisible(cbVisible.isSelected());
    }

    public void bColor_actionPerformed(ActionEvent e) {
        wall.setColor(bColor.getColor());
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        GradientEditor.edit(getParent(),wall.getGradient());
    }

    public void cbTransp_actionPerformed(ActionEvent e) {
        wall.setTransparent(cbTransp.isSelected());
    }

    public void cbDark_actionPerformed(ActionEvent e) {
        wall.setApplyDark(cbDark.isSelected());
    }
}


class WallEditor_cbDark_actionAdapter implements ActionListener {
    private WallEditor adaptee;
    WallEditor_cbDark_actionAdapter(WallEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbDark_actionPerformed(e);
    }
}


class WallEditor_cbTransp_actionAdapter implements ActionListener {
    private WallEditor adaptee;
    WallEditor_cbTransp_actionAdapter(WallEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbTransp_actionPerformed(e);
    }
}


class WallEditor_jButton1_actionAdapter implements ActionListener {
    private WallEditor adaptee;
    WallEditor_jButton1_actionAdapter(WallEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}


class WallEditor_bColor_actionAdapter implements ActionListener {
    private WallEditor adaptee;
    WallEditor_bColor_actionAdapter(WallEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bColor_actionPerformed(e);
    }
}


class WallEditor_cbVisible_actionAdapter implements ActionListener {
    private WallEditor adaptee;
    WallEditor_cbVisible_actionAdapter(WallEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbVisible_actionPerformed(e);
    }
}

