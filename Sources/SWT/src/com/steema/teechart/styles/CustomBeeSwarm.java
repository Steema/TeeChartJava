/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steema.teechart.styles;

import com.steema.teechart.IBaseChart;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.IGraphics3D;
import com.steema.teechart.drawing.Point;
import com.steema.teechart.functions.KDEType;
import java.util.Random;

/**
 *
 * @author Dev
 */
public class CustomBeeSwarm extends CustomViolin {
    
    public CustomBeeSwarm() {
        super();
        this.getPointer().setVisible(true);
        this.getPointer().setStyle(PointerStyle.CIRCLE);
        getKdeFunction().getOptions().setkDEType(KDEType.BEESWARM);
    }
    
    public CustomBeeSwarm(IBaseChart c) {
        super(c);
        this.getPointer().setVisible(true);
        this.getPointer().setStyle(PointerStyle.CIRCLE);
        getKdeFunction().getOptions().setkDEType(KDEType.BEESWARM);        
    }
    
    /**
     * Gets descriptive text.
     *
     * @return String
     */
    @Override
    public String getDescription() {
        return ""; //Language.getString("GalleryBeeSwarm");
    }

    @Override
    protected void addSampleValues(int numValues) {
    	
    	com.steema.teechart.styles.Box boxSeries = new com.steema.teechart.styles.Box();
  
		//getKdeFunction().getOptions().setSides(KDESides.BOTH);
		
		numValues = 30;
		Random rand = new Random();
		
		boxSeries.setPosition(0);
		boxSeries.setActive(false);

		for (int t = 0; t < numValues; t++)
		{
			boxSeries.add(rand.nextInt(100));
		}

		setDataSource(boxSeries);
    }
    
    /// <summary>
        /// Update runs the calculation. It assumes that a Series as datasource 
        /// has been set.
        /// </summary>
    /**
     * Update runs the calculation. It assumes that a Series as datasource 
     * has been set.
     * 
     * @param aYValues 
     */
    @Override
    public void update(ValueList aYValues) {
        int rowcount;

        if ((this.getDataSource()!=null) && ((this.getDataSource().getClass() == CustomBox.class) 
                                  || (this.getDataSource().getClass() == Box.class)
                                  || (this.getDataSource().getClass() == HorizBox.class)))
            this.setPosition(((CustomBox)getDataSource()).getPosition());

        getKdeFunction().setPosition(this.getPosition());
        getKdeFunction().getOptions().setkDEType(KDEType.BEESWARM);
        getKdeFunction().addListPoints(aYValues);

        if (!firstDraw)
        {
            //force a paint to calc
            firstDraw = true;
            if (IVertical)
              this.getChart().image(1, 400);
            else
              this.getChart().image(900,1);  
          
        }
        
        if (IVertical)
          getKdeFunction().getOptions().setResolution((int)((Math.abs(getVertAxis().calcYPosValue(aYValues.getMaximum()) -
                                                    getVertAxis().calcYPosValue(aYValues.getMinimum()))) /
                                                    ((this.getPointer().getVertSize() + this.getPointer().getPen().getWidth()) * 2)));
        else
            //getKdeFunction().getOptions().setResolution(28);
          getKdeFunction().getOptions().setResolution((int)
                  (Math.abs(getHorizAxis().calcXPosValue(getXValues().getMaximum()) - getHorizAxis().calcXPosValue(getXValues().getMinimum()))) /
                                                    ((this.getPointer().getHorizSize() + this.getPointer().getPen().getWidth()) * 2));

        /*KDE.Options.Resolution = Utils.Round((Math.Abs(GetHorizAxis.CalcXPosValue(XValues.Maximum) -
                                                     GetHorizAxis.CalcXPosValue(XValues.Minimum))) /
                                                     ((Pointer.HorizSize + Pointer.Pen.Width) * 2));*/
            
            
        getKdeFunction().calculateKDE();
        
        this.clear();
        getYValues().setOrder(ValueListOrder.NONE);
        getXValues().setOrder(ValueListOrder.NONE);

        for (int i = 0; i < getKdeFunction().getKDEPoints().size(); i++)
        {
            rowcount = (int)(getKdeFunction().getKDEPoints().get(i).x);
            if (rowcount > 0)
            {
                if (IVertical)
                  add(rowcount, getKdeFunction().getKDEPoints().get(i).y);
                else
                  add(getKdeFunction().getKDEPoints().get(i).y,rowcount);
            }
        }


        calcFirstLastVisibleIndex();

        getChart().invalidate();
    }
    
    @Override
    public void draw()
    {
        IGraphics3D aCanvas;
        Point tmpPoint;
        int pointSize, pointsWidth, tmpPos;

        if (getDataSource() != null)
            if ((getDataSource().getClass() == Box.class) || (getDataSource().getClass() == HorizBox.class))
              update(((Series)(getDataSource())).mandatory);

        aCanvas = getChart().getGraphics3D();

        aCanvas.setBrush(this.getBrush());
        aCanvas.getBrush().setColor(this.getColor());
        getChart().getGraphics3D().getBrush().setColor(this.getColor());

        aCanvas.setPen(this.getLinePen());

        for (int i = 0; i < this.getCount(); i++)
        {
            if (IVertical)
            {
                pointSize = (int)(1 + (this.getPointer().getVertSize() + this.getPointer().getPen().getWidth()) * 2);
                pointsWidth = (int)(pointSize * this.getXValues().getValue(i));
                tmpPos = (int)(this.getHorizAxis().calcXPosValue(getPosition()) - (pointsWidth / 2) + (pointSize / 2));
                for (int j = 0; j < (int)(this.getXValues().getValue(i)); j++)
                {
                    tmpPoint = new Point(tmpPos, this.calcYPos(i));
                    if (this.getPointer().getVisible())
                        this.drawPointer((int)(tmpPoint.x), (int)(tmpPoint.y), this.getValueColor(i), i);

                    tmpPos = tmpPos + pointSize;
                }
            }
            else
            {
                pointSize = (int)(1 + (this.getPointer().getHorizSize() + this.getPointer().getPen().getWidth()) * 2);
                pointsWidth = (int)(pointSize * this.getYValues().getValue(i));
                tmpPos = (int)(this.getVertAxis().calcYPosValue(getPosition()) - (pointsWidth / 2) + (pointSize / 2));
                for (int j = 0; j < (int)(this.getYValues().getValue(i)); j++)
                {
                    tmpPoint = new Point(tmpPos, this.calcXPos(i));
                    if (this.getPointer().getVisible())
                        this.drawPointer((int)(tmpPoint.y), (int)(tmpPoint.x), this.getValueColor(i), i);

                    tmpPos = tmpPos + pointSize;
                }              
            }
        }
    }
}
