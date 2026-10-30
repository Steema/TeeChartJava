package com.steema.teechart.editors;

import javax.swing.*;
import java.awt.Rectangle;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import com.steema.teechart.printer.PreviewPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import com.steema.teechart.Chart;
import com.steema.teechart.printer.Printer;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: PreviewPanelEditor class</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

public class PreviewPanelEditor extends BaseEditorDialog {


    private PreviewPanel previewPane;

    public PreviewPanelEditor(Chart c, PreviewPanel preview) {
        this(c, c.getPrinter(), preview);
    }


    public PreviewPanelEditor(Chart c, Printer p, PreviewPanel preview) {
        super();
        previewPane=preview;
        setEditorPane(new PreviewFrame(c,p,preview));
    }


    static public void showModal(Chart c, PreviewPanel preview)
     {
        showModal(c,c.getPrinter(),preview);
     }

     static public void showModal(Chart c,Printer p, PreviewPanel preview)
     {
        BaseEditorDialog.showModal(new PreviewFrame(c,p,preview));
        /*
        JDialog d = new JDialog();
        d.getContentPane().add(new PreviewPanelEditor(previewPanel));

        //d.validate();
        d.setSize(new Dimension(400, 300));
        d.setModal(true);
        d.setVisible(true);*/
    }

    public void createPanel(JComponent aOwner, PreviewPanel aPanel) {
        JPanel page;
        PanelEditor tmp;

        previewPanel = aPanel;
        page = new JPanel();

        tmp=new PanelEditor();  //.CreatePanel(Self,TeePreviewPanel1);
        tmp.setLayout(null);

        add(tmp,page,0);
    }

    private void load() {
        cbShowImage.setVisible(false);
        cbAsBitmap.setVisible(false);
        cbDragImage.setVisible(false);
        cbAllowMove.setVisible(false);
        cbAllowResize.setVisible(false);
        if (previewPanel!=null)
        {
           cbAllowMove.setSelected(previewPanel.getAllowMove());
           cbAllowResize.setSelected(previewPanel.getAllowResize());
           cbDragImage.setSelected(previewPanel.getDragImage());
           cbAsBitmap.setSelected(previewPanel.getAsBitmap());
           cbShowImage.setSelected(previewPanel.getShowImage());
           //RGOrientation.ItemIndex: = Ord(Orientation);
           //Button2.LinkPen(Margins);
           //InsertTeeShadowEditor(TabShadow).RefreshControls(Shadow);

           //bPaperColor.LinkProperty(TeePreviewPanel1, 'PaperColor '); {do not translate
        }

        if (tabPane.getTabCount() > 1) {
            tabPane.setSelectedIndex(1);
        }
        // BorderStyle:=TeeBorderStyle;
    }

