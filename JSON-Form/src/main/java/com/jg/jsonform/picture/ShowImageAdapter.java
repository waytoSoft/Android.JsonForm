package com.jg.jsonform.picture;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jg.jsonform.R;
import com.jg.jsonform.picture.model.data.PictureEntity;
import com.jg.jsonform.view.recyclerview.RecyclerViewBaseAdapter;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/10/16 20:50
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class ShowImageAdapter extends RecyclerViewBaseAdapter<PictureEntity> {

    public ShowImageAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onBaseCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_show_image, parent, false));
    }

    @Override
    public void onBaseBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Glide.with(mContent)
                .load("file://" + (mList.get(position).getUrl()))
                .placeholder(R.mipmap.default_img)
                .error(R.mipmap.default_img)
                .into(((ViewHolder) holder).imageView);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.Item_Show_ImageView);
        }
    }
}
