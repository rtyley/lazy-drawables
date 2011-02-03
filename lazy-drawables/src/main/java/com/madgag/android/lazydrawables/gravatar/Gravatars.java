package com.madgag.android.lazydrawables.gravatar;

import static com.madgag.android.digests.DigestUtils.md5Hex;

import java.util.concurrent.ConcurrentMap;

import com.google.common.base.Function;
import com.google.common.collect.MapMaker;

public class Gravatars {
	private final static ConcurrentMap<String, String> emailToGravatarIdCache 
	= new MapMaker().makeComputingMap(new Function<String, String>() {
		public String apply(String emailAddress) { return md5Hex(emailAddress.trim().toLowerCase()); }
	});

	public static String gravatarIdFor(String emailAddress) {
		return emailToGravatarIdCache.get(emailAddress);
	}
	
}
