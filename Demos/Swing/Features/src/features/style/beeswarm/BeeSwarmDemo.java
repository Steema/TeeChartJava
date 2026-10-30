/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package features.style.beeswarm;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.GradientDirection;
import com.steema.teechart.functions.KDESides;
import com.steema.teechart.styles.BeeSwarm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import features.ChartSamplePanel;

/**
 *
 * @author Dev
 */
public class BeeSwarmDemo extends ChartSamplePanel
    implements ActionListener {
    
    private BeeSwarm beeSwarm;
    private com.steema.teechart.styles.Box boxSeries;
    
    public BeeSwarmDemo() {
      super();
    }
    
    protected void initChart() {
        super.initChart();
        chart1.getHeader().setVisible(true);
        chart1.setText("BeeSwarm Series");
        
    }    
    
    protected void initComponents() {
        super.initComponents();
        
        boxSeries = new com.steema.teechart.styles.Box(chart1.getChart());

        beeSwarm = new BeeSwarm(chart1.getChart());        
        
        beeSwarm.getKdeFunction().getOptions().setSides(KDESides.BOTH);

            beeSwarm.getLinePen().setColor(java.awt.Color.ORANGE);
            //beeSwarm.getBrush().setColor(java.awt.Color.RED);

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

            beeSwarm.setDataSource(boxSeries);

            chart1.getAxes().getBottom().setMinMax(-0.5, 0.5);
            chart1.getAxes().getBottom().setIncrement(0.2);
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
