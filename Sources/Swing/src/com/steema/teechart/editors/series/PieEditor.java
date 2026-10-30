package com.steema.teechart.editors.series;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Rectangle;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.styles.Pie;
import com.steema.teechart.styles.PieMarks.MarksRotateStyle;
import com.steema.teechart.styles.PieOtherStyle;
import com.steema.teechart.styles.Series;
import com.steema.teechart.editors.ButtonColor;
import com.steema.teechart.editors.ButtonPen;
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
public class PieEditor extends BaseSeriesEditor {


    private Pie pie;
    private CircledEditor circled=new CircledEditor();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    private JSpinner jSpinner1;
    private JLabel jLabelValue;
    private JSpinner spValue;
    private JLabel jLabel1;
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JCheckBox cbEach = new JCheckBox();
    ButtonColor bColor = new ButtonColor();
    ButtonPen bBorder = new ButtonPen();
    JCheckBox cbAutoPen = new JCheckBox();
    JCheckBox cbMarkInside = new JCheckBox();
    JCheckBox cbAutoMarkPos = new JCheckBox();
    JCheckBox cbRotated = new JCheckBox();
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel labelFormat;
    private JComboBox jComboBox1;
    private JSpinner jSpinner4;
    private JLabel jLabelPalettes;
    private JComboBox jComboEdgeStyles;
    private JSpinner jSpinner3;
    private JTextField jTextFormat;
    private JSpinner jSpinner2;
    private JLabel jLabelRotateStyles;
    private JComboBox jComboRotateStyles;

