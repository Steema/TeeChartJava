package com.steema.teechart.editors.series;

import javax.swing.*;
import com.steema.teechart.languages.Language;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.border.BevelBorder;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.Shape;
import com.steema.teechart.styles.ShapeStyle;
import com.steema.teechart.styles.ShapeTextHorizAlign;
import com.steema.teechart.styles.ShapeTextVertAlign;
import com.steema.teechart.styles.ShapeXYStyle;
import com.steema.teechart.TextShapeStyle;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.misc.Utils;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.ButtonColor;
import com.steema.teechart.editors.GradientEditor;
import com.steema.teechart.editors.BrushEditor;
import com.steema.teechart.editors.ChartPenEditor;
import com.steema.teechart.editors.TextEditor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: Shape series editor</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ShapeEditor extends BaseSeriesEditor {


    private Shape shape;
    private TextEditor shapeText;

    ChartPenEditor penEditor = new ChartPenEditor();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JLabel jLabel1 = new JLabel();
    JComboBox cbStyle = new JComboBox();
    ButtonPen bShapePen = new ButtonPen();
    Border border1 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    JCheckBox cbEach = new JCheckBox();
    ButtonColor bShapeColor = new ButtonColor();
    JButton bShapeGradient = new JButton();
    JButton bShapeBrush = new JButton();
    JCheckBox cbTransparent = new JCheckBox();
    JCheckBox cbRound = new JCheckBox();
    JSpinner spDepth = new JSpinner();
    JSpinner spWidth = new JSpinner();
    JSpinner spOffset = new JSpinner();
    JCheckBox cbSideMargins = new JCheckBox();
    JCheckBox cbAutoMark = new JCheckBox();
    JPanel jPanel4 = new JPanel();
    Border border2 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border3 = new TitledBorder(border2, Language.getString("Alignment"));
    JRadioButton rbTop = new JRadioButton();
    JRadioButton rbCenter = new JRadioButton();
    JRadioButton rbBottom = new JRadioButton();
    Border border4 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border5 = new TitledBorder(border4, Language.getString("Alignment"));
    JTextArea textShape = new JTextArea();
    Border border6 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(124, 124, 124),
            new Color(178, 178, 178));
    Border border7 = BorderFactory.createLineBorder(Color.white, 2);
    Border border8 = BorderFactory.createLineBorder(Color.white, 2);
    Border border9 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(178, 178, 178));
    Border border10 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border11 = new TitledBorder(border10, Language.getString("Alignment"));
    JPanel jPanel5 = new JPanel();
    Border border12 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border13 = new TitledBorder(border12, Language.getString("Horiz_Alignment"));
    Border border14 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border15 = new TitledBorder(border14, Language.getString("Horiz_Alignment"));
    Border border16 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border17 = new TitledBorder(border16, Language.getString("Horiz_Alignment"));
    JRadioButton rbHorizLeft = new JRadioButton();
    JRadioButton rbHorizCenter = new JRadioButton();
    JRadioButton rbHorizRight = new JRadioButton();
    Border border18 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border19 = new TitledBorder(border18, Language.getString("Horiz_Alignment"));
    Border border20 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border21 = new TitledBorder(border20, Language.getString("Alignment"));
    JTextField textY0 = new JTextField();
    JTextField textX0 = new JTextField();
    JTextField textX1 = new JTextField();
    JTextField textY1 = new JTextField();
    JComboBox cbUnits = new JComboBox();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel5 = new JLabel();
    JLabel jLabel6 = new JLabel();
    Border border22 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border23 = new TitledBorder(border22, Language.getString("Alignment"));
    Border border24 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border25 = new TitledBorder(border24, Language.getString("Horiz_Alignment"));

    public ShapeEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        cbStyle.addItem(Language.getString("Rectangle"));
        cbStyle.addItem(Language.getString("Circle"));
        cbStyle.addItem(Language.getString("Vertical_Line"));
        cbStyle.addItem(Language.getString("Horiz_Line"));
        cbStyle.addItem(Language.getString("Triangle"));
        cbStyle.addItem(Language.getString("Invert_Triangle"));
        cbStyle.addItem(Language.getString("Line"));
        cbStyle.addItem(Language.getString("Diamond"));
        cbStyle.addItem(Language.getString("Cube"));
        cbStyle.addItem(Language.getString("Cross"));
        cbStyle.addItem(Language.getString("Diagonal_Cross"));
        cbStyle.addItem(Language.getString("Star"));
        cbStyle.addItem(Language.getString("Pyramid"));
        cbStyle.addItem(Language.getString("Invert_Pyramid"));

        cbUnits.addItem(Language.getString("Pixels"));
        cbUnits.addItem(Language.getString("Axis"));
        cbUnits.addItem(Language.getString("Axis_Origin"));
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        shape = (Shape) series;

        if (getParent()!=null) {
            getParent().add(this);
            this.setVisible(true);
        }

        setShape(shape);
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jPanel1.setLayout(null);
        jLabel1.setDisplayedMnemonic(Language.getString("L").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(cbStyle);
        jLabel1.setText(Language.getString("Style"));
        jLabel1.setBounds(new Rectangle(35, 12, 68, 14));
        cbStyle.setBounds(new Rectangle(119, 8, 110, 22));
        bShapePen.setBounds(new Rectangle(13, 75, 85, 23));
        bShapePen.setMnemonic(Language.getString("D").charAt(0));
        bShapePen.setText(Language.getString("Border"));
        jPanel3.setBorder(border1);
        jPanel3.setBounds(new Rectangle(0, 0, 395, 274));
        jPanel3.setLayout(null);
        cbEach.setMnemonic(Language.getString("E").charAt(0));
        cbEach.setText(Language.getString("Color_each"));
        cbEach.setBounds(new Rectangle(6, 6, 81, 23));
        bShapeColor.setBounds(new Rectangle(12, 39, 86, 23));
        bShapeColor.setMnemonic(Language.getString("C").charAt(0));
        bShapeColor.setText(Language.getString("Color"));
        bShapeGradient.setBounds(new Rectangle(15, 145, 86, 23));
        bShapeGradient.setMnemonic(Language.getString("G").charAt(0));
        bShapeGradient.setText(Language.getString("Gradient"));
        bShapeBrush.setBounds(new Rectangle(16, 111, 83, 23));
        bShapeBrush.setMnemonic(Language.getString("P").charAt(0));
        bShapeBrush.setText(Language.getString("Pattern"));
        cbTransparent.setMnemonic(Language.getString("S").charAt(0));
        cbTransparent.setText(Language.getString("Transparent"));
        cbTransparent.setBounds(new Rectangle(261, 22, 114, 23));
        cbRound.setMnemonic(Language.getString("R").charAt(0));
        cbRound.setText(Language.getString("Round_Rectangle"));
        cbRound.setBounds(new Rectangle(261, 2, 131, 23));
        jPanel2.setLayout(null);
        spDepth.setBounds(new Rectangle(115, 10, 50, 20));
        spWidth.setBounds(new Rectangle(115, 37, 50, 20));
        spOffset.setBounds(new Rectangle(115, 66, 50, 20));
        cbSideMargins.setMnemonic(Language.getString("I").charAt(0));
        cbSideMargins.setText(Language.getString("Bar_Side_Margins"));
        cbSideMargins.setBounds(new Rectangle(53, 102, 112, 23));
        cbAutoMark.setMnemonic(Language.getString("A").charAt(0));
        cbAutoMark.setText(Language.getString("Auto_Mark_position"));
        cbAutoMark.setBounds(new Rectangle(53, 126, 128, 23));
        jPanel4.setBorder(border23);
        jPanel4.setBounds(new Rectangle(265, 41, 106, 91));
        jPanel4.setLayout(null);
        rbTop.setBounds(new Rectangle(12, 17, 84, 23));
        rbTop.setText(Language.getString("Top"));
        rbTop.setMnemonic(Language.getString("T").charAt(0));
        rbCenter.setText(Language.getString("Center"));
        rbCenter.setMnemonic(Language.getString("N").charAt(0));
        rbCenter.setBounds(new Rectangle(12, 39, 84, 23));
        rbBottom.setText(Language.getString("Bottom"));
        rbBottom.setMnemonic(Language.getString("M").charAt(0));
        rbBottom.setBounds(new Rectangle(12, 61, 89, 23));

        ButtonGroup alignGroup = new ButtonGroup();
        textShape.addKeyListener(new ShapeEditor_textShape_keyAdapter(this));
        alignGroup.add(rbTop);
        alignGroup.add(rbCenter);
        alignGroup.add(rbBottom);

        textShape.setBorder(border9);
        textShape.setBounds(new Rectangle(109, 39, 142, 91));
        jPanel5.setBorder(border25);
        jPanel5.setBounds(new Rectangle(112, 132, 259, 44));
        jPanel5.setLayout(null);
        rbHorizLeft.setMnemonic(Language.getString("L").charAt(0));
        rbHorizLeft.setText(Language.getString("Left"));
        rbHorizLeft.setBounds(new Rectangle(14, 14, 72, 23));
        rbHorizCenter.setMnemonic(Language.getString("E").charAt(0));
        rbHorizCenter.setText(Language.getString("Center"));
        rbHorizCenter.setBounds(new Rectangle(86, 13, 69, 25));
        rbHorizRight.setMnemonic(Language.getString("R").charAt(0));
        rbHorizRight.setText(Language.getString("Right"));
        rbHorizRight.setBounds(new Rectangle(169, 12, 70, 27));

        ButtonGroup horizAlignGroup = new ButtonGroup();
        horizAlignGroup.add(rbHorizLeft);
        horizAlignGroup.add(rbHorizCenter);
        horizAlignGroup.add(rbHorizRight);

        jTabbedPane1.addChangeListener(new
                                       ShapeEditor_jTabbedPane1_changeAdapter(this));
        textY0.setBounds(new Rectangle(147, 55, 71, 22));
        textY0.addActionListener(new ShapeEditor_textY0_actionAdapter(this));
        textX0.setBounds(new Rectangle(58, 96, 71, 22));
        textX0.addActionListener(new ShapeEditor_textX0_actionAdapter(this));
        textX1.setBounds(new Rectangle(232, 95, 71, 22));
        textX1.addActionListener(new ShapeEditor_textX1_actionAdapter(this));
        textY1.setBounds(new Rectangle(152, 134, 72, 22));
        textY1.addActionListener(new ShapeEditor_textY1_actionAdapter(this));
        cbUnits.setBounds(new Rectangle(117, 20, 141, 20));
        cbUnits.addActionListener(new ShapeEditor_cbUnits_actionAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("U").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setHorizontalTextPosition(SwingConstants.TRAILING);
        jLabel2.setText(Language.getString("Units"));
        jLabel2.setBounds(new Rectangle(57, 23, 52, 15));
        jLabel3.setDisplayedMnemonic(Language.getString("T").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setText(Language.getString("Top"));
        jLabel3.setBounds(new Rectangle(97, 56, 45, 18));
        jLabel4.setDisplayedMnemonic(Language.getString("L").charAt(0));
        jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel4.setText(Language.getString("Left"));
        jLabel4.setBounds(new Rectangle(4, 97, 47, 19));
        jLabel5.setDisplayedMnemonic(Language.getString("R").charAt(0));
        jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel5.setText(Language.getString("Right"));
        jLabel5.setBounds(new Rectangle(181, 97, 42, 18));
        jLabel6.setDisplayedMnemonic(Language.getString("B").charAt(0));
        jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel6.setText(Language.getString("Bottom"));
        jLabel6.setBounds(new Rectangle(101, 139, 44, 15));
        bShapePen.addActionListener(new ShapeEditor_bShapePen_actionAdapter(this));
        bShapeBrush.addActionListener(new ShapeEditor_bShapeBrush_actionAdapter(this));
        bShapeGradient.addActionListener(new
                ShapeEditor_bShapeGradient_actionAdapter(this));
        bShapeColor.addActionListener(new ShapeEditor_bShapeColor_actionAdapter(this));
        cbStyle.addActionListener(new ShapeEditor_cbStyle_actionAdapter(this));
        rbHorizLeft.addActionListener(new ShapeEditor_rbHorizLeft_actionAdapter(this));
        rbHorizCenter.addActionListener(new
                                        ShapeEditor_rbHorizCenter_actionAdapter(this));
        rbHorizRight.addActionListener(new
                                       ShapeEditor_rbHorizRight_actionAdapter(this));
        rbTop.addActionListener(new ShapeEditor_rbTop_actionAdapter(this));
        rbCenter.addActionListener(new ShapeEditor_rbCenter_actionAdapter(this));
        rbBottom.addActionListener(new
                                        ShapeEditor_rbBottom_actionAdapter(this));
        cbRound.addActionListener(new ShapeEditor_cbRound_actionAdapter(this));
        cbTransparent.addActionListener(new
                                        ShapeEditor_cbTransparent_actionAdapter(this));
        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.add(jPanel1, Language.getString("Style"));
        jPanel1.add(jLabel1);
        jPanel1.add(cbStyle);
        jPanel4.add(rbBottom);
        jPanel4.add(rbTop);
        jPanel4.add(rbCenter);
        jPanel1.add(cbTransparent);
        jPanel1.add(cbRound);
        jPanel1.add(textShape);
        jPanel1.add(bShapeColor);
        jPanel5.add(rbHorizLeft);
        jPanel5.add(rbHorizCenter);
        jPanel5.add(rbHorizRight);
        jPanel1.add(jPanel4);
        jPanel1.add(bShapeGradient);
        jPanel1.add(bShapePen);
        jPanel1.add(bShapeBrush);
        jPanel1.add(jPanel5);
        jTabbedPane1.add(jPanel2, Language.getString("Position"));
        jPanel2.add(textY0);
        jPanel2.add(textX0);
        jPanel2.add(textY1);
        jPanel2.add(textX1);
        jPanel2.add(cbUnits);
        jPanel2.add(jLabel2);
        jPanel2.add(jLabel3);
        jPanel2.add(jLabel4);
        jPanel2.add(jLabel5);
        jPanel2.add(jLabel6);
        jTabbedPane1.add(jPanel3, Language.getString("Text"));
        jPanel3.add(spDepth);
        jPanel3.add(spWidth);
        jPanel3.add(spOffset);
        jPanel3.add(cbSideMargins);
        jPanel3.add(cbAutoMark);

        setShape(shape);
    }

    private void EnableRound()
    {
      cbRound.setEnabled(((shape.getStyle()==ShapeStyle.RECTANGLE) && (!shape.getChart().getAspect().getView3D())));
    }

    public void jTabbedPane1_stateChanged(ChangeEvent e) {
        // OnTabChange
        if (jTabbedPane1.getSelectedIndex()==2)  // tabText
            if (shapeText==null) {
                shapeText=new TextEditor(jPanel3,shape.getFormat().getFont());

            }
    }

    public void bShapePen_actionPerformed(ActionEvent e) {
        penEditor.setVisible(true);
        penEditor.refreshControls(shape.getPen());
    }

    public void bShapeBrush_actionPerformed(ActionEvent e) {
        BrushEditor.edit(this, shape.getBrush());
    }

    public void bShapeGradient_actionPerformed(ActionEvent e) {
        GradientEditor.edit(this, shape.getGradient());
    }

    public void bShapeColor_actionPerformed(ActionEvent e) {
        shape.setColor(bShapeColor.getColor());
    }

    public void cbStyle_actionPerformed(ActionEvent e) {
        if (shape!=null) {
            switch (cbStyle.getSelectedIndex()) {
            case 0:
                shape.setStyle(ShapeStyle.RECTANGLE);
                break;
            case 1:
                shape.setStyle(ShapeStyle.CIRCLE);
                break;
            case 2:
                shape.setStyle(ShapeStyle.VERTLINE);
                break;
            case 3:
                shape.setStyle( ShapeStyle.HORIZLINE);
                break;
            case 4:
                shape.setStyle(ShapeStyle.TRIANGLE);
                break;
            case 5:
                shape.setStyle(ShapeStyle.INVERTTRIANGLE);
                break;
            case 6:
                shape.setStyle(ShapeStyle.LINE);
                break;
            case 7:
                shape.setStyle(ShapeStyle.DIAMOND);
                break;
            case 8:
                shape.setStyle(ShapeStyle.CUBE);
                break;
            case 9:
                shape.setStyle(ShapeStyle.CROSS);
                break;
            case 10:
                shape.setStyle(ShapeStyle.DIAGCROSS);
                break;
            case 11:
                shape.setStyle(ShapeStyle.STAR);
                break;
            case 12:
                shape.setStyle(ShapeStyle.PYRAMID);
                break;
            case 13:
                shape.setStyle(ShapeStyle.INVERTPYRAMID);
                break;
            }
            EnableRound();
        }
    }

    public void rbHorizLeft_actionPerformed(ActionEvent e) {
        if (rbHorizLeft.isSelected())
          shape.setHorizAlignment(ShapeTextHorizAlign.LEFT);
    }

    public void rbHorizCenter_actionPerformed(ActionEvent e) {
        if (rbHorizCenter.isSelected())
          shape.setHorizAlignment(ShapeTextHorizAlign.CENTER);
    }

    public void rbHorizRight_actionPerformed(ActionEvent e) {
        if (rbHorizRight.isSelected())
          shape.setHorizAlignment(ShapeTextHorizAlign.RIGHT);
    }

    public void rbTop_actionPerformed(ActionEvent e) {
        if (rbTop.isSelected())
          shape.setVertAlignment(ShapeTextVertAlign.TOP);
    }

    public void rbCenter_actionPerformed(ActionEvent e) {
        if (rbCenter.isSelected())
          shape.setVertAlignment(ShapeTextVertAlign.CENTER);
    }

    public void rbBottom_actionPerformed(ActionEvent e) {
        if (rbBottom.isSelected())
          shape.setVertAlignment(ShapeTextVertAlign.BOTTOM);
    }

    public void cbRound_actionPerformed(ActionEvent e) {
        if (shape != null) {
            if (cbRound.isSelected())
                shape.getFormat().setShapeStyle(TextShapeStyle.ROUNDRECTANGLE);
            else
                shape.getFormat().setShapeStyle(TextShapeStyle.RECTANGLE);
        }
    }

    public void cbTransparent_actionPerformed(ActionEvent e) {
        shape.getFormat().setTransparent(cbTransparent.isSelected());
    }

    public void cbUnits_actionPerformed(ActionEvent e) {
        if (shape != null) {
            switch (cbUnits.getSelectedIndex()) {
                case 0:
                    shape.setXYStyle(ShapeXYStyle.PIXELS);
                    break;
                case 1:
                    shape.setXYStyle(ShapeXYStyle.AXIS);
                    break;
                case 2:
                    shape.setXYStyle(ShapeXYStyle.AXISORIGIN);
                    break;
            }
        }
    }

    public void textY0_actionPerformed(ActionEvent e) {
        shape.setY0(Utils.stringToDouble(textY0.getText(),shape.getY0()));
    }

    public void textX0_actionPerformed(ActionEvent e) {
        shape.setX0(Utils.stringToDouble(textX0.getText(),shape.getX0()));
    }

    public void textX1_actionPerformed(ActionEvent e) {
        shape.setX1(Utils.stringToDouble(textX1.getText(),shape.getX1()));
    }

    public void textY1_actionPerformed(ActionEvent e) {
        shape.setY1(Utils.stringToDouble(textY1.getText(),shape.getY1()));
    }

    public void textShape_actionPerformed(ActionEvent e) {
        String[] tmpStr= new String[1];
        tmpStr[0]=textShape.getText();
        shape.setText(tmpStr);
    }

    protected void setShape(Shape shape) {
        if (shape!=null) {

            cbStyle.setSelectedIndex(shape.getStyle().getValue());
            cbTransparent.setSelected(shape.getTransparent());

            if ( shape.getCount()==2 ) {
                textX0.setText(Double.toString(shape.getX0()));
                textY0.setText(Double.toString(shape.getY0()));
                textX1.setText(Double.toString(shape.getX1()));
                textY1.setText(Double.toString(shape.getY1()));
            }

            textShape.setFont(new java.awt.Font(shape.getFont().getName(),0,shape.getFont().getSize()));

            textShape.setText(Utils.arrayToString(shape.getText()));

            switch (shape.getHorizAlignment().getValue()) {
                case 0: rbHorizLeft.setSelected(true); break;
                case 1: rbHorizCenter.setSelected(true); break;
                case 2: rbHorizRight.setSelected(true); break;
            }

            switch (shape.getVertAlignment().getValue()) {
                case 0: rbTop.setSelected(true); break;
                case 1: rbCenter.setSelected(true); break;
                case 2: rbBottom.setSelected(true); break;
            }

            cbRound.setSelected((shape.getFormat().getShapeStyle()==(TextShapeStyle.ROUNDRECTANGLE)));
            EnableRound();
            cbUnits.setSelectedIndex(shape.getXYStyle().getValue());
            bShapeColor.setColor(shape.getColor());
            bShapePen.setPen(shape.getPen());
        }
    }

    public void textShape_keyReleased(KeyEvent e) {
       if(shape != null) {
           shape.setText(textShape.getText().split(Language.getString("LineSeparator")));
       }
    }
}


class ShapeEditor_textShape_keyAdapter extends KeyAdapter {
    private ShapeEditor adaptee;
    ShapeEditor_textShape_keyAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void keyReleased(KeyEvent e) {
        adaptee.textShape_keyReleased(e);
    }
}


class ShapeEditor_textY1_actionAdapter implements ActionListener {
    private ShapeEditor adaptee;
    ShapeEditor_textY1_actionAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.textY1_actionPerformed(e);
    }
}


