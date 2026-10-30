package com.steema.teechart.editors;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Rectangle;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.Frame;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSlider;

import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.drawing.DashCap;
import com.steema.teechart.drawing.LineCap;

import com.steema.teechart.languages.Language;

/**
 *
 * <p>Title: ChartPenEditor</p>
 *
 * <p>Description: Editor dialog for ChartPen objects.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ChartPenEditor extends JDialog {


    public int returnStatus = RET_CANCEL;

    /** A return status code - returned if Cancel button has been pressed */
    public static final int RET_CANCEL = 0;

    /** A return status code - returned if OK button has been pressed */
    public static final int RET_OK = 1;

    private ChartPen pen, backup;
    private boolean changed = false;
    private boolean initDialog = true;

    public ChartPenEditor() {
        super();

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        cbStyle.addItem(Language.getString("Solid"));
        cbStyle.addItem(Language.getString("Dash"));
        cbStyle.addItem(Language.getString("Dot"));
        cbStyle.addItem(Language.getString("Dash_Dot"));
        cbStyle.addItem(Language.getString("Dash_Dot_Dot"));

        cbJoin.addItem(Language.getString("Bevel"));
        cbJoin.addItem(Language.getString("Miter"));
        cbJoin.addItem(Language.getString("Round"));

        cbEnd.addItem(Language.getString("Flat"));
        cbEnd.addItem(Language.getString("Round"));
        cbEnd.addItem(Language.getString("Square"));

        setSize(256, 198);
    }

    public ChartPenEditor(ChartPen pen) {
        this(null, pen);
    }

    public ChartPenEditor(Frame parent, ChartPen pen) {
        this();
        refreshControls(pen);
        setLocationRelativeTo(parent);
    }

    public void refreshControls(ChartPen pen) {
        this.pen = pen;

        initDialog = true;

        backup = new ChartPen(pen);

        cbStyle.setSelectedIndex(pen.getStyle().getValue());

        cbVisible.setSelected(pen.getVisible());

        cbJoin.setSelectedIndex(pen.getDashCap().getValue());

        cbEnd.setSelectedIndex(pen.getEndCap().getValue());

        final SpinnerNumberModel sWidthModel = (SpinnerNumberModel) sWidth.
                                               getModel();
        sWidthModel.setValue(new Integer(pen.width));
        sWidthModel.setMinimum(new Integer(0));

        slTransp.setValue(pen.getTransparency());

        buttonColor1.setColor(pen.getColor());

        initDialog = false;
    }

    /**
     * Shows the ChartPen editor dialog for the specified Pen.
     *
     * @param owner Frame
     * @param pen ChartPen to edit
     * @return boolean True if Pen properties have been changed.
     */
    static public boolean showDialog(Frame owner, ChartPen pen) {
        ChartPenEditor dialog = new ChartPenEditor(owner, pen);


        dialog.setModal(true);
        dialog.setVisible(true);
        return dialog.returnStatus == RET_OK;
    }

    private Container createButtonPane() {

        JPanel buttonPane = new JPanel();
        {
            JButton okButton = new JButton(Language.getString("OK"));
            okButton.addActionListener(new
                                       ChartPenEditor_okButton_actionAdapter(this));
            getRootPane().setDefaultButton(okButton);

            JButton cancelButton = new JButton(Language.getString("Cancel"));
            cancelButton.addActionListener(new
                                           ChartPenEditor_cancelButton_actionAdapter(this));

            buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
            buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            buttonPane.add(Box.createHorizontalGlue());
            buttonPane.add(okButton);
            buttonPane.add(Box.createHorizontalStrut(10));
            buttonPane.add(cancelButton);
        }
        return buttonPane;
    }

    private void jbInit() throws Exception {
        cbVisible.setMnemonic(Language.getString("V").charAt(0));
        cbVisible.setText(Language.getString("Visible"));
        cbVisible.setBounds(new Rectangle(6, 4, 69, 23));
        cbVisible.addActionListener(new ChartPenEditor_cbVisible_actionAdapter(this));
        //jLabel1.setNextFocusableComponent(sWidth);
        jLabel1.setToolTipText("");
        jLabel1.setDisplayedMnemonic(Language.getString("W").charAt(0));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(sWidth);
        jLabel1.setText(Language.getString("Width"));
        jLabel1.setBounds(new Rectangle(90, 5, 41, 15));
        sWidth.setBounds(new Rectangle(134, 3, 64, 22));
        sWidth.setToolTipText("");
        buttonColor1.setBounds(new Rectangle(6, 33, 73, 25));
        buttonColor1.addActionListener(new
                                       ChartPenEditor_buttonColor1_actionAdapter(this));
        cbStyle.setBounds(new Rectangle(134, 30, 110, 23));
        cbStyle.addActionListener(new
                                  ChartPenEditor_jComboBox1_actionAdapter(this));
        //jLabel2.setNextFocusableComponent(null);
        jLabel2.setDisplayedMnemonic(Language.getString("E").charAt(0));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setLabelFor(cbEnd);
        jLabel2.setText(Language.getString("End"));
        jLabel2.setBounds(new Rectangle(6, 69, 29, 16));
        cbJoin.setBounds(new Rectangle(159, 64, 85, 23));
        cbJoin.addActionListener(new ChartPenEditor_cbJoin_actionAdapter(this));
        jLabel4.setDisplayedMnemonic(Language.getString("J").charAt(0));
        jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel4.setLabelFor(cbJoin);
        jLabel4.setText(Language.getString("Join"));
        jLabel4.setBounds(new Rectangle(118, 66, 38, 16));
        jLabel5.setDisplayedMnemonic(Language.getString("S").charAt(0));
        jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel5.setLabelFor(cbStyle);
        jLabel5.setText(Language.getString("Style"));
        jLabel5.setBounds(new Rectangle(93, 33, 38, 16));
        cbEnd.setBounds(new Rectangle(43, 66, 65, 23));
        cbEnd.addActionListener(new ChartPenEditor_cbEnd_actionAdapter(this));
        sWidth.addChangeListener(new ChartPenEditor_sWidth_changeAdapter(this));
        jLabel6.setDisplayedMnemonic(Language.getString("T").charAt(0));
        jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel6.setLabelFor(slTransp);
        jLabel6.setText(Language.getString("Transparency"));
        jLabel6.setBounds(new Rectangle(8, 103, 101, 16));
        slTransp.setBounds(new Rectangle(122, 100, 110, 24));
        slTransp.addChangeListener(new ChartPenEditor_slTransp_changeAdapter(this));

        penPanel.setLayout(null);
        penPanel.add(cbVisible);
        penPanel.add(sWidth, null);
        penPanel.add(jLabel1);
        penPanel.add(okButton);
        penPanel.add(buttonColor1);
        penPanel.add(jLabel5);
        penPanel.add(cbStyle);
        penPanel.add(cbJoin);
        penPanel.add(jLabel4);
        penPanel.add(jLabel2);
        penPanel.add(cbEnd);
        penPanel.add(jLabel6);
        penPanel.add(slTransp);

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(createButtonPane(), BorderLayout.SOUTH);
        this.getContentPane().add(penPanel, BorderLayout.CENTER);
        this.setResizable(false);

        pack();
    }

    public JCheckBox cbVisible = new JCheckBox();

    JButton okButton = new JButton();
    JLabel jLabel1 = new JLabel();
    JSpinner sWidth = new JSpinner();
    JButton cancelButton = new JButton();
    ButtonColor buttonColor1 = new ButtonColor();
    JComboBox cbStyle = new JComboBox();
    JLabel jLabel2 = new JLabel();
    JComboBox cbJoin = new JComboBox();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel5 = new JLabel();
    JLabel jLabel6 = new JLabel();
    JComboBox cbEnd = new JComboBox();
    JSlider slTransp = new JSlider();

    public JPanel penPanel = new JPanel();

    public void sWidth_stateChanged(ChangeEvent e) {
        if (!initDialog) {
            pen.setWidth(((Integer) sWidth.getValue()).intValue());
            changed = true;
        }
    }

    public void okButton_actionPerformed(ActionEvent e) {
        doClose(RET_OK);
    }

    public void slTransp_stateChanged(ChangeEvent e) {
        pen.setTransparency(slTransp.getValue());
    }

    public void cbVisible_actionPerformed(ActionEvent e) {
        if (!initDialog) {
            pen.setVisible(cbVisible.isSelected());
            changed = true;
        }
    }

    public void jComboBox1_actionPerformed(ActionEvent e) {
        if (!initDialog) {
            pen.setStyle(DashStyle.fromValue(cbStyle.getSelectedIndex()));
            changed = true;
        }
    }

    public void cancelButton_actionPerformed(ActionEvent e) {
        if (changed) {
            pen.assign(backup);
            pen.invalidate(); // TODO: probably better to move this to the pen class
        }

        doClose(RET_CANCEL);
    }

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    public void buttonColor1_actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonColor1) {
            pen.setColor(buttonColor1.getColor());
            changed = true;
        }
    }

    public void cbJoin_actionPerformed(ActionEvent e) {
        if (!initDialog) {
            pen.setDashCap(DashCap.fromValue(cbJoin.getSelectedIndex()));
            changed = true;
        }
    }

    public void cbEnd_actionPerformed(ActionEvent e) {
        if (!initDialog) {
            pen.setEndCap(LineCap.fromValue(cbEnd.getSelectedIndex()));
            changed = true;
        }
    }
}


