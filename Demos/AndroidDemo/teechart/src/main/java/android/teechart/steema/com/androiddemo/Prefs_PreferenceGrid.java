package android.teechart.steema.com.androiddemo;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by abde on 19/01/16.
 */
public class Prefs_PreferenceGrid extends PreferenceFragment {
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.pref_grid);
    }
}
