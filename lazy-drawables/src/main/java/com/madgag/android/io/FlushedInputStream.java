package com.madgag.android.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * An InputStream that skips the exact number of bytes provided, unless it reaches EOF. This is needed for
 * older versions of BitmapFactory.decodeStream() which can not handle partial skips - this was fixed in the
 * Android platform around 2010, but the precise version of Android the fix was applied to is not apparent.
 * <p/>
 * Taken from the android-imagedownloader project, licenced under Apache License, Version 2.0
 * http://code.google.com/p/android-imagedownloader/source/browse/trunk/src/com/example/android/imagedownloader/ImageDownloader.java?spec=svn4&r=4#210
 * <p/>
 * See also http://android-developers.blogspot.co.uk/2010/07/multithreading-for-performance.html
 */
public class FlushedInputStream extends FilterInputStream {
    public FlushedInputStream(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    public long skip(long n) throws IOException {
        long totalBytesSkipped = 0L;
        while (totalBytesSkipped < n) {
            long bytesSkipped = in.skip(n - totalBytesSkipped);
            if (bytesSkipped == 0L) {
                int b = read();
                if (b < 0) {
                    break;  // we reached EOF
                } else {
                    bytesSkipped = 1; // we read one byte
                }
            }
            totalBytesSkipped += bytesSkipped;
        }
        return totalBytesSkipped;
    }
}
