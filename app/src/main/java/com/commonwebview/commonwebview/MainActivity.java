package com.commonwebview.commonwebview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.commonwebview.webview.CommonWebView;

public class MainActivity extends AppCompatActivity {

    private CommonWebView webView;
    private WebViewImpl webImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webview);

        //用户需要设置绑定的对象
        webImpl = new WebViewImpl();
        webImpl.setJsObject(new JsInterfaceImp(webImpl.getWebViewJsObject(), this));
        webView.setHelper(webImpl);

        Intent intent = getIntent();
        if (intent.getIntExtra("key", -1) == 1) {
            webView.loadUrl("http://testmediaadmin.8531.cn/liveH5/index?liveId=777");
        } else if (intent.getIntExtra("key", -1) == 2) {
            webView.loadUrl("http://www.xinhuanet.com/politics/2018-11/21/c_129999219.htm");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }
}
