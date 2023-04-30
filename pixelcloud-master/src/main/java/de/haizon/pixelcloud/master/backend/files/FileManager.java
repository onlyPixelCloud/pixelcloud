package de.haizon.pixelcloud.master.backend.files;

import java.io.File;

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

}
