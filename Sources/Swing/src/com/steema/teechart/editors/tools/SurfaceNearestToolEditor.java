package com.steema.teechart.editors.tools;

// TODO ..

import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import com.steema.teechart.editors.tools.ToolSeriesEditor;
import com.steema.teechart.editors.ButtonColor;
import com.steema.teechart.tools.Tool;
import com.steema.teechart.languages.Language;

// import com.steema.teechart.tools.SurfaceNearest;

/**
 * <p>Title: SurfaceNearestToolEditor class</p>
 *
 * <p>Description:  Surface Nearest Tool Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

public class SurfaceNearestToolEditor extends ToolSeriesEditor {


    public SurfaceNearestToolEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void setTool(Tool tool) {
        /*        surfaceNearest=(SurfaceNearest)tool;
       SetTool(SurfaceNearest, typeof(Steema.TeeChart.Styles.Surface));

        buttonColor1.setColor(surfaceNearest.getCellColor());
        buttonColor2.setColor(surfaceNearest.getRowColor());
        buttonColor3.setColor(surfaceNearest.getColumnColor());

        checkBox1.setSelected(surfaceNearest.getCellColor()==Color.Red);
        checkBox2.setSelected(surfaceNearest.RowColor==Color.Blue);
        checkBox3.setSelected(surfaceNearest.ColumnColor==Color.Green);

        checkBox4.setSelected(surfaceNearest.getCellColor()==Color.Transparent);
        checkBox5.setSelected(surfaceNearest.getRowColor()==Color.Transparent);
        checkBox6.setSelected(surfaceNearest.getColumnColor()==Color.Transparent);
        Editormisc.Utils.Translate(this);
*/
    }


    private void jbInit() throws Exception {
        this.setLayout(null);
        buttonColor1.setBounds(new Rectangle(32, 57, 75, 23));
        buttonColor1.setMnemonic(Language.getString("C").charAt(0));
        buttonColor1.setText(Language.getString("Cell_"));
        buttonColor1.addActionListener(new
                SurfaceNearestToolEditor_buttonColor1_actionAdapter(this));
        buttonColor2.setBounds(new Rectangle(32, 88, 75, 23));
        buttonColor2.setMnemonic(Language.getString("R").charAt(0));
        buttonColor2.setText(Language.getString("Row_"));
        buttonColor2.addActionListener(new
                SurfaceNearestToolEditor_buttonColor2_actionAdapter(this));
        buttonColor3.setBounds(new Rectangle(32, 119, 75, 23));
        buttonColor3.setMnemonic(Language.getString("U").charAt(0));
        buttonColor3.setText(Language.getString("Column_"));
        buttonColor3.addActionListener(new
                SurfaceNearestToolEditor_buttonColor3_actionAdapter(this));
        checkBox1.setMnemonic(Language.getString("D").charAt(0));
        checkBox1.setText(Language.getString("Default"));
        checkBox1.setBounds(new Rectangle(127, 57, 75, 23));
        checkBox1.addActionListener(new
                SurfaceNearestToolEditor_checkBox1_actionAdapter(this));
        checkBox2.setMnemonic(Language.getString("F").charAt(0));
        checkBox2.setText(Language.getString("Default"));
        checkBox2.setBounds(new Rectangle(127, 88, 83, 23));
        checkBox2.addActionListener(new
                SurfaceNearestToolEditor_checkBox2_actionAdapter(this));
        checkBox3.setMnemonic(Language.getString("A").charAt(0));
        checkBox3.setText(Language.getString("Default"));
        checkBox3.setBounds(new Rectangle(127, 119, 83, 23));
        checkBox3.addActionListener(new
                SurfaceNearestToolEditor_checkBox3_actionAdapter(this));
        checkBox4.setMnemonic(Language.getString("N").charAt(0));
        checkBox4.setText(Language.getString("None"));
        checkBox4.setBounds(new Rectangle(229, 57, 83, 23));
        checkBox4.addActionListener(new
                SurfaceNearestToolEditor_checkBox4_actionAdapter(this));
        checkBox5.setMnemonic(Language.getString("E").charAt(0));
        checkBox5.setText(Language.getString("None"));
        checkBox5.setBounds(new Rectangle(229, 88, 83, 23));
        checkBox5.addActionListener(new
                SurfaceNearestToolEditor_checkBox5_actionAdapter(this));
        checkBox6.setMnemonic(Language.getString("O").charAt(0));
        checkBox6.setText(Language.getString("None"));
        checkBox6.setBounds(new Rectangle(229, 119, 83, 23));
        checkBox6.addActionListener(new
                SurfaceNearestToolEditor_checkBox6_actionAdapter(this));
        this.add(buttonColor1);
        this.add(buttonColor2);
        this.add(buttonColor3);
        this.add(checkBox1);
        this.add(checkBox2);
        this.add(checkBox3);
        this.add(checkBox4);
        this.add(checkBox5);
        this.add(checkBox6);
    }

