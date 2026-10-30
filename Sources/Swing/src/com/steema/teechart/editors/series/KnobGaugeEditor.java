package com.steema.teechart.editors.series;

import java.awt.event.ComponentEvent;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Rectangle;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.styles.Series;
import com.steema.teechart.editors.BrushEditor;
import com.steema.teechart.editors.ChartFontEditor;
import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.KnobGauge;
import com.steema.teechart.styles.PointerStyle;
import java.awt.event.ComponentListener;

public class KnobGaugeEditor extends BaseSeriesEditor implements ComponentListener {

    private KnobGauge knobGauge = new KnobGauge();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    BorderLayout borderLayout1 = new BorderLayout();
    private double value2 = 0.0;
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();
    JPanel jPanel6 = new JPanel();
    JPanel jPanel7 = new JPanel();
    JPanel jPanel8 = new JPanel();
    JLabel jLabel12 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JSpinner jSpinner1 = new JSpinner();
    JSpinner jSpinner2 = new JSpinner();
    JSpinner jSpinner3 = new JSpinner();
    JSpinner jSpinner4 = new JSpinner();
    JSpinner jSpinner5 = new JSpinner();
    JSpinner jSpinner6 = new JSpinner();
    JLabel jLabelValue = new JLabel();
    JSpinner spValue = new JSpinner();
    private JButton jButtonEndPoint;
    private JButton jButtonFaceBrush;
    private JLabel jLabelStartVal;
    private JLabel jLabelEndVal;
    private JButton jButtonCenter;
    private JButton jButtonHand;
    private JButton jButtonHiglight;
    JLabel jLabelTotalA = new JLabel();
    JLabel jLabelRotationA = new JLabel();
    JSpinner spTotalA = new JSpinner();
    JSpinner spRotationA = new JSpinner();
    JLabel jLabelDist = new JLabel();
    JSpinner spDist = new JSpinner();
    JLabel jLabelOffset = new JLabel();
    JSpinner spOffset = new JSpinner();
    JSpinner spStartVal = new JSpinner();
    JSpinner spEndVal = new JSpinner();
    boolean settingVals = false;
    private JButton jInnerBand;
    private JLabel jLabelPalettes;
    private JComboBox jComboPalettes;
    private JButton jMinorTicks;
    private JButton jTicks;
    private JLabel jLabel1;
    private JButton jButtonFont;
    private JLabel jLabelFormat;
    private JTextField jTextFormat;
    private JCheckBox jCheckLabelsVisible;
    private JCheckBox jCheckLabelsInside;
    private JCheckBox jCheckLabelsRotate;
    private JButton jMiddleBand;
    private JButton jOuterBand;
    private JCheckBox jAutoPositionNumeric;
    private JCheckBox jAutoValueNumeric;
    private JCheckBox jAutoPositionLinear;
    private JCheckBox jAutoValueLinear;
    private JCheckBox jHorizontalLinear;
    private JCheckBox checkAxisInside = new JCheckBox();
    private JLabel jLabelLeftLinear;
    private JLabel jLabelTopLinear;
    private JSpinner jSpinnerLeftLinear;
    public JSpinner jSpinnerTopLinear;
    private JCheckBox jVisibleLinear;
    private JCheckBox jVisibleNumeric;
    private boolean up = true;

