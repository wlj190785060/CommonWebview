package bean;

import java.io.Serializable;

/**
 * 文件上传返回bean
 * Created by wanglinjie.
 * create time:2019/2/15  上午11:00
 */
public class ZBJTUploadFileRspBean implements Serializable {
    private static final long serialVersionUID = 5809214541414322407L;

    /**
     * code :
     * data : {"response":""}
     */

    private String code;
    /**
     * response :服务器返回内容
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
        private String response;

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }
}
