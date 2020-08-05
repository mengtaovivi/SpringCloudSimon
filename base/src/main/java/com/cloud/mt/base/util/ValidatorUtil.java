package com.cloud.mt.base.util;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import java.util.Set;

/**
 * 描述: hibernate-validator校验工具类
 * <p>
 * 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 *
 * @author Zhanggq
 * @date 2019/5/13 15:11
 */
public class ValidatorUtil {
    private static javax.validation.Validator validator;


    static {
        validator = javax.validation.Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     * @param object 待校验对象
     * @param groups 待校验的组
     */
    public static void validateEntity(Object object, Class<? extends ValidGroup>... groups) throws ValidationException{
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        valid(constraintViolations);
        if (groups.length > 0) {
            validateEntity(object);
        }

    }

    /**
     * 打印约束违规信息
     * @param constraintViolations
     */
    public static void valid(Set<? extends ConstraintViolation<?>> constraintViolations) {
        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<Object> constraint = (ConstraintViolation<Object>) constraintViolations.iterator().next();
            throw new ValidationException(constraint.getMessage());
        }
    }
}
