package bean;

import java.io.Serializable;

/**
 * Created by wanglinjie.
 * create time:2019/2/15  下午3:48
 */
public class ZBJTOpenAppShareMenuRspBean implements Serializable {
    private static final long serialVersionUID = 8280311266568680416L;

    /**
     * code :
     * data : {"shareTo":""}
     */

    private String code;
    /**
     * shareTo:''，//1，2，3，4，5，6 ：【 1 = 微信 2 = 朋友圈 3 = 钉钉  4 = QQ 5 = 微博 6 = QQ空间】
     */

    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String shareTo;

        public String getShareTo() {
            return shareTo;
        }

        public void setShareTo(String shareTo) {
            this.shareTo = shareTo;
        }
    }
}
