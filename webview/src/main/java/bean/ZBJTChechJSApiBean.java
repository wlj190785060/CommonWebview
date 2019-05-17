package bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wanglinjie.
 * create time:2019/5/17  下午3:36
 */
public class ZBJTChechJSApiBean implements Serializable {
    private static final long serialVersionUID = -1534237882188852189L;
    private ArrayList<String> jsApiList;

    public ArrayList<String> getJsApiList() {
        return jsApiList;
    }

    public void setJsApiList(ArrayList<String> jsApiList) {
        this.jsApiList = jsApiList;
    }


}
