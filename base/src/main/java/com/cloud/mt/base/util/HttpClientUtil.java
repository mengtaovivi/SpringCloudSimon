package com.cloud.mt.base.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;


/**
 * Created by frt on 2017/10/30.
 *
 */
public class HttpClientUtil {
    private static PoolingHttpClientConnectionManager connectionManager = null;
    private static CloseableHttpClient httpClient = null;
    private static final String DEFAULT_CHARSET = "UTF-8";

    private HttpClientUtil(){

    }

    static {
        ConnectionSocketFactory plainFactory = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslFactory = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", plainFactory)
                .register("https", sslFactory)
                .build();
        connectionManager = new PoolingHttpClientConnectionManager(registry);
        //设置最大连接数
        connectionManager.setMaxTotal(200);
        //设置每个路由最大连接数
        connectionManager.setDefaultMaxPerRoute(20);
    }

    public synchronized static CloseableHttpClient getHttpClient(){
        if(httpClient==null){
            httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        }

        return httpClient;
    }

    private static HttpEntity getHttpEntity(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpGet, HttpClientContext.create());
        return response.getEntity();
    }

    private static String getQueryString(Map<String, Object> params) {
        if (params!=null && params.size()>0) {
            StringBuilder query = new StringBuilder();
            for (String key : params.keySet()) {
                Object val = params.get(key);
                if (val == null) {
                    continue;
                }
                if (val instanceof String || val instanceof Number) {
                    query.append(key).append("=").append(val).append("&");
                } else if (val instanceof Number[]) {
                    Number[] arr = (Number[]) val;
                    for (Number a : arr) {
                        query.append(key).append("=").append(a).append("&");
                    }
                } else if (val instanceof String[]) {
                    String[] arr = (String[]) val;
                    for (String a : arr) {
                        query.append(key).append("=").append(a).append("&");
                    }
                } else if (val instanceof int[]) {
                    int[] arr = (int[]) val;
                    for (int a : arr) {
                        query.append(key).append("=").append(a).append("&");
                    }
                } else if (val instanceof double[]) {
                    double[] arr = (double[]) val;
                    for (double a : arr) {
                        query.append(key).append("=").append(a).append("&");
                    }
                } else if (val instanceof long[]) {
                    long[] arr = (long[]) val;
                    for (long a : arr) {
                        query.append(key).append("=").append(a).append("&");
                    }
                }
            }
            String temp = query.toString();
            return temp.length()>0 ? temp.substring(0, temp.length() - 1) : temp;
        }
        return "";
    }

    private static String getMethod(String url, String charset, Map<String, Object> params) throws IOException {
        String queryString = HttpClientUtil.getQueryString(params);
        String requestUrl = StringUtils.isBlank(queryString) ? url : url + "?" + queryString;
        HttpEntity httpEntity = HttpClientUtil.getHttpEntity(requestUrl);
        String result = EntityUtils.toString(httpEntity, charset);
        EntityUtils.consume(httpEntity);
        return result;
    }

    private static String postMethod(String url, String charset, Object param) throws IOException {
        CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-type", "application/json; charset=utf-8");
        httpPost.setHeader("Accept", "application/json");

        StringEntity stringEntity = new StringEntity(JSON.toJSONString(param), charset);
        stringEntity.setContentEncoding(charset);
        stringEntity.setContentType("application/json");

        httpPost.setEntity(stringEntity);
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        String result = EntityUtils.toString(httpEntity, charset);
        EntityUtils.consume(httpEntity);
        return result;
    }

    private static String postMethod(String url, File[] file) throws IOException {
        CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
        HttpPost httpPost = new HttpPost(url);

        //创建上传文件的表单
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();

        //entityBuilder.addTextBody("test", "test");添加普通参数
        //添加上传的文件
        if(file.length == 1){
            entityBuilder.addPart("file",new FileBody(file[0]));
        }else {
            for (int i = 0; i <file.length; i++) {
                entityBuilder.addPart("file" + (i+1),new FileBody(file[i]));
            }
        }
        HttpEntity stringEntity = entityBuilder.build();

        httpPost.setEntity(stringEntity);
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        String result = EntityUtils.toString(httpEntity,DEFAULT_CHARSET);
        EntityUtils.consume(httpEntity);
        return result;
    }

    
    //加参数发送文件
    public static InputStream postMethod(String url, File file,Map<String,String> map) throws IOException {
        CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
        HttpPost httpPost = new HttpPost(url);

        //创建上传文件的表单
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder.setCharset(Charset.forName("UTF-8"));
        ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);
        for(String key:map.keySet()) {
            entityBuilder.addPart(key, new StringBody(map.get(key),contentType));
        }
        //entityBuilder.addTextBody("test", "test");添加普通参数
//        entityBuilder.addTextBody(name, text);
        //添加上传的文件
        entityBuilder.addPart("FileBytes",new FileBody(file));
        HttpEntity stringEntity = entityBuilder.build();

        httpPost.setEntity(stringEntity);
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        InputStream is=httpEntity.getContent();
//        EntityUtils.consume(httpEntity);
        return is;
    }
    
    
    
    
    
    

    public static String doGet(String url) throws IOException {
        return HttpClientUtil.getMethod(url, DEFAULT_CHARSET, null);
    }

    public static String doGet(String url, String charset) throws IOException {
        return HttpClientUtil.getMethod(url, charset, null);
    }

    public static String doGet(String url, Map<String, Object> params) throws IOException {
        return HttpClientUtil.getMethod(url, DEFAULT_CHARSET, params);
    }

    public static String doGet(String url, String charset, Map<String, Object> params) throws IOException {
        return HttpClientUtil.getMethod(url, charset, params);
    }

    public static String doPost(String url, Object param) throws IOException {
        return HttpClientUtil.postMethod(url, DEFAULT_CHARSET, param);
    }

    public static String doPost(String url, String charset, Object param) throws IOException {
        return HttpClientUtil.postMethod(url, charset, param);
    }

    public static String doFilePost(String url, File ...file) throws IOException {
        return HttpClientUtil.postMethod(url, file);
    }

}
