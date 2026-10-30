package com.steema.teechart;

import javax.swing.JComponent;
import java.awt.Container;
import javax.swing.ToolTipManager;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.styles.SeriesMarks;

/**
 *
 * <p>Title: ToolTip class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ToolTip {

    transient private JComponent component;
    
    private boolean active = true;
    private Color backColor;
	private Color fontColor;

	private SeriesMarks marks;
	
    public void setToolTip(JComponent component, String text) {
        if (component != null) {
            component.setToolTipText(text);
        }
    }

    /**
     * Disables tooltip
     */
    public void hide() {
        //ToolTipManager.sharedInstance().setEnabled(false); //effects all tooltips in application;
        active = false;
    }

    /**
     * Enables tooltip
     */
    public void show() {
        //ToolTipManager.sharedInstance().setEnabled(true); //effects all tooltips in application;
        active = true;
    }
    /**
     * Returns the text to be displayed at the ToolTip.
     * 
     * @return
     */
    public String getText() {
        if (component==null) {
            return null;
        } else {
            String s=component.getToolTipText();
            return s==null ? "" : s;
        }
    }
    /**
     * Sets the text to be displayed at the ToolTip.
     * 
     * @param value
     */
    public void setText(String value) {
        if (!active) { value=null; };
        setToolTip(component, value);
    }

    public ToolTip(IBaseChart value) {
        super();

        Container c = (Container) value.getParent().getControl();

        if (c instanceof JComponent) {
            component = (JComponent) c;
        }
    }
    /**
     * Enables/Disables tooltip.
     * 
     * @return
     */
    public boolean getActive() {
        return ToolTipManager.sharedInstance().isEnabled();
    }

    /**
     * Enables/Disables tooltip.
     * 
     * @param value
     */
    public void setActive(boolean value) {
        if (value) {
            show();
        } else {
            hide();
        }
    }
    /**
     * Returns the time lag before the Tool Tip appears.
     * 
     * @return
     */
    public int getInitialDelay() {
      return ToolTipManager.sharedInstance().getInitialDelay();
    }
    /**
     * Sets the time lag before the Tool Tip appears.
     * 
     * @param value
     */
    public void setInitialDelay(int value) {
      ToolTipManager.sharedInstance().setInitialDelay(value);
    }
    /**
     * Returns the period of time the ToolTip remains visible if the
     * pointer is stationary on a control with specified ToolTip text.
     * 
     * @return
     */
    public int getDismissDelay() {
      return ToolTipManager.sharedInstance().getDismissDelay();
    }
    /**
     * Sets the period of time the ToolTip remains visible if the 
     * pointer is stationary on a control with specified ToolTip text.
     * 
     * @param value
     */
    public void setDismissDelay(int value) {
      ToolTipManager.sharedInstance().setDismissDelay(value);
    }

	public void deactivate() {
		hide();
	}

	public void activate() {
		show();
	}
	
	public void prepareMarks(SeriesMarks marks)
	{
	}
	
}

