package com.steema.teechart.editors;

import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.Font;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: SelectList Class</p>
 *
 * <p>Description: SelectList Editor</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class SelectList extends JPanel {

    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, Language.getString("Size"));
    JButton bMoveUp = new JButton();
    JButton bMoveDown = new JButton();
    Border border3 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border4 = new TitledBorder(border3, Language.getString("Shadow"));
    Border border5 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    Border border6 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
            Color.white, Color.white, new Color(115, 114, 105),
            new Color(165, 163, 151));
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JList fromList = new JList();
    Border border7 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(178, 178, 178));
    JList toList = new JList();
    Border border8 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(178, 178, 178));
    JButton bRightAll = new JButton();
    JButton bLeftOne = new JButton();
    JButton bLeftAll = new JButton();
    JButton bRightOne = new JButton();
    Component controlToEnable=null;

    public SelectList() {
        try {
            jbInit();
            Load();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        setLayout(null);
        bMoveUp.setBounds(new Rectangle(300, 22, 29, 23));
        bMoveUp.setActionCommand(".");
        bMoveUp.setMnemonic(Language.getString("O").charAt(0));
        bMoveUp.addActionListener(new SelectList_bMoveUP_actionAdapter(this));
        bMoveDown.setBounds(new Rectangle(300, 46, 29, 23));
        bMoveDown.setMnemonic(Language.getString("F").charAt(0));
        bMoveDown.addActionListener(new SelectList_bMoveDown_actionAdapter(this));
        jLabel1.setDisplayedMnemonic(Language.getString("V").charAt(0));
        jLabel1.setText(Language.getString("Available"));
        jLabel1.setBounds(new Rectangle(9, 6, 69, 15));
        jLabel2.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel2.setText(Language.getString("Selected"));
        jLabel2.setBounds(new Rectangle(180, 6, 60, 15));
        fromList.setBorder(border7);
        fromList.setBounds(new Rectangle(3, 23, 125, 124));
        fromList.addInputMethodListener(new
                                        SelectList_fromList_inputMethodAdapter(this));
        fromList.addMouseListener(new SelectList_fromList_mouseAdapter(this));
        toList.setBorder(border8);
        toList.setBounds(new Rectangle(174, 23, 125, 124));
        toList.addInputMethodListener(new SelectList_toList_inputMethodAdapter(this));
        toList.addMouseListener(new SelectList_toList_mouseAdapter(this));
        bRightAll.setBounds(new Rectangle(129, 48, 44, 23));
        bRightAll.setFont(new java.awt.Font(Language.getString("Dialog"), Font.PLAIN, 8));
        bRightAll.setActionCommand(".");
        bRightAll.setDisabledIcon(null);
        bRightAll.setMnemonic(Language.getString("O").charAt(0));
        bRightAll.setText(">>");
        bRightAll.addActionListener(new SelectList_bRightAll_actionAdapter(this));
        bLeftOne.setBounds(new Rectangle(129, 72, 44, 23));
        bLeftOne.setFont(new java.awt.Font(Language.getString("Dialog"), Font.PLAIN, 11));
        bLeftOne.setActionCommand(".");
        bLeftOne.setMnemonic(Language.getString("O").charAt(0));
        bLeftOne.setText("<");
        bLeftOne.addActionListener(new SelectList_bLeftOne_actionAdapter(this));
        bLeftAll.setBounds(new Rectangle(129, 96, 44, 23));
        bLeftAll.setFont(new java.awt.Font(Language.getString("Dialog"), Font.PLAIN, 8));
        bLeftAll.setActionCommand(".");
        bLeftAll.setMnemonic(Language.getString("O").charAt(0));
        bLeftAll.setText("<<");
        bLeftAll.addActionListener(new SelectList_bLeftAll_actionAdapter(this));
        bRightOne.setBounds(new Rectangle(129, 24, 44, 23));
        bRightOne.setFont(new java.awt.Font(Language.getString("Dialog"), Font.PLAIN, 11));
        bRightOne.setActionCommand(".");
        bRightOne.setMnemonic(Language.getString("O").charAt(0));
        bRightOne.setText(">");
        bRightOne.addActionListener(new SelectList_bRightOne_actionAdapter(this));
        this.add(jLabel1);
        this.add(fromList);
        this.add(jLabel2);
        this.add(bLeftAll);
        this.add(bRightOne);
        this.add(bRightAll);
        this.add(bLeftOne);
        this.add(bMoveUp);
        this.add(bMoveDown);
        this.add(toList);

        toList.setModel(new DefaultListModel());
        fromList.setModel(new DefaultListModel());
    }

    public void EnableButtons() {
      bRightOne.setEnabled(fromList.getSelectedIndex()!=-1);
      bRightAll.setEnabled(fromList.getModel().getSize()>0);

      bLeftOne.setEnabled(toList.getSelectedIndex()!=-1);
      bLeftAll.setEnabled(toList.getModel().getSize()>0);

      bMoveUp.setEnabled(bLeftOne.isEnabled() && (toList.getSelectedIndex()>0));
      bMoveDown.setEnabled(bLeftOne.isEnabled() && (toList.getSelectedIndex()<toList.getModel().getSize()-1));
    }

    private void MoveListAll(JList source,JList dest) {
        DefaultListModel listModelDest = new DefaultListModel();
        if (dest.getModel().getSize()>0)
            listModelDest = ((DefaultListModel)dest.getModel());

        for (int i=0;i<source.getModel().getSize();i++)
            listModelDest.addElement(source.getModel().getElementAt(i));

        dest.setModel(listModelDest);
        DefaultListModel listModelSource = new DefaultListModel();
        listModelSource.clear();
        source.setModel(listModelSource);
    }

    private void MoveList(JList source,JList dest) {
      int max = source.getMaxSelectionIndex();
      DefaultListModel listModelDest = new DefaultListModel();
      DefaultListModel listModelSource = new DefaultListModel();
      listModelSource=((DefaultListModel)source.getModel());
      if (dest.getModel().getSize()>0)
          listModelDest = ((DefaultListModel)dest.getModel());

      for (int i = source.getMinSelectionIndex(); i <= max; i++)
          if (source.isSelectedIndex(i)) {
              listModelDest.addElement(source.getModel().getElementAt(i));
              listModelSource.removeElementAt(i);
          }
      dest.setModel(listModelDest);
    }

    private void Load() {
        String s=Language.getString("com_steema_teechart");
        bMoveUp.setIcon(new ImageIcon(SelectList.class.getResource(Language.getString("images_Up_gif"))));
        bMoveDown.setIcon(new ImageIcon(SelectList.class.getResource(Language.getString("images_Down_gif"))));
        EnableButtons();
        if (fromList.getModel().getSize()>0) fromList.requestFocus(); else toList.requestFocus();
    }

    public void bRightOne_actionPerformed(ActionEvent e) {
        MoveList(fromList,toList);
        Changed();
        if (fromList.getModel().getSize()==0) toList.requestFocus();
    }

    public void bLeftOne_actionPerformed(ActionEvent e) {
        MoveList(toList,fromList);
        Changed();
    }

    public void bRightAll_actionPerformed(ActionEvent e) {
        MoveListAll(fromList,toList);
        Changed();
    }

    public void bLeftAll_actionPerformed(ActionEvent e) {
        MoveListAll(toList,fromList);
        Changed();
    }

    public void fromList_mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1)
            EnableButtons();
        else
            if ( e.getClickCount() == 2 ) {
                bRightOne_actionPerformed(null);
                toList.setSelectedIndex(toList.getModel().getSize()-1);
                EnableButtons();
            }
    }

    public void toList_mouseClicked(MouseEvent e) {
        if (e.getClickCount()==1)
            EnableButtons();
        else
        if ( e.getClickCount() == 2 )
            bLeftOne_actionPerformed(null);
    }

    public void bMoveUP_actionPerformed(ActionEvent e) {
        bMoveDown_actionPerformed(e);
    }

    public void bMoveDown_actionPerformed(ActionEvent e) {
        int tmp= (e.getSource()== bMoveUp) ? -1 : 1;
        int tmp2=toList.getSelectedIndex();

        // exchange
        Object o1=toList.getModel().getElementAt(tmp2);
        Object o2=toList.getModel().getElementAt(tmp2+tmp);
        ((DefaultListModel)toList.getModel()).setElementAt(o2,tmp2);
        ((DefaultListModel)toList.getModel()).setElementAt(o1,tmp2+tmp);

        toList.setSelectedIndex(tmp2+tmp);
        toList.requestFocus();
        Changed();
    }

    private void Changed()
    {
      EnableButtons();
      if (controlToEnable!=null) controlToEnable.setEnabled(true);
    }

    public void fromList_caretPositionChanged(InputMethodEvent event) {
        EnableButtons();
    }

    public void toList_caretPositionChanged(InputMethodEvent event) {
        EnableButtons();
    }
}


