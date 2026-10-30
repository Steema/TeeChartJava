package com.steema.teechart.editors.tools;

// TODO

import com.steema.teechart.editors.tools.AxisToolEdit;
import com.steema.teechart.tools.Tool;
//import com.steema.teechart.tools.AxisScroll;

/**
 * <p>Title: AxisScrollEditor class</p>
 *
 * <p>Description:  Axis Scroll Editor panel</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class AxisScrollEditor extends AxisToolEdit {

    public AxisScrollEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTool(Tool tool) {
//         axisScroll=(AxisScroll)tool;
        bPen.setEnabled(false);
        bPen.setVisible(false);

     }


    private void jbInit() throws Exception {
        this.setLayout(null);
    }
}

