package com.steema.teechart.events;

import com.steema.teechart.events.FrameworkMouseEvent;

import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;

public class Utils {
	
	public static void handleMouseEvents(Object[] listeners, FrameworkMouseEvent e)
	{
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == MouseMoveListener.class) {
/*            	if (e.type == SWT.DragDetect)
                if (e.getID()==FrameworkMouseEvent.MOUSE_DRAGGED) {
                    ((MouseMoveListener) listeners[i + 1]).mouseDragged(e);
                } else*/ 
                if (e.getID()==FrameworkMouseEvent.MOUSE_MOVED) {
                	org.eclipse.swt.events.MouseEvent eMouse=new org.eclipse.swt.events.MouseEvent(e);
                    ((MouseMoveListener) listeners[i + 1]).mouseMove(eMouse);
                }
            }
            if (listeners[i] == MouseListener.class) {

            }
        }
	}

}
