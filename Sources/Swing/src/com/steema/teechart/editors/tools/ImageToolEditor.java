package com.steema.teechart.editors.tools;

import java.awt.Rectangle;
import com.steema.teechart.languages.Language;
import com.steema.teechart.misc.ImageUtils;

import java.awt.Color;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import com.steema.teechart.ImageMode;
import com.steema.teechart.tools.Tool;
import com.steema.teechart.tools.ChartImage;
import com.steema.teechart.editors.ImagePanel;
import com.steema.teechart.editors.tools.ToolSeriesEditor;

/**
 * <p>Title: ImageEditor class</p>
 *
 * <p>Description: Image Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

public class ImageToolEditor extends ToolSeriesEditor {


    public ImageToolEditor() {
        try {
            fillCombos();
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void fillCombos() {
        cbMode.addItem(Language.getString("Normal"));
        cbMode.addItem(Language.getString("Stretch"));
        cbMode.addItem(Language.getString("Center"));
        cbMode.addItem(Language.getString("Tile"));
    }

    public void setTool(Tool tool) {

        chartImage=(ChartImage)tool;
        super.setTool(chartImage);

        if (chartImage!=null) {
          CheckImageButton();

          switch (chartImage.getImageMode().getValue()) {
            case 1:  cbMode.setSelectedIndex(1); break;
            case 2:  cbMode.setSelectedIndex(2); break;
            case 3:  cbMode.setSelectedIndex(3); break;
            default: cbMode.setSelectedIndex(0); break;
          }
        }
    }


    private void CheckImageButton() {
      if (chartImage.getImage()!=null) {
          bBrowseTag=1;
          bBrowse.setText(Language.getString("ClearImage"));
          panelPicture.setImage(chartImage.getImage());
      }
      else {
          bBrowseTag=0;
          bBrowse.setText(Language.getString("BrowseImage"));
          panelPicture.setImage(null);
      }
      panelPicture.paintImmediately(0,0,getBounds().width, getBounds().height);
  }

    private void jbInit() throws Exception {
        this.setLayout(null);
        jPanel1.setBorder(border17);
        jPanel1.setBounds(new Rectangle(12, 48, 206, 118));
        jPanel1.setLayout(null);
        bBrowse.setBounds(new Rectangle(12, 18, 87, 23));
        bBrowse.setText(Language.getString("Browse"));
        bBrowse.addActionListener(new ImageEditor_bBrowse_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("M").charAt(0));
        jLabel1.setLabelFor(cbMode);
        jLabel1.setText(Language.getString("Mode"));
        jLabel1.setBounds(new Rectangle(12, 65, 34, 15));
        cbMode.setBounds(new Rectangle(12, 83, 69, 22));
        cbMode.addActionListener(new ImageEditor_cbMode_actionAdapter(this));
        panelPicture.setBorder(border3);
        panelPicture.setBounds(new Rectangle(105, 18, 87, 85));
        jPanel1.add(bBrowse);
        jPanel1.add(panelPicture);
        jPanel1.add(cbMode);
        jPanel1.add(jLabel1);
        this.add(jPanel1);
    }

    ChartImage chartImage;
    int bBrowseTag;
    JPanel jPanel1 = new JPanel();
    Border border1 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border2 = new TitledBorder(border1, Language.getString("Image"));
    JButton bBrowse = new JButton();
    JLabel jLabel1 = new JLabel();
    JComboBox cbMode = new JComboBox();
    ImagePanel panelPicture = new ImagePanel();
    Border border3 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(165, 163, 151));
    Border border4 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border5 = new TitledBorder(border4, Language.getString("Image"));
    Border border6 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border7 = new TitledBorder(border6, Language.getString("Image"));
    Border border8 = BorderFactory.createLineBorder(Color.white, 2);
    Border border9 = new TitledBorder(border8, Language.getString("Image"));
    Border border10 = BorderFactory.createLineBorder(Color.white, 2);
    Border border11 = new TitledBorder(border10, Language.getString("Image"));
    Border border12 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border13 = new TitledBorder(border12, Language.getString("Image"));
    Border border14 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border15 = new TitledBorder(border14, Language.getString("Image"));
    Border border16 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border17 = new TitledBorder(border16, Language.getString("Image"));


    public void bBrowse_actionPerformed(ActionEvent e) {
        if (bBrowseTag==1)
            chartImage.setImage(null);
        else {
            File filename=com.steema.teechart.editors.PictureDialog.getFileName(null);
            chartImage.setImage(ImageUtils.getImage(filename.getPath()));
        }
      CheckImageButton();
    }

    public void cbMode_actionPerformed(ActionEvent e) {
        if(chartImage != null) {
            switch (cbMode.getSelectedIndex()) {
            case 0:
                chartImage.setImageMode(ImageMode.NORMAL);
                break;
            case 1:
                chartImage.setImageMode(ImageMode.STRETCH);
                break;
            case 2:
                chartImage.setImageMode(ImageMode.CENTER);
                break;
            default:
                chartImage.setImageMode(ImageMode.TILE);
                break;
            }
        }
    }
}


final class ImageEditor_cbMode_actionAdapter implements ActionListener {
    private ImageToolEditor adaptee;
    ImageEditor_cbMode_actionAdapter(ImageToolEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbMode_actionPerformed(e);
    }
}


final class ImageEditor_bBrowse_actionAdapter implements ActionListener {
    private ImageToolEditor adaptee;
    ImageEditor_bBrowse_actionAdapter(ImageToolEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bBrowse_actionPerformed(e);
    }
}


