package com.steema.teechart.editors;

import java.awt.Rectangle;
import com.steema.teechart.languages.Language;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JList;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.steema.teechart.styles.SeriesCollection;

import com.steema.teechart.misc.Utils;
import com.steema.teechart.functions.Function;
import com.steema.teechart.styles.Series;
import com.steema.teechart.*;
import com.steema.teechart.editors.functions.*;

/**
 * <p>Title: FuncEdit class</p>
 *
 * <p>Description: Function Editor</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class FuncEdit extends SourceEditor {


    private PeriodEditor options;
    private CustomFunctionEditor custom;
    private CompressOHLCFunctionEditor compressOHLC;
    private CLVFunctionEditor clvFunction;
    private OBVFunctionEditor obvFunction;
    private CCIFunctionEditor cciFunction;
    private MovingAverageFunctionEditor movavgFunction;
    private PVOFunctionEditor pvoFunction;
    private BollingerFunctionEditor bollingerFunction;
    private MACDFunctionEditor macdFunction;
    private ADXFunctionEditor adxFunction;
    private SmoothingFunctionEditor smoothingFunction;

    private SelectList select;
    private boolean singleSource;
    private JTabbedPane jTabbedPane1;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanelSingle;
    private JPanel jPanelCompress;
    private JPanel jPanelMovAvg;
    private JPanel jPanelMACD;
    private JPanel jPanelCLV;
    private JPanel jPanelPVO;
    private JPanel jPanelADX;
    private JPanel jPanelCCI;
    private JPanel jPanelBollinger;
    private JPanel jPanelCustom;
    private JPanel jPanelOBV;
    private JPanel jPanelSmoothing;
    private com.steema.teechart.styles.Series series;
    private DefaultListModel model = new DefaultListModel();

    JComboBox cbSingle;
    JLabel jLabel1;
    JLabel jLabel2;
    JComboBox cbValues;
    JButton bNone;

    public FuncEdit() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * jbInit
     */
    protected void jbInit() throws Exception {
        super.jbInit();
        jTabbedPane1 = new JTabbedPane();
        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jPanelCompress = new JPanel();
        jPanelMovAvg = new JPanel();
        jPanelMACD = new JPanel();
        jPanelCLV = new JPanel();
        jPanelPVO = new JPanel();
        jPanelADX = new JPanel();
        jPanelCCI = new JPanel();
        jPanelBollinger = new JPanel();
        jPanelCustom = new JPanel();
        jPanelCustom.setLayout(borderLayout2);
        jPanelOBV = new JPanel();
        jPanelSmoothing = new JPanel();
        jTabbedPane1.setBorder(null);
        jTabbedPane1.setMinimumSize(new Dimension(100, 275));
        jTabbedPane1.setPreferredSize(new Dimension(100, 275));
        jTabbedPane1.addChangeListener(new FuncEdit_jTabbedPane1_changeAdapter(this));
        jPanel1.setLayout(borderLayout1);
        jLabel1=new JLabel(Language.getString("Series"));
        jLabel2=new JLabel(Language.getString("Values"));
        cbSingle = new JComboBox();
        cbValues = new JComboBox();
        jPanel2.setLayout(borderLayout2);
        jLabel1.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setHorizontalTextPosition(SwingConstants.RIGHT);
        jLabel1.setBounds(new Rectangle(13, 13, 50, 17));
        jLabel2.setDisplayedMnemonic(Language.getString("V").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabel2.setBounds(new Rectangle(25, 44, 38, 14));
        cbValues.setBounds(new Rectangle(68, 40, 115, 22));
        cbValues.addActionListener(new FuncEdit_cbValues_actionAdapter(this));
        cbSingle.setBounds(new Rectangle(68, 10, 115, 22));
        cbSingle.addActionListener(new FuncEdit_cbSingle_actionAdapter(this));
        bNone = new JButton();
        bNone.setBounds(new Rectangle(189, 8, 69, 25));
        bNone.setMnemonic(Language.getString("N").charAt(0));
        bNone.setText(Language.getString("None"));
        bNone.addActionListener(new FuncEdit_bNone_actionAdapter(this));

        jPanelSingle = new JPanel();
        jPanelSingle.setLayout(null);
        jPanelCompress.setLayout(borderLayout3);
        jPanelCLV.setLayout(borderLayout4);
        jPanelCCI.setLayout(borderLayout5);
        jPanelMovAvg.setLayout(borderLayout6);
        jPanelMACD.setLayout(borderLayout7);
        jPanelPVO.setLayout(borderLayout8);
        jPanelADX.setLayout(borderLayout9);
        jPanelBollinger.setLayout(borderLayout10);
        jPanelOBV.setLayout(borderLayout11);
        jPanelSmoothing.setLayout(borderLayout12);
        jPanelSingle.add(cbSingle);
        jPanelSingle.add(jLabel1);
        jPanelSingle.add(bNone);
        jPanelSingle.add(jLabel2);
        jPanelSingle.add(cbValues);


        jTabbedPane1.add(jPanel1, Language.getString("Function"));
        jTabbedPane1.add(jPanel2, Language.getString("Options"));
        jTabbedPane1.add(jPanelCompress, Language.getString("Compress"));
        jTabbedPane1.add(jPanelMovAvg, Language.getString("Mov_Avg"));
        jTabbedPane1.add(jPanelMACD, Language.getString("MACD"));
        jTabbedPane1.add(jPanelCLV, Language.getString("CLV"));
        jTabbedPane1.add(jPanelPVO, Language.getString("PVO"));
        jTabbedPane1.add(jPanelADX, Language.getString("ADX"));
        jTabbedPane1.add(jPanelCCI, Language.getString("CCI"));
        jTabbedPane1.add(jPanelBollinger, Language.getString("Bollinger"));
        jTabbedPane1.add(jPanelCustom, Language.getString("Custom"));
        jTabbedPane1.add(jPanelOBV, Language.getString("OBV"));
        jTabbedPane1.add(jPanelSmoothing, Language.getString("Smoothing"));

        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jPanel1.add(jPanelSingle, java.awt.BorderLayout.CENTER);
        super.jLabel1.setMaximumSize(new Dimension(52, 15));
        super.jLabel1.setMinimumSize(new Dimension(52, 15));
        super.jLabel1.setPreferredSize(new Dimension(52, 15));
        super.jLabel1.setText(Language.getString("Functions"));
    }

    public FuncEdit(com.steema.teechart.styles.Series s) {
        this();
        series=s;
        FillTeeFunctions(cbSources);

        if (series!=null) {

            if( series.getFunction()!=null) {
                singleSource = ((series.getFunction().noSourceRequired == false) &&
                                (series.getFunction().SingleSource == true));
            }
            jTabbedPane1.setSelectedIndex(0);

            select=new SelectList();
            select.controlToEnable=bApply;

            if (!singleSource)
                if ((jPanel1.getComponentCount() == 0) ||
                    (jPanel1.getComponent(0) != select)) {

                    jPanel1.removeAll();
                    jPanel1.add(select, BorderLayout.CENTER);
                    jPanel1.setVisible(true);
                    select.setVisible(true);

                }



            // Fill "from" list
            fillSeries(false);

            // Fill "to" list
            select.toList.removeAll();

            java.util.ArrayList a = series.dataSourceArray();

            if (a != null) {
                for (int c = 0; c < a.size(); c++) {

                  Object o = a.get(c);

                  if (o instanceof com.steema.teechart.styles.Series) {
                        com.steema.teechart.styles.Series tmp = (com.steema.
                                teechart.styles.Series) o;

                        JList tmpList = select.toList;
                        ((DefaultListModel) select.toList.getModel()).addElement(tmp);

                        int i = ((DefaultListModel) (select.fromList.getModel())).indexOf(tmp);

                        if (i != -1)
                            select.fromList.remove(i);
                    }
                }
            }

            // Set Function
            if (series.getFunction()==null)
                cbSources.setSelectedIndex(1);
            else {
                int index = functionIndexOf(series.getFunction());
                cbSources.setSelectedIndex(index+1);
            }

            select.EnableButtons();
        }

        bApply.setEnabled(false);
    }

    // Helper functions
    private void FillTeeFunctions(JComboBox aCombo) {
        if (series!=null)  {
            aCombo.removeAllItems();
            aCombo.addItem(Language.getString("FunctionNone"));

            for (int t = 0; t < com.steema.teechart.misc.Utils.FUNCTIONTYPESCOUNT;
                         t++) {
                try {
                    String s = Function.newInstance(Utils.functionTypesOf[t]).
                               getDescription();
                    aCombo.addItem(s.replaceAll(Language.getString("LineSeparator"), " "));
                } catch (Exception e) {
                }
            }
        }
    }

    private int functionIndexOf(Function f) {
        for (int t = 0; t < com.steema.teechart.misc.Utils.FUNCTIONTYPESCOUNT;
                     t++) {
            try {
                if (Utils.functionTypesOf[t]==f.getClass())
                           return t;
            } catch (Exception e) {
            }
        }

        return -1;
    }

    private void addSource(com.steema.teechart.styles.Series aSeries, boolean addCurrent) {
        Chart tmpChart = (Chart)series.getChart();

        if (tmpChart.isValidDataSource(series,aSeries)) {
            cbSingle.addItem(aSeries);

            if (addCurrent || (! series.hasDataSource(aSeries))) {
                model.addElement(aSeries.toString());
                select.fromList.setModel(model);
            }
        }
    }

    private void fillSources(boolean addCurrent) {
        for (int t=0; t<series.getChart().getSeriesCount(); t++) {
            addSource(series.getChart().getSeries(t),addCurrent);
        }
    }

    private void fillSeries(boolean addCurrent) {
        cbSingle.removeAllItems();
        select.fromList.removeAll();
        fillSources(addCurrent);

        if(singleSource)  {
            Object tmpSource = series.getDataSource();
            if ((tmpSource != null) && (tmpSource instanceof com.steema.teechart.styles.Series))  {
                int tmpIndex = ((DefaultComboBoxModel) (cbSingle.
                        getModel())).getIndexOf(tmpSource);
                if(tmpIndex != cbSingle.getSelectedIndex())  {
                    cbSingle.setSelectedIndex(tmpIndex);
                    cbSingle_actionPerformed(new ActionEvent(this,0,null));
                }
            }
            else cbSingle.setSelectedIndex(-1);
        }
    }

    private Function getFunctionType() {
       if (cbSources.getSelectedIndex()<1)
            return null;
        else  {
            try {
                return Function.newInstance(Utils.functionTypesOf[cbSources.
                                            getSelectedIndex() - 1]);
            } catch (Exception e) {
                return null;
            }
        }
    }

    private boolean addingPages;
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    BorderLayout borderLayout3 = new BorderLayout();
    BorderLayout borderLayout4 = new BorderLayout();
    BorderLayout borderLayout5 = new BorderLayout();
    BorderLayout borderLayout6 = new BorderLayout();
    BorderLayout borderLayout7 = new BorderLayout();
    BorderLayout borderLayout8 = new BorderLayout();
    BorderLayout borderLayout9 = new BorderLayout();
    BorderLayout borderLayout10 = new BorderLayout();
    BorderLayout borderLayout11 = new BorderLayout();
    BorderLayout borderLayout12 = new BorderLayout();


    private void addPages(boolean withOptions) {
        addingPages = true;
        jTabbedPane1.removeAll();
        addingPages = false;

        try {
            if ((cbSources.getSelectedIndex() > 0) &&
                (Function.newInstance(Utils.functionTypesOf[cbSources.
                                      getSelectedIndex() - 1]).getClass() ==
                 com.steema.teechart.functions.Custom.class)) {

                if (series.getFunction() == null ||
                    !(series.getFunction().getClass() ==
                      com.steema.teechart.functions.Custom.class))
                    series.setFunction(new com.steema.teechart.functions.Custom());

                jTabbedPane1.add(jPanelCustom, Language.getString("Custom"));
                jTabbedPane1.setSelectedComponent(jPanelCustom);
                jTabbedPane1_stateChanged(new ChangeEvent(this));
            }
            // CompressOHLC
            else if ((cbSources.getSelectedIndex() > 0) &&
                     (Function.newInstance(Utils.functionTypesOf[cbSources.
                                           getSelectedIndex() - 1]).getClass() ==
                      com.steema.teechart.functions.CompressOHLC.class)) {
                if (series.getFunction() == null ||
                    !(series.getFunction().getClass() ==
                      com.steema.teechart.functions.CompressOHLC.class))
                    series.setFunction(new com.steema.teechart.functions.
                                       CompressOHLC());
                jTabbedPane1.add(jPanel1, Language.getString("Source_Series"));
                jTabbedPane1.add(jPanelCompress, Language.getString("Compress"));
                jTabbedPane1.setSelectedIndex(0);
                jTabbedPane1_stateChanged(new ChangeEvent(this));
            }
            // CLVFunction
            else if ((cbSources.getSelectedIndex() > 0) &&
                     (Function.newInstance(Utils.functionTypesOf[cbSources.
                                           getSelectedIndex() - 1]).getClass() ==
                      com.steema.teechart.functions.CLV.class)) {
                if (series.getFunction() == null ||
                    !(series.getFunction().getClass() ==
                      com.steema.teechart.functions.CLV.class))
                    series.setFunction(new com.steema.teechart.functions.CLV());
                jTabbedPane1.add(jPanel1, Language.getString("Source_Series")); ;
                jTabbedPane1.add(jPanelCLV, Language.getString("C"));
                jTabbedPane1.setSelectedIndex(0);
                jTabbedPane1_stateChanged(new ChangeEvent(this));
            }
            // CCIFunction
            else if ((cbSources.getSelectedIndex() > 0) &&
                     (Function.newInstance(Utils.functionTypesOf[cbSources.
                                           getSelectedIndex() - 1]).getClass() ==
                      com.steema.teechart.functions.CCI.class)) {
                if (series.getFunction() == null ||
                    !(series.getFunction().getClass() ==
                      com.steema.teechart.functions.CCI.class))
                    series.setFunction(new com.steema.teechart.functions.CCI());
                jTabbedPane1.add(jPanel1, Language.getString("Source_Series")); ;
                jTabbedPane1.add(jPanelCCI, Language.getString("C"));
                jTabbedPane1.setSelectedIndex(0); ;
                jTabbedPane1_stateChanged(new ChangeEvent(this));
            }
            // MovingAverageFunction
            else if ((cbSources.getSelectedIndex() > 0) &&
                     (Function.newInstance(Utils.functionTypesOf[cbSources.
                                           getSelectedIndex() - 1]).getClass() ==
                      com.steema.teechart.functions.MovingAverage.class)) {
                if (series.getFunction() == null ||
                    !(series.getFunction().getClass() ==
                      com.steema.teechart.functions.MovingAverage.class))
                    series.setFunction(new com.steema.teechart.functions.
                                       MovingAverage());
                jTabbedPane1.add(jPanel1, Language.getString("Source_Series")); ;
                jTabbedPane1.add(jPanelMovAvg, Language.getString("Mov_Avg"));
                jTabbedPane1.setSelectedIndex(0); ;
                jTabbedPane1_stateChanged(new ChangeEvent(this));
            }
            // PVOFunction
            else if ((cbSources.getSelectedIndex() > 0) &&
                     (Function.newInstance(Utils.functionTypesOf[cbSources.
                                           getSelectedIndex() - 1]).getClass() ==
                      com.steema.teechart.functions.PVO.class)) {
                if (series.getFunction() == null ||
                    !(series.getFunction().getClass() ==
                      com.steema.teechart.functions.PVO.class))
                    series.setFunction(new com.steema.teechart.functions.PVO());
                jTabbedPane1.add(jPanel1, Language.getString("Source_Series")); ;
                jTabbedPane1.add(jPanelPVO, Language.getString("PVO"));
                jTabbedPane1.setSelectedIndex(0); ;
                jTabbedPane1_stateChanged(new ChangeEvent(this));
            }
            //Bollinger
            else if((cbSources.getSelectedIndex()>0) &&
                    (Function.newInstance(Utils.functionTypesOf[cbSources.getSelectedIndex()-1]).getClass() == com.steema.teechart.functions.Bollinger.class))
            {
                if (series.getFunction()==null || !(series.getFunction().getClass() == com.steema.teechart.functions.Bollinger.class))
                 series.setFunction(new com.steema.teechart.functions.Bollinger());
                jTabbedPane1.add(jPanel1,Language.getString("Source_Series"));
                jTabbedPane1.add(jPanelBollinger,Language.getString("Bollinger"));
                jTabbedPane1.setSelectedIndex(0);
                jTabbedPane1_stateChanged(new ChangeEvent(this));
            }
            // ADX
            else if((cbSources.getSelectedIndex()>0) && (Function.newInstance(Utils.functionTypesOf[cbSources.getSelectedIndex()-1]).getClass() == com.steema.teechart.functions.ADX.class))
            {
                if (series.getFunction()==null || !(series.getFunction().getClass() == com.steema.teechart.functions.ADX.class))
                    series.setFunction(new com.steema.teechart.functions.ADX());
                jTabbedPane1.add(jPanel1,Language.getString("Source_Series"));
                jTabbedPane1.add(jPanelADX ,Language.getString("ADX"));
                jTabbedPane1.setSelectedIndex(0);
                jTabbedPane1_stateChanged(new ChangeEvent(this));
            }
            //MACD
            else if((cbSources.getSelectedIndex()>0) && (Function.newInstance(Utils.functionTypesOf[cbSources.getSelectedIndex()-1]).getClass() == com.steema.teechart.functions.MACD.class))
            {
                if (series.getFunction()==null || !(series.getFunction().getClass() == com.steema.teechart.functions.MACD.class))
                    series.setFunction(new com.steema.teechart.functions.MACD());
                jTabbedPane1.add(jPanel1,Language.getString("Source_Series"));
                jTabbedPane1.add(jPanelMACD ,Language.getString("MACD"));
                jTabbedPane1.setSelectedIndex(0);
                jTabbedPane1_stateChanged(new ChangeEvent(this));
            }

             /** OBV
                     else if((cbSources.getSelectedIndex()>0) &&
                    (Function.newInstance(misc.Utils.functionTypesOf[cbSources.getSelectedIndex()-1]).getClass() == com.steema.teechart.functions.OBV.class))
                     {
                if (series.getFunction()==null || !(series.getFunction().getClass() == com.steeja.teechart.functions.OBV.class))
             series.setFunction(new com.steema.teechart.functions.OBV());
                jTabbedPane1.add(jPanel2,"Source Series");;
                jTabbedPane1.add(jPanelOBV ,"OBV");
                jTabbedPane1.setSelectedIndex(0);;
                tabControl1_SelectedIndexChanged(this, new System.EventArgs());
                     }

              Smoothing
                     else if((cbSources.getSelectedIndex()>0) &&
                    (Function.newInstance(misc.Utils.functionTypesOf[cbSources.getSelectedIndex()-1]).getClass() == com.steema.teechart.functions.Smoothing.class))
                     {
                if (series.getFunction()==null || !(series.getFunction().getClass() == com.steema.teechart.functions.Smoothing.class))
             series.setFunction(new com.steema.teechart.functions.Smoothing());
                jTabbedPane1.add(jPanel2,"Source Series");
                jTabbedPane1.add(jPanelSmoothing ,"Smoothing");
                jTabbedPane1.setSelectedIndex(0);
                tabControl1_getSelectedIndex()Changed(this, new System.EventArgs());
                     }
             */

            else {
                if (cbSources.getSelectedIndex() != 0)
                    if (series.getFunction() == null ||
                        !(series.getFunction().getClass() ==
                          Function.
                          newInstance(Utils.functionTypesOf[cbSources.getSelectedIndex() -
                                      1]).getClass()))
                        series.setFunction(Function.newInstance(Utils.
                                functionTypesOf[cbSources.getSelectedIndex() -
                                1]));

                jTabbedPane1.add(jPanel1, Language.getString("Source_Series"));

                if (withOptions)
                    jTabbedPane1.add(jPanel2, Language.getString("Options"));

                jTabbedPane1_stateChanged(new ChangeEvent(this));
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this,e.toString());
        }
    }

    private com.steema.teechart.functions.Function functionClass() {
        return getFunctionType();
    }

    private void tryCreateNewFunction()  {
        Function tmpClass = functionClass();

        if(tmpClass!=null) {
            if(series.getFunction() == null) {
                series.setFunction(tmpClass);
                bApply.setEnabled(false);
            }
            else if(tmpClass.getClass() != series.getFunction().getClass()) {
                series.setFunction(null);
                series.setFunction(tmpClass);
                bApply.setEnabled(false);
            }
            else
                bApply.setEnabled(true);
        }
    }

    public void cbSources_actionPerformed(ActionEvent e) {
        tryCreateNewFunction();

        addPages(getFunctionType() != null);

        if(getFunctionType() != null) {

            singleSource = ((series.getFunction().noSourceRequired == false) &&
                            (series.getFunction().SingleSource == true));

            if (singleSource) {
                if ((jPanel1.getComponentCount() == 0) ||
                    (jPanel1.getComponent(0) != jPanelSingle)) {
                    jPanel1.removeAll();
                    jPanel1.add(jPanelSingle, BorderLayout.CENTER);
                    jPanelSingle.setVisible(true);
                    jLabel2.setVisible(!series.getFunction().HideSourceList);
                    cbValues.setVisible(!series.getFunction().HideSourceList);
                }
            } else {
                if ((jPanel1.getComponentCount() == 0) ||
                    (jPanel1.getComponent(0) != select)) {

                    select = new SelectList();
                    select.controlToEnable = bApply;

                    jPanel1.removeAll();
                    jPanel1.add(select, BorderLayout.CENTER);
                    jPanel1.setVisible(true);
                    select.setVisible(true);
                }
            }
        }
    }

    protected void clearMembers(){
        series.setLabelMember("");

        for (int t = 0; t < series.getValuesLists().size(); t++)
            series.getValueList(t).setDataMember("");
    }

    private void doApply() {
            clearMembers();

            if(singleSource) {
                    if(cbSingle.getSelectedIndex() == -1){
                            series.setDataSource(null);
                            series.getMandatory().setDataMember("");
                    }
                    else {
                        series.setDataSource(cbSingle.getSelectedItem());
                        if (cbValues.getSelectedIndex() == -1)
                            series.getMandatory().setDataMember("");
                        else
                            series.getMandatory().setDataMember(cbValues.getSelectedItem().toString());
                    }
            }
            else

            if (select.toList.getModel().getSize()==0)
                series.setDataSource(null);
            else {
                SeriesCollection tmpSeries = new SeriesCollection(series.getChart());
                tmpSeries.clear();
                for (int t = 0; t < series.getChart().getSeriesCount(); t++) {
                    Series s = series.getChart().getSeries(t);
                    tmpSeries.internalAdd(s);
                }

                for (int i = 0; i < select.toList.getModel().getSize(); i++) {
                    String s=select.toList.getModel().getElementAt(i).toString();
                    Series tmp = tmpSeries.withTitle(s);
                    try {
                        series.checkOtherSeries(tmp);
                    } catch (Exception ex) {
                    }
                }

                if (select.toList.getModel().getSize()==1) {
                    Series tmp = tmpSeries.withTitle(select.toList.getModel().getElementAt(0).toString());
                    series.setDataSource(tmp);
                }
                else {
                    Object[] tmpList=new Object[select.toList.getModel().getSize()];
                    int t=0;
                    for (int tt=0;tt<select.toList.getModel().getSize();tt++) {
                        String s = select.toList.getModel().getElementAt(tt).toString();
                        Series tmp=tmpSeries.withTitle(s);
                        tmpList[tt]=tmp;
                    }
                    series.setDataSource(tmpList);
                }
            }

            Function f=getFunctionType();
            if (f!=null) {
                    if ((series.getFunction()==null) || (series.getFunction().getClass()!=f.getClass()))
                            series.setFunction(f);
                    else
                            series.checkDataSource();
            }
            else series.setFunction(null);
    }

    public void bApply_actionPerformed(ActionEvent e) {
        if (options!=null)
            options.Apply();

        doApply();
        bApply.setEnabled(false);
    }

    public void jTabbedPane1_stateChanged(ChangeEvent e) {
        if(!addingPages) {
            if (jTabbedPane1.getSelectedComponent()==jPanel2) {
                if (bApply.isEnabled())
                    bApply_actionPerformed(new ActionEvent(this,0,null));
                bApply.setEnabled(false);

                if ((options==null) && (series.getFunction()!=null)) {
                    options=new PeriodEditor(series.getFunction());
                    jPanel2.add(options, java.awt.BorderLayout.CENTER);
                    options.setVisible(true);

                }
            }
            // Custom
            else if ((jTabbedPane1.getSelectedComponent()==jPanelCustom) && (getFunctionType().getClass() == com.steema.teechart.functions.Custom.class))
            {
                if (bApply.isEnabled())
                    bApply_actionPerformed(new ActionEvent(this,0,null));
                bApply.setEnabled(false);

                if (series.getFunction()!=null) {
                    custom=new CustomFunctionEditor();
                    custom.setFunction(series.getFunction());
                    custom.controlToEnable=bApply;
                    jPanelCustom.add(custom, java.awt.BorderLayout.CENTER);
                    custom.setVisible(true);

                }
            }
            // CompressOHLC
            else if ((jTabbedPane1.getSelectedComponent()==jPanelCompress) && (getFunctionType().getClass() == com.steema.teechart.functions.CompressOHLC.class))
            {
                if (bApply.isEnabled())
                    bApply_actionPerformed(new ActionEvent(this,0,null));
                bApply.setEnabled(false);

                if (series.getFunction()!=null) {
                    compressOHLC=new CompressOHLCFunctionEditor();
                    compressOHLC.setFunction(series.getFunction());
                    compressOHLC.controlToEnable=bApply;
                    jPanelCompress.add(compressOHLC, java.awt.BorderLayout.CENTER);
                    compressOHLC.setVisible(true);

                }
            }
            // CLV
            else if ((jTabbedPane1.getSelectedComponent()==jPanelCLV) && (getFunctionType().getClass() == com.steema.teechart.functions.CLV.class))
            {
                if (bApply.isEnabled())
                    bApply_actionPerformed(new ActionEvent(this,0,null));
                bApply.setEnabled(false);

                if (series.getFunction()!=null) {
                    clvFunction=new CLVFunctionEditor(series, select.fromList);
                    clvFunction.controlToEnable=bApply;
                    jPanelCLV.add(clvFunction, java.awt.BorderLayout.CENTER);
                    clvFunction.setVisible(true);

                }
            }
            // OBV
            else if ((jTabbedPane1.getSelectedComponent()==jPanelOBV) && (getFunctionType().getClass() == com.steema.teechart.functions.OBV.class))
            {
                if (bApply.isEnabled())
                    bApply_actionPerformed(new ActionEvent(this,0,null));
                bApply.setEnabled(false);

                if (series.getFunction()!=null) {
                    obvFunction=new  OBVFunctionEditor(series, select.fromList);
                    obvFunction.controlToEnable=bApply;
                    jPanelOBV.add(obvFunction, java.awt.BorderLayout.CENTER);
                    obvFunction.setVisible(true);

                }
            }
            // CCI
            else if ((jTabbedPane1.getSelectedComponent()==jPanelCCI) && (getFunctionType().getClass() == com.steema.teechart.functions.CCI.class))
            {
                if (bApply.isEnabled())
                    bApply_actionPerformed(new ActionEvent(this,0,null));
                bApply.setEnabled(false);

                if (series.getFunction()!=null) {
                    cciFunction=new  CCIFunctionEditor();
                    cciFunction.setFunction(series.getFunction());
                    cciFunction.controlToEnable=bApply;
                    jPanelCCI.add(cciFunction, java.awt.BorderLayout.CENTER);
                    cciFunction.setVisible(true);

                }
            }
            // MovingAverage
            else if ((jTabbedPane1.getSelectedComponent()==jPanelMovAvg) && (getFunctionType().getClass() == com.steema.teechart.functions.MovingAverage.class))
            {
                if (bApply.isEnabled())
                    bApply_actionPerformed(new ActionEvent(this,0,null));
                bApply.setEnabled(false);

                if (series.getFunction()!=null) {
                    movavgFunction=new MovingAverageFunctionEditor();
                    movavgFunction.setFunction(series.getFunction());
                    movavgFunction.controlToEnable=bApply;
                    jPanelMovAvg.add(movavgFunction, java.awt.BorderLayout.CENTER);
                    movavgFunction.setVisible(true);

                }
            }
            // PVO
            else if ((jTabbedPane1.getSelectedComponent()==jPanelPVO) && (getFunctionType().getClass() == com.steema.teechart.functions.PVO.class))
            {
                if (bApply.isEnabled())
                    bApply_actionPerformed(new ActionEvent(this,0,null));
                bApply.setEnabled(false);

                if (series.getFunction()!=null) {
                    pvoFunction=new PVOFunctionEditor();
                    pvoFunction.setFunction(series.getFunction());
                    pvoFunction.controlToEnable=bApply;
                    jPanelPVO.add(pvoFunction, java.awt.BorderLayout.CENTER);
                    pvoFunction.setVisible(true);

                }
            }
            else if ((jTabbedPane1.getSelectedComponent()==jPanelBollinger) && (getFunctionType().getClass() == com.steema.teechart.functions.Bollinger.class))
            {
                if (bApply.isEnabled())
                    bApply_actionPerformed(new ActionEvent(this,0,null));
                bApply.setEnabled(false);

                if (series.getFunction()!=null) {
                    bollingerFunction=new BollingerFunctionEditor();
                    bollingerFunction.setFunction(series.getFunction());
                    bollingerFunction.controlToEnable=bApply;
                    jPanelBollinger.add(bollingerFunction, java.awt.BorderLayout.CENTER);
                    bollingerFunction.setVisible(true);
                }
            }
            // MACD
            else if ((jTabbedPane1.getSelectedComponent()==jPanelMACD) && (getFunctionType().getClass() == com.steema.teechart.functions.MACD.class))
            {
                if (bApply.isEnabled())
                    bApply_actionPerformed(new ActionEvent(this,0,null));
                bApply.setEnabled(false);

                if (series.getFunction()!=null) {
                    macdFunction=new MACDFunctionEditor();
                    macdFunction.setFunction(series.getFunction());
                    macdFunction.controlToEnable=bApply;
                    jPanelMACD.add(macdFunction, java.awt.BorderLayout.CENTER);
                    macdFunction.setVisible(true);
                }
            }
            // ADX
            else if ((jTabbedPane1.getSelectedComponent()==jPanelADX) && (getFunctionType().getClass() == com.steema.teechart.functions.ADX.class))
            {
                if (bApply.isEnabled())
                    bApply_actionPerformed(new ActionEvent(this,0,null));
                bApply.setEnabled(false);

                if (series.getFunction()!=null) {
                    adxFunction=new ADXFunctionEditor();
                    adxFunction.setFunction(series.getFunction());
                    adxFunction.controlToEnable=bApply;
                    jPanelADX.add(adxFunction, java.awt.BorderLayout.CENTER);
                    adxFunction.setVisible(true);

                }
            }
            // Smoothing
            else if ((jTabbedPane1.getSelectedComponent()==jPanelSmoothing) && (getFunctionType().getClass() == com.steema.teechart.functions.Smoothing.class))
            {
                if (bApply.isEnabled())
                    bApply_actionPerformed(new ActionEvent(this,0,null));
                bApply.setEnabled(false);

                if (series.getFunction()!=null) {
                    smoothingFunction=new SmoothingFunctionEditor();
                    smoothingFunction.setFunction(series.getFunction());
                    smoothingFunction.controlToEnable=bApply;
                    jPanelSmoothing.add(smoothingFunction, java.awt.BorderLayout.CENTER);
                    smoothingFunction.setVisible(true);

                }
            }
        }
    }

    public void cbSingle_actionPerformed(ActionEvent e) {
        bApply.setEnabled(true);
        cbValues.removeAllItems();
        cbValues.setEnabled(cbSingle.getSelectedIndex() != -1);
        bNone.setEnabled(cbValues.isEnabled());

        if (cbValues.isEnabled()) {
            Series tmp = (Series)cbSingle.getSelectedItem();
            for(int i = 1; i < tmp.getValuesLists().size(); ++i){
                DefaultComboBoxModel model = new DefaultComboBoxModel();
                model.addElement(tmp.getValueList(i).getName());
                cbValues.setModel(model);
            }
            if (cbValues.getModel().getSize() > 0)
                cbValues.setSelectedIndex(0);
        }
    }

    public void bNone_actionPerformed(ActionEvent e) {
        cbSingle.setSelectedIndex(-1);
        cbSingle_actionPerformed(new ActionEvent(this,0,null));
    }

    public void cbValues_actionPerformed(ActionEvent e) {
        bApply.setEnabled(true);
    }
}


final class FuncEdit_cbValues_actionAdapter implements ActionListener {
    private FuncEdit adaptee;
    FuncEdit_cbValues_actionAdapter(FuncEdit adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbValues_actionPerformed(e);
    }
}


final class FuncEdit_bNone_actionAdapter implements ActionListener {
    private FuncEdit adaptee;
    FuncEdit_bNone_actionAdapter(FuncEdit adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bNone_actionPerformed(e);
    }
}


final class FuncEdit_jTabbedPane1_changeAdapter implements ChangeListener {
    private FuncEdit adaptee;
    FuncEdit_jTabbedPane1_changeAdapter(FuncEdit adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.jTabbedPane1_stateChanged(e);
    }
}


final class FuncEdit_cbSingle_actionAdapter implements ActionListener {
    private FuncEdit adaptee;
    FuncEdit_cbSingle_actionAdapter(FuncEdit adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbSingle_actionPerformed(e);
    }
}

