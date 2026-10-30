package com.steema.teechart.styles;

import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.text.DateFormatSymbols;
import java.util.EventListener;
import java.util.GregorianCalendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.steema.teechart.DateTime;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.Rectangle;
import com.steema.teechart.TextShape;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.drawing.StringAlignment;
import com.steema.teechart.events.DateTimeResolver;
import com.steema.teechart.events.DefaultResolvers;
import com.steema.teechart.events.SeriesMouseAdapter;
import com.steema.teechart.events.SeriesMouseEvent;
import com.steema.teechart.languages.Language;
import com.steema.teechart.misc.MathUtils;

/**
 *
 * <p>Title: Calendar class</p>
 *
 * <p>Description: Calendar Series.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * calendar = new Calendar[3];
 * for (int t=0; t < 3; t++) {
 * calendar[t] = new Calendar(chart[t].getChart());
 * calendar[t].getMarks().setVisible(false);
 * calendar[t].setShowInLegend(false);
 * calendar[t].getDays().setTransparent(true);
 * calendar[t].getDays().setVisible(true);
 * calendar[t].getMonths().setTransparent(true);
 * calendar[t].getMonths().setVisible(true);
 * calendar[t].getWeekDays().setFormat("E");
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class Calendar extends Series {

    protected Object readResolve() {
        customDate = DefaultResolvers.DATETIME_RESOLVER;
        return super.readResolve();
    }

    transient protected DateTimeResolver customDate;

    public void setDateTimeResolver(DateTimeResolver resolver) {
        if (resolver != null) {
            this.customDate = resolver;
        } else {
            removeDateTimeResolver();
        }
    }

    public void removeDateTimeResolver() {
        this.customDate = DefaultResolvers.DATETIME_RESOLVER;
    }

    /**
     *
     * <p>Title: CalendarCell class</p>
     *
     * <p>Description: Contains the characteristics of each Calendar Cell.</p>
     *
     * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
     * Reserved.</p>
     *
     * <p>Company: Steema Software SL</p>
     *
     */
    public class CalendarCell extends TextShape {
        public Calendar series;

        public CalendarCell() {
            super(null);
        }

        public CalendarCell(Calendar s) {
            super(s.chart);

            series = s;
        }
    }


    /**
     *
     * <p>Title: CalendarCellUpper class</p>
     *
     * <p>Description: Format information for the upper row cells of the
     * Calendar Series.</p>
     *
     * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
     * Reserved.</p>
     *
     * <p>Company: Steema Software SL</p>
     *
     */
    public final class CalendarCellUpper extends CalendarCell {

        private boolean upper;
        private String format = "";

        public CalendarCellUpper() {
            super();
        }

        public CalendarCellUpper(Calendar s) {
            super(s);
            series = s;
        }

        /**
         * Enables/disables the display of months and weekdays in upper case.
         * <br>
         * Default value: false
         *
         * @return boolean
         */
        public boolean getUpperCase() {
            return upper;
        }

        /**
         * Enables/disables the display of months and weekdays in upper case.
         * <br>
         * Default value: false
         *
         * @param value boolean
         */
        public void setUpperCase(boolean value) {
            upper = setBooleanProperty(upper, value);
        }

        /**
         * Defines the DateValue format for the months and weekdays.<br>
         * Default value: ""
         *
         * @return String
         */
        public String getFormat() {
            return format;
        }

        /**
         * Defines the DateValue format for the months and weekdays.<br>
         * Default value: ""
         *
         * @param value String
         */
        public void setFormat(String value) {
            format = setStringProperty(format, value);
        }
    }

    public int dayOneRow;
    public int dayOneColumn;

    private DateTime date = DateTime.getNow();
    private CalendarCell days;
    private CalendarCellUpper months;
