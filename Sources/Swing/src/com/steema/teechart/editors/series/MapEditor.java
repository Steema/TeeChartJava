package com.steema.teechart.editors.series;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.Color;

import javax.swing.border.BevelBorder;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JTabbedPane;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.DefaultListModel;

import com.steema.teechart.editors.ChartListBox;
import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.Map;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.ButtonColor;
import com.steema.teechart.editors.ChartPenEditor;
import com.steema.teechart.styles.Polygon;

import com.steema.teechart.languages.Language;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: Gauges series editor</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class MapEditor extends BaseSeriesEditor {


    private Map map;
    private boolean changingText;

    private GridEditor grid3DEditor;
    private boolean setting;
    ChartPenEditor penEditor = new ChartPenEditor();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
//    ChartListBox lbShapes = new ChartListBox();
    ButtonPen bMapBrush = new ButtonPen();
    Border border1 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    JCheckBox cbEach = new JCheckBox();
    Border border2 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border3 = new TitledBorder(border2, Language.getString("Alignment"));
    Border border4 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border5 = new TitledBorder(border4, Language.getString("Alignment"));
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
    Border border18 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border19 = new TitledBorder(border18, Language.getString("Horiz_Alignment"));
    Border border20 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border21 = new TitledBorder(border20, Language.getString("Alignment"));
    Border border22 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border23 = new TitledBorder(border22, Language.getString("Alignment"));
    Border border24 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border25 = new TitledBorder(border24, Language.getString("Horiz_Alignment"));
    ButtonPen buttonPen1 = new ButtonPen();
    JPanel jPanel2 = new JPanel();
    JScrollPane jScrollPane1 = new JScrollPane();
    ChartListBox chartListBox1 = new ChartListBox();
    Border border26 = BorderFactory.createEmptyBorder();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JTabbedPane jTabbedPane2 = new JTabbedPane();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JTextField tbText = new JTextField();
    JTextField tbZ = new JTextField();
    JLabel jLabel3 = new JLabel();
    JLabel lShapeIndex = new JLabel();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    ButtonPen buttonPen2 = new ButtonPen();
    ButtonColor buttonColor1 = new ButtonColor();
    JCheckBox cbGlobalPen = new JCheckBox();
    JCheckBox cbGlobalBrush = new JCheckBox();
    JCheckBox cbClosed = new JCheckBox();
    JCheckBox cbVisible = new JCheckBox();
    JLabel jLabel4 = new JLabel();
    JSpinner spTransparency = new JSpinner();

    public MapEditor() {
        try {
            jbInit();

      // TODO   this.ChartListBox1.Click += new System.EventHandler(this.ChartListBox1_Click);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        map = (Map) series;

        changingText = false;

        if (map != null) {
            buttonPen2.setPen(map.getPen());
            jTabbedPane1.setSelectedIndex(1);
            jTabbedPane1.setSelectedIndex(0);
            enableTabs();
            jTabbedPane2.setSelectedIndex(1);
            if(map.getShapes().size() > 0) {
                jTabbedPane2.setSelectedIndex(0);
            }
        }

        if (grid3DEditor==null) {
            grid3DEditor = new GridEditor();
            grid3DEditor.setSeries(series, tabs);
            tabs.add(Language.getString("Grid_3D"), grid3DEditor);
        }

    }

   private void enableTabs() {
       boolean tmp = (map != null) && (map.getShapes().size() > 0);
       for(int i = 0; i < jPanel4.getComponentCount(); ++i) {
           jPanel4.getComponent(i).setEnabled(tmp);
       }
   }


    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        setting = true;
        jPanel1.setLayout(null);
        bMapBrush.setBounds(new Rectangle(19, 36, 75, 23));
        bMapBrush.setMnemonic(Language.getString("R").charAt(0));
        bMapBrush.setText(Language.getString("Brush"));
        bMapBrush.addActionListener(new MapEditor_bMapBrush_actionAdapter(this));
        cbEach.setMnemonic(Language.getString("E").charAt(0));
        cbEach.setText(Language.getString("Color_each"));
        cbEach.setBounds(new Rectangle(6, 6, 81, 23));
        buttonPen1.setBounds(new Rectangle(18, 9, 76, 23));
        buttonPen1.setMnemonic(Language.getString("B").charAt(0));
        buttonPen1.setText(Language.getString("Border"));
        buttonPen1.addActionListener(new MapEditor_buttonPen1_actionAdapter(this));
        jPanel2.setLayout(null);
        chartListBox1.setBorder(BorderFactory.createLoweredBevelBorder());
        chartListBox1.addListSelectionListener(new
                                               MapEditor_chartListBox1_listSelectionAdapter(this));
        jScrollPane1.setViewportBorder(border26);
        jScrollPane1.setBounds(new Rectangle(3, 2, 93, 192));
        jLabel1.setDisplayedMnemonic(Language.getString("T").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText(Language.getString("Text"));
        jLabel1.setBounds(new Rectangle(23, 18, 34, 15));
        jPanel3.setLayout(null);
        jLabel2.setDisplayedMnemonic(Language.getString("Z").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setText(Language.getString("Z"));
        jLabel2.setBounds(new Rectangle(23, 42, 34, 17));
        tbText.setBounds(new Rectangle(67, 17, 90, 20));
        tbText.addKeyListener(new MapEditor_tbText_keyAdapter(this));
        tbText.addActionListener(new MapEditor_tbText_actionAdapter(this));
        tbZ.setText("0");
        tbZ.setHorizontalAlignment(SwingConstants.RIGHT);
        tbZ.setBounds(new Rectangle(68, 40, 89, 20));
        tbZ.addActionListener(new MapEditor_tbZ_actionAdapter(this));
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setText(Language.getString("Shape_Index"));
        jLabel3.setBounds(new Rectangle(18, 76, 86, 15));
        lShapeIndex.setText("0");
        lShapeIndex.setBounds(new Rectangle(117, 73, 68, 21));
        jButton1.setBounds(new Rectangle(17, 39, 79, 23));
        jButton1.setMnemonic(Language.getString("U").charAt(0));
        jButton1.setText(Language.getString("Brush"));
        jButton1.addActionListener(new MapEditor_jButton1_actionAdapter(this));
        jButton2.setBounds(new Rectangle(193, 13, 81, 23));
        jButton2.setMnemonic(Language.getString("G").charAt(0));
        jButton2.setText(Language.getString("Gradient"));
        jButton2.addActionListener(new MapEditor_jButton2_actionAdapter(this));
        jPanel4.setLayout(null);
        buttonPen2.setBounds(new Rectangle(17, 13, 79, 23));
        buttonPen2.setMnemonic(Language.getString("B").charAt(0));
        buttonPen2.setText(Language.getString("Border"));
        buttonPen2.addActionListener(new MapEditor_buttonPen2_actionAdapter(this));
        buttonColor1.setBounds(new Rectangle(18, 65, 78, 23));
        buttonColor1.setMnemonic(Language.getString("C").charAt(0));
        buttonColor1.setText(Language.getString("Color"));
        buttonColor1.addActionListener(new MapEditor_buttonColor1_actionAdapter(this));
        cbGlobalPen.setMnemonic(Language.getString("O").charAt(0));
        cbGlobalPen.setText(Language.getString("Global"));
        cbGlobalPen.setBounds(new Rectangle(102, 13, 83, 23));
        cbGlobalPen.addActionListener(new MapEditor_cbGlobalPen_actionAdapter(this));
        cbGlobalBrush.setMnemonic(Language.getString("B").charAt(0));
        cbGlobalBrush.setText(Language.getString("Global"));
        cbGlobalBrush.setBounds(new Rectangle(102, 39, 83, 23));
        cbGlobalBrush.addActionListener(new
                                        MapEditor_cbGlobalBrush_actionAdapter(this));
        cbClosed.setMnemonic(Language.getString("S").charAt(0));
        cbClosed.setText(Language.getString("Closed"));
        cbClosed.setBounds(new Rectangle(193, 39, 83, 23));
        cbClosed.addActionListener(new MapEditor_cbClosed_actionAdapter(this));
        cbVisible.setMnemonic(Language.getString("V").charAt(0));
        cbVisible.setText(Language.getString("Visible"));
        cbVisible.setBounds(new Rectangle(193, 65, 83, 23));
        cbVisible.addActionListener(new MapEditor_cbVisible_actionAdapter(this));
        jLabel4.setDisplayedMnemonic(Language.getString("T").charAt(0));
        jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel4.setLabelFor(spTransparency);
        jLabel4.setText(Language.getString("Transparency"));
        jLabel4.setBounds(new Rectangle(92, 98, 97, 15));
        spTransparency.setBounds(new Rectangle(197, 94, 54, 21));
        spTransparency.addChangeListener(new
                                         MapEditor_spTransparency_changeAdapter(this));
        jTabbedPane1.addChangeListener(new MapEditor_jTabbedPane1_changeAdapter(this));
        jTabbedPane2.setBounds(new Rectangle(100, 0, 295, 274));
        jPanel1.add(buttonPen1);
        jPanel1.add(bMapBrush);
        jTabbedPane1.add(jPanel2, Language.getString("Shapes"));
        jTabbedPane1.add(jPanel1, Language.getString("Global"));
        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane2.add(jPanel3, Language.getString("Values"));
        jPanel3.add(jLabel1);
        jPanel3.add(tbText);
        jPanel3.add(tbZ);
        jPanel3.add(jLabel2);
        jPanel3.add(jLabel3);
        jPanel3.add(lShapeIndex);
        jPanel4.add(buttonPen2);
        jPanel4.add(jButton2);
        jPanel4.add(jButton1);
        jPanel4.add(buttonColor1);
        jPanel4.add(cbGlobalPen);
        jPanel4.add(cbGlobalBrush);
        jPanel4.add(cbClosed);
        jPanel4.add(cbVisible);
        jPanel4.add(spTransparency);
        jPanel4.add(jLabel4);
        jPanel2.add(jScrollPane1, null);
        jScrollPane1.getViewport().add(chartListBox1);
        jTabbedPane2.add(jPanel4, Language.getString("Format"));
        jPanel2.add(jTabbedPane2, null);
        chartListBox1.setShowSeriesIcon(false);
        setting = false;
    }

    private Polygon SelectedShape() {
        Polygon result = null;
        int index = chartListBox1.getSelectedIndex();
        if(index >= 0) {
            result = map.getPolygon(index);
        }
        return result;
    }

    public void buttonPen1_actionPerformed(ActionEvent e) {
        map.getPen().assign(buttonPen2.getPen());
    }

    public void chartListBox1_valueChanged(ListSelectionEvent e) {
        enableTabs();

        Polygon tmpPol = SelectedShape();
        if(tmpPol!=null) {
            lShapeIndex.setText(Integer.toString(tmpPol.getIndex()));
            buttonPen2.setPen(tmpPol.getPen());
            if(tmpPol.getParentBrush()) {
                buttonColor1.setColor(tmpPol.getColor(tmpPol.getIndex()));
            }
            else {
                buttonColor1.setColor(tmpPol.getPoints().getColor());
            }
            cbGlobalPen.setSelected(tmpPol.getParentPen());
            cbGlobalBrush.setSelected(tmpPol.getParentBrush());

            changingText=true;

            tbText.setText(tmpPol.getText());
            tbZ.setText(Double.toString(tmpPol.getZ()));

            changingText = false;

            cbClosed.setSelected(tmpPol.getClosed());
            cbVisible.setSelected(tmpPol.visible());

            spTransparency.setValue(new Integer(tmpPol.getTransparency()));
         }
         else {
            lShapeIndex.setText("");
            buttonPen2.setPen(null);
            //buttonColor1.setColor(Color.black);
            changingText=true;
            tbText.setText("");
            tbZ.setText("0");
            changingText=false;
         }

    }

    private Polygon selectedShape() {
                     if(chartListBox1.getSelectedIndex()>=0)
                return map.getPolygon(chartListBox1.getSelectedIndex());
                     else
                         return null;
    }

    public void bMapBrush_actionPerformed(ActionEvent e) {
        com.steema.teechart.editors.BrushEditor.edit(this, map.getBrush());
    }

    private void fillShapes() {
        DefaultListModel model = new DefaultListModel();

        if (map != null) {
            chartListBox1.clear();
            chartListBox1.setChart(null);

            for (int t = 0; t < map.getCount(); ++t) {
              model.addElement(map.getShapes().getPolygon(t).getPoints());
            }

           chartListBox1.setModel(model);
        }
    }

    public void jTabbedPane1_stateChanged(ChangeEvent e) {
        if (!setting && chartListBox1.getModel().getSize() != map.getShapes().size()) {
            fillShapes();
        }
    }

    public void buttonPen2_actionPerformed(ActionEvent e) {
        if (selectedShape() != null) {
            selectedShape().getPen().assign(buttonPen2.getPen());
            setCustomPen();
        }
    }

    private void setCustomPen() {
        selectedShape().setParentPen(false);
        cbGlobalPen.setSelected(false);
        map.invalidate();
       chartListBox1.repaint();
    }

    public void buttonColor1_actionPerformed(ActionEvent e) {
             if (selectedShape()!=null) {
                        selectedShape().setColor(buttonColor1.getColor());
                        setCustomBrush();
                }

    }

    private void setDefaultPen() {
            selectedShape().setParentPen(true);
            cbGlobalPen.setSelected(true);
            buttonPen2.setPen(null);
            buttonPen2.setPen(selectedShape().getPen());
            buttonPen2.invalidate();
            map.invalidate();
            chartListBox1.repaint();

    }

    private void setDefaultBrush() {
            selectedShape().setParentBrush(true);
            selectedShape().getPoints().setColor(selectedShape().getPoints().getColor());
            cbGlobalBrush.setSelected(true);
            map.invalidate();
            chartListBox1.repaint();
            buttonColor1.setColor(selectedShape().getPoints().getColor());
    }

    private void setCustomBrush() {
            selectedShape().setParentBrush(false);
            cbGlobalBrush.setSelected(false);
            map.invalidate();
            chartListBox1.repaint();
            buttonColor1.setColor(selectedShape().getPoints().getColor());

    }

    public void tbText_actionPerformed(ActionEvent e) {
    }

    public void tbZ_actionPerformed(ActionEvent e) {
                 if(!changingText) {
                double tmp=Double.parseDouble(tbZ.getText());
                if(selectedShape().getZ()!=tmp) {
                        selectedShape().setZ(tmp);
                        map.invalidate();
                }
          }

    }

    public void jButton1_actionPerformed(ActionEvent e) {
        //BrushEditor.edit();
// TODO       com.steema.teechart.editors.BrushEditor.edit(SelectedShape().getBrush());
        setCustomBrush();
    }

    public void cbGlobalPen_actionPerformed(ActionEvent e) {
        if (cbGlobalPen.isSelected()) {
            setDefaultPen();
        } else {
            setCustomPen();
        }
    }

    public void cbGlobalBrush_actionPerformed(ActionEvent e) {
        if (cbGlobalBrush.isSelected()) {
            setDefaultBrush();
        } else {
            setCustomBrush();
        }
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        if (selectedShape() != null) {
            com.steema.teechart.editors.GradientEditor.edit(this,SelectedShape().getGradient());
            setCustomBrush();
        }
    }

    public void cbClosed_actionPerformed(ActionEvent e) {
        selectedShape().setClosed(cbClosed.isSelected());
    }

    public void cbVisible_actionPerformed(ActionEvent e) {
        selectedShape().getPoints().setVisible(cbVisible.isSelected());
        map.getMarks().getItems().getItem(chartListBox1.getSelectedIndex()).setVisible(cbVisible.isSelected());
    }

    public void spTransparency_stateChanged(ChangeEvent e) {
        Object value = spTransparency.getValue();
        int tmp = 0;
        if(value instanceof Double) {
            tmp = ((Double)value).intValue();
        }
        else if(value instanceof Integer) {
            tmp = ((Integer)value).intValue();
        }
        if (selectedShape() != null) {

            selectedShape().setTransparency(tmp);
            setCustomBrush();
        }
    }

    public void tbText_keyReleased(KeyEvent e) {
        if (!changingText) {
           String tmp=selectedShape().getText();
           if(tmp != tbText.getText()) {
               selectedShape().setText(tbText.getText());
               chartListBox1.invalidate();
               chartListBox1.paintImmediately(chartListBox1.getBounds());
               map.invalidate();
           }
   }


    }

    /* TODO
              private void ChartListBox1_ChangeColor(object sender, Steema.TeeChart.NotifySeriesEventArgs e) {
                      SetCustomBrush();
              }
     */
}


final class MapEditor_spTransparency_changeAdapter implements ChangeListener {
    private MapEditor adaptee;
    MapEditor_spTransparency_changeAdapter(MapEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spTransparency_stateChanged(e);
    }
}


final class MapEditor_cbVisible_actionAdapter implements ActionListener {
    private MapEditor adaptee;
    MapEditor_cbVisible_actionAdapter(MapEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbVisible_actionPerformed(e);
    }
}


final class MapEditor_cbClosed_actionAdapter implements ActionListener {
    private MapEditor adaptee;
    MapEditor_cbClosed_actionAdapter(MapEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbClosed_actionPerformed(e);
    }
}


final class MapEditor_jButton2_actionAdapter implements ActionListener {
    private MapEditor adaptee;
    MapEditor_jButton2_actionAdapter(MapEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


final class MapEditor_cbGlobalBrush_actionAdapter implements ActionListener {
    private MapEditor adaptee;
    MapEditor_cbGlobalBrush_actionAdapter(MapEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbGlobalBrush_actionPerformed(e);
    }
}


final class MapEditor_cbGlobalPen_actionAdapter implements ActionListener {
    private MapEditor adaptee;
    MapEditor_cbGlobalPen_actionAdapter(MapEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbGlobalPen_actionPerformed(e);
    }
}


final class MapEditor_jButton1_actionAdapter implements ActionListener {
    private MapEditor adaptee;
    MapEditor_jButton1_actionAdapter(MapEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}


final class MapEditor_tbZ_actionAdapter implements ActionListener {
    private MapEditor adaptee;
    MapEditor_tbZ_actionAdapter(MapEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.tbZ_actionPerformed(e);
    }
}


final class MapEditor_tbText_actionAdapter implements ActionListener {
    private MapEditor adaptee;
    MapEditor_tbText_actionAdapter(MapEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.tbText_actionPerformed(e);
    }
}


class MapEditor_tbText_keyAdapter extends KeyAdapter {
    private MapEditor adaptee;
    MapEditor_tbText_keyAdapter(MapEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void keyReleased(KeyEvent e) {
        adaptee.tbText_keyReleased(e);
    }
}


final class MapEditor_buttonColor1_actionAdapter implements ActionListener {
    private MapEditor adaptee;
    MapEditor_buttonColor1_actionAdapter(MapEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.buttonColor1_actionPerformed(e);
    }
}


final class MapEditor_buttonPen2_actionAdapter implements ActionListener {
    private MapEditor adaptee;
    MapEditor_buttonPen2_actionAdapter(MapEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.buttonPen2_actionPerformed(e);
    }
}


final class MapEditor_jTabbedPane1_changeAdapter implements ChangeListener {
    private MapEditor adaptee;
    MapEditor_jTabbedPane1_changeAdapter(MapEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jTabbedPane1_stateChanged(e);
    }
}


final class MapEditor_bMapBrush_actionAdapter implements ActionListener {
    private MapEditor adaptee;
    MapEditor_bMapBrush_actionAdapter(MapEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bMapBrush_actionPerformed(e);
    }
}


final class MapEditor_chartListBox1_listSelectionAdapter implements
        ListSelectionListener {
    private MapEditor adaptee;
    MapEditor_chartListBox1_listSelectionAdapter(MapEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void valueChanged(ListSelectionEvent e) {
        adaptee.chartListBox1_valueChanged(e);
    }
}


final class MapEditor_buttonPen1_actionAdapter implements ActionListener {
    private MapEditor adaptee;
    MapEditor_buttonPen1_actionAdapter(MapEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.buttonPen1_actionPerformed(e);
    }
}

