package com.steema.teechart.exports;

import com.steema.teechart.misc.Enum;

/**
 *
 * <p>Title: PDFResourceType class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final class PDFResourceType extends Enum {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final PDFResourceType FONT = new PDFResourceType(0);
    public static final PDFResourceType IMAGE = new PDFResourceType(1);

    private PDFResourceType(int value) {
        super(value);
    }
}

