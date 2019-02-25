package port;

/**
 * Created by wanglinjie.
 * create time:2018/11/19  下午4:15
 */
public interface IimgBrower {
    /**
     * 超链接图片点击
     *
     * @param index
     * @param url 图片地址
     * @param map 记录图片是否已经被预览
     */
    void imageABrowseCB(int index,String url,SerializableHashMap map);

    /**
     * 非超链接图片点击
     *
     * @param index
     * @param url 图片地址
     * @param map 记录图片是否已经被预览
     */
    void imageBrowseCB(int index,String url,SerializableHashMap map);
}
