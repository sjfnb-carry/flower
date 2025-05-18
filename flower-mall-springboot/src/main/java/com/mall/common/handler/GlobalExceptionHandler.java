package com.mall.common.handler;

import com.mall.common.result.R;
import com.mall.common.result.ResultCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 统一异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  @ResponseBody
  public R error(Exception e){
    e.printStackTrace();
    return R.error().code(ResultCode.ERROR).message(e.getMessage());
  }

  @ExceptionHandler(GlobalException.class)
  @ResponseBody
  public R error(GlobalException e){
    e.printStackTrace();
    return R.error().code(e.getCode()).message(e.getMessage());
  }
}