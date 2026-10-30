package com.steema.teechart.editors;

import java.beans.*;
import com.steema.teechart.languages.Language;

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
public class ButtonColorBeanInfo extends SimpleBeanInfo {


    public java.awt.Image getIcon(int iconKind) {
        switch (iconKind) {
        case BeanInfo.ICON_COLOR_16x16:
            return loadImage(Language.getString("ButtonColor16x16_gif"));
        case BeanInfo.ICON_COLOR_32x32:
            return loadImage(Language.getString("ButtonColor32x32_gif"));
        case BeanInfo.ICON_MONO_16x16:
            return loadImage(Language.getString("ButtonColor16x16mono"));
        case BeanInfo.ICON_MONO_32x32:
            return loadImage(Language.getString("ButtonColor32x32mono"));
        default:
            return null;
        }
    }
}

