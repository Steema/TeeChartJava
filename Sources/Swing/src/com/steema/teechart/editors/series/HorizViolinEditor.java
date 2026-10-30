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
import com.steema.teechart.styles.HorizViolin;
import com.steema.teechart.styles.Violin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * <p>Title: HorizViolinEditor class</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2021 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class HorizViolinEditor extends BaseSeriesEditor {

    private HorizViolin horizViolin;
    private KDEEditor kdeEditor;
    
    public HorizViolinEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        horizViolin = (HorizViolin) series;

        kdeEditor = new KDEEditor();
        this.add(kdeEditor);
        kdeEditor.setBounds(new Rectangle(-30, 0, 478, 423));
        kdeEditor.setSeries(horizViolin, tabs);
        
        //set buttons
        bBorder.setPen(horizViolin.getLinePen());
        bColor.setColor(horizViolin.getBrush().getColor());
        
        jsTransparency.setValue(horizViolin.getTransparency());
 
        ePos.setText(String.valueOf(horizViolin.getPosition()));
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
        bPattern.addActionListener(new HorizViolinEditor_bPattern_actionAdapter(this));
        
        bColor.setBounds(new Rectangle(12, 43, 84, 23));
        bColor.setMnemonic(Language.getString("C").charAt(0));
        bColor.setText(Language.getString("Color"));
        bColor.addActionListener(new
                                 HorizViolinEditor_buttonColor1_actionAdapter(this));
        
        jLabelTransp.setDisplayedMnemonic('0');
        jLabelTransp.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabelTransp.setLabelFor(jsTransparency);
        jLabelTransp.setText(Language.getString("Transparency"));
        jLabelTransp.setBounds(new Rectangle(96, 46, 80, 14));
        jsTransparency.setBounds(new Rectangle(186, 44, 50, 20));
        jsTransparency.addChangeListener(new HorizViolinEditor_jsTransparency_changeAdapter(this));        
        
        jLabel4.setDisplayedMnemonic('0');
        jLabel4.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel4.setLabelFor(ePos);
        jLabel4.setText(Language.getString("Position"));
        jLabel4.setBounds(new Rectangle(118, 24, 51, 14));
        ePos.setText("");
        ePos.setBounds(new Rectangle(177, 22, 53, 20));
        ePos.addKeyListener(new HorizViolinEditor_ePos_keyAdapter(this));
        
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
        horizViolin.setPosition(com.steema.teechart.misc.Utils.stringToDouble(ePos.getText(), horizViolin.getPosition()));
    }

    void buttonColor1_actionPerformed(ActionEvent e) {
        horizViolin.getBrush().setColor(bColor.getColor());
    }
    
    public void bPattern_actionPerformed(ActionEvent e) {
        BrushEditor.edit(horizViolin.getBrush());
    }
    
    public void jsTransparency_stateChanged(ChangeEvent e) {
        horizViolin.setTransparency(((Integer)jsTransparency.getValue()));
        horizViolin.getChart().invalidate();
    }
}

class HorizViolinEditor_ePos_keyAdapter extends KeyAdapter {
    private HorizViolinEditor adaptee;
    HorizViolinEditor_ePos_keyAdapter(HorizViolinEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void keyReleased(KeyEvent e) {
        adaptee.ePos_keyReleased(e);
    }
}


final class HorizViolinEditor_buttonColor1_actionAdapter implements ActionListener {
    private HorizViolinEditor adaptee;
    HorizViolinEditor_buttonColor1_actionAdapter(HorizViolinEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.buttonColor1_actionPerformed(e);
    }
}

final class HorizViolinEditor_bPattern_actionAdapter implements ActionListener {
    private HorizViolinEditor adaptee;
    HorizViolinEditor_bPattern_actionAdapter(HorizViolinEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bPattern_actionPerformed(e);
    }
}

class HorizViolinEditor_jsTransparency_changeAdapter implements ChangeListener {
    private HorizViolinEditor adaptee;
    HorizViolinEditor_jsTransparency_changeAdapter(HorizViolinEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jsTransparency_stateChanged(e);
    }
}