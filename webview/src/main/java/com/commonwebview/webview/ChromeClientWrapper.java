package com.commonwebview.webview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.widget.FrameLayout;

import port.WebviewCBHelper;

/**
 * 通用webview
 *
 * @author wanglinjie
 * @date 2018/6/20 09:44.
 */
public class ChromeClientWrapper extends WebChromeClient
        implements WebLifecycleFragment.OnActivityResultCallback {

    private CommonWebView mWebProView;
    private WebChromeClient webChromeClient;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessage21;

    //特殊逻辑处理
    private WebviewCBHelper mHelper;

    /**
     * 选择文件 - result_code
     */
    public final static int FILE_CHOOSER_RESULT_CODE = 10;

    public ChromeClientWrapper(CommonWebView webProView, WebviewCBHelper helper) {
        super();
        mWebProView = webProView;
        mHelper = helper;
    }

    public void setmUploadMessage(ValueCallback<Uri> mUploadMessage) {
        this.mUploadMessage = mUploadMessage;
    }

    public void setmUploadMessage21(ValueCallback<Uri[]> mUploadMessage21) {
        this.mUploadMessage21 = mUploadMessage21;
    }

    public WebChromeClient getWrapper() {
        return webChromeClient;
    }

    public void setWrapper(WebChromeClient webChromeClient) {
        this.webChromeClient = webChromeClient;
    }

    private Activity findAttachActivity() {
        if (mWebProView != null) {
            View parent = (View) mWebProView.getParent();
            if (parent != null) {
                Context context = parent.getContext();
                while (context instanceof ContextWrapper) {
                    if (context instanceof Activity) {
                        return (Activity) context;
                    }
                    context = ((ContextWrapper) context).getBaseContext();
                }
            }
        }
        return null;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        //加载进度为30时就开始操作页面
        if (newProgress > 30) {
            onWebPageComplete();
        }
        if (webChromeClient != null) {
            webChromeClient.onProgressChanged(view, newProgress);
        } else {
            super.onProgressChanged(view, newProgress);
        }
    }

    /**
     * webview结束加载操作
     */
    private void onWebPageComplete() {
        Context context = mWebProView.getContext();
        while (context instanceof ContextThemeWrapper) {
            if (mHelper != null) {
                mHelper.onWebPageComplete(context);
                return;
            }
            context = ((ContextThemeWrapper) context).getBaseContext();
        }
    }


    @Override
    public void onReceivedTitle(WebView view, String title) {
        if (webChromeClient != null) {
            webChromeClient.onReceivedTitle(view, title);
        } else {
            super.onReceivedTitle(view, title);
        }
        if (mHelper != null) {
            mHelper.setReceivedTitle(view, title);
        }

    }

    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        if (webChromeClient != null) {
            webChromeClient.onReceivedIcon(view, icon);
        } else {
            super.onReceivedIcon(view, icon);
        }
        if (mHelper != null) {
            mHelper.setReceivedIcon(view, icon);
        }
    }

    @Override
    public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
        if (webChromeClient != null) {
            webChromeClient.onReceivedTouchIconUrl(view, url, precomposed);
        } else {
            super.onReceivedTouchIconUrl(view, url, precomposed);
        }
        if (mHelper != null) {
            mHelper.setReceivedTouchIconUrl(view, url, precomposed);
        }
    }

    private FrameLayout container;
    private CustomViewCallback customViewCallback;
    private boolean isFullScreen = false;

    /**
     * 支持全屏播放视频
     *
     * @param view
     * @param callback
     */
    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        if (!isFullScreen) {
            isFullScreen = true;

            //全屏业务逻辑
            if (mHelper != null) {
                mHelper.doFullVideo();
            }
            if (container != null) {
                callback.onCustomViewHidden();
                return;
            }
            Activity activity = findAttachActivity();
            if (activity == null) return;

            FrameLayout decor = (FrameLayout) activity.getWindow().getDecorView();
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            container = new WebFullScreenContainer(activity.getApplication());
            decor.addView(container, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            container.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            customViewCallback = callback;
        }
    }

    @Deprecated
    @Override
    public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
        if (webChromeClient != null) {
            webChromeClient.onShowCustomView(view, requestedOrientation, callback);
        } else {
            super.onShowCustomView(view, requestedOrientation, callback);
        }
    }

    @Override
    public void onHideCustomView() {
        if (isFullScreen) {
            isFullScreen = false;

            //退出全屏业务逻辑
            if (mHelper != null) {
                mHelper.exitFullVideo();
            }
            if (container == null) {
                return;
            }

            Activity activity = findAttachActivity();
            if (activity == null) return;
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            FrameLayout decor = (FrameLayout) activity.getWindow().getDecorView();
            decor.removeView(container);
            container = null;
            customViewCallback.onCustomViewHidden();
        }
    }

    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture,
                                  Message resultMsg) {
        if (webChromeClient != null &&
                webChromeClient.onCreateWindow(view, isDialog, isUserGesture, resultMsg)) {
            return true;
        }
        return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
    }

    @Override
    public void onRequestFocus(WebView view) {
        if (webChromeClient != null) {
            webChromeClient.onRequestFocus(view);
        } else {
            super.onRequestFocus(view);
        }
    }

    @Override
    public void onCloseWindow(WebView window) {
        if (webChromeClient != null) {
            webChromeClient.onCloseWindow(window);
        } else {
            super.onCloseWindow(window);
        }
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        if (webChromeClient != null && webChromeClient.onJsAlert(view, url, message, result)) {
            return true;
        }
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        if (webChromeClient != null && webChromeClient.onJsConfirm(view, url, message, result)) {
            return true;
        }
        return super.onJsConfirm(view, url, message, result);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
                              JsPromptResult result) {
        if (webChromeClient != null
                && webChromeClient.onJsPrompt(view, url, message, defaultValue, result)) {
            return true;
        }
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    @Override
    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
        if (webChromeClient != null
                && webChromeClient.onJsBeforeUnload(view, url, message, result)) {
            return true;
        }
        return super.onJsBeforeUnload(view, url, message, result);
    }

    @Override
    public void onExceededDatabaseQuota(String url, String databaseIdentifier, long quota,
                                        long estimatedDatabaseSize, long totalQuota,
                                        WebStorage.QuotaUpdater quotaUpdater) {
        if (webChromeClient != null) {
            webChromeClient.onExceededDatabaseQuota(url, databaseIdentifier, quota,
                    estimatedDatabaseSize, totalQuota, quotaUpdater);
        } else {
            super.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize,
                    totalQuota, quotaUpdater);
        }
    }

    @Override
    public void onReachedMaxAppCacheSize(long requiredStorage, long quota, WebStorage
            .QuotaUpdater quotaUpdater) {
        if (webChromeClient != null) {
            webChromeClient.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
        } else {
            super.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
        }
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions
            .Callback callback) {
        callback.invoke(origin, true, true);
        if (webChromeClient != null) {
            webChromeClient.onGeolocationPermissionsShowPrompt(origin, callback);
        } else {
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
    }

    @Override
    public void onGeolocationPermissionsHidePrompt() {
        if (webChromeClient != null) {
            webChromeClient.onGeolocationPermissionsHidePrompt();
        } else {
            super.onGeolocationPermissionsHidePrompt();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onPermissionRequest(PermissionRequest request) {
        if (webChromeClient != null) {
            webChromeClient.onPermissionRequest(request);
        } else {
            super.onPermissionRequest(request);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onPermissionRequestCanceled(PermissionRequest request) {
        if (webChromeClient != null) {
            webChromeClient.onPermissionRequestCanceled(request);
        } else {
            super.onPermissionRequestCanceled(request);
        }
    }

    @Override
    public boolean onJsTimeout() {
        if (webChromeClient != null && webChromeClient.onJsTimeout()) {
            return true;
        } else {
            return super.onJsTimeout();
        }
    }

    @Override
    public void onConsoleMessage(String message, int lineNumber, String sourceID) {
        if (webChromeClient != null) {
            webChromeClient.onConsoleMessage(message, lineNumber, sourceID);
        }
        super.onConsoleMessage(message, lineNumber, sourceID);
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        if (webChromeClient != null) {
            return webChromeClient.onConsoleMessage(consoleMessage);
        } else {
            return super.onConsoleMessage(consoleMessage);
        }
    }

    @Override
    public Bitmap getDefaultVideoPoster() {
        if (webChromeClient != null) {
            return webChromeClient.getDefaultVideoPoster();
        } else {
            return super.getDefaultVideoPoster();
        }
    }

    @Override
    public View getVideoLoadingProgressView() {
        if (webChromeClient != null) {
            return webChromeClient.getVideoLoadingProgressView();
        }
        return super.getVideoLoadingProgressView();
    }

    @Override
    public void getVisitedHistory(ValueCallback<String[]> callback) {
        if (webChromeClient != null) {
            webChromeClient.getVisitedHistory(callback);
        } else {
            super.getVisitedHistory(callback);
        }
    }

    // For Android 3.0+
    public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
        mUploadMessage = uploadMsg;
        openFileChooser();
    }

    //For Android 4.0-4.3 4.4.4
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        mUploadMessage = uploadMsg;
        openFileChooser();
    }

    // For Android 4.4 无方法。。。

    //For Android 5.0+
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                     FileChooserParams fileChooserParams) {
        if (webChromeClient != null
                && webChromeClient.onShowFileChooser(webView, filePathCallback,
                fileChooserParams)) {
            return true;
        }
        if (mUploadMessage21 != null) {
            mUploadMessage21.onReceiveValue(null);
            mUploadMessage21 = null;
        }
        mUploadMessage21 = filePathCallback;
        openFileChooser();
        return true;
    }

    /**
     * 照片选择器，也抽离出来
     */
    private void openFileChooser() {
        if (mWebProView != null && mWebProView.getFragment() != null) {
            WebLifecycleFragment fragment = mWebProView.getFragment();
            fragment.addOnActivityResultCallback(this);
            //跳转到图片选中页面
            if (mHelper != null) {
                mHelper.NavToImageSelect(fragment, FILE_CHOOSER_RESULT_CODE);
            }
        }
    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mHelper != null) {
            //文件处理
            if (requestCode == FILE_CHOOSER_RESULT_CODE) {
                mHelper.openFileResultCallBack(requestCode, resultCode, data, this, mUploadMessage, mUploadMessage21);
            } else {
                //别的业务逻辑
                mHelper.OnResultCallBack(requestCode, resultCode, data);
            }
        }
        return requestCode == FILE_CHOOSER_RESULT_CODE;
    }

}
