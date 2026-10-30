package android.teechart.steema.com.androiddemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.steema.teechart.TChart;
import com.steema.teechart.styles.CircularGauge;
import com.steema.teechart.themes.ThemesList;

public class Stats_Battery extends AppCompatActivity {
    public TChart chart;
    private CircularGauge cGauge;
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            // TODO Auto-generated method stub
            int level = intent.getIntExtra("level", 0);
            cGauge = new CircularGauge(chart.getChart());
            cGauge.setRedLineStartValue(0.0);
            cGauge.setRedLineEndValue(20.0);
            cGauge.setGreenLineStartValue(20.0);
            cGauge.setGreenLineEndValue(100.0);
            cGauge.getNumericGauge().setValue(level);
            cGauge.setValue(level);

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_battery);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        LinearLayout group = (LinearLayout) findViewById(R.id.linearLayoutTchart);
        chart = new TChart(this);
        group.addView(chart);
        chart.getPanel().setBorderRound(7);
        chart.getAspect().setView3D(false);
        chart.getZoom().setAllow(false);
        ThemesList.applyTheme(chart.getChart(), 11);
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED));
        chart.setText("Battery charge tool\n you can see battery percent\n in  circular gauge with embedded numeric gauge");
    }
    @Override
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
