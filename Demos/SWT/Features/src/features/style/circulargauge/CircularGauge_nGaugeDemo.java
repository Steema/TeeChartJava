/*
 * CircularGaugeDemo.java
 *
 * <p>Copyright: Copyright (c) 2005-2008 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */
package features.style.circulargauge;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

import com.steema.teechart.Rectangle;
import com.steema.teechart.styles.CircularGauge;

import features.ChartSample;

/**
 * 
 * @author tom
 */
public class CircularGauge_nGaugeDemo extends ChartSample implements ActionListener,
		SelectionListener, DisposeListener, ModifyListener {

	private CircularGauge cGauge;
	private Spinner jLeft, jTop;
	private Button jCheckBox1, jCheckBox2;
	private Timer t1, t2;
	private boolean up = true;
	private Label jLabelLeft, jLabelTop;

	public CircularGauge_nGaugeDemo(Composite c) {
		super(c);

	}

	protected void initChart() {

		super.initChart();

		t1 = new Timer(50, this);
		t1.addActionListener(this);
		t1.start();

		t2 = new Timer(500, this);
		t2.addActionListener(this);

		jCheckBox1 = addCheckButton("Same Value", "", this);
		jCheckBox2 = addCheckButton("Auto Position", "", this);
		jCheckBox1.setSelection(true);
		jCheckBox2.setSelection(true);

		jLabelTop = addLabel(0, "Top: ");
		jLabelTop.setText("Top: ");
		jTop = addSpinner(30, 0, 1000, 1, this);
		jTop.setEnabled(false);
		jLabelLeft = addLabel(0, "Left: ");
		jLabelLeft.setText("Left: ");
		jLeft = addSpinner(30, 0, 1000, 1, this);
		jLeft.setEnabled(false);

		cGauge = new CircularGauge(chart1.getChart());
		cGauge.fillSampleValues();
		cGauge.setValue(50);
		cGauge.setAutoValueNumericGauge(true);
		Double value = cGauge.getNumericGauge().truncate(cGauge.getValue());
		cGauge.getNumericGauge().setValue(value);
	}

	@SuppressWarnings("static-access")
	public void actionPerformed(ActionEvent e) {
		Object aux = e.getSource();
		if (aux == t1) {
			try {
				super.getDisplay().getDefault().syncExec(new Runnable() {
					public void run() {
						try {
							if (up) {
								cGauge.setValue(cGauge.getValue() + 0.1);
							} else {
								cGauge.setValue(cGauge.getValue() - 0.1);
							}
							if ((cGauge.getValue()) > 99) {
								up = false;
							} else if ((cGauge.getValue()) < 1) {
								up = true;
							}
						} catch (SWTException e2) {
							t1.stop();
							t2.stop();
						}
					}
				});
			} catch (SWTException e2) {
				t1.stop();
				t2.stop();
			}
		} else if (aux == t2) {
			try {
				super.getDisplay().getDefault().syncExec(new Runnable() {
					public void run() {
						try {
							if (!cGauge.getAutoValueNumericGauge()) {
								Random rnd = new Random();
								Double value = rnd.nextDouble() * 100;
								cGauge.getNumericGauge().setValue(value);
							}
						} catch (SWTException e2) {
							t1.stop();
							t2.stop();
						}
					}
				});
			} catch (SWTException e2) {
				t1.stop();
				t2.stop();
			}
		}
	}

	public void widgetDefaultSelected(SelectionEvent arg0) {

	}

	public void widgetSelected(SelectionEvent arg0) {
		Object aux = arg0.getSource();
		if (aux == jCheckBox2) {
			cGauge.setAutoPositionNumericGauge(jCheckBox2.getSelection());
			jLeft.setEnabled(!jCheckBox2.getSelection());
			jTop.setEnabled(!jCheckBox2.getSelection());
			jTop.setSelection(cGauge.getNumericGauge().getCustomBounds().getTop());
			jLeft.setSelection(cGauge.getNumericGauge().getCustomBounds().getLeft());
		} else if (aux == jCheckBox1) {
			if (jCheckBox1.getSelection()) {
				cGauge.setAutoValueNumericGauge(true);
				t2.stop();
			} else {
				t2.start();
				cGauge.setAutoValueNumericGauge(false);
			}
		}
	}

	public void widgetDisposed(DisposeEvent arg0) {
		t1.stop();
		t2.stop();
	}

	public void modifyText(ModifyEvent arg0) {
		Object aux = arg0.getSource();
		if (aux == jLeft) {
			Rectangle tmpR;
			tmpR = cGauge.getNumericGauge().getCustomBounds();
			tmpR.x = (Integer) jLeft.getSelection();
			cGauge.getNumericGauge().setCustomBounds(tmpR);
			cGauge.getChart().getParent().refreshControl();
		} else if (aux == jTop) {
			Rectangle tmpR;
			tmpR = cGauge.getNumericGauge().getCustomBounds();
			tmpR.y = jTop.getSelection();
			cGauge.getNumericGauge().setCustomBounds(tmpR);
			cGauge.getChart().getParent().refreshControl();
		}
	}
}
