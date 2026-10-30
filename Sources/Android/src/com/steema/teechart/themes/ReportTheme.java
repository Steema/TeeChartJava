package com.steema.teechart.themes;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.DashStyle;
import com.steema.teechart.styles.MarksTail;
import com.steema.teechart.styles.Series;

/**
 * Created by abde on 29/02/16.
 */
public class ReportTheme extends TeeChartTheme {

	private static final long serialVersionUID = 1L;

	public ReportTheme(IBaseChart c) {
		super(c);
	}

	public void apply() {
		super.apply();
		chart.getAspect().setSmoothingMode(true);

		chart.getWalls().getBack().getGradient().setEndColor(Color.YELLOW);
		chart.getWalls().getBack().getGradient().setMiddleColor(Color.EMPTY);
		chart.getWalls().getBack().getGradient().setStartColor(Color.WHITE);
        chart.getPanel().getBrush().setColor(Color.WHITE);
        chart.getPanel().setColor(Color.WHITE);

        chart.getHeader().getFont().setColor(Color.fromArgb(0xFF000080));
		chart.getHeader().getFont().setName("Verdana");
		chart.getHeader().getPen().setVisible(true);

		chart.getLegend().getFont().setName("Verdana");
		chart.getLegend().getSymbol().getPen().setVisible(true);

		chart.getPanel().setColor(Color.WHITE);
		chart.getPanel().getGradient().setVisible(false);
		chart.getPanel().getGradient().setEndColor(Color.WHITE);
		chart.getPanel().getGradient().setMiddleColor(Color.fromArgb(0xFFEAEAEA));

		chart.getWalls().getBack().setTransparent(false);
        chart.getWalls().getBack().setColor(Color.WHITE);
        //chart.getWalls().getBack().getGradient().setDirection(GradientDirection.VERTICAL);
        //chart.getWalls().getBack().getGradient().setEndColor(Color.WHITE);
        //chart.getWalls().getBack().getGradient().setStartColor(Color.fromArgb(0xFFEAEAEA));
        chart.getWalls().getBack().getGradient().setVisible(false);//true);
		chart.getWalls().getRight().setColor(Color.fromArgb(0xFFC0C0C0));

		chart.getAspect().setView3D(false);

		for (int t = 0; t < chart.getSeriesCount(); ++t) {
			doChangeSeries(chart.getSeries(t));
		}

		for (int t = 0; t < chart.getAxes().getCount(); ++t) {
			changeAxis(chart.getAxes().getAxis(t));
		}
	}

	/**
	 * Gets descriptive text.
	 *
	 * @return String
	 */
	public String getDescription() {
		return "Report";
	}

	public void changeAxis(Axis chartAxis) {
		chartAxis.getAxisPen().setColor(Color.fromArgb(0xFF404040));
		chartAxis.getGrid().setColor(Color.fromArgb(0xFFA9A9A9));
		chartAxis.getGrid().setStyle(DashStyle.SOLID);
		chartAxis.getLabels().getFont().setName("Verdana");
		chartAxis.getLabels().getFont().setSize(15);
		chartAxis.getTicksInner().setColor(Color.fromArgb(0xFFA9A9A9));
		chartAxis.getTicks().setLength(4);
		chartAxis.getTitle().getFont().setName("Verdana");
	}

	public void doChangeSeries(Series series) {
		series.getMarks().getArrow().setVisible(false);
		series.getMarks().getArrow().setWidth(8);
		series.getMarks().setArrowLength(chart.getGraphics3D().sizeToDIP(7));
		series.getMarks().getFont().setName("Verdana");
		series.getMarks().getGradient().setVisible(false);
		series.getMarks().setFollowSeriesColor(false);
		series.getMarks().setBackColor(Color.WHITE);
		series.getMarks().setTransparent(false);
		series.getMarks().getFont().setColor(Color.fromArgb(0xFF111111));
		series.getMarks().getFrame().setColor(Color.fromArgb(0xFF111111));
		series.getMarks().getShadow().setVisible(false);
		series.getMarks().getBrush().setVisible(true);
		series.getMarks().setTransparency(0);
		series.getMarks().getBrush().setColor(Color.WHITE);
		series.getMarks().getPen().setColor(series.getColor());
		series.getMarks().setMarkerTail(MarksTail.WithPointer);
	}
}