class ShapeEditor_textX1_actionAdapter implements ActionListener {
    private ShapeEditor adaptee;
    ShapeEditor_textX1_actionAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.textX1_actionPerformed(e);
    }
}


class ShapeEditor_textX0_actionAdapter implements ActionListener {
    private ShapeEditor adaptee;
    ShapeEditor_textX0_actionAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.textX0_actionPerformed(e);
    }
}


class ShapeEditor_textY0_actionAdapter implements ActionListener {
    private ShapeEditor adaptee;
    ShapeEditor_textY0_actionAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.textY0_actionPerformed(e);
    }
}


class ShapeEditor_cbUnits_actionAdapter implements ActionListener {
    private ShapeEditor adaptee;
    ShapeEditor_cbUnits_actionAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbUnits_actionPerformed(e);
    }
}


class ShapeEditor_cbTransparent_actionAdapter implements ActionListener {
    private ShapeEditor adaptee;
    ShapeEditor_cbTransparent_actionAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbTransparent_actionPerformed(e);
    }
}


class ShapeEditor_cbRound_actionAdapter implements ActionListener {
    private ShapeEditor adaptee;
    ShapeEditor_cbRound_actionAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbRound_actionPerformed(e);
    }
}


class ShapeEditor_rbBottom_actionAdapter implements ActionListener {
    private ShapeEditor adaptee;
    ShapeEditor_rbBottom_actionAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbBottom_actionPerformed(e);
    }
}


