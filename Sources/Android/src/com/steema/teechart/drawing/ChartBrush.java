package com.steema.teechart.drawing;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

import android.graphics.BitmapShader;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.ImageMode;
import com.steema.teechart.PointDouble;
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
public final class ChartBrush extends TeeBase {

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
	transient private Paint paint;

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

    /*
    private BufferedImage bufferedFrom(Image image) {
        return chart.getGraphics3D().bufferedFrom(image, getForegroundColor());
    }
    */

    /*
    public TexturePaint getHatchTexture() {

        hatchImage = bufferedFrom(style.getImage((Component)(this.getChart().getParent())));

        Rectangle r = new Rectangle(0, 0, hatchImage.getWidth(null),
                                    hatchImage.getHeight(null));

        return new TexturePaint((BufferedImage) hatchImage, r);
    }

    public TexturePaint getTexture(Rectangle rect) {
        Rectangle tileRect = rect;
        if (this.imageMode != ImageMode.STRETCH) {
            tileRect = this.getImageRect();
        }
        return new TexturePaint(bufferedFrom(image), tileRect);
    }
   */
    
    public void assign(final ChartBrush b) {
        foregroundColor = b.foregroundColor;
        color = b.color;
        visible = b.visible;
        style = b.style;
        image = b.image;
        imageTransparent = b.imageTransparent;
        solid = b.solid;
        wrapTile = b.wrapTile;
        imageMode = b.imageMode;

        //DB Chart cannot be assigned here, should be done by caller.
        /*
        //CDI chart cannot be null
        if(chart == null) {
            chart = b.chart;
        }
		*/
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
    public void setGradient(final Gradient value) {
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
    public void setForegroundColor(final Color value) {
        foregroundColor = setColorProperty(foregroundColor, value);
    }

    protected void applyDark(final Color aColor, int quantity) {
        color = aColor;
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
    public void setColor(final Color value) {
        color = setColorProperty(color, value);
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
     * @param value Image
     */
    public void setImage(Image value) {
        image = value;
        solid = image == null;
        invalidate();
    }

    /**
     * Loads a bitmap into a ChartBrush element from the specified path.
     *
     * @param fileName String
     */
    public void loadImage(final String fileName) {
        clearImage();
        //setImage(Toolkit.getDefaultToolkit().createImage(fileName)); //problem, ie not always working
        //setImage(ImageUtils.getImage(fileName, (Component)(this.getChart().getParent())));
        setImage(ImageUtils.getImage(fileName));
    }

    /**
     * Loads a bitmap into a ChartBrush element from the specified URL.
     *
     * @param location URL
     */
    public void loadImage(final URL location) {
        clearImage();
        //setImage(Toolkit.getDefaultToolkit().createImage(location)); //problem, ie not always working
        //setImage(ImageUtils.getImage(location, (Component)(this.getChart().getParent())));
        setImage(ImageUtils.getImage(location.getFile()));
    }
    
    /**
     * Loads a bitmap into a ChartBrush element from the specified stream.
     *
     * @param stream InputStream
     */
    public void loadImage(InputStream stream) {
        clearImage();
        //setImage(Toolkit.getDefaultToolkit().createImage(location)); //problem, ie not always working
        //setImage(ImageUtils.getImage(location, (Component)(this.getChart().getParent())));
        if (stream != null) {
        	setImage(ImageUtils.getImage(stream));
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

        Image tmp = ImageUtils.getImage(stream);

        if (tmp != null) {
            image = tmp;
        }

        Image tmp2 = ImageUtils.getImage(stream);

        if (tmp2 != null) {
            hatchImage = tmp2;
        }
    }

    private final Rectangle zeroRect=new Rectangle(0,0,0,0);
    
	public Paint getPaint() {
		return getPaint(zeroRect);
	}

	public Paint getPaint(final Rectangle rect) {
		if (getGradientVisible()) 
			return preparePaint(getGradientShader(rect));
		else
		if ((image != null) && (image.bitmap != null))
			return preparePaint(new BitmapShader(image.bitmap, TileMode.MIRROR, TileMode.MIRROR));
		else
			return preparePaint(null);
	}

	private Paint preparePaint(final Shader shader) {
		if (paint==null) {
			paint=new Paint(Paint.ANTI_ALIAS_FLAG);
			paint.setFilterBitmap(true);
			paint.setAntiAlias(true);
		}
		
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(color.getRGB());
		//paint.setAlpha(255-internalTransparency);  not necessary
		
		paint.setShader(shader);
		paint.setDither(shader != null);
	    
		return paint;
	}

	public Paint getPaint(final Point[] p) {
		if (getGradientVisible()) 
			return preparePaint(getGradientShader(polygonBounds(p)));
		else
			return preparePaint(null);
	}

	private Shader getGradientShader(final Rectangle rectangle) {
		Shader g;
		
		final GradientDirection dir = gradient.getDirection();
		
		final int[] colors;
		
		if (gradient.getUseMiddle()) {
			colors = new int[3];
			colors[0] = gradient.getStartColor().getRGB();
			colors[1] = gradient.getMiddleColor().getRGB();
			colors[2] = gradient.getEndColor().getRGB();		
		}
		else {
			colors = new int[2];
			colors[0] = gradient.getStartColor().getRGB();
			colors[1] = gradient.getEndColor().getRGB();		
		}

		if (dir == GradientDirection.HORIZONTAL) { 
				g = new LinearGradient(rectangle.x, 0, rectangle.getRight(), 0,
						colors, null,
						Shader.TileMode.CLAMP);
		}
		else
		if (dir == GradientDirection.VERTICAL) { 
				g = new LinearGradient(0, rectangle.y, 0, rectangle.getBottom(),
						colors, null,
						Shader.TileMode.CLAMP);
		}
		else
		if (dir == GradientDirection.RADIAL) { 
				final float radius = (float) Math.hypot(rectangle.width, rectangle.height);
				
				final Point center = rectangle.center();
				
				g = new RadialGradient(gradient.getRadialX() + center.x, 
							gradient.getRadialY() + center.y, radius, colors, null,
						Shader.TileMode.CLAMP);

		}
		else
		if (dir == GradientDirection.BACKDIAGONAL) 
			g = new LinearGradient(rectangle.x, 0, rectangle.getRight(), rectangle.getBottom(),
					colors, null,
				Shader.TileMode.CLAMP);
			else
				g = new LinearGradient(0, rectangle.getBottom(), rectangle.getRight(), rectangle.y,
						colors, null,
					Shader.TileMode.CLAMP);
		
		final double angle = gradient.getAngle();
		if (angle != 0) {
			Matrix m = new Matrix();
			m.setRotate((float) angle);
			g.setLocalMatrix(m);
		}
		
		return g;
	}

    transient private final Rectangle polyBounds=new Rectangle(0,0,0,0);
    
	private Rectangle polygonBounds(final Point[] p) {
		polyBounds.width = 0;
		polyBounds.height = 0;

		final int l = p.length;
		
		if (l==0) {
			polyBounds.x = 0;
			polyBounds.y = 0;
		} else {
		    polyBounds.x = p[0].x;
		    polyBounds.y = p[0].y;
		    
		    for (int t = 1; t<l; t++) {
		    	final int ppx = p[t].x;
		    	final int ppy = p[t].y;
		    	
		    	if (ppx < polyBounds.x)
		    		polyBounds.x = ppx;
		    	else
		    	if (ppx > polyBounds.getRight())
		    		polyBounds.setRight(ppx);
		    	
		    	if (ppy < polyBounds.y)
		    		polyBounds.y = ppy;
		    	else
		    	if (ppy > polyBounds.getBottom())
		    		polyBounds.setBottom(ppy);
		    	
		    }
		}
		
		return polyBounds;
	}

	private Rectangle polygonBounds(final PointDouble[] p) {
		polyBounds.width = 0;
		polyBounds.height = 0;

		final int l = p.length;
		
		if (l==0) {
			polyBounds.x = 0;
			polyBounds.y = 0;
		} else {
		    polyBounds.x = (int)p[0].x;
		    polyBounds.y = (int)p[0].y;
		    
		    for (int t = 1; t<l; t++) {
		    	final int ppx = (int)p[t].x;
		    	final int ppy = (int)p[t].y;
		    	
		    	if (ppx < polyBounds.x)
		    		polyBounds.x = ppx;
		    	else
		    	if (ppx > polyBounds.getRight())
		    		polyBounds.setRight(ppx);
		    	
		    	if (ppy < polyBounds.y)
		    		polyBounds.y = ppy;
		    	else
		    	if (ppy > polyBounds.getBottom())
		    		polyBounds.setBottom(ppy);
		    	
		    }
		}
		
		return polyBounds;
	}

	public Paint getPaint(final PointDouble[] p) {
		if (getGradientVisible()) 
			return preparePaint(getGradientShader(polygonBounds(p)));
		else
			return preparePaint(null);
	}
}

