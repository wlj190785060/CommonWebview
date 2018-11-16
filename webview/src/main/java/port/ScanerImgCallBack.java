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
     * @param isStream 是否为图片流
     */
    void OnScanerImg(final String imgUrl,final boolean isStream);

    /**
     * 1.0版本的图片解析
     * @param imgUrl
     */
    void OnScanerImg(final String imgUrl);

    /**
     * 取消解码线程，在页面结束时调用
     */
    void OnCancelScanerThread();

    /**
     * 是否需要支持二维码识别
     * @return  true 支持
     */
    boolean isNeedScanerImg();
}
