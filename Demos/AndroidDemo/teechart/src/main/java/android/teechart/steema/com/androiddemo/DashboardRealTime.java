package android.teechart.steema.com.androiddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.steema.teechart.ScrollMode;
import com.steema.teechart.TChart;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.events.ChartPaintAdapter;
import com.steema.teechart.styles.FastLine;
import com.steema.teechart.styles.Series;
import com.steema.teechart.themes.ThemesList;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class DashboardRealTime extends AppCompatActivity {

    private TChart chart;
    private LinearLayout llChart;
    private Timer timer;
    private MyTimerTask myTimerTask;
    private FastLine fastLine;
    private int pointsAdded;
    private long tStart;
    private long totalMs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_realtime);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chart = new TChart(this);
        llChart = (LinearLayout) findViewById(R.id.llChart);
        llChart.addView(chart);

        ThemesList.applyTheme(chart.getChart(), 11);

        chart.getHeader().setVisible(true);
        chart.setText("RealTime Example");
        chart.getLegend().setVisible(false);
        chart.getZoom().setAllow(false);
        chart.getPanning().setAllow(ScrollMode.NONE);

        // hide things for better speed
        chart.getAspect().setView3D(false);
        chart.getLegend().setVisible(false);
        chart.getFooter().setVisible(false);
        chart.getHeader().setVisible(false);
        chart.getWalls().setVisible(false);

        chart.getAxes().getBottom().setIncrement(10);
        chart.getAxes().getLeft().getLabels().setSeparation(150);
        chart.getAxes().getLeft().getLabels().setValueFormat("#,##0");

        for (int i=0; i<7; i++) {
            fastLine = new FastLine(chart.getChart());
            fastLine.getLinePen().setWidth(chart.getGraphics3D().sizeToDIP(1));
            fastLine.fillSampleValues();
        }

        chart.getPanel().setMarginLeft(7);
        chart.getPanel().setMarginRight(5);
        chart.getPanel().setMarginBottom(15);
        totalMs = 0;
        pointsAdded = 0;

        chart.addChartPaintListener(new ChartPaintAdapter() {

            @Override
            public void chartPainted(ChartDrawEvent e) {

                if ((timer != null) || (totalMs>0)) {
                    chart.getGraphics3D().getFont().setColor(Color.darkGray);
                    chart.getGraphics3D().getFont().setSize(chart.getAxes().getBottom().getLabels().getFont().getSize()-2);

                    int xPos = chart.getChart().getChartRect().getLeft();
                    int yDiff = chart.getGraphics3D().textHeight("Hg") + chart.getGraphics3D().sizeToDIP(5);

                    String tmpS = "Points added: " + String.valueOf(pointsAdded);
                    int yPos = chart.getHeight() - yDiff * 3 - chart.getGraphics3D().sizeToDIP(5);
                    chart.getGraphics3D().textOut(xPos, yPos, tmpS);

                    long tEnd = System.currentTimeMillis();
                    long tDelta = tEnd - tStart;
                    tmpS = "Total elapsed Time: " + new DecimalFormat("#,##0").format(totalMs + tDelta) + "ms";
                    yPos += yDiff;
                    chart.getGraphics3D().textOut(xPos, yPos, tmpS);

                    tmpS = "Points added per 500 ms (mean): " +  new DecimalFormat("#,##0.##").format((pointsAdded * 500.0) / (totalMs + tDelta));
                    yPos += yDiff;
                    chart.getGraphics3D().textOut(xPos, yPos, tmpS);
                }
            }
        });

        final Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (timer==null)
                    button.setText("STOP");
                else {
                    long tEnd = System.currentTimeMillis();
                    long tDelta = tEnd - tStart;
                    totalMs += tDelta;

                    button.setText("RUN");
                }

                startStopTimer(timer==null);
            }
        });
    }

    public void startStopTimer(Boolean run) {

        if (run) {

            if (timer != null) {
                timer.cancel();
            }

            timer = new Timer();
            myTimerTask = new MyTimerTask();
            tStart = System.currentTimeMillis();

            timer.schedule(myTimerTask, 0, 100);
        } else {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }
    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Random r = new Random();
                    Series s;
                    for (int i=0; i<chart.getSeriesCount(); i++) {
                        s = chart.getSeries(i);

                        double diff = s.getYValues().getRange() / 5;
                        s.add(s.getMaxXValue() + 1, s.getYValues().getLast() + (r.nextDouble() * diff) - (diff / 2));

                        if (s.getCount() > 50) {
                            s.delete(0, s.getCount() - 50);
                        }
                    }
                    pointsAdded++;
                    chart.refreshControl();
                }
            });
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