    public PieEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * setSeries
     *
     * @param series Series
     * @param tabs JTabbedPane
     */
    public void setSeries(Series series, JTabbedPane tabs) {
        pie=(Pie)series;
        circled.setSeries(pie,tabs);
        tabs.add(circled, Language.getString("Circled"), 0);
        bColor.setColor(pie.getColor());
        cbEach.setSelected(pie.getColorEach());
        bBorder.setPen(pie.getPen());
        cbAutoPen.setSelected(pie.getAutoPenColor());
        
        spValue.setValue(pie.getExplodeBiggest());
        jSpinner1.setValue(pie.getAngleSize());
        jSpinner2.setValue(pie.getTransparency());
        jSpinner3.setValue(pie.getBevelPercent());
        jComboEdgeStyles.setSelectedIndex(pie.getEdgeStyle().getValue());
        jTextFormat.setText(pie.getOtherSlice().getText());
        
        jComboBox1.setSelectedIndex(pie.getOtherSlice().getStyle().getValue());
        jSpinner4.setValue((int)Math.round(pie.getOtherSlice().getValue()));
        jTextFormat.setText(pie.getOtherSlice().getText());
        
        cbMarkInside.setSelected(pie.getMarksPie().getInsideSlice());
        cbAutoMarkPos.setSelected(pie.getAutoMarkPosition());
        cbRotated.setSelected(pie.getMarksPie().getRotated());
        jComboRotateStyles.setSelectedIndex(pie.getMarksPie().getRotateStyle()==MarksRotateStyle.Radial? 0 : 1);
        jComboRotateStyles.setEnabled(cbRotated.isSelected());
        
        tabs.setSelectedIndex(0);
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        this.setPreferredSize(new java.awt.Dimension(390, 236));
        jPanel1.setLayout(null);
        jPanel2.setLayout(null);
        jPanel3.setLayout(null);
        jPanel4.setLayout(null);
        cbEach.setMnemonic(Language.getString("E").charAt(0));
        cbEach.setText(Language.getString("Color_each_slice"));
        cbEach.setBounds(new Rectangle(61, 17, 146, 23));
        cbEach.addActionListener(new PieEditor_cbEach_actionAdapter(this));
        bColor.setBounds(new Rectangle(61, 52, 95, 25));
        bColor.setMnemonic(Language.getString("C").charAt(0));
        bColor.setText(Language.getString("Color"));
        bColor.addActionListener(new PieEditor_bColor_actionAdapter(this));
        bBorder.setBounds(256, 17, 89, 25);
        bBorder.setMnemonic(Language.getString("B").charAt(0));
        bBorder.setText(Language.getString("Border"));
        cbAutoPen.setMnemonic(Language.getString("A").charAt(0));
        cbAutoPen.setText(Language.getString("Auto Pen Color"));
        cbAutoPen.setBounds(new Rectangle(253, 47, 146, 23));
        cbAutoPen.addActionListener(new PieEditor_cbAutoPen_actionAdapter(this));
        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.add(jPanel1, Language.getString("Options"));
        jPanel1.add(bBorder);
        jPanel1.add(cbAutoPen);
        {
        	spValue = new JSpinner();
        	jPanel1.add(spValue);
        	spValue.setBounds(109, 19, 60, 20);
        	spValue.addChangeListener(new ChangeListener() {
        		public void stateChanged(ChangeEvent evt) {
        			if (pie!=null)
        			{
        				pie.setExplodeBiggest(((Integer)spValue.getValue()).intValue());
        			  pie.refreshSeries();
        			}
        		}
        	});
        }
        {
        	jLabelValue = new JLabel();
        	jPanel1.add(jLabelValue);
        	jLabelValue.setText(Language.getString("_EXPLODE_BIGGEST"));
        	jLabelValue.setBounds(28, 19, 103, 20);
        }
        {
        	jSpinner1 = new JSpinner();
        	jPanel1.add(jSpinner1);
        	jSpinner1.setBounds(109, 45, 60, 20);
        	jSpinner1.addChangeListener(new ChangeListener() {
        		public void stateChanged(ChangeEvent evt) {
        			if (pie!=null)
        			{
        				pie.setAngleSize(((Integer)jSpinner1.getValue()).intValue());
        				pie.refreshSeries();
        			}
        		}
        	});
        }
        {
        	jLabel1 = new JLabel();
        	jPanel1.add(jLabel1);
        	jLabel1.setText(Language.getString("_TOTAL_ANGLE"));
        	jLabel1.setBounds(28, 45, 103, 20);
        }
        {
        	jSpinner2 = new JSpinner();
        	jPanel1.add(jSpinner2);
        	jSpinner2.setBounds(109, 71, 60, 20);
        	jSpinner2.addChangeListener(new ChangeListener() {
        		public void stateChanged(ChangeEvent evt) {
        			if (pie!=null)
        			{
        				pie.setTransparency(((Integer)jSpinner2.getValue()).intValue());
        				pie.refreshSeries();
        			}
        		}
        	});
        }
        {
        	jLabel2 = new JLabel();
        	jPanel1.add(jLabel2);
        	jLabel2.setText(Language.getString("Transparency"));
        	jLabel2.setBounds(29, 71, 103, 20);
        }
        {
        	jSpinner3 = new JSpinner();
        	jPanel1.add(jSpinner3);
        	jSpinner3.setBounds(109, 97, 60, 20);
        	jSpinner3.addChangeListener(new ChangeListener() {
        		public void stateChanged(ChangeEvent evt) {
        			if (pie!=null)
        			{
        				pie.setBevelPercent(((Integer)jSpinner3.getValue()).intValue());
        				pie.refreshSeries();
        			}
        		}
        	});
        }
        {
        	jLabel3 = new JLabel();
        	jPanel1.add(jLabel3);
        	jLabel3.setText(Language.getString("Bevel_Percent"));
        	jLabel3.setBounds(29, 97, 103, 20);
        }
        {
        	jComboEdgeStyles = new JComboBox();
        	jPanel1.add(jComboEdgeStyles);
        	
        	jComboEdgeStyles.addItem(Language.getString("Flat"));
        	jComboEdgeStyles.addItem(Language.getString("Curved"));
        	jComboEdgeStyles.addItem(Language.getString("None"));

        	jComboEdgeStyles.setBounds(257, 97, 88, 25);
        	jComboEdgeStyles.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent evt) {
        			pie.setEdgeStyle(com.steema.teechart.drawing.EdgeStyle.fromInt(jComboEdgeStyles.getSelectedIndex()));
        			pie.refreshSeries();
        		}
        	});
        }
        {
        	jLabelPalettes = new JLabel();
        	jPanel1.add(jLabelPalettes);
        	jLabelPalettes.setText(Language.getString("Edge_Style"));
        	jLabelPalettes.setBounds(191, 97, 54, 20);
        }
        jTabbedPane1.add(jPanel2, Language.getString("Group_Slices"));
        {
        	jLabel4 = new JLabel();
        	jPanel2.add(jLabel4);
        	jLabel4.setText(Language.getString("Value"));
        	jLabel4.setBounds(29, 34, 103, 20);
        }
        {
        	jSpinner4 = new JSpinner();
        	jPanel2.add(jSpinner4);
        	jSpinner4.setBounds(156, 34, 60, 20);
        	jSpinner4.addChangeListener(new ChangeListener() {
        		public void stateChanged(ChangeEvent evt) {
        			if (pie!=null)
        			{
        				int tmpVal=((Integer)jSpinner4.getValue()).intValue();
        				pie.getOtherSlice().setValue(tmpVal);
        				pie.refreshSeries();
        			}
        		}
        	});
        }
        {
        	jLabel5 = new JLabel();
        	jPanel2.add(jLabel5);
        	jLabel5.setText(Language.getString("Style"));
        	jLabel5.setBounds(29, 115, 54, 20);
        }
        {
        	ComboBoxModel jComboBox1Model = 
        		new DefaultComboBoxModel(
        				new String[] { Language.getString("None"), 
        						           Language.getString("BelowPercent"), 
        						           Language.getString("BelowValue") });
        	jComboBox1 = new JComboBox();
        	jPanel2.add(jComboBox1);
        	jComboBox1.setModel(jComboBox1Model);
        	jComboBox1.setBounds(126, 113, 88, 25);
        	jComboBox1.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent evt) {
        			pie.getOtherSlice().setStyle(PieOtherStyle.fromInt(jComboBox1.getSelectedIndex()));
        			pie.refreshSeries();
        		}
        	});
        }
        {
        	labelFormat = new JLabel();
        	jPanel2.add(labelFormat);
        	labelFormat.setText(Language.getString("Label"));
        	labelFormat.setBounds(29, 66, 60, 20);
        }
        {
        	jTextFormat = new JTextField();
        	jPanel2.add(jTextFormat);
        	jTextFormat.setText("");
        	jTextFormat.setBounds(114, 66, 102, 20);
        	jTextFormat.addKeyListener(new KeyAdapter() {
        		public void keyReleased(KeyEvent evt) {
        			pie.getOtherSlice().setText(jTextFormat.getText());
        			pie.refreshSeries();
        		}
        	});
        }
        jTabbedPane1.add(jPanel3, Language.getString("Colors"));
        jPanel3.add(cbEach);
        jPanel3.add(bColor);
        
        cbMarkInside.setMnemonic(Language.getString("I").charAt(0));
        cbMarkInside.setText(Language.getString("Marks_Inside"));
        cbMarkInside.setBounds(new Rectangle(61, 17, 146, 23));
        cbMarkInside.addActionListener(new PieEditor_cbMarkInside_actionAdapter(this));
        cbAutoMarkPos.setMnemonic(Language.getString("A").charAt(0));
        cbAutoMarkPos.setText(Language.getString("Auto_Mark_Pos"));
        cbAutoMarkPos.setBounds(new Rectangle(61, 42, 146, 23));
        cbAutoMarkPos.addActionListener(new PieEditor_cbAutoMarkPos_actionAdapter(this));
        cbRotated.setMnemonic(Language.getString("A").charAt(0));
        cbRotated.setText(Language.getString("Rotated"));
        cbRotated.setBounds(new Rectangle(61, 67, 146, 23));
        cbRotated.addActionListener(new PieEditor_cbRotated_actionAdapter(this));
        jTabbedPane1.add(jPanel4, Language.getString("Marks"));
        jPanel4.add(cbMarkInside);
        jPanel4.add(cbAutoMarkPos);
        jPanel4.add(cbRotated);
        
    	jLabelRotateStyles = new JLabel();
    	jPanel4.add(jLabelRotateStyles);
    	jLabelRotateStyles.setText(Language.getString("Rotate_Style"));
    	jLabelRotateStyles.setBounds(81, 92, 146, 23);
    	
        {
        	jComboRotateStyles = new JComboBox();
        
	    	jPanel4.add(jComboRotateStyles);
	    	
	    	jComboRotateStyles.addItem(Language.getString("Radial"));
	    	jComboRotateStyles.addItem(Language.getString("Tangencial"));
	
	    	jComboRotateStyles.setBounds(160, 92, 88, 25);
	    	jComboRotateStyles.addActionListener(new ActionListener() {
	    		public void actionPerformed(ActionEvent evt) {
	    			pie.getMarksPie().setRotateStyle(jComboRotateStyles.getSelectedIndex()==0? MarksRotateStyle.Radial : MarksRotateStyle.Tangencial);
	    			pie.refreshSeries();
	    		}
	    	});
        }
    }

    public void bColor_actionPerformed(ActionEvent e) {
        pie.setColor(bColor.getColor());
    }

    public void cbEach_actionPerformed(ActionEvent e) {
        pie.setColorEach(cbEach.isSelected());
    }
    
    public void cbAutoPen_actionPerformed(ActionEvent e) {
        pie.setAutoPenColor(cbAutoPen.isSelected());
    }
    
    public void cbMarkInside_actionPerformed(ActionEvent e) {
        pie.getMarksPie().setInsideSlice(cbMarkInside.isSelected());
        pie.refreshSeries();
    }
    
    public void cbAutoMarkPos_actionPerformed(ActionEvent e) {
        pie.setAutoMarkPosition(cbAutoMarkPos.isSelected());
        pie.refreshSeries();
    }
    
    public void cbRotated_actionPerformed(ActionEvent e) {
        pie.getMarksPie().setRotated(cbRotated.isSelected());
        jComboRotateStyles.setEnabled(cbRotated.isSelected());
        pie.refreshSeries();
    }
}


