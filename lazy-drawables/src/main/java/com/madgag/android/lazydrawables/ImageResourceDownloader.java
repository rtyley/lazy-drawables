package com.madgag.android.lazydrawables;


public interface ImageResourceDownloader<K,ImageType> {
	ImageType get(K key);
}
