package com.steema.teechart;

import java.beans.PropertyChangeListener;
import java.beans.Customizer;

import com.steema.teechart.editors.ChartEditorPanel;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class TChartCustomizer extends ChartEditorPanel implements Customizer {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public TChartCustomizer() {
        super();
    }

    // Customizer
    public void setObject(Object bean) {
        setChart(((TChart)bean).getChart());
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {}
    public void removePropertyChangeListener(PropertyChangeListener listener) {}

}

