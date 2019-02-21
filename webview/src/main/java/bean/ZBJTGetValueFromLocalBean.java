package bean;

import java.io.Serializable;

/**
 * 利用客户端进行数据K-V取值
 * Created by wanglinjie.
 * create time:2019/2/14  下午5:05
 */
public class ZBJTGetValueFromLocalBean implements Serializable {
    private static final long serialVersionUID = 1384901606134832625L;

    /**
     * option : 0 存储方式，0为内存存储、1硬盘存储
     * key : KEY
     */

    private String option;
    private String key;

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
}
