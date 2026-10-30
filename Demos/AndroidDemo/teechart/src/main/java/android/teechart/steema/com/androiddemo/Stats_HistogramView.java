package android.teechart.steema.com.androiddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.steema.teechart.TChart;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.styles.Area;
import com.steema.teechart.themes.ThemesList;

public class Stats_HistogramView extends AppCompatActivity {
    public TChart chart;
    private Bundle extras;
    int[] redValues, greenValues, blueValues;
    private Area redLine, greenLine, blueLine;
    int[] redHistogram = new int[256], blueHistogram = new int[256],
            greenHistogram = new int[256];
    Axis axis1, axis2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_histogram_view);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        LinearLayout layoutR = (LinearLayout) findViewById(R.id.LinearLayoutR);
        chart = new TChart(this);
        layoutR.addView(chart);
        chart.getPanel().setBorderRound(7);
        chart.getAspect().setView3D(false);
        chart.getZoom().setAllow(false);

        ThemesList.applyTheme(chart.getChart(), 0);
        chart.setText("RGB histogram");
        extras = getIntent().getExtras();
        redValues = extras.getIntArray("redValues");
        greenValues = extras.getIntArray("greenValues");
        blueValues = extras.getIntArray("blueValues");
        redLine = new com.steema.teechart.styles.Area(chart.getChart());
        redLine.getMarks().setVisible(false);
        redLine.setTitle("Source");
        redLine.setColor(Color.RED);
        redLine.getLinePen().setColor(Color.RED);
        for (int i = 0; i < redValues.length; i++) {
            if (redValues[i] == 0) {
                redHistogram[0] = 0;
            } else {
                redHistogram[redValues[i]]++;
            }
            if (greenValues[i] == 0) {
                greenHistogram[0] = 0;
            } else {
                greenHistogram[greenValues[i]]++;
            }
            if (blueValues[i] == 0) {
                blueHistogram[0] = 0;
            } else {
                blueHistogram[blueValues[i]]++;
            }
        }
        redLine.add(redHistogram);

        chart.getLegend().setVisible(false);

        blueLine = new com.steema.teechart.styles.Area(chart.getChart());
        blueLine.getMarks().setVisible(false);
        blueLine.setColor(Color.BLUE);

        blueLine.getLinePen().setColor(Color.BLUE);

        blueLine.add(blueHistogram);

        greenLine = new com.steema.teechart.styles.Area(chart.getChart());
        greenLine.getMarks().setVisible(false);
        greenLine.setColor(Color.GREEN);
        greenLine.getLinePen().setColor(Color.GREEN);
        greenLine.add(greenHistogram);

        axis1 = new Axis(chart.getChart());
        axis2 = new Axis(chart.getChart());
        axis1.setHorizontal(false);
        axis2.setHorizontal(false);
        axis1.setAutomatic(true);
        axis2.setAutomatic(true);
        chart.getAxes().getLeft().setAutomatic(true);

        axis1.getLabels().getFont().setColor(Color.BLACK);
        axis2.getLabels().getFont().setColor(Color.BLACK);
        chart.getAxes().getLeft().setEndPosition(32);
        axis1.setStartPosition(34);
        axis1.setEndPosition(66);
        axis1.getTitle().setVisible(true);
        axis1.getTitle().setText("Number of repetitions");
        axis1.getTitle().getFont().setColor(Color.BLACK);
        axis1.getTitle().setAngle(90);
        axis2.setStartPosition(68);
        axis2.setEndPosition(100);
        chart.getAxes().getBottom().getTitle().setVisible(true);
        chart.getAxes().getBottom().getTitle()
                .setText("Pixel intensity (1-256)");
        chart.getAxes().getBottom().getTitle().getFont().setColor(Color.BLACK);
        chart.getAxes().getCustom().add(axis1);
        chart.getAxes().getCustom().add(axis2);

        greenLine.setCustomVertAxis(axis1);
        blueLine.setCustomVertAxis(axis2);

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
