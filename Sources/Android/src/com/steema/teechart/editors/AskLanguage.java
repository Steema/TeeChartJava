package com.steema.teechart.editors;

import com.steema.teechart.languages.Language;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public final class AskLanguage {

	public final static String[] items = { "(Default)", 
			"Catalan", "Chinese Simp", "Chinese Trad", "Dutch", "German", "English",
			"French", "Indonesian", "Italian", "Japanese", "Korean",
			"Norwegian", "Portuguese", "Russian", "Slovak",
			"Slovene", "Spanish", "Swedish", "Turkish", "Ukrainian", };

	public final static String[] locales = { "", 
			"ca", "zh CN", "zh HK", "nl", "de", "en", 
			"fr", "id", "it", "ja JP", "ko KR", 
			"no", "pt", "ru", "sk", 
			"sl", "es", "sv", "tr", "uk" };

	protected int language;
	
	public void chooseLanguage(final Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context)
				.setTitle(Language.getString("Language"))
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					
					
					public void onClick(DialogInterface dialog, int which) {
						Language.activate(locales[language]);
					}
				})
				.setNegativeButton(android.R.string.cancel, null);

		builder.setSingleChoiceItems(items, getCurrentLanguage(),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						language = item;
					}
				});

		builder.create().show();
	}

	public static int getCurrentLanguage() {
		if (Language.currentLocale==null)
			return 0;
		else
		{
			final String lang = Language.currentLocale.getLanguage();
			final String country = Language.currentLocale.getCountry();
			
			for (int t=0; t<locales.length; t++)
				if (locales[t].equalsIgnoreCase(lang) ||
					locales[t].equalsIgnoreCase(lang+" "+country))
						return t;
		}
		return 0;
	}
}
