package com.steema.teechart.editors;

import java.awt.Rectangle;
import javax.swing.*;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: SummaryEditor Class</p>
 *
 * <p>Description: Summary Editor</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class SummaryEditor extends com.steema.teechart.editors.SourceEditor {

    public SummaryEditor() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        cbAgg.addItem(Language.getString("Sum"));
        cbAgg.addItem(Language.getString("Count"));
        cbAgg.addItem(Language.getString("High"));
        cbAgg.addItem(Language.getString("Low"));
        cbAgg.addItem(Language.getString("Avg"));

        cbTimeStep.addItem(Language.getString("Hour"));
        cbTimeStep.addItem(Language.getString("Day"));
        cbTimeStep.addItem(Language.getString("Week"));
        cbTimeStep.addItem(Language.getString("WeekDay"));
        cbTimeStep.addItem(Language.getString("Month"));
        cbTimeStep.addItem(Language.getString("Quarter"));
        cbTimeStep.addItem(Language.getString("Year"));

        // .... aqui...
    }

    protected void jbInit() throws Exception {
        this.setLayout(null);
        cbTimeStep.setBounds(new Rectangle(74, 85, 108, 19));
        cbSort.setBounds(new Rectangle(74, 116, 133, 19));
        cbAgg.setBounds(new Rectangle(74, 53, 108, 19));
        jLabel1.setDisplayedMnemonic(Language.getString("G").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText(Language.getString("Goup_by"));
        jLabel1.setBounds(new Rectangle(7, 88, 54, 15));
        jLabel2.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setText(Language.getString("Sort_by"));
        jLabel2.setBounds(new Rectangle(2, 120, 59, 15));
        jLabel3.setDisplayedMnemonic(Language.getString("C").charAt(0));
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setText(Language.getString("Calc"));
        jLabel3.setBounds(new Rectangle(27, 56, 34, 15));
        jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel4.setText(Language.getString("of"));
        jLabel4.setBounds(new Rectangle(185, 55, 18, 15));
        cbGroup.setBounds(new Rectangle(217, 85, 141, 19));
        cbSortType.setBounds(new Rectangle(217, 116, 141, 19));
        cbValue.setBounds(new Rectangle(217, 53, 141, 19));
        this.add(jLabel2);
        this.add(jLabel1);
        this.add(jLabel3);
        this.add(jLabel4);
        this.add(cbAgg);
        this.add(cbTimeStep);
        this.add(cbSort);
        this.add(cbGroup);
        this.add(cbValue);
        this.add(cbSortType);
    }

    JComboBox cbTimeStep = new JComboBox();
    JComboBox cbSort = new JComboBox();
    JComboBox cbAgg = new JComboBox();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JComboBox cbGroup = new JComboBox();
    JComboBox cbSortType = new JComboBox();
    JComboBox cbValue = new JComboBox();

}



