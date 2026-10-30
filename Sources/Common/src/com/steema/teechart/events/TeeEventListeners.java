package com.steema.teechart.events;

import java.util.ArrayList;

/**
 *
 * <p>Title: TeeEventListeners class</p>
 *
 * <p>Description: List for intercommunication of TeeChart components</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class TeeEventListeners extends ArrayList {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ITeeEventListener getEvent(int index) {
        return (ITeeEventListener) get(index);
    }

    public void setEvent(int index, ITeeEventListener value) {
        set(index, value);
    }

    public void add(ITeeEventListener item) {
        super.add(item);
    }

    public void remove(ITeeEventListener item) {
        super.remove(item);
    }
}

