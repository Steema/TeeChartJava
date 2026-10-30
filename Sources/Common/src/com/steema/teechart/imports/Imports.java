package com.steema.teechart.imports;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.TeeBase;
import com.steema.teechart.Chart;
import java.io.File;


/**
 * <p>Title: Chart Imports class:  tChart1.getImports()</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public final class Imports extends TeeBase {
	
	private static final long serialVersionUID = 1L;

    private Template template;
    private XMLImport xml;


    public Imports(IBaseChart chart) {
        super(chart);
    }

    public Template getTemplate() {
        if (template == null) {
            template = new Template();
        }

        return template;
    }
    
    public XMLImport getXML() {
        if (xml == null) {
            xml = new XMLImport(chart);
        }

        return xml;
    }

    final public class Template {

        public Chart fromXML(String fileName) throws FileNotFoundException {
            return fromXML(new BufferedInputStream(
                    new FileInputStream(fileName)));
        }

        public Chart fromXML(InputStream stream) {
        	
        	Chart result = chart.getParent().xmlDecode(stream);
        	
            if ((chart.getParent()!=null))
             ((com.steema.teechart.TChart)(chart.getParent())).setChart(result);
            
            return result;
        }

        public Chart fromFile(String fileName) throws FileNotFoundException,
                IOException, ClassNotFoundException {
            return fromStream(new BufferedInputStream(new FileInputStream(
                    fileName)));
        }

        public Chart fromFile(File file) throws FileNotFoundException,
                IOException, ClassNotFoundException {
            return fromFile(file.getPath());
        }

        public Chart fromStream(InputStream stream) throws IOException,
                ClassNotFoundException {
            ObjectInputStream in = new ObjectInputStream(stream);
        	//CustomObjectInputStream oin = new CustomObjectInputStream(stream);
       	
            Chart result = null;
            try {
                result = (Chart) in.readObject();
                //result = (Chart) oin.readObject();
            } finally {
                in.close();
            }
            return result;
        }
    }
}

