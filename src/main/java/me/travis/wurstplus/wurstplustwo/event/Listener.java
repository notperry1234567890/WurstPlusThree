// =============================================== //
// Recompile disabled. Please run Recaf with a JDK //
// =============================================== //

package me.travis.wurstplus.wurstplustwo.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Listener {
    int value() default 0;
}
