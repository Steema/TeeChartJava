package com.steema.teechart.editors;

import java.io.File;
import com.steema.teechart.languages.Language;
import com.steema.teechart.misc.ImageUtils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Container;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.JSpinner;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.ListSelectionModel;
import javax.swing.JComponent;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.JComboBox;

import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.HatchStyle;
import com.steema.teechart.ImageMode;

/**
 *
 * <p>Title: BrushEditor class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class BrushEditor extends JDialog {


    public BrushEditor() {
        try {
            jbInit();
            fillCombos();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        jPanel5.add(gradient, BorderLayout.CENTER);
        colorEditor.getPreviewPanel().getParent().setVisible(false);
        jPanel3.add(colorEditor, BorderLayout.CENTER);

        InputMap iMap = getRootPane().getInputMap(
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), Language.getString("escape"));
        getRootPane().getActionMap().put(Language.getString("escape"), new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                cbOK_actionPerformed(null);
            }
        });
    }

    private int bBrowseTag;
    private ChartBrush brush;
    private boolean result;
    JPanel jPanel1 = new JPanel();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JCheckBox cbVisible = new JCheckBox();
    JPanel jPanel2 = new JPanel();
    JButton cbOK = new JButton();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();
    JPanel jPanel6 = new JPanel();
    GradientEditor gradient = new GradientEditor();
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    JColorChooser colorEditor = new JColorChooser();
    JPanel jPanel7 = new JPanel();
    JLabel jLabel1 = new JLabel();
    JSpinner spTransp = new JSpinner();
    JLabel jLabel2 = new JLabel();
    ButtonColor bBack = new ButtonColor();
    ButtonColor bFore = new ButtonColor();
    JScrollPane jScrollPane1 = new JScrollPane();
    JList lHatch = new JList(HatchStyle.DESCRIPTIONS);
    PanelHatch panelHatch = new PanelHatch();
    Border border1 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(103, 101, 98),
            new Color(148, 145, 140));

    final private class PanelHatch extends JPanel {
        public void paintComponent(Graphics g) {

            if (g instanceof Graphics2D) {
                Graphics2D gg = (Graphics2D) g;

                Rectangle r = getBounds();
                gg.setPaint(brush.getHatchTexture());
                gg.fillRect(0, 0, r.width, r.height);
            }
        }
    }

    public BrushEditor(ChartBrush brush) {
        this();
        this.brush = brush;

        colorEditor.setColor(brush.getColor());
        spTransp.setValue(new Integer(brush.getTransparency()));
        cbVisible.setSelected(brush.getVisible());
        gradient.refreshControls(brush.getGradient());
        bBack.setColor(brush.getColor());
        bFore.setColor(brush.getForegroundColor());
        lHatch.setSelectedIndex(brush.getStyle().getValue());

        if (brush!=null) {
          CheckImageButton();

          switch (brush.getImageMode().getValue()) {
            case 1:  cbMode.setSelectedIndex(1); break;
            case 2:  cbMode.setSelectedIndex(2); break;
            case 3:  cbMode.setSelectedIndex(3); break;
            default: cbMode.setSelectedIndex(0); break;
          }
        }

    }

    static public boolean edit(ChartBrush brush) {
        return edit(null, brush);
    }

    static public boolean edit(Container owner, ChartBrush brush) {
        BrushEditor editor = new BrushEditor(brush);


        editor.setSize(430, 355);
        editor.setModal(true);
        editor.setLocationRelativeTo(owner);
        editor.setVisible(true);

        return editor.result;
    }

    private void jbInit() throws Exception {
        cbVisible.setMnemonic(Language.getString("V").charAt(0));
        cbVisible.addActionListener(new BrushEditor_cbVisible_actionAdapter(this));
        jPanel2.setMinimumSize(new Dimension(10, 30));
        jPanel2.setPreferredSize(new Dimension(10, 30));
        jPanel2.setLayout(null);
        cbOK.setBounds(new Rectangle(320, 2, 75, 23));
        cbOK.setText(Language.getString("OK"));
        cbOK.addActionListener(new BrushEditor_cbOK_actionAdapter(this));
        jPanel5.setLayout(borderLayout1);
        jPanel3.setLayout(borderLayout2);
        jPanel7.setPreferredSize(new Dimension(10, 30));
        jPanel7.setLayout(null);
        jLabel1.setDisplayedMnemonic(Language.getString("T").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel1.setText(Language.getString("Transparency"));
        jLabel1.setBounds(new Rectangle(6, 6, 118, 14));
        spTransp.setBounds(new Rectangle(134, 4, 53, 20));
        spTransp.addChangeListener(new BrushEditor_spTransp_changeAdapter(this));
        jLabel2.setText("%");
        jLabel2.setBounds(new Rectangle(203, 6, 23, 14));
        jPanel4.setLayout(null);
        bBack.setBounds(new Rectangle(176, 13, 103, 25));
        bBack.setMnemonic(Language.getString("B").charAt(0));
        bBack.setText(Language.getString("Background"));
        bBack.addActionListener(new BrushEditor_bBack_actionAdapter(this));
        bFore.setBounds(new Rectangle(176, 68, 102, 25));
        bFore.setMnemonic(Language.getString("F").charAt(0));
        bFore.setText(Language.getString("Foreground"));
        bFore.addActionListener(new BrushEditor_bFore_actionAdapter(this));
        jScrollPane1.setBounds(new Rectangle(7, 14, 158, 201));
        lHatch.addListSelectionListener(new
                                        BrushEditor_lHatch_listSelectionAdapter(this));
        lHatch.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panelHatch.setBorder(border1);
        panelHatch.setBounds(new Rectangle(183, 110, 79, 68));
        jTabbedPane1.addChangeListener(new
                                       BrushEditor_jTabbedPane1_changeAdapter(this));
        jPanel6.setLayout(null);
        pContainer.setBorder(border1);
        pContainer.setBounds(new Rectangle(12, 19, 295, 206));
        pContainer.setLayout(null);
        bBrowse.setBounds(new Rectangle(20, 48, 91, 23));
        bBrowse.setText(Language.getString("Browse"));
        bBrowse.addActionListener(new BrushEditor_bBrowse_actionAdapter(this));
        jLabel3.setText(Language.getString("Mode"));
        jLabel3.setBounds(new Rectangle(20, 111, 34, 14));
        cbMode.setBounds(new Rectangle(20, 135, 74, 22));
        cbMode.addActionListener(new BrushEditor_cbMode_actionAdapter(this));
        pImage.setBorder(BorderFactory.createEtchedBorder());
        pImage.setBounds(new Rectangle(137, 33, 115, 126));
        this.getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);
        jPanel2.add(cbOK);
        jPanel1.setLayout(null);
        cbVisible.setBounds(new Rectangle(12, 4, 122, 23));
        cbVisible.setText(Language.getString("Visible"));
        this.getContentPane().add(jTabbedPane1,
                                  java.awt.BorderLayout.CENTER);
        this.getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);
        jPanel1.add(cbVisible);
        jTabbedPane1.add(jPanel3, Language.getString("Solid"));
        jPanel4.add(jScrollPane1);
        jPanel4.add(bBack);
        jPanel4.add(bFore);
        jPanel4.add(panelHatch);
        jScrollPane1.getViewport().add(lHatch);

        jTabbedPane1.add(jPanel5, Language.getString("Gradient"));
        jTabbedPane1.add(jPanel6, Language.getString("Image"));
        jPanel6.add(pContainer);
        pContainer.add(bBrowse);
        pContainer.add(cbMode);
        pContainer.add(jLabel3);
        pContainer.add(pImage);
        jPanel3.add(jPanel7, java.awt.BorderLayout.SOUTH);
        jPanel7.add(jLabel1);
        jPanel7.add(spTransp);
        jPanel7.add(jLabel2);
        jTabbedPane1.add(jPanel4, Language.getString("Hatch"));
        jPanel1.setPreferredSize(new Dimension(1, 30));
    }

    private void CheckImageButton() {
        if (brush.getImage()!=null) {
            bBrowseTag=1;
            bBrowse.setText(Language.getString("ClearImage"));
            pImage.setImage(brush.getImage());
        }
        else {
            bBrowseTag=0;
            bBrowse.setText(Language.getString("BrowseImage"));
            pImage.setImage(null);
        }
        pImage.paintImmediately(0,0,getBounds().width, getBounds().height);
    }


    public void spTransp_stateChanged(ChangeEvent e) {
        brush.setTransparency(((Integer) spTransp.getValue()).intValue());
    }

    public void cbVisible_actionPerformed(ActionEvent e) {
        brush.setVisible(cbVisible.isSelected());
    }

    public void cbOK_actionPerformed(ActionEvent e) {
        brush.setColor(colorEditor.getColor());
        result = true;
        dispose();
    }

    public void bBack_actionPerformed(ActionEvent e) {
        brush.setColor(bBack.getColor());
    }

    public void bFore_actionPerformed(ActionEvent e) {
        brush.setForegroundColor(bFore.getColor());
    }

    public void lHatch_valueChanged(ListSelectionEvent e) {
        brush.setStyle(HatchStyle.fromInt(lHatch.getSelectedIndex()));
        panelHatch.repaint();
    }

    private boolean washatched = false;
    JPanel pContainer = new JPanel();
    JButton bBrowse = new JButton();
    JLabel jLabel3 = new JLabel();
    JComboBox cbMode = new JComboBox();
    ImagePanel pImage = new ImagePanel();

    public void jTabbedPane1_stateChanged(ChangeEvent e) {
        /*if (jTabbedPane1.getSelectedComponent()==jPanel2) {
            brush.setSolid(true);
        } else */
        if (jTabbedPane1.getSelectedComponent() == jPanel4) {
            washatched = true;
            brush.setSolid(false);
        }
        if (washatched && jTabbedPane1.getSelectedComponent() == jPanel3) {
            washatched = false;
            brush.setSolid(true);
        }
    }

    private void fillCombos() {
        cbMode.addItem(Language.getString("Normal"));
        cbMode.addItem(Language.getString("Stretch"));
        cbMode.addItem(Language.getString("Center"));
        cbMode.addItem(Language.getString("Tile"));
    }


    public void bBrowse_actionPerformed(ActionEvent e) {
        if (bBrowseTag==1) {
            brush.setImage(null);
        }
        else {
            File filename=com.steema.teechart.editors.PictureDialog.getFileName(null);
            brush.setImage(ImageUtils.toImage(ImageUtils.getImage(filename.getPath(), pImage)));
        }
        CheckImageButton();
    }

    public void cbMode_actionPerformed(ActionEvent e) {
        if(brush != null) {
            switch (cbMode.getSelectedIndex()) {
            case 0:
                brush.setImageMode(ImageMode.NORMAL);
                break;
            case 1:
                brush.setImageMode(ImageMode.STRETCH);
                break;
            case 2:
                brush.setImageMode(ImageMode.CENTER);
                break;
            default:
                brush.setImageMode(ImageMode.TILE);
                break;
            }
        }
    }
}

