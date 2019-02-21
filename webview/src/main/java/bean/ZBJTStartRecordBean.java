package bean;

import java.io.Serializable;

/**
 * 录音
 * Created by wanglinjie.
 * create time:2019/2/14  下午4:53
 */
public class ZBJTStartRecordBean implements Serializable{

    private static final long serialVersionUID = -2359618262321759925L;
    /**
     * recordId : 1
     */

    private int recordId;

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }
}
