package com.xy.music.adapter;

import java.util.ArrayList;

import com.xy.music.R;
import com.xy.music.model.Video;
import com.xy.music.utils.FileUtil;
import com.xy.music.utils.ImageLoader;
import com.xy.music.utils.LoadedImage;
import com.xy.music.utils.VideoThumbLoader;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class VideoListAdapter extends BaseAdapter{

	private ArrayList<Video> mVideoList;
	private Context mContext;
	private VideoThumbLoader thumbLoader;
	private ArrayList<LoadedImage> photos = new ArrayList<LoadedImage>();
	private SparseBooleanArray mCheckArray = new SparseBooleanArray();
	private boolean isMulti = false;
	
	public VideoListAdapter(Context context, ArrayList<Video> mList) {
		this.mContext = context;
		this.mVideoList = mList;
		thumbLoader = new VideoThumbLoader(mContext);
	}
	
	@Override
	public int getCount() {
		return mVideoList.size();
	}

	@Override
	public Object getItem(int position) {
		return mVideoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		final Video video = mVideoList.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_video_list, null);
			holder.iv_thumbnail = (ImageView) convertView.findViewById(R.id.iv_video_item);
			holder.tv_title = (TextView) convertView.findViewById(R.id.tv_video_title);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_video_time);
			holder.tv_size = (TextView) convertView.findViewById(R.id.tv_video_size);
			holder.cb_video_list = (CheckBox) convertView.findViewById(R.id.cb_video_list);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		convertView.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				isMulti = true;
				notifyDataSetChanged();
				return true;
			}
		});
		
		if (isMulti) {
			holder.cb_video_list.setVisibility(View.VISIBLE);
		}
		holder.cb_video_list.setTag(R.id.cb_video_list, position);
		holder.cb_video_list.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				int pos = (Integer) buttonView.getTag(R.id.cb_video_list);
				mCheckArray.put(pos, isChecked);
				video.isChecked = isChecked;
			}
		});
		
		holder.tv_title.setText(video.getTitle());
		long min = video.getDuration() /1000 / 60;
		long sec = video.getDuration() /1000 % 60;
		holder.tv_time.setText(min + ":" + sec);
		holder.tv_size.setText(FileUtil.convertFileSize(video.getSize()));
		String path = video.getPath();
		holder.iv_thumbnail.setTag(path); // 绑定imageview
		thumbLoader.showThumbByAsynctack(path, holder.iv_thumbnail);
//		if (photos != null && photos.size() > 0 && photos.get(position).getBitmap() != null) {
//			holder.iv_thumbnail.setImageBitmap(photos.get(position).getBitmap());
//		}
		return convertView;
	}

	class ViewHolder {
		ImageView iv_thumbnail;
		TextView tv_title;
		TextView tv_time;
		TextView tv_size;
		CheckBox cb_video_list;
	}
	
	public void addPhoto(LoadedImage image){
		photos.add(image);
	}
}
