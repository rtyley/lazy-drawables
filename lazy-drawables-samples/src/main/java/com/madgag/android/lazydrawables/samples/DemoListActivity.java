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

package com.madgag.android.lazydrawables.samples;

import static android.R.drawable.ic_menu_delete;
import static android.widget.Toast.LENGTH_SHORT;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;
import static org.apache.commons.io.FileUtils.deleteDirectory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.madgag.android.lazydrawables.BitmapFileStore;
import com.madgag.android.lazydrawables.ImageProcessor;
import com.madgag.android.lazydrawables.ImageResourceDownloader;
import com.madgag.android.lazydrawables.ImageResourceStore;
import com.madgag.android.lazydrawables.ImageSession;
import com.madgag.android.lazydrawables.ScaledBitmapDrawableGenerator;

public class DemoListActivity extends ListActivity {
	public static final String TAG = "DLA";
	private final static int CLEAR_STORAGE_ID = Menu.FIRST;
	private File storageDirectory = new File(Environment.getExternalStorageDirectory(),TAG+"-icons");
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final float scale = getResources().getDisplayMetrics().density;
        int iconSize=Math.round(scale*50);
        
        ImageProcessor<Bitmap> imageProcessor = new ScaledBitmapDrawableGenerator(50, getResources());
		ImageResourceDownloader<String, Bitmap> downloader = slowImageMaker();
		ImageResourceStore<String, Bitmap> imageResourceStore = new BitmapFileStore<String>(storageDirectory);
		//Drawable downloadingDrawable = getResources().getDrawable(android.R.drawable.stat_sys_download);
		Drawable downloadingDrawable = getResources().getDrawable(R.drawable.thingo);
		//Drawable downloadingDrawable = imageProcessor.convert(Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888));
        
        ImageSession<String, Bitmap> imageSession = new ImageSession<String, Bitmap>(imageProcessor, downloader, imageResourceStore, downloadingDrawable);
        List<String> uniqueEmailAddresses = asList("a1@d.com","roberto@github.com","foo.bar@d.com","goo@d.com","bar@d.com","moo@d.com");
        Random random = new Random();
		List<String> emailAddresses = newArrayList();
		for (int i=0; i<200; ++i) {
			emailAddresses.add(uniqueEmailAddresses.get(random.nextInt(uniqueEmailAddresses.size())));
		}
		setListAdapter(new GravatarListAdapter(getLayoutInflater(), emailAddresses, imageSession, downloadingDrawable));
    }

	private ImageResourceDownloader<String, Bitmap> slowImageMaker() {
		return new ImageResourceDownloader<String, Bitmap>() {
			public Bitmap get(String key) {
				Log.d(TAG, "Asked to download "+key);
				try { Thread.sleep(4000L); } catch (InterruptedException e) {}
				Bitmap bitmap = Bitmap.createBitmap(80, 80, Bitmap.Config.ARGB_8888);
				Canvas c = new Canvas(bitmap);
				Paint paint = new Paint();
				paint.setColor(0xff0000ff);
				c.drawCircle(0, 0, 50, paint);
				
				paint.setColor(0xffff007f);
				paint.setStyle(Paint.Style.FILL);
                paint.setAntiAlias(true);
                paint.setTextSize(30);
				c.drawText(key, 0, 3, 0, 60, paint);
				Log.d(TAG, "Done drawing... "+key);
				return bitmap;
			}
		};
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, CLEAR_STORAGE_ID, 0, "Clear image storage").setShortcut('0', 'c').setIcon(ic_menu_delete);

        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case CLEAR_STORAGE_ID:
            try {
				deleteDirectory(storageDirectory);
			} catch (IOException e) {
				Toast.makeText(this, "Couldn't delete "+storageDirectory+" - "+e.getMessage(), LENGTH_SHORT).show();
			}
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
