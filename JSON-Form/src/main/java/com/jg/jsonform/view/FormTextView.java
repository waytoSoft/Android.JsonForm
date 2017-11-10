package com.jg.jsonform.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jg.jsonform.R;

/**
 * 选择表单
 * <p>
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/11/10 14:47
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FormTextView extends LinearLayout {
    private TextView mLabel, mRequired, mLine, mContentTextView;
    private ImageView mDrawableRight;

    private OnFormClickListener listener;

    public FormTextView(Context context) {
        this(context, null);
    }

    public FormTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FormTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

        initAttributeset(context, attrs, defStyleAttr);
    }

    /**
     * 初始化View
     * <p>
     * author: hezhiWu
     * created at 2017/11/10 15:41
     */
    private void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.form_textview_layout, null);

        mLabel = (TextView) rootView.findViewById(R.id.Form_TextView_label);
        mRequired = (TextView) rootView.findViewById(R.id.Form_TextView_Required);
        mLine = (TextView) rootView.findViewById(R.id.Form_TextView_Line);
        mContentTextView = (TextView) rootView.findViewById(R.id.Form_TextView_Content);
        mDrawableRight = (ImageView) rootView.findViewById(R.id.Form_TextView_Drawable_right);

        mContentTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener == null) {
                    Toast.makeText(getContext(), "Please init Listener", Toast.LENGTH_LONG).show();
                } else {
                    listener.onClickFormRightDrawable(mContentTextView);
                }
            }
        });

        mDrawableRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener == null) {
                    Toast.makeText(getContext(), "Please init Listener", Toast.LENGTH_LONG).show();
                } else {
                    listener.onClickFormRightDrawable(mContentTextView);
                }
            }
        });

        addView(rootView);
    }

    /**
     * 初始化属性
     * <p>
     * author: hezhiWu
     * created at 2017/11/10 15:48
     */
    private void initAttributeset(Context context, AttributeSet attrs, int defStyle) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FormTextView, defStyle, 0);

        String label = typedArray.getString(R.styleable.FormTextView_FormTextLabel);
        setFormLabel(label);

        String text = typedArray.getString(R.styleable.FormTextView_FormTextText);
        setFormText(text);

        String hint = typedArray.getString(R.styleable.FormTextView_FormTextHint);
        setFormHint(hint);

        boolean required = typedArray.getBoolean(R.styleable.FormTextView_FormTextRequired, false);
        setFormRequired(required);

        boolean lastItem = typedArray.getBoolean(R.styleable.FormTextView_FormTextLastItem, false);
        setFormLastItem(lastItem);

        Drawable drawable = typedArray.getDrawable(R.styleable.FormTextView_FormTextRightDrawable);
        setFormRightDrawable(drawable);
    }

    /**
     * 设置Label
     * <p>
     * author: hezhiWu
     * created at 2017/11/10 14:59
     *
     * @param label
     */
    public FormTextView setFormLabel(String label) {
        if (!TextUtils.isEmpty(label))
            mLabel.setText(label);

        return this;
    }

    /**
     * 设置Label
     * <p>
     * author: hezhiWu
     * created at 2017/11/10 14:59
     *
     * @param resId
     */
    public FormTextView setFormLabel(int resId) {
        if (resId > 0)
            mLabel.setText(resId);

        return this;
    }

    /**
     * 设置内容
     * <p>
     * author: hezhiWu
     * created at 2017/11/10 15:01
     *
     * @param text
     */
    public FormTextView setFormText(String text) {
        if (TextUtils.isEmpty(text))
            mContentTextView.setText(text);

        return this;
    }

    /**
     * 设置内容
     * <p>
     * author: hezhiWu
     * created at 2017/11/10 15:01
     *
     * @param resId
     */
    public FormTextView setFormText(int resId) {
        if (resId > 0)
            mContentTextView.setText(resId);

        return this;
    }

    /**
     * 设置提示
     * <p>
     * author: hezhiWu
     * created at 2017/11/10 15:02
     *
     * @param hint
     */
    public FormTextView setFormHint(String hint) {
        if (!TextUtils.isEmpty(hint))
            mContentTextView.setHint(hint);

        return this;
    }

    /**
     * 设置提示
     * <p>
     * author: hezhiWu
     * created at 2017/11/10 15:38
     *
     * @param resId
     */
    public FormTextView setFormHint(int resId) {
        if (resId > 0)
            mContentTextView.setHint(resId);

        return this;
    }

    /**
     * 设置是否必填项
     * <p>
     * author: hezhiWu
     * created at 2017/11/9 15:59
     *
     * @param required
     */
    public FormTextView setFormRequired(boolean required) {
        if (required) {
            mRequired.setVisibility(View.VISIBLE);
        } else {
            mRequired.setVisibility(View.INVISIBLE);
        }

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
    public FormTextView setFormLastItem(boolean lastItem) {
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
    public FormTextView setFormRightDrawable(Drawable drawable) {
        if (drawable != null) {
            mDrawableRight.setImageDrawable(drawable);
            mDrawableRight.setVisibility(View.VISIBLE);
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
    public FormTextView setFormRightDrawable(int resId) {
        if (resId > 0) {
            mDrawableRight.setImageResource(resId);
            mDrawableRight.setVisibility(View.VISIBLE);
        }

        return this;
    }

    /**
     * 设置监听器
     * <p>
     * author: hezhiWu
     * created at 2017/11/10 16:12
     */
    public FormTextView setFormClickListener(OnFormClickListener listener) {
        this.listener = listener;

        return this;
    }

    /**
     * 返回Label
     * <p>
     * author: hezhiWu
     * created at 2017/11/10 16:13
     */
    public String getFormLabel() {
        return mLabel.getText().toString().trim();
    }

    /**
     * 返回内容
     * <p>
     * author: hezhiWu
     * created at 2017/11/10 16:13
     */
    public String getFormText() {
        return mContentTextView.getText().toString().trim();
    }

    /**
     * 设置TextView
     * <p>
     * author: hezhiWu
     * created at 2017/11/10 16:14
     */
    public TextView getFormTextView() {
        return mContentTextView;
    }

    public interface OnFormClickListener {
        void onClickFormRightDrawable(View view);
    }

}
