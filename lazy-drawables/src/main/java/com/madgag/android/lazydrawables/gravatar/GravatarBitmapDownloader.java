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

package com.madgag.android.lazydrawables.gravatar;

import static java.net.URLEncoder.encode;
import static org.apache.commons.io.IOUtils.closeQuietly;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.madgag.android.lazydrawables.ImageResourceDownloader;

public class GravatarBitmapDownloader implements ImageResourceDownloader<String, Bitmap> {

	private final static String TAG = "GBD";

	private int size = 128;

	public Bitmap get(String gravatarId) {
		InputStream is = downloadStreamFor(gravatarId);
		try {
			return BitmapFactory.decodeStream(is);
		} finally {
			closeQuietly(is);
		}
	}

	private InputStream downloadStreamFor(String gravatarId) {
		Log.d(TAG, "downloadStreamFor " + gravatarId);
		try {
			URL aURL = new URL("http://www.gravatar.com/avatar/"
					+ encode(gravatarId) + "?s=" + size + "&d=mm");
			HttpURLConnection conn = (HttpURLConnection) aURL.openConnection();
			conn.setDoInput(true);
			conn.connect();
			return conn.getInputStream();
		} catch (IOException ioe) {
			Log.e(TAG, "downloadGravatar " + gravatarId, ioe);
			throw new RuntimeException(ioe);
		}
	}

}
