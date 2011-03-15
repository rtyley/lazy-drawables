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
