package com.steema.teechart.styles;

import com.steema.teechart.misc.Enum;


  public class WorldMapType extends Enum
  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final WorldMapType WORLD = new WorldMapType(0);
    public static final WorldMapType AFRICA = new WorldMapType(1);
    public static final WorldMapType ASIA = new WorldMapType(2);
    public static final WorldMapType AUSTRALIA = new WorldMapType(3);
    public static final WorldMapType CENTRALAMERICA = new WorldMapType(4);
    public static final WorldMapType EUROPE = new WorldMapType(5);
    public static final WorldMapType EUROPE15 = new WorldMapType(6);
    public static final WorldMapType EUROPE27 = new WorldMapType(7);
    public static final WorldMapType SPAIN = new WorldMapType(8);
    public static final WorldMapType MIDDLEEAST = new WorldMapType(9);
    public static final WorldMapType NORTHAMERICA = new WorldMapType(10);
    public static final WorldMapType SOUTHAMERICA = new WorldMapType(11);
    public static final WorldMapType USA = new WorldMapType(12);
    public static final WorldMapType USAHAWAIIALASKA = new WorldMapType(13);
    
    
     private WorldMapType(int value) {
        super(value);
    }
     
     public static WorldMapType fromName(String value) {
        int index = -1; 
        for(int i = 0; i < stringTable.length; i++) {
            if(value == stringTable[i]) {
                index = i;
                break;
            }
        }
        
        if(index == -1) {
            return WorldMapType.WORLD;
        }
        else {
            return fromInt(index);
        }
     }
     
     public static WorldMapType fromInt(int value) {
         switch(value) {
             case 1: return WorldMapType.AFRICA;
             case 2: return WorldMapType.ASIA;
             case 3: return WorldMapType.AUSTRALIA;
             case 4: return WorldMapType.CENTRALAMERICA;
             case 5: return WorldMapType.EUROPE;
             case 6: return WorldMapType.EUROPE15;
             case 7: return WorldMapType.EUROPE27;
             case 8: return WorldMapType.SPAIN;
             case 9: return WorldMapType.MIDDLEEAST;
             case 10: return WorldMapType.NORTHAMERICA;
             case 11: return WorldMapType.SOUTHAMERICA;
             case 12: return WorldMapType.USA;
             case 13: return WorldMapType.USAHAWAIIALASKA;
             default: return WorldMapType.WORLD;
         }
     }
     
        private static final String[] stringTable = {
             "World",
             "Africa",
             "Asia",
             "Australia",
             "CentralAmerica",
             "Europe",
             "Europe15",
             "Europe27",
             "Spain",
             "MiddleEast",
             "NorthAmerica",
             "SouthAmerica",
             "USA",
             "USAHawaiiAlaska"
         };

         public static String[] getNames() {
             return stringTable;
         }
  }

 