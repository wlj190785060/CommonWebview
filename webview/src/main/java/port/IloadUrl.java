package port;

import android.webkit.WebView;

/**
 * 链接加载处理
 * Created by wanglinjie.
 * create time:2018/12/11  下午3:39
 */
public interface IloadUrl {
    void shouldOverrideUrlLoading(WebView view, String url);
}
