package android.teechart.steema.com.androiddemo;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by steema on 21/12/2015.
 * Android stats tab
 */
public class TabAndroidStats extends Fragment {
    ListView lvAndroidStats;
    ArrayList<String> options = new ArrayList<String>();
    protected static StatsAndroidAdapter statsAndroidAdapter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (Main.searchView != null) {
            Main.searchView.setQuery("", false);
            Main.searchView.clearFocus();
        }
        options.add("Volume");
        options.add("Battery");
        options.add("WiFi");
        options.add("Images");
        View v = inflater.inflate(R.layout.tab_androidstats, container, false);
        lvAndroidStats = (ListView) v.findViewById(R.id.lvAndroidStats);
        statsAndroidAdapter = new StatsAndroidAdapter(getContext(), options);
        lvAndroidStats.setAdapter(statsAndroidAdapter);
        lvAndroidStats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    case 0:
                        startActivity(new Intent(getContext(), Stats_Volume.class));
                        break;
                    case 1:
                        startActivity(new Intent(getContext(), Stats_Battery.class));
                        break;
                    case 2:
                        startActivity(new Intent(getContext(), Stats_WiFi.class));
                        break;
                    case 3:
                        startActivity(new Intent(getContext(), Stats_ImageStatistics.class));
                        break;

                }
            }
        });
        return v;
    }

    public class StatsAndroidAdapter extends BaseAdapter implements Filterable {
        Context context;
        ArrayList<String> dades;
        ArrayList<String> tempDeviceList;
        ItemFilter filter = new ItemFilter();

        public StatsAndroidAdapter(Context context, ArrayList<String> dades) {
            this.context = context;
            this.dades = dades;
            tempDeviceList = dades;
        }

        @Override
        public int getCount() {
            return tempDeviceList.size();
        }

        @Override
        public Object getItem(int position) {
            return tempDeviceList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflador = getLayoutInflater(null);

            View v = inflador.inflate(R.layout.stats_adapter, null);

            ImageView ivImatge = (ImageView) v.findViewById(R.id.ivIcon);
            TextView tvName = (TextView) v.findViewById(R.id.tvText);

            if (getItem(position).toString() == "Volume")
                ivImatge.setImageResource(R.drawable.ic_volume_up_black_48dp);
            else if (getItem(position).toString() == "Battery")
                ivImatge.setImageResource(R.drawable.ic_battery_full_black_48dp);
            else if (getItem(position).toString() == "WiFi")
                ivImatge.setImageResource(R.drawable.ic_network_wifi_black_48dp);
            else if (getItem(position).toString() == "Images")
                ivImatge.setImageResource(R.drawable.ic_photo_size_select_actual_black_48dp);
            tvName.setText(getItem(position).toString());

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
                    Result.values = dades;
                    Result.count = dades.size();
                    return Result;
                }

                ArrayList<String> Filtered_Names = new ArrayList<String>();
                String filterString = constraint.toString().toLowerCase();
                String filterableString;

                for (int i = 0; i < dades.size(); i++) {
                    filterableString = dades.get(i);
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

}
