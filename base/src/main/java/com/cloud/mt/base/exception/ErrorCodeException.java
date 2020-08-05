package com.cloud.mt.base.exception;

/**
 * Created by kwy on 2019/7/1.
 */
public class ErrorCodeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String errCode;
    private String errMsg;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public ErrorCodeException(String errCode, String errMsg, Object... variables) {
        this.errCode = errCode;
        this.errMsg = (variables!=null && variables.length>0)?String.format(errMsg, variables):errMsg ;
    }

    public ErrorCodeException(ErrorCode error, Object... variables) {
        super((variables!=null && variables.length>0)?String.format(error.getMsg(), variables):error.getMsg());
        this.errCode = error.getCode();
        this.errMsg = (variables!=null && variables.length>0)?String.format(error.getMsg(), variables):error.getMsg() ;
    }

}
