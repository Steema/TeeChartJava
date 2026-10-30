/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steema.teechart.events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Utils {

    public static void handleMouseEvents(Object[] listeners, MouseEvent e)
    {
      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if (listeners[i] == MouseMotionListener.class) {
            if (e.getID()==MouseEvent.MOUSE_DRAGGED) {
                ((MouseMotionListener) listeners[i + 1]).mouseDragged(e);
            } else if (e.getID()==MouseEvent.MOUSE_MOVED) {
                ((MouseMotionListener) listeners[i + 1]).mouseMoved(e);
            }
        }
        if (listeners[i] == MouseListener.class) {

        }
      }
    }
}
