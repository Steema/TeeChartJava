package com.steema.teechart.editors;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import com.steema.teechart.languages.Language;
import javax.swing.JButton;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.Dialog;

import com.steema.teechart.styles.Series;
import com.steema.teechart.Chart;
import com.steema.teechart.editors.gallery.ChartGallery;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import java.util.EventListener;

/**
 *
 * <p>Title: SeriesEditor class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class SeriesEditor extends JPanel {


    private Chart chart;

    public SeriesEditor() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        setUpDownIcons(bUp, bDown);
    }

    public SeriesEditor(Chart chart) {
        this();
        if (chart!=null) {
            setChart(chart);
            enableButtons();
        }
    }

    public void setChart(Chart chart) {
        this.chart = chart;
        lbSeries.setChart(chart);

        if (lbSeries.getModel().getSize()>0) {
          lbSeries.setSelectedIndex(0);
        }
    }

    // Internal event
    interface SeriesListener extends EventListener {
        public void seriesAdded(Series value);
        public void seriesRemoved(Series value);
    }

    private SeriesListener listener;

    public synchronized void addSeriesListener(SeriesListener l) {
        listener = l;
    }


    static final public ImageIcon loadImage(final String resourceName) {
        final java.awt.image.ImageProducer ip = (java.awt.image.ImageProducer)
                                                java.security.AccessController.
                                                doPrivileged(
                new java.security.
                PrivilegedAction() {
            public Object run() {
                java.net.URL url = getClass().getResource(resourceName);
                if (url == null) {
                    return null;
                } else {
                    try {
                        return url.getContent();
                    } catch (java.io.IOException ioe) {
                        return null;
                    }
                }
            }
        });

        return null;
    }

    private void jbInit() throws Exception {
        setLayout(borderLayout1);
        bCloneSeries.setBounds(new Rectangle(12, 149, 84, 27));
        bCloneSeries.setMnemonic(Language.getString("L").charAt(0));
        bCloneSeries.setText(Language.getString("Clone"));
        bCloneSeries.addActionListener(new
                                       SeriesEditor_bCloneSeries_actionAdapter(this));
        bChangeSeries.setBounds(new Rectangle(12, 184, 84, 27));
        bChangeSeries.setMnemonic(Language.getString("C").charAt(0));
        bChangeSeries.setText(Language.getString("Change"));
        bChangeSeries.addActionListener(new
                SeriesEditor_bChangeSeries_actionAdapter(this));
        bAddSeries.setBounds(new Rectangle(12, 45, 84, 27));
        bAddSeries.setMnemonic(Language.getString("A").charAt(0));
        bAddSeries.setText(Language.getString("Add"));
        bAddSeries.addActionListener(new SeriesEditor_jButton7_actionAdapter(this));
        bUp.addActionListener(new SeriesEditor_jbUp_actionAdapter(this));
        bDown.addActionListener(new SeriesEditor_jbDown_actionAdapter(this));
        bUp.setIcon(null);
        bTitleSeries.addActionListener(new
                                       SeriesEditor_bTitleSeries_actionAdapter(this));
        bDeleteSeries.addActionListener(new
                                        SeriesEditor_bDeleteSeries_actionAdapter(this));
        lbSeries.setBorder(BorderFactory.createLoweredBevelBorder());
        lbSeries.addListSelectionListener(new
                SeriesEditor_lbSeries_listSelectionAdapter(this));
        bUp.setEnabled(false);
        bDown.setBounds(new Rectangle(57, 7, 28, 26));
        bDown.setEnabled(false);
        jPanel3.setLayout(null);
        bDeleteSeries.setBounds(new Rectangle(12, 80, 84, 27));
        bDeleteSeries.setMnemonic(Language.getString("D").charAt(0));
        bDeleteSeries.setText(Language.getString("Delete"));
        bTitleSeries.setBounds(new Rectangle(12, 115, 84, 27));
        bTitleSeries.setMnemonic(Language.getString("T").charAt(0));
        bTitleSeries.setText(Language.getString("Title"));
        bUp.setBounds(new Rectangle(19, 7, 28, 26));
        jPanel1.setLayout(null);
        jPanel2.setLayout(null);
        jPanel3.setPreferredSize(new Dimension(110, 1));
        jPanel1.setPreferredSize(new Dimension(1, 12));
        jPanel2.setPreferredSize(new Dimension(1, 12));
        jScrollPane1.setBorder(border1);
        jPanel3.add(bCloneSeries);
        jPanel3.add(bChangeSeries);
        jPanel3.add(bAddSeries);
        jPanel3.add(bDeleteSeries);
        jPanel3.add(bTitleSeries);
        jPanel3.add(bUp);
        jPanel3.add(bDown);
        this.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        jScrollPane1.getViewport().add(lbSeries);
        this.add(jPanel3, java.awt.BorderLayout.EAST);
        this.add(jPanel2, java.awt.BorderLayout.SOUTH);
        this.add(jPanel1, java.awt.BorderLayout.NORTH);
        this.add(jPanel4, java.awt.BorderLayout.WEST);
    }

    static void setUpDownIcons(JButton bUp, JButton bDown) {
        bUp.setIcon(new ImageIcon(SeriesEditor.class.getResource(Language.getString("images_Up_gif"))));
        bDown.setIcon(new ImageIcon(SeriesEditor.class.getResource(Language.getString("images_Down_gif"))));
    }

    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    ChartListBox lbSeries = new ChartListBox();
    JScrollPane jScrollPane1 = new JScrollPane();
    JButton bUp = new JButton();
    JButton bDown = new JButton();
    JButton bTitleSeries = new JButton();
    JButton bDeleteSeries = new JButton();
    JButton bCloneSeries = new JButton();
    JButton bChangeSeries = new JButton();
    JButton bAddSeries = new JButton();
    JPanel jPanel3 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    Border border1 = BorderFactory.createEmptyBorder();
    JPanel jPanel4 = new JPanel();

    public void jbUp_actionPerformed(ActionEvent e) {
        lbSeries.moveCurrentUp();
    }

    public void jbDown_actionPerformed(ActionEvent e) {
        lbSeries.moveCurrentDown();
    }

    private void enableArrowButtons() {
        bUp.setEnabled(lbSeries.getSelectedIndex() > 0);
        bDown.setEnabled(lbSeries.getSelectedIndex() < lbSeries.getCount() - 1);
    }

    private void enableButtons() {
        boolean anyExist = lbSeries.getSelectedIndex() != -1;
        bDeleteSeries.setEnabled(anyExist);
        bTitleSeries.setEnabled(anyExist);
        bCloneSeries.setEnabled(anyExist);
        bChangeSeries.setEnabled(anyExist);
        enableArrowButtons();
    }

    private void addSeries() {
        lbSeries.fillSeries();
        if (lbSeries.getCount() > 0) {
            lbSeries.setSelectedIndex(0);
        }
        enableButtons();
    }

    private void addToContainer(Series s) {
        // IContainer c=chart.ChartContainer;
        // if (c!=null) s.AddToContainer(c);
    }

    private Series addFromGallery() throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        Dialog tmp= (getParent() instanceof Dialog) ? (Dialog)getParent() : null;

        Series s = ChartGallery.createNew(tmp, chart);

        if (s != null) {
            if(s instanceof com.steema.teechart.styles.World) {
                //System.out.print("getMap " + ((com.steema.teechart.styles.World)s).getMap().getValue() + "\n");
               ((com.steema.teechart.styles.World)s).setInGallery(false); 
            }
            addToContainer(s);
            s.setChart(chart);
            addSeries();
            lbSeries.setSelectedSeries(s);

            if (listener!=null) {
                listener.seriesAdded(s);
            }
        }
        return s;
    }

    public void jButton7_actionPerformed(ActionEvent e) throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        Series tmpSeries = addFromGallery();
        if ((tmpSeries != null) && (tmpSeries.getFunction() != null)) {
        }
    }

    public void bTitleSeries_actionPerformed(ActionEvent e) {
        Series series = lbSeries.getSeries();
        TextInputString tmp = new TextInputString(series.toString());

        if (TextInput.query(Language.getString("ChangeSeriesTitle"), Language.getString("NewSeriesTitle"), tmp)) {
            series.setTitle(tmp.text);

        lbSeries.repaint();
        }

    }

    public void bDeleteSeries_actionPerformed(ActionEvent e) {
        Series old=lbSeries.getSeries();

        if (lbSeries.deleteSeries()) {
            if (listener!=null) {
                listener.seriesRemoved(old);
            }

            enableButtons();
        }
    }

    public void lbSeries_valueChanged(ListSelectionEvent e) {
        enableButtons();
    }

    public void bCloneSeries_actionPerformed(ActionEvent e) {
        Series s=lbSeries.getSeries();
        Series tmp=s.cloneSeries();
        s.getChart().addSeries(tmp);
        lbSeries.fillSeries();
        lbSeries.setSelectedSeries(tmp);
    }

    public void bChangeSeries_actionPerformed(ActionEvent e) throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        Series old=lbSeries.getSeries();
        lbSeries.changeTypeSeries(getParent());
        
        if (listener!=null) {
            listener.seriesRemoved(old);
            listener.seriesAdded(lbSeries.getSeries());
        }
    }
}


final class SeriesEditor_bDeleteSeries_actionAdapter implements ActionListener {
    private SeriesEditor adaptee;
    SeriesEditor_bDeleteSeries_actionAdapter(SeriesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bDeleteSeries_actionPerformed(e);
    }
}


final class SeriesEditor_bTitleSeries_actionAdapter implements ActionListener {
    private SeriesEditor adaptee;
    SeriesEditor_bTitleSeries_actionAdapter(SeriesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bTitleSeries_actionPerformed(e);
    }
}


final class SeriesEditor_jButton7_actionAdapter implements ActionListener {
    private SeriesEditor adaptee;
    SeriesEditor_jButton7_actionAdapter(SeriesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        try {
            adaptee.jButton7_actionPerformed(e);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(SeriesEditor_jButton7_actionAdapter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(SeriesEditor_jButton7_actionAdapter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(SeriesEditor_jButton7_actionAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


final class SeriesEditor_bCloneSeries_actionAdapter implements ActionListener {
    private SeriesEditor adaptee;
    SeriesEditor_bCloneSeries_actionAdapter(SeriesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.bCloneSeries_actionPerformed(e);
    }
}


class SeriesEditor_bChangeSeries_actionAdapter implements ActionListener {
    private SeriesEditor adaptee;
    SeriesEditor_bChangeSeries_actionAdapter(SeriesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        try {
            adaptee.bChangeSeries_actionPerformed(e);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(SeriesEditor_bChangeSeries_actionAdapter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(SeriesEditor_bChangeSeries_actionAdapter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(SeriesEditor_bChangeSeries_actionAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


final class SeriesEditor_jbDown_actionAdapter implements ActionListener {
    private SeriesEditor adaptee;
    SeriesEditor_jbDown_actionAdapter(SeriesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jbDown_actionPerformed(e);
    }
}


final class SeriesEditor_jbUp_actionAdapter implements ActionListener {
    private SeriesEditor adaptee;
    SeriesEditor_jbUp_actionAdapter(SeriesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jbUp_actionPerformed(e);
    }
}


final class SeriesEditor_lbSeries_listSelectionAdapter implements
        ListSelectionListener {
    private SeriesEditor adaptee;
    SeriesEditor_lbSeries_listSelectionAdapter(SeriesEditor adaptee) {
        this.adaptee = adaptee;
    }

    public void valueChanged(ListSelectionEvent e) {
        adaptee.lbSeries_valueChanged(e);
    }
}

