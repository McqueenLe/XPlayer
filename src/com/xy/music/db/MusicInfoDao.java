package com.xy.music.db;
/**
 * 音乐信息dao
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xy.music.activity.IConstants;
import com.xy.music.model.MusicInfo;

public class MusicInfoDao implements IConstants {
	
	private static final String TABLE_MUSIC = "music_info";
	private Context mContext;
	
	public MusicInfoDao(Context context) {
		this.mContext = context;
	}
	
	public void saveMusicInfo(List<MusicInfo> list) {
		DatabaseHelper helper = new DatabaseHelper(mContext);
		SQLiteDatabase db = helper.getWritableDatabase();
		for (MusicInfo music : list) {
			ContentValues cv = new ContentValues();
			cv.put("songid", music.songId);
			cv.put("albumid", music.albumId);
			cv.put("duration", music.duration);
			cv.put("musicname", music.musicName);
			cv.put("artist", music.artist);
			cv.put("data",music.data);
			cv.put("folder", music.folder);
			cv.put("musicnamekey", music.musicNameKey);
			cv.put("artistkey", music.artistKey);
			cv.put("favorite", music.favorite);
			db.insert(TABLE_MUSIC, null, cv);
		}
	}
	
	public List<MusicInfo> getMusicInfo() {
		DatabaseHelper helper = new DatabaseHelper(mContext);
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "select * from " + TABLE_MUSIC;
		
		return parseCursor(db.rawQuery(sql, null));
	}
	
	private List<MusicInfo> parseCursor(Cursor cursor) {
		List<MusicInfo> list = new ArrayList<MusicInfo>();
		while(cursor.moveToNext()) {
			MusicInfo music = new MusicInfo();
			music._id = cursor.getInt(cursor.getColumnIndex("_id"));
			music.songId = cursor.getInt(cursor.getColumnIndex("songid"));
			music.albumId = cursor.getInt(cursor.getColumnIndex("albumid"));
			music.duration = cursor.getInt(cursor.getColumnIndex("duration"));
			music.musicName = cursor.getString(cursor.getColumnIndex("musicname"));
			music.artist = cursor.getString(cursor.getColumnIndex("artist"));
			music.data = cursor.getString(cursor.getColumnIndex("data"));
			music.folder = cursor.getString(cursor.getColumnIndex("folder"));
			music.musicNameKey = cursor.getString(cursor.getColumnIndex("musicnamekey"));
			music.artistKey = cursor.getString(cursor.getColumnIndex("artistkey"));
			music.favorite = cursor.getInt(cursor.getColumnIndex("favorite"));
			list.add(music);
		}
		cursor.close();
		return list;
	}

	public List<MusicInfo> getMusicInfoByType(String selection, int type) {
		DatabaseHelper helper = new DatabaseHelper(mContext);
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "";
		if(type == START_FROM_ARTIST) {
			sql = "select * from " + TABLE_MUSIC + " where artist = ?";
		} else if(type == START_FROM_ALBUM) {
			sql = "select * from " + TABLE_MUSIC + " where albumid = ?";
		} else if(type == START_FROM_FOLDER) {
			sql = "select * from " + TABLE_MUSIC + " where folder = ?";
		}
		return parseCursor(db.rawQuery(sql, new String[]{ selection }));
	}
	
	public void setFavoriteStateById(int id, int favorite) {
		DatabaseHelper helper = new DatabaseHelper(mContext);
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "update " + TABLE_MUSIC + " set favorite = " + favorite + " where _id = " + id;
		db.execSQL(sql);
	}
	
	/**
	 * 数据库中是否有数据
	 * @return
	 */
	public boolean hasData() {
		DatabaseHelper helper = new DatabaseHelper(mContext);
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "select count(*) from " + TABLE_MUSIC;
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
		String sql = "select count(*) from " + TABLE_MUSIC;
		Cursor cursor = db.rawQuery(sql, null);
		int count = 0;
		if(cursor.moveToFirst()) {
			count = cursor.getInt(0);
		}
		return count;
	}

}
