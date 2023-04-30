package de.haizon.pixelcloud.master.backend.dependencies;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DependencyInject {

    String groupId();
    String artifactId();
    String version();

}
