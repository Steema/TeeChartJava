package com.steema.teechart.exports.editors;

import java.awt.Rectangle;
import javax.swing.*;
import com.steema.teechart.languages.Language;

/**
 * <p>Title: BMPOptions class</p>
 *
 * <p>Description: BMP Options Export</p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class BMPOptions extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BMPOptions() {
        try {
            jbInit();
            Load();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void Load() {
        cbColors.removeAllItems();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement(Language.getString("Default"));
        model.addElement(Language.getString("GrayScale"));
        cbColors.setModel(model);
        cbColors.setSelectedIndex(0);
    }

    public int getColorReduction() {
        return cbColors.getSelectedIndex();
    }

    private boolean getCBColorVisible() {
        return cbColors.isVisible();
    }

    private void setCBColorVisible(boolean value){
        cbColors.setVisible(value);
        jLabel1.setVisible(value);
    }

    public class EPSOptions
    {
      public EPSOptions(){
          super();
      }

      public void Load() {
          setCBColorVisible(false);
      }
    }

    public class PDFOptions  {
      public PDFOptions() {
          super();
      }

      public void Load()  {
          setCBColorVisible(false);
      }
    }

    public class SVGOptions  {
        public SVGOptions() {
            super();
        }

        public void Load()  {
           setCBColorVisible(false);
        }
    }

    public class VMLOptions {
        public VMLOptions() {
            super();
        }

        public void Load() {
            setCBColorVisible(false);
        }
    }

    private void jbInit() throws Exception {
        jLabel1.setDisplayedMnemonic('C');
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setLabelFor(cbColors);
        jLabel1.setText("Colors :");
        jLabel1.setBounds(new Rectangle(20, 17, 50, 15));
        this.setLayout(null);
        cbColors.setBounds(new Rectangle(81, 13, 103, 22));
        this.add(cbColors);
        this.add(jLabel1, null);
    }

    JLabel jLabel1 = new JLabel();
    JComboBox cbColors = new JComboBox();
}



