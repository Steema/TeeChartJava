package com.steema.teechart.styles;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.net.URL;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.ChartFont;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Point;

import com.steema.teechart.functions.Function;
import com.steema.teechart.Cursor;
import com.steema.teechart.Rectangle;
import com.steema.teechart.DateTime;
import com.steema.teechart.DateTimeStep;
import com.steema.teechart.TeeBase;
import com.steema.teechart.legend.LegendTextStyle;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.axis.Axis;
import com.steema.teechart.axis.CustomAxes;
import com.steema.teechart.Aspect;
import com.steema.teechart.events.ChartDrawEvent;
import com.steema.teechart.TextShape;
import com.steema.teechart.MultiLine;
import com.steema.teechart.editors.gallery.Gallery;

import com.steema.teechart.data.SeriesSource;
import com.steema.teechart.data.DataSeriesSource;

import com.steema.teechart.ChartException;
import com.steema.teechart.Comparator;
import com.steema.teechart.ImageMode;
import com.steema.teechart.PointDouble;
import com.steema.teechart.events.FrameworkMouseEvent;
import com.steema.teechart.events.EventListenerList;
import com.steema.teechart.events.SeriesMouseEvent;
import com.steema.teechart.events.SeriesMouseListener;
import com.steema.teechart.events.SeriesPaintListener;
import com.steema.teechart.Swapper;
import com.steema.teechart.misc.Utils;
import com.steema.teechart.languages.Language;
import java.lang.reflect.*;

/**
 * 
 * <p>Title: Series class</p>
 * 
 * <p>Description: The base class for all TeeChart Series styles.</p>
 * 
 * <p>Copyright (c) 2005-2019 by Steema Software. All Rights Reserved.</p>
 * 
 * <p>Company: Steema Software</p>
 * 
 */
