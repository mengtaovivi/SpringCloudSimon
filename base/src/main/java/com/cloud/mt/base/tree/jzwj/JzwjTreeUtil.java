package com.cloud.mt.base.tree.jzwj;


import com.cloud.mt.base.entity.WjMergeEntity;
import com.cloud.mt.base.entity.WjNode;
import com.cloud.mt.base.tree.TreeUtil;

import java.util.List;

//卷宗文件树转换辅助类工具
public class JzwjTreeUtil {

	//实体类转化为树节点
	public static class JzwjNodeAdapter implements TreeUtil.NodeAdapter<WjMergeEntity, WjNode> {
		@Override
		public WjNode adapte(WjMergeEntity wjMergeEntity) {
			WjNode wjNode = new WjNode();
			wjNode.setFileType(wjMergeEntity.getFileType());
			wjNode.setId(wjMergeEntity.getId().trim());
			wjNode.setMlbh(wjMergeEntity.getMlbh());
			wjNode.setMlxsmc(wjMergeEntity.getMlxsmc());
			wjNode.setSfzsdclb(wjMergeEntity.getSfzsdclb());
			if (wjMergeEntity.getParentId() != null) {
				wjNode.setParentId(wjMergeEntity.getParentId().trim());
				wjNode.setParentName(wjMergeEntity.getParentName());
			}
			wjNode.setQueryMLWJ(wjMergeEntity.isQueryMLWJ());
			wjNode.setLeaf(wjMergeEntity.isLeaf());
			wjNode.setWjhz(wjMergeEntity.getWjhz());
			wjNode.setStatus(wjMergeEntity.getStatus());
			wjNode.setWjxh(wjMergeEntity.getWjxh());
			wjNode.setIsMl(wjMergeEntity.getIsMl());
			return wjNode;
		}

	}

	public static class JzwjNodeFilter implements TreeUtil.NodeFilter {
		@Override
		public boolean accept(Object model, int depth, boolean leaf) {
			// TODO Auto-generated method stub
			return true;
		}

	}

	public static class JzwjIdAccess implements TreeUtil.IdAccess<WjMergeEntity, String> {

		@Override
		public String getId(WjMergeEntity node) {
			return node.getId().trim();
		}

		@Override
		public String getParentId(WjMergeEntity node) {
			return node.getParentId().trim();
		}

		@Override
		public void setIdPath(WjMergeEntity node, String idPath) {
		}
	}


	public static List<WjNode> jzwjBuildTree(String parentId, List<WjMergeEntity> entityLists) {
		return TreeUtil.buildTree(parentId, entityLists, new JzwjNodeAdapter(), new JzwjNodeFilter(), new JzwjIdAccess());
	}


}
