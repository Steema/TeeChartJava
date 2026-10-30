package com.steema.teechart.editors;

import com.steema.teechart.languages.Language;
import com.steema.teechart.misc.StringFormat;
//import com.steema.teechart.editors.functions.*;
//import com.steema.teechart.editors.tools.*;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.SWT;

import java.awt.Toolkit;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.Image;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Utils {

    private Utils() {}

    final static public Class[] toolsEditorsOf = { //TODO mm
                                                 /*AnnotationEditor.class,
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
                                                 AnnotationEditor.class,*/
                                                 /*
                                                 AxisToolEdit.class,
                                                 HotSpotEditor.class,
                                                 LightToolEditor.class,
                                                 ScrollBarEditor.class,
                                                 SurfaceNearestToolEditor.class,
                                                 ToolSeriesEditor.class,
                                                 ZoomToolEditor.class*/
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
    	null, //SmoothingFunctionEditor.class,
    	null, //CustomFunctionEditor.class,
    	null, //RootMeanSquare.class,
    	null, //StdDeviation.class,
    	null, //Stochastic.class,
    	null, //ExpMovAverage.class,
    	null, //Performance.class,
    	null, //CrossPoints.class,
    	null, //CompressOHLCFunctionEditor.class,
    	null, //CLVFunctionEditor.class,
    	null, //OBVFunctionEditor.class,
    	null, //CCIFunctionEditor.class,
    	null, //MovingAverageFunctionEditor.class,
    	null, //PVOFunctionEditor.class,
    	null, //DownSampling.class,
    	null, //Trend.class,
    	null, //Correlation.class,
    	null, //Variance.class,
    	null, //Perimeter.class,
    	null, //CurveFitting.class
    	null, //ADXFunctionEditor.class, // ADXFunction
    	null, //BollingerFunctionEditor.class, // Bollinger
    	null, //MACDFunctionEditor.class, // MACDFunctionEditor
    	null, // SAR
    	null // RSI
    };
    
    final static public Class[] swingfunctionEditorsOf = { 
//TODO mm
    	/*            null,
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
            null // RSI  */
    };

    //full path to avoid classname clashes (eg ShapeEditor)
    final static public Class[] seriesEditorsOf = {
    	//TODO MM
            /*com.steema.teechart.editors.series.LineEditor.class,
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
            com.steema.teechart.editors.series.IsoSurfaceEditor.class*/
    };

    /**
     * Shows a message box dialog asking confirmation.
     *
     * @param message an additional custom text to display at dialog.
     * @return boolean true if user selects "Yes"
     */
    static public boolean yesNo(String message) {
    	
    	Display d = Display.getDefault(); //new Display();
    	Shell s = new Shell(d);
    	s.setSize(400, 400);
    	s.setText(message);
    	MessageBox messageBox = new MessageBox(s, SWT.ICON_QUESTION
    			| SWT.YES | SWT.NO);
    	messageBox.setMessage(message);
    	messageBox.setText("TeeChart for Java");
    	int response = messageBox.open();
    	if (response == SWT.YES)
    		return true;
    	else
    		return false;
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
    

    //TODO MM
    /*public static void addCursorsToCombo(JComboBox cbCursor) {
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
    }*/

    public static Image makeTransparent(Image image, final java.awt.Color color) {

        ImageFilter filter = new RGBImageFilter() {

            private int transp = color.getRGB() | 0xFF000000;

            public final int filterRGB(int x, int y, int rgb) {
                return ((rgb | 0xFF000000) == transp) ? 0x00FFFFFF & rgb: rgb;
            }
        };

        ImageProducer producer = new FilteredImageSource(image.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(producer);
    }
}

