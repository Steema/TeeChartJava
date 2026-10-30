package com.steema.teechart.editors.series;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.CustomBox;

import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.editors.BrushEditor;
import com.steema.teechart.editors.ButtonColor;
import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.Violin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * <p>Title: ViolinEditor class</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2021 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ViolinEditor extends BaseSeriesEditor {

    private Violin violin;
    private KDEEditor kdeEditor;

    public ViolinEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        violin = (Violin) series;
       
        //add KDE
        kdeEditor = new KDEEditor();
        this.add(kdeEditor);
        kdeEditor.setBounds(new Rectangle(-30, 0, 478, 423));
        kdeEditor.setSeries(violin, tabs);
        
        //set buttons
        bBorder.setPen(violin.getLinePen());
        bColor.setColor(violin.getBrush().getColor());
        
        jsTransparency.setValue(violin.getTransparency());
 
        ePos.setText(String.valueOf(violin.getPosition()));
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        
        bBorder.setBounds(new Rectangle(12, 12, 84, 23));
        bBorder.setMaximumSize(new Dimension(81, 23));
        bBorder.setMinimumSize(new Dimension(81, 23));
        bBorder.setPreferredSize(new Dimension(81, 23));
        bBorder.setMnemonic(Language.getString("M").charAt(0));
        bBorder.setText(Language.getString("Border"));
        
        bPattern.setBounds(new Rectangle(12, 72, 84, 23));
        bPattern.setActionCommand(Language.getString("Pattern"));
        bPattern.setMnemonic(Language.getString("P").charAt(0));
        bPattern.setText(Language.getString("Pattern"));       
        bPattern.addActionListener(new ViolinEditor_bPattern_actionAdapter(this));
        
        bColor.setBounds(new Rectangle(12, 43, 84, 23));
        bColor.setMnemonic(Language.getString("C").charAt(0));
        bColor.setText(Language.getString("Color"));
        bColor.addActionListener(new
                                 ViolinEditor_buttonColor1_actionAdapter(this));
        
        jLabelTransp.setDisplayedMnemonic('0');
        jLabelTransp.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabelTransp.setLabelFor(jsTransparency);
        jLabelTransp.setText(Language.getString("Transparency"));
        jLabelTransp.setBounds(new Rectangle(96, 46, 80, 14));
        jsTransparency.setBounds(new Rectangle(186, 44, 50, 20));
        jsTransparency.addChangeListener(new ViolinEditor_jsTransparency_changeAdapter(this));        
        
        jLabel4.setDisplayedMnemonic('0');
        jLabel4.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel4.setLabelFor(ePos);
        jLabel4.setText(Language.getString("Position"));
        jLabel4.setBounds(new Rectangle(118, 24, 51, 14));
        ePos.setText("");
        ePos.setBounds(new Rectangle(177, 22, 53, 20));
        ePos.addKeyListener(new ViolinEditor_ePos_keyAdapter(this));
        
        this.add(bBorder);
        this.add(bPattern);
        this.add(jsTransparency);
        this.add(jLabelTransp);
        
        //this.add(jLabel4);
        //this.add(ePos); //mm not active
        
        this.add(bColor);
    }
    
    
    ButtonColor bColor = new ButtonColor();
    ButtonPen bBorder = new ButtonPen();
    JButton bPattern = new JButton();    
    JSpinner jsTransparency = new JSpinner();
    JLabel jLabelTransp = new JLabel();

    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("arrow"));
    
    Border border3 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border4 = new TitledBorder(border3, Language.getString("Multiple_arrows"));
    Border border5 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border6 = new TitledBorder(border5, Language.getString("Color"));
    Border border7 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    
    JLabel jLabel4 = new JLabel();
    JTextField ePos = new JTextField();


    public void ePos_keyReleased(KeyEvent e) {
        violin.setPosition(com.steema.teechart.misc.Utils.stringToDouble(ePos.getText(), violin.getPosition()));
    }

    void buttonColor1_actionPerformed(ActionEvent e) {
        violin.getBrush().setColor(bColor.getColor());
    }
    
    public void bPattern_actionPerformed(ActionEvent e) {
        BrushEditor.edit(violin.getBrush());
    }
    
    public void jsTransparency_stateChanged(ChangeEvent e) {
        violin.setTransparency(((Integer)jsTransparency.getValue()));
        violin.getChart().invalidate();
    }
}

class ViolinEditor_ePos_keyAdapter extends KeyAdapter {
    private ViolinEditor adaptee;
    ViolinEditor_ePos_keyAdapter(ViolinEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void keyReleased(KeyEvent e) {
        adaptee.ePos_keyReleased(e);
    }
}


final class ViolinEditor_buttonColor1_actionAdapter implements ActionListener {
    private ViolinEditor adaptee;
    ViolinEditor_buttonColor1_actionAdapter(ViolinEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.buttonColor1_actionPerformed(e);
    }
}

final class ViolinEditor_bPattern_actionAdapter implements ActionListener {
    private ViolinEditor adaptee;
    ViolinEditor_bPattern_actionAdapter(ViolinEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bPattern_actionPerformed(e);
    }
}

class ViolinEditor_jsTransparency_changeAdapter implements ChangeListener {
    private ViolinEditor adaptee;
    ViolinEditor_jsTransparency_changeAdapter(ViolinEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jsTransparency_stateChanged(e);
    }
}