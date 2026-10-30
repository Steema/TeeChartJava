package com.steema.teechart.imports;

import com.steema.teechart.misc.Utils;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.Series;
import com.steema.teechart.ChartException;
import com.steema.teechart.drawing.Color;

import java.io.InputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;

/*
 * Chart import data from XML
 * (Exported via getExport().getData().getXML().save())
 */
public class XMLImport extends DataImportFormat  {
    
    private String sSeriesNode="";
    private String sDataMember="";
    
    /** Creates a new instance of XMLImport */
    public XMLImport(IBaseChart c) {
        super(c);
    }

    /**
     * XML Stream to process for import
     */
    public void open(InputStream stream) throws IOException,
            ClassNotFoundException {
        
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
          DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
          
          try {
            Document m_doc = docBuilder.parse(stream);
            try {
              Load(m_doc);
            } catch (ChartException cExp) {}
          }
          catch (org.xml.sax.SAXException sar) {}
        }
       catch (javax.xml.parsers.ParserConfigurationException err) {};
    }
    
    private void XMLError(String error) throws ChartException
    {
      throw new ChartException(error);
    }

    /**
     * Returns Series (if set) into which to import XML data
     */
    public String getSeriesNode() 
    {
      return sSeriesNode;
    }
    
    /**
     * Set Series into which to import data. If not set Import process will 
     * create Series.
     */
    public void setSeriesNode(String value) 
    {
      sSeriesNode=value;
      for (int t=0;t<chart.getSeries().size();t++){
          if (chart.getSeries(t).getTitle().equals(value))
          {
            series=chart.getSeries(t);
            break;
          }
      }
    }

    /*
     * Gets ValueList Datamember if set.
     */
    public String getDataMember() 
    {
      return sDataMember;
    }
    
    /**
     * Sets ValueList Datamember.
     */
    public void setDataMember(String value) 
    {
      sDataMember=value;
    }
   
    private NodeList getSelectedNodes(Node node,  String arg)
    {
        Node tmpNode = node;

        for (int t=0;t<node.getChildNodes().getLength();t++)
            if (node.getChildNodes().item(t).getNodeName()==arg)
                tmpNode.appendChild(node.getChildNodes().item(t));
        return tmpNode.getChildNodes();
    }
    
    private void LoadSeriesNode(Node node,Document d) throws InstantiationException, 
            IllegalAccessException, ChartException
    {
      Node tmpValue, tmpValueX, tmpText, tmpItem, tmpColor;
      String tmpName, tmpX, tmpTex, tmpList;
      Color tmpCol;
      Series tmpSeries=series;
      String tmpSeriesTitle="";
      Object tmpObj;
      Series tmpClass;
      
      //tmpSeriesTitle=node.getAttributes().getNamedItem("title").getTextContent();
      tmpSeriesTitle=node.getAttributes().getNamedItem("title").getNodeValue();

      if (tmpSeries!=null) tmpSeries.clear();
      else
      {
        // Create a new Series...
        tmpClass=null;
        tmpItem=node.getAttributes().getNamedItem("type");
        
        String tmpSeriesName=tmpItem.getNodeValue(); //tmpItem.getTextContent();

        if (tmpItem!=null) 
        {
	  tmpName="com.steema.teechart.styles.";
          tmpName=tmpName+tmpSeriesName;

          for (int t=0;t<Utils.seriesTypesOf.length;t++)
          {
              if (Utils.seriesTypesOf[t].toString().equalsIgnoreCase(("class "+tmpName).toString()))
              //if (Utils.seriesTypesOf[t].toString().equals(("class "+tmpName).toString()))
              {
                  try
                  {
                  tmpClass = Series.newFromType(Utils.seriesTypesOf[t]);
                  } catch (Exception e) {}
                  break;
              }
          }

          if (tmpClass==null) tmpClass=Series.newFromType(Utils.seriesTypesOf[5]);  // Bar

          int idx = chart.addSeries(tmpClass);
          tmpSeries = chart.getSeries(idx);

          // Series Title
          tmpItem=node.getAttributes().getNamedItem("title");
          if (tmpItem!=null) 
            tmpSeries.setTitle(tmpItem.getNodeValue() /*tmpItem.getTextContent()*/);

          // Series Color
          tmpColor=node.getAttributes().getNamedItem("color");
          if (tmpColor!=null) 
            tmpSeries.setColor(Color.fromCode(tmpColor.getNodeValue() /*tmpColor.getTextContent()*/));
        }
      }

      NodeList tmpPoints=d.getElementsByTagName("points");

      if (tmpPoints!=null)
      {
        NodeList tmpPoint=d.getElementsByTagName("point");
          
        if (tmpPoint==null) XMLError("No <point> nodes.");
        else
        {
          tmpName=tmpSeries.getMandatory().valueSource;
          if (tmpName.length()==0) tmpName=getDataMember();
          if (tmpName.length()==0) tmpName=tmpSeries.getMandatory().getName();

          tmpX=tmpSeries.getNotMandatory().valueSource;
          if (tmpX.length()==0) tmpX=tmpSeries.getNotMandatory().getName();

          for (int t=0; t<tmpPoint.getLength(); t++)
          {
            //if (tmpSeriesTitle=="" /*tmpPoint.item(t).getParentNode().getParentNode().getAttributes().getNamedItem("title").getTextContent()*/){
              if (tmpSeriesTitle==tmpPoint.item(t).getParentNode().getParentNode().getAttributes().getNamedItem("title").getNodeValue()){
                NamedNodeMap tmpItems = tmpPoint.item(t).getAttributes();
                if (tmpItems==null)
                {
                  XMLError("<point> node has no data.");
                  break;
                }
                else
                {
                  tmpText=tmpItems.getNamedItem("text");
                  if (tmpText==null) 
                    tmpTex="";
                  else 
                    tmpTex=tmpText.getNodeValue(); //tmpText.getTextContent();

                  tmpColor=tmpItems.getNamedItem("color");
                  if (tmpColor==null) tmpCol=Color.EMPTY;

                  else tmpCol=Color.fromCode(tmpColor.getNodeValue() /*tmpColor.getTextContent()*/);

                  // Rest of values (if exist)
                  for (int tt=2; tt<tmpSeries.getValuesLists().size(); tt++)
                  {

                    tmpList=tmpSeries.getValuesLists().getValueList(tt).valueSource;
                    if (tmpList.length()==0) 
                      tmpList=tmpSeries.getValuesLists().getValueList(tt).getName();

                    tmpValue=tmpItems.getNamedItem(tmpList);
                    if (tmpValue!=null)
                      tmpSeries.getValuesLists().getValueList(tt).tempValue=Double.parseDouble(tmpValue.getNodeValue());
                  }

                  // Get X and Y values
                  tmpValue=tmpItems.getNamedItem(tmpName);
                  tmpValueX=tmpItems.getNamedItem(tmpX);

                  // Add point !
                  if (tmpValue==null)
                    if (tmpValueX==null)
                      tmpSeries.addNull(); //  .Add(tmpTex);
                    else
                      tmpSeries.add(Double.parseDouble(tmpValueX.getNodeValue()),0.0,tmpTex);
                  else
                    if (tmpValueX==null)
                    tmpSeries.add(Double.parseDouble(tmpValue.getNodeValue()),tmpTex,tmpCol);
                  else
                    tmpSeries.add(Double.parseDouble(tmpValueX.getNodeValue()),
                      Double.parseDouble(tmpValue.getNodeValue()),tmpTex,tmpCol);
                }
            }
          }
        }
      }
      else XMLError("No <points> node.");
    }
    
    /**
     * Passes XML Document to be parsed
     */
    public void Load(Document d) throws ChartException
    {
      if ((chart!=null) || (series!=null))
      {
        NodeList tmpSeries=d.getElementsByTagName("series");

        if (tmpSeries==null) XMLError("No <series> nodes.");
        else
        {
          if (series==null){
              while (chart.getSeriesCount()>0)
                  chart.removeSeries(chart.getSeries(0));
          }

          if (getSeriesNode().length()==0)
          { 
            if (tmpSeries.getLength()>0)
              for (int t=0; t<tmpSeries.getLength(); t++)
              {
                try {
                LoadSeriesNode(tmpSeries.item(t),d);
                } catch (IllegalAccessException ex) {
                } catch (InstantiationException ex) {}
                if (series!=null) break;
              }
            else
              XMLError("Empty <series> node.");
          }
          else
          {
            boolean tmpFound=false;
            
            if (tmpSeries.getLength()>0){
                for (int t=0; t<tmpSeries.getLength(); t++)
                {
                  Node tmpTitle=tmpSeries.item(t).getAttributes().getNamedItem("title");
                  if (tmpTitle!=null)
                    //if (tmpTitle.getTextContent().toLowerCase().equals(getSeriesNode().toLowerCase()))
                      if (tmpTitle.getNodeValue().toLowerCase().equals(getSeriesNode().toLowerCase()))
                    {
                      try{
                      LoadSeriesNode(tmpSeries.item(t),d);
                      } catch (IllegalAccessException ex) {
                      } catch (InstantiationException ex) {}
                      tmpFound=true;
                      break;
                    }
                }
            }

            if (!tmpFound) XMLError("Series "+getSeriesNode()+" not found");
          }
        }
      }
    }
}
