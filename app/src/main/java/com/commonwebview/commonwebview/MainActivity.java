package com.commonwebview.commonwebview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.commonwebview.webview.CommonWebView;

public class MainActivity extends AppCompatActivity {

    private CommonWebView webView, webViewCopy;
    private WebViewImpl webImpl;
    private JsInterfaceImp jsInterfaceImp;
    private Button bt_del, bt_add;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = findViewById(R.id.container);
        webView = findViewById(R.id.webview);
        bt_del = findViewById(R.id.bt_del);
        bt_add = findViewById(R.id.bt_add);
        bt_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.removeView(webView);
            }
        });

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.addView(webViewCopy, 2);
            }
        });
        //用户需要设置绑定的对象
        webImpl = new WebViewImpl();
        jsInterfaceImp = new JsInterfaceImp(webView, webImpl.getWebViewJsObject(), this);
        webImpl.setJsObject(jsInterfaceImp);
        webView.setHelper(webImpl);

//        Intent intent = getIntent();
//        if (intent.getIntExtra("key", -1) == 1) {
        webView.loadUrl("http://news.cctv.com/2019/02/20/ARTIZeKIQfykBbJwhtzMOJp4190220.shtml");
        webViewCopy = webView;
//        } else if (intent.getIntExtra("key", -1) == 2) {
//            webView.loadUrl("http://www.xinhuanet.com/politics/2018-11/21/c_129999219.htm");
//        }
    }
}
