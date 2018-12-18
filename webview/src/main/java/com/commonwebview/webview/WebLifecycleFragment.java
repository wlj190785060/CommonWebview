package com.commonwebview.webview;

import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * WebView生命周期管理
 *
 * @author wanglinjie
 * @date 2018/6/20 10:09.
 */
public class WebLifecycleFragment extends Fragment {

    private List<OnActivityResultCallback> mCallbacks = new ArrayList<>();
    private CommonWebView mWebview;

    public void setWebview(CommonWebView webview){
        mWebview = webview;
    }

    public void addOnActivityResultCallback(OnActivityResultCallback callback) {
        if (callback != null) {
            mCallbacks.add(callback);
        }
    }

    public void removeOnActivityResultCallback(OnActivityResultCallback callback) {
        if (callback != null) {
            mCallbacks.remove(callback);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!mCallbacks.isEmpty()) {
            List<OnActivityResultCallback> copy = new ArrayList(mCallbacks);
            for (OnActivityResultCallback callback : copy) {
                if (callback != null && callback.onActivityResult(requestCode, resultCode, data))
                    mCallbacks.remove(callback);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mWebview != null){
            mWebview.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mWebview != null){
            mWebview.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mWebview != null){
            mWebview.destroy();
        }
    }

    /**
     * {@link android.app.Activity#onActivityResult(int, int, Intent)} callback
     *
     * @author wanglinjie
     * @date 2018/6/20 上午10:12.
     */
    public interface OnActivityResultCallback {

        boolean onActivityResult(int requestCode, int resultCode, Intent data);
    }

}
