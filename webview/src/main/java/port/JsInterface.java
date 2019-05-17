package port;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import webutils.ClickTrackerUtils;
import webutils.HtmlPauseUtils;

/**
 * JS公共类，JS若要注入必须要继承该类,默认拥有通用浙报JSSDK的能力
 * Created by wanglinjie.
 * create time:2018/11/16  下午3:53
 */
public abstract class JsInterface extends ZBJTJsBridge implements IimgBrower, ISetImgs {

    /**
     * 记录是否被预览过的集合
     */
    private String mText;
    //普通图片
    private String[] mImgSrcs;
    //超链接图片,map中保存图片地址和文章地址
    private List<Map<String, String>> mSrcs;
    //音频数量
    private int audioCount = 0;
    //js绑定对象名
    private String jsObject;
    private Context mContext;
    /**
     * 记录是否被预览过的集合
     */
    public SerializableHashMap map, map1;

    public JsInterface(WebView webview, String jsObject, Context ctx) {
        super(webview);
        this.jsObject = jsObject;
        mContext = ctx;
    }

    /**
     * @param imgSrcs 获取网页图集
     */
    private void setImgSrcs(String[] imgSrcs) {
        if (imgSrcs != null && imgSrcs.length > 0) {
            mImgSrcs = imgSrcs;
        }
        setImgs();
    }

    public String[] getImgSrcs() {
        return mImgSrcs;
    }

    /**
     * 超链接图片
     *
     * @param srcs
     */
    private void setImgSrcs(List<Map<String, String>> srcs) {
        if (mSrcs != null && mSrcs.size() > 0) {
            mSrcs = srcs;
        }
        setAImgs();
    }

    /**
     * 获取超链接图片集合
     *
     * @return
     */
    public List<Map<String, String>> getAImgSrcs() {
        return mSrcs;
    }


    /**
     * @param text 获取网页文案
     */
    public void setHtmlText(String text) {
        mText = text;
    }

    public String getHtmlText() {
        return mText;
    }

    /**
     * 获取音频数量
     *
     * @return
     */
    public int getAudioCount() {
        return audioCount;
    }

    /**
     * 获取网页body源码
     *
     * @param htmlBody
     */
    public String setAttrHtmlSrc(String htmlBody) {
        return HtmlPauseUtils.parseHandleHtml(mContext, jsObject, htmlBody, new HtmlPauseUtils.ImgSrcsCallBack() {
            @Override
            public void callBack(String[] imgSrcs) {
                //兼容现有浙江新闻/县市报/24小时
                if (imgSrcs != null && imgSrcs.length > 0) {
                    for (int i = 0; i < imgSrcs.length; i++) {
                        if (!TextUtils.isEmpty(imgSrcs[i]) && (imgSrcs[i].contains("?w=")
                                || imgSrcs[i].contains("?width="))) {
                            imgSrcs[i] = imgSrcs[i].split("[?]")[0];
                        }
                    }
                    setImgSrcs(imgSrcs);
                }
            }
        }, new HtmlPauseUtils.ImgASrcsCallBack() {
            @Override
            public void callBack(List<Map<String, String>> imgSrcs) {
                if (imgSrcs != null && imgSrcs.size() > 0) {
                    //设置imageSrc超链接
                    setImgSrcs(imgSrcs);
                }
            }
        }, new HtmlPauseUtils.TextCallBack() {
            @Override
            public void callBack(String text) {
                if (!TextUtils.isEmpty(text)) {
                    setHtmlText(text);
                }
            }
        }, new HtmlPauseUtils.AudioCallBack() {
            @Override
            public void callBack(int count) {
                audioCount = count;
            }
        });
    }

    /**
     * @param index 点击图片逻辑
     */
    @JavascriptInterface
    public void imageBrowse(int index) {
        if (ClickTrackerUtils.isDoubleClick()) {
            return;
        }
        imageBrowseCB(index, mImgSrcs[index], map);
    }


    /**
     * @param index 点击超链接图片逻辑
     */
    @JavascriptInterface
    public void imageABrowse(int index) {
        if (ClickTrackerUtils.isDoubleClick()) {
            return;
        }
        String imgUrl = "";
        if (mSrcs.get(index) != null && !mSrcs.get(index).isEmpty()) {
            Set keys = mSrcs.get(index).keySet();
            if (keys != null) {
                Iterator iterator = keys.iterator();
                while (iterator.hasNext()) {
                    imgUrl = iterator.next().toString();
                }
            }
        }
        imageABrowseCB(index, imgUrl, map1);
    }

    //点击图片的业务逻辑
    @Override
    public void imageABrowseCB(int index, String url, SerializableHashMap map) {

    }

    //点击超链接的业务逻辑
    @Override
    public void imageBrowseCB(int index, String url, SerializableHashMap map) {
    }

    //设置普通图片，默认可以实现一套，也可以外部自己实现
    @Override
    public void setImgs() {
        if (mImgSrcs != null && mImgSrcs.length > 0) {
            map = new SerializableHashMap();
            for (int i = 0; i < mImgSrcs.length; i++) {
                map.getMap().put(i, false);
            }
        }
    }

    //设置超链接图片，默认可以实现一套，也可以外部自己实现
    @Override
    public void setAImgs() {
        if (mSrcs != null && mSrcs.size() > 0) {
            map1 = new SerializableHashMap();
            for (int i = 0; i < mSrcs.size(); i++) {
                map1.getMap().put(i, false);
            }
        }
    }

}
