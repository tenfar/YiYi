package com.tenfar.yiyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tenfar
 */

@Getter
@AllArgsConstructor
public enum Gender implements BaseEnum<Gender, Integer> {

    /*
     * 男
     */
    GENDER_MALE(0, "男"),
    /*
     * 女
     */
    GENDER_FEMALE(1, "女");

    private Integer value;

    private String displayName;

}
