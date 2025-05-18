package com.mall.common.result;

public interface ResultCode {
  /**
   * 成功状态码
   */
  int SUCCESS=20000;
  /**
   * 失败状态码
   */
  int ERROR=50000;
  /**
   * 没有权限状态码
   */
  int AUTH=403;
}
