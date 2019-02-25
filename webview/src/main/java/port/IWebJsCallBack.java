package port;

/**
 * 替换webview中的图片
 * Created by wanglinjie.
 * create time:2018/12/14  下午3:20
 */
public interface IWebJsCallBack {
    //具体逻辑在外部调用执行
    void setReplacePic(int position, String url);

    void setReplaceAPic(int position, String url);
}
