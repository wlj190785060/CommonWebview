package com.commonwebview.commonwebview;

import android.support.multidex.MultiDexApplication;

public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
//        UIUtils.init(this);
//        AppUtils.setChannel("hah");
    }
}
