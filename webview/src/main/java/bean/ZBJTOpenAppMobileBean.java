package bean;

import java.io.Serializable;

/**
 * 实名认证功能
 * Created by wanglinjie.
 * create time:2019/2/14  下午5:19
 */
public class ZBJTOpenAppMobileBean implements Serializable {
    private static final long serialVersionUID = 5231604980736953039L;

    /**
     * control ://0绑定手机号、1修改手机号
     * 当control为0时，若用户已绑定手机号，调用接口不会弹出页面，直接返回data信息
     * 当control为1时，若用户未绑定手机号，调用接口不会弹出页面，返回失败Code码
     */

    private String control;

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }
}
