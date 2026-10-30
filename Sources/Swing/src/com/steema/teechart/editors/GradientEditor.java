package com.steema.teechart.editors;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Rectangle;
import java.awt.Dimension;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.steema.teechart.drawing.*;
import com.steema.teechart.drawing.Color;

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
final public class GradientEditor extends JPanel {


    private Gradient gradient;

    public GradientEditor(Gradient value) {
        this();
        refreshControls(value);
    }

    public GradientEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        cbDirection.addItem(Language.getString("Vertical"));
        cbDirection.addItem(Language.getString("Horizontal"));
        cbDirection.addItem(Language.getString("Forward_Diagonal"));
        cbDirection.addItem(Language.getString("Back_Diagonal"));
        cbDirection.addItem(Language.getString("Radial"));
    }

    private void jbInit() throws Exception {
        setSize(new Dimension(205, 156));
        cbVisible.setMnemonic(Language.getString("V").charAt(0));
        cbVisible.setText(Language.getString("Visible"));
        cbVisible.setBounds(new Rectangle(7, 1, 83, 23));
        cbVisible.addActionListener(new GradientEditor_cbVisible_actionAdapter(this));
        this.setLayout(null);
        bStartColor.setBounds(new Rectangle(8, 27, 77, 23));
        bStartColor.setMnemonic(Language.getString("S").charAt(0));
        bStartColor.addActionListener(new
                                      GradientEditor_bStartColor_actionAdapter(this));
        bMidColor.setBounds(new Rectangle(8, 56, 77, 23));
        bMidColor.setMnemonic(Language.getString("M").charAt(0));
        bMidColor.addActionListener(new GradientEditor_bMidColor_actionAdapter(this));
        bEndColor.setBounds(new Rectangle(8, 87, 77, 23));
        bEndColor.setMnemonic(Language.getString("E").charAt(0));
        bEndColor.addActionListener(new GradientEditor_bEndColor_actionAdapter(this));
        cbUseMid.setMnemonic(Language.getString("U").charAt(0));
        cbUseMid.setText(Language.getString("Use_Middle"));
        cbUseMid.setBounds(new Rectangle(105, 59, 89, 17));
        cbUseMid.addActionListener(new GradientEditor_cbUseMid_actionAdapter(this));
        jButton1.setBounds(new Rectangle(114, 27, 77, 23));
        jButton1.setMargin(new Insets(2, 1, 2, 1));
        jButton1.setMnemonic(Language.getString("W").charAt(0));
        jButton1.setText(Language.getString("Swap"));
        jButton1.addActionListener(new GradientEditor_jButton1_actionAdapter(this));
        cbDirection.setBounds(new Rectangle(93, 2, 95, 20));
        cbDirection.addActionListener(new
                                      GradientEditor_cbDirection_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("T").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel1.setLabelFor(spTransp);
        jLabel1.setText(Language.getString("Transparency"));
        jLabel1.setBounds(new Rectangle(8, 117, 84, 16));
        spTransp.setBounds(new Rectangle(103, 115, 49, 19));
        spTransp.addChangeListener(new GradientEditor_spTransp_changeAdapter(this));
        this.add(bStartColor, null);
        this.add(bMidColor, null);
        this.add(bEndColor, null);
        this.add(cbVisible, null);
        this.add(jLabel1);
        this.add(spTransp);
        this.add(jButton1);
        this.add(cbDirection);
        this.add(cbUseMid);
        this.setPreferredSize(new Dimension(190, 150));
    }

    JCheckBox cbVisible = new JCheckBox();
    ButtonColor bStartColor = new ButtonColor(Language.getString("Start"), Color.EMPTY);
    ButtonColor bMidColor = new ButtonColor(Language.getString("Middle"), Color.EMPTY);
    ButtonColor bEndColor = new ButtonColor(Language.getString("End"), Color.EMPTY);
    JCheckBox cbUseMid = new JCheckBox();
    JButton jButton1 = new JButton();
    JComboBox cbDirection = new JComboBox();
    JLabel jLabel1 = new JLabel();
    JSpinner spTransp = new JSpinner();

    public void cbVisible_actionPerformed(ActionEvent e) {
        gradient.setVisible(cbVisible.isSelected());
    }

    public void bStartColor_actionPerformed(ActionEvent e) {
        gradient.setStartColor(bStartColor.getColor());
    }

    /**
     * Edit
     *
     * @param owner Frame
     * @param gradient Gradient
     */
    public static void edit(Container owner, Gradient gradient) {
        JDialog d = new JDialog();
        d.setTitle(Language.getString("Gradient_Editor"));
        d.setModal(true);
        d.setSize(new Dimension(190, 180));
        GradientEditor g = new GradientEditor(gradient);
        d.getContentPane().add(g);



        d.setLocationRelativeTo(owner);
        d.setVisible(true);
    }

    public static void edit(Container owner, Gradient gradient, boolean onlyStart, boolean hideVisible) {
        JDialog d = new JDialog();
        d.setTitle(Language.getString("Gradient_Editor"));
        d.setModal(true);
        d.setSize(new Dimension(205, 180));
        GradientEditor g = new GradientEditor(gradient);
        if (onlyStart) {
            g.bEndColor.setVisible(false);
            g.jButton1.setVisible(false);
        }

        g.cbVisible.setVisible(!hideVisible);
        d.getContentPane().add(g);

        d.setLocationRelativeTo(owner);
        d.setVisible(true);
    }

    public void bMidColor_actionPerformed(ActionEvent e) {
        gradient.setMiddleColor(bMidColor.getColor());
    }

    public void bEndColor_actionPerformed(ActionEvent e) {
        gradient.setEndColor(bEndColor.getColor());
    }

    public void cbUseMid_actionPerformed(ActionEvent e) {
        gradient.setUseMiddle(cbUseMid.isSelected());
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        Color tmp = gradient.getStartColor();
        gradient.setStartColor(gradient.getEndColor());
        gradient.setEndColor(tmp);
        bStartColor.setColor(gradient.getStartColor());
        bEndColor.setColor(gradient.getEndColor());
    }

    public void cbDirection_actionPerformed(ActionEvent e) {
        if (gradient!=null) {
            switch (cbDirection.getSelectedIndex()) {
            case 0:
                gradient.setDirection(GradientDirection.VERTICAL);
                break;
            case 1:
                gradient.setDirection(GradientDirection.HORIZONTAL);
                break;
            case 2:
                gradient.setDirection(GradientDirection.FORWARDDIAGONAL);
                break;
            case 3:
                gradient.setDirection(GradientDirection.BACKDIAGONAL);
                break;
            default:
                gradient.setDirection(GradientDirection.RADIAL);
                break;
            }
        }
    }

    public void refreshControls(Gradient gradient) {
        this.gradient = gradient;
        cbVisible.setSelected(gradient.getVisible());
        bStartColor.setColor(gradient.getStartColor());
        bMidColor.setColor(gradient.getMiddleColor());
        bEndColor.setColor(gradient.getEndColor());
        cbUseMid.setSelected(gradient.getUseMiddle());
        cbDirection.setSelectedIndex(gradient.getDirection().getValue());
        spTransp.setValue(new Integer(gradient.getTransparency()));
    }

    public void spTransp_stateChanged(ChangeEvent e) {
        gradient.setTransparency(((Integer)spTransp.getValue()).intValue());
    }
}


