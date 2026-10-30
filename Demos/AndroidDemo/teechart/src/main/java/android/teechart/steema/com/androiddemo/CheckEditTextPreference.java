package android.teechart.steema.com.androiddemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by abde on 21/01/16.
 */
public class CheckEditTextPreference extends DialogPreference {
    protected static final String chkKey = "ChkKey";
    private EditText editText;
    private CheckBox checkBox;
    private String etValue;
    private boolean chkValue;
    SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

    public CheckEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public CheckEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View onCreateDialogView() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(25, 0, 0, 0);
        //layoutParams.
        LinearLayout llChkEtPreference = new LinearLayout(getContext());
        llChkEtPreference.setOrientation(LinearLayout.VERTICAL);
        llChkEtPreference.setLayoutParams(layoutParams);
        checkBox = new CheckBox(getContext());
        editText = new EditText(getContext());
        editText.setLayoutParams(layoutParams);
        checkBox.setLayoutParams(layoutParams);
        checkBox.setText("Transparent");
        checkBox.setChecked(true);
        FrameLayout dialogView = new FrameLayout(getContext());
        llChkEtPreference.addView(editText);
        llChkEtPreference.addView(checkBox);
        dialogView.addView(llChkEtPreference);
        return dialogView;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        checkBox.setChecked(getValueChk());
        editText.setText(getValueEt());
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            String newValue = editText.getText().toString();
            boolean newValueChk = checkBox.isChecked();
            if (callChangeListener(newValue)) {
                setValueEt(newValue);
            }
            if (callChangeListener(newValueChk)) {
                setValueChk(newValueChk);
            }
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getString(index);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        setValueEt(restorePersistedValue ? getPersistedString("") : defaultValue.toString());
        setValueChk(mySharedPreferences.getBoolean(chkKey, true));
    }

    public void setValueEt(String value) {
        this.etValue = value;
        persistString(this.etValue);
    }

    public String getValueEt() {
        return this.etValue;
    }

    public void setValueChk(boolean value) {
        this.chkValue = value;
        mySharedPreferences.edit().putBoolean(chkKey, this.chkValue).apply();
    }

    public boolean getValueChk() {
        return this.chkValue;
    }

}
