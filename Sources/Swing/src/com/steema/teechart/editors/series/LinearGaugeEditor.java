package com.steema.teechart.editors.series;

import com.steema.teechart.styles.LinearGauge;

import java.awt.event.ComponentEvent;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.editors.ChartFontEditor;
import com.steema.teechart.styles.Series;
import com.steema.teechart.editors.BrushEditor;
import com.steema.teechart.languages.Language;
import java.awt.event.ComponentListener;

public class LinearGaugeEditor extends BaseSeriesEditor implements ComponentListener {

	private LinearGauge linearGauge=new LinearGauge();

	JTabbedPane jTabbedPane1 = new JTabbedPane();
	BorderLayout borderLayout1 = new BorderLayout();
	JPanel jPanel1 = new JPanel();
	JPanel jPanel2 = new JPanel();
	JPanel jPanel3 = new JPanel();
	JPanel jPanel4 = new JPanel();
	JPanel jPanel5 = new JPanel();
	JPanel jPanel6 = new JPanel();
	JLabel jLabelValue = new JLabel();
	JSpinner spValue = new JSpinner();
	private JButton jButtonEndPoint;
	private JButton jButtonFaceBrush;
	private JLabel jLabelStartVal;
	private JLabel jLabelEndVal;
	private JButton jButtonCenter;
	private JButton jButtonHand;
                javax.swing.JLabel jLabel12 = new JLabel();
        javax.swing.JLabel jLabel2= new JLabel();
        javax.swing.JSpinner jSpinner1 = new JSpinner();
        javax.swing.JSpinner jSpinner2 = new JSpinner();
        javax.swing.JSpinner jSpinner3 = new JSpinner();
        javax.swing.JSpinner jSpinner4 = new JSpinner();
        javax.swing.JSpinner jSpinner5 = new JSpinner(); 
        javax.swing.JSpinner jSpinner6 = new JSpinner();

	JSpinner spStartVal = new JSpinner();
	JSpinner spEndVal = new JSpinner();
	boolean settingVals = false;
	private JButton jInnerBand;
	private JButton jTicks;
	private JButton jMinorTicks;
	private JComboBox jComboPalettes;
	private JButton jButtonFont;
	private JLabel jLabelPalettes;
	private JTextField jTextFormat;
	private JCheckBox jCheckLabelsVisible;
	private JButton jMiddleBand;
	private JButton jOuterBand;

