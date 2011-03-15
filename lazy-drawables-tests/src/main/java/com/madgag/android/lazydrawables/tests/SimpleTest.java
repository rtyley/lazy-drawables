/*
 * Copyright (c) 2011 Roberto Tyley
 * This file is part of 'lazy-drawables-tests'.
 *
 * 'lazy-drawables-tests' is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * 'lazy-drawables-tests' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details
 *
 * You should have received a copy of the GNU General Public License
 * along with 'lazy-drawables-tests'.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.madgag.android.lazydrawables.tests;

import static android.graphics.Bitmap.createBitmap;

import java.util.HashMap;
import java.util.Map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.test.InstrumentationTestCase;

import com.madgag.android.lazydrawables.ImageProcessor;
import com.madgag.android.lazydrawables.ImageResourceDownloader;
import com.madgag.android.lazydrawables.ImageResourceStore;
import com.madgag.android.lazydrawables.ImageSession;
import com.madgag.android.lazydrawables.ScaledBitmapDrawableGenerator;

public class SimpleTest extends InstrumentationTestCase {
	
	public SimpleTest() {
	}
	
	public void testThatNothingDies() {
		Resources resources = getInstrumentation().getContext().getResources();
		ImageProcessor<Bitmap> imageProcessor = new ScaledBitmapDrawableGenerator(50, resources);
		ImageResourceDownloader<String, Bitmap> downloader = new ImageResourceDownloader<String, Bitmap>() {
			public Bitmap get(String key) {
				return createBitmap(100, 100, Bitmap.Config.ARGB_8888);
			}
		};
		ImageResourceStore<String, Bitmap> imageResourceStore = new MapBasedStore<String, Bitmap>();
		Drawable downloadingDrawable = resources.getDrawable(android.R.drawable.stat_sys_download);
		ImageSession<String, Bitmap> session = new ImageSession<String, Bitmap>(imageProcessor, downloader, imageResourceStore, downloadingDrawable);
		
		Drawable bangDrawable = session.get("bang");
	}

	
	
	static class MapBasedStore<K, V> implements ImageResourceStore<K, V> {
		Map<K,V> m=new HashMap<K, V>();
		
		public V get(K key) {
			return m.get(key);
		}
	
		public void put(K key, V value) {
			m.put(key, value);
		};
	}
}
