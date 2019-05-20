package port;

import android.webkit.WebView;

import java.io.Serializable;

import bean.ZBJTGetAppInfoRspBean;
import bean.ZBJTGetLocalRspBean;
import bean.ZBJTGetValueFromLocalRspBean;
import bean.ZBJTModifyUserInfoRspBean;
import bean.ZBJTOpenAppMobileRspBean;
import bean.ZBJTOpenAppShareMenuRspBean;
import bean.ZBJTReturnBean;
import bean.ZBJTSelectImageRspBean;
import bean.ZBJTStartRecordRspBean;
import bean.ZBJTUploadFileRspBean;
import webutils.JsonUtils;

/**
 * 给外部使用的JS回调处理类，不允许拓展
 * Created by wanglinjie.
 * create time:2019/2/28  上午9:05
 */
final public class JsInterfaceCallBack implements Serializable{

    private static final long serialVersionUID = -415618058441243590L;
    private WebView webview;

    public JsInterfaceCallBack(WebView webview) {
        this.webview = webview;
    }

    //执行具体的js操作
    private void exeJs(final WebView webview, Object beanRsp, String callback) {
        final String execUrl = "javascript:" + callback + "('" + JsonUtils.toJsonString(beanRsp) + "');";
        webview.post(new Runnable() {
            @Override
            public void run() {
                webview.loadUrl(execUrl);
            }
        });
    }

    //打开分享
    public void openAppShareMenu(ZBJTOpenAppShareMenuRspBean beanRsp, String callback) {
        exeJs(webview, beanRsp, callback);
    }

    //更新分享信息
    public void updateAppShareData(ZBJTReturnBean beanRsp, String callback) {
        exeJs(webview, beanRsp, callback);
    }

    //选择照片
    public void selectImage(ZBJTSelectImageRspBean beanRsp, String callback) {
        exeJs(webview, beanRsp, callback);
    }


    public void startRecord(ZBJTStartRecordRspBean beanRsp, String callback) {
        exeJs(webview, beanRsp, callback);
    }

    public void getAppInfo(ZBJTGetAppInfoRspBean beanRsp, String callback) {
        exeJs(webview, beanRsp, callback);
    }

    public void getLocation(ZBJTGetLocalRspBean beanRsp, String callback) {
        exeJs(webview, beanRsp, callback);
    }

    public void uploadFile(ZBJTUploadFileRspBean beanRsp, String callback) {
        exeJs(webview, beanRsp, callback);
    }

    public void closeWindow(ZBJTReturnBean beanRsp, String callback) {
        exeJs(webview, beanRsp, callback);
    }

    public void saveValueToLocal(ZBJTReturnBean beanRsp, String callback) {
        exeJs(webview, beanRsp, callback);
    }

    public void getValueFromLocal(ZBJTGetValueFromLocalRspBean beanRsp, String callback) {
        exeJs(webview, beanRsp, callback);
    }

    public void login(ZBJTReturnBean beanRsp, String callback) {
        exeJs(webview, beanRsp, callback);
    }

    public void getUserInfo(String json, String callback) {
        final String execUrl = "javascript:" + callback + "('" + json + "');";
        webview.post(new Runnable() {
            @Override
            public void run() {
                webview.loadUrl(execUrl);
            }
        });
    }

    public void openAppMobile(ZBJTOpenAppMobileRspBean beanRsp, String callback) {
        exeJs(webview, beanRsp, callback);
    }

    public void modifyUserInfo(ZBJTModifyUserInfoRspBean beanRsp, String callback) {
        exeJs(webview, beanRsp, callback);
    }
}