//   SurfaceNearest surfaceNearest;
    ButtonColor buttonColor1 = new ButtonColor();
    ButtonColor buttonColor2 = new ButtonColor();
    ButtonColor buttonColor3 = new ButtonColor();
    JCheckBox checkBox1 = new JCheckBox();
    JCheckBox checkBox2 = new JCheckBox();
    JCheckBox checkBox3 = new JCheckBox();
    JCheckBox checkBox4 = new JCheckBox();
    JCheckBox checkBox5 = new JCheckBox();
    JCheckBox checkBox6 = new JCheckBox();

    public void buttonColor1_actionPerformed(ActionEvent e) {
        //        surfaceNearest.setCellColor(buttonColor1.getColor());
        checkBox1.setSelected(false);
    }

    public void buttonColor2_actionPerformed(ActionEvent e) {
        //        surfaceNearest.setRowColor(buttonColor2.getColor());
        checkBox2.setSelected(false);
    }

    public void buttonColor3_actionPerformed(ActionEvent e) {
 //       surfaceNearest.setColumnColor(buttonColor3.getColor());
        checkBox3.setSelected(false);
    }

    public void checkBox1_actionPerformed(ActionEvent e) {
 //        surfaceNearest.setCellColor(Color.Red);
        buttonColor1.setEnabled(false);
    }

    public void checkBox2_actionPerformed(ActionEvent e) {
 //       surfaceNearest.setRowColor(Color.Blue);
        buttonColor2.setEnabled(false);
    }

    public void checkBox3_actionPerformed(ActionEvent e) {
 //       surfaceNearest.setColumnColor(Color.Green);
        buttonColor3.setEnabled(false);
    }

    public void checkBox4_actionPerformed(ActionEvent e) {
 //       surfaceNearest.setCellColor(Color.Transparent);
    }

    public void checkBox5_actionPerformed(ActionEvent e) {
 //       surfaceNearest.setRowColor(Color.Transparent);
    }

    public void checkBox6_actionPerformed(ActionEvent e) {
        //        surfaceNearest.setColumnColor(Color.Transparent);
    }
}


class SurfaceNearestToolEditor_checkBox6_actionAdapter implements
        ActionListener {
    private SurfaceNearestToolEditor adaptee;
    SurfaceNearestToolEditor_checkBox6_actionAdapter(SurfaceNearestToolEditor
            adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.checkBox6_actionPerformed(e);
    }
}


class SurfaceNearestToolEditor_checkBox5_actionAdapter implements
        ActionListener {
    private SurfaceNearestToolEditor adaptee;
    SurfaceNearestToolEditor_checkBox5_actionAdapter(SurfaceNearestToolEditor
            adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.checkBox5_actionPerformed(e);
    }
}


class SurfaceNearestToolEditor_checkBox4_actionAdapter implements
        ActionListener {
    private SurfaceNearestToolEditor adaptee;
    SurfaceNearestToolEditor_checkBox4_actionAdapter(SurfaceNearestToolEditor
            adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.checkBox4_actionPerformed(e);
    }
}


class SurfaceNearestToolEditor_checkBox3_actionAdapter implements
        ActionListener {
    private SurfaceNearestToolEditor adaptee;
    SurfaceNearestToolEditor_checkBox3_actionAdapter(SurfaceNearestToolEditor
            adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.checkBox3_actionPerformed(e);
    }
}


class SurfaceNearestToolEditor_checkBox2_actionAdapter implements
        ActionListener {
    private SurfaceNearestToolEditor adaptee;
    SurfaceNearestToolEditor_checkBox2_actionAdapter(SurfaceNearestToolEditor
            adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.checkBox2_actionPerformed(e);
    }
}


class SurfaceNearestToolEditor_checkBox1_actionAdapter implements
        ActionListener {
    private SurfaceNearestToolEditor adaptee;
    SurfaceNearestToolEditor_checkBox1_actionAdapter(SurfaceNearestToolEditor
            adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.checkBox1_actionPerformed(e);
    }
}


class SurfaceNearestToolEditor_buttonColor3_actionAdapter implements
        ActionListener {
    private SurfaceNearestToolEditor adaptee;
    SurfaceNearestToolEditor_buttonColor3_actionAdapter(
            SurfaceNearestToolEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.buttonColor3_actionPerformed(e);
    }
}


class SurfaceNearestToolEditor_buttonColor2_actionAdapter implements
        ActionListener {
    private SurfaceNearestToolEditor adaptee;
    SurfaceNearestToolEditor_buttonColor2_actionAdapter(
            SurfaceNearestToolEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.buttonColor2_actionPerformed(e);
    }
}


class SurfaceNearestToolEditor_buttonColor1_actionAdapter implements
        ActionListener {
    private SurfaceNearestToolEditor adaptee;
    SurfaceNearestToolEditor_buttonColor1_actionAdapter(
            SurfaceNearestToolEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.buttonColor1_actionPerformed(e);
    }
}


