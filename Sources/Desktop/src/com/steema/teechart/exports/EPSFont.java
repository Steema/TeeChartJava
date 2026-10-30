package com.steema.teechart.exports;

/**
 *
 * <p>Title: EPSFont class</p>
 *
 * <p>Description: Stores all used fonts</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

     public class EPSFont {
// TODO     public EPSFont(EPSFonts fonts, Drawing.ChartFont f) : base(fonts, f) { }

       protected String getFontName(com.steema.teechart.drawing.ChartFont f) {
         String res = f.getName();
         if ((f.getBold()) && (!f.getItalic()))
             res += ",Bold";
         else
           if ((f.getItalic()) && (!f.getBold()))
               res += ",Italic";
           else
             if ((f.getBold()) && (f.getItalic()))
                 res += ",BoldItalic";
         res = res.replace(' ','-');
         return res;
       }

       public String getDictFontName() {
// TODO         return GraphicsFontName; // TODO : replace with dictionaries, once ready
           return "to do";
       }
     }

