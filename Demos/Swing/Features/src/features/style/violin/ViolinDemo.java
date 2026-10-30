/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package features.style.violin;

import com.steema.teechart.functions.KDESides;
import com.steema.teechart.styles.Violin;
import features.ChartSamplePanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Dev
 */
public class ViolinDemo extends ChartSamplePanel
    implements ActionListener {
    
    private Violin violin;
    private com.steema.teechart.styles.Box boxSeries;
    
    public ViolinDemo() {
      super();
    }
    
    protected void initChart() {
        super.initChart();
        chart1.getHeader().setVisible(true);
        chart1.setText("Violin Series");
        
    }    
    
    protected void initComponents() {
        super.initComponents();

        chart1.getAxes().getAxis(WIDTH).setAutomatic(true);
        chart1.getAxes().getAxis(HEIGHT).setAutomatic(true);
        
        boxSeries = new com.steema.teechart.styles.Box(chart1.getChart());
        
        violin = new Violin(chart1.getChart());
        
        chart1.addSeries(violin);
        
        violin.getKdeFunction().getOptions().setSides(KDESides.BOTH);

            violin.getKdeFunction().getOptions().setResolution(500);

            //violin.getLinePen().setColor(java.awt.Color.ORANGE);
            //violin.getBrush().setColor(java.awt.Color.RED);

            boxSeries.setPosition(0);

            boxSeries.add(4.723143097);
            boxSeries.add(-163.8499657);
            boxSeries.add(175.231925);
            boxSeries.add(-48.35468425);
            boxSeries.add(14.6993532);
            boxSeries.add(114.734763);
            boxSeries.add(17.60508917);
            boxSeries.add(210.2650095);
            boxSeries.add(-61.72121173);
            boxSeries.add(114.4151786);
            boxSeries.add(196.452651);
            boxSeries.add(-93.70487623);
            boxSeries.add(94.04043151);
            boxSeries.add(-18.48801027);
            boxSeries.add(108.4558658);
            boxSeries.add(26.95490135);
            boxSeries.add(205.3224574);

            violin.setDataSource(boxSeries);

            violin.checkDataSource();
    }    
        

    protected void initGUI() {
        super.initGUI();
        JLabel tmpLabel;
        JPanel tmpPane = getButtonPane();
        {
            tmpPane.add(Box.createHorizontalStrut(10));
            tmpPane.add(Box.createHorizontalGlue());
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
