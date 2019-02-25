package port;

import android.content.Intent;

/**
 * webview  onActivityResult结果返回操作
 * Created by wanglinjie.
 * create time:2018/4/19  下午3:13
 */

interface IResultBack {
    /**
     * 可以做登录返回等特殊操作
     * @return
     */
    boolean OnResultCallBack(int requestCode, int resultCode, Intent data);
}