    private void jbInit() throws Exception {
        this.setLayout(new BorderLayout());
        jPanel1.setLayout(null);
        cbAllowMove.setMnemonic(Language.getString("M").charAt(0));
        cbAllowMove.setSelected(true);
        cbAllowMove.setText(Language.getString("Allow_Move"));
        cbAllowMove.setBounds(new Rectangle(23, 14, 97, 23));
        cbAllowMove.addActionListener(new
                PreviewPanelEditor_cbAllowMove_actionAdapter(this));
        cbAllowResize.setMnemonic(Language.getString("R").charAt(0));
        cbAllowResize.setSelected(true);
        cbAllowResize.setText(Language.getString("Allow_Resize"));
        cbAllowResize.setBounds(new Rectangle(23, 41, 113, 23));
        cbAllowResize.addActionListener(new
                PreviewPanelEditor_cbAllowResize_actionAdapter(this));
        cbShowImage.setMnemonic(Language.getString("I").charAt(0));
        cbShowImage.setSelected(true);
        cbShowImage.setText(Language.getString("Show_Image"));
        cbShowImage.setBounds(new Rectangle(23, 68, 137, 23));
        cbShowImage.addActionListener(new
                PreviewPanelEditor_cbShowImage_actionAdapter(this));
        cbDragImage.setMnemonic(Language.getString("D").charAt(0));
        cbDragImage.setText(Language.getString("Drag_Image"));
        cbDragImage.setBounds(new Rectangle(147, 14, 83, 23));
        cbDragImage.addActionListener(new
                PreviewPanelEditor_cbDragImage_actionAdapter(this));
        cbAsBitmap.setMnemonic(Language.getString("B").charAt(0));
        cbAsBitmap.setText(Language.getString("As_Bitmap"));
        cbAsBitmap.setBounds(new Rectangle(147, 41, 83, 23));
        cbAsBitmap.addActionListener(new
                PreviewPanelEditor_cbAsBitmap_actionAdapter(this));
        jPanel3.setBorder(border10);
        jPanel3.setBounds(new Rectangle(23, 99, 134, 93));
        jPanel3.setLayout(null);
        bShadowColor.setBounds(new Rectangle(33, 52, 71, 23));
        bShadowColor.setMnemonic(Language.getString("C").charAt(0));
        bShadowColor.setText(Language.getString("Color"));
        spShadowSize.setValue(new Integer(5));
        jLabel1.setDisplayedMnemonic(Language.getString("Z").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(spShadowSize);
        jLabel1.setText(Language.getString("Size"));
        jLabel1.setBounds(new Rectangle(20, 24, 34, 15));
        spShadowSize.setBounds(new Rectangle(63, 20, 54, 23));
        jPanel4.setBorder(border10);
        jPanel4.setBounds(new Rectangle(168, 99, 117, 93));
        jPanel4.setLayout(null);
        jRadioButton1.setMnemonic(Language.getString("E").charAt(0));
        jRadioButton1.setSelected(true);
        jRadioButton1.setText(Language.getString("Default"));
        jRadioButton1.setBounds(new Rectangle(13, 15, 91, 23));
        jRadioButton1.addActionListener(new
                PreviewPanelEditor_jRadioButton1_actionAdapter(this));
        jRadioButton2.setMnemonic(Language.getString("O").charAt(0));
        jRadioButton2.setText(Language.getString("Portrait"));
        jRadioButton2.setBounds(new Rectangle(13, 39, 91, 23));
        jRadioButton2.addActionListener(new
                PreviewPanelEditor_jRadioButton2_actionAdapter(this));
        jRadioButton3.setText(Language.getString("Landscape"));
        jRadioButton3.setBounds(new Rectangle(13, 62, 91, 23));
        jRadioButton3.addActionListener(new
                PreviewPanelEditor_jRadioButton3_actionAdapter(this));
        bMargins.setBounds(new Rectangle(263, 41, 71, 23));
        bMargins.setMnemonic(Language.getString("A").charAt(0));
        bMargins.setText(Language.getString("Margins"));
        bPaperColor.setBounds(new Rectangle(263, 14, 71, 23));
        bPaperColor.setMnemonic(Language.getString("R").charAt(0));
        bPaperColor.setText(Language.getString("Color"));
        jPanel1.add(cbDragImage);
        jPanel1.add(cbAsBitmap);
        jPanel1.add(cbAllowMove);
        jPanel1.add(cbAllowResize);
        jPanel1.add(cbShowImage);
        jPanel1.add(jPanel3);
        jPanel3.add(bShadowColor);
        jPanel3.add(jLabel1);
        jPanel3.add(spShadowSize);
        jPanel1.add(jPanel4);
        jPanel4.add(jRadioButton1);
        jPanel4.add(jRadioButton2);
        jPanel4.add(jRadioButton3);
        jPanel1.add(bMargins);
        jPanel1.add(bPaperColor);
        tabPane.add(jPanel1, Language.getString("Options"));
        this.add(tabPane, BorderLayout.CENTER);
        setSize(new Dimension(375,250));
    }

    PreviewPanel previewPanel;
    JTabbedPane tabPane = new JTabbedPane();
    JPanel jPanel1 = new JPanel();
    JCheckBox cbAllowMove = new JCheckBox();
    JCheckBox cbAllowResize = new JCheckBox();
    JCheckBox cbShowImage = new JCheckBox();
    JCheckBox cbDragImage = new JCheckBox();
    JCheckBox cbAsBitmap = new JCheckBox();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    Border border1 = BorderFactory.createEmptyBorder();
    Border border2 = new TitledBorder(border1, Language.getString("Shadow"));
    JButton bShadowColor = new JButton();
    Border border3 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border4 = new TitledBorder(border3, Language.getString("Shadow"));
    JLabel jLabel1 = new JLabel();
    JSpinner spShadowSize = new JSpinner();
    JPanel jPanel4 = new JPanel();
    Border border5 = BorderFactory.createEmptyBorder();
    Border border6 = new TitledBorder(border5, Language.getString("Orientation"));
    Border border7 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border8 = new TitledBorder(border7, Language.getString("Shadow"));
    Border border9 = BorderFactory.createEtchedBorder(Color.white,
            new Color(165, 163, 151));
    Border border10 = new TitledBorder(border9, Language.getString("Shadow"));
    JRadioButton jRadioButton1 = new JRadioButton();
    JRadioButton jRadioButton2 = new JRadioButton();
    JRadioButton jRadioButton3 = new JRadioButton();
    Border border11 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border12 = new TitledBorder(border11, Language.getString("Orientation"));
    Border border13 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border14 = new TitledBorder(border13, Language.getString("Orientation"));
    Border border15 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border16 = new TitledBorder(border15, Language.getString("Orientation"));
    JButton bMargins = new JButton();
    Border border17 = BorderFactory.createLineBorder(Color.white, 2);
    Border border18 = new TitledBorder(border17, Language.getString("Orientation"));

    Border border19 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border20 = new TitledBorder(border19, Language.getString("Orientation"));
    ButtonColor bPaperColor = new ButtonColor();

    public void cbAllowMove_actionPerformed(ActionEvent e) {
        previewPanel.setAllowMove(cbAllowMove.isSelected());
    }

    public void cbAllowResize_actionPerformed(ActionEvent e) {
          previewPanel.setAllowResize(cbAllowResize.isSelected());
    }

    public void cbDragImage_actionPerformed(ActionEvent e) {
          previewPanel.setDragImage(cbDragImage.isSelected());
    }

    public void cbAsBitmap_actionPerformed(ActionEvent e) {
// TODO         previewPanel.setAsBitmap(cbAsBitmap.isSelected());
    }

    public void cbShowImage_actionPerformed(ActionEvent e) {
        previewPanel.setShowImage(cbShowImage.isSelected());
    }

    public void jRadioButton1_actionPerformed(ActionEvent e) {
        previewPanel.setOrientation(previewPanel.getPrinter().getPrinterJob().defaultPage().getOrientation());
    }

    public void jRadioButton2_actionPerformed(ActionEvent e) {
        previewPanel.setOrientation(PageFormat.PORTRAIT);
    }

    public void jRadioButton3_actionPerformed(ActionEvent e) {
        previewPanel.setOrientation(PageFormat.LANDSCAPE);
    }
}


class PreviewPanelEditor_jRadioButton3_actionAdapter implements ActionListener {
    private PreviewPanelEditor adaptee;
    PreviewPanelEditor_jRadioButton3_actionAdapter(PreviewPanelEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jRadioButton3_actionPerformed(e);
    }
}


class PreviewPanelEditor_jRadioButton2_actionAdapter implements ActionListener {
    private PreviewPanelEditor adaptee;
    PreviewPanelEditor_jRadioButton2_actionAdapter(PreviewPanelEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jRadioButton2_actionPerformed(e);
    }
}


class PreviewPanelEditor_jRadioButton1_actionAdapter implements ActionListener {
    private PreviewPanelEditor adaptee;
    PreviewPanelEditor_jRadioButton1_actionAdapter(PreviewPanelEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jRadioButton1_actionPerformed(e);
    }
}


class PreviewPanelEditor_cbShowImage_actionAdapter implements ActionListener {
    private PreviewPanelEditor adaptee;
    PreviewPanelEditor_cbShowImage_actionAdapter(PreviewPanelEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbShowImage_actionPerformed(e);
    }
}


class PreviewPanelEditor_cbAsBitmap_actionAdapter implements ActionListener {
    private PreviewPanelEditor adaptee;
    PreviewPanelEditor_cbAsBitmap_actionAdapter(PreviewPanelEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAsBitmap_actionPerformed(e);
    }
}


class PreviewPanelEditor_cbDragImage_actionAdapter implements ActionListener {
    private PreviewPanelEditor adaptee;
    PreviewPanelEditor_cbDragImage_actionAdapter(PreviewPanelEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbDragImage_actionPerformed(e);
    }
}


class PreviewPanelEditor_cbAllowResize_actionAdapter implements ActionListener {
    private PreviewPanelEditor adaptee;
    PreviewPanelEditor_cbAllowResize_actionAdapter(PreviewPanelEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAllowResize_actionPerformed(e);
    }
}


class PreviewPanelEditor_cbAllowMove_actionAdapter implements ActionListener {
    private PreviewPanelEditor adaptee;
    PreviewPanelEditor_cbAllowMove_actionAdapter(PreviewPanelEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAllowMove_actionPerformed(e);
    }
}

