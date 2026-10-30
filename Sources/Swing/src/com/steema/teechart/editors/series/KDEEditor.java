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
import com.steema.teechart.editors.ButtonColor;
import com.steema.teechart.functions.KDE;
import com.steema.teechart.functions.KDEClamp;
import com.steema.teechart.functions.KDESides;
import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.CustomBeeSwarm;
import com.steema.teechart.styles.CustomViolin;
//import com.steema.teechart.styles.Violin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
public class KDEEditor extends BaseSeriesEditor {


    Series srcSeries;
    Boolean loading=false;
            
    public KDEEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        cbClamp.addItem(Language.getString("MINMAX"));
        cbClamp.addItem(Language.getString("MANUAL"));
        
        cbSides.addItem(Language.getString("LEFT"));
        cbSides.addItem(Language.getString("RIGHT"));
        cbSides.addItem(Language.getString("BOTH"));
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        
        srcSeries = series;
        
        kdeFunction = ((CustomViolin)series).getKdeFunction();
        
        boolean isBeeSwarm = CustomBeeSwarm.class.isAssignableFrom(series.getClass());

        loading = true;
        
        cbClamp.setSelectedIndex(kdeFunction.getOptions().getClamp().getValue());
        
        cbSides.setSelectedIndex(kdeFunction.getOptions().getSides().getValue());
        
        eClampMin.setText(String.valueOf(kdeFunction.getClampMin()));
        eClampMax.setText(String.valueOf(kdeFunction.getClampMax()));
        eScale.setText(String.valueOf(kdeFunction.getScale()));
        eResolution.setText(String.valueOf(kdeFunction.getOptions().getResolution()));
        eBandwidth.setText(String.valueOf(kdeFunction.getOptions().getBandwidth()));
        
        loading = false;
        
        this.add(jLabelClamp);
        this.add(cbClamp);
        this.add(jLabelClampMin);
        this.add(eClampMin);
        this.add(jLabelClampMax);
        this.add(eClampMax);
       
