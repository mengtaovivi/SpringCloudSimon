package com.cloud.mt.base.exception;

/**
 * @Author simon
 * @Description  自定义异常
 * @Date 10:31 2020/6/12
 * @Param
 * @return
 **/
public class ApiException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private int code;
  private String message;

  public ApiException(String message) {
    this.code = -1;
    this.message = message;
  }

  public ApiException(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
