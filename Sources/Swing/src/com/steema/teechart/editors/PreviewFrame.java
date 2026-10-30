package com.steema.teechart.editors;


import com.steema.teechart.printer.PreviewPanel;
import com.steema.teechart.Chart;
import com.steema.teechart.printer.PrintMargins;
import com.steema.teechart.printer.PrintMarginUnit;
import com.steema.teechart.printer.Printer;

import javax.swing.border.BevelBorder;
import java.awt.Rectangle;
import java.awt.Color;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.*;
import java.awt.Font;
import javax.swing.DebugGraphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.WindowEvent;

import java.awt.print.*;
import java.awt.*;
import javax.print.*;
import java.awt.BorderLayout;
import com.steema.teechart.misc.Utils;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: PreviewFrame class</p>
 *
 * <p>Description: Preview Frame</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 *
 *
 */
public class PreviewFrame extends JPanel implements CloseButtonPane {


    public PreviewFrame() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public PreviewFrame(Chart c, JComponent parent) {
        this(c, c.getPrinter());
    }

    public PreviewFrame(Chart c, com.steema.teechart.printer.Printer p) {
        this();

        chart = c;
        prn = p;

        previewPane = new PreviewPanel(chart, prn);
        this.add(previewPane, BorderLayout.CENTER);

        loadPrinterSettings();
    }

    public PreviewFrame(Chart c, com.steema.teechart.printer.Printer p,PreviewPanel preview) {
        this();

        chart = c;
        prn = p;

        previewPane = preview;
        this.remove(tabPane);
        this.add(tabPane, BorderLayout.CENTER);
        loadPrinterSettings();
    }

    static public void showModal(Chart c) {
        showModal(c, c.getPrinter());
    }

    static public void showModal(Chart c, Printer p) {
        BaseEditorDialog.showModal(
                new PreviewFrame(c, p)
                );
    }

    static public void showModal(Chart c,PreviewPanel preview) {
        showModal(c, c.getPrinter(),preview);
    }

    static public void showModal(Chart c, Printer p,PreviewPanel preview) {
        BaseEditorDialog.showModal(
                new PreviewFrame(c, p,preview)
                );
    }

    public void addCloseButton(AbstractButton closeButton) {
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(closeButton);
    }

    public void setChart(Chart chart) {
        this.chart = chart;
        if (previewPane == null) {
            previewPane = new PreviewPanel(chart, prn);
            //previewPane.setBounds(new Rectangle(129, 50, 260, 241));
            this.add(previewPane, BorderLayout.CENTER);
            if (prn == null) {
                prn = chart.getPrinter();
                loadPrinterSettings();
            }
        } else {
            previewPane.setChart(chart);
        }
    }

    public PreviewPanel getPreviewPanel() {
        return previewPane;
    }

    private void loadPrinterSettings() {
        setting = true;

        if (prn != null) {
            printDocument1 = prn.getPrinterJob();

            if (prn.getLandscape()) {
                radioButton2.setSelected(true);
            } else {
                radioButton1.setSelected(true);
            }

            prn.getOptions();

            PrintService[] services = PrinterJob.lookupPrintServices();

            cbPrinter.removeAllItems();
            for (int t = 0; t < services.length; t++) {
                cbPrinter.addItem(services[t].getName());
            }

            if (printDocument1 != null) {
                for (int t = 0; t < services.length; t++) {
                    if (printDocument1.getPrintService().getName() ==
                        services[t].getName()) {
                        cbPrinter.setSelectedIndex(t);
                    }
                }
            }

            if (cbPrinter.getSelectedIndex() != -1) { //CDI TF02010126
            } else {
                bSetup.setEnabled(false);
                bPrint.setEnabled(false);
            }

            if (prn.getLandscape()) {
                radioButton2.setSelected(true);
            } else {
                radioButton1.setSelected(true);
            }
            cbProportional.setSelected(prn.getProportional());
            cbGrayScale.setSelected(prn.getGrayscale());

            if (prn.getProportional()) {
                com.steema.teechart.Rectangle tmpRect=prn.calcProportionalMargins(chart,prn.getPaperRect());
                prn.setMargins(new PrintMargins(chart,tmpRect.getLeft(),
                                                        tmpRect.getTop(),tmpRect.getRight(),
                                                        tmpRect.getBottom()));
            }

            com.steema.teechart.printer.PrintMargins m = prn.getMargins();

            setting = true;
            spLeft.setValue(new Integer(m.getLeft()));
            spTop.setValue(new Integer(m.getTop()));
            spRight.setValue(new Integer(m.getRight()));
            spBottom.setValue(new Integer(m.getBottom()));

            cbPrintBackground.setSelected(prn.getPrintPanelBackground());
            cbMarginUnits.setSelectedIndex(prn.getMarginUnits().getValue());
            setting = false;

            CheckPageButtons();
        }

        setting = false;

    }

