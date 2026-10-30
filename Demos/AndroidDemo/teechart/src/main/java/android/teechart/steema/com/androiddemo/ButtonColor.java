package android.teechart.steema.com.androiddemo;

import android.content.Context;
import android.widget.Button;

import com.steema.teechart.drawing.Color;
import com.steema.teechart.languages.Language;

public class ButtonColor extends Button {

    public interface OnColorChangeListener {
        public void onColorChanged(final Color newColor);
    }

    public ButtonColor(final Context context, final Color color,
                       final OnColorChangeListener onColorChangeListener) {

        super(context);
        setBackgroundColor(color.getRGB());
        setText(Language.getString("Color"));

        final int c = color.getRGB();

        if (c == -16777216) // black
            setTextColor(Color.white.getRGB());
        else {
            float[] hsv = new float[3];
            Color.colorToHSV(color.getRGB(), hsv);

            if (hsv[2] > 200)
                setTextColor(Color.white.getRGB());
        }
    }

}
