package com.xue.viewpagerdemo.common;

import android.content.Context;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by 薛贤俊 on 2019/2/21.
 */
public class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private List<AdapterItem> itemList;

    private Context context;

    private SparseArray<Class<? extends BaseViewHolder>> viewHolders;

    private LayoutInflater inflater;

    public BaseAdapter(List<AdapterItem> itemList, Context context, SparseArray<Class<? extends BaseViewHolder>> viewHolders) {
        this.itemList = itemList;
        this.context = context;
        this.viewHolders = viewHolders;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        try {
            Class clazz = viewHolders.get(viewType);
            HolderAnnotation annotation = (HolderAnnotation) clazz.getAnnotation(HolderAnnotation.class);
            int layoutId = annotation.layoutId();
            View itemView = inflater.inflate(layoutId, viewGroup, false);
            Constructor constructor = clazz.getConstructor(View.class);
            BaseViewHolder viewHolder = (BaseViewHolder) constructor.newInstance(itemView);
            viewHolder.initViews();
            return viewHolder;
        } catch (Throwable e) {

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int position) {
        AdapterItem item = itemList.get(position);
        baseViewHolder.bindView(item.getDataModel());
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }
}