class BrushEditor_cbMode_actionAdapter implements ActionListener {
    private BrushEditor adaptee;
    BrushEditor_cbMode_actionAdapter(BrushEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbMode_actionPerformed(e);
    }
}


class BrushEditor_bBrowse_actionAdapter implements ActionListener {
    private BrushEditor adaptee;
    BrushEditor_bBrowse_actionAdapter(BrushEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bBrowse_actionPerformed(e);
    }
}


final class BrushEditor_lHatch_listSelectionAdapter implements ListSelectionListener {
    private BrushEditor adaptee;
    BrushEditor_lHatch_listSelectionAdapter(BrushEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void valueChanged(ListSelectionEvent e) {
        adaptee.lHatch_valueChanged(e);
    }
}


final class BrushEditor_bFore_actionAdapter implements ActionListener {
    private BrushEditor adaptee;
    BrushEditor_bFore_actionAdapter(BrushEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bFore_actionPerformed(e);
    }
}


final class BrushEditor_bBack_actionAdapter implements ActionListener {
    private BrushEditor adaptee;
    BrushEditor_bBack_actionAdapter(BrushEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bBack_actionPerformed(e);
    }
}


final class BrushEditor_cbOK_actionAdapter implements ActionListener {
    private BrushEditor adaptee;
    BrushEditor_cbOK_actionAdapter(BrushEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbOK_actionPerformed(e);
    }
}


final class BrushEditor_jTabbedPane1_changeAdapter implements ChangeListener {
    private BrushEditor adaptee;
    BrushEditor_jTabbedPane1_changeAdapter(BrushEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jTabbedPane1_stateChanged(e);
    }
}


final class BrushEditor_cbVisible_actionAdapter implements ActionListener {
    private BrushEditor adaptee;
    BrushEditor_cbVisible_actionAdapter(BrushEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbVisible_actionPerformed(e);
    }
}


final class BrushEditor_spTransp_changeAdapter implements ChangeListener {
    private BrushEditor adaptee;
    BrushEditor_spTransp_changeAdapter(BrushEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spTransp_stateChanged(e);
    }
}

