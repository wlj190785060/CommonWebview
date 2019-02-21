package bean;

import java.io.Serializable;

/**
 * 拍照或从手机相册中选图
 * Created by wanglinjie.
 * create time:2019/2/14  下午4:47
 */
public class ZBJTSelectImageBean implements Serializable {
    private static final long serialVersionUID = 3183504733113470132L;

    /**
     * count :可选择图片数量，该版本暂不开发，预留参数
     * dataType :返回值方式，地址或base64，该版本暂不开发，两者皆返回
     * sourceType :选择方式，相册或相机，该版本暂不开发，由用户自行选择
     */

    private String count;
    private String dataType;
    private String sourceType;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }
}