class ShapeEditor_rbCenter_actionAdapter implements ActionListener {
    private ShapeEditor adaptee;
    ShapeEditor_rbCenter_actionAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbCenter_actionPerformed(e);
    }
}


class ShapeEditor_rbTop_actionAdapter implements ActionListener {
    private ShapeEditor adaptee;
    ShapeEditor_rbTop_actionAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbTop_actionPerformed(e);
    }
}


class ShapeEditor_rbHorizRight_actionAdapter implements ActionListener {
    private ShapeEditor adaptee;
    ShapeEditor_rbHorizRight_actionAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbHorizRight_actionPerformed(e);
    }
}


class ShapeEditor_rbHorizCenter_actionAdapter implements ActionListener {
    private ShapeEditor adaptee;
    ShapeEditor_rbHorizCenter_actionAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbHorizCenter_actionPerformed(e);
    }
}


class ShapeEditor_rbHorizLeft_actionAdapter implements ActionListener {
    private ShapeEditor adaptee;
    ShapeEditor_rbHorizLeft_actionAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.rbHorizLeft_actionPerformed(e);
    }
}


class ShapeEditor_cbStyle_actionAdapter implements ActionListener {
    private ShapeEditor adaptee;
    ShapeEditor_cbStyle_actionAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbStyle_actionPerformed(e);
    }
}


class ShapeEditor_bShapeColor_actionAdapter implements ActionListener {
    private ShapeEditor adaptee;
    ShapeEditor_bShapeColor_actionAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bShapeColor_actionPerformed(e);
    }
}


class ShapeEditor_bShapeGradient_actionAdapter implements ActionListener {
    private ShapeEditor adaptee;
    ShapeEditor_bShapeGradient_actionAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bShapeGradient_actionPerformed(e);
    }
}


class ShapeEditor_bShapeBrush_actionAdapter implements ActionListener {
    private ShapeEditor adaptee;
    ShapeEditor_bShapeBrush_actionAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bShapeBrush_actionPerformed(e);
    }
}


class ShapeEditor_bShapePen_actionAdapter implements ActionListener {
    private ShapeEditor adaptee;
    ShapeEditor_bShapePen_actionAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bShapePen_actionPerformed(e);
    }
}


class ShapeEditor_jTabbedPane1_changeAdapter implements ChangeListener {
    private ShapeEditor adaptee;
    ShapeEditor_jTabbedPane1_changeAdapter(ShapeEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jTabbedPane1_stateChanged(e);
    }
}

