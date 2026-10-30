package com.steema.teechart.drawing;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;

public final class Image extends android.graphics.drawable.BitmapDrawable {

	public final Bitmap bitmap;
	
	public Image(final int width, final int height) {
		bitmap=Bitmap.createBitmap(width, height, Config.ARGB_8888);
	}

	public Image(final String fileName) {
		super();
		bitmap = BitmapFactory.decodeFile(fileName);
	}

	public Image(final InputStream stream) {
		super();
		bitmap = BitmapFactory.decodeStream(stream);
	}

	public Image(final Bitmap bitmap) {
		super();
		this.bitmap = bitmap;
	}

	public Image(int width, int height, final Object control) {
		this(width, height);
	}

	public int getRGB(final int x, final int y) {
		return bitmap.getPixel(x, y);
	}

	public int getWidth(final Object object) {
		return bitmap.getWidth();
	}

	public int getHeight(final Object object) {
		return bitmap.getHeight();
	}

	public void setRGB(final int x, final int y, final int color) {
		bitmap.setPixel(x, y, color);
	}

	public void save(OutputStream stream) {
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
	}

	public void save(String fileName, String fileExtension, Object format) throws FileNotFoundException {
		FileOutputStream stream = new FileOutputStream(fileName);
		
		if (fileExtension == "jpg")
			bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream);
		else
		if (fileExtension == "png")
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
	}

}
