package com.commonwebview.webview;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * {@link WebViewClient } 包装器
 *
 * @author a_liYa
 * @date 2017/11/10 上午9:46.
 */
class WebClientWrapper extends WebViewClient {

    private WebViewClient webViewClient;

    public WebClientWrapper() {
        super();
    }

    public WebViewClient getWrapper() {
        return webViewClient;
    }

    public void setWrapper(WebViewClient webViewClient) {
        this.webViewClient = webViewClient;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N
                && webViewClient != null
                && webViewClient.shouldOverrideUrlLoading(view, url)) {
            return true;
        }
        //电话、邮件、短信之类
        if (url.startsWith("tel:") || url.startsWith("sms:") || url.startsWith("mailto:")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            view.getContext().startActivity(intent);
            return true;
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if (webViewClient != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && webViewClient.shouldOverrideUrlLoading(view, request)) {
            return true;
        }
        return super.shouldOverrideUrlLoading(view, request);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (webViewClient != null) {
            webViewClient.onPageStarted(view, url, favicon);
        }
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (webViewClient != null) {
            webViewClient.onPageFinished(view, url);
        }
        super.onPageFinished(view, url);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        if (webViewClient != null) {
            webViewClient.onLoadResource(view, url);
        }
        super.onLoadResource(view, url);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onPageCommitVisible(WebView view, String url) {
        if (webViewClient != null) {
            webViewClient.onPageCommitVisible(view, url);
        }
        super.onPageCommitVisible(view, url);
    }

    //这里有特殊逻辑就自己重写方法
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if (webViewClient != null) {
            return webViewClient.shouldInterceptRequest(view, url);
        }
        return super.shouldInterceptRequest(view, url);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest
            request) {
        if (webViewClient != null) {
            return webViewClient.shouldInterceptRequest(view, request);
        }
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
        if (webViewClient != null) {
            webViewClient.onTooManyRedirects(view, cancelMsg, continueMsg);
        } else {
            super.onTooManyRedirects(view, cancelMsg, continueMsg);
        }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String
            failingUrl) {
        if (webViewClient != null) {
            webViewClient.onReceivedError(view, errorCode, description, failingUrl);
        }
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError
            error) {
        if (webViewClient != null) {
            webViewClient.onReceivedError(view, request, error);
        }
        super.onReceivedError(view, request, error);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request,
                                    WebResourceResponse errorResponse) {
        if (webViewClient != null) {
            webViewClient.onReceivedHttpError(view, request, errorResponse);
        }
        super.onReceivedHttpError(view, request, errorResponse);
    }

    @Override
    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        if (webViewClient != null) {
            webViewClient.onFormResubmission(view, dontResend, resend);
        } else {
            super.onFormResubmission(view, dontResend, resend);
        }
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        if (webViewClient != null) {
            webViewClient.doUpdateVisitedHistory(view, url, isReload);
        }
        super.doUpdateVisitedHistory(view, url, isReload);
    }

    /**
     * 对于部分网页证书报错的问题
     * @param view
     * @param handler
     * @param error
     */
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//        if (webViewClient != null) {
//            webViewClient.onReceivedSslError(view, handler, error);
//        } else {
//            super.onReceivedSslError(view, handler, error);
//        }
        // 建议
        handler.proceed(); // 针对https，忽略证书错误，相当于信任所有证书
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        if (webViewClient != null) {
            webViewClient.onReceivedClientCertRequest(view, request);
        } else {
            super.onReceivedClientCertRequest(view, request);
        }
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host,
                                          String realm) {
        if (webViewClient != null) {
            webViewClient.onReceivedHttpAuthRequest(view, handler, host, realm);
        } else {
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }
    }

    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        if (webViewClient != null && webViewClient.shouldOverrideKeyEvent(view, event)) {
            return true;
        }
        return super.shouldOverrideKeyEvent(view, event);
    }

    @Override
    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        if (webViewClient != null) {
            webViewClient.onUnhandledKeyEvent(view, event);
        } else {
            super.onUnhandledKeyEvent(view, event);
        }
    }

    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        if (webViewClient != null) {
            webViewClient.onScaleChanged(view, oldScale, newScale);
        }
        super.onScaleChanged(view, oldScale, newScale);
    }

    @Override
    public void onReceivedLoginRequest(WebView view, String realm, String account, String
            args) {
        if (webViewClient != null) {
            webViewClient.onReceivedLoginRequest(view, realm, account, args);
        }
        super.onReceivedLoginRequest(view, realm, account, args);
    }
}
