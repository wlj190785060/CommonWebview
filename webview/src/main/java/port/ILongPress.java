package port;

/**
 * webview长按事件回调接口，传递图片地址
 * Created by wanglinjie.
 * create time:2018/4/19  下午3:13
 */

interface ILongPress {
    /**
     * 用于传递图片地址
     * @param imgUrl
     * @param isScanerImg
     */
    void onLongClickCallBack(String imgUrl, boolean isScanerImg);
}
