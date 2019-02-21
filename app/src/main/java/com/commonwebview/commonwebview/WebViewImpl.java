package com.commonwebview.commonwebview;

import port.WebviewCBHelper;

/**
 * 扩展类，可以实现webview高级功能
 * Created by wanglinjie.
 * create time:2018/11/19  下午4:56
 */
public class WebViewImpl extends WebviewCBHelper {
    @Override
    public String getUserAgent() {
        return "";
    }

    //如果不绑定对象，则属于正常的webview加载链接
    //使用浙江新闻通用版本则使用ZBJTJsBridge.PREFIX_JS_METHOD_NAME
    @Override
    public String getWebViewJsObject() {
        return "zjxw";
    }

    //默认非省流量模式
    @Override
    public boolean isProvinTrafficMode() {
        return false;
    }

}
