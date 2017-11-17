package com.jg.jsonform.view.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jg.jsonform.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/9/30$ 上午10:29$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public abstract class RecyclerViewBaseAdapter<T> extends RecyclerView.Adapter {

    /*类型-加载Item*/
    private static final int TYPE_ITEM = 0;

    /*类型-加载更多*/
    private static final int TYPE_FOOTER = 1;

    /*下拉刷新*/
    public static final int REFRESH = 0x100;

    /*加载更多*/
    public static final int LOAD_MORE = 0x200;

    /*默认加载数量*/
    private int pageSize = 20;

    /*加载状态*/
    private int loadStatus;

    /*标记是否正在刷新*/
    private boolean isLoading = false;

    /*标记是否加载更多，在加载完成后使用*/
    private boolean isLoadMore = true;

    protected Context mContent;
    protected LayoutInflater mInflater;

    /*目标对象集合*/
    protected List<T> mList = new ArrayList<>();

    /*item 点击事件*/
    protected OnRecyclerViewItemClickListener listener;

    /*item 长按事件*/
    protected OnRecyclerViewItemLongClickListener longListener;

    private PullToRefreshRecyclerView.Mode mMode;

    public RecyclerViewBaseAdapter(Context context) {
        this.mContent = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return onBaseCreateViewHolder(parent, viewType);
        } else if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(mInflater.inflate(R.layout.recyclerview_loading_foot_layout, parent, false));
        } else
            return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        int type = getItemViewType(position);
        if (type == TYPE_ITEM) {
            onBaseBindViewHolder(holder, position);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(holder.itemView, mList.get(position), position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longListener != null) {
                    longListener.onLongItemClick(holder.itemView, mList.get(position), position);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() >= pageSize
                && position + 1 == getItemCount()
                && (mMode == PullToRefreshRecyclerView.Mode.PULL_FROM_END || mMode == PullToRefreshRecyclerView.Mode.BOTH)
                && !isLoading
                && isLoadMore) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        int count = mList.size();
        if (count >= pageSize
                && (mMode == PullToRefreshRecyclerView.Mode.PULL_FROM_END || mMode == PullToRefreshRecyclerView.Mode.BOTH)
                && !isLoading
                && isLoadMore) {
            return ++count;
        } else if (count > 0) {
            return count;
        } else
            return 0;
    }

    /**
     * 添加数据
     * <p>
     * author: hezhiWu
     * created at 2017/9/30 上午11:26
     *
     * @param list
     */
    public void addItems(List<T> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     * <p>
     * author: hezhiWu
     * created at 2017/9/30 上午11:26
     *
     * @param position
     * @param list
     */
    public void addItems(int position, List<T> list) {
        mList.addAll(position, list);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     * <p>
     * author: hezhiWu
     * created at 2017/9/30 上午11:30
     *
     * @param list
     */
    public void addItems(T[] list) {
        mList.addAll(Arrays.asList(list));
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     * <p>
     * author: hezhiWu
     * created at 2017/9/30 上午11:31
     *
     * @param position
     * @param list
     */
    public void addItems(int position, T[] list) {
        mList.addAll(position, Arrays.asList(list));
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     * <p>
     * author: hezhiWu
     * created at 2017/9/30 上午11:26
     *
     * @param list
     */
    public void addItem(T list) {
        mList.add(list);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     * <p>
     * author: hezhiWu
     * created at 2017/9/30 上午11:27
     *
     * @param position
     * @param list
     */
    public void addItem(int position, T list) {
        mList.add(position, list);
        notifyDataSetChanged();
    }

    /**
     * 清空数据
     * <p>
     * author: hezhiWu
     * created at 2017/9/30 下午2:39
     */
    public void clearList() {
        mList.clear();
    }

    /**
     * 设置Mode
     * <p>
     * author: hezhiWu
     * created at 2017/9/30 下午2:00
     */
    public void setMode(PullToRefreshRecyclerView.Mode mode) {
        this.mMode = mode;
    }

    /**
     * 设置加载状态
     * <p>
     * author: hezhiWu
     * created at 2017/9/30 下午3:25
     */
    public void setLoadStatus(int loadStatus) {
        this.loadStatus = loadStatus;
    }

    /**
     * 设置是否正在加载
     * <p>
     * author: hezhiWu
     * created at 2017/9/30 下午3:25
     */
    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    /**
     * 设置点击事件监听器
     * <p>
     * author: hezhiWu
     * created at 2017/10/13 10:40
     */
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 设置长按事件监听器
     * <p>
     * author: hezhiWu
     * created at 2017/10/13 10:39
     */
    public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener longListener) {
        this.longListener = longListener;
    }

    /**
     * 设置是否加载更多
     * <p>
     * author: hezhiWu
     * created at 2017/9/30 下午3:38
     */
    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    /**
     * 设置PageSize
     * <p>
     * author: hezhiWu
     * created at 2017/10/17 15:45
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public abstract RecyclerView.ViewHolder onBaseCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void onBaseBindViewHolder(RecyclerView.ViewHolder holder, int position);

    class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }
    }


    /**
     * @author hezhiWu
     * @version V1.0
     * @Package com.yunwei.water.ui.biz.interfac
     * @Description:RecyclerView 点击事件
     * @date 2016/9/12 16:43
     */
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Object data, int position);
    }

    /**
     * @author hezhiWu
     * @version V1.0
     * @Package com.yunwei.water.ui.biz.interfac
     * @Description:RecyclerView 长按事件
     * @date 2016/9/12 16:43
     */
    public interface OnRecyclerViewItemLongClickListener {
        void onLongItemClick(View view, Object data, int position);
    }
}