class PieEditor_cbEach_actionAdapter implements ActionListener {
    private PieEditor adaptee;
    PieEditor_cbEach_actionAdapter(PieEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbEach_actionPerformed(e);
    }
}


class PieEditor_bColor_actionAdapter implements ActionListener {
    private PieEditor adaptee;
    PieEditor_bColor_actionAdapter(PieEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bColor_actionPerformed(e);
    }
}

class PieEditor_cbAutoPen_actionAdapter implements ActionListener {
    private PieEditor adaptee;
    PieEditor_cbAutoPen_actionAdapter(PieEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAutoPen_actionPerformed(e);
    }
}

class PieEditor_cbMarkInside_actionAdapter implements ActionListener {
    private PieEditor adaptee;
    PieEditor_cbMarkInside_actionAdapter(PieEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbMarkInside_actionPerformed(e);
    }
}

class PieEditor_cbAutoMarkPos_actionAdapter implements ActionListener {
    private PieEditor adaptee;
    PieEditor_cbAutoMarkPos_actionAdapter(PieEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAutoMarkPos_actionPerformed(e);
    }
}

class PieEditor_cbRotated_actionAdapter implements ActionListener {
    private PieEditor adaptee;
    PieEditor_cbRotated_actionAdapter(PieEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbRotated_actionPerformed(e);
    }
}