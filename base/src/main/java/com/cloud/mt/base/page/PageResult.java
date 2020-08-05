package com.cloud.mt.base.page;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 分页返回结果
 * @author Louis
 * @date Aug 19, 2018
 */
@ApiModel("分页返回结果")
public class PageResult<T> {
	/**
	 * 当前页码
	 */
	@ApiModelProperty(value = "当前页码")
	private int pageNum;
	/**
	 * 每页数量
	 */
	@ApiModelProperty(value = "每页数量")
	private int pageSize;
	/**
	 * 记录总数
	 */
	@ApiModelProperty(value = "记录总数")
	private long totalSize;
	/**
	 * 页码总数
	 */
	@ApiModelProperty(value = "页码总数")
	private int totalPages;
	/**
	 * 分页数据
	 */
	@ApiModelProperty(value = "分页数据")
	private List<T> content;
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public long getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public List<T> getContent() {
		return content;
	}
	public void setContent(List<T> content) {
		this.content = content;
	}
}
