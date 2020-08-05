package com.cloud.mt.base.type;

/**
 * @Author simon
 * @Description 是或否
 * @Date 18:08 2020/7/28
 * @Param
 * @return
 **/
public enum BooleanTypeEnum implements ICodeEnum<BooleanTypeEnum> {

    /**
     * 是
     */
    Y("Y", "是"),
    N("N", "否"),
    ;

    private String code;
    private String name;

    BooleanTypeEnum(String code, String name) {
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

    public static BooleanTypeEnum fromCode(String code) {
        for (BooleanTypeEnum typeEnum : BooleanTypeEnum.values()) {
            if (typeEnum.toCode().equals(code)) {
                return typeEnum;
            }
        }
        return null;
    }

}
