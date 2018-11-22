package port;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.zjrb.core.utils.click.ClickTracker;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import scanerhelp.HtmlPauseUtils;

/**
 * JS公共类，JS必须要继承该类
 * Created by wanglinjie.
 * create time:2018/11/16  下午3:53
 */
public class JsInterface implements IimgBrower {

    /**
     * 记录是否被预览过的集合
     */
    private String mText;
    //图片
    private String[] mImgSrcs;
    //超链接图片,src->a
    private List<Map<String, String>> mSrcs;
    //音频数量
    private int audioCount = 0;
    //js绑定对象名
    private String jsObject;

    public JsInterface(String jsObject) {
        this.jsObject = jsObject;
    }

    //TODO

    /**
     * @param imgSrcs 获取网页图集
     */
    private void setImgSrcs(String[] imgSrcs) {
        mImgSrcs = imgSrcs;

        if (imgSrcs != null && imgSrcs.length > 0) {
        }

    }

    //TODO

    /**
     * 超链接图片
     *
     * @param srcs
     */
    private void setImgSrcs(List<Map<String, String>> srcs) {
        mSrcs = srcs;
        if (mSrcs != null && mSrcs.size() > 0) {
        }
    }

    public String[] getImgSrcs() {
        return mImgSrcs;
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
     * 获取网页源码
     *
     * @param htmlCode
     */
    public String setAttrHtmlSrc(String htmlCode) {
        return HtmlPauseUtils.parseHandleHtml(jsObject, htmlCode, new HtmlPauseUtils.ImgSrcsCallBack() {
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
     * @param index 点击图片
     */
    @JavascriptInterface
    public void imageBrowse(int index) {
        if (ClickTracker.isDoubleClick()) {
            return;
        }
        imageBrowseCB(mImgSrcs[index]);
    }


    /**
     * @param index 点击超链接图片
     */
    @JavascriptInterface
    public void imageABrowse(int index) {
        if (ClickTracker.isDoubleClick()) {
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
        imageABrowseCB(imgUrl);
    }

    //用户自己实现业务逻辑
    @Override
    public void imageABrowseCB(String url) {

    }

    @Override
    public void imageBrowseCB(String url) {

    }

}
