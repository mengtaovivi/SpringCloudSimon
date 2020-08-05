package com.cloud.mt.base.enums;

public enum ShowField {

	ms("1", "民事（含侵权、物权、婚姻家事等）","A"),
	xzyzycq("3", "行政与征用拆迁","C"),
	zscq("4", "知识产权","D"), 
	jrbxzq("5","金融保险证券","E"),
	ldyshbz("6", "劳动与社会保障","F"), 
	gsyht("7", "公司与合同","G"),
	jsgsyfdc("8", "建设工程与房地产","H"),
	ylws("10","医疗卫生","J"), 
	swflyhshs("11", "涉外法律与海商海事","K"),
	zxypc("12", "执行与破产","L"),
	;

	private String id;

	private String name;
	
	private String zjCode;

	ShowField(String id, String name,String zjCode) {
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

	public void setId(String id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getZjCode() {
		return zjCode;
	}

	public void setZjCode(String zjCode) {
		this.zjCode = zjCode;
	}

	public static ShowField getValue(String id) {
		ShowField[] fieldTypes = values();
		for (ShowField field : fieldTypes) {
			if (field.getId().equals(id)) {
				return field;
			}
		}
		return null;
	}
	
	public static ShowField getOneName(String name) {
		ShowField[] fieldTypes = values();
		for (ShowField field : fieldTypes) {
			if (field.getName().equals(name)) {
				return field;
			}
		}
		return null;
	}

}