class SelectList_bMoveDown_actionAdapter implements ActionListener {
    private SelectList adaptee;
    SelectList_bMoveDown_actionAdapter(SelectList adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bMoveDown_actionPerformed(e);
    }
}


class SelectList_bMoveUP_actionAdapter implements ActionListener {
    private SelectList adaptee;
    SelectList_bMoveUP_actionAdapter(SelectList adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bMoveUP_actionPerformed(e);
    }
}


class SelectList_toList_mouseAdapter extends MouseAdapter {
    private SelectList adaptee;
    SelectList_toList_mouseAdapter(SelectList adaptee) {
        this.adaptee = adaptee;
    }

    public void mouseClicked(MouseEvent e) {
        adaptee.toList_mouseClicked(e);
    }
}


class SelectList_fromList_mouseAdapter extends MouseAdapter {
    private SelectList adaptee;
    SelectList_fromList_mouseAdapter(SelectList adaptee) {
        this.adaptee = adaptee;
    }

    public void mouseClicked(MouseEvent e) {
        adaptee.fromList_mouseClicked(e);
    }
}


class SelectList_toList_inputMethodAdapter implements InputMethodListener {
    private SelectList adaptee;
    SelectList_toList_inputMethodAdapter(SelectList adaptee) {
        this.adaptee = adaptee;
    }

