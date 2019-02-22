package com.xue.viewpagerdemo.items;

import com.xue.viewpagerdemo.ViewType;
import com.xue.viewpagerdemo.common.AdapterItem;
import com.xue.viewpagerdemo.model.PageVO;

import java.util.List;

/**
 * Created by 薛贤俊 on 2019/2/21.
 */
public class PageItem implements AdapterItem<List<PageVO>> {

    private List<PageVO> model;

    public PageItem(List<PageVO> model) {
        this.model = model;
    }

    @Override
    public List<PageVO> getDataModel() {
        return model;
    }

    @Override
    public int getViewType() {
        return ViewType.TYPE_PAGER;
    }
}
