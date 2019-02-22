package com.xue.viewpagerdemo.viewholder;

import android.content.Context;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.xue.viewpagerdemo.R;
import com.xue.viewpagerdemo.SubPagerAdapter;
import com.xue.viewpagerdemo.common.BaseViewHolder;
import com.xue.viewpagerdemo.common.HolderAnnotation;
import com.xue.viewpagerdemo.model.NestedViewModel;
import com.xue.viewpagerdemo.model.PageVO;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by 薛贤俊 on 2019/2/21.
 */
@HolderAnnotation(layoutId = R.layout.item_pager)
public class PagerViewHolder extends BaseViewHolder<List<PageVO>> {

    private ViewPager viewPager;

    private TabLayout tabLayout;

    private PagerAdapter pagerAdapter;

    private List<PageVO> model;

    private NestedViewModel viewModel;

    private Observer<Integer> observer = new Observer<Integer>() {
        @Override
        public void onChanged(Integer height) {
            if (height != null) {
                itemView.getLayoutParams().height = height;
                itemView.requestLayout();
            }
        }
    };

    public PagerViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void initViews() {
        viewPager = itemView.findViewById(R.id.viewpager);
        tabLayout = itemView.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager, true);
        viewPager.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                viewPager.requestLayout();
            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });

        itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
                if (viewModel != null) {
                    viewModel.getChildView().setValue(itemView);
                }
            }

            @Override
            public void onViewDetachedFromWindow(View view) {
                if (viewModel != null) {
                    viewModel.getChildView().setValue(null);
                }
            }
        });
    }

    @Override
    public void bindView(List<PageVO> data) {
        if (model == data) {
            return;
        }
        model = data;
        Context context = itemView.getContext();
        if (context instanceof FragmentActivity) {
            FragmentActivity fragmentActivity = (FragmentActivity) context;
            pagerAdapter = new SubPagerAdapter(fragmentActivity.getSupportFragmentManager(), model);
            viewPager.setAdapter(pagerAdapter);
            pagerAdapter.notifyDataSetChanged();
            viewModel = ViewModelProviders.of(fragmentActivity).get(NestedViewModel.class);
            viewModel.getPagerHeight().removeObserver(observer);
            viewModel.getPagerHeight().observe(fragmentActivity, observer);
            if (viewModel.getPagerHeight().getValue() != null)
                itemView.getLayoutParams().height = viewModel.getPagerHeight().getValue();
        }
    }
}
