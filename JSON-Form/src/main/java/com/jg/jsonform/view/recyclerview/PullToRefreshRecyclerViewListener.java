package com.jg.jsonform.view.recyclerview;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/9/30$ 上午10:06$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public interface PullToRefreshRecyclerViewListener {

    /*下拉刷新*/
    void onDownRefresh();

    /*上拉刷新*/
    void onPullRefresh();

}