    private void CheckPageButtons() {
//        bFirst.setVisible(chart.getPage().getMaxPointsPerPage() != 0);
        bLast.setVisible(bFirst.isVisible());
        bPrevious.setVisible(bFirst.isVisible());
        bNext.setVisible(bFirst.isVisible());
    }

    private void RefreshView() {
        CheckPageButtons();

        if (prn != null) {
//                prn = chart.getPrinter();
                loadPrinterSettings();
            }

        if (previewPane != null) {
            previewPane.invalidate();

            previewPane.repaint();
        }

        /* to do
                printPreviewControl1.Document=printDocument1;

                printPreviewControl1.Refresh();
                printPreviewControl1.Invalidate(); */
    }

    private void jbInit() throws Exception {
        this.setLayout(new BorderLayout());

        cbMarginUnits.addItem(Language.getString("Percent"));
        cbMarginUnits.addItem(Language.getString("172_Inch"));

        printerLabel.setDisplayedMnemonic(Language.getString("I").charAt(0));
        printerLabel.setLabelFor(cbPrinter);
        printerLabel.setText(Language.getString("Printer"));

        bPrint.setMnemonic(Language.getString("P").charAt(0));
        bPrint.setText(Language.getString("Print"));
        bPrint.addActionListener(new PreviewFrame_bPrint_actionAdapter(this));

        bSetup.setMnemonic(Language.getString("S").charAt(0));
        bSetup.setText(Language.getString("Setup"));
        bSetup.addActionListener(new PreviewFrame_bSetup_actionAdapter(this));

        //tabPane.setBounds(new Rectangle(0, 36, 123, 359));
        tabPane.addChangeListener(new PreviewFrame_jTabbedPane1_changeAdapter(this));
        tabPane.setPreferredSize(new Dimension(125, 400));
        tabPane.setBounds(new Rectangle(0, 0, 400, 400));
        jPanel2.setLayout(null);
        jPanel4.setBorder(border10);
        jPanel4.setBounds(new Rectangle(5, 0, 106, 73));
        jPanel4.setLayout(null);
        radioButton1.setMnemonic(Language.getString("O").charAt(0));
        radioButton1.setText(Language.getString("Portrait"));
        radioButton1.setBounds(new Rectangle(12, 17, 74, 23));
        radioButton1.addActionListener(new
                                       PreviewFrame_radioButton1_actionAdapter(this));
        radioButton2.setMnemonic(Language.getString("L").charAt(0));
        radioButton2.setText(Language.getString("Landscape"));
        radioButton2.setBounds(new Rectangle(12, 40, 85, 23));
        radioButton2.addActionListener(new
                                       PreviewFrame_radioButton2_actionAdapter(this));
        jPanel5.setBorder(border8);
        jPanel5.setBounds(new Rectangle(5, 102, 106, 136));
        jPanel5.setLayout(null);
        spTop.setBounds(new Rectangle(26, 19, 47, 21));
        spTop.addInputMethodListener(new PreviewFrame_spTop_inputMethodAdapter(this));
        spTop.addChangeListener(new PreviewFrame_spTop_changeAdapter(this));
        spLeft.setBounds(new Rectangle(6, 42, 47, 21));
        spLeft.addInputMethodListener(new
                                      PreviewFrame_spLeft_inputMethodAdapter(this));
        spLeft.addChangeListener(new PreviewFrame_spLeft_changeAdapter(this));
        spRight.setBounds(new Rectangle(52, 42, 47, 21));
        spRight.addInputMethodListener(new
                                       PreviewFrame_spRight_inputMethodAdapter(this));
        spRight.addChangeListener(new PreviewFrame_spRight_changeAdapter(this));
        spBottom.setBounds(new Rectangle(26, 64, 47, 21));
        spBottom.addInputMethodListener(new
                                        PreviewFrame_spBottom_inputMethodAdapter(this));
        spBottom.addChangeListener(new PreviewFrame_spBottom_changeAdapter(this));
        jLabel3.setDisplayedMnemonic(Language.getString("U").charAt(0));
        jLabel3.setLabelFor(cbMarginUnits);
        jLabel3.setText(Language.getString("Margin_Units"));
        jLabel3.setBounds(new Rectangle(8, 87, 92, 15));
        cbMarginUnits.setBounds(new Rectangle(7, 105, 89, 21));
        cbMarginUnits.addActionListener(new
                                        PreviewFrame_cbMarginUnits_actionAdapter(this));
        bFirst.setBounds(new Rectangle(4, 238, 27, 23));
        bFirst.setFont(new java.awt.Font(Language.getString("Arial"), Font.PLAIN, 11));
        bFirst.setBorder(border11);
        bFirst.setText("<<");
        bFirst.addActionListener(new PreviewFrame_bFirst_actionAdapter(this));
        bLast.setBounds(new Rectangle(83, 238, 27, 23));
        bLast.setFont(new java.awt.Font(Language.getString("Arial"), Font.PLAIN, 11));
        bLast.setBorder(border11);
        bLast.setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);
        bLast.setMnemonic('0');
        bLast.setText(">>");
        bLast.addActionListener(new PreviewFrame_bLast_actionAdapter(this));
        bPrevious.setBounds(new Rectangle(31, 238, 26, 23));
        bPrevious.setFont(new java.awt.Font(Language.getString("Arial"), Font.PLAIN, 11));
        bPrevious.setBorder(border11);
        bPrevious.setText("<");
        bPrevious.addActionListener(new PreviewFrame_jButton1_actionAdapter(this));
        bNext.setBounds(new Rectangle(57, 238, 26, 23));
        bNext.setFont(new java.awt.Font(Language.getString("Arial"), Font.PLAIN, 11));
        bNext.setBorder(border11);
        bNext.setText(">");
        bNext.addActionListener(new PreviewFrame_jButton2_actionAdapter(this));
        cbPrintBackground.setMnemonic(Language.getString("B").charAt(0));
        cbPrintBackground.setText(Language.getString("Print_Background"));
        cbPrintBackground.setBounds(new Rectangle(3, 26, 107, 23));
        cbPrintBackground.addActionListener(new
                                            PreviewFrame_cbPrintBackground_actionAdapter(this));
        jPanel3.setLayout(null);
        cbProportional.setMnemonic(Language.getString("R").charAt(0));
        cbProportional.setText(Language.getString("Proportional"));
        cbProportional.setBounds(new Rectangle(3, 55, 113, 23));
        cbProportional.addActionListener(new
                                         PreviewFrame_cbProportional_actionAdapter(this));
        cbGrayScale.setMnemonic(Language.getString("G").charAt(0));
        cbGrayScale.setText(Language.getString("Grayscale"));
        cbGrayScale.setBounds(new Rectangle(3, 83, 113, 23));
        cbGrayScale.addActionListener(new
                                      PreviewFrame_cbGrayScale_actionAdapter(this));
        cbPrinter.addActionListener(new PreviewFrame_cbPrinter_actionAdapter(this));
        jPanel5.add(spBottom);
        jPanel5.add(spTop);
        jPanel5.add(spLeft);
        jPanel5.add(spRight);
        jPanel5.add(jLabel3);
        jPanel5.add(cbMarginUnits);
        jPanel2.add(bLast);
        jPanel2.add(bFirst);
        jPanel2.add(bPrevious);
        jPanel2.add(bNext);
        jPanel2.add(jPanel4);
        tabPane.add(jPanel2, Language.getString("Page"));
        tabPane.add(jPanel3, Language.getString("Format"));
        jPanel4.add(radioButton1);
        jPanel4.add(radioButton2);
        jPanel2.add(jPanel5);
        jPanel3.add(cbGrayScale);
        jPanel3.add(cbProportional);
        jPanel3.add(cbPrintBackground);
        buttonPane.setBounds(new Rectangle(0, 1, 506, 39));
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPane.add(printerLabel);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(cbPrinter);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(bPrint);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(bSetup);
        this.add(buttonPane, java.awt.BorderLayout.PAGE_START);
        this.add(tabPane, BorderLayout.LINE_START);
        setPreferredSize(new Dimension(500, 400));

