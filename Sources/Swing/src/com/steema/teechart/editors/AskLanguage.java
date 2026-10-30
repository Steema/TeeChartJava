package com.steema.teechart.editors;

import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JList;
import java.awt.Rectangle;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: AskLanguage class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class AskLanguage extends JDialog {

    private DefaultListModel listModel;

    public AskLanguage() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        listModel = new DefaultListModel();

        listModel.addElement(Language.getString("English"));
        listModel.addElement(Language.getString("Arabic"));
        listModel.addElement(Language.getString("Brazilian"));
        listModel.addElement(Language.getString("Catalan"));
        listModel.addElement(Language.getString("Chinese_Simp"));
        listModel.addElement(Language.getString("Chinese_Trad"));
        listModel.addElement(Language.getString("Danish"));
        listModel.addElement(Language.getString("Dutch"));
        listModel.addElement(Language.getString("Finnish"));
        listModel.addElement(Language.getString("French"));
        listModel.addElement(Language.getString("Galician"));
        listModel.addElement(Language.getString("German"));
        listModel.addElement(Language.getString("Hebrew"));
        listModel.addElement(Language.getString("Hellenic"));
        listModel.addElement(Language.getString("Hungarian"));
        listModel.addElement(Language.getString("Indonesian"));
        listModel.addElement(Language.getString("Italian"));
        listModel.addElement(Language.getString("Japanese"));
        listModel.addElement(Language.getString("Korean"));
        listModel.addElement(Language.getString("Norwegian"));
        listModel.addElement(Language.getString("Polish"));
        listModel.addElement(Language.getString("Portuguese"));
        listModel.addElement(Language.getString("Russian"));
        listModel.addElement(Language.getString("Slovak"));
        listModel.addElement(Language.getString("Slovene"));
        listModel.addElement(Language.getString("Spanish"));
        listModel.addElement(Language.getString("Swedish"));
        listModel.addElement(Language.getString("Turkish"));
        listModel.addElement(Language.getString("Ukrainian"));

        jList1.setModel(listModel);
        jList1.setSelectedIndex(language);
    }

    public static final int OK = 0;
    public static final int CANCEL = 1;

    private int result = CANCEL;
    public int language = 0;
    public JLabel lbLangs;

    JList jList1 = new JList();
    JButton jButton1 = new JButton();

    public AskLanguage(int value) {
        this();
        language = value;
    }

    static public void changeLanguage(int value) {
        AskLanguage a = new AskLanguage(value);
        a.showModal();
    }

    public int showModal() {
        setVisible(true);
        return result;
    }

    public DefaultListModel getLanguages()
    {
        return listModel;
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(null);
        jList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList1.setBounds(new Rectangle(14, 12, 194, 254));
        this.getContentPane().add(jList1);
        jButton1.setBounds(new Rectangle(120, 284, 88, 30));
        jButton1.setActionCommand(Language.getString("OK"));
        jButton1.setText(Language.getString("OK"));
        jButton1.addActionListener(new AskLanguage_jButton1_actionAdapter(this));
        this.getContentPane().add(jButton1);
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        result = OK;
    }
}


final class AskLanguage_jButton1_actionAdapter implements ActionListener {
    private AskLanguage adaptee;
    AskLanguage_jButton1_actionAdapter(AskLanguage adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}