        if (!isBeeSwarm)
        {
            this.add(jLabelSides);
            this.add(cbSides);
            this.add(jLabelScale);
            this.add(eScale);
            this.add(jLabelResolution);
            this.add(eResolution);
            this.add(jLabelBandwidth);
            this.add(eBandwidth);
        }

    }

    private void jbInit() throws Exception {
        this.setLayout(null);

        jLabelClamp.setDisplayedMnemonic('0');
        jLabelClamp.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelClamp.setLabelFor(cbClamp);
        jLabelClamp.setText(Language.getString("Clamp"));
        jLabelClamp.setBounds(new Rectangle(250, 16, 80, 14));
        cbClamp.setBounds(new Rectangle(344, 14, 70, 20));
        cbClamp.addActionListener(new KDEEditor_cbClamp_actionAdapter(this));
        jLabelClampMin.setDisplayedMnemonic('0');
        jLabelClampMin.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelClampMin.setLabelFor(eClampMin);
        jLabelClampMin.setText(Language.getString("ClampMin"));
        jLabelClampMin.setBounds(new Rectangle(250, 46, 80, 14));
        eClampMin.setText("");
        eClampMin.setBounds(new Rectangle(344, 44, 70, 20));
        eClampMin.addKeyListener(new KDEEditor_eClampMin_keyAdapter(this));
        jLabelClampMax.setDisplayedMnemonic('0');
        jLabelClampMax.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelClampMax.setLabelFor(eClampMax);
        jLabelClampMax.setText(Language.getString("ClampMax"));
        jLabelClampMax.setBounds(new Rectangle(250, 76, 80, 14));
        eClampMax.setText("");
        eClampMax.setBounds(new Rectangle(344, 74, 70, 20));
        eClampMax.addKeyListener(new KDEEditor_eClampMax_keyAdapter(this));
        jLabelSides.setDisplayedMnemonic('0');
        jLabelSides.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSides.setLabelFor(cbSides);
        jLabelSides.setText(Language.getString("Sides"));
        jLabelSides.setBounds(new Rectangle(250, 106, 80, 14));
        cbSides.setBounds(new Rectangle(344, 104, 70, 20));
        cbSides.addActionListener(new KDEEditor_cbSides_actionAdapter(this));
        //cbStairs.addActionListener(new AreaEditor_cbSides_actionAdapter(this));
        jLabelScale.setDisplayedMnemonic('0');
        jLabelScale.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelScale.setLabelFor(eScale);
        jLabelScale.setText(Language.getString("Scale"));
        jLabelScale.setBounds(new Rectangle(250, 136, 80, 14));
        eScale.setText("");
        eScale.setBounds(new Rectangle(344, 134, 70, 20));
        eScale.addKeyListener(new KDEEditor_eScale_keyAdapter(this));
        jLabelResolution.setDisplayedMnemonic('0');
        jLabelResolution.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelResolution.setLabelFor(eResolution);
        jLabelResolution.setText(Language.getString("Resolution"));
        jLabelResolution.setBounds(new Rectangle(250, 166, 80, 14));
        eResolution.setText("");
        eResolution.setBounds(new Rectangle(344, 164, 70, 20));
        eResolution.addKeyListener(new KDEEditor_eResolution_keyAdapter(this));
        jLabelBandwidth.setDisplayedMnemonic('0');
        jLabelBandwidth.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelBandwidth.setLabelFor(eBandwidth);
        jLabelBandwidth.setText(Language.getString("Bandwidth"));
        jLabelBandwidth.setBounds(new Rectangle(250, 196, 80, 14));
        eBandwidth.setText("");
        eBandwidth.setBounds(new Rectangle(344, 194, 70, 20));
        eBandwidth.addKeyListener(new KDEEditor_eBandwidth_keyAdapter(this));
    }

    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("arrow"));
    private KDE kdeFunction;
    Border border3 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border4 = new TitledBorder(border3, Language.getString("Multiple_arrows"));
    Border border5 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border6 = new TitledBorder(border5, Language.getString("Color"));
    Border border7 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));

    
    JLabel jLabelClamp = new JLabel();
    JLabel jLabelClampMin = new JLabel();
    JLabel jLabelClampMax = new JLabel();
    JLabel jLabelSides = new JLabel();
    JLabel jLabelScale = new JLabel();
    JLabel jLabelResolution = new JLabel();
    JLabel jLabelBandwidth = new JLabel();
    
    JComboBox cbClamp = new JComboBox();
    JTextField eClampMin = new JTextField();
    JTextField eClampMax = new JTextField();
    JComboBox cbSides = new JComboBox();
    JTextField eScale = new JTextField();
    JTextField eResolution = new JTextField();
    JTextField eBandwidth = new JTextField();
    
    private void updateKDE()
    {
        if (!loading)
          ((CustomViolin)srcSeries).update();
    }

    public void eClampMin_keyReleased(KeyEvent e) {
        kdeFunction.setClampMin(com.steema.teechart.misc.Utils.stringToDouble(eClampMin.getText(), kdeFunction.getClampMin()));
        updateKDE();
    }

    void eClampMax_keyReleased(KeyEvent e) {
        kdeFunction.setClampMax(com.steema.teechart.misc.Utils.stringToDouble(eClampMax.getText(), kdeFunction.getClampMax()));
        updateKDE();
    }
    
    void cbSides_actionPerformed(ActionEvent e) {
        if (kdeFunction!=null) {
          kdeFunction.getOptions().setSides(KDESides.fromInt(
                    cbSides.getSelectedIndex()));
          
          updateKDE();
        }
    }

    void cbClamp_actionPerformed(ActionEvent e) {
        if (kdeFunction!=null) {
          kdeFunction.getOptions().setClamp(KDEClamp.fromInt(
                    cbClamp.getSelectedIndex()));
          
          updateKDE();
        }
    }

    void eBandwidth_keyReleased(KeyEvent e) {
        kdeFunction.getOptions().setBandwidth(Integer.decode(eBandwidth.getText()));
        updateKDE();
    }

    void eScale_keyReleased(KeyEvent e) {
        kdeFunction.setScale(com.steema.teechart.misc.Utils.stringToDouble(eScale.getText(), kdeFunction.getScale()));
        updateKDE();
    }

    void eResolution_keyReleased(KeyEvent e) {
        kdeFunction.getOptions().setResolution(Integer.decode(eResolution.getText()));
        updateKDE();
    }
}


class KDEEditor_eClampMin_keyAdapter extends KeyAdapter {
    private KDEEditor adaptee;
    KDEEditor_eClampMin_keyAdapter(KDEEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void keyReleased(KeyEvent e) {
        adaptee.eClampMin_keyReleased(e);
    }
}

class KDEEditor_eClampMax_keyAdapter extends KeyAdapter {
    private KDEEditor adaptee;
    KDEEditor_eClampMax_keyAdapter(KDEEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void keyReleased(KeyEvent e) {
        adaptee.eClampMax_keyReleased(e);
    }
}

class KDEEditor_eBandwidth_keyAdapter extends KeyAdapter {
    private KDEEditor adaptee;
    KDEEditor_eBandwidth_keyAdapter(KDEEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void keyReleased(KeyEvent e) {
        adaptee.eBandwidth_keyReleased(e);
    }
}

class KDEEditor_eScale_keyAdapter extends KeyAdapter {
    private KDEEditor adaptee;
    KDEEditor_eScale_keyAdapter(KDEEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void keyReleased(KeyEvent e) {
        adaptee.eScale_keyReleased(e);
    }
}

class KDEEditor_eResolution_keyAdapter extends KeyAdapter {
    private KDEEditor adaptee;
    KDEEditor_eResolution_keyAdapter(KDEEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void keyReleased(KeyEvent e) {
        adaptee.eResolution_keyReleased(e);
    }
}

final class KDEEditor_cbSides_actionAdapter implements ActionListener {
    private KDEEditor adaptee;
    KDEEditor_cbSides_actionAdapter(KDEEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbSides_actionPerformed(e);
    }
}

final class KDEEditor_cbClamp_actionAdapter implements ActionListener {
    private KDEEditor adaptee;
    KDEEditor_cbClamp_actionAdapter(KDEEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbClamp_actionPerformed(e);
    }
}



