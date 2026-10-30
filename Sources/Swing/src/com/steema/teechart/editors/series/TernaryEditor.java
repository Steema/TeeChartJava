package com.steema.teechart.editors.series;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.BorderLayout;

import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.styles.Series;
import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.PointerStyle;
import com.steema.teechart.styles.Ternary;
import com.steema.teechart.styles.Ternary.TernaryLegendStyle;
import com.steema.teechart.styles.Ternary.TernaryStyle;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.MouseListener;

public class TernaryEditor extends BaseSeriesEditor implements ComponentListener, ActionListener, MouseListener, ChangeListener
{

    private Ternary ternary = new Ternary();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JLabel jLabelTernaryStyle = new JLabel();
    JLabel jLabelTernaryTransparency = new JLabel();
    JLabel jLabelTernaryLegendStyle = new JLabel();
    JLabel jLabelTernaryVertexATitle = new JLabel();
    JLabel jLabelTernaryVertexBTitle = new JLabel();
    JLabel jLabelTernaryVertexCTitle = new JLabel();
    JLabel jLabelTernaryAxisIncrement = new JLabel();
    JComboBox jComboTernaryStyle;
    JComboBox jComboTernaryLegendStyle;
    JComboBox jComboAxisIncrement;
    JSpinner jSpinnerTransparency = new JSpinner();
    JTextField jTextBoxVertexA = new JTextField();
    JTextField jTextBoxVertexB = new JTextField();
    JTextField jTextBoxVertexC = new JTextField();
    JButton jButtonVertexAChange = new JButton();
    JButton jButtonVertexBChange = new JButton();
    JButton jButtonVertexCChange = new JButton();
    JCheckBox jPointsVisible = new JCheckBox();
    JCheckBox jPoints3D = new JCheckBox();
    JCheckBox jPointsDark3D = new JCheckBox();
    JCheckBox jPointsInflateMargins = new JCheckBox();
    JCheckBox jPointsColorEach = new JCheckBox();
    JLabel jPointerStyle = new JLabel();
    JLabel jPointerTransparency = new JLabel();
    JComboBox jComboPointerStyle;
    JSpinner jSpinnerPointerTransparency;

