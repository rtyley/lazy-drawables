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
