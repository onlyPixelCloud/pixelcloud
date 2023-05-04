package de.haizon.pixelcloud.api.setup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Question {

    int id();

    String question();

}
