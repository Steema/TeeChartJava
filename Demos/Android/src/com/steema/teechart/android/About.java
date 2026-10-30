package com.steema.teechart.android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

public class About extends Activity
		 {

	private EditText textEdit;
	private ImageView facebook,twitter;
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		textEdit = (EditText) findViewById(R.id.editText1);
		textEdit.setEnabled(false);
		facebook = (ImageView) findViewById(R.id.facebook);
		facebook.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				 Uri uri = Uri.parse("http://www.facebook.com/SteemaSoftware");
				 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				 startActivity(intent);
				
			}});
		twitter = (ImageView) findViewById(R.id.twitter);
		twitter.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				 Uri uri = Uri.parse("http://twitter.com/#!/SteemaSoftware");
				 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				 startActivity(intent);
				
			}});
		
	}


}