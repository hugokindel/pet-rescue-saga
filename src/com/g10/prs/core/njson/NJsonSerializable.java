package com.g10.prs.core.njson;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Annotation interface for a NJson serializable type or field. */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NJsonSerializable {
    /** The path to search for this element. */
    String path() default "";
}