package ca.ulaval.glo4003.middleware.auth.annotation;

import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface RBAC {
    Role[] roles() default {};
}
