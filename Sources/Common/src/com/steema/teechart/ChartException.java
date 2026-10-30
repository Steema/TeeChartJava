package com.steema.teechart;

/**
 *
 * <p>Title: ChartException class</p>
 *
 * <p>Description: Underlying TeeChart exception class</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ChartException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ChartException() {
        this("");
    }

    public ChartException(String message) {
        super(message);
    }
}

