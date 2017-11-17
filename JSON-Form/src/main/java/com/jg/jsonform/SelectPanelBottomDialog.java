package com.jg.jsonform;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TextView;

import com.jg.jsonform.view.ArrayListAdapter;

import java.util.List;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/11/16 17:14
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class SelectPanelBottomDialog {

    public Activity mActivity;
    private boolean multieselect = false;
    private String[] array;
    private String mTitle;
    private TextView textView;
    private GridView mGridView;

    private String selectContent;
    private String[] selectContents;

    private BottomSheetDialog bottomSheetDialog;

    public SelectPanelBottomDialog(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * 设置多选
     * <p>
     * author: hezhiWu
     * created at 2017/11/17 10:41
     */
    public SelectPanelBottomDialog setMultieselect(boolean multieselect) {
        this.multieselect = multieselect;
        return this;
    }

    /**
     * 设置显示内容
     * <p>
     * author: hezhiWu
     * created at 2017/11/17 10:40
     */
    public SelectPanelBottomDialog setContent(String[] array) {
        this.array = array;
        return this;
    }

    /**
     * 设置显示内容
     * <p>
     * author: hezhiWu
     * created at 2017/11/17 10:40
     */
    public SelectPanelBottomDialog setContent(List<String> list) {
        array = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return this;
    }

    /**
     * 设置显示的TextView
     * <p>
     * author: hezhiWu
     * created at 2017/11/17 10:40
     */
    public SelectPanelBottomDialog setTextView(TextView textView) {
        this.textView = textView;
        return this;
    }

    /**
     * 设置标题
     * <p>
     * author: hezhiWu
     * created at 2017/11/17 10:40
     */
    public SelectPanelBottomDialog setTitle(String title) {
        this.mTitle = title;
        return this;
    }

    /**
     * 设置标题
     * <p>
     * author: hezhiWu
     * created at 2017/11/17 10:40
     */
    public SelectPanelBottomDialog setTitle(int resId) {
        this.mTitle = mActivity.getString(resId);
        return this;
    }

    /**
     * showDialog
     * <p>
     * author: hezhiWu
     * created at 2017/11/17 10:41
     */
    public void show() {
        View rootView = LayoutInflater.from(mActivity).inflate(R.layout.select_panel_bottom_layout, null);
        TextView title = (TextView) rootView.findViewById(R.id.Panel_Title);
        mGridView = (GridView) rootView.findViewById(R.id.Panel_Grid);

        initGridView();

        if (!TextUtils.isEmpty(mTitle))
            title.setText(this.mTitle);

        rootView.findViewById(R.id.Panel_Close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        rootView.findViewById(R.id.Panel_Check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!multieselect) {
                    if (textView != null) {
                        textView.setText(selectContent);
                    }
                }
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog = new BottomSheetDialog(mActivity);
        bottomSheetDialog.setContentView(rootView);
        bottomSheetDialog.show();
    }

    private void initGridView() {
        GridViewAdapter adapter = new GridViewAdapter(mActivity);
        mGridView.setAdapter(adapter);

        adapter.appendToList(array);
    }

    private int selectPosition = -1;

    class GridViewAdapter extends ArrayListAdapter<String> {
        public GridViewAdapter(Context context) {
            super(context);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_panel_layout, null);

            final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.Panel_CheckBox);
            checkBox.setText(mList.get(position));
            if (selectPosition == position) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }

            checkBox.setOnCheckedChangeListener(null);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!multieselect) {
                        selectPosition = position;
                        selectContent = mList.get(position);
                        notifyDataSetChanged();
                    } else {
                        //TODO 多选逻辑处理
                    }
                }
            });

            return convertView;
        }
    }
}
