package com.commonwebview.commonwebview;

import android.support.multidex.MultiDexApplication;

import com.zjrb.core.utils.AppUtils;
import com.zjrb.core.utils.UIUtils;

public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        UIUtils.init(this);
        AppUtils.setChannel("hah");
    }
}
