/*
 * JDBCImportDemo.java
 *
 * <p>Copyright (c) 2006-2007 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */

package features.imports;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import features.ChartSamplePanel;

import com.steema.teechart.styles.MarksStyle;
import com.steema.teechart.styles.Bar;
import com.steema.teechart.legend.LegendAlignment;
import com.steema.teechart.styles.Series;

import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

import java.awt.Cursor;

/**
 *
 * @author db
 */
public class JDBCImportDemo extends ChartSamplePanel implements ActionListener {

    /**
     * Creates a new instance of XmlImportDemo
     */
    public JDBCImportDemo() {
        super();
        loadButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();

        if (source == loadButton) {

            chart1.getLegend().setAlignment(LegendAlignment.BOTTOM);

            chart1.getSeries().clear();

            Bar bar1 = new Bar(chart1.getChart());
            bar1.setColorEach(true);
            bar1.getMarks().setStyle(MarksStyle.VALUE);

            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            try {
                JDBC_addData(bar1);
            } finally {
                setCursor(Cursor.getDefaultCursor());
            }
        }
    }

    /**
     * Uses an in-memory H2 database so the example needs no external server,
     * credentials, or network connection. Add the H2 driver to the runtime
     * classpath; see Demos/README.md.
     */
    public void JDBC_addData(Series series) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:h2:mem:teechart")) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE CategorySalesDemo ("
                        + "CategoryName VARCHAR(40) NOT NULL, "
                        + "CategorySales DOUBLE NOT NULL)");
                statement.execute("INSERT INTO CategorySalesDemo VALUES "
                        + "('Beverages', 18420.50), "
                        + "('Condiments', 9630.25), "
                        + "('Confections', 12780.00), "
                        + "('Dairy Products', 15340.75), "
                        + "('Seafood', 11210.00)");
            }

            try (Statement statement = connection.createStatement();
                    ResultSet results = statement.executeQuery(
                            "SELECT CategoryName, CategorySales "
                            + "FROM CategorySalesDemo "
                            + "ORDER BY CategoryName")) {
                series.getYValues().setDataMember("CategorySales");
                series.setLabelMember("CategoryName");
                series.setDataSource(results);
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Could not load the JDBC sample data", ex);
        }
    }

    protected void initChart() {
        super.initChart();
        chart1.getHeader().setText("JDBC data from an in-memory H2 database");
    }

    protected void initComponents() {
        super.initComponents();
        loadButton = new JButton("Load!");
    }

    protected void initGUI() {
        super.initGUI();
        JPanel tmpPane = getButtonPane();
        {
            tmpPane.add(loadButton);
            tmpPane.add(Box.createHorizontalGlue());
        }
    }

    private JButton loadButton;
}