    public KnobGaugeEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public KnobGaugeEditor(KnobGauge c) {
        super();
        try {
            jbInit();
            knobGauge = c;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jPanel1.setLayout(null);
        jPanel2.setLayout(null);
        jPanel3.setLayout(null);
        jPanel1.addComponentListener(this);
        jPanel2.addComponentListener(this);
        jPanel3.addComponentListener(this);
        jPanel4.addComponentListener(this);
        jPanel5.addComponentListener(this);
        jPanel6.addComponentListener(this);
        jPanel8.addComponentListener(this);


        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(465, 261));
        jTabbedPane1.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent evt) {
                settingVals = true;
                switch (jTabbedPane1.getSelectedIndex()) {
                    case 3:
                        spStartVal.setValue(knobGauge.getRedLineStartValue());
                        spEndVal.setValue(knobGauge.getRedLineEndValue());
                        jPanel5.remove(spStartVal);
                        jPanel4.add(spStartVal);
                        jPanel5.remove(spEndVal);
                        jPanel4.add(spEndVal);
                        jPanel5.remove(jLabelStartVal);
                        jPanel4.add(jLabelStartVal);
                        jPanel5.remove(jLabelEndVal);
                        jPanel4.add(jLabelEndVal);
                        break;
                    case 4:
                        spStartVal.setValue(knobGauge.getGreenLineStartValue());
                        spEndVal.setValue(knobGauge.getGreenLineEndValue());
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
            jLabelValue.setBounds(new Rectangle(10, 50, 54, 20));
            jLabelTotalA.setBounds(new Rectangle(10, 70, 54, 20));
            jLabelDist.setBounds(new Rectangle(10, 90, 54, 20));
            jLabelOffset.setBounds(new Rectangle(10, 110, 54, 20));
            jLabelRotationA.setBounds(new Rectangle(10, 130, 54, 20));

            jLabelValue.setText(Language.getString("Value"));
            jLabelTotalA.setText(Language.getString("TotalAngle"));
            jLabelDist.setText(Language.getString("Distance"));
            jLabelOffset.setText(Language.getString("Offset"));
            jLabelRotationA.setText(Language.getString("Rotation angle"));
            checkAxisInside.setText(Language.getString("Axis inside"));

            spValue.setBounds(62, 50, 54, 20);
            spTotalA.setBounds(62, 70, 54, 20);
            spDist.setBounds(62, 90, 54, 20);
            spOffset.setBounds(62, 110, 54, 20);
            spRotationA.setBounds(62, 130, 54, 20);
            checkAxisInside.setBounds(160, 150, 104, 20);


            jPanel1.add(spValue);
            spValue.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent evt) {
                    if (knobGauge != null) {
                        if (((Integer) spValue.getValue()).intValue() < 0) {
                            knobGauge.setValue(0);
                        } else if (((Integer) spValue.getValue()).intValue() > 100) {
                            knobGauge.setValue(100);
                        } else {
                            knobGauge.setValue(((Integer) spValue.getValue()).intValue());
                        }
                    }
                }
            });
            jPanel1.add(jLabelValue);
            jPanel1.add(jLabelTotalA);
            jPanel1.add(jLabelDist);
            jPanel1.add(jLabelOffset);
            jPanel1.add(jLabelRotationA);

            jPanel1.add(spTotalA);
            spTotalA.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent evt) {
                    if (knobGauge != null) {
                        knobGauge.setTotalAngle(((Integer) spTotalA.getValue()).intValue());
                    }
                }
            });
            jPanel1.add(spOffset);


            spOffset.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent evt) {
                    if (knobGauge != null) {
                        knobGauge.setHandOffset(((Integer) spOffset.getValue()).intValue());
                    }
                }
            });
            jPanel1.add(spDist);
            spDist.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent evt) {
                    if (knobGauge != null) {
                        knobGauge.setHandDistance(((Integer) spDist.getValue()).intValue());
                    }
                }
            });
            jPanel1.add(spRotationA);
            spRotationA.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent evt) {
                    if (knobGauge != null) {
                        knobGauge.setRotationAngle(((Integer) spRotationA.getValue()).intValue());
                    }
                }
            });

            jPanel1.add(checkAxisInside);
            checkAxisInside.setText("Axis inside");

            checkAxisInside.setBorderPaintedFlat(true);
            checkAxisInside.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent evt) {
                    if(checkAxisInside.isSelected())
                    {
                    knobGauge.setLabelsInside(true);
                    
                    } else
                    {
                        knobGauge.setLabelsInside(false);
                    }

                }
            });
            {
                jButtonHand = new JButton();
                jPanel1.add(jButtonHand);
                jButtonHand.setText("Hand");
                jButtonHand.setBounds(147, 50, 74, 20);
                jButtonHand.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent evt) {
                        knobGauge.getHand().setStyle(PointerStyle.DIAMOND);
                        GaugeSeriesPointerEditor.edit(knobGauge.getHand());

                    }
                });
            }
            {
                jButtonCenter = new JButton();
                jPanel1.add(jButtonCenter);
                jButtonCenter.setText("Center");
                jButtonCenter.setBounds(229, 51, 74, 20);
                jButtonCenter.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent evt) {
                        GaugeSeriesPointerEditor.edit(knobGauge.getCenter());
                    }
                });
            }
            {
                jButtonFaceBrush = new JButton();
                jPanel1.add(jButtonFaceBrush);
                jButtonFaceBrush.setText("FaceBrush");
                jButtonFaceBrush.setBounds(229, 83, 74, 20);
                jButtonFaceBrush.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent evt) {
                        BrushEditor.edit(knobGauge.getFaceBrush());
                    }
                });
            }
                        {
                jButtonHiglight = new JButton();
                jPanel1.add(jButtonHiglight);
                jButtonHiglight.setText("HighlightBrush");
                jButtonHiglight.setBounds(300, 150, 74, 20);
                jButtonHiglight.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent evt) {
                        BrushEditor.edit(knobGauge.getHighLightBrush());

                    }
                });
            }
            {
                jButtonEndPoint = new JButton();
                jPanel1.add(jButtonEndPoint);
                jButtonEndPoint.setText("EndPoint");
                jButtonEndPoint.setBounds(147, 82, 74, 20);
                jButtonEndPoint.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent evt) {
                        SeriesPointerEditorDialog.edit(knobGauge.getEndPoint());
                    }
                });
            }
            {
                int length = com.steema.teechart.themes.ColorPalettes.Palettes.length;
                String[] palettes = new String[length + 2];

                palettes[0] = "BlackPalette";
                palettes[1] = "BluesPalette";
                for (int i = 2; i < length + 2; i++) {
                    palettes[i] = com.steema.teechart.themes.ColorPalettes.PaletteNames[i - 2];
                }
                ComboBoxModel jComboPalettesModel =
                        new DefaultComboBoxModel(palettes);
                jComboPalettes = new JComboBox();
                jPanel1.add(jComboPalettes);
                jComboPalettes.setModel(jComboPalettesModel);
                jComboPalettes.setBounds(203, 123, 100, 21);
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
                jLabelPalettes.setBounds(149, 123, 54, 20);
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
            jMinorTicks.addMouseListener(new MouseAdapter() {

                public void mouseReleased(MouseEvent evt) {
                    GaugeSeriesPointerEditor.edit(knobGauge.getMinorTicks());
                }
            });

            jTicks.addMouseListener(new MouseAdapter() {

                public void mouseReleased(MouseEvent evt) {
                    GaugeSeriesPointerEditor.edit(knobGauge.getTicks());
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
            jSpinner1.setValue((int) Math.round(knobGauge.getTicks().getVertSize()));
            jSpinner2.setValue((int) Math.round(knobGauge.getMinorTickDistance()));
            jSpinner3.setValue(3);
            jSpinner4.setValue((int) Math.round(knobGauge.getMinorTicks().getVertSize()));
            jSpinner5.setValue((int) Math.round(knobGauge.getMaximum()));
            jSpinner6.setValue((int) Math.round(knobGauge.getMinimum()));


            jSpinner1.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent e) {
                    if (knobGauge != null) {
                        if ((Integer) jSpinner1.getValue() > 100) {
                            jSpinner1.setValue(100);
                        } else if ((Integer) jSpinner1.getValue() < 0) {
                            jSpinner1.setValue(0);
                        }
                        knobGauge.getTicks().setVertSize((Integer) jSpinner1.getValue());
                    }
                }
            });
            jSpinner2.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent e) {
                    if ((Integer) jSpinner2.getValue() > 100) {
                        jSpinner2.setValue(100);
                    } else if ((Integer) jSpinner2.getValue() < 0) {
                        jSpinner2.setValue(0);
                    }

                    if (knobGauge != null) {
                        knobGauge.setMinorTickDistance(((Integer) jSpinner2.getValue()));
                    }
                }
            });
            jSpinner3.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent e) {

                    if ((Integer) jSpinner3.getValue() > 100) {
                        jSpinner3.setValue(100);
                    } else if ((Integer) jSpinner3.getValue() < 0) {
                        jSpinner3.setValue(0);
                    }

                    if (knobGauge != null) {
                        knobGauge.getAxis().setMinorTickCount((Integer) jSpinner3.getValue());
                    }
                }
            });
            jSpinner4.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent e) {
                    if ((Integer) jSpinner4.getValue() > 100) {
                        jSpinner4.setValue(100);
                    } else if ((Integer) jSpinner4.getValue() < 0) {
                        jSpinner4.setValue(0);
                    }

                    if (knobGauge != null) {
                        knobGauge.getMinorTicks().setVertSize((Integer) jSpinner4.getValue());
                    }
                }
            });
            jSpinner5.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent e) {
                    if ((Integer) jSpinner5.getValue() > 1000) {
                        jSpinner5.setValue(1000);
                    } else if ((Integer) jSpinner5.getValue() < 0) {
                        jSpinner5.setValue(0);
                    }

                    if (knobGauge != null) {
                        knobGauge.setMaximum((Integer) jSpinner5.getValue());
                    }
                }
            });
            jSpinner6.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent e) {
                    if ((Integer) jSpinner6.getValue() > (Integer) jSpinner5.getValue()) {
                        jSpinner6.setValue((Integer) jSpinner5.getValue() - 1);
                    } else if ((Integer) jSpinner6.getValue() < 0) {
                        jSpinner6.setValue(0);
                    }

                    if (knobGauge != null) {
                        knobGauge.setMinimum((Integer) jSpinner6.getValue());
                    }
                }
            });


        }






        jTabbedPane1.add(jPanel3, Language.getString("Labels"));
        {
            jCheckLabelsVisible = new JCheckBox();
            jPanel3.add(jCheckLabelsVisible);
            jCheckLabelsVisible.setText(Language.getString("Show Labels"));
            jCheckLabelsVisible.setBounds(29, 14, 92, 18);
            jCheckLabelsVisible.setBorderPaintedFlat(true);
            jCheckLabelsVisible.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent evt) {

                    knobGauge.setLabelsVisible(jCheckLabelsVisible.isSelected());
                }
            });

            jTextFormat = new JTextField();
            jPanel3.add(jTextFormat);
            jTextFormat.setText("#,##0.###");
            jTextFormat.setBounds(29, 102, 102, 21);
            jTextFormat.addKeyListener(new KeyAdapter() {

                public void keyReleased(KeyEvent evt) {
                    knobGauge.setValueFormat(jTextFormat.getText());
                }
            });

            jLabelFormat = new JLabel();
            jPanel3.add(jLabelFormat);
            jLabelFormat.setText(Language.getString("Format"));
            jLabelFormat.setBounds(29, 80, 54, 20);

            jButtonFont = new JButton();
            jPanel3.add(jButtonFont);
            jButtonFont.setText("Font");
            jButtonFont.setBounds(186, 13, 58, 21);
            jButtonFont.addMouseListener(new MouseAdapter() {

                public void mouseReleased(MouseEvent evt) {
                    ChartFontEditor.edit(null, knobGauge.getAxis().getLabels().getFont());
                }
            });
            jCheckLabelsInside = new JCheckBox();
            jPanel3.add(jCheckLabelsInside);
            jCheckLabelsInside.setText("Labels Inside");
            jCheckLabelsInside.setBounds(29, 40, 92, 18);
            jCheckLabelsInside.setBorderPaintedFlat(true);
            jCheckLabelsInside.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent evt) {
                    knobGauge.setLabelsInside(jCheckLabelsInside.isSelected());
                }
            });
            jCheckLabelsRotate = new JCheckBox();
            jPanel3.add(jCheckLabelsRotate);
            jCheckLabelsRotate.setText("Labels Rotate");
            jCheckLabelsRotate.setBounds(29, 65, 92, 18);
            jCheckLabelsRotate.setBorderPaintedFlat(true);
            jCheckLabelsRotate.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent evt) {
                    knobGauge.setRotateLabels(jCheckLabelsRotate.isSelected());
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
                    GaugeSeriesPointerEditor.edit(knobGauge.getRedLine());
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
                    if (knobGauge != null) {
                        double dVal = getDoubleValue(spStartVal);
                        switch (jTabbedPane1.getSelectedIndex()) {
                            case 3:
                                knobGauge.setRedLineStartValue(dVal);
                                break;
                            case 4:
                                knobGauge.setGreenLineStartValue(dVal);
                                break;
                        }
                    }
                }
            });
            spEndVal.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent evt) {
                    if (knobGauge != null) {
                        double dVal = getDoubleValue(spEndVal);
                        switch (jTabbedPane1.getSelectedIndex()) {
                            case 3:
                                knobGauge.setRedLineEndValue(dVal);
                                break;
                            case 4:
                                knobGauge.setGreenLineEndValue(dVal);
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
                    if (!settingVals) {
                        double dVal = getDoubleValue(spStartVal);
                        switch (jTabbedPane1.getSelectedIndex()) {
                            case 3:
                                knobGauge.setRedLineStartValue(dVal);
                                break;
                            case 4:
                                knobGauge.setGreenLineStartValue(dVal);
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
                    GaugeSeriesPointerEditor.edit(knobGauge.getGreenLine());
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
                    BrushEditor.edit(knobGauge.getFrame().getOuterBand());
                }
            });

            jMiddleBand = new JButton();
            jPanel6.add(jMiddleBand);
            jMiddleBand.setText("MiddleBand");
            jMiddleBand.setBounds(140, 52, 68, 21);
            jMiddleBand.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    BrushEditor.edit(knobGauge.getFrame().getMiddleBand());
                }
            });

            jInnerBand = new JButton();
            jPanel6.add(jInnerBand);
            jInnerBand.setText("InnerBand");
            jInnerBand.setBounds(140, 84, 64, 21);
            jInnerBand.setSize(68, 21);
            jInnerBand.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    BrushEditor.edit(knobGauge.getFrame().getInnerBand());
                }
            });


        }
        jTabbedPane1.add(jPanel7, Language.getString("Numeric Gauge"));
        jPanel7.setLayout(null);
        {
            jVisibleNumeric = new JCheckBox();
            jPanel7.add(jVisibleNumeric);
            jVisibleNumeric.setText("Visible");
            jVisibleNumeric.setBounds(23, 34, 68, 21);
            jVisibleNumeric.setSize(68, 21);
            jVisibleNumeric.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent e) {
                    if (jVisibleNumeric.isSelected()) {
                        knobGauge.getNumericGauge().setVisible(true);
                        jAutoValueNumeric.setEnabled(knobGauge.getNumericGauge().getVisible());
                    } else {
                        knobGauge.getNumericGauge().setVisible(false);
                        jAutoValueNumeric.setEnabled(knobGauge.getNumericGauge().getVisible());
                    }
                }
            });

            jAutoValueNumeric = new JCheckBox();
            jPanel7.add(jAutoValueNumeric);
            jAutoValueNumeric.setText("Autovalue");
            jAutoValueNumeric.setBounds(23, 54, 90, 21);
            jAutoValueNumeric.setSize(90, 21);
            jAutoValueNumeric.setSelected(knobGauge.getAutoValueNumericGauge());
            jAutoValueNumeric.setEnabled(knobGauge.getNumericGauge().getVisible());
            jAutoValueNumeric.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent e) {
                    if (jAutoValueNumeric.isSelected()) {
                        knobGauge.setAutoValueNumericGauge(true);
                    } else {
                        knobGauge.setAutoValueNumericGauge(false);
                        doAutoValueNumeric(false);
                    }
                }
            });
            jAutoValueNumeric.setEnabled(knobGauge.getNumericGauge().getVisible());
        }
        jTabbedPane1.add(jPanel8, Language.getString("Linear Gauge"));
        jPanel8.setLayout(null);
        {
            knobGauge.setAutoPositionLinearGauge(true);
            jVisibleLinear = new JCheckBox();
            jPanel8.add(jVisibleLinear);
            jVisibleLinear.setText("Visible");
            jVisibleLinear.setBounds(23, 34, 68, 21);
            jVisibleLinear.setSize(68, 21);
            jVisibleLinear.setSelected(false);
            knobGauge.getLinearGauge().setVisible(false);
            jVisibleLinear.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent e) {
                    if (jVisibleLinear.isSelected()) {
                        knobGauge.getLinearGauge().setVisible(true);
                        jAutoValueLinear.setEnabled(true);
                        jAutoPositionLinear.setEnabled(true);
                        jHorizontalLinear.setEnabled(true);
                        if (jAutoPositionLinear.isSelected()) {
                            jSpinnerLeftLinear.setValue(knobGauge.getLinearGauge().getCustomBounds().getLeft());
                            jSpinnerTopLinear.setValue(knobGauge.getLinearGauge().getCustomBounds().getTop());
                            jSpinnerLeftLinear.setEnabled(false);
                            jSpinnerTopLinear.setEnabled(false);
                        } else {
                            jSpinnerLeftLinear.setValue(knobGauge.getLinearGauge().getCustomBounds().getLeft());
                            jSpinnerTopLinear.setValue(knobGauge.getLinearGauge().getCustomBounds().getTop());
                        }

                    } else {
                        knobGauge.getLinearGauge().setVisible(false);
                        jAutoValueLinear.setEnabled(false);
                        jAutoPositionLinear.setEnabled(false);
                        jHorizontalLinear.setEnabled(true);
                    }
                }
            });

            jAutoValueLinear = new JCheckBox();
            jPanel8.add(jAutoValueLinear);
            jAutoValueLinear.setText("Autovalue");
            jAutoValueLinear.setBounds(23, 54, 90, 21);
            jAutoValueLinear.setSize(90, 21);
            jAutoValueLinear.setSelected(knobGauge.getAutoValueLinearGauge());

            jAutoValueLinear.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent e) {
                    if (jAutoValueLinear.isSelected()) {
                        knobGauge.setAutoValueLinearGauge(true);
                    } else {
                        knobGauge.setAutoValueLinearGauge(false);
                        doAutoValueLinear(false);
                    }
                }
            });


            jHorizontalLinear = new JCheckBox();
            jPanel8.add(jHorizontalLinear);
            jHorizontalLinear.setText("Horizontal");
            jHorizontalLinear.setBounds(23, 74, 90, 21);
            jHorizontalLinear.setSize(90, 21);
            jHorizontalLinear.setSelected(false);
            jHorizontalLinear.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent e) {
                    if (jHorizontalLinear.isSelected()) {
                        knobGauge.getLinearGauge().setHorizontal(true);
                    } else {
                        knobGauge.getLinearGauge().setHorizontal(false);
                    }
                }
            });

            jAutoPositionLinear = new JCheckBox();
            jPanel8.add(jAutoPositionLinear);
            jAutoPositionLinear.setText("Autoposition");
            jAutoPositionLinear.setBounds(150, 14, 90, 21);
            jAutoPositionLinear.setSize(90, 21);
            jAutoPositionLinear.setSelected(true);
            jAutoPositionLinear.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent e) {
                    if (jAutoPositionLinear.isSelected()) {
                        knobGauge.setAutoPositionLinearGauge(true);
                        jSpinnerLeftLinear.setEnabled(false);
                        jSpinnerTopLinear.setEnabled(false);

                    } else {
                        knobGauge.setAutoPositionLinearGauge(false);
                        jSpinnerLeftLinear.setEnabled(true);
                        jSpinnerTopLinear.setEnabled(true);
                        jSpinnerLeftLinear.setValue(knobGauge.getLinearGauge().getCustomBounds().getLeft());
                        jSpinnerTopLinear.setValue(knobGauge.getLinearGauge().getCustomBounds().getTop());
                    }
                }
            });

            jLabelLeftLinear = new JLabel();
            jLabelLeftLinear.setText("Left:");
            jPanel8.add(jLabelLeftLinear);
            jLabelLeftLinear.setBounds(120, 74, 50, 21);
            jLabelLeftLinear.setSize(50, 21);

            jSpinnerLeftLinear = new JSpinner();
            jPanel8.add(jSpinnerLeftLinear);
            jSpinnerLeftLinear.setBounds(150, 74, 50, 21);
            jSpinnerLeftLinear.setSize(50, 21);

            jLabelTopLinear = new JLabel();
            jLabelTopLinear.setText("Top:");
            jPanel8.add(jLabelTopLinear);
            jLabelTopLinear.setBounds(230, 74, 50, 21);
            jLabelTopLinear.setSize(50, 21);

            jSpinnerTopLinear = new JSpinner();
            jPanel8.add(jSpinnerTopLinear);
            jSpinnerTopLinear.setBounds(260, 74, 50, 21);
            jSpinnerTopLinear.setSize(50, 21);

            jSpinnerLeftLinear.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent e) {
                    if ((Integer) (jSpinnerLeftLinear.getValue()) < 0) {
                        jSpinnerLeftLinear.setValue(0);
                    } else {
                        com.steema.teechart.Rectangle tmpR;
                        tmpR = knobGauge.getLinearGauge().getCustomBounds();
                        tmpR.x = (Integer) (jSpinnerLeftLinear.getValue());
                        knobGauge.getLinearGauge().setCustomBounds(tmpR);
                        knobGauge.getChart().getParent().refreshControl();

                    }
                }
            });
            jSpinnerTopLinear.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent e) {
                    if ((Integer) (jSpinnerTopLinear.getValue()) < 0) {
                        jSpinnerTopLinear.setValue(0);
                    } else {
                        com.steema.teechart.Rectangle tmpR;
                        tmpR = knobGauge.getLinearGauge().getCustomBounds();
                        tmpR.y = (Integer) (jSpinnerTopLinear.getValue());
                        knobGauge.getLinearGauge().setCustomBounds(tmpR);
                        knobGauge.getChart().getParent().refreshControl();
                    }
                }
            });
            jSpinnerLeftLinear.setEnabled(false);
            jSpinnerTopLinear.setEnabled(false);
            jHorizontalLinear.setEnabled(false);
            jAutoValueLinear.setEnabled(false);


        }


    }

    protected void SetGaugeColorPalette(int index) {
        switch (index) {
            case 0:
                knobGauge.setGaugeColorPalette(com.steema.teechart.styles.CustomGauge.BlackPalette);
                break;
            case 1:
                knobGauge.setGaugeColorPalette(com.steema.teechart.styles.CustomGauge.BluesPalette);
                break;
            default:
                knobGauge.setGaugeColorPalette(com.steema.teechart.themes.ColorPalettes.Palettes[index - 2]);
                break;
        }
    }

    private double getDoubleValue(JSpinner spinner) {
        if (spinner.getValue().getClass() == Integer.class) {
            return ((Integer) spinner.getValue()).intValue() * 1.0;
        } else {
            return ((Double) spinner.getValue()).doubleValue();
        }
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
        knobGauge = (KnobGauge) series;

        spValue.setValue((int) knobGauge.getValue());
        spTotalA.setValue((int) knobGauge.getTotalAngle());
        spRotationA.setValue((int) knobGauge.getRotationAngle());
        spDist.setValue((int) knobGauge.getHandDistance());
        spOffset.setValue((int) knobGauge.getHandOffset());
        jCheckLabelsVisible.setSelected(knobGauge.getLabelsVisible());
        jCheckLabelsInside.setSelected(knobGauge.getLabelsInside());
        jCheckLabelsRotate.setSelected(knobGauge.getRotateLabels());
        jTextFormat.setText(knobGauge.getValueFormat());

    }

    public void componentResized(ComponentEvent e) {
        Object obj = e.getSource();
        if (obj == jPanel1) {
            spValue.setValue((int) Math.round(knobGauge.getValue()));
            spTotalA.setValue((int) Math.round(knobGauge.getTotalAngle()));
            spRotationA.setValue((int) Math.round(knobGauge.getRotationAngle()));
            spDist.setValue((int) Math.round(knobGauge.getHandDistance()));
            spOffset.setValue((int) Math.round(knobGauge.getHandOffset()));
            jCheckLabelsInside.setSelected(knobGauge.getLabelsInside());

        } else if (obj == jPanel3) {

            jCheckLabelsVisible.setSelected(knobGauge.getLabelsVisible());
            jCheckLabelsInside.setSelected(knobGauge.getLabelsInside());
            jCheckLabelsRotate.setSelected(knobGauge.getRotateLabels());
        }


    }

    public void componentMoved(ComponentEvent e) {
        Object obj = e.getSource();
        if (obj == jPanel1) {
            spValue.setValue((int) knobGauge.getValue());
            spTotalA.setValue((int) knobGauge.getTotalAngle());
            spRotationA.setValue((int) knobGauge.getRotationAngle());
            spDist.setValue((int) knobGauge.getHandDistance());
            spOffset.setValue((int) knobGauge.getHandOffset());
            jCheckLabelsInside.setSelected(knobGauge.getLabelsInside());

        } else if (obj == jPanel3) {

            jCheckLabelsVisible.setSelected(knobGauge.getLabelsVisible());
            jCheckLabelsInside.setSelected(knobGauge.getLabelsInside());
            jCheckLabelsRotate.setSelected(knobGauge.getRotateLabels());
        }

    }

    public void componentShown(ComponentEvent e) {
        Object obj = e.getSource();
        if (obj == jPanel1) {
            spValue.setValue((int) knobGauge.getValue());
            spTotalA.setValue((int) knobGauge.getTotalAngle());
            spRotationA.setValue((int) knobGauge.getRotationAngle());
            spDist.setValue((int) knobGauge.getHandDistance());
            spOffset.setValue((int) knobGauge.getHandOffset());
            jCheckLabelsInside.setSelected(knobGauge.getLabelsInside());

        } else if (obj == jPanel3) {

            jCheckLabelsVisible.setSelected(knobGauge.getLabelsVisible());
            jCheckLabelsInside.setSelected(knobGauge.getLabelsInside());
            jCheckLabelsRotate.setSelected(knobGauge.getRotateLabels());
        }


    }

    public void componentHidden(ComponentEvent e) {

        Object obj = e.getSource();
        if (obj == jPanel1) {
            spValue.setValue((int) knobGauge.getValue());
            spTotalA.setValue((int) knobGauge.getTotalAngle());
            spRotationA.setValue((int) knobGauge.getRotationAngle());
            spDist.setValue((int) knobGauge.getHandDistance());
            spOffset.setValue((int) knobGauge.getHandOffset());
            jCheckLabelsInside.setSelected(knobGauge.getLabelsInside());

        } else if (obj == jPanel3) {

            jCheckLabelsVisible.setSelected(knobGauge.getLabelsVisible());
            jCheckLabelsInside.setSelected(knobGauge.getLabelsInside());
            jCheckLabelsRotate.setSelected(knobGauge.getRotateLabels());
        }

    }

    private void doAutoValueLinear(boolean b) {
        if (!b && !knobGauge.getAutoValueLinearGauge()) {

            ActionListener l = new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (up) {
                        value2 += 1;

                    } else {
                        value2 -= 1;
                    }
                    if (value2 > 99) {
                        up = false;
                    } else if (value2 < 1) {
                        up = true;
                    }
                    knobGauge.getLinearGauge().setValue(value2);
                }
            };
            Timer t = new Timer(100, l);
            t.addActionListener(l);
            t.start();

        }
    }

    private void doAutoValueNumeric(boolean b) {
        if (!b && !knobGauge.getAutoValueNumericGauge()) {

            ActionListener l = new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (up) {
                        value2 += 1;

                    } else {
                        value2 -= 1;
                    }
                    if (value2 > 99) {
                        up = false;
                    } else if (value2 < 1) {
                        up = true;
                    }
                    knobGauge.getNumericGauge().setValue(value2);
                }
            };
            Timer t = new Timer(100, l);
            t.addActionListener(l);
            t.start();

        }
    }
}
