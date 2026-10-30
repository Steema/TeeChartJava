package android.teechart.steema.com.androiddemo;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.steema.teechart.TChart;

public class Prefs_FragmentPrefs extends PreferenceFragment {

    private ListPreference lsAxes;
    private SwitchPreference swVisibleAxes, swInvertedAxes, swGrid;
    private EditTextPreference EtPref;
    private PreferenceCategory pcAxes, pcAspect, pcMarks;
    private PreferenceScreen prefScreen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //	boolean[] aspect=new ChartView().getAspect(); //aspect[0]==3d view, aspect[1]== orthogonal projection
        //[LEFT,BOTTOM, TOP, RIGHT, DEPTH, DEPTH TOP]
        //final SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences();
        Bundle bundle = this.getArguments();
        final boolean[] arrayVisible = bundle.getBooleanArray("visibleArray");
        final boolean[] arrayInverted = bundle.getBooleanArray("invertedArray");
        final boolean[] arrayGrid = bundle.getBooleanArray("gridArray");
        final String[] arrayTitles = bundle.getStringArray("titlesArray");
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        prefScreen = (PreferenceScreen) findPreference("prefScreen");
        pcAspect = (PreferenceCategory) findPreference("pcAspect");
        pcAxes = (PreferenceCategory) findPreference("pcAxes");
        swVisibleAxes = (SwitchPreference) findPreference("swVisibleAxes");
        pcMarks = (PreferenceCategory) findPreference("pcMarks");
        EtPref = (EditTextPreference) findPreference("EtPref");
        swInvertedAxes = (SwitchPreference) findPreference("swInvertedAxes");
        lsAxes = (ListPreference) findPreference("lsAxes");
        lsAxes.setSummary(lsAxes.getValue());
        swGrid = (SwitchPreference) findPreference("swGrid");
        //region Initializing
        if (!bundle.getBoolean("enabledAxes"))
            prefScreen.removePreference(pcAxes);
        if (!bundle.getBoolean("view3D"))
            prefScreen.removePreference(pcAspect);
        switch (lsAxes.getValue()) {
            case "Left":
                if (arrayVisible != null) {
                    swVisibleAxes.setChecked(arrayVisible[0]);
                }
                if (arrayInverted != null) {
                    swInvertedAxes.setChecked(arrayInverted[0]);
                }
                if (arrayTitles != null) {
                    EtPref.setText(arrayTitles[0]);
                }
                if (arrayGrid != null)
                    swGrid.setChecked(arrayGrid[0]);
                break;
            case "Bottom":
                if (arrayVisible != null) {
                    swVisibleAxes.setChecked(arrayVisible[1]);
                }
                if (arrayInverted != null) {
                    swInvertedAxes.setChecked(arrayInverted[1]);
                }
                if (arrayTitles != null) {
                    EtPref.setText(arrayTitles[1]);
                }
                if (arrayGrid != null)
                    swGrid.setChecked(arrayGrid[1]);
                break;
            case "Top":
                if (arrayVisible != null) {
                    swVisibleAxes.setChecked(arrayVisible[2]);
                }
                if (arrayInverted != null) {
                    swInvertedAxes.setChecked(arrayInverted[2]);
                }
                if (arrayTitles != null) {
                    EtPref.setText(arrayTitles[2]);
                }
                if (arrayGrid != null)
                    swGrid.setChecked(arrayGrid[2]);
                break;
            case "Right":
                if (arrayVisible != null) {
                    swVisibleAxes.setChecked(arrayVisible[3]);
                }
                if (arrayInverted != null) {
                    swInvertedAxes.setChecked(arrayInverted[3]);
                }
                if (arrayTitles != null) {
                    EtPref.setText(arrayTitles[3]);
                }
                if (arrayGrid != null)
                    swGrid.setChecked(arrayGrid[3]);
                break;
            case "Depth":
                if (arrayVisible != null) {
                    swVisibleAxes.setChecked(arrayVisible[4]);
                }
                if (arrayInverted != null) {
                    swInvertedAxes.setChecked(arrayInverted[4]);
                }
                if (arrayTitles != null) {
                    EtPref.setText(arrayTitles[4]);
                }
                if (arrayGrid != null)
                    swGrid.setChecked(arrayGrid[4]);
                break;
            case "Depth Top":
                if (arrayVisible != null) {
                    swVisibleAxes.setChecked(arrayVisible[5]);
                }
                if (arrayInverted != null) {
                    swInvertedAxes.setChecked(arrayInverted[5]);
                }
                if (arrayTitles != null) {
                    EtPref.setText(arrayTitles[5]);
                }
                if (arrayGrid != null)
                    swGrid.setChecked(arrayGrid[5]);
                break;
        }

