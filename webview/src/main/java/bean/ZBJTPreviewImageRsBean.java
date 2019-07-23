package bean;

import java.io.Serializable;
import java.util.Map;

public class ZBJTPreviewImageRsBean implements Serializable {
    private static final long serialVersionUID = 4708881291377835802L;
    public int code;
    public DataBean data;

    public static class DataBean implements Serializable {
        private static final long serialVersionUID = -1762435805853807368L;
        public Map<Integer,String> hasPreviewed;
        public Map<Integer,String> hasSaved;
    }
}
