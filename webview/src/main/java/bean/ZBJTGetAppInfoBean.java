package bean;

import java.io.Serializable;

/**
 * 获取客户端信息
 * Created by wanglinjie.
 * create time:2019/2/14  下午4:55
 */
public class ZBJTGetAppInfoBean implements Serializable{

    private static final long serialVersionUID = -6195041533747458046L;
    /**
     * uuid : 1 /默认为0，只有为1时会回传设备唯一编码
     */

    private int uuid;

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }
}
