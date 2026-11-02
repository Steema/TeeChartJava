package android.teechart.steema.com.androiddemo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class About extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setElevation(0);
            actionBar.setTitle(R.string.itAbout);
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
