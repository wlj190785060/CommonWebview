package bean;

import java.io.Serializable;

/**
 * 定位返回bean
 * Created by wanglinjie.
 * create time:2019/2/15  上午10:57
 */
public class ZBJTGetLocalRspBean implements Serializable {
    private static final long serialVersionUID = 9104575572303229565L;

    /**
     * code : 1
     * data : {"latitude":"","lontitude":"","accuracy":"","city":"","address":"","speed":"","timestamp":""}
     */

    private String code;
    /**
     * data：JSON对象字符串
     * latitude：31.208117 - 纬度
     * lontitude：121.598552 -经度
     * accuracy：40.0 -精度
     * city：上海 -城市
     * address：上海市浦东新区郭守敬路498号-14号楼-1楼 -地址
     * speed：3 -速度
     * timestamp：-毫秒级时间戳
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
        private String latitude;
        private String lontitude;
        private String accuracy;
        private String city;
        private String address;
        private String speed;
        private String timestamp;

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLontitude() {
            return lontitude;
        }

        public void setLontitude(String lontitude) {
            this.lontitude = lontitude;
        }

        public String getAccuracy() {
            return accuracy;
        }

        public void setAccuracy(String accuracy) {
            this.accuracy = accuracy;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSpeed() {
            return speed;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
