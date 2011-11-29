/*
 * Copyright (c) 2011 Roberto Tyley
 * This file is part of 'lazy-drawables'.
 *
 * 'lazy-drawables' is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * 'lazy-drawables' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details
 *
 * You should have received a copy of the GNU General Public License
 * along with 'lazy-drawables'.  If not, see <http://www.gnu.org/licenses/>.
 */

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

    @Override
	public Drawable convert(Bitmap bitmap) {
		return new BitmapDrawable(resources, createScaledBitmap(bitmap, sizeInPixels, sizeInPixels, true));
	}

}
