package com.commonwebview.commonwebview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.commonwebview.webview.CommonWebView;

public class MainActivity extends AppCompatActivity {

    private CommonWebView webView;
    private WebViewImpl webImpl;
    private JsInterfaceImp jsInterfaceImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webview);

        //用户需要设置绑定的对象
        webImpl = new WebViewImpl();
        //注入对象,如果需要使用getZBJTWebViewJsObject
        jsInterfaceImp = new JsInterfaceImp(webView, webImpl.getWebViewJsObject(), this);
//        jsInterfaceImp.setInterFace(jsInterfaceImp);
        webImpl.setJsObject(jsInterfaceImp);
        webView.setHelper(webImpl);

        Intent intent = getIntent();
        if (intent.getIntExtra("key", -1) == 1) {
            webView.loadUrl("http://news.cctv.com/2019/02/20/ARTIZeKIQfykBbJwhtzMOJp4190220.shtml");
        } else if (intent.getIntExtra("key", -1) == 2) {
            webView.loadUrl("http://www.xinhuanet.com/politics/2018-11/21/c_129999219.htm");
        }
    }
}
