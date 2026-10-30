package com.steema.teechart.styles;

import java.util.ArrayList;

/**
 *
 * <p>Title: PaletteList class</p>
 *
 * <p>Description: A collection of GridPalette objects.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class PaletteList extends ArrayList {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GridPalette getPalette(int index) {
        return (GridPalette) get(index);
    }

    public void GridPalette(int index, GridPalette value) {
        set(index, value);
    }
}

