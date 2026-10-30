/*
 * LineDemo.java
 *
 * <p>Copyright: (c) 2005-2018 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */
package features.style.line;


import java.util.Random;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

import com.steema.teechart.TChart;
import com.steema.teechart.legend.LegendStyle;
import com.steema.teechart.styles.Line;
import com.steema.teechart.themes.ColorPalettes;
import com.steema.teechart.themes.Theme;

import features.ChartSample;

/**
*
* @author yeray
*/
public class LineRealtimeDemo extends ChartSample implements SelectionListener {
	
    private Button animateButton;
    private Button threeDButton;
    private Button legendButton;
        
	final int time = 1;  // ONE_MILLISECOND
	Runnable mtimer;
	    
	/** Creates a new instance of LineRealtimeDemo */
	public LineRealtimeDemo(Composite c) {
		super(c);			
	}
	
	public void widgetDefaultSelected(SelectionEvent se) { }

	public void widgetSelected(SelectionEvent se) { }	
		
    protected void createContent() {
    	super.createContent();    

    	SelectionAdapter optionsSelector = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent se) {	
				Widget source = se.widget;
				boolean isSelected = ((Button)source).getSelection();
				if (source == animateButton) {
					if (isSelected){
						startTimer();
		            }
		            else {
		            	stopTimer();
		            }
			    } else if (source == threeDButton) {
			    	chart1.getAspect().setView3D(isSelected);
		            chart1.refreshControl();
			    } else if (source == legendButton) {
			    	chart1.getLegend().setVisible(isSelected);
		            chart1.refreshControl();
			    }
			}				
		};

        animateButton = addCheckButton("Animate", "", optionsSelector);
        threeDButton = addCheckButton("3D", "", optionsSelector);
        legendButton = addCheckButton("Legend", "", optionsSelector);    	
    }
    
    protected void initContent() {
    	super.initContent();

    	animateButton.setSelection(true);    	
    }
    
    protected void initChart() {
    	super.initChart();
        
    	ColorPalettes.applyPalette(chart1.getChart(), Theme.DefaultPalette);
        
    	Line lineSeries1 = new com.steema.teechart.styles.Line(chart1.getChart());
    	Line lineSeries2 = new com.steema.teechart.styles.Line(chart1.getChart());
    	Line lineSeries3 = new com.steema.teechart.styles.Line(chart1.getChart());
    	Line lineSeries4 = new com.steema.teechart.styles.Line(chart1.getChart());        
                
        lineSeries1.fillSampleValues(50);
        lineSeries2.fillSampleValues(50);
        lineSeries3.fillSampleValues(50);
        lineSeries4.fillSampleValues(50);
        
        chart1.getAspect().setSmoothingMode(false);
        chart1.getAspect().setTextSmooth(false);
        
        chart1.getAxes().getBottom().setIncrement(5);
        
        chart1.getHeader().setVisible(false);
        
        chart1.getLegend().setLegendStyle(LegendStyle.LASTVALUES);
        chart1.getLegend().setVisible(false);
        
        mtimer = new Runnable() {
    		public void run() {
    			AnimateSeries(chart1);
    			chart1.timerExec(time, this);
    		}
    	};
    	
    	startTimer();
    	
    	addDisposeListener(new DisposeListener() {
	        public void widgetDisposed(DisposeEvent e)
	        {
	        	stopTimer();
	        }
	    });
    }
    
    private void AnimateSeries(TChart chart){
        Random rnd = new Random();
        double newX, newY, deltaY;
        
        chart.setAutoRepaint(false);
        
        deltaY = (chart.getAxes().getLeft().getMaximum() - chart.getAxes().getLeft().getMinimum()) / 10;
        
        for (int i=0; i<chart.getSeriesCount(); i++) {
            // show only 50 points - delete the rest
            com.steema.teechart.styles.Series s = chart.getSeries(i);
            
            while(s.getCount() > 50) {
                s.delete(0);
            }
            newX = s.getXValues().getLast() + 1;
            newY = s.getYValues().getLast() + deltaY*rnd.nextDouble() - deltaY/2;

            if (Math.abs(newY)>1.0e+4){
                newY = 0.0;
            }
            
            s.add(newX, newY);
        }
        
        chart.setAutoRepaint(false);        
        chart.refreshControl();
    }
    
	private void startTimer() {
		if (chart1 != null)
			chart1.timerExec(time, mtimer);
	}

	private void stopTimer() {
		if (chart1 != null)
			chart1.timerExec(-1, mtimer);
	}
}
