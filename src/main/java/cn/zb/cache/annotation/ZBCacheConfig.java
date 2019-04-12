package cn.zb.cache.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ TYPE })
@Retention(RUNTIME)
public @interface ZBCacheConfig {
    

    String value() default "";

    // 命名空间
    String nameSpace() default "";

}
