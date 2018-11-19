package port;

/**
 * Created by wanglinjie.
 * create time:2018/11/19  下午4:15
 */
public interface IimgBrower {
    /**
     * 超链接图片点击
     *
     * @param url 图片地址
     */
    void imageABrowseCB(String url);

    /**
     * 非超链接图片点击
     *
     * @param url 图片地址
     */
    void imageBrowseCB(String url);
}