        ButtonGroup group = new ButtonGroup();
        group.add(radioButton1);
        group.add(radioButton2);
    }

    private Chart chart;
    private Printer prn;
    private PrinterJob printDocument1;
    private boolean setting = false;
    JLabel printerLabel = new JLabel();
    JComboBox cbPrinter = new JComboBox();
    JButton bSetup = new JButton();
    JButton bPrint = new JButton();
    JPanel buttonPane = new JPanel();
    JTabbedPane tabPane = new JTabbedPane();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    Border border1 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border2 = new TitledBorder(border1, Language.getString("Orientation"));
    Border border3 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border4 = new TitledBorder(border3, Language.getString("Orientation"));
    JRadioButton radioButton1 = new JRadioButton();
    JRadioButton radioButton2 = new JRadioButton();
    JPanel jPanel5 = new JPanel();
    Border border5 = BorderFactory.createEmptyBorder();
    Border border6 = new TitledBorder(border5, Language.getString("Margins"));
    Border border7 = BorderFactory.createEtchedBorder(Color.white,
            new Color(165, 163, 151));
    Border border8 = new TitledBorder(border7, Language.getString("Margins"));
    Border border9 = BorderFactory.createEtchedBorder(Color.white,
            new Color(165, 163, 151));
    Border border10 = new TitledBorder(border9, Language.getString("Orientation"));
    JSpinner spTop = new JSpinner();
    JSpinner spLeft = new JSpinner();
    JSpinner spRight = new JSpinner();
    JSpinner spBottom = new JSpinner();
    JLabel jLabel3 = new JLabel();
    JComboBox cbMarginUnits = new JComboBox();
    JButton bFirst = new JButton();
    JButton bLast = new JButton();
    Border border11 = BorderFactory.createEmptyBorder();
    Border border12 = BorderFactory.createEmptyBorder();
    JButton bPrevious = new JButton();
    Border border13 = BorderFactory.createEmptyBorder();
    Border border14 = BorderFactory.createEmptyBorder();
    JButton bNext = new JButton();
    Border border15 = BorderFactory.createEmptyBorder();
    JCheckBox cbPrintBackground = new JCheckBox();
    JCheckBox cbProportional = new JCheckBox();
    JCheckBox cbGrayScale = new JCheckBox();
    PreviewPanel previewPane;
    public void radioButton1_actionPerformed(ActionEvent e) {
        if (!setting) {
            prn.getPrinterJob().defaultPage().setOrientation(PageFormat.
                    PORTRAIT);
            prn.setLandscape(false);
            RefreshView();
        }
    }

    public void radioButton2_actionPerformed(ActionEvent e) {
        prn.getPrinterJob().defaultPage().setOrientation(PageFormat.LANDSCAPE);
        prn.setLandscape(true);
        RefreshView();
    }

    public void bSetup_actionPerformed(ActionEvent e) {
        printDocument1.printDialog();
        if (prn.getLandscape()) {
            radioButton2.setSelected(true);
        } else {
            radioButton1.setSelected(true);
        }

        RefreshView();
    }

    public void cbPrintBackground_actionPerformed(ActionEvent e) {
        prn.setPrintPanelBackground(cbPrintBackground.isSelected());
        RefreshView();
    }

    public void cbProportional_actionPerformed(ActionEvent e) {
        if (!setting) {
            prn.setProportional(cbProportional.isSelected());
            RefreshView();
        }
    }

    private int LEFT = 0;
    private int TOP = 1;
    private int RIGHT = 2;
    private int BOTTOM = 3;

    private boolean ChangingMargins = false;
    private Rectangle oldMargins;

    private void previewPanelChangeMargins(Object sender,
                                           boolean disableProportional,
                                           Rectangle newMargins) {
        ChangingMargins = true;
        try {
            /* UDLeftMa.Position =NewMargins.Left;
             UDRightMa.Position=NewMargins.Right;
             UDTopMa.Position  =NewMargins.Top;
             UDBotMa.Position  =NewMargins.Bottom;*/
            if (disableProportional) {
                prn.setProportional(false);
                //ChangingProp = true;
                //CBProp.Checked=False;
                //ChangingProp = false;
            }
        } finally {
            ChangingMargins = false;
        }
    }


    private void resetMargins() {
        if (prn.getProportional()) {
            oldMargins = prn.calcProportionalMargins(chart,prn.getPaperRect());
            //chart.setPrintMargins(com.steema.teechart.Rectangle(oldMargins));
        }

        //previewPanelChangeMargins(this, false, chart.getPrintMargins());
        RefreshView();
    }

    private int setMarginPos(int buttonVal, int aPos, int otherside)
    {
        if ((buttonVal + otherside) < 100) {
            aPos = buttonVal;

            if (!ChangingMargins) {
                this.RefreshView();
                //tmpR = chart.getPrintMargins();
                //BReset.Enabled=not EqualRect(tmpR,OldMargins);
                //CBProp.Checked=False;
            }

        } else {
            spLeft.setValue(new Integer(aPos));
        }
        return aPos;
    }

   /* private Rectangle changeMargin(int buttonVal, Rectangle printRect, int axis) {

        int otherside;
        int aPos;

        switch (axis) {
        case 0:  //LEFT
            otherside = printRect.x+printRect.width;
            aPos=setMarginPos(buttonVal,printRect.x,otherside);
            return new Rectangle(aPos,printRect.y,printRect.width,printRect.height);
        case 1: //TOP
            otherside = printRect.y+printRect.height;
            aPos=setMarginPos(buttonVal,printRect.y,otherside);
            return new Rectangle(printRect.x,aPos,printRect.width,printRect.height);
        case 2: //RIGHT
            otherside = printRect.x;
            aPos=setMarginPos(buttonVal,printRect.x+printRect.width,otherside);
            return new Rectangle(printRect.x,printRect.y,aPos,printRect.height);
        case 3: //BOTTOM
            //aPos = printRect.y+printRect.height;
            otherside = printRect.y;
            aPos=setMarginPos(buttonVal,printRect.y+printRect.height,otherside);
            return new Rectangle(printRect.x,printRect.y,printRect.width,aPos);
        }
        return printRect;
    }*/

    public void spLeft_stateChanged(ChangeEvent e) {
        if (!setting) {
            prn.getMargins().setLeft(((Integer) spLeft.getValue()).intValue());
            prn.setProportional(false);
            RefreshView();
        }
    }

    public void spTop_stateChanged(ChangeEvent e) {

        if (!setting) {
            prn.getMargins().setTop(((Integer) spTop.getValue()).intValue());
            prn.setProportional(false);
            RefreshView();
        }
    }

    public void spRight_stateChanged(ChangeEvent e) {
        if (!setting) {
            prn.getMargins().setRight(((Integer) spRight.getValue()).intValue());
            prn.setProportional(false);
            RefreshView();
        }
    }

    public void spBottom_stateChanged(ChangeEvent e) {
        if (!setting) {
            prn.getMargins().setBottom(((Integer) spBottom.getValue()).intValue());
            prn.setProportional(false);
            RefreshView();
        }
    }

    private int GetMarginVal(int marginValue, boolean isWidth) {
        Paper tmp=prn.getPrinterJob().defaultPage().getPaper();

        int pageDim = Utils.round( isWidth ? tmp.getWidth() : tmp.getHeight());

        return marginValue * pageDim / 100;
    }

    public void bPrint_actionPerformed(ActionEvent e) {

        java.awt.Cursor cursor = java.awt.Cursor.getPredefinedCursor(Cursor.
                WAIT_CURSOR);

        prn.isPrinting = true;
        //PrintUtilities pmisc.Utils = new PrintUtilities(previewPane.preview);
        //pmisc.Utils.print(prn.getOrientation());
        
        PrinterJob printJob = prn.getPrinterJob();
        printJob.setPrintable(previewPane.preview);
        //if (printJob.printDialog())
        try {
            printJob.print();
        } catch (PrinterException pe) {
            System.out.println(Language.getString("Error_printing") + pe);
        }

        prn.isPrinting = false;
        cursor = java.awt.Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    }

    public void spLeft_inputMethodTextChanged(InputMethodEvent event) {
//        spLeft_stateChanged(event);
    }

    public void spTop_inputMethodTextChanged(InputMethodEvent event) {
//        spTop_stateChanged(event);
    }

    public void spRight_inputMethodTextChanged(InputMethodEvent event) {
//        spRight_stateChanged(event);
    }

    public void spBottom_inputMethodTextChanged(InputMethodEvent event) {
//        spBottom_stateChanged(event);
    }

    public void cbGrayScale_actionPerformed(ActionEvent e) {
        prn.setGrayscale(cbGrayScale.isSelected());

//        printDocument1.DefaultPageSettings.Color=!checkBox3.Checked;
        RefreshView();
    }

    public void cbPrinter_actionPerformed(ActionEvent e) {
      if (!setting)
      {
          PrintService ps = PrinterJob.lookupPrintServices()[cbPrinter.
                            getSelectedIndex()];
          try {
              prn.getPrinterJob().setPrintService(ps);
          } catch (Exception ex) {
              ex.printStackTrace();
          }
      }
    }

    /*    private void printPreviewControl1_Click(object sender, System.EventArgs e)
        {
          if (trackBar1.getValue()<100)
            trackBar1.Value*=2;
          else
            trackBar1.Value/=2;
          trackBar1_Scroll(trackBar1,new EventArgs());
        }*/

    public void cbQuality_actionPerformed(ActionEvent e) {
//        printPreviewControl1.UseAntiAlias=cbQuality.Checked;
        //prn.setUseAntiAlias();
        RefreshView();
    }

    public void bFirst_actionPerformed(ActionEvent e) {
        chart.getPage().setCurrent(1);
        RefreshView();
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        chart.getPage().previous();
        RefreshView();
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        chart.getPage().next();
        RefreshView();
    }

    public void bLast_actionPerformed(ActionEvent e) {
        chart.getPage().setCurrent(chart.getPage().getCount());
        RefreshView();
    }

    public void cbMarginUnits_actionPerformed(ActionEvent e) {
        if (!setting) {
          if (cbMarginUnits.getSelectedIndex()==0)
          {
              prn.setMarginUnits(PrintMarginUnit.PERCENT);
          }
          else if (cbMarginUnits.getSelectedIndex()==1)
          {
              prn.setMarginUnits(PrintMarginUnit.SEVENTYSECONDSINCH);
          }
          prn.setProportional(false);
          prn.getOptions();
          RefreshView();
        }
    }

    public void this_windowClosing(WindowEvent e) {
        //prn.isPartial=false;
    }

    public void tbResolution_stateChanged(ChangeEvent e) {
//        prn.setResolution(tbResolution.getValue());
//        lbResolution.setText=tbResolution.Value.ToString();
        RefreshView();
    }

    public void jTabbedPane1_stateChanged(ChangeEvent e) {
        if (tabPane.getSelectedIndex() == 0) {
            CheckPageButtons();
        }
    }
}


