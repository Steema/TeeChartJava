package com.steema.teechart.styles;

import java.util.ArrayList;

/**
 *
 * <p>Title: ContourLevels class</p>
 *
 * <p>Description: ContourLevels stores information about Contour Series's Y
 * position levels.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ContourLevels extends ArrayList {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Stores information about Contour Series's Y position levels.
     */
    public ContourLevels() {
        super();
    }

    /**
     * Accesses ContourLevel characteristics selecting Level by index.
     *
     * @param index int
     * @return ContourLevel
     */
    public ContourLevel getLevel(int index) {
        return (ContourLevel) get(index);
    }

    /**
     * Accesses ContourLevel characteristics selecting Level by index.
     *
     * @param index int
     * @param value ContourLevel
     */
    public void setLevel(int index, ContourLevel value) {
        set(index, value);
    }

    public ContourLevel add(ContourLevel value) {
        super.add(value);
        return value;
    }
}



