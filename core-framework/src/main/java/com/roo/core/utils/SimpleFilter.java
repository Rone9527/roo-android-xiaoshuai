package com.roo.core.utils;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author oldnine
 * @since 2018/11/24
 */
public class SimpleFilter<T> extends Filter {

    private List<T> completeListData = new ArrayList<>();

    private OnResultListener<T> mOnResultListener;

    private OnFilter<T> mOnFilter;

    public SimpleFilter() {
        this(Collections.emptyList());
    }
    public SimpleFilter(List<T> completeListData) {
        this.completeListData.addAll(completeListData);
    }

    public void setOnFilter(OnFilter<T> onFilter) {
        mOnFilter = onFilter;
    }

    public void setOnResultListener(OnResultListener<T> onResultListener) {
        mOnResultListener = onResultListener;
    }

    public List<T> getCompleteListData() {
        return completeListData;
    }

    public synchronized void setCompleteListData(List<T> completeListData) {
        this.completeListData.clear();
        this.completeListData.addAll(completeListData);
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        final FilterResults results = new FilterResults();
        final ArrayList<T> newValues = new ArrayList<>();
        if (!(constraint == null)) {
            synchronized (this) {
                for (T s : completeListData) {
                    if (mOnFilter.onCompare(s, constraint)) {
                        newValues.add(s);
                    }
                }
            }
        }
        results.count = newValues.size();
        results.values = newValues;
        return results;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void publishResults(CharSequence constraint, FilterResults results) {
        if (results.count > 0) {
            mOnResultListener.onDataRefresh((List<T>) results.values);
        } else {
            mOnResultListener.onDataInvalidated();
        }
    }

    public interface OnResultListener<T> {

        void onDataRefresh(List<T> data);

        void onDataInvalidated();

    }

    public interface OnFilter<T> {
        boolean onCompare(T data, CharSequence constraint);
    }
}
