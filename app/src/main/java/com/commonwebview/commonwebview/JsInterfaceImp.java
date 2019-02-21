package com.commonwebview.commonwebview;


import android.content.Context;
import android.webkit.WebView;

import bean.ZBJTGetAppInfoBean;
import bean.ZBJTGetAppInfoRspBean;
import bean.ZBJTGetLocalRspBean;
import bean.ZBJTGetValueFromLocalBean;
import bean.ZBJTGetValueFromLocalRspBean;
import bean.ZBJTModifyUserInfoBean;
import bean.ZBJTModifyUserInfoRspBean;
import bean.ZBJTOpenAppMobileBean;
import bean.ZBJTOpenAppMobileRspBean;
import bean.ZBJTOpenAppShareMenuBean;
import bean.ZBJTOpenAppShareMenuRspBean;
import bean.ZBJTReturnBean;
import bean.ZBJTSelectImageBean;
import bean.ZBJTSelectImageRspBean;
import bean.ZBJTStartRecordBean;
import bean.ZBJTStartRecordRspBean;
import bean.ZBJTUploadFileBean;
import bean.ZBJTUploadFileRspBean;
import port.JsInterface;
import port.ZBJTJSInterFace;

/**
 * 实现ZBJTJSInterFace接口则表示支持云平台新标准，不继承则需要自己实现一套
 * 继承Js实现类，如果不继承，则没有注入功能
 * Created by wanglinjie.
 * create time:2018/11/27  上午10:08
 */
public class JsInterfaceImp extends JsInterface implements ZBJTJSInterFace {
    public JsInterfaceImp(WebView webView, String jsObject, Context ctx) {
        super(webView, jsObject, ctx);
    }

    //点击图片逻辑
    @Override
    public void imageABrowseCB(int index, String url) {

    }

    @Override
    public void imageBrowseCB(int index, String url) {
    }

    //浙报集团通用JSSDK实现
    @Override
    public void openAppShareMenu(WebView webview, ZBJTOpenAppShareMenuBean bean, ZBJTOpenAppShareMenuRspBean beanRsp, String callback) {

    }

    @Override
    public void updateAppShareData(WebView webview, ZBJTOpenAppShareMenuBean bean, ZBJTReturnBean beanRsp, String callback) {

    }

    @Override
    public void selectImage(WebView webview, ZBJTSelectImageBean bean, ZBJTSelectImageRspBean beanRsp, String callback) {

    }

    @Override
    public void startRecord(WebView webview, ZBJTStartRecordBean bean, ZBJTStartRecordRspBean beanRsp, String callback) {

    }

    @Override
    public void getAppInfo(WebView webview, ZBJTGetAppInfoBean bean, ZBJTGetAppInfoRspBean BeanRsp, String callback) {

    }

    @Override
    public void getLocation(WebView webview, ZBJTGetLocalRspBean BeanRsp, String callback) {

    }

    @Override
    public void uploadFile(WebView webview, ZBJTUploadFileBean bean, ZBJTUploadFileRspBean beanRsp, String callback) {

    }

    @Override
    public void closeWindow(WebView webview, ZBJTReturnBean beanRsp, String callback) {

    }

    @Override
    public void saveValueToLocal(WebView webview, ZBJTGetValueFromLocalBean bean, ZBJTReturnBean beanRsp, String callback) {

    }

    @Override
    public void getValueFromLocal(WebView webview, ZBJTGetValueFromLocalBean bean, ZBJTGetValueFromLocalRspBean beanRsp, String callback) {

    }

    @Override
    public void login(WebView webview, ZBJTReturnBean beanRsp, String callback) {

    }

    @Override
    public void getUserInfo(WebView webview, String json, String callback) {

    }

    @Override
    public void openAppMobile(WebView webVebview, ZBJTOpenAppMobileBean bean, ZBJTOpenAppMobileRspBean beanRsp, String callback) {

    }

    @Override
    public void modifyUserInfo(WebView webview, ZBJTModifyUserInfoBean bean, ZBJTModifyUserInfoRspBean beanRsp, String callback) {

    }
}
