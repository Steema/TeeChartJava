package android.teechart.steema.com.androiddemo;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.steema.teechart.misc.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabs;
    private ViewPager viewPager;
    private CharSequence Titles[] = {"Series", "Dashboard", "Android Stats"};
    private int tabSelected = 0;
    protected static SearchView searchView;
    private ViewPagerAdapter adapter;
    private ArrayList<String> m_devices;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT > 20) {
            toolbar.setElevation(0);
        }
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Titles.length);

        // Assigning ViewPager View and setting the adapter
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        // Assiging the Tab Layout View
        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                if (searchView != null) {
                    tabSelected = tabs.getSelectedTabPosition();
                    if (tabSelected == 0)
                        TabSeries.lvAdapter.getFilter().filter("");
                    else if (tabSelected == 1)
                        TabDashboard.dashboardAdapter.getFilter().filter("");
                    else if (tabSelected == 2)
                        if (TabAndroidStats.statsAndroidAdapter != null)
                            TabAndroidStats.statsAndroidAdapter.getFilter().filter("");
                    searchView.clearFocus();
                    searchView.setQuery("", false);
                    searchView.setIconified(true);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        // Associate searchable configuration with the SearchView
        // SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        // searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                tabSelected = tabs.getSelectedTabPosition();
                if (tabSelected == 0)
                    TabSeries.lvAdapter.getFilter().filter(newText);
                else if (tabSelected == 1)
                    TabDashboard.dashboardAdapter.getFilter().filter(newText);
                else if (tabSelected == 2)
                    if (TabAndroidStats.statsAndroidAdapter != null)
                        TabAndroidStats.statsAndroidAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_web) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.steema.com/")));
        } else if (id == R.id.nav_call) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("tel:+34 972 218 797")));
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, About.class));
        } else if (id == R.id.nav_mail) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:info@steema.com")));
        } else if (id == R.id.nav_newChart) {
            listSeries();
        } else if (id == R.id.nav_openChart) {
            openChartFile();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openChartFile() {

        final File filesDir = new File(Environment.getExternalStorageDirectory() + File.separator + "TeeChart" + File.separator + "Files");
        if (filesDir.isDirectory()) {
            final ArrayList<File> files = getListFiles(new File(Environment.getExternalStorageDirectory() + File.separator + "TeeChart" + File.separator + "Files"));
            ArrayList<String> fileNames = new ArrayList<>();

            for (File f : files) {
                fileNames.add(f.getName());
            }

            final ListView lvListSeries;
            lvListSeries = new ListView(this);
            lvListSeries.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileNames));

            lvListSeries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getBaseContext(), ChartView.class);
                    intent.putExtra("chartFile", true);
                    intent.putExtra("chartValue", files.get(position).getAbsolutePath());

                    startActivity(intent);
                }
            });

            AlertDialog.Builder emptyChartsDialog = new AlertDialog.Builder(this);
            emptyChartsDialog.setTitle("Select a file: ").setView(lvListSeries).show();
        }
    }

    private ArrayList<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                if (file.getName().endsWith(".tej")) {
                    inFiles.add(file);
                }
            }
        }

        return inFiles;
    }

    /*
    * List the series to create an empty series
     */
    private void listSeries() {
        final ListView lvListSeries;
        m_devices = new ArrayList<>();

        for (int i = 0; i < Utils.seriesTypesOf.length; i++) {
            String seriesName = Utils.seriesTypesOf[i].getSimpleName();
            if (!(seriesName.equals("Surface") || seriesName.equals("Waterfall") || seriesName.equals("TriSurface") || seriesName.equals("IsoSurface") || seriesName.equals("Tower"))) {
                m_devices.add(seriesName);
            }
        }

        Collections.sort(m_devices);
        lvListSeries = new ListView(this);
        lvListSeries.setAdapter(new CustomAdapter(this, m_devices));
        lvListSeries.setBackgroundColor(android.graphics.Color.WHITE);

        lvListSeries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                Intent myIntent = new Intent(arg1.getContext(),
                        ChartView.class);
                myIntent.putExtra("serieSelected", true);
                myIntent.putExtra("EmptyData", true);
                myIntent.putExtra("seriesSelected", (lvListSeries.getItemAtPosition(arg2)).toString());
                startActivity(myIntent);
            }
        });

        AlertDialog.Builder emptyChartsDialog = new AlertDialog.Builder(this);
        emptyChartsDialog.setTitle("Select a new chart: ").setView(lvListSeries).show();
    }
}
