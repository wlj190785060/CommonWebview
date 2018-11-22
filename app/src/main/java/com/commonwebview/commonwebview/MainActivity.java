package com.commonwebview.commonwebview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.commonwebview.webview.CommonWebView;
import com.zjrb.core.utils.UIUtils;

public class MainActivity extends AppCompatActivity {

    private CommonWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webview);
        //用户需要设置绑定的对象
        webView.setHelper(new WebViewImpl());
        Intent intent = getIntent();
        if(intent.getIntExtra("key",-1) == 1){
            webView.loadUrl("http://news.cctv.com/2018/11/19/ARTIXGtItcBfVVLkngV1D2Cb181119.shtml");
        }else if(intent.getIntExtra("key",-1) == 2){
            webView.loadUrl("http://www.xinhuanet.com/politics/2018-11/21/c_129999219.htm");
        }
    }
}
