package com.commonwebview.commonwebview;


import android.content.Context;
import android.webkit.WebView;

import bean.ZBJTAppEventBean;
import bean.ZBJTGetAppInfoRspBean;
import bean.ZBJTGetLocalRspBean;
import bean.ZBJTGetValueFromLocalBean;
import bean.ZBJTGetValueFromLocalRspBean;
import bean.ZBJTModifyUserInfoRspBean;
import bean.ZBJTOpenAppMobileBean;
import bean.ZBJTOpenAppMobileRspBean;
import bean.ZBJTOpenAppShareMenuBean;
import bean.ZBJTOpenAppShareMenuRspBean;
import bean.ZBJTPreviewImageBean;
import bean.ZBJTReturnBean;
import bean.ZBJTSelectImageBean;
import bean.ZBJTSelectImageRspBean;
import bean.ZBJTStartRecordRspBean;
import bean.ZBJTUploadFileBean;
import bean.ZBJTUploadFileRspBean;
import bean.ZBJTUserInfoBean;
import port.JsInterface;
import port.SerializableHashMap;
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

    //点击超链接图片逻辑
    @Override
    public void imageABrowseCB(int index, String url, SerializableHashMap map) {

    }

    //点击图片逻辑
    @Override
    public void imageBrowseCB(int index, String url, SerializableHashMap map) {
    }

    //浙报集团通用JSSDK实现
    @Override
    public void openAppShareMenu(ZBJTOpenAppShareMenuBean bean, ZBJTOpenAppShareMenuRspBean beanRsp, String callback) {

    }

    @Override
    public void updateAppShareData(ZBJTOpenAppShareMenuBean bean, ZBJTReturnBean beanRsp, String callback) {

    }

    @Override
    public void selectImage(ZBJTSelectImageBean bean, ZBJTSelectImageRspBean beanRsp, String callback) {

    }

    @Override
    public void startRecord(ZBJTStartRecordRspBean beanRsp, String callback) {

    }

    @Override
    public void getAppInfo(ZBJTGetAppInfoRspBean BeanRsp, String callback) {

    }

    @Override
    public void getLocation(ZBJTGetLocalRspBean BeanRsp, String callback) {

    }

    @Override
    public void uploadFile(ZBJTUploadFileBean bean, ZBJTUploadFileRspBean beanRsp, String callback) {

    }

    @Override
    public void closeWindow(ZBJTReturnBean beanRsp, String callback) {

    }

    @Override
    public void saveValueToLocal(ZBJTGetValueFromLocalBean bean, ZBJTReturnBean beanRsp, String callback) {

    }

    @Override
    public void getValueFromLocal(ZBJTGetValueFromLocalRspBean beanRsp, String callback) {

    }

    @Override
    public void login(ZBJTReturnBean beanRsp, String callback) {

    }

    @Override
    public void getUserInfo(ZBJTUserInfoBean bean, String callback) {

    }

    @Override
    public void openAppMobile(ZBJTOpenAppMobileBean bean, ZBJTOpenAppMobileRspBean beanRsp, String callback) {

    }

    @Override
    public void modifyUserInfo(ZBJTModifyUserInfoRspBean beanRsp, String callback) {

    }

    @Override
    public void previewImage(ZBJTPreviewImageBean bean, String callback) {

    }

    @Override
    public void listenAppEvent(ZBJTAppEventBean bean, String callback) {

    }
}
