package com.steema.teechart.exports;

import com.steema.teechart.TeeBase;
import com.steema.teechart.IBaseChart;


/**
 * <p>Title: Exports class</p>
 *
 * <p>Description: </p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * //( 1) Save the template into a Stream...
 *    ByteArrayOutputStream m = new ByteArrayOutputStream();
 *    myChart.getExport().getTemplate().toStream(m);
 * //( 2) Load the template into other Chart...
 *    copyChart.setChart(copyChart.getImport().getTemplate().fromStream(
 *            new ByteArrayInputStream(m.toByteArray())
 *    ));
 * //( 3) repaint the Chart
 *    copyChart.repaint();
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public final class Exports extends TeeBase {
	
	private static final long serialVersionUID = 1L;

    private ImageExport image;
    private TemplateExport template;
    private DataExport data;

    final public class DataExport {

        private XMLFormat xmlFormat;
        private TextFormat txtFormat;
        private HTMLFormat htmlFormat;
        private ExcelFormat excelFormat;

        public DataExport() {}

        /**
         * Export Chart to XML data format
         *
         * @return XMLFormat
         */
        public XMLFormat getXML() {
            if (xmlFormat == null) {
                xmlFormat = new XMLFormat(chart);
            }
            return xmlFormat;
        }

        /**
         * Export Chart as text
         *
         * <p>Example:
         * <pre><font face="Courier" size="4">
         * myChart.getExport().getData().getText().save(tmpName);
         * </font></pre></p>

         * @return TextFormat
         */
        public TextFormat getText() {
            if (txtFormat == null) {
                txtFormat = new TextFormat(chart);
            }
            return txtFormat;
        }

        /**
         * Export Chart as HTML table
         *
         * @return HTMLFormat
         */
        public HTMLFormat getHTML() {
            if (htmlFormat == null) {
                htmlFormat = new HTMLFormat(chart);
            }
            return htmlFormat;
        }

        /**
         * Export Chart as Excel spreadsheet
         *
         * @return ExcelFormat
         */
        public ExcelFormat getExcel() {
            if (excelFormat == null) {
                excelFormat = new ExcelFormat(chart);
            }
            return excelFormat;
        }

    }


    public Exports(IBaseChart chart) {
        super(chart);
    }

    /**
     * Gets a class instance with methods to export chart Series data.
     *
     * @return DataExport
     */
    public DataExport getData() {
        if (data == null) {
            data = new DataExport();
        }

        return data;
    }

    /**
     * Gets a class instance with methods to create images from chart.
     *
     * @return ImageExport
     */
    public ImageExport getImage() {
        if (image == null) {
            image = new ImageExport(chart);
        }

        return image;
    }

    /**
     * Gets a class instance with methods to store the chart to a file or
     * stream using Java standard serialization and XMLEncoder mechanisms.
     *
     * <p>Example:
     * <pre><font face="Courier" size="4">
     *  myChart.getExport().getTemplate().toXML(tmpName);
     *          showSavedFile(tmpName);
     * </font></pre></p>
     *
     * @return Template
     */
    public TemplateExport getTemplate() {
        if (template == null) {
            template = new TemplateExport(getChart());
        }

        return template;
    }

    
}
