package android.teechart.steema.com.androiddemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steema on 21/12/2015.
 * Series custom adapter
 */
class CustomAdapterView extends LinearLayout {
    public CustomAdapterView(Context context, String device) {
        super(context);

        // container is a horizontal layer
        setOrientation(LinearLayout.HORIZONTAL);
        setPadding(0, 6, 0, 6);

        // image:params
        LinearLayout.LayoutParams Params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        Params.setMargins(6, 0, 6, 0);
        // image:itself
        ImageView ivLogo = new ImageView(context);
        String logo = device;
        logo = logo.toLowerCase();

        // load image
        int id = getResources()
                .getIdentifier(logo, "drawable", context.getPackageName());
        if (id != 0)
            ivLogo.setImageDrawable(context.getResources().getDrawable(id));
        else
            ivLogo.setImageDrawable(context.getResources().getDrawable(getResources().getIdentifier("pie", "drawable", context.getPackageName())));

        // image:add
        addView(ivLogo, Params);

        // vertical layer for text
        Params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        LinearLayout PanelV = new LinearLayout(context);
        PanelV.setOrientation(LinearLayout.VERTICAL);
        PanelV.setGravity(Gravity.CENTER);
        TextView textName = new TextView(context);
        textName.setGravity(Gravity.CENTER);
        textName.setTextSize(16);
        textName.setTextColor(Color.rgb(0, 0, 0));
        textName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textName.setText(device);
        PanelV.addView(textName);
        addView(PanelV, Params);
    }
}

public class CustomAdapter extends BaseAdapter implements Filterable {

    ItemFilter filter = new ItemFilter();
    private Context context;
    private List<String> deviceList;
    private List<String> tempDeviceList;

    public CustomAdapter(Context context, List<String> deviceList) {
        this.context = context;
        this.deviceList = deviceList;
        tempDeviceList = deviceList;
    }

    public int getCount() {
        return tempDeviceList.size();
    }

    public Object getItem(int position) {
        return tempDeviceList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        String device = tempDeviceList.get(position);
        View v = new CustomAdapterView(this.context, device);
        return v;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub

            FilterResults Result = new FilterResults();
            // if constraint is empty return the original names
            if (constraint.length() == 0) {
                Result.values = deviceList;
                Result.count = deviceList.size();
                return Result;
            }

            ArrayList<String> Filtered_Names = new ArrayList<String>();
            String filterString = constraint.toString().toLowerCase();
            String filterableString;

            for (int i = 0; i < deviceList.size(); i++) {
                filterableString = deviceList.get(i);
                if (filterableString.toLowerCase().contains(filterString)) {
                    Filtered_Names.add(filterableString);
                }
            }
            Result.values = Filtered_Names;
            Result.count = Filtered_Names.size();

            return Result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // TODO Auto-generated method stub
            tempDeviceList = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }
    }
}

