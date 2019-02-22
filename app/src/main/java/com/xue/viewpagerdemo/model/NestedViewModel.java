package com.xue.viewpagerdemo.model;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by 薛贤俊 on 2019/2/21.
 */
public class NestedViewModel extends ViewModel {

    private MutableLiveData<Integer> pagerHeight;

    private MutableLiveData<View> childView;

    private MutableLiveData<RecyclerView> childList;

    public MutableLiveData<Integer> getPagerHeight() {
        if (pagerHeight == null) {
            pagerHeight = new MutableLiveData<>();
        }
        return pagerHeight;
    }

    public MutableLiveData<View> getChildView() {
        if (childView == null) {
            childView = new MutableLiveData<>();
        }
        return childView;
    }

    public MutableLiveData<RecyclerView> getChildList() {
        if (childList == null) {
            childList = new MutableLiveData<>();
        }
        return childList;
    }
}
