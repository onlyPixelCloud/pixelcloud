package de.haizon.pixelcloud.master.backend.dependencies;

public record Dependency (String groupId, String artifactId, String version){
    public Dependency {
        if (groupId == null || artifactId == null || version == null) {
            throw new IllegalArgumentException("groupId, artifactId and version must not be null");
        }
    }
}
