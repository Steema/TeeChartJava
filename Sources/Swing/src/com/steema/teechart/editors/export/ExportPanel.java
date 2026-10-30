package com.steema.teechart.editors.export;

import javax.swing.*;
import com.steema.teechart.languages.Language;
import java.awt.color.ColorSpace;
import java.awt.image.ColorConvertOp;
import java.awt.image.BufferedImage;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Clipboard;
import java.awt.Color;

import java.io.File;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import javax.imageio.ImageIO;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

import com.steema.teechart.Chart;
import com.steema.teechart.styles.Series;
// TODO: Remove dependency on BMPOptions here
import com.steema.teechart.exports.editors.BMPOptions;

import com.steema.teechart.editors.BaseEditorDialog;
import com.steema.teechart.exports.DataExportFormat;
import com.steema.teechart.exports.TextFormat;
import com.steema.teechart.exports.XMLFormat;
import com.steema.teechart.exports.HTMLFormat;
import com.steema.teechart.exports.ExcelFormat;


import javax.imageio.ImageWriteParam;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.ImageWriter;

import java.util.Iterator;

import javax.imageio.IIOImage;

import java.awt.Image;
import java.awt.image.RenderedImage;

//import javax.imageio.plugins.bmp.BMPImageWriteParam;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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
public class ExportPanel extends JPanel implements ClipboardOwner {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Chart chart;

    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JButton bCopy = new JButton();
    JButton bSave = new JButton();
    JTabbedPane tabs = new JTabbedPane();
    JPanel imageTab = new JPanel();
    JPanel templateTab = new JPanel();
    JPanel dataTab = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel jPanel5 = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    JScrollPane jScrollPane1 = new JScrollPane();
    Border border1 = BorderFactory.createEmptyBorder();
    JList imageFormats = new JList();
    JTabbedPane jTabbedPane2 = new JTabbedPane();
    JPanel jPanel6 = new JPanel();
    JPanel jPanel7 = new JPanel();
    Border border2 = BorderFactory.createEmptyBorder();
    Border border3 = new TitledBorder(border2, Language.getString("Format"));
    JLabel jLabel1 = new JLabel();
    JSpinner spWidth = new JSpinner();
    JLabel jLabel2 = new JLabel();
    JSpinner spHeight = new JSpinner();
    JCheckBox jCheckBox1 = new JCheckBox();
    JCheckBox cbData = new JCheckBox();
    JCheckBox cbXML = new JCheckBox();
    ExportEditors exportEditor = null;
    ImageWriteParam format = null;
    JPanel options = null;

    String[] supportedImgFormats = new String[] {"BMP",
                                   "JPEG",
                                   "PNG"};

    String fmtString = null;

    boolean editorLoaded = false;
    private double widthHeightRatio;
    JCheckBox jCheckBox2 = new JCheckBox();
    JLabel labelSize = new JLabel();
    BorderLayout borderLayout4 = new BorderLayout();
    JLabel jLabel3 = new JLabel();
    JComboBox cbSeries = new JComboBox();
    JPanel jPanel2 = new JPanel();
    Border border4 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border5 = new TitledBorder(border4, Language.getString("Format"));
    JRadioButton rbText = new JRadioButton();
    JRadioButton rbXML = new JRadioButton();
    JRadioButton rbHTML = new JRadioButton();
    JPanel jPanel3 = new JPanel();
    Border border6 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border7 = new TitledBorder(border6, Language.getString("Include"));
    JCheckBox cbPointIndex = new JCheckBox();
    JCheckBox cbPointLabels = new JCheckBox();
    JCheckBox cbHeader = new JCheckBox();
    JLabel jLabel4 = new JLabel();
    JComboBox cbDelimiter = new JComboBox();
    JTextField eCustom = new JTextField();
    JRadioButton rbExcel = new JRadioButton();

    public ExportPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        DefaultListModel l = new DefaultListModel();
        String[] formats = ImageIO.getWriterFormatNames();
        for (int t = 0; t < formats.length; t++) {
            search:for (int tt = 0; tt < supportedImgFormats.length; tt++) {
                String tmp = formats[t].toUpperCase();
                if (tmp == supportedImgFormats[tt].toUpperCase()) {
                    if (l.indexOf(tmp) == -1) {
                        l.addElement(tmp);
                        fmtString = tmp;
                    }
                    break search;
                }
            }
        }

