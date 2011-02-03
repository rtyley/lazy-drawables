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
