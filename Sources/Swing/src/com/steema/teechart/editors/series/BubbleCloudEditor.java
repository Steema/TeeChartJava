package com.steema.teechart.editors.series;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.steema.teechart.PositionUnits;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.editors.BaseSeriesEditor;
import com.steema.teechart.editors.BrushEditor;
import com.steema.teechart.editors.ButtonColor;
import com.steema.teechart.editors.ButtonPen;
import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.BubbleCloud;
import com.steema.teechart.styles.PaletteStyle;
import com.steema.teechart.styles.Series;

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
public class BubbleCloudEditor extends BaseSeriesEditor {

	private BubbleCloud bubble;
	
	private BorderLayout borderLayout1 = new BorderLayout();
	private JTabbedPane jTabbedPane1 = new JTabbedPane();
	private JPanel jPanel1 = new JPanel();
	private JPanel jPanel2 = new JPanel();
	
	JLabel lblSeparation;
	JSlider sbSeparation;
	JLabel lblRotation;
	JSlider sbRotation;
	JLabel lblSizeRatio;
	JSlider sbSizeRatio;
	JLabel lblTransparency;
	JSlider sbTransparency;
	JComboBox cbSepUnits;
	ButtonPen btnPen;
	JButton btnPattern;
	
	JRadioButton rbColor;
	JRadioButton rbRange;
	JRadioButton rbPalette;
	JSpinner spPalette;
	ButtonColor bColor;
	ButtonColor bFromColor;
	ButtonColor bMidColor;
	ButtonColor bToColor;
	JComboBox cbPalStyle;
	
	private static final long serialVersionUID = 1L;

	public BubbleCloudEditor() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

