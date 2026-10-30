package android.teechart.steema.com.androiddemo;

import android.app.Dialog;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.editors.Utils;
import com.steema.teechart.languages.Language;
import com.steema.teechart.styles.Series;

public class SeriesDataDialog extends Dialog {

	public interface OnImportData {
		public void execute();
	}
	
	private Series series;
	private final SeriesDataView seriesView;
	private Context context;
	private OnImportData onImportData;
	
	public SeriesDataDialog(final Context context, final Series series,
							OnImportData onImportData) {
		
		super(context);

		this.onImportData = onImportData;
		this.context = context;
		this.series = series;
		
		final LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.VERTICAL);
		
		final LinearLayout layout2 = new LinearLayout(context);
		layout2.setOrientation(LinearLayout.HORIZONTAL);

		final ButtonColor color = new ButtonColor(context, series.getColor(),
				new ButtonColor.OnColorChangeListener() {

					public void onColorChanged(Color newColor) {

						series.setColor(newColor);
					}
				});

		layout2.addView(color);

		final Button add = new Button(context);
		add.setText(Language.getString("Add"));
		add.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				addPoint();
			}
		});
		layout2.addView(add);
		
		final Button remove = new Button(context);
		remove.setWidth(200);
		remove.setText(Language.getString("Remove"));
		remove.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				removePoint();
			}
		});
		layout2.addView(remove);

		layout.addView(layout2);
		
		final ScrollView scroll = new ScrollView(context);

		scroll.setHorizontalScrollBarEnabled(true);

		seriesView = new SeriesDataView(context, series);

		scroll.addView(seriesView, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));

		layout.addView(scroll);
		
		setContentView(layout);

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
}