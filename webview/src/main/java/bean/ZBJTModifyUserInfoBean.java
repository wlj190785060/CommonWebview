package bean;

import java.io.Serializable;

/**
 * Created by wanglinjie.
 * create time:2019/2/14  下午5:21
 */
public class ZBJTModifyUserInfoBean implements Serializable {
    private static final long serialVersionUID = 7348044815635315166L;

    /**
     * option :要修改的项 name:名称、address:地址、其他：返回参数不合法Code码
     */

    private String option;

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
