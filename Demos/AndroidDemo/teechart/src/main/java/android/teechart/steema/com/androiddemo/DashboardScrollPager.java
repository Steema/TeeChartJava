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

import com.steema.teechart.ScrollMode;
import com.steema.teechart.TChart;
import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.events.ChartPaintAdapter;
import com.steema.teechart.styles.Line;
import com.steema.teechart.themes.ThemesList;
import com.steema.teechart.tools.ScrollPager;

public class DashboardScrollPager extends AppCompatActivity {
    TChart chart;
    LinearLayout llScrollPager;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_scroll_pager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //CODE
        chart = new TChart(this);
        llScrollPager = (LinearLayout) findViewById(R.id.llScrollPager);

        extras = new Bundle();
        chart.getHeader().setVisible(true);
        chart.setText("ScrollPager Tool Example");
        chart.getLegend().setVisible(false);
        chart.getZoom().setAllow(false);
        chart.getPanning().setAllow(ScrollMode.NONE);
        llScrollPager.addView(chart);
        // Start tool
        Line lineSeries = new Line(chart.getChart());
        lineSeries.getLinePen().setWidth(2);
        lineSeries.fillSampleValues(100);

        ScrollPager scrollPager1 = new ScrollPager(chart.getChart());
        scrollPager1.setSeries(lineSeries);

        ThemesList.applyTheme(scrollPager1.getSubChartTChart().getChart(), 11);
        ThemesList.applyTheme(chart.getChart(), 11);
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
