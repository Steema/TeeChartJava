package com.steema.teechart.android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class About extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		openLink(R.id.linkWebsite, "https://www.steema.com");
		openLink(R.id.linkGithub, "https://github.com/Steema/TeeChartJava");
		openLink(R.id.linkX, "https://x.com/SteemaSoftware");
		openLink(R.id.linkLinkedin, "https://www.linkedin.com/company/steema-software");
		openLink(R.id.linkYoutube, "https://www.youtube.com/c/Steema");
	}

	private void openLink(int viewId, final String url) {
		findViewById(viewId).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
			}
		});
	}
}
