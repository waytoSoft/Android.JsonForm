package com.jg.jsonform.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jg.jsonform.R;
import com.jg.jsonform.entity.EditEntity;
import com.jg.jsonform.entity.FormEntity;
import com.jg.jsonform.entity.SelectionBoxEntity;
import com.jg.jsonform.form.FormConstacts;
import com.jg.jsonform.utils.IStringUtils;

import org.json.JSONObject;

import java.util.List;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/11/10 16:25
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FormSelectionBoxView extends LinearLayout {
    private final int DEFULT_NUM_COLUMNS = 2;

    private TextView mLabel, mRequired, mLine;
    private SelfAdapterGridView mGridView;
    private LinearLayout mContainer;

    private int defalutValue = -1;

    private boolean enabled = true;
    private FormEntity formEntity;

    private GridViewAdapter adapter;

    private OnCheckedChangeListener listener;

    public FormSelectionBoxView(Context context) {
        this(context, null);
    }

    public FormSelectionBoxView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FormSelectionBoxView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

        initAttributeset(context, attrs, defStyleAttr);
    }

    /**
     * 初始View
     * <p>
     * author: hezhiWu
     * created at 2017/11/10 16:26
     */
    private void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.form_selectionbox_layout, null);

        mLabel = (TextView) rootView.findViewById(R.id.Form_selectionBox_label);
        mRequired = (TextView) rootView.findViewById(R.id.Form_SelectionBox_Required);
        mLine = (TextView) rootView.findViewById(R.id.Form_selectionBox_Line);
        mGridView = (SelfAdapterGridView) rootView.findViewById(R.id.Form_selectionBox_GridView);
        mContainer = (LinearLayout) rootView.findViewById(R.id.Form_selectionBox_Container);

        addView(rootView);
    }

    /**
     * 初始化属性
     * <p>
     * author: hezhiWu
     * created at 2017/11/10 16:33
     */
    private void initAttributeset(Context context, AttributeSet attrs, int defStyle) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FormSelectionBoxView, defStyle, 0);

        String lable = typedArray.getString(R.styleable.FormSelectionBoxView_FormSelectionBoxLabel);
        setFormLabel(lable);

        boolean required = typedArray.getBoolean(R.styleable.FormSelectionBoxView_FormSelectionBoxRequired, false);
        setFormRequired(required);

        boolean lastItem = typedArray.getBoolean(R.styleable.FormSelectionBoxView_FormSelectionBoxLastItem, false);
        setFormLastItem(lastItem);

        int numColumns = typedArray.getInt(R.styleable.FormSelectionBoxView_FormSelectionBoxNumColumns, DEFULT_NUM_COLUMNS);
        initGridView(numColumns);
    }

    /**
     * 初始化GridView
     * <p>
     * author: hezhiWu
     * created at 2017/11/1 14:13
     */
    private void initGridView(int numColumns) {
        adapter = new GridViewAdapter(getContext());

        mGridView.setNumColumns(numColumns);
        mGridView.setAdapter(adapter);
    }

    /**
     * 设置Label
     * <p>
     * author: hezhiWu
     * created at 2017/11/10 14:59
     *
     * @param label
     */
    public FormSelectionBoxView setFormLabel(String label) {
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
    public FormSelectionBoxView setFormLabel(int resId) {
        if (resId > 0)
            mLabel.setText(resId);

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
    public FormSelectionBoxView setFormRequired(boolean required) {
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
    public FormSelectionBoxView setFormLastItem(boolean lastItem) {
        if (lastItem) {
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 2);
            mLine.setLayoutParams(params);
        }

        return this;
    }

    /**
     * 添加数据
     * <p>
     * author: hezhiWu
     * created at 2017/10/31 14:26
     */
    public FormSelectionBoxView setSelectionBoxContent(FormEntity entity) {
        this.formEntity = entity;
        defalutValue = IStringUtils.toInt(entity.getValue());
        adapter.appendToList(entity.getSelectionBox());
        return this;
    }

    /**
     * 设置监听器
     * <p>
     * author: hezhiWu
     * created at 2017/10/31 16:10
     */
    public FormSelectionBoxView setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.listener = listener;
        return this;
    }
    
    /**
     *设置值
     * 
     *author: hezhiWu
     *created at 2017/11/18 下午11:45
     */
    public FormSelectionBoxView setValue(int value){
        defalutValue=value;
        adapter.notifyDataSetChanged();

        enabled=false;
        return this;
    }

    /**
     * 返回选择RadioButton对应的Value
     * <p>
     * author: hezhiWu
     * created at 2017/11/10 17:14
     */
    public int getValue() {
        return defalutValue;
    }

    /**
     * 返回Label
     * <p>
     * author: hezhiWu
     * created at 2017/11/10 17:15
     */
    public String getLabel() {
        return mLabel.getText().toString().trim();
    }

    /**
     * 返回容器
     * <p>
     * author: hezhiWu
     * created at 2017/11/10 17:15
     */
    public LinearLayout getContainerView() {
        return mContainer;
    }

    /**
     * 渲染View
     * <p>
     * author: hezhiWu
     * created at 2017/11/1 15:58
     */
    public void renderingView(String formJson, boolean enabled) {
        this.enabled = enabled;
        try {
            JSONObject jsonObject = new JSONObject(formJson);
            defalutValue = Integer.parseInt(jsonObject.getString("status"));
            adapter.notifyDataSetChanged();
            init();

            FormEntity formEntity = new Gson().fromJson(formJson, FormEntity.class);

            for (int i = 0; i < mContainer.getChildCount(); i++) {
                View view = mContainer.getChildAt(i);
                int type = IStringUtils.toInt(view.getTag(R.id.form_type).toString());

                if (type == FormConstacts.FormType.Edit.getValue()) {

                    FormEditView writeView = (FormEditView) view;

                    renderingEditViewData(writeView, formEntity, formEntity.getValue(), enabled);

                } else if (type == FormConstacts.FormType.Text.getValue()) {

                    FormTextView selectView = (FormTextView) view;

                    renderingTextViewData(selectView, formEntity, formEntity.getValue(), enabled);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化
     * <p>
     * author: hezhiWu
     * created at 2017/11/10 17:16
     */
    public void init() {
        if (formEntity == null)
            return;

        List<SelectionBoxEntity> list = formEntity.getSelectionBox();
        if (list == null || list.size() <= 0)
            return;

        for (SelectionBoxEntity entity : list) {
            if (defalutValue == entity.getKey() && list != null) {
                mContainer.removeAllViews();
                listener.onCheckedChange(mContainer, entity.getForm());
            }
        }
    }

    private void renderingEditViewData(FormEditView writeView, FormEntity formEntity, String value, boolean enabled) {
         /*判断处理原始表单配制编辑状态*/
        EditEntity editEntity = formEntity.getEdit();
        if (editEntity != null && !editEntity.isEnabled()) {
            if (enabled) {
                writeView.setFormEnabled(editEntity.isEnabled());
            }
        } else {
            writeView.setFormEnabled(enabled);
        }

        if (!enabled) {
            writeView.setFormRightVisibility(View.GONE);
        }

        if (enabled) {
            writeView.setFormRequired(formEntity.isRequired());
        } else {
            writeView.setFormRequired(false);
        }

        if (!TextUtils.isEmpty(value) && !"null".equals(value)) {
            writeView.setFormText(value);
        } else {
            writeView.setFormHint(R.string.nothing);
        }
    }


    private void renderingTextViewData(FormTextView selectView, FormEntity formEntity, String value, boolean enabled) {
        if (!enabled) {
            selectView.setFormRightDrawableVisibility(View.GONE);
        }

        if (enabled) {
            selectView.setFormRequired(formEntity.isRequired());
        } else {
            selectView.setFormRequired(false);
        }

        selectView.setFormEnable(enabled);


        if (!TextUtils.isEmpty(value) && !"null".equals(value)) {
            selectView.setFormText(value);
        } else {
            selectView.setFormHint(R.string.nothing);
        }
    }

    /**
     * 适配器
     * <p>
     * author: hezhiWu <hezhi.woo@gmail.com>
     * version: V1.0
     * created at 2017/11/10 17:11
     * <p>
     * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
     */
    class GridViewAdapter extends ArrayListAdapter<SelectionBoxEntity> {

        public GridViewAdapter(Context context) {
            super(context);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(mContext).inflate(R.layout.form_selectionbox_item, null);

            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.SelectionBox_Item_Radio);
            viewHolder.layout = (LinearLayout) convertView.findViewById(R.id.SelectionBox_Item_Layout);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.SelectionBox_Item_Name);

            viewHolder.textView.setText(mList.get(position).getName());

            if (mList.get(position).getKey() == defalutValue) {
                viewHolder.checkBox.setChecked(true);
                mList.get(position).setCheck(true);
            } else {
                viewHolder.checkBox.setChecked(false);
                mList.get(position).setCheck(false);
            }

            viewHolder.layout.setEnabled(enabled);
            viewHolder.checkBox.setEnabled(enabled);

            viewHolder.checkBox.setOnCheckedChangeListener(null);
            viewHolder.checkBox.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    defalutValue = mList.get(position).getKey();
                    notifyDataSetChanged();

                    if (listener != null) {
                        listener.onCheckedChange(mContainer, mList.get(position).getForm());
                    }
                }
            });
            viewHolder.layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    defalutValue = mList.get(position).getKey();
                    notifyDataSetChanged();

                    if (listener != null) {
                        listener.onCheckedChange(mContainer, mList.get(position).getForm());
                    }
                }
            });

            return convertView;
        }

        class ViewHolder {
            CheckBox checkBox;
            LinearLayout layout;
            TextView textView;
        }
    }

    public interface OnCheckedChangeListener {
        void onCheckedChange(LinearLayout layout, List<FormEntity> entities);
    }

}