class PreviewFrame_spBottom_changeAdapter implements ChangeListener {
    private PreviewFrame adaptee;
    PreviewFrame_spBottom_changeAdapter(PreviewFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spBottom_stateChanged(e);
    }
}


class PreviewFrame_spRight_changeAdapter implements ChangeListener {
    private PreviewFrame adaptee;
    PreviewFrame_spRight_changeAdapter(PreviewFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spRight_stateChanged(e);
    }
}


class PreviewFrame_spTop_changeAdapter implements ChangeListener {
    private PreviewFrame adaptee;
    PreviewFrame_spTop_changeAdapter(PreviewFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spTop_stateChanged(e);
    }
}


class PreviewFrame_spLeft_changeAdapter implements ChangeListener {
    private PreviewFrame adaptee;
    PreviewFrame_spLeft_changeAdapter(PreviewFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spLeft_stateChanged(e);
    }
}


class PreviewFrame_cbProportional_actionAdapter implements ActionListener {
    private PreviewFrame adaptee;
    PreviewFrame_cbProportional_actionAdapter(PreviewFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbProportional_actionPerformed(e);
    }
}


class PreviewFrame_cbPrintBackground_actionAdapter implements ActionListener {
    private PreviewFrame adaptee;
    PreviewFrame_cbPrintBackground_actionAdapter(PreviewFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbPrintBackground_actionPerformed(e);
    }
}


