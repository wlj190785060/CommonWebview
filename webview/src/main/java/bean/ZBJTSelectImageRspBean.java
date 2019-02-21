package bean;

import java.io.Serializable;
import java.util.List;

/**
 * 拍照或者选择图片回调bean
 * Created by wanglinjie.
 * create time:2019/2/15  上午10:39
 */
public class ZBJTSelectImageRspBean implements Serializable {
    private static final long serialVersionUID = -6742580407990441696L;

    /**
     * code : 1|0|12001
     * data : {"imageList":["a","b"],"base64List":["c","d"]}
     * imageList：[]-地址返回数组
     * base64List：[]-base64返回数组
     */

    private String code;
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
        private List<String> imageList;
        private List<String> base64List;

        public List<String> getImageList() {
            return imageList;
        }

        public void setImageList(List<String> imageList) {
            this.imageList = imageList;
        }

        public List<String> getBase64List() {
            return base64List;
        }

        public void setBase64List(List<String> base64List) {
            this.base64List = base64List;
        }
    }
}
