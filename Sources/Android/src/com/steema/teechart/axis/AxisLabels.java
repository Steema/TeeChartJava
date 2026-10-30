package com.steema.teechart.axis;

import java.text.DecimalFormat;
import com.steema.teechart.languages.Language;

import com.steema.teechart.DateTime;
import com.steema.teechart.MultiLine;

import com.steema.teechart.TextShape;

/**
 * Axis Label characteristic
 *
 * <p>Title: AxisLabels class</p>
 *
 * <p>Description: Axis Label characteristics</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class AxisLabels extends TextShape {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// labels
    protected int iAngle;
    protected boolean bOnAxis = true;
    protected int iSeparation = 10;
    public AxisLabelStyle iStyle = AxisLabelStyle.AUTO;
    protected boolean bExponent;
    protected int position;
    protected String axisvaluesformat = Language.getString("DefValueFormat");
    
    transient protected DecimalFormat valuesDecimal;
    
    protected String sDatetimeformat = "";
    transient protected Axis axis;

    private AxisLabelAlign align = AxisLabelAlign.DEFAULT;
    private int customSize;
    private boolean multiline;
    private boolean exactDateTime = true;
    private boolean roundfirstlabel = true;
    private AxisLabelsItems items;

    private boolean labelsAlternate;

    /**
     * Accesses the Label characteristics of Axis Labels.
     *
     * @param a Axis
     */
    public AxisLabels(Axis a) {
        super(a.chart);
        axis = a;
        bTransparent = true;
        items = new AxisLabelsItems(a);
        readResolve();
    }

    protected Object readResolve() {
        valuesDecimal = new DecimalFormat(Language.getString("DefValueFormat"));
        return this;
    }

    protected boolean shouldSerializeTransparent() {
        return!bTransparent;
    }

    /**
     * Determines whether Axis.Increment calculates Axis Labels in
     * exact DateTime steps. <br>
     * This is very useful when Axis.Increment is a DateTimeStep constant value.
     * <br>When ExactDateTime is false (the default value), the OneMonth
     * increment equals 30 days, and axis do not calculate how many days a
     * month has. <br>
     * The Series XValues or YValues properties should have DateTime = true.
     * ( XValues for horizontal Axis and YValues for vertical Axis).
     *
     * Default value: false
     *
     * @return boolean
     */
    public boolean getExactDateTime() {
        return exactDateTime;
    }

    /**
     * Determines whether Axis.Increment calculates Axis Labels in
     * exact DateTime steps. <br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setExactDateTime(boolean value) {
        exactDateTime = setBooleanProperty(exactDateTime, value);
    }

    /**
     * Defines the rotation degree applied to each Axis Label. Valid angle
     * in degrees are 0, 90, 180, 270 and 360. <br>
     * Please note that some printers and video drivers fail when drawing
     * rotated fonts or calculating the rotated font dimensions.
     * Metafile Charts containing rotated fonts sometimes place text
     * at sligthly different coordinates.
     *
     * Default value: 0
     *
     * @return int
     */
    public int getAngle() {
        return iAngle;
    }
    /**
     * Defines the rotation degree applied to each Axis Label.<br>
     * Default value: 0
     *
     * @param value int
     */
    public void setAngle(int value) {
        iAngle = setIntegerProperty(iAngle, value % 360);
    }

    /**
     *
     * Determines whether the Labels at Axis Minimum and Maximum positions
     * will be shown or not.<br>
     *
     * Default value: true
     *
     * @return boolean
     */
    public boolean getOnAxis() {
        return bOnAxis;
    }
    /**
     * Shows the Labels at Axis Minimum and Maximum positions when true.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setOnAxis(boolean value) {
        bOnAxis = setBooleanProperty(bOnAxis, value);
    }

    /**
     * Specifies the minimum distance between Axis Labels as a percentage.
     * Setting it to "0" zero makes Axis skip calculating overlapping labels.
     * (No clipping is performed). Labels visibility depends also on
     * Labels.Font size, Labels.Angle and Axis.Increment properties. <br>
     * Default value: 10
     *
     * @return int
     */
    public int getSeparation() {
        return iSeparation;
    }

    /**
     * Specifies the minimum distance between Axis Labels as a percentage.<br>
     * Default value: 10
     *
     * @param value int
     */
    public void setSeparation(int value) {
        iSeparation = setIntegerProperty(iSeparation, value);
    }

    /**
     * Changes the spacing occupied by the axis labels between the Ticks and
     * the Title.<br>
     * Default value: 0
     *
     * @return int
     */
    public int getCustomSize() {
        return customSize;
    }

    /**
     * Changes the spacing occupied by the axis labels between the Ticks and
     * the Title.<br>
     * Default value: 0
     *
     * @param value int
     */
    public void setCustomSize(int value) {
        customSize = setIntegerProperty(customSize, value);
    }

    /**
     * The style of the labels. Setting Axis.Label.Style to talAuto will
     * force the Axis to guess what labels will be drawn. For each Active
     * associated Series, if the Series have XLabels then the Label.Style
     * will be talText. If no Series have XLabels, then Label.Style will be
     * talValue. If no Active Series are associated with the Axis,
     * then Label.Style will be talNone. <br>
     * Default value: AxisLabelStyle.Auto
     *
     * @return AxisLabelStyle
     */
    public AxisLabelStyle getStyle() {
        return iStyle;
    }

    /**
     * Sets the style of the labels.<br>
     * Default value: AxisLabelStyle.Auto
     *
     * @param value AxisLabelStyle
     */
    public void setStyle(AxisLabelStyle value) {
        if (iStyle != value) {
            iStyle = value;
            invalidate();
        }
    }

    /**
     * Controls if Axis labels will be automatically "rounded" to the nearest
     * magnitude. Run-time only. This applies both to DateTime and non-DateTime
     * axis values. When false, Axis labels will start at Maximum Axis value.<br>
     * Default value: true
     *
     * @return boolean
     */
    public boolean getRoundFirstLabel() {
        return roundfirstlabel;
    }

    /**
     * Axis labels will be automatically "rounded" to the nearest magnitude when
     * true.<br>
     * Default value: true
     *
     * @param value boolean
     */
    public void setRoundFirstLabel(boolean value) {
        roundfirstlabel = setBooleanProperty(roundfirstlabel, value);
    }

    /**
     * Standard DateTime formatting string specifier used to draw the
     * axis labels.<br>
     * Default value: ""
     *
     * @return String
     */
    public String getDateTimeFormat() {
        return sDatetimeformat;
    }

    /**
     * Standard DateTime formatting string specifier used to draw the
     * axis labels.<br>
     * Default value: ""
     *
     * @param value String
     */
    public void setDateTimeFormat(String value) {
        sDatetimeformat = setStringProperty(sDatetimeformat, value);
    }

    /**
     * Specifies the desired formatting string to be applied to Axis Labels.
     * It has effect when Axis associated Series have their XValues.DateTime
     * or YValues.DateTime property set to false.<br>
     * For DateTime Axis labels use the AxisLabels.DateTimeFormat property.<br>
     * ValueFormat is a standard formatting string specifier. Chart Axis uses
     * it to draw the axis labels. Chart Series uses it to draw the Marks. <br>
     * Default value: '#,##0.###'
     *
     * @return formatting String to be applied to Axis Labels.
     */
    public String getValueFormat() {
        return axisvaluesformat;
    }

    /**
     * Specifies the desired formatting string to be applied to Axis Labels.<br>
     * Default value: '#,##0.###'
     *
     * @param value String
     */
    public void setValueFormat(String value) {
      if (axisvaluesformat != value) {
        axisvaluesformat = setStringProperty(axisvaluesformat, value);
        valuesDecimal = new DecimalFormat(axisvaluesformat);
      }
    }

    /**
     * Automatically breaks DateTime Labels on occurence of a space " ".
     * Use '\n' in other label types to break a line or use SplitInLines in
     * the OnGetAxisLabel event. <br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getMultiLine() {
        return multiline;
    }

    /**
     * Automatically breaks DateTime Labels on occurence of a space " ".<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setMultiLine(boolean value) {
        multiline = setBooleanProperty(multiline, value);
    }

    /**
     * Sets Separator String to invoke 'new line' in String St. Replaces input
     * string St with separator to be used to break lines at each occurrence
     * of the separator.
     *
     * @param s Input String
     * @param separator String to be used for new line
     * @return String
     */
    public String splitInLines(final String s, final String separator) {
        return s.replaceAll(separator,Language.lineSeparator);
    }

    /**
     * Sets Separator Char to invoke 'new line' in String St. Replaces input
     * string St with separator to be used to break lines at each occurrence
     * of the separator.
     *
     * @param s Input String
     * @param separator char to be used for new line
     * @return String
     */
    public String splitInLines(final String s, final char separator) {
      return s.replace(separator, Language.lineSeparator.charAt(0));
    }

    /**
     * Enables/disables the display of Axis Labels in exponent format with
     * super-script fonts.<br>
     * Default value: false
     *
     * @return boolean
     */
    public boolean getExponent() {
        return bExponent;
    }

    /**
     * Enables/disables the display of Axis Labels in exponent format with
     * super-script fonts.<br>
     * Default value: false
     *
     * @param value boolean
     */
    public void setExponent(boolean value) {
        bExponent = setBooleanProperty(bExponent, value);
    }

    /**
     * The position of Labels on an Axis.<br>
     * The default position of an AxisLabel will depend on the Axis with which
     * it is associated. A Bottom Axis will place Labels below the Axis by
     * default. A Top Axis will place the Labels above the Axis.<br>
     * Default value: Default position <br>
     * Opposite: Labels positioned on the opposite side to the default position.
     *
     * @return AxisLabelAlign
     */
    public AxisLabelAlign getAlign() {
        return align;
    }

    /**
     * Sets the position of Labels on an Axis.<br>
     * Default value: Default position
     *
     * @param value AxisLabelAlign
     */
    public void setAlign(AxisLabelAlign value) {
        if (align != value) {
            align = value;
            invalidate();
        }
    }

    /**
     * Contains the custom labels.
     *
     * @return AxisLabelsItems
     */
    public AxisLabelsItems getItems() {
        return items;
    }

    private int internalLabelSize(double value, boolean isWidth) {
        MultiLine m = chart.multiLineTextWidth(labelValue(value));

        int result = m.width;
        int tmp = m.count;

        boolean tmpMulti;

        if (isWidth) {
            tmpMulti = (iAngle == 90) || (iAngle == 270);
        } else {
            tmpMulti = (iAngle == 0) || (iAngle == 180);
        }

        if (tmpMulti) {
            result = chart.getGraphics3D().getFontHeight() * tmp;
        }

        return result;
    }

    /**
     * returns the Axis Label width of the Value parameter. It uses the Axis
     * formatting specifiers, the Axis Labels Font and the Labels rotation
     * and style.
     *
     * @param value double Axis value
     * @return int Axis Label width in pixels
     */
    public int labelWidth(double value) {
        //			int tmp;
        //			int tmpResult=chart.MultiLineTextWidth(LabelValue(value),tmp);
        //			if ((angle==90) || (angle==270))
        //			{
        //				tmpResult=chart.getGraphics3D().FontHeight*tmp;
        //			}
        return internalLabelSize(value, true);
        //return tmpResult;
    }

    /**
     * returns the Axis Label height of the Value parameter. It uses the Axis
     * formatting specifiers, the Axis Labels Font and the Labels rotation
     * and style.
     *
     * @param value double Axis value
     * @return int Axis Label height in pixels
     */
    public int labelHeight(double value) {
        return internalLabelSize(value, false);
        //			int tmp;
        //			int tmpResult=chart.MultiLineTextWidth(LabelValue(value),out tmp);
        //			if ((angle==0) || (angle==180))
        //				tmpResult=chart.getGraphics3D().FontHeight*tmp;
        //			return tmpResult;
    }

    /**
     * returns the corresponding text representation of the Value parameter.
     * It uses the Axis formatting specifiers.
     *
     * @param value double
     * @return String
     */
    public String labelValue(double value) {
        String tmpResult;

        if (axis.iAxisDateTime) {
     //MM todo      //if ((value >= -657435) && (value <= 2958466)){
                try { //CDI TF02010053
                    if (sDatetimeformat.length() == 0) {
                        tmpResult = new DateTime(value).toString(axis.
                                dateTimeDefaultFormat(axis.iRange));
                    } else {
                        tmpResult = new DateTime(value).toString(
                                sDatetimeformat);
                    }
                } catch (Exception e) {
                    tmpResult = new DateTime(value).toString(axis.
                            dateTimeDefaultFormat(axis.iRange));
                }
            /*} else {
                tmpResult = "";
            }*/
        } else {
            try { //CDI TF02010053
            	  tmpResult= new DecimalFormat(getValueFormat()).format(value);
            } catch (Exception e) {
                tmpResult = (new DecimalFormat(Language.getString("DefValueFormat"))).format(
                        value);
            }
        }

        if (chart.getParent() != null) {
            tmpResult = chart.getParent().getAxisLabelResolver().getLabel(
                axis,
                null,
                -1,
                tmpResult);
        }

        if (multiline) {
            tmpResult = splitInLines(tmpResult, " ");
        }

        return tmpResult;
    }

    /**
     * Gets the axis labels to be drawn in two rows or columns.
     *
     * @return boolean
     */
    public boolean getAlternate() {
        return labelsAlternate;
    }

    /**
     * Sets the axis labels to be drawn in two rows or columns.
     *
     * @param value boolean
     */
    public void setAlternate(boolean value) {
        labelsAlternate=setBooleanProperty(labelsAlternate,value);
    }

    public int getPosition(){
      return position;
    }

    public void setPosition(int value){
        position = setIntegerProperty(position, value);
    }
}

