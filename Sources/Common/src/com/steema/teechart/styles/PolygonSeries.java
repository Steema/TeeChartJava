package com.steema.teechart.styles;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.Rectangle;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.ChartBrush;

import com.steema.teechart.drawing.IGraphics3D;

    /**
     *
     * <p>Title: TeeChart for Java</p>
     *
     * <p>Description: Polygon Series is a descendant of {@link Series}.
     * Use Polygon Series to define the shape of the Polygons to be drawn by
     * the Map Series. A Polygon Series contains the co-ordinates of the Points
     * which define the boundary of a Polygon.<br>PolygonSeries stores the point
     * Valuelists and individual characteristics of each Map Series 'sub'
     * Series (each Polygon is a Chart Series).<br>
     * Map Series hides the need to treat each Polygon as a Series by treating
     * the Polygons as an internal collection of Series.</p>
     *
     * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights
     * Reserved.</p>
     *
     * <p>Company: Steema Software SL</p>
     *
     */
    final public class PolygonSeries extends Series {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private ChartPen pen;
        private Polygon iPolygon;

        public PolygonSeries(IBaseChart c) {
            super(c);
        }

        public PolygonSeries() {
            this((IBaseChart)null);
        }
        
        public void assign(Series source) {
            if (source instanceof PolygonSeries) {
                PolygonSeries tmp = (PolygonSeries)source;
                tmp.setPolygon(((PolygonSeries)source).getPolygon());
                tmp.setColor(((PolygonSeries) source).getColor());   
            }
            super.assign(source);
        }        

        protected void drawLegendShape(IGraphics3D g,
                                       int valueIndex,
                                       Rectangle rect) {
            super.drawLegendShape(g, valueIndex, rect);
        }


        //TODO !!
        protected void prepareLegendCanvas(IGraphics3D g,
                                           int valueIndex,
                                           Color backColor,
                                           ChartBrush
                                           aBrush) {
            super.prepareLegendCanvas(g, valueIndex, backColor, aBrush);
            getPolygon().parentSeries.doBeforeDrawChart();

        }

        public void setActive(boolean value) {
            super.setActive(value);
            getPolygon().parentSeries.repaint();
        }
        
        public String toString()
        {
           if(iPolygon.getText() == null || iPolygon.getText().equals("")) {
              return Integer.toString(getPolygon().getIndex()); 
           }
           else if (!iPolygon.getText().equals("")) {
              return iPolygon.getText();
           }
           else {
              return super.toString(); 
           }
        }


        public void setColor(Color value) {
           super.setColor(value);

           // if (chart==null) {
           //    getPolygon().getParentSeries(getPolygon().getIndex()).setColor(value);
           // }
        }


        protected void addSampleValues(int numValues) {
            // super.addSampleValues(numValues);
        }

        public ChartPen getPen() {
            if (pen == null) {
                pen = new ChartPen(Color.BLACK);
            }
            return pen;
        }

        public Polygon getPolygon() {
            return iPolygon;
        }
        
        public void setPolygon(Polygon value) {
            iPolygon = value;
        }
    }