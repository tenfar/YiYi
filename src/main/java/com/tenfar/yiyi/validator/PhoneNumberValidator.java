package com.tenfar.yiyi.validator;

import com.tenfar.yiyi.annotation.PhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义校验器 校验是否合法的电话号码
 * 判断字符串是否为有效的手机号码(支持虚拟运营商)
 * 各运营商手机号段, 更新日期:2020-06-11
 * 移动:134, 135, 136, 137, 138, 139, 147, 150, 151, 152, 157, 158, 159, 178, 182, 183, 184, 187, 188
 * 联通:130, 131, 132, 145, 155, 156, 175, 176, 185, 186
 * 电信:133, 149, 153, 173, 177, 180, 181, 189
 * 虚拟:170, 171
 *
 * @author tenfar
 */
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    private static final int MOBILE_LENGTH = 11;
    private static final String MOBILE_PHONE_REGEX = "^1(3(4[0-8]\\d{7}|[0-3,5-9]\\d{8})|(4[579]|5[0-3,5-9]|7[0,1,3,5-8]|8\\d)\\d{8})$";
    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //null 时不进行校验
        if (value != null && value.matches(MOBILE_PHONE_REGEX) && value.length() == MOBILE_LENGTH) {
            // 获取默认提示信息
            String defaultConstraintMessageTemplate = context.getDefaultConstraintMessageTemplate();
            System.out.println("default message :" + defaultConstraintMessageTemplate);
            // 禁用默认提示信息
            context.disableDefaultConstraintViolation();
            // 设置提示语
            context.buildConstraintViolationWithTemplate("请输入合法的手机号码").addConstraintViolation();
            return false;
        }
        return true;
    }
}
