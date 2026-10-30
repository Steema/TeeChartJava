package com.steema.teechart.exports;

import com.steema.teechart.misc.MemoryOutputStream;
import com.steema.teechart.languages.Language;


/**
 * <p>Title: EPSPage class</p>
 *
 * <p>Description: EPSPage data export</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */

public class EPSPage {

    private String m_string = "";
    private int width, height;
// TODO    private Stream m_stream = null;
    private MemoryOutputStream canvasstream = null;
    private EPSData m_epsdata;

    public EPSData getEpsData() {
        return m_epsdata;
    }

    public EPSPage(MemoryOutputStream s, int Width, int Height) {
// TODO   m_stream = s;
      width = Width;
      height = Height;
      if (canvasstream==null) canvasstream = new MemoryOutputStream();
      if (m_epsdata==null) m_epsdata = new  EPSData(canvasstream);
    }

    private void AddToStream(MemoryOutputStream s, String text) {
      String linetext = text+"\n";
/* TODO
      byte[] buff = Encoding.ASCII.GetBytes(linetext);
      s.Write(buff, 0 ,buff.Length);
*/
    }

    private void CopyStream(MemoryOutputStream src, MemoryOutputStream dst) {
      int size = 32768;
      byte[] bytes = new byte[size];
      int numBytes;
/* TODO
      while((numBytes = src.Read(bytes, 0, size)) > 0)
        dst.Write(bytes, 0, numBytes);
*/
      }

      private String getFormatLongToString(long Value, int length) {
        String res = "";
        String S = "";
        if (Value >=0) S = String.valueOf(Value);
        int i = length - S.length();
        for (int j=0; j<i; j++) res += "0";
        res += S;
        return res;
      }

      private void PageHeader(MemoryOutputStream s) {
        AddToStream(s,"%!PS-Adobe-3.0 EPSF-3.0");
      }

      private void PageInfo(MemoryOutputStream s) {
        m_string ="%%BoundingBox: 0 0 "+String.valueOf(width)+" " +String.valueOf(height)+"\n";
        m_string += "%%Creator: "+Language.getString("TeeChart")+"\n";
        m_string +="%%LanguageLevel: 1\n";
        m_string +="%%EndComments";
        AddToStream(s,m_string);
      }

      private void PageDict(MemoryOutputStream s) {
        m_string ="/bd{bind def} bind def /ld{load def}bd /ed{exch def}bd /xd{cvx def}bd\n";
        m_string += "/np/newpath ld /cp/closepath ld /m/moveto ld /l/lineto ld /rm/rmoveto ld/rl/rlineto ld\n";
        m_string += "/rot/rotate ld /sc/scale ld /tr/translate ld\n";
        m_string += "/cpt/currentpoint ld\n";
        m_string += "/sw/setlinewidth ld /sd/setdash ld /rgb/setrgbcolor ld\n";
        m_string += "/gs/gsave ld /gr/grestore ld\n";
        m_string += "/st/stroke ld /fi/fill ld /s/show ld\n";
        m_string += "/ltext{cpt st m s}def \n";
        m_string += "/rtext{cpt st m dup stringwidth pop neg 0 rm s} def\n";
        m_string +="/ctext{cpt st m dup stringwidth pop -2 div 0 rm s} def";
        AddToStream(s,m_string);
        m_string = "/ellipsedict 8 dict def\n";
        m_string += "ellipsedict /mtrx matrix put\n";
        m_string += "/ellipse\n";
        m_string += "{ ellipsedict begin\n";
        m_string += "  np\n";
        m_string += "   /endangle exch def\n";
        m_string += "   /startangle exch def\n";
        m_string += "   /yrad exch def\n";
        m_string += "   /xrad exch def\n";
        m_string += "   /y exch def\n";
        m_string += "   /x exch def\n";
        m_string += "  /savematrix mtrx currentmatrix def\n";
        m_string += "  x y tr xrad yrad sc";
        m_string += "  0 0 1 startangle endangle arc\n";
        m_string += "  savematrix setmatrix\n";
        m_string += "  end\n";
        m_string += "} def";
        AddToStream(s,m_string);
        m_string = "/piedict 8 dict def\n";
        m_string += "piedict /mtrx matrix put\n";
        m_string += "/pie\n";
        m_string += "{ piedict begin\n";
        m_string += "  np\n";
        m_string += "   /endangle exch def\n";
        m_string += "   /startangle exch def\n";
        m_string += "   /yrad exch def\n";
        m_string += "   /xrad exch def\n";
        m_string += "   /y exch def\n";
        m_string += "   /x exch def\n";
        m_string += "  /savematrix mtrx currentmatrix def\n";
        m_string += "  x y tr xrad yrad sc\n";
        m_string += "  newpath\n";
        m_string += "  0 0 m\n";
        m_string += "  0 0 1 startangle endangle arc\n";
        m_string += "  closepath\n";
        m_string += "  savematrix setmatrix\n";
        m_string += "  end\n";
        m_string += "} def";
        AddToStream(s,m_string);
      }

      public void ConstructPage(MemoryOutputStream s) {
// TODO    s.Position = 0;
        // start, write page header and info
        PageHeader(s);
        PageInfo(s);
        PageDict(s);
        AddToStream(s,"%%EndProlog");
        // actual drawing comes here...
// TODO     m_epsdata.getStream().Position = 0;
        CopyStream(m_epsdata.getStream(),s);
      }

      public void ConstructPage() {
// TODO   if (m_stream!=null) ConstructPage(m_stream);
      }
}

