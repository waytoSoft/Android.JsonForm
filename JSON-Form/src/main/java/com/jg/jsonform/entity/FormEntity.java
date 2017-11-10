package com.jg.jsonform.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 表单实体
 * <p>
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/10/24 10:12
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FormEntity implements Serializable {

    /*标签*/
    private String label;

    /*值*/
    private String value;

    /*提示*/
    private String hint;

    /*类型[TextView、EditView]*/
    private int type;

    /*必填项*/
    private boolean required;

    /*提交Key*/
    private String submitKey;

    private String matchingKey;

    private EditEntity edit;

    private TextEntity text;

    private List<SelectionBoxEntity> selectionBox;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public EditEntity getEdit() {
        return edit;
    }

    public void setEdit(EditEntity edit) {
        this.edit = edit;
    }

    public TextEntity getText() {
        return text;
    }

    public void setText(TextEntity text) {
        this.text = text;
    }

    public String getSubmitKey() {
        return submitKey;
    }

    public void setSubmitKey(String submitKey) {
        this.submitKey = submitKey;
    }

    public String getMatchingKey() {
        return matchingKey;
    }

    public void setMatchingKey(String matchingKey) {
        this.matchingKey = matchingKey;
    }

    public List<SelectionBoxEntity> getSelectionBox() {
        return selectionBox;
    }

    public void setSelectionBox(List<SelectionBoxEntity> selectionBox) {
        this.selectionBox = selectionBox;
    }
}
