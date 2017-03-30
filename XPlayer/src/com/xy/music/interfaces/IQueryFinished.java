package com.xy.music.interfaces;

import java.util.List;

import com.xy.music.model.MusicInfo;

public interface IQueryFinished {
	
	public void onFinished(List<MusicInfo> list);

}
