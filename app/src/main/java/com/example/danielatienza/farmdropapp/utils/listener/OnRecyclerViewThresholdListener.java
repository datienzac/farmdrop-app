package com.example.danielatienza.farmdropapp.utils.listener;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by danielatienza on 16/12/2016.
 */
public abstract class OnRecyclerViewThresholdListener extends RecyclerView.OnScrollListener {
    public static final int LIST_THRESHOLD = 5;

    private int mPreviousTotal = 0;
    private boolean mLoading = true;
    private int mFirstVisibleItem;
    private int mVisibleItemCount;
    private int mTotalItemCount;
    private LinearLayoutManager mLayoutManager;

    public abstract void onVisibleThreshold();

    public OnRecyclerViewThresholdListener(LinearLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        if(mLayoutManager.getChildCount() == 0) {
            onVisibleThreshold();
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        mVisibleItemCount = recyclerView.getChildCount();
        mTotalItemCount = mLayoutManager.getItemCount();
        mFirstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

        if (mLoading) {
            if (mTotalItemCount > mPreviousTotal) {
                mLoading = false;
                mPreviousTotal = mTotalItemCount;
            }
        }

        if (!mLoading &&
                (mTotalItemCount - mVisibleItemCount) <= (mFirstVisibleItem + LIST_THRESHOLD)) {
            onVisibleThreshold();
            mLoading = true;
        }
    }

    public void reset() {
        mVisibleItemCount = 0;
        mTotalItemCount = 0;
        mPreviousTotal = 0;
    }

    public boolean isReset() {
        return (mVisibleItemCount + mTotalItemCount + mPreviousTotal) == 0;
    }
}
