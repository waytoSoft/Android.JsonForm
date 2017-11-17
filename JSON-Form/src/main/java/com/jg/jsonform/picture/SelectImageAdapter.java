package com.jg.jsonform.picture;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jg.jsonform.R;
import com.jg.jsonform.picture.model.data.PictureEntity;
import com.jg.jsonform.view.recyclerview.RecyclerViewBaseAdapter;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/10/13 11:32
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class SelectImageAdapter extends RecyclerViewBaseAdapter<PictureEntity> {

    private OnSelectPictureListener selectPictureListener;

    public SelectImageAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onBaseCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBaseBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;

        /*计算图片大小*/
        int width = getScreenW((Activity) mContent) / 3;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, width);
        viewHolder.imageView.setLayoutParams(params);
        viewHolder.textView.setLayoutParams(params);

        viewHolder.checkBox.setOnCheckedChangeListener(null);

        /*判断添加拍照*/
        if (mList.get(position).getUrl().equals(SelectImageActivity.KEY_TAKE_PICTURE)) {

            Glide.with(mContent).load(R.mipmap.icon_tack_picture).into(viewHolder.imageView);
            viewHolder.checkBox.setVisibility(View.GONE);

        } else {
            Glide.with(mContent)
                    .load("file://" + (mList.get(position).getUrl()))
                    .placeholder(R.mipmap.default_img)
                    .error(R.mipmap.default_img)
                    .into(viewHolder.imageView);

            viewHolder.checkBox.setVisibility(View.VISIBLE);
        }

        viewHolder.checkBox.setChecked(mList.get(position).isSelecte());

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    viewHolder.textView.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.textView.setVisibility(View.GONE);
                }

                mList.get(position).setSelecte(isChecked);

                if (selectPictureListener != null)
                    selectPictureListener.onSelectePicture(mList.get(position), isChecked);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        PhotoAlbumImageView imageView;
        CheckBox checkBox;
        TextView textView;

        public ViewHolder(View view) {
            super(view);
            imageView = (PhotoAlbumImageView)view.findViewById(R.id.Item_ImageView);
            checkBox = (CheckBox) view.findViewById(R.id.ItemSelectePicture_CheckBox);
            textView = (TextView) view.findViewById(R.id.ItemSelectePicture_shadow);
        }
    }

    public void setSelectPictureListener(OnSelectPictureListener selectPictureListener) {
        this.selectPictureListener = selectPictureListener;
    }


    /**
     * 获取屏幕宽度
     * <p>
     * author: hezhiWu
     * created at 2017/10/16 20:19
     */
    public static int getScreenW(Activity aty) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = aty.getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        return w;
    }

    interface OnSelectPictureListener {
        void onSelectePicture(PictureEntity entity, boolean isCheck);
    }
}
