package com.example.zhaoh.fragmentandviewpage;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Jiang Chen on 16/4/26.
 */
public class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    private static final int LOAD_MORE_TYPE = -1;

    private final int mResource;
    private final List<T> mDataSet;

    private Action1<Integer> mOnItemClickAction;
    private Func1<Integer, Boolean> mOnItemLongClickFunc;

    private boolean isLoading = true;

    public CommonAdapter(@LayoutRes int resource, @NonNull List<T> dataSet) {
        this.mResource = resource;
        this.mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(viewType != LOAD_MORE_TYPE ? mResource : R.layout.item_loading, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder.getItemViewType() == LOAD_MORE_TYPE) {
            return;
        }
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(com.example.zhaoh.fragmentandviewpage.BR.item, mDataSet.get(position));
        onDataBinding(binding, position);
        binding.executePendingBindings();
        if (mOnItemClickAction != null) {
            holder.itemView.setOnClickListener(v -> mOnItemClickAction.call(position));
        }
        if (mOnItemLongClickFunc != null) {
            holder.itemView.setOnLongClickListener(v -> mOnItemLongClickFunc.call(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mDataSet.size() - 1 && mDataSet.get(position) == null) {
            return LOAD_MORE_TYPE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setLoadMoreAction(RecyclerView recyclerView, Action0 action) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layout = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = layout.getItemCount();
                if (totalItemCount >= 16) {
                    int lastVisibleItem = layout.findLastVisibleItemPosition();
                    if (!isLoading && totalItemCount <= (lastVisibleItem + 1)) {
                        isLoading = true;
                        mDataSet.add(null);
                        notifyDataSetChanged();
                        new Handler().postDelayed(action::call, 1000);
                    }
                }
            }
        });
    }

    public void reset() {
        isLoading = false;
        mDataSet.remove(null);
        notifyDataSetChanged();
    }

    public void finish() {
        isLoading = true;
    }

    public void setOnItemClickAction(Action1<Integer> onItemClickAction) {
        mOnItemClickAction = onItemClickAction;
    }

    public void setOnItemLongClickFunc(Func1<Integer, Boolean> onItemLongClickFunc) {
        mOnItemLongClickFunc = onItemLongClickFunc;
    }

    protected void onDataBinding(ViewDataBinding binding, int position) {
    }
}