    private void jbInit() throws Exception {
    	this.setLayout(borderLayout1);
    	this.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    	jPanel1.setLayout(null);
    	jPanel2.setLayout(null);
    	jTabbedPane1.add(jPanel1, Language.getString("Format"));
    	jTabbedPane1.add(jPanel2, Language.getString("Palette"));
    	
    	// Format tab
    	JLabel lbl1 = new JLabel(Language.getString("Separation:"));
		lbl1.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl1.setBounds(10, 25, 81, 14);
		jPanel1.add(lbl1);
		
		lblSeparation = new JLabel("20");
		lblSeparation.setBounds(221, 25, 46, 14);
		jPanel1.add(lblSeparation);
		
		sbSeparation = new JSlider();
		sbSeparation.addChangeListener(new BubbleCloudEditor_sbSeparation_changeAdapter(this));
		sbSeparation.setOrientation(JScrollBar.HORIZONTAL);
		sbSeparation.setBounds(101, 25, 110, 14);
		sbSeparation.setPaintTicks(true);
		sbSeparation.setMinorTickSpacing(50);
		jPanel1.add(sbSeparation);
		
		JLabel lbl2 = new JLabel(Language.getString("Rotation:"));
		lbl2.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl2.setBounds(10, 50, 81, 14);
		jPanel1.add(lbl2);
		
		sbRotation = new JSlider();
		sbRotation.setMaximum(360);
		sbRotation.addChangeListener(new BubbleCloudEditor_sbRotation_changeAdapter(this));
		sbRotation.setOrientation(JScrollBar.HORIZONTAL);
		sbRotation.setBounds(101, 50, 110, 14);
		sbRotation.setPaintTicks(true);
		sbRotation.setMinorTickSpacing(40);
		jPanel1.add(sbRotation);
		
		lblRotation = new JLabel("345");
		lblRotation.setBounds(221, 50, 46, 14);
		jPanel1.add(lblRotation);
		
		JLabel lbl3 = new JLabel(Language.getString("Size Ratio:"));
		lbl3.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl3.setBounds(10, 75, 81, 14);
		jPanel1.add(lbl3);
		
		sbSizeRatio = new JSlider();
		sbSizeRatio.setMaximum(300);
		sbSizeRatio.addChangeListener(new BubbleCloudEditor_sbSizeRatio_changeAdapter(this));
		sbSizeRatio.setOrientation(JScrollBar.HORIZONTAL);
		sbSizeRatio.setBounds(101, 75, 110, 14);
		sbSizeRatio.setPaintTicks(true);
		sbSizeRatio.setMinorTickSpacing(40);
		jPanel1.add(sbSizeRatio);
		
		lblSizeRatio = new JLabel("1.63");
		lblSizeRatio.setBounds(221, 75, 46, 14);
		jPanel1.add(lblSizeRatio);
		
		JLabel lbl4 = new JLabel(Language.getString("Transparency:"));
		lbl4.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl4.setBounds(10, 100, 81, 14);
		jPanel1.add(lbl4);
		
		sbTransparency = new JSlider();
		sbTransparency.addChangeListener(new BubbleCloudEditor_sbTransparency_changeAdapter(this));
		sbTransparency.setOrientation(JScrollBar.HORIZONTAL);
		sbTransparency.setBounds(101, 100, 110, 14);
		sbTransparency.setPaintTicks(true);
		sbTransparency.setMinorTickSpacing(20);
		jPanel1.add(sbTransparency);
		
		lblTransparency = new JLabel("0");
		lblTransparency.setBounds(221, 100, 46, 14);
		jPanel1.add(lblTransparency);
		
		JLabel lblSepUnits = new JLabel(Language.getString("Separation Units:"));
		lblSepUnits.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSepUnits.setBounds(10, 135, 99, 14);
		jPanel1.add(lblSepUnits);
		
		cbSepUnits = new JComboBox();
		cbSepUnits.addItem(Language.getString("Percent"));
		cbSepUnits.addItem(Language.getString("Pixels"));
		cbSepUnits.setBounds(121, 132, 90, 20);
		cbSepUnits.addActionListener(new BubbleCloudEditor_cbSepUnits_actionAdapter(this));
		jPanel1.add(cbSepUnits);
		
		btnPen = new ButtonPen(Language.getString("Border"));
		btnPen.setMnemonic(Language.getString("B").charAt(0));
		btnPen.setBounds(277, 21, 89, 23);
		jPanel1.add(btnPen);
		
		btnPattern = new JButton(Language.getString("Pattern..."));
		btnPattern.addActionListener(new BubbleCloudEditor_btnPattern_actionAdapter(this));
		btnPattern.setBounds(277, 66, 89, 23);
		jPanel1.add(btnPattern);
		
		// Palette tab
		JPanel pColorMode = new JPanel();
		pColorMode.setBorder(new TitledBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)), Language
				.getString("Color Mode:"), TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		pColorMode.setBounds(17, 16, 240, 120);
		pColorMode.setLayout(null);
		
		rbColor = new JRadioButton(Language.getString("Series:"));
		rbColor.addActionListener(new BubbleCloudEditor_rbColor_actionAdapter(this));
		rbColor.setBounds(8, 16, 80, 16);
		pColorMode.add(rbColor);
		
		rbRange = new JRadioButton(Language.getString("Range:"));
		rbRange.addActionListener(new BubbleCloudEditor_rbRange_actionAdapter(this));
		rbRange.setBounds(8, 42, 80, 16);
		pColorMode.add(rbRange);
		
		rbPalette = new JRadioButton(Language.getString("Palette:"));
		rbPalette.addActionListener(new BubbleCloudEditor_rbPalette_actionAdapter(this));
		rbPalette.setBounds(8, 69, 80, 16);
		pColorMode.add(rbPalette);
		
		bColor = new ButtonColor();
		bColor.addActionListener(new BubbleCloudEditor_bColor_actionAdapter(this));
		bColor.setBounds(96, 11, 75, 23);
		pColorMode.add(bColor);
		
		bFromColor = new ButtonColor();
		bFromColor.addActionListener(new BubbleCloudEditor_bFromColor_actionAdapter(this));
		bFromColor.setBounds(96, 40, 40, 23);
		pColorMode.add(bFromColor);
		
		bMidColor = new ButtonColor();
		bMidColor.addActionListener(new BubbleCloudEditor_bMidColor_actionAdapter(this));
		bMidColor.setBounds(144, 40, 40, 23);
		pColorMode.add(bMidColor);
		
