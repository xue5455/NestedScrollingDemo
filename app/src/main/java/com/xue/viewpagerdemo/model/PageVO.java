package com.xue.viewpagerdemo.model;

/**
 * Created by 薛贤俊 on 2019/2/21.
 */
public class PageVO {
    private int color;
    private String title;

    public PageVO(int color, String title) {
        this.color = color;
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }
}
