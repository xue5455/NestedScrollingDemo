package com.xue.viewpagerdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.xue.viewpagerdemo.model.NestedViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by 薛贤俊 on 2019/2/22.
 */
public class NestedScrollLayout2 extends FrameLayout implements NestedScrollingParent2 {

    private View mChildView;
    /**
     * 最外层的RecyclerView
     */
    private RecyclerView mRootList;
    /**
     * 子RecyclerView
     */
    private RecyclerView mChildList;

    private NestedViewModel mScrollViewModel;

    private int mAxes;

    public NestedScrollLayout2(@NonNull Context context) {
        super(context);
    }

    public NestedScrollLayout2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTarget(LifecycleOwner target) {
        if (target instanceof FragmentActivity) {
            mScrollViewModel = ViewModelProviders.of((FragmentActivity) target).get(NestedViewModel.class);
        } else if (target instanceof Fragment) {
            mScrollViewModel = ViewModelProviders.of((Fragment) target).get(NestedViewModel.class);
        } else {
            throw new IllegalArgumentException("target must be FragmentActivity or Fragment");
        }
        mScrollViewModel.getChildView().observe(target, new Observer<View>() {
            @Override
            public void onChanged(@Nullable View view) {
                mChildView = view;
            }
        });
        mScrollViewModel.getChildList().observe(target, new Observer<View>() {
            @Override
            public void onChanged(@Nullable View view) {
                mChildList = (RecyclerView) view;
            }
        });
    }

    public void setRootList(RecyclerView recyclerView) {
        mRootList = recyclerView;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        mAxes = axes;
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        mAxes = SCROLL_AXIS_NONE;
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {

    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (mChildView == null) {
            return;
        }
        if (target == mRootList) {
            onParentScrolling(mChildView.getTop(), dy, consumed);
        } else {
            onChildScrolling(mChildView.getTop(), dy, consumed);
        }
    }

    /**
     * 父列表在滑动
     *
     * @param childTop
     * @param dy
     * @param consumed
     */
    private void onParentScrolling(int childTop, int dy, int[] consumed) {
        //列表已经置顶
        if (childTop == 0) {
            if (dy > 0 && mChildList != null) {
                //还在向下滑动，此时滑动子列表
                mChildList.scrollBy(0, dy);
                consumed[1] = dy;
            } else {
                if (mChildList != null && mChildList.canScrollVertically(dy)) {
                    consumed[1] = dy;
                    mChildList.scrollBy(0, dy);
                }
            }
        } else {
            if (childTop < dy) {
                consumed[1] = dy - childTop;
            }
        }
    }

    private void onChildScrolling(int childTop, int dy, int[] consumed) {
        if (childTop == 0) {
            if (dy < 0) {
                //向上滑动
                if (!mChildList.canScrollVertically(dy)) {
                    consumed[1] = dy;
                    mRootList.scrollBy(0, dy);
                }
            }
        } else {
            if (dy < 0 || childTop > dy) {
                consumed[1] = dy;
                mRootList.scrollBy(0, dy);
            } else {
                //dy大于0
                consumed[1] = dy;
                mRootList.scrollBy(0, childTop);
            }
        }
    }

    @Override
    public int getNestedScrollAxes() {
        return mAxes;
    }

}
