package com.jg.jsonform;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/10/25 14:12
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FormConstacts {

    /*当前时间默认值*/
    public static final String DEFALUT_CURRENT_DATA_VALUE = "currentData";

    /**
     * 表单类型
     * <p>
     * author: hezhiWu
     * created at 2017/10/25 14:20
     */
    public enum FormType {
        Edit(1), Text(2),SelectionBox(3);

        private int value;

        public int getValue() {
            return value;
        }

        FormType(int value) {
            this.value = value;
        }
    }
}
