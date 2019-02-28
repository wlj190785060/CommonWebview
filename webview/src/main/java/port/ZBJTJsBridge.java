package port;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import webutils.JsonUtils;

/**
 * 浙报集团通用JSSDK
 * Created by wanglinjie.
 * create time:2019/2/14  上午10:32
 */
public abstract class ZBJTJsBridge {
    public static final String PREFIX_JS_METHOD_NAME = "ZBJTJSBridge";

    private ZBJTJSInterFace interFace;
    private WebView webview;

    public ZBJTJsBridge(WebView webview) {
        this.webview = webview;
    }

    //接口赋值
    public void setInterFace(ZBJTJSInterFace interFace) {
        this.interFace = interFace;
    }

    /**
     * 通过反射去查找指定的方法
     *
     * @param api      执行的api方法
     * @param json     js传的json数据
     * @param callback 回传js的callback方法
     */
    @JavascriptInterface
    public void invoke(String api, String json, String callback) {
        //未传参则报错
        if (interFace == null) {
            webviewLoadUrl(callback, setErrorRspJson("0"));
            return;
        }
        //api校验
        if (TextUtils.isEmpty(api)) {
            webviewLoadUrl(callback, setErrorRspJson("11001"));
            return;
        }

        try {
            //获取方法
            Method method = getClass().getDeclaredMethod(api, Class.forName("java.lang.String"), Class.forName("java.lang.String"));
            //调用
            method.invoke(getClass().newInstance(), json, callback);
        } catch (NoSuchMethodException e) {
            webviewLoadUrl(callback, setErrorRspJson("11001"));
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    /**
//     * 检测某方法是否存在
//     *
//     * @param api
//     * @return
//     */
//    private boolean checkJSApiValid(String api) {
//        try {
//            Method[] methods = getClass().getMethods();
//            for (Method method : methods) {
//                if (method.getName() != null) {
//                    if (api.equals(method.getName())) {
//                        return true;
//                    }
//                } else {
//                    return false;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    /**
     * webview加载js方法
     *
     * @param callback
     * @param json
     */
    private void webviewLoadUrl(String callback, String json) {
        final String execUrl;
        if (!TextUtils.isEmpty(callback)) {
            execUrl = "javascript:" + callback + "(" + json + ")";
        } else {
            execUrl = "javascript:" + "console.error" + "(" + json + ")";
        }
        if (webview != null) {
            webview.post(new Runnable() {
                @Override
                public void run() {
                    webview.loadUrl(execUrl);
                }
            });
        }

    }

    /**
     * 设置异常返回json
     *
     * @param errorCode 错误码
     */
    private String setErrorRspJson(final String errorCode) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("code", errorCode);
            JSONObject jsonData = new JSONObject();
            jsonObj.put("data", jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return JsonUtils.toJsonString(jsonObj);
    }

    /**
     * 判断当前客户端版本是否支持指定JS接口
     */
    private void checkJSApi(String json, String callback) {
        try {
            List<String> apiList = JsonUtils.parseArray(json, String.class);
            //获取方法
            Method[] methods = getClass().getMethods();
            if (apiList != null && apiList.size() > 0) {
                //api容器
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("code", "1");
                Map<String, String> checkResult = new HashMap<>();
                //JS动态传递方法名
                for (int i = 0; i < apiList.size(); i++) {
                    if (methods != null && methods.length > 0) {
                        for (Method method : methods) {
                            if (method.getName() != null) {
                                if (apiList.get(i).equals(method.getName())) {
                                    checkResult.put(apiList.get(i), "1");
                                } else {
                                    jsonObj.put("code", "0");
                                    checkResult.put(apiList.get(i), "0");
                                }
                            } else {
                                webviewLoadUrl(callback, setErrorRspJson("11001"));
                                return;
                            }
                        }
                    } else {
                        webviewLoadUrl(callback, setErrorRspJson("11001"));
                        return;
                    }
                }
                //组装json
                JSONObject jsonObjData = new JSONObject();
                jsonObjData.put("checkResult", checkResult);
                jsonObj.put("data", jsonObjData);
                webviewLoadUrl(callback, jsonObj.toString());
            } else {
                webviewLoadUrl(callback, setErrorRspJson("11002"));
                return;
            }
        } catch (JsonParseException e) {
            //JSON解析失败则返回参数不合法
            webviewLoadUrl(callback, setErrorRspJson("11002"));
            e.printStackTrace();
        } catch (Exception e) {
            webviewLoadUrl(callback, setErrorRspJson("0"));
            e.printStackTrace();
        }

    }

    /**
     * 打开分享，分享成功后传入回调
     */
    private void openAppShareMenu(String json, String callback) {
        //参数不合法
        if (TextUtils.isEmpty(json)) {
            webviewLoadUrl(callback, setErrorRspJson("11002"));
            return;
        }
        ZBJTOpenAppShareMenuBean bean;
        try {
            bean = JsonUtils.parseObject(json, ZBJTOpenAppShareMenuBean.class);
            interFace.openAppShareMenu(webview, bean, new ZBJTOpenAppShareMenuRspBean(), callback);
        } catch (Exception e) {
            webviewLoadUrl(callback, setErrorRspJson("11002"));
            e.printStackTrace();
        }
    }

    /**
     * 更新原生分享内容接口
     *
     * @return
     */
    private void updateAppShareData(String json, String callback) {
        if (TextUtils.isEmpty(json)) {
            webviewLoadUrl(callback, setErrorRspJson("11002"));
            return;
        }
        ZBJTOpenAppShareMenuBean bean;
        try {
            bean = JsonUtils.parseObject(json, ZBJTOpenAppShareMenuBean.class);
            interFace.updateAppShareData(webview, bean, new ZBJTReturnBean(), callback);
        } catch (Exception e) {
            webviewLoadUrl(callback, setErrorRspJson("11002"));
            e.printStackTrace();
        }
    }

    /**
     * 拍照或从手机相册中选择图片，并将结果回传给Js
     *
     * @return
     */
    private void selectImage(String json, String callback) {
        if (TextUtils.isEmpty(json)) {
            webviewLoadUrl(callback, setErrorRspJson("11002"));
            return;
        }
        ZBJTSelectImageBean bean;
        try {
            bean = JsonUtils.parseObject(json, ZBJTSelectImageBean.class);
            interFace.selectImage(webview, bean, new ZBJTSelectImageRspBean(), callback);
        } catch (Exception e) {
            webviewLoadUrl(callback, setErrorRspJson("11002"));
            e.printStackTrace();
        }
    }

    /**
     * 录音
     *
     * @return
     */
    private void startRecord(String json, String callback) {
        if (TextUtils.isEmpty(json)) {
            webviewLoadUrl(callback, setErrorRspJson("11002"));
            return;
        }
        ZBJTStartRecordBean bean;
        ZBJTStartRecordRspBean.DataBean rspBean;
        try {
            bean = JsonUtils.parseObject(json, ZBJTStartRecordBean.class);
            rspBean = new ZBJTStartRecordRspBean().getData();
            rspBean.setRecordId(bean.getRecordId());
            interFace.startRecord(webview, bean, new ZBJTStartRecordRspBean(), callback);
        } catch (Exception e) {
            webviewLoadUrl(callback, setErrorRspJson("11002"));
            e.printStackTrace();
        }
    }

    /**
     * 获取客户端信息接口
     *
     * @return
     */
    private void getAppInfo(String json, String callback) {
        if (TextUtils.isEmpty(json)) {
            webviewLoadUrl(callback, setErrorRspJson("11002"));
            return;
        }
        ZBJTGetAppInfoBean bean;
        try {
            bean = JsonUtils.parseObject(json, ZBJTGetAppInfoBean.class);
            interFace.getAppInfo(webview, bean, new ZBJTGetAppInfoRspBean(), callback);
        } catch (Exception e) {
            webviewLoadUrl(callback, setErrorRspJson("11002"));
            e.printStackTrace();
        }
    }

    /**
     * 定位
     *
     * @return
     */
    private void getLocation(String json, String callback) {
        interFace.getLocation(webview, new ZBJTGetLocalRspBean(), callback);
    }

    /**
     * 文件上传
     */
    private void uploadFile(String json, String callback) {
        if (TextUtils.isEmpty(json)) {
            webviewLoadUrl(callback, setErrorRspJson("11002"));
            return;
        }
        ZBJTUploadFileBean bean;
        try {
            bean = JsonUtils.parseObject(json, ZBJTUploadFileBean.class);
            interFace.uploadFile(webview, bean, new ZBJTUploadFileRspBean(), callback);
        } catch (Exception e) {
            webviewLoadUrl(callback, setErrorRspJson("11002"));
            e.printStackTrace();
        }
    }

    /**
     * 关闭页面
     *
     * @return
     */
    private void closeWindow(String json, String callback) {
        interFace.closeWindow(webview, new ZBJTReturnBean(), callback);
    }

    /**
     * 利用客户端进行数据Key-Value存储
     *
     * @return
     */
    private void saveValueToLocal(String json, String callback) {
        if (TextUtils.isEmpty(json)) {
            webviewLoadUrl(callback, setErrorRspJson("11002"));
            return;
        }
        ZBJTGetValueFromLocalBean bean;
        try {
            bean = JsonUtils.parseObject(json, ZBJTGetValueFromLocalBean.class);
            interFace.saveValueToLocal(webview, bean, new ZBJTReturnBean(), callback);
        } catch (Exception e) {
            webviewLoadUrl(callback, setErrorRspJson("11002"));
            e.printStackTrace();
        }
    }

    /**
     * 利用客户端进行数据Key-Value取值
     */
    private void getValueFromLocal(String json, String callback) {
        if (TextUtils.isEmpty(json)) {
            webviewLoadUrl(callback, setErrorRspJson("11002"));
            return;
        }
        ZBJTGetValueFromLocalBean bean;
        try {
            bean = JsonUtils.parseObject(json, ZBJTGetValueFromLocalBean.class);
            interFace.getValueFromLocal(webview, bean, new ZBJTGetValueFromLocalRspBean(), callback);
        } catch (Exception e) {
            webviewLoadUrl(callback, setErrorRspJson("11002"));
            e.printStackTrace();
        }
    }

    /**
     * 登录
     *
     * @return
     */
    private void login(String json, String callback) {
        interFace.login(webview, new ZBJTReturnBean(), callback);
    }

    /**
     * 获取当前用户信息
     *
     * @return
     */
    private void getUserInfo(String json, String callback) {
        if (TextUtils.isEmpty(json)) {
            webviewLoadUrl(callback, setErrorRspJson("11002"));
            return;
        }
        interFace.getUserInfo(webview, json, callback);
    }

    /**
     * 实名认证功能-绑定手机号（调用判断手机绑定前先判断登录状态）
     *
     * @return
     */
    private void openAppMobile(String json, String callback) {
        if (TextUtils.isEmpty(json)) {
            webviewLoadUrl(callback, setErrorRspJson("11002"));
            return;
        }
        ZBJTOpenAppMobileBean bean;
        try {
            bean = JsonUtils.parseObject(json, ZBJTOpenAppMobileBean.class);
            interFace.openAppMobile(webview, bean, new ZBJTOpenAppMobileRspBean(), callback);
        } catch (Exception e) {
            webviewLoadUrl(callback, setErrorRspJson("11002"));
            e.printStackTrace();
        }
    }

    /**
     * 修改用户相关信息-[收货名称\收货地址] 调用判断用户收货名称修改前先判断登录状态
     *
     * @return
     */
    private void modifyUserInfo(String json, String callback) {
        if (TextUtils.isEmpty(json)) {
            webviewLoadUrl(callback, setErrorRspJson("11002"));
            return;
        }
        ZBJTModifyUserInfoBean bean;
        try {
            bean = JsonUtils.parseObject(json, ZBJTModifyUserInfoBean.class);
            interFace.modifyUserInfo(webview, bean, new ZBJTModifyUserInfoRspBean(), callback);
        } catch (Exception e) {
            webviewLoadUrl(callback, setErrorRspJson("11002"));
            e.printStackTrace();
        }
    }

}
