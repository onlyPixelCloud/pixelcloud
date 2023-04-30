package de.haizon.pixelcloud.master.backend.dependencies;

import de.haizon.pixelcloud.master.CloudMaster;
import de.haizon.pixelcloud.master.backend.dependencies.loader.PixelClassLoader;
import de.haizon.pixelcloud.master.backend.downloader.UrlDownloader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class DependencyLoader {

    private final Map<Dependency, String> dependencies;

    public DependencyLoader() {
        this.dependencies = new LinkedHashMap<>();
    }

    public void load(Dependency dependency) {
        if (dependencies.containsKey(dependency)) {
            return;
        }

        if (new File("dependencies", dependency.artifactId() + "-" + dependency.version() + ".jar").exists()) {
            CloudMaster.getInstance().getCloudLogger().info("Loading dependency " + dependency.artifactId() + "...");
            dependencies.put(dependency, "file://" + new File("dependencies", dependency.artifactId() + "-" + dependency.version() + ".jar").getAbsolutePath());
        } else {
            CloudMaster.getInstance().getCloudLogger().info("Downloading dependency " + dependency.artifactId() + "...");
            String url = "https://repo1.maven.org/maven2/" + dependency.groupId().replace(".", "/") + "/" + dependency.artifactId() + "/" + dependency.version() + "/" + dependency.artifactId() + "-" + dependency.version() + ".jar";
            dependencies.put(dependency, url);
            new UrlDownloader(url, new File("dependencies", dependency.artifactId() + "-" + dependency.version() + ".jar")).download();
        }
    }

    public void load(Class<?> clazz) {
        if (clazz == null) {
            return;
        }
        DependencyInject dependencyInject = clazz.getAnnotation(DependencyInject.class);
        if (dependencyInject == null) {
            return;
        }

        load(new Dependency(dependencyInject.groupId(), dependencyInject.artifactId(), dependencyInject.version()));
    }

    public void injectClasses() {

        List<URL> loadedDependencyURLsList = new ArrayList<>();

        dependencies.forEach((dependency, s) -> {
            try {
                loadedDependencyURLsList.add(new File("dependencies", dependency.artifactId() + "-" + dependency.version() + ".jar").toURI().toURL());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });

        URL[] loadedDependencyURLs = loadedDependencyURLsList.toArray(new URL[0]);

        try {
            CloudMaster.getInstance().getCloudLogger().info("Injecting dependencies...");

            ClassLoader currentContextClassLoader = Thread.currentThread().getContextClassLoader();
            URL[] urls = new URL[loadedDependencyURLs.length + 1];
            urls[0] = new File("pixelcloud-master-1.0-SNAPSHOT.jar").toURI().toURL();
            System.arraycopy(loadedDependencyURLs, 0, urls, 1, loadedDependencyURLs.length);
            URLClassLoader classLoader = new URLClassLoader(urls, currentContextClassLoader);

            Thread.currentThread().setContextClassLoader(classLoader);

            CloudMaster.getInstance().getCloudLogger().info("Injected dependencies!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Dependency, String> getDependencies() {
        return dependencies;
    }
}
