package com.madgag.android.lazydrawables;

import static android.graphics.Bitmap.createScaledBitmap;
import static java.lang.Math.round;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ScaledBitmapDrawableGenerator implements ImageProcessor<Bitmap> {

	private final int sizeInPixels;
	private final Resources resources;
	
	public ScaledBitmapDrawableGenerator(int sizeInDIP, Resources resources) {
		this.resources = resources;
		final float scale = resources.getDisplayMetrics().density;
		sizeInPixels = round(sizeInDIP * scale);
	}

	public Drawable convert(Bitmap bitmap) {
		return new BitmapDrawable(resources, createScaledBitmap(bitmap, sizeInPixels, sizeInPixels, true));
	}

}
