package android.teechart.steema.com.androiddemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.steema.teechart.misc.Utils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by steema on 21/12/2015.
 * Series tab.
 */
public class TabSeries extends Fragment {
    protected static CustomAdapter lvAdapter;
    private static ListView lvSeries;
    private ArrayList<String> seriesArray;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_series, container, false);
        seriesArray = new ArrayList<String>();
        if (Main.searchView != null) {
            Main.searchView.setQuery("", false);
            Main.searchView.clearFocus();
        }
        lvSeries = (ListView) v.findViewById(R.id.lvSeries);
        for (int i = 0; i < Utils.seriesTypesOf.length; i++) {
            seriesArray.add(Utils.seriesTypesOf[i].getSimpleName());
        }
        Collections.sort(seriesArray);
        lvAdapter = new CustomAdapter(getContext(), seriesArray);
        lvSeries.setAdapter(lvAdapter);
        lvSeries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                Intent myIntent = new Intent(arg1.getContext(),
                        ChartView.class);
                myIntent.putExtra("SerieSelected", true);
                myIntent.putExtra("seriesSelected", (lvSeries.getItemAtPosition(arg2)).toString());
                startActivityForResult(myIntent, 1);
            }
        });
        return v;
    }
}

