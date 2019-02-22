package com.xue.viewpagerdemo.viewholder;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.xue.viewpagerdemo.R;
import com.xue.viewpagerdemo.common.BaseViewHolder;
import com.xue.viewpagerdemo.common.HolderAnnotation;

import androidx.annotation.NonNull;

/**
 * Created by 薛贤俊 on 2019/2/21.
 */
@HolderAnnotation(layoutId = R.layout.item_parent_holder)
public class ImageViewHolder extends BaseViewHolder<Bitmap> {

    private ImageView imageView;

    private Bitmap bitmap;

    public ImageViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void initViews() {
        imageView = itemView.findViewById(R.id.imageview);
    }

    @Override
    public void bindView(Bitmap data) {
        if (bitmap == data) {
            return;
        }
        bitmap = data;
        imageView.setImageBitmap(bitmap);
    }
}