	public LinearGaugeEditor() {
		super();
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
        
       public LinearGaugeEditor(LinearGauge l) {
		super();
		try {
			jbInit();
                        linearGauge = l;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        this.setPreferredSize(new java.awt.Dimension(365, 244));
        jPanel1.setLayout(null);
        jPanel2.setLayout(null);
        jPanel3.setLayout(null);
        jPanel1.addComponentListener(this);
        jPanel2.addComponentListener(this);
        jPanel3.addComponentListener(this);
        jPanel4.addComponentListener(this);
        jPanel5.addComponentListener(this);
        jPanel6.addComponentListener(this);
        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(465, 261));
        jTabbedPane1.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent evt) {
        		settingVals = true; 
        		switch (jTabbedPane1.getSelectedIndex()){
    			    case 3: spStartVal.setValue(linearGauge.getRedLineStartValue()); 
    			            spEndVal.setValue(linearGauge.getRedLineEndValue());
    			            jPanel5.remove(spStartVal);
    			            jPanel4.add(spStartVal);
    			            jPanel5.remove(spEndVal);
    			            jPanel4.add(spEndVal);
    			            jPanel5.remove(jLabelStartVal);
    			            jPanel4.add(jLabelStartVal);
    			            jPanel5.remove(jLabelEndVal);
    			            jPanel4.add(jLabelEndVal);
    			            break;
    			    case 4: spStartVal.setValue(linearGauge.getGreenLineStartValue());
    			            spEndVal.setValue(linearGauge.getGreenLineEndValue());
    			            jPanel4.remove(spStartVal);
    			            jPanel5.add(spStartVal);
    			            jPanel4.remove(spEndVal);
    			            jPanel5.add(spEndVal);
    			            jPanel4.remove(jLabelStartVal);
    			            jPanel5.add(jLabelStartVal);
    			            jPanel4.remove(jLabelEndVal);
    			            jPanel5.add(jLabelEndVal);
    			            break;
    			  }
        		settingVals = false;
        	}
        });
        jTabbedPane1.add(jPanel1, Language.getString("Options"));
        jPanel1.setPreferredSize(new java.awt.Dimension(35, 23));
        {
          jLabelValue.setBounds(28, 19, 54, 20);

          jLabelValue.setText(Language.getString("Value"));

          spValue.setBounds(74, 19, 54, 20);

          jPanel1.add(spValue);
          spValue.addChangeListener(new ChangeListener() {
          	public void stateChanged(ChangeEvent evt) {
          		if (linearGauge!=null)
          			linearGauge.setValue(((Integer)spValue.getValue()).intValue());
          	}
          });
          jPanel1.add(jLabelValue);
          {
          	jButtonHand = new JButton();
          	jPanel1.add(jButtonHand);
          	jButtonHand.setText("Hand");
          	jButtonHand.setBounds(27, 55, 101, 20);

          	jButtonHand.addActionListener(new ActionListener() {
          		public void actionPerformed(ActionEvent evt) {
          			GaugeSeriesPointerEditor.edit(linearGauge.getHand());
          		}
          	});
          }
          {
          	jButtonCenter = new JButton();
          	jPanel1.add(jButtonCenter);
          	jButtonCenter.setText("Max Value Indicator");
          	jButtonCenter.setBounds(27, 130, 101, 20);

          	jButtonCenter.addActionListener(new ActionListener() {
          		public void actionPerformed(ActionEvent evt) {
          	SeriesPointerEditorDialog.edit(linearGauge.getMaxValueIndicator());
          		}
          	});
          }
          {
          	jButtonFaceBrush = new JButton();
          	jPanel1.add(jButtonFaceBrush);
          	jButtonFaceBrush.setText("FaceBrush");
          	jButtonFaceBrush.setBounds(27, 80, 101, 20);
          	jButtonFaceBrush.addActionListener(new ActionListener() {
          		public void actionPerformed(ActionEvent evt) {
          			BrushEditor.edit(linearGauge.getFaceBrush());
          		}
          	});
          }
          {
          	jButtonEndPoint = new JButton();
          	jPanel1.add(jButtonEndPoint);
          	jButtonEndPoint.setText("Value Area Brush");
          	jButtonEndPoint.setBounds(27, 105, 101, 20);

          	jButtonEndPoint.addActionListener(new ActionListener() {
          		public void actionPerformed(ActionEvent evt) {
          			BrushEditor.edit(linearGauge.getValueAreaBrush());
          		}
          	});
          }
          {
          	int length = com.steema.teechart.themes.ColorPalettes.Palettes.length;
        		String[] palettes = new String[length+2];

        		palettes[0]="BlackPalette";
        		palettes[1]="BluesPalette";
            for (int i = 2; i < length + 2; i++)
            {
            	palettes[i]=com.steema.teechart.themes.ColorPalettes.PaletteNames[i - 2];  
            }
          	ComboBoxModel jComboPalettesModel = 
          		new DefaultComboBoxModel( palettes );
          	jComboPalettes = new JComboBox();
          	jPanel1.add(jComboPalettes);
          	jComboPalettes.setModel(jComboPalettesModel);
          	jComboPalettes.setBounds(219, 129, 100, 21);
          	jComboPalettes.addActionListener(new ActionListener() {
          		public void actionPerformed(ActionEvent evt) {
          			SetGaugeColorPalette(jComboPalettes.getSelectedIndex());
          		}
          	});
          }
          {
          	jLabelPalettes = new JLabel();
          	jPanel1.add(jLabelPalettes);
          	jLabelPalettes.setText("Palettes");
          	jLabelPalettes.setBounds(160, 129, 54, 20);
          }
        }
