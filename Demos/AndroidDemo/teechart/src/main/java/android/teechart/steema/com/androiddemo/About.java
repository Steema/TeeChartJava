package android.teechart.steema.com.androiddemo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class About extends AppCompatActivity implements View.OnClickListener {
    ImageView ivTwitter, ivFacebook;

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
        ivFacebook = (ImageView) findViewById(R.id.ivFacebook);
        ivTwitter = (ImageView) findViewById(R.id.ivTwitter);
        ivFacebook.setOnClickListener(this);
        ivTwitter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivFacebook:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/SteemaSoftware")));
                break;

            case R.id.ivTwitter:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/SteemaSoftware")));
                break;
        }
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