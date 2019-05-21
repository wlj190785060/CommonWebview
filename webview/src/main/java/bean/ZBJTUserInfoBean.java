package bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 用户信息
 * Created by wanglinjie.
 * create time:2019/5/20  下午4:39
 */
public class ZBJTUserInfoBean implements Serializable {
    private static final long serialVersionUID = -629323834967335274L;
    private ArrayList<String> signParaList;

    public ArrayList<String> getsignParaList() {
        return signParaList;
    }

    public void setSignParaList(ArrayList<String> signParaList) {
        this.signParaList = signParaList;
    }

}
