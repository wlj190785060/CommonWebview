package bean;

import java.io.Serializable;

/**
 * 文件上传
 * Created by wanglinjie.
 * create time:2019/2/14  下午4:59
 */
public class ZBJTUploadFileBean implements Serializable {
    private static final long serialVersionUID = 5096286035914299378L;

    /**
     * serverUrl : 用户所需要上传文件到的服务器地址
     * localUrl :用户所需要上传的文件的本地地址
     * fileName :用户上传文件的名称（供服务器识别）
     * inputName :/默认为文件的名字，为空为'file'
     * extend :解析json字符串，key-value添加到上传formdata，参数不合法则传不合法
     */

    private String serverUrl;
    private String localUrl;
    private String fileName;
    private String inputName;
    private String extend;

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }
}
