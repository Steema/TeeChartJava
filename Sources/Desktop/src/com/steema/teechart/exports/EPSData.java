package com.steema.teechart.exports;

import com.steema.teechart.misc.MemoryOutputStream;
//import com.steema.teechart.export.EPSFonts;

/**
 * <p>Title: EPSData class</p>
 *
 * <p>Description: EPS data export</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

  public class EPSData {

     private MemoryOutputStream m_objStream = null;
     private int m_intPosition = 0;
     private EPSFonts m_fontlist = new EPSFonts();
//     private com.steema.teechart.drawing.GraphicsImages m_imagelist = new Drawing.GraphicsImages();

     public EPSData(MemoryOutputStream stream) {
       m_objStream = stream;
     }

     public MemoryOutputStream getStream() {
       return m_objStream;
     }

     public void setStream(MemoryOutputStream value) {
       m_objStream = value;
     }

     public int getPosition(){
       return m_intPosition;
     }

     public void setPosition(int value) {
       m_intPosition = value;
     }

     public EPSFonts getFonts() {
       return m_fontlist;
     }

     public void setFonts(EPSFonts value) {
       m_fontlist = value;
     }

/* TODO

     public Drawing.GraphicsImages Images {
       get { return m_imagelist; }
       set { m_imagelist = value; }
     }
 */
}