public class Series extends TeeBase implements ISeries, Cloneable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constant field for internal use.
	 */
	public static final int AUTODEPTH = -1;
	/**
	 * Constant field for internal use.
	 */
	public static final int AUTOZORDER = -1;
	transient protected EventListenerList listenerList;
	transient protected MarkTextResolver customMarkText;

	public void setMarkTextResolver(MarkTextResolver resolver) {
		if (resolver != null) {
			this.customMarkText = resolver;
		} else {
			removeMarkTextResolver();
		}
	}

	public void removeMarkTextResolver() {
		customMarkText = MARKTEXT_RESOLVER;
	}

	public void addSeriesPaintListener(SeriesPaintListener l) {
		listenerList.add(SeriesPaintListener.class, l);
	}

	public void removeSeriesPaintListener(SeriesPaintListener l) {
		listenerList.remove(SeriesPaintListener.class, l);
	}

	protected void fireSeriesPaint(ChartDrawEvent e) {
		final Object[] listeners = listenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == SeriesPaintListener.class) {
				switch (e.getDrawPart()) {
				// SERIES
				case 3: {
					if (e.getID() == ChartDrawEvent.PAINTING) {
                            ((SeriesPaintListener) listeners[i + 1]).seriesPainting(e);
					} else if (e.getID() == ChartDrawEvent.PAINTED) {
                            ((SeriesPaintListener) listeners[i + 1]).seriesPainted(e);
					}
					break;
				}
				}
			}
		}
	}

	public void addSeriesMouseListener(SeriesMouseListener l) {
		listenerList.add(SeriesMouseListener.class, l);
	}

	public void removeSeriesMouseListener(SeriesMouseListener l) {
		listenerList.remove(SeriesMouseListener.class, l);
	}

	protected void fireSeriesMouseEvent(SeriesMouseEvent se) {
		final Object[] listeners = listenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == SeriesMouseListener.class) {
				switch (se.getID()) {
				case SeriesMouseEvent.SERIES_CLICKED: {
					((SeriesMouseListener) listeners[i + 1]).seriesClicked(se);
					break;
				}
				case SeriesMouseEvent.SERIES_ENTERED: {
					((SeriesMouseListener) listeners[i + 1]).seriesEntered(se);
					break;
				}
				case SeriesMouseEvent.SERIES_EXITED: {
					((SeriesMouseListener) listeners[i + 1]).seriesExited(se);
					break;
				}
				}
			}
		}
	}

	protected void doClickPointer(int valueIndex, int x, int y) {
        fireSeriesMouseEvent(new SeriesMouseEvent(this, SeriesMouseEvent.SERIES_CLICKED, valueIndex, new Point(x, y)));
	}

	protected boolean hasListenersOf(Class listener) {
		final Object[] listeners = listenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == listener) {
				return true;
			}
		}
		return false;
	}
	// #region Private variables
	protected ChartBrush bBrush = new ChartBrush(null, Color.EMPTY);
	protected boolean bActive = true;
	private String title = "";
	transient private int updating;
	// Axes
	private HorizontalAxis horizontalAxis = HorizontalAxis.BOTTOM;
	private VerticalAxis verticalAxis = VerticalAxis.LEFT;
	private Axis customHorizAxis, customVertAxis;
	/**
	 * Returns the horizontal axis associated with the series.
	 */
	transient private Axis horizAxis;

	public Axis getHorizAxis() {
		return horizAxis;
	}
	transient private Axis vertAxis;

	public Axis getVertAxis() {
		return vertAxis;
	}

	// Values, Colors and Labels
	transient private ValuesLists valuesList;
	protected ValueList vxValues;
	protected ValueList vyValues;
	protected StringList sLabels;
	protected ColorList iColors;
	protected boolean bColorEach;
	private int zOrder = AUTOZORDER;
	transient public int iZOrder;
	transient private boolean isMouseInside;
	transient private ValueListOrder iLabelOrder;
	private int depth = Series.AUTODEPTH;
	private boolean showInLegend = true;
	transient protected ValueList mandatory;
	transient protected ValueList notMandatory;
	public boolean manualData;
	protected SeriesMarks marks;
	transient protected int startZ, middleZ, endZ;
	transient public boolean drawBetweenPoints = true;
	transient protected boolean yMandatory = true;
	transient boolean allowSinglePoint = true;
	/**
     * Returns True when the Series needs axes to display points.
     * For example, Pie Series returns False (it does not need axes).
	 */
	transient public boolean useAxis = true;
	transient protected boolean useSeriesColor = true;
	transient protected boolean calcVisiblePoints = true;
	protected String valueFormat = Language.getString("DefValueFormat");
	protected String labelMember = "";
	protected String colorMember = "";
	protected String percentFormat = Language.getString("DefPercentFormat");
	transient protected DecimalFormat percentDecimal;
	private Function function;
	/**
	 * Returns true if the series has ZValues.
	 */
	transient public boolean hasZValues;
	transient protected int firstVisible, lastVisible;
	private Cursor cursor = Cursor.DEFAULT;
	transient protected int iNumSampleValues;

	// Prevent instantiation
	private Series() {
		this((IBaseChart) null);
	}

	// Prevent instantiation
	protected Series(IBaseChart c) {
		super(c);

		setColor(Color.EMPTY);

		checkValuesList();

		vxValues = new ValueList(this, Language.getString("ValuesX"));
		vxValues.setOrder(ValueListOrder.ASCENDING);
		vyValues = new ValueList(this, Language.getString("ValuesY"));

		seriesFormat = new DecimalFormat(valueFormat);

		defaultNull = 0.0;

		readResolve();

		if (chart != null) {
			bBrush.setChart(chart);
			chart.addSeries(this);
			added();
		}
	}

	public ValueList getNotMandatory() {
		return notMandatory;
	}

	public ValueList getMandatory() {
		return mandatory;
	}

	public void dispose() {
		if (function != null) {
			function.setSeries(null);
		}
		setDataSource(null);
		setChart(null);
	}

	/**
	 * Creates a new series from the Class type of an existing series object.
	 * 
	 * @param type
	 *            Class
	 * @return Series
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	static public Series newFromType(final Class type)
			throws InstantiationException, IllegalAccessException {
		return (Series) (type.newInstance());
	}

	private void initMandatory() {
		mandatory = vyValues;
		notMandatory = vxValues;
	}

	private void checkValuesList() {
		if (valuesList == null) {
			valuesList = new ValuesLists();
		}
	}

	protected Object readResolve() {

		checkValuesList();

		listenerList = new EventListenerList();
		customMarkText = MARKTEXT_RESOLVER;

		drawBetweenPoints = true;
		yMandatory = true;
		allowSinglePoint = true;

		useAxis = true;

		useSeriesColor = true;
		calcVisiblePoints = true;

        percentDecimal = new DecimalFormat(Language.getString("DefPercentFormat"));

		initMandatory();
		recalcGetAxis();
		return this;
	}

	/**
	 * Creates a new Series object and sets the Name, Chart, Sub-Style and
	 * Function methods.<br>
	 * This is an internal method, you should seldomly use it in your
	 * applications.
	 * 
     * @param chart IBaseChart
     * @param type Class
     * @param aFunction Class
     * @param subIndex int
	 * @return Series
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	static public Series createNewSeries(IBaseChart chart, final Class type,
            final Class aFunction, int subIndex) throws
            InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
		return createNewSeries(chart, type, aFunction, subIndex, null);
	}

	/**
	 * Creates a new Series object and sets the Name, Chart, Sub-Style and
	 * Function methods.<br>
	 * This is an internal method, you should seldomly use it in your
	 * applications.
	 * 
     * @param chart IBaseChart
     * @param type Class
     * @param aFunction Class
     * @param subIndex int
     * @param tag Object
	 * @return Series
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	static public Series createNewSeries(IBaseChart chart, final Class type,
            final Class aFunction, int subIndex, Object tag) throws
            InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
		// if (AOwner==null) AOwner=chart;

		Series result = null;

		if (tag == null) {
			result = Series.newFromType(type);
		} else {
			Class a = tag.getClass();
			Constructor<Series> b = type.getConstructor(a);
			result = b.newInstance(tag);
		}


		// result.setName(getNewSeriesName(AOwner));

		result.setChart(chart);

		if (aFunction != null) {
			result.setFunction((Function) aFunction.newInstance());
			// result.setName(getUniqueName(series.getOwner(),Language.getString("DefaultFunctionName"));
		}

		if (subIndex != 0) {
			result.setSubGallery(subIndex);
		}

		return result;
	}

	/**
	 * Creates a new Series object and sets the Name, Chart and Function
	 * methods.
	 * 
     * @param chart IBaseChart
     * @param type Class
     * @param aFunction Class
	 * @return Series
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	static public Series createNewSeries(IBaseChart chart, final Class type,
            final Class aFunction) throws
            InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
		return createNewSeries(chart, type, aFunction, 0);
	}

	protected void doBeforeDrawValues() {
		fireSeriesPaint(new ChartDrawEvent(this, ChartDrawEvent.PAINTING,
				ChartDrawEvent.SERIES));
	}

	protected void doAfterDrawValues() {
		fireSeriesPaint(new ChartDrawEvent(this, ChartDrawEvent.PAINTED,
				ChartDrawEvent.SERIES));
	}

	public void calcZOrder() {
		if (zOrder == AUTOZORDER) {
			if (chart.getAspect().getView3D()) {
				chart.setMaxZOrder(chart.getMaxZOrder() + 1);
				iZOrder = chart.getMaxZOrder();
			} else {
				iZOrder = 0;
			}
		} else {
			chart.setMaxZOrder(Math.max(chart.getMaxZOrder(), getZOrder()));
		}
	}

	/**
	 * Accesses the stored Color Array, if created, for the Series.<br>
	 * When attaching new series to any Chart, setting TChart Series.SerieColor
     * to Color.EMPTY will make TeeChart assign a different color to each Series.
     * <br>
	 * Some Series types allow Color.EMPTY in their Pen and Brushes properties,
	 * thus forcing the use of the actual point color instead of the Pen or
	 * Brush assigned color. <br>
	 * Default value: null
	 * 
     * <p>Example:
     * <pre><font face="Courier" size="4">
	 * candleSeries.add( ...., Color.Yellow );
	 * candleSeries.getColors().setColor( 10, Color.Blue);
     * </font></pre></p>
	 * 
	 * @see Series#getColorEach
	 * @return ColorList
	 */
	public ColorList getColors() {
		if (iColors == null) {
			iColors = new ColorList((getCount() > 0) ? getCount()
					: ValueList.defaultCapacity);
		}
		return iColors;
	}

	// For XMLEncoder only
	/**
	 * Accesses the stored Color Array, if created, for the Series.<br>
	 * Default value: null
	 * 
	 * @see #getColors
     * @param value ColorList
	 */
	public void setColors(final ColorList value) {
		iColors = value;
	}

	/**
	 * Point characteristics.
	 * 
     * @param index int
	 * @return SeriesXYPoint
	 */
	public SeriesXYPoint getPoint(int index) {
		return new SeriesXYPoint(this, index);
	}

	/**
	 * Values defining horizontal point positions.<br>
	 * By default, any Series has an XValues property. This is the IValueList
	 * where the point values will be stored at runtime. Also by default,
	 * XValues is a Public method. Some derived Series publish it: Line series,
     * Bar series, Points series, etc. Some others publish it with another,
     * more friendly name: GanttSeries.StartValues, etc.  <br><br>
	 * <b>WARNING: </b><br>
     * You <b>CAN NOT Delete, Clear or Add values DIRECTLY.</b> You need to
     * call the Series equivalent methods to do this.
	 * 
	 * @return ValueList
	 */
	public ValueList getXValues() {
		return vxValues;
	}

	// For XMLEncoder only
	public void setXValues(final ValueList value) {
		vxValues = value;
	}

	/**
     * Values defining vertical point positions.<br><br>
	 * <b>WARNING: </b><br>
     * You <b>CAN NOT Delete, Clear or Add values DIRECTLY.</b> You need to
     * call the Series equivalent methods to do this.
	 * 
	 * @return ValueList
	 */
	public ValueList getYValues() {
		return vyValues;
	}

	// For XMLEncoder only
	public void setYValues(final ValueList value) {
		vyValues = value;
	}

	/**
	 * Copies all properties from one Series component to another.<br>
	 * Only the common properties shared by both source and destination Series
	 * are copied. <br>
     * The following code copies all properties from Series2 into Series1:
     * <br><br>
     * Series1.Assign( Series2 ) ;<br><br>
	 * Some Series types restore method values after assigning them. For
	 * example, Points series restores the Pointer.Visible method to true after
     * being assigned to a Line series, which has Pointers invisible by default.
     * <br>
	 * <b>Note:</b> Series events are not assigned. Series DataSource and
     * FunctionType properties are assigned.  Assign is used by
     * CloneChartSeries and ChangeSeriesType methods for example.
	 * 
     * @param source Series
	 */
	public void assign(final Series source) {
		// AssignCommonProperties(source);
		title = source.title;
		bBrush.assign(source.bBrush);
		bColorEach = source.bColorEach;
		showInLegend = source.showInLegend;

		valueFormat = source.valueFormat;
		seriesFormat = new DecimalFormat(valueFormat);

		percentFormat = source.percentFormat;
		percentDecimal = source.percentDecimal;
		bActive = source.bActive;

		if (datasource == null) {
			assignValues(source);
		} else {
			labelMember = source.labelMember;
			for (int i = 0; i < source.getValuesLists().size(); ++i) {
				getValuesLists().getValueList(i).valueSource = source
						.getValuesLists().getValueList(i).valueSource;
			}
		}

		checkDataSource();
	}

	public boolean getAllowSinglePoint() {
		return allowSinglePoint;
	}

	static public Series assignDispose(Series s, final Series newSeries) {

		int index = s.chart.getSeriesIndexOf(s);
		newSeries.assign(s);
		s.dispose();
		s = newSeries;
		s.chart.moveSeriesTo(s, index);
		return s;
	}

	/**
	 * Replaces ASeries object with a new Series object of another class.
	 * 
     * @param s Series
     * @param newType Class
	 * @return Series
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	static public Series changeType(Series s, final Class newType)
			throws InstantiationException, IllegalAccessException,
			NoSuchMethodException, IllegalArgumentException,
			InvocationTargetException {
		if (s.getClass() != newType) { // only if different classes
			final Series newSeries = createNewSeries(s.chart, newType, null);
			if (newSeries != null) {
				s = Series.assignDispose(s, newSeries);
			}
		}
		return s;
	}

	/**
	 * Returns the steema.teechart.styles.ValuesLists object of the series.<br>
	 * It permits access to the list of TeeChartValueLists of a Series. <br>
     * Several standard Series types such as TLineSeries and  TBarSeries
     * maintain 2 ValueLists, X and Y values. Other Series such as TCandleSeries
     * maintain more lists, ie. DateValues, OpenValues, HighValues, LowValues
     * and CloseValues.
	 * 
	 * @see com.steema.teechart.styles.ValuesLists
	 * @return ValuesLists
	 */
	public ValuesLists getValuesLists() {
		return valuesList;
	}

	public ValueList valueListOfAxis(Axis a) {
		ValueList result;
		if (associatedToAxis(a)) {
			if (a.getHorizontal()) {
				result = this.vxValues;
			} else {
				result = this.vyValues;
			}
		} else {
			result = null;
		}

		return result;
	}

	public void valuesListAdd(final ValueList value) {
		checkValuesList();
		valuesList.add(value);
	}

    //Returns if a Series has "X" values (or Y values for non-vertical series like HorizBar)
	public boolean hasNoMandatoryValues() {

		if ( /* iUseNotMandatory && */(getCount() > 0)) {

			ValueList tmp = getNotMandatory();

			if ((tmp.getFirst() == 0) && (tmp.getLast() == getCount() - 1)) {

				int tmpCount = Math.min(10000, getCount());

				for (int t = 0; t < tmpCount; t++) {
					if (tmp.getValue(t) != t) {
						return true;
					}
				}
			} else {
				return true;
			}
		}

		return false;
	}

    // Returns if a Series has Colors
    public boolean hasColors() {
        Color tmpSeriesColor = getColor();

        int tmpCount = (getCount() - 1 < 10000) ? getCount() - 1 : 10000;

        for (int t = 0; t <= tmpCount; t++) {
            Color tmpColor = getValueColor(t);

            if ((!tmpColor.isEmpty()) &&
                (tmpColor != tmpSeriesColor)) {
                return true;
            }
        }
        return false;
    }

    // Returns if a Series has labels
    public boolean hasLabels() {

        if (getLabels().getCount() > 0) {

            int tmpCount = (getCount() - 1 < 10000) ?
                           getCount() - 1 :
                           10000;

            for (int t = 0; t <= tmpCount; t++) {
                if (getLabels().getString(t).length() != 0) {
                    return true;
                }
            }
        }

        return false;
    }
    
	/**
	 * Returns the URL of the associated bitmap icon for a given Series class.<br>
	 * This icon is used at ChartListBox and Series Editor dialog.
	 * 
	 * @return URL
	 */
	public URL getBitmapEditor() {
		// do not use getClass().getSimpleName() ( Java5 )
		String name = getClass().getName();
		
		if (name.indexOf("teechart.styles") == -1) //generic for custom overrides
			return Series.class.getResource("icons/line.gif");
		
		name = "icons/" + name.substring(name.lastIndexOf('.') + 1) + ".gif";
		return Series.class.getResource(name);
	}

	/**
	 * Returns the value list that the AListName parameter has.
	 * 
     * @param aListName String
	 * @return ValueList
	 */
	public ValueList getYValueList(String aListName) {
		aListName = aListName.toUpperCase();

		for (int t = 2; t < valuesList.size(); t++) {
			if (aListName.equals(valuesList.getValueList(t).name.toUpperCase())) {
				return valuesList.getValueList(t);
			}
		}

		return vyValues;
	}

	/**
	 * Specifies the custom horizontal axis for the series.<br>
     * After adding a new horizontal Custom Axis to a Chart, use
     * CustomHorizAxis to associate the Series to the Custom Axis. <br>
	 * Together with the axis PositionPercent and "stretching" methods, it's
	 * possible to have unlimited axes floating anywhere on the chart. <br>
	 * Scroll, zoom, and axis hit-detection also apply to custom-created axes.<br>
	 * Creating extra axes is only allowed at run-time, as a few lines of code
	 * are necessary.<br>
	 * Default value: null
	 * 
	 * @return Axis
	 */
	public Axis getCustomHorizAxis() {
		return customHorizAxis;
	}

	public Axis getMandatoryAxis() {
		return yMandatory ? getVertAxis() : getHorizAxis();
	}

	/**
	 * Specifies the custom horizontal axis for the series.<br>
	 * Default value: null
	 * 
     * @param value Axis
	 */
	public void setCustomHorizAxis(final Axis value) {
		customHorizAxis = value;
		horizontalAxis = (value != null) ? HorizontalAxis.CUSTOM
				: HorizontalAxis.BOTTOM;
		recalcGetAxis();
		repaint();
	}

	/**
	 * Specifies the custom horizontal axis for the series.<br>
	 * Default value: null
	 * 
     * @param value int
	 */
	public void setCustomHorizAxis(int value) {
		if (getChart() != null) {
			CustomAxes tmp = getChart().getAxes().getCustom();
			setCustomHorizAxis(tmp.getAxis(value));
		}
	}

	/**
	 * Specifies the custom vertical axis for the series.<br>
	 * Default value: null
	 * 
	 * @return Axis
	 */
	public Axis getCustomVertAxis() {
		return customVertAxis;
	}

	/**
	 * Specifies the custom vertical axis for the series.<br>
	 * Default value: null
	 * 
     * @param value int
	 */
	public void setCustomVertAxis(int value) {
		if (getChart() != null) {
			CustomAxes tmp = getChart().getAxes().getCustom();
			setCustomVertAxis(tmp.getAxis(value));
		}
	}

	/**
	 * Specifies the custom vertical axis for the series.<br>
	 * Default value: null
	 * 
     * @param value Axis
	 */
	public void setCustomVertAxis(final Axis value) {
		customVertAxis = value;
		verticalAxis = (value != null) ? VerticalAxis.CUSTOM
				: VerticalAxis.LEFT;
		recalcGetAxis();
		repaint();
	}

	/**
	 * Accesses a list of series point labels.
	 * 
	 * @return StringList
	 */
	public StringList getLabels() {
		if (sLabels == null) {
			sLabels = new StringList((getCount() > 0) ? getCount()
					: ValueList.defaultCapacity);
		}
		return sLabels;
	}

	/**
	 * Accesses a list of series point labels.
	 * 
     * @param value StringList
	 */
	public void setLabels(final StringList value) {
		sLabels = value;
	}

	/**
     * The the Datasource Label Field.
     * Default value: ""
	 * 
	 * @return String
	 */
	public String getLabelMember() {
		return labelMember;
	}

	/**
	 * Sets the Datasource Label Field.<br>
	 * Default value: ""
	 * 
     * @param value String
	 */
	public void setLabelMember(final String value) {
		if (!labelMember.equals(value)) {
			labelMember = value;
			checkDataSource();
		}
	}

	/**
	 * The the Datasource Color Field.<br>
	 * Default value: ""
	 * 
	 * @return String
	 */
	public String getColorMember() {
		return colorMember;
	}

	/**
	 * Sets the Datasource Color Field.<br>
	 * Default value: ""
	 * 
     * @param value String
	 */
	public void setColorMember(final String value) {
		if (!colorMember.equals(value)) {
			colorMember = value;
			checkDataSource();
		}
	}

	/**
	 * Returns the number of pixels for horizontal margins
	 * 
     * @param margins Margins
	 */
	public void calcHorizMargins(final Margins margins) {
		margins.min = 0;
		margins.max = 0; // abstract
	}

	/**
	 * Returns the number of pixels for vertical margins
	 * 
     * @param margins Margins
	 */
	public void calcVerticalMargins(final Margins margins) {
		margins.min = 0;
		margins.max = 0; // abstract
	}

	public void galleryChanged3D(boolean is3D) {
		chart.getAspect().setView3D(is3D);
	}

	public boolean getUseAxis() {
		return useAxis;
	}

	public boolean associatedToAxis(final Axis a) {
		return useAxis
				&& (((a.getHorizontal() && ((horizAxis == a) || (horizontalAxis == HorizontalAxis.BOTH))) || ((!a
						.getHorizontal()) && ((vertAxis == a) || (verticalAxis == VerticalAxis.BOTH)))) || a.isDepthAxis);
	}

	/**
	 * Returns a new Series, copy of this original.<br>
	 * It returns the SeriesIndex of the new Series.
	 * 
	 * @return Series
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public Series cloneSeries() {
		return (Series) clone();
	}

	protected boolean isPointInChartRect(int valueIndex) {
		Rectangle r;
		Point point = new Point(calcXPos(valueIndex), calcYPos(valueIndex));
		r = chart.getChartRect();
		return r.contains(point.x, point.y);

	}

	protected int countLegendItems() {
		return getCount();
	}

	public Object clone() {
		Class tmp = null;

		if (getFunction() != null) {
			tmp = getFunction().getClass();
		}

		try {
			Series result = Series.createNewSeries(chart, getClass(), tmp);

			// result.Assign(this);
			// add values to --> result
			// if DataSource is not null, values are already added in Assign.
			// if (result.DataSource==null)

			result.assign(this);
			// result.assignValues(this);
			return result;

		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Adds all Values from Source series to the current Series.
	 * 
     * @param source Series
	 */
	public void assignValues(final Series source) {
		final ArrayList<Series> a = new ArrayList<Series>();
		a.add(source);
		addValues(a);
	}

	protected int addChartValue(final Series source, int valueIndex) {
		double tmpX = source.getXValues().value[valueIndex];
		double tmpY = source.getYValues().value[valueIndex];

        // if we are adding values from (for example) an Horizontal Bar series...
		if (yMandatory != source.yMandatory) {
			final double tmp = tmpX;
			tmpX = tmpY;
			tmpY = tmp;
		}

		// pending: if ...FY.Order<>loNone then (inverted)

		int result = vxValues.addChartValue(tmpX);
		getYValues().insertChartValue(result, tmpY);

		// rest of lists...
		int listsCount = valuesList.size();
		if (listsCount > 2) {
			int tmp = source.valuesList.size() - 1;
			for (int t = 2; t < listsCount; t++) {
				tmpY = (t <= tmp) ? source.valuesList.getValueList(t).value[valueIndex]
						: 0;
				valuesList.getValueList(t).insertChartValue(result, tmpY);
			}
		}

		return result;
	}

	// Called when a new value is added to the series.
	protected void addedValue(final Series source, int valueIndex) {
		int tmpIndex = addChartValue(source, valueIndex);

		if (source.iColors != null) {
			getColors().setColor(tmpIndex,
					source.getColors().getColor(valueIndex));
		}

		if (source.sLabels != null) {
			getLabels().setString(tmpIndex,
					source.sLabels.getString(valueIndex));
		}

		notifyNewValue(this, tmpIndex);
	}

	// This method is called whenever the series points are added,
	// deleted, modified, etc.
	// DB : NOT sure to include it.
	static private void notifyValue(int valueIndex) {
		/*
		 * if (IUpdating==0) foreach (Series s in linkedSeries ) switch
		 * (ValueEvent) { case veClear : if (rOnClear in recalcOptions) clear();
		 * case veDelete : if (rOnDelete in recalcOptions ) if
		 * (FunctionType==null) DeletedValue(this,valueIndex); case veAdd : if
		 * (rOnInsert in recalcOptions) if (FunctionType==null)
		 * AddedValue(this,valueIndex); case veModify : if (rOnModify in
		 * recalcOptions) AddValues(this); case veRefresh: AddValues(this); }
		 */
	}

	public Cursor mouseEvent(final FrameworkMouseEvent e, Cursor c) {
		return c;
	}

	// Triggers the OnAfterAdd event when a new point is added.
	// DB : Not sure to include it in NET.
	private void notifyNewValue(final Series sender, int valueIndex) {
		/*
		 * if (OnAfterAdd!=null) OnAfterAdd(sender,valueIndex);
		 * NotifyValue(veAdd,valueIndex);
		 */
		if (bActive) {
			repaint();
		}
	}

	// Allocates memory for the ALabel String parameter and inserts it
	// into the labels list.
	private void insertLabel(int valueIndex, final String text) {
		if (text.length() != 0) {
			getLabels().setString(valueIndex, text);
		}
	}

	private void addValuesFrom(final Series source) {
		int sourceCount = source.getCount();

		for (int t = 0; t < sourceCount; t++) {
			addedValue(source, t);
		}
	}

	/**
	 * Recalculates all dependent Series points again.<br>
     * Each Series has a DataSource method. When DataSource is a valid Series
     * or DataSet component, Series get all point values from the DataSource
     * and adds them as Series points. The RefreshSeries method forces the
     * Series to Clear and get all points again from the DataSource component.
     * The Refreshing process traverses the Series tree recursively.
	 */
	public void refreshSeries() {
		if (chart != null) {
			for (int t = 0; t < chart.getSeriesCount(); t++) {
				ISeries s = chart.getSeries(t);
				if (s.hasDataSource(this)) {
					s.checkDataSource();
				}
			}
			this.repaint();
		}
	}

	/**
	 * Recalculates the function just one time, when finished adding points.
	 */
	public void beginUpdate() {
		updating++;
	}

	/**
	 * Recalculates the function just one time, when finished adding points.
	 */
	public void endUpdate() {
		updating--;
		if (updating == 0) {
			refreshSeries();
			invalidate();
		}
	}

	// Clears and adds all points values from "source" ArrayList of Series.
	protected void addValues(final ArrayList source) {

		final Series s = (Series) source.get(0); // .get(0);

		if (isValidSourceOf(s)) {
			beginUpdate(); // before Clear
			clear();

			if (function == null) {
				// "Copy Function", copy values...

				if (yMandatory != s.yMandatory) // CI Mod Dec03 #1274
				{
					vxValues.dateTime = s.vyValues.dateTime;
					vyValues.dateTime = s.vxValues.dateTime;
					vxValues.setOrder(s.vyValues.getOrder());
					vyValues.setOrder(s.vxValues.getOrder());
				} else {
					vxValues.dateTime = s.vxValues.dateTime;
					vyValues.dateTime = s.vyValues.dateTime;
					vxValues.setOrder(s.vxValues.getOrder());
					vyValues.setOrder(s.vyValues.getOrder());
				}

				addValuesFrom(s);
			} else {
				vxValues.dateTime = s.vxValues.dateTime;
				vyValues.dateTime = s.vyValues.dateTime;
				vxValues.setOrder(s.vxValues.getOrder());
				vyValues.setOrder(s.vyValues.getOrder());
				function.addPoints(source); // calculate function
			}

			endUpdate(); // propagate changes...
		}

		// #IF TEELITE: addValuesFrom((Series) source.get(0));
	}

	public void setColorEach(boolean value) {
		bColorEach = setBooleanProperty(bColorEach, value);

		if (!bColorEach) {
			for (int t = 0; t < getCount(); t++) {
				if (isNull(t)) {
					return;
				}
			}
			iColors = null;
			invalidate();
		}
	}

	public ArrayList dataSourceArray() {
		if (datasource instanceof ArrayList) {
			final ArrayList a = (ArrayList) datasource;
			if (a.get(0) instanceof Series) {
				final ArrayList result = new ArrayList();
				for (int t = 0; t < a.size(); t++) {
					result.add(a.get(t));
				}

				return result;
			}
		} /*
		 * ??? repeated from above, except above code returns a duplicated array
		 * else if (datasource instanceof ArrayList) { ArrayList a = (ArrayList)
		 * datasource; if (a.get(0) instanceof Series) { return a; } }
		 */else if (datasource instanceof Series) {
			final ArrayList result = new ArrayList();
			result.add(datasource);
			return result;
		} else if (datasource instanceof Series[]) {
			final Series[] tmp = (Series[]) datasource;

			final ArrayList result = new ArrayList();
			for (int t = 0; t < tmp.length; t++) {
				result.add(tmp[t]);
			}

			return result;
		}

		return null;
	}

	public boolean hasDataSource(final Object source) {
		if (datasource == null) {
			return false;
		} else {
			if (datasource == source) {
				return true;
			}

			final ArrayList a = dataSourceArray();
			if (a == null) {
				return false;
			} else {
				return a.contains(source);
			}

		}
	}

	public void doDoubleClick(int valueIndex, final FrameworkMouseEvent e) {
		fireSeriesMouseEvent(new SeriesMouseEvent(this,
				SeriesMouseEvent.SERIES_CLICKED, valueIndex, e));
	}

	public void doClick(int valueIndex, final FrameworkMouseEvent e) {
		fireSeriesMouseEvent(new SeriesMouseEvent(this,
				SeriesMouseEvent.SERIES_CLICKED, valueIndex, e));
	}

	/**
	 * Draws points with different preset Colors.<br>
	 * If false, all points will be drawn using the Series Series.Color color
	 * method.<br>
	 * If true, each Series point will be "colored" with its corresponding point
	 * color. The point colors are stored in the Series.PointColor array. If a
	 * point has an Color.Empty color value, then a TeeChart determined,
	 * available color value will be used to draw it.<br>
	 * Default value: false
	 * 
	 * @return boolean
	 */
	public boolean getColorEach() {
		return bColorEach;
	}

	public int getCountLegendItems() {
		return getCount();
	}

	/**
	 * For internal use.
	 * 
	 * @return int
	 */
	public int getNumGallerySeries() {
		return 2;
	}

	/**
	 * Gets descriptive text. 
	 * 
	 * @return String
	 */
	public String getDescription() {
		return "serie"; //Default description in case no override method defined
	}

	private double calcMinMaxValue(int a, int b, int c, int d) {
		double result = 0;
		Axis axis = yMandatory ? horizAxis : vertAxis;
		if (yMandatory) {
			if (axis.getMaximumOffset() != 0 || axis.getMinimumOffset() != 0) {
				if (a > c) {
					result = axis.getInverted() ? axis.calcPosPoint(c
							- axis.getMaximumOffset()) : axis.calcPosPoint(a
							- axis.getMaximumOffset());
				} else {
					result = axis.getInverted() ? axis.calcPosPoint(c
							+ axis.getMinimumOffset()) : axis.calcPosPoint(a
							+ axis.getMinimumOffset());
				}
			} else {
				result = axis.getInverted() ? axis.calcPosPoint(c) : axis
						.calcPosPoint(a);
			}
		} else {
			result = axis.getInverted() ? axis.calcPosPoint(b) : axis
					.calcPosPoint(d);
			if (axis.getMaximumOffset() != 0 || axis.getMinimumOffset() != 0) {
				if (b > d) {
					result = axis.getInverted() ? axis.calcPosPoint(b
							+ axis.getMinimumOffset()) : axis.calcPosPoint(d
							+ axis.getMinimumOffset());
				} else {
					result = axis.getInverted() ? axis.calcPosPoint(b
							- axis.getMaximumOffset()) : axis.calcPosPoint(d
							- axis.getMaximumOffset());
				}
			} else {
				result = axis.getInverted() ? axis.calcPosPoint(b) : axis
						.calcPosPoint(d);
			}
		}

		return result;
	}

	/**
	 * Returns if this series is Visible.<br>
	 * 
	 * @return boolean
	 */
	public boolean getVisible() {
		return bActive;
	}

	/**
	 * An alias to Active property. <br>
	 * Shows or Hides the component.
	 * 
     * @param value boolean
	 */
	public void setVisible(boolean value) {
		setActive(value);
	}

	/**
	 * Returns the corresponding X value of a Screen position between Axis
	 * limits.<br>
	 * The Screen position must be between Axis limits.
	 * 
     * @param screenPos int
	 * @return double
	 */
	public double xScreenToValue(int screenPos) {
		return horizAxis.calcPosPoint(screenPos);
	}

	/**
	 * Returns the corresponding Y value of a Screen position between Axis
	 * limits.<br>
	 * The resulting Value is based on the Series.GetVertAxis.
	 * 
     * @param screenPos int
	 * @return double
	 */
	public double yScreenToValue(int screenPos) {
		return vertAxis.calcPosPoint(screenPos);
	}

	public void prepareForGallery(boolean isEnabled) {
		fillSampleValues(4);
		getMarks().setVisible(false);
		getMarks().getFont().setSize(7);
		getMarks().setArrowLength(4);
		getMarks().setDrawEvery(2);
		getMarks().getCallout().setLength(4);

		setColorEach(false);
	}

	protected void prepareLegendCanvas(IGraphics3D g, int valueIndex,
			final Color backColor, final ChartBrush aBrush) {
	}

	protected void drawLegendShape(IGraphics3D g, int valueIndex,
			final Rectangle rect) {
		g.rectangle(rect); /* <-- rectangle */

		if (g.getBrush().getForegroundColor() == chart.getLegend().getColor()) {
			/* <-- color conflict ! */
			Color oldColor = g.getPen().getColor();
			if (g.getBrush().getForegroundColor() == Color.BLACK) {
				g.getPen().setColor(Color.WHITE);
			}
			g.getBrush().setVisible(false);
			g.rectangle(rect); /* <-- frame rectangle */
			g.getPen().setColor(oldColor);
		}
	}

	public Color legendItemColor(int index) {
		return getValueColor(index);
	}

	public void drawLegend(int valueIndex, final Rectangle rect) {
		drawLegend(chart.getGraphics3D(), valueIndex, rect);
	}

	private ChartBrush tmpStyle = null;

	protected void drawLegend(IGraphics3D g, int valueIndex, Rectangle rect) {

		if ((valueIndex != -1) || (!getColorEach())) {
			// set pen
			// g.Pen=pen;

			// set brush color
			final Color tmpColor = (valueIndex == -1) ? getColor()
					: legendItemColor(valueIndex);

			g.getBrush().setColor(tmpColor);

			// if not empty brush...
			if (!tmpColor.isEmpty()) {
				// set background color and style
				Color tmpBack = chart.getLegend().getColor();

				if (tmpStyle == null) {
					tmpStyle = new ChartBrush(chart);
				}

				tmpStyle.assign(bBrush); // CDI TTrack //#1482

				prepareLegendCanvas(g, valueIndex, tmpBack, tmpStyle);

				// After calling PrepareLegendCanvas, set custom symbol pen, if
				// any...
				if (chart.getLegendPen() != null) {
					g.setPen(chart.getLegendPen());
				}

				// if back color is "default", use Legend back color
				if (tmpBack.isEmpty()) {
					tmpBack = chart.getLegend().getColor();
					if (tmpBack.isEmpty()) {
						tmpBack = chart.getPanel().getColor();
					}
				}

				// if brush has no bitmap, set brush style

				// if Brush.Style!=tmpStyle )
				// {
				// if ( (Bitmap==null) || Bitmap.Empty ) /* 5.01 */
				// Brush.Style=tmpStyle;
				// }

				// final setting

				// force the use of TILED imagemode, so image always fits the
				// legendshape boundary
				tmpStyle.setImageMode(ImageMode.TILE);
				chart.setBrushCanvas(g.getBrush().getColor(), tmpStyle, tmpBack);

				// draw shape
				drawLegendShape(g, valueIndex, rect);
			}
		}
	}

	public void calcFirstLastVisibleIndex() {

		firstVisible = -1;
		lastVisible = -1;

		if (getCount() > 0) {
			int tmpLastIndex = getCount() - 1;

			if (calcVisiblePoints
					&& (notMandatory.getOrder() != ValueListOrder.NONE)) {

				/*
				 * NOTE: The code below does NOT use a "divide by 2" (bubble)
				 * algorithm because the tmpMin value might not have any
				 * correspondence with a Series point. When the Series point
				 * values are "floating" (not int) the "best" solution is to do
				 * a lineal all-traverse search. However, this code can still be
				 * optimized. It will be revisited for next coming releases.
				 */

				Rectangle r = chart.getChartRect();

				double tmpMin = calcMinMaxValue(r.x, r.y, r.getRight(),
						r.getBottom());

				firstVisible = 0;

				if (notMandatory.getFirst() <= tmpMin) {
					while (notMandatory.value[firstVisible] < tmpMin) {
						firstVisible++;
						if (firstVisible > tmpLastIndex) {
							firstVisible = -1;
							break;
						}
					}
				}

				if (firstVisible >= 0) {
					double tmpMax = calcMinMaxValue(r.getRight(),
							r.getBottom(), r.x, r.y);

					if (notMandatory.getLast() <= tmpMax) {
						lastVisible = tmpLastIndex;
					} else {
						lastVisible = firstVisible;

						while ((lastVisible < tmpLastIndex)
								&& (notMandatory.value[lastVisible] < tmpMax)) {
							lastVisible++;
						}

						while ((!drawBetweenPoints) && (lastVisible > 0)
								&& (notMandatory.value[lastVisible] > tmpMax)) {
							lastVisible--;
						}
					}
				}
			} else {
				firstVisible = 0;
				lastVisible = tmpLastIndex;
			}
		}
	}

	/**
	 * Returns the length in pixels of the longest Mark text.
	 * 
	 * @return int
	 */
	public int maxMarkWidth() {
		int tmpResult = 0;
		int count = getCount();
		for (int t = 0; t < count; t++) {
			tmpResult = Math.max(tmpResult, getMarks().textWidth(t));
		}
		return tmpResult;
	}

	/**
	 * Returns corresponding Point value suitable for displaying at Series
	 * Marks.
	 * 
     * @param valueIndex int
	 * @return double
	 */
	public double getMarkValue(int valueIndex) {
		return mandatory.value[valueIndex];
	}
	transient private DecimalFormat seriesFormat = null;

	private String formatValue(final double value) {
		try {
			return seriesFormat.format(value);
		} catch (Exception e) {
			return new DecimalFormat(Language.getString("DefValueFormat"))
					.format(value);
		}
	}

	private String labelOrValue(int valueIndex) {
		String tmpResult = (sLabels == null) ? "" : sLabels
				.getString(valueIndex);
		if (tmpResult.length() == 0) {
			tmpResult = formatValue(getMarkValue(valueIndex));
		}
		return tmpResult;
	}

	private String getAXValue(int valueIndex) {
		if (!(horizAxis == null)) {

			if (vxValues.getDateTime()) {
				String result;

				try {
					if (horizAxis.getLabels().getDateTimeFormat().equals("")) {
						result = new DateTime(vxValues.value[valueIndex])
								.toString(horizAxis
										.dateTimeDefaultFormat(horizAxis
												.getRange()));
					} else {
						result = new DateTime(vxValues.value[valueIndex])
								.toString(horizAxis.getLabels()
										.getDateTimeFormat());
					}
				} catch (Exception e) {
					result = (new DecimalFormat(
							Language.getString("DefValueFormat")))
							.format(vxValues.value[valueIndex]);
				}

				return result;

			} else {
				return formatValue(vxValues.value[valueIndex]);
			}

		} else {
			return horizAxis.getLabels().labelValue(vxValues.value[valueIndex]);
		}
	}

	private String getAYValue(int valueIndex) {
		return formatValue(getMarkValue(valueIndex));
	}

	/**
	 * Returns the String corresponding to the Series Mark text for a given
	 * ValueIndex point.<br>
	 * The Mark text depends on the Marks.Style method.<br>
	 * 
	 * @param valueIndex
	 *            int
	 * @return String
	 */
	public String getMarkText(int valueIndex) {
		String tmpResult;

		String tmp = marks.getMultiLine() ? Language.lineSeparator : " ";

		if (marks.markerStyle == MarksStyle.VALUE) {
			tmpResult = getAYValue(valueIndex);
		} else if (marks.markerStyle == MarksStyle.PERCENT) {
			tmpResult = marks.percentString(valueIndex, false);
		} else if (marks.markerStyle == MarksStyle.LABEL) {
			tmpResult = labelOrValue(valueIndex);
		} else if (marks.markerStyle == MarksStyle.LABELPERCENT) {
			tmpResult = labelOrValue(valueIndex) + tmp
					+ marks.percentString(valueIndex, false);
		} else if (marks.markerStyle == MarksStyle.LABELVALUE) {
			tmpResult = labelOrValue(valueIndex) + tmp + getAYValue(valueIndex);
		} else if (marks.markerStyle == MarksStyle.LEGEND) {
			tmpResult = chart.formattedValueLegend(this, valueIndex);
			if (!marks.getMultiLine()) {
				tmpResult = tmpResult.replace(Language.columnSeparator, " ");
			}
		} else if (marks.markerStyle == MarksStyle.PERCENTTOTAL) {
			tmpResult = marks.percentString(valueIndex, true);
		} else if (marks.markerStyle == MarksStyle.LABELPERCENTTOTAL) {
			tmpResult = labelOrValue(valueIndex) + tmp
					+ marks.percentString(valueIndex, true);
		} else if (marks.markerStyle == MarksStyle.XVALUE) {
			tmpResult = getAXValue(valueIndex);
		} else if (marks.markerStyle == MarksStyle.XY) {
			tmpResult = getAXValue(valueIndex) + tmp + getAYValue(valueIndex);
		} else if (marks.markerStyle == MarksStyle.SERIESTITLE) {
			 tmpResult = this.toString();
		} else if (marks.markerStyle == MarksStyle.POINTINDEX) {
			tmpResult = String.valueOf(valueIndex);
		} else if (marks.markerStyle == MarksStyle.PERCENTRELATIVE) {
			double pval = (valueIndex == 0) ? 1.0 : getMarkValue(valueIndex) / getMarkValue(0);
			tmpResult = percentDecimal.format(pval*100);
		} else {
			tmpResult = "";
		}

		return customMarkText.getMarkText(valueIndex, tmpResult);
	}

	/**
	 * Returns the String representation of a Index point used to draw the Mark.
	 * 
	 * @param index
	 *            int
	 * @return String
	 */
	public String getValueMarkText(int index) {
		return getMarkText(index);
	}

	private double defaultNull = 0.0;

	/**
	 * Sets or returns the value to be used as a null value within the series.
	 * 
	 * @param value
	 *            double
	 */
	public double getDefaultNullValue() {
		return defaultNull;
	}

	public void setDefaultNullValue(final double value) {
		defaultNull = value;
	}

	/**
	 * Forces the Chart to Repaint.<br>
	 * You don't normally call Repaint directly. It can be used within derived
	 * TChartSeries components when changing their properties internally .
	 */
	public void repaint() {
		invalidate();
	}

	/**
	 * Returns True when the tmpSeries parameter is of the same class.
	 * 
	 * @param s
	 *            ISeries
	 * @return boolean
	 */
	protected boolean sameClass(ISeries s) {
		return getClass() == s.getClass();
	}

	/**
	 * Sorts all points in the series using the Labels (texts) list.<br>
	 * <br>
	 * <b>Note:</b> non-mandatory values (X) are modified (they are not
	 * preserved).
	 * 
	 * @param order
	 *            ValueListOrder
	 */
	public void sortByLabels(final ValueListOrder order) {
		if (order != ValueListOrder.NONE) {
			iLabelOrder = order;

			Utils.sort(0, getCount() - 1, new Comparator() {

				public final int compare(int a, int b) {
					int result = getLabels().getString(a).compareTo(
							getLabels().getString(b));
					return (iLabelOrder == ValueListOrder.DESCENDING) ? -result
							: result;
				}
			}, getValueIndexSwapper());

			iLabelOrder = ValueListOrder.NONE;
			notMandatory.fillSequence(); // force set X values (lost them)
			invalidate();
		}
	}

	public void sortByLabels() {
		sortByLabels(ValueListOrder.ASCENDING);
	}

	/**
	 * For internal use.
	 * 
	 * @return int
	 */
	public int getStartZ() {
		return startZ;
	}

	public void setStartZ(int value) {
		startZ = value;
	}

	/**
	 * For internal use.
	 * 
	 * @return int
	 */
	public int getMiddleZ() {
		return middleZ;
	}

	public void setMarks(final SeriesMarks value) {
		marks = value;
	}

	public void setMiddleZ(int value) {
		middleZ = value;
	}

	public void setZPositions() {
		int w = getChart().getSeriesWidth3D();
		setStartZ(iZOrder * w);

		if (getDepth() == Series.AUTODEPTH) {
			setEndZ(getStartZ() + w);
		} else {
			setEndZ(getStartZ() + getDepth());
		}

		setMiddleZ((getStartZ() + getEndZ()) / 2);

		if (marks != null) {
			marks.zPosition = getMiddleZ();
		}
	}

	/**
	 * For internal use.
	 * 
	 * @return int
	 */
	public int getEndZ() {
		return endZ;
	}

	public void setEndZ(int value) {
		endZ = value;
	}

	/**
	 * Determines the Format to display point values.<br>
	 * It specifies the desired formatting string to be applied to Axis Labels.
	 * It has effect when Axis associated Series have their XValues.DateTime or
	 * YValues.DateTime is set to false. <br>
	 * For DateTime Axis labels use AxisLabels.DateTimeFormat. <br>
	 * ValueFormat is a standard formatting string specifier. <br>
	 * Chart Axis uses it to draw the axis labels. <br>
	 * Chart Series uses it to draw the Marks. <br>
	 * Default value: Language.getString("DefValueFormat")
	 * 
	 * @return String
	 */
	public String getValueFormat() {
		return valueFormat;
	}

	/**
	 * Determines the Format to display point values.<br>
	 * Default value: Language.getString("DefValueFormat")
	 * 
	 * @param value
	 *            String
	 */
	public void setValueFormat(final String value) {
		valueFormat = setStringProperty(valueFormat, value);
		seriesFormat = new DecimalFormat(valueFormat);
	}

	/**
	 * The Format to display point values as percentage.<br>
	 * PercentFormat is a standard string specifier. It is used to draw the
	 * Series Marks Percent Style figures.<br>
	 * Default value: Language.getString("DefPercentFormat")
	 * 
	 * @see Series#getValueFormat
	 * @return String
	 */
	public String getPercentFormat() {
		return percentFormat;
	}

	/**
	 * Sets the Format to display point values as percentage.<br>
	 * Default value: Language.getString("DefPercentFormat")
	 * 
	 * @param value
	 *            String
	 */
	public void setPercentFormat(final String value) {
		percentFormat = setStringProperty(percentFormat, value);
		percentDecimal = new DecimalFormat(percentFormat);
	}

	/**
	 * Horizontal axis associated to this Series.<br>
	 * Default value: HorizontalAxis.Bottom
	 * 
	 * @return HorizontalAxis
	 */
	public HorizontalAxis getHorizontalAxis() {
		return horizontalAxis;
	}

	/**
	 * Stes the Horizontal axis associated to this Series.<br>
	 * Default value: HorizontalAxis.Bottom
	 * 
	 * @param value
	 *            HorizontalAxis
	 */
	public void setHorizontalAxis(final HorizontalAxis value) {
		if (horizontalAxis != value) {
			horizontalAxis = value;
			if (horizontalAxis != HorizontalAxis.CUSTOM) {
				customHorizAxis = null;
			}
			recalcGetAxis();
			invalidate();
		}
	}

	/**
	 * Determines Vertical axis associated to this Series.<br>
	 * Default value: VerticalAxis.Left
	 * 
	 * @return VerticalAxis
	 */
	public VerticalAxis getVerticalAxis() {
		return verticalAxis;
	}

	/**
	 * Determines Vertical axis associated to this Series.<br>
	 * Default value: VerticalAxis.Left
	 * 
	 * @param value
	 *            VerticalAxis
	 */
	public void setVerticalAxis(final VerticalAxis value) {
		if (verticalAxis != value) {
			verticalAxis = value;
			if (verticalAxis != VerticalAxis.CUSTOM) {
				customVertAxis = null;
			}
			recalcGetAxis();
			invalidate();
		}
	}

	/**
	 * Returns "Value" parameter coordinate position in pixels.
	 * 
	 * @param value
	 *            double
	 * @return int
	 */
	public int calcPosValue(final double value) {
		return yMandatory ? calcYPosValue(value) : calcXPosValue(value);
	}

	/**
	 * The pixel Screen Horizontal coordinate of the ValueIndex Series value.<br>
	 * This coordinate is calculated using the Series associated Horizontal
	 * Axis.
	 * 
	 * @param index
	 *            int
	 * @return int
	 */
	public int calcXPos(int index) {
		return calcXPosValue(vxValues.value[index]);
	}

	/**
	 * The pixel Screen Horizontal coordinate of the specified Value. <br>
	 * This coordinate is calculated using the Series associated Horizontal
	 * Axis.
	 * 
	 * @param value
	 *            double
	 * @return int
	 */
	public int calcXPosValue(final double value) {
		return horizAxis.calcXPosValue(value);
	}

	/**
	 * The pixel Screen Vertical coordinate of the ValueIndex Series value.<br>
	 * This coordinate is calculated using the Series associated Vertical Axis.
	 * 
	 * @param index
	 *            int
	 * @return int
	 */
	public int calcYPos(int index) {
		return calcYPosValue(getYValues().value[index]);
	}

	/**
	 * The pixel Screen Vertical coordinate of the specified Value.<br>
	 * This coordinate is calculated using the Series associated Vertical Axis.
	 * 
	 * @param value
	 *            double
	 * @return int
	 */
	public int calcYPosValue(final double value) {
		return vertAxis.calcYPosValue(value);
	}

	public double getOriginValue(int valueIndex) {
		return getMarkValue(valueIndex);
	}

	/* Draws a point Mark using the APosition coordinates. */
	protected void drawMark(int valueIndex, final String st,
			final SeriesMarksPosition aPosition) {
		marks.internalDraw(valueIndex, getValueColor(valueIndex), st, aPosition);
	}

	protected Color getDefaultColor(int valueIndex) {
		final Color result;

		if (bColorEach) {
			final Color c = getChart().getGraphics3D().getDefaultColor(
					valueIndex);

			if (getColor().isEmpty()) {
				result = c;
			} else {
				result = c.transparentColor(bBrush.getTransparency());
			}
		} else {
			result = getColor();
		}

		return result;
	}

	/**
	 * The colour of the index point.
	 * 
	 * @param valueIndex
	 *            int
	 * @return Color
	 */
	public Color getValueColor(int valueIndex) {
		if ((iColors != null) && (iColors.size() > valueIndex)) {
			Color result = iColors.getColor(valueIndex);
			return Utils.colorIsEmpty(result) ? getDefaultColor(valueIndex)
					: result;
		} else {
			return getDefaultColor(valueIndex);
		}
	}

	protected void calculateMarkPosition(TextShape shape, final String aText,
			int xPos, int yPos, SeriesMarksPosition position) {
		com.steema.teechart.drawing.IGraphics3D g = chart.getGraphics3D();

		final MultiLine tmpMulti = chart.multiLineTextWidth(aText);
		int tmpH = tmpMulti.count;
		int tmpW = tmpMulti.width;

		if (marks.getSymbol() != null && marks.getSymbol().ShouldDraw()) {
			tmpW += g.getFontHeight();
		}

		// #if POCKET
		tmpW += Math.round(g.textWidth(Language.getString("CharForHeight")) * 0.5);
		// #else
		// tmpW += Utils.Round(g.TextWidth(Texts.CharForHeight)); //DB
		// #endif

		tmpH *= g.getFontHeight();

		// ChartPen oldPen = g.Pen.Clone() as ChartPen; //MS: v3
		// g.Pen = shape.Pen;

		if (shape.getPen().getVisible()) {
			int tmpWidth = 2 * Math.round(shape.getPen().getWidth());
			tmpW += tmpWidth;
			tmpH += tmpWidth;
		} else {
			tmpH++;
		}

		position.width = tmpW;
		position.height = tmpH;
		position.arrowTo.x = xPos;
		position.arrowTo.y = yPos;
		position.arrowFrom.x = position.arrowTo.x;
		position.arrowFrom.y = position.arrowTo.y;
		position.leftTop.x = position.arrowTo.x - (tmpW / 2);
		position.leftTop.y = position.arrowTo.y - tmpH; // + 1;

		if (marks.getSymbol() != null) {
			marks.getSymbol().setTop(position.leftTop.y + 2);
			marks.getSymbol().setLeft(position.leftTop.x + 2);
			marks.getSymbol().setBottom(
					marks.getSymbol().getTop() + g.getFontHeight() - 4);
			marks.getSymbol().setRight(
					marks.getSymbol().getLeft() + g.getFontHeight() - 4);
		}

		// g.Pen = oldPen; //MS: v3
	}

	protected int lastVisibleMark() {
		return lastVisible;
	}

    public void drawMarks() {
        IGraphics3D g = chart.getGraphics3D();
        Aspect a = chart.getAspect();
        
        SeriesMarksPosition aPos = new SeriesMarksPosition();

        boolean shouldZoomFont = (a.getView3D() && (!g.getSupports3DText())
                && a.getZoomText() && (a.getZoom() != 100));

        for (int t = firstVisible; t <= lastVisibleMark(); t++) {

            if ((t % marks.getDrawEvery()) == 0) {

                if (!isNull(t)) {

                    String st = getMarkText(t);

                    if (st.length() != 0) {
                        TextShape tmpMark = marks.markItem(t);

                        if (tmpMark.getVisible()) {
                            g.setFont(tmpMark.getFont());

                            if (shouldZoomFont) {
                                ChartFont f = g.getFont();
                                f.setSize(Math.max(
                                        1,
                                        Utils.round(0.01 * a.getZoom()
                                                * f.getSize())));
                            }

                            calculateMarkPosition(tmpMark, st, calcXPos(t), calcYPos(t), aPos);
                            drawMark(t, st, aPos);
                        }
                    }
                }
            }
        }
    }

	private boolean clipRegionCreate(boolean activeRegion, Rectangle tmpR ) {

		if (chart.canClip()) {
			chart.getGraphics3D().save();
			// tmpR.height++; out mm 27ago07 causes incr series upon series
			chart.getGraphics3D().clipCube(tmpR, 0,
					chart.getAspect().getWidth3D());
			activeRegion = true;
		}

		return activeRegion;
	}

	// Remove the clipping region
	private boolean clipRegionDone(boolean activeRegion) {
		if (activeRegion) {
                    if (chart!=null)
                      if (chart.getGraphics3D()!=null)
			chart.getGraphics3D().unClip();
			// chart.getGraphics3D().clipCube(chart.getChartBounds(), 0, 0);
			activeRegion = false;
		}

		return activeRegion;
	}

	// Draw the "Marks" of the Series
	public boolean drawMarksSeries(final Series s, boolean activeRegion) {
		if (s.getCount() > 0) {
			if ((s.marks != null) && (s.getMarks().getVisible())) {
				if (s.marks.getTailParams() == null)
					s.marks.setTailParams(new TailParams(s.chart));

				if (s.getMarks().bClip) {
					activeRegion = clipRegionCreate(activeRegion, chart.getChartRect());
				}
				s.drawMarks();
				if (s.getMarks().bClip) {
					activeRegion = clipRegionDone(activeRegion);
				}
			}
		}
		return activeRegion;
	}

	// if the ASeries parameter has the same "Z" order than the current series,
	// draw the point
	private void tryDrawSeries(ISeries s, int valueIndex) {
		if (s.getActive() && (s.getZOrder() == getZOrder())
				&& (valueIndex < s.getCount())) {
			s.drawValue(valueIndex);
		}
	}

	protected boolean drawSeriesForward(int valueIndex) {
		return !chart.getParent().invertedRotation();
	}

    protected int[] sortDrawSeries(int firstSeries, int lastSeries, int valueIndex) {
    	return null;
    };
    
	// Draw one single point (ValueIndex) for all Series
	private void drawAllSeriesValue(int valueIndex) {

		int[] orderedSeries = sortDrawSeries(chart.getSeries().indexOf(this), chart.getSeries().size(), valueIndex);

		if (orderedSeries != null) {
			try {
				if (valueIndex >= getCount() || drawSeriesForward(valueIndex)) {
					for (int i = 0; i < orderedSeries.length; i++)
						tryDrawSeries(chart.getSeries(orderedSeries[i]), valueIndex);
				} else {
					for (int i = orderedSeries.length - 1; i >= 0; i--)
						tryDrawSeries(chart.getSeries(orderedSeries[i]), valueIndex);
				}
			} finally {
				orderedSeries = null;
			}
		} else {
			if (valueIndex >= getCount() || drawSeriesForward(valueIndex)) {
				for (int i = chart.getSeries().indexOf(this); i < chart.getSeries().size(); i++)
					tryDrawSeries(chart.getSeries(i), valueIndex);
			} else {
				for (int i = chart.getSeries().size() - 1; i >= chart.getSeries().indexOf(this); i--)
					tryDrawSeries(chart.getSeries(i), valueIndex);
			}
		}
	}

	// Returns True if the series is the first one with the same ZOrder.
	// Some series allow sharing the same ZOrder (example: Stacked Bars).
	private boolean firstInZOrder() {
		if (getActive()) {
			for (int t = 0; t < chart.getSeriesCount(); t++) {
				ISeries s = chart.getSeries(t);

				if (s == this) {
					break;
				} else if (s.getActive() && (s.getZOrder() == getZOrder())) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean getHasZValues() {
		return hasZValues;
	}

	/**
	 * Returns true if there are more series that share the same Z order.<br>
	 * For example Stacked Bars.
	 * 
	 * @return boolean
	 */
	protected boolean moreSameZOrder() {
		if (chart.getAspect().getApplyZOrder()) {
			for (int t = 0; t < chart.getSeriesCount(); t++) {
				ISeries s = chart.getSeries(t);
				if (s != this) {
					if (s.getActive() && (!s.getHasZValues())
							&& (s.getZOrder() == getZOrder())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	protected void draw() {

		if (chart.getToolCheckHidden() != null) {
			if (drawValuesForward()) {
				for (int t = firstVisible; t <= lastVisible; t++) {
					chart.getToolCheckHidden().checkDrawHidden(this, t);
				}
			} else {
				for (int t = lastVisible; t >= firstVisible; t--) {
					chart.getToolCheckHidden().checkDrawHidden(this, t);
				}
			}
		} else {
			if (drawValuesForward()) {
				for (int t = firstVisible; t <= lastVisible; t++) {
					drawValue(t);
				}
			} else {
				for (int t = lastVisible; t >= firstVisible; t--) {
					drawValue(t);
				}
			}
		}
	}

	public void doBeforeDrawChart() {
	} // abstract

	/**
	 * Draws the series to the Chart Canvas.
	 * 
	 */
	public void drawSeries() {

		boolean activeRegion = false; /* <-- VERY IMPORTANT !!!! */

		if (chart.getAspect().getView3D() && moreSameZOrder()) {

			if (firstInZOrder()) {
				activeRegion = false;

				if (chart.getAspect().getClipPoints() && (!activeRegion)) {
					activeRegion = clipRegionCreate(activeRegion, chart.getChartRect());
				}

				int tmpFirst = -1;
				int tmpLast = -1;

				for (int t = chart.getSeriesIndexOf(this); t < chart
						.getSeriesCount(); t++) {

					Series s = chart.getSeries(t);

					if (s.getActive() && (s.getZOrder() == getZOrder())) {

						s.calcFirstLastVisibleIndex();

						if (s.getFirstVisible() != -1) {

							tmpFirst = (tmpFirst == -1) ? s.getFirstVisible()
									: Math.max(tmpFirst, s.getFirstVisible());
							tmpLast = (tmpLast == -1) ? s.getLastVisible()
									: Math.max(tmpLast, s.getLastVisible());

							s.doBeforeDrawValues();

						}
					}
				}

				// values
				if (tmpFirst != -1) {
					if (drawValuesForward()) {
						for (int t = tmpFirst; t <= tmpLast; t++) {
							drawAllSeriesValue(t);
						}
					} else {
						for (int t = tmpLast; t >= tmpFirst; t--) {
							drawAllSeriesValue(t);
						}
					}
				}

				// Finish Clipping Region
				activeRegion = clipRegionDone(activeRegion);

				// Marks and doAfterDrawValues
				for (int t = 0; t < chart.getSeriesCount(); t++) {
					Series s = chart.getSeries(t);
					if (s.getActive() && (s.getZOrder() == getZOrder())
							&& (s.getFirstVisible() != -1)) {
						if (!s.getMarks().getOnTop()) {
							activeRegion = drawMarksSeries(s, activeRegion);
						}
						doAfterDrawValues();
					}
				}
			}
		} else {
			calcFirstLastVisibleIndex();

			if (firstVisible != -1) {

				doBeforeDrawValues();

				if (useAxis && chart.getAspect().getClipPoints()) {
					activeRegion = clipRegionCreate(activeRegion, chart.getChartRect());
				}

				draw();

				activeRegion = clipRegionDone(activeRegion);

				if (!this.getMarks().getOnTop()) {
					activeRegion = drawMarksSeries(this, activeRegion);
				}

				doAfterDrawValues();
			}
		}
	}

	/**
	 * Obsolete.&nbsp;Use the Series.Color method instead.
	 * 
	 * @see Series#getColor
	 * @param value
	 *            Color
	 */
	protected void setSeriesColor(final Color value) {
		setColor(value);
	}

	protected boolean canAddRandomPoints() {
		return (chart != null) && chart.isDesignTime();
	}

	private Object datasource;
        protected Object oldDataSource;

	/**
	 * Object to load data from.<br>
	 * Default value: null
	 * 
	 * @return Object
	 */
	public Object getDataSource() {
		return datasource;
	}

	/**
	 * Object to load data from.<br>
	 * Default value: null
	 * 
	 * @param value
	 *            Object
	 */
	public void setDataSource(final Object value) {
		if (datasource != value) {

			// To protect when disposing the Data.SeriesSource component.

			if (datasource instanceof SeriesSource) {
				((SeriesSource) datasource).setSeries(null);
			}

			datasource = value;

			if (datasource instanceof SeriesSource) {
				((SeriesSource) datasource).setSeries(this);
			}

			// if (! (datasource is Series)) Function=null; //CI Dec 03
			if (!(datasource instanceof Series)
					&& !(datasource instanceof Series[])
					&& !(datasource instanceof ArrayList)) {
				setFunction(null);
			}

			checkDataSource();
		}
	}

	/**
	 * Adds the collection of objects that implement the IList interface.
	 * 
	 * @param list
	 *            ArrayList
	 */
	public void add(final ArrayList list) {
		for (int t = 0; t < list.size(); t++) {
			add(((Double) list.get(t)).doubleValue());
		}
	}

	public ValueList getValueList(int index) {
		return valuesList.getValueList(index);
	}

	public ValueList getValueList(final String name) {
		for (int t = 0; t < valuesList.size(); t++) {
			if (valuesList.getValueList(t).name.equals(name)) {
				return valuesList.getValueList(t);
			}
		}
		return null;
	}

	protected int getFields(ValueList otherList) {
		int fieldCount = 0;

		for (int t = 0; t < valuesList.size(); t++) {
			ValueList v = valuesList.getValueList(t);
			if (v.getDataMember().length() != 0) {
				fieldCount++;
				if (mandatory != v) {
					otherList = v;
				}
			}
		}
		return fieldCount;
	}

	// Prevents a circular relationship between series and
	// series DataSource. Raises an exception if "dest" is already "this".
	public void checkOtherSeries(Series dest) throws ChartException {
		if (dest == this) {
			throw new ChartException(Language.getString("CircularSeries"));
		} else {
			ArrayList a = dest.dataSourceArray();
			if (a != null) {
				for (int t = 0; t < a.size(); t++) {
					((Series) a.get(t)).checkOtherSeries(this);
				}
			}
		}
	}

	private void fillFromDataSource() {
		if (datasource instanceof Series) {
			// addValues((Series) datasource);

			ArrayList aList = new ArrayList();
			// aList.add(new Series[] { (Series)datasource });
			aList.add((Series) datasource);
			addValues(aList);
		} else if (datasource instanceof ArrayList) {
			ArrayList a = (ArrayList) (datasource);
			if (a.size() > 0) {
				Object o = a.get(0);
				if (o instanceof Series) {
					addValues(a);
				}
			}
		} else if (datasource instanceof Series[]) {
			Series[] tmp = (Series[]) datasource;
			ArrayList a = new ArrayList();
			for (int t = 0; t < tmp.length; t++) {
				a.add(tmp[t]);
			}
			addValues(a);
		} else if (datasource instanceof SeriesSource) {
			((SeriesSource) datasource).refreshData();
		} else {
			if (!DataSeriesSource.tryRefreshData(this)) {
				/** @todo THROWING HERE MAKES EXPLOSION */
				// throw new TeeChartException(
				// "Cannot bind to non-supported datasource: " +
				// datasource.toString());
			}
		}
	}

	/**
	 * Refreshes all Series point values, either from database Tables or Series
	 * points.<br>
	 * 
	 * You can call this method regularly if you want new or modified data to
	 * appear in realtime in the Series. The parent Chart will be repainted to
	 * reflect any changes.
	 */
	public void checkDataSource() {
		if (datasource != null) {
			fillFromDataSource();
		} else if (function != null) {
			if (function.noSourceRequired) { // For "Custom" function
				beginUpdate();
				clear();
				function.addPoints(null);
				endUpdate();
			}
		} else if (!manualData) {
			if (canAddRandomPoints()) {
				fillSampleValues();
			}
		}
		// else
		// clear();
	}

	protected void recalcGetAxis() {

		horizAxis = chart != null ? chart.getAxes().getBottom() : null;
		vertAxis = chart != null ? chart.getAxes().getLeft() : null;

		if (horizontalAxis == HorizontalAxis.TOP) {
			horizAxis = chart.getAxes().getTop();
		} else if (horizontalAxis == HorizontalAxis.CUSTOM) {
			if (customHorizAxis != null) {
				horizAxis = customHorizAxis;
			}
		}

		if (verticalAxis == VerticalAxis.RIGHT) {
			vertAxis = chart.getAxes().getRight();
		} else if (verticalAxis == VerticalAxis.CUSTOM) {
			if (customVertAxis != null) {
				vertAxis = customVertAxis;
			}
		}
	}

    private void applyThemeSeries(Series series) {
        /* getInternalTheme TO DO*/
        if (getChart().getSeriesCount() > 0 && this.getChart().getInternalTheme() != null) {
        	this.getChart().getInternalTheme().doChangeSeries(series);
        }
    	
    }
	protected void added() {
		if (getColor() != null)
		  if (getColor().isEmpty()) {
			  setColor(chart.freeSeriesColor(true));
		}
		recalcGetAxis();
		applyThemeSeries(this);
		if (title == "") title = getLanguageSpecificTitle();
		checkDataSource();
	}
	
	private String getLanguageSpecificTitle() {
		 String tmpResult = "";
		 if (chart != null) {
			 char first;
		     int count = 0;
		        
	          tmpResult = this.getDescription();
	          first = tmpResult.charAt(0);
	          if (Character.isUpperCase(first)) {
	            tmpResult = tmpResult.substring(1);
	            first = Character.toLowerCase(first);
	            tmpResult = first + tmpResult;
	          }
	          tmpResult = tmpResult.replace(" ", "");
	          tmpResult = tmpResult.replace(".", "");
	          tmpResult = tmpResult.replace("&", "");

	          for (int i=0; i<chart.getSeriesCount(); i++) {
	        	  Series s = chart.getSeries(i);
	        	  
	        	  if (s.getClass() == this.getClass()) ++count;
	          }

	          tmpResult += String.valueOf(count);
		 }
		 else
			 tmpResult = super.toString();
		 
		 return tmpResult;
	}

	/**
	 * Obsolete.&nbsp;Use the Series.Color method instead.
	 * 
	 * @see steema.teechart.styles.Series.Color
	 * 
	 * @return Color
	 */
	protected Color getSeriesColor() {
		return getColor();
	}

	/**
	 * Default color for all points.<br>
	 * The TChart Series SeriesColor method is the default color in which the
	 * Series points will be drawn. It could be any valid color. If you add
	 * points with Color.EMPTY color, then they will be drawn with the
	 * SeriesColor color. This method is the default Color associated to the
	 * Series. When you place a new Series component in a Chart, TeeChart will
	 * assign a free color to this method (a Color that no other Series in the
	 * same Chart uses). Some Series have the ColorEach boolean property.
	 * Setting this to true will force the Series to paint each point with a
	 * different color, thus without using its SeriesColor.<br>
	 * SeriesColor is also used to paint the small rectangle in Chart Legend. <br>
	 * Default value: Color.Empty
	 * 
	 * @see Series#getColorEach
	 * @return Color
	 */
	public Color getColor() {
		return bBrush.getColor();
	}

	/**
	 * Default color for all points.<br>
	 * Default value: Color.Empty
	 * 
	 * @see #getColorEach
	 * @param value
	 *            Color
	 */
	public void setColor(final Color value) {
		bBrush.setColor(value);
		if (chart != null) {
			chart.broadcastEvent(this, SeriesEventStyle.CHANGECOLOR);
		}
	}

	/**
	 * Shows or hides this series.<br>
	 * It can be changed both at design time or runtime. When hiding, all point
	 * values are preserved, so there's no need to refill the values again when
	 * showing them. The Series relatives Chart Axis are rescaled in order to
	 * accomodate changes.
	 * 
	 * @return boolean
	 */
	public boolean getActive() {
		return bActive;
	}

	/**
	 * Shows or hides this series.<br>
	 * It can be changed both at design time or runtime. When hiding, all point
	 * values are preserved, so there's no need to refill the values again when
	 * showing them. The Series relatives Chart Axis are rescaled in order to
	 * accomodate changes.
	 * 
	 * @param value
	 *            boolean
	 */
	public void setActive(boolean value) {
		bActive = setBooleanProperty(bActive, value);
		if (chart != null) {
			chart.broadcastEvent(this, SeriesEventStyle.CHANGEACTIVE);
		}
	}

	/**
	 * Exchanges one point with another. Also the point color and point label.
	 * 
	 * @param a
	 *            int index of first point to exchange.
	 * @param b
	 *            int index of second point to exchange.
	 */
	protected void swapValueIndex(int a, int b) {
		for (int t = 0; t < valuesList.size(); t++) {
			ValueList v = valuesList.getValueList(t);
			v.exchange(a, b);
		}

		if (iColors != null) {
			iColors.exchange(a, b);
		}
		if (sLabels != null) {
			sLabels.exchange(a, b);
		}
	}

	public Swapper getValueIndexSwapper() {
		return new Swapper() {

			public final void swap(int a, int b) {
				swapValueIndex(a, b);
			}
		};
	}

	/**
	 * Series description to show in Legend and dialogs.<br>
	 * Every Series has a Title method of type String. It is used in
	 * TChart.Legend to draw the series descriptions. If Title is empty, then
	 * the Series class Name will be used to draw the legend. Setting Title both
	 * at design time and runtime will force the Chart to repaint. <br>
	 * Default value: ""
	 * 
	 * @return String
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Series description to show in Legend and dialogs.<br>
	 * Default value: ""
	 * 
	 * @param value
	 *            String
	 */
	public void setTitle(final String value) {
		title = setStringProperty(title, value);
		if (chart != null) {
			chart.broadcastEvent(this, SeriesEventStyle.CHANGETITLE);
		}
	}

	public void onDisposing() {
	}

	public void setChart(IBaseChart value) {

		if (chart != value) {
			if (chart != null) {
				chart.removeSeries(this);
			}

			super.setChart(value);

			if (bBrush != null) {
				bBrush.setChart(chart);
			}

			if (chart != null) {
				chart.addSeries(this);
				added();
			}

			if (marks != null) {
				marks.iSeries = this;
				marks.setChart(chart);
				marks.getCallout().setChart(chart);
			}

			if (function != null) {
				function.setChart(chart);
			}

			if (chart != null) {
				chart.invalidate();
			}

			for (int t = 0; t < valuesList.size(); t++) {
				valuesList.getValueList(t).series = this;
				valuesList.getValueList(t).setChart(chart);
			}
		}
	}

	protected void setHorizontal() {
		mandatory = vxValues;
		notMandatory = vyValues;
		yMandatory = false;
	}

	/**
	 * Adds new point with specified DateTime x and Double y values.
	 * 
	 * @see ValueList
	 * @param x
	 *            DateTime
	 * @param y
	 *            double
	 * @return int index of added point
	 */
	public int add(final DateTime x, final double y) {
		vxValues.dateTime = true;
		return add(x.toDouble(), y);
	}

	/**
	 * @see ValueList
	 * 
	 * @param x
	 *            DateTime
	 * @param y
	 *            DateTime
	 * @return int index of added point
	 */
	public int add(final DateTime x, final DateTime y) {
		vxValues.dateTime = true;
		vyValues.dateTime = true;
		return add(x.toDouble(), y.toDouble());
	}

	/**
	 * Adds new point with specified DateTime x, Double y values and Color.
	 * 
	 * @see ValueList
	 * 
	 * @param x
	 *            DateTime
	 * @param y
	 *            double
	 * @param c
	 *            Color
	 * @return int index of added point
	 */
	public int add(final DateTime x, final double y, final Color c) {
		vxValues.dateTime = true;
		return add(x.toDouble(), y, c);
	}

	/**
	 * Adds new point with specified DateTime x, Double y values and Text.
	 * 
	 * @see ValueList
	 * 
	 * @param x
	 *            DateTime
	 * @param y
	 *            double
	 * @param text
	 *            String
	 * @return int index of added point
	 */
	public int add(final DateTime x, final double y, final String text) {
		vxValues.dateTime = true;
		return add(x.toDouble(), y, text);
	}

	/**
	 * Obsolete.&nbsp;Please use add(x,y,Color.<!-- -->Transparent) method
	 * instead.
	 * 
	 * @param x
	 *            double
	 * @param y
	 *            double
	 * @return int
	 */
	public int addNullXY(double x, double y) {
		return add(x, y, Color.TRANSPARENT);
	}

	/**
	 * Obsolete.&nbsp;Please use add() method without parameters instead.
	 * 
	 * @return int
	 */
	public int addNull() {
		return add();
	}

	/**
	 * Adds a new null (transparent) point.
	 * 
	 * @return int
	 */
	public int add() {
		return add(getDefaultNullValue(), Color.TRANSPARENT);
	}

	/**
	 * Adds a new point with specified value.
	 * 
	 * @param value
	 *            int
	 * @return int
	 */
	public int add(final int value) {
		if (yMandatory) {
			return add(getCount(), value);
		} else {
			return add(value, getCount());
		}
	}

	/**
	 * Adds a new point with specified value.
	 * 
	 * @param value
	 *            double
	 * @return int
	 */
	public int add(final double value) {
		if (yMandatory) {
			return add(getCount(), value);
		} else {
			return add(value, getCount());
		}
	}

	/**
	 * Adds a new point with specified value.
	 * 
	 * @param value
	 *            float
	 * @return int
	 */
	public int add(final float value) {
		return add((double) value);
	}

	/**
	 * Adds a new point with specified value and text.
	 * 
	 * @param value
	 *            double
	 * @param text
	 *            String
	 * @return int
	 */
	public int add(final double value, final String text) {
		if (yMandatory) {
			return add(getCount(), value, text);
		} else {
			return add(value, getCount(), text);
		}
	}
	
	private boolean willAddColor = false;

	/**
	 * Adds a new point with specified x and y values.
	 * 
	 * @param x
	 *            double
	 * @param y
	 *            double
	 * @return int
	 */
	public int add(final double x, final double y) {
		int tmp = vxValues.addChartValue(x);
		vyValues.insertChartValue(tmp, y);

		int listsCount = valuesList.size();
		if (listsCount > 2) {
			for (int t = 2; t < listsCount; t++) {
				ValueList v = valuesList.getValueList(t);
				v.insertChartValue(tmp, v.tempValue);
			}
		}
		
		if (iColors != null && !willAddColor) //CDI TF02012933
	    {
	      iColors.setColor(tmp, Color.EMPTY);
	    }

		if (updating == 0) {
			invalidate();
		}

		return tmp;
	}

	/**
	 * Adds the pair of floating point x- and y-pixel coordinates
	 * 
	 * @param p
	 *            Double
	 * @return int
	 */
	public int add(final Point.Double p) {
		return add(p.x, p.y);
	}

	/**
	 * Adds a new point with specified x,y values and text.
	 * 
	 * @param x
	 *            double
	 * @param y
	 *            double
	 * @param text
	 *            String
	 * @return int
	 */
	public int add(final double x, final double y, final String text) {
		return add(x, y, text, Color.EMPTY);
	}

	/**
	 * Adds a new point with specified x,y values, text and color.
	 * 
	 * @param x
	 *            double
	 * @param y
	 *            double
	 * @param text
	 *            String
	 * @param color
	 *            Color
	 * @return int
	 */
	public int add(final double x, final double y, final String text,
			final Color color) {
		willAddColor = true;
		int tmp = add(x, y);
		willAddColor = false;
		if (!color.isEmpty()) {
                  if (tmp <= getColors().getCount())
                    getColors().add(tmp, color);  //revisit TJ71015679
                  else
		    getColors().setColor(tmp, color);
		}
		if ((text != null) /* && (text.Length!=0) */) {
			if (getXValues().getOrder() == ValueListOrder.NONE) {
				getLabels().setString(getLabels().size(), text);
			} else if (text.length() != 0) {
				getLabels().insert(tmp, text);
			}
		}
		return tmp;
	}

	/**
	 * Adds a new Datetime point to a Series, label and color.
	 * 
	 * @param aDate
	 *            DateTime datetime value
	 * @param y
	 *            double
	 * @param text
	 *            String point text
	 * @param color
	 *            Color
	 * @return int index of added point
	 */
	public int add(final DateTime aDate, final double y, final String text,
			final Color color) {
		vxValues.dateTime = true;
		return add(aDate.toDouble(), y, text, color);
	}

	/**
	 * Adds a new point with specified x and y values and color.
	 * 
	 * @param x
	 *            double
	 * @param y
	 *            double
	 * @param color
	 *            Color
	 * @return int
	 */
	public int add(final double x, final double y, final Color color) {
		return add(x, y, "", color);
	}

	/**
	 * Adds a new point with specified value, text and color.
	 * 
	 * @param value
	 *            double
	 * @param text
	 *            String
	 * @param color
	 *            Color
	 * @return int
	 */
	public int add(final double value, final String text, final Color color) {
		if (yMandatory) {
			return add(getCount(), value, text, color);
		} else {
			return add(value, getCount(), text, color);
		}
	}

	/**
	 * Adds a new point with specified value and color.
	 * 
	 * @param value
	 *            double
	 * @param color
	 *            Color
	 * @return int
	 */
	public int add(final double value, final Color color) {
		if (yMandatory) {
			return add(getCount(), value, color);
		} else {
			return add(value, getCount(), color);
		}
	}

	/**
	 * Adds a new null point with specified text.
	 * 
	 * @param text
	 *            String
	 * @return int
	 */
	public int add(final String text) {
		return add(getDefaultNullValue(), text, Color.TRANSPARENT);
	}

	/**
	 * Adds all points in source Series.
	 * 
	 * @param source
	 *            Series
	 */
	// [Description("Adds all points in source Series.")]
	public void add(final Series source) {
		addValuesFrom(source);
	}

	protected double[] convertArray(ArrayList a, int numPoints) {
		final double[] tmpArray = new double[numPoints];

		final Double[] arrayofDoubles = (Double[]) a.toArray(new Double[] {});

		for (int i = 0; i < arrayofDoubles.length; i++) {
			tmpArray[i] = arrayofDoubles[i].doubleValue();
		}

		// System.arraycopy(arrayofDoubles, 0, tmpArray, 0, numPoints);

		// if (a.contains(DateTime.class)) {
		// tmpArray = new double[numPoints];
		// int max = a.size();
		// for (int i = 0; i <= max; i++) {
		// tmpArray[i] = (DateTime)a.get(i);
		// }
		// } else if (a.contains(int.class) || a.contains(float.class)) {
		// tmpArray = new double[numPoints];
		// a.CopyTo(tmpArray, 0);
		// } else if (a.contains(double.class)) {
		// tmpArray = (double[]) a;
		// }

		return tmpArray;
	}

	/**
	 * Adds the X and Y arrays.
	 * 
	 * @param xValues
	 *            ArrayList
	 * @param yValues
	 *            ArrayList
	 */
	public void add(final ArrayList xValues, final ArrayList yValues) {
		int numPoints = yValues.size();
		getXValues().count = numPoints;
		getXValues().value = convertArray(xValues, numPoints);
		getYValues().count = numPoints;
		getYValues().value = convertArray(yValues, numPoints);
		getXValues().statsOk = false;
		getYValues().statsOk = false;
		invalidate();
	}

	/**
	 * Adds 2 array of double values.
	 * 
	 * @param xValues
	 *            ArrayList
	 * @param yValues
	 *            ArrayList
	 */
	public void add(final double[] xValues, final double[] yValues) {
		add(xValues, yValues, false);
	}

	/*
	 * Adds the X and Y arrays, optionally appending the values to any existing
	 * ones.
	 */
	public void add(final double[] xValues, final double[] yValues,
			boolean append) {
		int numPoints = yValues.length;
		if (append) {
			System.arraycopy(getXValues().value, 0, xValues, 0,
					xValues.length - 1);
			getXValues().value = xValues;
			System.arraycopy(getYValues().value, 0, yValues, 0,
					yValues.length - 1);
			getYValues().value = yValues;

			getXValues().count += numPoints;
			getYValues().count += numPoints;
		} else {
			getXValues().count = numPoints;
			getXValues().value = xValues;
			getYValues().count = numPoints;
			getYValues().value = yValues;
		}
		getXValues().statsOk = false;
		getYValues().statsOk = false;

		for (int i = 0; i < getYValues().count; i++) {
			if (getYValues().value[i] == getDefaultNullValue()) {
				setNull(i);
			}
		}

		invalidate();
	}

	/**
	 * Adds the array of double values.
	 * 
	 * @param values
	 *            double[]
	 */
	public void add(final double[] values) {
		for (int t = 0; t < values.length; t++) {
			add(values[t]);
		}
	}

	/**
	 * Adds the array of float values.
	 * 
	 * @param values
	 *            float[]
	 */
	public void add(final float[] values) {
		for (int t = 0; t < values.length; t++) {
			add(values[t]);
		}
	}

	/**
	 * Adds the array of integer values.
	 * 
	 * @param values
	 *            int[]
	 */
	public void add(final int[] values) {
		for (int t = 0; t < values.length; t++) {
			add(values[t]);
		}
	}

	public boolean hasClickEvents() {
		return hasListenersOf(SeriesMouseListener.class);
	}

	public MouseClicked checkMouse(Cursor c, int x, int y) {
		final MouseClicked r = new MouseClicked(c, false);

		if ((getCursor() != Cursor.DEFAULT)
				|| (hasListenersOf(SeriesMouseListener.class))) {
			if (clicked(x, y) != -1) { // <-- mouse is over a Series value !
				if (getCursor() != Cursor.DEFAULT) {
					r.cursor = getCursor();
				}

				if (!isMouseInside) {
					isMouseInside = true;
					fireSeriesMouseEvent(new SeriesMouseEvent(this,
							SeriesMouseEvent.SERIES_ENTERED, -1,
							new Point(x, y)));
				}

				r.clicked = true;
			} else {
				if (isMouseInside) {
					isMouseInside = false;
					fireSeriesMouseEvent(new SeriesMouseEvent(this,
							SeriesMouseEvent.SERIES_EXITED, -1, new Point(x, y)));
				}
			}
		}

		return r;
	}

	/**
	 * Reorders points according to Order property of X,Y,etc value lists.<br>
	 * Refreshes sort order of Series points if Order type of XValues or YValues
	 * is not loNone.
	 */
	public void checkOrder() {
		if (mandatory.getOrder() != ValueListOrder.NONE) {
			mandatory.sort();

			// if NotMandatory list has a ValueSource, do not call FillSequence
			if (notMandatory.valueSource.length() == 0) {
				notMandatory.fillSequence();
			}
			repaint();
		}
	}

	/**
	 * Removes all points, texts and Colors from the Series.<br>
	 * Dependent Series are notified. If no new points are appended to the
	 * Series, nothing will be painted.
	 */
	public void clear() {
		clearLists();
		// if (OnClearValues!=null) ...
		if (updating == 0) {
			invalidate();
		}
	}

	protected void clearLists() {
		// Values
		for (int t = 0; t < valuesList.size(); t++) {
			valuesList.getValueList(t).clear();
		}
		if (sLabels != null) {
			sLabels.clear();
		}
		if (iColors != null) {
			iColors.clear();
		}
		if (marks != null) {
			marks.clear();
		}
	}

	public int clicked(final Point p) {
		return clicked(p.x, p.y);
	} // abstract

	/**
	 * Returns the ValueIndex of the "clicked" point in the Series.<br>
	 * Clicked means the X and Y coordinates are in the point screen region
	 * bounds. If no point is "touched", Clicked returns -1
	 * 
	 * @param x
	 *            int
	 * @param y
	 *            int
	 * @return int
	 */
	public int clicked(int x, int y) {
		return -1;
	} // abstract

	/**
	 * Returns the number of points in the Series.
	 * 
	 * @return int
	 */
	public int getCount() {
		return mandatory.count;
	}

	/**
	 * Removes the index th point.<br>
	 * X values remain unchanged.<br>
	 * The Chart will be automatically redrawn. <br>
	 * Dependent Series will be recalculated.
	 * 
	 * @param index
	 *            int
	 */
	public void delete(int index) {
		for (int i = 0; i < valuesList.size(); ++i) {
			valuesList.getValueList(i).removeAt(index);
		}

		if ((sLabels != null) && (sLabels.size() > index)) {
			sLabels.remove(index);
		}
		if ((iColors != null) && (iColors.size() > index)) {
			iColors.remove(index);
		}
		if (marks != null) { // CDI if not checked causes exception when Series
								// not associated with Chart.
			if (marks.getPositions().size() > index) {
				marks.getPositions().remove(index);
			}
			if (marks.getItems().size() > index) {
				marks.getItems().remove(index);
			}
		}
		if (updating == 0) {
			invalidate();
		}
	}

	/**
	 * Removes count number of points starting at index.<br>
	 * When RemoveGap parameter is true, it calls ValueList FillSequence.<br>
	 * The Chart will be automatically redrawn. <br>
	 * Dependent Series will be recalculated.
	 * 
	 * @param index
	 *            int
	 * @param count
	 *            int
	 * @param removeGap
	 *            boolean
	 */
	public void delete(int index, int count, boolean removeGap) {
            
                for (int i = 0; i < this.getValuesLists().size();i++)
                    getValuesLists().getValueList(i).removeRange(index, count);

                if ((sLabels != null) && (sLabels.size() > index + count - 1)) {
			sLabels.removeRange(index, count);
		}
		if ((iColors != null) && (iColors.getCount() > index + count - 1)) {
			iColors.removeRange(index, count);
		}
		if ((marks != null)
				&& (marks.getPositions().size() > index + count - 1)) {
			marks.getPositions().removeRange(index, count);
		}
		if (removeGap) {
			notMandatory.fillSequence();
		}
		if (updating == 0) {
			invalidate();
		}
	}

	public void delete(int index, int count) {
		delete(index, count, false);
	}

	protected SeriesRandom randomBounds(int numValues) {
		double minX, maxX;

		final SeriesRandom result = new SeriesRandom();

		result.MinY = 0;
		double maxY = 1000;

		if ((chart != null) && (chart.getMaxValuesCount() > 0)) {
			result.MinY = chart.getMinYValue(vertAxis);
			maxY = chart.getMaxYValue(vertAxis);

			if (maxY == result.MinY) {
				if (maxY == 0) {
					maxY = 1000;
				} else {
					maxY = 2.0 * result.MinY;
				}
			}

			minX = chart.getMinXValue(horizAxis);
			maxX = chart.getMaxXValue(horizAxis);

			if (maxX == minX) {
				if (maxX == 0) {
					maxX = numValues;
				} else {
					maxX = 2.0 * minX;
				}
			}

			if (!yMandatory) {
				double tmp = minX;
				minX = result.MinY;
				result.MinY = tmp;

				tmp = maxX;
				maxX = maxY;
				maxY = tmp;
			}
		} else {
			if (vxValues.getDateTime()) {
				minX = DateTime.getNow().toDouble();
				maxX = minX + DateTimeStep.STEP[DateTimeStep.ONEDAY] * (numValues - 1);
			} else {
				minX = 0;
				maxX = numValues - 1;
			}
		}

		if (Utils.compareDoubles(Double.NEGATIVE_INFINITY, result.MinY) == 0) {
			result.MinY = 0;
		}

		if (Utils.compareDoubles(Double.NEGATIVE_INFINITY, minX) == 0) {
			minX = 0;
		}

		if (Utils.compareDoubles(Double.NEGATIVE_INFINITY, maxY) == 0) {
			maxY = 0;
		}

		if (Utils.compareDoubles(Double.NEGATIVE_INFINITY, maxX) == 0) {
			maxX = 0;
		}

		result.StepX = maxX - minX;
		if (numValues > 1) {
			result.StepX /= (numValues - 1);
		}
		result.DifY = maxY - result.MinY;
		if (result.DifY > Integer.MAX_VALUE) {
			result.DifY = Integer.MAX_VALUE;
		} else if (result.DifY < -Integer.MAX_VALUE) {
			result.DifY = -Integer.MAX_VALUE;
		}

		result.tmpY = result.MinY + result.DifY * result.Random();
		result.tmpX = minX;

		return result;
	}

	public int getNumSampleValues() {
		return numSampleValues();
	}

	protected int numSampleValues() {
		return 25; // default number of random sample values
	}

	protected void addSampleValues(int numValues) {
		final SeriesRandom s = randomBounds(numValues);

		int tmpH = (int) Utils.round(s.DifY * 0.25);

		for (int t = 1; t <= numValues; t++) {
			s.tmpY = (int) Math.abs(s.tmpY + tmpH * s.Random() - (tmpH / 2));

			if (yMandatory) {
				add(s.tmpX, s.tmpY);
			} else {
				add(s.tmpY, s.tmpX);
			}

			s.tmpX += s.StepX;
		}
	}

	/**
	 * Adds random values to help visualize the Series.<br>
	 * Each series class determines how many random points to add.
	 */
	public void fillSampleValues() {
		fillSampleValues(getNumSampleValues());
	}

	/**
	 * Adds the specified NumValues random points.
	 * 
	 * @param numValues
	 *            int the number of sample values to add.
	 */
	public void fillSampleValues(int numValues) {
		if (numValues == 0) {
			numValues = iNumSampleValues;
			if (numValues <= 0) {
				numValues = getNumSampleValues();
			}
		}

		clear();

		beginUpdate();
		try {
			addSampleValues(numValues);
			checkOrder();
		} finally {
			endUpdate();
		}

		manualData = false;
	}

	/**
	 * Returns true if the index th point in the Series is "null" or "empty". <br>
	 * A point is considered "null" when the color of that point is
	 * "Transparent".
	 * 
	 * @param index
	 *            int the point index.
	 * @return boolean true if the point color is Color.Transparent
	 */
	public boolean isNull(int index) {
		boolean result;

		if (index >= 0) {
			result = (iColors != null) && (iColors.getCount() > index)
					&& (iColors.getColor(index) == Color.TRANSPARENT);
		} else {
			result = false;
		}

		return result;
	}

	/**
	 * Validates Series datasource.<br>
	 * The isValidSourceOf function returns false if the Value parameter is the
	 * same as Self. <br>
	 * It's used to validate the DataSource property both at design and
	 * run-time.
	 * 
	 * @param value
	 *            Series the series to validate.
	 * @return boolean true if value can be a Series data source.
	 */
	public boolean isValidSourceOf(final Series value) {
		return this != value;
	}

	protected boolean isValidSeriesSource(final Series value) {
		return true;
	}

	/**
	 * Function object to calculate values.<br>
	 * Default value: null
	 * 
	 * @return Function
	 */
	public Function getFunction() {
		return function;
	}

	public void setFunction(final Function value) {
		if (function != value) {
			// if (function!=null) function.Dispose();

			function = value;
			if (function != null) {
				function.setSeries(this);
			}
			checkDataSource();
		}
	}

	/**
	 * The Depth of the series points or interconnecting lines.<br>
	 * Default value: -1
	 * 
	 * @return int
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * Sets the Depth of the series points or interconnecting lines.<br>
	 * Default value: -1
	 * 
	 * @param value
	 *            int
	 */
	public void setDepth(int value) {
		depth = setIntegerProperty(depth, value);
	}

	/**
	 * Determines where on the depth axis the Series is drawn.<br>
	 * <br>
	 * Read-only and run time. <br>
	 * <br>
	 * It's valid only when TChart.Aspect.View3D is true and when there's more
	 * than one Series in the same chart. <br>
	 * You can't alter the ZOrder property directly. If you want a different
	 * order, you need to use tChart.SeriesList instead.<br>
	 * The ZOrder property is calculated for each Series just before the Chart
	 * is drawn. <br>
	 * TChart.ApplyZOrder controls if Series will be assigned a different Z
	 * position or not. When False, all Series are drawn at same Z plane.<br>
	 * TChart.MaxZOrder returns the highest of all Series ZOrder values. <br>
	 * Default value: AutoZOrder
	 * 
	 * @return int
	 */
	public int getZOrder() {
		return (zOrder == AUTOZORDER) ? iZOrder : zOrder;
	}

	/**
	 * Determines where on the depth axis the Series is drawn.<br>
	 * <br>
	 * Default value: AutoZOrder
	 * 
	 * @param value
	 *            int
	 */
	public void setZOrder(int value) {
		zOrder = setIntegerProperty(zOrder, value);
		iZOrder = (zOrder == AUTOZORDER) ? 0 : zOrder;
	}

	/**
	 * Displays this Series Title in Legend.<br>
	 * It is only meaningful when LegendStyle is Series or LastValues. <br>
	 * Default value: true
	 * 
	 * @return boolean
	 */
	public boolean getShowInLegend() {
		return showInLegend;
	}

	/**
	 * Displays this Series Title in Legend.<br>
	 * Default value: true
	 * 
	 * @param value
	 *            boolean
	 */
	public void setShowInLegend(boolean value) {
		showInLegend = setBooleanProperty(showInLegend, value);
	}

	/**
	 * Returns the index of the Series' first visible point.
	 * 
	 * @return int
	 */
	public int getFirstVisible() {
		return firstVisible;
	}

	/**
	 * Returns the index of the series point that is displayed first when the
	 * series is drawn.
	 **/
	public int firstDisplayed() {
		return drawValuesForward() ? firstVisible : lastVisible;
	}

	/**
	 * Returns the index of the series point that is displayed first when the
	 * series is drawn.
	 **/
	public int lastDisplayed() {
		return drawValuesForward() ? lastVisible : firstVisible;
	}

	/**
	 * Returns the index of the Series' last visible point.
	 * 
	 * @return int
	 */
	public int getLastVisible() {
		return lastVisible;
	}

	/**
	 * Cursor displayed when mouse is over a series point.<br>
	 * Each Series determines the intersection of points with mouse coordinates
	 * each time the mouse moves. There are many different Cursors available.
	 * The Series ZOrder determines the order in which Series will be examined
	 * to calculate the clicked Series point. <br>
	 * Default value: default
	 * 
	 * @return Cursor
	 */
	public Cursor getCursor() {
		return cursor;
	}

	/**
	 * Cursor displayed when mouse is over a series point.<br>
	 * Default value: default
	 * 
	 * @param value
	 *            Cursor
	 */
	public void setCursor(final Cursor value) {
		cursor = value;
	}

	/**
	 * Defines how to draw a mark near to each Series point.<br>
	 * A mark consist of a colored rectangle with a text string on it and a line
	 * that indicates which points corresponds to which mark. <br>
	 * Each different Series type draws it's marks differently. <br>
	 * 
	 * @return SeriesMarks
	 */
	public SeriesMarks getMarks() {
		if (marks == null) {
			marks = new SeriesMarks(this); // delayed creation
			marks.zPosition = middleZ;
		}
		return marks;
	}

	/**
	 * Returns whether Series draws its points in ascending/descending order. <br>
	 * Some Series need to draw their points in descending order (starting from
	 * the last point to the first) depending on certain situations. For
	 * example, when the horizontal axis Inverted property is True. <br>
	 * 
	 * @return boolean true if values in this series are displayed forward, from
	 *         0 to Count-1.
	 */
	public boolean drawValuesForward() {
		boolean result;

		if (mandatory == vyValues) {
			result = !horizAxis.getInverted();

			if (chart.getParent().invertedRotation()) {
				result = !result;
			}

		} else {
			result = !vertAxis.getInverted();
		}

		return result;
	}

	/**
	 * Called internally. Draws the "ValueIndex" point of the Series.
	 * 
	 * @param index
	 *            int
	 */
	public void drawValue(int index) {
	} // abstract

	public void drawHiddenValue(int valueIndex, PointDouble startEndVals,
			int gapSize, Axis axis) {
	}

	; // abstract

	public int legendToValueIndex(int legendIndex) {
		return legendIndex;
	}

	/**
	 * Sets the specified series point to a null (transparent) point.<br>
	 * 
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * <font face="Courier" size="4">
	 * lineSeries1.setNull( 123 ); // -- make null (empty) point index 123
	 * lineSeries1.setIgnoreNulls( false ); // -- allow null points
	 * lineSeries1.setStairs( true ); // -- set "stairs" mode
	 * </font>
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param valueIndex
	 *            int
	 */
	public void setNull(int valueIndex) {
		getColors().setColor(valueIndex, Color.TRANSPARENT);
	}

	/**
	 * Toogles between null (transparent) and regular point.
	 * 
	 * @param valueIndex
	 *            int
	 * @param isnull
	 *            boolean
	 */
	public void setNull(int valueIndex, boolean isnull) {
		getColors().setColor(valueIndex,
				isnull ? Color.TRANSPARENT : Color.EMPTY);
	}

	public void createSubGallery(final Gallery addSubChart) {
		addSubChart.createSubChart(Language.getString("Normal"));
	}

	/**
	 * Creates and prepares the index'th Series style to show at sub-gallery
	 * dialog.
	 * 
	 * @param index
	 *            int
	 */
	public void setSubGallery(int index) {
	} // abstract

	protected void setValueList(final ValueList l, final ValueList value) {
		l.assign(value);
		repaint();
	}

	/**
	 * Returns the size in pixels corresponding to value parameter in horizontal
	 * axis scales.<br>
	 * This coordinate is calculated using the Series associated Horizontal
	 * Axis.
	 * 
	 * @param value
	 *            double
	 * @return int
	 */
	public int calcXSizeValue(final double value) {
		return horizAxis.calcSizeValue(value);
	}

	/**
	 * Returns the size in pixels corresponding to value parameter in vertical
	 * axis scales.<br>
	 * This coordinate is calculated using the Series associated Vertical Axis.
	 * 
	 * @param value
	 *            double
	 * @return int
	 */
	public int calcYSizeValue(final double value) {
		return vertAxis.calcSizeValue(value);
	}

	private String calcXValue(int valueIndex) {
		if (horizAxis != null) {
			return horizAxis.getLabels().labelValue(vxValues.value[valueIndex]);
		} else {
			return formatValue(vxValues.value[valueIndex]);
		}
	}

	/**
	 * Overridden ToString() method.
	 * 
	 * @see Series#getTitle
	 * @return String the Series Series.Title method.
	 */
	public String toString() {
		String tmpResult = (title == null) ? "" : title;

		if (tmpResult.length() == 0) {

			if (chart != null) {
				tmpResult = Language.getString("Series") + Integer.toString(chart.getSeriesIndexOf(this));
			} else {
				tmpResult = super.toString();
			}
		}
		return tmpResult;
	}

	/**
	 * Obsolete.&nbsp;Please use ToString() method instead.
	 * 
	 * @return String
	 */
	public String titleOrName() {
		return toString();
	}

	private String valueToStr(final double tmpValue) {
		if (mandatory.getDateTime()) {
			if (tmpValue < 1) {
				return new DateTime(tmpValue).toShortTimeString();
			} else {
				return new DateTime(tmpValue).toShortDateString();
			}
		} else {
			return formatValue(tmpValue);
		}
	}

	public String calcPercentSt(final double value) {
		return percentDecimal.format((mandatory.getTotalABS() == 0) ? 100
				: 100.0 * value / mandatory.getTotalABS());
	}

	/*
	 * Returns the formatted String corresponding to the LegendIndex point. The
	 * String is used to show at the Legend. Uses the LegendTextStyle property
	 * to ) the appropiate formatting.
	 */
	/**
	 * Returns the formatted String corresponding to the LegendIndex point.
	 * 
	 * 
	 * @param legendIndex
	 *            int
	 * @param legendTextStyle
	 *            LegendTextStyle
	 * @return String
	 */
	public String getLegendString(int legendIndex,
			final LegendTextStyle legendTextStyle) {

		int valueIndex = legendToValueIndex(legendIndex);
		String tmpResult = (sLabels == null) ? "" : sLabels
				.getString(valueIndex);

		if (legendTextStyle != LegendTextStyle.PLAIN) {

			final double tmpValue = getMarkValue(valueIndex);

			if (legendTextStyle == LegendTextStyle.LEFTVALUE) {
				if (tmpResult.length() != 0) {
					tmpResult = Language.columnSeparator + tmpResult;
				}
				return valueToStr(tmpValue) + tmpResult;
			} else if (legendTextStyle == LegendTextStyle.RIGHTVALUE) {
				if (tmpResult.length() != 0) {
					tmpResult += Language.columnSeparator;
				}
				return tmpResult + valueToStr(tmpValue);
			} else if (legendTextStyle == LegendTextStyle.LEFTPERCENT) {
				if (tmpResult.length() != 0) {
					tmpResult = Language.columnSeparator + tmpResult;
				}
				return calcPercentSt(tmpValue) + tmpResult;
			} else if (legendTextStyle == LegendTextStyle.RIGHTPERCENT) {
				if (tmpResult.length() != 0) {
					tmpResult += Language.columnSeparator;
				}
				return tmpResult + calcPercentSt(tmpValue);
			} else if (legendTextStyle == LegendTextStyle.XVALUE) {
				return calcXValue(valueIndex);
			} else if (legendTextStyle == LegendTextStyle.VALUE) {
				return valueToStr(tmpValue);
			} else if (legendTextStyle == LegendTextStyle.PERCENT) {
				return calcPercentSt(tmpValue);
			} else if (legendTextStyle == LegendTextStyle.XANDVALUE) {
				return calcXValue(valueIndex) + Language.columnSeparator
						+ valueToStr(tmpValue);
			} else if (legendTextStyle == LegendTextStyle.XANDPERCENT) {
				return calcXValue(valueIndex) + Language.columnSeparator
						+ calcPercentSt(tmpValue);
			}
		}
		return tmpResult;
	}

	/**
	 * The Maximum Value of the Series X Values List.
	 * 
	 * @return double
	 */
	public double getMaxXValue() {
		return vxValues.getMaximum();
	}

	/**
	 * The Maximum Value of the Series Y Values List.
	 * 
	 * @return double
	 */
	public double getMaxYValue() {
		return vyValues.getMaximum();
	}

	/**
	 * The Minimum Value of the Series X Values List.
	 * 
	 * @return double
	 */
	public double getMinXValue() {
		return vxValues.getMinimum();
	}

	/**
	 * The Minimum Value of the Series Y Values List.
	 * 
	 * @return double
	 */
	public double getMinYValue() {
		return vyValues.getMinimum();
	}

	/**
	 * The Maximum Z Value. For non-3D series, this is the Z order.
	 * 
	 * @return double
	 */
	public double getMaxZValue() {
		return getZOrder();
	}

	/**
	 * The Minimum Z Value. For non-3D series, this is the Z order.
	 * 
	 * @return double
	 */
	public double getMinZValue() {
		return getZOrder();
	}

	/**
	 * Returns whether or not this Series has Y values as mandatory.
	 * 
	 * @return boolean
	 */
	public boolean getYMandatory() {
		return yMandatory;
	}

	public interface MarkTextResolver {

		String getMarkText(int valueIndex, String markText);
	};

	transient private final static MarkTextResolver MARKTEXT_RESOLVER = new MarkTextResolver() {

		public String getMarkText(int valueIndex, String markText) {
			return markText;
		}
	};

	public ChartBrush getBrush() {
		return bBrush;
	}
}
