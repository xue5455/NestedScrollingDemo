package com.xue.viewpagerdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.OverScroller;

import com.xue.viewpagerdemo.model.NestedViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by 薛贤俊 on 2019/2/15.
 */
public class NestedScrollLayout extends FrameLayout {
    private View mChildView;
    /**
     * 最外层的RecyclerView
     */
    private RecyclerView mRootList;
    /**
     * 子RecyclerView
     */
    private RecyclerView mChildList;
    /**
     * 用来处理Fling
     */
    private OverScroller mScroller;

    private NestedViewModel mScrollViewModel;

    private int mLastY;

    public NestedScrollLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public NestedScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
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
                Log.d("xuetest","onChanged");
                mChildList = (RecyclerView) view;
            }
        });
    }

    public void setRootList(RecyclerView recyclerView) {
        mRootList = recyclerView;
    }


    private void init() {
        mScroller = new OverScroller(getContext());
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed) {
        stopScroller();
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

    private void stopScroller() {
        mScroller.forceFinished(true);
    }

    private void onFling(int dy) {
        if (mChildView != null) {
            //子列表有显示
            int top = mChildView.getTop();
            if (top == 0) {
                if (dy > 0) {
                    if (mChildList != null && mChildList.canScrollVertically(dy)) {
                        mChildList.scrollBy(0, dy);
                    } else {
                        stopScroller();
                    }
                } else {
                    if (mChildList != null && mChildList.canScrollVertically(dy)) {
                        mChildList.scrollBy(0, dy);
                    } else {
                        mRootList.scrollBy(0, dy);
                    }
                }
            } else {
                if (dy > 0) {
                    if (top > dy) {
                        mRootList.scrollBy(0, dy);
                    } else {
                        mRootList.scrollBy(0, top);
                    }
                } else {
                    if (mRootList.canScrollVertically(dy)) {
                        mRootList.scrollBy(0, dy);
                    } else {
                        stopScroller();
                    }
                }
            }
        } else {
            if (!mRootList.canScrollVertically(dy)) {
                stopScroller();
            } else {
                mRootList.scrollBy(0, dy);
            }
        }
    }

    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        mLastY = 0;
        this.mScroller.fling(0, 0, (int) velocityX, (int) velocityY, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
        invalidate();
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            int currY = mScroller.getCurrY();
            int dy = currY - mLastY;
            mLastY = currY;
            if (dy != 0) {
                onFling(dy);
            }
            invalidate();
        }
        super.computeScroll();
    }
}