    public TernaryEditor()
    {
        super();
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public TernaryEditor(Ternary c)
    {
        super();
        try
        {
            jbInit();
            ternary = c;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception
    {
        this.setLayout(borderLayout1);
        jPanel1.setLayout(null);
        jPanel2.setLayout(null);
        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(465, 261));
        jTabbedPane1.addChangeListener(new ChangeListener()
        {

            public void stateChanged(ChangeEvent evt)
            {
            }
        });
        jTabbedPane1.add(jPanel1, Language.getString("Ternary Editor"));
        jPanel1.setPreferredSize(new java.awt.Dimension(35, 23));
        {
            jComboTernaryStyle = new JComboBox(new String[]
                    {
                        "Bubble", "Point"
                    });
            jComboTernaryStyle.setSelectedIndex(0);
            jComboTernaryStyle.addActionListener(this);
            jComboTernaryLegendStyle = new JComboBox(new String[]
                    {
                        "Bubble", "BubbleWeight", "Values", "ValuesBubble", "ValuesBubbleWeight", "Weight"
                    });
            jComboTernaryLegendStyle.setSelectedIndex(1);
            jComboTernaryLegendStyle.addActionListener(this);
            jComboAxisIncrement = new JComboBox(new String[]
                    {
                        "1", "2", "5", "10", "20", "50"
                    });
            jComboAxisIncrement.setSelectedIndex(3);
            jComboAxisIncrement.addActionListener(this);
            jTextBoxVertexA.setText(ternary.getVertexTitles()[0].toString());
            jButtonVertexAChange.addMouseListener(this);
            jButtonVertexBChange.addMouseListener(this);
            jButtonVertexCChange.addMouseListener(this);
            jTextBoxVertexB.setText(ternary.getVertexTitles()[1].toString());
            jTextBoxVertexC.setText(ternary.getVertexTitles()[2].toString());

            jLabelTernaryStyle.setBounds(100, 10, 94, 20);
            jLabelTernaryLegendStyle.setBounds(100, 30, 124, 20);
            jLabelTernaryTransparency.setBounds(100, 50, 94, 20);
            jLabelTernaryVertexATitle.setBounds(100, 70, 94, 20);
            jLabelTernaryVertexBTitle.setBounds(100, 90, 94, 20);
            jLabelTernaryVertexCTitle.setBounds(100, 110, 94, 20);
            jLabelTernaryAxisIncrement.setBounds(100, 130, 94, 20);
            jLabelTernaryStyle.setText("Ternary Style:");
            jLabelTernaryLegendStyle.setText("Ternary Legend Style:");
            jLabelTernaryTransparency.setText("Transparency:");
            jLabelTernaryVertexATitle.setText("Vertex A Title:");

            jLabelTernaryVertexBTitle.setText("Vertex B Title:");
            jLabelTernaryVertexCTitle.setText("Vertex C Title:");
            jLabelTernaryAxisIncrement.setText("Axis Increment:");
            jComboTernaryStyle.setBounds(240, 10, 94, 20);
            jComboTernaryLegendStyle.setBounds(240, 30, 94, 20);
            jSpinnerTransparency.setBounds(240, 50, 94, 20);
            jTextBoxVertexA.setBounds(240, 70, 94, 20);
            jTextBoxVertexB.setBounds(240, 90, 94, 20);
            jTextBoxVertexC.setBounds(240, 110, 94, 20);
            jButtonVertexAChange.setBounds(350, 70, 94, 20);
            jButtonVertexAChange.setText("Change");
            jButtonVertexBChange.setText("Change");
            jButtonVertexCChange.setText("Change");
            jButtonVertexBChange.setBounds(350, 90, 94, 20);
            jButtonVertexCChange.setBounds(350, 110, 94, 20);
            jComboAxisIncrement.setBounds(240, 130, 94, 20);
            jSpinnerTransparency.setModel(new SpinnerNumberModel(ternary.getPointer().getTransparency(), 0, 100, 1));
            jSpinnerTransparency.addChangeListener(this);
            jPanel1.add(jLabelTernaryStyle);
            jPanel1.add(jLabelTernaryTransparency);
            jPanel1.add(jLabelTernaryLegendStyle);
            jPanel1.add(jLabelTernaryVertexATitle);
            jPanel1.add(jLabelTernaryVertexBTitle);
            jPanel1.add(jLabelTernaryVertexCTitle);
            jPanel1.add(jLabelTernaryAxisIncrement);
            jPanel1.add(jComboTernaryStyle);
            jPanel1.add(jComboTernaryLegendStyle);
            jPanel1.add(jSpinnerTransparency);
            jPanel1.add(jTextBoxVertexA);
            jPanel1.add(jTextBoxVertexB);
            jPanel1.add(jTextBoxVertexC);
            jPanel1.add(jComboAxisIncrement);
            jPanel1.add(jButtonVertexAChange);
            jPanel1.add(jButtonVertexBChange);
            jPanel1.add(jButtonVertexCChange);



        }

        jTabbedPane1.add(jPanel2, Language.getString("Points"));
        jPanel2.setPreferredSize(new java.awt.Dimension(35, 23));
        {

            jPointsVisible.setBounds(100, 10, 80, 20);
            jPointsVisible.setSelected(ternary.getPointer().getVisible());
            jPointsVisible.addChangeListener(this);
            jPointsVisible.setText("Visible");
            jPanel2.add(jPointsVisible);
            jPoints3D.setBounds(100, 30, 124, 20);
            jPoints3D.setSelected(ternary.getPointer().getDraw3D());
            jPoints3D.setText("3D");
            jPoints3D.addChangeListener(this);
            jPanel2.add(jPoints3D);
            jPointsDark3D.setBounds(100, 50, 124, 20);
            jPointsDark3D.setText("Dark 3D");
            jPointsDark3D.setSelected(ternary.getPointer().getDraw3D());
            jPointsDark3D.addChangeListener(this);
            jPanel2.add(jPointsDark3D);
            jPointsInflateMargins.setBounds(100, 70, 124, 20);
            jPointsInflateMargins.setText("Inflate Margins");
            jPointsInflateMargins.setSelected(ternary.getPointer().getInflateMargins());
            jPointsInflateMargins.addChangeListener(this);
            jPanel2.add(jPointsInflateMargins);
            jPointsColorEach.setBounds(100, 90, 124, 20);
            jPointsColorEach.setText("Color Each");
            jPointsColorEach.setSelected(ternary.getColorEach());
            jPointsColorEach.addChangeListener(this);
            jPanel2.add(jPointsColorEach);
            jPointerStyle.setBounds(240, 10, 60, 20);
            jPointerStyle.setText("Style: ");
            jPanel2.add(jPointerStyle);
            jComboPointerStyle = new JComboBox(new String[]
                    {
                        "Circle", "Triangle",
                        "Down Triangle", "Rectangle","Diamond"
                    });

            jComboPointerStyle.setBounds(300, 10, 124, 20);
            jComboPointerStyle.addActionListener(this);
            jPanel2.add(jComboPointerStyle);
            jPointerTransparency.setBounds(100, 110, 80, 20);
            jPointerTransparency.setText("Transparency: ");
            jPanel2.add(jPointerTransparency);
            jSpinnerPointerTransparency = new JSpinner();
            jSpinnerPointerTransparency.setBounds(190, 110, 124, 20);
            jSpinnerPointerTransparency.setModel(new SpinnerNumberModel(ternary.getPointer().getTransparency(),0,100,1));
            jSpinnerPointerTransparency.addChangeListener(this);
            jPanel2.add(jSpinnerPointerTransparency);
        }



    }

    @Override
    public void setSeries(Series series, JTabbedPane tabs)
    {
    }

    public void componentResized(ComponentEvent e)
    {
    }

    public void componentMoved(ComponentEvent e)
    {
    }

    public void componentShown(ComponentEvent e)
    {
    }

    public void componentHidden(ComponentEvent e)
    {
    }

    public void actionPerformed(ActionEvent e)
    {
        Object aux = e.getSource();
        if (aux == jComboTernaryStyle)
        {
            switch (jComboTernaryStyle.getSelectedIndex())
            {
                case 0:
                    ternary.setTernaryStyle(TernaryStyle.Bubble);
                    break;
                case 1:
                    ternary.setTernaryStyle(TernaryStyle.Point);
                    break;

            }
        }
        else if (aux == jComboTernaryLegendStyle)
        {
            switch (jComboTernaryLegendStyle.getSelectedIndex())
            {
                case 0:
                    ternary.setTernaryLegendStyle(TernaryLegendStyle.Bubble);
                    break;
                case 1:
                    ternary.setTernaryLegendStyle(TernaryLegendStyle.BubbleWeight);
                    break;
                case 2:
                    ternary.setTernaryLegendStyle(TernaryLegendStyle.Values);
                    break;
                case 3:
                    ternary.setTernaryLegendStyle(TernaryLegendStyle.ValuesBubble);
                    break;
                case 4:
                    ternary.setTernaryLegendStyle(TernaryLegendStyle.ValuesBubbleWeight);
                    break;
                case 5:
                    ternary.setTernaryLegendStyle(TernaryLegendStyle.Weight);
                    break;

            }
        }
        else if (jComboAxisIncrement == aux)
        {
            switch (jComboAxisIncrement.getSelectedIndex())
            {
                case 0:
                    ternary.getChart().getAxes().getBottom().setIncrement(1);
                    break;
                case 1:
                    ternary.getChart().getAxes().getBottom().setIncrement(2);
                    break;
                case 2:
                    ternary.getChart().getAxes().getBottom().setIncrement(5);
                    break;
                case 3:
                    ternary.getChart().getAxes().getBottom().setIncrement(10);
                    break;
                case 4:
                    ternary.getChart().getAxes().getBottom().setIncrement(20);
                    break;
                case 5:
                    ternary.getChart().getAxes().getBottom().setIncrement(50);
                    break;

            }

        }
         else if (jComboPointerStyle == aux)
        {
            switch (jComboPointerStyle.getSelectedIndex())
            {
                case 0:
                    ternary.getPointer().setStyle(PointerStyle.CIRCLE);
                    break;
                case 1:
                  ternary.getPointer().setStyle(PointerStyle.TRIANGLE);
                    break;
                case 2:
                    ternary.getPointer().setStyle(PointerStyle.DOWNTRIANGLE);
                    break;
                case 3:
                   ternary.getPointer().setStyle(PointerStyle.RECTANGLE);
                    break;
                case 4:
                 ternary.getPointer().setStyle(PointerStyle.DIAMOND);
                    break;
            }

        }
        else if (aux == jTextBoxVertexA)
        {
            ternary.setVertexTitle(jTextBoxVertexA.getText(), 0);
        }
        else if (aux == jTextBoxVertexB)
        {
            ternary.setVertexTitle(jTextBoxVertexB.getText(), 0);
        }
        else if (aux == jTextBoxVertexC)
        {
            ternary.setVertexTitle(jTextBoxVertexC.getText(), 0);
        }

    }

    public void mouseClicked(MouseEvent e)
    {
        Object aux = e.getSource();
        if (aux == jButtonVertexAChange)
        {
            ternary.setVertexTitle(jTextBoxVertexA.getText(), 0);
            ternary.getPointer().setStyle(PointerStyle.DIAMOND);
        }
        else if (aux == jButtonVertexBChange)
        {
            ternary.setVertexTitle(jTextBoxVertexB.getText(), 1);
        }
        else if (aux == jButtonVertexCChange)
        {
            ternary.setVertexTitle(jTextBoxVertexC.getText(), 2);
        }

    }

    public void mousePressed(MouseEvent e)
    {
    }

    public void mouseReleased(MouseEvent e)
    {
    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }

    public void stateChanged(ChangeEvent e)
    {
        Object aux = e.getSource();
        if (aux == jSpinnerTransparency)
        {
            ternary.getPointer().setTransparency(((Integer) jSpinnerTransparency.getValue()));
            ternary.refreshSeries();
        }
        else if(aux == jSpinnerPointerTransparency)
        {
            ternary.getPointer().setTransparency(((Integer) jSpinnerPointerTransparency.getValue()));
            ternary.refreshSeries();
        }
        else if (aux == jPointsVisible)
        {
            if (jPointsVisible.isSelected())
            {
                ternary.getPointer().setVisible(true);
            }
            else
            {
                ternary.getPointer().setVisible(false);
            }
        }
        else if (aux == jPoints3D)
        {
            if (jPoints3D.isSelected())
            {
                ternary.getPointer().setDraw3D(true);
            }
            else
            {
                ternary.getPointer().setDraw3D(false);
            }
        }
        else if (aux == jPointsDark3D)
        {
            if (jPointsDark3D.isSelected())
            {
                ternary.getPointer().setDark3D(true);
            }
            else
            {
                ternary.getPointer().setDark3D(false);
            }
        }
        else if (aux == jPointsInflateMargins)
        {
            if (jPointsInflateMargins.isSelected())
            {
                ternary.getPointer().setInflateMargins(true);
            }
            else
            {
                ternary.getPointer().setInflateMargins(false);
            }
        }
        else if(jPointsColorEach.isSelected())
        {
                ternary.setColorEach(true);
                ternary.refreshSeries();
           }
            else
            {
                ternary.setColorEach(false);
                ternary.refreshSeries();
            }
        }
    }

