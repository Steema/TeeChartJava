package com.steema.teechart.editors;

import javax.swing.JButton;
import com.steema.teechart.languages.Language;
import javax.swing.DefaultButtonModel;
import java.awt.Graphics;
import com.steema.teechart.drawing.ChartPen;
import com.steema.teechart.drawing.Graphics3DAWT;
import java.awt.event.ActionEvent;

/**
 *
 * <p>Title: ButtonPen class</p>
 *
 * <p>Description: Specialized JButton to automatically show the ChartPen
 * dialog.</p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights
 * Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
public class ButtonPen extends JButton {

    private ChartPen pen;

    /**
     * Creates a new ButtonPen with default text.
     */
    public ButtonPen() {
        this(Language.getString("EditPen"));
    }

    /**
     * Creates a new ButtonPen with text specified by "caption" parameter.
     *
     * @param caption String
     */
    public ButtonPen(String caption) {
        super();
        setText(caption);
        setModel(new ButtonPenModel(this));
    }

    /**
     * Creates a new ButtonPen and sets the ChartPen "value" parameter.
     *
     * @param value ChartPen to be edited
     */
    public ButtonPen(ChartPen value) {
        this();
        pen = value;
    }

    /**
     * Creates a new ButtonPen and sets the "caption" text and ChartPen "value"
     * parameter.
     *
     * @param value ChartPen
     * @param caption String
     */
    public ButtonPen(ChartPen value, String caption) {
        this(caption);
        pen = value;
    }

    /**
     * Returns the ChartPen being edited.
     *
     * @return ChartPen
     */
    public ChartPen getPen() {
        return pen;
    }

    /**
     * Sets the ChartPen to be edited.
     *
     * @param value ChartPen
     */
    public void setPen(ChartPen value) {
        pen = value;
        invalidate();
    }

    /**
     * Paints a small line at button's left using the ChartPen.
     *
     * @param g Graphics
     */
    public void paint(Graphics g) {
        super.paint(g);

        if (pen != null) {
            Graphics3DAWT.penPrepare(pen, g);
            int tmp = getHeight() / 2;
            g.drawLine(5, tmp, 10, tmp);
        }
    }

    /**
     * Shows the ChartPen editor dialog.
     *
     * @return boolean True if Pen properties have been changed.
     */
    public boolean editPen() {
        return ChartPenEditor.showDialog(null, pen);
    }
}

final class PenChanged extends ActionEvent {

    public ButtonPen button;

    protected PenChanged(ButtonPen value) {
        super(value, 0, "");
        button = value;
    }
}

final class ButtonPenModel extends DefaultButtonModel {

    private ButtonPen button;

    public ButtonPenModel(ButtonPen button) {
        super();
        this.button = button;
    }

    public void setPressed(boolean b) {
        if ((isPressed() == b) || !isEnabled()) {
            return;
        }
        button.editPen();
        super.fireActionPerformed(new PenChanged(button));
        //super.setPressed(b); //TJ71012734
    }
}

