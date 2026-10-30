package com.steema.teechart.editors;

import java.lang.reflect.InvocationTargetException;
import javax.swing.JList;
import com.steema.teechart.languages.Language;
import java.awt.Component;
import javax.swing.JLabel;
import java.awt.Image;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListModel;
import java.util.ArrayList;
import java.awt.Container;

import com.steema.teechart.styles.Series;
import com.steema.teechart.Chart;

import com.steema.teechart.editors.gallery.ChartGallery;
import com.steema.teechart.misc.Utils;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.drawing.Graphics3DAWT;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

/**
 *
 * <p>Title: ChartListBox class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ChartListBox extends JList {

    public boolean askDelete = true;

    protected boolean showSeriesColor;
    protected boolean showSeriesIcon;
    protected boolean showActiveCheck;
    protected boolean showSeriesTitle;

    private Chart chart;

    public ChartListBox() {
        super();
        showSeriesColor = true;
        showSeriesIcon = true;
        showActiveCheck = true;
        showSeriesTitle = true;

        setFixedCellHeight(24);
        setCellRenderer(new Renderer());

        addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int x = e.getX();

                    int checkX = 18;
                    if ((x >= checkX) && (x <= checkX + 18)) {
                        int index = locationToIndex(e.getPoint());
                        Series s = chart.getSeries(index);
                        s.setActive(!s.getActive());
                        updateUI();
                        e.consume();
                    }
                }
            }
        }
        );
    }

    public ChartListBox(Chart chart) {
        this();
        setChart(chart);
    }

    public void setChart(Chart value) {
        chart = value;
        if (chart == null) {
            clear();
        } else {
            setModel(new ChartListModel());
        }
    }

    public boolean getShowActiveCheck() {
        return showActiveCheck;
    }

    public boolean getShowSeriesColor() {
        return showSeriesColor;
    }

    public boolean getShowSeriesIcon() {
        return showSeriesIcon;
    }

    public boolean getShowSeriesTitle() {
        return showSeriesTitle;
    }

    public void setShowActiveCheck(boolean value) {
        if (value != showActiveCheck) {
            showActiveCheck = value;
            fillSeries();
        }
    }

    public void setShowSeriesColor(boolean value) {
        if (value != showSeriesColor) {
            showSeriesColor = value;
            fillSeries();
        }
    }

    public void setShowSeriesIcon(boolean value) {
        if (value != showSeriesIcon) {
            showSeriesIcon = value;
            fillSeries();
        }
    }

    public void setShowSeriesTitle(boolean value) {
        if (value != showSeriesTitle) {
            showSeriesTitle = value;
            fillSeries();
        }
    }

    public void clear() {
    }

    public void moveCurrentUp() {
        Series s = getSeries();
        Chart c = (Chart) s.getChart();
        int tmp = c.getSeries().indexOf(s);

        if (tmp > 0) {
            c.getSeries().exchange(tmp, tmp - 1);
            fillSeries();
            setSelectedIndex(tmp - 1);
        }
    }

    public void moveCurrentDown() {
        Series s = getSeries();
        Chart c = (Chart) s.getChart();
        int tmp = c.getSeries().indexOf(s);

        if (tmp < (c.getSeriesCount() - 1)) {
            c.getSeries().exchange(tmp, tmp + 1);
            fillSeries();
            setSelectedIndex(tmp + 1);
        }
    }

    public void fillSeries() {
        updateUI();
        clearSelection();
    }

    final class ChartListModel extends DefaultListModel {

        public int getSize() {
            return chart.getSeriesCount();
        }

        /**
         * Returns the value at the specified index.
         *
         * @param index the requested index
         * @return the value at <code>index</code>
         */
        public Object getElementAt(int index) {
            return chart.getSeries(index);
        }
    }


    public void changeTypeSeries(Container owner) throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        if (anySelected() && (chart != null)) {

            ArrayList<Series> tmpList = new ArrayList<Series>();

            // get selected list of series
            if (isMultiSelect()) {

                for (int t = 0; t < getModel().getSize(); t++) {
                    if (isSelectedIndex(t)) {
                        tmpList.add(chart.getSeries(t));
                    }
                }
            } else {
                tmpList.add(getSeries());
            }

            boolean firstTime = true;
            Class newClass = null;

            // change all selected...
            for (int t = 0; t < tmpList.size(); t++) {
                Series tmpSeries = (Series) tmpList.get(t);
                if (firstTime) {
                    newClass = ChartGallery.changeSeriesDialog(owner, tmpSeries).getClass();
                    firstTime = false;
                } else {
                    try {
                        com.steema.teechart.styles.Series.changeType(tmpSeries, newClass);
                    }
                    catch(InstantiationException ee) {}
                    catch(IllegalAccessException ee) {}

                }

                tmpSeries.setChart(chart);
                tmpList.set(t, tmpSeries);
            }

            // reset the selected list...
            for (int t = 0; t < tmpList.size(); t++) {
                int tmp = indexOf(tmpList.get(t));
                if (tmp != -1) {
                    selectSeries(tmp);
                }
            }
        }
    }

    private int indexOf(Object object) {
        DefaultListModel l = (DefaultListModel) getModel();
        return l.indexOf(object);
    }

    private void selectSeries(int index) {
        if (isMultiSelect()) {
            addSelectionInterval(index, index);
        } else {
            setSelectedIndex(index);
        }
    }

    private boolean isMultiSelect() {
        return getSelectionMode() != ListSelectionModel.SINGLE_SELECTION;
    }

    private boolean anySelected() {
        return!isSelectionEmpty();
    }

    private void doDelete() {
        if (isMultiSelect()) {
            int num = getModel().getSize();

            int index = 0;

            for (int t = 0; t < num; t++) {
                if (isSelectedIndex(t)) {
                    chart.getSeries(index).dispose();
                } else {
                    index++;
                }
            }
        } else
        if (getSelectedIndex() != -1) {
            getSeries().dispose();
        }

        fillSeries();
    }

    public boolean deleteSeries() {

        boolean result = false;

        if (anySelected()) {
            if (askDelete) {
                String tmpSt;

                if ((!isMultiSelect()) || (getSelectedIndices().length == 1)) {
                    tmpSt = getSeries().titleOrName();
                } else {
                    tmpSt = Language.getString("SelectedSeries");
                }

                if (com.steema.teechart.editors.Utils.yesNoDelete(tmpSt)) {
                    doDelete();
                    result = true;
                }
            } else {
                doDelete();
                result = true;
            }
        }

        return result;
    }

    public int getCount() {
        return chart.getSeriesCount();
    }

    public void setSelectedSeries(Series s) {
        setSelectedValue(s, true);
    }

    public Series getSeries() {
        return chart.getSeries(getSelectedIndex());
    }

    public void setItem(int index, String value) {
        DefaultListModel l = (DefaultListModel) getModel();
        l.set(index, value);
    }

    final private class Renderer extends javax.swing.DefaultListCellRenderer {

        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean selected,
                boolean focus) {

            JLabel jl;

            if (showSeriesTitle) {
                jl = (JLabel)super.getListCellRendererComponent(list, value,
                        index,
                        selected, focus);
            } else {
                jl = new JLabel();
            }
            int w = 0;

            if (showActiveCheck) {
                w += 18;
            }
            ;
            if (showSeriesColor) {
                w += 18;
            }
            ;
            if (showSeriesIcon) {
                w += 22;
            }
            ;

            Image i = list.createImage(w, 21);

            int x = 0;

            if (i != null) {
                Graphics g = i.getGraphics();

                Series series = (Series) value;

                if (showSeriesIcon) {
                    ImageIcon tmp = new ImageIcon(series.getBitmapEditor());
                    if (tmp != null) {
                        g.drawImage(tmp.getImage(), x, 0,
                                    list.getBackground(), null);
                        x += tmp.getIconWidth();
                    }
                }
                /*
                if (showSeriesIcon) {
                    URL urlTemp = series.getBitmapEditor();
                    if (urlTemp != null){
                        ImageIcon tmp = new ImageIcon(urlTemp);
                        if (tmp != null) {
                            g.drawImage(tmp.getImage(), x, 0,
                                        list.getBackground(), null);
                            x += tmp.getIconWidth();
                        }
                    }
                }
                */

                if (showActiveCheck) {
                    Graphics3DAWT a = new Graphics3DAWT();
                    a.setGraphics(g);

                    //TODO: Move this rectangle fill to Utils.drawCheckBox
                    a.getBrush().setColor(list.getBackground());
                    a.getPen().setVisible(false);
                    a.rectangle(x, 0, x + 18, 21);

                    Utils.drawCheckBox(x + 2, 4, a, series.getActive(), true,
                                       new Color(list.getBackground().getRGB()));
                    x += 18;
                }

                if (showSeriesColor) {
                    g.setColor(list.getBackground());
                    g.fillRect(x, 0, 18, 21);
                    g.setColor(((Series) value).getColor());
                    g.fillRect(x + 4, 6, 10, 10);
                    x += 22;
                }

                g.dispose();

                jl.setIcon(new ImageIcon(i));
            }

            return jl;
        }
    }
}

