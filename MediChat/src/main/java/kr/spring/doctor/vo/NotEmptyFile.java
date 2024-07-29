package kr.spring.doctor.vo;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotEmptyFileValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmptyFile {
    String message() default "파일을 선택해 주세요.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
