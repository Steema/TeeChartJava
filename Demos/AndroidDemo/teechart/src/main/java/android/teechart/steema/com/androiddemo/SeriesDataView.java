package android.teechart.steema.com.androiddemo;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.steema.teechart.drawing.Color;
import com.steema.teechart.styles.Series;
import com.steema.teechart.styles.ValueList;

import java.text.DecimalFormat;

public class SeriesDataView extends TableLayout {

	private Series series;
	private DecimalFormat valuesFormat;

	public SeriesDataView(final Context context, final Series series) {
		super(context);
		this.series=series;
		this.setLayoutParams (new ViewGroup.MarginLayoutParams
				(0, ViewGroup.LayoutParams.WRAP_CONTENT));
		valuesFormat = new DecimalFormat(series.getValueFormat());
		setStretchAllColumns(true);

		addSeriesRows(context);
	}

	protected void addSeriesRows(final Context context) {
		
		boolean hasX = series.hasNoMandatoryValues();
		@SuppressWarnings("unused")
		boolean hasLabels = ! series.getLabels().isEmpty();

		for (int t = 0; t < Math.min(series.getCount(), 100); t++) {
			
			TableRow row = new TableRow(context);
			final TextView label = new EditText(context);
			TextView x = null;
			label.setMinWidth(200);
			label.setGravity(Gravity.CENTER);
			//if (hasX)
			//	x = createValueEdit(context, t, series.getXValues());

			final TextView y = createValueEdit(context, t, series.getYValues());
			y.setMinWidth(200);
			y.setGravity(Gravity.CENTER);
			final Color c = series.getValueColor(t);

			final Button color = new Button(context);
			color.setBackgroundColor(c.getRGB());
			color.setText("    ");
			color.setTag(t);

			color.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					int initialColor = android.graphics.Color.WHITE;
					final ColorPickerDialog c =  new ColorPickerDialog(getContext(), initialColor, new ColorPickerDialog.OnColorSelectedListener() {

						@Override
						public void onColorSelected(int c) {
							series.getColors().set((Integer) color.getTag(), new Color(c));
							color.setBackgroundColor(c);
							series.repaint();						}

					});
					c.show();
				}

			});
			
			label.setText(series.getLabels().getString(t));
			label.setTag(t);
			
			label.addTextChangedListener(new TextWatcher() {

				public void afterTextChanged(Editable arg0) {
					series.getLabels().setString((Integer) label.getTag(), label.getText().toString());
					series.repaint();
				}

				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
				}

				public void onTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
				}
			
			});
			
			row.addView(label);
			
		//	if (hasX)
		//		row.addView(x);
			
			row.addView(y);
			row.addView(color);
			
			addView(row,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		}
	}

	private TextView createValueEdit(final Context context, int index, final ValueList values) {
		final TextView edit = new EditText(context);
		edit.setTag(index);
		
		if (values.getDateTime())
			edit.setInputType(InputType.TYPE_CLASS_DATETIME);
		else
			edit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
		
		edit.setText(valuesFormat.format(values.getValue(index)));
		
		edit.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable arg0) {
				
				final String s = edit.getText().toString();
				
				if (s.length() > 0) {
					try {
						values.setValue(((Integer)edit.getTag()), 
							  Double.parseDouble(s));

						series.repaint();
					} catch (NumberFormatException e) {
						Toast.makeText(context, "Not a valid number.", 
								Toast.LENGTH_SHORT).show();
					}
				}
			}

			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			public void onTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}
		});
		
		return edit;
	}
}
	