class PreviewFrame_bSetup_actionAdapter implements ActionListener {
    private PreviewFrame adaptee;
    PreviewFrame_bSetup_actionAdapter(PreviewFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bSetup_actionPerformed(e);
    }
}


class PreviewFrame_radioButton2_actionAdapter implements ActionListener {
    private PreviewFrame adaptee;
    PreviewFrame_radioButton2_actionAdapter(PreviewFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.radioButton2_actionPerformed(e);
    }
}


class PreviewFrame_radioButton1_actionAdapter implements ActionListener {
    private PreviewFrame adaptee;
    PreviewFrame_radioButton1_actionAdapter(PreviewFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.radioButton1_actionPerformed(e);
    }
}


class PreviewFrame_bPrint_actionAdapter implements ActionListener {
    private PreviewFrame adaptee;
    PreviewFrame_bPrint_actionAdapter(PreviewFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bPrint_actionPerformed(e);
    }
}


class PreviewFrame_spLeft_inputMethodAdapter implements InputMethodListener {
    private PreviewFrame adaptee;
    PreviewFrame_spLeft_inputMethodAdapter(PreviewFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void inputMethodTextChanged(InputMethodEvent event) {
        adaptee.spLeft_inputMethodTextChanged(event);
    }

    public void caretPositionChanged(InputMethodEvent event) {
    }
}


class PreviewFrame_spTop_inputMethodAdapter implements InputMethodListener {
    private PreviewFrame adaptee;
    PreviewFrame_spTop_inputMethodAdapter(PreviewFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void inputMethodTextChanged(InputMethodEvent event) {
        adaptee.spTop_inputMethodTextChanged(event);
    }

    public void caretPositionChanged(InputMethodEvent event) {
    }
}


class PreviewFrame_spRight_inputMethodAdapter implements InputMethodListener {
    private PreviewFrame adaptee;
    PreviewFrame_spRight_inputMethodAdapter(PreviewFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void inputMethodTextChanged(InputMethodEvent event) {
        adaptee.spRight_inputMethodTextChanged(event);
    }

    public void caretPositionChanged(InputMethodEvent event) {
    }
}


class PreviewFrame_spBottom_inputMethodAdapter implements InputMethodListener {
    private PreviewFrame adaptee;
    PreviewFrame_spBottom_inputMethodAdapter(PreviewFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void inputMethodTextChanged(InputMethodEvent event) {
        adaptee.spBottom_inputMethodTextChanged(event);
    }

    public void caretPositionChanged(InputMethodEvent event) {
    }
}


class PreviewFrame_cbGrayScale_actionAdapter implements ActionListener {
    private PreviewFrame adaptee;
    PreviewFrame_cbGrayScale_actionAdapter(PreviewFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbGrayScale_actionPerformed(e);
    }
}


class PreviewFrame_cbPrinter_actionAdapter implements ActionListener {
    private PreviewFrame adaptee;
    PreviewFrame_cbPrinter_actionAdapter(PreviewFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbPrinter_actionPerformed(e);
    }
}


class PreviewFrame_bFirst_actionAdapter implements ActionListener {
    private PreviewFrame adaptee;
    PreviewFrame_bFirst_actionAdapter(PreviewFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bFirst_actionPerformed(e);
    }
}


class PreviewFrame_jButton1_actionAdapter implements ActionListener {
    private PreviewFrame adaptee;
    PreviewFrame_jButton1_actionAdapter(PreviewFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}


class PreviewFrame_jButton2_actionAdapter implements ActionListener {
    private PreviewFrame adaptee;
    PreviewFrame_jButton2_actionAdapter(PreviewFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


class PreviewFrame_bLast_actionAdapter implements ActionListener {
    private PreviewFrame adaptee;
    PreviewFrame_bLast_actionAdapter(PreviewFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bLast_actionPerformed(e);
    }
}


class PreviewFrame_cbMarginUnits_actionAdapter implements ActionListener {
    private PreviewFrame adaptee;
    PreviewFrame_cbMarginUnits_actionAdapter(PreviewFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbMarginUnits_actionPerformed(e);
    }
}


class PreviewFrame_jTabbedPane1_changeAdapter implements ChangeListener {
    private PreviewFrame adaptee;
    PreviewFrame_jTabbedPane1_changeAdapter(PreviewFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jTabbedPane1_stateChanged(e);
    }
}

