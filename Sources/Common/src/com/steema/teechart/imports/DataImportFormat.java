package com.steema.teechart.imports;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.Series;
import java.io.File;

/**
 * <p>Title: TeeChart for Java Data Import Format base class</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class DataImportFormat {
    
    protected IBaseChart chart;
    protected Series series;
    protected BufferedInputStream stream;
    
    /** Creates a new instance of DataImportFormat */
    public DataImportFormat() {
        super();
    }
    
    public DataImportFormat(IBaseChart c) {
        super();
        chart = c;
    }

    public DataImportFormat(IBaseChart c, Series s) {
        this(c);
        series = s;
    }
    
    public void open(String fileName) throws FileNotFoundException,
        IOException, ClassNotFoundException {
      BufferedInputStream bf=new BufferedInputStream(new FileInputStream(fileName));
      open(bf);
    }

    public void open(File file) throws FileNotFoundException,
            IOException, ClassNotFoundException {
      open(file.getPath());
    }
    
    /*override this method*/
    public void open(InputStream stream) throws IOException,
            ClassNotFoundException {
    }
}
