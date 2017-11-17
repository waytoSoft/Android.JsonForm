package com.jg.jsonform.form;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jg.jsonform.FormConstacts;
import com.jg.jsonform.R;
import com.jg.jsonform.SelectPanelBottomDialog;
import com.jg.jsonform.attachment.ImageAttachmentView;
import com.jg.jsonform.entity.EditEntity;
import com.jg.jsonform.entity.FormEntity;
import com.jg.jsonform.entity.SelectionBoxEntity;
import com.jg.jsonform.entity.StructureEntity;
import com.jg.jsonform.entity.TextEntity;
import com.jg.jsonform.picture.SelectImageActivity;
import com.jg.jsonform.picture.model.data.PictureEntity;
import com.jg.jsonform.utils.IDateTimeUtils;
import com.jg.jsonform.utils.IStringUtils;
import com.jg.jsonform.view.FormEditView;
import com.jg.jsonform.view.FormSelectionBoxView;
import com.jg.jsonform.view.FormTextView;
import com.jg.jsonform.view.ReboundScrollView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 表单渲染Fragment
 * <p>
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/10/25 10:32
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public abstract class FormRenderingFragment extends Fragment {

    ReboundScrollView mScrollView;
    private LinearLayout mContainerLayout, mBottomLayout, mMoreContainerLayout;
    private TextView mImageAttachmentViewTitle, mMoreExtendTextView, mEmptyImageTextView;

    private FormSelectionBoxView selectionBoxView;

    private ImageAttachmentView mImageAttachmentView;

    public boolean isImage;

    public boolean isImagRequired;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.form_rendering_layout, null);
        findViewById(rootView);

        renderingView();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 初始化控件
     * <p>
     * author: hezhiWu
     * created at 2017/10/25 13:59
     */
    private void findViewById(View view) {
        mScrollView = (ReboundScrollView) view.findViewById(R.id.form_rendering_scrollView);
        mContainerLayout = (LinearLayout) view.findViewById(R.id.form_rendering_container);
        mBottomLayout = (LinearLayout) view.findViewById(R.id.form_rendering_ButtomLayout);
        mMoreContainerLayout = (LinearLayout) view.findViewById(R.id.form_rendering_more_container);
        mMoreExtendTextView = (TextView) view.findViewById(R.id.form_rendering_more_extend_textView);
        mImageAttachmentView = (ImageAttachmentView) view.findViewById(R.id.form_rendering_ImageAttachmentView);
        mImageAttachmentViewTitle = (TextView) view.findViewById(R.id.form_rendering_ImageAttachmentView_Title);
        mMoreExtendTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (mMoreContainerLayout.getVisibility() == View.VISIBLE) {
                    mMoreContainerLayout.setVisibility(View.GONE);
                    mMoreExtendTextView.setText("查看更多");
                    mMoreExtendTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.mipmap.icon_open, 0);
                } else {
                    mMoreContainerLayout.setVisibility(View.VISIBLE);
                    mMoreExtendTextView.setText("收起");
                    mMoreExtendTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.mipmap.icon_close, 0);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mScrollView.smoothScrollTo(0, v.getTop());
                        }
                    });
                }
            }
        });

        /*添加底部View*/
        if (getBottomView() != null) {
            mBottomLayout.addView(getBottomView());
        }
    }

    /**
     * 渲染View
     * <p>
     * author: hezhiWu
     * created at 2017/10/25 13:55
     */
    private void renderingView() {
        String jsonForm = getJsonForm();
        if (TextUtils.isEmpty(jsonForm)) {
            Toast.makeText(getActivity(), "表单配制文件为空", Toast.LENGTH_LONG).show();
            return;
        }

        StructureEntity structureEntity = new Gson().fromJson(getJsonForm(), StructureEntity.class);
        if (structureEntity == null) {
            Toast.makeText(getActivity(), "表单配制文件有错", Toast.LENGTH_LONG).show();
            return;
        }

        /*初始化附件*/
        isImage = structureEntity.isImg();
        isImagRequired = structureEntity.isImgRequired();

        mImageAttachmentView.setVisibility(isImage ? View.VISIBLE : View.GONE);
        mImageAttachmentViewTitle.setVisibility(isImage ? View.VISIBLE : View.GONE);

        /*渲染默认表单*/
        List<FormEntity> formEntities = structureEntity.getForm();
        if (formEntities == null || formEntities.size() == 0) {
            Toast.makeText(getActivity(), "请配制表单文件", Toast.LENGTH_LONG).show();
            return;
        }

        matchingFormView(formEntities, mContainerLayout);

        /*折叠表单*/
        List<FormEntity> flodFormEntities = structureEntity.getFlodForm();
        if (flodFormEntities == null || formEntities.size() <= 0) {
            mMoreExtendTextView.setVisibility(View.GONE);
            mMoreContainerLayout.setVisibility(View.GONE);

        } else {
            mMoreExtendTextView.setVisibility(View.VISIBLE);
            mMoreContainerLayout.setVisibility(View.GONE);

            matchingFormView(flodFormEntities, mMoreContainerLayout);
        }

    }

    /**
     * 匹配表单View
     * <p>
     * author: hezhiWu
     * created at 2017/10/30 16:15
     *
     * @param formEntities 表单数据集
     * @param layout       是否折叠
     */
    private void matchingFormView(List<FormEntity> formEntities, LinearLayout layout) {
        for (int i = 0; i < formEntities.size(); i++) {
            if (formEntities.get(i).getType() == FormConstacts.FormType.Edit.getValue()) {

                if (i == formEntities.size() - 1) {
                    renderingEditView(formEntities.get(i), true, layout);
                } else {
                    renderingEditView(formEntities.get(i), false, layout);
                }

            } else if (formEntities.get(i).getType() == FormConstacts.FormType.Text.getValue()) {

                if (i == formEntities.size() - 1) {
                    renderingTextView(formEntities.get(i), true, layout);
                } else {
                    renderingTextView(formEntities.get(i), false, layout);
                }

            } else if (formEntities.get(i).getType() == FormConstacts.FormType.SelectionBox.getValue()) {

                if (i == formEntities.size() - 1) {
                    renderingSelectionBoxView(formEntities.get(i), true, layout);
                } else {
                    renderingSelectionBoxView(formEntities.get(i), false, layout);
                }

            }
        }
    }

    /**
     * 渲染输入表单
     * <p>
     * author: hezhiWu
     * created at 2017/10/25 14:21
     *
     * @param entity     表单数据
     * @param isLastItem 是否最后一个表单Item
     * @param layout     是否折叠页
     */
    private void renderingEditView(final FormEntity entity, boolean isLastItem, LinearLayout layout) {
        final FormEditView writeView = new FormEditView(getActivity());

        writeView.setFormLabel(entity.getLabel());
        writeView.setFormRequired(entity.isRequired());
        writeView.setFormLastItem(isLastItem);

        String value = entity.getValue();
        if (TextUtils.isEmpty(entity.getValue())) {
            writeView.setFormHint(entity.getHint());
        } else if (FormConstacts.DEFALUT_CURRENT_DATA_VALUE.equals(entity.getValue())) {
            writeView.setFormText(IDateTimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
        } else {
            writeView.setFormText(value);
        }

        EditEntity editEntity = entity.getEdit();
        if (editEntity != null) {
            /*设置左边图标*/
            String icon = editEntity.getIcon();
            if (!TextUtils.isEmpty(icon)) {
                int resId = getResources().getIdentifier(icon, "drawable", getActivity().getPackageName());
                writeView.setFormRightDrawable(resId);
                writeView.setFormClickListener(new FormEditView.OnFormClickListener() {
                    @Override
                    public void onClickFormRightDrawable(View view) {
                        onClickWriteItemViewListener(writeView, entity.getSubmitKey());
                    }
                });
            }

            /*编辑状态*/
            writeView.setFormEnabled(editEntity.isEnabled());

            /*设置输入类型*/
            int inputType = editEntity.getInputType();
            if (inputType == 0) {
                writeView.setFormInputType(0x00020001);
            } else {
                writeView.setFormInputType(inputType);
            }
        }

        /*设置Tag*/
        writeView.setTag(R.id.form_type, FormConstacts.FormType.Edit.getValue());
        writeView.setTag(new Gson().toJson(entity));

        layout.addView(writeView);
    }

    /**
     * 渲染输入表单
     * <p>
     * author: hezhiWu
     * created at 2017/10/25 14:21
     *
     * @param entity     表单数据
     * @param isLastItem 是否最后一个表单Item
     * @param layout     是否折叠页
     */
    private void renderingTextView(final FormEntity entity, boolean isLastItem, LinearLayout layout) {
        final FormTextView selectView = new FormTextView(getActivity());

        selectView.setFormLabel(entity.getLabel());
        selectView.setFormRequired(entity.isRequired());

        /*默认值*/
        String value = entity.getValue();
        if (TextUtils.isEmpty(entity.getValue())) {
            selectView.setFormHint(entity.getHint());
        } else {
            selectView.setFormText(value);
        }

       /*设置Tag*/
        selectView.setTag(R.id.form_type, FormConstacts.FormType.Text.getValue());
        selectView.setTag(new Gson().toJson(entity));

        final TextEntity textEntity = entity.getText();
        if (textEntity != null) {
            String icon = textEntity.getIcon();
            if (!TextUtils.isEmpty(icon)) {
                int resId = getResources().getIdentifier(icon, "drawable", getActivity().getPackageName());
                selectView.setFormRightDrawable(resId);
            }

            selectView.setFormClickListener(new FormTextView.OnFormClickListener() {
                @Override
                public void onClickFormRightDrawable(View view) {
                    if (textEntity.isSuperClick()) {
                        onClickSelectItemViewListener(selectView, entity.getSubmitKey());
                    } else {
                        new SelectPanelBottomDialog(getActivity())
                                .setMultieselect(false)
                                .setContent(textEntity.getKey())
                                .setTextView(selectView.getFormTextView())
                                .show();
//                        Toast.makeText(getActivity(),"Please SuperClick",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        layout.addView(selectView);
    }

    /**
     * 渲染状态选择表单
     * <p>
     * author: hezhiWu
     * created at 2017/10/31 11:30
     */
    private void renderingSelectionBoxView(FormEntity entity, boolean isLastItem, LinearLayout layout) {
        if (entity == null)
            return;

        selectionBoxView = new FormSelectionBoxView(getActivity());

        List<SelectionBoxEntity> list = entity.getSelectionBox();
        if (list == null || list.size() == 0)
            return;

        selectionBoxView.setFormLabel(entity.getLabel())
                .setFormRequired(entity.isRequired())
                .setSelectionBoxContent(entity)
                .setFormLastItem(isLastItem)
                .setOnCheckedChangeListener(new FormSelectionBoxView.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChange(LinearLayout layout, List<FormEntity> entities) {
                        layout.removeAllViews();
                        if (entities != null && entities.size() >= 0) {
                            matchingFormView(entities, layout);
                        }
                    }
                }).init();

        /*设置Tag*/
        selectionBoxView.setTag(R.id.form_type, FormConstacts.FormType.SelectionBox.getValue());
        selectionBoxView.setTag(new Gson().toJson(entity));

        layout.addView(selectionBoxView);
    }

    /**
     * 返回表单Json数据
     * <p>
     * author: hezhiWu
     * created at 2017/10/26 9:58
     */
    public abstract String getJsonForm();

    /**
     * 返回底部View
     * <p>
     * author: hezhiWu
     * created at 2017/11/1 9:32
     */
    public View getBottomView() {
        return null;
    }

    /**
     * 获取图片附件标题(默认标题：添加图片)
     * <p>
     * author: hezhiWu
     * created at 2017/10/25 14:50
     */
    public String getImageAttachmentViewTitle() {
        return getString(R.string.image_title);
    }

    /**
     * 渲染表单数据(默认不可编辑)
     * <p>
     * author: hezhiWu
     * created at 2017/10/25 15:04
     *
     * @param formJson 表单数据Json
     */
    public void renderingFormData(String formJson) {
        renderingFormData(formJson, false);
    }

    /**
     * 渲染表单数据
     * <p>
     * author: hezhiWu
     * created at 2017/10/25 15:04
     *
     * @param formJson 表单数据Json
     * @param enabled  编辑状态
     */
    public void renderingFormData(String formJson, boolean enabled) {
        if (enabled) {
            mBottomLayout.setVisibility(View.VISIBLE);
        } else {
            mBottomLayout.setVisibility(View.GONE);
        }

        if (mContainerLayout != null) {
            renderingFormDetailsData(formJson, enabled, false, mContainerLayout);
        }

        if (mMoreContainerLayout != null) {
            renderingFormDetailsData(formJson, enabled, true, mMoreContainerLayout);
        }

        if (selectionBoxView != null) {
            selectionBoxView.renderingView(formJson, enabled);
            renderingFormDetailsData(formJson, enabled, false, selectionBoxView.getContainerView());
        }

    }

    /**
     * 渲染表单详细数据
     * <p>
     * author: hezhiWu
     * created at 2017/10/30 11:56
     *
     * @param formJson 表单数据Json
     * @param enabled  编辑状态
     * @param isFlod   是否折叠页
     * @param layout   表单布局
     */
    private void renderingFormDetailsData(String formJson, boolean enabled, boolean isFlod, LinearLayout layout) {
        int count = layout.getChildCount();
        try {
            JSONObject jsonObject = new JSONObject(formJson);
            /*处理附件*/
            if (!isFlod) {
                String attachmentJson = jsonObject.getString("Attachments");
                List<String> attachments = new Gson().fromJson(attachmentJson, new TypeToken<ArrayList<String>>() {
                }.getType());

                renderingAttachment(attachments, enabled);
            }

            for (int i = 0; i < count; i++) {
                View view = layout.getChildAt(i);

                int type = IStringUtils.toInt(view.getTag(R.id.form_rendering_container).toString());
                FormEntity formEntity = new Gson().fromJson(view.getTag().toString(), FormEntity.class);

                if (formEntity == null) {
                    Toast.makeText(getActivity(), "参数有误", Toast.LENGTH_LONG).show();
                    return;
                }

                String key = formEntity.getMatchingKey();
                String value = jsonObject.getString(key);

                if (type == FormConstacts.FormType.Edit.getValue()) {

                    FormEditView writeView = (FormEditView) view;

                    renderingEditViewData(writeView, formEntity, value, enabled);

                } else if (type == FormConstacts.FormType.Text.getValue()) {

                    FormTextView selectView = (FormTextView) view;

                    renderingTextViewData(selectView, formEntity, value, enabled);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 渲染EditView数据
     * <p>
     * author: hezhiWu
     * created at 2017/10/26 22:09
     *
     * @param writeView
     * @param formEntity
     * @param value
     * @param enabled
     */
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

    /**
     * 渲染TextView数据
     * <p>
     * author: hezhiWu
     * created at 2017/10/26 22:10
     *
     * @param selectView
     * @param formEntity
     * @param value
     * @param enabled
     */
    private void renderingTextViewData(FormTextView selectView, FormEntity formEntity, String value, boolean enabled) {
        if (!enabled) {
            selectView.setFormRightDrawableVisibility(View.GONE);
        }

        if (enabled) {
            selectView.setFormRequired(formEntity.isRequired());
        } else {
            selectView.setFormRequired(false);
        }


        if (!TextUtils.isEmpty(value) && !"null".equals(value)) {
            selectView.setFormText(value);
        } else {
            selectView.setFormHint(R.string.nothing);
        }
    }

    /**
     * 渲染附件数据
     * <p>
     * author: hezhiWu
     * created at 2017/10/26 18:18
     *
     * @param attachments
     * @param enabled
     */
    private void renderingAttachment(List<String> attachments, boolean enabled) {
        //修改标题
        mImageAttachmentViewTitle.setText("图片信息");

        /*初始化图片文件*/
        if (isImage) {
            boolean isHasImage = false;
            for (String attachment : attachments) {
                if (attachment.contains(".png") || attachment.contains(".jpg")) {
                    isHasImage = true;
                    if (enabled) {
                        mImageAttachmentView.setImageList(attachment);
                    } else {
                        mImageAttachmentView.setShowImage(attachment);
                    }
                }
            }
            if (!isHasImage) {
                mEmptyImageTextView.setVisibility(View.VISIBLE);
                mImageAttachmentView.setVisibility(View.GONE);
            }
        } else {
            mImageAttachmentView.setVisibility(View.GONE);
            mImageAttachmentViewTitle.setVisibility(View.GONE);
        }
    }

    /**
     * 添加底部View
     * <p>
     * author: hezhiWu
     * created at 2017/10/25 14:57
     *
     * @param view
     */
    @Deprecated
    public void addFormBottomLayoutView(View view) {
        mBottomLayout.addView(view);
    }

    /**
     * 返回表单值
     * <p>
     * author: hezhiWu
     * created at 2017/11/1 9:58
     */
    public JSONObject getFormVale() {
        JSONObject jsonObject = new JSONObject();
        if (mContainerLayout != null) {
            jsonObject = getFormVale(mContainerLayout, jsonObject, true);
        }

        if (mMoreContainerLayout != null) {
            jsonObject = getFormVale(mMoreContainerLayout, jsonObject, false);
        }

        if (selectionBoxView != null) {
            jsonObject = getFormVale(selectionBoxView.getContainerView(), jsonObject, false);
        }

        return jsonObject;
    }

    /**
     * 通过布局获取表单值
     * <p>
     * author: hezhiWu
     * created at 2017/11/1 9:59
     *
     * @param layout
     * @param jsonObject
     * @param isMatchingAccessories
     */
    private JSONObject getFormVale(LinearLayout layout, JSONObject jsonObject, boolean isMatchingAccessories) {
        int count = layout.getChildCount();
        if (count <= 0) {
            return jsonObject;
        }

        for (int i = 0; i < count; i++) {
            View view = layout.getChildAt(i);
            String value = null;

            FormEntity formEntity = new Gson().fromJson(view.getTag().toString(), FormEntity.class);

            if (formEntity == null) {
                Toast.makeText(getActivity(), "参数有误", Toast.LENGTH_LONG).show();
                return null;
            }

            String key = formEntity.getSubmitKey();

            int type = IStringUtils.toInt(view.getTag(R.id.form_type).toString());

            /*获取EditView表单值*/
            if (type == FormConstacts.FormType.Edit.getValue()) {

                FormEditView writeView = (FormEditView) view;
                if (formEntity.isRequired()) {
                    if (TextUtils.isEmpty(writeView.getFormText())) {
                        Toast.makeText(getActivity(), formEntity.getLabel() + "不能为空", Toast.LENGTH_LONG).show();
                        return null;
                    }
                }

                value = writeView.getFormText();

                if (formEntity.getEdit() != null) {
                    String regular = formEntity.getEdit().getRegular();
                    if (!TextUtils.isEmpty(regular))
                        if (!IStringUtils.matchingRegular(value, regular)) {
                            Toast.makeText(getActivity(), formEntity.getLabel() + "格式不正确", Toast.LENGTH_LONG).show();
                            return null;
                        }
                }

                /*获取TextView表单值*/
            } else if (type == FormConstacts.FormType.Text.getValue()) {

                FormTextView selectView = (FormTextView) view;
                if (formEntity.isRequired()) {
                    if (TextUtils.isEmpty(selectView.getFormText())) {
                        Toast.makeText(getActivity(), formEntity.getLabel() + "不能为空", Toast.LENGTH_LONG).show();
                        return null;
                    }
                }

                value = selectView.getFormText();

                String[] array = formEntity.getText().getKey();
                int[] values = formEntity.getText().getValue();
                if (values != null && values.length > 0 && array != null && array.length > 0)
                    if (array != null && array.length > 0) {
                        for (int j = 0; j < array.length; j++) {
                            if (array[j].equals(value)) {
                                value = String.valueOf(values[j]);
                                break;
                            }
                        }
                    }

            } else if (type == FormConstacts.FormType.SelectionBox.getValue()) {

                FormSelectionBoxView selectionBoxView = (FormSelectionBoxView) view;
                if (selectionBoxView.getValue() == -1) {
                    Toast.makeText(getActivity(), "请选择" + selectionBoxView.getLabel(), Toast.LENGTH_LONG).show();
                    return null;
                }

                value = String.valueOf(selectionBoxView.getValue());
            }

            try {
                jsonObject.put(key, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        if (isMatchingAccessories) {
//            /*获取图片资源*/
//            String images = null;
//            if (isImg && isImgRequired) {
//                if (TextUtils.isEmpty(mImageAttachmentView.getImageStrs())) {
//                    WaytoProgressDialog.showPromptMessage(getContext(), R.mipmap.icon_tip, "图片不能为空");
//                    return jsonObject;
//                }
//                images = mImageAttachmentView.getImageStrs();
//            }
//
//            /*获取视频资源*/
//            String videoPaths = null;
//            if (isVideo && isVideoRequired) {
//                List<MediaObject> path = MediaBiz.getInstance().getmMediaObject();
//                if (path == null || path.size() == 0) {
//                    WaytoProgressDialog.showPromptMessage(getContext(), R.mipmap.icon_tip, "视频不能为空");
//                    return jsonObject;
//                }
//                StringBuffer stringBuffer = new StringBuffer();
//                for (MediaObject object : path) {
//                    stringBuffer.append(object.getObjectFilePath()).append(",");
//                }
//                videoPaths = stringBuffer.substring(0, stringBuffer.length() - 1);
//            }

//            try {
//                String attr = "";
//                if (!TextUtils.isEmpty(images)) {
//                    attr = images;
//                }
//                if (!TextUtils.isEmpty(videoPaths)) {
//                    if (!TextUtils.isEmpty(attr)) {
//                        attr = attr + "," + videoPaths;
//                    }
//                }
//                jsonObject.put("AttachmentMark", attr);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        return jsonObject;
    }

    /**
     * TextViewItem自定义点击事件，请在子类处理(注：子类需注掉super.onClickSelectItemViewListener)
     * <p>
     * author: hezhiWu
     * created at 2017/10/25 14:36
     *
     * @param selectView
     * @param key
     */
    public void onClickSelectItemViewListener(final FormTextView selectView, String key) {
        Toast.makeText(getActivity(), "请在子类重写点击处理事件", Toast.LENGTH_LONG).show();
    }

    /**
     * EditViewItem自定义点击事件，请在子类处理(注：子类需注掉super.onClickWriteItemViewListener)
     * <p>
     * author: hezhiWu
     * created at 2017/10/26 10:57
     *
     * @param writeView
     * @param key
     */
    public void onClickWriteItemViewListener(FormEditView writeView, String key) {
        Toast.makeText(getActivity(), "请在子类重写点击处理事件", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
          /*图片选择回调*/
        if (resultCode == SelectImageActivity.SELECT_PICTURE_RESULT_CODE) {
            if (resultCode == SelectImageActivity.SELECT_PICTURE_RESULT_CODE) {
                List<PictureEntity> lists = (List<PictureEntity>) data.getSerializableExtra("imgs");
                mImageAttachmentView.setImageListToEntity(lists);
            }
        }
    }
}
