package com.steema.teechart.languages;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * <p>
 * Title: TeeChart for Java
 * </p>
 * 
 * <p>
 * Description: Internationalization i18n language selection.
 * </p>
 * 
 * <p>
 * Copyright: (c) 2004-2019 by Steema Software SL. All Rights Reserved.
 * </p>
 * 
 * <p>
 * Company: Steema Software SL
 * </p>
 * 
 * @author cdi
 * @version 1.1
 */
public final class Language {

	private static final String languages = "com.steema.teechart.languages.languages";
	public static Locale currentLocale;
	private static ResourceBundle bundle;

	public static String lineSeparator;
	public static String columnSeparator;
	public static boolean isDesignTime;

	static public void activate(final String locale) {

		if (locale == "")
			activate();
		else {
			final int pos = locale.indexOf(" ");

			if (pos == -1)
				activate(new Locale(locale));
			else {
				final String lang = locale.substring(0, pos - 1);
				final String country = locale.substring(pos + 1);
				activate(new Locale(lang, country));
			}
		}
	}

	static public void activate(final Locale locale) {
		currentLocale = locale;

		if (!isDesignTime) {
			try {
				// See getBundle method in jdk documentation
				bundle = ResourceBundle.getBundle(languages, currentLocale);

			} catch (java.util.MissingResourceException exception) {
				bundle = ResourceBundle.getBundle(languages);
			}

			lineSeparator = getString("LineSeparator");
			columnSeparator = getString("ColumnSeparator");
		} 
		else
		{
			lineSeparator = "\n";
			columnSeparator = "\t";
		}

	}

	static public void activate() {
		activate(Locale.getDefault());
	}

	static public String getString(final String text) {

		if (bundle == null)
			activate();

		if (bundle == null)
			return text;
		else
		try {
			return (String) bundle.getObject(text);
		} catch (MissingResourceException e) {
			try {
				return (String) bundle.getObject(text.toUpperCase());
			} catch (MissingResourceException e1) {
				return text;
			}
		}
	}
}
