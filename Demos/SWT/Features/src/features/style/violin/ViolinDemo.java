package features.style.violin;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.functions.KDESides;
import com.steema.teechart.styles.BeeSwarm;
import com.steema.teechart.styles.Violin;

import features.ChartSample;
import features.utils.EnumStrings;

public class ViolinDemo extends ChartSample implements ModifyListener, SelectionListener {

	private Violin violin;
	private com.steema.teechart.styles.Box boxSeries;

	public ViolinDemo(Composite c) {
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
		chart1.setText("Violin Series");
        //chart1.getAxes().getAxis(WIDTH).setAutomatic(true);
        //chart1.getAxes().getAxis(HEIGHT).setAutomatic(true);
        
        boxSeries = new com.steema.teechart.styles.Box(chart1.getChart());
        
        violin = new Violin(chart1.getChart());
        
        chart1.addSeries(violin);
        
        violin.getKdeFunction().getOptions().setSides(KDESides.BOTH);

            violin.getKdeFunction().getOptions().setResolution(500);

            //violin.getLinePen().setColor(java.awt.Color.ORANGE);
            //violin.getBrush().setColor(java.awt.Color.RED);

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

            violin.setDataSource(boxSeries);

            violin.checkDataSource();	      
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
