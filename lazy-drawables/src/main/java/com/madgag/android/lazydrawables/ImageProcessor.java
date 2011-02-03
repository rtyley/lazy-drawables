package com.madgag.android.lazydrawables;

import android.graphics.drawable.Drawable;

public interface ImageProcessor<ImageResourceType> {
	public Drawable convert(ImageResourceType imageResource);
}
