package com.jg.jsonform.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jg.jsonform.R;

/**
 * 输入表单格式
 * <p>
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/11/9 10:47
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FormEditView extends LinearLayout {

    private TextView mLabelView, mRequired, mLine, mEmptyTextView;
    private EditText mContentEditText;
    private ImageView mDrawableRight;

    private OnFormClickListener listener;

    public FormEditView(Context context) {
        this(context, null);
    }

    public FormEditView(Context context, AttributeSet attri) {
        super(context, attri);
        initView();
        initAttributeset(context, attri, 0);
    }

    /**
     * 初始化View
     * <p>
     * author: hezhiWu
     * created at 2017/11/9 11:13
     */
    private void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.form_editview_layout, null);

        mLabelView = (TextView) rootView.findViewById(R.id.Form_EditView_label);
        mRequired = (TextView) rootView.findViewById(R.id.Form_EditView_Required);
        mLine = (TextView) rootView.findViewById(R.id.Form_EditView_Line);
        mContentEditText = (EditText) rootView.findViewById(R.id.Form_EditView_Content);
        mDrawableRight = (ImageView) rootView.findViewById(R.id.Form_EditView_Drawable_right);
        mEmptyTextView = (TextView) rootView.findViewById(R.id.Form_Empty_TextView);

        mDrawableRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickFormRightDrawable(mContentEditText);
                } else {
                    Toast.makeText(getContext(), "Please init onClickListener", Toast.LENGTH_LONG).show();
                }
            }
        });

        mEmptyTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickFormRightDrawable(mContentEditText);
                } else {
                    Toast.makeText(getContext(), "Please init onClickListener", Toast.LENGTH_LONG).show();
                }
            }
        });

        addView(rootView);
    }

    /**
     * 初始化属性
     * <p>
     * author: hezhiWu
     * created at 2017/11/9 11:13
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    private void initAttributeset(Context context, AttributeSet attrs, int defStyle) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FormEditView, defStyle, 0);

        String label = typedArray.getString(R.styleable.FormEditView_FormLabel);
        setFormLabel(label);

        String text = typedArray.getString(R.styleable.FormEditView_FormText);
        setFormText(text);

        String hint = typedArray.getString(R.styleable.FormEditView_FormHint);
        setFormHint(hint);

        boolean required = typedArray.getBoolean(R.styleable.FormEditView_FormRequired, false);
        setFormRequired(required);

        boolean enable = typedArray.getBoolean(R.styleable.FormEditView_FormEnabled, true);
        setFormEnabled(enable);

        int type = typedArray.getInt(R.styleable.FormEditView_FormInputType, 0x00020001);
        setFormInputType(type);

        boolean lastItem = typedArray.getBoolean(R.styleable.FormEditView_FormLastItem, false);
        setFormLastItem(lastItem);

        Drawable drawable = typedArray.getDrawable(R.styleable.FormEditView_FormRightDrawable);
        setFormRightDrawable(drawable);
    }

    /**
     * 设置Label
     * <p>
     * author: hezhiWu
     * created at 2017/11/9 15:59
     *
     * @param label
     */
    public FormEditView setFormLabel(String label) {
        if (!TextUtils.isEmpty(label))
            mLabelView.setText(label);

        return this;
    }

    /**
     * 设置Label
     * <p>
     * author: hezhiWu
     * created at 2017/11/9 15:59
     *
     * @param resId
     */
    public FormEditView setFormLable(int resId) {
        if (resId > 0)
            mLabelView.setText(resId);

        return this;
    }

    /**
     * 设置内容
     * <p>
     * author: hezhiWu
     * created at 2017/11/9 15:59
     *
     * @param text
     */
    public FormEditView setFormText(String text) {
        if (!TextUtils.isEmpty(text))
            mContentEditText.setText(text);

        if (!TextUtils.isEmpty(mContentEditText.getText().toString().trim()) || mContentEditText.getVisibility() == View.GONE) {
            mContentEditText.setVisibility(View.VISIBLE);
            mEmptyTextView.setVisibility(View.GONE);

        }

        return this;
    }

    /**
     * 设置内容
     * <p>
     * author: hezhiWu
     * created at 2017/11/9 16:02
     *
     * @param resId
     */
    public FormEditView setFormText(int resId) {
        if (resId > 0)
            mContentEditText.setText(resId);

        if (!TextUtils.isEmpty(mContentEditText.getText().toString().trim()) || mContentEditText.getVisibility() == View.GONE) {
            mContentEditText.setVisibility(View.VISIBLE);
            mEmptyTextView.setVisibility(View.GONE);

        }

        return this;
    }

    /**
     * 设置内容
     * <p>
     * author: hezhiWu
     * created at 2017/11/9 15:59
     *
     * @param hint
     */
    public FormEditView setFormHint(String hint) {
        if (!TextUtils.isEmpty(hint))
            mContentEditText.setHint(hint);

        return this;
    }

    /**
     * 设置提示信息
     * <p>
     * author: hezhiWu
     * created at 2017/11/9 15:59
     *
     * @param resId
     */
    public FormEditView setFormHint(int resId) {
        if (resId > 0)
            mContentEditText.setHint(resId);

        return this;
    }

    /**
     * 设置提示信息
     * <p>
     * author: hezhiWu
     * created at 2017/11/9 15:59
     *
     * @param required
     */
    public FormEditView setFormRequired(boolean required) {
        if (required) {
            mRequired.setVisibility(View.VISIBLE);
        } else {
            mRequired.setVisibility(View.INVISIBLE);
        }

        return this;
    }

    /**
     * 设置编辑状态
     * <p>
     * author: hezhiWu
     * created at 2017/11/9 16:00
     *
     * @param enabled
     */
    public FormEditView setFormEnabled(boolean enabled) {
        mContentEditText.setEnabled(enabled);

        return this;
    }

    /**
     * 设置输入类型
     * <p>
     * author: hezhiWu
     * created at 2017/11/9 16:00
     *
     * @param type
     */
    public FormEditView setFormInputType(int type) {
        mContentEditText.setInputType(type);

        return this;
    }

    /**
     * 设置是否最后一个Item
     * <p>
     * author: hezhiWu
     * created at 2017/11/9 16:00
     *
     * @param lastItem
     */
    public FormEditView setFormLastItem(boolean lastItem) {
        if (lastItem) {
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 2);
            mLine.setLayoutParams(params);
        }

        return this;
    }

    /**
     * 设置右边图标
     * <p>
     * author: hezhiWu
     * created at 2017/11/9 16:00
     *
     * @param drawable
     */
    public FormEditView setFormRightDrawable(Drawable drawable) {
        if (drawable != null) {
            mDrawableRight.setImageDrawable(drawable);
            mDrawableRight.setVisibility(View.VISIBLE);
            mEmptyTextView.setVisibility(View.VISIBLE);
            mContentEditText.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 设置左边图标
     * <p>
     * author: hezhiWu
     * created at 2017/11/9 16:01
     *
     * @param resId
     */
    public FormEditView setFormRightDrawable(int resId) {
        if (resId > 0) {
            mDrawableRight.setImageResource(resId);
            mDrawableRight.setVisibility(View.VISIBLE);
            mEmptyTextView.setVisibility(View.VISIBLE);
            mContentEditText.setVisibility(View.GONE);
        }

        return this;
    }

    /**
     * 设置点击事情
     * <p>
     * author: hezhiWu
     * created at 2017/11/9 17:02
     */
    public FormEditView setFormClickListener(OnFormClickListener listener) {
        this.listener = listener;

        return this;
    }

    /**
     * 返回输入内容
     * <p>
     * author: hezhiWu
     * created at 2017/11/9 16:05
     */
    public String getFormText() {
        return mContentEditText.getText().toString().trim();
    }

    /**
     * 返回Label
     * <p>
     * author: hezhiWu
     * created at 2017/11/9 16:05
     */
    public String getFormLabel() {
        return mLabelView.getText().toString().trim();
    }

    /**
     * 返回输入框
     * <p>
     * author: hezhiWu
     * created at 2017/11/9 17:06
     */
    public EditText getFormEditText() {
        return mContentEditText;
    }


    public interface OnFormClickListener {
        void onClickFormRightDrawable(View view);
    }
}
