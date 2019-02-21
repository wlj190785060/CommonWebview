package bean;

import java.io.Serializable;

/**
 * 获取用户信息返回bean
 * Created by wanglinjie.
 * create time:2019/2/15  上午11:06
 */
public class ZBJTGetUserInfoRspBean<T> implements Serializable {
    private static final long serialVersionUID = 6484308864882076036L;

    /**
     * code :
     * data : {"user":{},"login":"","mobile":"","timestamp":"","signature":""}
     */

    private String code;
    /**
     * user: {},// 返回当前用户信息，具体根据各个客户端接口调整,
     * login: true, // 是否登录
     * mobile: false, // 是否绑定手机号
     * timestamp: 1534234508275 //用于签名的时间戳
     * signature: "134ced200d9815baa832027b192a49abdb2920a645428b9f472924c7dfa9d6c6"//签名
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
        //TODO 这里是一个json对象，需要根据各个客户端接口进行调整
        private String user;
        private String login;
        private String mobile;
        private String timestamp;
        private String signature;


        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }
    }
}
