package com.jg.jsonform.attachment;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.jg.jsonform.R;
import com.jg.jsonform.picture.model.data.PictureEntity;
import com.jg.jsonform.utils.IDensityUtil;
import com.jg.jsonform.utils.IFileUtils;
import com.jg.jsonform.utils.IUtil;
import com.jg.jsonform.utils.Luban;
import com.jg.jsonform.utils.OnCompressListener;
import com.jg.jsonform.view.ArrayListAdapter;

import java.io.File;


/**
 * 图片附件适配器
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/14 10:15
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class ImageAttachmentAdapter extends ArrayListAdapter<PictureEntity> {
    private final String TAG = getClass().getSimpleName();

    public static final String ADD_IMG_FLAG = "add:";
    private boolean isShowCloseIcon = false;

    public ImageAttachmentAdapter(Context activity) {
        super(activity);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHodler hodler = new ViewHodler();
        convertView = LayoutInflater.from(mContext).inflate(R.layout.accessory_gridview_item, null);
        hodler.imageView = (ImageView) convertView.findViewById(R.id.accessory_imageview);
        hodler.closeIv = (ImageView) convertView.findViewById(R.id.close_iv);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(IDensityUtil.getScreenW(mContext) / 5, IDensityUtil.getScreenW(mContext) / 5);
        params.rightMargin = 5;
        params.topMargin = 5;
        hodler.imageView.setLayoutParams(params);

        if (!TextUtils.isEmpty(mList.get(position).getNewUrl()) && mList.get(position).getNewUrl().startsWith(ADD_IMG_FLAG)) {
            Glide.with(mContext).load(R.mipmap.icon_add_btn).into(hodler.imageView);
            hodler.closeIv.setVisibility(View.GONE);
        } else {
            if (!TextUtils.isEmpty(mList.get(position).getNewUrl())) {
                if ("compressing".equals(mList.get(position).getNewUrl())) {
                    Glide.with(mContext).load(R.mipmap.default_img).into(hodler.imageView);
                } else {
                    Glide.with(mContext).load(IUtil.fitterUrl(mList.get(position).getNewUrl()))
                            .error(R.mipmap.main_img_defaultpic_small).into(hodler.imageView);
                    if (!TextUtils.isEmpty(mList.get(position).getUrl())) {
                        isShowCloseIcon = true;
                    }
                }
            } else {
                mList.get(position).setNewUrl("compressing");
                Luban.get(mContext)
                        .load(new File(mList.get(position).getUrl()))//传人要压缩的图片
                        .putGear(Luban.THIRD_GEAR)//设定压缩档次，默认三挡
                        .setDate(mList.get(position).getDate())
                        .setPosition(position)
                        .setCompressListener(new OnCompressListener() { //设置回调

                            @Override
                            public void onStart() {
                            }

                            @Override
                            public void onSuccess(int position, File file) {
                                mList.get(position).setNewUrl(file.getAbsolutePath());
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onError(Throwable e) {
                            }
                        }).launch();
            }
        }

        if (isShowCloseIcon && !mList.get(position).getNewUrl().startsWith(ADD_IMG_FLAG)) {
            hodler.closeIv.setVisibility(View.VISIBLE);
        } else {
            hodler.closeIv.setVisibility(View.GONE);
        }

        hodler.closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*删除本地压缩过的图片*/
                IFileUtils.delete(mList.get(position).getNewUrl());
                removePos(position);
                if (mList.size() == ImageAttachmentView.DEFAULT_PICTURE_NUMBER - 1 && !mList.get(mList.size() - 1).getNewUrl().contains(ADD_IMG_FLAG)) {
                    PictureEntity entity = new PictureEntity();
                    entity.setNewUrl(ADD_IMG_FLAG);
                    mList.add(entity);
                }
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    private class ViewHodler {
        private ImageView imageView;
        private ImageView closeIv;
    }

    public void setShowCloseIcon(boolean showCloseIcon) {
        this.isShowCloseIcon = showCloseIcon;
    }
}