		bToColor = new ButtonColor();
		bToColor.addActionListener(new BubbleCloudEditor_bToColor_actionAdapter(this));
		bToColor.setBounds(192, 40, 40, 23);
		pColorMode.add(bToColor);
		
		JLabel lblNewLabel = new JLabel(Language.getString("Steps:"));
		lblNewLabel.setBounds(96, 70, 37, 13);
		pColorMode.add(lblNewLabel);
		
		JLabel lblStyle = new JLabel(Language.getString("Style:"));
		lblStyle.setBounds(96, 93, 33, 13);
		pColorMode.add(lblStyle);
		
		cbPalStyle = new JComboBox();
		cbPalStyle.addItem(Language.getString("Pale"));
		cbPalStyle.addItem(Language.getString("Strong"));
		cbPalStyle.addItem(Language.getString("Gray Scale"));
		cbPalStyle.addItem(Language.getString("Inverted Gray"));
		cbPalStyle.addItem(Language.getString("Rainbow"));
		cbPalStyle.setBounds(132, 91, 98, 18);
		cbPalStyle.addActionListener(new BubbleCloudEditor_cbPalStyle_actionAdapter(this));
		pColorMode.add(cbPalStyle);
		
		spPalette = new JSpinner();
		spPalette.addChangeListener(new BubbleCloudEditor_spPalette_changeAdapter(this));
		spPalette.setBounds(131, 68, 53, 17);
		pColorMode.add(spPalette);
		
