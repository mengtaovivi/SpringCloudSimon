package com.cloud.mt.base.enums;

//案件领域
public enum Field {

	ms("1", "民事（含侵权、物权、婚姻家事等）","A"),
	jj("2", "经济","B"), 
	xzyzycq("3", "行政与征用拆迁","C"),
	zscq("4", "知识产权","D"), 
	jrbxzq("5","金融保险证券","E"),
	ldyshbz("6", "劳动与社会保障","F"), 
	gsyht("7", "公司与合同","G"),
	jsgsyfdc("8", "建设工程与房地产","H"),
	xxwlygxjs("9","信息网络与高新技术","I"),
	ylws("10","医疗卫生","J"), 
	swflyhshs("11", "涉外法律与海商海事","K"),
	zxypc("12", "执行与破产","L"), 
	qt("13", "其他","M"),;

	private final String id;
	private final String name;
	private final String zjCode;

	Field(String id, String name,String zjCode) {
		this.id = id;
		this.name = name;
		this.zjCode = zjCode;
	}

	public String getName() {
		return this.name;
	}

	public String getId() {
		return id;
	}
 
	public String getZjCode() {
		return zjCode;
	}

	public static Field getValue(String id) {
		Field[] fieldTypes = values();
		for (Field field : fieldTypes) {
			if (field.getId().equals(id)) {
				return field;
			}
		}
		return null;
	}
	
	public static Field getOneName(String name) {
		Field[] fieldTypes = values();
		for (Field field : fieldTypes) {
			if (field.getName().equals(name)) {
				return field;
			}
		}
		return null;
	}
}
