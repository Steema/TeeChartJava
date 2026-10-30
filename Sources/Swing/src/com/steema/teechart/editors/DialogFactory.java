/*
 * DialogFactory.java
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 */

package com.steema.teechart.editors;

import com.steema.teechart.Chart;
import com.steema.teechart.Shadow;
import com.steema.teechart.TextShape;
import com.steema.teechart.axis.Axes;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.legend.Legend;
import com.steema.teechart.styles.Series;
import com.steema.teechart.tools.Tool;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 */
public class DialogFactory {

    public static void showModal(Object object) {
        showModal((String)null, object);
    }

    public static void showModal(String title, Object object) {
        showModal((JFrame)null, title, object);
    }

    public static void showModal(JFrame owner, Object object) {
        showModal(owner, null, object);
    }

    public static void showModal(JFrame owner, String title, Object object) {
        JDialog d = getEditDialog(owner, object);
        if (d != null) {
            if (title != null) {
                d.setTitle(title);
            }
            d.setModal(true);
            d.setVisible(true);
            if (owner != null) {
                d.setLocationRelativeTo(owner);
            }
        }
    }

    public static JDialog getEditDialog(Axes axes) {
        return getEditDialog((JFrame)null, axes);
    }

    public static JDialog getEditDialog(JFrame owner, Axes axes) {
        ChartEditor dialog = new ChartEditor(owner, (Chart)axes.getChart());
        dialog.getEditor().selectAxes();
        return dialog;
    }

    public static JDialog getEditDialog(Axis axis) {
        return getEditDialog((JFrame)null, axis);
    }

    public static JDialog getEditDialog(JFrame owner, Axis axis) {
        ChartEditor dialog = new ChartEditor(owner, (Chart)axis.getChart());
        dialog.getEditor().selectAxis(axis);
        return dialog;
    }

    public static JDialog getEditDialog(Chart chart) {
        return getEditDialog((JFrame)null, chart);
    }

    public static JDialog getEditDialog(JFrame owner, Chart chart) {
        return new ChartEditor(owner, chart);
    }

    public static JDialog getEditDialog(ChartBrush brush) {
       return getEditDialog((JFrame)null, brush);
    }

    public static JDialog getEditDialog(JFrame owner, ChartBrush brush) {         //TODO
        return new BrushEditor(brush);
       //return new BaseEditorDialog(owner, (JPanel)new BrushEditor(brush));
    }

    public static JDialog getEditDialog(Gradient gradient) {
        return getEditDialog((JFrame)null, gradient);
    }

    public static JDialog getEditDialog(JFrame owner, Gradient gradient) {
        return new BaseEditorDialog(owner, (JPanel)new GradientEditor(gradient));
    }

    public static JDialog getEditDialog(Legend legend) {
        return getEditDialog((JFrame)null, legend);
    }

    public static JDialog getEditDialog(JFrame owner, Legend legend) {
        ChartEditor dialog = new ChartEditor(owner, (Chart)legend.getChart());
        dialog.getEditor().selectLegend();
        return dialog;
    }

    public static JDialog getEditDialog(Series series) {
        return getEditDialog((JFrame)null, series);
    }

    public static JDialog getEditDialog(JFrame owner, Series series) {
        ChartEditor dialog = new ChartEditor(owner, (Chart)series.getChart());
        dialog.getEditor().selectSeries(series);
        return dialog;
    }

    public static JDialog getEditDialog(Shadow shadow) {
        return getEditDialog((JFrame)null, shadow);
    }

    public static JDialog getEditDialog(JFrame owner, Shadow shadow) {
        return new BaseEditorDialog(owner, (JPanel)new ShadowEditor(shadow));
    }

    public static JDialog getEditDialog(TextShape shape) {
        return getEditDialog((JFrame)null, shape);
    }

    public static JDialog getEditDialog(JFrame owner, TextShape shape) {
        return new BaseEditorDialog(owner, (JPanel)new ShapeEditor(shape));
    }

    public static JDialog getEditDialog(Tool tool) {
        return getEditDialog((JFrame)null, tool);
    }

    public static JDialog getEditDialog(JFrame owner, Tool tool) {
        ChartEditor dialog = new ChartEditor(owner, (Chart)tool.getChart());
        dialog.getEditor().selectTool(tool);
        return dialog;
    }

    public static JDialog getEditDialog(Object object) {
        return getEditDialog((JFrame)null, object);
    }

    public static JDialog getEditDialog(JFrame owner, Object object) {
        JDialog d = null;
        if (object instanceof Axes) {
            d = getEditDialog(owner, (Axes)object);
        } else if (object instanceof Axis) {
            d = getEditDialog(owner, (Axis)object);
        } else if (object instanceof Chart) {
            d = getEditDialog(owner, (Chart)object);
        } else if (object instanceof ChartBrush) {
            d = getEditDialog(owner, (ChartBrush)object);
        } else  if (object instanceof Series) {
            d = getEditDialog(owner, (Series)object);
        } else  if (object instanceof Gradient) {
            d = getEditDialog(owner, (Gradient)object);
        } else  if (object instanceof Legend) {
            d = getEditDialog(owner, (Legend)object);
        } else if (object instanceof Shadow) {
            d = getEditDialog(owner, (Shadow)object);
        } else if (object instanceof TextShape) {
            d = getEditDialog(owner, (TextShape)object);
        } else  if (object instanceof Tool) {
            d = getEditDialog(owner, (Tool)object);
        };
        return d;
    }
}

