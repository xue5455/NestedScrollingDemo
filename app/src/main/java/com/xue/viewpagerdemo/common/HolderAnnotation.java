package com.xue.viewpagerdemo.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by 薛贤俊 on 2019/2/21.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface HolderAnnotation {
    int layoutId();
}
