package com.steema.teechart.tools;

import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.languages.Language;
import com.steema.teechart.Rectangle;

import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Image;
import com.steema.teechart.ImageMode;
import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: ChartImage</p>
 *
 * <p>Description: Chart Image tool, use it to display a picture using the
 * specified Series axes as boundaries. When the axis are zoomed or scrolled,
 * the picture will follow the new boundaries.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ChartImage extends ToolSeries {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image;
    private ImageMode imageMode = ImageMode.STRETCH;

    public ChartImage() {
        this(null);
    }

    public ChartImage(IBaseChart c) {
        super(c);
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("ImageTool");
    }

    /**
     * Gets detailed descriptive text.
     *
     * @return String
     */
    public String getSummary() {
        return Language.getString("ImageToolSummary");
    }

    /**
     * Loads an image from a specified file path. <br>
     * Default value: null
     *
     * @return Image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Loads an image from a specified file path. <br>
     * Default value: null
     *
     * @param value Image
     */
    public void setImage(Image value) {
        image = value;
        invalidate();
    }

    /**
     * Controls how the image is displayed.<br>
     * Default value: ImageMode.Stretch
     *
     * @return ImageMode
     */
    public ImageMode getImageMode() {
        return imageMode;
    }

    /**
     * Controls how the image is displayed.<br>
     * Default value: ImageMode.Stretch
     *
     * @param value ImageMode
     */
    public void setImageMode(ImageMode value) {
        if (imageMode != value) {
            imageMode = value;
            invalidate();
        }
    }

    public void chartEvent(ChartDrawEvent ce) {
        super.chartEvent(ce);

        if (((ce.getID()==ChartDrawEvent.PAINTING) &&
             (ce.getDrawPart()==ChartDrawEvent.SERIES)) &&
             (image != null)) {

            Rectangle r = new Rectangle();

            if (getSeries() != null) {
                r.x = getSeries().calcXPosValue(getSeries().getMinXValue());
                r.width = getSeries().calcXPosValue(getSeries().getMaxXValue()) -
                          r.x;
                r.y = getSeries().calcYPosValue(getSeries().getMaxYValue());
                r.height = getSeries().calcYPosValue(getSeries().getMinYValue()) -
                           r.y;
            } else {
                r.x = getHorizAxis().calcPosValue(getHorizAxis().getMinimum());
                r.width = getHorizAxis().calcPosValue(getHorizAxis().getMaximum()) -
                          r.x;

                r.y = getVertAxis().calcYPosValue(getVertAxis().getMaximum());
                r.height = getVertAxis().calcYPosValue(getVertAxis().getMinimum()) -
                           r.y;
            }

            IGraphics3D g = chart.getGraphics3D();
            if (chart.canClip()) {
                g.clipCube(chart.getChartRect(), 0, chart.getAspect().getWidth3D());
            }

            g.draw(g.calcRect3D(r, 0), image, imageMode, false);
            g.unClip();
        }
    }
}

