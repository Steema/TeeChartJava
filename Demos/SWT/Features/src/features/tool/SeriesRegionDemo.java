/*
 * SeriesRegionDemo.java
 *
 * <p>Copyright: (c) 2005-2018 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */

package features.tool;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Widget;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.legend.LegendStyle;
import com.steema.teechart.misc.Utils;
import com.steema.teechart.styles.Line;
import com.steema.teechart.themes.ThemesList;
import com.steema.teechart.tools.SeriesRegion;

import features.ChartSample;

/**
 * @author yeray
 * 
 */
public class SeriesRegionDemo extends ChartSample implements ModifyListener,
		SelectionListener {

	private SeriesRegion seriesRegionTool1;

	public SeriesRegionDemo(Composite c) {
		super(c);
	}

	protected void createContent() {
		super.createContent();

		addLabel(SWT.LEFT, "Lower:");
		lowerSpinner = addSpinner(SWT.BORDER, 0, 19, 1, this);

		addLabel(SWT.LEFT, "Upper:");
		upperSpinner = addSpinner(SWT.BORDER, 0, 19, 1, this);

		useOriginCheckBox = addCheckButton("Use origin", "", this);

		originSpinner = addSpinner(SWT.BORDER, 0, 5000, 10, this);

		drawBehindCheckBox = addCheckButton("Draw behind", "", this);

		view3DCheckBox = addCheckButton("View 3D", "", this);
	}

	protected void initContent() {
		super.initContent();

		lowerSpinner.setSelection(2);
		upperSpinner.setSelection(15);
		useOriginCheckBox.setSelection(true);
		originSpinner.setSelection(500);
		drawBehindCheckBox.setSelection(true);
		view3DCheckBox.setSelection(false);
	}

	protected void initChart() {
		super.initChart();

		chart1.getAspect().setView3D(false);
		chart1.getHeader().setVisible(true);
		chart1.setText("SeriesRegion Tool Example");
		chart1.getLegend().setLegendStyle(LegendStyle.SERIES);

		line1 = new Line(chart1.getChart());
		line1.fillSampleValues(20);
		line1.getLinePen().setWidth(2);
		line1.setColor(Color.GOLD);
		line1.getLinePen().setColor(Utils.darkenColor(Color.GOLD, 60));

		seriesRegionTool1 = new SeriesRegion(chart1.getChart());
		seriesRegionTool1.setSeries(line1);

		seriesRegionTool1.setAutoBound(false);
		seriesRegionTool1.setUseOrigin(true);
		seriesRegionTool1.setOrigin(500);
		seriesRegionTool1.setLowerBound(line1.getXValues().getValue(2));
		seriesRegionTool1.setUpperBound(line1.getXValues().getValue(15));

		seriesRegionTool1.getGradient().setVisible(true);
		seriesRegionTool1.getGradient().setTransparency(40);

		seriesRegionTool1.getPen().setVisible(false);

		ThemesList.applyTheme(chart1.getChart(), 1);
	}

	private Line line1;
	private Spinner lowerSpinner, upperSpinner, originSpinner;
	private Button useOriginCheckBox, drawBehindCheckBox, view3DCheckBox;

	public void modifyText(ModifyEvent me) {
		Widget source = me.widget;
		if (source == lowerSpinner) {
			seriesRegionTool1.setLowerBound(line1.getXValues().getValue(lowerSpinner.getSelection()));
		} else if (source == upperSpinner) {
			seriesRegionTool1.setUpperBound(line1.getXValues().getValue(upperSpinner.getSelection()));
		} else if (source == originSpinner) {
			seriesRegionTool1.setOrigin(originSpinner.getSelection());
		}

		chart1.refreshControl();
	}

	public void widgetSelected(SelectionEvent se) {
		Widget source = se.widget;

		if (source == useOriginCheckBox) {
			seriesRegionTool1.setUseOrigin(useOriginCheckBox.getSelection());
			originSpinner.setEnabled(useOriginCheckBox.getSelection());
		} else if (source == drawBehindCheckBox)
			seriesRegionTool1.setDrawBehindSeries(drawBehindCheckBox.getSelection());
		else if (source == view3DCheckBox)
			chart1.getAspect().setView3D(view3DCheckBox.getSelection());

		chart1.refreshControl();
	}

	public void widgetDefaultSelected(SelectionEvent se) {
	}
}
