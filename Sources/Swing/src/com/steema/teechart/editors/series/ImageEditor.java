package com.steema.teechart.editors.series;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import java.io.File;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.ImageBar;
import com.steema.teechart.drawing.Image;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.editors.ImagePanel;
import com.steema.teechart.languages.Language;
import com.steema.teechart.misc.ImageUtils;

/**
 * <p>Title: ImageEditor class</p>
 *
 * <p>Description: Image Editor</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ImageEditor extends BaseSeriesEditor {


    public ImageEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSeries(Series series, JTabbedPane tabs) {
        imageBar = (ImageBar) series;

        Image image = imageBar.getImage();
        jPanelImage.setImage(image);

    }


    private void jbInit() throws Exception {
        border12 = BorderFactory.createEtchedBorder(Color.white,
                new Color(148, 145, 140));
        this.setLayout(null);
        jLabel3.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setText(Language.getString("Transparency"));
        jLabel3.setBounds(new Rectangle( -9, 154, 93, 14));
        jPanel1.setBorder(border16);
        jPanel1.setBounds(new Rectangle(5, 0, 295, 174));
        jPanel1.setLayout(null);
        cbTiled.setMnemonic(Language.getString("T").charAt(0));
        cbTiled.setText(Language.getString("Tiled"));
        cbTiled.setBounds(new Rectangle(20, 60, 92, 17));
        cbTiled.addActionListener(new ImageEditor_cbTiled_actionAdapter(this));
        jButton1.setBounds(new Rectangle(114, 204, 79, 23));
        jButton1.setMnemonic(Language.getString("B").charAt(0));
        jButton1.setText(Language.getString("Border"));
        bBrowse.setBounds(new Rectangle(22, 28, 81, 23));
        bBrowse.setMnemonic(Language.getString("B").charAt(0));
        bBrowse.setText(Language.getString("Browse"));
        bBrowse.addActionListener(new ImageEditor_bBrowse_actionAdapter(this));
        jPanelImage.setBorder(BorderFactory.createLineBorder(Color.black));
        jPanelImage.setBounds(new Rectangle(153, 13, 128, 152));
        jPanel1.add(jButton1);
        jPanel1.add(bBrowse);
        jPanel1.add(cbTiled);
        jPanel1.add(jPanelImage);
        this.add(jPanel1);
    }

    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("custom"));
    private ImageBar imageBar;
    Border border3 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border4 = new TitledBorder(border3, Language.getString("Multiple_customs"));
    Border border5 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border6 = new TitledBorder(border5, Language.getString("Color"));
    Border border7 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border8 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border9 = new TitledBorder(border8, Language.getString("Levels"));
    JTextField eValue = new JTextField();
    Border border10 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border11 = new TitledBorder(border10, Language.getString("Line_mode"));
    JLabel jLabel3 = new JLabel();
    JPanel jPanel1 = new JPanel();
    Border border12 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    JCheckBox cbTiled = new JCheckBox();
    Border border13 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border14 = new TitledBorder(border13, Language.getString("Color"));
    JButton jButton1 = new JButton();
    Border border15 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border16 = new TitledBorder(border15, Language.getString("Image"));
    JButton bBrowse = new JButton();
    ImagePanel jPanelImage = new ImagePanel();

    public void bBrowse_actionPerformed(ActionEvent e) {
        File filename=com.steema.teechart.editors.PictureDialog.getFileName(null);
        Image img = ImageUtils.getImage(filename.getPath());
        if(img != null) {
            jPanelImage.setImage(img);
            imageBar.setImage(img);
        }
    }

    public void cbTiled_actionPerformed(ActionEvent e) {
        imageBar.setImageTiled(cbTiled.isSelected());
    }
}

final class ImageEditor_cbTiled_actionAdapter implements ActionListener {
    private ImageEditor adaptee;
    ImageEditor_cbTiled_actionAdapter(ImageEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbTiled_actionPerformed(e);
    }
}

final class ImageEditor_bBrowse_actionAdapter implements ActionListener {
    private ImageEditor adaptee;
    ImageEditor_bBrowse_actionAdapter(ImageEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bBrowse_actionPerformed(e);
    }
}

