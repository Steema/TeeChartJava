package com.steema.teechart.exports;


import com.steema.teechart.languages.Language;
import com.steema.teechart.IBaseChart;

/**
 *
 * <p>Title: ExcelFont class</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * @author not attributable
 * @version 1.1
 */

/// <summary>
/// Chart data export to Excel table
/// </summary>
public class ExcelFormat extends HTMLFormat {

    public ExcelFormat(IBaseChart c) {
        super(c);
        setFileExtension("xls");
    }

    public String getFilterFiles() {
        return Language.getString("ExcelFilter");
    }

}

