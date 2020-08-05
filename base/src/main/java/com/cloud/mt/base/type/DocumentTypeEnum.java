package com.cloud.mt.base.type;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 文书类型枚举，用于每个文书，示例：
 * 追加起诉书
 * 变更起诉决定书
 * 量刑建议书
 * 撤回起诉决定书
 * @Author: Ifan
 **/
public enum DocumentTypeEnum implements ICodeEnum<DocumentTypeEnum> {
    ADDITIONAL_INDICTMENT_DECISION("ADDITIONAL_INDICTMENT_DECISION", "追加起诉书"),
    CHANGE_INDICTMENT_DECISION("CHANGE_INDICTMENT_DECISION", "变更起诉决定书"),
        SENTENCING_PROPOSAL("SENTENCING_PROPOSAL", "量刑建议书"),
    WITHDRAWAL_OF_INDICTMENT_DECISION("WITHDRAWAL_OF_INDICTMENT_DECISION", "撤回起诉决定书");


    private String code;
    private String name;

    DocumentTypeEnum(String code, String name) {
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

    //讲枚举转换成list格式，这样前台遍历的时候比较容易，列如 下拉框 后台调用toList方法，你就可以得到code 和name了
    public static List toList() {
        List list = Lists.newArrayList();//Lists.newArrayList()其实和new ArrayList()几乎一模
        //  一样, 唯一它帮你做的(其实是javac帮你做的), 就是自动推导(不是"倒")尖括号里的数据类型.

        for (DocumentTypeEnum documentTypeEnum : DocumentTypeEnum.values()) {
            Map<String, String> map = new HashMap<>();
            map.put("code", documentTypeEnum.getCode());
            map.put("name", documentTypeEnum.getName());
            list.add(map);
        }
        return list;
    }

    public static List toListCode() {
        List list = Lists.newArrayList();//Lists.newArrayList()其实和new ArrayList()几乎一模
        //  一样, 唯一它帮你做的(其实是javac帮你做的), 就是自动推导(不是"倒")尖括号里的数据类型.

        for (DocumentTypeEnum documentTypeEnum : DocumentTypeEnum.values()) {
            list.add(documentTypeEnum.getCode());
        }
        return list;
    }

    public static List toListName() {
        List list = Lists.newArrayList();//Lists.newArrayList()其实和new ArrayList()几乎一模
        //  一样, 唯一它帮你做的(其实是javac帮你做的), 就是自动推导(不是"倒")尖括号里的数据类型.

        for (DocumentTypeEnum documentTypeEnum : DocumentTypeEnum.values()) {
            list.add(documentTypeEnum.getName());
        }
        return list;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
