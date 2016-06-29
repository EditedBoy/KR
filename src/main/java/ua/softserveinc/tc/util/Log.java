package ua.softserveinc.tc.util;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by edward on 6/12/16.
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Log {
}
