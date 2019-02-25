package port;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.webkit.ValueCallback;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import com.commonwebview.webview.CommonWebView;

/**
 * webview回调辅助抽象类，可扩展
 * Created by wanglinjie.
 * create time:2018/6/20  下午4:59
 */

abstract public class WebviewCBHelper implements IResultBack, IOpenFileChooser,
        IScanerImg, IWebViewSetting,
        IloadUrl, IWebpageComplete, IProvinTraffic, IDoFullVideo {

    //是否是链接稿
    private boolean isBrowserLink = false;

    //配置js注入，重新设置webview等
    private JsInterface jsInterface;

    //设置us
    abstract public String getUserAgent();

    public boolean isBrowserLink() {
        return isBrowserLink;
    }

    //是否是链接稿,如果是外链稿且需要注入则需要设置该项
    public void setBrowserLinkStat(boolean isBrowserLink) {
         this.isBrowserLink = isBrowserLink;
    }

    //返回业务逻辑处理
    @Override
    public void OnResultCallBack(int requestCode, int resultCode, Intent data) {
    }

    //图片选择器
    @Override
    public void NavToImageSelect(Fragment fragment, int requestCode) {

    }

    //文件管理回调
    @Override
    public void openFileResultCallBack(int requestCode, int resultCode, Intent data, ValueCallback<Uri> mUploadMessage, ValueCallback<Uri[]> mUploadMessage21) {
    }

    //二维码识别业务逻辑
    @Override
    public void OnScanerImg(String imgUrl, boolean isStream) {

    }

    /**
     * 老版本方法，不推荐使用
     *
     * @param imgUrl
     */
    @Deprecated
    @Override
    public void OnScanerImg(String imgUrl) {

    }

    //是否需要支持二维码 默认不支持
    @Override
    public boolean isNeedScanerImg() {
        return true;
    }

    //关闭二维码识别线程池（如果有的话）
    @Override
    public void OnCancelScanerThread() {

    }

    //如果要重写需要调用super()
    @CallSuper
    @Override
    public void setWebviewConfig(CommonWebView webview) {
        if (jsInterface != null) {
            webview.addJavascriptInterface(jsInterface, getWebViewJsObject());
        }
    }

    abstract public String getWebViewJsObject();

    /**
     * 获取js绑定对象
     *
     * @return
     */
    public JsInterface getJsObject() {
        return jsInterface;
    }

    /**
     * 设置js绑定对象
     *
     * @param jsInterface
     */
    public void setJsObject(JsInterface jsInterface) {
        this.jsInterface = jsInterface;
    }

    @Override
    public void shouldOverrideUrlLoading(WebView view, String url) {

    }

    @Override
    public void onWebPageComplete() {

    }

    /**
     * 默认为非省流量模式
     *
     * @return
     */
    abstract public boolean isProvinTrafficMode();

    /**
     * 是否要进行省流量操作
     *
     * @param url
     * @return
     */
    @Override
    public WebResourceResponse doProvinTraffic(String url) {
        return null;
    }

    /**
     * 视频全屏操作
     */
    @Override
    public void doFullVideo() {

    }

    @Override
    public void exitFullVideo() {

    }
}