final class ChartPenEditor_slTransp_changeAdapter implements ChangeListener {
    private ChartPenEditor adaptee;
    ChartPenEditor_slTransp_changeAdapter(ChartPenEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.slTransp_stateChanged(e);
    }
}


final class ChartPenEditor_cbEnd_actionAdapter implements ActionListener {
    private ChartPenEditor adaptee;
    ChartPenEditor_cbEnd_actionAdapter(ChartPenEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbEnd_actionPerformed(e);
    }
}


final class ChartPenEditor_cbJoin_actionAdapter implements ActionListener {
    private ChartPenEditor adaptee;
    ChartPenEditor_cbJoin_actionAdapter(ChartPenEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbJoin_actionPerformed(e);
    }
}


final class ChartPenEditor_buttonColor1_actionAdapter implements ActionListener {
    private ChartPenEditor adaptee;
    ChartPenEditor_buttonColor1_actionAdapter(ChartPenEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.buttonColor1_actionPerformed(e);
    }
}


final class ChartPenEditor_cancelButton_actionAdapter implements ActionListener {
    private ChartPenEditor adaptee;
    ChartPenEditor_cancelButton_actionAdapter(ChartPenEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cancelButton_actionPerformed(e);
    }
}


final class ChartPenEditor_jComboBox1_actionAdapter implements ActionListener {
    private ChartPenEditor adaptee;
    ChartPenEditor_jComboBox1_actionAdapter(ChartPenEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jComboBox1_actionPerformed(e);
    }
}


final class ChartPenEditor_cbVisible_actionAdapter implements ActionListener {
    private ChartPenEditor adaptee;
    ChartPenEditor_cbVisible_actionAdapter(ChartPenEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbVisible_actionPerformed(e);
    }
}


final class ChartPenEditor_okButton_actionAdapter implements ActionListener {
    private ChartPenEditor adaptee;
    ChartPenEditor_okButton_actionAdapter(ChartPenEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.okButton_actionPerformed(e);
    }
}


final class ChartPenEditor_sWidth_changeAdapter implements ChangeListener {
    private ChartPenEditor adaptee;
    ChartPenEditor_sWidth_changeAdapter(ChartPenEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.sWidth_stateChanged(e);
    }
}

