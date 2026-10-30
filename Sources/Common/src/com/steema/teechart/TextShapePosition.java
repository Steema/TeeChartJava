package com.steema.teechart;

/**
 *
 * <p>Title: TextShapePosition class</p>
 *
 * <p>Description: Shape Custom Position.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class TextShapePosition extends TextShape {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected boolean bCustomPosition;

    public TextShapePosition(IBaseChart c) {
        super(c);
    }

    /**
     * Allows custom positioning of TextShape when true.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getCustomPosition() {
        return bCustomPosition;
    }

    /**
     * Set to true to permit custom positioning of TextShape.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setCustomPosition(boolean value) {
        bCustomPosition = setBooleanProperty(bCustomPosition, value);
    }

    protected boolean shouldSerializeLeft() {
        return bCustomPosition;
    }

    protected boolean shouldSerializeTop() {
        return bCustomPosition;
    }

    protected boolean shouldSerializeRight() {
        return bCustomPosition;
    }

    protected boolean shouldSerializeBottom() {
        return bCustomPosition;
    }
}

