package com.steema.teechart.drawing;

import com.steema.teechart.TeeBase;
import com.steema.teechart.Rectangle;
import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: Gradient class</p>
 *
 * <p>Description: Used to configure a gradient filling.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Gradient extends TeeBase implements Cloneable {
	
	private static final long serialVersionUID = 1L;

    protected boolean visible;
    private Point[] customTargetPolygon = null;

    private GradientDirection direction = GradientDirection.VERTICAL;
    private Color startColor = Color.GOLD;
    private Color middleColor = Color.GRAY;
    private Color endColor = Color.WHITE;

    //private float sigmaFocus = 0.5F;
    //private float sigmaScale = 1.0F;
    //private boolean sigma;
    //private boolean wrapTile = false;
    //private boolean gammaCorrection;

    private boolean useMiddle;
    private int transparency = 0;
    private double angle = 0D;

    private int radialX = 0;
    private int radialY = 0;
    
    private int radialTransition = 1;

    /** @todo NECESSARY?? */
    //public boolean Polygonal;

    public Gradient(IBaseChart c) {
        super(c);
    }

    public Gradient() {
        super(null);
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Copies the Gradient parameter properties into the Canvas.Gradient object.
     *
     * @param value Gradient
     */
    public void assign(Gradient value) {
        angle = value.angle;
        direction = value.direction;
        startColor = value.startColor;
        middleColor = value.middleColor;
        endColor = value.endColor;
        radialX = value.radialX;
        radialY = value.radialY;
        radialTransition = value.radialTransition;

        customTargetPolygon = value.customTargetPolygon;
        /*
        sigmaFocus = value.sigmaFocus;
        sigmaScale = value.sigmaScale;
        sigma = value.sigma;
        wrapTile = value.wrapTile;
        gammaCorrection = value.gammaCorrection;
        */

        useMiddle = value.useMiddle;
        visible = value.visible;
        transparency = value.transparency;
    }

    /**
     * Used only for polygonal gradients, to repeat fillings.<br>
     * Default value : WrapMade.Clamp
     *
     * @return boolean
     */
//    public boolean getWrapTile() {
//        return wrapTile;
//    }

//    public void setWrapTile(boolean value) {
//        if (wrapTile != value) {
//            wrapTile = value;
//            invalidate();
//        }
//    }

    /**
     * Amount of transparency in percentage.<br>
     * Default value: 0
     *
     * @return int
     */
    public int getTransparency() {
        return transparency;
    }

    /**
     * Specifies the amount of transparency as a percentage.<br>
     * Default value: 0
     *
     * @param value int
     */
    public void setTransparency(int value) {
        transparency = setIntegerProperty(transparency, value);
    }

    /**
     * Ratio between Start and End Colors.<br>
     * By setting a value from OF to 1F, you can determine where the centre
     * color will be strongest. A value of 0.5F will place it in the centre of
     * the object. <br>
     * Default value: 0.5F
     *
     * @return float
     */
//    public float getSigmaFocus() {
//        return sigmaFocus;
//    }

//    public void setSigmaFocus(float value) {
//        if (sigmaFocus != value) {
//            sigmaFocus = value;
//            invalidate();
//        }
//    }

    /**
     * Ratio between Colors, from 0 to 1.<br>
     * By setting a value from OF to 1F, you can determine the intensity of the
     * center color . A value of 1F will set the highest intensity possible.<br>
     * Default value: 1.0F
     *
     * @return float
     */
//    public float getSigmaScale() {
//        return sigmaScale;
//    }

//    public void setSigmaScale(float value) {
//        if (sigmaScale != value) {
//            sigmaScale = value;
//            invalidate();
//        }
//    }

    /**
     * Enables use of SigmaFocus and SigmaScale.<br>
     * Enables a Gradient based on a bell-shaped curve using EndColor as the
     * central color and StartColor at the borders. <br>
     * Default value: false
     *
     * @return boolean
     */
//    public boolean getSigma() {
//        return sigma;
//    }

//    public void setSigma(boolean value) {
//        sigma = setBooleanProperty(sigma, value);
//    }

    /**
     * Enables fine-tuning of displayed Colors. <br>
     * Angle of gradient filling. When zero, uses Direction property.<br>
     * Default value: O
     *
     * @return double
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Enables fine-tuning of displayed Colors. <br>
     * Default value: O
     *
     * @param value double
     */
    public void setAngle(double value) {
        angle = setDoubleProperty(angle, value);
    }

    /**
     * Enables fine-tuning of displayed Colors.<br>
     * This effectively broadens the distance over which the colors blend
     * when true. <br>
     * Default value: false
     *
     * @return boolean
     */
//    public boolean getGammaCorrection() {
//        return gammaCorrection;
//    }

//    public void setGammaCorrection(boolean value) {
//        gammaCorrection = setBooleanProperty(gammaCorrection, value);
//    }

    /**
     * The direction the gradient fill to be applied.<br>
     * Default value: GradientDirection.Vertical
     *
     *
     * @return GradientDirection
     */
    public GradientDirection getDirection() {
        return direction;
    }

    /**
     * Specifies the direction the gradient fill to be applied.<br>
     * Default value: GradientDirection.Vertical
     *
     *
     * @param value GradientDirection
     */
    public void setDirection(GradientDirection value) {
        if (direction != value) {
            direction = value;
            invalidate();
        }
    }

    /**
     * One of the three Colors used to create the gradient fill.<br>
     * It is one of the three colors used to create the gradient fill.
     * The gradient fill is composed of the three colors: StartColor, MidColor
     * and EndColor. <br>
     * Default value: GOLD
     *
     *
     * @return Color
     */
    public Color getStartColor() {
        return startColor;
    }

    /**
     * Specifies the start Color used to create the gradient fill.<br>
     * Default value: GOLD
     *
     *
     * @param value Color
     */
    public void setStartColor(Color value) {
        startColor = setColorProperty(startColor, value);
    }

    /**
     * Determines whether MiddleColor is used when creating the gradient fill,
     * or not.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getUseMiddle() {
        return useMiddle;
    }

    /**
     * A MiddleColor is used when creating the gradient fill when true.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setUseMiddle(boolean value) {
        useMiddle = setBooleanProperty(useMiddle, value);
    }

    /**
     * One of the three Colors used to create the gradient fill.<br>
     * The gradient fill is composed of the three colors: StartColor, MidColor
     * and EndColor. <br>
     * Default value: GRAY
     *
     *
     * @return Color
     */
    public Color getMiddleColor() {
        return middleColor;
    }

    /**
     * Specifies the middle Color used to create the gradient fill.<br>
     * Default value: GRAY
     *
     *
     * @param value Color
     */
    public void setMiddleColor(Color value) {
        if (middleColor != value) {
            middleColor = value;
            useMiddle = !middleColor.isEmpty();
            invalidate();
        }
    }

    /**
     * One of the three Colors used to create the gradient fill.<br>
     * The gradient fill is composed of the three colors: StartColor, MidColor
     * and EndColor. <br>
     * Default value: WHITE
     *
     *
     * @return Color
     */
    public Color getEndColor() {
        return endColor;
    }

    /**
     * Specifies the end Color used to create the gradient fill.<br>
     * Default value: WHITE
     *
     *
     * @param value Color
     */
    public void setEndColor(Color value) {
        endColor = setColorProperty(endColor, value);
    }

    /**
     * Determines whether the gradient fill appears on screen.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getVisible() {
        return visible;
    }

    /**
     * Determines whether the gradient fill appears on screen.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setVisible(boolean value) {
        visible = setBooleanProperty(visible, value);
    }
    /**
     *
     * @return int
     */
    public int getRadialX() {
        return radialX;
    }
    /**
     *
     * @param value int
     */
    public void setRadialX(int value) {
        radialX = setIntegerProperty(radialX, value);
    }
    /**
     *
     * @return int
     */
    public int getRadialY() {
        return radialY;
    }
    
    /**
     *
     * @param value int
     */
    public void setRadialY(int value) {
        radialY = setIntegerProperty(radialY, value);
    }
    
    public int getRadialTransition() {
        return radialTransition;
    }
    /**
     * lengthens/softens the radial transition
     */
    public void setRadialTransition(int value) {
        radialTransition = setIntegerProperty(radialTransition, value);
    }
    
    /// <summary>
    /// Assign a rectangle to this property to define a custom rectangle to a Gradient.
    /// </summary>
    public Point[] getCustomTargetPolygon()
    {
      return customTargetPolygon;
    }

    public void setCustomTargetPolygon(Point[] value)
    {
      customTargetPolygon = value;
    }


    public Color correctColor(Color value) {
        return (transparency == 0) ? value : value.transparentColor(transparency);
    }

    /**
     * Obsolete. Please use ChartBrush.getGradient() and Graphics3D.Rectangle methods.
     * Fills the Canvas area using the Gradient color and direction properties.
     *
     * @param g IGraphics3D
     * @param left int
     * @param top int
     * @param right int
     * @param bottom int
     */
    public void draw(IGraphics3D g, int left, int top, int right, int bottom) {
        draw(g, Rectangle.fromLTRB(left, top, right, bottom));
    }

    /**
     * Obsolete. Please use ChartBrush.getGradient() and Graphics3D.Polygon methods.
     * Fills the Canvas area using the Gradient color and direction properties.
     *
     * @param g IGraphics3D
     * @param p Point[]
     */
    public void draw(final IGraphics3D g, final Point[] p) {
        g.getBrush().getGradient().assign(this);
        g.polygon(p);
    }

    /**
     * Obsolete. Please use ChartBrush.getGradient() and Graphics3D.Rectangle methods.
     * Fills the Canvas area using the Gradient color and direction properties.
     *
     * @param g IGraphics3D
     * @param rect Rectangle
     */
    public void draw(IGraphics3D g, Rectangle rect) {
        g.getBrush().getGradient().assign(this);
        g.rectangle(rect);
    }
    
    private Rectangle customTargetRectangle;
    
    public Rectangle getCustomTargetRectangle()
    {
    	if (customTargetRectangle == null)
    		customTargetRectangle = new Rectangle(Rectangle.EMPTY);
    	
    	if (customTargetRectangle != Rectangle.EMPTY)
    	{
    		if (customTargetRectangle.getWidth() == 0)
    		{
    			customTargetRectangle.width=1;
    		}

    		if (customTargetRectangle.getHeight() == 0)
    		{
    			customTargetRectangle.height = 1;
    		}
    	}
        return customTargetRectangle;
    }
    
    public void setCustomTargetRectangle(Rectangle r)
    {
    	customTargetRectangle = r;
    }

}

