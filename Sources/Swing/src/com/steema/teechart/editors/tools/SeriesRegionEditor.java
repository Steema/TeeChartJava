package com.steema.teechart.editors.tools;

import com.steema.teechart.editors.BrushEditor;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.editors.GradientEditor;
import com.steema.teechart.languages.Language;
import com.steema.teechart.misc.Utils;
import com.steema.teechart.tools.SeriesRegion;
import com.steema.teechart.tools.Tool;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * <p>Title: SeriesRegionEditor class</p>
 *
 * <p>Description: SeriesRegion Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class SeriesRegionEditor extends ToolSeriesEditor {

    SeriesRegion seriesRegion;
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel jPanelOptions = new JPanel();
    JPanel jPanelFormat = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    Border borderOrigin = new TitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140)), Language.getString("Origin"));
    Border borderBounds = new TitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140)), Language.getString("Bounds"));
    JPanel jPanelOrigin = new JPanel();
    JPanel jPanelBounds = new JPanel();
    JCheckBox cbUseOrigin = new JCheckBox();
    JCheckBox cbAutoBounds = new JCheckBox();
    JLabel jLabelOrigin = new JLabel();
    JLabel jLabelLower = new JLabel();
    JLabel jLabelUpper = new JLabel();
    JTextField textFieldOrigin = new JTextField();
    JTextField textLower = new JTextField();
    JTextField textUpper = new JTextField();

    JCheckBox cbDrawBehind = new JCheckBox();
    ButtonPen bPen = new ButtonPen();
    JButton bBrush = new JButton();
    JButton bGradient = new JButton();
    JLabel jLabelTransp = new JLabel();
    JSpinner spTransp = new JSpinner();

    public SeriesRegionEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTool(Tool tool) {
        setting = true;
        seriesRegion = (SeriesRegion) tool;

        super.setTool(seriesRegion);

        cbUseOrigin.setSelected(seriesRegion.getUseOrigin());
        textFieldOrigin.setText(String.valueOf(seriesRegion.getOrigin()));

        cbAutoBounds.setSelected(seriesRegion.getAutoBound());
        textLower.setText(String.valueOf(seriesRegion.getLowerBound()));
        textUpper.setText(String.valueOf(seriesRegion.getUpperBound()));

        cbDrawBehind.setSelected(seriesRegion.getDrawBehindSeries());
        bPen.setPen(seriesRegion.getPen());

        setting = false;
    }

    private void jbInit() throws Exception {
        this.setLayout(null);

        jTabbedPane1.setBounds(new Rectangle(3, 54, 224, 164));
        jTabbedPane1.add(jPanelOptions, Language.getString("Options"));
        jTabbedPane1.add(jPanelFormat, Language.getString("Format"));

        jPanelOptions.setLayout(null);
        jPanelFormat.setLayout(null);

        cbUseOrigin.setMnemonic(Language.getString("U").charAt(0));
        cbUseOrigin.setText(Language.getString("Use_Origin"));
        cbUseOrigin.setBounds(new Rectangle(6, 19, 95, 17));
        cbUseOrigin.addActionListener(new SeriesRegionEditor_cbUseOrigin_actionAdapter(this));

        jLabelOrigin.setDisplayedMnemonic(Language.getString("O").charAt(0));
        jLabelOrigin.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelOrigin.setText(Language.getString("Origin"));
        jLabelOrigin.setBounds(new Rectangle(104, 21, 44, 13));

        textFieldOrigin.setBounds(new Rectangle(155, 18, 45, 20));
        textFieldOrigin.addActionListener(new SeriesRegionEditor_textFieldOrigin_actionAdapter(this));

        jPanelOrigin.setBorder(borderOrigin);
        jPanelOrigin.setBounds(new Rectangle(4, 6, 206, 46));
        jPanelOrigin.setLayout(null);
        jPanelOrigin.add(cbUseOrigin);
        jPanelOrigin.add(jLabelOrigin);
        jPanelOrigin.add(textFieldOrigin);

        jPanelBounds.setBorder(borderBounds);
        jPanelBounds.setBounds(new Rectangle(4, 58, 206, 69));
        jPanelBounds.setLayout(null);
        jPanelBounds.add(cbAutoBounds);
        jPanelBounds.add(jLabelLower);
        jPanelBounds.add(textLower);
        jPanelBounds.add(jLabelUpper);
        jPanelBounds.add(textUpper);

        cbAutoBounds.setMnemonic(Language.getString("A").charAt(0));
        cbAutoBounds.setText(Language.getString("Automatic"));
        cbAutoBounds.setBounds(new Rectangle(6, 19, 95, 17));
        cbAutoBounds.addActionListener(new SeriesRegionEditor_cbAutoBounds_actionAdapter(this));

        jLabelLower.setDisplayedMnemonic(Language.getString("L").charAt(0));
        jLabelLower.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelLower.setText(Language.getString("Lower"));
        jLabelLower.setBounds(new Rectangle(104, 21, 44, 13));

        textLower.setBounds(new Rectangle(155, 18, 45, 20));
        textLower.addActionListener(new SeriesRegionEditor_textLower_actionAdapter(this));

        jLabelUpper.setDisplayedMnemonic(Language.getString("U").charAt(0));
        jLabelUpper.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelUpper.setText(Language.getString("Upper"));
        jLabelUpper.setBounds(new Rectangle(104, 46, 44, 13));

        textUpper.setBounds(new Rectangle(155, 43, 45, 20));
        textUpper.addActionListener(new SeriesRegionEditor_textUpper_actionAdapter(this));

        jPanelOptions.add(jPanelOrigin);
        jPanelOptions.add(jPanelBounds);

        cbDrawBehind.setMnemonic(Language.getString("D").charAt(0));
        cbDrawBehind.setText(Language.getString("_Draw_Behind"));
        cbDrawBehind.setBounds(new Rectangle(6, 6, 106, 17));
        cbDrawBehind.addActionListener(new SeriesRegionEditor_cbDrawBehind_actionAdapter(this));

        bPen.setBounds(new Rectangle(6, 30, 84, 23));
        bPen.setMnemonic(Language.getString("B").charAt(0));
        bPen.setText(Language.getString("Border"));

        bBrush.setBounds(new Rectangle(95, 30, 84, 23));
        bBrush.setText(Language.getString("Pattern"));
        bBrush.addActionListener(new SeriesRegionEditor_bBrush_actionAdapter(this));

        bGradient.setBounds(new Rectangle(6, 60, 84, 23));
        bGradient.setMnemonic(Language.getString("G").charAt(0));
        bGradient.setText(Language.getString("Gradient"));
        bGradient.addActionListener(new SeriesRegionEditor_bGradient_actionAdapter(this));

        jLabelTransp.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabelTransp.setLabelFor(spTransp);
        jLabelTransp.setText(Language.getString("Transparency"));
        jLabelTransp.setBounds(new Rectangle(0, 100, 90, 13));

        spTransp.setBounds(new Rectangle(100, 100, 50, 20));
        spTransp.addChangeListener(new SeriesRegionEditor_spTransp_changeAdapter(this));

        jPanelFormat.add(cbDrawBehind);
        jPanelFormat.add(bPen);
        jPanelFormat.add(bBrush);
        jPanelFormat.add(bGradient);
        jPanelFormat.add(jLabelTransp);
        jPanelFormat.add(spTransp);

        this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }

    public void cbUseOrigin_actionPerformed(ActionEvent e) {
        if (seriesRegion != null && !setting) {
            seriesRegion.setUseOrigin(cbUseOrigin.isSelected());
            seriesRegion.getSeries().getChart().invalidate();
        }
        textFieldOrigin.setEnabled(cbUseOrigin.isSelected());
    }

    public void textFieldOrigin_actionPerformed(ActionEvent e) {
        if (seriesRegion != null && !setting) {
            seriesRegion.setOrigin(Utils.stringToDouble(textFieldOrigin.getText(), 0.0));
            seriesRegion.getSeries().getChart().invalidate();
        }
    }

    public void cbAutoBounds_actionPerformed(ActionEvent e) {
        if (seriesRegion != null && !setting) {
            seriesRegion.setAutoBound(cbAutoBounds.isSelected());
            seriesRegion.getSeries().getChart().invalidate();
        }
        textLower.setEnabled(!cbAutoBounds.isSelected());
        textUpper.setEnabled(!cbAutoBounds.isSelected());
    }

    public void textLower_actionPerformed(ActionEvent e) {
        if (seriesRegion != null && !setting) {
            seriesRegion.setLowerBound(Utils.stringToDouble(textLower.getText(), 0.0));
            seriesRegion.getSeries().getChart().invalidate();
        }
    }

    public void textUpper_actionPerformed(ActionEvent e) {
        if (seriesRegion != null && !setting) {
            seriesRegion.setUpperBound(Utils.stringToDouble(textUpper.getText(), 0.0));
            seriesRegion.getSeries().getChart().invalidate();
        }
    }

    public void cbDrawBehind_actionPerformed(ActionEvent e) {
        if (seriesRegion != null && !setting) {
            seriesRegion.setDrawBehindSeries(cbDrawBehind.isSelected());
            seriesRegion.getSeries().getChart().invalidate();
        }
    }

    public void bBrush_actionPerformed(ActionEvent e) {
        if (seriesRegion != null && !setting) {
            BrushEditor.edit(this, seriesRegion.getBrush());
        }
    }

    public void bGradient_actionPerformed(ActionEvent e) {
        if (seriesRegion != null && !setting) {
            GradientEditor.edit(this, seriesRegion.getGradient());
        }
    }

    public void spTransp_stateChanged(ChangeEvent e) {
        if (seriesRegion != null && !setting) {
            seriesRegion.setTransparency(((Integer) spTransp.getValue()).intValue());
        }
    }
}

