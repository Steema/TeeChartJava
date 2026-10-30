package com.steema.teechart.data;

import com.steema.teechart.styles.Map;
import com.steema.teechart.styles.Polygon;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Random;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;



  /// <summary>
  /// Summary description for TeeSHP.
  /// </summary>
  public class SHP
  {
    public class SHPHeader
    {
        public int FileCode;
        public int Unused1;
        public int Unused2;
        public int Unused3;
        public int Unused4;
        public int Unused5;
        public int FileLength;
        public int Version;
        public int ShapeType;
        public double XMin;
        public double YMin;
        public double XMax;
        public double YMax;
        public double ZMin;
        public double ZMax;
        public double MMin;
        public double MMax;
    }
    
    public class SHPRecordHeader
    {
        public int RecordNumber;
        public int RecordLength;
    }
      
    private static final int SHP_NULL = 0;
    private static final int SHP_POINT = 1;
    private static final int SHP_POLYLINE = 3;
    private static final int SHP_POLYGON = 5;
    private static final int SHP_MULTIPOINT = 8;
    private static final int SHP_POINTZ = 11;
    private static final int SHP_POLYLINEZ = 13;
    private static final int SHP_POLYGONZ = 15;
    private static final int SHP_MULTIPOINTZ = 18;
    private static final int SHP_POINTM = 21;
    private static final int SHP_POLYLINEM = 23;
    private static final int SHP_POLYGONM = 25;
    private static final int SHP_MULTIPOINTM = 28;
    private static final int SHP_MULTIPATCH = 31;
    private static final int SHPMaxRandom = 1000000;

    private Random rnd = new Random();
    private int ShapeType;
    private int po;
    //private ListBox.ObjectCollection debug;

    public SHP()
    {
    }

    private short[] integerBytes(int Value)
    {
      byte[] result = new byte[4];
      result[0] = (byte)Value;
      result[1] = (byte)(Value >>> 8);
      result[2] = (byte)(Value >>> 16);
      result[3] = (byte)(Value >>> 24);

      return integerShorts(result, Value);
    }
    
    private short[] integerShorts(byte[] bytes, int Value) {
       short[] result = new short[4]; 
       short shift = 0;
       
       for(short i = 0; i < 4; ++i) {
          if(bytes[i] >= 0) {
              result[i] = bytes[i];
          } 
          else {
             shift = (short)(i * 8); 
             result[i] = (short)(Value >>> shift);
          }
       }
       return result;
    }
    
    
    /*private byte[] integerBytes(int Value)
    {
      byte[] result = new byte[4];
      result[0] = (byte)(Value & 0xff000000);
      System.out.print("result[0] " + result[0] + " Value " + Value + "\n");
      result[1] = (byte)((Value & 0x00ff0000) >>> 8);
      System.out.print("result[1] " + result[1] + " Value " + Value + "\n");
      result[2] = (byte)((Value & 0x0000ff00) >>> 16);
      System.out.print("result[2] " + result[2] + " Value " + Value + "\n");
      result[3] = (byte)((Value & 0x000000ff) >>> 24);
      System.out.print("result[3] " + result[3] + " Value " + Value + "\n");

      return result;
    }*/

    public void loadMap(Map series, String fileName) throws FileNotFoundException,
        IOException, ClassNotFoundException, Exception
    {
      loadMap(series, fileName, null, null, null);
    }

    public void loadMap(Map series, String fileName, boolean isResource) throws FileNotFoundException,
        IOException, ClassNotFoundException, Exception
    {
      loadMap(series, fileName, null, null, null, isResource);
    }

    public void loadMap(Map series, String fileName, String filter) throws FileNotFoundException,
        IOException, ClassNotFoundException, Exception
    {
      loadMap(series, fileName, null, null, filter);
    }

    /// <summary>
    /// Loads an ESRI(c) SHP file into a Map series.
    /// </summary>
    /// <param name="Series"></param>
    /// <param name="FileName"></param>
    /// <param name="Table"></param>
    /// <param name="FieldName"></param>
    /// <param name="FieldValue"></param>
    /// <param name="Debug"></param>
    public void loadMap(Map Series, String FileName, String FieldName, String FieldValue,
        String filter) throws FileNotFoundException,
        IOException, ClassNotFoundException, Exception
    {
      loadMap(Series, FileName,
        FieldName, FieldValue,
        filter, false);
    }
    
    private byte[] byteArrayFromInputStream(InputStream stream) throws IOException {
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      int nRead;
      byte[] data = new byte[16384];

      while ((nRead = stream.read(data, 0, data.length)) != -1) {
        buffer.write(data, 0, nRead);
      } 
      return buffer.toByteArray();
    }

    protected void loadMap(Map Series, String FileName,
          String FieldName, String FieldValue,
          String filter, boolean isResource) throws FileNotFoundException,
        IOException, ClassNotFoundException, Exception
    {
      SHPHeader shpHeader = new SHPHeader();
      SHPRecordHeader shpRecordHeader = new SHPRecordHeader();

      String tmpName = replaceFilePath(FileName, ".shp");
      String manifest = "/com/steema/teechart/styles/maps/";

      InputStream ff = null;
      if (isResource)
        ff = this.getClass().getResourceAsStream(manifest + FileName + ".shp");
      else
        ff = new BufferedInputStream(new FileInputStream(tmpName));      
      
      ByteBuffer f = ByteBuffer.wrap(byteArrayFromInputStream(ff));
      f.order(ByteOrder.LITTLE_ENDIAN);

      try
      {
        f.position(0);
        readSHPHeader(f, shpHeader);

        verifySignature(shpHeader.FileCode, tmpName);

        showHeader(shpHeader);

        tmpName = replaceFilePath(FileName, ".shx");

        InputStream ffx = null;
        if (isResource)
          ffx = this.getClass().getResourceAsStream(manifest + FileName + ".shx"); 
        else
          ffx = new BufferedInputStream(new FileInputStream(tmpName));
        
        
        ByteBuffer fx = ByteBuffer.wrap(byteArrayFromInputStream(ffx));
        fx.order(ByteOrder.LITTLE_ENDIAN);
        
        try
        {
          fx.position(0);
          //fx.Position = 0;
          readSHPHeader(fx, shpHeader);

          verifySignature(shpHeader.FileCode, tmpName);

          int NumRecords = showHeader(shpHeader);

          Series.clear();
          Series.beginUpdate();
          boolean oldrepaint = true;
          if (Series.getChart() != null)
          {
            oldrepaint = Series.getChart().getAutoRepaint();
            Series.getChart().setAutoRepaint(false);
          }

          for (int i = 1; i <= NumRecords; ++i)
          {
            readRecordHeader(fx, shpRecordHeader);

            //f.Position = 2 * shpRecordHeader.RecordNumber;
           // f.position(2 * shpRecordHeader.RecordNumber);
            readRecordHeader(f, shpRecordHeader); 
            
            int pos = f.position();
            

            ShapeType = f.getInt();
            loadShape(f, Series, FieldName, FieldValue, i - 1);
          }

          Series.endUpdate();
          if (Series.getChart() != null)
          {
            Series.getChart().setAutoRepaint(oldrepaint);
          }
          //Series.Chart.Invalidate();
        }
        finally
        {
          ffx.close();
        }
      }
      finally
      {
        ff.close();
      }
    }

    private void loadShape(ByteBuffer f, Map Series, String fieldName, String fieldValue, int count) throws Exception
    {
      Polygon tmpShape;
      final int MaxNumParts = 10000;
      double[] Box = new double[4];
      int NumParts, NumPoints;
      int[] Parts = new int[MaxNumParts - 1];
      int p;
      String tmpText = "";
      double tmpZ = 0;

      switch (ShapeType)
      {
        case SHP_POLYLINE:
        case SHP_POLYGON:
          Box[0] = f.getDouble();
          Box[1] = f.getDouble();
          Box[2] = f.getDouble();
          Box[3] = f.getDouble();
          NumParts = f.getInt();
          NumPoints = f.getInt();

          if (NumParts > MaxNumParts) throw new Exception("Maximum number of parts exceeded: " + NumParts);

          for (p = 0; p < NumParts; ++p)
          {
            Parts[p] = f.getInt();
          }

          tmpText = "";
          tmpZ = rnd.nextDouble() * SHPMaxRandom;

          po = 0;

          for (p = 1; p <= NumParts; ++p)
          {
            tmpShape = new Polygon(Series.getShapes(), Series.getChart());

            if (p == NumParts) readPoints(f, tmpShape, NumPoints);
            else readPoints(f, tmpShape, Parts[p]);

            int i = Series.getShapes().add(tmpShape);
            Series.getShapes().getPolygon(i).setClosed((ShapeType == SHP_POLYGON));
            Series.getShapes().getPolygon(i).setZ(tmpZ);
            Series.getShapes().getPolygon(i).setText(tmpText);

          }
          break;

        default:
          throw new Exception("Invalid ShapeType " + ShapeType);
      }
    }

    private void readPoints(ByteBuffer f, Polygon Shape, int UpTo) throws IOException
    {
      Shape.getPoints().beginUpdate();
      for (int t = 0; t < UpTo - po; ++t)
      {
        Shape.add(f.getDouble(), f.getDouble());
      }

      po = UpTo;

      Shape.getPoints().endUpdate();
    }

    private int showHeader(SHPHeader ASHPHeader)
    {
      short[] B = integerBytes(ASHPHeader.FileLength);
      int result = B[3] + (B[2] * 256) + (B[1] * 256 * 256) + (B[0] * 256 * 256 * 256);
      result = (result * 2 - 100) / 8;
      return result;
    }

    private void verifySignature(int Value, String FileName) throws Exception
    {
      short[] B = integerBytes(Value);
      if ((B[0] != 0) || (B[1] != 0) || (B[2] != 0x27) || ((B[3] != 0x0A) && (B[3] != 0x0D)))
        throw new Exception("Invalid file: " + FileName);
    }

    private void addDouble(SHPHeader ASHPHeader, int Order, double MyDouble)
    {
      switch (Order)
      {
        case 0:
          ASHPHeader.XMin = MyDouble;
          break;
        case 1:
          ASHPHeader.YMin = MyDouble;
          break;
        case 2:
          ASHPHeader.XMax = MyDouble;
          break;
        case 3:
          ASHPHeader.YMax = MyDouble;
          break;
        case 4:
          ASHPHeader.ZMin = MyDouble;
          break;
        case 5:
          ASHPHeader.ZMax = MyDouble;
          break;
        case 6:
          ASHPHeader.MMin = MyDouble;
          break;
        case 7:
          ASHPHeader.MMax = MyDouble;
          break;
      }
    }


    private void addInteger(SHPHeader ASHPHeader, int Order, int MyInt32)
    {
      switch (Order)
      {
        case 0:
          ASHPHeader.FileCode = MyInt32;
          break;
        case 1:
          ASHPHeader.Unused1 = MyInt32;
          break;
        case 2:
          ASHPHeader.Unused2 = MyInt32;
          break;
        case 3:
          ASHPHeader.Unused3 = MyInt32;
          break;
        case 4:
          ASHPHeader.Unused4 = MyInt32;
          break;
        case 5:
          ASHPHeader.Unused5 = MyInt32;
          break;
        case 6:
          ASHPHeader.FileLength = MyInt32;
          break;
        case 7:
          ASHPHeader.Version = MyInt32;
          break;
        case 8:
          ASHPHeader.ShapeType = MyInt32;
          break;
      }
    }


    private void addInteger(SHPRecordHeader ASHPRecordHeader, int Order, int MyInt32)
    {
      switch (Order)
      {
        case 0:
          ASHPRecordHeader.RecordNumber = MyInt32;
          break;
        case 1:
          ASHPRecordHeader.RecordLength = MyInt32;
          break;
      }
    }


    private void readSHPHeader(ByteBuffer f, SHPHeader ASHPHeader) throws IOException
    {
      int myInt32;
      double myDouble;

      for (int i = 0; i < 9; ++i)
      {
        myInt32 = f.getInt();
        addInteger(ASHPHeader, i, myInt32);
      }

      for (int i = 0; i < 8; ++i)
      {
        myDouble = f.getDouble();
        addDouble(ASHPHeader, i, myDouble);
      }
    }


    private void readRecordHeader(ByteBuffer f, SHPRecordHeader ASHPRecordHeader) throws IOException
    {
      int myInt32;

      for (int i = 0; i < 2; ++i)
      {
        myInt32 = f.getInt();
        addInteger(ASHPRecordHeader, i, myInt32);
      }

      ASHPRecordHeader.RecordNumber = reverse(ASHPRecordHeader.RecordNumber);
      ASHPRecordHeader.RecordLength = reverse(ASHPRecordHeader.RecordLength);

    }

    private int reverse(int Value)
    {
      short[] B = integerBytes(Value);
      int result = (B[0] * 256 * 256 * 256) + (B[1] * 256 * 256) + (B[2] * 256) + B[3];
      return result;
    }

    private String replaceFilePath(String fileName, String extension)
    {
      File file = new File(fileName);
      boolean exists = file.exists();

      int i = fileName.indexOf(".");

      if (i == -1)
        fileName += extension;
      else
        fileName = fileName.substring(0, i) + extension;

      file = new File(fileName);
      exists = file.exists();

      if (exists)
        return fileName;
      else
        //return Application.StartupPath + @"\.\Styles\Maps\" + fileName;
        return "";
        
    }
}
