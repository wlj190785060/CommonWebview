package port;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.webkit.ValueCallback;

import com.commonwebview.webview.ChromeClientWrapper;

/**
 * 打开相册
 * Created by wanglinjie.
 * create time:2018/6/21  下午3:16
 */

public interface IOpenFileChooser {
    /**
     * 路由到图片选择页面
     *
     * @param fragment
     * @param requestCode
     */
    void NavToImageSelect(Fragment fragment, int requestCode);

    /**
     * 图片选中返回操作
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @return
     */
    void openFileResultCallBack(int requestCode, int resultCode, Intent data, ChromeClientWrapper wrapper, ValueCallback<Uri> mUploadMessage, ValueCallback<Uri[]> mUploadMessage21);
}
