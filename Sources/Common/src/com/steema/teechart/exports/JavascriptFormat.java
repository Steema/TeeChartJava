package com.steema.teechart.exports;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.Title;
import com.steema.teechart.axis.Axes;
import com.steema.teechart.drawing.ChartFont;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.drawing.GradientDirection;
import com.steema.teechart.languages.Language;
import com.steema.teechart.legend.Legend;
import com.steema.teechart.legend.LegendAlignment;
import com.steema.teechart.misc.Utils;
import com.steema.teechart.styles.Area;
import com.steema.teechart.styles.Bar;
import com.steema.teechart.styles.Bubble;
import com.steema.teechart.styles.Candle;
import com.steema.teechart.styles.Donut;
import com.steema.teechart.styles.HorizArea;
import com.steema.teechart.styles.HorizBar;
import com.steema.teechart.styles.HorizLine;
import com.steema.teechart.styles.Line;
import com.steema.teechart.styles.Pie;
import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.SeriesCollection;

public class JavascriptFormat extends ImageExportFormat {

	private static final long serialVersionUID = 1L;
	private String fileName;
	private String canvasName;
	private String sourceScript;
	private String chartName;
	private String[] customCode;
	private boolean doFullPage;
	private OutputStream istream;

	public JavascriptFormat(IBaseChart chart) {
		super(chart);
		initVars();
		fileExtension = "html";
	}

	public void initVars() {
		canvasName = "canvas1";
		chartName = "chart1";
		doFullPage = true;
		sourceScript = "http://www.steema.com/files/public/teechart/html5/jscript/src";
	}

