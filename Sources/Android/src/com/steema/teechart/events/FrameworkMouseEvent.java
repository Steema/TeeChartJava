package com.steema.teechart.events;

import com.steema.teechart.drawing.Point;

public class FrameworkMouseEvent extends Event {

    public static final int MOUSE_FIRST 	= 500;

    /**
     * The last number in the range of ids used for mouse events.
     */
    public static final int MOUSE_LAST          = 507;

    /**
     * The "mouse clicked" event. This <code>MouseEvent</code>
     * occurs when a mouse button is pressed and released.
     */
    public static final int MOUSE_CLICKED = MOUSE_FIRST;

    /**
     * The "mouse pressed" event. This <code>MouseEvent</code>
     * occurs when a mouse button is pushed down.
     */
    public static final int MOUSE_PRESSED = 3; //Event.MOUSE_DOWN

    /**
     * The "mouse released" event. This <code>MouseEvent</code>
     * occurs when a mouse button is let up.
     */
    public static final int MOUSE_RELEASED = 4; //Event.MOUSE_UP

    /**
     * The "mouse moved" event. This <code>MouseEvent</code>
     * occurs when the mouse position changes.
     */
    public static final int MOUSE_MOVED = 5; //Event.MOUSE_MOVE

    /**
     * The "mouse entered" event. This <code>MouseEvent</code>
     * occurs when the mouse cursor enters the unobscured part of component's
     * geometry. 
     */
    public static final int MOUSE_ENTERED = 6; //Event.MOUSE_ENTER

    /**
     * The "mouse exited" event. This <code>MouseEvent</code>
     * occurs when the mouse cursor exits the unobscured part of component's
     * geometry.
     */
    public static final int MOUSE_EXITED = 7; //Event.MOUSE_EXIT

 /**
     * The "mouse dragged" event. This <code>MouseEvent</code>
     * occurs when the mouse position changes while a mouse button is pressed.
     */
    public static final int MOUSE_DRAGGED = 29; //SWT DragDetect 

    /**
     * The "mouse wheel" event.  This is the only <code>MouseWheelEvent</code>.
     * It occurs when a mouse equipped with a wheel has its wheel rotated.
     * @since 1.4
     */
    public static final int MOUSE_WHEEL = 7 + MOUSE_FIRST; 

    /**
     * Indicates no mouse buttons; used by {@link #getButton}. 
     * @since 1.4
     */ 
    public static final int NOBUTTON = 0;

    /**
     * Indicates mouse button #1; used by {@link #getButton}.
     * @since 1.4
     */ 
    public static final int BUTTON1 = 1;

    /**
     * Indicates mouse button #2; used by {@link #getButton}.
     * @since 1.4
     */ 
    public static final int BUTTON2 = 2;

    /**
     * Indicates mouse button #3; used by {@link #getButton}.
     * @since 1.4
     */ 
    public static final int BUTTON3 = 3;

	public Object widget;
    
    
    public FrameworkMouseEvent(int button, int index, int type, int x, int y)
    {
       this.button = button;
       this.index = index;
       this.type = type;
       this.x =  x;
       this.y = y;
    }
    

    public int getButton()
    {
    	return this.button;
    }
    
    public int getID()
    {
    	return type;
    }
    
    public int getX()
    {
    	return x;
    }
    
    public int getY()
    {
    	return y;
    }
    
    /**
     * Constructs and initializes a point at the specified 
     * {@code (x,y)} location in the coordinate space. 
     * @param x the X coordinate of the newly constructed <code>Point</code>
     * @param y the Y coordinate of the newly constructed <code>Point</code>
     * @since 1.0
     */
    public Point getPoint(){
    	int x;
    	int y;
    	synchronized (this) {
    	    x = this.x;
    	    y = this.y;
    	}
            return new Point(x, y);
    }
    
    public int getClickCount()
    {
    	return 1;
    }
    
    public static String getEventName(int eventType) {
        return "unknown ???";
      }


	public void setSource(Object source) {
		// TODO Auto-generated method stub
		
	}
}
