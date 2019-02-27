package port;

import android.graphics.Bitmap;
import android.webkit.WebView;

/**
 * 设置webview标题和图标
 * Created by wanglinjie.
 * create time:2019/2/27  上午10:55
 */
public interface ISetReceivedTitleIcon {
    //设置标题
    void setReceivedTitle(WebView view, String title);

    //设置图标
    void setReceivedIcon(WebView view, Bitmap icon);

    //设置URL点击事件
    void setReceivedTouchIconUrl(WebView view, String url, boolean precomposed);
}
