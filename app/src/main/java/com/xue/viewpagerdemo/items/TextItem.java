package com.xue.viewpagerdemo.items;

import com.xue.viewpagerdemo.ViewType;
import com.xue.viewpagerdemo.common.AdapterItem;

/**
 * Created by 薛贤俊 on 2019/2/22.
 */
public class TextItem implements AdapterItem<String> {

    private String text;

    public TextItem(String text) {
        this.text = text;
    }

    @Override
    public String getDataModel() {
        return text;
    }

    @Override
    public int getViewType() {
        return ViewType.TYPE_TEXT;
    }
}
