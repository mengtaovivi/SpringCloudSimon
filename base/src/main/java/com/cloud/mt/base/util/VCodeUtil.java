package com.cloud.mt.base.util;

import lombok.extern.java.Log;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 手机验证码生成工具类
 * @Author: Ifan
 **/
@Log
public final class VCodeUtil {

    @Autowired
    private RedisUtil redisUtils;

    public enum CodeDigit {

        FOUR_DIGIT(4, 1000, 9999),
        SIX_DIGIT(6, 100000, 999999);

        public int code;

        public int startVal;

        public int endVal;

        CodeDigit(int code, int startVal, int endVal){
            this.code = code;
            this.startVal = startVal;
            this.endVal = endVal;
        }

    }

    /**
     * 根据CodeDigit枚举生成验证码
     * @return
     */
    public static Integer buildVCode(CodeDigit codeDigit) {
        return RandomUtils.nextInt(codeDigit.startVal, codeDigit.endVal);
    }

    /**
     * 默认生成六位验证码
     * @return
     */
    public static Integer buildVCode() {
        return buildVCode(CodeDigit.SIX_DIGIT);
    }

    /**
     * 验证码转为String类型
     * @return
     */
    public static String buildVCodeStr() {
        return buildVCode().toString();
    }

    /**
     * 根据CodeDigit枚举生成验证码，验证码转为String类型
     * @param codeDigit
     * @return
     */
    public static String buildVCodeStr(CodeDigit codeDigit) {
        return buildVCode(codeDigit).toString();
    }
}
