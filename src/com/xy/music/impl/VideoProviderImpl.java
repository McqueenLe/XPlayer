package com.xy.music.impl;

import java.util.ArrayList;
import java.util.List;

import com.xy.music.interfaces.VideoListInterface;
import com.xy.music.model.Video;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;

public class VideoProviderImpl implements VideoListInterface{
	
	private Context mContext;
	
	public VideoProviderImpl(Context context) {
		this.mContext = context;
	}

	@Override
	public List<Video> getList() {
		List<Video> mList = new ArrayList<Video>();
		if (mContext != null) {
			ContentResolver ctr = mContext.getContentResolver();
			Cursor c = ctr.query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null,
                    null, MediaColumns.DATE_ADDED + " DESC ");
			if (c != null) {
				while (c.moveToNext()) {
					int id = c.getInt(c.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
					String title = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
					String path = c.getString(c.getColumnIndex(MediaStore.Video.Media.DATA));
					long duration = c.getLong(c.getColumnIndex(MediaStore.Video.Media.DURATION));
					long size = c.getLong(c.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
					String mimeType = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
					String displayName = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
					String album = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM));
					String artist = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST));
					Video video = new Video(id, title, album, artist, displayName, mimeType, path, size, duration);
					mList.add(video);
				}
			}
			c.close();
		}
		return mList;
	}

}
