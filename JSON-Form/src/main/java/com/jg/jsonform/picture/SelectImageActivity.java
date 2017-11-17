package com.jg.jsonform.picture;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jg.jsonform.R;
import com.jg.jsonform.picture.model.SelectImageContract;
import com.jg.jsonform.picture.model.SelectImagePresent;
import com.jg.jsonform.picture.model.data.PictureEntity;
import com.jg.jsonform.view.recyclerview.PullToRefreshRecyclerView;
import com.jg.jsonform.view.recyclerview.PullToRefreshRecyclerViewListener;
import com.jg.jsonform.view.recyclerview.RecyclerViewBaseAdapter;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/10/13 11:29
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class SelectImageActivity extends AppCompatActivity implements PullToRefreshRecyclerViewListener, SelectImageContract.SelectImageView, RecyclerViewBaseAdapter.OnRecyclerViewItemClickListener, SelectImageAdapter.OnSelectPictureListener {
    /*拍照Key*/
    public final static String KEY_TAKE_PICTURE = "tabk_picture";
    private final int TACK_PICTURE_VALUE = 490;
    public static final int SELECT_PICTURE_RESULT_CODE = 491;

    public final int DEFULT_PAGESIZE = 200;
    public int pageIndex = 0;

    private TextView mTotalTextView;
    private Toolbar mToolbar;

    private ActionBar mActionBar;

    private PullToRefreshRecyclerView refresRecyclerView;

    private SelectImageAdapter adapter;

    private SelectImagePresent selectImagePresent;

    private String mImagePath;

    private List<PictureEntity> selectImgs = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);

        initView();

        selectImagePresent = new SelectImagePresent(this, this);

        initRecycleView();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 390);
        } else {
            refresRecyclerView.startDownRefresh();
        }
    }

    /**
     * 初始化View
     * <p>
     * author: hezhiWu
     * created at 2017/10/16 19:16
     */
    private void initView() {
        refresRecyclerView = (PullToRefreshRecyclerView) findViewById(R.id.RecycleView);
        mTotalTextView = (TextView) findViewById(R.id.Title_total);
        mToolbar = (Toolbar) findViewById(R.id.Toolbar);


        mTotalTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("imgs", (Serializable) selectImgs);
                intent.putExtras(bundle);
                setResult(SELECT_PICTURE_RESULT_CODE, intent);
                SelectImageActivity.this.finish();
            }
        });

        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 初始化RecycleView
     * <p>
     * author: hezhiWu
     * created at 2017/10/16 19:16
     */
    private void initRecycleView() {
        adapter = new SelectImageAdapter(this);
        adapter.setSelectPictureListener(this);

        refresRecyclerView.setRecyclerViewAdapter(adapter);
        refresRecyclerView.setPullToRefreshRecyclerViewListener(this);
        refresRecyclerView.setOnItemClickListener(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 390) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                refresRecyclerView.startDownRefresh();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TACK_PICTURE_VALUE:/*拍照回调*/
                    PictureEntity entity = new PictureEntity();
                    entity.setUrl(mImagePath);
                    adapter.addItem(1, entity);
                    break;
            }
        }
    }

    @Override
    public void onDownRefresh() {
        pageIndex = 0;
        selectImagePresent.queryAlbum();
    }

    @Override
    public void onPullRefresh() {
        pageIndex++;
        selectImagePresent.queryAlbum();
    }

    @Override
    public void onItemClick(View view, Object data, int position) {
        PictureEntity path = (PictureEntity) data;
        if (path==null)
            return;

        if (KEY_TAKE_PICTURE.equals(path.getUrl())) {
            tackPictureAction();
        }
    }

    @Override
    public void onSelectePicture(PictureEntity entity, boolean isChecked) {
        if (isChecked) {
            selectImgs.add(entity);
        } else {
            selectImgs.remove(entity);
        }

        if (selectImgs.size() > 0)
            mTotalTextView.setText("确定[" + selectImgs.size() + "]");
        else
            mTotalTextView.setText("");
    }

    @Override
    public int getPageIndex() {
        return pageIndex;
    }

    @Override
    public int getPageSize() {
        return DEFULT_PAGESIZE;
    }

    @Override
    public void onQueryAlbumSuccess(List<PictureEntity> imgs) {
        adapter.clearList();

        if (imgs != null) {
            PictureEntity entity = new PictureEntity();
            entity.setUrl(KEY_TAKE_PICTURE);
            imgs.add(0, entity);
        }

        adapter.addItems(imgs);
    }

    @Override
    public void onQueryAlbumMoreSuccess(List<PictureEntity> imgs) {
        adapter.addItems(adapter.getItemCount() - 1, imgs);
    }

    @Override
    public void onQueryAlbumFailure(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onQueryEmpty() {
        refresRecyclerView.showEmptyTextView("暂无图片");
    }

    @Override
    public void onCloseDownRefresh() {
        refresRecyclerView.onCloseDownRefresh();
    }

    @Override
    public void onClosePullRefresh() {
        refresRecyclerView.onCloseLoadMore();
    }

    @Override
    public void onLoadMoreComplete() {
        refresRecyclerView.onLoadMoreComplete();
    }

    /**
     * 拍照
     * <p>
     * author: hezhiWu
     * created at 2017/10/16 11:28
     */
    private void tackPictureAction() {
        if (getSDFreeSize() < 1) {
            Toast.makeText(this, "SD卡容量已满，请先清理SD卡没用的文件", Toast.LENGTH_LONG).show();
            return;
        }
        mImagePath = getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath() + "/IMG_" + System.currentTimeMillis() + ".png";
        File file = new File(mImagePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, TACK_PICTURE_VALUE);
    }

    /**
     * SD卡剩余容量
     * <p>
     * author: hezhiWu
     * created at 2017/3/22 17:27
     */
    public static long getSDFreeSize() {
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        //返回SD卡空闲大小
        //return freeBlocks * blockSize;  //单位Byte
        //return (freeBlocks * blockSize)/1024;   //单位KB
        return (freeBlocks * blockSize) / 1024 / 1024; //单位MB
    }
}
