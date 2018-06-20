package scanerhelp;

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

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;

/**
 * 图片二维码解析工具类
 * Created by wanglinjie.
 * create time:2018/4/19  下午3:19
 */

public class ImageScanerUtils {

    private volatile static ImageScanerUtils instance;

    private ImageScanerUtils() {
    }

    public static ImageScanerUtils get() {
        if (instance == null) {
            synchronized (ImageScanerUtils.class) {
                if (instance == null) {
                    instance = new ImageScanerUtils();
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
     * @return 调用方式：ImageScanerUtils.handleQRCodeFormBitmap(getBitmap(sUrl));
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

}
