package bean;

import java.io.Serializable;

/**
 * 修改用户信息返回bean
 * Created by wanglinjie.
 * create time:2019/2/15  上午11:20
 */
public class ZBJTModifyUserInfoRspBean implements Serializable {
    private static final long serialVersionUID = -1824728735905085386L;

    /**
     * code :
     * data : {"option":"","value":""}
     */

    private String code;
    /**
     * option:'',//修改结果
     * value:'',//修改结果
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
        private String value;

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
