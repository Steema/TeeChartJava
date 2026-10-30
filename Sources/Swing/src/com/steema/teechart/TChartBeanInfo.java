package com.steema.teechart;

import java.beans.*;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Component;
import com.steema.teechart.editors.ToolsEditor;
import com.steema.teechart.tools.ToolsCollection;

/**
 * <p>Title: TeeChart for Java</p>
 *
 * <p>Description: TeeChart for Java</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class TChartBeanInfo extends SimpleBeanInfo {

    Class beanClass = TChart.class;

    String iconColor16x16Filename = "images/TChart16x16.gif";
    String iconColor32x32Filename = "images/TChart32x32.gif";
    String iconMono16x16Filename = "images/TChart16x16mono.gif";
    String iconMono32x32Filename = "images/TChart32x32mono.gif";

    public TChartBeanInfo() {
        super();
    }

    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor _afterDraw = new PropertyDescriptor(
                    "afterDraw", beanClass, null, null);

            PropertyDescriptor _allowScroll = new PropertyDescriptor(
                    "allowScroll", beanClass, null, null);

            PropertyDescriptor _applyZOrder = new PropertyDescriptor(
                    "applyZOrder", beanClass, "getApplyZOrder",
                    "setApplyZOrder");

            PropertyDescriptor _aspect = new PropertyDescriptor(
                    "aspect", beanClass, "getAspect", null);

            PropertyDescriptor _autoRepaint = new PropertyDescriptor(
                    "autoRepaint", beanClass, "getAutoRepaint",
                    "setAutoRepaint");

            PropertyDescriptor _axes = new PropertyDescriptor(
                    "axes", beanClass, "getAxes", null);

            PropertyDescriptor _backColor = new PropertyDescriptor(
                    "backColor", beanClass, "getBackColor", "setBackColor");

            PropertyDescriptor _background = new PropertyDescriptor(
                    "background", beanClass, "getBackground", "setBackground");

            PropertyDescriptor _backgroundImage = new PropertyDescriptor(
                    "backgroundImage", beanClass, "getBackgroundImage", null);

            PropertyDescriptor _backImage = new PropertyDescriptor(
                    "backImage", beanClass, null, null);

            PropertyDescriptor _beforeDraw = new PropertyDescriptor(
                    "beforeDraw", beanClass, null, null);

            PropertyDescriptor _beforeDrawAxes = new PropertyDescriptor(
                    "beforeDrawAxes", beanClass, null, null);

            PropertyDescriptor _beforeDrawSeries = new PropertyDescriptor(
                    "beforeDrawSeries", beanClass, null, null);

            PropertyDescriptor _clickAxis = new PropertyDescriptor(
                    "clickAxis", beanClass, null, null);

            PropertyDescriptor _clickBackground = new PropertyDescriptor(
                    "clickBackground", beanClass, null, null);

            PropertyDescriptor _clickLegend = new PropertyDescriptor(
                    "clickLegend", beanClass, null, null);

            PropertyDescriptor _clickSeries = new PropertyDescriptor(
                    "clickSeries", beanClass, null, null);

            PropertyDescriptor _clickTitle = new PropertyDescriptor(
                    "clickTitle", beanClass, null, null);

            PropertyDescriptor _clipPoints = new PropertyDescriptor(
                    "clipPoints", beanClass, "getClipPoints", "setClipPoints");

            PropertyDescriptor _cursor = new PropertyDescriptor(
                    "cursor", beanClass, "getCursor", "setCursor");

            PropertyDescriptor _export = new PropertyDescriptor(
                    "export", beanClass, "getExport", null);

            PropertyDescriptor _font = new PropertyDescriptor(
                    "font", beanClass, "getFont", null);

            PropertyDescriptor _footer = new PropertyDescriptor(
                    "footer", beanClass, "getFooter", null);

            PropertyDescriptor _foreground = new PropertyDescriptor(
                    "foreground", beanClass, "getForeground", null);

            PropertyDescriptor _frame = new PropertyDescriptor(
                    "frame", beanClass, "getFrame", null);

            PropertyDescriptor _getAxisLabel = new PropertyDescriptor(
                    "getAxisLabel", beanClass, null, null);

            PropertyDescriptor _getCursorPos = new PropertyDescriptor(
                    "getCursorPos", beanClass, "getGetCursorPos", null);

            PropertyDescriptor _getLegendPos = new PropertyDescriptor(
                    "getLegendPos", beanClass, null, null);

            PropertyDescriptor _getLegendRect = new PropertyDescriptor(
                    "getLegendRect", beanClass, null, null);

            PropertyDescriptor _getLegendText = new PropertyDescriptor(
                    "getLegendText", beanClass, null, null);

            PropertyDescriptor _getNextAxisLabel = new PropertyDescriptor(
                    "getNextAxisLabel", beanClass, null, null);

            PropertyDescriptor _graphics3D = new PropertyDescriptor(
                    "graphics3D", beanClass, "getGraphics3D", "setGraphics3D");

            PropertyDescriptor _header = new PropertyDescriptor(
                    "header", beanClass, "getHeader", null);

            PropertyDescriptor _height = new PropertyDescriptor(
                    "height", beanClass, null, "setHeight");

            PropertyDescriptor _image = new PropertyDescriptor(
                    "image", beanClass, "getImage", null);

            PropertyDescriptor _import = new PropertyDescriptor(
                    "import", beanClass, "getImport", null);

            PropertyDescriptor _legend = new PropertyDescriptor(
                    "legend", beanClass, "getLegend", null);

            PropertyDescriptor _metafiling = new PropertyDescriptor(
                    "metafiling", beanClass, "getMetafiling", null);

            PropertyDescriptor _mousePosition = new PropertyDescriptor(
                    "mousePosition", beanClass, null, null);

            PropertyDescriptor _page = new PropertyDescriptor(
                    "page", beanClass, "getPage", null);

            PropertyDescriptor _panel = new PropertyDescriptor(
                    "panel", beanClass, "getPanel", null);

            PropertyDescriptor _panning = new PropertyDescriptor(
                    "panning", beanClass, "getPanning", null);

            PropertyDescriptor _preferredSize = new PropertyDescriptor(
                    "preferredSize", beanClass, "getPreferredSize", null);

            PropertyDescriptor _printer = new PropertyDescriptor(
                    "printer", beanClass, "getPrinter", null);

            PropertyDescriptor _printing = new PropertyDescriptor(
                    "printing", beanClass, "getPrinting", "setPrinting");

            PropertyDescriptor _scroll = new PropertyDescriptor(
                    "scroll", beanClass, null, null);

            PropertyDescriptor _series = new PropertyDescriptor(
                    "series", beanClass, "getSeries", null);

            PropertyDescriptor _seriesCount = new PropertyDescriptor(
                    "seriesCount", beanClass, "getSeriesCount", null);

            PropertyDescriptor _subFooter = new PropertyDescriptor(
                    "subFooter", beanClass, "getSubFooter", null);

            PropertyDescriptor _subHeader = new PropertyDescriptor(
                    "subHeader", beanClass, "getSubHeader", null);

            PropertyDescriptor _text = new PropertyDescriptor(
                    "text", beanClass, "getText", "setText");

            PropertyDescriptor _tools = new PropertyDescriptor(
                    "tools", beanClass, "getTools", "setTools");

            _tools.setPropertyEditorClass(ToolsPropertyEditor.class);

            PropertyDescriptor _undoneZoom = new PropertyDescriptor(
                    "undoneZoom", beanClass, null, null);

            PropertyDescriptor _walls = new PropertyDescriptor(
                    "walls", beanClass, "getWalls", null);

            PropertyDescriptor _width = new PropertyDescriptor(
                    "width", beanClass, null, "setWidth");

            PropertyDescriptor _zoom = new PropertyDescriptor(
                    "zoom", beanClass, "getZoom", null);

            PropertyDescriptor[] pds = new PropertyDescriptor[] {
                                       _afterDraw,
                                       _allowScroll,
                                       _applyZOrder,
                                       _aspect,
                                       _autoRepaint,
                                       _axes,
                                       _backColor,
                                       _backColor,
                                       _background,
                                       _background,
                                       _backgroundImage,
                                       _backImage,
                                       _beforeDraw,
                                       _beforeDrawAxes,
                                       _beforeDrawSeries,
                                       _clickAxis,
                                       _clickBackground,
                                       _clickLegend,
                                       _clickSeries,
                                       _clickTitle,
                                       _clipPoints,
                                       _cursor,
                                       _export,
                                       _font,
                                       _footer,
                                       _foreground,
                                       _frame,
                                       _getAxisLabel,
                                       _getCursorPos,
                                       _getLegendPos,
                                       _getLegendRect,
                                       _getLegendText,
                                       _getNextAxisLabel,
                                       _graphics3D,
                                       _header,
                                       _height,
                                       _image,
                                       _import,
                                       _legend,
                                       _metafiling,
                                       _mousePosition,
                                       _page,
                                       _panel,
                                       _panning,
                                       _preferredSize,
                                       _printer,
                                       _printing,
                                       _scroll,
                                       _series,
                                       _seriesCount,
                                       _subFooter,
                                       _subHeader,
                                       _text,
                                       _tools,
                                       _undoneZoom,
                                       _walls,
                                       _width,
                                       _zoom
            };
            return pds;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    // Override default behaviour, SimpleBeanInfo can't find resource
    // when its name is absolute.
    public Image loadImage(String resource)
    {
        ImageIcon i=new ImageIcon(beanClass.getResource(resource));
        return i.getImage();
    }

    public Image getIcon(int iconKind) {
        switch (iconKind) {
        case BeanInfo.ICON_COLOR_16x16:
            return loadImage(iconColor16x16Filename);

        case BeanInfo.ICON_COLOR_32x32:
            return loadImage(iconColor32x32Filename);

        case BeanInfo.ICON_MONO_16x16:
            return loadImage(iconMono16x16Filename);

        case BeanInfo.ICON_MONO_32x32:
            return loadImage(iconMono32x32Filename);
        }

        return null;
    }

    public BeanDescriptor getBeanDescriptor() {
	return new BeanDescriptor(TChart.class, TChartCustomizer.class);
    }

    public BeanInfo[] getAdditionalBeanInfo() {
        Class superclass = beanClass.getSuperclass();
        try {
            BeanInfo superBeanInfo = Introspector.getBeanInfo(superclass);
            return new BeanInfo[] {superBeanInfo};
        } catch (IntrospectionException introspectionException) {
            introspectionException.printStackTrace();
            return null;
        }
    }
}

class ToolsPropertyEditor extends PropertyEditorSupport {
    public Component getCustomEditor() {
        ToolsCollection tools=(ToolsCollection)getValue();
        return new ToolsEditor(tools);
    }

    public boolean supportsCustomEditor() {
        return true;
      }
}

