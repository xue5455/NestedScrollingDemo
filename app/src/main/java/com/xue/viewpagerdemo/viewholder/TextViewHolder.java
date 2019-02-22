package com.xue.viewpagerdemo.viewholder;

import android.view.View;
import android.widget.TextView;

import com.xue.viewpagerdemo.R;
import com.xue.viewpagerdemo.common.BaseViewHolder;
import com.xue.viewpagerdemo.common.HolderAnnotation;

import androidx.annotation.NonNull;

/**
 * Created by 薛贤俊 on 2019/2/22.
 */
@HolderAnnotation(layoutId = R.layout.item_text)
public class TextViewHolder extends BaseViewHolder<String> {

    private TextView textView;

    public TextViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void initViews() {
        textView = itemView.findViewById(R.id.textview);
    }

    @Override
    public void bindView(String data) {
        textView.setText(data);
    }
}
