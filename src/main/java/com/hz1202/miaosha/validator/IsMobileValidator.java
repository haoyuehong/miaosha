package com.hz1202.miaosha.validator;


import  javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.hz1202.miaosha.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;


/**
 * @Author: mol
 * @Description:
 * @Date: create in 15:45 2018/5/4
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;

    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(required) {
            return ValidatorUtil.isMobile(value);
        }else {
            if(StringUtils.isEmpty(value)) {
                return true;
            }else {
                return ValidatorUtil.isMobile(value);
            }
        }
    }

}
