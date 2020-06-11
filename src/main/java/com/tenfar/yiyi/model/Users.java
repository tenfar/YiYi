package com.tenfar.yiyi.model;

import com.tenfar.yiyi.common.enums.Gender;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * users
 *
 * @author tenfar
 */
@ApiModel(value = "Users")
@Data
public class Users implements Serializable {
    private static final long serialVersionUID = -4602989894592598071L;
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String name;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;
    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "电子邮件")
    private String email;
    /**
     * 电话号码
     */
    @ApiModelProperty(value = "电话号码")
    private String phone;
    /**
     * 性别
     */
    @ApiModelProperty(value = "电话号码")
    private Gender sex;
}