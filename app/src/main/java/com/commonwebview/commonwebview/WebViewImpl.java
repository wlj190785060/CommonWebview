package com.commonwebview.commonwebview;

import port.WebviewCBHelper;

/**
 * Created by wanglinjie.
 * create time:2018/11/19  下午4:56
 */
public class WebViewImpl extends WebviewCBHelper {
    //如果不绑定对象，则属于正常的webview加载链接
    @Override
    public String getWebViewJsObject() {
        return "zjxw";
    }
}
