
使用方法： compile 'cn.daily.android:common-webview:1.0.0-SNAPSHOT'

该webview已将可能存在的特殊逻辑和自定义设置进行剥离。



### WebviewCBHelper类：
该类是抽象类，需要用户自己集成实现。包含了打开文件处理，结果返回处理，二维码扫描结果处理，Webview UA设置。

### 其他：
该webview默认没有JS注入，需要用户自己进行注入调用addJavascriptInterface()。
其他处理用户可以自行通过getSettings对webview进行配置。


