package com.steema.teechart.tools;


import com.steema.teechart.languages.Language;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.misc.StringFormat;

/**
 *
 * <p>Title: PageNumber class</p>
 *
 * <p>Description: PageNumber annotation tool.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class PageNumber extends Annotation {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PageNumber() {
        this(null);
    }

    public PageNumber(IBaseChart c) {
        super(c);
        setText(Language.getString("PageOfPages"));
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("PageNumber");
    }

    /**
     * Defines the format of the PageNumTool text String.
     *
     * @return String
     */
    public String getFormat() {
        return super.getInnerText();
    }

    /**
     * Defines the format of the PageNumTool text String.
     *
     * @param value String
     */
    public void setFormat(String value) {
        setText(value);
    }

    protected String getInnerText() {
        if (!chart.isDesignTime()) {
            try {
               return StringFormat.format2(getFormat(),
                                            chart.getPage().getCurrent(),
                                            chart.getPage().getCount());
            } catch (Exception e) { // FormatException ?
                return getFormat();
            }
        } else {
            return getFormat();
        }
    }
}

