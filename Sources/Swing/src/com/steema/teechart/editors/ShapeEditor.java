package com.steema.teechart.editors;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.steema.teechart.TextShape;
import com.steema.teechart.TextShapeStyle;
import java.awt.Dimension;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: ShapeEditor class</p>
 *
 * <p>Description: Shape editor</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ShapeEditor extends JPanel {

    BorderLayout borderLayout1 = new BorderLayout();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    private TextShape shape;
    BorderLayout borderLayout2 = new BorderLayout();
    private GradientEditor gradient = new GradientEditor();
    private ShadowEditor shadow = new ShadowEditor();
    private ChartFontEditor fontEditor = new ChartFontEditor();
    private BevelEditor bevelEditor = new BevelEditor();
    BorderLayout borderLayout3 = new BorderLayout();
    BorderLayout borderLayout4 = new BorderLayout();
    ButtonColor buttonColor1 = new ButtonColor();
    ButtonPen buttonPen1 = new ButtonPen();
    JButton jButton1 = new JButton();
    JCheckBox cbRoundFrame = new JCheckBox();
    JCheckBox cbTransparent = new JCheckBox();
    JLabel jLabel1 = new JLabel();
    JSpinner spTransp = new JSpinner();
    JCheckBox cbVisible = new JCheckBox();
    JPanel jPanel5 = new JPanel();
    BorderLayout borderLayout5 = new BorderLayout();

    public ShapeEditor() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        jPanel2.add(fontEditor, BorderLayout.CENTER);
        jPanel3.add(gradient, BorderLayout.CENTER);
        jPanel4.add(shadow, BorderLayout.CENTER);
        jPanel5.add(bevelEditor, BorderLayout.CENTER);
    }

    public ShapeEditor(TextShape shape) {
        this();
        this.shape = shape;
        refreshControls(shape);
    }

    public ShapeEditor(JTabbedPane pane, TextShape shape) {
        this(shape);
        addTabsTo(pane);
    }

    public static void showModal(TextShape shape) {
        BaseEditorDialog.showModal(new ShapeEditor(shape));
    }

    public void setShape(TextShape shape) {
        this.shape = shape;
        refreshControls(shape);
    }

    void addTabsTo(JTabbedPane pane) {
        while (jTabbedPane1.getTabCount() > 0) {
            pane.add(jTabbedPane1.getComponentAt(0), jTabbedPane1.getTitleAt(0));
        }
    }

    public void refreshControls(TextShape shape) {
        this.shape = shape;

        cbVisible.setSelected(shape.getVisible());
        buttonColor1.setColor(shape.getColor());
        buttonPen1.setPen(shape.getPen());
        fontEditor.refreshControls(shape.getFont());
        gradient.refreshControls(shape.getGradient());
        shadow.refreshControls(shape.getShadow());
        bevelEditor.refreshControls(shape.getBevel());
        cbRoundFrame.setSelected(shape.getShapeStyle()==TextShapeStyle.ROUNDRECTANGLE);
        cbTransparent.setSelected(shape.getTransparent());
        spTransp.setValue(new Integer(shape.getTransparency()));
    }

    private void jbInit() throws Exception {
        setLayout(borderLayout1);
        jPanel3.setLayout(borderLayout2);
        jPanel2.setLayout(borderLayout3);
        jPanel4.setLayout(borderLayout4);
        buttonColor1.setBounds(new Rectangle(5, 30, 91, 23));
        buttonColor1.setMnemonic(Language.getString("C").charAt(0));
        buttonColor1.setText(Language.getString("Color"));
        buttonColor1.addActionListener(new
                                       ShapeEditor_buttonColor1_actionAdapter(this));
        jPanel1.setLayout(null);
        buttonPen1.setBounds(new Rectangle(5, 61, 91, 23));
        buttonPen1.setMnemonic(Language.getString("F").charAt(0));
        buttonPen1.setText(Language.getString("Frame"));
        jButton1.setBounds(new Rectangle(5, 92, 91, 23));
        jButton1.setMnemonic(Language.getString("P").charAt(0));
        jButton1.setText(Language.getString("Pattern"));
        jButton1.addActionListener(new ShapeEditor_jButton1_actionAdapter(this));
        cbRoundFrame.setMnemonic(Language.getString("R").charAt(0));
        cbRoundFrame.setText(Language.getString("Round_frame"));
        cbRoundFrame.setBounds(new Rectangle(129, 30, 126, 23));
        cbRoundFrame.addActionListener(new
                                       ShapeEditor_cbRoundFrame_actionAdapter(this));
        cbTransparent.setMnemonic(Language.getString("T").charAt(0));
        cbTransparent.setText(Language.getString("Transparent"));
        cbTransparent.setBounds(new Rectangle(129, 61, 128, 23));
        cbTransparent.addActionListener(new
                                        ShapeEditor_cbTransparent_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("A").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel1.setLabelFor(spTransp);
        jLabel1.setText(Language.getString("Transparency"));
        jLabel1.setBounds(new Rectangle(46, 129, 101, 14));
        spTransp.setBounds(new Rectangle(150, 126, 53, 20));
        spTransp.addChangeListener(new ShapeEditor_spTransp_changeAdapter(this));
        cbVisible.setMnemonic(Language.getString("V").charAt(0));
        cbVisible.setText(Language.getString("Visible"));
        cbVisible.setBounds(new Rectangle(5, 4, 108, 23));
        cbVisible.addActionListener(new ShapeEditor_cbVisible_actionAdapter(this));
        jPanel5.setLayout(borderLayout5);
        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.add(jPanel1, Language.getString("Format"));
        jPanel1.add(buttonColor1);
        jPanel1.add(buttonPen1);
        jPanel1.add(jButton1);
        jPanel1.add(cbVisible);
        jPanel1.add(cbRoundFrame);
        jPanel1.add(cbTransparent);
        jPanel1.add(spTransp);
        jPanel1.add(jLabel1);
        jTabbedPane1.add(jPanel2, Language.getString("Text"));
        jTabbedPane1.add(jPanel3, Language.getString("Gradient"));
        jTabbedPane1.add(jPanel4, Language.getString("Shadow"));
        jTabbedPane1.add(jPanel5, Language.getString("Bevels"));
        setPreferredSize(new Dimension(250,200));
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        BrushEditor.edit(shape.getBrush());
    }

    public void buttonColor1_actionPerformed(ActionEvent e) {
        shape.setColor(buttonColor1.getColor());
    }

    public void cbRoundFrame_actionPerformed(ActionEvent e) {
        shape.setShapeStyle(cbRoundFrame.isSelected() ? TextShapeStyle.ROUNDRECTANGLE : TextShapeStyle.RECTANGLE);
    }

    public void cbTransparent_actionPerformed(ActionEvent e) {
        shape.setTransparent(cbTransparent.isSelected());
    }

    public void spTransp_stateChanged(ChangeEvent e) {
        shape.setTransparency(((Integer)spTransp.getValue()).intValue());
    }

    public void cbVisible_actionPerformed(ActionEvent e) {
        shape.setVisible(cbVisible.isSelected());
    }
}


final class ShapeEditor_cbVisible_actionAdapter implements ActionListener {
    private ShapeEditor adaptee;
    ShapeEditor_cbVisible_actionAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbVisible_actionPerformed(e);
    }
}


final class ShapeEditor_spTransp_changeAdapter implements ChangeListener {
    private ShapeEditor adaptee;
    ShapeEditor_spTransp_changeAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spTransp_stateChanged(e);
    }
}


final class ShapeEditor_cbTransparent_actionAdapter implements ActionListener {
    private ShapeEditor adaptee;
    ShapeEditor_cbTransparent_actionAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbTransparent_actionPerformed(e);
    }
}


final class ShapeEditor_cbRoundFrame_actionAdapter implements ActionListener {
    private ShapeEditor adaptee;
    ShapeEditor_cbRoundFrame_actionAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbRoundFrame_actionPerformed(e);
    }
}


final class ShapeEditor_buttonColor1_actionAdapter implements ActionListener {
    private ShapeEditor adaptee;
    ShapeEditor_buttonColor1_actionAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.buttonColor1_actionPerformed(e);
    }
}


final class ShapeEditor_jButton1_actionAdapter implements ActionListener {
    private ShapeEditor adaptee;
    ShapeEditor_jButton1_actionAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}

