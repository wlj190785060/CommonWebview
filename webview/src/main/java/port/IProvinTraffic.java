package port;

import android.webkit.WebResourceResponse;

/**
 * 省流量模式业务
 * Created by wanglinjie.
 * create time:2019/2/21  下午3:59
 */
public interface IProvinTraffic {
    /**
     * 省流量模式操作
     */
    WebResourceResponse doProvinTraffic(String url);
}
