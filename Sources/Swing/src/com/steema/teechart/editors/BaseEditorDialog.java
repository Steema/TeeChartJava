package com.steema.teechart.editors;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import com.steema.teechart.languages.Language;

/**
 *
 * <p>Title: BaseEditorDialog class</p>
 *
 * <p>Description: Standard Editor Dialog </p>
 *
 * <p>Copyright (c) 2005-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 *
 */
public class BaseEditorDialog extends JDialog {

    /** The button pane. */
    protected Container buttonPane;
    /** The editor pane. */
    protected Container editorPane;
    /** The content pane. */
    protected Container contentPane;

    JButton closeButton = new JButton();

    /**
     * Creates a new instance of BaseEditorDialog
     */
    public BaseEditorDialog() {
        this((JFrame)null);
    }

    /**
     * Creates a new instance of BaseEditorDialog
     *
     * @param editorPane the <code>editorPane</code> object for this dialog
     */
    public BaseEditorDialog(JPanel editorPane) {
        this((JFrame)null, editorPane);
    }

    /**
     * Creates a new instance of BaseEditorDialog
     *
     * @param owner the <code>JFrame</code> from which the dialog is displayed
     * @param editorPane the <code>editorPane</code> object for this dialog
     */
    public BaseEditorDialog(JFrame owner, JPanel editorPane) {
        this(owner);
        setEditorPane(editorPane);
    }

    /**
     * Creates a new instance of BaseEditorDialog
     *
     * @param owner the <code>JFrame</code> from which the dialog is displayed
     */
    public BaseEditorDialog(JFrame owner) {
        super(owner, true);

        try {
            init();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        InputMap iMap = getRootPane().getInputMap(
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), Language.getString("escape"));
        getRootPane().getActionMap().put(Language.getString("escape"), new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                closeDialog(null);
            }
        });

        closeButton.addActionListener(new CloseAction(this));
    }

    /**
     * Brings up a dialog that displays an editor determined by <code>editorPane</code>
     *
     * @param editorPane the <code>editorPane</code> object for this dialog
     */
    static public void showModal(Container editorPane)
    {
        showModal((JFrame)null, editorPane);
    }

    /**
     * Brings up a dialog that displays an editor determined by <code>editorPane</code>
     *
     * @param owner the <code>JFrame</code> in which the dialog is displayed
     * @param editorPane the <code>editorPane</code> object for this dialog
     */
    static public void showModal(JFrame owner, Container editorPane)
    {
        BaseEditorDialog d = new BaseEditorDialog(owner);
        d.setEditorPane(editorPane);
        d.setModal(true);
        d.setVisible(true);
    }

    /**
     * Returns the <code>editorPane</code> object for this dialog.
     *
     * @return the <code>editorPane</code> property
     *
     * @see #setEditorPane
     */
    public Container getEditorPane() {
        return editorPane;
    }

    /**
     * Sets the <code>editorPane</code> property.
     * This method is called by the constructor.
     *
     * @param editorPane the <code>editorPane</code> object for this dialog
     *
     * @see #getEditorPane
     */
    public void setEditorPane(Container editorPane) {
        if (this.editorPane != null) { contentPane.remove(this.editorPane); }
        if (editorPane == null) { editorPane = new JPanel(); }
        this.editorPane = editorPane;
        contentPane.add(editorPane, BorderLayout.CENTER);
        if (editorPane instanceof CloseButtonPane) {
            ((CloseButtonPane)editorPane).addCloseButton(closeButton);
            buttonPane.setVisible(false);
        } else {
            buttonPane.setVisible(true);
        }
        pack();
    }

    /**
     * Called by the constructor methods to create the default
     * <code>buttonPane</code>.
     */
    protected Container createButtonPane() {
        JPanel p = new JPanel();
        {
            p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
            p.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            p.add(Box.createHorizontalGlue());
            p.add(closeButton);
        }
        return p;
    }

    /**
     * Called by the constructor methods to create the default
     * <code>closeButton</code>.
     */
    protected void createCloseButton() {
        closeButton.setDefaultCapable(true);
        closeButton.setText(Language.getString("Close"));
        closeButton.addActionListener(new CloseAction(this));
    }

    /**
     * Sets the <code>buttonPane</code> property.
     * This method is called by the constructor.
     *
     * @param buttonPane the <code>editorPane</code> object for this dialog
     */
    protected void setButtonPane(Container buttonPane) {
        if (this.buttonPane != null) { contentPane.remove(this.buttonPane); }
        this.buttonPane = buttonPane;
        contentPane.add(buttonPane, java.awt.BorderLayout.SOUTH);
        pack();
    }

    private void init() throws Exception {
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(new BorderLayout());
        createCloseButton();
        setButtonPane(createButtonPane());
        setEditorPane(new JPanel());
        setLocationRelativeTo(getParent());
    }

    public void closeDialog(ActionEvent e) {
        dispose();
    }
}

final class CloseAction implements ActionListener {
    private BaseEditorDialog adaptee;

    CloseAction(BaseEditorDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent ae) {
        adaptee.closeDialog(ae);
    }
}

