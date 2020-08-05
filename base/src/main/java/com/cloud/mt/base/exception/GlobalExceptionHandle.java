package com.cloud.mt.base.exception;

import com.cloud.mt.base.http.HirResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;

/**
 * @Author HAI
 * @Date 2019/6/26 14:04
 * @Version 1.0
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandle {

	@ExceptionHandler(value = ErrorCodeException.class)
	@ResponseBody
	public ResponseEntity handleException(ErrorCodeException e) {
		log.error("!-ERROR-! {}", e);
		HirResponse response = new HirResponse();
		response.setCode(e.getErrCode());
		response.setMsg(e.getErrMsg());
		// response.setData();
		if ("SYS_USER_NOT_LOGIN".equals(e.getErrCode())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public HirResponse handleException(Exception e) {
		log.error("!-ERROR-! {}", e);
		return HirResponse.error();
	}

	/**
	 * 参数校验的异常捕获
	 *
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = ConstraintViolationException.class)
	@ResponseBody
	public HirResponse handleException(ConstraintViolationException e) {
		log.error("!-ERROR-! {}", e);
		return HirResponse.error(e.getLocalizedMessage());
	}

	/**
	 * 缺少参数的异常捕获
	 *
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = MissingServletRequestParameterException.class)
	@ResponseBody
	public HirResponse handleException(MissingServletRequestParameterException e) {
		log.error("!-ERROR-! {}", e);
		return HirResponse.error(e.getLocalizedMessage());
	}

	/**
	 * 参数校验异常捕获
	 *
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseBody
	public HirResponse handleException(MethodArgumentNotValidException e) {
		log.error("!-ERROR-! {}", e);
		return HirResponse.error(e.getBindingResult().getFieldError().getDefaultMessage());
	}
}
