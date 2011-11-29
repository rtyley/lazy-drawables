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

package com.madgag.android.lazydrawables;

import static android.graphics.BitmapFactory.decodeFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.util.Log;

public class BitmapFileStore<K> implements ImageResourceStore<K, Bitmap> {

	private final static String TAG = "BFS";
	
	private final File storeDirectory;
	
	public BitmapFileStore(File storeDirectory) {
		this.storeDirectory = storeDirectory;
		
	}

	public Bitmap get(K key) {
		return decodeFile(imageFileFor(key).getPath());
	}

	public void put(K key, Bitmap bitmap) {
		File imageFile = imageFileFor(key);
		try {
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(imageFile));
		} catch (FileNotFoundException e) {
			Log.e(TAG, "Couldn't write "+imageFile, e);
		}
	}
	
	private File imageFileFor(K key) {
		ensureDirectory();
		return new File(storeDirectory, key.toString() + ".png");
	}
	
	
    private void ensureDirectory() {
		if (!storeDirectory.exists() && !storeDirectory.mkdirs()) {
            throw new RuntimeException("Can't create " + storeDirectory);
        }
		try {
			new File(storeDirectory, ".nomedia").createNewFile();
		} catch (IOException e) {
			Log.e(TAG, "Couldn't write nomedia file for "+storeDirectory, e);
		}
    }



	


}
