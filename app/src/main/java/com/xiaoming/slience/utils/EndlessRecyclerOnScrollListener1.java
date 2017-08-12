package com.xiaoming.slience.utils;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * @author slience
 * @des
 * @time 2017/6/118:53
 */

public abstract class EndlessRecyclerOnScrollListener1 extends
        RecyclerView.OnScrollListener {

    private int previousTotal = 0;
    private boolean loading = true;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    int[] firstVisibleItems;

    private int currentPage = 1;

    private StaggeredGridLayoutManager mGridLayoutManager;

    public EndlessRecyclerOnScrollListener1(
            StaggeredGridLayoutManager gridLayoutManager) {
        mGridLayoutManager = gridLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mGridLayoutManager.getItemCount();
        if(firstVisibleItems == null){
            firstVisibleItems = new int[mGridLayoutManager.getSpanCount()];
        }
        mGridLayoutManager.findFirstVisibleItemPositions(firstVisibleItems);
        firstVisibleItem = findMin(firstVisibleItems);



        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading
                && (totalItemCount - visibleItemCount) <= firstVisibleItem) {
            currentPage++;
            onLoadMore(currentPage);
            loading = true;
        }
    }

    private int findMin(int[] firstPositions) {
        int min = firstPositions[0];
        for (int value : firstPositions) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    public abstract void onLoadMore(int currentPage);
}
