package android.teechart.steema.com.androiddemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.steema.teechart.TChart;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.events.ChartPaintAdapter;
import com.steema.teechart.styles.Calendar;
import com.steema.teechart.styles.HorizBar;
import com.steema.teechart.styles.MarksStyle;
import com.steema.teechart.styles.MultiBars;

import java.util.Random;

public class DashboardServerStatus extends AppCompatActivity {

    TChart chart, chart2;
    LinearLayout llChart, llChart2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_server_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        llChart = (LinearLayout) findViewById(R.id.llChart1);
        llChart2 = (LinearLayout) findViewById(R.id.llChart2);

        chart = new TChart(this);
        chart2 = new TChart(this);

        llChart.addView(chart);
        llChart2.addView(chart2);

        //region Bars
        chart.getPanel().getGradient().setVisible(false);
        chart.getWalls().setVisible(false);
        chart.getZoom().setAllow(false);
        chart.getHeader().setAutoSize(false);
        chart.getHeader().getFont().setColor(Color.GRAY);
        chart.getHeader().getFont().setSize(36);
        chart.getHeader().getFont().setName("Segoe UI");
        chart.getHeader().getPen().setColor(new Color(128, 128, 128));
        chart.getHeader().getPen().setWidth(0);
        chart.getHeader().getPen().setStyle(DashStyle.DOT);
        chart.getHeader().getShadow().setVisible(false);
        chart.getHeader().setText("Server Status");
        chart.getHeader().setTransparent(false);
        chart.getHeader().setAdjustFrame(false);
        chart.getHeader().getBrush().setTransparency(100);
        chart.getAxes().getBottom().setVisible(false);
        chart.getAxes().getBottom().setMinimumOffset(5);
        chart.getAxes().getBottom().setMaximumOffset(5);
        chart.getAxes().getLeft().setVisible(false);
        chart.getLegend().setVisible(false);
        chart.getAspect().setView3D(false);
        chart.getPanel().setColor(Color.WHITE);

        final HorizBar horizBar1 = new HorizBar(chart.getChart());
        horizBar1.getPen().setTransparency(50);
        horizBar1.getPen().setStyle(DashStyle.DASH);
        horizBar1.getPen().setColor(new Color(128, 128, 128));
        horizBar1.getMarks().setVisible(false);
        horizBar1.setColor(Color.WHITE);
        horizBar1.setBarHeightPercent(59);
        horizBar1.setMultiBar(MultiBars.NONE);

        for (int i = 0; i < 5; i++) {
            horizBar1.add(100);
        }

        HorizBar horizBar2 = new HorizBar(chart.getChart());
        horizBar2.getPen().setVisible(false);
        horizBar2.setColorEach(true);
        horizBar2.getMarks().getFont().setColor(Color.WHITE);
        horizBar2.getMarks().getFont().setName("Segoe UI");
        horizBar2.getMarks().getFont().setBold(true);
        horizBar2.getMarks().setTransparent(true);
        horizBar2.getMarks().setStyle(MarksStyle.PERCENT);
        horizBar2.getMarks().getArrow().setVisible(false);
        horizBar2.getMarks().getCallout().getArrow().setVisible(false);
        horizBar2.getMarks().getCallout().setLength(-57);
        horizBar2.setBarHeightPercent(55);
        horizBar2.setMultiBar(MultiBars.NONE);
        horizBar2.add(new double[]{60, 85, 40, 55, 50});

        chart.addChartPaintListener(new ChartPaintAdapter() {

            @Override
            public void chartPainted(ChartDrawEvent e) {
                chart.getGraphics3D().getFont().setColor(Color.BLACK);
                chart.getGraphics3D().getFont().setBold(false);
                int XPos = chart.getChart().getChartRect().getLeft() + 5;
                chart.getGraphics3D().textOut(XPos, horizBar1.calcYPos(5) - 20, "Memory Usage");
                chart.getGraphics3D().textOut(XPos, horizBar1.calcYPos(4) - 20, "CPU usage");
                chart.getGraphics3D().textOut(XPos, horizBar1.calcYPos(3) - 20, "Storage space");
                chart.getGraphics3D().textOut(XPos, horizBar1.calcYPos(2) - 20, "Apps installed");
                chart.getGraphics3D().textOut(XPos, horizBar1.calcYPos(1) - 20, "Bandwidth");
            }
        });
        horizBar2.getMarks().getFont().setSize(13);
        //endregion

        //region Calendar
        chart2.getPanel().getGradient().setVisible(false);
        chart2.getPanel().setColor(Color.WHITE);
        chart2.getWalls().setVisible(false);
        chart2.getHeader().setVisible(false);

        Calendar calendar1 = new Calendar(chart2.getChart());
        calendar1.getPen().setVisible(false);
        calendar1.getMonths().getFont().setSize(13);
        calendar1.getMonths().getFont().setName("Segoe UI");
        calendar1.getSunday().setColor(new Color(68, 102, 163));
        calendar1.getSunday().getPen().setVisible(false);
        calendar1.getToday().setColor(new Color(227, 28, 33));
        calendar1.getToday().getPen().setVisible(false);
        calendar1.getTrailing().getFont().setColor(Color.GRAY);
        calendar1.getWeekDays().setColor(new Color(204, 204, 255));
        calendar1.getWeekDays().setTransparent(false);
        calendar1.getWeekDays().getPen().setVisible(false);

        calendar1.addCalendarChangeListener(new Calendar.CalendarChangeListener() {

            @Override
            public void seriesChanged(Calendar.CalendarChangeEvent e) {

                Random rnd1 = new Random();

                for (int i = 0; i < chart.getSeries(1).getCount(); i++) {
                    chart.getSeries(1).getXValues().setValue(i, rnd1.nextInt(100));
                }

                chart.refreshControl();
            }
        });
        //endregion
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
