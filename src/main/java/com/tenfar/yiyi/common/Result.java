package com.tenfar.yiyi.common;

import com.alibaba.fastjson.JSON;
import com.tenfar.yiyi.common.enums.ResultCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一API响应结果封装
 *
 * @author tenfar
 */
@Data
public class Result implements Serializable {

    private static final long serialVersionUID = -5818698169677465767L;
    private Integer code;
    private String message;
    private Object data;

    public int getCode() {
        return code;
    }

    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.getValue();
        return this;
    }

    public Result setCode(int code) {
        this.code = code;
        return this;
    }


    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
