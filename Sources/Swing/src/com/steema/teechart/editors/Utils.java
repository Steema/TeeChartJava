package com.steema.teechart.editors;

import com.steema.teechart.languages.Language;
import com.steema.teechart.editors.functions.*;
import com.steema.teechart.editors.tools.*;
import com.steema.teechart.misc.StringFormat;

import java.awt.Toolkit;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.Image;
import java.awt.image.FilteredImageSource;

import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.image.RGBImageFilter;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Utils {

    private Utils() {}

    final static public Class[] toolsEditorsOf = {
        AnnotationEditor.class,
        RotateEditor.class,
        ImageToolEditor.class,
        CursorEditor.class,
        DragMarksEditor.class,
        AxisArrowEditor.class,
        ColorLineEditor.class,
        ColorBandEditor.class,
        DrawLineEditor.class,
        NearestPointEditor.class,
        MarksTipEditor.class,
        ExtraLegendEditor.class,
        DragPointEditor.class,
        SeriesAnimationEditor.class,
        PieToolEditor.class,
        GridTransposeEditor.class,
        GanttToolEditor.class,
        GridBandEditor.class,
        AnnotationEditor.class,
        SeriesBandEditor.class,
        /*
         AxisToolEdit.class,
         HotSpotEditor.class,
         LightToolEditor.class,
         ScrollBarEditor.class,
         SurfaceNearestToolEditor.class,
         ToolSeriesEditor.class,
         ZoomToolEditor.class*/
        AxisBreaksEditor.class,
        SubChartToolEditor.class,
        ScrollPagerEditor.class,
        SeriesRegionEditor.class
    };

    final static public Class[] functionEditorsOf = {
        null,
        null,
        null,
        null,
        null,
        null,
        null, //Average.class,
        null, //Count.class,
        null, //Momentum.class,
        null, //MomentumDivision.class,
        null, //Cumulative.class,
        null, //ExpAverage.class,
        SmoothingFunctionEditor.class,
        CustomFunctionEditor.class,
        null, //RootMeanSquare.class,
        null, //StdDeviation.class,
        null, //Stochastic.class,
        null, //ExpMovAverage.class,
        null, //Performance.class,
        null, //CrossPoints.class,
        CompressOHLCFunctionEditor.class,
        CLVFunctionEditor.class,
        OBVFunctionEditor.class,
        CCIFunctionEditor.class,
        MovingAverageFunctionEditor.class,
        PVOFunctionEditor.class,
        null, //DownSampling.class,
        null, //Trend.class,
        null, //Correlation.class,
        null, //Variance.class,
        null, //Perimeter.class,
        null, //CurveFitting.class
        ADXFunctionEditor.class, // ADXFunction
        BollingerFunctionEditor.class, // Bollinger
        MACDFunctionEditor.class, // MACDFunctionEditor
        null, // SAR
        null, // RSI
        null //HistogramFunction.class
    };

    //full path to avoid classname clashes (eg ShapeEditor)
    final static public Class[] seriesEditorsOf = {
        com.steema.teechart.editors.series.LineEditor.class,
        com.steema.teechart.editors.series.PointsEditor.class,
        com.steema.teechart.editors.series.AreaEditor.class,
        com.steema.teechart.editors.series.FastLineEditor.class,
        com.steema.teechart.editors.series.LineEditor.class,
        com.steema.teechart.editors.series.BarEditor.class,
        com.steema.teechart.editors.series.BarEditor.class,
        com.steema.teechart.editors.series.PieEditor.class,
        com.steema.teechart.editors.series.ShapeEditor.class,
        com.steema.teechart.editors.series.ArrowEditor.class,
        com.steema.teechart.editors.series.PointsEditor.class, //Bubble
        com.steema.teechart.editors.series.GanttEditor.class,
        com.steema.teechart.editors.series.CandleEditor.class,
        com.steema.teechart.editors.series.DonutEditor.class,
        com.steema.teechart.editors.series.VolumeEditor.class,
        com.steema.teechart.editors.series.BarEditor.class, //Bar3D
        com.steema.teechart.editors.series.Point3DEditor.class, //Points3D
        com.steema.teechart.editors.series.PolarEditor.class,
        com.steema.teechart.editors.series.PolarEditor.class, //Radar
        com.steema.teechart.editors.series.ClockEditor.class,
        com.steema.teechart.editors.series.PolarEditor.class, //WindRose
        com.steema.teechart.editors.series.PyramidEditor.class,
        com.steema.teechart.editors.series.SurfaceEditor.class,
        com.steema.teechart.editors.series.PointsEditor.class, //LinePoint
        com.steema.teechart.editors.series.BarEditor.class, //BarJoin
        com.steema.teechart.editors.series.ColorGridEditor.class,
        com.steema.teechart.editors.series.WaterFallEditor.class,
        com.steema.teechart.editors.series.HistoEditor.class,
        com.steema.teechart.editors.series.ErrBarEditor.class, //Error
        com.steema.teechart.editors.series.ErrBarEditor.class,
        com.steema.teechart.editors.series.ContourEditor.class,
        com.steema.teechart.editors.series.SmithEditor.class,
        com.steema.teechart.editors.series.CalendarEditor.class,
        com.steema.teechart.editors.series.HighLowEditor.class,
        com.steema.teechart.editors.series.TriSurfaceEditor.class,
        com.steema.teechart.editors.series.FunnelEditor.class,
        com.steema.teechart.editors.series.BoxPlotEditor.class,
        com.steema.teechart.editors.series.BoxPlotEditor.class, //HorizBox
        com.steema.teechart.editors.series.AreaEditor.class,
        com.steema.teechart.editors.series.TowerEditor.class,
        com.steema.teechart.editors.series.PointFigureEditor.class,
        com.steema.teechart.editors.series.GaugesEditor.class,
        com.steema.teechart.editors.series.Vector3DEditor.class,
        com.steema.teechart.editors.series.MapEditor.class,
        com.steema.teechart.editors.series.BezierEditor.class,
        com.steema.teechart.editors.series.ImageEditor.class,
        com.steema.teechart.editors.series.IsoSurfaceEditor.class,
        com.steema.teechart.editors.series.CircularGaugeEditor.class,
        com.steema.teechart.editors.series.LinearGaugeEditor.class,
        com.steema.teechart.editors.series.LinearGaugeEditor.class, //VerticalLinearGauge
        com.steema.teechart.editors.series.LinearGaugeEditor.class, //Missing NumericGauge
        com.steema.teechart.editors.series.KnobGaugeEditor.class,
        com.steema.teechart.editors.series.HistoEditor.class, //HorizHistogram
        com.steema.teechart.editors.series.MapEditor.class, //World
        com.steema.teechart.editors.series.ErrorPointEditor.class,
        com.steema.teechart.editors.series.ErrorPoint3DEditor.class,
        com.steema.teechart.editors.series.BubbleCloudEditor.class,
        com.steema.teechart.editors.series.ViolinEditor.class,
        //com.steema.teechart.editors.series.KDEEditor.class,
        com.steema.teechart.editors.series.HorizViolinEditor.class,
        com.steema.teechart.editors.series.BeeSwarmEditor.class,
        com.steema.teechart.editors.series.HorizBeeSwarmEditor.class
    };

    /**
     * Shows a message box dialog asking confirmation.
     *
     * @param message an additional custom text to display at dialog.
     * @return boolean true if user selects "Yes"
     */
    static public boolean yesNo(String message) {
        return JOptionPane.showConfirmDialog(null, message, Language.getString("Confirm"),
                JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION;
    }

    /**
     * Shows a dialog asking confirmation with Yes and No options.
     *
     * @param s an additional custom text to show at dialog
     * @return boolean true if user selects "Yes"
     */
    static public boolean yesNoDelete(String s) {
        return yesNo(StringFormat.format(Language.getString("SureToDelete"), s));
    }

    public static void addCursorsToCombo(JComboBox cbCursor) {
        cbCursor.addItem(Language.getString("Default"));
        cbCursor.addItem(Language.getString("Crosshair"));
        cbCursor.addItem(Language.getString("Text"));
        cbCursor.addItem(Language.getString("Wait"));
        cbCursor.addItem(Language.getString("Southwest_Resize"));
        cbCursor.addItem(Language.getString("Southeast_Resize"));
        cbCursor.addItem(Language.getString("Northwest_Resize"));
        cbCursor.addItem(Language.getString("Northeast_Resize"));
        cbCursor.addItem(Language.getString("North_Resize"));
        cbCursor.addItem(Language.getString("South_Resize"));
        cbCursor.addItem(Language.getString("West_Resize"));
        cbCursor.addItem(Language.getString("East_Resize"));
        cbCursor.addItem(Language.getString("Hand"));
        cbCursor.addItem(Language.getString("Move"));
    }

    public static Image makeTransparent(Image image, final java.awt.Color color) {

        ImageFilter filter = new RGBImageFilter() {

            private int transp = color.getRGB() | 0xFF000000;

            public final int filterRGB(int x, int y, int rgb) {
                return ((rgb | 0xFF000000) == transp) ? 0x00FFFFFF & rgb : rgb;
            }
        };

        ImageProducer producer = new FilteredImageSource(image.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(producer);
    }
}
