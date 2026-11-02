package com.steema.teechart.editors;

/*
 * AboutDialog.java
 *
 * Created on 24 March 2005, 0:01
 */

import java.awt.BorderLayout;
import com.steema.teechart.languages.Language;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;


/**
 *
 * <p>Title: AboutDialog class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2004-2019 by Steema Software SL. All Rights Reserved.</p>
 *
 * <p>Company: Steema Software SL</p>
 *
 */
final public class AboutDialog extends JDialog {


    JLabel label1 = new JLabel();
    JLabel label2 = new JLabel();
    JLabel label3 = new JLabel();
    JLabel label4 = new JLabel();
    JButton closeButton = new JButton(Language.getString("Close"));
    JLabel backgroundPane = new JLabel();
    JLabel urlLabel = new JLabel();
    /**
     * Creates a new instance of AboutDialog, with an optional parent frame.
     *
     * @param parent JFrame
     */
    public AboutDialog(JFrame parent) {
        this();
        setLocationRelativeTo(parent);
    }

    public AboutDialog() {
        super();

        String s = Language.getString("About_TeeChart_for");
        setTitle(Language.getString("About_TeeChart_for"));

        try {
            jbInit();

            getContentPane().setBackground(Color.WHITE);
            urlLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static String steemaCom = Language.getString("www_steema_com");

    private void jbInit() {
        setSize(370, 280);
        setResizable(false);

        ImageIcon image = new ImageIcon(AboutDialog.class.getResource(
                Language.getString("images_aboutback_png")));

        backgroundPane.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        backgroundPane.setText("");
        backgroundPane.setBounds(new Rectangle(6, 3, 357, 98));
        backgroundPane.setLayout(new BorderLayout());

        urlLabel.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                urlLabel_mouseReleased(e);
            }
        });

        backgroundPane.add(new JLabel(image), BorderLayout.CENTER);

        image = new ImageIcon(AboutDialog.class.getResource(
                Language.getString("images_teeChartPro")));
        urlLabel.setText(
                Language.getString("_html_a_href_http") + steemaCom + "'>" + steemaCom +
                Language.getString("_a_html"));
        urlLabel.setBounds(new Rectangle(192, 99, 97, 15));
        label4.setBounds(new Rectangle(19, 176, 270, 17));
        label1.setToolTipText("");
        label1.setBounds(new Rectangle(19, 125, 210, 17));
        label3.setBounds(new Rectangle(19, 157, 210, 17));
        label2.setBounds(new Rectangle(19, 141, 210, 17));
        closeButton.setBounds(new Rectangle(151, 209, 75, 25));
        backgroundPane.add(new JLabel(image), BorderLayout.CENTER);
        backgroundPane.add(urlLabel, BorderLayout.PAGE_END);
        label1.setText(Language.getString("TeeChart_for_Java")+" "
         +com.steema.teechart.misc.Utils.BuildNumber);
        label2.setText(Language.getString("Copyright"));
        label3.setText(Language.getString("All_Rights_Reserved"));
        label4.setText(Language.getString("Java_Version") +
                       System.getProperty(Language.getString("java_version")) +
                       " " + Language.getString("from")+ " " + System.getProperty(Language.getString("java_vendor"))
                );
        closeButton.setActionCommand(Language.getString("Close"));
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }
        );

        getRootPane().setDefaultButton(closeButton);

        getContentPane().setLayout(null);

        this.getContentPane().add(backgroundPane);
        this.getContentPane().add(label2);
        this.getContentPane().add(label3);
        this.getContentPane().add(label4);
        this.getContentPane().add(label1);
        this.getContentPane().add(closeButton);
        this.getContentPane().add(urlLabel);
        this.getContentPane().add(backgroundPane, null);
    }

    /**
     * Shows the TeeChart About box modal dialog.
     *
     * @param parent JFrame
     */
    static public void showDialog(JFrame parent) {
        AboutDialog tmp = new AboutDialog(parent);
        tmp.setModal(true);
        tmp.setVisible(true);
    }

    /**
     * Shows the TeeChart About box modal dialog.
     */
    static public void showDialog() {
        showDialog(null);
    }

    public void urlLabel_mouseReleased(MouseEvent e) {
        openURL(Language.getString("http") + steemaCom);
    }

    // openURL method from http://www.centerkey.com/java/browser

    private static final String errMsg =
            Language.getString("Error_attempting_to");

    public static void openURL(String url) {
        String osName = System.getProperty(Language.getString("os_name"));
        try {
            if (osName.startsWith(Language.getString("Mac_OS"))) {
                Class fileMgr = Class.forName(Language.getString("com_apple_eio"));
                Method openURL = fileMgr.getDeclaredMethod(Language.getString("openURL"),
                        new Class[] {String.class});
                openURL.invoke(null, new Object[] {url});
            } else if (osName.startsWith(Language.getString("Windows"))) {
                Runtime.getRuntime().exec(
                        Language.getString("rundll32_url_dll") + url);
            } else { //assume Unix or Linux
                String[] browsers = {Language.getString("firefox"), Language.getString("opera"), Language.getString("konqueror"),
                                    Language.getString("epiphany"), Language.getString("mozilla"), Language.getString("netscape")};
                String browser = null;
                for (int count = 0; count < browsers.length && browser == null;
                                 count++) {
                    if (Runtime.getRuntime().exec(new String[] {"which",
                                                  browsers[count]}).waitFor() ==
                        0) {
                        browser = browsers[count];
                    }
                }
                if (browser == null) {
                    throw new Exception("Could not find web browser");
                } else {
                    Runtime.getRuntime().exec(new String[] {browser, url});
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                                          errMsg + ":\n" +
                                          e.getLocalizedMessage());
        }
    }
}

