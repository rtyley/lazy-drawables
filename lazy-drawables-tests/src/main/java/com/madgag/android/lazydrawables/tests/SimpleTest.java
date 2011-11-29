/*
 * Copyright 2011 Roberto Tyley
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
