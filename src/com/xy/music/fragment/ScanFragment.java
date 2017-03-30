package com.xy.music.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.xy.music.R;
import com.xy.music.activity.IConstants;
import com.xy.music.activity.MenuScanActivity;
import com.xy.music.db.DatabaseHelper;
import com.xy.music.utils.MusicUtils;
import com.xy.music.view.TitleBar;

/**
 * 
 * @author xy
 *
 */
public class ScanFragment extends Fragment implements IConstants, OnClickListener {

	private TitleBar mTitleBar;
	private Handler mHandler;
	private DatabaseHelper mHelper;
	
	public static final int SCAN_SUCCESS = 0;
	public static final int SCAN_FAIL = 1;
	
	private ImageView mIvGreenHalo;
	private ImageView mIvGreenHalo2;
	private ImageView mIvWhiteHalo;
	private ImageView mIvCircle;
	private ImageButton mBtnFreeOnOff;
	
	private Animation mScaleFadeAnim;
//	private Animation mShakeAnim;
	
	private int musicNum; // 歌曲数量
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHelper = new DatabaseHelper(getActivity());
//		mShakeAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
		mScaleFadeAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_fade);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.menu_scan_fragment, container,
				false);
		mTitleBar = (TitleBar) view.findViewById(R.id.titlebar_back);
		mTitleBar.setTitle("歌曲扫描");
		mTitleBar.setOnClickListener(this);

		mIvCircle = (ImageView) view.findViewById(R.id.ivCircle_fragment_wifi);
		mIvGreenHalo = (ImageView) view.findViewById(R.id.ivGreenHalo_fragment_wifi);
		mIvGreenHalo2 = (ImageView) view.findViewById(R.id.ivGreenHalo2_fragment_wifi);
		mIvWhiteHalo = (ImageView) view.findViewById(R.id.ivWhiteHalo_fragment_wifi);
		mBtnFreeOnOff = (ImageButton) view.findViewById(R.id.btnFreeOnOff_fragment_wifi);
		mBtnFreeOnOff.setOnClickListener(this);
		
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case SCAN_SUCCESS: //扫描成功
					stopCircleAnim();
					mIvWhiteHalo.clearAnimation();
					mIvGreenHalo.clearAnimation();
					mIvGreenHalo2.clearAnimation();
					mBtnFreeOnOff.setImageResource(R.drawable.btn_wifi_off_selector);
					Toast.makeText(getActivity(),"为您扫描到"+musicNum+"首歌曲",Toast.LENGTH_SHORT).show();
					break;

				case SCAN_FAIL: // 扫描失败
					Toast.makeText(getActivity(),"抱歉!未扫描到歌曲",Toast.LENGTH_SHORT).show();
					break;
					
				default:
					break;
				}
				super.handleMessage(msg);
//				((MenuScanActivity)getActivity()).mViewPager.setCurrentItem(0, true);
			}
		};

		return view;
	}

	private void getData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				mHelper.deleteTables(getActivity());
				MusicUtils.queryMusic(getActivity(), START_FROM_LOCAL);
				MusicUtils.queryAlbums(getActivity());
				MusicUtils.queryArtist(getActivity());
				MusicUtils.queryFolder(getActivity());
				musicNum = MusicUtils.queryMusic(getActivity(), START_FROM_LOCAL).size();
				Message message = new Message();
				message.what = SCAN_SUCCESS;
				mHandler.sendMessage(message);
			}
		}).start();
	}

	@Override
	public void onClick(View v) {
		if(v == mBtnFreeOnOff) {
			mBtnFreeOnOff.setImageResource(R.drawable.btn_wifi_on_selector);
			startCircleAnim();
			mIvWhiteHalo.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.scale_fade));
			mIvGreenHalo.startAnimation(mScaleFadeAnim);
			mIvGreenHalo2.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.scale_fade2));
			getData();
		} else if(v == mTitleBar) {
			((MenuScanActivity)getActivity()).mViewPager.setCurrentItem(0, true);
		}
	}
	
	/**
	 * 开始转圈动画
	 */
	public void startCircleAnim() {
		if (mIvCircle.getAnimation() == null) {
			Animation rotateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
			mIvCircle.startAnimation(rotateAnimation);
			mIvCircle.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 结束转圈动画
	 */
	public void stopCircleAnim() {
		mIvCircle.clearAnimation();
		mIvCircle.setVisibility(View.INVISIBLE);
	}
}
