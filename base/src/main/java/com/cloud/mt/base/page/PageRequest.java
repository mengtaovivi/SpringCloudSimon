package com.cloud.mt.base.page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页请求
 * @author Ifan
 */
@ApiModel("分页请求")
public class PageRequest {
	/**
	 * 当前页码
	 */
	@ApiModelProperty(value = "当前页码")
	private int pageNum = 1;
	/**
	 * 每页数量
	 */
	@ApiModelProperty(value = "每页数量")
	private int pageSize = 10;

	@ApiModelProperty(value = "业务参数")
	private Map<String, Object> data = new HashMap<>();

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

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public <T> T getDataBean(Class<T> clazz) {
		String jsonString = JSON.toJSONString(data);
		return JSONObject.parseObject(jsonString, clazz);
	}

	/**
	 * 获取列表类型的参数
	 * @param key
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public <T> List<T> getDataList(String key, Class<T> clazz) {
		Object dataObj = data.get(key);
		Object json = JSONObject.toJSON(data);
		if(json instanceof JSONArray){
			JSONArray array = ((JSONArray) json);
			return array.toJavaList(clazz);
		}
		throw new NullPointerException();
	}

	/**
	 * 获取String类型的参数
	 * @param key
	 * @return
	 */
	public String getStringParam(String key){
		Object object = data.get(key);
		if(object!=null){
			return object.toString();
		}
		return null;
	}

	/**
	 * 获取Integer类型的参数
	 * @param key
	 * @return
	 */
	public Integer getIntegerParam(String key) {
		Object value = data.get(key);
		Integer number = null;
		try {
			if (value instanceof String) {
				number = Integer.parseInt(value.toString());
			}
			if (value instanceof Number) {
				number = ((Number) value).intValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return number;
	}

}
