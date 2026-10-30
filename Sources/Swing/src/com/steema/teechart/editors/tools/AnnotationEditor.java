package com.steema.teechart.editors.tools;

import java.awt.Rectangle;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.Color;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.tools.Tool;
import com.steema.teechart.editors.ShapeEditor;
import com.steema.teechart.tools.Annotation;
import com.steema.teechart.drawing.StringAlignment;
import com.steema.teechart.tools.PageNumber;
import com.steema.teechart.editors.BaseToolsEditor;

import com.steema.teechart.editors.Utils;
import com.steema.teechart.languages.Language;
import javax.swing.border.EtchedBorder;

/**
 * <p>Title: AnnotationEditor class</p>
 *
 * <p>Description:  Annotation Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class AnnotationEditor extends BaseToolsEditor {


    public AnnotationEditor() {
        try {
            jbInit();

            cbTextAlign.addItem(Language.getString("Left"));
            cbTextAlign.addItem(Language.getString("Center"));
            cbTextAlign.addItem(Language.getString("Right"));

            cbPos.addItem(Language.getString("Left_Top"));
            cbPos.addItem(Language.getString("Left_Bottom"));
            cbPos.addItem(Language.getString("Right_Top"));
            cbPos.addItem(Language.getString("Right_Bottom"));

            cbHead.addItem(Language.getString("None"));
            cbHead.addItem(Language.getString("Line"));
            cbHead.addItem(Language.getString("Solid"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
         this.invalidate();
    }

    public void setTool(Tool tool) {
        atool=tool;
        annotation=(Annotation)tool;

       shapeForm=new ShapeEditor(jTabbedPane1,annotation.getShape());

        if (atool instanceof PageNumber)
          memoText.setText(((com.steema.teechart.tools.PageNumber)atool).getFormat());
        else
          memoText.setText(annotation.getText());

        if (annotation.getTextAlign()==StringAlignment.NEAR)
            cbTextAlign.setSelectedIndex(0);
        else
            if (annotation.getTextAlign()==StringAlignment.CENTER)
                cbTextAlign.setSelectedIndex(1);
            else
                cbTextAlign.setSelectedIndex(2);

        cbPos.setSelectedIndex(annotation.getPosition().getValue());

        bCalloutPen.setPen(annotation.getCallout().getArrow());
        spX.setValue(new Integer(annotation.getCallout().getXPosition()));
        spY.setValue(new Integer(annotation.getCallout().getYPosition()));
        spZ.setValue(new Integer(annotation.getCallout().getZPosition()));

        spArrowDist.setValue(new Integer(annotation.getCallout().getDistance()));
        cbHead.setSelectedIndex(annotation.getCallout().getArrowHead().getValue());
        spHeadSize.setValue(new Integer(annotation.getCallout().getArrowHeadSize()));

        cbCustomPos.setSelected(annotation.getShape().getCustomPosition());
        cbCustomSize.setSelected(annotation.getCustomSize());
        spLeft.setValue(new Integer(annotation.getLeft()));
        spTop.setValue(new Integer(annotation.getTop()));
        spLeft.setEnabled(cbCustomPos.isSelected());
        spTop.setEnabled(cbCustomPos.isSelected());
        spHeight.setValue(new Integer(annotation.getHeight()));
        spWidth.setValue(new Integer(annotation.getWidth()));
        spHeight.setEnabled(cbCustomSize.isSelected());
        spWidth.setEnabled(cbCustomSize.isSelected());



        Utils.addCursorsToCombo(cbCursor);
    }


    private void jbInit() throws Exception {
        this.setLayout(borderLayout3);
        jLabel1.setDisplayedMnemonic(Language.getString("T").charAt(0));
        jLabel1.setLabelFor(memoText);
        jLabel1.setText(Language.getString("Text"));
        jLabel1.setBounds(new Rectangle(8, 2, 183, 15));
        jPanel1.setLayout(null);
        memoText.setBorder(border2);
        memoText.setBounds(new Rectangle(5, 22, 214, 93));
        memoText.addKeyListener(new AnnotationEditor_memoText_keyAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("A").charAt(0));
        jLabel2.setLabelFor(cbTextAlign);
        jLabel2.setText(Language.getString("Text_alignment"));
        jLabel2.setBounds(new Rectangle(5, 127, 110, 15));
        jLabel3.setDisplayedMnemonic(Language.getString("C").charAt(0));
        jLabel3.setLabelFor(cbCursor);
        jLabel3.setText(Language.getString("Cursor"));
        jLabel3.setBounds(new Rectangle(109, 127, 107, 15));
        cbTextAlign.setBounds(new Rectangle(5, 146, 98, 22));
        cbCursor.setBounds(new Rectangle(111, 146, 108, 22));
        jPanel2.setLayout(null);
        jPanel4.setBorder(border35);
        jPanel4.setBounds(new Rectangle(1, 108, 223, 79));
        jPanel4.setLayout(null);
        jPanel5.setBorder(border39);
        jPanel5.setBounds(new Rectangle(1, 5, 222, 104));
        jPanel5.setLayout(null);
        jLabel4.setDisplayedMnemonic(Language.getString("A").charAt(0));
        jLabel4.setLabelFor(cbPos);
        jLabel4.setText(Language.getString("Auto"));
        jLabel4.setBounds(new Rectangle(23, 25, 34, 15));
        cbPos.setBounds(new Rectangle(61, 21, 109, 22));
        cbCustomPos.setMnemonic(Language.getString("C").charAt(0));
        cbCustomPos.setText(Language.getString("Custom"));
        cbCustomPos.setBounds(new Rectangle(14, 57, 79, 23));
        cbCustomSize.setMnemonic(Language.getString("U").charAt(0));
        cbCustomSize.setText(Language.getString("Custom"));
        cbCustomSize.setBounds(new Rectangle(12, 30, 74, 23));
        jLabel5.setDisplayedMnemonic(Language.getString("E").charAt(0));
        jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel5.setLabelFor(spLeft);
        jLabel5.setText(Language.getString("Left"));
        jLabel5.setBounds(new Rectangle(90, 51, 53, 15));
        jLabel6.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel6.setLabelFor(spTop);
        jLabel6.setText(Language.getString("Top"));
        jLabel6.setBounds(new Rectangle(109, 76, 34, 15));
        jLabel7.setDisplayedMnemonic(Language.getString("H").charAt(0));
        jLabel7.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel7.setLabelFor(spHeight);
        jLabel7.setText(Language.getString("Height"));
        jLabel7.setBounds(new Rectangle(96, 22, 45, 15));
        jLabel8.setDisplayedMnemonic(Language.getString("W").charAt(0));
        jLabel8.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel8.setLabelFor(spWidth);
        jLabel8.setText(Language.getString("Width"));
        jLabel8.setBounds(new Rectangle(95, 47, 46, 15));
        spTop.setBounds(new Rectangle(155, 72, 55, 22));
        spLeft.setBounds(new Rectangle(155, 47, 55, 22));
        spHeight.setBounds(new Rectangle(153, 18, 55, 22));
        spWidth.setBounds(new Rectangle(153, 43, 55, 22));
        jPanel3.setLayout(null);
        bCalloutPen.setBounds(new Rectangle(25, 15, 83, 23));
        bCalloutPen.setMnemonic(Language.getString("B").charAt(0));
        bCalloutPen.setText(Language.getString("Border"));
        bCalloutPointer.setBounds(new Rectangle(124, 15, 83, 23));
        bCalloutPointer.setMnemonic(Language.getString("P").charAt(0));
        bCalloutPointer.setText(Language.getString("Pointer"));
        jPanel6.setBorder(border41);
        jPanel6.setBounds(new Rectangle(24, 51, 131, 106));
        jPanel6.setLayout(null);
        jLabel9.setDisplayedMnemonic(Language.getString("D").charAt(0));
        jLabel9.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel9.setLabelFor(spArrowDist);
        jLabel9.setText(Language.getString("Distance"));
        jLabel9.setBounds(new Rectangle(160, 56, 59, 15));
        spArrowDist.setBounds(new Rectangle(230, 52, 58, 22));
        jLabel10.setDisplayedMnemonic(Language.getString("A").charAt(0));
        jLabel10.setLabelFor(cbHead);
        jLabel10.setText(Language.getString("Arrow_Head"));
        jLabel10.setBounds(new Rectangle(174, 87, 80, 15));
        cbHead.setBounds(new Rectangle(172, 105, 116, 22));
        spHeadSize.setBounds(new Rectangle(238, 131, 50, 22));
        jLabel11.setDisplayedMnemonic(Language.getString("X").charAt(0));
        jLabel11.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel11.setLabelFor(spX);
        jLabel11.setText(Language.getString("X"));
        jLabel11.setBounds(new Rectangle(25, 23, 17, 15));
        jLabel12.setDisplayedMnemonic(Language.getString("Y").charAt(0));
        jLabel12.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel12.setLabelFor(spY);
        jLabel12.setText(Language.getString("Y"));
        jLabel12.setBounds(new Rectangle(24, 49, 18, 15));
        jLabel13.setDisplayedMnemonic(Language.getString("Z").charAt(0));
        jLabel13.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel13.setLabelFor(spZ);
        jLabel13.setText(Language.getString("Z"));
        jLabel13.setBounds(new Rectangle(23, 75, 19, 15));
        spX.setBounds(new Rectangle(51, 19, 57, 22));
        spY.setBounds(new Rectangle(51, 45, 57, 22));
        spZ.setBounds(new Rectangle(51, 71, 57, 22));
        jLabel14.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel14.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel14.setLabelFor(spHeadSize);
        jLabel14.setText(Language.getString("Size"));
        jLabel14.setBounds(new Rectangle(191, 135, 40, 15));
        jPanel1.add(memoText);
        jPanel1.add(jLabel2);
        jPanel1.add(cbTextAlign);
        jPanel1.add(jLabel3);
        jPanel1.add(cbCursor);
        jPanel1.add(jLabel1);
        jTabbedPane1.add(jPanel1, Language.getString("Options"));
        jTabbedPane1.add(jPanel2, Language.getString("Position_Size"));
        jPanel4.add(cbCustomSize);
        jPanel4.add(jLabel8);
        jPanel4.add(jLabel7);
        jPanel4.add(spWidth);
        jPanel4.add(spHeight);
        jPanel2.add(jPanel5);
        jPanel5.add(jLabel4);
        jPanel5.add(cbPos);
        jPanel5.add(cbCustomPos);
        jPanel5.add(jLabel6);
        jPanel5.add(jLabel5);
        jPanel5.add(spLeft);
        jPanel5.add(spTop);
        jPanel2.add(jPanel4);
        jTabbedPane1.add(jPanel3, Language.getString("Callout"));
        jPanel3.add(bCalloutPen);
        jPanel3.add(bCalloutPointer);
        jPanel6.add(jLabel11);
        jPanel6.add(jLabel12);
        jPanel6.add(spX);
        jPanel6.add(spY);
        jPanel6.add(spZ);
        jPanel6.add(jLabel13);
        jPanel3.add(spArrowDist);
        jPanel3.add(cbHead);
        jPanel3.add(spHeadSize);
        jPanel3.add(jLabel14);
        jPanel3.add(jLabel9);
        jPanel3.add(jLabel10);
        jPanel3.add(jPanel6);
        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }

    Tool atool;
    Annotation annotation;
    ShapeEditor shapeForm;
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JLabel jLabel1 = new JLabel();
    JTextPane memoText = new JTextPane();
    Border border1 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(124, 124, 124),
            new Color(178, 178, 178));
    Border border2 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(124, 124, 124),
            new Color(178, 178, 178));
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JComboBox cbTextAlign = new JComboBox();
    JComboBox cbCursor = new JComboBox();
    JPanel jPanel4 = new JPanel();
    Border border3 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border4 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border5 = new TitledBorder(border4, Language.getString("Position"));
    JPanel jPanel5 = new JPanel();
    Border border6 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border7 = new TitledBorder(border6, Language.getString("Size"));
    Border border8 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border9 = new TitledBorder(border8, Language.getString("Position"));
    JLabel jLabel4 = new JLabel();
    JComboBox cbPos = new JComboBox();
    JCheckBox cbCustomPos = new JCheckBox();
    JCheckBox cbCustomSize = new JCheckBox();
    JLabel jLabel5 = new JLabel();
    JLabel jLabel6 = new JLabel();
    JLabel jLabel7 = new JLabel();
    JLabel jLabel8 = new JLabel();
    JSpinner spTop = new JSpinner();
    JSpinner spLeft = new JSpinner();
    JSpinner spHeight = new JSpinner();
    JSpinner spWidth = new JSpinner();
    Border border10 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border11 = new TitledBorder(border10, Language.getString("Position"));
    Border border12 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border13 = new TitledBorder(border12, Language.getString("Size"));
    ButtonPen bCalloutPen = new ButtonPen();
    JButton bCalloutPointer = new JButton();
    JPanel jPanel6 = new JPanel();
    Border border14 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border15 = new TitledBorder(border14, Language.getString("Position"));
    JLabel jLabel9 = new JLabel();
    JSpinner spArrowDist = new JSpinner();
    JLabel jLabel10 = new JLabel();
    JComboBox cbHead = new JComboBox();
    JSpinner spHeadSize = new JSpinner();
    JLabel jLabel11 = new JLabel();
    JLabel jLabel12 = new JLabel();
    JLabel jLabel13 = new JLabel();
    JSpinner spX = new JSpinner();
    JSpinner spY = new JSpinner();
    JSpinner spZ = new JSpinner();
    JLabel jLabel14 = new JLabel();
    Border border16 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border17 = new TitledBorder(border16, Language.getString("Position"));
    Border border18 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border19 = new TitledBorder(border18, Language.getString("Size"));
    Border border20 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border21 = new TitledBorder(border20, Language.getString("Position"));

    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    BorderLayout borderLayout3 = new BorderLayout();
    Border border22 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border23 = new TitledBorder(border22, Language.getString("Size"));
    Border border24 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border25 = new TitledBorder(border24, Language.getString("Position"));
    Border border26 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border27 = new TitledBorder(border26, Language.getString("Position"));
    Border border28 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border29 = new TitledBorder(border28, Language.getString("Position"));
    Border border30 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border31 = new TitledBorder(border30, Language.getString("Size"));
    Border border32 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border33 = new TitledBorder(border32, Language.getString("Size"));
    Border border34 = BorderFactory.createEtchedBorder(Color.white,
            new Color(165, 163, 151));
    Border border35 = new TitledBorder(border34, Language.getString("Size"));
    Border border36 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border37 = new TitledBorder(border36, Language.getString("Position"));
    Border border38 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border39 = new TitledBorder(border38, Language.getString("Position"));
    Border border40 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border41 = new TitledBorder(border40, Language.getString("Position"));

    void memoText_keyReleased(KeyEvent e) {
        annotation.setText(memoText.getText());
    }
}


final class AnnotationEditor_memoText_keyAdapter extends KeyAdapter {
    private AnnotationEditor adaptee;
    AnnotationEditor_memoText_keyAdapter(AnnotationEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void keyReleased(KeyEvent e) {
        adaptee.memoText_keyReleased(e);
    }
}

