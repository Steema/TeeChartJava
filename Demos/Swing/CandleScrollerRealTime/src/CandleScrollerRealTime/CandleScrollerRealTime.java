/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CandleScrollerRealTime;

import com.steema.teechart.DateTime;
import com.steema.teechart.Dimension;
import com.steema.teechart.TChart;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.events.ChartPaintAdapter;
import com.steema.teechart.styles.Candle;
import com.steema.teechart.styles.CandleStyle;
import com.steema.teechart.styles.FastLine;
import com.steema.teechart.styles.VerticalAxis;
import com.steema.teechart.themes.ThemesList;
import com.steema.teechart.tools.ScrollPager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Calendar;
import javax.swing.JFrame;

/**
 *
 * @author Marc
 */
public class CandleScrollerRealTime extends JFrame{

    int sizeX = 500;
    int sizeY = 500;

    private TChart chart;
    private final FastLine series;
    private final Candle candle;  //ex Candle
    private final ScrollPager scrollPager;
    
    java.util.Random rand = new java.util.Random();
    
    public CandleScrollerRealTime() {
        super();
        
        addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent evt) {
                scrollPagerDemoResized(evt);
            }
        });
         
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(820, 520));

        chart = new TChart();
        //chart.setAllowMouse(false);
        
        chart.getPanel().getGradient().setVisible(false);
        chart.getHeader().setText("TeeChart scroller demo");
        com.steema.teechart.themes.ThemesList.applyTheme(chart.getChart(), 1);
        
        chart.getAxes().getBottom().getLabels().setDateTimeFormat("dd/MM/yy hh:mm");
        chart.getAxes().getBottom().setAutomatic(true);
        
        chart.getAspect().setView3D(false);
       
        chart.getWalls().getBack().getGradient().setVisible(false);
        chart.getWalls().getBack().setColor(chart.getWalls().getBack().getGradient().getStartColor());
        
        candle = new Candle(chart.getChart());
        candle.setStyle(CandleStyle.CANDLEBAR);
        
        //create the Series to show in the scroller and hide it on the main chart
        series = new FastLine(chart.getChart());
        series.setColor(Color.fromArgb(128, 255, 255, 255));
        series.setVerticalAxis(VerticalAxis.RIGHT);
        chart.getAxes().getRight().setStartPosition(0);
        chart.getAxes().getRight().setEndPosition(0);
        chart.getAxes().getRight().getLabels().setVisible(false);
        series.getXValues().setDateTime(true);
        
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                sizeX = e.getComponent().getWidth();
                sizeY = e.getComponent().getHeight();
                setPreferredSize(new Dimension(sizeX, sizeY));
            }
        });
        
        add(chart);
        pack();
        setVisible(true);
        
        scrollPager = new ScrollPager(chart.getChart());
        //scrollPager.getSubChartTChart().setAllowMouse(false);
        scrollPager.getSubChartTChart().getFooter().setText("duh \n"); //sizing bug workaround. To fix
        scrollPager.getSubChartTChart().getFooter().setVisible(true);
        scrollPager.setSeries(series);
        scrollPager.setActive(true);

        chart.addChartPaintListener(new ChartPaintAdapter() {
            @Override
            public void chartPainted(ChartDrawEvent e) {
               paintCounter++;
               chart.getGraphics3D().getFont().setSize(11);
               chart.getGraphics3D().getFont().setColor(Color.white);
               chart.getGraphics3D().textOut(10, 10, "repaints:" + Integer.toString(paintCounter) 
                                                     +  ", total points: " +  Integer.toString(counter));
                                                     //+ " active Series points: " + Integer.toString(series.getCount()) ); 
            }
        });
    }
    
    private void scrollPagerDemoResized(ComponentEvent evt) {
        if (scrollPager != null) {
            ThemesList.applyTheme(scrollPager.getSubChartTChart().getChart(), 1);
            scrollPager.getSubChartTChart().getFooter().setText(" "); //sizing bug workaround. To fix
            scrollPager.getSubChartTChart().getFooter().setVisible(true);
        }
    }
    
    private int paintCounter = 0;
    private int counter = 0;
    
    public boolean renderChart(){

        drawChart();
        
        try {
                if (counter > 10000)
                {
                    //only use allowmouse if requiring to disable mouse interaction with chart
                    //scrollPager.getSubChartTChart().setAllowMouse(true);
                    //chart.setAllowMouse(true);
                  
                    return false;
                }
                else
                {
                    //scrollPager.getSubChartTChart().setAllowMouse(false);
                    //chart.setAllowMouse(false);
                    Thread.sleep(20);
                }
                            
        } catch (InterruptedException e) {
                e.printStackTrace();
        }
        
        return true;
    }
    
    DateTime tmpDate;
    double lastClose;
    boolean setAxisScale = false;
    
    public void drawChart() {
        
        if ((counter == 0) && (scrollPager != null)) {
          ThemesList.applyTheme(scrollPager.getSubChartTChart().getChart(), 1);
          scrollPager.getSubChartTChart().getFooter().setText(" "); //sizing bug workaround. To fix
          scrollPager.getSubChartTChart().getFooter().setVisible(true);
        }
                
        series.beginUpdate();
        scrollPager.getSubChartTChart().getSeries().getSeries(0).beginUpdate();
        
        double candleHigh;
        double candleLow;
        
        for (int j = 0; j < 10; j++) {
            counter++;
            double newVal = rand.nextInt(20);
            if (j > 0)
            {
                if (lastClose>120)
                  newVal = - newVal;
                else if (lastClose<-20)
                  newVal = newVal; //! ;-)
                else
                  newVal = 20 - rand.nextInt(40);
                
                newVal = lastClose + newVal;
                
                series.add(tmpDate.getTimeInMillis(),newVal);
                scrollPager.getSubChartTChart().getSeries().getSeries(0).add(tmpDate.getTimeInMillis(),newVal);

                //random data with some kind of close-open consistency....
                double closeVal = newVal;
                if (closeVal > lastClose)
                {
                    candleHigh = closeVal+10-rand.nextInt(3);
                    candleLow = lastClose-10+rand.nextInt(3);
                }
                else
                {
                    candleHigh = lastClose+10-rand.nextInt(3);
                    candleLow = closeVal-10+rand.nextInt(3);
                }
                candle.add(tmpDate.getTimeInMillis(),lastClose,candleHigh,candleLow,closeVal);
                
                lastClose = closeVal;
            } 
            else
            {
              series.add(tmpDate.getTimeInMillis(),newVal);
              scrollPager.getSubChartTChart().getSeries().getSeries(0).add(tmpDate.getTimeInMillis(),newVal);
              
              candleHigh = newVal+5-rand.nextInt(3);
              candleLow = newVal-5+rand.nextInt(3);
              lastClose = candleLow+rand.nextInt(6);
              candle.add(tmpDate.getTimeInMillis(),newVal,candleHigh,candleLow,lastClose);
            }
            
            //demonstrates setting a limit on maximum points in a Series
            /*if (series.getCount() > 8000)  
            {
                series.delete(0, 1, false);
                candle.delete(0, 1, false);
                scrollPager.getSubChartTChart().getSeries().getSeries(0).delete(0, 1, false);
            }*/
           
            tmpDate.add(Calendar.DATE, 1);
        }

        scrollPager.getSubChartTChart().getSeries().getSeries(0).endUpdate();
        series.endUpdate();
        chart.repaint();

        int lastVisibleId = series.getCount() - 1;
        if (lastVisibleId < 0) {
            lastVisibleId = 0;
        }

        int firstVisibleId = lastVisibleId - 100;
        if (firstVisibleId < 0) {
            firstVisibleId = 0;
        }

        double last = series.getXValues().getValue(lastVisibleId);
        double first = series.getXValues().getValue(firstVisibleId);

        if (series.getCount() < 500) //scroll data in when running the lowly populated chart
            chart.getAxes().getBottom().setMinMax(first, last);
        else if (setAxisScale==false)
        { //stop resetting the main chart's x axis. Give control to the scroller with a starting 'block'
          setAxisScale = true;
          chart.getAxes().getBottom().setMinMax(series.getXValues().getValue(100), series.getXValues().getValue(400)); 
          
          scrollPager.getColorBandTool().setStart(series.getXValues().getValue(100));
          scrollPager.getColorBandTool().setEnd(series.getXValues().getValue(400));
          
          //Show Chart Axis Labels
          chart.getAxes().getBottom().getLabels().setVisible(true);
          chart.getAxes().getBottom().getTitle().setText(" "); //sizing bug workaround. To fix
          chart.getAxes().getBottom().getTitle().setVisible(true);
        }

        pack();
    }
    
    public static void main(String[] args) {

      CandleScrollerRealTime teeChartScroll = new CandleScrollerRealTime();
      
      boolean keepGoing = true;        
        
        teeChartScroll.tmpDate = DateTime.getToday(); //set start datetime
        teeChartScroll.tmpDate.addMonths(-329); //we're going to add 27 years of sample daily data.
        while (keepGoing==true) {

            keepGoing = teeChartScroll.renderChart();
        }
    }
}

