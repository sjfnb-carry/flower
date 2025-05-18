package com.mall.common.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FuyouResult {
        // 定义jackson对象
        private static final ObjectMapper MAPPER = new ObjectMapper();

        // 响应业务状态
        private Integer status;

        // 响应消息
        private String msg;

        // 响应中的数据
        private Object data;

        @JsonIgnore
        private String ok;	// 不使用

        public static FuyouResult build(Integer status, String msg, Object data) {
            return new FuyouResult(status, msg, data);
        }

        public static FuyouResult build(Integer status, String msg, Object data, String ok) {
            return new FuyouResult(status, msg, data, ok);
        }

        public static FuyouResult ok(Object data) {
            return new FuyouResult(data);
        }

        public static FuyouResult ok() {
            return new FuyouResult(null);
        }

        public static FuyouResult errorMsg(String msg) {
            return new FuyouResult(500, msg, null);
        }

        public static FuyouResult errorMap(Object data) {
            return new FuyouResult(501, "error", data);
        }

        public static FuyouResult errorTokenMsg(String msg) {
            return new FuyouResult(502, msg, null);
        }

        public static FuyouResult errorException(String msg) {
            return new FuyouResult(555, msg, null);
        }

        public static FuyouResult errorUserQQ(String msg) {
            return new FuyouResult(556, msg, null);
        }

        public FuyouResult() {

        }

        public FuyouResult(Integer status, String msg, Object data) {
            this.status = status;
            this.msg = msg;
            this.data = data;
        }

        public FuyouResult(Integer status, String msg, Object data, String ok) {
            this.status = status;
            this.msg = msg;
            this.data = data;
            this.ok = ok;
        }

        public FuyouResult(Object data) {
            this.status = 200;
            this.msg = "OK";
            this.data = data;
        }

        public Boolean isOK() {
            return this.status == 200;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public String getOk() {
            return ok;
        }

        public void setOk(String ok) {
            this.ok = ok;
        }
}
