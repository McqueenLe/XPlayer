package com.xy.music.utils;

import android.graphics.Bitmap;

public class LoadedImage {
	Bitmap mBitmap;

	public LoadedImage(Bitmap bitmap) {
		mBitmap = bitmap;
	}
	
	public Bitmap getBitmap() {
		return mBitmap;
	}
}
