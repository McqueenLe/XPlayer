package com.xy.music.view;

import com.xy.music.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TitleBar extends RelativeLayout {
    private ImageView mLeftIcon;
    private ImageView mRightIcon;
    private TextView mTvTitle;

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        RelativeLayout view = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.titlebar_layout, this, true);
        mTvTitle = (TextView) view.findViewById(R.id.tvTitle_titlebar_layout);
        mLeftIcon = (ImageView) view.findViewById(R.id.ivBack_titlebar_layout);
        mRightIcon = (ImageView) view.findViewById(R.id.ivMenu_titlebar_layout);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        int titleStringId = array.getResourceId(R.styleable.TitleBar_titleText, -1); // 获得Title 标题文字的id
        int leftIconresId = array.getResourceId(R.styleable.TitleBar_leftIcon, -1); // 获得标题栏左侧图标的id
        int rightIconresId = array.getResourceId(R.styleable.TitleBar_rightIcon, -1); // 获得标题栏右侧图标的id
        boolean showTitleText = array.getBoolean(R.styleable.TitleBar_showTitleText, true);
        boolean showLeftIcon = array.getBoolean(R.styleable.TitleBar_showLeftIcon, false);
        boolean showRightIcon = array.getBoolean(R.styleable.TitleBar_showRightIcon,false);

        if (titleStringId != -1) {
            setTitle(titleStringId); // 设置标题栏标题
        }

        setTitleTextVisibility(showTitleText);

        if (showLeftIcon || leftIconresId != -1 ) {
            if (leftIconresId != -1) { //设置标题栏左侧图标,默认为隐藏
                setLeftIcon(leftIconresId);
            }
            setLeftIconVisibility(true);
        }

        if (showRightIcon || rightIconresId != -1) {
            if (rightIconresId != -1) { //设置标题栏右侧图标，默认为隐藏
                setRightIcon(rightIconresId);
            }
            setRightIconVisibility(true);
        }
    }

    /**
     * 设置标题文字
     * @param resId 标题文字的String Id
     */
    public void setTitle(int resId) {
        mTvTitle.setText(resId);
    }
    
    /**
     * 设置标题文字
     * @param text 标题文字的String
     */
    public void setTitle(CharSequence text) {
        mTvTitle.setText(text);
    }

    /**
     * 设置标题栏左边图标图片
     * @param resId 图片资源ID
     */
    public void setLeftIcon(int resId) {
        mLeftIcon.setImageResource(resId);
    }

    /**
     * 设置标题栏右边图标图片
     * @param resId 图片资源ID
     */
    public void setRightIcon(int resId) {
        mRightIcon.setImageResource(resId);
    }

    /**
     * 设置左边图标的点击事件
     * @param listener
     */
    public void setLeftIconClickListener(OnClickListener listener) {
        mLeftIcon.setOnClickListener(listener);
    }

    /**
     * 设置右边图标的点击事件
     * @param listener
     */
public void setRightIconClickListener(OnClickListener listener) {
        mRightIcon.setOnClickListener(listener);
    }

/**
 * 设置标题是否隐藏
 * @param hide
 */
public void setTitleTextVisibility(boolean show) {
if (!show) { 
    mTvTitle.setVisibility(View.INVISIBLE); // hide title
} else { 
    mTvTitle.setVisibility(View.VISIBLE); // show title
}
}

    /**
     * 设置左边图标是否隐藏
     * @param hide
     */
public void setLeftIconVisibility(boolean show) {
    if (!show) { 
        mLeftIcon.setVisibility(View.INVISIBLE); // hide left icon
    } else { 
        mLeftIcon.setVisibility(View.VISIBLE); // show left icon
    }
}

    /**
     * 设置右边图标是否隐藏
     * @param hide
     */
    public void setRightIconVisibility(boolean show) {
        if (!show) {
            mRightIcon.setVisibility(View.INVISIBLE); // hide right icon
        } else {
            mRightIcon.setVisibility(View.VISIBLE); // show right icon
        }
    }
}
