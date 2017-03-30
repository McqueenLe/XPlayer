package com.xy.music.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

import com.xy.music.R;
import com.xy.music.activity.IConstants;
import com.xy.music.activity.MenuSettingActivity;
import com.xy.music.storage.SPStorage;
import com.xy.music.view.TitleBar;

/**
 * 更多设置页面
 * @author xy
 *
 */
public class SettingFragment extends Fragment implements OnClickListener, IConstants {
	
	private LinearLayout mAdviceLayout, mAboutLayout;
	private CheckedTextView mChangeSongTv, mAutoLyricTv, mFilterSizeTv, mFilterTimeTv;
	private SPStorage mSp;
	
	private TitleBar mTitleBar;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.menu_setting_fragment, container, false);
		mSp = new SPStorage(getActivity());
		initView(view);
		return view;
	}

	private void initView(View view) {
		mAboutLayout = (LinearLayout) view.findViewById(R.id.setting_about_layout);
		mAdviceLayout = (LinearLayout) view.findViewById(R.id.setting_author_layout);
		mAboutLayout.setOnClickListener(this);
		mAdviceLayout.setOnClickListener(this);
		
		mTitleBar = (TitleBar) view.findViewById(R.id.titleBar_setting_back);
		mTitleBar.setTitle("设置");
		mTitleBar.setOnClickListener(this);
		
		mChangeSongTv = (CheckedTextView) view.findViewById(R.id.shake_change_song);
		mAutoLyricTv = (CheckedTextView) view.findViewById(R.id.auto_download_lyric);
		mFilterSizeTv = (CheckedTextView) view.findViewById(R.id.filter_size);
		mFilterTimeTv = (CheckedTextView) view.findViewById(R.id.filter_time);
		
		mChangeSongTv.setChecked(mSp.getShake());
		mAutoLyricTv.setChecked(mSp.getAutoLyric());
		mFilterSizeTv.setChecked(mSp.getFilterSize());
		mFilterTimeTv.setChecked(mSp.getFilterTime());
		
		mChangeSongTv.setOnClickListener(this);
		mAutoLyricTv.setOnClickListener(this);
		mFilterSizeTv.setOnClickListener(this);
		mFilterTimeTv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.setting_about_layout: // 关于
			break;
		case R.id.setting_author_layout: // 作者
			break;
		case R.id.shake_change_song:
			mChangeSongTv.toggle();
			mSp.saveShake(mChangeSongTv.isChecked());
			Intent intent = new Intent(BROADCAST_SHAKE);
			intent.putExtra(SHAKE_ON_OFF, mChangeSongTv.isChecked());
			getActivity().sendBroadcast(intent);
			break;
		case R.id.auto_download_lyric:
			mAutoLyricTv.toggle();
			mSp.saveAutoLyric(mAutoLyricTv.isChecked());
			break;
		case R.id.filter_size:
			mFilterSizeTv.toggle();
			mSp.saveFilterSize(mFilterSizeTv.isChecked());
			break;
		case R.id.filter_time:
			mFilterTimeTv.toggle();
			mSp.saveFilterTime(mFilterTimeTv.isChecked());
			break;
		case R.id.titleBar_setting_back:
			((MenuSettingActivity)getActivity()).mViewPager.setCurrentItem(0, true);
			break;
		}
	}
}
