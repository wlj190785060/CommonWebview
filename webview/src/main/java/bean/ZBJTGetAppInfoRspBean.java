package bean;

import java.io.Serializable;

/**
 * 获取客户端信息返回bean
 * Created by wanglinjie.
 * create time:2019/2/15  上午10:52
 */
public class ZBJTGetAppInfoRspBean implements Serializable {
    private static final long serialVersionUID = -3129870860580016327L;

    /**
     * code : 1
     * data : {"app":"zjxw","version":"050500","networkType":"wifi","device":"iphone8.1/SM-N9008","system":"OS|Android","systemVersion":"8.1|7.1.1","uuid":"01c47915-4777-11d8-bc70-0090272ff725","timestamp":"1497933185000","signature":""}
     */

    private String code;
    /**
     * 返回值data：JSON对象字符串
     * app:'zjxw',//客户端名称
     * <p>
     * version: "050500",// 客户端版本信息,
     * <p>
     * networkType: "wifi",// 返回网络类型2g，3g，4g，wifi,
     * <p>
     * device:'iphone8,1'/'SM-N9008',//设备
     * <p>
     * system:'iOS'|'Android',系统
     * <p>
     * systemVersion:'8.1'|'7.1.1',//系统版本
     * <p>
     * uuid: "01c47915-4777-11d8-bc70-0090272ff725", // 设备唯一DeviceId
     * <p>
     * timestamp: 1497933185000 //用于签名的时间戳
     * <p>
     * signature: "3de16fa75203e652932d1626371669a2cb328d33aa4c11e55d02b261b83a837a"//签名
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
        private String app;
        private String version;
        private String networkType;
        private String device;
        private String system;
        private String systemVersion;
        private String uuid;
        private String timestamp;
        private String signature;

        public String getApp() {
            return app;
        }

        public void setApp(String app) {
            this.app = app;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getNetworkType() {
            return networkType;
        }

        public void setNetworkType(String networkType) {
            this.networkType = networkType;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public String getSystem() {
            return system;
        }

        public void setSystem(String system) {
            this.system = system;
        }

        public String getSystemVersion() {
            return systemVersion;
        }

        public void setSystemVersion(String systemVersion) {
            this.systemVersion = systemVersion;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
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
