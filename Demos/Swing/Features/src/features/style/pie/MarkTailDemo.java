/*
 * AutoMarkPositionDemo.java
 *
 * <p>Copyright: (c) 2005-2007 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */

package features.style.pie;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.styles.MarksTail;
import com.steema.teechart.styles.Pie;
import com.steema.teechart.styles.SeriesMarks;
import com.steema.teechart.styles.TailAlignment;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import features.ChartSamplePanel;

/**
 *
 * @author Steema Software SL
 */
public class MarkTailDemo extends ChartSamplePanel
    implements ItemListener {

    private Pie pieSeries;

    /**
     * Creates a new instance of AutoMarkPositionDemo
     */
    public MarkTailDemo() {
        super();
        markButton.addItemListener(this);
        view3DButton.addItemListener(this);
    }

    public void itemStateChanged(ItemEvent e) {
        Object source = e.getItemSelectable();
        boolean isSelected = (e.getStateChange() == ItemEvent.SELECTED);

        if (source == markButton) {
            pieSeries.setAutoMarkPosition(isSelected);
        } else if (source == view3DButton) {
            chart1.getAspect().setView3D(isSelected);
        };
    }

    protected void initChart() {
        super.initChart();
        chart1.getAspect().setElevation(315);
        chart1.getAspect().setOrthogonal(false);
        chart1.getAspect().setPerspective(0);
        chart1.getAspect().setRotation(360);
    }

    protected void initComponents() {
        super.initComponents();

        pieSeries = new com.steema.teechart.styles.Pie(chart1.getChart());

        SeriesMarks tmpMarks = pieSeries.getMarks();
        tmpMarks.setArrowLength(19);
        tmpMarks.getCallout().setDistance(4);
        tmpMarks.getCallout().getPen().setVisible(false);
        tmpMarks.setVisible(true);
        
        tmpMarks.setMarkerTail(MarksTail.WithPointer);
        tmpMarks.getTailParams().setAlign(TailAlignment.Auto);
        tmpMarks.getTailParams().setCustomPoint(true);

        pieSeries.setAutoMarkPosition(true);
        pieSeries.fillSampleValues(16);

        markButton = new JCheckBox("Auto Mark position");
        markButton.setSelected(pieSeries.getAutoMarkPosition());
        view3DButton = new JCheckBox("3D");
        view3DButton.setSelected(chart1.getAspect().getView3D());
    }

    protected void initGUI() {
        super.initGUI();
        JPanel tmpPane = getButtonPane();
        {
            tmpPane.add(markButton);
            tmpPane.add(Box.createHorizontalStrut(20));
            tmpPane.add(view3DButton);
            tmpPane.add(Box.createHorizontalGlue());
        }
    }

    private JCheckBox markButton, view3DButton;
}
