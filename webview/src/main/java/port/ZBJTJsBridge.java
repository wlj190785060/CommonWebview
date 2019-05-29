package port;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

import bean.ZBJTChechJSApiBean;
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
import bean.ZBJTUserInfoBean;
import webutils.JsonUtils;

/**
 * 浙报集团通用JSSDK
 * Created by wanglinjie.
 * create time:2019/2/14  上午10:32
 */
public class ZBJTJsBridge {
    //浙江新闻6.0新版本JS调用对象名
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

        switch (api) {
            case "checkJSApi":
                checkJSApi(json, callback);
                break;
            case "uploadFile":
                uploadFile(json, callback);
                break;
            case "selectImage":
                selectImage(json, callback);
                break;
            case "saveValueToLocal":
                saveValueToLocal(json, callback);
                break;
            case "getLocation":
                getLocation(json, callback);
                break;
            case "updateAppShareData":
                updateAppShareData(json, callback);
                break;
            case "startRecord":
                startRecord(json, callback);
                break;
            case "openAppMobile":
                openAppMobile(json, callback);
                break;
            case "getAppInfo":
                getAppInfo(json, callback);
                break;
            case "login":
                login(json, callback);
                break;
            case "modifyUserInfo":
                modifyUserInfo(json, callback);
                break;
            case "closeWindow":
                closeWindow(json, callback);
                break;
            case "getValueFromLocal":
                getValueFromLocal(json, callback);
                break;
            case "getUserInfo":
                getUserInfo(json, callback);
                break;
            case "openAppShareMenu":
                openAppShareMenu(json, callback);
                break;
            default:
                webviewLoadUrl(callback, setErrorRspJson("11001"));
                break;
        }
    }

    /**
     * webview加载js方法
     *
     * @param callback
     * @param json
     */
    private void webviewLoadUrl(String callback, String json) {
        final String execUrl;
        if (!TextUtils.isEmpty(callback)) {
            execUrl = "javascript:" + callback + "('" + json + "');";
        } else {
            execUrl = "javascript:" + "console.error" + "('" + json + "');";
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
            ZBJTChechJSApiBean bean = JsonUtils.parseObject(json, ZBJTChechJSApiBean.class);

            //获取方法
            Method[] methods = ZBJTJsBridge.class.getDeclaredMethods();
            ArrayList<String> arrayMethods = new ArrayList<>();
            if (bean != null && !bean.getJsApiList().isEmpty()) {
                //api容器
                JsonObject jsonObj = new JsonObject();
                jsonObj.addProperty("code", "1");
                JsonObject checkResult = new JsonObject();
                jsonObj.add("data", checkResult);
                JsonObject apiBean = new JsonObject();
                checkResult.add("checkResult", apiBean);
                if (methods != null && methods.length > 0) {
                    for (int i = 0; i < methods.length; i++) {
                        arrayMethods.add(methods[i].getName());
                    }

                    for (int i = 0; i < bean.getJsApiList().size(); i++) {
                        if (arrayMethods != null && !arrayMethods.isEmpty()) {
                            if (arrayMethods.contains(bean.getJsApiList().get(i))) {
                                apiBean.addProperty(bean.getJsApiList().get(i), "1");
                            } else {
                                jsonObj.addProperty("code", "0");
                                apiBean.addProperty(bean.getJsApiList().get(i), "0");
                            }
                        } else {
                            webviewLoadUrl(callback, setErrorRspJson("11001"));
                            return;
                        }
                    }
                }
                webviewLoadUrl(callback, String.valueOf(jsonObj));//JsonUtils.toJsonString(jsonObj)
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
            interFace.openAppShareMenu(bean, new ZBJTOpenAppShareMenuRspBean(), callback);
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
            interFace.updateAppShareData(bean, new ZBJTReturnBean(), callback);
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
            interFace.selectImage(bean, new ZBJTSelectImageRspBean(), callback);
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
        ZBJTStartRecordRspBean rspBean;
        ZBJTStartRecordRspBean.DataBean dataBean = new ZBJTStartRecordRspBean.DataBean();
        try {
            //将js传递的RecordId设置到回传参数rsp中
            bean = JsonUtils.parseObject(json, ZBJTStartRecordBean.class);
            rspBean = new ZBJTStartRecordRspBean();
            rspBean.setData(dataBean);
            if (bean != null) {
                dataBean.setRecordId(bean.getRecordId());
            }
            interFace.startRecord(rspBean, callback);
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
        ZBJTGetAppInfoRspBean rspBean;
        ZBJTGetAppInfoRspBean.DataBean dataBean = new ZBJTGetAppInfoRspBean.DataBean();
        try {
            bean = JsonUtils.parseObject(json, ZBJTGetAppInfoBean.class);
            rspBean = new ZBJTGetAppInfoRspBean();
            rspBean.setData(dataBean);
            if (bean != null) {
                dataBean.setUuid(bean.getUuid());
            }
            interFace.getAppInfo(rspBean, callback);
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
        interFace.getLocation(new ZBJTGetLocalRspBean(), callback);
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
            interFace.uploadFile(bean, new ZBJTUploadFileRspBean(), callback);
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
        interFace.closeWindow(new ZBJTReturnBean(), callback);
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
            interFace.saveValueToLocal(bean, new ZBJTReturnBean(), callback);
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
        ZBJTGetValueFromLocalRspBean rspBean;
        ZBJTGetValueFromLocalRspBean.DataBean dataBean = new ZBJTGetValueFromLocalRspBean.DataBean();
        try {
            bean = JsonUtils.parseObject(json, ZBJTGetValueFromLocalBean.class);
            rspBean = new ZBJTGetValueFromLocalRspBean();
            rspBean.setData(dataBean);
            if (bean != null) {
                dataBean.setOption(bean.getOption());
                dataBean.setKey(bean.getKey());
            }
            interFace.getValueFromLocal(rspBean, callback);
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
        interFace.login(new ZBJTReturnBean(), callback);
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
        ZBJTUserInfoBean bean;
        try {
            bean = JsonUtils.parseObject(json, ZBJTUserInfoBean.class);
            interFace.getUserInfo(bean, callback);
        } catch (Exception e) {
            webviewLoadUrl(callback, setErrorRspJson("11002"));
            e.printStackTrace();
        }

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
            interFace.openAppMobile(bean, new ZBJTOpenAppMobileRspBean(), callback);
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
        ZBJTModifyUserInfoRspBean rspBean;
        ZBJTModifyUserInfoRspBean.DataBean dataBean = new ZBJTModifyUserInfoRspBean.DataBean();
        try {
            bean = JsonUtils.parseObject(json, ZBJTModifyUserInfoBean.class);
            rspBean = new ZBJTModifyUserInfoRspBean();
            rspBean.setData(dataBean);
            if (bean != null) {
                dataBean.setOption(bean.getOption());
            }
            interFace.modifyUserInfo(rspBean, callback);
        } catch (Exception e) {
            webviewLoadUrl(callback, setErrorRspJson("11002"));
            e.printStackTrace();
        }
    }

}