		jPanel2.add(pColorMode);
    }
    
	public void sbSeparation_stateChanged(ChangeEvent e) {
		bubble.setSeparation(sbSeparation.getValue());
		lblSeparation.setText(String.valueOf(sbSeparation.getValue()));
	}
    
	public void sbRotation_stateChanged(ChangeEvent e) {
		bubble.setRotation(sbRotation.getValue());
		lblRotation.setText(String.valueOf(sbRotation.getValue()));
	}
    
	public void sbSizeRatio_stateChanged(ChangeEvent e) {
		bubble.setSizeRatio((double) (sbSizeRatio.getValue()) / 100);
		lblSizeRatio.setText(String.valueOf((double) (sbSizeRatio.getValue()) / 100));
	}
    
	public void sbTransparency_stateChanged(ChangeEvent e) {
		bubble.getBrush().setTransparency(sbTransparency.getValue());
		lblTransparency.setText(String.valueOf(sbTransparency.getValue()));
		bubble.invalidate();
	}
    
	public void cbSepUnits_actionPerformed(ActionEvent e) {
		if (bubble != null) {
			boolean tmp = (cbSepUnits.getSelectedIndex() == 0);
			bubble.setSeparationUnits(tmp ? PositionUnits.PERCENT
					: PositionUnits.PIXELS);
		}
	}

	public void btnPattern_actionPerformed(ActionEvent e) {
		BrushEditor.edit(this, bubble.getBrush());
	}

	public void rbColor_actionPerformed(ActionEvent e) {
		bubble.setColorEach(true);
		bubble.setColorEach(false);
		bubble.setUseColorRange(false);
		bubble.setUsePalette(false);

		rbColor.setSelected(true);
		rbRange.setSelected(false);
		rbPalette.setSelected(false);
	}
    
	public void rbRange_actionPerformed(ActionEvent e) {
		bubble.setUseColorRange(true);
		bubble.setUsePalette(false);

		rbColor.setSelected(false);
		rbRange.setSelected(true);
		rbPalette.setSelected(false);
	}

	public void rbPalette_actionPerformed(ActionEvent e) {
		bubble.setUseColorRange(false);
		bubble.setUsePalette(true);

		rbColor.setSelected(false);
		rbRange.setSelected(false);
		rbPalette.setSelected(true);
	}
    
	public void bColor_actionPerformed(ActionEvent e) {
		Color newColor = bColor.getColor();
		if (newColor != bubble.getColor()) {
			rbColor.doClick();
			bubble.setColor(newColor);
		}
	}

	public void bFromColor_actionPerformed(ActionEvent e) {
		Color newColor = bFromColor.getColor();
		if (newColor != bubble.getStartColor()) {
			rbRange.doClick();
			bubble.setStartColor(newColor);
		}
	}

	public void bMidColor_actionPerformed(ActionEvent e) {
		Color newColor = bMidColor.getColor();
		if (newColor != bubble.getMidColor()) {
			rbRange.doClick();
			bubble.setMidColor(newColor);
		}
	}

	public void bToColor_actionPerformed(ActionEvent e) {
		Color newColor = bToColor.getColor();
		if (newColor != bubble.getEndColor()) {
			rbRange.doClick();
			bubble.setEndColor(newColor);
		}
	}
	
	public void cbPalStyle_actionPerformed(ActionEvent e) {
		if (bubble != null) {
			rbPalette.doClick();
			if (cbPalStyle.getSelectedIndex() == 0)
				bubble.setPaletteStyle(PaletteStyle.PALE);
			else if (cbPalStyle.getSelectedIndex() == 1)
				bubble.setPaletteStyle(PaletteStyle.STRONG);
			else if (cbPalStyle.getSelectedIndex() == 2)
				bubble.setPaletteStyle(PaletteStyle.GRAYSCALE);
			else if (cbPalStyle.getSelectedIndex() == 3)
				bubble.setPaletteStyle(PaletteStyle.INVERTED_GRAYSCALE);
			else
				bubble.setPaletteStyle(PaletteStyle.RAINBOW);
		}
	}
	
	public void spPalette_stateChanged(ChangeEvent e) {
		if (bubble != null) {
			rbPalette.doClick();
			bubble.setPaletteSteps((Integer) spPalette.getValue());
		}
	}
    
	@Override
	public void setSeries(Series series, JTabbedPane tabs) {
		bubble = (BubbleCloud) series;
		sbSeparation.setValue(bubble.getSeparation());
		sbRotation.setValue(bubble.getRotation());
		sbSizeRatio.setValue((int)(bubble.getSizeRatio() * 100));
		sbTransparency.setValue(bubble.getBrush().getTransparency());
		btnPen.setPen(bubble.getPen());
		cbSepUnits.setSelectedIndex(bubble.getSeparationUnits().getValue());
		
		rbColor.setSelected((!bubble.getUseColorRange()) && (!bubble.getUsePalette()));
		rbRange.setSelected(bubble.getUseColorRange());
		rbPalette.setSelected(bubble.getUsePalette());
		bColor.setColor(bubble.getColor());
		bFromColor.setColor(bubble.getStartColor());
		bMidColor.setColor(bubble.getMidColor());
		bToColor.setColor(bubble.getEndColor());
		spPalette.setValue(bubble.getPaletteSteps());
		
		if (bubble.getPaletteStyle() == PaletteStyle.PALE)
			cbPalStyle.setSelectedIndex(0);
		else if (bubble.getPaletteStyle() == PaletteStyle.STRONG)
			cbPalStyle.setSelectedIndex(1);
		else if (bubble.getPaletteStyle() == PaletteStyle.GRAYSCALE)
			cbPalStyle.setSelectedIndex(2);
		else if (bubble.getPaletteStyle() == PaletteStyle.INVERTED_GRAYSCALE)
			cbPalStyle.setSelectedIndex(3);
		else
			cbPalStyle.setSelectedIndex(4);
	}
	
}

final class BubbleCloudEditor_sbSeparation_changeAdapter implements ChangeListener {
	private BubbleCloudEditor adaptee;

	BubbleCloudEditor_sbSeparation_changeAdapter(BubbleCloudEditor adaptee) {
		this.adaptee = adaptee;
	}

	public void stateChanged(ChangeEvent e) {
		adaptee.sbSeparation_stateChanged(e);
	}
}

final class BubbleCloudEditor_sbRotation_changeAdapter implements ChangeListener {
	private BubbleCloudEditor adaptee;

	BubbleCloudEditor_sbRotation_changeAdapter(BubbleCloudEditor adaptee) {
		this.adaptee = adaptee;
	}

