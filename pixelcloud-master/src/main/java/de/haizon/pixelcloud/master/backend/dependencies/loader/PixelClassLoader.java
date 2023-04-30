package de.haizon.pixelcloud.master.backend.dependencies.loader;

import java.net.URL;
import java.net.URLClassLoader;

public class PixelClassLoader extends URLClassLoader {

    public PixelClassLoader(ClassLoader parent) {
        super(new URL[0], parent);
    }

    public void addURL(URL url) {
        super.addURL(url);
    }
}
