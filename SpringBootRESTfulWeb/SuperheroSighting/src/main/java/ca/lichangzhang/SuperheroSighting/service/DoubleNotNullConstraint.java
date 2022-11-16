package ca.lichangzhang.SuperheroSighting.service;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

/**　Email: xiaoyuzhang668@gmail.com
 *   Date: 2022
 *
 * @author catzh
 */
@Documented
@Constraint(validatedBy = DoubleNotNullValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation

public @interface DoubleNotNullConstraint {

     String message() default "Value should not be empty";

    String messageNumeric() default "Value should be all numeric.";
    
    String messageNotZero() default "Value should not be zero.";

    String messageLength() default "Value must be more than 8 digits and less than 10 digits.";

    boolean notEmpty() default false;

    int min() default 0;

    int max() default Integer.MAX_VALUE;
    
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
