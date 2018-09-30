package com.mcrminer;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
@ActiveProfiles(value = {"test"})
public @interface DatabaseTest {
}
