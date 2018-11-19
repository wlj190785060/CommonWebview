package scanerhelp;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * webview工具类
 * Created by wanglinjie.
 * create time:2018/4/19  下午3:19
 */

final public class WebviewUtils {

    private volatile static WebviewUtils instance;

    private WebviewUtils() {
    }

    public static WebviewUtils get() {
        if (instance == null) {
            synchronized (WebviewUtils.class) {
                if (instance == null) {
                    instance = new WebviewUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 根据地址获取网络图片
     *
     * @param sUrl 图片地址
     * @return
     */
    public Bitmap getBitmap(String sUrl) {
        try {
            URL url = new URL(sUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 校验二维码
     *
     * @param bitmap
     * @return 调用方式：WebviewUtils.handleQRCodeFormBitmap(getBitmap(sUrl));
     */
    public Result handleQRCodeFormBitmap(Bitmap bitmap) {
        if (bitmap == null) return null;
        //获取图片宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] data = new int[width * height];
        bitmap.getPixels(data, 0, width, 0, 0, width, height);
        RGBLuminanceSource source = new RGBLuminanceSource(width, height, data);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();

        Hashtable<DecodeHintType, String> hints = new Hashtable<>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        Result result = null;
        try {
            try {
                result = reader.decode(bitmap1, hints);
            } catch (ChecksumException e) {
                e.printStackTrace();
            } catch (FormatException e) {
                e.printStackTrace();
            }

        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 判断字符串是否为URL
     *
     * @param urls 用户头像key
     * @return true:是URL、false:不是URL
     */
    public boolean isHttpUrl(String urls) {
        boolean isurl = false;
        String regex = "(((https|http)?://)?([a-z0-9]+[.])|(www.))"
                + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";//设置正则表达式

        Pattern pat = Pattern.compile(regex.trim());//比对
        Matcher mat = pat.matcher(urls.trim());
        isurl = mat.matches();//判断是否匹配
        if (isurl) {
            isurl = true;
        }
        return isurl;
    }

    /**
     * @return Properties对象
     */
    public Properties getConfigProperties() {
        Properties props = new Properties();
        InputStream in = WebviewUtils.class.getResourceAsStream("/publish.properties");
        try {
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    /**
     * 判断当前 context 是否为夜间模式.
     *
     * @param context The current context.
     * @return true : 表示为夜间模式.
     */
    public boolean isUiModeNight(Context context) {
        final Configuration config = context.getResources().getConfiguration();
        final int currentUiMode = config.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return Configuration.UI_MODE_NIGHT_YES == currentUiMode;
    }

}
