package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.ChartBrush;
import com.steema.teechart.drawing.Gradient;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.editors.gallery.Gallery;

/**
 *
 * <p>Title: Violin class</p>
 *
 * <p>Description: Violin Series</p>
 *
 * <p>Copyright (c) 2005-2021 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 * @see com.steema.teechart.TChart
 *
 */
public class Violin extends CustomViolin {
    private static final long serialVersionUID = 1L;
    private ValueList offsetValues;

    public Violin() {
      super();
    }
    
    public Violin(IBaseChart c) {
        super(c);
        marks.getTailParams().setAlign(TailAlignment.Auto);
        offsetValues = new ValueList(this, Language.getString("ValuesOffset"));
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryViolin");
    }

}

