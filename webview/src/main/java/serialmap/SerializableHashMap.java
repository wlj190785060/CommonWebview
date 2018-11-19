package serialmap;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 序列化map
 * Created by wanglinjie.
 * create time:2017/11/9  下午3:48
 */
public class SerializableHashMap implements Serializable {

    private HashMap<Integer, Boolean> map;

    public HashMap<Integer, Boolean> getMap() {
        if (map == null) {
            map = new HashMap<>();
        }
        return map;
    }

    public void setMap(HashMap<Integer, Boolean> map) {
        this.map = map;
    }
}  