	@Override
	public void save(String name) throws FileNotFoundException {
		setWidth((chart.getWidth() > 0) ? chart.getWidth() : 600);
		setHeight((chart.getHeight() > 0) ? chart.getHeight() : 400);

		fileName = name;
		istream = new FileOutputStream(fileName);

		addChartToStream();
		try {
			istream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void addHeader() {
		if (doFullPage) {
			addToStream("<HTML>");
			addToStream("<head>");
			addToStream("<title>" + chart.getHeader().getText().replace(" ", "").trim() + "</title>");
			addToStream("<script src=\"" + sourceScript + "/teechart.js\" type=\"text/javascript\"></SCRIPT>");
			addToStream("<script src=\"" + sourceScript + "/teechart-extras.js\" type=\"text/javascript\"></SCRIPT>");
			addToStream("<script type=\"text/javascript\">");
			addToStream("var " + chartName + ";");
			addToStream("function draw() {");
		}
	}

	private void addChartToStream() {
		addHeader();

		addToStream("  " + chartName + "=new Tee.Chart(\"" + canvasName + "\");");

		emitGradient("  " + chartName + ".panel.format.gradient", chart.getPanel().getGradient());
		emitGradient("  " + chartName + ".walls.back.format.gradient", chart.getWalls().getBack().getGradient());
		emitTitle("  " + chartName + ".title", chart.getHeader());
		emitTitle("  " + chartName + ".footer", chart.getFooter());
		emitSeriesList(chartName + ".", chart.getSeries());

		if (!emptiedChart) {
			emitLegend("  " + chartName + ".legend", chart.getLegend());
			emitAxes("  " + chartName + ".axes", chart.getAxes());
			emitCustomCode();
		}

		addToStream("  " + chartName + ".draw();");

		if (doFullPage) {
			addToStream("}");
			addToStream("</script>");
			addToStream("</HEAD>");
			addToStream("<BODY onload=\"draw()\">");
			addToStream("<canvas id=\"" + canvasName + "\" width=\"" + getWidth() + "\" height=\"" + getHeight() + "\">");
			addToStream("This browser does not seem to support HTML5 Canvas.");
			addToStream("</canvas>");
			addToStream("</BODY>");
			addToStream("</HTML>");
		}
	}

	private String gradientDirection(GradientDirection lGradient) {
		if (lGradient == GradientDirection.BACKDIAGONAL)
			return "diagonalup";
		else if (lGradient == GradientDirection.FORWARDDIAGONAL)
			return "diagonaldown";
		else if (lGradient == GradientDirection.HORIZONTAL)
			return "leftright";
		else if (lGradient == GradientDirection.VERTICAL)
			return "topbottom";
		else
			return "bottomtop";
	}

	private String gradientColors(Color startColor, Color endColor) {
		return "\"#" + Utils.colorToHex(startColor, false) + "\", \"#" + Utils.colorToHex(endColor, false) + "\"";
	}

	private void emitGradient(String tag, Gradient aGradient) {
		addToStream(tag + ".colors = [" + gradientColors(aGradient.getStartColor(), aGradient.getEndColor()) + "];");
		addToStream(tag + ".direction = \"" + gradientDirection(aGradient.getDirection()) + "\";");
		addToStream(tag + ".visible = " + toBool(aGradient.getVisible()) + ";");
	}

	private void emitStrings(String tag, String[] aStrings) {
		String tmp;

		tmp = tag + "=\"";

		for (int t = 0; t < aStrings.length; t++) {
			if (t > 0)
				tmp = tmp + '\n';
			tmp = tmp + aStrings[t];
		}

		addToStream(tmp + "\";");
	}

	private String FontStyle(ChartFont aStyle) {
		String result = "";
		if (aStyle.getBold())
			result = result + " bold";
		if (aStyle.getItalic())
			result = result + " italic";
		if (result != "")
			result = result + " ";
		return result;
	}

	private void emitFont(String tag, ChartFont aFont) {
		addToStream(tag + ".style=\"" + String.valueOf(Math.round(aFont.getSize() * 96.0 / 72)) + "px " + FontStyle(aFont) + aFont.getName() + "\";");
		addToStream(tag + ".fill=\"#" + Utils.colorToHex(aFont.getColor(), false) + "\";");
	}

	private void emitTitle(String tag, Title aTitle) {
		if (aTitle.getLines() != null) {
			emitStrings(tag + ".text", aTitle.getLines());
			emitFont(tag + ".format.font", aTitle.getFont());
		}
	}

	private void emitSeriesList(String tag, SeriesCollection aSeries) {
		for (int t = 0; t < aSeries.size(); t++)
			emitSeries(tag, aSeries.getSeries(t));
	}

	private void addNew(String tag, Series series, String aType) {
		addToStream("  var Series" + chart.getSeriesIndexOf(series) + "=" + tag + "addSeries(new Tee." + aType + "());");
	}

	private String doubles(int count, double[] aList) {
		String result = "";
		DecimalFormat numFormat = (DecimalFormat) NumberFormat.getInstance(new Locale("en-US"));
		numFormat.setGroupingUsed(false);

		if (count > 0) {
			result = numFormat.format(aList[0]);
			for (int t = 1; t < count; t++)
				result += "," + numFormat.format(aList[t]);
		}

		return result;
	}

	private String colors(Series series) {
		String result = "#" + Utils.colorToHex(series.getValueColor(0), false);
		for (int t = 1; t < series.getCount() - 1; t++)
			result = result + ",#" + Utils.colorToHex(series.getValueColor(t), false);

		return result;
	}

	private String labels(Series series) {
		String result = "\"" + series.getLabels().getString(0) + "\"";
		for (int t = 1; t < series.getCount() - 1; t++)
			result = result + ",\"" + series.getLabels().getString(t) + "\"";

		return result;
	}

	private String toBool(Boolean value) {
		return value ? "true" : "false";
	}

	private String direction(Legend aLegend) {
		if (aLegend.getAlignment() == LegendAlignment.LEFT)
			return "left";
		else if (aLegend.getAlignment() == LegendAlignment.RIGHT)
			return "right";
		else if (aLegend.getAlignment() == LegendAlignment.TOP)
			return "top";
		else
			return "bottom";
	}

	private void emitCustomCode() {
		if (customCode != null)
			for (int i = 0; i < customCode.length; i++)
				addToStream(customCode[i]);
	}

	private void emitLegend(String tag, Legend aLegend) {
		addToStream(tag + ".position=\"" + direction(aLegend) + "\";");
		emitFont(tag + ".format.font", aLegend.getFont());
		emitGradient(tag + ".format.gradient", aLegend.getGradient());
	}

	private void emitAxes(String tag, Axes axes) {
		emitFont(tag + ".left.labels.format.font", axes.getLeft().getLabels().getFont());
		emitFont(tag + ".bottom.labels.format.font", axes.getBottom().getLabels().getFont());
		emitFont(tag + ".right.labels.format.font", axes.getRight().getLabels().getFont());
		emitFont(tag + ".top.labels.format.font", axes.getTop().getLabels().getFont());
	}
	
	private boolean emptiedChart = false;

	private void emitEmptyChart() {
		try {
			istream = new FileOutputStream(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		addHeader();

		addToStream("  " + chartName + "=new Tee.Chart(\"" + canvasName + "\");");
		addToStream("  " + "a2=new Tee.Annotation(" + chartName + ");");
		addToStream("  " + "a2.format.fill=\"orange\";");
		addToStream("  " + "a2.format.stroke.color=\"blue\";");
		addToStream("  " + "a2.format.font.style=\"18px Arial\";");
		addToStream("  " + "a2.format.font.fill=\"brown\";");
		addToStream("  " + "a2.text=\"" + Language.getString("JScriptNonSupportMsg") + "\";");
		addToStream("  " + "a2.position.x=100;");
		addToStream("  " + "a2.position.y=50;");
		addToStream("  " + chartName + ".tools.add(a2);");
	}

	private void emitSeries(String tag, Series series) {

		String tmp;

		if (series.getClass() == Line.class)
			addNew(tag, series, "Line");
		else if (series.getClass() == Area.class)
			addNew(tag, series, "Area");
		else if (series.getClass() == Bar.class)
			addNew(tag, series, "Bar");
		else if (series.getClass() == HorizLine.class)
			addNew(tag, series, "HorizLine");
		else if (series.getClass() == HorizArea.class)
			addNew(tag, series, "HorizArea");
		else if (series.getClass() == HorizBar.class)
			addNew(tag, series, "HorizBar");
		else if (series.getClass() == Pie.class)
			addNew(tag, series, "Pie");
		else if (series.getClass() == Bubble.class)
			addNew(tag, series, "Bubble");
		else if (series.getClass() == Donut.class)
			addNew(tag, series, "Donut");
		else if (series.getClass() == Candle.class)
			addNew(tag, series, "Candle");
		else { // not implemented yet. write out empty chart
			emptiedChart = true;
			emitEmptyChart();
			return;
		}

		tmp = "  Series" + chart.getSeriesIndexOf(series) + '.';

		addToStream(tmp + "title=\"" + series.toString() + "\";");

		if (!series.getColor().isEmpty())
			addToStream(tmp + "format.fill=\"#" + Utils.colorToHex(series.getColor(), false) + "\";");

		if (series.getColorEach())
			addToStream(tmp + "colorEach=\"yes\";");
		else
			addToStream(tmp + "colorEach=\"no\";");

		if (series.getCount() > 0) {
			addToStream(tmp + "data.values=[" + doubles(series.getCount(), series.getMandatory().getValues()) + "];");

			if (series.hasNoMandatoryValues())
				addToStream(tmp + "data.x=[" + doubles(series.getCount(), series.getNotMandatory().getValues()) + "];");

			if (series.hasColors())
				addToStream(tmp + "palette=[" + colors(series) + "];");

			if (series.hasLabels())
				addToStream(tmp + "data.labels=[" + labels(series) + "];");
		}

		addToStream(tmp + "marks.visible=" + toBool(series.getMarks().getVisible()) + ";");
	}

	protected void addToStream(final String text) {
		String linetext = text + "\n";
		try {
			istream.write(linetext.getBytes());
		} catch (IOException ex) {
		}
	}

	public String getSourceScriptPath() {
		return sourceScript;
	}

	public void setSourceScriptPath(String s) {
		sourceScript = s;
	}

	public String getChartName() {
		return chartName;
	}

	public void setChartName(String name) {
		chartName = name;
	}

	public String[] getCustomCode() {
		return customCode;
	}

	public void setCustomCode(String[] code) {
		customCode = code;
	}
}
