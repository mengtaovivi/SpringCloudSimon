package com.cloud.mt.base.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Set;

/**
 * 公众平台通用接口工具类
 * @author kwy
 */
public class WxHttpUtil {
    private static Logger log = LoggerFactory.getLogger(WxHttpUtil.class);
    private static String DEFAULT_CHARSET = "UTF-8";
    private static int DEFAULT_CONNECT_TIMEOUT = 3000;
    private static int DEFAULT_READ_TIMEOUT = 3000;


    public static String doGet(String url) {
        return httpRequest(url, "GET", null, DEFAULT_CHARSET, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT, null);
    }
    public static String doGet(String url, int connectTimeout, int readTimeout) {
        return httpRequest(url, "GET", null, DEFAULT_CHARSET, connectTimeout, readTimeout, null);
    }
    public static String doGet(String url, int connectTimeout, int readTimeout, String charset) {
        return httpRequest(url, "GET", null, charset, connectTimeout, readTimeout, null);
    }
    public static String doGet(String url, int connectTimeout, int readTimeout, String charset, Map<String, String> headerMap) {
        return httpRequest(url, "GET", null, charset, connectTimeout, readTimeout, headerMap);
    }
    /**
     * url为不带 ? 号的url
     */
    public static String doGet(String url, Map<String, String> paramMap) throws IOException {
        String query = buildQuery(paramMap, DEFAULT_CHARSET);
        return httpRequest(url + "?" + query, "GET", null, DEFAULT_CHARSET, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT, null);
    }
    /**
     * url为不带 ? 号的url
     */
    public static String doGet(String url, Map<String, String> paramMap, String charset, int connectTimeout, int readTimeout) throws IOException {
        String query = buildQuery(paramMap, charset);
        return httpRequest(url + "?" + query, "GET", null, charset, connectTimeout, readTimeout, null);
    }
    /**
     * url为不带 ? 号的url
     */
    public static String doGet(String url, Map<String, String> paramMap, String charset, int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
        String query = buildQuery(paramMap, charset);
        return httpRequest(url + "?" + query, "GET", null, charset, connectTimeout, readTimeout, headerMap);
    }




    public static String doPost(String url, String jsonParam){
        return httpRequest(url, "POST", jsonParam, DEFAULT_CHARSET, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT, null);
    }
    public static String doPost(String url, String jsonParam, String charset, int connectTimeout, int readTimeout) {
        return httpRequest(url, "POST", jsonParam, charset, connectTimeout, readTimeout, null);
    }
    public static String doPost(String url, String jsonParam, String charset, int connectTimeout, int readTimeout, Map<String, String> headerMap) {
        return httpRequest(url, "POST", jsonParam, charset, connectTimeout, readTimeout, headerMap);
    }
    /**
     * url为不带 ? 号的url
     */
    public static String doPost(String url, String jsonParam, Map<String, String> urlParamMap) throws IOException {
        String query = buildQuery(urlParamMap, DEFAULT_CHARSET);
        return httpRequest(url + "?" + query,"POST", jsonParam, DEFAULT_CHARSET, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT, null);
    }
    public static String doPost(String url, String jsonParam, Map<String, String> paramMap, String charset, int connectTimeout, int readTimeout) throws IOException {
        String query = buildQuery(paramMap, charset);
        return httpRequest(url + "?" + query, "POST", jsonParam, charset, connectTimeout, readTimeout, null);
    }
    public static String doPost(String url, String jsonParam, Map<String, String> paramMap, String charset, int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
        String query = buildQuery(paramMap, charset);
        return httpRequest(url + "?" + query, "POST", jsonParam, charset, connectTimeout, readTimeout, headerMap);
    }


    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return String
     */
    private static String httpRequest(String requestUrl, String requestMethod, String outputStr, String charset, int connectTimeout, int readTimeout, Map<String, String> headerMap) {
        StringBuilder buffer = new StringBuilder();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("TLSV1"); //使用TLS协议加密
//          SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setConnectTimeout(connectTimeout);
            httpUrlConn.setReadTimeout(readTimeout);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if(headerMap!=null){
                for(Map.Entry<String, String> entry : headerMap.entrySet()){
                    httpUrlConn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            if ("GET".equalsIgnoreCase(requestMethod)){
                httpUrlConn.connect();
            }

            // 当有数据需要提交时
            if (StringUtils.isNotBlank(outputStr)) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes(charset));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            httpUrlConn.disconnect();
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:", e);
        }
        return buffer.toString();
    }


    private static String buildQuery(Map<String, String> params, String charset) throws IOException {
        if (params == null || params.isEmpty()) {
            return null;
        }

        StringBuilder query = new StringBuilder();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        boolean hasParam = false;

        for (Map.Entry<String, String> entry : entries) {
            String name = entry.getKey();
            String value = entry.getValue();
            // 忽略参数名或参数值为空的参数
            if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)) {
                if (hasParam) {
                    query.append("&");
                } else {
                    hasParam = true;
                }

                query.append(name).append("=").append(URLEncoder.encode(value, charset));
            }
        }

        return query.toString();
    }
}

/**
 * 证书信任管理器（用于https请求）
 */
class MyX509TrustManager implements X509TrustManager {

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}