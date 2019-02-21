package bean;

import java.io.Serializable;

/**
 * 通用返回bean
 * Created by wanglinjie.
 * create time:2019/2/15  下午3:56
 */
public class ZBJTReturnBean implements Serializable {
    private static final long serialVersionUID = -3078140569184697545L;

    /**
     * code :
     */
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
