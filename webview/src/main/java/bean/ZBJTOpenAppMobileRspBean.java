package bean;

import java.io.Serializable;

/**
 * 实名认证返回bean
 * Created by wanglinjie.
 * create time:2019/2/15  上午11:17
 */
public class ZBJTOpenAppMobileRspBean implements Serializable {
    private static final long serialVersionUID = -6541610163971257160L;

    /**
     * code :
     * data : {"mobile":""}
     * mobile: "13787654321"//绑定或修改后的手机号
     */

    private String code;
    /**
     * mobile :
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
        private String mobile;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