final class GradientEditor_spTransp_changeAdapter implements ChangeListener {
    private GradientEditor adaptee;
    GradientEditor_spTransp_changeAdapter(GradientEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spTransp_stateChanged(e);
    }
}


final class GradientEditor_cbDirection_actionAdapter implements ActionListener {
    private GradientEditor adaptee;
    GradientEditor_cbDirection_actionAdapter(GradientEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbDirection_actionPerformed(e);
    }
}


final class GradientEditor_jButton1_actionAdapter implements ActionListener {
    private GradientEditor adaptee;
    GradientEditor_jButton1_actionAdapter(GradientEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}


final class GradientEditor_cbUseMid_actionAdapter implements ActionListener {
    private GradientEditor adaptee;
    GradientEditor_cbUseMid_actionAdapter(GradientEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbUseMid_actionPerformed(e);
    }
}


final class GradientEditor_bEndColor_actionAdapter implements ActionListener {
    private GradientEditor adaptee;
    GradientEditor_bEndColor_actionAdapter(GradientEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bEndColor_actionPerformed(e);
    }
}


final class GradientEditor_bMidColor_actionAdapter implements ActionListener {
    private GradientEditor adaptee;
    GradientEditor_bMidColor_actionAdapter(GradientEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bMidColor_actionPerformed(e);
    }
}


final class GradientEditor_bStartColor_actionAdapter implements ActionListener {
    private GradientEditor adaptee;
    GradientEditor_bStartColor_actionAdapter(GradientEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bStartColor_actionPerformed(e);
    }
}


final class GradientEditor_cbVisible_actionAdapter implements ActionListener {
    private GradientEditor adaptee;
    GradientEditor_cbVisible_actionAdapter(GradientEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbVisible_actionPerformed(e);
    }
}

