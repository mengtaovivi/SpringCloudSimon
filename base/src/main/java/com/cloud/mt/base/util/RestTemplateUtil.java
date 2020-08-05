package com.cloud.mt.base.util;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author HAI
 * @Date 2019/7/17 15:22
 * @Version 1.0
 */
public class RestTemplateUtil {
	
	
	/**
	 * 使用restTemplate发送post（统一封装）
	 */
	
	
	public Map<String,String> responseMap(String url,String systemid,String tyyw_token,Map<String,Object> map) {
		RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        
        headers.add("systemid", systemid);
        headers.add("tyyw_token", tyyw_token);
        
        
        HttpEntity<Map<String,Object>> request=new HttpEntity<Map<String,Object>>(map,headers);
        
        Map<String,String> responseMap= restTemplate.postForObject(url, request, Map.class);
        return responseMap;
	}
	
	
    /**
     * 使用restTemplate发送文件
     * @param fileParam
     * @param url
     * @param file
     * @param params
     * @param tClass
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T>T send(String fileParam,String url,File file, Map<String,Object> params, Class<T> tClass) throws IOException {

        // 上传文件
        byte[] bytesArray= Files.readAllBytes(file.toPath());

        return send(fileParam,file.getName(), url, bytesArray, params, tClass,MediaType.APPLICATION_OCTET_STREAM);
    }


    /**
     * 使用restTemplate发送文件
     * @param fileParam 接口文件接收的参数名，如file
     * @param fileName 文件名
     * @param url   发送的url
     * @param bytesArray    文件的数组
     * @param params    额外参数
     * @param tClass    接口返回类型class
     * @param <T>   接口返回类型
     * @return
     * @throws IOException
     */
    public static <T>T send(String fileParam,String fileName,String url,byte[] bytesArray, Map<String,Object> params, Class<T> tClass,MediaType mediaType) throws IOException {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(mediaType);


        ByteArrayResource contentsAsResource = new ByteArrayResource(bytesArray) {
            @Override
            public String getFilename() {
                return fileName;
            }
        };

        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();

        paramMap.add(fileParam, contentsAsResource);


        if (params!=null&&!params.isEmpty()) {
            params.keySet().forEach(key->{
                paramMap.add(key,params.get(key));
            });
        }

        return restTemplate.postForObject(url, paramMap, tClass);
    }







    public static void main(String[] args) throws IOException {

        String url = "http://127.0.0.1:8081/caseConsulting/jcgImportCaseAndFile";

        File file = new File("C:\\Users\\Hua-cloud\\Desktop\\sdf.sql");

        Map<String, Object> map = new HashMap<>();

        map.put("inlineImport","你好好！");

        String fileParam = "file";

        String send = send(fileParam,url, file, map, String.class);

        System.out.println(send);
    }

}
