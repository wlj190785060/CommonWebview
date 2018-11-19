package port;

import com.commonwebview.webview.CommonWebView;

/**
 * webview setting设置
 * Created by wanglinjie.
 * create time:2018/10/17  下午2:09
 */
public interface WebViewSetting {
    /**
     * @param webview
     */
    void setWebviewConfig(CommonWebView webview);

    /**
     * 继承子类必须要实现的方法
     * @return 自定义返回JS绑定对象
     */
    String getWebViewJsObject();
}