jTabbedPane1.add(jPanel2, Language.getString("Ticks"));
        {
              JButton jTicks = new JButton(); 
              jPanel2.add(jTicks);
              jTicks.setText("Ticks"); 
              jTicks.setBounds(22, 20, 78, 21);
              
               JButton jMinorTicks = new JButton(); 
              jPanel2.add(jMinorTicks);
              jMinorTicks.setText("Minor Ticks"); 
              jMinorTicks.setBounds(22, 45, 78, 21);
              jMinorTicks.addMouseListener(new MouseAdapter()
              {
                      public void mouseReleased(MouseEvent evt)
                      {
                            GaugeSeriesPointerEditor.edit(linearGauge.getMinorTicks());
        	      }
             });
             
             jTicks.addMouseListener(new MouseAdapter() {
        	 public void mouseReleased(MouseEvent evt) {
        			GaugeSeriesPointerEditor.edit(linearGauge.getTicks());
        		}
        	});
                
                jPanel2.add(jSpinner1);
                jSpinner1.setBounds(120, 20, 50, 21);
                jPanel2.add(jSpinner2);
                jSpinner2.setBounds(240, 20, 50, 21);
                jPanel2.add(jSpinner3);
                jSpinner3.setBounds(120, 45, 50, 21);
                jPanel2.add(jSpinner4);
                jSpinner4.setBounds(240, 45, 50, 21);  
                jPanel2.add(jLabel12);
                jLabel12.setBounds(26, 70, 50, 21);
                jPanel2.add(jLabel2);
                jLabel2.setBounds(26, 95, 50, 21);
                jLabel12.setText("Maximum");
                jLabel2.setText("Minimum");
                jPanel2.add(jSpinner5);
                jSpinner5.setBounds(80, 70, 50, 21);
                jPanel2.add(jSpinner6);
                jSpinner6.setBounds(80, 95, 50, 21);
                jSpinner1.setValue((Integer)linearGauge.getTicks().getVertSize());
               jSpinner2.setValue((int) Math.round(linearGauge.getMinorTickDistance()));
                jSpinner3.setValue(3);
                jSpinner4.setValue((int) Math.round(linearGauge.getMinorTicks().getVertSize()));
                jSpinner5.setValue((int) Math.round(linearGauge.getMaximum()));
                jSpinner6.setValue((int) Math.round(linearGauge.getMinimum()));
                
                
                jSpinner1.addChangeListener(new ChangeListener() {
          		public void stateChanged(ChangeEvent e) {
                             if(linearGauge!=null)
                             {
                                if((Integer) jSpinner1.getValue()>100)
                                {
                                    jSpinner1.setValue(100);
                                }
                                else if((Integer) jSpinner1.getValue()<0)
                                {
                                    jSpinner1.setValue(0);
                                }
                            linearGauge.getTicks().setVertSize((Integer) jSpinner1.getValue()); 
                             }
                             }
          	});
                jSpinner2.addChangeListener(new ChangeListener() {
          		public void stateChanged(ChangeEvent e) {
                               if((Integer) jSpinner2.getValue()>100)
                                {
                                    jSpinner2.setValue(100);
                                }
                                else if((Integer) jSpinner2.getValue()<0)
                                {
                                    jSpinner2.setValue(0);
                                }
                               
                             if(linearGauge!=null)
                                    linearGauge.setMinorTickDistance(((Integer) jSpinner2.getValue())); 
          		}
          	});
                jSpinner3.addChangeListener(new ChangeListener() {
          		public void stateChanged(ChangeEvent e) {
                            
                               if((Integer) jSpinner3.getValue()>100)
                                {
                                    jSpinner3.setValue(100);
                                }
                                else if((Integer) jSpinner3.getValue()<0)
                                {
                                    jSpinner3.setValue(0);
                                }
                               
                             if(linearGauge!=null)
                            linearGauge.getAxis().setMinorTickCount((Integer) jSpinner3.getValue()); 
          		}
          	});
                jSpinner4.addChangeListener(new ChangeListener() {
          		public void stateChanged(ChangeEvent e) {
                                if((Integer) jSpinner4.getValue()>100)
                                {
                                    jSpinner4.setValue(100);
                                }
                                else if((Integer) jSpinner4.getValue()<0)
                                {
                                    jSpinner4.setValue(0);
                                }
                               
                             if(linearGauge!=null)
                            linearGauge.getMinorTicks().setVertSize((Integer) jSpinner4.getValue()); 
          		}
          	});
                jSpinner5.addChangeListener(new ChangeListener() {
                    
          		public void stateChanged(ChangeEvent e) {
                                if((Integer) jSpinner5.getValue()>1000)
                                {
                                    jSpinner5.setValue(1000);
                                }
                                else if((Integer) jSpinner5.getValue()<0)
                                {
                                    jSpinner5.setValue(0);
                                }
                               
                            if(linearGauge!=null)
                            linearGauge.setMaximum((Integer) jSpinner5.getValue()); 
          		}
          	});
                jSpinner6.addChangeListener(new ChangeListener() {
          		public void stateChanged(ChangeEvent e) {
                                if((Integer) jSpinner6.getValue()>(Integer) jSpinner5.getValue())
                                {
                                    jSpinner6.setValue((Integer) jSpinner5.getValue()-1);
                                }
                                else if((Integer) jSpinner6.getValue()<0)
                                {
                                    jSpinner6.setValue(0);
                                }
                                                           
                            if(linearGauge!=null)
                              linearGauge.setMinimum((Integer) jSpinner6.getValue());
          		}
          	});
              

    }
     
   
          
        
        jTabbedPane1.add(jPanel3, Language.getString("Labels"));
        {
        	jCheckLabelsVisible = new JCheckBox();
        	jPanel3.add(jCheckLabelsVisible);
        	jCheckLabelsVisible.setText("Labels Visible");
        	jCheckLabelsVisible.setBounds(29, 20, 92, 18);
        	jCheckLabelsVisible.setBorderPaintedFlat(true);
        	jCheckLabelsVisible.addChangeListener(new ChangeListener() {
        		public void stateChanged(ChangeEvent evt) {
        			linearGauge.getAxis().getLabels().setVisible(jCheckLabelsVisible.isSelected());
        		}
        	});
        }
        {
        	jTextFormat = new JTextField();
        	jPanel3.add(jTextFormat);
        	jTextFormat.setText("#,##0.###");
        	jTextFormat.setBounds(29, 103, 102, 21);
        	jTextFormat.addKeyListener(new KeyAdapter() {
        		public void keyReleased(KeyEvent evt) {
        			linearGauge.setValueFormat(jTextFormat.getText());
        		}
        	});
        }
        {
        	JLabel labelFormat = new JLabel();
        	jPanel3.add(labelFormat);
        	labelFormat.setText(Language.getString("Format"));
        	labelFormat.setBounds(29, 77, 54, 20);
        }
        {
        	jButtonFont = new JButton();
        	jPanel3.add(jButtonFont);
        	jButtonFont.setText("Font");
        	jButtonFont.setBounds(187, 20, 58, 21);
        	jButtonFont.addMouseListener(new MouseAdapter() {
        		public void mouseReleased(MouseEvent evt) {
        			ChartFontEditor.edit(null,  linearGauge.getAxis().getLabels().getFont());
        		}
        	});
        }
        jTabbedPane1.add(jPanel4, Language.getString("Red_Line"));
        jPanel4.setPreferredSize(new java.awt.Dimension(187, 92));
        {
            
                JButton jStyleRed = new JButton(); 
                jPanel4.add(jStyleRed);
                jStyleRed.setText("Style..."); 
                jStyleRed.setBounds(22, 20, 78, 21);
                jStyleRed.addMouseListener(new MouseAdapter() {
        	 public void mouseReleased(MouseEvent evt) {
        			GaugeSeriesPointerEditor.edit(linearGauge.getRedLine());
        		}
        	});
                
        	jLabelStartVal = new JLabel();
        	jLabelEndVal = new JLabel();
        	jPanel4.setLayout(null);
        	jPanel4.add(jLabelStartVal);
        	jPanel4.add(jLabelEndVal);
        	jLabelStartVal.setText(Language.getString("Start_Value"));
        	jLabelStartVal.setBounds(28, 70, 54, 20);
        	jLabelEndVal.setText(Language.getString("End_Value"));
        	jLabelEndVal.setBounds(28, 90, 54, 20);
            	spStartVal.addChangeListener(new ChangeListener() {
          	public void stateChanged(ChangeEvent evt) {
          		if (linearGauge!=null)
          		{
          			double dVal = getDoubleValue(spStartVal);
          			switch (jTabbedPane1.getSelectedIndex()){
        				case 3:
        					linearGauge.setRedLineStartValue(dVal); 
        					break;
        				case 4:
        					linearGauge.setGreenLineStartValue(dVal);
        					break;
        				}
          		}
          	}
          });
        	spEndVal.addChangeListener(new ChangeListener() {
          	public void stateChanged(ChangeEvent evt) {
          		if (linearGauge!=null)
          		{
          			double dVal = getDoubleValue(spEndVal);
          			switch (jTabbedPane1.getSelectedIndex()){
        				case 3: linearGauge.setRedLineEndValue(dVal); 
        					break;
        				case 4: linearGauge.setGreenLineEndValue(dVal);
        					break;
        				}
          	  }
          	}
          });
        	jPanel4.add(spStartVal);
        	jPanel4.add(spEndVal);
        	spStartVal.setBounds(102, 70, 54, 20);
        	spEndVal.setBounds(102, 90, 54, 20);
        	 
        	spStartVal.addChangeListener(new ChangeListener() {
        		public void stateChanged(ChangeEvent evt) {
        			if (!settingVals)
        			{
        				double dVal = getDoubleValue(spStartVal);
        				switch (jTabbedPane1.getSelectedIndex()){
        				case 3: linearGauge.setRedLineStartValue(dVal); 
        					break;
        				case 4: linearGauge.setGreenLineStartValue(dVal);
        					break;
        				}
        			}
        		}
        	});
        }
        jTabbedPane1.add(jPanel5, Language.getString("Green_Line"));
        jPanel5.setPreferredSize(new java.awt.Dimension(187, 92));
        {
        	JButton jStyleGreen = new JButton(); 
                jPanel5.add(jStyleGreen);
                jStyleGreen.setText("Style..."); 
                jStyleGreen.setBounds(22, 20, 78, 21);
                jStyleGreen.addMouseListener(new MouseAdapter() {
        	 public void mouseReleased(MouseEvent evt) {
        			GaugeSeriesPointerEditor.edit(linearGauge.getGreenLine());
        		}
        	});
            jPanel5.setLayout(null);
        }
        jTabbedPane1.add(jPanel6, Language.getString("Frame"));
                jPanel6.setLayout(null);
        {
              	jOuterBand = new JButton();
        	jPanel6.add(jOuterBand);
        	jOuterBand.setText("OuterBand");
        	jOuterBand.setBounds(140, 20, 66, 21);
        	jOuterBand.setSize(68, 21);
        	jOuterBand.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent evt) {
        			BrushEditor.edit(linearGauge.getFrame().getOuterBand());
        		}
        	});
      
        	jMiddleBand = new JButton();
        	jPanel6.add(jMiddleBand);
        	jMiddleBand.setText("MiddleBand");
        	jMiddleBand.setBounds(140, 52, 68, 21);
        	jMiddleBand.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent evt) {
        			BrushEditor.edit(linearGauge.getFrame().getMiddleBand());
        		}
        	});
     
        	jInnerBand = new JButton();
        	jPanel6.add(jInnerBand);
        	jInnerBand.setText("InnerBand");
        	jInnerBand.setBounds(140, 84, 64, 21);
        	jInnerBand.setSize(68, 21);
        	jInnerBand.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent evt) {
        			BrushEditor.edit(linearGauge.getFrame().getInnerBand());
        		}
        	});

                        
        }
    }
	
	protected void SetGaugeColorPalette(int index)
	{
		switch(index)
		{
			case 0:
				linearGauge.setGaugeColorPalette(com.steema.teechart.styles.CustomGauge.BlackPalette);
				break;
			case 1:
				linearGauge.setGaugeColorPalette(com.steema.teechart.styles.CustomGauge.BluesPalette);
				break;
			default:
				linearGauge.setGaugeColorPalette(com.steema.teechart.themes.ColorPalettes.Palettes[index-2]);
			  break;
		}  
	}	
	
	private double getDoubleValue(JSpinner spinner)
	{
		if (spinner.getValue().getClass() == Integer.class)
		  return ((Integer)spinner.getValue()).intValue() * 1.0;
		else
			return ((Double)spinner.getValue()).doubleValue();
	}

	/**
	 * setSeries
	 * 
	 * @param series
	 *          Series
	 * @param tabs
	 *          JTabbedPane
	 */
	public void setSeries(Series series, JTabbedPane tabs) {
		linearGauge = (LinearGauge) series;

		spValue.setValue((int) Math.round(linearGauge.getValue()));
		jTextFormat.setText(linearGauge.getValueFormat());
		
		/*spTotalA.setValue((int) Math.round(linearGauge.getTotalAngle()));
		spDist.setValue((int) Math.round(linearGauge.getHandDistance()));
		spOffset.setValue((int) Math.round(linearGauge.getHandOffset()));*/
	}

    public void componentResized(ComponentEvent e) {
      Object obj = e.getSource();
      if(obj == jPanel1)
      {
                spValue.setValue((int) Math.round(linearGauge.getValue()));

      }
      else if(obj == jPanel3)
      {
          
      jCheckLabelsVisible.setSelected(linearGauge.getAxis().getLabels().getVisible());

      }

    }

    public void componentMoved(ComponentEvent e) {
      Object obj = e.getSource();
      if(obj == jPanel1)
      {
          	spValue.setValue((int) linearGauge.getValue());

      }
      else if(obj == jPanel3)
      {
            jCheckLabelsVisible.setSelected(linearGauge.getAxis().getLabels().getVisible());

      }

    }

    public void componentShown(ComponentEvent e) {
              Object obj = e.getSource();
      if(obj == jPanel1)
      {
          	spValue.setValue((int) linearGauge.getValue());

      }
      else if(obj == jPanel3)
      {
 jCheckLabelsVisible.setSelected(linearGauge.getAxis().getLabels().getVisible());
      }

    
    }

    public void componentHidden(ComponentEvent e) {
      
        Object obj = e.getSource();
      if(obj == jPanel1)
      {
          	spValue.setValue((int) linearGauge.getValue());

      }
       else if(obj == jPanel3)
      {
 jCheckLabelsVisible.setSelected(linearGauge.getAxis().getLabels().getVisible());
       }
     
    }

}
