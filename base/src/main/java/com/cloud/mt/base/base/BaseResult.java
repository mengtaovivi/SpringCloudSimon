package com.cloud.mt.base.base;

import com.cloud.mt.base.enums.SuccessCode;
import com.cloud.mt.base.exception.ErrorCode;
import com.cloud.mt.base.exception.ErrorCodeException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author simon
 * @Description result
 * @Date 17:59 2020/7/28
 * @Param
 * @return
 **/
public class BaseResult implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 返回码
	 */
	private String code;

	/**
	 * 提示
	 */
	private String message;

	/**
	 * data
	 */
	private Map<String, Object> dataMap;

	/**
	 * 总条数
	 */
	private Long total;

	public BaseResult(ErrorCode errorCode) {
		this.code = errorCode.getCode();
		this.message = errorCode.getMsg();
	}

	public BaseResult(SuccessCode successCode) {
		super();
		this.code = successCode.getCode();
		this.message = successCode.getMessage();
	}

	public BaseResult(SuccessCode successCode, Map<String, Object> data) {
		super();
		this.code = successCode.getCode();
		this.message = successCode.getMessage();
		this.dataMap = data;
	}

	public BaseResult(SuccessCode successCode, Map<String, Object> data, Long total) {
		super();
		this.code = successCode.getCode();
		this.message = successCode.getMessage();
		this.dataMap = data;
		this.total = total;
	}

	public BaseResult(SuccessCode successCode, String key, Object value) {
		super();
		this.dataMap = new HashMap<String, Object>();
		this.dataMap.put(key, value);
		this.code = successCode.getCode();
		this.message = successCode.getMessage();
	}

	public BaseResult(ErrorCodeException e) {
		this.code = e.getErrCode();
		this.message = e.getErrMsg();
	}

	public BaseResult(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

}
