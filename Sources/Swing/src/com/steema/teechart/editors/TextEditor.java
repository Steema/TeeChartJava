package com.steema.teechart.editors;

import java.awt.*;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import java.awt.event.*;
import java.awt.Container;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JPanel;

import com.steema.teechart.editors.ShadowEditor;
import com.steema.teechart.editors.ChartFontEditor;
import com.steema.teechart.drawing.ChartFont;
import com.steema.teechart.languages.Language;


/**
 * <p>Title: TextEditor Class</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class TextEditor extends JPanel {

    private ChartFont font;
    private ShadowEditor shadowEditor;
    JPanel jPanel1 = new JPanel();
    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("Size"));
    JButton bFont = new JButton();
    JButton bFill = new JButton();
    Border border3 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border4 = new TitledBorder(border3, Language.getString("Shadow"));
    JLabel labelColor = new JLabel();
    Border border5 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border6 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));

    public TextEditor() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        setLayout(null);
        jPanel1.setBorder(border4);
        jPanel1.setBounds(new Rectangle( -1, 35, 415, 268));
        jPanel1.setLayout(null);
        bFont.setBounds(new Rectangle(8, 9, 71, 23));
        bFont.setMnemonic(Language.getString("O").charAt(0));
        bFont.setText(Language.getString("Font"));
        bFont.addActionListener(new TextEditor_bFont_actionAdapter(this));
        bFill.setBounds(new Rectangle(131, 9, 71, 23));
        bFill.setMnemonic(Language.getString("F").charAt(0));
        bFill.setText(Language.getString("Fill"));
        bFill.addActionListener(new TextEditor_bFill_actionAdapter(this));
        labelColor.setBorder(border6);
        labelColor.setBounds(new Rectangle(94, 10, 24, 20));
        labelColor.addMouseListener(new TextEditor_labelColor_mouseAdapter(this));
        this.add(jPanel1);
        this.add(bFill);
        this.add(bFont);
        this.add(labelColor);
    }

    public void bFont_actionPerformed(ActionEvent e) {
        ChartFontEditor.edit(this, font);
        SetLabelColor();
    }

    private void SetLabelColor()
    {
      labelColor.setBackground(font.getColor());
    }


    public TextEditor(Container parent,com.steema.teechart.drawing.ChartFont f)
    {
      super();
      updateDialog(f);
      shadowEditor=new ShadowEditor(font.getShadow());
      parent.add(this);
    }

    private void updateDialog(ChartFont f)
    {
         font=f;
         SetLabelColor();
    }

    void refreshControls(ChartFont f)
    {
        updateDialog(f);
        if (shadowEditor!=null)
        shadowEditor.refreshControls(font.getShadow());
     }

    public void labelColor_mouseClicked(MouseEvent e) {
        font.setColor(ColorEditor.choose(this,font.getColor()));
        SetLabelColor();
    }

    public void bFill_actionPerformed(ActionEvent e) {
        if (BrushEditor.edit(font.getBrush()))
        SetLabelColor();
    }
}


class TextEditor_bFill_actionAdapter implements ActionListener {
    private TextEditor adaptee;
    TextEditor_bFill_actionAdapter(TextEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bFill_actionPerformed(e);
    }
}


class TextEditor_labelColor_mouseAdapter extends MouseAdapter {
    private TextEditor adaptee;
    TextEditor_labelColor_mouseAdapter(TextEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void mouseClicked(MouseEvent e) {
        adaptee.labelColor_mouseClicked(e);
    }
}


class TextEditor_bFont_actionAdapter implements ActionListener {
    private TextEditor adaptee;
    TextEditor_bFont_actionAdapter(TextEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bFont_actionPerformed(e);
    }
}


