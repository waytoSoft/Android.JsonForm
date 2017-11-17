package com.jg.jsonform.view.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jg.jsonform.R;

/**
 * RecyclerView上下拉刷新
 * <p>
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/9/3 20:00
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class PullToRefreshRecyclerView extends LinearLayout implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private TextView mEmptyTextView;

    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridLayoutManager;


    private PullToRefreshRecyclerViewListener pullToRefreshRecyclerViewListener;

    private RecyclerViewBaseAdapter mAdapter;

    /*grid类型，默认3个Item*/
    private int mSpanNum = 3;
    /*间距*/
    private int mSpacingInPixels;

    /*默认加载数量*/
    private int pageSize = 20;

    /*布局类型,默认List*/
    private Type mType = Type.List;

    /*刷新模式，默认下拉刷新*/
    private Mode mMode = Mode.PULL_FROM_START;

    /*标记是否正在加载中*/
    private boolean isLoading = false;

    public PullToRefreshRecyclerView(Context context) {
        super(context, null);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        analysisAttributeset(context, attrs, 0);

        initView();
    }

    /**
     * 初始化View
     * <p>
     * author: hezhiWu
     * created at 2017/9/3 20:19
     */
    public void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.pulltorefresh_recyclerview, null);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.pullToRefresh_swiperefreshLayout);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.pullToRefresh_recycleListview);
        mEmptyTextView = (TextView) rootView.findViewById(R.id.pullToRefresh_Empty_TextView);

        initRecyclerType(mType);

        /*设置刷新模式*/
        setMode(mMode);

        mRecyclerView.addOnScrollListener(new RecyclerViewOnScrollListener());

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!mSwipeRefreshLayout.isRefreshing()) {
                    return true;
                } else
                    return false;
            }
        });

        addView(rootView);
    }

    /**
     * 初始RecyclerType显示模式,目前支持List、Grid、Gallery三种类型
     * <p>
     * author: hezhiWu
     * created at 2017/9/3 20:50
     */
    private void initRecyclerType(Type type) {
        if (type == Type.List) {

            mLinearLayoutManager = new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));

        } else if (type == Type.Grid) {

            mGridLayoutManager = new GridLayoutManager(getContext(), mSpanNum);
            mRecyclerView.addItemDecoration(new SpaceItemDecoration(mSpacingInPixels));
            mRecyclerView.setLayoutManager(mGridLayoutManager);

        } else if (type == Type.Gallery) {

            mLinearLayoutManager = new LinearLayoutManager(getContext(), OrientationHelper.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);

        }
    }

    /**
     * 解析
     * <p>
     * author: hezhiWu
     * created at 2017/9/3 20:44
     */
    public void analysisAttributeset(Context context, AttributeSet attrs, int defStyle) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PullToRefreshRecyclerView, defStyle, 0);

        /*解析类型*/
        int type = typedArray.getInt(R.styleable.PullToRefreshRecyclerView_recyclerView_Type, Type.List.getValue());
        if (type == Type.List.getValue()) {
            mType = Type.List;
        } else if (type == Type.Grid.getValue()) {
            mType = Type.Grid;
        } else if (type == Type.Gallery.getValue()) {
            mType = Type.Gallery;
        }

        /*解析刷新模式*/
        int mode = typedArray.getInt(R.styleable.PullToRefreshRecyclerView_recyclerView_Mode, Mode.PULL_FROM_START.getValue());
        if (mode == Mode.DISABLED.getValue()) {
            mMode = Mode.DISABLED;
        } else if (mode == Mode.PULL_FROM_START.getValue()) {
            mMode = Mode.PULL_FROM_START;
        } else if (mode == Mode.PULL_FROM_END.getValue()) {
            mMode = Mode.PULL_FROM_END;
        } else if (mode == Mode.BOTH.getValue()) {
            mMode = Mode.BOTH;
        }

        mSpanNum = typedArray.getInt(R.styleable.PullToRefreshRecyclerView_span_Count, 3);

        mSpacingInPixels = typedArray.getInt(R.styleable.PullToRefreshRecyclerView_spac_InPixels, 0);

        pageSize = typedArray.getInt(R.styleable.PullToRefreshRecyclerView_recyclerView_PageSize, 20);
    }


    @Override
    public void onRefresh() {
        if (pullToRefreshRecyclerViewListener == null) {
            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }

        if (mAdapter == null) {
            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }

        if (isLoading) {
            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }

        mAdapter.setLoadStatus(RecyclerViewBaseAdapter.REFRESH);
        isLoading = true;
        pullToRefreshRecyclerViewListener.onDownRefresh();
    }

    /**
     * 开启下拉刷新
     * <p>
     * author: hezhiWu
     * created at 2017/9/30 下午2:32
     */
    public void startDownRefresh() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        onRefresh();
    }

    /**
     * 结束下拉刷新
     * <p>
     * author: hezhiWu
     * created at 2017/9/30 下午2:33
     */
    public void onCloseDownRefresh() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        isLoading = false;
    }

    /**
     * 关闭加载更多
     * <p>
     * author: hezhiWu
     * created at 2017/9/30 下午3:31
     */
    public void onCloseLoadMore() {
        isLoading = false;
        mAdapter.setLoadMore(false);
    }

    /**
     * 加载更多完成
     * <p>
     * author: hezhiWu
     * created at 2017/9/30 下午3:33
     */
    public void onLoadMoreComplete() {
        isLoading = false;
    }

    /**
     * 设置适配器
     * <p>
     * author: hezhiWu
     * created at 2017/9/30 上午11:47
     */
    public void setRecyclerViewAdapter(RecyclerViewBaseAdapter adapter) {
        this.mAdapter = adapter;
        mRecyclerView.setAdapter(adapter);
        adapter.setMode(mMode);
        adapter.setPageSize(pageSize);
    }

    /**
     * 设置刷新监听器
     * <p>
     * author: hezhiWu
     * created at 2017/9/3 21:35
     */
    public void setPullToRefreshRecyclerViewListener(PullToRefreshRecyclerViewListener listener) {
        this.pullToRefreshRecyclerViewListener = listener;
    }

    /**
     * 设置点击事件监听器
     * <p>
     * author: hezhiWu
     * created at 2017/10/13 10:40
     */
    public void setOnItemClickListener(RecyclerViewBaseAdapter.OnRecyclerViewItemClickListener listener) {
        if (mAdapter != null)
            mAdapter.setOnItemClickListener(listener);
    }

    /**
     * 设置长按事件监听器
     * <p>
     * author: hezhiWu
     * created at 2017/10/13 10:39
     */
    public void setOnItemLongClickListener(RecyclerViewBaseAdapter.OnRecyclerViewItemLongClickListener longListener) {
        if (mAdapter != null)
            mAdapter.setOnItemLongClickListener(longListener);
    }

    /**
     * 设置刷新模式
     * <p>
     * author: hezhiWu
     * created at 2017/9/3 21:38
     */
    public void setMode(Mode mode) {
        this.mMode = mode;

        if (mode == Mode.DISABLED || mode == Mode.PULL_FROM_END)
            mSwipeRefreshLayout.setEnabled(false);

        if (mAdapter != null)
            mAdapter.setMode(mode);
    }

    /**
     * 设置布局模式
     * <p>
     * author: hezhiWu
     * created at 2017/9/3 21:39
     */
    public void setType(Type type) {
        this.mType = type;
    }

    /**
     * 当加载数据为空时,显示提示消息(默认提示消息——"暂无消息")
     * <p>
     * author: hezhiWu
     * created at 2017/9/3 21:49
     */
    public void showEmptyTextView() {
        mEmptyTextView.setVisibility(View.VISIBLE);
    }

    /**
     * 当加载数据为空时,显示提示消息
     * <p>
     * author: hezhiWu
     * created at 2017/9/3 21:53
     *
     * @param resId
     */
    public void showEmptyTextView(int resId) {
        showEmptyTextView(getResources().getString(resId));
    }

    /**
     * 当加载数据为空时,显示提示消息
     * <p>
     * author: hezhiWu
     * created at 2017/9/3 21:54
     *
     * @param message
     */
    public void showEmptyTextView(String message) {
        mEmptyTextView.setText(message);
        mEmptyTextView.setVisibility(View.VISIBLE);
    }

    /**
     * 当加载数据为空时,显示提示消息
     * <p>
     * author: hezhiWu
     * created at 2017/9/3 21:54
     *
     * @param textResId
     * @param imageResId
     */
    public void showEmptyTextView(int textResId, int imageResId) {
        showEmptyTextView(getResources().getString(textResId, imageResId));
    }

    /**
     * 当加载数据为空时,显示提示消息
     * <p>
     * author: hezhiWu
     * created at 2017/9/3 21:54
     *
     * @param message
     * @param imageResId
     */
    public void showEmptyTextView(String message, int imageResId) {
        //TODO 等显示处理...

    }

    /**
     * RecyclerView 滑动监听
     * <p>
     * author: hezhiWu <hezhi.woo@gmail.com>
     * version: V1.0
     * created at 2017/9/3 21:59
     * <p>
     * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
     */
    private class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (isLoading)
                return;

            if (mSwipeRefreshLayout.isRefreshing()) {
                mAdapter.setLoading(true);
                return;
            }

            if (pullToRefreshRecyclerViewListener == null)
                return;

            if (mAdapter == null)
                return;

            int lastVisibleItem = 0;
            int totalItemCount = 0;

            if (mType == Type.List) {
                lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
                totalItemCount = mLinearLayoutManager.getItemCount();
            } else if (mType == Type.Grid) {
                lastVisibleItem = mGridLayoutManager.findLastVisibleItemPosition();
                totalItemCount = mGridLayoutManager.getItemCount();
            }

            if (mMode == Mode.BOTH || mMode == Mode.PULL_FROM_END) {
                if (lastVisibleItem == totalItemCount - 1 && dy > 0) {
                    isLoading = true;

                    mAdapter.setLoadStatus(RecyclerViewBaseAdapter.LOAD_MORE);
                    mAdapter.setLoading(false);

                    pullToRefreshRecyclerViewListener.onPullRefresh();
                }
            }
        }
    }

    /**
     * 刷新模式
     * <p>
     * author: hezhiWu <hezhi.woo@gmail.com>
     * version: V1.0
     * created at 2017/9/3 21:59
     * <p>
     * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
     */
    public enum Mode {

        /*禁用所有刷新的手势及刷新处理*/
        DISABLED(0x100),

        /*只允许下拉刷新*/
        PULL_FROM_START(0x200),

        /*只允许上拉刷新*/
        PULL_FROM_END(0x300),

        /*允许所有刷新手势及刷新处理*/
        BOTH(0x400);

        private int value;

        public int getValue() {
            return value;
        }

        Mode(int value) {
            this.value = value;
        }
    }

    /**
     * 布局类型
     * <p>
     * author: hezhiWu <hezhi.woo@gmail.com>
     * version: V1.0
     * created at 2017/9/3 21:59
     * <p>
     * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
     */
    public enum Type {

        List(10),

        Grid(20),

        Gallery(30);

        private int value;

        public int getValue() {
            return value;
        }

        Type(int value) {
            this.value = value;
        }
    }

}
