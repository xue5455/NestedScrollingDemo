package com.xue.viewpagerdemo;

import android.os.Bundle;
import android.util.SparseArray;

import com.xue.viewpagerdemo.model.PageVO;

import java.lang.ref.SoftReference;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Created by 薛贤俊 on 2019/2/21.
 */
public class SubPagerAdapter extends FragmentStatePagerAdapter {

    private List<PageVO> itemList;

    public SubPagerAdapter(FragmentManager fm, List<PageVO> itemList) {
        super(fm);
        this.itemList = itemList;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new SubFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("color", itemList.get(position).getColor());
        bundle.putInt("position",position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return itemList == null ? 0 : itemList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return itemList.get(position).getTitle();
    }
}
