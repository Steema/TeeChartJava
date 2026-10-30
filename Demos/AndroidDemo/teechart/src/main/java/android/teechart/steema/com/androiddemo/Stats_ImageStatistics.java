package android.teechart.steema.com.androiddemo;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.steema.teechart.TChart;

public class Stats_ImageStatistics extends AppCompatActivity implements View.OnClickListener {
    public TChart chart;
    ImageView imView;
    Button generateHistogram, browse;
    Bitmap bitmap;
    TextView textView;
    private int[] redArray, greenArray, blueArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_image_statistics);
        generateHistogram = (Button) findViewById(R.id.btnGenerateHistogram);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        generateHistogram.setOnClickListener(this);
        browse = (Button) findViewById(R.id.btnBrowse);
        browse.setOnClickListener(this);
        imView = (ImageView) findViewById(R.id.ivImage);
        textView = (TextView) findViewById(R.id.tvImageId);
        textView.setEnabled(false);
        textView.setClickable(false);
        BitmapDrawable drawable = (BitmapDrawable) imView.getDrawable();
        bitmap = drawable.getBitmap();
        if(isGreater(bitmap))
        {
            bitmap = rescale(bitmap);
        }
    }

    public void marchThroughImage(Bitmap image) {
        int w = image.getWidth();
        int h = image.getHeight();

        int position = 0;
        redArray = new int[w * h];
        greenArray = new int[w * h];
        blueArray = new int[w * h];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int pixel = image.getPixel(j, i);
                printPixelRGB(pixel, i, j, position);
                position++;
            }
        }
    }

    public void printPixelRGB(int pixel, int i, int j, int position) {
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        redArray[position] = red;
        greenArray[position] = green;
        blueArray[position] = blue;
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btnBrowse) {
            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 0);

        } else if (v.getId() == R.id.btnGenerateHistogram) {

            marchThroughImage(bitmap);
            Intent myIntent = new Intent(v.getContext(), Stats_HistogramView.class);
            myIntent.putExtra("redValues", redArray);
            myIntent.putExtra("greenValues", greenArray);
            myIntent.putExtra("blueValues", blueArray);
            startActivityForResult(myIntent, 0);
        }

    }

    private Bitmap rescale(Bitmap bitmap) {
        int w, h;
        w = bitmap.getWidth();
        h = bitmap.getHeight();
        if (w > 200 || h > 200)
        {
            if(w>200 && h > 200 )
            {
                w = 200; h = 200;
                bitmap =	 Bitmap.createScaledBitmap(bitmap, w, h, false);
            }
            else if (w > 200 && h<=200)
            {
                if(w*h>40000)
                {
                    w=200;
                    bitmap = Bitmap.createScaledBitmap(bitmap, w, h, false);
                }

            }
            else if (h > 200 && w<=200)
            {
                if(w*h>40000)
                {
                    h=200;
                    bitmap = Bitmap.createScaledBitmap(bitmap, w, h, false);
                }

            }
        }
        return bitmap;
    }
    private boolean isGreater(Bitmap bitmap) {
        int w, h;
        w = bitmap.getWidth();
        h = bitmap.getHeight();
        boolean b = false;
        if (w > 200 || h > 200)
        {
            if(w>200 && h > 200 )
            {
                w = 200; h = 200;
                b=true;
            }
            else if (w > 200 && h<=200)
            {
                if(w*h>40000)
                {
                    w=200;
                    b=true;
                }

            }
            else if (h > 200 && w<=200)
            {
                if(w*h>40000)
                {
                    h=200;
                    b=true;
                }

            }
        }
        return b;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            imView.setImageURI(targetUri);
            imView.setAdjustViewBounds(true);
            textView.setEnabled(true);
            textView.setText(targetUri.getLastPathSegment());
            BitmapDrawable drawable = (BitmapDrawable) imView.getDrawable();
            bitmap = drawable.getBitmap();
            if(isGreater(bitmap))
            {
                bitmap = rescale(bitmap);
            }



        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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