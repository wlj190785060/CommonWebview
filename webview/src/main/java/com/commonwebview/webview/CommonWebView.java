package com.commonwebview.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.zgy.utils.utils.NetworkUtil;
import cn.zgy.utils.utils.UIUtils;
import cn.zgy.utils.utils.manager.PathManager;
import port.WebviewCBHelper;

/**
 * 通用webview
 *
 * @author wanglinjie
 * @date 2018/6/20 09:44.
 */
public class CommonWebView extends WebView implements View.OnLongClickListener {

    private WebClientWrapper mWebClientWrapper;
    private ChromeClientWrapper mChromeClientWrapper;

    private WebviewCBHelper helper;

    private static final String FRAGMENT_TAG = "webView_fragment_lifecycle";

    /**
     * 4.4以前的版本存在漏洞
     *
     * @param context
     */
    public CommonWebView(Context context) {
        super(context);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            super.removeJavascriptInterface("searchBoxJavaBridge_");
            super.removeJavascriptInterface("accessibility");
            super.removeJavascriptInterface("accessibilityTraversal");
        }
        init();
    }

    public CommonWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            super.removeJavascriptInterface("searchBoxJavaBridge_");
            super.removeJavascriptInterface("accessibility");
            super.removeJavascriptInterface("accessibilityTraversal");
        }
        init();
    }

    public CommonWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            super.removeJavascriptInterface("searchBoxJavaBridge_");
            super.removeJavascriptInterface("accessibility");
            super.removeJavascriptInterface("accessibilityTraversal");
        }
        init();
    }

    public WebviewCBHelper getHelper() {
        return helper;
    }

    public void setHelper(WebviewCBHelper helper) {
        this.helper = helper;
    }

    //扩展wrapper
    public WebClientWrapper getmWebClientWrapper() {
        return mWebClientWrapper;
    }

    public ChromeClientWrapper getmChromeClientWrapper() {
        return mChromeClientWrapper;
    }

    public WebLifecycleFragment getFragment() {
        return mLifecycleFragment;
    }

    private void init() {
        configWebView();
        if (mChromeClientWrapper == null) {
            super.setWebChromeClient(mChromeClientWrapper = new ChromeClientWrapper(this));
        }
        if (mWebClientWrapper == null) {
            super.setWebViewClient(mWebClientWrapper = new WebClientWrapper());
        }
    }

    /**
     * 绑定对象需要自己加
     */
    private void configWebView() {
        WebSettings settings = getSettings();
        requestFocus(View.FOCUS_DOWN);
        settings.setJavaScriptEnabled(true); // 启用支持javaScript
//        addJavascriptInterface(new ZJXWWebJsInterface(this), "zjxw");
        settings.setGeolocationEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        settings.setUseWideViewPort(true);
        settings.setSavePassword(false);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(false); // 网页缩放
        // 建议缓存策略为，判断是否有网络，有的话，使用LOAD_DEFAULT,无网络时，使用LOAD_CACHE_ELSE_NETWORK
        if (NetworkUtil.isNetworkAvailable(getContext())) {
            if (UIUtils.isDebuggable()) { // debuggable时 no_cache
                settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            } else {
                settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            }
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        // 开启DOM storage API 功能
        settings.setDomStorageEnabled(true);
        // 开启database storage API功能
        settings.setDatabaseEnabled(true);
        String cacheDirPath = PathManager.get().getWebViewCacheDir();
        // 设置数据库缓存路径
        settings.setDatabasePath(cacheDirPath); // API 19 deprecated
        // 设置Application caches缓存目录
        settings.setAppCachePath(cacheDirPath);
        // 开启Application Cache功能
        settings.setAppCacheEnabled(true);
        // WebView在安卓5.0之前默认允许其加载混合网络协议内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // 设置4.2以后版本支持autoPlay，非用户手势促发
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            settings.setMediaPlaybackRequiresUserGesture(false);
        }

        String userAgent = settings.getUserAgentString();
        //自定义ua
        if (helper != null && !TextUtils.isEmpty(this.helper.getUserAgent())) {
            settings.setUserAgentString(userAgent + "; " + this.helper.getUserAgent());
        } else {
            settings.setUserAgentString(userAgent + "; ");
        }
        setOnLongClickListener(this);

    }

    /**
     * 对返回结果做特殊处理，客户端实现接口
     *
     * @param client
     */
    @Override
    public void setWebChromeClient(WebChromeClient client) {
        if (mChromeClientWrapper == null) {
            super.setWebChromeClient(mChromeClientWrapper = new ChromeClientWrapper(this) {
                @Override
                public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
                    if (helper != null) {
                        helper.OnResultCallBack();
                    }
                    return super.onActivityResult(requestCode, resultCode, data);
                }
            });
        }
        mChromeClientWrapper.setWrapper(client);
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        if (mWebClientWrapper == null) {
            super.setWebViewClient(mWebClientWrapper = new WebClientWrapper());
        }
        mWebClientWrapper.setWrapper(client);
    }

    private WebLifecycleFragment mLifecycleFragment;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            if (getContext() instanceof FragmentActivity) {
                FragmentActivity fa = (FragmentActivity) getContext();
                FragmentManager fragmentManager = fa.getSupportFragmentManager();
                if (mLifecycleFragment == null) {
                    mLifecycleFragment = new WebLifecycleFragment();
                }
                fragmentManager.beginTransaction().add(mLifecycleFragment, FRAGMENT_TAG)
                        .commitAllowingStateLoss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        try {
            if (getContext() instanceof FragmentActivity) {
                FragmentActivity fAct = (FragmentActivity) getContext();
                FragmentManager fragmentManager = fAct.getSupportFragmentManager();
                if (mLifecycleFragment != null) {
                    if (!fAct.isDestroyed()) {
                        fragmentManager.beginTransaction().remove(mLifecycleFragment)
                                .commitAllowingStateLoss();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDetachedFromWindow();
    }

    /**
     * @param v
     * @return
     */
    @Override
    public boolean onLongClick(View v) {
        final HitTestResult htr = getHitTestResult();//获取所点击的内容
        if (htr.getType() == HitTestResult.IMAGE_TYPE && helper != null) {//判断被点击的类型为图片
            helper.OnScanerImg(htr.getExtra());
        }
        return false;
    }
}
