/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package features.style.violin;

import com.steema.teechart.functions.KDESides;
import com.steema.teechart.styles.HorizViolin;
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
public class HorizViolinDemo extends ChartSamplePanel
    implements ActionListener {
    
    private HorizViolin violin;
    private com.steema.teechart.styles.HorizBox boxSeries;
    
    public HorizViolinDemo() {
      super();
    }
    
    protected void initChart() {
        super.initChart();
        chart1.getHeader().setVisible(true);
        chart1.setText("HorizViolin Series");
        
    }    
    
    protected void initComponents() {
        super.initComponents();

        boxSeries = new com.steema.teechart.styles.HorizBox(chart1.getChart());
        
        violin = new HorizViolin(chart1.getChart());//  (chart1.getChart());  //<--- potser volem un constructor a Violin per agafar Chart
                                                           //Mira la demo Bar3DDemo...mira on crida el constructor de Bar3D
                                                           //i prova de copiar el constructor (o el format del constructor) a Violin.

        
        
        
        //chart1.addSeries(boxSeries);
        chart1.addSeries(violin);
        
        violin.getKdeFunction().getOptions().setSides(KDESides.BOTH);

            //violin.getKdeFunction().getOptions().setResolution(500);

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
            boxSeries.add(-146.8977273);
            boxSeries.add(185.5018042);
            boxSeries.add(8.294135496);
            boxSeries.add(170.1018831);
            boxSeries.add(-25.0084555);
            boxSeries.add(109.5537878);
            boxSeries.add(-42.54134484);
            boxSeries.add(161.2249696);
            boxSeries.add(1.95131609);
            boxSeries.add(-33.33688556);
            boxSeries.add(109.03816);
            boxSeries.add(-38.9393706);
            boxSeries.add(121.3493094);
            boxSeries.add(-4.908452899);
            boxSeries.add(-166.2188619);
            boxSeries.add(11.57283366);
            boxSeries.add(176.3964678);
            boxSeries.add(110.8755204);
            boxSeries.add(-51.29477103);
            boxSeries.add(178.5478547);
            boxSeries.add(553.9499109);
            boxSeries.add(2.750346155);
            boxSeries.add(29.28765195);
            boxSeries.add(-62.47847974);
            boxSeries.add(-109.3304066);
            boxSeries.add(120.8580358);
            boxSeries.add(114.4805217);
            boxSeries.add(21.41081879);
            boxSeries.add(150.9424472);
            boxSeries.add(181.2817844);
            boxSeries.add(104.2415804);
            boxSeries.add(-148.9825013);
            boxSeries.add(102.8203318);
            boxSeries.add(17.94859818);
            boxSeries.add(17.27953926);
            boxSeries.add(25.95785178);
            boxSeries.add(-43.26549148);
            boxSeries.add(-111.4736412);
            boxSeries.add(208.0000028);
            boxSeries.add(20.66494709);
            boxSeries.add(514.1211521);
            boxSeries.add(196.0813888);
            boxSeries.add(188.3434843);
            boxSeries.add(108.7538702);
            boxSeries.add(118.5680419);
            boxSeries.add(97.88270558);
            boxSeries.add(10.84328606);
            boxSeries.add(105.3636852);
            boxSeries.add(126.2237861);
            boxSeries.add(167.2021452);
            boxSeries.add(111.0311866);
            boxSeries.add(-56.52883643);
            boxSeries.add(-7.375059038);
            boxSeries.add(-54.20342289);
            boxSeries.add(-5.268905046);
            boxSeries.add(110.6727969);
            boxSeries.add(101.4781021);
            boxSeries.add(110.6464315);
            boxSeries.add(25.76979155);
            boxSeries.add(118.1465839);
            boxSeries.add(-38.29618771);
            boxSeries.add(1.485343235);
            boxSeries.add(2.132530501);
            boxSeries.add(-25.96838117);

            violin.setDataSource(boxSeries);
            
            //violin.setSmoothed(true);

            violin.checkDataSource();
        
            //boxSeries.setActive(false);
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
