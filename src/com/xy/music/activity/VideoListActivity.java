package com.xy.music.activity;

import java.util.ArrayList;

import com.xy.music.R;
import com.xy.music.adapter.VideoListAdapter;
import com.xy.music.impl.VideoProviderImpl;
import com.xy.music.model.Video;
import com.xy.music.utils.LoadedImage;
import com.xy.music.view.TitleBar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class VideoListActivity extends Activity{
	private ListView mListView;
	private TitleBar mTitleBar;
	private VideoListAdapter mListAdapter;
	private ArrayList<Video> mVideoList = new ArrayList<Video>();
	private SparseBooleanArray mArray = new SparseBooleanArray();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_list);
		mListView = (ListView) findViewById(R.id.listview_video_list);
		mTitleBar = (TitleBar) findViewById(R.id.activity_video_list_back);
		mTitleBar.setTitle("我的视频");
		mTitleBar.setLeftIconClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		// 获取视频列表
		getVideoList();
		
		mListAdapter = new VideoListAdapter(VideoListActivity.this, mVideoList);
		mListView.setAdapter(mListAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Video video = mVideoList.get(position);
				Intent intent = new Intent(VideoListActivity.this, VideoPlayActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("video", video);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				
				return false;
			}
		});
	}
	
//	@Override
//	public Object onRetainNonConfigurationInstance() {
//		final ListView listview = mListView;
//		final int count = listview.getChildCount();
//		final LoadedImage[] list = new LoadedImage[count];
//
//		for (int i = 0; i < count; i++) {
//			final ImageView v = (ImageView) listview.getChildAt(i);
//			list[i] = new LoadedImage(
//					((BitmapDrawable) v.getDrawable()).getBitmap());
//		}
//
//		return list;
//	}
	
	// 获取视频列表
	private void getVideoList() {
		VideoProviderImpl videoImpl = new VideoProviderImpl(VideoListActivity.this);
		mVideoList.addAll(videoImpl.getList());
//		for(int i=0; i<mVideoList.size(); i++) {
//			Video v = mVideoList.get(i);
//			mArray.put(i, v.isChecked);
//		}
	}
	
}
