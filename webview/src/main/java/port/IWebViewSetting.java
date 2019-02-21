package port;

import com.commonwebview.webview.CommonWebView;

/**
 * webview setting设置
 * Created by wanglinjie.
 * create time:2018/10/17  下午2:09
 */
public interface IWebViewSetting {
    /**
     * @param webview
     */
    void setWebviewConfig(CommonWebView webview);
    /**
     * 获取通用浙报集团JS绑定对象
     * @return
     */
    String getZBJTWebViewJsObject();
}
