package com.steema.teechart.editors.series;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import com.steema.teechart.editors.*;
import com.steema.teechart.styles.*;
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
public class LineEditor extends BaseSeriesEditor {

    private Line line;
    ButtonPen bPen = new ButtonPen();
    ButtonColor bColor = new ButtonColor();
    JButton jButton1 = new JButton();
    ButtonPen bOutline = new ButtonPen();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    Border border1 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("Line_mode"));
    JCheckBox cbStairs = new JCheckBox();
    JCheckBox cbInverted = new JCheckBox();
    JCheckBox cbDark3D = new JCheckBox();
    JCheckBox cbEach = new JCheckBox();
    JCheckBox cbClickable = new JCheckBox();
    JCheckBox cbEachLine = new JCheckBox();
    JCheckBox cbSmoothed = new JCheckBox();
    JLabel jLabel1 = new JLabel();
    JSpinner spHeight3D = new JSpinner();
    JLabel jLabel2 = new JLabel();
    JComboBox cbStack = new JComboBox();
    JLabel jLabel3 = new JLabel();
    JSlider slTransp = new JSlider();
    //Border border3 = new TitledBorder(border1, Language.getString("TreatNulls"));
    JComboBox cbTreatNulls = new JComboBox();

    public LineEditor() {
    	setBorder(null);
    	   	
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        /*cbStack.setModel(new DefaultComboBoxModel(new String[] {"None", "Overlap", "Stack", "Stacked 100%"}));*/
    }

    /**
     * setSeries
     *
     * @param series Series
     * @param tabs JTabbedPane
     */
    public void setSeries(Series series, JTabbedPane tabs) {
        line=(Line)series;
        bPen.setPen(line.getLinePen());
        bColor.setColor(line.getColor());
        bOutline.setPen(line.getOutLine());

        cbStairs.setSelected(line.getStairs());
        cbInverted.setEnabled(cbStairs.isSelected());
        cbInverted.setSelected(line.getInvertedStairs());

        cbDark3D.setSelected(line.getDark3D());
        cbEach.setSelected(line.getColorEach());
        cbClickable.setSelected(line.getClickableLine());
        cbEachLine.setSelected(line.getColorEachLine());

        cbStack.setSelectedIndex(line.getStacked().getValue());
        slTransp.setValue(line.getTransparency());
        spHeight3D.setValue(new Integer(line.getLineHeight()));
        
        cbTreatNulls.setSelectedIndex(line.getTreatNulls().getValue());
        
        cbSmoothed.setSelected(line.getSmoothed());    

        tabs.add(new SeriesPointerEditor(line.getPointer()),Language.getString("Pointer"), 1);
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jPanel1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Line Mode:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        jPanel1.setBounds(new Rectangle(4, 3, 288, 74));
        jPanel1.setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Color", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setBounds(163, 79, 129, 80);
        add(panel);
        panel.setLayout(null);
        
        cbStack.addItem(Language.getString("None"));
        cbStack.addItem(Language.getString("Overlap"));
        cbStack.addItem(Language.getString("Stack"));
        cbStack.addItem(Language.getString("Stacked 100%"));
        
        cbTreatNulls.addItem(Language.getString("DontPaint"));
        cbTreatNulls.addItem(Language.getString("Skip"));
        cbTreatNulls.addItem(Language.getString("Ignore"));
        
        JLabel lblTreatNulls = new JLabel();
        lblTreatNulls.setText(Language.getString("TreatNulls"));
        lblTreatNulls.setHorizontalAlignment(SwingConstants.TRAILING);
        lblTreatNulls.setDisplayedMnemonic('K');
        lblTreatNulls.setBounds(new Rectangle(-30, 89, 86, 14));
        lblTreatNulls.setBounds(38, 15, 86, 14);
        
        //bColor = new ButtonColor();
        bColor.setText("Color");
        bColor.setMnemonic('C');
        bColor.setBounds(new Rectangle(11, 43, 78, 23));
        panel.add(bColor);
        panel.add(cbEach);
        
        cbStairs.setMnemonic(Language.getString("S").charAt(0));
        cbStairs.setText(Language.getString("Stairs"));
        cbStairs.setBounds(new Rectangle(5, 15, 73, 23));
        cbStairs.addActionListener(new LineEditor_cbStairs_actionAdapter(this));
        cbInverted.setEnabled(false);
        cbInverted.setMnemonic(Language.getString("I").charAt(0));
        cbInverted.setText(Language.getString("Inverted"));
        cbInverted.setBounds(new Rectangle(5, 37, 81, 23));
        cbInverted.addActionListener(new LineEditor_cbInverted_actionAdapter(this));
        cbDark3D.setHorizontalAlignment(SwingConstants.RIGHT);
        cbDark3D.setMnemonic(Language.getString("D").charAt(0));
        cbDark3D.setText(Language.getString("Dark_3D"));
        cbDark3D.setBounds(new Rectangle(52, 136, 105, 23));
        cbDark3D.addActionListener(new LineEditor_cbDark3D_actionAdapter(this));
        cbEach.setMnemonic(Language.getString("E").charAt(0));
        cbEach.setText(Language.getString("Color_each"));
        cbEach.setBounds(new Rectangle(11, 15, 109, 23));
        cbEach.addActionListener(new LineEditor_cbEach_actionAdapter(this));
        cbClickable.setMnemonic(Language.getString("K").charAt(0));
        cbClickable.setText(Language.getString("Clickable"));
        cbClickable.setBounds(new Rectangle(13, 170, 81, 23));
        cbClickable.addActionListener(new LineEditor_cbClickable_actionAdapter(this));
        cbEachLine.setMnemonic(Language.getString("N").charAt(0));
        cbEachLine.setText(Language.getString("Color_each_line"));
        cbEachLine.setBounds(new Rectangle(13, 190, 105, 23));
        cbEachLine.addActionListener(new LineEditor_cbEachLine_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("H").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel1.setLabelFor(spHeight3D);
        jLabel1.setText(Language.getString("Height_3D"));
        jLabel1.setBounds(new Rectangle(19, 116, 84, 14));
        spHeight3D.setBounds(new Rectangle(105, 113, 52, 20));
        spHeight3D.addChangeListener(new LineEditor_spHeight3D_changeAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("K").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel2.setLabelFor(cbStack);
        jLabel2.setText(Language.getString("Stack"));
        jLabel2.setBounds(new Rectangle(-30, 89, 86, 14));
        cbStack.setBounds(new Rectangle(62, 85, 96, 22));
        cbStack.addActionListener(new LineEditor_cbStack_actionAdapter(this));
        jLabel3.setDisplayedMnemonic(Language.getString("T").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.LEFT);
        jLabel3.setLabelFor(slTransp);
        jLabel3.setText(Language.getString("Transparency"));
        jLabel3.setBounds(new Rectangle(181, 165, 86, 14));
        slTransp.setBounds(new Rectangle(177, 184, 110, 24));
        slTransp.addChangeListener(new LineEditor_slTransp_changeAdapter(this));
        jPanel2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        jPanel2.setBounds(new Rectangle(4, 213, 286, 42));
        jPanel2.setLayout(null);
        cbTreatNulls.setBounds(new Rectangle(141, 11, 100, 22));
        cbTreatNulls.addActionListener(new 
                LineEditor_cbTreatNulls_actionAdapter(this));
        
        cbSmoothed.setMnemonic(Language.getString("T").charAt(0));
        cbSmoothed.setText(Language.getString("Smoothed"));
        cbSmoothed.setBounds(new Rectangle(13, 150, 81, 23));
        cbSmoothed.addActionListener(new LineEditor_cbSmoothed_actionAdapter(this));  

        this.add(cbSmoothed);
        this.add(cbDark3D);
        this.add(cbEachLine);
        this.add(cbClickable);
        this.add(jPanel1);
        jPanel1.add(cbStairs);
        jPanel1.add(cbInverted);
        
        //JButton jButton1 = new JButton();
        jButton1.setText("Pattern...");
        jButton1.setMnemonic('P');
        jButton1.setBounds(new Rectangle(97, 45, 84, 23));
        jButton1.setBounds(97, 15, 84, 23);
        jPanel1.add(jButton1);
        jButton1.addActionListener(new LineEditor_jButton1_actionAdapter(this));
        
        //ButtonPen buttonPen = new ButtonPen();
        bPen.setText("Border");
        bPen.setMnemonic('B');
        bPen.setBounds(189, 15, 91, 23);
        jPanel1.add(bPen);
        
        //ButtonPen bOutline = new ButtonPen();
        bOutline.setText("Outline..");
        bOutline.setMnemonic('O');
        bOutline.setBounds(new Rectangle(319, 215, 88, 23));
        bOutline.setBounds(189, 43, 91, 23);
        jPanel1.add(bOutline);
        this.add(spHeight3D);
        this.add(jLabel1);
        this.add(jLabel2);
        this.add(cbStack);
        this.add(jLabel3);
        this.add(slTransp);
        this.add(jPanel2);
        jPanel2.add(lblTreatNulls);
        jPanel2.add(cbTreatNulls);
    }

    public void bColor_actionPerformed(ActionEvent e) {
        line.setColor(bColor.getColor());
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        BrushEditor.edit(this, line.getBrush());
    }

    public void cbStairs_actionPerformed(ActionEvent e) {
        line.setStairs(cbStairs.isSelected());
        cbInverted.setEnabled(cbStairs.isSelected());
    }

    public void cbInverted_actionPerformed(ActionEvent e) {
        line.setInvertedStairs(cbInverted.isSelected());
    }

    public void cbDark3D_actionPerformed(ActionEvent e) {
        line.setDark3D(cbDark3D.isSelected());
    }

    public void cbEach_actionPerformed(ActionEvent e) {
        line.setColorEach(cbEach.isSelected());
    }

    public void cbClickable_actionPerformed(ActionEvent e) {
        line.setClickableLine(cbClickable.isSelected());
    }

    public void cbEachLine_actionPerformed(ActionEvent e) {
        line.setColorEachLine(cbEachLine.isSelected());
    }

    public void cbStack_actionPerformed(ActionEvent e) {
        if (line!=null) {
            line.setStacked(CustomStack.fromInt(cbStack.getSelectedIndex()));
        }
    }
    
    public void cbTreatNulls_actionPerformed(ActionEvent e) {
        if (line!=null) {
            line.setTreatNulls(TreatNullsStyle.fromInt(
                    cbTreatNulls.getSelectedIndex()));
        }
    }
    
    public void cbSmoothed_actionPerformed(ActionEvent e) {
          line.setSmoothed(cbSmoothed.isSelected());
          line.SmoothSeries();
    }    

    public void slTransp_stateChanged(ChangeEvent e) {
        line.setTransparency(slTransp.getValue());
        line.repaint();
    }

    public void spHeight3D_stateChanged(ChangeEvent e) {
        line.setLineHeight(((Integer)spHeight3D.getValue()).intValue());
    }
}


final class LineEditor_spHeight3D_changeAdapter implements ChangeListener {
    private LineEditor adaptee;
    LineEditor_spHeight3D_changeAdapter(LineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spHeight3D_stateChanged(e);
    }
}


final class LineEditor_slTransp_changeAdapter implements ChangeListener {
    private LineEditor adaptee;
    LineEditor_slTransp_changeAdapter(LineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.slTransp_stateChanged(e);
    }
}


final class LineEditor_cbStack_actionAdapter implements ActionListener {
    private LineEditor adaptee;
    LineEditor_cbStack_actionAdapter(LineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbStack_actionPerformed(e);
    }
}


final class LineEditor_cbEachLine_actionAdapter implements ActionListener {
    private LineEditor adaptee;
    LineEditor_cbEachLine_actionAdapter(LineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbEachLine_actionPerformed(e);
    }
}


final class LineEditor_cbClickable_actionAdapter implements ActionListener {
    private LineEditor adaptee;
    LineEditor_cbClickable_actionAdapter(LineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbClickable_actionPerformed(e);
    }
}


final class LineEditor_cbEach_actionAdapter implements ActionListener {
    private LineEditor adaptee;
    LineEditor_cbEach_actionAdapter(LineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbEach_actionPerformed(e);
    }
}

class LineEditor_cbSmoothed_actionAdapter implements ActionListener {
    private LineEditor adaptee;
    LineEditor_cbSmoothed_actionAdapter(LineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbSmoothed_actionPerformed(e);
    }
}


final class LineEditor_cbDark3D_actionAdapter implements ActionListener {
    private LineEditor adaptee;
    LineEditor_cbDark3D_actionAdapter(LineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbDark3D_actionPerformed(e);
    }
}


final class LineEditor_cbInverted_actionAdapter implements ActionListener {
    private LineEditor adaptee;
    LineEditor_cbInverted_actionAdapter(LineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbInverted_actionPerformed(e);
    }
}


final class LineEditor_cbStairs_actionAdapter implements ActionListener {
    private LineEditor adaptee;
    LineEditor_cbStairs_actionAdapter(LineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbStairs_actionPerformed(e);
    }
}


final class LineEditor_jButton1_actionAdapter implements ActionListener {
    private LineEditor adaptee;
    LineEditor_jButton1_actionAdapter(LineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}


final class LineEditor_bColor_actionAdapter implements ActionListener {
    private LineEditor adaptee;
    LineEditor_bColor_actionAdapter(LineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bColor_actionPerformed(e);
    }
}

final class LineEditor_cbTreatNulls_actionAdapter implements ActionListener {
    private LineEditor adaptee;
    LineEditor_cbTreatNulls_actionAdapter(LineEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbTreatNulls_actionPerformed(e);
    }
}
