使用方法： compile 'cn.daily.android:common-webview:1.0.0.7'

该webview已将可能存在的特殊逻辑和自定义设置进行剥离。

###长按事件

主要是针对长按识别二维码的功能，默认关闭，需要在WebviewCBHelper类中自定义打开
```
public class CommonWebView extends WebView implements View.OnLongClickListener
```

### WebviewCBHelper类：
```
public class WebViewImpl extends WebviewCBHelper {
    //如果不绑定对象，则属于正常的webview加载链接
    @Override
    public String getWebViewJsObject() {
        return "zjxw";
    }
}
```
#调用方法
 ```
 webImpl = new WebViewImpl();
 webImpl.setJsObject(new JsInterfaceImp(webImpl.getWebViewJsObject()));
 webView.setHelper(webImpl);
 ```
该类是抽象类，需要用户自己集成实现。包含了打开文件处理，结果返回处理，二维码扫描结果处理，Webview UA设置。

### JsInterface：

public class JsInterfaceImp extends JsInterface {
    public JsInterfaceImp(String jsObject) {
        super(jsObject);
    }

    @Override
    public void imageABrowseCB(String url) {
        L.e("WLJ" + url);
    }

    @Override
    public void imageBrowseCB(String url) {
        L.e("WLJ" + url);
    }


}

该类为抽象类，用户外部继承以及拓展jssdk方法用，需要通过WebviewCBHelper类中的set方法进行设置。

### 其他：
该webview默认没有JS注入，需要用户自己自己实现webviewCBHelper进行设置才可以。

### 新增功能：
支持链接稿/外链稿中的图片点击，js注入等操作，可以视为普通链接稿来操作。


