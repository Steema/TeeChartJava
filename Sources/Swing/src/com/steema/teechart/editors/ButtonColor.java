package com.steema.teechart.editors;

import javax.swing.JButton;
import com.steema.teechart.languages.Language;

import java.awt.Graphics;
import javax.swing.DefaultButtonModel;
import java.awt.event.ActionEvent;
import com.steema.teechart.drawing.Color;

/**
 *
 * <p>Title: ButtonColor class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ButtonColor extends JButton {

    private Color color;

    public ButtonColor() {
        this(Language.getString("Color"), Color.BLACK);
    }

    public ButtonColor(String text, Color value) {
        super();
        color = value;
        setText(text);
        setModel(new ButtonModel(this));
    }

    public ButtonColor(Color value) {
        this(Language.getString("Color"), value);
    }

    public Color getColor() {
        return color;
    }

    public java.awt.Color getAWTColor() {
        return color;
    }

    public void setColor(Color value) {
        color = value;
        invalidate();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(color);
        int tmp = getHeight() / 4;
        g.fillRect(5, tmp, 10, 2 * tmp);
    }

    public Color chooseColor() {
        Color tmp = ColorEditor.choose(this, color);
        if (tmp != null) {
            if (color != tmp) {
                setColor(tmp);
            }
        }
        return (tmp == null) ? color : tmp;
    }
}

final class ColorChanged extends ActionEvent {

    public ButtonColor button;

    protected ColorChanged(ButtonColor value) {
        super(value, 0, "");
        button = value;
    }
}

final class ButtonModel extends DefaultButtonModel {

    private ButtonColor button;

    public ButtonModel(ButtonColor button) {
        super();
        this.button = button;
    }

    public void setPressed(boolean b) {
        if ((isPressed() == b) || !isEnabled()) {
            return;
        }

        button.chooseColor();
        super.fireActionPerformed(new ColorChanged(button));
        //super.setPressed(b); //TJ71012734
    }
}

