package com.xue.viewpagerdemo.items;

import android.graphics.Bitmap;

import com.xue.viewpagerdemo.ViewType;
import com.xue.viewpagerdemo.common.AdapterItem;

/**
 * Created by 薛贤俊 on 2019/2/21.
 */
public class ParentItem implements AdapterItem<Bitmap> {
    private Bitmap bitmap;

    public ParentItem(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public Bitmap getDataModel() {
        return bitmap;
    }

    @Override
    public int getViewType() {
        return ViewType.TYPE_PARENT;
    }
}