    public void inputMethodTextChanged(InputMethodEvent event) {
    }

    public void caretPositionChanged(InputMethodEvent event) {
        adaptee.toList_caretPositionChanged(event);
    }
}


class SelectList_fromList_inputMethodAdapter implements InputMethodListener {
    private SelectList adaptee;
    SelectList_fromList_inputMethodAdapter(SelectList adaptee) {
        this.adaptee = adaptee;
    }

    public void inputMethodTextChanged(InputMethodEvent event) {
    }

    public void caretPositionChanged(InputMethodEvent event) {
        adaptee.fromList_caretPositionChanged(event);
    }
}


class SelectList_bLeftAll_actionAdapter implements ActionListener {
    private SelectList adaptee;
    SelectList_bLeftAll_actionAdapter(SelectList adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bLeftAll_actionPerformed(e);
    }
}


class SelectList_bRightAll_actionAdapter implements ActionListener {
    private SelectList adaptee;
    SelectList_bRightAll_actionAdapter(SelectList adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bRightAll_actionPerformed(e);
    }
}


class SelectList_bLeftOne_actionAdapter implements ActionListener {
    private SelectList adaptee;
    SelectList_bLeftOne_actionAdapter(SelectList adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bLeftOne_actionPerformed(e);
    }
}


class SelectList_bRightOne_actionAdapter implements ActionListener {
    private SelectList adaptee;
    SelectList_bRightOne_actionAdapter(SelectList adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bRightOne_actionPerformed(e);
    }
}


