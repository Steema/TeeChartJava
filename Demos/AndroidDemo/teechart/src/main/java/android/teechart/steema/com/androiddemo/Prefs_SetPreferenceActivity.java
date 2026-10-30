package android.teechart.steema.com.androiddemo;

import android.content.Intent;
import android.os.Bundle;
import android.preference.SwitchPreference;
import android.view.MenuItem;

public class Prefs_SetPreferenceActivity extends AppCompatPreferenceActivity {
    private Prefs_FragmentPrefs fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //[LEFT,BOTTOM, TOP, RIGHT, DEPTH, DEPTH TOP]
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putBoolean("view3D", intent.getBooleanExtra("view3D", true));
        bundle.putBooleanArray("visibleArray", intent.getBooleanArrayExtra("visibleArray"));
        bundle.putBooleanArray("invertedArray", intent.getBooleanArrayExtra("invertedArray"));
        bundle.putStringArray("titlesArray", intent.getStringArrayExtra("titlesArray"));
        bundle.putBooleanArray("gridArray", intent.getBooleanArrayExtra("gridArray"));
        bundle.putBoolean("enabledAxes", intent.getBooleanExtra("enabledAxes", true));
        this.getSharedPreferences("Prefs_FragmentPrefs", 0).edit().clear().commit();
        this.fragment = new Prefs_FragmentPrefs();
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                this.fragment).commit();
    }

    protected boolean isValidFragment(String fragmentName) {
        return Prefs_FragmentPrefs.class.getName().equals(fragmentName);
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
