package port;

/**
 * webview回调辅助类
 * Created by wanglinjie.
 * create time:2018/6/20  下午4:59
 */

 public class WebviewCBHelper implements LongClickCallBack, ResultCallBack, UserAgentDefined {
    @Override
    public String getUserAgent() {
        return null;
    }

    @Override
    public void onLongClickCallBack(String imgUrl, boolean isScanerImg) {

    }

    @Override
    public boolean OnResultCallBack() {
        return false;
    }
}
