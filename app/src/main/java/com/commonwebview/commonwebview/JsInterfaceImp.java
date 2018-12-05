package com.commonwebview.commonwebview;


import android.content.Context;

import port.JsInterface;

/**
 * 继承Js实现类，如果不继承，则没有注入功能
 * Created by wanglinjie.
 * create time:2018/11/27  上午10:08
 */
public class JsInterfaceImp extends JsInterface {
    public JsInterfaceImp(String jsObject, Context ctx) {
        super(jsObject, ctx);
    }

    @Override
    public void imageABrowseCB(String url) {
    }

    @Override
    public void imageBrowseCB(String url) {
    }


}
