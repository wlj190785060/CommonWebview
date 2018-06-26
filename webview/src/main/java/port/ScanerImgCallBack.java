package port;

/**
 * 二维码处理逻辑
 * Created by wanglinjie.
 * create time:2018/6/22  下午4:47
 */

interface ScanerImgCallBack {

    /**
     * 二维码解析逻辑
     *
     * @param imgUrl
     */
    void OnScanerImg(final String imgUrl);

    /**
     * 取消解码线程，在页面结束时调用
     */
    void OnCancelScanerThread();
}