final class SeriesRegionEditor_cbUseOrigin_actionAdapter implements ActionListener {

    private SeriesRegionEditor adaptee;

    SeriesRegionEditor_cbUseOrigin_actionAdapter(SeriesRegionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbUseOrigin_actionPerformed(e);
    }
}

final class SeriesRegionEditor_textFieldOrigin_actionAdapter implements ActionListener {

    private SeriesRegionEditor adaptee;

    SeriesRegionEditor_textFieldOrigin_actionAdapter(SeriesRegionEditor adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.textFieldOrigin_actionPerformed(e);
    }
}

final class SeriesRegionEditor_cbAutoBounds_actionAdapter implements ActionListener {

    private SeriesRegionEditor adaptee;

    SeriesRegionEditor_cbAutoBounds_actionAdapter(SeriesRegionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbAutoBounds_actionPerformed(e);
    }
}

final class SeriesRegionEditor_textLower_actionAdapter implements ActionListener {

    private SeriesRegionEditor adaptee;

    SeriesRegionEditor_textLower_actionAdapter(SeriesRegionEditor adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.textLower_actionPerformed(e);
    }
}

final class SeriesRegionEditor_textUpper_actionAdapter implements ActionListener {

    private SeriesRegionEditor adaptee;

    SeriesRegionEditor_textUpper_actionAdapter(SeriesRegionEditor adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.textUpper_actionPerformed(e);
    }
}

final class SeriesRegionEditor_cbDrawBehind_actionAdapter implements ActionListener {

    private SeriesRegionEditor adaptee;

    SeriesRegionEditor_cbDrawBehind_actionAdapter(SeriesRegionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cbDrawBehind_actionPerformed(e);
    }
}

final class SeriesRegionEditor_bBrush_actionAdapter implements ActionListener {

    private SeriesRegionEditor adaptee;

    SeriesRegionEditor_bBrush_actionAdapter(SeriesRegionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bBrush_actionPerformed(e);
    }
}

final class SeriesRegionEditor_bGradient_actionAdapter implements ActionListener {

    private SeriesRegionEditor adaptee;

    SeriesRegionEditor_bGradient_actionAdapter(SeriesRegionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bGradient_actionPerformed(e);
    }
}

final class SeriesRegionEditor_spTransp_changeAdapter implements ChangeListener {

    private SeriesRegionEditor adaptee;

    SeriesRegionEditor_spTransp_changeAdapter(SeriesRegionEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.spTransp_stateChanged(e);
    }
}
