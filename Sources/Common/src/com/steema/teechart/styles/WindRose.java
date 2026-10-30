package com.steema.teechart.styles;


import com.steema.teechart.languages.Language;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.misc.Utils;

/**
 *
 * <p>Title: WindRose class</p>
 *
 * <p>Description: WindRose Series.</p>
 *
 * <p>Example:
 * <pre><font face="Courier" size="4">
 * series1 = new WindRose(myChart.getChart());
 * series1.clear();
 * series1.add( 30, 100, "", Color.EMPTY );
 * series1.add( 60, 200, "", Color.EMPTY );
 * series1.add( 90,  50, "", Color.EMPTY );
 * series1.add( 120, 150, "", Color.EMPTY );
 * series1.setCircled(true);
 * series1.setAngleIncrement(15);
 * series1.getBrush().setVisible(false);
 * series1.getMarks().setVisible(false);
 * series1.setCircleBackColor(Color.SILVER);
 * series1.getCircleLabelsFont().setColor(Color.YELLOW);
 * series1.getCircleLabelsFont().setBold(true);
 * series1.getPointer().getBrush().setColor(Color.BLUE);
 * series1.getPointer().setHorizSize(2);
 * series1.getPointer().setVertSize(2);
 * series1.getPointer().setVisible(true);
 * </font></pre></p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class WindRose extends Custom2DPolar {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WindRose() {
        this(null);
    }

    public WindRose(IBaseChart c) {
        super(c);

        setCircleLabels(true);
        setRotationAngle(90);
    }

    protected String getCircleLabel(double angle, int index) {

        switch (Utils.round(angle)) {
        case 0:
            return "N";
        case 15:
            return "NNNW";
        case 30:
            return "NNW";
        case 45:
            return "NW";
        case 60:
            return "NWW";
        case 75:
            return "NWWW";
        case 90:
            return "W";
        case 105:
            return "SWWW";
        case 120:
            return "SWW";
        case 135:
            return "SW";
        case 150:
            return "SSW";
        case 165:
            return "SSSW";
        case 180:
            return "S";
        case 195:
            return "SSSE";
        case 210:
            return "SSE";
        case 225:
            return "SE";
        case 240:
            return "SEE";
        case 255:
            return "SEEE";
        case 270:
            return "E";
        case 285:
            return "NEEE";
        case 300:
            return "NEE";
        case 315:
            return "NE";
        case 330:
            return "NNE";
        case 345:
            return "NNNE";
        default:
            return "";
        }
    }

    public void prepareForGallery(boolean isEnabled) {
        super.prepareForGallery(isEnabled);
        setAngleIncrement(45);
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("GalleryWindRose");
    }
}

