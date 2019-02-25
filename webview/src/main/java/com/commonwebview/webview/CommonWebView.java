package com.commonwebview.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import port.IWebJsCallBack;
import port.WebviewCBHelper;
import webutils.WebviewUtils;

/**
 * 通用webview
 *
 * @author wanglinjie
 * @date 2018/6/20 09:44.
 */
public class CommonWebView extends WebView implements IWebJsCallBack, View.OnLongClickListener {

    private WebClientWrapper mWebClientWrapper;
    private ChromeClientWrapper mChromeClientWrapper;

    private WebviewCBHelper helper;
    /**
     * WebView缓存目录
     */
    private final String WEBVIEW_CACHE = "webview";
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
        configWebView();
    }

    public CommonWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            super.removeJavascriptInterface("searchBoxJavaBridge_");
            super.removeJavascriptInterface("accessibility");
            super.removeJavascriptInterface("accessibilityTraversal");
        }
        configWebView();
    }

    public CommonWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            super.removeJavascriptInterface("searchBoxJavaBridge_");
            super.removeJavascriptInterface("accessibility");
            super.removeJavascriptInterface("accessibilityTraversal");
        }
        configWebView();
    }

    public WebviewCBHelper getHelper() {
        return helper;
    }

    public void setHelper(WebviewCBHelper helper) {
        this.helper = helper;
        //重置设置相关参数
        init();
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

        if (helper != null) {
            helper.setWebviewConfig(this);
        }

        if (helper != null && !TextUtils.isEmpty(this.helper.getUserAgent())) {
            getSettings().setUserAgentString(getSettings().getUserAgentString() + "; " + this.helper.getUserAgent());
        }

        if (helper != null) {
            super.setWebViewClient(mWebClientWrapper = new WebClientWrapper(helper));
        }
    }

    /**
     * 绑定对象需要自己加
     */
    private void configWebView() {
        WebSettings settings = getSettings();
        requestFocus(View.FOCUS_DOWN);
        settings.setJavaScriptEnabled(true); // 启用支持javaScript
        settings.setGeolocationEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        settings.setUseWideViewPort(true);
        settings.setSavePassword(false);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(false); // 网页缩放
        // 建议缓存策略为，判断是否有网络，有的话，使用LOAD_DEFAULT,无网络时，使用LOAD_CACHE_ELSE_NETWORK
        if (WebviewUtils.get().isNetworkAvailable(getContext())) {
            if (BuildConfig.DEBUG) { // debuggable时 no_cache
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
        String cacheDirPath = getContext().getFileStreamPath(WEBVIEW_CACHE).getAbsolutePath();//PathManager.get().getWebViewCacheDir();
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
        settings.setUserAgentString(userAgent + "; ");
        setOnLongClickListener(this);

        if (mChromeClientWrapper == null) {
            super.setWebChromeClient(mChromeClientWrapper = new ChromeClientWrapper(this));
        }

        if (mWebClientWrapper == null) {
            super.setWebViewClient(mWebClientWrapper = new WebClientWrapper());
        }

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
                        helper.OnResultCallBack(requestCode,resultCode,data);
                    }
                    return super.onActivityResult(requestCode, resultCode, data);
                }
            });
        }
        mChromeClientWrapper.setWrapper(client);
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        if (mWebClientWrapper == null && helper != null) {
            super.setWebViewClient(mWebClientWrapper = new WebClientWrapper(helper));
        }
        mWebClientWrapper.setWrapper(client);
    }

    //生命周期fragment
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
                    mLifecycleFragment.setWebview(this);
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
     * 长按识别二维码功能，解析二维码
     * 支持本地图片流,需要将回传的string转换成byte[]用Glide加载
     *
     * @param v
     * @return
     */
    @Override
    public boolean onLongClick(View v) {
        if (helper != null && helper.isNeedScanerImg()) {
            final HitTestResult htr = getHitTestResult();//获取所点击的内容
            if (htr.getType() == HitTestResult.IMAGE_TYPE && !TextUtils.isEmpty(htr.getExtra())) {//判断被点击的类型为图片
                //如果是链接
                if (WebviewUtils.get().isHttpUrl(htr.getExtra())) {
                    helper.OnScanerImg(htr.getExtra(), false);
                } else {
                    //本地图片则进行base64解析后输出
                    helper.OnScanerImg(Base64.decode(htr.getExtra(), Base64.DEFAULT).toString(), true);
                }
            }
        }
        return false;
    }


    /**
     * webview暂停时音频也要暂停
     */
    @Override
    public void onPause() {
        super.onPause();
        //暂停音频
        if (helper != null && helper.getJsObject() != null && helper.getJsObject().getAudioCount() > 0) {
            final String execUrl = "javascript:musicPause();";
            post(new Runnable() {
                @Override
                public void run() {
                    loadUrl(execUrl);
                }
            });
        }
        //暂停所有的JS加载
        pauseTimers();
    }

    @Override
    public void onResume() {
        super.onResume();
        resumeTimers();
    }

    @Override
    public void destroy() {
        super.destroy();
        if (getParent() != null && getParent() instanceof ViewGroup) {
            ((ViewGroup) getParent()).removeView(this);
        }
        removeAllViews();
    }

    /**
     * 替换图片
     *
     * @param position 位置
     * @param url      图片url
     */
    @Override
    public void setReplacePic(int position, String url) {
        final String execUrl = "javascript:replaceImage('" + position + "','" +
                url + "')";
        post(new Runnable() {
            @Override
            public void run() {
                loadUrl(execUrl);
            }
        });
    }

    /**
     * 替换超链接图片
     *
     * @param position
     * @param url
     */
    @Override
    public void setReplaceAPic(int position, String url) {
        final String execUrl = "javascript:setReplaceAPic('" + position + "','" +
                url + "')";
        post(new Runnable() {
            @Override
            public void run() {
                loadUrl(execUrl);
            }
        });
    }

}
