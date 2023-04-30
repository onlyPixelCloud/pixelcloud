package de.haizon.pixelcloud.api.logger;

public interface ICloudLogger {

    void info(String message);

    void severe(String message);

    void warning(String message);

    void success(String message);

}
