package android.teechart.steema.com.androiddemo;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.editors.Utils;
import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.Series;

/**
 * Created by abde on 28/01/16.
 */
public class DataDialog extends Dialog {

    private Series series;
    private final SeriesDataView seriesView;
    private Context context;

    public DataDialog(final Context context, final Series series) {

        super(context);

        this.context = context;
        this.series = series;

        final LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        final LinearLayout layout2 = new LinearLayout(context);
        layout2.setOrientation(LinearLayout.HORIZONTAL);

        final Button add = new Button(context);
        add.setText(Language.getString("Add"));
        add.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                addPoint();
            }
        });
        layout2.addView(add);

        final Button remove = new Button(context);
        remove.setText(Language.getString("Remove"));
        remove.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                removePoint();
            }
        });
        layout2.addView(remove);
        final Button btnOk = new Button(context);
        btnOk.setText("OK");
        /*btnOk.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1f));*/
        btnOk.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dismiss();
            }
        });
        layout2.addView(btnOk);
        layout.addView(layout2);

        final ScrollView scroll = new ScrollView(context);

        scroll.setHorizontalScrollBarEnabled(true);

        seriesView = new SeriesDataView(context, series);

        scroll.addView(seriesView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f));
        scroll.setSmoothScrollingEnabled(true);
        layout.addView(scroll);
        setContentView(layout);
        //setView(layout);

        setTitle(Language.getString("Series Data"));
    }

    private void removePoint() {
        series.delete(0);
        refreshLayout();
    }

    private void refreshLayout() {
        seriesView.removeAllViews();
        seriesView.addSeriesRows(context);
        seriesView.requestLayout();
    }

    private void addPoint() {
        series.add(0);
        refreshLayout();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        dismiss();
        super.onRestoreInstanceState(savedInstanceState);
    }
}
