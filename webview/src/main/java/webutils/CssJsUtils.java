package webutils;

import android.annotation.TargetApi;
import android.content.Context;
import android.text.TextUtils;
import android.webkit.WebResourceRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import port.WebviewCBHelper;

/**
 * css及js注入辅助工具类
 * Created by wanglinjie.
 * create time:2018/11/21  上午11:18
 */
final public class CssJsUtils {
    //是否注入成功
    private boolean isInject = false;
    private String mDefaultEncoding = "UTF-8";
    private volatile static CssJsUtils instance;
    private WebviewCBHelper mHelper;
    private Context mContext;

    private CssJsUtils() {
    }

    private CssJsUtils(Context ctx) {
        mContext = ctx.getApplicationContext();
    }

    public static CssJsUtils get(Context ctx) {
        if (instance == null) {
            synchronized (CssJsUtils.class) {
                if (instance == null) {
                    instance = new CssJsUtils(ctx);
                }
            }
        }
        return instance;
    }

    public boolean isInject() {
        return isInject;
    }
    /**
     * 传递helper
     *
     * @param mHelper
     * @return
     */
    public CssJsUtils setmHelper(WebviewCBHelper mHelper) {
        this.mHelper = mHelper;
        return this;
    }
    /**
     * 根据给定的网址，获取返回的html注入css和js
     *
     * @param
     * @return
     * @throw
     */
    public String getUrlData(WebviewCBHelper helper, WebResourceRequest request, String cookiesUrl, String cssPath, String jsPath) {
        mHelper = helper;
        String page;
        if (request == null) {
            page = getHtml(cookiesUrl);
        } else {
            page = getHtml(request, cookiesUrl);
        }
        String css = "";
        if (cssPath != null && !cssPath.isEmpty()) {
            css = buildCss(cssPath);
        }
        String js = "";
        if (jsPath != null && !jsPath.isEmpty()) {
            js = buildJS(jsPath);
        }
        page = inject(page, css, js);
        return page;
    }

    /**
     * 请求指定的url并返回html页字符串
     * 对于5.0及以上适用该API
     *
     * @param
     * @return
     * @throw
     */

    @TargetApi(21)
    private String getHtml(WebResourceRequest request, String cookies) {
        StringBuilder total = new StringBuilder();
        try {
            URL url = new URL(request.getUrl().toString());
            Map<String, String> headers = request.getRequestHeaders();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设置请求的header
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            //扫码请求登录网页cookies一定要设置，不然后台判断登录状态会出错
            if (cookies != null && !cookies.isEmpty()) {
                connection.setRequestProperty("Cookie", cookies);
            }
            connection.setRequestMethod(request.getMethod());
            connection.connect();
            InputStream is = connection.getInputStream();
            String encoding = connection.getContentEncoding();
            if (encoding == null) {
                encoding = mDefaultEncoding;
            }
            BufferedReader r = new BufferedReader(new InputStreamReader(is, encoding));
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            is.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return total.toString();
    }


    /**
     * 根据url获取html代码
     *
     * @param urlLink
     * @return
     * @throws Exception
     */
    private String getHtml(String urlLink) {
        StringBuilder total = new StringBuilder();
        try {
            URL url = new URL(urlLink);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            InputStream inStream = conn.getInputStream();
            String encoding = conn.getContentEncoding();
            if (encoding == null) {
                encoding = mDefaultEncoding;
            }

            BufferedReader r = new BufferedReader(new InputStreamReader(inStream, encoding));
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            inStream.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total.toString();
    }

    /**
     * 根据指定路径读取css文件，需要支持现有的浙江新闻详情页的模式
     *
     * @param
     * @return
     * @throw
     */
    private String buildCss(String css) {
        if (mContext == null) return null;
        StringBuilder contents = new StringBuilder();

        InputStreamReader reader;
        try {
            reader = new InputStreamReader(mContext.getAssets().open(css), mDefaultEncoding);
            BufferedReader br = new BufferedReader(reader);

            String line;
            while ((line = br.readLine()) != null) {
                contents.append(line);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "<style  charset=\"UTF-8\" rel=\"stylesheet\" type=\"text/css\">" + contents.toString().trim().replace("\n", "") + "</style>";
    }

    /**
     * 根据给定路径读取js文件
     *
     * @param
     * @return
     * @throw
     */
    private String buildJS(String jsPath) {
        if (mContext == null) return null;
        StringBuilder contents = new StringBuilder();

        InputStreamReader reader;
        try {
            reader = new InputStreamReader(mContext.getAssets().open(jsPath), mDefaultEncoding);
            BufferedReader br = new BufferedReader(reader);

            String line;
            while ((line = br.readLine()) != null) {
                contents.append(line);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "<script type=\"text/javascript\" charset=\"UTF-8\">" + contents.toString().trim().replace("\n", "") + "</script>";
    }

    /**
     * 给定的HTML中插入css和js代码
     *
     * @param page 页面源码
     * @param css  注入的css
     * @param js   注入的js
     * @return
     */
    private String inject(String page, String css, String js) {
        //在body中添加点击属性
        String start = page.substring(0, page.indexOf("</head>")) + "</head>";
        String body = page.substring(page.indexOf("<body>") + 6, page.indexOf("</body>"));
        //</body>之后余下内容
        String remain = page.substring(page.indexOf("</body>"), page.length());
        //更新body
        if (mHelper != null) {
            body = mHelper.getJsObject().setAttrHtmlSrc(body);
        }
        //更新后的html内容
        page = start + "<body>" + body + remain;

        //注入css,加到head的最后
        String res;
        int headEnd = page.indexOf("</head>");
        if (headEnd > 0) {
            res = page.substring(0, headEnd) + css + page.substring(headEnd, page.length());
            isInject = true;
        } else {
            res = "<head>" + css + "</head>" + page;
        }
        //注入js，加到body的最后
        int bodyEnd = res.indexOf("</body>");
        if (bodyEnd > 0) {
            res = res.substring(0, bodyEnd) + js + res.substring(bodyEnd, res.length());
            isInject = true;
        }

        return res;
    }

    /**
     * 原生详情页注入css和js
     *
     * @param htmlCode   assert中的html模板
     * @param content    接口中的html内容
     * @param cssContent assert中的css文本内容,目前场景为日夜间模式css
     * @param jsPath     assert中的js文件路径
     * @param css        程序初始下发的css集合
     * @param js         程序初始下发的js集合
     * @return 注入之后的代码
     */
    public String detailInjectCssJs(String htmlCode, String content, String cssContent, String jsPath, List<String> css, List<String> js) {
        String css_js = "";
        if (!TextUtils.isEmpty(htmlCode)) {
//            String htmlBody = mHelper.getJsObject().setAttrHtmlSrc(content);
            String cssTotal = "<link id=\"ui_mode_link\" charset=\"UTF-8\" href=\"%1$s\" " +
                    "rel=\"stylesheet\" type=\"text/css\"/>";
            String html = "<script type=\"text/javascript\" charset=\"UTF-8\" src=\"%1$s\"></script>";
            css_js += String.format(cssTotal, cssContent);
            css_js += String.format(html, jsPath);
            if (css != null && !css.isEmpty()) {
                for (int i = 0; i < css.size(); i++) {
                    css_js += String.format(cssTotal, css.get(i));
                }
            }

            if (js != null && !js.isEmpty()) {
                for (int i = 0; i < js.size(); i++) {
                    css_js += String.format(html, js.get(i));
                }
            }
            return String.format(htmlCode, css_js, content);
        }
        return null;
    }

}
