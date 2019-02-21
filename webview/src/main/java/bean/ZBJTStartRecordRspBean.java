package bean;

import java.io.Serializable;

/**
 * 录音结果返回bean
 * Created by wanglinjie.
 * create time:2019/2/15  上午10:45
 */
public class ZBJTStartRecordRspBean implements Serializable {
    private static final long serialVersionUID = 4540040762776852551L;

    /**
     * code : 1|0|12001
     * data : {"audioPath":"","recordId":""}
     * audioPath：‘’ - 录音文件地址
     */

    private String code;
    /**
     * audioPath :
     * recordId :
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
        private String audioPath;
        private String recordId;

        public String getAudioPath() {
            return audioPath;
        }

        public void setAudioPath(String audioPath) {
            this.audioPath = audioPath;
        }

        public String getRecordId() {
            return recordId;
        }

        public void setRecordId(String recordId) {
            this.recordId = recordId;
        }
    }
}
