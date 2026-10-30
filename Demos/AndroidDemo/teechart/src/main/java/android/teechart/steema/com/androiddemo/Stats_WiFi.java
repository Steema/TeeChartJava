package android.teechart.steema.com.androiddemo;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.steema.teechart.TChart;
import com.steema.teechart.drawing.Color;
import com.steema.teechart.legend.LegendAlignment;
import com.steema.teechart.styles.Bar;
import com.steema.teechart.styles.ValueListOrder;
import com.steema.teechart.themes.ThemesList;

import java.util.List;

public class Stats_WiFi extends AppCompatActivity implements View.OnClickListener {
    WifiManager wifi;
    public TChart chart;
    Button scanButton;
    private Bar availableNetworks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        while (wifi.getWifiState() == wifi.WIFI_STATE_ENABLING) {

        }
        if (wifi.isWifiEnabled()) {
        setContentView(R.layout.stats_wifi);
            scanButton = (Button) findViewById(R.id.btnScan);
            scanButton.setOnClickListener(this);
            LinearLayout group = (LinearLayout) findViewById(R.id.linearLayoutTchart);
            chart = new TChart(this);
            group.addView(chart);

            chart.getPanel().setBorderRound(7);
            chart.getAspect().setView3D(false);
            ThemesList.applyTheme(chart.getChart(), 11);
            chart.getLegend().setAlignment(LegendAlignment.BOTTOM);
            chart.getZoom().setAllow(false);
            chart.getAxes().getAxis(0).setMaximum(100);
            chart.getAxes().getAxis(0).setAutomaticMaximum(false);
            chart.getLegend().getTitle().setVisible(true);
            chart.getLegend().getTitle().setText("Intensity level in %");

            // Setup WiFi
            wifi.startScan();
            List<ScanResult> results = wifi.getScanResults();
            String[] networksName = new String[10];
            int[] networksSignal = new int[10];
            for (int i = 0; i < results.size() && i < 10; i++) {
                networksName[i] = results.get(i).SSID;
                if (WifiManager.calculateSignalLevel(results.get(i).level, 10) == 0) {
                    networksSignal[i] = 1;
                } else {
                    networksSignal[i] = WifiManager.calculateSignalLevel(
                            results.get(i).level, 10);

                }
            }
            availableNetworks = new Bar(chart.getChart());
            availableNetworks.getYValues().setOrder(ValueListOrder.DESCENDING);
            for (int i = 0; i < results.size() && i < 10; i++) {
                double value = networksSignal[i] * 10;
                availableNetworks.add(networksSignal[i] * 10, networksName[i],
                        setColor(value));
            }

            availableNetworks.getYValues().sort();
            availableNetworks.getXValues().fillSequence();
        } else {
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    Stats_WiFi.this);

// Setting Dialog Title
            alertDialog2.setTitle("WiFi");

// Setting Dialog Message
            alertDialog2.setMessage("Do you want to enable WiFi?");


// Setting Positive "Yes" Btn
            alertDialog2.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog
                            wifi.setWifiEnabled(true);
                            Intent myIntent = new Intent(Stats_WiFi.this,
                                    Stats_WiFi.class);
                            startActivityForResult(myIntent, 0);
                            finish();
                        }
                    });

// Setting Negative "NO" Btn
            alertDialog2.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Stats_WiFi.this.finish();
                        }
                    });

// Showing Alert Dialog
            alertDialog2.show();

        }


    }

    public void onClick(View v) {

        if (wifi.isWifiEnabled()) {
            wifi.startScan();

            List<ScanResult> results = wifi.getScanResults();
            String[] networksName = new String[10];
            int[] networksSignal = new int[10];
            for (int i = 0; i < results.size() && i < 10; i++) {
                networksName[i] = results.get(i).SSID;
                if (WifiManager.calculateSignalLevel(results.get(i).level, 10) == 0) {
                    networksSignal[i] = 1;
                } else {
                    networksSignal[i] = WifiManager.calculateSignalLevel(
                            results.get(i).level, 10);

                }
            }
            chart.removeAllSeries();
            availableNetworks = new Bar(chart.getChart());
            availableNetworks.getYValues().setOrder(ValueListOrder.DESCENDING);
            for (int i = 0; i < results.size() && i < 10; i++) {
                double value = networksSignal[i] * 10;
                availableNetworks.add(value, networksName[i], setColor(value));
            }

            availableNetworks.getYValues().sort();
            availableNetworks.getXValues().fillSequence();
            chart.getLegend().setAlignment(LegendAlignment.BOTTOM);
        }
    }

    public Color setColor(double value) {
        if (value >= 0 && value <= 19) {
            return Color.fromArgb(255, 0, 0);
        } else if (value >= 20 && value <= 39) {
            return Color.fromArgb(255, 64, 0);
        } else if (value >= 40 && value <= 59) {
            return Color.fromArgb(255, 128, 0);
        } else if (value >= 60 && value <= 79) {
            return Color.fromArgb(255, 255, 0);
        } else if (value >= 80 && value <= 100) {
            return Color.fromArgb(0, 255, 0);
        } else
            return Color.fromArgb(0, 255, 0);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}