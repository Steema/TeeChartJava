package android.teechart.steema.com.androiddemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by steema on 21/12/2015.
 */
public class TabDashboard extends Fragment {

    ListView lstDashboards;
    TextView textView;
    ArrayList<String> dashboardsList;
    protected static DashboardAdapter dashboardAdapter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (Main.searchView != null) {
            Main.searchView.setQuery("", false);
            Main.searchView.clearFocus();
        }
        View v = inflater.inflate(R.layout.tab_dashboard, container, false);
        lstDashboards = (ListView) v.findViewById(R.id.lstDashboards);
        textView = new TextView(getContext());

        dashboardsList = new ArrayList<String>();
        dashboardsList.add("Scroll Pager");
        dashboardsList.add("Web Analytics");
        dashboardsList.add("Server Status");
        dashboardsList.add("Real Time");
        dashboardAdapter = new DashboardAdapter(getContext(), dashboardsList);
        lstDashboards.setAdapter(dashboardAdapter);
        lstDashboards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch ((String) parent.getItemAtPosition(position)) {
                    case "Scroll Pager":
                        startActivity(new Intent(getContext(), DashboardScrollPager.class));
                        break;
                    case "Web Analytics":
                        startActivity(new Intent(getContext(), DashboardWebAnalytics.class));
                        break;
                    case "Server Status":
                        startActivity(new Intent(getContext(), DashboardServerStatus.class));
                        break;
                    case "Real Time":
                        startActivity(new Intent(getContext(), DashboardRealTime.class));
                        break;
                }
            }
        });
        return v;
    }

    public class DashboardAdapter extends BaseAdapter implements Filterable {
        Context context;
        ItemFilter filter = new ItemFilter();
        ArrayList<String> dades;
        private ArrayList<String> tempDeviceList;

        public DashboardAdapter(Context context, ArrayList<String> dades) {
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

            if (getItem(position).toString() == "Scroll Pager")
                ivImatge.setImageResource(R.drawable.ic_call_to_action_black_48dp);
            else if (getItem(position).toString() == "Web Analytics")
                ivImatge.setImageResource(R.drawable.ic_web_black_48dp);
            else if (getItem(position).toString() == "Server Status")
                ivImatge.setImageResource(R.drawable.ic_info_outline_black_48dp);
            else if (getItem(position).toString() == "Real Time")
                ivImatge.setImageResource(R.drawable.fastline);

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
