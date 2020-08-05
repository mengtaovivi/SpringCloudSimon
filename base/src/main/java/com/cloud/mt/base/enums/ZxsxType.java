package com.cloud.mt.base.enums;

//咨询事项类型type
public enum ZxsxType {
	ajss("案件事实"), //案件事实
	flsy("法律适用"), //法律适用
	ajcx("案件程序")  //案件程序
	;
	
	private String zxsxName;
	
	ZxsxType(String zxsxName){
		this.zxsxName=zxsxName;
	}
	
	public String getZxsxName(){
		return zxsxName;
	}
	
}
