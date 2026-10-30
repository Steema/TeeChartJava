package com.steema.teechart.events;

import java.awt.Component;

public class FrameworkMouseWheelEvent extends java.awt.event.MouseWheelEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FrameworkMouseWheelEvent(Component source, int id, long when, int modifiers, int x, int y, int clickCount,
			boolean popupTrigger, int scrollType, int scrollAmount, int wheelRotation) {
		super(source, id, when, modifiers, x, y, clickCount, popupTrigger, scrollType, scrollAmount, wheelRotation);
	}
	
	public FrameworkMouseWheelEvent(Component source, int id, long when, int modifiers, int x, int y, int xAbs,
			int yAbs, int clickCount, boolean popupTrigger, int scrollType, int scrollAmount, int wheelRotation) {
		super(source, id, when, modifiers, x, y, xAbs, yAbs, clickCount, popupTrigger, scrollType, scrollAmount, wheelRotation);
	}

	//From JDK 1.7
	/*public FrameworkMouseWheelEvent(Component source, int id, long when, int modifiers, int x, int y, int xAbs,
			int yAbs, int clickCount, boolean popupTrigger, int scrollType, int scrollAmount, int wheelRotation,
			double preciseWheelRotation) {
		super(source, id, when, modifiers, x, y, xAbs, yAbs, clickCount, popupTrigger, scrollType, scrollAmount, wheelRotation,
				preciseWheelRotation);
	}*/
}
