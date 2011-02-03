package com.madgag.android.lazydrawables;

public class ImageResourceService<K, ImageResourceType> {
	ImageResourceDownloader<K, ImageResourceType> downloader;
	ImageResourceStore<K, ImageResourceType> diskImageStore;
}
