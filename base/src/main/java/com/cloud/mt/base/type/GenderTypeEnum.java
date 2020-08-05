package com.cloud.mt.base.type;

/**
 * 性别类型
 *
 * @author kwy
 * @version 2019/6/26
 */
public enum GenderTypeEnum implements ICodeEnum<GenderTypeEnum> {

    /**
     * 男
     */
    MALE("MALE", "男"),
    /**
     * 女
     */
    FEMALE("FEMALE", "女"),
    ;

    private String code;
    private String name;

    GenderTypeEnum(String code, String name) {
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

    public static GenderTypeEnum fromCode(String code) {
        for (GenderTypeEnum typeEnum : GenderTypeEnum.values()) {
            if (typeEnum.toCode().equals(code)) {
                return typeEnum;
            }
        }
        return null;
    }

}