	public void stateChanged(ChangeEvent e) {
		adaptee.sbRotation_stateChanged(e);
	}
}

final class BubbleCloudEditor_sbSizeRatio_changeAdapter implements ChangeListener {
	private BubbleCloudEditor adaptee;

	BubbleCloudEditor_sbSizeRatio_changeAdapter(BubbleCloudEditor adaptee) {
		this.adaptee = adaptee;
	}

	public void stateChanged(ChangeEvent e) {
		adaptee.sbSizeRatio_stateChanged(e);
	}
}

final class BubbleCloudEditor_sbTransparency_changeAdapter implements ChangeListener {
	private BubbleCloudEditor adaptee;

	BubbleCloudEditor_sbTransparency_changeAdapter(BubbleCloudEditor adaptee) {
		this.adaptee = adaptee;
	}

	public void stateChanged(ChangeEvent e) {
		adaptee.sbTransparency_stateChanged(e);
	}
}

final class BubbleCloudEditor_cbSepUnits_actionAdapter implements ActionListener {
    private BubbleCloudEditor adaptee;

	BubbleCloudEditor_cbSepUnits_actionAdapter(BubbleCloudEditor adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.cbSepUnits_actionPerformed(e);
	}
}

final class BubbleCloudEditor_btnPattern_actionAdapter implements ActionListener {
	private BubbleCloudEditor adaptee;

	BubbleCloudEditor_btnPattern_actionAdapter(BubbleCloudEditor adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.btnPattern_actionPerformed(e);
	}
}

final class BubbleCloudEditor_rbColor_actionAdapter implements ActionListener {
	private BubbleCloudEditor adaptee;

	BubbleCloudEditor_rbColor_actionAdapter(BubbleCloudEditor adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.rbColor_actionPerformed(e);
	}
}

final class BubbleCloudEditor_rbRange_actionAdapter implements ActionListener {
	private BubbleCloudEditor adaptee;

	BubbleCloudEditor_rbRange_actionAdapter(BubbleCloudEditor adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.rbRange_actionPerformed(e);
	}
}

final class BubbleCloudEditor_rbPalette_actionAdapter implements ActionListener {
	private BubbleCloudEditor adaptee;

	BubbleCloudEditor_rbPalette_actionAdapter(BubbleCloudEditor adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.rbPalette_actionPerformed(e);
	}
}

final class BubbleCloudEditor_bColor_actionAdapter implements ActionListener {
	private BubbleCloudEditor adaptee;

	BubbleCloudEditor_bColor_actionAdapter(BubbleCloudEditor adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.bColor_actionPerformed(e);
	}
}

final class BubbleCloudEditor_bFromColor_actionAdapter implements ActionListener {
	private BubbleCloudEditor adaptee;

	BubbleCloudEditor_bFromColor_actionAdapter(BubbleCloudEditor adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.bFromColor_actionPerformed(e);
	}
}

final class BubbleCloudEditor_bMidColor_actionAdapter implements ActionListener {
	private BubbleCloudEditor adaptee;

	BubbleCloudEditor_bMidColor_actionAdapter(BubbleCloudEditor adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.bMidColor_actionPerformed(e);
	}
}

final class BubbleCloudEditor_bToColor_actionAdapter implements ActionListener {
	private BubbleCloudEditor adaptee;

	BubbleCloudEditor_bToColor_actionAdapter(BubbleCloudEditor adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.bToColor_actionPerformed(e);
	}
}

final class BubbleCloudEditor_cbPalStyle_actionAdapter implements ActionListener {
    private BubbleCloudEditor adaptee;

    BubbleCloudEditor_cbPalStyle_actionAdapter(BubbleCloudEditor adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.cbPalStyle_actionPerformed(e);
	}
}

final class BubbleCloudEditor_spPalette_changeAdapter implements ChangeListener {
	private BubbleCloudEditor adaptee;

	BubbleCloudEditor_spPalette_changeAdapter(BubbleCloudEditor adaptee) {
		this.adaptee = adaptee;
	}

	public void stateChanged(ChangeEvent e) {
		adaptee.spPalette_stateChanged(e);
	}
}
