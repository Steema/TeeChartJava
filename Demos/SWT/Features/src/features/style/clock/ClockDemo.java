/*
 * ClockDemo.java
 *
 * <p>Copyright: (c) 2005-2018 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */

package features.style.clock;

import com.steema.teechart.BevelStyle;
import com.steema.teechart.TChart;
import com.steema.teechart.Wall;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.styles.Clock;
import com.steema.teechart.styles.ClockStyle;

import features.ChartSample;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 *
 * @author yeray
 */
public class ClockDemo extends ChartSample implements SelectionListener  {

    private TChart[] chart;
    private Clock[] clock;

    /** Creates a new instance of ClockDemo */
    public ClockDemo(Composite c) {
        super(c);
    }
    
	public void widgetDefaultSelected(SelectionEvent se) { }

	public void widgetSelected(SelectionEvent se) { }	
	
	protected void createContent() {
    	super.createContent();
	}
	
    protected void initContent() {
    	super.initContent();
    }
    
    protected void initChart() {
    	super.initChart();
    	
        initCharts();
        initClocks();
        
    	addDisposeListener(new DisposeListener() {
	        public void widgetDisposed(DisposeEvent e) {
	        	for (int t=0; t < clock.length; t++)
	                clock[t].dispose();
	        }
	    });
    }

    protected void initCharts() {
        Gradient tmpGradient;
        Wall tmpWall;

        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        getSamplePane().setLayout(gridLayout);
        
        chart = new TChart[4];
        
        for (int t=0; t < chart.length; t++)  {
        	if (t==0)
        		chart[t] = chart1;
        	else
        		chart[t] = new TChart(getSamplePane(), SWT.PUSH);
        	
        	chart[t].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        	
            chart[t].getAspect().setView3D(false);
            chart[t].getAxes().setVisible(false);
            chart[t].getAxes().getBottom().setIncrement(30.0);
            chart[t].getFooter().setVisible(false);
            chart[t].getFrame().setVisible(false);
            chart[t].getHeader().setVisible(false);
            chart[t].getLegend().setVisible(false);
            chart[t].getPanning().setActive(false);
            chart[t].getWalls().setView3D(false);
            chart[t].getZoom().setAllow(false);
            tmpWall = chart[t].getWalls().getBack();
            tmpWall.getBrush().setColor(Color.WHITE);
            tmpWall.getBrush().setVisible(false);
            tmpWall.getPen().setVisible(false);
        }

        
        TChart tmpChart;

        /* chart0 */
        tmpChart = chart[0];
        tmpChart.getPanel().setBevelOuter(BevelStyle.NONE);
        tmpGradient = tmpChart.getPanel().getGradient();
        tmpGradient.setEndColor(Color.SILVER);
        tmpGradient.setStartColor(Color.BLACK);
        tmpGradient.setVisible(true);

        /* chart1 */
        tmpChart = chart[1];
        tmpChart.getPanel().setBevelOuter(BevelStyle.NONE);
        tmpGradient = tmpChart.getPanel().getGradient();
        tmpGradient.setEndColor(Color.WHITE);
        tmpGradient.setStartColor(Color.ORANGE);
        tmpGradient.setVisible(true);

        /* chart2 */
        tmpChart = chart[2];
        tmpGradient = tmpChart.getPanel().getGradient();
        tmpGradient.setEndColor(Color.BLACK);
        tmpGradient.setStartColor(Color.LIGHT_YELLOW);
        tmpGradient.setVisible(true);

        /* chart3 */
        tmpChart = chart[3];
        tmpChart.getPanel().setBevelOuter(BevelStyle.LOWERED);
        tmpChart.getPanel().setBevelInner(BevelStyle.LOWERED);
        tmpGradient = tmpChart.getPanel().getGradient();
        tmpGradient.setEndColor(Color.SILVER);
        tmpGradient.setStartColor(Color.BLACK);
        tmpGradient.setVisible(true);
    }

    protected void initClocks() {

        clock = new Clock[4];
        for (int t=0; t < clock.length; t++) {
            clock[t] = new Clock();
            clock[t].setChart(chart[t].getChart());
            clock[t].getMarks().setVisible(false);
        }
        Clock tmpClock;

        /* Clock0 */
        tmpClock = clock[0];
        tmpClock.getBrush().setColor(Color.RED);
        tmpClock.getCircleLabelsFont().setColor(Color.LIME);
        tmpClock.getCircleLabelsFont().setSize(13);
        tmpClock.getCircleLabelsFont().setBold(true);
        tmpClock.getCirclePen().setColor(Color.YELLOW);
        tmpClock.getPenHours().setColor(Color.BLACK);
        tmpClock.getPenMinutes().setColor(Color.BLACK);
        tmpClock.getPenSeconds().setColor(Color.WHITE);

        /* Clock1 */
        tmpClock = clock[1];
        tmpClock.setColor(Color.WHITE);
        tmpClock.getBrush().setColor(Color.YELLOW);
        tmpClock.getCircleLabelsFont().setColor(Color.RED);
        tmpClock.getCircleLabelsFont().setSize(16);
        tmpClock.getCircleLabelsFont().setBold(true);
        tmpClock.getCirclePen().setVisible(false);
        tmpClock.getPenHours().setColor(Color.BLUE);
        tmpClock.getPenMinutes().setColor(Color.BLACK);
        tmpClock.getPenSeconds().setColor(Color.WHITE);

        /* Clock2 */
        tmpClock = clock[2];
        tmpClock.setColor(Color.LIME);
        tmpClock.getBrush().setColor(Color.LIME);
        tmpClock.getCircleLabelsFont().setColor(Color.WHITE);
        tmpClock.getCircleLabelsFont().setSize(13);
        tmpClock.getCirclePen().setColor(Color.BLUE);
        tmpClock.getCirclePen().setStyle(DashStyle.DOT);
        tmpClock.getPenHours().setColor(Color.BLACK);
        tmpClock.getPenMinutes().setColor(Color.BLACK);
        tmpClock.getPenSeconds().setColor(Color.WHITE);
        tmpClock.setStyle(ClockStyle.DECIMAL);

        /* Clock3 */
        tmpClock = clock[3];
        tmpClock.setColor(Color.LIME);
        tmpClock.getBrush().setColor(Color.WHITE);
        tmpClock.getCircleLabelsFont().setColor(Color.MAROON);
        tmpClock.getCircleLabelsFont().setSize(16);
        tmpClock.getCircleLabelsFont().setBold(true);
        tmpClock.getCircleLabelsFont().setItalic(true);
        tmpClock.getCirclePen().setVisible(false);
        tmpClock.getPenHours().setColor(Color.TEAL);
        tmpClock.getPenHours().setWidth(3);
        tmpClock.getPenMinutes().setColor(Color.BLACK);
        tmpClock.getPenSeconds().setColor(Color.WHITE);
        tmpClock.getPenSeconds().setWidth(2);
    }
}
