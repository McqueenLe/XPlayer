package com.xy.music.db;
/**
 * 艺术家dao
 */
import java.util.ArrayList;
import java.util.List;

import com.xy.music.model.ArtistInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ArtistInfoDao {

	private static final String TABLE_ARTIST = "artist_info";
	private Context mContext;
	
	public ArtistInfoDao(Context context) {
		this.mContext = context;
	}
	
	public void saveArtistInfo(List<ArtistInfo> list) {
		DatabaseHelper helper = new DatabaseHelper(mContext);
		SQLiteDatabase db = helper.getWritableDatabase();
		for (ArtistInfo info : list) {
			ContentValues cv = new ContentValues();
			cv.put("artist_name", info.artist_name);
			cv.put("number_of_tracks", info.number_of_tracks);
			db.insert(TABLE_ARTIST, null, cv);
		}
	}
	
	public List<ArtistInfo> getArtistInfo() {
		DatabaseHelper helper = new DatabaseHelper(mContext);
		SQLiteDatabase db = helper.getReadableDatabase();
		List<ArtistInfo> list = new ArrayList<ArtistInfo>();
		String sql = "select * from " + TABLE_ARTIST;
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext()) {
			ArtistInfo info = new ArtistInfo();
			info.artist_name = cursor.getString(cursor.getColumnIndex("artist_name"));
			info.number_of_tracks = cursor.getInt(cursor.getColumnIndex("number_of_tracks"));
			list.add(info);
		}
		cursor.close();
		return list;
	}
	
	/**
	 * 数据库中是否有数据
	 * @return
	 */
	public boolean hasData() {
		DatabaseHelper helper = new DatabaseHelper(mContext);
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "select count(*) from " + TABLE_ARTIST;
		Cursor cursor = db.rawQuery(sql, null);
		boolean has = false;
		if(cursor.moveToFirst()) {
			int count = cursor.getInt(0);
			if(count > 0) {
				has = true;
			}
		}
		cursor.close();
		return has;
	}
	
	public int getDataCount() {
		DatabaseHelper helper = new DatabaseHelper(mContext);
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "select count(*) from " + TABLE_ARTIST;
		Cursor cursor = db.rawQuery(sql, null);
		int count = 0;
		if(cursor.moveToFirst()) {
			count = cursor.getInt(0);
		}
		return count;
	}
}
