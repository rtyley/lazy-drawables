package com.madgag.android.lazydrawables;


public interface ImageResourceStore<K,ImageType> {
    
	ImageType get(K key);
  
	void put(K key, ImageType value);
	
}
