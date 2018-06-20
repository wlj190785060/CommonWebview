package com.commonwebview.webview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * WebView全屏容器
 *
 * @author wanglinjie
 * @date 2018/6/20 10:51.
 */
public class WebFullScreenContainer extends FrameLayout {

    public WebFullScreenContainer(@NonNull Context context) {
        super(context);
        setBackgroundColor(context.getResources().getColor(android.R.color.black));
    }

    public WebFullScreenContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WebFullScreenContainer(@NonNull Context context, @Nullable AttributeSet attrs,
                                  @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WebFullScreenContainer(@NonNull Context context, @Nullable AttributeSet attrs,
                                  @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
