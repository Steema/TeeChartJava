package com.steema.teechart.functions;


import com.steema.teechart.languages.Language;
import java.util.ArrayList;
import com.steema.teechart.styles.ValueList;
import com.steema.teechart.IBaseChart;
import com.steema.teechart.styles.Series;
import com.steema.teechart.drawing.Graphics3D;
import com.steema.teechart.drawing.LineCross;


/**
 *
 * <p>Title: CrossPoints class</p>
 *
 * <p>Description: CrossPoints Function.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class CrossPoints extends Function {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ValueList tmp1, tmp2, tmpX1, tmpX2;

    public CrossPoints() {
        this((IBaseChart)null);
    }

    public CrossPoints(IBaseChart c) {
        super(c);

        canUsePeriod = false;
    }

    private LineCross linesCross(int index1, int index2) {
      LineCross result=
        Graphics3D.crossingLines(
                tmpX1.value[index1], tmp1.value[index1],
                tmpX1.value[index1 + 1], tmp1.value[index1 + 1],
                tmpX2.value[index2], tmp2.value[index2],
                tmpX2.value[index2 + 1], tmp2.value[index2 + 1]);
              return result;
    }


    public void addPoints(ArrayList source) {
        if (source.size()> 0) {
            Series s = (Series) source.get(0);
            if (s.getCount() > 0) {
                doCalculation(s, s.getNotMandatory());
            }
        }
    }

    protected void doCalculation(Series source,
                                 ValueList notMandatorySource) {
        if (getSeries().dataSourceArray().size() > 1) {
            source = (Series) (getSeries().dataSourceArray().get(0));
            tmp1 = valueList(source);
            tmpX1 = notMandatorySource;
            Series tmpS = (Series) (getSeries().dataSourceArray().get(1));
            tmp2 = valueList(tmpS);
            tmpX2 = tmpS.getNotMandatory();

            getSeries().clear();

            if ((tmpX1.count > 1) && (tmpX2.count > 1)) {
                int index1 = 0;
                int index2 = 0;

                LineCross r;

                do {
                    r= linesCross(index1, index2);
                    if (r.result) {
                        getSeries().add(r.cross.x, r.cross.y);
                    }

                    if (tmpX2.value[index2 + 1] < tmpX1.value[index1 + 1]) {
                        index2++;
                    } else {
                        index1++;
                    }

                } while ((index1 < tmpX1.count) && (index2 < tmpX2.count));
            }
        }
    }

    /**
     * Gets descriptive text.
     *
     * @return String
     */
    public String getDescription() {
        return Language.getString("FunctionCross");
    }
}

