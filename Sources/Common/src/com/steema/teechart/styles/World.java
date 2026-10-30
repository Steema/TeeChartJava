package com.steema.teechart.styles;

  

import com.steema.teechart.IBaseChart;
import com.steema.teechart.data.SHP;
import com.steema.teechart.drawing.IGraphics3D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

  /// <summary>
  /// World Map series.
  /// </summary>
  public class World extends Map {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private WorldMapType map = WorldMapType.EUROPE;
    private SHP teeshp;
    private com.steema.teechart.Shape shadow = null;

    private String[] loadStrings(String Name)
    {
      Name = "/com/steema/teechart/styles/maps/" + Name + ".txt";
      InputStream sr = this.getClass().getResourceAsStream(Name); 
      try
      {
            try {
                return inputStreamAsString(sr);
            } catch (IOException ex) {
                Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
            }
      }
      finally
      {
            try {
                sr.close();
            } catch (IOException ex) {
                Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
            }
      }
      return null;
    }
    
    public String[] inputStreamAsString(InputStream stream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();
        String line = null;

        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }

        br.close();
        String[] result = sb.toString().split("\n");
        return result;
    }

    private int splitFilterIndex(String value)
    {
      int i = value.indexOf(' ');
      String num = value.substring(0, i);
      return Integer.parseInt(num);
    }
    
    private String splitFilterLabel(String value)
    {
      int i = value.indexOf(' ');
      return value.substring(i + 1);
    }

    private void filterShapes(String Filter)
    {
      String[] st = loadStrings(Filter);
      int shapeindex;
      String tmp = "";
      for (int i = 0; i < st.length; i++) 
      {
        shapeindex = splitFilterIndex(st[i]);
        tmp = splitFilterLabel(st[i]);
        getLabels().set(shapeindex, tmp);

        if (getMap() != WorldMapType.WORLD)
            this.getMandatory().setValue(shapeindex, 12345678);
      }
    }

    private void loadMapShapes() throws FileNotFoundException, IOException, ClassNotFoundException, Exception
    {
      beginUpdate();
      try
      {
        clear();
        // //#1 : Load shapes
        switch (getMap().getValue())
        {
          case 8: loadMapFromResource(this, "Spain"); break;
          case 12:
          case 13: loadMapFromResource(this, "USA"); break;
          default: loadMapFromResource(this, "World"); break;
        }
        // //#2 : Filter shapes
        switch (getMap().getValue())
        {
          case 1: filterShapes("Africa"); break;
          case 2: filterShapes("Asia"); break;
          case 3: filterShapes("AustraliaNewZealand"); break;
          case 4: filterShapes("CentralAmerica"); break;
          case 5: filterShapes("Europe"); break;
          case 6: filterShapes("Europe_EC15"); break;
          case 7: filterShapes("Europe_EC27"); break;
          case 8: filterShapes("Spain"); break;
          case 9: filterShapes("MiddleEast"); break;
          case 10: filterShapes("NorthAmerica"); break;
          case 11: filterShapes("SouthAmerica"); break;
          case 12: filterShapes("USA"); break;
          case 13: filterShapes("USAHawaiiAlaska"); break;
          case 0:
            String[] st = loadStrings("World");
            for (int i = 0; i < st.length; i++) 
            {
              filterShapes(st[i]);
            } break;
        }
        

        if (getMap() != WorldMapType.WORLD)
        {
          int t = 0;
          while (t < getShapes().size())
          {
            if (getMandatory().getValue(t) == 12345678.0)
            {
              getShapes().getPolygon(t).setIndex(t);
              t++;
            }
            else delete(t);
          }
        }
        
        // Clean values
        for (int t = 0; t < getShapes().size(); t++)
        {
          getMandatory().setValue(t, 0); 
        }
      }
      finally
      {
        endUpdate();
      }
    }

    public void assign(Series source)
    {
      super.assign(source);
      if (source instanceof World)
      {
        this.shadow.assign(((World)source).shadow);
        this.map = ((World)source).map;
      }
    }

    public void galleryChanged3D(boolean Is3D)
    {
      if (getChart() != null) getChart().getAspect().setView3D(false);
    }
 
    public void setSubGallery(int index)
    {
      map = WorldMapType.fromInt(index);
    }
 
    public void prepareForGallery(boolean IsEnabled)
    {
      inGallery = true;
      try
      {
        super.prepareForGallery(IsEnabled);
      }
      finally
      {
        inGallery = false;
      }
    }

    @Override
    public void setChart(IBaseChart c)
    {
        super.setChart(c);
        try {
            tryLoadMapShapes();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }
     }

    private void tryLoadMapShapes() throws FileNotFoundException, IOException, ClassNotFoundException, Exception
    {
       if (getChart() != null)
       {
	  getChart().getAspect().setView3D(false);
	  if (!inGallery) loadMapShapes();

       }
    }

    private String getResourceName(String titleText)
    {
        if("World".equals(titleText)) { map = WorldMapType.WORLD; return "WorldCities"; }
        else if("Africa".equals(titleText)) { map = WorldMapType.AFRICA; return "Africa"; }
        else if("Asia".equals(titleText)) { map = WorldMapType.ASIA; return "Asia"; }
        else if("Australia".equals(titleText)) { map = WorldMapType.AUSTRALIA; return "AustraliaNewZealand"; }
        else if("CentralAmerica".equals(titleText)) { map = WorldMapType.CENTRALAMERICA; return "CentralAmerica"; }
        else if("Europe".equals(titleText)) { map = WorldMapType.EUROPE; return "World"; }
        else if("Europe15".equals(titleText)) { map = WorldMapType.EUROPE15; return "World"; }
        else if("Europe27".equals(titleText)) { map = WorldMapType.EUROPE27; return "World"; }
        else if("Spain".equals(titleText)) { map = WorldMapType.SPAIN; return "Spain"; }
        else if("MiddleEast".equals(titleText)) { map = WorldMapType.MIDDLEEAST; return "World"; }
        else if("NorthAmerica".equals(titleText)) { map = WorldMapType.NORTHAMERICA; return "NorthAmerica"; }
        else if("SouthAmerica".equals(titleText)) { map = WorldMapType.SOUTHAMERICA; return "SouthAmerica"; }
        else if("USA".equals(titleText)) { map = WorldMapType.USA; return "USA"; }
        else if("USAHawaiiAlaska".equals(titleText)) { map = WorldMapType.USAHAWAIIALASKA; return "USAHawaiiAlaska"; }
        else { map = WorldMapType.WORLD; return "WorldCities"; }
    }

    protected void addSampleValues(int numValues)
    {  
      if (inGallery)
        super.addSampleValues(numValues);
      else
      {
        SeriesRandom r = randomBounds(numValues);
        double tmpH = r.DifY * 0.25;
        for (int t = 0; t < getCount(); t++)
        {
          r.tmpY = java.lang.Math.abs(r.tmpY + r.Random() * tmpH - (tmpH * 0.5));
          getMandatory().setValue(t, r.tmpY);
        }
      }
    }

    @Override
    protected void draw()
    {
      if (getShadow().getVisible())
      {
        IGraphics3D g = getChart().getGraphics3D();
        //Color oldbrushcolor = g.Brush.Color;
        //bool oldVisible = g.Pen.Visible;
        //g.Pen.Visible = false;
        //g.Brush.Color = Shadow.Color;
        for (int t = 0; t < getShapes().size(); t++)
        {
          if ((getShapes().getPolygon(t).getPoints().getActive()) && (getShapes().getPolygon(t).getPoints().getCount() > 0) && (getShapes().getPolygon(t).visible()))
          {
            getShapes().getPolygon(t).calcPoints(getShadow().getWidth(), getShadow().getHeight());
            getShapes().getPolygon(t).draw(g, t);
          }
        }
        //g.Pen.Visible = oldVisible;
        //g.Brush.Color = oldbrushcolor;
      }

      super.draw();
    }

    public void fillSampleValues(int numValues)
    {
      beginUpdate();

      try
      {
        addSampleValues(numValues);
        checkOrder();
      }
      finally
      {
        endUpdate();
      }

      manualData = false;
    }

    public void loadMapFromResource(Map Series, String ShpName) throws FileNotFoundException, IOException, ClassNotFoundException, Exception
    {
      if(teeshp == null) teeshp = new SHP();  
      teeshp.loadMap(Series, ShpName, true);
    }

    public World(WorldMapType initArg)
    {
	this((IBaseChart)null);
    
	inGallery = true;
	if (initArg != null)
	{
	   map = initArg;
	}
    }

    public World()   
    {
	this((IBaseChart)null);
    
    }

    public World(IBaseChart c) 
    {
	super(c);
    }

    public World(IBaseChart c, String loadMaps)
    {
        super(c);
        inGallery = Boolean.valueOf(loadMaps);
    }

    public WorldMapType getMap() 
    { 
        if(map == null) map = WorldMapType.EUROPE;
        return map; 
    }
    
    public void setMap(WorldMapType value) throws FileNotFoundException, IOException, ClassNotFoundException, Exception
    {
        map = value;
        if (!inGallery)
        {
          loadMapShapes();
        }
        invalidate();
    }

    public com.steema.teechart.Shape getShadow()
    {
        if (shadow == null) shadow = new com.steema.teechart.Shape(getChart());
        return shadow;
    }
      
    public void setShadow(com.steema.teechart.Shape value)
    {
        shadow = value;
        invalidate();
    }

    private boolean inGallery = false;

    public boolean getInGallery() { return inGallery; }
			
    public void setInGallery(boolean value) { inGallery = value; }
}
