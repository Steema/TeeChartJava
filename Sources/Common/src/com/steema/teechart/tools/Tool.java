package com.steema.teechart.tools;

import com.steema.teechart.events.ChangeEvent;
import com.steema.teechart.events.ChangeListener;
import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.events.DragListener;
import com.steema.teechart.events.MouseMoveListener;
import com.steema.teechart.Cursor;
import com.steema.teechart.TeeBase;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.events.SizeListener;
import com.steema.teechart.events.ToolMouseListener;
import com.steema.teechart.styles.ISeries;

import com.steema.teechart.events.FrameworkMouseEvent;
import java.net.URL;

import com.steema.teechart.events.Utils;
import com.steema.teechart.events.EventListenerList;
import java.util.EventListener;


/**
 *
 * <p>Title: Tool class</p>
 *
 * <p>Description: Base Tool class.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Tool extends TeeBase {
	
	private static final long serialVersionUID = 1L;

    static public int clickTolerance = 3;

    protected ChartPen pPen=null;  // Delayed creation at derived getPen()
    protected ChartBrush bBrush=null; // Delayed creation at derived getBrush()
    private boolean active = true;

    transient protected EventListenerList listenerList;

    protected Object readResolve() {
        listenerList = new EventListenerList();
        return this;
    }

    static GetFirstLast getFirstLastSeries(ISeries s) {
        GetFirstLast result = new GetFirstLast();

        result.min = s.getFirstVisible();
        if (result.min < 0) {
            result.min = 0;
        }

        result.max = s.getLastVisible();
        if (result.max < 0) {
            result.max = s.getCount() - 1;
        } else
        if (result.max >= s.getCount()) {
            result.max = s.getCount() - 1; // 5.03
        }

        result.result = (s.getCount() > 0) && (result.min <= s.getCount()) &&
                        (result.max <= s.getCount());
        return result;
    }

    protected Tool(IBaseChart c) {
        super(c);

        if (chart != null) {
            chart.getTools().add(this);
        }

        readResolve();
    }

    protected Tool() {
        this((IBaseChart)null);
    }

    public String toString() {
        return getDescription();
    }

    public void addMouseMotionListener(MouseMoveListener l) {
        listenerList.add(MouseMoveListener.class, l);
    }

    public void removeMouseMotionListener(MouseMoveListener l) {
        listenerList.remove(MouseMoveListener.class, l);
    }
    
    public void addToolListener(ToolListener l) {
        listenerList.add(ToolListener.class, l);
    }

    public void removeToolListener(ToolListener l) {
        listenerList.remove(ToolListener.class, l);
    }

    protected void fireMouse(FrameworkMouseEvent e) {
        Object[] listeners = listenerList.getListenerList();
        Utils.handleMouseEvents(listeners,e);
    }

    protected void fireChanged(ChangeEvent ce) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            Class listen = (Class)listeners[i];
            if (listen.isAssignableFrom(ChangeListener.class)) {
              ((ChangeListener) listeners[i + 1]).stateChanged(ce);
            }
        }
    }

    protected void fireClicked(FrameworkMouseEvent e) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ToolMouseListener.class) {
              ((ToolMouseListener) listeners[i + 1]).toolClicked(e);
            }
        }
    }

    protected void fireDragged(ChangeEvent changeEvent) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == DragListener.class) {
              ((DragListener) listeners[i + 1]).dragFinished(changeEvent);
            }
        }
    }

    protected void fireDragging(ChangeEvent e) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == DragListener.class) {
              ((DragListener) listeners[i + 1]).dragMoving(e);
            }
        }
    }

    protected void fireResized(ChangeEvent e) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == SizeListener.class) {
              ((SizeListener) listeners[i + 1]).sizeChanged(e);
            }
        }
    }
    
    protected void fireActiveChanged(ChangeEvent e) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ToolListener.class) {
              ((ToolListener) listeners[i + 1]).toolActiveChanged();
            }
        }
    }

    /**
     * Enables/Disables the indexed Tool.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setActive(boolean value) {
        active = setBooleanProperty(active, value);
        fireActiveChanged(new ChangeEvent(this));
    }

    /**
     * Enables/Disables the indexed Tool.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getActive() {
        return active;
    }

    public void setChart(IBaseChart value) {
        if (chart != value) {
            if (chart != null) {
                chart.getTools().remove(this);
            }

            super.setChart(value);

            if (chart != null && !InternalUse) {
                chart.getTools().internalAdd(this);
            }
            if (pPen != null) {
                pPen.setChart(chart);
            }
            if (bBrush != null) {
                bBrush.setChart(chart);
            }
            if (chart != null) {
                chart.invalidate();
            }
        }
    }

    public void chartEvent(ChartDrawEvent ce) {}

    public Cursor mouseEvent(FrameworkMouseEvent e, Cursor c) {
        fireMouse(e);
        return c;
    }
    
    public void dispose() {
        this.setChart(null);
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return "";
    }

    /**
     * Gets detailed descriptive text.
     *
     * @return String
     */
    public String getSummary() {
        return "";
    }

    /**
     * Returns the URL of the associated icon for a given Tool class.<br>
     * This icon is used at Tools gallery listbox and Tools Editor dialog.
     *
     * @return URL
     */
    public URL getBitmapEditor() {
        // do not use getClass().getSimpleName()  ( Java5 )
        String name = getClass().getName();
        name = "icons/"+name.substring(name.lastIndexOf('.')+1)+".gif";
        return Tool.class.getResource(name);
    }
    
    public interface ToolListener extends EventListener {
        void toolActiveChanged();
    }
}

final class GetFirstLast {
    boolean result;
    int min;
    int max;
}


