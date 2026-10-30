package android.teechart.steema.com.androiddemo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.steema.teechart.Chart;
import com.steema.teechart.TChart;
import com.steema.teechart.Zoom;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.GradientDirection;
import com.steema.teechart.languages.Language;
import com.steema.teechart.legend.LegendAlignment;
import com.steema.teechart.styles.Area;
import com.steema.teechart.styles.Arrow;
import com.steema.teechart.styles.Bar3D;
import com.steema.teechart.styles.BarJoin;
import com.steema.teechart.styles.Bezier;
import com.steema.teechart.styles.BeeSwarm;
import com.steema.teechart.styles.Box;
import com.steema.teechart.styles.BubbleCloud;
import com.steema.teechart.styles.Calendar;
import com.steema.teechart.styles.Candle;
import com.steema.teechart.styles.Circular;
import com.steema.teechart.styles.CircularGauge;
import com.steema.teechart.styles.ColorGrid;
import com.steema.teechart.styles.Contour;
import com.steema.teechart.styles.Custom3DPalette;
import com.steema.teechart.styles.CustomPoint;
import com.steema.teechart.styles.Donut;
import com.steema.teechart.styles.Error;
import com.steema.teechart.styles.ErrorPoint3D;
import com.steema.teechart.styles.FastLine;
import com.steema.teechart.styles.Funnel;
import com.steema.teechart.styles.Gantt;
import com.steema.teechart.styles.HighLow;
import com.steema.teechart.styles.Histogram;
import com.steema.teechart.styles.HorizLine;
import com.steema.teechart.styles.HorizBeeSwarm;
import com.steema.teechart.styles.HorizViolin;
import com.steema.teechart.styles.IsoSurface;
import com.steema.teechart.styles.Line;
import com.steema.teechart.styles.LinearGauge;
import com.steema.teechart.styles.Map;
import com.steema.teechart.styles.MarksTail;
import com.steema.teechart.styles.Pie;
import com.steema.teechart.styles.Points3D;
import com.steema.teechart.styles.Polar;
import com.steema.teechart.styles.Radar;
import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.SeriesPointer;
import com.steema.teechart.styles.Surface;
import com.steema.teechart.styles.Tower;
import com.steema.teechart.styles.TriSurface;
import com.steema.teechart.styles.Vector3D;
import com.steema.teechart.styles.Volume;
import com.steema.teechart.styles.Violin;
import com.steema.teechart.styles.Waterfall;
import com.steema.teechart.styles.World;
import com.steema.teechart.themes.ThemesList;
import com.steema.teechart.tools.PieTool;
import com.steema.teechart.tools.PieToolStyle;
import com.steema.teechart.tools.Rotate;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class ChartView extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private int DEFAULT_THEME = 13;
    private TChart chart;
    private Series series;
    private Bundle extras;

    private boolean mExternalStorageAvailable = false;
    private boolean mExternalStorageWritable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getSharedPreferences("preferences", 0).edit().clear().commit();
        setContentView(R.layout.activity_chart_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply();

        chart = new TChart(this);
        LinearLayout group = (LinearLayout) findViewById(R.id.llChartView);
        group.addView(chart);
        extras = getIntent().getExtras();
        if (extras.getBoolean("chartFile", false)) {
            try {
                Chart auxChart = chart.getImport().getTemplate().fromFile(extras.getString("chartValue"));
                chart.setChart(auxChart);
                if (chart.getSeriesCount()>0) {
                    series = chart.getSeries(0);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (extras.getBoolean("serieSelected", true)) {
                selectSerie(extras.getString("seriesSelected"));
            }
        }

        createPrefs();
    }

    /*
    * Create the new series
     */
    public void selectSerie(String serieSelected) {
        chart.removeAllSeries();

        try {
            series = Series.createNewSeries(chart.getChart(), Class.forName("com.steema.teechart.styles." + serieSelected), null);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        chart.addSeries(series);

        ThemesList.applyTheme(chart.getChart(), DEFAULT_THEME);

        chart.getPanel().setBorderRound(7);
        chart.getHeader().setText(serieSelected + " Series");
        chart.getHeader().getFont().setSize(14);
        chart.getAxes().getLeft().getLabels().setSeparation(150);

        //the following may be used and set to change default zoom and
        //scroll behaviour. See the tutorials for more information.
        //chart.getZoom().setZoomStyle( Zoom.ZoomStyle.FULLCHART);
        //if inchart ...>
        //		chart.getPanning().setMouseButton(FrameworkMouseEvent.BUTTON1);
        //		chart.getZoom().setMouseButton(FrameworkMouseEvent.BUTTON3);

        chart.getZoom().setZoomStyle(Zoom.ZoomStyle.CLASSIC);
        //chart.getZoom().setZoomReset(ZoomResetStyle.LONGPRESS); //default double-tap

        if (series instanceof BubbleCloud)
            series.fillSampleValues(10);
        else if ((series instanceof Vector3D) || (series instanceof FastLine))
            series.fillSampleValues(100);
        else
            series.fillSampleValues(8);

        if (seriesInstanceOf(Arrays.asList(Map.class, TriSurface.class, Vector3D.class, Bar3D.class,
                ErrorPoint3D.class, Points3D.class, Surface.class, IsoSurface.class, Tower.class,
                Waterfall.class))) {

            new Rotate(chart.getChart());
            chart.getAspect().setView3D(true);
        }

        if ((series instanceof Contour) || (series instanceof ColorGrid)) {
            chart.getZoom().setZoomStyle(Zoom.ZoomStyle.INCHART);
        }

        if (series instanceof Custom3DPalette) {
            ((Custom3DPalette)series).setUseColorRange(true);
            ((Custom3DPalette)series).setUsePalette(false);

            chart.getAspect().setOrthogonal(false);
            chart.getAspect().setZoom(70);
            chart.getAspect().setRotation(320);
            chart.getAspect().setElevation(340);
            chart.getAspect().setPerspective(37);
            chart.getAspect().setChart3DPercent(90);
            chart.getAxes().getDepth().setVisible(true);
            chart.getAxes().getLeft().setIncrement(0.2);

            if (series instanceof Vector3D)
                chart.getAxes().getDepth().setIncrement(20);
            else
                chart.getAxes().getBottom().setIncrement(2);
        }

        if (series instanceof LinearGauge) {
            chart.getHeader().getFont().setSize(14);
            ((LinearGauge) series).getHand().getGradient().setVisible(true);
            ((LinearGauge) series).getHand().getGradient().setStartColor(Color.GREEN);
            ((LinearGauge) series).getHand().getGradient().setEndColor(Color.YELLOW);
            ((LinearGauge) series).getHand().getGradient().setDirection(GradientDirection.HORIZONTAL);
            ((LinearGauge) series).getHand().getColor().transparentColor(20);
        }

        /*if (!(series instanceof Custom3D)) {
            int paletteNum = 1;
            for (int i = 0; i < series.getCount(); i++)
                series.getColors().setColor(i, ColorPalettes.Palettes[paletteNum][i % ColorPalettes.Palettes[paletteNum].length]);
        }*/

        if (series instanceof Box) {
            ((Box)series).getMildOut().setHorizSize(chart.getGraphics3D().sizeToDIP(4));
            ((Box)series).getMildOut().setVertSize(chart.getGraphics3D().sizeToDIP(4));
            ((Box)series).getExtrOut().setHorizSize(chart.getGraphics3D().sizeToDIP(4));
            ((Box)series).getExtrOut().setVertSize(chart.getGraphics3D().sizeToDIP(4));
        }

        if (series instanceof Circular) {
            ((Circular) series).setCircled(true);

            if (series instanceof Pie) {
                ((Pie)series).setExplodeBiggest(10);
                PieTool pieTool1 = new PieTool(chart.getChart());
                pieTool1.setSeries(series);
                pieTool1.setStyle(PieToolStyle.EXPLODE);
            }
        }

        if (!(seriesInstanceOf(Arrays.asList(FastLine.class, Line.class, HorizLine.class,
                World.class, Map.class,  CircularGauge.class, BubbleCloud.class, Tower.class,
                Surface.class, IsoSurface.class, TriSurface.class, ColorGrid.class, Candle.class,
                Contour.class))))
            series.setColorEach(true);

        if (series instanceof BubbleCloud) {
            series.getMarks().setTransparent(true);
            series.getMarks().getFont().setColor(Color.WHITE);
        }

        if (seriesInstanceOf(Arrays.asList(Area.class, Candle.class, Contour.class, FastLine.class,
                Vector3D.class)))
            chart.getLegend().setVisible(false);
        else if (seriesInstanceOf(Arrays.asList(BubbleCloud.class, Donut.class, Funnel.class,
                Gantt.class, Pie.class, Radar.class, World.class)))
            chart.getLegend().setAlignment(LegendAlignment.BOTTOM);

        if ((series instanceof CustomPoint) || (series instanceof Points3D) || (series instanceof Polar)
                ||(series instanceof Radar)) {

            SeriesPointer pointer;
            if (series instanceof CustomPoint)
                pointer = ((CustomPoint) series).getPointer();
            else if (series instanceof Points3D) {
                pointer = ((Points3D) series).getPointer();
                ((Points3D) series).setDepthSize(chart.getGraphics3D().sizeToDIP(4));
            }
            else if (series instanceof Polar) {
                pointer = ((Polar) series).getPointer();
            }
            else
                pointer = ((Radar) series).getPointer();

            if (series instanceof Arrow) {
                pointer.getPen().setWidth(chart.getGraphics3D().sizeToDIP(2)); //for the arrow pen
            }

            pointer.setHorizSize(chart.getGraphics3D().sizeToDIP(4)); //both for the arrow head and pointers
            pointer.setVertSize(chart.getGraphics3D().sizeToDIP(4));
        }

        if ((series instanceof Polar) || (series instanceof Radar)) {
            series.getBrush().setTransparency(50);
            series.getVertAxis().setAutomaticMinimum(false);
            series.getVertAxis().setMinimum(0);
        }

        if (seriesInstanceOf(Arrays.asList(BarJoin.class, Bezier.class, Contour.class, Error.class,
                HighLow.class, HorizLine.class, Line.class, Volume.class))) {

            ChartPen pen;
            if (series instanceof BarJoin)
                pen = ((BarJoin)series).getJoinPen();
            else if (series instanceof Bezier)
                pen = ((Bezier)series).getLinePen();
            else if (series instanceof Contour)
                pen = ((Contour)series).getPen();
            else if (series instanceof Error)
                pen = ((Error)series).getErrorPen();
            else if (series instanceof HighLow)
                pen = ((HighLow)series).getPen();
            else
                pen = ((CustomPoint)series).getLinePen();


            pen.setWidth(chart.getGraphics3D().sizeToDIP(2));
        }

        if (seriesInstanceOf(Arrays.asList(Candle.class))) {
            ((Candle)series).setCandleWidth(chart.getGraphics3D().sizeToDIP(4));
            ((Candle)series).getPen().setWidth(1);
        }

        if (getIntent().getBooleanExtra("EmptyData", false)) {
            DataDialog dialog = new DataDialog(this, series);
            dialog.setCancelable(true);
            dialog.show();
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        }
    }

    private boolean seriesInstanceOf(List classesList) {
        return classesList.contains(series.getClass());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itSettings:
                openSettings();
                return true;
            case R.id.itChangeTheme:
                changeTheme();
                return true;
            case R.id.itEditor:
                DataDialog dialog = new DataDialog(this, series);
                dialog.show();
                return true;
            case R.id.itDescription:
                getDescription(series.getClass().getSimpleName());
                return true;
            case R.id.itShare:
                shareImage();
                return true;
            case R.id.itSaveAsFile:
                saveFile();
                return true;
            case R.id.itSaveAsImage:
                saveImage();
                return true;
        }
        return false;
    }

    private void openSettings() {
        Intent intent = new Intent(this, Prefs_SetPreferenceActivity.class);
        boolean[] arrayInverted = new boolean[6];
        //[LEFT,BOTTOM, TOP, RIGHT, DEPTH, DEPTH TOP]
        arrayInverted[0] = chart.getAxes().getLeft().getInverted();
        arrayInverted[1] = chart.getAxes().getBottom().getInverted();
        arrayInverted[2] = chart.getAxes().getTop().getInverted();
        arrayInverted[3] = chart.getAxes().getRight().getInverted();
        arrayInverted[4] = chart.getAxes().getDepth().getInverted();
        arrayInverted[5] = chart.getAxes().getDepthTop().getInverted();

        boolean[] arrayVisible = new boolean[6];
        //[LEFT,BOTTOM, TOP, RIGHT, DEPTH, DEPTH TOP]
        arrayVisible[0] = chart.getAxes().getLeft().getVisible();
        arrayVisible[1] = chart.getAxes().getBottom().getVisible();
        arrayVisible[2] = chart.getAxes().getTop().getVisible();
        arrayVisible[3] = chart.getAxes().getRight().getVisible();
        arrayVisible[4] = chart.getAxes().getDepth().getVisible();
        arrayVisible[5] = chart.getAxes().getDepthTop().getVisible();

        String[] arrayTitles = new String[6];
        //[LEFT,BOTTOM, TOP, RIGHT, DEPTH, DEPTH TOP]
        arrayTitles[0] = chart.getAxes().getLeft().getTitle().getText();
        arrayTitles[1] = chart.getAxes().getBottom().getTitle().getText();
        arrayTitles[2] = chart.getAxes().getTop().getTitle().getText();
        arrayTitles[3] = chart.getAxes().getRight().getTitle().getText();
        arrayTitles[4] = chart.getAxes().getDepth().getTitle().getText();
        arrayTitles[5] = chart.getAxes().getDepthTop().getTitle().getText();

        boolean[] arrayGrid = new boolean[6];
        arrayGrid[0] = chart.getAxes().getLeft().getGrid().getVisible();
        arrayGrid[1] = chart.getAxes().getBottom().getGrid().getVisible();
        arrayGrid[2] = chart.getAxes().getTop().getGrid().getVisible();
        arrayGrid[3] = chart.getAxes().getRight().getGrid().getVisible();
        arrayGrid[4] = chart.getAxes().getDepth().getGrid().getVisible();
        arrayGrid[5] = chart.getAxes().getDepthTop().getGrid().getVisible();

        boolean enabledAxes = !(series instanceof Circular);
        boolean view3D = true;
        String substring = series.getClass().getSimpleName().length() > 2 ? series.getClass().getSimpleName().substring(series.getClass().getSimpleName().length() - 2) : series.getClass().getSimpleName();

        if (series instanceof Custom3DPalette || substring.equals("3D") || series instanceof Histogram) {
            view3D = false;
        }

        intent.putExtra("view3D", view3D);
        intent.putExtra("visibleArray", arrayVisible);
        intent.putExtra("invertedArray", arrayInverted);
        intent.putExtra("titlesArray", arrayTitles);
        intent.putExtra("enabledAxes", enabledAxes);
        intent.putExtra("gridArray", arrayGrid);
        startActivityForResult(intent, 0);
    }

    private void changeTheme() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.selectTheme);
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        View view = getLayoutInflater().inflate(R.layout.theme_dialog, null);
        final RadioGroup radioButtonGroup = (RadioGroup) view.findViewById(R.id.rgThemes);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                series.setColorEach(false);
                int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
                View radioButton = radioButtonGroup.findViewById(radioButtonID);
                ThemesList.applyTheme(chart.getChart(), radioButtonGroup.indexOfChild(radioButton));
                if (!(series instanceof CircularGauge))
                    series.setColorEach(true);

                if (radioButtonGroup.indexOfChild(radioButton) != 13)
                    series.getMarks().setMarkerTail(MarksTail.None);

                if (radioButtonGroup.indexOfChild(radioButton) == 11) {
                    series.getMarks().getBrush().setVisible(false);
                    series.getMarks().getFont().setSize(chart.getAxes().getLeft().getLabels().getFont().getSize());
                    series.getMarks().getFont().setColor(Color.black);
                }

                String substring = series.getClass().getSimpleName().length() > 2 ? series.getClass().getSimpleName().substring(series.getClass().getSimpleName().length() - 2) : series.getClass().getSimpleName();
                if (substring.equals("3D")) {
                    chart.getAspect().setView3D(true);
                }
            }
        });

        dialog.setView(view);
        dialog.show();
        createPrefs();
    }

    private void shareImage() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        Bitmap b = screenShot(findViewById(R.id.llChartView));
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                b, "Title", null);
        Uri imageUri = Uri.parse(path);
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(share, "Select"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        //super.onActivityResult(requestCode, resultCode, data);

		/*
         * To make it simple, always re-load Preference setting.
		 */
        loadPref();
    }

    private void createPrefs() {
        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mySharedPreferences.edit().putBoolean("sw3d", chart.getAspect().getView3D()).apply();

        mySharedPreferences.edit().putBoolean("swOrthogonal", chart.getAspect().getOrthogonal()).apply();

        switch (chart.getZoom().getZoomStyle().toString()) {
            case "INCHART":
                mySharedPreferences.edit().putString("lsZoom", "INCHART").apply();
                break;
            case "FULLCHART":
                mySharedPreferences.edit().putString("lsZoom", "FULLCHART").apply();
                break;
            case "CLASSIC":
                mySharedPreferences.edit().putString("lsZoom", "CLASSIC").apply();
                break;
        }

        if (chart.getLegend().getAlignment() == LegendAlignment.BOTTOM) {
            mySharedPreferences.edit().putString("lsLegendPos", "Bottom");
        } else if (chart.getLegend().getAlignment() == LegendAlignment.TOP) {
            mySharedPreferences.edit().putString("lsLegendPos", "Top");
        } else if (chart.getLegend().getAlignment() == LegendAlignment.LEFT) {
            mySharedPreferences.edit().putString("lsLegendPos", "Left");
        } else if (chart.getLegend().getAlignment() == LegendAlignment.RIGHT) {
            mySharedPreferences.edit().putString("lsLegendPos", "Right");
        }

        if (series != null) {
            mySharedPreferences.edit().putBoolean("swColorsEach", series.getColorEach()).apply();
            mySharedPreferences.edit().putBoolean("swMarks", series.getMarks().getVisible()).apply();
        }
        mySharedPreferences.edit().putString("etSerieTitle", chart.getHeader().getText()).apply();
        mySharedPreferences.edit().putBoolean("swInvertedAxes", chart.getAxes().getLeft().getInverted()).apply();
        mySharedPreferences.edit().putBoolean("swVisibleLegend", chart.getLegend().getVisible()).apply();
        mySharedPreferences.edit().putInt("prefPerspective", chart.getAspect().getPerspective()).apply();
        mySharedPreferences.edit().putInt("pref3dPercent", chart.getAspect().getChart3DPercent()).apply();

        mySharedPreferences.edit().apply();
    }

    private void loadPref() {

        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        chart.getAspect().setView3D(mySharedPreferences.getBoolean("sw3d", chart.getAspect().getView3D()));
        chart.getAspect().setOrthogonal(mySharedPreferences.getBoolean("swOrthogonal", chart.getAspect().getOrthogonal()));
        switch (mySharedPreferences.getString("lsAxes", "default")) {
            case "Left":
                chart.getAxes().getLeft().setVisible(mySharedPreferences.getBoolean("swVisibleAxes", chart.getAxes().getLeft().getVisible()));
                chart.getAxes().getLeft().getTitle().setText(mySharedPreferences.getString("EtPref", chart.getAxes().getLeft().getTitle().getText()));
                chart.getAxes().getLeft().setInverted(mySharedPreferences.getBoolean("swInvertedAxes", chart.getAxes().getLeft().getInverted()));
                chart.getAxes().getLeft().getGrid().setVisible(mySharedPreferences.getBoolean("swGrid", chart.getAxes().getLeft().getGrid().getVisible()));

                break;
            case "Right":
                chart.getAxes().getRight().setVisible(mySharedPreferences.getBoolean("swVisibleAxes", chart.getAxes().getRight().getVisible()));
                chart.getAxes().getRight().getTitle().setText(mySharedPreferences.getString("EtPref", chart.getAxes().getRight().getTitle().getText()));
                chart.getAxes().getRight().setInverted(mySharedPreferences.getBoolean("swInvertedAxes", chart.getAxes().getRight().getInverted()));
                chart.getAxes().getRight().getGrid().setVisible(mySharedPreferences.getBoolean("swGrid", chart.getAxes().getRight().getGrid().getVisible()));
                break;
            case "Bottom":
                chart.getAxes().getBottom().setVisible(mySharedPreferences.getBoolean("swVisibleAxes", chart.getAxes().getBottom().getVisible()));
                chart.getAxes().getBottom().getTitle().setText(mySharedPreferences.getString("EtPref", chart.getAxes().getBottom().getTitle().getText()));
                chart.getAxes().getBottom().setInverted(mySharedPreferences.getBoolean("swInvertedAxes", chart.getAxes().getBottom().getInverted()));
                chart.getAxes().getBottom().getGrid().setVisible(mySharedPreferences.getBoolean("swGrid", chart.getAxes().getBottom().getGrid().getVisible()));
                break;
            case "Top":
                chart.getAxes().getTop().setVisible(mySharedPreferences.getBoolean("swVisibleAxes", chart.getAxes().getTop().getVisible()));
                chart.getAxes().getTop().getTitle().setText(mySharedPreferences.getString("EtPref", chart.getAxes().getTop().getTitle().getText()));
                chart.getAxes().getTop().setInverted(mySharedPreferences.getBoolean("swInvertedAxes", chart.getAxes().getTop().getInverted()));
                chart.getAxes().getTop().getGrid().setVisible(mySharedPreferences.getBoolean("swGrid", chart.getAxes().getTop().getGrid().getVisible()));
                break;
            case "Depth":
                chart.getAxes().getDepth().setVisible(mySharedPreferences.getBoolean("swVisibleAxes", chart.getAxes().getDepth().getVisible()));
                chart.getAxes().getDepth().getTitle().setText(mySharedPreferences.getString("EtPref", chart.getAxes().getDepth().getTitle().getText()));
                chart.getAxes().getDepth().setInverted(mySharedPreferences.getBoolean("swInvertedAxes", chart.getAxes().getDepth().getInverted()));
                chart.getAxes().getDepth().getGrid().setVisible(mySharedPreferences.getBoolean("swGrid", chart.getAxes().getDepth().getGrid().getVisible()));
                break;
            case "DepthTop":
                chart.getAxes().getDepthTop().setVisible(mySharedPreferences.getBoolean("swVisibleAxes", chart.getAxes().getDepthTop().getVisible()));
                chart.getAxes().getDepthTop().getTitle().setText(mySharedPreferences.getString("EtPref", chart.getAxes().getDepthTop().getTitle().getText()));
                chart.getAxes().getDepthTop().setInverted(mySharedPreferences.getBoolean("swInvertedAxes", chart.getAxes().getDepthTop().getInverted()));
                chart.getAxes().getDepthTop().getGrid().setVisible(mySharedPreferences.getBoolean("swGrid", chart.getAxes().getDepthTop().getGrid().getVisible()));
                break;
        }

        switch (mySharedPreferences.getString("lsZoom", "default")) {
            case "INCHART":
                chart.getZoom().setZoomStyle(Zoom.ZoomStyle.INCHART);
                break;
            case "FULLCHART":
                chart.getZoom().setZoomStyle(Zoom.ZoomStyle.FULLCHART);
                break;
            case "CLASSIC":
                chart.getZoom().setZoomStyle(Zoom.ZoomStyle.CLASSIC);
                break;
        }

        String vakir = mySharedPreferences.getString("lsLegendPos", "default");
        switch (vakir) {
            case "Bottom":
                chart.getLegend().setAlignment(LegendAlignment.BOTTOM);
                break;
            case "Top":
                chart.getLegend().setAlignment(LegendAlignment.TOP);
                break;
            case "Left":
                chart.getLegend().setAlignment(LegendAlignment.LEFT);
                break;
            case "Right":
                chart.getLegend().setAlignment(LegendAlignment.RIGHT);
                break;
        }

        series.setColorEach(mySharedPreferences.getBoolean("swColorsEach", series.getColorEach()));

        chart.getHeader().setText(mySharedPreferences.getString("etSerieTitle", chart.getHeader().getText()));
        series.getMarks().setVisible(mySharedPreferences.getBoolean("swMarks", series.getMarks().getVisible()));
        chart.getLegend().setVisible(mySharedPreferences.getBoolean("swVisibleLegend", chart.getLegend().getVisible()));
        chart.getAspect().setPerspective(mySharedPreferences.getInt("prefPerspective", chart.getAspect().getPerspective()));
        if (!(series instanceof Custom3DPalette))
            chart.getAspect().setChart3DPercent(mySharedPreferences.getInt("pref3dPercent", chart.getAspect().getChart3DPercent()));
    }

    //region Menu Options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_chart_view, menu);

        if (series instanceof Custom3DPalette)
            menu.getItem(2).setVisible(false);

        if (series instanceof Calendar) {
            menu.getItem(0).setVisible(false);
            menu.getItem(2).setVisible(false);
        }

        return true;
    }

    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    private void saveImage() {
        final File imgDir = new File(Environment.getExternalStorageDirectory() + File.separator + "TeeChart" + File.separator + "Images");
        imgDir.mkdirs();
        String fname = series.getTitle() + " - " + java.util.Calendar.getInstance().getTime() + ".png";
        File file = new File(imgDir, fname);

        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            screenShot(findViewById(R.id.llChartView)).compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "Image saved", Toast.LENGTH_LONG).show();
    }

    private void saveFile() {
        checkExternalStorage();

        final File filesDir = new File(Environment.getExternalStorageDirectory() + File.separator + "TeeChart" + File.separator + "Files");
        filesDir.mkdirs();

        if (mExternalStorageAvailable) {
            if (mExternalStorageWritable) {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                final EditText edittext = new EditText(this);
                alert.setTitle("Chart file name");

                alert.setView(edittext);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String text = edittext.getText().toString();
                        if (!text.equals("")) {
                            OutputStream stream;
                            try {
                                text = checkFileExtension(text, Language
                                        .getString("TeeFilesExtension"));

                                stream = new FileOutputStream(filesDir.getAbsolutePath() + File.separator + text);

                                try {
                                    chart.getExport().getTemplate().toStream(stream);
                                    stream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();
            } else
                Toast.makeText(this, "Storage read-only. Cannot save Chart.",
                        Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Storage not available.", Toast.LENGTH_SHORT).show();

    }
    //endregion

    private void checkExternalStorage() {
        final String state = Environment.getExternalStorageState();

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                Toast.makeText(getApplicationContext(), "The application needs access to the" +
                "disk to export the chart", Toast.LENGTH_LONG).show();

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }

        if (Environment.MEDIA_MOUNTED.equals(state)
                || Environment.MEDIA_SHARED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = mExternalStorageWritable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWritable = false;
        } else {
            // Something else is wrong. It may be one of many other states, but
            // all we need
            // to know is we can neither read nor write
            mExternalStorageAvailable = mExternalStorageWritable = false;
        }
    }

    private String checkFileExtension(final String text, final String ext) {
        return hasExtension(text, ext) ? text : text + "." + ext;
    }

    private boolean hasExtension(final String file, final String ext) {
        return file.trim().toUpperCase().endsWith("." + ext.toUpperCase());
    }

    //region Descriptions
    private void getDescription(String descriptionSelected) {
        TextView description;
        final AlertDialog.Builder dialog = new AlertDialog.Builder(ChartView.this);
        View viewDialogs = getLayoutInflater().inflate(R.layout.dialog_descriptions, null);
        dialog.setTitle("Description");
        dialog.setView(viewDialogs);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
        switch (descriptionSelected.toUpperCase()) {
            case "PIE":
                dialog.setTitle("Pie Series Description");
                description = (TextView) viewDialogs.findViewById(R.id.tvDescriptionText);
                description.setText(R.string.pie_description);
                break;

            case "LINE":
                dialog.setTitle("Line Series Description");
                description = (TextView) viewDialogs.findViewById(R.id.tvDescriptionText);
                description.setText(R.string.line_description);
                break;

            case "BAR":
                dialog.setTitle("Bar Series Description");
                description = (TextView) viewDialogs.findViewById(R.id.tvDescriptionText);
                description.setText(R.string.bar_description);
                break;

            case "AREA":
                dialog.setTitle("Area Series Description");
                description = (TextView) viewDialogs.findViewById(R.id.tvDescriptionText);
                description.setText(R.string.area_description);
                break;

            case "POINT":
                dialog.setTitle("Points Series Description");
                description = (TextView) viewDialogs.findViewById(R.id.tvDescriptionText);
                description.setText(R.string.points_description);
                break;

            case "HORIZBAR":
                dialog.setTitle("Horiz Bar Series Description");
                description = (TextView) viewDialogs.findViewById(R.id.tvDescriptionText);
                description.setText(R.string.bar_description);
                break;

            case "CANDLE":
                dialog.setTitle("Candle Series Description");
                description = (TextView) viewDialogs.findViewById(R.id.tvDescriptionText);
                description.setText(R.string.candle_description);
                break;

            case "SURFACE":
                dialog.setTitle("Surface Series Description");
                description = (TextView) viewDialogs.findViewById(R.id.tvDescriptionText);
                description.setText(R.string.surface_description);
                break;

            case "TOWER":
                dialog.setTitle("Tower Series Description");
                description = (TextView) viewDialogs.findViewById(R.id.tvDescriptionText);
                description.setText(R.string.tower_description);
                break;

            case "CONTOUR":
                dialog.setTitle("Contour Series Description");
                description = (TextView) viewDialogs.findViewById(R.id.tvDescriptionText);
                description.setText(R.string.contour_description);
                break;

            case "COLORGRID":
                dialog.setTitle("ColorGrid Series Description");
                description = (TextView) viewDialogs.findViewById(R.id.tvDescriptionText);
                description.setText(R.string.colorgrid_description);
                break;

            case "POLAR":
                dialog.setTitle("Polar Series Description");
                description = (TextView) viewDialogs.findViewById(R.id.tvDescriptionText);
                description.setText(R.string.polar_description);
                break;

            case "SMITH":
                dialog.setTitle("Smith Series Description");
                description = (TextView) viewDialogs.findViewById(R.id.tvDescriptionText);
                description.setText(R.string.smith_description);
                break;

            case "RADAR":
                dialog.setTitle("Radar Series Description");
                description = (TextView) viewDialogs.findViewById(R.id.tvDescriptionText);
                description.setText(R.string.polar_description);
                break;

            case "FASTLINE":
                dialog.setTitle("Fastline Series Description");
                description = (TextView) viewDialogs.findViewById(R.id.tvDescriptionText);
                description.setText(R.string.fastline_description);
                break;

            case "ISOSURFACE":
                dialog.setTitle("ISOSurface Series Description");
                description = (TextView) viewDialogs.findViewById(R.id.tvDescriptionText);
                description.setText(R.string.isosurface_description);
                break;

            case "BUBBLE":
                dialog.setTitle("Bubble Series Description");
                description = (TextView) viewDialogs.findViewById(R.id.tvDescriptionText);
                description.setText(R.string.bubble_description);
                break;

            case "CIRCULARGAUGE":
                dialog.setTitle("Circular Gauge Series Description");
                description = (TextView) viewDialogs.findViewById(R.id.tvDescriptionText);
                description.setText(R.string.circulargauge_description);
                break;

            case "LINEARGAUGE":
                dialog.setTitle("Linear Gauge Series Description");
                description = (TextView) viewDialogs.findViewById(R.id.tvDescriptionText);
                description.setText(R.string.lineargauge_description);
                break;

            case "MAP":
                dialog.setTitle("Map Series Description");
                description = (TextView) viewDialogs.findViewById(R.id.tvDescriptionText);
                description.setText(R.string.map_description);
                break;

            case "HIGHLOW":
                dialog.setTitle("HighLow Series Description");
                description = (TextView) viewDialogs.findViewById(R.id.tvDescriptionText);
                description.setText(R.string.highlow_description);
                break;
            default:
                dialog.setTitle(descriptionSelected);
                description = (TextView) viewDialogs.findViewById(R.id.tvDescriptionText);
                description.setText("WIP");
                break;
        }
    }
    //endregion

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("chart", chart.getChart());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        chart.setChart((Chart) savedInstanceState.getSerializable("chart"));
        createPrefs();
    }
}
