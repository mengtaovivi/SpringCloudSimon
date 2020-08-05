package com.cloud.mt.base.type;

/**
 * @Author simon
 * @Description 浏览器引擎类型，浏览器可能不止一个引擎
 * @Date 18:08 2020/7/28
 * @Param
 * @return
 **/
public enum BrowserTypeEnum implements ICodeEnum<BrowserTypeEnum> {

    /**
     * 主要IE系列浏览器
     */
    MSIE("MSIE", "主要IE系列浏览器"),
    /**
     * 兼容浏览模式，IE、傲游、世界之窗浏览器、Avant、腾讯TT、Sleipnir、GOSURF、GreenBrowser和KKman
     */
    TRIDENT("Trident", "兼容浏览模式，IE、傲游、世界之窗浏览器、Avant、腾讯TT、Sleipnir、GOSURF、GreenBrowser和KKman"),
    /**
     * 新IE
     */
    EDGE("Edge", "新IE"),
    ;

    private String code;
    private String name;

    BrowserTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toName() {
        return name;
    }

    @Override
    public String toCode() {
        return code;
    }

    public static BrowserTypeEnum fromCode(String code) {
        for (BrowserTypeEnum typeEnum : BrowserTypeEnum.values()) {
            if (typeEnum.toCode().equals(code)) {
                return typeEnum;
            }
        }
        return null;
    }

}