//    private JButton nextMonth = new JButton();
//    private JButton previousMonth = new JButton();

    private Button nextMonth; // = new Button();
    private Button previousMonth; // = new Button();
    
    private Menu popupMenu;
    private CalendarCell sunday;
    private CalendarCell today;
    private CalendarCell trailing;
    private CalendarCellUpper weekDays;

    transient private final int iColumns = 7;
    transient private int iFirstDay;
    transient private final int iRows = 8;

    private ChartPen pen;

    public Calendar() {
        this((IBaseChart)null);
    }

    public Calendar(IBaseChart c) {
        super(c);

        GregorianCalendar calendar = new GregorianCalendar();

        iFirstDay = (calendar.getFirstDayOfWeek());

        calcVisiblePoints = false;
        setShowInLegend(false);
        if (super.chart != null) {
            add(); //CDI TF02010113
        }
        
        setButton(nextMonth, ">", 30, true);
        setButton(previousMonth, "<", 6, false);

        date = DateTime.getNow();
        weekDays = new CalendarCellUpper(this);
        weekDays.getPen().setVisible(false);
        weekDays.setFormat("E");

        months = new CalendarCellUpper(this);
        months.setTransparent(true);
        months.setFormat("MMMM, yyyy");

        days = new CalendarCell(this);
        days.setTransparent(true);

        today = new CalendarCell(this);
        today.getShadow().setVisible(false);
        today.getFont().setColor(Color.WHITE);
        today.setColor(Color.BLUE);

        sunday = new CalendarCell(this);
        sunday.getShadow().setVisible(false);
        sunday.setColor(Color.RED);
        sunday.getFont().setColor(Color.WHITE);

        trailing = new CalendarCell(this);
        trailing.setTransparent(true);
        trailing.getFont().setColor(Color.DARK_GRAY);

        getMarks().getBrush().setColor(Color.GOLD);

        useSeriesColor = false;

        readResolve();

        useAxis = false;
        
        addSeriesMouseListener(new SeriesMouseAdapter() {
            public void seriesClicked(SeriesMouseEvent e) {
                 checkClick(e.getPoint());
            }
        });
    }

    /**
     * Element Pen Characteristics.
     *
     * @return ChartPen
     */
    public ChartPen getPen() {
        if (pen == null) {
            pen = new ChartPen(chart, Color.BLACK);
        }
        return pen;
    }

    /**
     * Defines a day and causes Calender Series to display the associated month.
     * <br>
     *
     * @return DateTime
     */
    public DateTime getDate() {
        return date;
    }

    /**
     * Sets a day and causes Calender Series to display the associated month.
     * <br>
     *
     * @param value DateTime
     */
    public void setDate(DateTime value) {

        value = customDate.getDate(this, value);

        if (value != date) {
            fireCalendarChangeEvent(new CalendarChangeEvent(this, CalendarChangeEvent.SERIES_CHANGED, value));
            date = value;
            invalidate();
        }
    }

    protected void drawCell(CalendarCell cell, int column, int row,
                            String text) {
        int w = chart.getGraphics3D().getFontHeight();

        int x = xCell(column - 0.5);
        int y = yCell(row - 0.5) - (w / 2);

        if (cell.getShadow().getVisible()) {
            x -= (cell.getShadow().getWidth() / 2);
            y -= (cell.getShadow().getHeight() / 2);
        }

        chart.getGraphics3D().textOut(x, y, text);
    }
    
    public void addCalendarChangeListener(CalendarChangeListener l) {
        listenerList.add(CalendarChangeListener.class, l);
    }

    public void removeCalendarChangeListener(CalendarChangeListener l) {
        listenerList.remove(CalendarChangeListener.class, l);
    }
        
    protected void fireCalendarChangeEvent(CalendarChangeEvent se) {
        final Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == CalendarChangeListener.class) {
                switch (se.getID()) {
                    case CalendarChangeEvent.SERIES_CHANGED: {
                        ((CalendarChangeListener) listeners[i + 1]).seriesChanged(se);
                        break;
                    }
                }
            }
        }
    }

    public void prepareForGallery(boolean isEnabled) {
        super.prepareForGallery(isEnabled);
        getPen().setVisible(false);
        getDays().getFont().setSize(6);
        getMonths().setVisible(false);
        getWeekDays().getFont().setSize(5);
        getWeekDays().getShadow().setVisible(false);
        getSunday().getFont().setSize(6);
        getToday().getFont().setSize(6);
        getTrailing().setVisible(false);
        getNextMonthButton().setVisible(false);
        getPreviousMonthButton().setVisible(false);
    }

    /**
     * Defines Calender Cell appearance characteristics for calender days.
     *
     * @return CalendarCell
     */
	public CalendarCell getDays() {
		if (days == null)
			days = new CalendarCell(this);
		return days;
	}

    /**
     * Defines Calender Cell appearance for weekday titles.
     *
     * @return CalendarCellUpper
     */
	public CalendarCellUpper getWeekDays() {
		if (weekDays == null)
			weekDays = new CalendarCellUpper(this);
		return weekDays;
	}

    /**
     * Defines Calender Cell appearance for the month name.
     *
     * @return CalendarCellUpper
     */
	public CalendarCellUpper getMonths() {
		if (months == null)
			months = new CalendarCellUpper(this);
		return months;
	}

    /**
     * Defines Calender Cell appearance for the highlighted day.
     *
     * @return CalendarCell
     */
	public CalendarCell getToday() {
		if (today == null)
			today = new CalendarCell(this);
		return today;
	}

    /**
     * Defines Calender Cell appearance for all Sundays.
     *
     * @return CalendarCell
     */
	public CalendarCell getSunday() {
		if (sunday == null)
			sunday = new CalendarCell(this);
		return sunday;
	}

    /**
     * Defines Calender Cell appearance for the trailing days.
     *
     * @return CalendarCell
     */
	public CalendarCell getTrailing() {
		if (trailing == null)
			trailing = new CalendarCell(this);
		return trailing;
	}

    /**
     * Gets the month after the present one.<br>
     * Returns the button used to scroll the calendar to "next" month.
     *
     * @return JButton
     */
	public Button getNextMonthButton() {
		return nextMonth;
	}

    /**
     * Gets the month before the present one.<br>
     * Returns the button used to scroll the calendar to "previous" month.
     *
     * @return JButton
     */
	public Button getPreviousMonthButton() {
		return previousMonth;
	}

    private int xCell(double column) {
        return getHorizAxis().iStartPos +
                MathUtils.round(column * getHorizAxis().iAxisSize / iColumns);
    }

    private int yCell(double row) {
        return getVertAxis().iStartPos +
                MathUtils.round(row * getVertAxis().iAxisSize / getRows());
    }

    protected int numSampleValues() {
        return 1;
    }

    /**
     * Returns the ValueIndex of the "clicked" point in the Series.
     *
     * @param x int
     * @param y int
     * @return int
     */
    public int clicked(int x, int y) {
        return (getActive() && seriesRect().contains(x, y)) ? 0 : -1;
    }
    
	/**
	 * Returns the Date of the "clicked" cell in the Calendar.
	 * 
	 * @param x
	 *            int
	 * @param y
	 *            int
	 * @return int
	 */
    public DateTime clickedDate(int x, int y) {
        DateTime d = null;
        
        if (clicked(x,y) == 0) {
            int tmpC = 1+(x-getHorizAxis().iStartPos)/(getHorizAxis().iAxisSize/getColumns());
            int tmpR = 1+(y-getVertAxis().iStartPos)/(getVertAxis().iAxisSize/getRows());
            
            d = cellDate(tmpC, tmpR);
        }
        
        return d;
    }

    /**
     * Returns the bounderies of a 2D-Rectangle defining the outline of the
     * specified Calender series cell.
     *
     * @param column int
     * @param row int
     * @return Rectangle
     */
    public Rectangle rectCell(int column, int row) {
        return Rectangle.fromLTRB(xCell(column - 1) + 1,
                                  yCell(row - 1) + 1, xCell(column),
                                  yCell(row));
    }

	private void setButton(Button b, String text, int aLeftPos, boolean next) {

		if ((chart != null) && (chart.getParent() != null)) {
			Composite composite = (Composite) (chart.getParent()); // new Composite(shell, SWT.BORDER);
			b = new Button(composite, SWT.FLAT);

			b.setBounds(aLeftPos, 6, 20, 15);
			b.setText(text);
			b.setVisible(true);

			if (next)
				b.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent event) {
						nextMonth();
					}
				});
			else
				b.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent event) {
						previousMonth();
					}
				});
		}
	}

    public void monthClick(Object sender) {
        if (sender == nextMonth) {
            nextMonth();
        } else {
            previousMonth();
        }
    }

    public void setActive(boolean value) {
        super.setActive(value);
        nextMonth.setVisible(getVisible());
        previousMonth.setVisible(getVisible());
    }
    
    /**
     * Set both buttons visible to true/false
     * @param value
     */
    public void setShowButtons(boolean value)
    {
    	previousMonth.setVisible(value);
    	nextMonth.setVisible(value);
    }

    public void setChart(IBaseChart c) {
        super.setChart(c);
        getDays().setChart(chart);
        getWeekDays().setChart(chart);
        getMonths().setChart(chart);
        getToday().setChart(chart);
        getSunday().setChart(chart);
        getTrailing().setChart(chart);
        
        setButton(nextMonth, ">", 30, true);
        setButton(previousMonth, "<", 6, false);
    }

    private Rectangle seriesRect() {
        Rectangle result = new Rectangle();

        result.x = getHorizAxis().iStartPos;
        result.width = getHorizAxis().iEndPos - result.x;

        result.y = getVertAxis().iStartPos;
        result.height = getVertAxis().iEndPos - result.y;

        int tmpRowSize = MathUtils.round(getVertAxis().iAxisSize / iRows);

        if (getWeekDays().getVisible()) {
            result.y += tmpRowSize;
        }
        if (getMonths().getVisible()) {
            result.y += tmpRowSize;
        }

        return result;
    }

    private void drawGrid() {
        if (getPen().getVisible()) {
            int minRowLines = (getWeekDays().getVisible()) ? 1 : 0;
            if (getMonths().getVisible()) {
                minRowLines++;
            }

            IGraphics3D g = chart.getGraphics3D();
            g.setPen(pen);

            for (int c = 0; c <= iColumns; c++) {
                g.verticalLine(xCell(c), yCell(minRowLines),
                               getVertAxis().iEndPos, getStartZ());
            }
            for (int r = minRowLines; r <= getRows(); r++) {
                g.horizontalLine(getHorizAxis().iStartPos,
                                 getHorizAxis().iEndPos, yCell(r),
                                 getStartZ());
            }
        }
    }

    private void drawBack(CalendarCell aCell, Rectangle rect) {
        if (aCell.getShadow().getVisible()) {
            rect.height -= aCell.getShadow().getHeight();
            rect.width -= aCell.getShadow().getWidth();
        }

        aCell.drawRectRotated(chart.getGraphics3D(), rect, 0, getStartZ());
        aCell.setShapeBounds(rect); // 5.02
    }

    private void drawDay(CalendarCell aCell, int c, int r, int d) {
        if (aCell.getVisible()) {
            drawBack(aCell, rectCell(c, r));
            chart.getGraphics3D().setFont(aCell.getFont());
            chart.getGraphics3D().setTextAlign(StringAlignment.CENTER);
            drawCell(aCell, c, r, Integer.toString(d));
        }
    }

    private final class NextDayData {
        int d, c, r;
    }

    private void nextDay(NextDayData value) {
        value.d++;
        value.c++;

        if (value.c > iColumns) {
            value.c = 1;
            value.r++;
        }
    }

    /**
     * Returns the number of Rows present in the Calender Series.
     *
     * @return int
     */
    public int getRows() {
        int result = iRows;
        if (!weekDays.getVisible()) {
            result--;
        }
        if (!months.getVisible()) {
            result--;
        }
        return result;
    }

    protected void draw() {
        NextDayData data = new NextDayData();
        int tmpPrevDays, tmpDays, y2, m2, r;
        String tmpSt;

        super.draw();

        IGraphics3D g = chart.getGraphics3D();

        if (getWeekDays().getVisible()) {
            g.setFont(getWeekDays().getFont());
            g.setTextAlign(StringAlignment.CENTER);

            r = 1;

            if (getMonths().getVisible()) {
                r++;
            }
            for (int col = 1; col <= iColumns; col++) {
                drawBack(getWeekDays(), rectCell(col, r));

                tmpSt = new DateTime(1970, 1, col + iFirstDay - 1).
                        toString(getWeekDays().getFormat());

                if (getWeekDays().getUpperCase()) {
                    tmpSt = tmpSt.toUpperCase();
                }
                drawCell(getWeekDays(), col, r, tmpSt);
            }
        }

        if (getMonths().getVisible()) {

            g.setFont(getMonths().getFont());
            g.setTextAlign(StringAlignment.CENTER);

            Rectangle rect = seriesRect();
            rect.y = rectCell(4, 0).getBottom();
            rect.height = rectCell(4, 1).getBottom() - rect.y;
            drawBack(getMonths(), rect);
            tmpSt = getDate().toString(getMonths().getFormat());
            if (getMonths().getUpperCase()) {
                tmpSt = tmpSt.toUpperCase();
            }
            drawCell(getMonths(), 4, 1, tmpSt);
        }

        int y = getDate().getYear();
        int m = getDate().getMonth();
        int tmpToday = getDate().getDay();

        int d = 1;
        DateTime temp = new DateTime(y, m, d);

        int c = temp.getDayOfWeek();
        if (c == 0) {
            c = 7;
        }
        c = 1 + ((c + (7 - iFirstDay) - 1) % iColumns);
        r = 3;

        if (!getWeekDays().getVisible()) {
            r--;
        }
        if (!getMonths().getVisible()) {
            r--;
        }

        dayOneRow = r;
        dayOneColumn = c;

        tmpDays = DateTime.getDaysInMonth(y, m);

        if (getTrailing().getVisible() && (c > 1)) {
            m2 = m - 1;
            y2 = y;
            if (m2 == -1) {
                m2 = 11;  //month index starts at 0
                y2--;
            }
            tmpPrevDays = DateTime.getDaysInMonth(y2, m2);
            for (int t = 1; t <= c; t++) {
                drawDay(getTrailing(), t, r, tmpPrevDays - (c - t) );
            }
        }

        data.c = c;
        data.r = r;

        do {
            nextDay(data);
            d = data.d;
            c = data.c;
            r = data.r;
            if ((d == tmpToday) && getToday().getVisible()) {
                drawDay(getToday(), c, r, d);
            } else
            if (new DateTime(y, m, d).getDayOfWeek() == java.util.Calendar.SUNDAY) { // DayOfWeek.Sunday) {
                drawDay(getSunday(), c, r, d);
            } else {
                drawDay(getDays(), c, r, d);
            }
        } while (d < tmpDays);

        data.c = c++;
        data.r = r;
        if (getTrailing().getVisible() && ((c <= iColumns) || (r < getRows()))) {
            m++;
            d = 1;
            c = data.c;
            data.d = 0;
            while ((c <= iColumns) && (r <= getRows())) {
                drawDay(getTrailing(), c, r, d);
                nextDay(data);
                d = data.d;
                c = data.c;
                r = data.r;
            }
        }

        if (getPen().getVisible()) {
            drawGrid();
        }
    }

    /**
     * Gets description text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("CalendarSeries");
    }

    protected DateTime cellDate(int aCol, int aRow) {
        int tmpD;
        DateTime result = getDate();
        int y = result.getYear();
        int m = result.getMonth();
        int d = result.getDay();

        if (aRow == dayOneRow) {
            if (aCol >= dayOneColumn) {
                result = new DateTime(y, m, aCol - dayOneColumn /*+ 1*/);
            } else
            if (getTrailing().getVisible()) { // previous month
                m--;
                if (m == -1) {
                    m = 11;
                    y--;
                }
                tmpD = 1 + DateTime.getDaysInMonth(y, m) -
                       dayOneColumn + aCol;
                result = new DateTime(y, m, tmpD);
            }
        } else
        if (aRow > dayOneRow) {
            tmpD = (7 * (aRow - dayOneRow)) + aCol - dayOneColumn /*+ 1*/;
            if (tmpD > DateTime.getDaysInMonth(y, m)) { // next month...
                if (getTrailing().getVisible()) {
                    tmpD -= DateTime.getDaysInMonth(y, m);
                    m++;
                    if (m > 12) {
                        m = 1;
                        y++;
                    }
                } else {
                    return result;
                }
            }

            result = new DateTime(y, m, tmpD);
        }
        return result;
    }

    /**
     * Returns the number of Columns present in the Calender Series.
     *
     * @return int
     */
    public int getColumns() {
        return iColumns;
    }

    private void changeMonthMenu(Object sender) {

        final int tmp = 0;
        /** @todo FINISH !! */
        // int tmp= ((MenuItem) sender).Index;

        int y = getDate().getYear();
        int d = getDate().getDay();
        int m = tmp + 1;
        int tmpDays = DateTime.getDaysInMonth(y, m);
        if (d > tmpDays) {
            d = tmpDays;
        }
        setDate(new DateTime(y, m, d));
    }

    /**
     * Returns the popupmenu used by the calendar to show all the month names
     * (when rightclicking the month name on the calendar).<br>
     * This popupmenu is used to go to the desired month. <br>
     *
     * @return Menu
     */
    public Menu getPopupMenu() {
        if (popupMenu == null) {

            // first time, do creation
            popupMenu = new PopupMenu();

            // add 12 months
            for (int t = 1; t <= 12; t++) {
                MenuItem tmp = new MenuItem();

                DateFormatSymbols symbols = new DateFormatSymbols();
                tmp.setLabel(symbols.getMonths()[t - 1]);

                /** @todo FINISH !! */
//                tmp.Click += new EventHandler(ChangeMonthMenu);

                popupMenu.add(tmp);
            }
        }

        return popupMenu; // already existing popup
    }

	protected void checkClick(int x, int y) {
	}

	protected void checkClick(Point p) {
		if (getMonths().getShapeBounds().contains(p)) {
			/** @todo FINISH !! */
			// popupMenu.show(chart.getParent().getControl(),new Point(x, y));
		} else if (clicked(p) == 0) {

			DateTime d = clickedDate(p.x, p.y);
			if (d != null)
				setDate(d);
		}

		chart.setCancelMouse(true);
	}

    /**
     * Moves the Calender series onto the month after the present one.
     */
	public void nextMonth() {
		DateTime tmp = (DateTime) date.clone();
		setDate(tmp.addMonths(1));
		invalidate();
	}
    /**
     * Moves the Calender series onto the month before the present one.
     */
	final public void previousMonth() {
		DateTime tmp = (DateTime) date.clone();
		setDate(tmp.addMonths(-1));
		invalidate();
	}

    /**
     * Returns month number of currently displayed month.
     */
	public int getMonth() {
		return date.getMonth();
	}

	public class CalendarChangeEvent {

		public static final int SERIES_CHANGED = 982383862 + 4;

		private int id;
		private ISeries series;
		private DateTime value;

		public CalendarChangeEvent(ISeries series, int id, DateTime value) {
			this.series = series;
			this.id = id;
			this.value = value;
		}

		public ISeries getSeries() {
			return series;
		}

		public int getID() {
			return id;
		}

		public DateTime getValue() {
			return value;
		}
	}

	public interface CalendarChangeListener extends EventListener {
		public void seriesChanged(CalendarChangeEvent e);
	}
}

