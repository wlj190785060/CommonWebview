package port;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.webkit.ValueCallback;

import com.commonwebview.webview.CommonWebView;

/**
 * webview回调辅助抽象类，可扩展
 * Created by wanglinjie.
 * create time:2018/6/20  下午4:59
 */

public abstract class WebviewCBHelper implements LongClickCallBack, ResultCallBack,
        UserAgentDefined, OpenFileChooser,
        ScanerImgCallBack, WebViewSetting {

    //配置js注入，重新设置webview等
    private JsInterface jsInterface;

    /**
     * 需要在子类中创建对象
     */
    private WebviewCBHelper() {
    }

    //设置us
    @Override
    public String getUserAgent() {
        return null;
    }

    //长按逻辑
    @Override
    public void onLongClickCallBack(String imgUrl, boolean isScanerImg) {

    }

    //返回业务逻辑处理
    @Override
    public boolean OnResultCallBack() {
        return false;
    }

    //图片选择器
    @Override
    public void NavToImageSelect(Fragment fragment, int requestCode) {

    }

    //文件管理回调
    @Override
    public boolean openFileResultCallBack(int requestCode, int resultCode, Intent data, ValueCallback<Uri> mUploadMessage, ValueCallback<Uri[]> mUploadMessage21) {
        return false;
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

    //是否需要支持二维码 默认支持
    @Override
    public boolean isNeedScanerImg() {
        return true;
    }

    //关闭二维码识别线程池（如果有的话）
    @Override
    public void OnCancelScanerThread() {

    }

    @CallSuper
    @Override
    public void setWebviewConfig(CommonWebView webview) {
        webview.addJavascriptInterface(jsInterface = new JsInterface(getWebViewJsObject()), getWebViewJsObject());
    }

    public JsInterface getJsObject() {
        return jsInterface;
    }

}
