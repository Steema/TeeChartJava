package com.steema.teechart.printer;

import java.beans.SimpleBeanInfo;
import java.beans.BeanInfo;

/**
 *
 * <p>Title: PreviewPanelBeanInfo class</p>
 *
 * <p>Description: Preview Panel Bean info </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class PreviewPanelBeanInfo extends SimpleBeanInfo {
    public java.awt.Image getIcon(int iconKind) {
        switch (iconKind) {
        case BeanInfo.ICON_COLOR_16x16:
            return loadImage("../images/PreviewPanel16x16.gif");
        case BeanInfo.ICON_COLOR_32x32:
            return loadImage("../images/PreviewPanel32x32.gif");
        case BeanInfo.ICON_MONO_16x16:
            return loadImage("../images/PreviewPanel16x16mono.gif");
        case BeanInfo.ICON_MONO_32x32:
            return loadImage("../images/PreviewPanel32x32mono.gif");
        default:
            return null;
        }
    }
}

