package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: Error class</p>
 *
 * <p>Description: ErrorSeries Series.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Error extends CustomError {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Error() {
        this(null);
    }

    public Error(IBaseChart c) {
        super(c);
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryError");
    }
}

