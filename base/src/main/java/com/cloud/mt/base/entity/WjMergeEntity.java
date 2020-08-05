package com.cloud.mt.base.entity;

import lombok.Data;

//文件树合并实体类
@Data
public class WjMergeEntity {
	//父id
	private String parentId;
	
	private String parentName;
	
	private String id;
	
	private String status;
	
	//目录显示名称
	private String mlxsmc;
	
	private boolean queryMLWJ;
	
	//目录编号
	private String mlbh;
	
	//文件类型
	private String fileType;
	
	private String sfzsdclb;
	//是否有叶子节点
	private boolean leaf;
	
	private String wjhz;
	
	private String wjxh;
	
	//是否是目录，(0:false,1:true)如果是目录则显示第几页
	private String isMl;
	
	
	
}
