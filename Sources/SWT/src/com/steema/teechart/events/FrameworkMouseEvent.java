package com.steema.teechart.events;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.SWT;

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
    	switch(this.type)
    	{
    	  case SWT.MouseDown:
            return 1; //"mouse down";
          case SWT.MouseUp:
            return 1; //"mouse up";
          case SWT.MouseDoubleClick:
              return 2; //"mouse double click";
    	}
    	
    	return 0;
    }
    
    public static String getEventName(int eventType) {
        switch(eventType) {
          case SWT.None:
            return "null";
          case SWT.KeyDown:
            return "key down";
          case SWT.KeyUp:
            return "key up";
          case SWT.MouseDown:
            return "mouse down";
          case SWT.MouseUp:
            return "mouse up";
          case SWT.MouseMove:
            return "mouse move";
          case SWT.MouseEnter:
            return "mouse enter";
          case SWT.MouseExit:
            return "mouse exit";
          case SWT.MouseDoubleClick:
            return "mouse double click";
          case SWT.Paint:
            return "paint";
          case SWT.Move:
            return "move";
          case SWT.Resize:
            return "resize";
          case SWT.Dispose:
            return "dispose";
          case SWT.Selection:
            return "selection";
          case SWT.DefaultSelection:
            return "default selection";
          case SWT.FocusIn:
            return "focus in";
          case SWT.FocusOut:
            return "focus out";
          case SWT.Expand:
            return "expand";
          case SWT.Collapse:
            return "collapse";
          case SWT.Iconify:
            return "iconify";
          case SWT.Deiconify:
            return "deiconify";
          case SWT.Close:
            return "close";
          case SWT.Show:
            return "show";
          case SWT.Hide:
            return "hide";
          case SWT.Modify:
            return "modify";
          case SWT.Verify:
            return "verify";
          case SWT.Activate:
            return "activate";
          case SWT.Deactivate:
            return "deactivate";
          case SWT.Help:
            return "help";
          case SWT.DragDetect:
            return "drag detect";
          case SWT.Arm:
            return "arm";
          case SWT.Traverse:
            return "traverse";
          case SWT.MouseHover:
            return "mouse hover";
          case SWT.HardKeyDown:
            return "hard key down";
          case SWT.HardKeyUp:
            return "hard key up";
          case SWT.MenuDetect:
            return "menu detect";
        }
        
        return "unkown ???";
      }


	public void setSource(Object source) {
		data=source;
	}
}
