package com.cloud.mt.base.util;



import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * @author licheng
 * @description aes工具类
 * @date 2019/6/17 10:46
 */

public class AesUtil {


    /**
     * 加密密钥 长度必须是 16
     */
    private static final String KEY = "dwzblbonirih1zhh";

    /**
     * 初始化向量 长度必须是 16
     */
    private static final String IV = "r0bxysfo9wwhxq4k";

    /**
     * 参数分别代表 算法名称/加密模式/数据填充方式
     *
     * <p>CBC模式 必须指定初始化向量</p>
     */
    private static final String ALGORITHMSTR = "AES/CBC/PKCS5Padding";

    /**
     * 加密算法
     */
    private static final  String ALGORITHM = "AES";

    /**
     * 加密
     *
     * @param content 加密的字符串
     * @return
     * @throws Exception
     */
    public static String encrypt(String content) throws Exception{
        return encrypt(content, KEY, IV);
    }

    /**
     * 加密
     *
     * @param content 加密的字符串
     * @param key     秘钥
     * @return
     * @throws Exception
     */
    public static String encrypt(String content, String key) throws Exception{
        return encrypt(content, key, IV);
    }

    /**
     * 加密
     * <p>CBC模式 必须指定初始化向量</p>
     *
     * @param content 加密字符串
     * @param key     秘钥
     * @param iv      初始化向量是16位长度的字符串
     * @return
     * @throws Exception
     */
    public static String encrypt(String content, String key, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        if (iv == null) {
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), ALGORITHM));
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), ALGORITHM), new IvParameterSpec(iv.getBytes()));
        }
        byte[] b = cipher.doFinal(content.getBytes("utf-8"));
        // 采用base64算法进行转码,避免出现中文乱码
        return Base64.getEncoder().encodeToString(b);
    }

    /**
     * 解密
     *
     * @param content 解密的字符串
     * @return
     * @throws Exception
     */
    public static String decrypt(String content) throws Exception {
        return decrypt(content, KEY, IV);
    }
    /**
     * 解密
     *
     * @param content 解密的字符串
     * @param key     密钥
     * @return
     * @throws Exception
     */
    public static String decrypt(String content, String key) throws Exception {
        return decrypt(content, key, IV);
    }

    /**
     * 解密
     * <p>CBC模式 必须指定初始化向量</p>
     *
     * @param content 解密的字符串
     * @param key     密钥
     * @param iv      初始化向量是16位长度的字符串
     * @return
     * @throws Exception
     */
    public static String decrypt(String content, String key, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        if (iv == null) {
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), ALGORITHM));
        } else {
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), ALGORITHM), new IvParameterSpec(iv.getBytes()));
        }

        // 采用base64算法进行转码,避免出现中文乱码
        byte[] encryptBytes = Base64.getDecoder().decode(content);
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    public static void main(String args[]) throws Exception {

        String content = "{\n" +
                " \"type\":\"0\",\n" +
                "    \"userId\":\"101\"\n" +
                "    \"caseId\":\"1" +
                "    \"groupId\":\"2\"\n" +
                "}";

        //加密
        String encrypted = encrypt(content);

        System.out.println("加密前：" + content);

        System.out.println("加密后：" + encrypted.replace("/","#"));
        //解密
        String decrypted = decrypt(encrypted.replace("#","/"));

        System.out.println("解密后：" + decrypted);
    }

}
