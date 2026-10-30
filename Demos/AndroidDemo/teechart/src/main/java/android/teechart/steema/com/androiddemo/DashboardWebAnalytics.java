package android.teechart.steema.com.androiddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.steema.teechart.TChart;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.legend.LegendAlignment;
import com.steema.teechart.styles.Donut;
import com.steema.teechart.styles.Line;
import com.steema.teechart.styles.PointerStyle;
import com.steema.teechart.tools.Annotation;
import com.steema.teechart.tools.AnnotationPosition;

public class DashboardWebAnalytics extends AppCompatActivity {
    TChart chart, chart2, chart3;
    LinearLayout llchart, llchart2, llchart3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_web_analytics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //CODE

        llchart = (LinearLayout) findViewById(R.id.chart1);
        llchart2 = (LinearLayout) findViewById(R.id.chart3);
        llchart3 = (LinearLayout) findViewById(R.id.chart2);
        chart = new TChart(this);
        chart2 = new TChart(this);
        chart3 = new TChart(this);

        initDonut1();
        initDonut2();
        initLine();

    }

    private void initLine() {
        llchart3.addView(chart2);
        chart3.getZoom().setAllow(false);

        chart3.getLegend().setAlignment(LegendAlignment.BOTTOM);
        chart3.getLegend().getFont().setName("Verdana");
        chart3.getLegend().getShadow().setTransparency(0);
        chart3.getLegend().setTransparent(true);
        chart3.getHeader().setVisible(false);
        chart3.getAxes().getLeft().getGrid().setVisible(false);
        chart3.getAxes().getLeft().getMinorTicks().setVisible(false);
        chart3.getAxes().getLeft().getLabels().setSeparation(100);
        chart3.getAxes().getBottom().getMinorTicks().setVisible(false);
        chart3.getAspect().setView3D(false);
        chart3.getPanel().getGradient().setVisible(false);
        chart3.getPanel().setColor(Color.WHITE);
        chart3.getWalls().getBack().setVisible(false);

        Line line1 = new Line(chart3.getChart());
        line1.setTitle("Speed");
        line1.setSmoothed(true);
        line1.getPointer().setInflateMargins(true);
        line1.getPointer().getPen().setVisible(false);
        line1.getPointer().setStyle(PointerStyle.CIRCLE);
        line1.getPointer().setVisible(true);
        line1.fillSampleValues(6);

        Line line2 = new Line(chart3.getChart());
        line2.setColor(Color.fromArgb(6, 191, 89));
        line2.setTitle("Time");
        line2.setSmoothed(true);
        line2.getPointer().setInflateMargins(true);
        line2.getPointer().getPen().setVisible(false);
        line2.getPointer().setStyle(PointerStyle.CIRCLE);
        line2.getPointer().setVisible(true);
        line2.fillSampleValues(6);

        Line line3 = new Line(chart3.getChart());
        line3.setColor(Color.fromArgb(238, 17, 34));
        line3.setTitle("Visitors");
        line3.setSmoothed(true);
        line3.getPointer().setInflateMargins(true);
        line3.getPointer().getPen().setVisible(false);
        line3.getPointer().setStyle(PointerStyle.CIRCLE);
        line3.getPointer().setVisible(true);
        line3.fillSampleValues(6);
    }

    private void initDonut1() {
        llchart.addView(chart);
        chart.getFooter().getFont().setColor(Color.black);
        chart.getFooter().getFont().setSize(28);
        chart.getFooter().getFont().setName("Tahoma");
        chart.getFooter().setText("Expenses");
        chart.getHeader().setVisible(false);
        chart.getLegend().setVisible(false);
        chart.getAspect().setView3D(false);
        chart.getPanel().getGradient().setVisible(false);
        chart.getPanel().setColor(Color.WHITE);
        chart.getWalls().getBack().setVisible(false);

        Donut donut1 = new Donut(chart.getChart());
        donut1.getMarks().setVisible(false);
        donut1.getShadow().setVisible(false);
        donut1.getOtherSlice().getLegend().setVisible(false);
        donut1.getPen().setVisible(false);
        donut1.setRotationAngle(180);
        donut1.setDonutPercent(95);
        donut1.add(750);
        donut1.add(250, Color.WHITE);
        donut1.setCircled(true);

        Annotation annotation1 = new Annotation(chart.getChart());
        annotation1.setPosition(AnnotationPosition.CENTER);
        annotation1.getShape().getFont().setSize(39);
        annotation1.getShape().getFont().setName("Segoe UI");
        annotation1.getShape().setText("$750");
        annotation1.getShape().setTransparent(true);
    }

    private void initDonut2() {
        llchart2.addView(chart3);
        chart2.getFooter().getFont().setColor(Color.black);
        chart2.getFooter().getFont().setSize(28);
        chart2.getFooter().getFont().setName("Tahoma");
        chart2.getFooter().setText("Visitors");
        chart2.getHeader().setVisible(false);
        chart2.getLegend().setVisible(false);
        chart2.getAspect().setView3D(false);
        chart2.getPanel().getGradient().setVisible(false);
        chart2.getPanel().setColor(Color.WHITE);
        chart2.getWalls().getBack().setVisible(false);

        Donut donut2 = new Donut(chart2.getChart());
        donut2.getMarks().setVisible(false);
        donut2.getShadow().setVisible(false);
        donut2.getOtherSlice().getLegend().setVisible(false);
        donut2.getPen().setVisible(false);
        donut2.setRotationAngle(247);
        donut2.setDonutPercent(95);
        donut2.add(3390, Color.fromArgb(6, 191, 89));
        donut2.add(2610, Color.WHITE);
        donut2.setCircled(true);

        Annotation annotation2 = new Annotation(chart2.getChart());
        annotation2.setPosition(AnnotationPosition.CENTER);
        annotation2.getShape().getFont().setSize(39);
        annotation2.getShape().getFont().setName("Segoe UI");
        annotation2.getShape().setText("3390");
        annotation2.getShape().setTransparent(true);
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
