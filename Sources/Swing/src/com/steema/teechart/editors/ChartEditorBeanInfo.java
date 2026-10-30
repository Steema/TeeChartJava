package com.steema.teechart.editors;

import java.beans.SimpleBeanInfo;
import java.beans.BeanInfo;
import com.steema.teechart.languages.Language;

/**
 *
 * <p>Title: ChartEditorBeanInfo class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ChartEditorBeanInfo extends SimpleBeanInfo {

    public java.awt.Image getIcon(int iconKind) {
        switch (iconKind) {
        case BeanInfo.ICON_COLOR_16x16:
            return loadImage(Language.getString("_images"));
        case BeanInfo.ICON_COLOR_32x32:
            return loadImage(Language.getString("_images1"));
        case BeanInfo.ICON_MONO_16x16:
            return loadImage(Language.getString("_images2"));
        case BeanInfo.ICON_MONO_32x32:
            return loadImage(Language.getString("_images3"));
        default:
            return null;
        }
    }
}

