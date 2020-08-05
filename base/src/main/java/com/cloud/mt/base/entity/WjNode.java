package com.cloud.mt.base.entity;

import com.cloud.mt.base.tree.Node;
import lombok.Data;

import java.util.List;


//案件卷宗树节点
@Data
public class WjNode implements Node<WjNode> {

	private List<WjNode> HasChild;

	private List<WjNode> children;

	//孩子节点的数量
	private Integer childNum;

	//是否叶子节点
	private boolean leaf;

	//查询目录文件
	private boolean queryMLWJ;

	private String status;
//    hasChild:[],

	//父id
	private String parentId;

	//父名称
	private String parentName;

	private String id;

	//目录显示名称
	private String mlxsmc;


	//目录编号
	private String mlbh;

	//文件类型
	private String fileType;

	//是否展示导出列表
	private String sfzsdclb;

	private String wjhz;

	private String wjxh;

	//如果是文件，则查看文件下目录是否完全导出 1：true,完全导出，0:false，没完全 导出
	private String isAllImport;

	//是否是目录，(0:false,1:true)如果是目录则显示第几页
	private String isMl;


}
