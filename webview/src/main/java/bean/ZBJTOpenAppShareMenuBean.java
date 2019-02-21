package bean;

import java.io.Serializable;

/**
 * 打开分享
 * Created by wanglinjie.
 * create time:2019/2/14  下午4:34
 */
public class ZBJTOpenAppShareMenuBean implements Serializable {

    private static final long serialVersionUID = 679916787335292724L;
    /**
     * title :分享标题
     * desc :分享描述
     * link :分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
     * imgUrl :分享图标
     * onlyImageShare : 1 是否为图片分享，1为是，0为否，不传为否
     * videoUrl :视频分享，暂不开发
     * audioUrl :音频分享，暂不开发
     */

    private String title;
    private String desc;
    private String link;
    private String imgUrl;
    private String onlyImageShare;
    private String videoUrl;
    private String audioUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getOnlyImageShare() {
        return onlyImageShare;
    }

    public void setOnlyImageShare(String onlyImageShare) {
        this.onlyImageShare = onlyImageShare;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }
}
