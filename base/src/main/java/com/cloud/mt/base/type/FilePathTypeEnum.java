package com.cloud.mt.base.type;

/**
 * 文件路径类型
 *
 * @author kwy
 * @version 2019/7/2
 */
public enum FilePathTypeEnum implements ICodeEnum<FilePathTypeEnum> {

    /**
     * LOCAL_FILE
     */
    LOCAL_FILE("LOCAL_FILE", "本地文件"),
    /**
     * URL_FILE
     */
    URL_FILE("URL_FILE", "url文件"),
    ;

    private String code;
    private String name;

    FilePathTypeEnum(String code, String name) {
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

    public static FilePathTypeEnum fromCode(String code) {
        for (FilePathTypeEnum typeEnum : FilePathTypeEnum.values()) {
            if (typeEnum.toCode().equals(code)) {
                return typeEnum;
            }
        }
        return null;
    }

}
