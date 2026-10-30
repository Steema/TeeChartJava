package features.style.beeswarm;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.functions.KDESides;
import com.steema.teechart.styles.BeeSwarm;

import features.ChartSample;
import features.utils.EnumStrings;

public class BeeSwarmDemo extends ChartSample implements ModifyListener, SelectionListener {

	private BeeSwarm series;
	private BeeSwarm beeSwarm;
	private com.steema.teechart.styles.Box boxSeries;

	public BeeSwarmDemo(Composite c) {
		super(c);

	}

	protected void createContent() {
		super.createContent();

	}

	protected void initContent() {
		super.initContent();

	}

	protected void initChart() {
		super.initChart();
		chart1.getHeader().setVisible(true);
		chart1.setText("BeeSwarm Series");
		boxSeries = new com.steema.teechart.styles.Box(chart1.getChart());

		beeSwarm = new BeeSwarm(chart1.getChart());

		beeSwarm.getKdeFunction().getOptions().setSides(KDESides.BOTH);

		beeSwarm.getLinePen().setColor(java.awt.Color.ORANGE);
		// beeSwarm.getBrush().setColor(java.awt.Color.RED);

		boxSeries.setPosition(0);

		boxSeries.add(4.723143097);
		boxSeries.add(-163.8499657);
		boxSeries.add(175.231925);
		boxSeries.add(-48.35468425);
		boxSeries.add(14.6993532);
		boxSeries.add(114.734763);
		boxSeries.add(17.60508917);
		boxSeries.add(210.2650095);
		boxSeries.add(-61.72121173);
		boxSeries.add(114.4151786);
		boxSeries.add(196.452651);
		boxSeries.add(-93.70487623);
		boxSeries.add(94.04043151);
		boxSeries.add(-18.48801027);
		boxSeries.add(108.4558658);
		boxSeries.add(26.95490135);
		boxSeries.add(205.3224574);

		beeSwarm.setDataSource(boxSeries);
		
		//beeSwarm.fillSampleValues();

		chart1.getAxes().getBottom().setMinMax(-0.5, 0.5);
		chart1.getAxes().getBottom().setIncrement(0.2);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyText(ModifyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
