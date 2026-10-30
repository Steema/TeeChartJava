package com.steema.teechart.editors.tools;

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;

import com.steema.teechart.editors.tools.AxisToolEdit;
import com.steema.teechart.tools.Tool;
import com.steema.teechart.tools.ColorLine;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: ColorLineEditor class</p>
 *
 * <p>Description: ColorLine Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ColorLineEditor extends AxisToolEdit {


    public ColorLineEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTool(Tool tool) {
        setting=true;
        super.setTool(tool);
        colorLine=(ColorLine)tool;
        tbValue.setText(String.valueOf(colorLine.getValue()));
        cb3D.setSelected(colorLine.getDraw3D());
        cbBehind.setSelected(colorLine.getDrawBehind());
        cbDrag.setSelected(colorLine.getAllowDrag());
        cbDragRepaint.setSelected(colorLine.getDragRepaint());
        cbNoLimit.setSelected(colorLine.getNoLimitDrag());
        setting=false;
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jLabel1.setDisplayedMnemonic(Language.getString("V").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(tbValue);
        jLabel1.setText(Language.getString("Value"));
        jLabel1.setBounds(new Rectangle(36, 89, 34, 15));
        tbValue.setBounds(new Rectangle(75, 86, 107, 20));
        tbValue.addKeyListener(new ColorLineEditor_tbValue_keyAdapter(this));
        cbDrag.setMnemonic(Language.getString("D").charAt(0));
        cbDrag.setText(Language.getString("Allow_Drag3"));
        cbDrag.setBounds(new Rectangle(75, 111, 116, 23));
        cbDrag.addActionListener(new ColorLineEditor_cbDrag_actionAdapter(this));
        cbDragRepaint.setMnemonic(Language.getString("R").charAt(0));
        cbDragRepaint.setText(Language.getString("Drag_Repaint"));
        cbDragRepaint.setBounds(new Rectangle(75, 136, 116, 23));
        cbDragRepaint.addActionListener(new
                ColorLineEditor_cbDragRepaint_actionAdapter(this));
        cbNoLimit.setMnemonic(Language.getString("L").charAt(0));
        cbNoLimit.setText(Language.getString("No_Limit_Drag"));
        cbNoLimit.setBounds(new Rectangle(75, 161, 116, 23));
        cbNoLimit.addActionListener(new ColorLineEditor_cbNoLimit_actionAdapter(this));
        cbBehind.setMnemonic(Language.getString("E").charAt(0));
        cbBehind.setText(Language.getString("Draw_Behind1"));
        cbBehind.setBounds(new Rectangle(75, 186, 116, 23));
        cbBehind.addActionListener(new ColorLineEditor_cbBehind_actionAdapter(this));
        cb3D.setMnemonic('3');
        cb3D.setText(Language.getString("Draw_3D"));
        cb3D.setBounds(new Rectangle(75, 211, 116, 23));
        cb3D.addActionListener(new ColorLineEditor_cb3D_actionAdapter(this));
        this.add(jLabel1);
        this.add(cbDragRepaint);
        this.add(cbDrag);
        this.add(tbValue);
        this.add(cbNoLimit);
        this.add(cbBehind);
        this.add(cb3D);
    }

    boolean setting;
    ColorLine colorLine;
    JLabel jLabel1 = new JLabel();
    JTextField tbValue = new JTextField();
    JCheckBox cbDrag = new JCheckBox();
    JCheckBox cbDragRepaint = new JCheckBox();
    JCheckBox cbNoLimit = new JCheckBox();
    JCheckBox cbBehind = new JCheckBox();
    JCheckBox cb3D = new JCheckBox();

    public void cbDrag_actionPerformed(ActionEvent e) {
        if (!setting)
          colorLine.setAllowDrag(cbDrag.isSelected());
    }

    public void cbDragRepaint_actionPerformed(ActionEvent e) {
        if (!setting)
          colorLine.setDragRepaint(cbDragRepaint.isSelected());
    }

    public void cbNoLimit_actionPerformed(ActionEvent e) {
        if (!setting)
          colorLine.setNoLimitDrag(cbNoLimit.isSelected());
    }

    public void cbBehind_actionPerformed(ActionEvent e) {
        if (!setting)
          colorLine.setDrawBehind(cbBehind.isSelected());
    }

    public void cb3D_actionPerformed(ActionEvent e) {
        if (!setting)
          colorLine.setDraw3D(cb3D.isSelected());
    }

    public void tbValue_keyReleased(KeyEvent e) {
        double tmpval=0;
        tmpval=com.steema.teechart.misc.Utils.stringToDouble(tbValue.getText(),tmpval);
        colorLine.setValue(tmpval);
    }
}


class ColorLineEditor_cb3D_actionAdapter implements ActionListener {
    private ColorLineEditor adaptee;
    ColorLineEditor_cb3D_actionAdapter(ColorLineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cb3D_actionPerformed(e);
    }
}


class ColorLineEditor_cbBehind_actionAdapter implements ActionListener {
    private ColorLineEditor adaptee;
    ColorLineEditor_cbBehind_actionAdapter(ColorLineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbBehind_actionPerformed(e);
    }
}


class ColorLineEditor_cbNoLimit_actionAdapter implements ActionListener {
    private ColorLineEditor adaptee;
    ColorLineEditor_cbNoLimit_actionAdapter(ColorLineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbNoLimit_actionPerformed(e);
    }
}


class ColorLineEditor_cbDragRepaint_actionAdapter implements ActionListener {
    private ColorLineEditor adaptee;
    ColorLineEditor_cbDragRepaint_actionAdapter(ColorLineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbDragRepaint_actionPerformed(e);
    }
}


class ColorLineEditor_cbDrag_actionAdapter implements ActionListener {
    private ColorLineEditor adaptee;
    ColorLineEditor_cbDrag_actionAdapter(ColorLineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbDrag_actionPerformed(e);
    }
}


class ColorLineEditor_tbValue_keyAdapter extends KeyAdapter {
    private ColorLineEditor adaptee;
    ColorLineEditor_tbValue_keyAdapter(ColorLineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void keyReleased(KeyEvent e) {
        adaptee.tbValue_keyReleased(e);
    }
}