        //endregion
        lsAxes.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                lsAxes.setValue((String) newValue);
                lsAxes.setSummary(lsAxes.getValue());
                //region switch
                switch ((String) newValue) {
                    case "Left":
                        if (arrayVisible != null) {
                            swVisibleAxes.setChecked(arrayVisible[0]);
                        }
                        if (arrayInverted != null) {
                            swInvertedAxes.setChecked(arrayInverted[0]);
                        }
                        if (arrayTitles != null) {
                            EtPref.setText(arrayTitles[0]);
                        }
                        if (arrayGrid != null)
                            swGrid.setChecked(arrayGrid[0]);
                        break;
                    case "Bottom":
                        if (arrayVisible != null) {
                            swVisibleAxes.setChecked(arrayVisible[1]);
                        }
                        if (arrayInverted != null) {
                            swInvertedAxes.setChecked(arrayInverted[1]);
                        }
                        if (arrayTitles != null) {
                            EtPref.setText(arrayTitles[1]);
                        }
                        if (arrayGrid != null)
                            swGrid.setChecked(arrayGrid[1]);
                        break;
                    case "Top":
                        if (arrayVisible != null) {
                            swVisibleAxes.setChecked(arrayVisible[2]);
                        }
                        if (arrayInverted != null) {
                            swInvertedAxes.setChecked(arrayInverted[2]);
                        }
                        if (arrayTitles != null) {
                            EtPref.setText(arrayTitles[2]);
                        }
                        if (arrayGrid != null)
                            swGrid.setChecked(arrayGrid[2]);
                        break;
                    case "Right":
                        if (arrayVisible != null) {
                            swVisibleAxes.setChecked(arrayVisible[3]);
                        }
                        if (arrayInverted != null) {
                            swInvertedAxes.setChecked(arrayInverted[3]);
                        }
                        if (arrayTitles != null) {
                            EtPref.setText(arrayTitles[3]);
                        }
                        if (arrayGrid != null)
                            swGrid.setChecked(arrayGrid[3]);
                        break;
                    case "Depth":
                        if (arrayVisible != null) {
                            swVisibleAxes.setChecked(arrayVisible[4]);
                        }
                        if (arrayInverted != null) {
                            swInvertedAxes.setChecked(arrayInverted[4]);
                        }
                        if (arrayTitles != null) {
                            EtPref.setText(arrayTitles[4]);
                        }
                        if (arrayGrid != null)
                            swGrid.setChecked(arrayGrid[4]);
                        break;
                    case "Depth Top":
                        if (arrayVisible != null) {
                            swVisibleAxes.setChecked(arrayVisible[5]);
                        }
                        if (arrayInverted != null) {
                            swInvertedAxes.setChecked(arrayInverted[5]);
                        }
                        if (arrayTitles != null) {
                            EtPref.setText(arrayTitles[5]);
                        }
                        if (arrayGrid != null)
                            swGrid.setChecked(arrayGrid[5]);
                        break;
                }
                //mySharedPreferences.edit().apply();
                //endregion
                return false;
            }
        });
    }

    private void openPreferenceScreen(final String screenName) {
        final Preference pref = findPreference(screenName);
        if (pref instanceof PreferenceScreen) {
            final PreferenceScreen preferenceScreen = (PreferenceScreen) pref;
            (getActivity()).setTitle(preferenceScreen.getTitle());
            setPreferenceScreen((PreferenceScreen) pref);
        }
    }
}
