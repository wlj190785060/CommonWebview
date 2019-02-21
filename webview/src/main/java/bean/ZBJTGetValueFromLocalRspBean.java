package bean;

import java.io.Serializable;

/**
 * K-V返回bean
 * Created by wanglinjie.
 * create time:2019/2/15  上午11:03
 */
public class ZBJTGetValueFromLocalRspBean implements Serializable {
    private static final long serialVersionUID = 1444001382300925949L;

    /**
     * code :
     * data : {"option":"","key":"","value":""}
     */

    private String code;
    /**
     * option:0, // 0表示内存存储，1表示硬盘存储
     * key: "name",// 存储的Key值
     * value: "小明",// 存储的Value值
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
        private String option;
        private String key;
        private String value;

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
