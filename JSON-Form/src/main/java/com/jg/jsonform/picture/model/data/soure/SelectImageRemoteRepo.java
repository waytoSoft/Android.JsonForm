package com.jg.jsonform.picture.model.data.soure;

import android.database.Cursor;
import android.media.ExifInterface;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.jg.jsonform.picture.model.data.PictureEntity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/10/13 11:57
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class SelectImageRemoteRepo implements SelectImageDataSoure {

    private static SelectImageRemoteRepo remoteRepo;

    public static SelectImageRemoteRepo newInstance() {
        if (remoteRepo == null)
            remoteRepo = new SelectImageRemoteRepo();

        return remoteRepo;
    }

    @Override
    public void queryAlbum(final QueryAlbumCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                /*查询条件，可自行定义需要查询哪些文件夹下所有图片*/
//                String selection = MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME + " = 'Camera' ";
                String selection = null;

                String sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC limit " + callBack.getPageSize() + " offset " + callBack.getPageIndex() * callBack.getPageSize();

                Cursor mCursor = callBack.getContext().getContentResolver()
                        .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                null,
                                selection,
                                null,
                                sortOrder);

                List<PictureEntity> imgs = new ArrayList<>();
                while (mCursor.moveToNext()) {
                    try {
                        // 获取图片的路径
                        String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));

                        // 获取该图片的父路径名
                        String parentName = new File(path).getParentFile().getName();

                        /*只过滤相册*/
                        if (!TextUtils.isEmpty(path) && !TextUtils.isEmpty(parentName)) {

                            /*图片存储时间*/
                            ExifInterface exifInterface = new ExifInterface(path);
                            String date = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);

                            PictureEntity entity=new PictureEntity();
                            entity.setDate(date);
                            entity.setUrl(path);
                            entity.setSelecte(false);

                            imgs.add(entity);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        callBack.onQueryAlbumFailure("查询失败");
                    }
                }
                mCursor.close();
                callBack.onQueryAlbumSuccess(imgs);
            }
        }).start();
    }
}