        // TODO: Custom Image formats...
        // l.add("Metafile");
        // l.add("GIF");
        // l.add("TIFF");
        // l.add("VML");
        // l.add("SVG");
        // l.add("PDF");
        // l.add("PostScript");

        imageFormats.setModel(l);

        if (l.getSize() > 0) {
            imageFormats.setSelectedIndex(0);
        }

        cbDelimiter.addItem(Language.getString("Space"));
        cbDelimiter.addItem(Language.getString("Tab"));
        cbDelimiter.addItem(Language.getString("Comma"));
        cbDelimiter.addItem(Language.getString("Colon"));
        cbDelimiter.addItem(Language.getString("Custom"));

        ButtonGroup group = new ButtonGroup();
        group.add(rbHTML);
        group.add(rbText);
        group.add(rbXML);
        group.add(rbExcel);

    }

    public ExportPanel(Chart chart) {
        this();
        this.chart = chart;

        spWidth.setValue(new Integer(chart.getWidth()));
        spHeight.setValue(new Integer(chart.getHeight()));

        widthHeightRatio = chart.getWidth() / chart.getHeight();

        // Fill cbSeries (Data tab)
        cbSeries.addItem(Language.getString("AllSeries"));

        for (int t = 0; t < chart.getSeriesCount(); t++) {
            cbSeries.addItem(chart.getSeries(t));
        }

        rbText.setSelected(true);
        cbDelimiter.setSelectedIndex(1);

        editorLoaded = true;
    }

    public static void showModal(Chart chart) {
        BaseEditorDialog.showModal(
                new ExportPanel(chart)
                );
    }

    public void setChart(Chart chart) {
        this.chart = chart;
        spWidth.setValue(new Integer(chart.getWidth()));
        spHeight.setValue(new Integer(chart.getHeight()));

        widthHeightRatio = chart.getWidth() / chart.getHeight();
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jPanel1.setPreferredSize(new Dimension(10, 40));
        jPanel1.setLayout(null);
        bCopy.setBounds(new Rectangle(10, 8, 75, 23));
        bCopy.setMnemonic(Language.getString("C").charAt(0));
        bCopy.setText(Language.getString("Copy"));
        bCopy.addActionListener(new ExportEditor_bCopy_actionAdapter(this));
        bSave.setBounds(new Rectangle(105, 8, 75, 23));
        bSave.setMnemonic(Language.getString("S").charAt(0));
        bSave.setText(Language.getString("Save"));
        bSave.addActionListener(new ExportEditor_bSave_actionAdapter(this));
        imageTab.setLayout(borderLayout2);
        jPanel5.setBorder(border3);
        jPanel5.setPreferredSize(new Dimension(100, 10));
        jPanel5.setLayout(borderLayout3);
        jScrollPane1.setBorder(border1);
        jLabel1.setDisplayedMnemonic(Language.getString("W").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel1.setLabelFor(spWidth);
        jLabel1.setText(Language.getString("Width"));
        jLabel1.setBounds(new Rectangle(17, 15, 58, 14));
        jPanel7.setLayout(null);
        spWidth.setBounds(new Rectangle(81, 13, 67, 20));
        spWidth.addChangeListener(new ExportEditor_spWidth_changeAdapter(this));
        jLabel2.setDisplayedMnemonic(Language.getString("H").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel2.setLabelFor(spHeight);
        jLabel2.setText(Language.getString("Height"));
        jLabel2.setBounds(new Rectangle(17, 46, 58, 14));
        spHeight.setBounds(new Rectangle(81, 43, 67, 20));
        spHeight.addChangeListener(new ExportEditor_spHeight_changeAdapter(this));
        jCheckBox1.setMnemonic(Language.getString("K").charAt(0));
        jCheckBox1.setSelected(true);
        jCheckBox1.setText(Language.getString("Keep_aspect_ratio"));
        jCheckBox1.setBounds(new Rectangle(25, 84, 138, 23));
        jCheckBox1.addItemListener(new ExportEditor_jCheckBox1_itemAdapter(this));
        cbData.setMnemonic(Language.getString("I").charAt(0));
        cbData.setSelected(true);
        cbData.setText(Language.getString("Include_Series_Data"));
        cbData.setBounds(new Rectangle(15, 10, 201, 23));
        cbData.addActionListener(new ExportEditor_cbData_actionAdapter(this));
        templateTab.setLayout(null);
        cbXML.setMnemonic(Language.getString("X").charAt(0));
        cbXML.setText(Language.getString("XML_format"));
        cbXML.setBounds(new Rectangle(15, 43, 121, 23));
        cbXML.addActionListener(new ExportEditor_cbXML_actionAdapter(this));
        jPanel6.setToolTipText("");
        jPanel6.setLayout(borderLayout4);
        imageFormats.addListSelectionListener(new
                                              ExportEditor_imageFormats_listSelectionAdapter(this));
        jCheckBox2.setText(Language.getString("Size"));
        jCheckBox2.setBounds(new Rectangle(15, 76, 122, 23));
        jCheckBox2.addActionListener(new ExportEditor_jCheckBox2_actionAdapter(this));
        labelSize.setText(Language.getString("0bytes"));
        labelSize.setBounds(new Rectangle(164, 76, 114, 23));
        dataTab.setLayout(null);
        jLabel3.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel3.setText(Language.getString("Series"));
        jLabel3.setBounds(new Rectangle(11, 12, 115, 15));
        cbSeries.setBounds(new Rectangle(12, 32, 169, 21));
        jPanel2.setBorder(border5);
        jPanel2.setBounds(new Rectangle(10, 60, 172, 116));
        jPanel2.setLayout(null);
        rbText.setMnemonic(Language.getString("T").charAt(0));
        rbText.setSelected(true);
        rbText.setText(Language.getString("Text"));
        rbText.setBounds(new Rectangle(13, 14, 91, 23));
        rbXML.setMnemonic(Language.getString("X").charAt(0));
        rbXML.setText(Language.getString("XML"));
        rbXML.setBounds(new Rectangle(13, 37, 91, 23));
        rbHTML.setMnemonic(Language.getString("H").charAt(0));
        rbHTML.setText(Language.getString("HTML_Table"));
        rbHTML.setBounds(new Rectangle(13, 61, 91, 23));
        jPanel3.setBorder(border7);
        jPanel3.setBounds(new Rectangle(199, 10, 151, 100));
        jPanel3.setLayout(null);
        cbPointIndex.setMnemonic(Language.getString("I").charAt(0));
        cbPointIndex.setText(Language.getString("Point_Index"));
        cbPointIndex.setBounds(new Rectangle(10, 18, 130, 23));
        cbPointLabels.setMnemonic(Language.getString("L").charAt(0));
        cbPointLabels.setSelected(true);
        cbPointLabels.setText(Language.getString("Point_Labels"));
        cbPointLabels.setBounds(new Rectangle(10, 41, 130, 23));
        cbHeader.setMnemonic(Language.getString("E").charAt(0));
        cbHeader.setSelected(true);
        cbHeader.setText(Language.getString("Header"));
        cbHeader.setBounds(new Rectangle(10, 65, 134, 23));
        jLabel4.setDisplayedMnemonic(Language.getString("D").charAt(0));
        jLabel4.setLabelFor(cbDelimiter);
        jLabel4.setText(Language.getString("Delimiter"));
        jLabel4.setBounds(new Rectangle(206, 121, 142, 15));
        cbDelimiter.setBounds(new Rectangle(206, 140, 78, 21));
        eCustom.setBounds(new Rectangle(291, 140, 43, 21));
        rbExcel.setMnemonic('E');
        rbExcel.setText("Excel");
        rbExcel.setBounds(new Rectangle(13, 85, 93, 23));
        this.add(jPanel1, java.awt.BorderLayout.SOUTH);
        jPanel1.add(bCopy);
        jPanel1.add(bSave);
        this.add(tabs, java.awt.BorderLayout.CENTER);
        tabs.add(imageTab, Language.getString("Image"));
        tabs.add(templateTab, Language.getString("Template"));
        templateTab.add(cbData);
        templateTab.add(cbXML);
        templateTab.add(jCheckBox2);
        templateTab.add(labelSize);
        tabs.add(dataTab, Language.getString("Data"));
        dataTab.add(jLabel3, null);
        dataTab.add(cbSeries);
        jPanel2.add(rbHTML);
        jPanel2.add(rbExcel);
        jPanel2.add(rbXML);
        jPanel2.add(rbText);
        dataTab.add(jPanel3);
        jPanel3.add(cbPointIndex);
        jPanel3.add(cbPointLabels);
        jPanel3.add(cbHeader);
        dataTab.add(jLabel4);
        dataTab.add(cbDelimiter);
        dataTab.add(eCustom);
        dataTab.add(jPanel2);
        imageTab.add(jTabbedPane2, java.awt.BorderLayout.CENTER);
        imageTab.add(jPanel5, java.awt.BorderLayout.WEST);
        jPanel5.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        jScrollPane1.getViewport().add(imageFormats);
        jPanel7.add(spWidth);
        jPanel7.add(jLabel1);
        jPanel7.add(jLabel2);
        jPanel7.add(spHeight);
        jPanel7.add(jCheckBox1);
        jTabbedPane2.add(jPanel6, Language.getString("Options"));
        jTabbedPane2.add(jPanel7, Language.getString("Size"));
        setPreferredSize(new Dimension(350, 250));
    }

    private void setClipboard(Transferable contents) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                contents, this);
    }

    public void bCopy_actionPerformed(ActionEvent e) {
        if (tabs.getSelectedComponent() == imageTab) {
            // TODO GrayScale for BMPExport
            exportEditor.setOptions(format);
            chart.getExport().getImage().copyToClipboard(getImageWidth(),
                    getImageHeight());
        } else {
            if (tabs.getSelectedComponent() == templateTab) {
                setClipboard(new StringSelection(chart.getExport().getTemplate().
                                                 getXML()));
            } else {
                // copy data to clipboard
                getDataFormat().copyToClipboard();
            }
        }
    }

    private int getImageHeight() {
        return ((Integer) spHeight.getValue()).intValue();
    }

    private int getImageWidth() {
        return ((Integer) spWidth.getValue()).intValue();
    }

    private void setAspectRatio(boolean horiz) {
        if (jCheckBox1.isSelected()) {
            if (horiz) {
                ChangeListener[] cL = spHeight.getChangeListeners();
                spHeight.removeChangeListener(cL[0]);
                spHeight.setValue(new Integer((int) (((Integer) spWidth.
                        getValue()).intValue()
                        / widthHeightRatio)));
                spHeight.addChangeListener(cL[0]);
            } else {
                ChangeListener[] cL = spWidth.getChangeListeners();
                spWidth.removeChangeListener(cL[0]);
                spWidth.setValue(new Integer((int) (((Integer) spHeight.
                        getValue()).intValue()
                        * widthHeightRatio)));
                spWidth.addChangeListener(cL[0]);
            }
        }
    }

    private void addPanel(JPanel panel) {
        if ((jPanel6.getComponentCount() == 0) ||
            (jPanel6.getComponent(0) != panel)) {
            jPanel6.removeAll();
            jPanel6.add(panel);

            panel.setVisible(true);
            jPanel6.setVisible(false);
            jPanel6.setVisible(true);
        }
    }

    public ImageWriteParam createFormat(int index) {
        switch (index) {
        case 0:
            exportEditor = new BMPEditor();
            return null; //new BMPImageWriteParam(getLocale());
        case 1:
            exportEditor = new PNGEditor();
            return new JPEGImageWriteParam(getLocale()); //trick? Progressive output support
        case 2:
            exportEditor = new JPEGEditor();
            return new JPEGImageWriteParam(getLocale());
        case 3:
            exportEditor = new GIFEditor();
            return new JPEGImageWriteParam(getLocale());
        default:
            exportEditor = new JPEGEditor();
            return new JPEGImageWriteParam(getLocale());
        }
    }

    public void imageFormats_valueChanged(ListSelectionEvent e) {

        jTabbedPane2.setSelectedIndex(0);
        jTabbedPane2.invalidate();

        if (options != null) {
            options = null;
        }

        format = createFormat(imageFormats.getSelectedIndex());

        if (exportEditor != null) {
            options = exportEditor.getOptions();
        }

        addPanel(options);
    }

    public void bSave_actionPerformed(ActionEvent e) {

        if (tabs.getSelectedComponent() == imageTab) {
            saveImage();
        } else
        if (tabs.getSelectedComponent() == templateTab) {
            try {
                saveTemplate();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            saveData();
        }
    }

    private final class TemplateFilter extends FileFilter {

        public boolean accept(File f) {
            String name = f.getName().toLowerCase();

            return f.isDirectory() ? true :
                    name.endsWith(Language.getString("TeeFilesExtension")) ||
                    name.endsWith("xml");
        }

        public String getDescription() {
            return Language.getString("TeeFiles");
        }
    }


    private void saveTemplate() throws FileNotFoundException, IOException {
        String extension = Language.getString("TeeFilesExtension");
        if(cbXML.isSelected()) extension = Language.getString("xml");
        
        File outFile = getSaveFile(extension,
                                   Language.getString("SaveChart"),
                                   new TemplateFilter());

        if (outFile != null) {
            if (outFile.getName().toUpperCase().endsWith(Language.getString(
                    "xml"))) {
                chart.getExport().getTemplate().toXML(outFile.getName());
            } else {
                chart.getExport().getTemplate().toFile(outFile.getName());
            }
        }
    }

    private String getDelimiter() {
        switch (cbDelimiter.getSelectedIndex()) {
        case 0:
            return " ";
        case 1:
            return "\t";
        case 2:
            return ",";
        case 3:
            return ":";
        default:
            return eCustom.getText();
        }
    }

    private DataExportFormat getDataFormat() {
        DataExportFormat result = null;

        if (rbText.isSelected()) {
            result = new TextFormat(chart);
        } else
        if (rbXML.isSelected()) {
            result = new XMLFormat(chart);
        } else
        if (rbExcel.isSelected()) {
            result = new ExcelFormat(chart);
        } else {
            result = new HTMLFormat(chart);
        }

        result.setIncludeIndex(cbPointIndex.isSelected());
        result.setIncludeHeader(cbHeader.isSelected());
        result.setIncludeLabels(cbPointLabels.isSelected());

        if (result instanceof TextFormat) {
            ((TextFormat) result).textDelimiter = getDelimiter();
        }

        if (cbSeries.getSelectedIndex() > 0) {
            result.setSeries((Series) cbSeries.getSelectedItem());
        }

        return result;
    }

    private class DataFileFilter extends FileFilter {
        public DataExportFormat data;

        public boolean accept(File f) {
            return true;
        }

        public String getDescription() {
            return data.getFilterFiles();
        }
    }


    private void saveData() {
        DataFileFilter tmp = new DataFileFilter();
        tmp.data = getDataFormat();

        File outFile = getSaveFile(tmp.data.getFileExtension(),
                                   Language.getString("Save_Chart_data_to"),
                                   tmp);

        if (outFile != null) {
            try {
                tmp.data.save(outFile.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getChartName() {
        return chart.getHeader().getText().trim();
    }

    private File getSaveFile(String extension, String title,
                             FileFilter filter) {

        //File save dialogue
        String filename = getChartName() + "." + extension;
        File proposedFile = new File(filename);
        JFileChooser fc = new JFileChooser();

        fc.setDialogTitle(title);
        fc.setCurrentDirectory(new File("."));
        fc.setSelectedFile(proposedFile);
        fc.setFileFilter(filter);

        fc.showSaveDialog(this);
        return fc.getSelectedFile();
    }

    private void saveImage() {
        File outFile = getSaveFile(imageFormats.getSelectedValue().toString().
                                   toLowerCase(),
                                   Language.getString("Save_Chart_image_to"),
                                   new ImageFilter());

        if (outFile != null) {
            exportEditor.setOptions(format);

            try {
                Image img = chart.image(getImageWidth(),getImageHeight());

                RenderedImage rendImage = (RenderedImage) img;

                if (imageFormats.getSelectedValue().equals(Language.getString(
                        "BMP"))) {
                    BMPOptions ops = (BMPOptions) exportEditor.options;

                    if (ops.getColorReduction() == 0) {

                        rendImage = (RenderedImage) img;

                    } else {
                        // save with GrayScale
                        BufferedImage logoCopy = (BufferedImage) img; //ImageUtils.toBufferedImage(img);

                        ColorSpace cs = ColorSpace.getInstance(ColorSpace.
                                CS_GRAY);
                        ColorConvertOp op = new ColorConvertOp(cs, null);
                        logoCopy = op.filter(logoCopy, null);

                        // This method returns an Image object from a buffered image
                        img = Toolkit.getDefaultToolkit().createImage(
                                logoCopy.
                                getSource());

                        rendImage = (RenderedImage) logoCopy;
                    }
                }

                Iterator iter = ImageIO.getImageWritersByFormatName(
                        imageFormats.getSelectedValue().toString());
                ImageWriter writer = (ImageWriter) iter.next();

                // Prepare output file
                ImageOutputStream ios = ImageIO.createImageOutputStream(
                        outFile);
                writer.setOutput(ios);

                // Write the image
                writer.write(null, new IIOImage(rendImage, null, null),
                             format);

                // Cleanup
                ios.flush();
                writer.dispose();
                ios.close();
            } catch (IOException error) {
                error.printStackTrace();
            }

        }
    }

    public void jCheckBox1_itemStateChanged(ItemEvent e) {
        if (editorLoaded == true) {
            setAspectRatio(true);
        }
    }

    public void spWidth_stateChanged(ChangeEvent e) {
        if (editorLoaded == true) {
            setAspectRatio(true);
        }
    }

    public void spHeight_stateChanged(ChangeEvent e) {
        if (editorLoaded == true) {
            setAspectRatio(false);
        }
    }

    public void jCheckBox2_actionPerformed(ActionEvent actionEvent) {
        displayTemplateSize();
    }

    private int templateSize() {
        ByteArrayOutputStream m = new ByteArrayOutputStream();

        if (cbXML.isSelected()) {
            chart.getExport().getTemplate().toXML(m);
        } else {
            try {
                chart.getExport().getTemplate().toStream(m);
            } catch (IOException ex) {
            }
        }

        try {
            m.close();
        } catch (IOException ex1) {
        }

        return m.size();
    }

    private void displayTemplateSize() {
        if (jCheckBox2.isSelected()) {
            labelSize.setText(Integer.toString(templateSize()) +
                              Language.getString("bytes"));
        } else {
            labelSize.setText("");
        }
    }

    public void lostOwnership(Clipboard clipboard, Transferable contents) {
    }

    public void cbData_actionPerformed(ActionEvent actionEvent) {
        displayTemplateSize();
    }

    public void cbXML_actionPerformed(ActionEvent actionEvent) {
        displayTemplateSize();
    }

}


final class ExportEditor_cbXML_actionAdapter implements ActionListener {
    private ExportPanel adaptee;
    ExportEditor_cbXML_actionAdapter(ExportPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.cbXML_actionPerformed(actionEvent);
    }
}


final class ExportEditor_cbData_actionAdapter implements ActionListener {
    private ExportPanel adaptee;
    ExportEditor_cbData_actionAdapter(ExportPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.cbData_actionPerformed(actionEvent);
    }
}


final class ExportEditor_jCheckBox2_actionAdapter implements ActionListener {
    private ExportPanel adaptee;
    ExportEditor_jCheckBox2_actionAdapter(ExportPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jCheckBox2_actionPerformed(actionEvent);
    }
}


final class ExportEditor_spHeight_changeAdapter implements ChangeListener {
    private ExportPanel adaptee;
    ExportEditor_spHeight_changeAdapter(ExportPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spHeight_stateChanged(e);
    }
}


final class ExportEditor_spWidth_changeAdapter implements ChangeListener {
    private ExportPanel adaptee;
    ExportEditor_spWidth_changeAdapter(ExportPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spWidth_stateChanged(e);
    }
}


final class ExportEditor_jCheckBox1_itemAdapter implements ItemListener {
    private ExportPanel adaptee;
    ExportEditor_jCheckBox1_itemAdapter(ExportPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
        adaptee.jCheckBox1_itemStateChanged(e);
    }
}


final class ExportEditor_bSave_actionAdapter implements ActionListener {
    private ExportPanel adaptee;
    ExportEditor_bSave_actionAdapter(ExportPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bSave_actionPerformed(e);
    }
}


final class ExportEditor_imageFormats_listSelectionAdapter implements
        ListSelectionListener {
    private ExportPanel adaptee;
    ExportEditor_imageFormats_listSelectionAdapter(ExportPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void valueChanged(ListSelectionEvent e) {
        adaptee.imageFormats_valueChanged(e);
    }
}


final class ExportEditor_bCopy_actionAdapter implements ActionListener {
    private ExportPanel adaptee;
    ExportEditor_bCopy_actionAdapter(ExportPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bCopy_actionPerformed(e);
    }
}
