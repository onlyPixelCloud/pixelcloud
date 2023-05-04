package de.haizon.pixelcloud.master.backend.files;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class FileManager {

    public void createDirectories(String... directories) {
        for (String directory : directories) {
            File file = new File(directory);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }

    public boolean fileExists(String folder, String fileName){
        File file = new File(folder, fileName);
        return file.exists();
    }

    public boolean folderExists(String folder){
        File file = new File(folder);
        return file.exists();
    }

    public void createFolder(String folder){
        File file = new File(folder);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    public List<File> listFiles(String folder){
        File file = new File(folder);
        if(file.exists()){
            return List.of(Objects.requireNonNull(file.listFiles()));
        }
        return null;
    }

}
