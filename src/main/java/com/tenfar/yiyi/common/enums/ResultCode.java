package com.tenfar.yiyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应码枚举，参考HTTP状态码的语义
 *
 * @author tenfar
 */
@Getter
@AllArgsConstructor
public enum ResultCode implements BaseEnum<ResultCode, Integer> {
    /**
     *
     */
    SUCCESS(200, "请求成功"),
    FAIL(400, "请求失败"),
    ARGUMENT_ERROR(402, "参数错误"),
    UNAUTHORIZED(401, "未认证"),
    NOT_FOUND(404, "接口不存在"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误");

    private Integer value;
    private String displayName;

}
