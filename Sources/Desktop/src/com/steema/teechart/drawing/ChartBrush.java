package com.steema.teechart.drawing;

import java.awt.TexturePaint;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.ImageMode;
import com.steema.teechart.Rectangle;
import com.steema.teechart.TeeBase;
import com.steema.teechart.misc.ImageUtils;

/**
 *
 * <p>Title: ChartBrush class</p>
 *
 * <p>Description: Common Chart Brush (pattern) used to fill polygons and
 * rectangles.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ChartBrush extends TeeBase {
	
	private static final long serialVersionUID = 20131209L;

    protected boolean visible = true;
    protected Color color = Color.WHITE;
    transient protected Color defaultColor;
    transient protected boolean defaultVisible;

    private Color foregroundColor = Color.SILVER;
    private Gradient gradient;

    transient private Image image; // see writeObject

    private ImageMode imageMode = ImageMode.STRETCH;
    private boolean imageTransparent;
    private boolean solid = true;
    private HatchStyle style = HatchStyle.BACKWARDDIAGONAL;
    private boolean wrapTile = true;

    transient private Image hatchImage; // see writeObject

    public ChartBrush(IBaseChart c, boolean startVisible) {
        this(c, Color.WHITE, startVisible);
    }

    public ChartBrush(IBaseChart c) {
        this(c, Color.WHITE, true);
    }

    public ChartBrush(IBaseChart c, Color aColor) {
        this(c, aColor, true);
    }

    public ChartBrush(IBaseChart c, Color aColor, boolean startVisible) {
        super(c);

        color = aColor;
        defaultColor = color;
        visible = startVisible;
        defaultVisible = visible;
    }

    public void reset() {
        setColor(Color.WHITE);
        setSolid(true);
        setTransparency(0);
        setForegroundColor(Color.SILVER);
        setStyle(HatchStyle.BACKWARDDIAGONAL);
    }

    public TexturePaint getHatchTexture() {

        hatchImage = style.getImage(getChart().getParent().getControl());

        Rectangle r = new Rectangle(0, 0, hatchImage.getWidth(null),
                                    hatchImage.getHeight(null));

        return new TexturePaint(ImageUtils.bufferedFrom(hatchImage), r);
    }

    public TexturePaint getTexture(Rectangle rect) {
        Rectangle tileRect = rect;
        if (imageMode != ImageMode.STRETCH) {
            tileRect = getImageRect();
        }
        return new TexturePaint(ImageUtils.bufferedFrom(image), tileRect);
    }

    public void assign(ChartBrush b) {
        foregroundColor = b.foregroundColor;
        color = b.color;
        visible = b.visible;
        style = b.style;
        image = b.image;
        imageTransparent = b.imageTransparent;
        solid = b.solid;
        wrapTile = b.wrapTile;
        imageMode = b.imageMode;

        //CDI chart cannot be null
        if(chart == null) {
            chart = b.chart;
        }

        // chart = b.chart;  <-- problem, when assigning brush from another chart

        if (b.gradient != null) {
            getGradient().assign(b.gradient);
        } else {
            gradient = null;
        }

        setNullHandle();
    }

    private void setNullHandle() {
        if (chart != null) {
            chart.doChangedBrush(this);
        }
    }

    public void setDefaultColor(Color value) {
        defaultColor = value;
        color = value;
    }

    public void setChart(IBaseChart c) {
        super.setChart(c);
        if (gradient != null) {
            gradient.setChart(chart);
        }
    }

    /**
     * The Transparency level from 0 to 100%.<br>
     * Transparency is a value between 0 and 100 which sets the transparency
     * percentage with respect to foreground versus background.<br>
     * Default value: 0

     * @return int
     */
    public int getTransparency() {
        return Color.transparencyOf(color);
    }

    /**
     * Sets Transparency level from 0 to 100%.<br>
     * Default value: 0
     *
     * @param value int
     */
    public void setTransparency(int value) {
    	color = Graphics3D.transparentColor(value, color);

      if (gradient != null)
            gradient.setTransparency(value);
//
//        if (color != Color.EMPTY) {
//            setColor(color.transparentColor(value));
//        }
//
//        if (gradient != null) {
//            gradient.setTransparency(value);
//        }        
    }

    /**
     * Fills using Color only. Does not use Foreground color.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getSolid() {
        return solid;
    }

    /**
     * Fills using Color only. Does not use Foreground color.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setSolid(boolean value) {
        solid = setBooleanProperty(solid, value);
    }

    /**
     * The Brush image will be Transparent when true.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getImageTransparent() {
        return imageTransparent;
    }

    /**
     * Sets the Brush image to Transparent.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setImageTransparent(boolean value) {
        imageTransparent = setBooleanProperty(imageTransparent, value);
    }

    public boolean getGradientVisible() {
        return (gradient != null) && gradient.visible;
    }

    /**
     * Fill Gradient.<br>
     * Gradient specifies the colors used to fill a zone.
     * The zone is filled using these three colors: StartColor, MidColor and
     * EndColor. You can control the drawing output by setting
     * Gradient.Direction.<br>
     * Use the Visible property to show / hide filling. <br>
     * Default value: null
     *
     * @return Gradient
     */
    public Gradient getGradient() {
        if (gradient == null) {
            gradient = new Gradient(chart);
            //CDI --
            //gradient.Transparency=Transparency;
        }
        return gradient;
    }

    /**
     * Sets the Gradient fill.<br>
     * Default value: null
     *
     * @param value Gradient
     */
    public void setGradient(Gradient value) {
        if (value == null) {
            gradient = null;
        } else {
            getGradient().assign(value);
        }
    }

    /**
     * Use invalidate when the entire canvas needs to be repainted.<br>
     * When more than one region within the canvas needs repainting, Invalidate
     * will cause the entire window to be repainted in a single pass, avoiding
     * flicker caused by redundant repaints. There is no performance penalty
     * for calling Invalidate multiple times before the control is actually
     * repainted.
     */
    public void invalidate() {
        setNullHandle();
        super.invalidate();
    }

    /*
        public Brush getDrawingBrush() {
            if (handle == null) {
                if ((image != null) && (imageMode == ImageMode.Tile)) {
                    handle = new TextureBrush(image, wrapMode);
                } else
                if (solid) {
                    handle = new SolidBrush(color);
                } else {
                    handle = new HatchBrush(style, foregroundColor, color);
                }
            }
            return handle;
        }
     */

    /**
     * Drawing Brush Image Style.<br>
     * Default value: Stretch
     *
     * @return ImageMode
     */
    public ImageMode getImageMode() {
        return imageMode;
    }

    /**
     * Drawing Brush Image Style.<br>
     * Default value: Stretch
     *
     * @param value ImageMode
     */
    public void setImageMode(ImageMode value) {
        if (imageMode != value) {
            imageMode = value;
            invalidate();
        }
    }

    public Rectangle getImageRect() {
        if (image !=  null) {
            return new Rectangle(0,0, image.getWidth(null), image.getHeight(null));
        } else {
            return null;
        }
    }

    /**
     * Drawing Brush Image Style.<br>
     * Default value: Tile
     *
     * @return boolean
     */
    public boolean getWrapTile() {
        return wrapTile;
    }

    /**
     * Drawing Brush Image Style.<br>
     * Default value: Tile
     *
     * @param value boolean
     */
    public void setWrapTile(boolean value) {
        if (wrapTile != value) {
            wrapTile = value;
            invalidate();
        }
    }

    /**
     * Color to fill inner portions of Brush, when Solid is false.<br>
     * Default value: SILVER
     *
     *
     * @return Color
     */
    public Color getForegroundColor() {
        return foregroundColor;
    }

    /**
     * Specifies the Color to fill inner portions of Brush, when Solid is false.
     * <br>
     * Default value: SILVER
     *
     *
     * @param value Color
     */
    public void setForegroundColor(Color value) {
        foregroundColor = setColorProperty(foregroundColor, value);
    }

    protected void applyDark(Color c, int quantity) {
        color = c;
        applyDark(quantity);
    }

    public void applyDark(int quantity) {
        color = color.applyDark(quantity);
        setNullHandle();
    }

    /**
     * Determines the color used to fill a zone.
     *
     * @return Color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Specifies the color used to fill a zone.
     *
     * @param value Color
     */
    public void setColor(Color value) {
        color = setColorProperty(color, value);
    }

    public void setColor(java.awt.Color value) { //@TODO Tempory
        setColor(new Color(value));
    }

    /**
     * Determines if the brush will draw lines or not.
     *
     * @return boolean
     */
    public boolean getVisible() {
        return visible;
    }

    /**
     * Determines if the brush will draw lines or not.
     *
     * @param value boolean
     */
    public void setVisible(boolean value) {
        visible = setBooleanProperty(visible, value);
    }

    public void setDefaultVisible(boolean value) {
        defaultVisible = value;
        visible = value;
    }

    /**
     * Determines the style in which the zone is filled or patterned using both
     * Color and ForegroundColor.<br>
     * Default value: HatchStyle.BackwardDiagonal
     *
     * @return HatchStyle
     */
    public HatchStyle getStyle() {
        return style;
    }

    /**
     * Determines the style in which the zone is filled or patterned using both
     * Color and ForegroundColor.<br>
     * Default value: HatchStyle.BackwardDiagonal
     *
     * @param value HatchStyle
     */
    public void setStyle(HatchStyle value) {
        if (style != value) {
            style = value;
            solid = false;
            hatchImage = null;
            invalidate();
        }
    }

    /**
     * Image to use for fill.<br>
     * Default value: null
     *
     * @return Image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Image to use for fill.<br>
     * Default value: null
     *
     * @param image2 Image
     */
    public void setImage(Image i) {
        image = i;
        solid = image == null;
        invalidate();
    }

    /**
     * Loads a bitmap into a ChartBrush element from the specified path.
     *
     * @param fileName String
     */
    public void loadImage(String fileName) {
        clearImage();
        setImage(ImageUtils.toImage(ImageUtils.getImage(fileName,getChart().getParent().getControl())));
    }

    /**
     * Loads a bitmap into a ChartBrush element from the specified URL.
     *
     * @param location URL
     */
    public void loadImage(URL location) {
        clearImage();
        setImage(ImageUtils.toImage(ImageUtils.getImage(location,getChart().getParent().getControl())));
    }
    
    /**
     * Loads a bitmap into a ChartBrush element from the specified stream.
     *
     * @param stream InputStream
     * @throws IOException 
     */
    public void loadImage(InputStream stream) {
        clearImage();

        if (stream != null) {
        	setImage(ImageUtils.getImage(stream,chart.getParent().getControl()));
        }
    }    

    /**
     * Clears the ChartBrush element from all associated bitmap images.
     */
    public void clearImage() {
    	//todo swt dispose
        setImage(null);
    }

    private void writeObject(ObjectOutputStream stream) throws java.io.
            IOException {
        stream.defaultWriteObject();

        ImageUtils.writeImage(stream, image);
        ImageUtils.writeImage(stream, hatchImage);
    }

    private void readObject(ObjectInputStream stream) throws java.io.
            IOException, ClassNotFoundException {
        stream.defaultReadObject();

        Image tmp = ImageUtils.readImage(stream, null);

        if (tmp != null) {
            image = tmp;
        }

        Image tmp2 = ImageUtils.readImage(stream, null);

        if (tmp2 != null) {
            hatchImage = tmp2;
        }
    }
}

